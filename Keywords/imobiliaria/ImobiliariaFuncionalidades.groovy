package imobiliaria

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.util.List

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import auxiliares.RegrasEmailDestination
import auxiliares.MenuMatriz
import internal.GlobalVariable
import rpacommands.rpaCommands

public class ImobiliariaFuncionalidades extends rpaCommands{

	private static String path
	private static String alerta
	private static TestData data
	private static TestData datatplog
	private static String testDataPath = 'Data Files/residencia/CotaResidData'
	private static int rollCheck


	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(String ramo){
		data = findTestData(retornaDATA(ramo))
		path = data.sourceUrl
	}


	public static dataPath(String aba){
		switch(aba){
			case "imobiliariaEmpresa":
				testDataPath = 'Data Files/Imobiliaria/EmpresaData'
				break;
			case "imobiliariaResidencia":
				testDataPath = 'Data Files/residencia/CotaResidData'
				break
		}
	}
	public static roolCheck(String aba){
		switch(aba){
			case "imobiliariaEmpresa":
				rollCheck = 22
				break;
			case "imobiliariaResidencia":
				rollCheck = 21
				break
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static String retornaDATA(String ramo){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('imobiliariaResidencia','Data Files/Imobiliaria/ResidenciaData');
		urls.put('imobiliariaEmpresa','Data Files/Imobiliaria/EmpresaData');
		return urls.get(ramo.toString());
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static boolean validaDados(int cellStatus){
		System.out.println("RowNumber "+data.getRowNumbers()+" cellStatusCheck "+cellStatus)
		boolean value
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(cellStatus, i).equals('Processar')) {
				value = true
				break;
			} else {
				value = false
			}
		}
		System.err.println('Data ['+value+']')
		return value
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static List<String> lista( List<String> registros , int v){
		for (int i = 1; i <= data.getColumnNumbers(); i++) {
			int d = i -1
			registros[d] = data.getValue(i, v).trim()
		}

		for (int j = 0; j < registros.size();j++ ){
			System.err.println("["+j+"] ["+registros[j]+"]")
		}

		return registros
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static menuEmissao(){
		MenuMatriz.subMenuEquals('/*/body/div/div/app-root/app-private/app-private-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div/ul', "span", "Produção & Sinistros")
		MenuMatriz.subMenuEquals("/*/body/div[1]/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul", "a", "Produção")
	}

	/**
	 * @author be54955
	 */
	public static fluxoImobiliaria(String aba, String tipoSelecao, String a){
		fluxoCompletoImobiliariaFuncionalidades(aba, tipoSelecao,  a)
		reprocessaFalhas(aba,tipoSelecao, a)
		enviarEmailImobiliaria(aba, tipoSelecao)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoImobiliariaFuncionalidades(String aba,  String tipoSelecao, String a){
		System.out.println()
		try{
			dataPath(aba)
			roolCheck(aba)
			initDataFiles(aba)
			System.out.println()
			if(validaDados(rollCheck)){
				acessoAmbiente(aba,a)
				WebUI.delay(20);
				menuEmissao()
				WebUI.waitForPageLoad(15)
				alteraJanejaGenerica(1, 0)
				System.out.println()
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					alerta=""
					//confirmacao=""
					if (data.getValue(rollCheck, i).equals('Processar')) {
						System.out.println("["+i+"] ["+tipoSelecao+"] ["+aba+"]")
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						if(emissaoFluxo(registros, aba,tipoSelecao, a)) {
							try{
								switch (aba){
									case "imobiliariaEmpresa":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[21], 21, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[22], 22, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[23], 23, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[24], 24, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[26], 26, aba);
										break;
									case "imobiliariaResidencia":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[21], 20, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[22], 21, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[23], 22, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[24], 23, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[26], 25, aba);
										break;
								}
							} catch (Exception e){}
						} else {
							try{
								switch (aba){
									case "imobiliariaEmpresa":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[21], 21, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[25], 25, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[26], 26, aba);
										break;
									case "imobiliariaResidencia":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[21], 20, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[25], 24, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[26], 25, aba);
										break;
								}
							} catch (Exception e){}
						}
					}
				}
			}
		}catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param tipoSelecao
	 * @return
	 */
	public static tipo(List<String> registros,String tipoSelecao){
		switch (tipoSelecao){
			case "Emissão":
			case "1240":
				emissao(registros, 133)
				break;
			case "Outras Operações":
				outrasOperacoes(registros,133)
				break;
		}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static emissao(List<String> registros, int r){
		System.out.println()
		//RAMO / MODALIDADE
		limparCamposInput('//*[@id="ramo"]')
		preencheXpath('//*[@id="ramo"]', registros[r], 1);
		//PESQUISAR
		clickXpath('//*[@id="imagenLupa"]', 2);
		//Corretor
		limparCamposInput('//*[@id="mediador"]')
		preencheXpath('//*[@id="mediador"]', registros[0], 1);
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static outrasOperacoes(List<String> registros, int r){
		//RAMO / MODALIDADE
		limparCamposInput('//*[@id="ramo"]')
		preencheXpath('//*[@id="ramo"]', registros[r], 1);
		//PESQUISAR
		clickXpath('//*[@id="imagenLupa"]', 2);
		//Corretor
		preencheXpath('//*[@id="mediador"]', registros[4], 1);
		commandTAB('//*[@id="polSusep_SUSEP"]')
		commandTAB('//*[@id="mediador"]')
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean selecionarCorretor(){
		boolean b = false
		try{
			//Selecionar
			clickXpath('//*[@id="btnSeleccionar"]', 2);
			b = true
		} catch (Exception e){}
		return b
	}

	/**
	 * @author be54955
	 * @param operativa
	 * @return
	 */
	public static selecionarOperativa(String operativa){
		selecionaItemLista(operativa, '//*[@id="codOperativa"]', 'option')
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param aba
	 * @param tipoSelecao
	 * @param a
	 * @return
	 */
	public static boolean emissaoFluxo(List<String> registros, String aba,String tipoSelecao, String a){
		boolean b
		System.out.println("Emissão Ramo ["+tipoSelecao+"]" )
		WebUI.waitForPageLoad(17)
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0))){
			/**
			 * TELA - MENU PRODUÇÃO
			 */
			System.out.println('[Menu Produção]')
			//Tipo de Seleção : Emissão / Outras Operações
			selecionaTipoSelecao(tipoSelecao,'//*[@id="datosSeleccionContent"]/table[1]/tbody/tr',"td")
			WebUI.delay(5);
			//Apolice
			limparCamposInput('//*[@id="polSusep_SUSEP"]')
			String susep = registros[0].replaceAll("-","")
			preencheXpath('//*[@id="polSusep_SUSEP"]', susep, 0);
			WebUI.delay(5);
			alerta = pegaMSGAlerta()
			System.err.println(alerta)
			if(alertListBlocList(alerta)){
				b = false
				registros[25] = alerta
				registros[21] = 'Processado'
				fechaAlerta()
				//HOME
				clickXpath('//*[@id="imgOptMenuBackHome"]', 2);
			} else{
				//Apolice
				commandTAB('//*[@id="polSusep_SUSEP"]')
				if(selecionarCorretor()){
					WebUI.delay(5);
					alerta = pegaMSGAlerta()
					System.err.println(alerta)
					if(alertListBlocList(alerta)){
						b = false
						registros[25] = alerta
						registros[21] = 'Processado'
						fechaAlerta()
						//HOME
						clickXpath('//*[@id="imgOptMenuBackHome"]', 2);
					} else{
						selecionarOperativa('Emitir aplicação/item')
						//AVANÇAR
						selecionaItemLista('Avançar', '//*[@id="seleccionOperativaSectionBar"]/table', 'div')
						WebUI.delay(5);

						try{
							fechaAlerta()
						} catch (Exception e){}

						System.err.println()

						try{
							//SELECIONA RAMO
							switch(aba){
								case "imobiliariaEmpresa":
									selecionaItemLista('Imobiliária Empresa', '//*[@id="row_0_tableRamos"]', 'td')
									break;
								case "imobiliariaResidencia":
									selecionaItemLista('Imobiliária Residência', '//*[@id="row_0_tableRamos"]', 'td')
									break;
							}
							WebUI.delay(5);
						} catch (Exception e){}

						alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
						System.out.println("ALERTA ["+alerta+"] ["+b+"]")
						if(alertListBlocList(alerta)){
							registros[25] = alerta
							registros[21] = 'Processado'
							b = false
							//HOME
							clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
							WebUI.delay(10);
						} else {
							if(dadosBasicos(registros)){
								b = true
								if(dadosRisco(registros)){
									b = true
									if(coberturas(registros, aba)){
										b = true
										if(dadosEmissao(registros)){
											b = true
											if(dadosCliente(registros)){
												b = true
												if(dadosComuns(registros)){
													b = true
													if(clausulas(registros)){
														b = true
														if(confirmacaoEmissao(registros)){
															b = true
															if(emissaoRealizada(registros)){
																b = true
															}else {
																b = false
															}
														} else {
															b = false
														}
													}else {
														b = false
													}
												}else {
													b = false
												}
											}else {
												b = false
											}
										}else {
											b = false
										}
									}else {
										b = false
									}
								} else {
									b = false
								}
							} else {
								b = false
							}
						}
					}//MSG ALERTA
				} else {
					//SELEÇÃO
					b = false
					registros[25] = "Corretor inexistente"
					registros[21] = 'Processado'
					//MENU
					clickXpath('//*[@id="0"]', 5);
				}
			}

			alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
			System.out.println("ALERTA ["+alerta+"] ["+b+"]")
			if(alertListBlocList(alerta)){
				registros[25] = alerta
				registros[21] = 'Processado'
				b = false
				//MENU
				try{
					clickXpath('//*[@id="0"]', 5);
				} catch (Exception e){}
			} else {
				registros[21] = 'Processado'
			}
			//VALIDA TELA
		} else {
			registros[25] = " Tela [Menu Produção] não apresentada"
			registros[21] = 'Processado'
			WebUI.closeBrowser();
			acessoAmbiente(aba,a)
			WebUI.delay(20);
			menuEmissao()
			WebUI.delay(15);
			alteraJanejaGenerica(1, 15)
			b = false
		}
		return b
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean dadosBasicos(List<String> registros){
		System.out.println('[ Dados Basicos ]')
		boolean b = false
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 10))){
			registros[26] = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 10)

			try{
				//Início de Vigência
				limparCamposInput('//*[@id="DatosGeneralesBean$fechaEfecto"]')
				preencheXpath('//*[@id="DatosGeneralesBean$fechaEfecto"]', registros[2], 0);
				commandTAB('//*[@id="DatosGeneralesBean$fechaEfecto"]')
			} catch (Exception e ){}

			try{
				//Categoria do Risco
				selecionaItemListaContains(registros[3], '//*[@id="CategoriaRiesgoBean$catRiesgo"]', 'option')
			} catch (Exception e){}

			try{
				//Avançar
				clicarEmElementoDaTela('//*[@id="btnAceptar"]', 1)
			} catch (Exception e){}
			System.out.println()
			/**
			 * Ajusta casos em que o IOF é apresentado errado na massa de dados
			 */
			contradizIOF()
			b = true
			if(alertListBlocList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5))){
				b = false
			}
		} else {
			registros[25] = " Tela [D. BÁSICOS] não apresentada"
			registros[21] = 'Processado'
			b = false

			try{
				//MENU
				clickXpath('//*[@id="0"]', 5)
			} catch (Exception e){}
		}
		return b
	}

	/**
	 * @author be54955
	 */
	public static contradizIOF(){
		try{
			String a = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			System.out.println('['+a+']')
			switch(a){
				case " Segurado não é Isento de IOF":
					seguradoIsentoIOF("N")
					clicarEmElementoDaTela('//*[@id="btnAceptar"]', 1)
					break;

				case " Segurado Isento de IOF":
					seguradoIsentoIOF("S")
					clicarEmElementoDaTela('//*[@id="btnAceptar"]', 1)
					break;
			}
		} catch (Exception e){

		}
	}

	/**
	 * @author be54955
	 * @param isento
	 * @return
	 */
	public static seguradoIsentoIOF(String isento){
		switch (isento){
			case "S":
				clickXpath('//*[@id="DatosImpuestosBean$exentoIva1"]', 2);
				break;
			case "N":
				clickXpath('//*[@id="DatosImpuestosBean$exentoIva2"]', 2);
				break;
		}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean dadosRisco(List<String> registros){
		System.out.println('[ Dados de Riscos ]')
		boolean b = false
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 15))){
			registros[26] = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 15)
			try {
				//CPF/CNPJ do Locador
				preencheXpath('//*[@id="DadosLocadorBean$cpfLocador_doc"]', registros[4], 0);
				//Nome do Locador
				preencheXpath('//*[@id="DadosLocadorBean$nomeLocador"]', registros[5], 0);
				//CPF/CNPJ do Locatario
				preencheXpath('//*[@id="DadosLocadorBean$cpfLocatario_doc"]', registros[6], 0);
				//Nome do Locatario
				preencheXpath('//*[@id="DadosLocadorBean$nomeLocatario"]', registros[7], 0);
				//CEP / Bairro
				preencheXpath('//*[@id="COD_BR"]', registros[9].subSequence(0,5), 0);
				String sub = registros[9].toString().replaceAll("-","")
				preencheXpath('//*[@id="SUB_COD_BR"]',sub.subSequence(5,8), 0);
				clickXpath('//*[@id="COD_BRAjaxLocFinderImg"]', 2);
				//Logradouro
				limparCamposInput('//*[@id="DadosEndBean$cep_tipoVia"]')
				preencheXpath('//*[@id="DadosEndBean$cep_tipoVia"]', registros[11], 0);
				//Endereço
				limparCamposInput('//*[@id="DadosEndBean$cep_logradouro"]')
				preencheXpath('//*[@id="DadosEndBean$cep_logradouro"]', registros[12], 0);
				//Numero
				preencheXpath('//*[@id="DadosEndBean$numeroEnd"]', registros[13], 0);
				//Avançar
				selecionaItemLista( 'Aceitar','//*[@id="divAppContent"]',"div")
				WebUI.delay(2)
				if(alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0))){
					//CPF/CNPJ do Locador
					preencheXpath('//*[@id="DadosLocadorBean$cpfLocador_doc"]', registros[4], 0);
					//Nome do Locador
					preencheXpath('//*[@id="DadosLocadorBean$nomeLocador"]', registros[5], 0);
					//CPF/CNPJ do Locatario
					preencheXpath('//*[@id="DadosLocadorBean$cpfLocatario_doc"]', registros[6], 0);
					//Bairro
					preencheXpath('//*[@id="DESC_BR"]', registros[10], 0);
					//Complemento
					preencheXpath('//*[@id="DadosEndBean$complemento"]', registros[14], 0);
					//Avançar
					selecionaItemLista( 'Aceitar','//*[@id="divAppContent"]',"div")
				}

				verificaDadosDeRisco(registros)
				if(alertListBlocList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0))){
					b = false
				} else {
					b = true
				}

			} catch (Exception e){
				b = false
			}
		} else {
			registros[25] = " Tela [Dados de Risco] não apresentada"
			registros[21] = 'Não Calculado '
			b = false
		}
		return b
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static void verificaDadosDeRisco(List<String> registros){
		try{
			WebUI.delay(5);
			alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
			System.out.println("ALERTA ["+alerta+"]")
			if(alertListBlocList(alerta)){
				//CEP / Bairro
				limparCamposInput('//*[@id="COD_BR"]')
				preencheXpath('//*[@id="COD_BR"]', registros[10], 0);
				limparCamposInput('//*[@id="SUB_COD_BR"]')
				preencheXpath('//*[@id="SUB_COD_BR"]', registros[11], 0);
				preencheXpath('//*[@id="DESC_BR"]', registros[12], 0);
				//logradouro
				limparCamposInput('//*[@id="DadosRiscoResiDigital$logradouro"]')
				preencheXpath('//*[@id="DadosRiscoResiDigital$logradouro"]', registros[14], 0);
				//Avançar
				selecionaItemLista( 'Avançar','//*[@id="divAppContent"]/table/tbody/tr[1]/td',"div")
			}
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba,String tipoSelecao,String a ){
		try{
			System.err.println('Reprocessa Falhas')
			initDataFiles()
			if(validaDados(rollCheck)){
				fluxoCompletoImobiliariaFuncionalidades(aba, tipoSelecao, a)
				reprocessaFalhas(aba,ramo,tipoSelecao, a)
			}
		} catch (Exception e){}
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailImobiliaria(String tipoAba, String tipoSelecao){
		System.out.println()
		dataPath(tipoAba)
		roolCheck(tipoAba)
		initDataFiles()
		try{
			if(!validaDados(rollCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Imobiliaria\\ConfiguracoesImobiliaria.txt";
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path

				switch(tipoAba){
					case "imobiliariaEmpresa":
						anexos[1] = "imobiliariaEmpresa.xlsx"
						break;
					case "imobiliariaResidencia.xlsx":
						anexos[1] = "imobiliariaResidencia.xlsx"
						break
				}


				System.out.println(anexos[0]+" - "+anexos[1])
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos, tipoAba)
			}
		} catch (Exception e){
		}
	}


	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean coberturas(List<String> registros, String aba){
		System.out.println()
		WebUI.delay(5);
		boolean b
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 10))){
			registros[26] = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 10)
			try{
				//Edificio
				limparCamposInput('//*[@id="PartidasAsegurablesBean$lineasPartidas[0].capitales[0].capital"]')
				preencheXpath('//*[@id="PartidasAsegurablesBean$lineasPartidas[0].capitales[0].capital"]', formataValoresSegurados(registros[15]), 0);
				commandTAB('//*[@id="PartidasAsegurablesBean$lineasPartidas[0].capitales[0].capital"]')
			} catch (Exception e){}

			try{
				//VR.de Edifício
				limparCamposInput('//*[@id="PartidasAsegurablesBean$lineasPartidas[1].capitales[0].capital"]')
				preencheXpath('//*[@id="PartidasAsegurablesBean$lineasPartidas[1].capitales[0].capital"]', formataValoresSegurados(registros[16]), 0);
				commandTAB('//*[@id="PartidasAsegurablesBean$lineasPartidas[1].capitales[0].capital"]')
			} catch (Exception e){}
			//CONTINUAR
			clickXpath('//*[@id="btnContinuar"]', 3);

			
			if(alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5))){
				clickXpath('//*[@id="btnContinuar"]', 3);
			}
			
			
			if(aba.equals('imobiliariaResidencia')){
				selecionaCobertura(registros)
			}
			WebUI.delay(5);

			if(alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5))){
				//CONTINUAR
				clickXpath('//*[@id="btnContinuar"]', 3);
			}

			if(aba.equals('imobiliariaEmpresa')){
				//Calcular
				clickXpath('//*[@id="btnCalcular"]', 3);
				//Avançar
				clickXpath('//*[@id="btnAceptar"]', 3);
			}

			if(alertListBlocList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5))){
				b = false
			}else {
				try{
					registros[22] = recuperaValor('//*[@id="modalidad_0_0_primaRecibo"]', 'value', 0)
				} catch (Exception e){}
				//Emitir
				clickXpath('//*[@id="ParrillaBean$btnEmitir"]', 3);
				b = true
			}

		} else {
			registros[25] = " Tela [Coberturas] não apresentada"
			registros[21] = 'Processado'
			b = false

			try{
				//MENU
				clickXpath('//*[@id="0"]', 5)
			} catch (Exception e){}
		}
		System.out.println(b)
		return b
	}

//	/**
//	 * @author BE54955
//	 * @param cobertura
//	 * @return
//	 */
//	public static boolean coberturasList(String cobertura){
//		System.err.println("\nalertListBloc ["+cobertura+"]")
//		boolean r = false
//		List<String> listOfCoberturas = new ArrayList<String>()
//		listOfCoberturas.add("Danos Elétricos")
//		listOfCoberturas.add("Vendaval")
//		listOfCoberturas.add("Impacto de veiculo")
//		System.out.println("Cobertura -["+cobertura+"]-")
//		for(int i = 0; i < listOfCoberturas.size(); i++){
//			System.err.println("["+i+"] ["+listOfCoberturas[i]+"]")
//			if(listOfCoberturas[i].contains(cobertura.toString())){
//				System.err.println("FOUND COBERT. TO BLOC ["+i+"] ["+listOfCoberturas[i]+"]")
//				r = true
//				break
//			}
//		}
//		System.out.println("\nCobertura ["+r+"]")
//		return  r
//	}

	/**
	 * @author BE54955
	 * @param c
	 * @return
	 */
	public  static String mapCobertura(int c){
		Map<Integer, String> mes = new HashMap<String, String>();
		mes.put(0,"Danos Elétricos");
		mes.put(1,"Vendaval");
		mes.put(2,"Impacto de veiculo");
		return mes.get(c);
	}
	
	/**
	 * @author be54955
	 */
	public static selecionaCobertura(List<String> registros){
 		System.out.println()
		String[] coberturas = new String[3]
		coberturas[0] = registros[17];
		coberturas[1] = registros[18];
		coberturas[2] = registros[19];
		for(int i = 0; i<coberturas.size();i++){
			System.out.println("["+i+"] ["+coberturas[i]+"] ["+coberturas[i].size()+"] ["+ mapCobertura(i)+"]")
			if(coberturas[i].size()>0){
			  cobertura(mapCobertura(i))
			}
		}
		//Calcular
		clickXpath('//*[@id="btnCalcular"]', 2)
		//Avançar
		clickXpath('//*[@id="btnAceptar"]', 2)
		System.out.println()
	}

	/**
	 * @author BE54955
	 * @param registros
	 * @return
	 */
	public static cobertura(String cobertura){
		System.out.println()
		String xpath = '//*[@id="gridTablaCoberturas"]/tbody'
		String tag = 'tr'
		boolean f = true
		selecionaListaDeElementos(xpath,tag)
		int j = 1
		for(int i = 0; i < listOfElements.size(); i++){
			if(!listOfElements[i].getText().equals("")){
				System.err.println("FOUND ["+j+"] ["+listOfElements[i].getText()+"]")
				//if(coberturasList(listOfElements[i].getText())){
				if(listOfElements[i].getText().equals(cobertura)){
					System.out.println()
					//CheckBox
					clickXpath('//*[@id="gridTablaCoberturas"]/tbody/tr['+j+']/td[1]', 1);
					//Editar
					//clickXpath('//*[@id="gridTablaCoberturas"]//tbody//tr['+j+']//td[6]//*[@id="41"]', 1);
					//Valor
					//preencheXpath('//*[@id="gridTablaCoberturas"]/tbody/tr['+j+']/td[3]', registros[17], 0);
					//Calcular
					//clickXpath('//*[@id="btnCalcular"]', 2)
					//Avançar
					//clickXpath('//*[@id="btnAceptar"]', 2)
				}
				j++
			}
		}
	}
	
	/**
	 * @author be54955
	 * @param vlr
	 * @return
	 */
	public static String formataValoresSegurados(String vlr){
		String Nvlr
		try{
			Nvlr = vlr.replace(".","")
		}catch (Exception e){}
		return Nvlr
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean dadosEmissao(List<String> registros){
		System.out.println()
		WebUI.delay(5);
		boolean b
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 10))){
			registros[26] = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 10)
			try{
				//Avançar
				clickXpath('//*[@id="btnAceptar"]', 1);
				b = true
			} catch (Exception e){}
		} else {
			registros[25] = " Tela [D. Emissão] não apresentada"
			registros[21] = 'Processado'
			b = false

			try{
				//MENU
				clickXpath('//*[@id="0"]', 5)
			} catch (Exception e){}
		}
		return b
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean dadosCliente(List<String> registros){
		System.out.println()
		WebUI.delay(5);
		boolean b
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 10))){
			registros[26] = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 10)
			try{
				//Avançar
				clickXpath('//*[@id="btnAceptar"]', 1);
				b = true
				if(alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5))){
					//Avançar
					clickXpath('//*[@id="btnAceptar"]', 1);
				}
			} catch (Exception e){}
		} else {
			registros[25] = " Tela [D. Cliente] não apresentada"
			registros[21] = 'Processado'
			b = false

			try{
				//MENU
				clickXpath('//*[@id="0"]', 5)
			} catch (Exception e){}
		}
		return b
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean dadosComuns(List<String> registros){
		System.out.println()
		WebUI.delay(5);
		boolean b
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 10).toString().trim())){
			registros[26] = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 10).toString().trim()
			try{
				//Avançar
				clickXpath('//*[@id="btnAceptar"]', 1);
				b = true
				if(alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5))){
					//Avançar
					clickXpath('//*[@id="btnAceptar"]', 1);
					b = true
				} else if (alertListBlocList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5))) {
					b = false
					registros[25] = alertListBlocList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5))
					registros[21] = 'Processado'
				}
			} catch (Exception e){}
		} else {
			registros[25] = " Tela [D. Comuns] não apresentada"
			registros[21] = 'Processado'
			b = false

			try{
				//MENU
				clickXpath('//*[@id="0"]', 5)
			} catch (Exception e){}
		}
		return b
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean clausulas(List<String> registros){
		System.out.println()
		WebUI.delay(5);
		boolean b
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 10).toString().trim())){
			registros[26] = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 10).toString().trim()
			try{
				//Avançar
				clickXpath('//*[@id="btnAceptar"]', 1);
				b = true
				if(alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5))){
					//Avançar
					clickXpath('//*[@id="btnAceptar"]', 1);
					b = true
				}
			} catch (Exception e){}
		} else {
			registros[25] = " Tela [Clausulas] não apresentada"
			registros[21] = 'Processado'
			b = false

			try{
				//MENU
				clickXpath('//*[@id="0"]', 5)
			} catch (Exception e){}
		}
		return b
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean confirmacaoEmissao(List<String> registros){
		System.out.println()
		WebUI.delay(5);
		boolean b
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 22).toString().trim())){
			registros[26] = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 22).toString().trim()
			try{
				//Avançar
				clickXpath('//*[@id="btnAceptar"]', 1);
				b = true
				if(alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5))){
					//Avançar
					clickXpath('//*[@id="btnAceptar"]', 1);
					b = true
				}
			} catch (Exception e){}
		} else {
			registros[25] = " Tela [Confirmação de Emissão] não apresentada"
			registros[21] = 'Processado'
			b = false

			try{
				//MENU
				clickXpath('//*[@id="0"]', 5)
			} catch (Exception e){}
		}
		return b
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean emissaoRealizada(List<String> registros){
		System.out.println()
		WebUI.delay(5);
		boolean b
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 17).toString().trim())){
			registros[26] = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 17).toString().trim()
			try{
				//Número de Proposta
				registros[23] = recuperaValor('//*[@id="mensajeEmisionContent"]/table/tbody/tr[1]/td[2]', 'innerText', 5)
				//Número de Apólice
				registros[24] = recuperaValor('//*[@id="mensajeEmisionContent"]/table/tbody/tr[2]/td[2]', 'innerText', 5)
				//Avançar
				clickXpath('//*[@id="btnValida"]', 1);
				b = true
				if(alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5))){
					//Avançar
					clickXpath('//*[@id="btnValida"]', 1);
					b = true
				}
				registros[21] = "Processado"
			} catch (Exception e){}
		} else {
			registros[25] = " Tela [EMISSÃO REALIZADA] não apresentada"
			registros[21] = 'Processado'
			b = false

			try{
				//MENU
				clickXpath('//*[@id="0"]', 5)
			} catch (Exception e){}
		}
		return b
	}




}

package migracaoDeDados

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

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



public class MigracaoDeDadosFuncionalidades extends rpaCommands{

	private static TestData data
	private static TestData datatplog
	private static String testDataPath = 'Data Files/MigracaoDeDados/MigracaoDeDadosDataData'
	private static String tplog = 'Data Files/MigracaoDeDados/TP_LOGRADOURO_DATA'
	private static String path
	private static String alerta
	private static String nomeFantasia
	private static String atividadePrincipal
	private static String natureza
	private static String ocupacao
	private static String faturamento
	private static String qdtFuncionários
	private static String patrimônio
	private static String sexo
	private static String profissao
	private static String nomeMae
	private static String pis
	private static String rendaMensal
	private static String estadoCivil
	private static String inss


	/**
	 * Dados fixos 
	 * @return
	 */
	public static dadosSocioeconomicosSociodemograficos(){
		nomeFantasia = 'AAAAA AAAAA'
		atividadePrincipal = 'AAAAA AAAAA'
		natureza = 'OUTRAS ATIVIDADES DE SERVIÇOS'
		ocupacao = 'I55 - ALOJAMENTO'
		faturamento = 'Até R$120.000,00'
		qdtFuncionários = 'Até 9'
		patrimônio = 'Até R$300.000,00'

		sexo = 'Masculino'
		profissao= 'Arquiteto'
		nomeMae= 'AAAAA AAAAA'
		pis= '95935959359'
		rendaMensal= 'Até R$1.500,00'
		estadoCivil= 'Casado'
		inss = '95935959359'
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(String ramo){
		data = findTestData(retornaURLDATA(ramo))
		path = data.sourceUrl
	}

	/**
	 * @author be54955
	 * @param url
	 * @return
	 */
	public static String retornaURLDATA(String url){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('1240', 'Data Files/MigracaoDeDados/MigracaoDeDadosDataAutoAnualData');
		urls.put('1241', 'Data Files/MigracaoDeDados/MigracaoDeDadosDataData');
		urls.put('1249', 'Data Files/MigracaoDeDados/MigracaoDeDadosDataAutoCaixaData');
		urls.put('2026', 'Data Files/MigracaoDeDados/MigracaoDeDadosResidenciaData');
		urls.put('2027', 'Data Files/MigracaoDeDados/MigracaoDeDadosCondominioData');
		urls.put('2028', 'Data Files/MigracaoDeDados/MigracaoDeDadosEmpresaData');
		return urls.get(url.toString());
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initTPLogDataFiles(){
		datatplog = findTestData(tplog)
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static boolean validaDados(int cellStatus){
		System.out.println("RowNumber "+data.getRowNumbers())
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
	 * @author be54955
	 * @param ramo
	 * @return
	 */
	public static int rollCheck(String ramo){
		Map<String, String> line = new HashMap<String, String>();
		line.put("1240",131);
		line.put("1241",69);
		line.put("1249",138);
		line.put("2026",73);
		line.put("2027",76);
		line.put("2028",133);
		return line.get(ramo);
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
	 * @author ALZ_ServiceQA
	 * @param aba
	 * @return
	 */
	public static acessoAmbiente(String aba, String a){
		iniciaAplicacaoMatrizCommands(retornaAnbiente(a));
		String abaAmbiente = aba+a
		System.out.println('['+abaAmbiente+']')
		switch (a){
			case "INT":
				realizaLoginIntraNetProducaoTransacionalCommands(abaAmbiente,'Produção e recibos');
				break;
			case "PRD":
				realizaLoginIntraNetProducaoCommands(abaAmbiente)
				break;
		}
	}

	public static String retornaAnbiente(String aba){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('PRD','IntranetProdução');
		urls.put('INT','IntranetTransacional');
		return urls.get(aba.toString());
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoMigracaoDeDadosFuncionalidades(String aba, String ramo, String tipoSelecao, String a){
		fluxoCompletoMigracaoDeDadosFuncionalidades(aba,ramo,tipoSelecao, a)
		reprocessaFalhas(aba,ramo,tipoSelecao,a)
		enviarEmailMigracaoDeDados(aba,ramo,tipoSelecao)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba, String ramo,String tipoSelecao,String a ){
		System.err.println('Reprocessa Falhas')
		initDataFiles(ramo)
		if(validaDados(rollCheck(ramo))){
			fluxoCompletoMigracaoDeDadosFuncionalidades(aba, ramo,tipoSelecao, a)
			reprocessaFalhas(aba,ramo,tipoSelecao, a)
		}
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
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailMigracaoDeDados(String tipoAba, String ramo, String tipoSelecao){
		System.out.println()
		initDataFiles(ramo)
		try{
			if(!validaDados(rollCheck(ramo))){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\MigracaoDeDados\\ConfiguracoesMigracao.txt";
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = pathXLSX(tipoAba)+".xlsx"
				System.out.println(anexos[0]+" - "+anexos[1])
				sendEmail.CreateEmail.createEmailSendMultipleFilesMigracaoDeDados(sender, emaildestination, anexos, tipoAba, tipoSelecao )
			}
		} catch (Exception e){
		}
	}

	/**
	 * @author be54955
	 * @param p
	 * @return
	 */
	public static String pathXLSX(String p){
		Map<String, String> path = new HashMap<String, String>()
		path.put('pozoAutoAnual', 'Pozo_1240');
		path.put('pozoAuto', 'Pozo_1241');
		path.put('pozoCondominio', 'Pozo_2027');
		path.put('pozoEmpresa', 'Pozo_2028');
		path.put('pozoResidencia', 'Pozo_2026');
		path.put('pozoAutoCaixa', 'Pozo_1249');
		return path.get(p.toString());
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoMigracaoDeDadosFuncionalidades(String aba, String ramo, String tipoSelecao, String a){

		try{
			initDataFiles(ramo)
			System.out.println()
			if(validaDados(rollCheck(ramo))){
				initTPLogDataFiles()
				acessoAmbiente(aba,a)
				WebUI.delay(20);
				menuEmissao()
				WebUI.waitForPageLoad(15)
				alteraJanejaGenerica(1, 15)
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					alerta=""
					if (data.getValue(rollCheck(ramo), i).equals('Processar')) {
						System.out.println("["+i+"] ["+tipoSelecao+"]")
						List<String> registros = new ArrayList<>();
						lista( registros , i)

						switch (ramo){
							case "1240":
								registros[134] = registros[133]
								registros[133] = ramo.toString()
								break;
							case "1241":
								registros[72] = registros[71]
								registros[71] = ramo.toString()
								break;
							case "1249":
								registros[141] = registros[140]
								registros[140] = ramo.toString()
								break;
							case "2026":
								registros[71] = ramo.toString()
								break;
							case "2027":
								registros[71] = ramo.toString()
								break;
							case "2028":
								registros[136] = registros[135]
								registros[135] = ramo.toString()
								break;
						}


						if(fluxoEmissao(registros, aba,tipoSelecao, ramo)) {
							try{
								System.err.println("["+alerta+"] ["+registros[72]+"]")
								switch (ramo){
									case "1240":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[73], 130, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[72],131, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[74],132, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[71],134, aba);
										break;

									case "1241":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[73], 68, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[72],69, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[74],70, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[71],72, aba);
										break;

									case "1249":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[73], 137, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[72],138, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[74],139, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[71],141, aba);
										break;

									case "2026":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[73], 72, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[72],73, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[74],74, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[71],76, aba);
										break;

									case "2027":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[73], 75, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[72],75, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[74],76, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[71],77, aba);
										break;

									case "2028":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[73], 132, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[72],133, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[74],134, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[71],136, aba);
										break;
								}
							} catch (Exception e){}
						} else {
							try{
								System.err.println("["+alerta+"] ["+registros[72]+"] ["+registros[73]+"]")
								switch (ramo){
									case "1240":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[73], 130, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[72], 133, aba);
										break;

									case "1241":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[73], 68, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[72], 71, aba);
										break;

									case "1249":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[73], 137, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[72], 140, aba);
										break;

									case "2026":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[73], 72, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[72], 75, aba);
										break;

									case "2027":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[73], 75, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[72], 78, aba);
										break;

									case "2028":
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[73], 132, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[72], 135, aba);
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
	 * @param aba
	 * @return
	 */
	public static boolean fluxoEmissao(List<String> registros, String aba,String tipoSelecao, String ramo){
		boolean b
		System.out.println("Emissão Ramo ["+registros[71].toString()+"] ["+tipoSelecao+"]" )
		WebUI.waitForPageLoad(15)
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0))){

			/**
			 * TELA - MENU PRODUÇÃO
			 */
			System.out.println('[Menu Produção]')
			//Tipo de Seleção : Emissão / Outras Operações
			selecionaTipoSelecao(tipoSelecao,'//*[@id="datosSeleccionContent"]/table[1]/tbody/tr',"td")
			WebUI.delay(5);
			switch (tipoSelecao){
				case "Emissão":
					switch (ramo){
						case "1240":
						emissao(registros, 133)
						break;
						case "1241":
						emissao(registros, 71)
						break;
						case "1249":
						emissao(registros, 140)
						break;
						case "2026":
						emissao(registros, 71)
						break;
						case "2027":
						emissao(registros, 71)
						break;
						case "2028":
						emissao(registros, 135)
						break;
					}
					break;
				case "Outras Operações":
					switch (ramo){
						case "1240":
						outrasOperacoes(registros,133)
						break;
						case "1241":
						outrasOperacoes(registros,71)
						break;
						case "1249":
						outrasOperacoes(registros,140)
						break;
						case "2026":
						outrasOperacoes(registros,71)
						break;
						case "2027":
						outrasOperacoes(registros,71)
						break;
						case "2028":
						outrasOperacoes(registros,135)
						break;
					}
					break;
				case "Endosso":
					switch (ramo){
						case "1240":
						outrasOperacoesEndosso(registros,132)
						break;
						case "1241":
						outrasOperacoesEndosso(registros,70)
						break;
						case "1249":
						outrasOperacoesEndosso(registros,139)
						break;
						case "2026":
						outrasOperacoesEndosso(registros,74)
						break;
						case "2027":
						outrasOperacoesEndosso(registros,132)
						break;
						case "2028":
						outrasOperacoesEndosso(registros,134)
						break;
					}
					break;
				case "EndossoCobertura":
					switch (ramo){
						case "1240":
						outrasOperacoesEndosso(registros,132)
						break;
						case "1241":
						outrasOperacoesEndosso(registros,70)
						break;
						case "1249":
						outrasOperacoesEndosso(registros,139)
						break;
						case "2026":
						outrasOperacoesEndosso(registros,74)
						break;
						case "2027":
						outrasOperacoesEndosso(registros,132)
						break;
						case "2028":
						outrasOperacoesEndosso(registros,134)
						break;
					}
					break;
			}
			WebUI.delay(5);
			alerta = pegaMSGAlerta()
			System.err.println(alerta)
			if(alertListBlocList(alerta)){
				b = false
				registros[72] = alerta
				registros[73] = 'Processado'
				fechaAlerta()
				//HOME
				clickXpath('//*[@id="imgOptMenuBackHome"]', 2);
			} else{
				//Corretor
				commandTAB('//*[@id="mediador"]')
				if(selecionarCorretor()){
					WebUI.delay(5);
					alerta = pegaMSGAlerta()
					System.err.println(alerta)
					if(alertListBlocList(alerta)){
						b = false
						registros[72] = alerta
						registros[73] = 'Processado'
						fechaAlerta()
						//HOME
						clickXpath('//*[@id="imgOptMenuBackHome"]', 2);
					} else{
						//Operativa
						if(tipoSelecao.equals('Endosso')||tipoSelecao.equals('EndossoCobertura')){
							selecionarOperativa('Endosso')
						}else{
							selecionarOperativa('Emitir apólice')
						}

						//AVANÇAR
						selecionaItemLista('Avançar', '//*[@id="seleccionOperativaSectionBar"]/table', 'div')
						WebUI.delay(5);

						try{
							fechaAlerta()
						} catch (Exception e){}

						/**
						 * Ação válida apenas para endosso
						 */
						if(tipoSelecao.equals('Endosso')||tipoSelecao.equals('EndossoCobertura')){
							//Motivo
							preencheXpath('//*[@id="motivo"]', '.', 0);
							//AVANÇAR
							selecionaItemLista('Avançar', '//*[@id="modificacionSectionBar"]/table/tbody/tr/td/table/tbody/tr/td', 'div')
							//WebUI.delay(5);
							try{
								WebUI.waitForPageLoad(10)
							} catch (Exception e){}
						}

						try{
							fechaAlerta()
						} catch (Exception e){}

						//D. BÁSICOS
						b = dadosBasicos(registros, ramo, tipoSelecao)
						alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
						System.out.println("ALERTA ["+alerta+"] ["+b+"]")
						if(alertListBlocList(alerta)){
							registros[72] = alerta
							registros[73] = 'Processado'
							b = false
							//HOME
							clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
							WebUI.delay(10);
						} else {
							if(b){
								if(coberturas(registros, ramo, tipoSelecao)){
									b = true
									if(tabelaDePrecos(registros, ramo, tipoSelecao)){
										b = true
										if(dadosEmissao(registros, ramo, tipoSelecao)){
											b = true
											if(dadosDoCliente(registros, ramo, tipoSelecao)){
												b = true
												if(dadosComuns(registros, ramo, tipoSelecao)){
													b = true
												}else{
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

								WebUI.delay(2);
								alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
								if(alertListBlocList(alerta)){
									b = false
									registros[72] = alerta
									registros[73] = 'Processado'
									try{
										//Fechar
										clickXpath('//*[@id="lnkTopLink_Close"]', 5);
										fechaAlerta()
										alteraJanejaGenerica(0, 1)
										menuEmissao()
										alteraJanejaGenerica(1, 5)
									} catch (Exception e){}
								} else {
									// System.out.println('['+b+']')
									if(b){
										/**
										 * TELA - CLÁUSULAS
										 */
										if(clausulas(registros, ramo, tipoSelecao)){
											if(confirmaEmissao(registros, ramo, tipoSelecao)){
												if(emissaoRealisada(registros, ramo, tipoSelecao)){
													b = true
												} else {
													b = false
												}
											} else {
												b = false
											}
										} else {
											b = false
										}
										registros[73] = 'Processado'
									} else {
										//HOME
										clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
									}
								}
							} else {
								registros[72] = " Tela [D. BÁSICOS] não apresentada"
								registros[73] = 'Processado'
								//HOME
								clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
							}
						}

					}//MSG ALERTA

				} else {
					//SELEÇÃO
					b = false
					registros[72] = "Corretor inexistente"
					registros[73] = 'Processado'
					//HOME
					clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
				}
			}
		} else {
			registros[72] = " Tela [Menu Produção] não apresentada"
			registros[73] = 'Processado'
			WebUI.closeBrowser();
			acessoAmbiente(aba)
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
	public static boolean dadosBasicos(List<String> registros, String ramo, String tipoSelecao){
		System.out.println('[ Dados Basicos ]')
		boolean b = false
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 10))){
			if(tipoSelecao.equals("Endosso")||tipoSelecao.equals("EndossoCobertura")){
				try{
					//Início de Alter.
					limparCamposInput('//*[@id="DatosSuplementoBean$fechaEfectoSupl"]')
					preencheXpath('//*[@id="DatosSuplementoBean$fechaEfectoSupl"]', formataData(registros[1]), 0);
					//Avançar
					clicarEmElementoDaTela('//*[@id="btnAceptar"]', 1)
					b = true
				} catch (Exception e){
					b = false
				}
			} else {
				//Tipo de Período
				selecionaItemLista(registros[3], '//*[@id="DatosGeneralesBean$tipoPeriodo"]', 'option')
				WebUI.delay(5);
				//Início de Vigência
				limparCamposInput('//*[@id="DatosGeneralesBean$fechaEfecto"]')
				preencheXpath('//*[@id="DatosGeneralesBean$fechaEfecto"]', formataData(registros[1]), 0);
				//Término Vigência
				clicarEmElementoDaTela('//*[@id="DatosGeneralesBean$fechaTerm"]', 0)
				try{
					limparCamposInput('//*[@id="DatosGeneralesBean$fechaTerm"]')
					preencheXpath('//*[@id="DatosGeneralesBean$fechaTerm"]', formataData(registros[2]), 0);
				} catch (Exception e){}
				//Segurado Isento do IOF
				seguradoIsentoIOF(registros[4])
				//CPF/CNPJ Tomador
				//preencheXpath('//*[@id="IntervinientesBean$nifTomador_doc"]', StringFormatter.formataDocumentoCPFCNPJ(registros[5]), 0);
				//CPF/CNPJ Segurado
				if(verificaAlertaDeDocumento(registros, ramo)){
					limparCamposInput('//*[@id="IntervinientesBean$nifTomador_doc"]')
					preencheXpath('//*[@id="IntervinientesBean$nifTomador_doc"]', registros[5], 0);
				} else {
					limparCamposInput('//*[@id="IntervinientesBean$nifTomador_doc"]')
					preencheXpath('//*[@id="IntervinientesBean$nifTomador_doc"]', StringFormatter.formataDocumentoCPFCNPJ(registros[5]), 0);
				}

				//Nome do Tomador
				preencheXpath('//*[@id="IntervinientesBean$nomFigTomadorValue"]', registros[6], 0);

				try{
					//CPF/CNPJ Segurado
					if(verificaAlertaDeDocumento(registros, ramo)){
						limparCamposInput('//*[@id="IntervinientesBean$nifAsegurado_doc"]')
						preencheXpath('//*[@id="IntervinientesBean$nifAsegurado_doc"]', registros[5], 0);
					} else {
						limparCamposInput('//*[@id="IntervinientesBean$nifAsegurado_doc"]')
						preencheXpath('//*[@id="IntervinientesBean$nifAsegurado_doc"]', StringFormatter.formataDocumentoCPFCNPJ(registros[5]), 0);
					}

					//Nome do Segurado
					preencheXpath('//*[@id="IntervinientesBean$nomFigAseguradoValue"]', registros[5], 0);
					//Categoria do Risco
					selecionaItemLista(registros[7], '//*[@id="CategoriaRiesgoBean$catRiesgo"]', 'option')
				} catch (Exception e){}


				try{
					//Nome do Segurado
					preencheXpath('//*[@id="IntervinientesBean$nomFigAseguradoValue"]', registros[5], 0);
				} catch (Exception e){}

				try{
					//Categoria do Risco
					selecionaItemLista(registros[7], '//*[@id="CategoriaRiesgoBean$catRiesgo"]', 'option')
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
			}
		} else {
			registros[72] = " Tela [D. BÁSICOS] não apresentada"
			registros[73] = 'Processado'
			b = false
			//HOME
			try{
				clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
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
	 * 
	 * @param registros
	 * @param ramo
	 * @return
	 */
	public static boolean verificaAlertaDeDocumento(List<String> registros, String ramo){
		boolean d = false
		try{
			String size = registros[indexAlerta(ramo)].toString().trim()
			//System.out.println('ALERT TO FOUND INDEX ['+registros[indexAlerta(ramo)].toString().trim()+'] ['+size.length()+']')
			if(size.length()>0){
				if(registros[indexAlerta(ramo)].toString().trim().contains('CPF ou CNPJ inválido')){
					//System.out.println('ALERT FOUND INDEX ['+registros[indexAlerta(ramo)].toString().trim()+']')
					d = true
				}
			}
		} catch (Exception e){}

		System.out.println(d)
		return d
	}

	/**
	 * @author be54955
	 * @param ramo
	 * @return
	 */
	public static int indexAlerta(String ramo){
		int index
		try{
			switch (ramo){
				case "1240":
					index =  134
					break;
				case "1241":
					index = 72
					break;
				case "1249":
					index = 141
					break;
				case "2026":
					index =  75
					break;
				case "2027":
					index =  78
					break;
				case "2028":
					index =  136
					break;
			}
		} catch (Exception e){}
		return index
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean coberturas(List<String> registros, String ramo, String tipoSelecao){
		System.out.println('Comerturas ['+ramo+"]")
		boolean b
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 10))){
			if(tipoSelecao.equals("Endosso")){
				//Calcular
				clickXpath('//*[@id="btnCalcular"]', 1);
				b = true
			} else if(tipoSelecao.equals("EndossoCobertura")){
				//Recibo Primeiro Manual
				//clickXpath(premioManual(ramo),3)
				//Seleciona tabela de coberturas
				String xpath ='//*[@id="blCoberturasCellContent"]'
				String tag="label"
				selecionaListaDeElementos(xpath,tag)
				switch (ramo){
					case "1240":
						ramo1240(registros,ramo)
						break;
					case "1241":
						ramo1241(registros,ramo)
						break;
					case "1249":
						ramo1249Endosso(registros,ramo)
						break;
					case "2026":
						ramo2026(registros,ramo)
						break;
					case "2027":
						ramo2027(registros,ramo)
						break;
					case "2028":
						ramo2028(registros,ramo)
						break;
				}
				System.out.println()
				//Calcular
				clickXpath('//*[@id="btnCalcular"]', 1);
			} else {
				//Recibo Primeiro Manual
				clickXpath(premioManual(ramo),3)
				//Seleciona tabela de coberturas
				String xpath ='//*[@id="blCoberturasCellContent"]'
				String tag="label"
				selecionaListaDeElementos(xpath,tag)
				switch (ramo){
					case "1240":
						ramo1240(registros,ramo)
						break;

					case "1241":
						ramo1241(registros,ramo)
						break;

					case "1249":
						ramo1249(registros,ramo)
						break;

					case "2026":
						ramo2026(registros,ramo)
						break;

					case "2027":
						ramo2027(registros,ramo)
						break;

					case "2028":
						ramo2028(registros,ramo)
						break;
				}
				System.out.println()
				//Calcular
				clickXpath('//*[@id="btnCalcular"]', 1);
			}
			b =alerta(registros, 3, ramo)
			if(b.equals(true)){
				//Avançar
				try{
					clickXpath('//*[@id="btnAceptar"]', 1);
				} catch (Exception e){}
			}
		} else {
			registros[72] = " Tela [Coberturas] não apresentada"
			registros[73] = 'Processado'
			b = false
			//HOME
			try{
				clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
			} catch (Exception e){}
		}
		return b
	}

	/**
	 * @author be54955
	 * 
	 * @param registros
	 * @param ramo
	 * @return
	 */
	public static ramo1240(List<String> registros, String ramo){
		selecionaCobertura(registros, coberturaIndex("DANOS PARCIAIS AO VEICULO SEGURADO (CASCO)"), 8, 9, ramo)
		selecionaCobertura(registros, coberturaIndex("PERDA TOTAL POR DANOS AO VEICULO SEGURADO (CASCO)"), 10, 11, ramo)
		selecionaCobertura(registros, coberturaIndex("ROUBO, FURTO, INCENDIO PARCIAL DO VEICULO SEGURADO"), 12, 13, ramo)
		selecionaCobertura(registros, coberturaIndex("ROUBO, FURTO, INCENDIO, TOTAL DO VEICULO SEGURADO"), 14, 15, ramo)
		selecionaCobertura(registros, coberturaIndex("DANOS MATERIAIS CAUSADOS A TERCEIROS"), 16, 17, ramo)
		selecionaCobertura(registros, coberturaIndex("DANOS PESSOAIS CAUSADOS A TERCEIROS"), 18, 19, ramo)
		selecionaCobertura(registros, coberturaIndex("ACIDENTES PESSOAIS DE PASSAGEIROS MORTE"), 20, 21, ramo)
		selecionaCobertura(registros, coberturaIndex("ACIDENTES PESSOAIS PASSAGEIROS INVALIDEZ PERMANENTE"), 22, 23, ramo)
		selecionaCobertura(registros, coberturaIndex("ACESSORIOS DO VEICULO SEGURADO"), 24, 25, ramo)
		selecionaCobertura(registros, coberturaIndex("EQUIPAMENTOS DO VEICULO SEGURADO"), 26, 27, ramo)
		selecionaCobertura(registros, coberturaIndex("CARROCERIA DO VEICULO SEGURADO"), 28, 29, ramo)
		selecionaCobertura(registros, coberturaIndex("DIARIAS POR INDISPONIBILIDADE 15 DIAS"), 30, 31, ramo)
		selecionaCobertura(registros, coberturaIndex("DIARIAS POR INDISPONIBILIDADE 30 DIAS"), 32, 33, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL DANOS MORAIS"), 34, 35, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL DE BLINDAGEM"), 36, 37, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL DE VIDROS"), 38, 39, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL VALOR DE NOVO - 6 MESES"), 40, 41, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA 15 DIAS"), 42, 43, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA 30 DIAS"), 44, 45, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA 7 DIAS"), 46, 47, ramo)
		selecionaCobertura(registros, coberturaIndex("APP COM DMH"), 48, 49, ramo)
		selecionaCobertura(registros, coberturaIndex("G. ADIC LANTERNAS FAROIS E LENTES DE RETROVISORES"), 50, 51, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL DESPESAS EXTRAORDINARIAS"), 52, 53, ramo)
		selecionaCobertura(registros, coberturaIndex("ASSISTENCIA 24H PLANO 1 (AUTO)"), 54, 55, ramo)
		selecionaCobertura(registros, coberturaIndex("ASSISTENCIA 24H PLANO 2 (AUTO)"), 56, 57, ramo)
		selecionaCobertura(registros, coberturaIndex("ASSISTENCIA 24H PLANO 3 (AUTO)"), 58, 59, ramo)
		selecionaCobertura(registros, coberturaIndex("ASSISTENCIA 24H PLANO 4 (AUTO)"), 60, 61, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA ESPECIAL 7 DIAS"), 62, 63, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA ESPECIAL 15 DIAS"), 64, 65, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA ESPECIAL 30 DIAS"), 66, 67, ramo)
		selecionaCobertura(registros, coberturaIndex("CARRO RESERVA ESPECIAL 2- 7 DIAS"), 68, 69, ramo)
		selecionaCobertura(registros, coberturaIndex("CARRO RESERVA ESPECIAL 2 - 15 DIAS"), 70, 71, ramo)
		selecionaCobertura(registros, coberturaIndex("CARRO RESERVA ESPECIAL 2 - 30 DIAS"), 72, 73, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA ILIMITADO"), 74, 75, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA ESPECIAL ILIMITADO"), 76, 77, ramo)
		selecionaCobertura(registros, coberturaIndex("CARRO RESERVA ESPECIAL 2 ILIMITADO"), 78, 79, ramo)
		selecionaCobertura(registros, coberturaIndex("SALDO FINANCIAMENTO 5%"), 80, 81, ramo)
		selecionaCobertura(registros, coberturaIndex("SALDO FINANCIAMENTO 20%"), 82, 83, ramo)
		selecionaCobertura(registros, coberturaIndex("TETO SOLAR OU PANORAMICO"), 84, 85, ramo)
		selecionaCobertura(registros, coberturaIndex("LANTERNA E FAROL AUXILIARES"), 86, 87, ramo)
		selecionaCobertura(registros, coberturaIndex("REPARO PARA-CHOQUE/MARTELINHO"), 88, 89, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL DE VIDROS PARA-BRISA"), 90, 91, ramo)
		selecionaCobertura(registros, coberturaIndex("ASSISTENCIA 24H PLANO COMPACTO (AUTO)"), 92, 93, ramo)
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param ramo
	 * @return
	 */
	public static ramo1241(List<String> registros, String ramo){
		selecionaCobertura(registros, coberturaIndex("Casco - Basica Compreensiva - Colisão, Incendio e Roubo"), 8, 9, ramo)
		selecionaCobertura(registros, coberturaIndex("RCF - Danos Materiais"), 10, 11, ramo)
		selecionaCobertura(registros, coberturaIndex("RCF - Danos Corporais"), 12, 13, ramo)
		selecionaCobertura(registros, coberturaIndex("APP - Morte"), 14, 15, ramo)
		selecionaCobertura(registros, coberturaIndex("APP - Invalidez Permanente"), 16, 17, ramo)
		selecionaCobertura(registros, coberturaIndex("RCF - Danos Morais"), 18, 19, ramo)
		selecionaCobertura(registros, coberturaIndex("Blindagem"), 20, 21, ramo)
		selecionaCobertura(registros, coberturaIndex("Vidros Plano 1"), 22, 23, ramo)
		selecionaCobertura(registros, coberturaIndex("Carro Reserva 15 Dias"), 26, 27, ramo)
		selecionaCobertura(registros, coberturaIndex("Carro Reserva 30 Dias"), 28, 29, ramo)
		selecionaCobertura(registros, coberturaIndex("Carro Reserva 7 Dias"), 24, 25, ramo)
		selecionaCobertura(registros, coberturaIndex("Assistência 24 Horas"), 30, 31, ramo)
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param ramo
	 * @return
	 */
	public static ramo1249(List<String> registros, String ramo){
		System.out.println()
		selecionaCobertura(registros, coberturaIndex("DANOS PARCIAIS AO VEICULO SEGURADO (CASCO)"), 8, 9, ramo)
		selecionaCobertura(registros, coberturaIndex("PERDA TOTAL POR DANOS AO VEICULO SEGURADO (CASCO)"), 10, 11, ramo)
		selecionaCobertura(registros, coberturaIndex("ROUBO, FURTO, INCENDIO PARCIAL DO VEICULO SEGURADO"), 12, 13, ramo)
		selecionaCobertura(registros, coberturaIndex("ROUBO, FURTO, INCENDIO, TOTAL DO VEICULO SEGURADO"), 14, 15, ramo)
		selecionaCobertura(registros, coberturaIndex("DANOS MATERIAIS CAUSADOS A TERCEIROS"), 16, 17, ramo)
		selecionaCobertura(registros, coberturaIndex("DANOS PESSOAIS CAUSADOS A TERCEIROS"), 18, 19, ramo)
		selecionaCobertura(registros, coberturaIndex("ACIDENTES PESSOAIS DE PASSAGEIROS MORTE"), 20, 21, ramo)
		selecionaCobertura(registros, coberturaIndex("ACIDENTES PESSOAIS PASSAGEIROS INVALIDEZ PERMANENT"), 22, 23, ramo)
		selecionaCobertura(registros, coberturaIndex("ACESSORIOS DO VEICULO SEGURADO"), 24, 25, ramo)
		selecionaCobertura(registros, coberturaIndex("EQUIPAMENTOS DO VEICULO SEGURADO"), 26, 27, ramo)
		selecionaCobertura(registros, coberturaIndex("DIARIAS POR INDISPONIBILIDADE 15 DIAS"), 28, 29, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL DANOS MORAIS"), 30, 31, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL DE BLINDAGEM"), 32, 33, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL DE VIDROS"), 34, 35, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA 15 DIAS"), 36, 37, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA 30 DIAS"), 38, 39, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA 7 DIAS"), 40, 41, ramo)
		selecionaCobertura(registros, coberturaIndex("APP COM DMH"), 42, 43, ramo)
		selecionaCobertura(registros, coberturaIndex("G. ADIC LANTERNAS FAROIS E LENTES DE RETROVISORES"), 44, 45, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL DESPESAS EXTRAORDINARIAS"), 46, 47, ramo)
		selecionaCobertura(registros, coberturaIndex("ASSISTENCIA 24H PLANO 1 (AUTO)"), 48, 49, ramo)
		selecionaCobertura(registros, coberturaIndex("ASSISTENCIA 24H PLANO 2 (AUTO)"), 50, 51, ramo)
		selecionaCobertura(registros, coberturaIndex("ASSISTENCIA 24H PLANO 3 (AUTO)"), 52, 53, ramo)
		selecionaCobertura(registros, coberturaIndex("ASSISTENCIA 24H PLANO 4 (AUTO)"), 54, 55, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA ESPECIAL 7 DIAS"), 56, 57, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA ESPECIAL 15 DIAS"), 58, 59, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA ESPECIAL 30 DIAS"), 60, 61, ramo)
		selecionaCobertura(registros, coberturaIndex("CARRO RESERVA ESPECIAL 2- 7 DIAS"), 62, 63, ramo)
		selecionaCobertura(registros, coberturaIndex("CARRO RESERVA ESPECIAL 2 - 15 DIAS"), 64, 65, ramo)
		selecionaCobertura(registros, coberturaIndex("CARRO RESERVA ESPECIAL 2 - 30 DIAS"), 66, 67, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA ILIMITADO"), 68, 69, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL CARRO RESERVA ESPECIAL ILIMITAD"), 70, 71, ramo)
		selecionaCobertura(registros, coberturaIndex("CARRO RESERVA ESPECIAL 2 ILIMITADO"), 72, 73, ramo)
		selecionaCobertura(registros, coberturaIndex("FURTO DE ESTEPE"), 74, 75, ramo)
		selecionaCobertura(registros, coberturaIndex("TETO SOLAR OU PANORAMICO"), 76, 77, ramo)
		selecionaCobertura(registros, coberturaIndex("LANTERNA E FAROL AUXILIARES"), 78, 79, ramo)
		selecionaCobertura(registros, coberturaIndex("REPARO PARA-CHOQUE/MARTELINHO"), 80, 81, ramo)
		selecionaCobertura(registros, coberturaIndex("GARANTIA ADICIONAL DE VIDROS PARA-BRISA"), 82, 83, ramo)
		selecionaCobertura(registros, coberturaIndex("ASSISTENCIA 24H PLANO COMPACTO (AUTO)"), 84, 85, ramo)
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param ramo
	 * @return
	 */
	public static ramo1249Endosso(List<String> registros, String ramo){
		System.out.println()
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("DANOS PARCIAIS AO VEICULO SEGURADO (CASCO)"), 8, 9, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("PERDA TOTAL POR DANOS AO VEICULO SEGURADO (CASCO)"), 10, 11, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("ROUBO, FURTO, INCENDIO PARCIAL DO VEICULO SEGURADO"), 12, 13, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("ROUBO, FURTO, INCENDIO, TOTAL DO VEICULO SEGURADO"), 14, 15, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("DANOS MATERIAIS CAUSADOS A TERCEIROS"), 16, 17, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("DANOS PESSOAIS CAUSADOS A TERCEIROS"), 18, 19, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("ACIDENTES PESSOAIS DE PASSAGEIROS MORTE"), 20, 21, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("ACIDENTES PESSOAIS PASSAGEIROS INVALIDEZ PERMANENT"), 22, 23, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("ACESSORIOS DO VEICULO SEGURADO"), 24, 25, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("EQUIPAMENTOS DO VEICULO SEGURADO"), 26, 27, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("DIARIAS POR INDISPONIBILIDADE 15 DIAS"), 28, 29, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("GARANTIA ADICIONAL DANOS MORAIS"), 30, 31, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("GARANTIA ADICIONAL DE BLINDAGEM"), 32, 33, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("GARANTIA ADICIONAL DE VIDROS"), 34, 35, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("GARANTIA ADICIONAL CARRO RESERVA 15 DIAS"), 36, 37, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("GARANTIA ADICIONAL CARRO RESERVA 30 DIAS"), 38, 39, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("GARANTIA ADICIONAL CARRO RESERVA 7 DIAS"), 40, 41, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("APP COM DMH"), 42, 43, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("G. ADIC LANTERNAS FAROIS E LENTES DE RETROVISORES"), 44, 45, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("GARANTIA ADICIONAL DESPESAS EXTRAORDINARIAS"), 46, 47, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("ASSISTENCIA 24H PLANO 1 (AUTO)"), 48, 49, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("ASSISTENCIA 24H PLANO 2 (AUTO)"), 50, 51, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("ASSISTENCIA 24H PLANO 3 (AUTO)"), 52, 53, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("ASSISTENCIA 24H PLANO 4 (AUTO)"), 54, 55, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("GARANTIA ADICIONAL CARRO RESERVA ESPECIAL 7 DIAS"), 56, 57, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("GARANTIA ADICIONAL CARRO RESERVA ESPECIAL 15 DIAS"), 58, 59, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("GARANTIA ADICIONAL CARRO RESERVA ESPECIAL 30 DIAS"), 60, 61, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("CARRO RESERVA ESPECIAL 2- 7 DIAS"), 62, 63, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("CARRO RESERVA ESPECIAL 2 - 15 DIAS"), 64, 65, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("CARRO RESERVA ESPECIAL 2 - 30 DIAS"), 66, 67, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("GARANTIA ADICIONAL CARRO RESERVA ILIMITADO"), 68, 69, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("GARANTIA ADICIONAL CARRO RESERVA ESPECIAL ILIMITAD"), 70, 71, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("CARRO RESERVA ESPECIAL 2 ILIMITADO"), 72, 73, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("FURTO DE ESTEPE"), 74, 75, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("TETO SOLAR OU PANORAMICO"), 76, 77, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("LANTERNA E FAROL AUXILIARES"), 78, 79, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("REPARO PARA-CHOQUE/MARTELINHO"), 80, 81, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("GARANTIA ADICIONAL DE VIDROS PARA-BRISA"), 82, 83, ramo)
		selecionaCoberturaEndosso(registros, coberturaIndexEndosso("ASSISTENCIA 24H PLANO COMPACTO (AUTO)"), 84, 85, ramo)
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param ramo
	 * @return
	 */
	public static ramo2026(List<String> registros, String ramo){
		selecionaCobertura(registros, coberturaIndex("Garantia adicional danos morais"), 8, 9, ramo)
		selecionaCobertura(registros, coberturaIndex("Danos eletricos"), 10, 11, ramo)
		selecionaCobertura(registros, coberturaIndex("Responsabilidade civil familiar"), 12, 13, ramo)
		selecionaCobertura(registros, coberturaIndex("Desmoronamento"), 14, 15, ramo)
		selecionaCobertura(registros, coberturaIndex("Roubo ou furto qualificado do conteudo"), 16, 17, ramo)
		selecionaCobertura(registros, coberturaIndex("Quebra de vidros, marmores e granitos"), 18, 19, ramo)
		selecionaCobertura(registros, coberturaIndex("Perda ou pagamento de aluguel"), 20, 21, ramo)
		selecionaCobertura(registros, coberturaIndex("Assistencia 24 horas"), 22, 23, ramo)
		selecionaCobertura(registros, coberturaIndex("Impacto de veiculos terrestres"), 24, 25, ramo)
		selecionaCobertura(registros, coberturaIndex("Vendaval, furacao, ciclone, tornado e granizo"), 26, 27, ramo)
		selecionaCobertura(registros, coberturaIndex("Incendio,raio,explosao,fumaca e queda de aeronave"), 28, 29, ramo)
		selecionaCobertura(registros, coberturaIndex("Escritorio em residencia"), 30, 31, ramo)
		selecionaCobertura(registros, coberturaIndex("Protecao para bikes"), 32, 33, ramo)
		selecionaCobertura(registros, coberturaIndex("Ruptura ou vazamento de tubulações"), 34, 35, ramo)
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param ramo
	 * @return
	 */
	public static ramo2027(List<String> registros, String ramo){
		selecionaCobertura(registros, coberturaIndex("Tumultos, Greves e Lock-outs"), 8, 9, ramo)
		selecionaCobertura(registros, coberturaIndex("Vida Empregados"), 10, 11, ramo)
		selecionaCobertura(registros, coberturaIndex("Responsabilidade Civil do Condomínio"), 12, 13, ramo)
		selecionaCobertura(registros, coberturaIndex("Roubo ou Furto de Conteúdo"), 14, 15, ramo)
		selecionaCobertura(registros, coberturaIndex("Danos Morais"), 16, 17, ramo)
		selecionaCobertura(registros, coberturaIndex("Responsabilidade Civil do Sindico"), 18, 19, ramo)
		selecionaCobertura(registros, coberturaIndex("Incêndio para Conteudo dos Condôminos"), 20, 21, ramo)
		selecionaCobertura(registros, coberturaIndex("Assistência 24 Horas"), 22, 23, ramo)
		selecionaCobertura(registros, coberturaIndex("Desmoronamento"), 24, 25, ramo)
		selecionaCobertura(registros, coberturaIndex("Danos Elétricos"), 26, 27, ramo)
		selecionaCobertura(registros, coberturaIndex("Quebra de Vidros, Mármores e Granitos"), 28, 29, ramo)
		selecionaCobertura(registros, coberturaIndex("RC Portões"), 30, 31, ramo)
		selecionaCobertura(registros, coberturaIndex("Vendaval, Furacão, Ciclone, Tornado e Granizo"), 32, 33, ramo)
		selecionaCobertura(registros, coberturaIndex("Incêndio,Raio,Explosão,Fumaça e Queda de Aeronave"), 34, 35, ramo)
		selecionaCobertura(registros, coberturaIndex("Impacto de veículos terrestres"), 36, 37, ramo)
		selecionaCobertura(registros, coberturaIndex("Perda ou Pagamento de Aluguel dos Condôminos"), 38, 39, ramo)
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param ramo
	 * @return
	 */
	public static ramo2028(List<String> registros, String ramo){
		selecionaCobertura(registros, coberturaIndex("Despesas Fixas 1"), 8, 9, ramo)
		selecionaCobertura(registros, coberturaIndex("R.C. Estab. Com. Ind."), 10, 11, ramo)
		selecionaCobertura(registros, coberturaIndex("Lucros Cessantes"), 12, 13, ramo)
		selecionaCobertura(registros, coberturaIndex("Tumultos, Greves e Lock-outs"), 14, 15, ramo)
		selecionaCobertura(registros, coberturaIndex("Roubo ou Furto de Conteúdo"), 16, 17, ramo)//
		selecionaCobertura(registros, coberturaIndex("Danos Morais"), 18, 19, ramo)
		selecionaCobertura(registros, coberturaIndex("Assistência Funeral"), 20, 21, ramo)
		selecionaCobertura(registros, coberturaIndex("Assistência 24 Horas"), 22, 23, ramo)
		selecionaCobertura(registros, coberturaIndex("Anúncios e Letreiros"), 24, 25, ramo)
		selecionaCobertura(registros, coberturaIndex("Despesas com Demolição e Desentulho"), 26, 27, ramo)
		selecionaCobertura(registros, coberturaIndex("Desmoronamento"), 28, 29, ramo)
		selecionaCobertura(registros, coberturaIndex("Despesas Fixas"), 30, 31, ramo)
		selecionaCobertura(registros, coberturaIndex("Responsabilidade Civil do Empregador"), 32, 33, ramo)
		selecionaCobertura(registros, coberturaIndex("Responsabilidade Civil Estabelecimento de Ensino"), 34, 35, ramo)
		selecionaCobertura(registros, coberturaIndex("Roubo ou Furto de Valores em Trânsito"), 36, 37, ramo)
		selecionaCobertura(registros, coberturaIndex("Deterioração Mercadorias em Ambiente Frigorificado"), 38, 39, ramo)
		selecionaCobertura(registros, coberturaIndex("Equipamento Eletrônico"), 40, 41, ramo)
		selecionaCobertura(registros, coberturaIndex("Recomposição de Documentos"), 42, 43, ramo)
		selecionaCobertura(registros, coberturaIndex("Equipamentos Estacionarios"), 44, 45, ramo)
		selecionaCobertura(registros, coberturaIndex("Danos Elétricos"), 46, 47, ramo)
		selecionaCobertura(registros, coberturaIndex("Derrame de Sprinklers (Chuveiros Automáticos)"), 48, 49, ramo)
		selecionaCobertura(registros, coberturaIndex("Despesas com Instalação em novo Local"), 50, 51, ramo)
		selecionaCobertura(registros, coberturaIndex("Equipamentos Móveis"), 52, 53, ramo)
		selecionaCobertura(registros, coberturaIndex("Perda de Aluguel"), 54, 55, ramo)
		selecionaCobertura(registros, coberturaIndex("Quebra de Vidros, Mármores e Granitos"), 56, 57, ramo)
		selecionaCobertura(registros, coberturaIndex("Impacto de veículos terrestres"), 58, 59, ramo)
		selecionaCobertura(registros, coberturaIndex("RC Estabelecimentos Comerciais e Industriais"), 60, 61, ramo)
		selecionaCobertura(registros, coberturaIndex("Vendaval, Furacão, Ciclone, Tornado e Granizo"), 62, 63, ramo)
		selecionaCobertura(registros, coberturaIndex("Incêndio,Raio,Explosão,Fumaça e Queda de Aeronave"), 64, 65, ramo)
		selecionaCobertura(registros, coberturaIndex("Derrame ou Vazameto de Tubulação Hidraulica"), 66, 67, ramo)
		selecionaCobertura(registros, coberturaIndex("RC Estabelecimento de Hospedagem (Hotéis)"), 68, 69, ramo)
		selecionaCobertura(registros, coberturaIndex("RC Estabelecimento Bares e Restaurantes"), 70, 71, ramo)
		selecionaCobertura(registros, coberturaIndex("Bens do Segurado em Locais de Terceiros"), 72, 73, ramo)
		selecionaCobertura(registros, coberturaIndex("Responsabilidade Civil Profissional Farmacêutico"), 74, 75, ramo)
		selecionaCobertura(registros, coberturaIndex("Equipamentos Portáteis"), 76, 77, ramo)
		selecionaCobertura(registros, coberturaIndex("Pequenas Obras"), 78, 79, ramo)
		selecionaCobertura(registros, coberturaIndex("RC Pet Shop"), 80, 81, ramo)
		selecionaCobertura(registros, coberturaIndex("Despesas Extraordinárias com Salários Temporários"), 82, 83, ramo)
		selecionaCobertura(registros, coberturaIndex("Danos de bens de clientes em guarda volume"), 84, 85, ramo)
		selecionaCobertura(registros, coberturaIndex("Responsabilidade Civil Profissional de Estética"), 86, 87, ramo)
		selecionaCobertura(registros, coberturaIndex("Roubo ou Furto de Bens e Valores de Clientes"), 88, 89, ramo)
		selecionaCobertura(registros, coberturaIndex("Responsabilidade Civil Padarias e Confeitarias"), 90, 91, ramo)
		selecionaCobertura(registros, coberturaIndex("Responsabilidade Civil Bullying"), 92, 93, ramo)
		selecionaCobertura(registros, coberturaIndex("Responsabilidade Civil Academias"), 94, 95, ramo)
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean tabelaDePrecos(List<String> registros, String ramo, String tipoSelecao){
		System.out.println('Tabela de preços ['+ramo+"]")
		boolean b
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 16))){
			if(tipoSelecao.equals("Endosso")){
				//Emitir
				selecionaItemLista('Emitir', '//*[@id="blParrillaSectionBar"]/table/tbody/tr/td/table/tbody', 'div')
				fechaAlerta()
				b = true
			} else {
				switch (ramo){
					case "1240":
					//Tipo de pagamento
						formaPagamentoTabelaDePrecos(registros[94])
					//Forma de Pagamento
						selecionaItemLista(registros[96],'//*[@id="ParrillaBean$primRecibo"]',"option")

					//Dia do Pagamento
						preencheXpath('//*[@id="ParrillaBean$diaPago"]', ajustaDiaPagamento(registros[1], registros[97]) , 0);
					//Tipo Comissionamento
						selecionaItemLista(registros[98],'//*[@id="ComisionamientoBean$tipoComisionamiento"]',"option")
					//Comissão
						limpaCampo('//*[@id="ComisionamientoBean$porcComision"]', 1)
						preencheXpath('//*[@id="ComisionamientoBean$porcComision"]', verificaComissaoValorZero(registros[99]), 0);
						commandTAB('//*[@id="ComisionamientoBean$porcComision"]')
						break;

					case "1241":
					//Tipo de pagamento
						formaPagamentoTabelaDePrecos(registros[32])
					//Forma de Pagamento
						selecionaItemLista(registros[34],'//*[@id="ParrillaBean$primRecibo"]',"option")
					//Dia do Pagamento
						preencheXpath('//*[@id="ParrillaBean$diaPago"]', ajustaDiaPagamento(registros[1], registros[35]) , 0);
					//Tipo Comissionamento
						selecionaItemLista(registros[36],'//*[@id="ComisionamientoBean$tipoComisionamiento"]',"option")
					//Comissão
						limpaCampo('//*[@id="ComisionamientoBean$porcComision"]', 1)
						preencheXpath('//*[@id="ComisionamientoBean$porcComision"]', verificaComissaoValorZero(registros[37]), 0);
						commandTAB('//*[@id="ComisionamientoBean$porcComision"]')
						break;

					case "1249":
						if(tipoSelecao.equals("EndossoCobertura")){
							//Comissão
							limpaCampo('//*[@id="ComisionamientoBean$porcComision"]', 1)
							preencheXpath('//*[@id="ComisionamientoBean$porcComision"]', verificaComissaoValorZero(registros[91]), 0);
							commandTAB('//*[@id="ComisionamientoBean$porcComision"]')
							break;
						} else {
							//Tipo de pagamento
							formaPagamentoTabelaDePrecos(registros[86])
							//Forma de Pagamento
							selecionaItemLista(registros[88],'//*[@id="ParrillaBean$primRecibo"]',"option")
							//Dia do Pagamento
							preencheXpath('//*[@id="ParrillaBean$diaPago"]', ajustaDiaPagamento(registros[1], registros[89]) , 0);
							//Tipo Comissionamento
							selecionaItemLista(registros[90],'//*[@id="ComisionamientoBean$tipoComisionamiento"]',"option")
							//Comissão
							limpaCampo('//*[@id="ComisionamientoBean$porcComision"]', 1)
							preencheXpath('//*[@id="ComisionamientoBean$porcComision"]', verificaComissaoValorZero(registros[91]), 0);
							commandTAB('//*[@id="ComisionamientoBean$porcComision"]')
							break;
						}
					case "2026":
					//Tipo de pagamento
						formaPagamentoTabelaDePrecos(registros[36])
					//Forma de Pagamento
						selecionaItemLista(registros[38],'//*[@id="ParrillaBean$primRecibo"]',"option")
					//Dia do Pagamento
						preencheXpath('//*[@id="ParrillaBean$diaPago"]', ajustaDiaPagamento(registros[1], registros[39]) , 0);
					//Tipo Comissionamento
						selecionaItemLista(registros[40],'//*[@id="ComisionamientoBean$tipoComisionamiento"]',"option")
					//Comissão
						limpaCampo('//*[@id="ComisionamientoBean$porcComision"]', 1)
						preencheXpath('//*[@id="ComisionamientoBean$porcComision"]', verificaComissaoValorZero(registros[41]), 0);
						commandTAB('//*[@id="ComisionamientoBean$porcComision"]')
						break;

					case "2027":
					//Tipo de pagamento
						formaPagamentoTabelaDePrecos(registros[40])
					//Forma de Pagamento
						selecionaItemLista(registros[42],'//*[@id="ParrillaBean$primRecibo"]',"option")
					//Dia do Pagamento
						preencheXpath('//*[@id="ParrillaBean$diaPago"]', ajustaDiaPagamento(registros[1], registros[43]) , 0);
					//Tipo Comissionamento
						selecionaItemLista(registros[44],'//*[@id="ComisionamientoBean$tipoComisionamiento"]',"option")
					//Comissão
						limpaCampo('//*[@id="ComisionamientoBean$porcComision"]', 1)
						preencheXpath('//*[@id="ComisionamientoBean$porcComision"]', verificaComissaoValorZero(registros[45]), 0);
						commandTAB('//*[@id="ComisionamientoBean$porcComision"]')
						break;

					case "2028":
					//Tipo de pagamento
						formaPagamentoTabelaDePrecos(registros[96])
					//Forma de Pagamento
						selecionaItemLista(registros[98],'//*[@id="ParrillaBean$primRecibo"]',"option")
					//Dia do Pagamento
						preencheXpath('//*[@id="ParrillaBean$diaPago"]', ajustaDiaPagamento(registros[1], registros[99]) , 0);
					//Tipo Comissionamento
						selecionaItemLista(registros[100],'//*[@id="ComisionamientoBean$tipoComisionamiento"]',"option")
					//Comissão
						limpaCampo('//*[@id="ComisionamientoBean$porcComision"]', 1)
						preencheXpath('//*[@id="ComisionamientoBean$porcComision"]', verificaComissaoValorZero(registros[101]), 0);
						commandTAB('//*[@id="ComisionamientoBean$porcComision"]')
						break;
				}
				//Calcular
				clickXpath('//*[@id="btnCalcular"]', 2);
				//Emitir
				selecionaItemLista('Emitir', '//*[@id="blParrillaSectionBar"]/table/tbody/tr/td/table/tbody', 'div')
				fechaAlerta()
			}
			b =alerta(registros, 3,ramo)
		} else {
			registros[72] = " Tela [TABELA DE PREÇOS] não apresentada"
			registros[73] = 'Processado'
			b = false
			//HOME
			try{
				clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
			} catch (Exception e){}
		}
		return b
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean dadosEmissao(List<String> registros, String ramo, String tipoSelecao){
		System.out.println('dadosEmissao ['+ramo+']')
		boolean b
		String s = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).toString().subSequence(0, 10)
		if(listaDeTelas(s)){
			if(tipoSelecao.equals("Endosso")||tipoSelecao.equals("EndossoCobertura")){
				//Avançar
				try{
					clickXpath('//*[@id="btnAceptar"]', 2);
					b = true
				} catch (Exception e){
					b = false
				}
			} else {
				switch (ramo){

					case "1240":
						switch (registros[96]){
							case "Débito em conta":
							//Tipo de Conta
							selecionaItemLista(registros[100].toLowerCase(), '//*[@id="DatosBancariosBean$ccc_cuenta_typeAccount"]', 'option')
							//Banco / Agencia / DCO
							selecionaItemLista(registros[101], '//*[@id="DatosBancariosBean$ccc_cuenta_vBank"]', 'option')
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vOffice"]', registros[102], 0);
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlOffice"]', registros[103], 0);
							//N.de Conta/DCC
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vAccNumber"]', registros[104], 0);
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlAcc"]', registros[105], 0);
							//Verificar
							clickXpath('//*[@id="DatosBancariosBean$ccc_cuenta_btnConfirm"]', 2);
							break;
						}
						break;


					case "1241":
						switch (registros[42]){
							case "Débito em conta":
							//Tipo de Conta
							selecionaItemLista(registros[46].toLowerCase(), '//*[@id="DatosBancariosBean$ccc_cuenta_typeAccount"]', 'option')
							//Banco / Agencia / DCO
							selecionaItemLista(registros[47], '//*[@id="DatosBancariosBean$ccc_cuenta_vBank"]', 'option')
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vOffice"]', registros[48], 0);
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlOffice"]', registros[49], 0);
							//N.de Conta/DCC
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vAccNumber"]', registros[50], 0);
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlAcc"]', registros[51], 0);
							//Verificar
							clickXpath('//*[@id="DatosBancariosBean$ccc_cuenta_btnConfirm"]', 2);
							break;
						}
						break;

					case "1249":
						switch (registros[88]){
							case "Débito em conta":
							//Tipo de Conta
							selecionaItemLista(registros[92].toLowerCase(), '//*[@id="DatosBancariosBean$ccc_cuenta_typeAccount"]', 'option')
							//Banco / Agencia / DCO
							selecionaItemLista(registros[93], '//*[@id="DatosBancariosBean$ccc_cuenta_vBank"]', 'option')
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vOffice"]', registros[94], 0);
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlOffice"]', registros[95], 0);
							//N.de Conta/DCC
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vAccNumber"]', registros[96], 0);
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlAcc"]', registros[97], 0);
							//Verificar
							clickXpath('//*[@id="DatosBancariosBean$ccc_cuenta_btnConfirm"]', 2);
							break;
						}
						break;

					case "2026":
						switch (registros[38]){
							case "Débito em conta":
							//Tipo de Conta
							selecionaItemLista(registros[42].toLowerCase(), '//*[@id="DatosBancariosBean$ccc_cuenta_typeAccount"]', 'option')
							//Banco / Agencia / DCO
							selecionaItemLista(registros[43], '//*[@id="DatosBancariosBean$ccc_cuenta_vBank"]', 'option')
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vOffice"]', registros[44], 0);
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlOffice"]', registros[45], 0);
							//N.de Conta/DCC
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vAccNumber"]', registros[46], 0);
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlAcc"]', registros[47], 0);
							//Verificar
							clickXpath('//*[@id="DatosBancariosBean$ccc_cuenta_btnConfirm"]', 2);
							break;
						}
						break;

					case "2027":
						switch (registros[42]){
							case "Débito em conta":
							//Tipo de Conta
							selecionaItemLista(registros[46].toLowerCase(), '//*[@id="DatosBancariosBean$ccc_cuenta_typeAccount"]', 'option')
							//Banco / Agencia / DCO
							selecionaItemLista(registros[47], '//*[@id="DatosBancariosBean$ccc_cuenta_vBank"]', 'option')
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vOffice"]', registros[48], 0);
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlOffice"]', registros[49], 0);
							//N.de Conta/DCC
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vAccNumber"]', registros[50], 0);
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlAcc"]', registros[51], 0);
							//Verificar
							clickXpath('//*[@id="DatosBancariosBean$ccc_cuenta_btnConfirm"]', 2);
							break;
						}
						break;

					case "2028":
						switch (registros[98]){
							case "Débito em conta":
							//Tipo de Conta
							selecionaItemLista(registros[102].toLowerCase(), '//*[@id="DatosBancariosBean$ccc_cuenta_typeAccount"]', 'option')
							//Banco / Agencia / DCO
							selecionaItemLista(registros[103], '//*[@id="DatosBancariosBean$ccc_cuenta_vBank"]', 'option')
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vOffice"]', registros[104], 0);
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlOffice"]', registros[105], 0);
							//N.de Conta/DCC
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vAccNumber"]', registros[106], 0);
							preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlAcc"]', registros[107], 0);
							//Verificar
							clickXpath('//*[@id="DatosBancariosBean$ccc_cuenta_btnConfirm"]', 2);
							break;
						}
						break;
				}

				if(checkPopupAlert(registros, 5)){
					b = false
				} else {
					//Avançar
					try{
						clickXpath('//*[@id="btnAceptar"]', 2);
					}catch (Exception e){}
					b =alerta(registros, 3, ramo)
				}
			}

			/**
			 * Lista de clientes - opcional
			 */
			listaDeClientes()

			System.out.println('['+b+'] ['+registros[72]+']')

		} else {
			registros[72] = " Tela [D. Emissão] não apresentada"
			registros[73] = 'Processado'
			b = false
		}
		return b
	}

	/**
	 * @author be54955
	 */
	public static listaDeClientes(){
		try{
			String s = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).toString().subSequence(0, 13)
			if(listaDeTelas(s)){
				selecionaListaDeElementos('//*[@id="ListaClientesBean$listaClientes_tbody"]','tr')
				for(int k = 0; k < listOfElements.size(); k++){
					listOfElements[k].click()
					break
				}
			}
		}catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param time
	 * @return
	 */
	public static boolean checkPopupAlert(List<String> registros, int time){
		WebUI.delay(time);
		boolean b = false
		String alertPopup = pegaMSGAlerta()
		System.out.println(alertPopup)
		if(alertListBlocList(alertPopup)){
			registros[72] = alertPopup
			registros[73] = 'Processado'
			b = true
		}
		fechaAlerta()
		return b
	}


	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean dadosDoCliente(List<String> registros, String ramo, String tipoSelecao){
		System.out.println('dadosDoCliente ['+ramo+']')
		boolean b = false
		String s = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).toString().subSequence(0, 10)
		if(listaDeTelas(s)){
			//Dados fixos
			dadosSocioeconomicosSociodemograficos()
			if(tipoSelecao.equals("Endosso")||tipoSelecao.equals("EndossoCobertura")){
				try{
					//Número
					limparCamposInput('//*[@id="DomicilioClienteBeanTomador$numero"]')
					preencheXpath('//*[@id="DomicilioClienteBeanTomador$numero"]', '0', 0);
					b = true
				} catch (Exception e){}

				try{
					//Renda Mensal
					selecionaItemLista(rendaMensal, '//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]', 'option')
					b = true
				} catch (Exception e){}

				//Avançar
				try{
					clickXpath('//*[@id="btnAceptar"]', 2);
					b = true
				} catch(Exception e){
					b = false
				}

				//Avançar
				try{
					if(alertaDeContinue(registros, 2)){
						clickXpath('//*[@id="btnAceptar"]', 2);
						b = true
					}
				} catch(Exception e){
					b = false
				}

				//Avanço de tela para PJ
				//Avançar
				try{
					String t = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).toString().subSequence(0, 10)
					if(t.equals('D. Cliente')){
						clickXpath('//*[@id="btnAceptar"]', 2);
						b = true
					}
				} catch(Exception e){
				}


			} else {

				switch (ramo){
					case "1240":
						dadosDoClienteAutoAnual(registros, ramo)
						break;

					case "1241":
						dadosDoClienteAuto(registros, ramo)
						break;

					case "1249":
						dadosDoClienteAutoCaixa(registros, ramo)
						break

					case "2026":
						dadosDoClienteResidencia(registros, ramo)
						break;

					case "2027":
						dadosDoClienteCondominio(registros, ramo)
						break;

					case "2028":
						dadosDoClienteEmpresa(registros, ramo)
						break;
				}

				try{
					//Avançar
					try{
						clickXpath('//*[@id="btnAceptar"]', 2);
					}catch(Exception e){}
					b =alerta(registros, 3, ramo)
				} catch (Exception e){}

				/**
				 * Verifica mensagens de alerta antes de segui com a execução
				 */
				try{
					//Avançar
					try{
						if(alertaDeContinue(registros, 2)){
							clickXpath('//*[@id="btnAceptar"]', 2);
						}
					}catch(Exception e){}
					b =alerta(registros, 3, ramo)
				} catch (Exception e){}


				/**
				 * Existe uma situação onde a tela 'D. CLIENTE' fica bloqueada sem motivos aparentes, então forçamos mais um comando para avançar 
				 */
				try{
					s=""
					s = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).toString().subSequence(0, 10)
					if(s.contains('D. Cliente')){
						clickXpath('//*[@id="btnAceptar"]', 2);
					}
					b =alerta(registros, 3, ramo)
				} catch (Exception e){}
			}

			try{
				if(alertaDeContinue(registros, 2)){
					clickXpath('//*[@id="btnAceptar"]', 2);
					b = true
				}
			} catch(Exception e){}

		} else {
			registros[72] = " Tela [D. CLIENTE] não apresentada"
			registros[73] = 'Processado'
			b = false
			//HOME
			try{
				clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
			} catch (Exception e){}
		}
		System.out.println('dadosDoCliente ['+b+']')
		return b
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param ramo
	 * @return
	 */
	public static dadosDoClienteAuto(List<String> registros, String ramo){
		System.out.println('dadosDoCliente ['+ramo+']')


		try{
			//Data Expedição **
			limparCamposInput('//*[@id="DadosGeraisClienteBeanTomador$dataExpedicao"]')
		} catch (Exception e){}


		try{
			//Data Expedição **
			//preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$dataExpedicao"]', formataData(registros[44]), 0);
			//Órgão Emissor **
			preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$orgaoEmissor"]', 'SP', 0);
			//D. Nascimento
			limparCamposInput('//*[@id="DadosGeraisClienteBeanTomador$dataNascimento"]')
			preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$dataNascimento"]', formataData(registros[44]), 0);
		} catch (Exception e){}

		try{
			//Tipo Endereço
			selecionaItemLista(tplog(registros[47]), '//*[@id="DomicilioClienteBeanTomador$moradatype"]', 'option')
		} catch (Exception e){}

		try{
			//Endereco
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$moradaaddressPart2"]', registros[48], 0);
		} catch (Exception e){}

		try{
			//numero
			clicarEmElementoDaTela('//*[@id="DomicilioClienteBeanTomador$numero"]', 0)
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$numero"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$numero"]', formateNumero(registros[49]), 0);
		} catch (Exception e){}

		try{
			//cep
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]', registros[45], 0);
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]', registros[46], 0);
			//lupa
			clickXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostalAjaxLocFinderImg"]', 2);
		} catch (Exception e){}

		try{
			//Telefone
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$telefone_nationPhone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone_nationPhone"]', registros[54], 0);
			//Celular
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$celular_nationPhone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$celular_nationPhone"]', registros[55], 0);
			//E-mail 1
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$email1"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$email1"]', registros[56], 0);

		} catch (Exception e){}

		/**
		 * TELA PRD
		 */
		try{
			//Telefone
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$telefone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone"]', registros[54], 0);
			//Celular
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$celular"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$celular"]', registros[55], 0);
			//E-mail 1
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$email1"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$email1"]', registros[56], 0);

		} catch (Exception e){}



		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$sexo_label"]', 'textContent', 0)
			if(str.contains("Sexo".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Sexo
				selecionaItemLista(registros[57], '//*[@id="OutrosDadosClienteBeanTomador$sexo"]', 'option')
				//Profissão
				selecionaItemLista(profissao, '//*[@id="OutrosDadosClienteBeanTomador$profissao"]', 'option')
				//Estado Civil
				selecionaItemLista(estadoCivil, '//*[@id="OutrosDadosClienteBeanTomador$estadoCivil"]', 'option')
				//Nome Mãe
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$nomeMae"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$nomeMae"]', nomeMae, 0);
				//PIS
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$pis"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$pis"]', pis, 0);
				//INSS
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$inss"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$inss"]', inss, 0);
			}
		} catch (Exception e){}


		/**
		 * INT		
		 */
		try{
			//Renda Mensal
			selecionaItemLista(rendaMensal, '//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]', 'option')
		} catch (Exception e){}

		/**
		 * PRD
		 */
		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$sexo_label"]', 'textContent', 0)
			if(str.contains("Sexo".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Renda Mensal
				selecionaItemLista(rendaMensal, '//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]', 'option')
			}
		} catch (Exception e){}


		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia_label"]', 'textContent', 0)
			if(str.contains("Nome Fantasia".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Nome Fantasia
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia"]', nomeFantasia, 0);
				//Atividade Principal
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$actividadePrincipal"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$actividadePrincipal"]', atividadePrincipal, 0);
				//Natureza
				selecionaItemLista(natureza, '//*[@id="OutrosDadosClienteBeanTomador$naturezaRendimento"]', 'option')
				//Ocupação
				selecionaItemLista(ocupacao, '//*[@id="OutrosDadosClienteBeanTomador$ocupacao"]', 'option')
				//Faturamento
				selecionaItemLista(faturamento, '//*[@id="OutrosDadosClienteBeanTomador$faturamento"]', 'option')
				//QTD. Funcionarios
				selecionaItemLista(qdtFuncionários, '//*[@id="OutrosDadosClienteBeanTomador$numeroFuncionarios"]', 'option')
				//Patrimonio
				selecionaItemLista(patrimônio, '//*[@id="OutrosDadosClienteBeanTomador$patrimonio"]', 'option')

			}
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param ramo
	 * @return
	 */
	public static dadosDoClienteAutoAnual(List<String> registros, String ramo){
		System.out.println('dadosDoCliente ['+ramo+']')
		try{
			//Data Expedição **
			limparCamposInput('//*[@id="DadosGeraisClienteBeanTomador$dataExpedicao"]')
		} catch (Exception e){}

		try{
			//Data Expedição **
			//preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$dataExpedicao"]', formataData(registros[44]), 0);
			//Órgão Emissor **
			preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$orgaoEmissor"]', 'SP', 0);
			//D. Nascimento
			limparCamposInput('//*[@id="DadosGeraisClienteBeanTomador$dataNascimento"]')
			preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$dataNascimento"]', formataData(registros[106]), 0);
		} catch (Exception e){}

		try{
			//Tipo Endereço
			selecionaItemLista(tplog(registros[109]), '//*[@id="DomicilioClienteBeanTomador$moradatype"]', 'option')
		} catch (Exception e){}

		try{
			//Endereco
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$moradaaddressPart2"]', registros[110], 0);
		} catch (Exception e){}

		try{
			//numero
			clicarEmElementoDaTela('//*[@id="DomicilioClienteBeanTomador$numero"]', 0)
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$numero"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$numero"]', formateNumero(registros[111]), 0);
		} catch (Exception e){}

		try{
			//cep
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]', registros[107], 0);
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]', registros[108], 0);
			//lupa
			clickXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostalAjaxLocFinderImg"]', 2);
		} catch (Exception e){}


		/**
		 * TELA INT 
		 */
		try{
			//Telefone
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$telefone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone"]', registros[116], 0);
			//Celular
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$celular"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$celular"]', registros[117], 0);
			//E-mail 1
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$email1"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$email1"]', registros[118], 0);

		} catch (Exception e){}

		/**
		 * TELA PRD
		 */
		try{
			//Telefone
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$telefone_nationPhone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone_nationPhone"]', registros[116], 0);
			//Celular
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$celular_nationPhone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$celular_nationPhone"]', registros[117], 0);
			//E-mail 1
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$email1"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$email1"]', registros[118], 0);

		} catch (Exception e){}

		/**
		 * INT
		 */
		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$sexo_label"]', 'textContent', 0)
			if(str.contains("Sexo".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Sexo
				selecionaItemLista(registros[119], '//*[@id="OutrosDadosClienteBeanTomador$sexo"]', 'option')
				//Profissão
				selecionaItemLista(profissao, '//*[@id="OutrosDadosClienteBeanTomador$profissao"]', 'option')
				//Estado Civil
				selecionaItemLista(registros[120], '//*[@id="OutrosDadosClienteBeanTomador$estadoCivil"]', 'option')
				//Nome Mãe
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$nomeMae"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$nomeMae"]', nomeMae, 0);
				//PIS
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$pis"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$pis"]', pis, 0);
				//INSS
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$inss"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$inss"]', inss, 0);
				//Renda Mensal
				selecionaItemLista(rendaMensal, '//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]', 'option')
			}
		} catch (Exception e){}

		/**
		 * PRD
		 */
		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$sexo_label"]', 'textContent', 0)
			if(str.contains("Sexo".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Renda Mensal
				selecionaItemLista(rendaMensal, '//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]', 'option')
			}
		} catch (Exception e){}

		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia_label"]', 'textContent', 0)
			if(str.contains("Nome Fantasia".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Nome Fantasia
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia"]', nomeFantasia, 0);
				//Atividade Principal
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$actividadePrincipal"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$actividadePrincipal"]', atividadePrincipal, 0);
				//Natureza
				selecionaItemLista(natureza, '//*[@id="OutrosDadosClienteBeanTomador$naturezaRendimento"]', 'option')
				//Ocupação
				selecionaItemLista(ocupacao, '//*[@id="OutrosDadosClienteBeanTomador$ocupacao"]', 'option')
				//Faturamento
				selecionaItemLista(faturamento, '//*[@id="OutrosDadosClienteBeanTomador$faturamento"]', 'option')
				//QTD. Funcionarios
				selecionaItemLista(qdtFuncionários, '//*[@id="OutrosDadosClienteBeanTomador$numeroFuncionarios"]', 'option')
				//Patrimonio
				selecionaItemLista(patrimônio, '//*[@id="OutrosDadosClienteBeanTomador$patrimonio"]', 'option')

			}
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param ramo
	 * @return
	 */
	public static dadosDoClienteAutoCaixa(List<String> registros, String ramo){
		System.out.println('dadosDoCliente ['+ramo+']')
		try{
			//Data Expedição **
			limparCamposInput('//*[@id="DadosGeraisClienteBeanTomador$dataExpedicao"]')
		} catch (Exception e){}

		try{
			//Data Expedição **
			//preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$dataExpedicao"]', formataData(registros[44]), 0);
			//Órgão Emissor **
			preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$orgaoEmissor"]', 'SP', 0);
			//D. Nascimento
			limparCamposInput('//*[@id="DadosGeraisClienteBeanTomador$dataNascimento"]')
			preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$dataNascimento"]', formataData(registros[98]), 0);
		} catch (Exception e){}

		try{
			//Tipo Endereço
			selecionaItemLista(tplog(registros[101]), '//*[@id="DomicilioClienteBeanTomador$moradatype"]', 'option')
		} catch (Exception e){}

		try{
			//Endereco
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$moradaaddressPart2"]', registros[102], 0);
		} catch (Exception e){}

		try{
			//numero
			clicarEmElementoDaTela('//*[@id="DomicilioClienteBeanTomador$numero"]', 0)
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$numero"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$numero"]', formateNumero(registros[103]), 0);
		} catch (Exception e){}

		try{
			//cep
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]', registros[99], 0);
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]', registros[100], 0);
			//lupa
			clickXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostalAjaxLocFinderImg"]', 2);
		} catch (Exception e){}

		/**
		 * INT
		 */
		try{
			//Telefone
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$telefone_nationPhone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone_nationPhone"]', registros[108], 0);
			//Celular
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$celular_nationPhone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$celular_nationPhone"]', registros[109], 0);
			//E-mail 1
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$email1"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$email1"]', registros[110], 0);
		} catch (Exception e){}

		/**
		 * TELA PRD
		 */
		try{
			//Telefone
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$telefone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone"]', registros[108], 0);
			//Celular
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$celular"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$celular"]', registros[109], 0);
			//E-mail 1
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$email1"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$email1"]', registros[110], 0);

		} catch (Exception e){}

		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$sexo_label"]', 'textContent', 0)
			if(str.contains("Sexo".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Sexo
				selecionaItemLista(sexo, '//*[@id="OutrosDadosClienteBeanTomador$sexo"]', 'option')
				//Profissão
				selecionaItemLista(profissao, '//*[@id="OutrosDadosClienteBeanTomador$profissao"]', 'option')
				//Estado Civil
				selecionaItemLista(estadoCivil, '//*[@id="OutrosDadosClienteBeanTomador$estadoCivil"]', 'option')
				//Nome Mãe
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$nomeMae"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$nomeMae"]', nomeMae, 0);
				//PIS
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$pis"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$pis"]', pis, 0);
				//INSS
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$inss"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$inss"]', inss, 0);
				//Renda Mensal
				selecionaItemLista(rendaMensal, '//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]', 'option')
			}
		} catch (Exception e){}

		/**
		 * PRD
		 */
		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$sexo_label"]', 'textContent', 0)
			if(str.contains("Sexo".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Renda Mensal
				selecionaItemLista(rendaMensal, '//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]', 'option')
			}
		} catch (Exception e){}

		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia_label"]', 'textContent', 0)
			if(str.contains("Nome Fantasia".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Nome Fantasia
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia"]', nomeFantasia, 0);
				//Atividade Principal
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$actividadePrincipal"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$actividadePrincipal"]', atividadePrincipal, 0);
				//Natureza
				selecionaItemLista(natureza, '//*[@id="OutrosDadosClienteBeanTomador$naturezaRendimento"]', 'option')
				//Ocupação
				selecionaItemLista(ocupacao, '//*[@id="OutrosDadosClienteBeanTomador$ocupacao"]', 'option')
				//Faturamento
				selecionaItemLista(faturamento, '//*[@id="OutrosDadosClienteBeanTomador$faturamento"]', 'option')
				//QTD. Funcionarios
				selecionaItemLista(qdtFuncionários, '//*[@id="OutrosDadosClienteBeanTomador$numeroFuncionarios"]', 'option')
				//Patrimonio
				selecionaItemLista(patrimônio, '//*[@id="OutrosDadosClienteBeanTomador$patrimonio"]', 'option')

			}
		} catch (Exception e){}
	}


	/**
	 * @author be54955
	 * @param registros
	 * @param ramo
	 * @return
	 */
	public static dadosDoClienteCondominio(List<String> registros, String ramo){
		System.out.println('dadosDoCliente ['+ramo+']')
		//	dadosSocioeconomicosSociodemograficos(registros)

		try{
			//Data Expedição **
			//preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$dataExpedicao"]', formataData(registros[44]), 0);
			//Órgão Emissor **
			//preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$orgaoEmissor"]', 'SP', 0);
			//D. Nascimento
			preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$dataNascimento"]', formataData(registros[52]), 0);
		} catch (Exception e){}

		try{
			//Tipo Endereço
			selecionaItemLista(tplog(registros[55]), '//*[@id="DomicilioClienteBeanTomador$moradatype"]', 'option')
		} catch (Exception e){}

		try{
			//Endereco
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$moradaaddressPart2"]', registros[56], 0);
		} catch (Exception e){}

		try{
			//numero
			clicarEmElementoDaTela('//*[@id="DomicilioClienteBeanTomador$numero"]', 0)
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$numero"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$numero"]', formateNumero(registros[57]), 0);
		} catch (Exception e){}

		try{
			//complemento
			clicarEmElementoDaTela('//*[@id="DomicilioClienteBeanTomador$restoMorada"]', 0)
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$restoMorada"]', formateNumero(registros[58]), 0);
		} catch (Exception e){}

		try{
			//cep
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]', registros[53], 0);
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]', registros[54], 0);

			//lupa
			clickXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostalAjaxLocFinderImg"]', 2);
			//Telefone
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$telefone_nationPhone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone_nationPhone"]', registros[62], 0);
			//Celular
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$celular_nationPhone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$celular_nationPhone"]', registros[63], 0);
			//E-mail 1
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$email1"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$email1"]', registros[64], 0);
		} catch (Exception e){}


		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$sexo_label"]', 'textContent', 0)
			if(str.contains("Sexo".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Sexo
				selecionaItemLista(registros[65], '//*[@id="OutrosDadosClienteBeanTomador$sexo"]', 'option')
				//Profissão
				selecionaItemLista(profissao, '//*[@id="OutrosDadosClienteBeanTomador$profissao"]', 'option')
				//Estado Civil
				selecionaItemLista(registros[66], '//*[@id="OutrosDadosClienteBeanTomador$estadoCivil"]', 'option')
				//Nome Mãe
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$nomeMae"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$nomeMae"]', nomeMae, 0);
				//PIS
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$pis"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$pis"]', pis, 0);
				//INSS
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$inss"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$inss"]', inss, 0);
			}
		} catch (Exception e){}

		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$sexo_label"]', 'textContent', 0)
			if(str.contains("Sexo".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Renda Mensal
				selecionaItemLista(rendaMensal, '//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]', 'option')
			}
		} catch (Exception e){}

		/**
		 * PRD
		 */
		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$sexo_label"]', 'textContent', 0)
			if(str.contains("Sexo".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Renda Mensal
				selecionaItemLista(rendaMensal, '//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]', 'option')
			}
		} catch (Exception e){}

		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia_label"]', 'textContent', 0)
			if(str.contains("Nome Fantasia".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Nome Fantasia
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia"]', nomeFantasia, 0);
				//Atividade Principal
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$actividadePrincipal"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$actividadePrincipal"]', atividadePrincipal, 0);
				//Natureza
				selecionaItemLista(natureza, '//*[@id="OutrosDadosClienteBeanTomador$naturezaRendimento"]', 'option')
				//Ocupação
				selecionaItemLista(ocupacao, '//*[@id="OutrosDadosClienteBeanTomador$ocupacao"]', 'option')
				//Faturamento
				selecionaItemLista(faturamento, '//*[@id="OutrosDadosClienteBeanTomador$faturamento"]', 'option')
				//QTD. Funcionarios
				selecionaItemLista(qdtFuncionários, '//*[@id="OutrosDadosClienteBeanTomador$numeroFuncionarios"]', 'option')
				//Patrimonio
				selecionaItemLista(patrimônio, '//*[@id="OutrosDadosClienteBeanTomador$patrimonio"]', 'option')

			}
		} catch (Exception e){}

	}

	/**
	 * @author be54955
	 * @param registros
	 * @param ramo
	 * @return
	 */
	public static dadosDoClienteResidencia(List<String> registros, String ramo){
		System.out.println('dadosDoCliente ['+ramo+']')
		try{
			//Data Expedição **
			limparCamposInput('//*[@id="DadosGeraisClienteBeanTomador$dataExpedicao"]')
		} catch (Exception e){}

		try{
			//Data Expedição **
			//preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$dataExpedicao"]', formataData(registros[44]), 0);
			//Órgão Emissor **
			//preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$orgaoEmissor"]', 'SP', 0);
			//D. Nascimento
			limparCamposInput('//*[@id="DadosGeraisClienteBeanTomador$dataNascimento"]')
			preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$dataNascimento"]', formataData(registros[48]), 0);
		} catch (Exception e){}

		try{
			//Tipo Endereço
			selecionaItemLista(tplog(registros[51]), '//*[@id="DomicilioClienteBeanTomador$moradatype"]', 'option')
		} catch (Exception e){}

		try{
			//Endereco
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$moradaaddressPart2"]', registros[52], 0);
		} catch (Exception e){}

		try{
			//numero
			clicarEmElementoDaTela('//*[@id="DomicilioClienteBeanTomador$numero"]', 0)
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$numero"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$numero"]', formateNumero(registros[53]), 0);
		} catch (Exception e){}

		try{
			//complemento
			clicarEmElementoDaTela('//*[@id="DomicilioClienteBeanTomador$restoMorada"]', 0)
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$restoMorada"]', formateNumero(registros[54]), 0);
		} catch (Exception e){}

		try{
			//cep
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]', registros[49], 0);
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]', registros[50], 0);
			//lupa
			clickXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostalAjaxLocFinderImg"]', 2);
		} catch (Exception e){}

		/**
		 * TELA INT
		 */
		try{
			//Telefone
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$telefone_nationPhone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone_nationPhone"]', registros[58], 0);
			//Celular
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$celular_nationPhone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$celular_nationPhone"]', registros[59], 0);
			//E-mail 1         //*[@id="DomicilioClienteBeanTomador$email1"]
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$email1"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$email1"]', registros[60], 0);
		} catch (Exception e){}

		/**
		 * TELA PRD
		 */
		try{
			//Telefone
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$telefone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone"]', registros[58], 0);
			//Celular
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$celular"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$celular"]', registros[59], 0);
			//E-mail 1
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$email1"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$email1"]', registros[60], 0);

		} catch (Exception e){}

		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$sexo_label"]', 'textContent', 0)
			if(str.contains("Sexo".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Sexo
				selecionaItemLista(registros[61], '//*[@id="OutrosDadosClienteBeanTomador$sexo"]', 'option')
				//Profissão
				selecionaItemLista(profissao, '//*[@id="OutrosDadosClienteBeanTomador$profissao"]', 'option')
				//Estado Civil
				selecionaItemLista(registros[62], '//*[@id="OutrosDadosClienteBeanTomador$estadoCivil"]', 'option')
				//Nome Mãe
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$nomeMae"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$nomeMae"]', nomeMae, 0);
				//PIS
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$pis"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$pis"]', pis, 0);
				//INSS
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$inss"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$inss"]', inss, 0);
			}
		} catch (Exception e){}

		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$sexo_label"]', 'textContent', 0)
			if(str.contains("Sexo".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Renda Mensal
				selecionaItemLista(rendaMensal, '//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]', 'option')
			}
		} catch (Exception e){}

		/**
		 * PRD
		 */
		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$sexo_label"]', 'textContent', 0)
			if(str.contains("Sexo".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Renda Mensal
				selecionaItemLista(rendaMensal, '//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]', 'option')
			}
		} catch (Exception e){}

		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia_label"]', 'textContent', 0)
			if(str.contains("Nome Fantasia".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Nome Fantasia
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia"]', nomeFantasia, 0);
				//Atividade Principal
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$actividadePrincipal"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$actividadePrincipal"]', atividadePrincipal, 0);
				//Natureza
				selecionaItemLista(natureza, '//*[@id="OutrosDadosClienteBeanTomador$naturezaRendimento"]', 'option')
				//Ocupação
				selecionaItemLista(ocupacao, '//*[@id="OutrosDadosClienteBeanTomador$ocupacao"]', 'option')
				//Faturamento
				selecionaItemLista(faturamento, '//*[@id="OutrosDadosClienteBeanTomador$faturamento"]', 'option')
				//QTD. Funcionarios
				selecionaItemLista(qdtFuncionários, '//*[@id="OutrosDadosClienteBeanTomador$numeroFuncionarios"]', 'option')
				//Patrimonio
				selecionaItemLista(patrimônio, '//*[@id="OutrosDadosClienteBeanTomador$patrimonio"]', 'option')

			}
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param ramo
	 * @return
	 */
	public static dadosDoClienteEmpresa(List<String> registros, String ramo){
		System.out.println('dadosDoCliente ['+ramo+']')
		try{
			//Data Expedição **
			limparCamposInput('//*[@id="DadosGeraisClienteBeanTomador$dataExpedicao"]')
		} catch (Exception e){}

		try{
			//Data Expedição **
			//preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$dataExpedicao"]', formataData(registros[44]), 0);
			//Órgão Emissor **
			//preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$orgaoEmissor"]', 'SP', 0);
			//D. Nascimento
			limparCamposInput('//*[@id="DadosGeraisClienteBeanTomador$dataNascimento"]')
			preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$dataNascimento"]', formataData(registros[108]), 0);
		} catch (Exception e){}

		try{
			//Tipo Endereço
			selecionaItemLista(tplog(registros[111]), '//*[@id="DomicilioClienteBeanTomador$moradatype"]', 'option')
		} catch (Exception e){}

		try{
			//Endereco
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$moradaaddressPart2"]', registros[112], 0);
		} catch (Exception e){}

		try{
			//numero
			clicarEmElementoDaTela('//*[@id="DomicilioClienteBeanTomador$numero"]', 0)
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$numero"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$numero"]', formateNumero(registros[113]), 0);
		} catch (Exception e){}

		try{
			//complemento
			clicarEmElementoDaTela('//*[@id="DomicilioClienteBeanTomador$restoMorada"]', 0)
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$restoMorada"]', formateNumero(registros[114]), 0);
		} catch (Exception e){}

		try{
			//cep
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]', registros[109], 0);
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]', registros[110], 0);
			//lupa
			clickXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostalAjaxLocFinderImg"]', 2);
		} catch (Exception e){}


		/**
		 * INT 	
		 */
		try{
			//Telefone
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$telefone_nationPhone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone_nationPhone"]', registros[118], 0);
			//Celular
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$celular_nationPhone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$celular_nationPhone"]', registros[119], 0);
			//E-mail 1
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$email1"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$email1"]', registros[120], 0);

		} catch (Exception e){}

		/**
		 * PRD 
		 */
		try{
			//Telefone
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$telefone"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone"]', registros[118], 0);
			//Celular
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$celular"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$celular"]', registros[119], 0);
			//E-mail 1
			limparCamposInput('//*[@id="DomicilioClienteBeanTomador$email1"]')
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$email1"]', registros[120], 0);

		} catch (Exception e){}


		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$sexo_label"]', 'textContent', 0)
			if(str.contains("Sexo".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Sexo
				selecionaItemLista(registros[121], '//*[@id="OutrosDadosClienteBeanTomador$sexo"]', 'option')
				//Profissão
				selecionaItemLista(profissao, '//*[@id="OutrosDadosClienteBeanTomador$profissao"]', 'option')
				//Estado Civil
				selecionaItemLista(registros[122], '//*[@id="OutrosDadosClienteBeanTomador$estadoCivil"]', 'option')
				//Nome Mãe
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$nomeMae"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$nomeMae"]', nomeMae, 0);
				//PIS
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$pis"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$pis"]', pis, 0);
				//INSS
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$inss"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$inss"]', inss, 0);
				//Renda Mensal
				selecionaItemLista(rendaMensal, '//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]', 'option')
			}
		} catch (Exception e){}

		try{
			String str = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia_label"]', 'textContent', 0)
			if(str.contains("Nome Fantasia".toString())){
				/**
				 * DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
				 */
				//Nome Fantasia
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia"]', nomeFantasia, 0);
				//Atividade Principal
				limparCamposInput('//*[@id="OutrosDadosClienteBeanTomador$actividadePrincipal"]')
				preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$actividadePrincipal"]', atividadePrincipal, 0);
				//Natureza
				selecionaItemLista(natureza, '//*[@id="OutrosDadosClienteBeanTomador$naturezaRendimento"]', 'option')
				//Ocupação
				selecionaItemLista(ocupacao, '//*[@id="OutrosDadosClienteBeanTomador$ocupacao"]', 'option')
				//Faturamento
				selecionaItemLista(faturamento, '//*[@id="OutrosDadosClienteBeanTomador$faturamento"]', 'option')
				//QTD. Funcionarios
				selecionaItemLista(qdtFuncionários, '//*[@id="OutrosDadosClienteBeanTomador$numeroFuncionarios"]', 'option')
				//Patrimonio
				selecionaItemLista(patrimônio, '//*[@id="OutrosDadosClienteBeanTomador$patrimonio"]', 'option')

			}
		} catch (Exception e){}

	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean dadosComuns(List<String> registros, String ramo, String tipoSelecao){
		System.out.println()
		boolean b = false
		String s = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).toString().subSequence(0, 9)
		if(listaDeTelas(s)){
			if(tipoSelecao.equals("Endosso")||tipoSelecao.equals("EndossoCobertura")){
				try{
					//Avançar
					clickXpath('//*[@id="btnAceptar"]', 2);
					b = true
				} catch (Exception e){
					b = false
				}
			} else {
				try{
					if(ramo.equals('1249')){
						//Cosseguro
						selecionaItemLista(registros[113], '//*[@id="ReaseguroCoaseguroBean$coaseguro"]', 'option')
						//Preencher dados cosseguro
						clickXpath('//*[@id="blReaseguroCoaseguroContent"]/table/tbody/tr[1]/td[2]', 2);
						//COSSEGURO ACEITO
						cosseguroAceito(registros,ramo)
						//Avançar
						clickXpath('//*[@id="btnAceptar"]', 2);
						//Avançar
						clickXpath('//*[@id="btnAceptar"]', 2);
						b =alerta(registros, 3, ramo)
					} else {
						//Avançar
						clickXpath('//*[@id="btnAceptar"]', 2);
						b =alerta(registros, 3, ramo)
					}
				} catch (Exception e){}
			}
		} else {
			registros[72] = " Tela [D. Comuns] não apresentada"
			registros[73] = 'Processado'
		}
		System.out.println('dadosComuns ['+b+"]")
		return b
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param ramo
	 * @return
	 * Falta definir a origem dos dados para preenchimento 
	 */
	public static  cosseguroAceito(List<String> registros, String ramo){
		System.out.println()
		String s = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).toString().subSequence(0, 9)
		if(listaDeTelas(s)){
			//Tipo de Cosseguro
			selecionaItemLista(registros[114], '//*[@id="CoaseguroAceptadoBean$tipoCoaseguro"]', 'option')
			//Data Início
			preencheXpath('//*[@id="CoaseguroAceptadoBean$fechaIniciacion"]', registros[115], 0);
			//Código de Companhia
			preencheXpath('//*[@id="CoaseguroAceptadoCompanyia$codigo"]', registros[116], 0);
			//Lupa
			clickXpath('//*[@id="blCoaseguroAcepContent"]/table[2]/tbody/tr/td[2]', 2);
			//Gestor Associado
			preencheXpath('//*[@id="CoaseguroAceptadoBean$gestorAsociado"]', registros[117], 0);
			//Nº Certificado
			preencheXpath('//*[@id="CoaseguroAceptadoBean$numCertif"]',registros[118], 0);
			//Apólice Referenciada
			preencheXpath('//*[@id="CoaseguroAceptadoBean$polizaRef"]', registros[119], 0);
			//% Participação
			preencheXpath('//*[@id="CoaseguroAceptadoBean$porcParticipacion"]', registros[120], 0);
			//% Comissão s/P.L.
			preencheXpath('//*[@id="CoaseguroAceptadoBean$porcComisionPN"]', registros[121], 0);
			//% Comissão s/R.E.
			preencheXpath('//*[@id="CoaseguroAceptadoBean$porcComisionRE"]', registros[122], 0);
			//% Comissão s/C.R.C
			preencheXpath('//*[@id="CoaseguroAceptadoBean$porcComisionCRC"]', registros[123], 0);
			//% Custos Gestão
			preencheXpath('//*[@id="CoaseguroAceptadoBean$porcGastosAdm"]', registros[124], 0);
			//Parcela Única
			try{
				clickXpath(selecionaParcela(registros[125]), 2);
			} catch (Exception e){}
		} else {
			registros[72] = " Tela [COSSEGURO ACEITO] não apresentada"
			registros[73] = 'Processado'
		}
	}

	/**
	 * @author be54955
	 * @param x
	 * @return
	 */
	public static String selecionaParcela(String x){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('S', '//*[@id="blCoaseguroAcepContent"]/table[6]/tbody/tr[4]/td[2]/table/tbody/tr/td[2]/table/tbody/tr/td[2]');
		urls.put('N', '//*[@id="blCoaseguroAcepContent"]/table[6]/tbody/tr[4]/td[2]/table/tbody/tr/td[3]/table/tbody/tr/td[2]');
		return urls.get(ramo.toString());
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean clausulas(List<String> registros, String ramo, String tipoSelecao){
		System.out.println()
		boolean b = false
		if(tipoSelecao.equals("EndossoCobertura")){
			WebUI.delay(2);
			String s = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).toString().subSequence(0, 9)
			System.out.println("["+s+"]")
			if(listaDeTelas(s)){
				try{
					//Avançar
					clickXpath('//*[@id="btnAceptar"]', 2);
					b =alerta(registros, 3,ramo)
				} catch (Exception e){}
			} else {
				registros[72] = " Tela [CLÁUSULAS] não apresentada"
				registros[73] = 'Processado'
				//HOME
				try{
					clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
				} catch (Exception e){}
			}
		} else {
			WebUI.delay(2);
			String s = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).toString().subSequence(0, 9)
			System.out.println("["+s+"]")
			if(listaDeTelas(s)){
				try{
					//CLÁUSULAS OPERACIONAIS
					System.out.println()
					clausulasOperacionais(registros, '300 - Texto livre', indexCheck('300 - Texto livre',ramo), indexClausulaTexto('300 - Texto livre',ramo), tipoSelecao, ramo)
					clausulasOperacionais(registros, '310 - Clausula de Franquia', indexCheck('310 - Clausula de Franquia',ramo), indexClausulaTexto('310 - Clausula de Franquia',ramo), tipoSelecao, ramo)
					if(ramo.equals('1249')){
						clausulasOperacionais(registros, '305 - Clausula Caixa', indexCheck('305 - Clausula Caixa',ramo), indexClausulaTexto('305 - Clausula Caixa',ramo), tipoSelecao, ramo)
					}
					//Avançar
					clickXpath('//*[@id="btnAceptar"]', 2);
					b =alerta(registros, 3,ramo)
				} catch (Exception e){}
			} else {
				registros[72] = " Tela [CLÁUSULAS] não apresentada"
				registros[73] = 'Processado'
				//HOME
				try{
					clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
				} catch (Exception e){}
			}

		}


		System.out.println('clausulas ['+b+"]")
		return b
	}

	/**
	 * @author be54955
	 * @param clausula
	 * @param ramo
	 * @return
	 */
	public static int indexCheck(String clausula, String ramo){
		int index
		switch (clausula){
			case "300 - Texto livre":
				switch (ramo){
					case "1240":
					index = 121
					break
					case "1241":
					index = 59
					break
					case "1249":
					index = 126
					break
					case "2026":
					index = 63
					break
					case "2027":
					index = 67
					break
					case "2028":
					index = 123
					break
				}
				break;

			case "310 - Clausula de Franquia":
				switch (ramo){
					case "1240":
					index = 123
					break
					case "1241":
					index = 61
					break
					case "1249":
					index = 130
					break
					case "2026":
					index = 65
					break
					case "2027":
					index = 69
					break
					case "2028":
					index = 125
					break
				}
				break;

			case "305 - Clausula Caixa":
				switch (ramo){
					case "1249":
					index = 128
				}
				break;
		}
		return index
	}

	/**
	 * @author be54955
	 * @param clausula
	 * @param ramo
	 * @return
	 */
	public static int indexClausulaTexto(String clausula, String ramo){
		int index
		switch (clausula){
			case "300 - Texto livre":
				switch (ramo){
					case "1240":
					index = 122
					break
					case "1241":
					index = 60
					break
					case "1249":
					index = 127
					break
					case "2026":
					index = 64
					break
					case "2027":
					index = 68
					break
					case "2028":
					index = 124
					break
				}
				break;

			case "310 - Clausula de Franquia":
				switch (ramo){
					case "1240":
					index = 124
					break
					case "1241":
					index = 62
					break
					case "1249":
					index = 131
					break
					case "2026":
					index = 66
					break
					case "2027":
					index = 70
					break
					case "2028":
					index = 126
					break
				}
				break;

			case "305 - Clausula Caixa":
				switch (ramo){
					case "1249":
					index = 129
				}
				break;
		}
		return index
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param titulo
	 * @param check
	 * @param indexText
	 * @return
	 */
	public static clausulasOperacionais(List<String> registros, String titulo, int check, int indexText, String tipoSelecao, String ramo){
		System.out.println('['+titulo+'] ['+registros[check]+'] ['+check+'] ['+registros[indexText]+'] ['+indexText+'] ['+tipoSelecao+'] ['+ramo+']')
		/**
		 * Verifica se existe a lina da clausula adicionada
		 */
		boolean linha = existeLinaDaClausulaAdicionada()
		/**
		 * Seleciona Cláusula
		 */
		try{
			if(registros[check].equals('S')){
				if(tipoSelecao.equals("Endosso")){
					inclusaoTextoLivre(titulo, ramo)
					textoClausula(registros, indexText)
				} else {
					adicionaClaula(titulo)
					inclusaoTextoLivre(titulo, ramo)
					textoClausula(registros, indexText)
				}
				try{
					//Salvar
					clickXpath('//*[@id="btnAceptar"]', 2);
				} catch(Exception e){}
			}
		} catch (Exception e){}
	}


	/**
	 * @author be54955
	 * @param titulo
	 * @return
	 */
	public static adicionaClaula(String titulo){
		//Cláusula
		selecionaItemLista(titulo, '//*[@id="ClausulasOpcionalesBean$clausula"]', 'option')
		//Adicionar Cláusula
		selecionaListaDeElementos('//*[@id="blClausulasOpcionalesContent"]/table[1]/tbody/tr','img')
		for(int k = 0; k < listOfElements.size(); k++){
			System.out.println("["+k+"] ["+listOfElements[k].getAttribute('title') +"]" )
			if(listOfElements[k].getAttribute('title').contains('Adicionar Cláusula'.toString())){
				listOfElements[k].click()
				break
			}
		}
	}

	/**
	 * @author be54955
	 * @param titulo
	 */
	public static void  inclusaoTextoLivre(String titulo, String ramo){
		/**
		 * Seleciona Cláusula para inclusão do texto livre
		 */
		selecionaListaDeElementos('//*[@id="blClausulasSeleccionadasContent"]/table[2]/tbody','input')
		for(int k = 0; k < listOfElements.size(); k++){
			System.out.println("["+k+"] ["+listOfElements[k].getAttribute('value') +"] ["+titulo+"] ["+clausuras(titulo)+']' )
			if(listOfElements[k].getAttribute('value').contains(clausuras(titulo))){
				clickXpath( clausurasURL(titulo, ramo), 0);
				break
			}
		}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param indexText
	 */
	public static void textoClausula(List<String> registros, int indexText){
		//TEXTO CLÁUSULA
		limparCamposInput('//*[@id="TextoClausulaBean$textoClausulaLibre"]')
		preencheXpath('//*[@id="TextoClausulaBean$textoClausulaLibre"]', registros[indexText], 1);
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean existeLinaDaClausulaAdicionada(){
		/**
		 * Verifica se existe a lina da clausula adicionada
		 */
		boolean linha = false
		try{
			selecionaListaDeElementos('//*[@id="blClausulasSeleccionadasContent"]/table[2]','input')
			for(int k = 0; k < listOfElements.size(); k++){
				System.out.println("["+k+"] ["+listOfElements[k].getAttribute('value') +"]" )
				if( listOfElements[k].getAttribute('value').contains('Texto livre'.toString()) ||
				listOfElements[k].getAttribute('value').contains('Clausula de Franquia'.toString()) ||
				listOfElements[k].getAttribute('value').contains('Clausula Caixa'.toString()) ){
					linha = true
					break
				}
			}
		} catch (Exception e){}
		System.out.println("Lina da clausula adicionada ["+linha+"]")
		return linha
	}

	/**
	 *
	 * @param ramo
	 * @return
	 */
	public static String clausuras(String ramo){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('300 - Texto livre', 'Texto livre');
		urls.put('305 - Clausula Caixa', 'Clausula Caixa');
		urls.put('310 - Clausula de Franquia', 'Clausula de Franquia');
		return urls.get(ramo.toString());
	}

	/**
	 *
	 * @param ramo
	 * @return
	 */
	public static String clausurasURL(String titulo, String ramo){

		String url
		//		Map<String, String> urls = new HashMap<String, String>()
		if(ramo.equals('1249')){
			url = urls1249(titulo)

			//		  urls.put('300 - Texto livre', '//*[@id="blClausulasSeleccionadasContent"]/table[2]/tbody/tr[1]/td[6]');
			//		  urls.put('305 - Clausula Caixa', '//*[@id="blClausulasSeleccionadasContent"]/table[2]/tbody/tr[2]/td[6]');
			//		  urls.put('310 - Clausula de Franquia', '//*[@id="blClausulasSeleccionadasContent"]/table[2]/tbody/tr[3]/td[6]');
			//		  urls.get(titulo.toString());
		} else {
			url = urls(titulo)
			//		  urls.put('300 - Texto livre', '//*[@id="blClausulasSeleccionadasContent"]/table[2]/tbody/tr[1]/td[6]');
			//		  urls.put('310 - Clausula de Franquia', '//*[@id="blClausulasSeleccionadasContent"]/table[2]/tbody/tr[2]/td[6]');
			//		  urls.get(titulo.toString());
		}
		return  url
	}


	/**
	 * @author be54955
	 * @param titulo
	 * @return
	 */
	public static String urls1249(String titulo){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('300 - Texto livre', '//*[@id="blClausulasSeleccionadasContent"]/table[2]/tbody/tr[1]/td[6]');
		urls.put('305 - Clausula Caixa', '//*[@id="blClausulasSeleccionadasContent"]/table[2]/tbody/tr[2]/td[6]');
		urls.put('310 - Clausula de Franquia', '//*[@id="blClausulasSeleccionadasContent"]/table[2]/tbody/tr[3]/td[6]');
		urls.get(titulo.toString());
		return  urls.get(titulo.toString());
	}

	/**
	 * @author be54955
	 * @param titulo
	 * @return
	 */
	public static String urls(String titulo){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('300 - Texto livre', '//*[@id="blClausulasSeleccionadasContent"]/table[2]/tbody/tr[1]/td[6]');
		urls.put('310 - Clausula de Franquia', '//*[@id="blClausulasSeleccionadasContent"]/table[2]/tbody/tr[2]/td[6]');
		urls.get(titulo.toString());
		return  urls.get(titulo.toString());
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean confirmaEmissao(List<String> registros, String ramo, String tipoSelecao){
		System.out.println()
		boolean b = false
		String s = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).toString().subSequence(0, 22)
		if(listaDeTelas(s)){
			try{
				//Avançar
				clickXpath('//*[@id="btnAceptar"]', 2);
				b =alerta(registros, 3, ramo)
			} catch (Exception e){}

			try{
				//Avançar
				clickXpath('//*[@id="btnAceptar"]', 2);
				b =alerta(registros, 3, ramo)
			} catch (Exception e){}
		} else {
			registros[72] = " Tela [CONFIRMAÇÃO DE EMISSÃO] não apresentada"
			registros[73] = 'Processado'
			b = false
			//HOME
			try{
				clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
			} catch (Exception e){}
		}
		System.out.println('confirmaEmissao ['+b+"]")
		return b
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean emissaoRealisada(List<String> registros, String ramo, String tipoSelecao){
		WebUI.delay(15);
		System.out.println()
		boolean b = false
		String s = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).toString().subSequence(0, 17)
		if(listaDeTelas(s)){
			try{
				if(tipoSelecao.equals("Endosso")||tipoSelecao.equals("EndossoCobertura") ){
					System.out.println()
					registros[71] = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
					System.out.println(registros[71])
					registros[72] = recuperaValor('//*[@id="CabeceraBean$endosoSusep"]', 'innerText', 0)
					System.out.println(registros[72])
					registros[74] = ajusteValorNaoEncontrado(recuperaValor('//*[@id="CabeceraBean$endosoSusepSUSEP"]', 'innerText', 0))
					System.out.println(registros[74])
				} else {
					System.out.println()
					registros[71] = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
					System.out.println(registros[71])
					registros[72] = recuperaValor('//*[@id="CabeceraBean$sucursal"]', 'innerText', 0)
					System.out.println(registros[72])
				}
				//Avançar
				clickXpath('//*[@id="btnValida"]', 1);
				WebUI.delay(5);
				b = true
			} catch (Exception e){}
		} else {
			registros[72] = " Tela [EMISSÃO REALIZADA] não apresentada"
			registros[73] = 'Processado'
			b = false
			//HOME
			try{
				clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
			} catch (Exception e){}
		}
		System.out.println('emissaoRealisada ['+b+"]")
		return b
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param c
	 * @param capital
	 * @param primerRec
	 * @return
	 */
	public static selecionaCobertura(List<String> registros, String c, int capital, int primerRec, String r){
		System.out.println("Cobertura ["+c+"] Capital ["+capital+"] primerRec["+primerRec+"] Valor ["+registros[capital]+"] Ramo ["+r+"]")
		if(!valorZero(registros[capital])){
			try{
				clickXpath('//*[@id="CoberturasBean$lineasCoberturas['+c+'].selecCobertura"]', 1);
			} catch (Exception e){}

			try{
				preencheXpath('//*[@id="CoberturasBean$lineasCoberturas['+c+'].capitales[0].capital"]', ajusteValorCoberturas(registros[capital]), 0);
				preencheXpath(premio(r, c), ajusteValorCoberturas(registros[primerRec]), 0);
				commandTAB(premio(r, c))
			} catch (Exception e){}
		}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param c
	 * @param capital
	 * @param primerRec
	 * @return
	 */
	public static selecionaCoberturaEndosso(List<String> registros, String c, int capital, int primerRec, String r){
		System.out.println("Cobertura ["+c+"] Capital ["+capital+"] primerRec["+primerRec+"] Valor ["+registros[capital]+"] Ramo ["+r+"]")
		if(!valorZero(registros[capital])){
			try{
				limparCamposInput('//*[@id="CoberturasBean$lineasCoberturas['+c+'].capitales[0].capital"]')
				preencheXpath('//*[@id="CoberturasBean$lineasCoberturas['+c+'].capitales[0].capital"]', ajusteValorCoberturas(registros[capital]), 0);

				limparCamposInput(premio(r, c))
				preencheXpath(premio(r, c), ajusteValorCoberturas(registros[primerRec]), 0);
				commandTAB(premio(r, c))
			} catch (Exception e){}
		}
	}

	/**
	 * @author be54955
	 * @param cobertura
	 * @return
	 */
	public static String coberturaIndex(String cobertura){
		String strIndex
		int index
		try{
			int j = 0
			for(int i = 0; i < listOfElements.size(); i++){
				if( listOfElements[i].getText().equals('Coberturas'.toString()) ||
				listOfElements[i].getText().equals('Valor'.toString())      ||
				listOfElements[i].getText().equals('Taxa'.toString())       ||
				listOfElements[i].getText().equals('Prêmio Cob.'.toString())||
				listOfElements[i].getText().equals('Prêmio Anual'.toString()))
				{
				} else {
					//System.out.println("["+j+"] ["+listOfElements[i].getText()+"] ["+cobertura+"]")
					index = j;
					if( listOfElements[i].getText().equals(cobertura.toString())){
						//System.out.println("FOUND ["+j+"] ["+listOfElements[i].getText()+"] ["+cobertura+"]")
						strIndex = String.valueOf(index)
						break
					}
					j++
				}
			}
		} catch (Exception e){}
		System.out.println("\nINDEX ["+strIndex+"] COBERTURA ["+cobertura+"]\n")
		return strIndex
	}

	/**
	 * @author be54955
	 * @param cobertura
	 * @return
	 */
	public static String coberturaIndexEndosso(String cobertura){
		String strIndex
		int index
		try{
			int j = 0
			for(int i = 0; i < listOfElements.size(); i++){
				if( listOfElements[i].getText().equals('Coberturas'.toString()) ||
				listOfElements[i].getText().equals('Valor'.toString())      ||
				listOfElements[i].getText().equals('Taxa'.toString())       ||
				listOfElements[i].getText().equals('Prêmio Cob.'.toString())||
				listOfElements[i].getText().equals('Prêmio Anual'.toString())||
				listOfElements[i].getText().equals('Op.'.toString()) )
				{
				} else {
					//System.out.println("["+j+"] ["+listOfElements[i].getText()+"] ["+cobertura+"]")
					index = j;
					if( listOfElements[i].getText().equals(cobertura.toString())){
						//System.out.println("FOUND ["+j+"] ["+listOfElements[i].getText()+"] ["+cobertura+"]")
						strIndex = String.valueOf(index)
						break
					}
					j++
				}
			}
		} catch (Exception e){}
		System.out.println("\nINDEX ["+strIndex+"] COBERTURA ["+cobertura+"]\n")
		return strIndex
	}

	/**
	 * @author be54955
	 * @param v
	 * @return
	 */
	public static boolean valorZero(String v){
		boolean b = false
		if(v.equals('0')){
			b = true
		} else {
			String nStr
			nStr = v.replaceAll(',','')
			Integer number = Integer.valueOf(nStr);
			if(number==0){
				b = true
			}
		}
		return b
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
	 * @param registros
	 * @return
	 */
	public static outrasOperacoesEndosso(List<String> registros, int r){
		System.out.println()
		//Outras Opções
		clickXpath('//*[@id="tipoDeSeleccion2"]', 0);
		//Apolice
		clickXpath('//*[@id="polSusep_SUSEP"]', 0);
		preencheXpath('//*[@id="polSusep_SUSEP"]', formatteApolice(registros[r]), 0);
		commandTAB('//*[@id="polSusep_SUSEP"]')
		//Item
		commandTAB('//*[@id="polSusep_Item"]')
		//Proposta
		commandTAB('//*[@id="proyecto"]')
	}

	/**
	 * @author be54955
	 * @param a
	 * @return
	 */
	public static String formatteApolice(String a){
		String n
		try{
			n=a.replaceAll("[-,/,' ']",'')
		} catch (Exception e){}
		return n
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
	 * @param forma
	 * @return
	 */
	public static formaPagamentoTabelaDePrecos(String forma){
		switch (forma){
			case "À Vista ":
				clickXpath('//*[@id="ParrillaBean$modalidades[0]rad"]', 2);
				break;
			case "Parcelado":
				clickXpath('//*[@id="ParrillaBean$modalidades[1]rad"]', 2);
				break;
		}
	}

	/**
	 * @author be54955	
	 * @param dt
	 * @return
	 */
	public static String formataData(String dt){
		String NdtFormat = dt.replace('.', '')
		return NdtFormat
	}

	/**
	 * @author be54955
	 * @param v
	 * @return
	 */
	public static String ajusteValorCoberturas(String v){
		String b
		String nStr = v.replaceAll(',','.')
		double d = Double.parseDouble(nStr);
		String nStr2 = String.valueOf(d)
		b = nStr2.replace('.',',')
		return b
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
	 * @param n
	 * @return
	 */
	public static String formateNumero(String n){
		String b
		int result = Integer.parseInt(n);
		b = String.valueOf(result)
		return b
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean alerta(List<String> registros, int delay, String ramo){
		boolean b=true
		if(delay>0){
			WebUI.delay(delay);
		}

		try{
			registros[72] = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 0)
			if(alertListBlocList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 0))){
				b = false
			}
			System.out.println()
		} catch (Exception e){}

		//HOME
		if(b.equals(false)){
			try{
				//	clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
			} catch (Exception e){}
		}

		return b
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean alertaDeContinue(List<String> registros, int delay){
		boolean b=false
		if(delay>0){
			WebUI.delay(delay);
		}
		try{
			if(alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 0))){
				b = true
			}
			System.out.println()
		} catch (Exception e){}
		return b
	}

	/**
	 * @author be54955
	 * @param tpl
	 * @return
	 */
	private static String tplog(String tpl){
		String tp
		for(int i = 1; i <= datatplog.getRowNumbers(); i++) {
			System.out.println('['+datatplog.getValue(1, i).toString().trim()+'] ['+datatplog.getValue(2, i).toString().trim()+']')
			if(datatplog.getValue(1, i).toString().trim().equals(tpl)){
				tp = datatplog.getValue(2, i).toString().trim()
				break
			}
		}
		return tp
	}

	/**
	 * @author be54955
	 * @param v
	 * @param d
	 * @return
	 */
	public static String ajustaDiaPagamento(String v, String d){
		String nValue
		try{
			int n = Integer.parseInt(d)
			if(n == 0){
				nValue = v.substring(0, 2)
			} else {
				nValue = d
			}
		} catch (Exception e){

		}
		return nValue
	}

	/**
	 * 
	 * @param ramo
	 * @return
	 */
	public static String premioManual(String ramo){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('1240', '//*[@id="OpcionesCapitalesBean$primerRecibManual"]');
		urls.put('1241', '//*[@id="OpcionesCapitalesBean$primerRecibManual"]');
		urls.put('1249', '//*[@id="OpcionesCapitalesBean$primerRecibManual"]');
		urls.put('2026', '//*[@id="OpcionesCapitalesBean$primerRecibManual"]');
		urls.put('2027', '//*[@id="OpcionesCapitalesBean$primerRecibManual"]');
		urls.put('2028', '//*[@id="OpcionesCapitalesBean$primerRecibManual"]');
		return urls.get(ramo.toString());
	}

	/**
	 * 
	 * @param remo
	 * @param i
	 * @return
	 */
	public static String premio(String ramo, String c){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('1240', '//*[@id="CoberturasBean$lineasCoberturas['+c+'].capitales[0].primerRec"]');
		urls.put('1241', '//*[@id="CoberturasBean$lineasCoberturas['+c+'].capitales[0].primerRec"]');
		urls.put('1249', '//*[@id="CoberturasBean$lineasCoberturas['+c+'].capitales[0].primerRec"]');
		urls.put('2026', '//*[@id="CoberturasBean$lineasCoberturas['+c+'].capitales[0].primerRec"]');
		urls.put('2027', '//*[@id="CoberturasBean$lineasCoberturas['+c+'].capitales[0].primerRec"]');
		urls.put('2028', '//*[@id="CoberturasBean$lineasCoberturas['+c+'].capitales[0].primerRec"]');
		return urls.get(ramo.toString());
	}

	/**
	 * @author be54955
	 * @param c
	 * @return
	 */
	public static String verificaComissaoValorZero(String c){
		String comissao
		if(valorZero(c)){
			comissao = '0,01'
		} else {
			comissao = c
		}
		return comissao
	}

}

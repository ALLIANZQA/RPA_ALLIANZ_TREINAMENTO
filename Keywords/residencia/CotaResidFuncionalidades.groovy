package residencia

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
import auxiliares.ExtractDates

public class CotaResidFuncionalidades extends rpaCommands{

	private static String path
	private static String alerta
	private static TestData data
	private static TestData datatplog
	private static String testDataPath = 'Data Files/residencia/CotaResidData'
	private static int rollCheck = 26
	private static String confirmacao
	private static String tplog = 'Data Files/MigracaoDeDados/TP_LOGRADOURO_DATA'

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		path = data.sourceUrl
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
			if (data.getValue(cellStatus, i).equals('Calcular')) {
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

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static String retornaAnbiente(String aba){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('PRD','IntranetProdução');
		urls.put('INT','IntranetTransacional');
		return urls.get(aba.toString());
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
	 * @param operativa
	 * @return
	 */
	public static selecionarOperativa(String operativa){
		selecionaItemLista(operativa, '//*[@id="codOperativa"]', 'option')
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
	 * @param aba
	 * @param tipoSelecao
	 * @param a
	 * @return
	 */
	public static fluxoCotaResidFuncionalidades(String aba, String tipoSelecao, String a){
		fluxoCompletoCotaResidFuncionalidades(aba, tipoSelecao,  a)
		reprocessaFalhas(aba,tipoSelecao,a)
		enviarEmailCotaResid(aba,tipoSelecao)
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
			initTPLogDataFiles()
			if(validaDados(rollCheck)){
				fluxoCompletoCotaResidFuncionalidades(aba, tipoSelecao,  a)
				reprocessaFalhas(aba,ramo,tipoSelecao, a)
			}
		} catch (Exception e){}
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailCotaResid(String tipoAba, String tipoSelecao){
		System.out.println()
		initDataFiles()
		initTPLogDataFiles()
		try{
			if(!validaDados(rollCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Residencia\\ConfiguracoesResidencia.txt";
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "Cota_Resid.xlsx"
				System.out.println(anexos[0]+" - "+anexos[1])
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos, tipoAba)
			}
		} catch (Exception e){
		}
	}

	/**
	 * @author be54955
	 */
	public static fluxoCompletoCotaResidFuncionalidades(String aba, String tipoSelecao, String a){
		try{
			initDataFiles()
			initTPLogDataFiles()
			System.out.println()
			if(validaDados(rollCheck)){
				acessoAmbiente(aba,a)
				WebUI.delay(20);
				menuEmissao()
				WebUI.waitForPageLoad(15)
				alteraJanejaGenerica(1, 15)
				System.out.println()
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					alerta=""
					confirmacao=""
					if (data.getValue(rollCheck, i).equals('Calcular')) {
						System.out.println("["+i+"] ["+tipoSelecao+"]")
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						if(calcular(registros, aba,tipoSelecao, a)) {
							try{
								//	System.err.println("["+registros[25]+"]\n["+registros[26]+"]\n["+registros[27]+"]")
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[25], 25, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[26], 26, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[27]+" "+ExtractDates.dataAtualizada(), 27, aba);
							} catch (Exception e){}
						} else {
							try{
								//		System.err.println("["+registros[25]+"]\n["+registros[26]+"]\n["+registros[27]+"]")
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[25], 25, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[27]+" "+ExtractDates.dataAtualizada(), 27, aba);
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
	 * @param tipoSelecao
	 * @param a
	 * @return
	 */
	public static boolean calcular(List<String> registros, String aba,String tipoSelecao, String a){
		boolean b
		System.out.println("Emissão Ramo ["+tipoSelecao+"]" )
		WebUI.waitForPageLoad(20)
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0))){
			/**
			 * TELA - MENU PRODUÇÃO
			 */
			System.out.println('[Menu Produção]')
			//Tipo de Seleção : Emissão / Outras Operações
			selecionaTipoSelecao(tipoSelecao,'//*[@id="datosSeleccionContent"]/table[1]/tbody/tr',"td")
			WebUI.delay(5);
			//Agrupamento
			selecionaItemLista('Residência Digital','//*[@id="agrupacion"]',"option")
			//Produto
			selecionaItemLista('Residência Digital','//*[@id="producto"]',"option")
			//Corretor
			limparCamposInput('//*[@id="mediador"]')
			preencheXpath('//*[@id="mediador"]', registros[0], 0);
			WebUI.delay(5);
			alerta = pegaMSGAlerta()
			System.err.println(alerta)
			if(alertListBlocList(alerta)){
				b = false
				registros[27] = alerta
				registros[25] = 'Não Calculado'
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
						registros[27] = alerta
						registros[25] = 'Não Calculado'
						fechaAlerta()
						//HOME
						clickXpath('//*[@id="imgOptMenuBackHome"]', 2);
					} else{
						selecionarOperativa('Emitir apólice')
						//AVANÇAR
						selecionaItemLista('Avançar', '//*[@id="seleccionOperativaSectionBar"]/table', 'div')
						WebUI.delay(10);

						try{
							fechaAlerta()
						} catch (Exception e){}

						//D. BÁSICOS
						b = dadosBasicos(registros)
						alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
						System.out.println("ALERTA ["+alerta+"] ["+b+"]")
						if(alertListBlocList(alerta)){
							registros[27] = alerta
							registros[25] = 'Não Calculado'
							b = false
							//HOME
							//	clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
							//Fechar
							clickXpath('//*[@id="lnkTopLink_Close"]', 5);
							try{
								fechaAlerta()
							} catch (Exception e){}
							alteraJanejaGenerica(0, 15)
							menuEmissao()
							WebUI.delay(15);
							alteraJanejaGenerica(1, 15)
							//WebUI.delay(10);
						} else {
							if(b){
								if(dadosRisco(registros)){
									b = true
									if(tabelaDePrecos(registros)){
										b = true
										registros[25] = 'Calculado'
										//HOME
										clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
									} else {
										b = false
									}
								} else {
									b = false
								}
							} else {
								registros[27] = " Tela [D. BÁSICOS] não apresentada"
								registros[25] = 'Não Calculado'
								//HOME
								//clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
								//Fechar
								clickXpath('//*[@id="lnkTopLink_Close"]', 5);
								try{
									fechaAlerta()
								} catch (Exception e){}
								alteraJanejaGenerica(0, 2)
								menuEmissao()
								WebUI.delay(10);
								alteraJanejaGenerica(1, 2)
							}
						}
					}//MSG ALERTA
				} else {
					//SELEÇÃO
					b = false
					registros[27] = "Corretor inexistente"
					registros[25] = 'Calculado'
					//HOME
					//clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
					//Fechar
					clickXpath('//*[@id="lnkTopLink_Close"]', 5);
					try{
						fechaAlerta()
					} catch (Exception e){}
					alteraJanejaGenerica(0, 2)
					menuEmissao()
					WebUI.delay(10);
					alteraJanejaGenerica(1, 2)
				}
			}

			alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
			System.out.println("ALERTA ["+alerta+"] ["+b+"]")
			if(alertListBlocList(alerta)){
				registros[27] = alerta
				registros[25] = 'Não Calculado '
				b = false
				//HOME
				try{
					//clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
					//Fchar
					clickXpath('//*[@id="lnkTopLink_Close"]', 5);
				} catch (Exception e){}

				try{
					fechaAlerta()
				} catch (Exception e){}
				alteraJanejaGenerica(0, 2)
				menuEmissao()
				WebUI.delay(10);
				alteraJanejaGenerica(1, 2)

			}


		} else {
			registros[27] = " Tela [Menu Produção] não apresentada"
			registros[25] = 'Não Calculado'
			WebUI.closeBrowser();
			acessoAmbiente(aba,a)
			WebUI.delay(20);
			menuEmissao()
			WebUI.delay(10);
			alteraJanejaGenerica(1, 2)
			b = false
		}


		alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
		System.out.println("ALERTA ["+alerta+"] ["+b+"]")
		if(alertListBlocList(alerta)){
			registros[27] = alerta
			registros[25] = 'Não Calculado '
			b = false
			WebUI.closeBrowser();
			acessoAmbiente(aba,a)
			WebUI.delay(20);
			menuEmissao()
			WebUI.waitForPageLoad(15)
			alteraJanejaGenerica(1, 15)
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
			//Tipo de Período
			selecionaItemLista(registros[1], '//*[@id="DatosGeneralesBean$tipoPeriodo"]', 'option')
			//WebUI.delay(5);
			//Início de Vigência
			limparCamposInput('//*[@id="DatosGeneralesBean$fechaEfecto"]')
			preencheXpath('//*[@id="DatosGeneralesBean$fechaEfecto"]', formataData(registros[2]), 0);
			//Término Vigência
			clicarEmElementoDaTela('//*[@id="DatosGeneralesBean$fechaTerm"]', 0)
			try{
				limparCamposInput('//*[@id="DatosGeneralesBean$fechaTerm"]')
				preencheXpath('//*[@id="DatosGeneralesBean$fechaTerm"]', formataData(registros[3]), 0);
			} catch (Exception e){}
			//Segurado Isento do IOF
			seguradoIsentoIOF(registros[4])

			//CPF/CNPJ Tomador
			limparCamposInput('//*[@id="IntervinientesBean$nifTomador_doc"]')
			//preencheXpath('//*[@id="IntervinientesBean$nifTomador_doc"]', StringFormatter.formataDocumentoCPFCNPJ(registros[5]), 0);
			preencheXpath('//*[@id="IntervinientesBean$nifTomador_doc"]', registros[5], 0);

			//Nome do Tomador
			preencheXpath('//*[@id="IntervinientesBean$nomFigTomadorValue"]', registros[6], 0);

			try{
				//CPF/CNPJ Segurado
				limparCamposInput('//*[@id="IntervinientesBean$nifAsegurado_doc"]')
				//preencheXpath('//*[@id="IntervinientesBean$nifAsegurado_doc"]', StringFormatter.formataDocumentoCPFCNPJ(registros[5]), 0);
				preencheXpath('//*[@id="IntervinientesBean$nifAsegurado_doc"]', registros[5], 0);

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
				selecionaItemLista(registros[9], '//*[@id="CategoriaRiesgoBean$catRiesgo"]', 'option')
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

			try{
				WebUI.delay(5);
				alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
				System.out.println("ALERTA TO CONTINUE["+alerta+"] ["+b+"]")
				if(alertListContinueList(alerta)){
					//Avançar
					selecionaItemLista( 'Avançar','//*[@id="divAppContent"]/table/tbody/tr[1]/td',"div")
					alerta="";
				}
			}catch (Exception e){}
			b = true

		} else {
			registros[72] = " Tela [D. BÁSICOS] não apresentada"
			registros[73] = 'Calculado'
			b = false
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
	 * @param dt
	 * @return
	 */
	public static String formataData(String dt){
		String NdtFormat = dt.replace('.', '')
		return NdtFormat
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean dadosRisco(List<String> registros){
		System.out.println('[ APPLICATION PAGE P DADOS RISCO RESI DIGIT ]')
		boolean b = false
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 37))){
			try {
				//CEP / Bairro
				preencheXpath('//*[@id="COD_BR"]', registros[10], 0);
				preencheXpath('//*[@id="SUB_COD_BR"]', registros[11], 0);
				clickXpath('//*[@id="COD_BRAjaxLocFinderImg"]', 2);
				//TIPO
				try{
					selecionaItemLista(tplog(registros[13]).toUpperCase(),'//*[@id="DadosRiscoResiDigital$tipoLogradouro"]', 'option')
				} catch (Exception e){
					selecionaItemLista('OUTROS','//*[@id="DadosRiscoResiDigital$tipoLogradouro"]', 'option')
				}
				//logradouro
				limparCamposInput('//*[@id="DadosRiscoResiDigital$logradouro"]')
				preencheXpath('//*[@id="DadosRiscoResiDigital$logradouro"]', registros[14], 0);
				//Numero
				preencheXpath('//*[@id="DadosRiscoResiDigital$numero"]', registros[15], 0);
				//Complemento
				preencheXpath('//*[@id="DadosRiscoResiDigital$complemento"]', registros[16], 0);
				//Valor Garantido
				limparCamposInput('//*[@id="InformacoesComplementares$valorGarantido"]')
				preencheXpath('//*[@id="InformacoesComplementares$valorGarantido"]', ajusteValorGarantido(registros[19]), 0);
				commandTAB('//*[@id="InformacoesComplementares$valorGarantido"]')
				//Tipo de Cobertura
				selecionaItemLista( registros[20],'//*[@id="InformacoesComplementares$tpCobertura"]',"option")
				//Tipo de Assitência
				selecionaItemLista( registros[21],'//*[@id="InformacoesComplementares$tpAssistencia"]',"option")
				//É patrimônio Histórico?
				selecionaTipoSelecao( registros[22],'//*[@id="PatrimonioHistBeanContent"]/table/tbody/tr/td/table',"td")
				//Avançar
				selecionaItemLista( 'Avançar','//*[@id="divAppContent"]/table/tbody/tr[1]/td',"div")
				verificaDadosDeRisco(registros)
				b = true

				try{
					WebUI.delay(5);
					alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
					System.out.println("ALERTA TO CONTINUE["+alerta+"] ["+b+"]")
					if(alertListContinueList(alerta)){
						//Bairoo
						limparCamposInput('//*[@id="DESC_BR"]')
						preencheXpath('//*[@id="DESC_BR"]', registros[12], 0);
						//Tipo
						try{
							selecionaItemLista(tplog(registros[13]).toUpperCase(),'//*[@id="DadosRiscoResiDigital$tipoLogradouro"]', 'option')
						} catch (Exception e){
							selecionaItemLista('OUTROS','//*[@id="DadosRiscoResiDigital$tipoLogradouro"]', 'option')
						}
						//Avançar
						selecionaItemLista( 'Avançar','//*[@id="divAppContent"]/table/tbody/tr[1]/td',"div")
						alerta="";
					}
				}catch (Exception e){}

				try{
					WebUI.delay(5);
					alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
					System.out.println("ALERTA TO CONTINUE["+alerta+"] ["+b+"]")
					if(alertListContinueList(alerta)){
						//Avançar
						selecionaItemLista( 'Avançar','//*[@id="divAppContent"]/table/tbody/tr[1]/td',"div")
						alerta="";
					}
				}catch (Exception e){}
			} catch (Exception e){
				b = false
			}
		} else {
			registros[27] = " Tela [Dados de Risco] não apresentada"
			registros[25] = 'Não Calculado '
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
				//TIPO
				selecionaItemLista(registros[13].toUpperCase(),'//*[@id="DadosRiscoResiDigital$tipoLogradouro"]',"option")
				//Avançar
				selecionaItemLista( 'Avançar','//*[@id="divAppContent"]/table/tbody/tr[1]/td',"div")
			}
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static boolean tabelaDePrecos(List<String> registros){
		System.out.println('[Tabela de preços]')
		boolean b
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).subSequence(0, 16))){
			//% Desc CAP
			selecionaItemListaContains( '0','//*[@id="DescuentosParrillaBean$descCAP"]',"option")
			selecionaItemListaContains( registros[23].toString().replace("%","").trim(),'//*[@id="DescuentosParrillaBean$descCAP"]',"option")
			//Comissão
			limpaCampo('//*[@id="ComisionamientoBean$porcComision"]', 1)
			preencheXpath('//*[@id="ComisionamientoBean$porcComision"]', verificaComissaoValorZero(registros[24]), 0);
			commandTAB('//*[@id="ComisionamientoBean$porcComision"]')
			//Calcular
			clickXpath('//*[@id="btnCalcular"]', 2);
			//Salvar cotação
			selecionaItemLista( 'Salvar Cotação','//*[@id="divAppContent"]/table/tbody/tr[1]/td',"div")
			//Referência Cotação - CPF
			preencheXpath('//*[@id="EstudioBean$refEstudio"]', StringFormatter.formataDocumentoCPFCNPJ(registros[5]), 0);
			//Nome
			preencheXpath('//*[@id="EstudioBean$nombreEstudio"]', registros[6], 0);
			//Telefone
			preencheXpath('//*[@id="EstudioBean$telefonoEstudio"]', registros[7], 0);
			System.out.println()
			//Salvar
			clickXpath('//*[@id="o_2"]', 0);
			WebUI.delay(5);
			registros[27] = pegaMSGAlerta()
			//System.out.println(registros[27])
			fechaAlerta()
			WebUI.delay(5);
			registros[26] = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0).subSequence(34, 43)
			//System.out.println(registros[26])
			b = true
		} else {
			registros[27] = " Tela [TABELA DE PREÇOS] não apresentada"
			registros[25] = 'Não Calculado '
			b = false
		}
		return b
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
	public static boolean alerta(List<String> registros, int delay, String ramo){
		boolean b=true
		if(delay>0){
			WebUI.delay(delay);
		}

		try{
			registros[26] = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 0)
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
	 * @param vg
	 * @return
	 */
	public static String ajusteValorGarantido(String vg){
		String valor
		try{
			valor = vg.toString().replace(".","")
		}catch (Exception e){}
		return valor
	}

	/**
	 * @author be54955
	 * @param tp
	 * @return
	 */
	public static String configuraTipo(String tp){
		String valor
		Map<String, String> tipo = new HashMap<String, String>();
		tipo.put("RUA",'RUA');
		tipo.put("ES",'ESTRADA');
		tipo.put("AL",'ALAMEDA');
		tipo.put("TV",'TRAVESSA');
		tipo.put("AC",'AVENIDA');
		tipo.put("CJ",'CONJUNTO');
		tipo.put("ES",'ESTRADA');

		tipo.put("AR",'ÁREA');
		tipo.put("ES",'ESTRADA');
		tipo.put("ES",'ESTRADA');
		tipo.put("ES",'ESTRADA');
		tipo.put("ES",'ESTRADA');

		return tipo.get(tp);;
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

}

package saude

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

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
import internal.GlobalVariable
import rpacommands.rpaCommands
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.WriteExcelFactory;

public class AtualizaCustoApoliceVigentesFuncionalidades extends rpaCommands {

	private static boolean bProximoReg
	private static boolean bElementPresent
	private static boolean bAlertPresent
	private static String sApoliceSusep
	private static String sObs
	private static String sOperativa
	private static String path
	private static boolean bAlertpresent
	private static int rowNumbers
	private static TestData data
	private static String testDataPath = 'Data Files/Saude/AtualizaCustoApolicesVigentesData'
	private static String sProposta
	private static String sApoliceRenov
	private static String sLog
	private static String sAlerta
	private static boolean checkErrorTelaDadosRisco
	private static boolean checkErrorTelaPacotePersonalizado
	private static boolean checkErrorTelaResumoDados
	private static boolean checkErrorTelaTabelaPremiosPorPlano
	private static boolean checkErrorTelaTabelaPrecos
	private static boolean checkErrorTelaEmissao
	private static boolean checkErrorTelaDCliente
	private static boolean checkErrorTelaClausulas
	private static boolean checkErrorTelaConfirmacaoEmissao
	private static boolean checkErrorTelaDComuns
	private static boolean alerta
	private static boolean topAlertPresent

	public static fluxoCompletoAtualizaCustoApoliceVigentes(String aba){

		if(carregaDados()){
			iniciaAplicacaoMatrizCommands('IntranetProdução');
			realizaLoginIntraNetProducaoCommands(aba);
			menu()
			fluxoAtualizaCustoApoliceVigentes(aba)
			enviarAtualizaCustoApoliceVigentes(aba)
			finalizar()
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean carregaDados(){
		boolean sizeTrue = false
		if(validaExecutados(getData(), 'x', 1)==false){
			if(getData().getRowNumbers()>0){
				sizeTrue = true;
			}
		}
		System.err.println(sizeTrue)
		return sizeTrue
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static TestData getData(){
		return data = findTestData(testDataPath)
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static menu(){
		String xpathProducaoSinistros = '/html/body/div/div/app-root/app-private/app-private-header/nx-epac-header/nx-epac-navigation/div/div[1]/nx-epac-navigation-desktop/div[1]/div/div/ul/li[1]/a/span'
		clickXpath(xpathProducaoSinistros, 5)
		String xpathProduto = '/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul/li[6]/a'
		clickXpath(xpathProduto, 5)
		WebUI.waitForPageLoad(30)i
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()
		System.err.println()
	}

	/**
	 * @author T_DCDias
	 * @param aba
	 * @return
	 */
	public static fluxoAtualizaCustoApoliceVigentes(String aba){
		path = data.sourceUrl

		for (int i = 1; i <=  data.getRowNumbers() ; i++) {
			if (findTestData(testDataPath).getValue('EXECUTAR', i).toLowerCase() == 'x') {
				bProximoReg = false
				sObs = ''

				System.err.println('[ Fluxo Atualiza Custo Apolice Vigentes ]')
				System.err.println()

				'Tela Menu Producao'
				WebUI.switchToWindowTitle('')
				WebUI.waitForPageLoad(30)
				WebUI.click(findTestObject('Object Repository/PageSaude/Page_Menu Producao/input_Outras Operaes_tipoDeSel'))
				WebUI.delay(2)
				WebUI.setText(findTestObject('Object Repository/PageSaude/Page_Menu Producao/input_Aplice_polSusep_SUSEP'), findTestData(testDataPath).getValue('APÓLICE', i))
				WebUI.setText(findTestObject('Object Repository/PageSaude/Page_Menu Producao/input_Item_polSusep_Item'), '0')
				WebUI.delay(2)
				WebUI.click(findTestObject('Object Repository/PageSaude/Page_Menu Producao/div_Selecionar'))
				WebUI.delay(2)

				topAlertPresent = WebUI.waitForAlert(2)
				if (!verificaAlertas()){


					WebUI.selectOptionByLabel(findTestObject('Object Repository/PageSaude/Page_Menu Producao/select_Selecione uma operativa'), 'Endosso de apólice de grupo', false)
					WebUI.click(findTestObject('Object Repository/PageSaude/Page_Menu Producao/div_Avanar'))
					bAlertpresent = WebUI.waitForAlert(2)
					if (bAlertpresent==true) {
						WebUI.acceptAlert()
					}
					WebUI.setText(findTestObject('Object Repository/PageSaude/Page_Menu Producao/textarea_Motivo_motivo'), findTestData(testDataPath).getValue('MOTIVO', i))
					WebUI.click(findTestObject('Object Repository/PageSaude/Page_Menu Producao/div_Avanar_1'))

					'Tela Trem - Dados Basicos'
					WebUI.waitForPageLoad(30)
					WebUI.maximizeWindow()
					WebUI.setText(findTestObject('Object Repository/PageSaude/Page_Tren/input_Data vigncia alter._Dato'), findTestData(testDataPath).getValue('DATA VIGÊNCIA ALTER.', i))
					WebUI.click(findTestObject('Object Repository/PageSaude/Page_Tren/div_Avanar'))

					bElementPresent = identificaAlertas('//*[@id="dataErrorSection"]/table','tr')
					System.err.println("\nbElementPresent ["+bElementPresent+"]\n")
					if(bElementPresent == false){
						alerta = false
						/**
						 * TELA DADOS DO RISCO
						 */
						WebUI.delay(10)
						String telaDadosRisco = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
						System.err.println("tela ["+telaDadosRisco+"]")
						if(telaDadosRisco.contains("RISCO".toString())){
							WebUI.click(findTestObject('Object Repository/PageSaude/Page_Tren/div_Avanar'))
							checkErrorTelaDadosRisco = false
						}


						bElementPresent = identificaAlertas('//*[@id="dataErrorSection"]/table','td')
						System.err.println("\nbElementPresent ["+bElementPresent+"]\n")
						if(bElementPresent == false){
							alerta = false

							/**
							 * TELA PACOTE PERSONALIZADO
							 */
							WebUI.delay(10)
							String telaPacotePersonalizado = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
							System.err.println("tela ["+telaPacotePersonalizado+"]")
							if(telaPacotePersonalizado.contains("Personalizado".toString())){
								WebUI.click(findTestObject('Object Repository/PageSaude/Page_Tren/div_Avanar'))
								checkErrorTelaPacotePersonalizado = false
							}

							/**
							 * TELA RESUMO DOS PACOTES
							 */
							WebUI.delay(10)
							String telaResumoPacotes = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
							System.err.println("tela ["+telaResumoPacotes+"]")
							if(telaResumoPacotes.contains("Pacote".toString())){
								WebUI.click(findTestObject('Object Repository/PageSaude/Page_Tren/div_Avanar'))
								checkErrorTelaResumoDados = false
							}

							WebUI.delay(2)
							bElementPresent = identificaAlertas('//*[@id="dataErrorSection"]/table','td')
							System.err.println("\nbElementPresent ["+bElementPresent+"]\n")
							if(bElementPresent == false){
								alerta = false

								/**
								 * TELA Tabela de premios por plano
								 */
								WebUI.delay(10)
								String telaTabelaPremiosPorPlano = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
								System.err.println("tela ["+telaTabelaPremiosPorPlano+"]")
								if(telaTabelaPremiosPorPlano.contains("PRÊMIOS".toString())){

									try{
										WebUI.delay(5)
										clickXpath('//*[@id="ComissionamentoBean$tipoComissionamento"]', 2)
										tipoComissionamento('Livre')
										WebUI.delay(2)
										limpaCampo('//*[@id="ComissionamentoBean$comissaoValor"]',2)
										preencheXpath('//*[@id="ComissionamentoBean$comissaoValor"]', findTestData(testDataPath).getValue('COMISSAO', i), 2)
										WebUI.delay(2)
										limpaCampo('//*[@id="ComissionamentoBean$agenciamento"]',2)
										preencheXpath('//*[@id="ComissionamentoBean$agenciamento"]', findTestData(testDataPath).getValue('AGENCIAMENTO', i), 2)
										WebUI.delay(2)
										clickXpath('//*[@id="btnCalcular"]', 2)
										WebUI.delay(2)
										WebUI.click(findTestObject('Object Repository/PageSaude/Page_Tren/div_Avanar'))
										checkErrorTelaTabelaPremiosPorPlano = false
									} catch (Exception e){
										WebUI.delay(10)
										preencheXpath('//*[@id="ComissionamentoBean$comissaoValor"]', findTestData(testDataPath).getValue('COMISSAO', i), 2)
										WebUI.delay(2)
										limpaCampo('//*[@id="ComissionamentoBean$agenciamento"]',2)
										preencheXpath('//*[@id="ComissionamentoBean$agenciamento"]', findTestData(testDataPath).getValue('AGENCIAMENTO', i), 2)
										WebUI.delay(2)
										clickXpath('//*[@id="btnCalcular"]', 2)
										WebUI.delay(2)
										WebUI.click(findTestObject('Object Repository/PageSaude/Page_Tren/div_Avanar'))
										checkErrorTelaTabelaPremiosPorPlano = false
									}
								}


								/**
								 * TABELA DE PREÇOS
								 */
								WebUI.delay(10)
								String telaTabelaPrecos = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
								System.err.println("tela ["+telaTabelaPrecos+"]")
								if(telaTabelaPrecos.contains("PREÇOS ".toString())){
									WebUI.delay(5)
									clickXpath('//*[@id="ParrillaBean$btnEmitir"]', 2)
									checkErrorTelaTabelaPrecos = false
								}

								/**
								 * D. EMISSÃO
								 */
								WebUI.delay(10)
								String telaEmissao = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
								System.err.println("tela ["+telaEmissao+"]")
								if(telaEmissao.contains("Emissão".toString())){
									WebUI.delay(5)
									WebUI.click(findTestObject('Object Repository/PageSaude/Page_Tren/div_Avanar'))
									checkErrorTelaEmissao = false
								}

								/**
								 * D. CLIENTE
								 */
								WebUI.delay(10)
								String telaDCliente = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
								System.err.println("tela ["+telaDCliente+"]")
								if(telaDCliente.contains("Cliente".toString())){
									WebUI.delay(2)
									WebUI.click(findTestObject('Object Repository/PageSaude/Page_Tren/div_Avanar'))
									WebUI.delay(2)
									WebUI.click(findTestObject('Object Repository/PageSaude/Page_Tren/div_Avanar'))
									WebUI.delay(2)
									telaListaClientes()
									checkErrorTelaDCliente = false
								}

								/**
								 * D. COMUNS
								 */
								WebUI.delay(10)
								String telaDComuns = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
								System.err.println("tela ["+telaDComuns+"]")
								if(telaDComuns.contains("Comuns".toString())){
									WebUI.delay(5)
									WebUI.click(findTestObject('Object Repository/PageSaude/Page_Tren/div_Avanar'))
									checkErrorTelaDComuns = false
								}


								/**
								 * CLAUSULAS
								 */
								WebUI.delay(10)
								String telaClausulas = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
								System.err.println("tela ["+telaClausulas+"]")
								if(telaClausulas.contains("CLÁUSULAS".toString())){
									WebUI.delay(5)
									WebUI.click(findTestObject('Object Repository/PageSaude/Page_Tren/div_Avanar'))
									checkErrorTelaClausulas = false
								}

								/**
								 * CONFIRMAÇÃO DE EMISSÃO
								 */
								WebUI.delay(10)
								String telaConfirmacaoEmissao = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
								System.err.println("tela ["+telaConfirmacaoEmissao+"]")
								if(telaConfirmacaoEmissao.contains("CONFIRMAÇÃO".toString())){

									WebUI.delay(2)
									WebUI.click(findTestObject('Object Repository/PageSaude/Page_Tren/div_Avanar'))
									WebUI.delay(10)
									sProposta = recuperaValor('//*[@id="CabeceraBean$polizaSusep"]', 'textContent', 2)
									sApoliceRenov = recuperaValor('//*[@id="CabeceraBean$polizaSusepSUSEP"]', 'outerText', 2)
									sLog = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
									sAlerta = ''
									System.err.println('\nsProposta [ '+sProposta+' ]\nsApoliceRenov [ '+sApoliceRenov+' ]\nsLog [ '+sLog+' ]')
									WebUI.delay(2)
									clickXpath('//*[@id="btnValida"]', 2)
									checkErrorTelaConfirmacaoEmissao = false
									WebUI.delay(2)
								}
								salvarEvidencia(i, aba)
								System.err.println()
							} else {
								alerta = true
							}
						} else {
							alerta = true
						}
					} else {
						alerta = true
					}
				} else {
					alerta = true
				}


				if(alerta){
					salvarFalhaAlerta(i, path, aba, sProposta, sApoliceRenov, sAlerta,sLog)
					WebUI.delay(2)
					telaInicial()
					System.err.println()
				}
			}
		}
	}

	/**
	 * @author T_DCDias
	 * @param i
	 * @param aba
	 * @return
	 */
	public static salvarEvidencia(int i, String aba){

		System.err.println('\n['+checkErrorTelaDadosRisco+'] ['+
				checkErrorTelaPacotePersonalizado+'] ['+
				checkErrorTelaResumoDados+'] ['+
				checkErrorTelaTabelaPremiosPorPlano+'] ['+
				checkErrorTelaTabelaPrecos+'] ['+
				checkErrorTelaEmissao+'] ['+
				checkErrorTelaDCliente+'] ['+
				checkErrorTelaClausulas+'] ['+
				checkErrorTelaConfirmacaoEmissao+'] ['+
				checkErrorTelaDComuns+']')

		if(!checkErrorTelaDadosRisco &&
		!checkErrorTelaPacotePersonalizado &&
		!checkErrorTelaResumoDados &&
		!checkErrorTelaTabelaPremiosPorPlano &&
		!checkErrorTelaTabelaPrecos &&
		!checkErrorTelaEmissao &&
		!checkErrorTelaDCliente &&
		!checkErrorTelaClausulas&&
		!checkErrorTelaConfirmacaoEmissao &&
		!checkErrorTelaDComuns) {
			salvarExecutados( i, path, aba, sProposta, sApoliceRenov, sLog)
		} else {

			if(checkErrorTelaDadosRisco){
				sAlerta = "Tela Renovações não apresentada pelo sistema"
				telaInicial()
				finalizar();
			}

			if(checkErrorTelaPacotePersonalizado){
				sAlerta = "Tela Dados de Risco não apresentada pelo sistema"
				telaInicial()
			}

			if(checkErrorTelaResumoDados){
				sAlerta = "Tela Tabela de Preços não apresentada pelo sistema"
				telaInicial()
			}

			if(checkErrorTelaTabelaPremiosPorPlano){
				sAlerta = "Sistema PD indisponível, tente mais tarde"
				telaInicial()
			}

			if(checkErrorTelaTabelaPrecos){
				sAlerta = "Sistema PD indisponível, tente mais tarde"
				telaInicial()
			}

			if(checkErrorTelaEmissao){
				sAlerta = "Sistema PD indisponível, tente mais tarde"
				telaInicial()
			}

			if(checkErrorTelaDCliente){
				sAlerta = "Sistema PD indisponível, tente mais tarde"
				telaInicial()
			}

			if(checkErrorTelaClausulas){
				sAlerta = "Sistema PD indisponível, tente mais tarde"
				telaInicial()
			}

			if(checkErrorTelaConfirmacaoEmissao){
				sAlerta = "Sistema PD indisponível, tente mais tarde"
				telaInicial()
			}

			if(checkErrorTelaDComuns){
				sAlerta = "Sistema PD indisponível, tente mais tarde"
				telaInicial()
			}
			salvarFalha(i, path, aba, sProposta, sApoliceRenov, sAlerta,sLog)
		}
	}

	/**
	 * @author T_DCDias
	 * @param i
	 * @param path
	 * @param aba
	 * @return
	 */
	public static salvarExecutados(int i, String path, String aba, String sProposta,String sApoliceRenov,String sLog){
		i = i-1;
		WriteExcelFactory.updateSheetLine('',i, path,'',0,aba);
		WriteExcelFactory.updateSheetLine('',i, path,'x',1,aba);
		WriteExcelFactory.updateSheetLine('',i, path,sProposta,9,aba);
		WriteExcelFactory.updateSheetLine('',i, path,sApoliceRenov,10,aba);
		WriteExcelFactory.updateSheetLine('',i, path,sLog,12,aba);
	}

	/**
	 * @author T_DCDias
	 * @param i
	 * @param path
	 * @param aba
	 * @return
	 */
	public static salvarFalha(int i, String path, String aba, String sProposta,String sApoliceRenov,String sAlerta,String sLog){
		i = i-1;
		WriteExcelFactory.updateSheetLine('',i, path,sAlerta,11,aba);
	}

	/**
	 * @author T_DCDias
	 * @param i
	 * @param path
	 * @param aba
	 * @return
	 */
	public static salvarFalhaAlerta(int i, String path, String aba, String sProposta,String sApoliceRenov,String sAlerta,String sLog){
		i = i-1;
		WriteExcelFactory.updateSheetLine('',i, path,'',0,aba);
		WriteExcelFactory.updateSheetLine('',i, path,'x',1,aba);
		WriteExcelFactory.updateSheetLine('',i, path,sAlerta,11,aba);
	}

	/**
	 *
	 * @return
	 */
	public static finalizar(){
		'Encerra a operação'
		WebUI.closeWindowIndex(1)
		WebUI.switchToWindowIndex(0)
		WebUI.closeBrowser()
	}

	/**
	 * Voltar a Tela Inicial e Continuar o Processo
	 * @return
	 */
	public static telaInicial(){
		try{
			clickXpath('//*[@id="0"]', 2)
		} catch (Exception e){
			clickXpath('//*[@id="imgOptMenuBackHome"]', 2)
		}
	}


	/**
	 * @author T_DCDias
	 * @return
	 */
	public static telaListaClientes(){
		try{
			if (WebUI.verifyTextPresent('LISTA CLIENTES*', true, FailureHandling.OPTIONAL)) {
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/ListaClientesBeanlistaClientes_0_0'))
				WebUI.waitForPageLoad(30)
			}
		} catch (Exception e){}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static telaEmissaoRealizada(){
		String telaEmissaoRealizada = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
		System.err.println("tela ["+telaEmissaoRealizada+"]")
		System.err.println()
		if(telaEmissaoRealizada.contains("EMISSÃO".toString())){
			WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_EMISSO REALIZADA  1211-AUTO'), 10, FailureHandling.STOP_ON_FAILURE)
			sProposta = WebUI.getText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_propostaFinal'), FailureHandling.CONTINUE_ON_FAILURE)
			sApoliceRenov = WebUI.getText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/label_ApoliceRenovada'), FailureHandling.CONTINUE_ON_FAILURE)
			sLog = WebUI.getText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_EMISSO REALIZADA  1211-AUTO'), FailureHandling.CONTINUE_ON_FAILURE)
			bElementPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_LISTAGEM DE BLOQUEIOS'), 2)
		}
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarAtualizaCustoApoliceVigentes(String tipoAba){
		System.err.println("Recursive method")
		data = getData()
		if(!validaExecutados(data, 'x', 1)){
			iniciaAplicacaoMatrizCommands('IntranetProdução');
			realizaLoginIntraNetProducaoCommands(tipoAba);
			fluxoAtualizaCustoApoliceVigentes(tipoAba)
			enviarAtualizaCustoApoliceVigentes(tipoAba)
		} else{
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Saude\\AtualizaCustoApolicesVigentes\\ConfiguracoesAtualizaCustoApolicesVigentes.txt";
			lerConfigurações(pathTxtEmail)
			String pathAnexos = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Saude\\AtualizaCustoApolicesVigentes\\Anexos.txt"
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination,  FileInput.readFile(pathAnexos), tipoAba )
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static tipoComissionamento(String tipo){
		String xpath = '//*[@id="ComissionamentoBean$tipoComissionamento"]'
		String tag = 'option'
		WebUI.delay(5)
		selecionaListaDeElementos(xpath,tag)
		for(int j = 0; j < listOfElements.size(); j++){
			if(listOfElements.get(j).getText().equals(tipo) ){
				listOfElements.get(j).click()
			}
		}
	}

	/**
	 *
	 * @return
	 */
	public static boolean identificaAlertas(String xpath, String tag ){
		System.err.println('[ Check alerts ]')
		try{
			boolean msg = false
			List<String> listaDeAlertas = new ArrayList<String>()

			StringBuilder strb = new StringBuilder()

			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				listaDeAlertas.add(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr['+i+']/td[2]', 'textContent', 1)+' / ')
			}

			int tam = listaDeAlertas.size()
			System.err.println('tam ['+tam+']')
			if(tam>0){
				for(int j = 0; j < tam; j++){
					strb.append(listaDeAlertas.get(j)+' ')
				}
				msg = true
				sAlerta = strb.toString().replace('atributo nao encontrado','')
			}
			System.err.println('['+msg+'] Alertas ['+sAlerta+']')
			return msg
		} catch (Exception e){
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param xpath
	 * @param tag
	 * @return
	 */
	public static List<String> returnList( String xpath, String tag){
		List<String> list = new ArrayList<String>()
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			list.add(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr['+i+']/td[2]', 'textContent', 1)+' / ')
		}
		return list
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static boolean verificaAlertas(){
		boolean topAlertPresent = WebUI.waitForAlert(2)
		if (topAlertPresent == true) {
			sAlerta = WebUI.getAlertText().toString()
			WebUI.delay(2)
			WebUI.acceptAlert()
		}
		return topAlertPresent
	}
}

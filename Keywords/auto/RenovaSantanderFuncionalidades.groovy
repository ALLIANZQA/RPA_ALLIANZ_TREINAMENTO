package auto

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.Duration
import java.time.Instant
import java.util.List
import org.openqa.selenium.WebElement
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
import org.openqa.selenium.remote.RemoteWebDriver
import rpacommands.rpaCommands
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import org.openqa.selenium.Keys as Keys
import auxiliares.WriteExcelFactory;
import sendEmail.envioDeEmail
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.MenuMatriz
import auxiliares.ExtractDates

import sendEmail.emailDinamico;
import auxiliares.timer;
import auxiliares.GetHost;


/**
 * ATUALIZAÇÃO 22/02/2024
 * @author BE54955
 *
 */
public class RenovaSantanderFuncionalidades extends rpaCommands {

	protected static List<WebElement> listOfElements
	protected static TestData data
	protected static TestData dataSelecionada
	private static TestData dataCap
	//	private static String testDataPath = 'Data Files/Auto/RenovacoesSantanderData/RenovaSantander'
	//	private static String tabCapPath = 'Data Files/Auto/RenovacoesSantanderData/TabCap (1)'
	private static String testDataPath
	private static String tabCapPath
	private static String testDataPathsasam
	private static String tabCapPathsasam

	protected static String path

	protected static String iPrLiqAtlAnt
	protected static String sGrupoSeg
	protected static String iPropCorretor
	protected static String iComissaoCap
	protected static String iDescontoCap

	/**
	 * teste
	 */
	//	protected static String iPrliqAtu
	protected static String dQtdParcelas


	protected static String sdPrliqAtuCalc

	//	protected static double dPrliqAtuCalc
	protected static String sProposta
	protected static String sPacote
	protected static String sApoliceRenov
	protected static String sLog
	private static String sTelefoneCliente
	private static String sEmailCliente
	private static String sNrEndCliente
	private static String sVeicVersao
	protected static boolean bProximoRegistro
	private static boolean bErrorPresent
	private static boolean bAlertPresent
	private static boolean bElementPresent
	protected static String sAlerta
	private static String sDataExpedicao
	private static String sWindowTitle
	private static String sRamo
	private static String sFormaPagamento = 'Débito em conta bancária'
	private static String sEmailVazio = 'nao@informado.com'
	private static String sFoneVazio = '1191111111'
	private static String sNrEndVazio = '0'
	private static String destinatário = "Tomador"
	private static String enderecoEntrega = "Domicilio Habitual"
	private static String sSexo = "Masculino"
	private static String sEstadoCivil = "Solteiro"
	private static String sDTNascimento = "19741010"
	protected static boolean validaTela
	protected static boolean alertaRenovacao
	protected static boolean alertaDadosRisco
	protected static boolean subAlertaDadosRisco
	protected static boolean alertatelaTabelaPrecos
	protected static boolean alertaDadosEmissao
	protected static boolean alertaDadosCliente
	protected static boolean alertaConfirmarPagamento
	protected static boolean alertatelaClausulas
	protected static boolean alertaConfirmacaoEmissao
	protected static boolean alertaEmissaoRealizada
	protected static boolean alertaSistemaPD
	protected static boolean alertaNR
	protected static boolean alertaDadosDeRisco
	protected static String aba = 'RenovacaoSantander';
	protected static List<String> registros
	protected static coringa = true
	protected static String limitePremio
	private static String dataDoDocumento;


	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "RenovaSantander.xlsx"
	private static int cellCheckAlert = 31;
	
	private static List<String> apolices;
	
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(String testDataPath, String tabCapPath ){
		data = findTestData(testDataPath)
		dataCap = findTestData(tabCapPath)
		path = data.sourceUrl
		System.err.println("data - Row Numbers ["+data.getRowNumbers()+"] path ["+path+"] dataCap - Row Numbers ["+dataCap.getRowNumbers()+"]")
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static iniURLDateFiles(){
		testDataPath = 'Data Files/Auto/RenovacoesSantanderData/RenovaSantander'
		tabCapPath = 'Data Files/Auto/RenovacoesSantanderData/TabCap (1)'
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static iniURLDateFilesSasam(){
		testDataPathsasam = 'Data Files/Auto/RenovacoesSantanderData/RenovaSantanderSASAM'
		tabCapPathsasam = 'Data Files/Auto/RenovacoesSantanderData/TabCap (1)'
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static menuRenovaSantander(String a){
		MenuMatriz.subMenuEquals('/*/body/div[1]/main/app-root/app-private/app-private-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div', "span", "Produção & Sinistros")
		String xpathGR =  xpathGestaoRenovacoes(a);
		clickXpath(xpathGR, 5)
		WebUI.waitForPageLoad(30)
		WebUI.switchToWindowTitle('RENOVAÇÕES')
		WebUI.maximizeWindow()
	}

	/**
	 * @author BE54955
	 * @param a
	 * @return
	 */
	public static String xpathGestaoRenovacoes(String a){
		Map<String, String> mes = new HashMap<String, String>();
		mes.put("PRD",'/*/body/div[1]/main/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[2]/div/div[1]/ul/li[1]/a');
		mes.put("DEV",'/*/body/div[1]/main/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[2]/div/div[1]/ul/li[4]/a');
		return mes.get(a);;
	}


	/**
	 * @author T_DCDias
	 * @return
	 */
	public static menuProducaoSantander(){
		WebUI.delay(3)
		MenuMatriz.subMenuEquals('/*/body/div/div/app-root/app-private/app-private-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div/ul', "span", "Produção & Sinistros")
		//Produção
		MenuMatriz.subMenu('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul', "a","Produção")
		//Menu Produção
		WebUI.waitForPageLoad(30)
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()
	}
	/**
	 * @author T_DCDias
	 * @param xpath
	 * @param tag
	 * @return
	 */
	public static foundSelectedValueCombo(String xpath, String tag, String value){
		String nValue = value.replaceAll("\\s+"," ");
		WebUI.delay(5)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements.get(i).getText().equals(nValue)){
				System.err.println("["+i+"] ["+listOfElements.get(i).getText()+"]")
				listOfElements.get(i).click()
				break
			}
		}
	}

	/**
	 * @author T_DCDias
	 * @param xpath
	 * @param tag
	 * @return
	 */
	public static String getSelectedValueCombo(String xpath, String tag){
		WebUI.delay(5)
		String value = "";
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements.get(i).isSelected()){
				value =  listOfElements.get(i).getText()
			}
		}
		return value;
	}

	/**
	 * @author T_DCDias
	 */
	public static selecionaListaDeElementos(String xpath, String tag){
		listOfElements = listaDeElementos(xpath, tag)
	}

	/**
	 * @author T_DCDias
	 * @param xpath
	 * @param tag
	 * Retorna uma lista de elementos
	 * @return
	 */
	public static List<WebElement> listaDeElementos(String xpath, String tag){
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement Table = driver.findElement(By.xpath(xpath))
		List<WebElement> rows_table = Table.findElements(By.tagName(tag))
		return rows_table
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static fechaAlerta(){
		try{
			WebDriver driver = DriverFactory.getWebDriver();
			driver.switchTo().alert().accept();
		}catch (Exception e){
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param xpath
	 * @return
	 */
	public static String retornaValorDoElementoDaTela(String xpath){
		String valor
		WebDriver driver = DriverFactory.getWebDriver()
		valor = driver.findElement(By.xpath(xpath)).getText();
		return valor
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param xpath
	 * @return
	 */
	public static String retornaAtriDoElementoDaTela(String xpath, String atributo){
		String valor
		WebDriver driver = DriverFactory.getWebDriver()
		valor = driver.findElement(By.xpath(xpath)).getAttribute(atributo);
		return valor
	}

	/**
	 * @author T_DCDias
	 * @param valor
	 * @param xpath
	 * @return
	 */
	public static verificaZeroKM(String valor){
		String xpath = '//*[@id="InfVeicBean$zeroKM"]'
		String tag = 'option'
		WebUI.delay(5)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements.get(i).getText().equals(valor)){
				System.err.println("["+i+"] ["+listOfElements.get(i).getText()+"]")
				listOfElements.get(i).click()
				break
			}
		}
	}
	
	
	
	public static fluxoInicialRenovaSantander(String tipoAba,String a,  String[] dataParaExecutar){
		System.out.println()
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		iniURLDateFiles()
		initDataFiles(testDataPath, tabCapPath )
		fluxoCompletoRenovaSantander(tipoAba, a, dataParaExecutar);
		recursivo(tipoAba,a, dataParaExecutar)
		
	    FileInput.atualizaLog(Instant.now().toString(), pathFile)
		List<String> times = rpaCommands.convertToInstant(pathFile);
		String dataInicio = times[0];
		String horaInicial =  times[1];
		timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		aba = 'RenovacaoSantander';
		System.out.println()
		List<String> msgAlertas = new ArrayList<String>()
		msgAlertas.addAll(confrmaData(tipoAba, dataParaExecutar))
		System.out.println("ALERTAS: "+msgAlertas.size())
		
		try{
			enviarEmailSantanderDinamico(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal, msgAlertas );
		  } catch (Exception e){
		}
		
	}
	
	public static recursivo(String tipoAba,String a,  String[] dataParaExecutar){
		System.out.println("*** Recursivo ***");
		initDataFiles(testDataPath, tabCapPath );
		iniURLDateFiles();
		if(validaDados(tipoAba, a, dataParaExecutar)){
		  fluxoCompletoRenovaSantander(tipoAba,a, dataParaExecutar);
		  recursivo(tipoAba,a, dataParaExecutar);
		}
	}

	public static boolean validaDados(String tipoAba,String a,  String[] dataParaExecutar){
	boolean b = false;
	for(int i = 0; i < dataParaExecutar.size(); i++){
		System.err.println('Data ['+i+'] ['+dataParaExecutar[i]+']')
		if(i==0){
			dataDoDocumento = dataParaExecutar[i]
		}
		if(validaDataParaPorcessar(dataParaExecutar[i])){
			b = true;
		}
	}
	return b;
	}
	
	
	
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static fluxoCompletoRenovaSantander(String tipoAba,String a,  String[] dataParaExecutar){
		aba = tipoAba
		//iniURLDateFiles()
		//initDataFiles(testDataPath, tabCapPath )
		try{
			for(int i = 0; i < dataParaExecutar.size(); i++){
				System.err.println('Data ['+i+'] ['+dataParaExecutar[i]+']')
				if(i==0){
					dataDoDocumento = dataParaExecutar[i]
				}
				if(validaDataParaPorcessar(dataParaExecutar[i])){
				System.out.println()
					renovaSantander(tipoAba,a ,dataParaExecutar[i])
					if(!enviarEmailRenovaSantander(tipoAba, dataParaExecutar[i])){
						enviarEmailError("Error", "RenovaSantander" );
					}
				}
			}
		} catch (Exception e){
			fluxoCompletoRenovaSantander(tipoAba, dataParaExecutar)
			//finalizar()
		}
		try{
		  WebUI.closeBrowser()
		  finalizar()
		} catch (Exception e){}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static renovaSantander(String tipoAba,String a ,String dataParaExecutar){
		try{
			iniURLDateFiles()
			//		iniciaAplicacaoMatrizCommands('IntranetProdução');
			acessoAmbiente(tipoAba, a);
			//		realizaLoginIntraNetProducaoCommands(tipoAba);
			initDataFiles(testDataPath, tabCapPath )
			WebUI.delay(5)
			System.err.println()
			menuRenovaSantander(a)
			fluxoRenovaSantander(tipoAba, a, dataParaExecutar)
		} catch (Exception e){}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static fluxoRenovaSantander(String tipoAba, String a, String dataParaExecutar){
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(fluxoSantander(tipoAba), i).toLowerCase().equals('x')) {
				if(auxiliares.ExtractDates.compareDate(dataParaExecutar, data.getValue(fluxoDataSantander(tipoAba), i))){
					limpaAlertas()
					registros = new ArrayList<>();
					lista( registros , i)
					System.err.println("Fluxo Renova Santander ["+i+"] ["+tipoAba+"]")
					bProximoRegistro = false;
					validaTela = false;
					sAlerta = "";
					iPrLiqAtlAnt= "";
					sProposta = "";
					sApoliceRenov = "";
					sLog = "";
					limitePremio = "";
					sPacote = data.getValue(11, i);
					sRamo = registros[14];
					dQtdParcelas = "";
					sdPrliqAtuCalc= "";

					switch (tipoAba){
						case "RenovaSantander":
						telaRenovacoes(i, a, tipoAba)
						break;
						case "RenovaSantanderSASAM":
						RenovaSantanderSasanFuncionalidades.telaMenuProducao(i, tipoAba)
						break;
						case "RenovaSantanderSASAM2":
						RenovaSantanderSasan2Funcionalidades.telaMenuProducaoSasan2(i, tipoAba)
						break;
						case "RenovaEmissaoIncorreta":
						RenovaSantanderSasanFuncionalidades.telaMenuProducao(i, tipoAba)
						break;
					}
				}
			}
		}
	}


	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static int fluxoSantander(String tipoAba){
		int cell
		switch (tipoAba){
			case "RenovaSantander":
			cell = 3
			break;
			case "RenovaSantanderSASAM":
			cell = 3
			break;
			case "RenovaSantanderSASAM2":
			cell = 5
			break;
			case "RenovaEmissaoIncorreta":
			cell = 3
			break;
		}
		return cell
	}

	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static int fluxoDataSantander(String tipoAba){
		int cell
		switch (tipoAba){
			case "RenovaSantander":
			cell = 2
			break;
			case "RenovaSantanderSASAM":
			cell = 2
			break;
			case "RenovaSantanderSASAM2":
			cell = 4
			break;
			case "RenovaEmissaoIncorreta":
			cell = 2
			break;
		}
		return cell
	}

	/**
	 * @author T_DCDias
	 * @param i
	 * @return
	 */
	public static telaRenovacoes(int i, String a, String aba){
		WebUI.delay(15)
		String telaRenovacoes = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
		System.err.println("tela ["+telaRenovacoes+"]")
		System.err.println()
		if(telaRenovacoes.contains("Renovações".toString())){
			alertaRenovacao = false
			filtrar(i)
			errorPresent()
			if(verificaApolicesNR()){

				System.err.println('bProximoRegistro ['+bProximoRegistro+"]")

				if (bProximoRegistro == false) {
					// TELA RENOVAÇÕES
					System.err.println()
					try{
						String siPrLiqAtlAnt = WebUI.getText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/PrLiqAtlAnt'))
						iPrLiqAtlAnt = 'R$ '+siPrLiqAtlAnt;
						System.err.println('Page_Renovaes')
						System.err.println('iPrLiqAtlAnt ['+iPrLiqAtlAnt+"]")
						System.err.println()
					} catch (Exception e){}

					try{
						WebUI.waitForElementVisible(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/input_Status_iconbutton'), 5)
						WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/input_Status_iconbutton'))
					} catch (Exception e){}

					WebUI.waitForPageLoad(5)
					WebUI.switchToWindowTitle('RENOVACIONES')
					System.err.println()
					bAlertPresent = WebUI.waitForAlert(2)
					if (bAlertPresent == true) {
						WebUI.acceptAlert()
					}

					WebUI.waitForPageLoad(10)

					System.err.println(sRamo)
					'Tela Renovações'
					//RAMO
					//WebUI.selectOptionByLabel(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_RENOVACIONES/select_1211-0-0-AUTOMOVEL SEGM'), sRamo, false)
					selecionaItemLista('//*[@id="nuevoRamo"]',"option",ramos(sRamo))
					try{
						WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_RENOVACIONES/div_Renovar com alterao'))
					} catch (Exception e){}

					WebUI.delay(5)
					bAlertPresent = WebUI.waitForAlert(4)
					if (bAlertPresent == true) {
						WebUI.acceptAlert()
					}

					try{
						bErrorPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_RENOVACIONES/td_Deve informar o e-mail'), 2)
					} catch (Exception e){}

					if (bErrorPresent == true) {
						try{
							WebUI.selectOptionByLabel(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_RENOVACIONES/select_Sim    No'), 'Não', false)
							WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_RENOVACIONES/div_Renovar com alterao'))
						} catch (Exception e){}
					}
					System.err.println()
					telaDadosBasicos(i,2)
					telaDadosRisco(i,2)
					alertas(i, aba, a)

				}//TELA RENOVAÇÕES
				System.err.println()
				System.err.println("validaTela ["+validaTela+"]")
				System.err.println("sAlerta ["+sAlerta+"]")
				System.err.println("bProximoRegistro ["+bProximoRegistro+"]")
				System.err.println("Coringa ["+coringa+"]")
				System.err.println("limitePremio ["+limitePremio+"]")
				System.err.println()

				if(!validaTela){
					System.err.println('validaTela ['+validaTela+']')
					System.err.println()
					//sAlerta = ""
					try{
						salvarExecutados(i, path, aba,pacotesNovos(sPacote), iPrLiqAtlAnt, sdPrliqAtuCalc , sProposta, sApoliceRenov,sAlerta+" "+limitePremio,sLog)
					} catch (Exception e){}

					limpaDadosDeEmissao()
					System.err.println()
					WebUI.delay(5)
					telaInicial(aba, a)
					coringa = false
				} else if(!validaTela && !bProximoRegistro) {
					//TELA RENOVAÇÃO NÃO CARREGADA
					System.err.println("!validaTela ["+validaTela+"] !bProximoRegistro ["+!bProximoRegistro+"]")
					System.err.println()
					coringa = false
				}

				if(bProximoRegistro){
					System.err.println('[bProximoRegistro]')

					if(sAlerta.equals(" Apólice a renovar já utilizada")){
						try{
							WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_Fechar'))
						} catch (Exception e){}
						WebUI.acceptAlert()
						WebUI.switchToWindowTitle('RENOVACIONES')
						try{
							WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_RENOVACIONES/div_Voltar'))
						} catch (Exception e){}
						WebUI.delay(2)
						try{
							WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/div_Limpar'))
						} catch (Exception e){}
						WebUI.delay(5)
						coringa = false

					} else {
						WebUI.delay(2)
						try{
							WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/div_Limpar'))
						} catch (Exception e){}
						WebUI.delay(5)
						coringa = false
					}

					//				salvarFalhaProximoRegistro(i, path, aba, sAlerta)  <<<<<<<
				}

				System.err.println("Coringa ["+coringa+"]")
				if(coringa){
					salvarFalhaProximoRegistro(i, path, aba, sAlerta)
					try{
						WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_Fechar'))
					} catch (Exception e){}
					WebUI.acceptAlert()
					alteraJanejaGenerica(1, 2)
					//Fechar
					clickXpath('//*[@id="lnkTopLink_Close"]', 1);
					WebUI.acceptAlert()
					alteraJanejaGenerica(0, 2)
					menuRenovaSantander(a)

				}

			} else {
				alertaRenovacao = false
				alertaDadosRisco = false
				alertatelaTabelaPrecos = false
				alertas(i, aba,a);
			}


		} else {
			alertaRenovacao = true
			alertaDadosRisco = false
			alertatelaTabelaPrecos = false
			alertas(i, aba,a)
		}
	}

	/**
	 * @author T_DCDias
	 * @param i
	 * @return
	 */
	public static alertas(int i, String aba, String a){

		System.err.println("alertas:\nalertaRenovacao ["+alertaRenovacao
			+"]\nalertaDadosRisco ["+alertaDadosRisco
			+"]\nalertatelaTabelaPrecos ["+alertatelaTabelaPrecos
			+"]\nalertaSistemaPD ["+alertaSistemaPD
			+"]\nalertaDadosEmissao ["+alertaDadosEmissao
			+"]\nalertaDadosCliente ["+alertaDadosCliente
			+"]\nalertaConfirmacaoEmissao ["+alertaConfirmacaoEmissao
			+"]\nalertaEmissaoRealizada ["+alertaEmissaoRealizada
			+"]\nsAlerta ["+sAlerta
			+"]\nalertaNR ["+alertaNR
			+"]\nvalidaTela ["+validaTela
			+"]\nalertaDadosDeRisco ["+alertaDadosDeRisco+"] ")
		System.err.println()
		
		if(validaTela){
			try{
				salvarFalha(i, path, aba,sPacote, iPrLiqAtlAnt, sdPrliqAtuCalc , sProposta, sApoliceRenov,sAlerta,sLog)
				salvarFalhaProximoRegistro(i, path, aba, sAlerta)
				coringa = false
			} catch (Exception ){}
		}


		if(alertaDadosDeRisco){
			try{
				//telaInicial(aba)
				//finalizar();
				salvarFalha(i, path, aba,sPacote, iPrLiqAtlAnt, sdPrliqAtuCalc , sProposta, sApoliceRenov,sAlerta,sLog)
				salvarFalhaProximoRegistro(i, path, aba, sAlerta)
				coringa = false
			} catch (Exception ){}
		}

		if(alertaNR){
			try{
				sAlerta = "Status NR"
				//finalizar();
				//salvarFalha(i, path, aba,sPacote, iPrLiqAtlAnt, dPrliqAtuCalc , sProposta, sApoliceRenov,sAlerta,sLog)
				salvarFalhaProximoRegistro(i, path, aba, sAlerta)
				telaInicial(aba, a)
				coringa = false
			} catch (Exception ){}
		}


		if(alertaRenovacao){
			try{
				sAlerta = "Tela Renovações não apresentada pelo sistema"
				telaInicial(aba, a)
				finalizar();
				salvarFalha(i, path, aba,sPacote, iPrLiqAtlAnt, sdPrliqAtuCalc, sProposta, sApoliceRenov,sAlerta,sLog)
				coringa = false
			} catch (Exception ){}
		}

		if(alertaDadosRisco){
			try{
				if(!subAlertaDadosRisco){
					sAlerta = "Tela Dados de Risco não apresentada pelo sistema"
					salvarFalha(i, path, aba,sPacote, iPrLiqAtlAnt, sdPrliqAtuCalc , sProposta, sApoliceRenov,sAlerta,sLog)
					coringa = false
				} else {
					salvarFalhaProximoRegistro( i,  path, aba, sAlerta)
					coringa = false
				}
				telaInicial(aba,a)
			} catch (Exception ){}
		}

		if(alertatelaTabelaPrecos){
			try{
				sAlerta = "Tela Tabela de Preços não apresentada pelo sistema"
				telaInicial(aba, a)
				salvarFalha(i, path, aba,sPacote, iPrLiqAtlAnt, sdPrliqAtuCalc , sProposta, sApoliceRenov,sAlerta,sLog)
				coringa = false
			} catch (Exception ){}
		}

		if(alertaSistemaPD){
			try{
				sAlerta = "Sistema PD indisponível, tente mais tarde"
				telaInicial(aba, a)
				salvarFalha(i, path, aba,sPacote, iPrLiqAtlAnt, sdPrliqAtuCalc , sProposta, sApoliceRenov,sAlerta,sLog)
				coringa = false
			} catch (Exception ){}
		}

		if(alertaDadosEmissao){
			try{
				sAlerta = "Tela Dados de Emissão não apresentada pelo sistema"

				WebUI.delay(15)
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_Fechar'))
				WebUI.acceptAlert()
				WebUI.switchToWindowTitle('RENOVACIONES')
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_RENOVACIONES/div_Voltar'))
				WebUI.delay(2)
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/div_Limpar'))
				WebUI.delay(5)
				salvarFalha(i, path, aba,sPacote, iPrLiqAtlAnt, sdPrliqAtuCalc , sProposta, sApoliceRenov,sAlerta,sLog)
				coringa = false
			} catch (Exception ){}
		}


		if(alertaDadosCliente){
			try{
				sAlerta = "Tela Dados do Cliente não apresentada pelo sistema"

				WebUI.delay(15)
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_Fechar'))
				WebUI.acceptAlert()
				WebUI.switchToWindowTitle('RENOVACIONES')
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_RENOVACIONES/div_Voltar'))
				WebUI.delay(2)
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/div_Limpar'))
				WebUI.delay(5)
				salvarFalha(i, path, aba,sPacote, iPrLiqAtlAnt, sdPrliqAtuCalc , sProposta, sApoliceRenov,sAlerta,sLog)
				coringa = false
			} catch (Exception ){}
		}

		if(alertaConfirmacaoEmissao){
			try{
				sAlerta = "Tela CONFIRMAÇÃO DE EMISSÃO não apresentada pelo sistema"

				WebUI.delay(15)
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_Fechar'))
				WebUI.acceptAlert()
				WebUI.switchToWindowTitle('RENOVACIONES')
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_RENOVACIONES/div_Voltar'))
				WebUI.delay(2)
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/div_Limpar'))
				WebUI.delay(5)
				salvarFalha(i, path, aba,sPacote, iPrLiqAtlAnt, sdPrliqAtuCalc , sProposta, sApoliceRenov,sAlerta,sLog)
				coringa = false
			} catch (Exception ){}
		}

		if(alertaEmissaoRealizada){
			try{
				//sAlerta = "Tela EMISSÃO REALIZADA não apresentada pelo sistema"

				WebUI.delay(15)
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_Fechar'))
				WebUI.acceptAlert()
				WebUI.switchToWindowTitle('RENOVACIONES')
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_RENOVACIONES/div_Voltar'))
				WebUI.delay(2)
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/div_Limpar'))
				WebUI.delay(5)
				//salvarFalha(i, path, aba,sPacote, iPrLiqAtlAnt, dPrliqAtuCalc , sProposta, sApoliceRenov,sAlerta,sLog)
				salvarFalhaProximoRegistro(i, path, aba, sAlerta)
				coringa = false
			} catch (Exception ){}
		}
	}


	/**
	 * @author T_DCDias
	 * @param i
	 * @param path
	 * @param aba
	 * @return
	 */
	public static salvarExecutados(int i, String path, String aba, String sPacote, String iPrLiqAtlAnt, String dPrliqAtuCalc, String sProposta,String sApoliceRenov,String sAlerta,String sLog){

		System.err.println("sPacote "+sPacote
		+"\niPrLiqAtlAnt "+iPrLiqAtlAnt
		+"\ndPrliqAtuCalc "+dPrliqAtuCalc
		+"\nsProposta "+sProposta
		+"\nsApoliceRenov "+sApoliceRenov
		+"\nssAlerta "+sAlerta
		+"\nsLog "+sLog
		+"\nAba "+aba )
		try {
			i = i-1;
			switch (aba){
				case "RenovaSantander":
				WriteExcelFactory.updateSheetLine('',i, path,'',2,aba);
				WriteExcelFactory.updateSheetLine('',i, path,'x',3,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sPacote,26,aba);
				WriteExcelFactory.updateSheetLine('',i, path,iPrLiqAtlAnt,27,aba);
				WriteExcelFactory.updateSheetLine('',i, path,dPrliqAtuCalc,28,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sProposta,29,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sApoliceRenov,30,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sAlerta,31,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sLog,32,aba);
				break;
				case "RenovaSantanderSASAM":
				WriteExcelFactory.updateSheetLine('',i, path,'',2,aba);
				WriteExcelFactory.updateSheetLine('',i, path,'x',3,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sPacote,88,aba);
				WriteExcelFactory.updateSheetLine('',i, path,iPrLiqAtlAnt,89,aba);
				WriteExcelFactory.updateSheetLine('',i, path,dPrliqAtuCalc,90,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sProposta,91,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sApoliceRenov,92,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sAlerta,93,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sLog,94,aba);
				break;
				case "RenovaEmissaoIncorreta":
				WriteExcelFactory.updateSheetLine('',i, path,'',2,aba);
				WriteExcelFactory.updateSheetLine('',i, path,'x',3,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sPacote,78,aba);
				WriteExcelFactory.updateSheetLine('',i, path,iPrLiqAtlAnt,79,aba);
				WriteExcelFactory.updateSheetLine('',i, path,dPrliqAtuCalc,80,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sProposta,81,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sApoliceRenov,82,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sAlerta,83,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sLog,84,aba);
				break;
				case "RenovaSantanderSASAM2":
				WriteExcelFactory.updateSheetLine('',i, path,'',4,aba);
				WriteExcelFactory.updateSheetLine('',i, path,'x',5,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sPacote,535,aba);
				WriteExcelFactory.updateSheetLine('',i, path,iPrLiqAtlAnt,55,aba);
				WriteExcelFactory.updateSheetLine('',i, path,dPrliqAtuCalc,56,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sProposta,57,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sApoliceRenov,58,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sAlerta,59,aba);
				WriteExcelFactory.updateSheetLine('',i, path,sLog,60,aba);
				break;
			}

		} catch (Exception e){}


	}

	/**
	 * @author be54955
	 * @return
	 */
	public static limpaDadosDeEmissao(){
		try{
			sPacote="";
			iPrLiqAtlAnt="";
			sdPrliqAtuCalc="";
			sProposta="";
			sApoliceRenov="";
			sAlerta="";
			sLog="";
		} catch (Exception e){}
	}

	/**
	 * @author T_DCDias
	 * @param i
	 * @param path
	 * @param aba
	 * @return
	 */
	public static salvarFalha(int i, String path, String aba, String sPacote, String iPrLiqAtlAnt, String dPrliqAtuCalc, String sProposta,String sApoliceRenov,String sAlerta,String sLog){
		i = i-1;
		switch (aba){
			case "RenovaSantander":
			WriteExcelFactory.updateSheetLine('',i, path,sAlerta,31,aba);
			break;
			case "RenovaSantanderSASAM":
			WriteExcelFactory.updateSheetLine('',i, path,sAlerta,83,aba);
			break;
			case "RenovaEmissaoIncorreta":
			WriteExcelFactory.updateSheetLine('',i, path,sAlerta,83,aba);
			break;
			case "RenovaSantanderSASAM2":
			WriteExcelFactory.updateSheetLine('',i, path,sAlerta,59,aba);
			break;
		}

	}

	/**
	 * @author T_DCDias
	 * @param i
	 * @param path
	 * @param aba
	 * @return
	 */
	public static salvarFalhaProximoRegistro(int i, String path, String aba,String sAlerta){
		i = i-1;
		System.out.println("salvarFalhaProximoRegistro "+aba)
		switch (aba){
			case "RenovaSantander":
			WriteExcelFactory.updateSheetLine('',i, path,'',2,aba);
			WriteExcelFactory.updateSheetLine('',i, path,'x',3,aba);
			WriteExcelFactory.updateSheetLine('',i, path,"",26,aba);
			WriteExcelFactory.updateSheetLine('',i, path,"",27,aba);
			WriteExcelFactory.updateSheetLine('',i, path,"",28,aba);
			WriteExcelFactory.updateSheetLine('',i, path,"",29,aba);
			WriteExcelFactory.updateSheetLine('',i, path,"",30,aba);
			WriteExcelFactory.updateSheetLine('',i, path,sAlerta,31,aba);
			WriteExcelFactory.updateSheetLine('',i, path,"",32,aba);
			break;
			case "RenovaSantanderSASAM":
			WriteExcelFactory.updateSheetLine('',i, path,'',2,aba);
			WriteExcelFactory.updateSheetLine('',i, path,'x',3,aba);
			WriteExcelFactory.updateSheetLine('',i, path,sAlerta,83,aba);
			break;
			case "RenovaEmissaoIncorreta":
			WriteExcelFactory.updateSheetLine('',i, path,'',2,aba);
			WriteExcelFactory.updateSheetLine('',i, path,'x',3,aba);
			WriteExcelFactory.updateSheetLine('',i, path,sAlerta,83,aba);
			break;
			case "RenovaSantanderSASAM2":
			WriteExcelFactory.updateSheetLine('',i, path,'',4,aba);
			WriteExcelFactory.updateSheetLine('',i, path,'x',5,aba);
			WriteExcelFactory.updateSheetLine('',i, path,sAlerta,59,aba);
			break;
		}

	}

	/**
	 * Voltar a Tela Inicial e Continuar o Processo
	 * @return
	 */
	public static telaInicial(String tipoAba, String a){

		try{
			WebUI.delay(2)
			alteraJanejaGenerica(2, 2)
			fecharJanelaAtual(2)
		} catch (Exception e){}

		try{
			WebUI.delay(2)
			alteraJanejaGenerica(1, 5)
			fecharJanelaAtual(1)
		} catch (Exception e){}
		//WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_Fechar'))
		try{
			WebUI.delay(5)
			WebUI.acceptAlert()
		} catch (Exception ){}

		alteraJanejaGenerica(0, 5)
		switch (tipoAba){
			case "RenovaSantander":
			menuRenovaSantander(a)
			break;
			case "RenovaSantanderSASAM":
			menuRenovaSantander(a)
			break;
			case "RenovaSantanderSASAM2":
			menuProducaoSantander()
			break;
			case "RenovaEmissaoIncorreta":
			menuRenovaSantander(a)
			break;
		}
		WebUI.delay(5)
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static errorPresent(){

		bErrorPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/td_BV'), 2)
		if (bErrorPresent == true) {
			validaTela = false
			bProximoRegistro = true
			sAlerta = 'Status BV. Renovação com problema, terá que ser digitada.'
			sProposta = ''
			sApoliceRenov = ''
			sLog = ''
		}

		bErrorPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/td_No existem dados'), 2)
		if (bErrorPresent == true) {
			validaTela = false
			bProximoRegistro = true
			sAlerta = 'Não existem dados.'
			sProposta = ''
			sApoliceRenov = ''
			sLog = ''
		}

		bErrorPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/td_OK'), 2)
		if (bErrorPresent == true) {
			validaTela = false
			bProximoRegistro = true
			sAlerta = 'Renovação já emitida por outro usuário.'
			sProposta = ''
			sApoliceRenov = ''
			sLog = ''
		}


	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static filtrar(int i){
		try{
			WebUI.setText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/input_poliza_SUSEP'), data.getValue(1, i))
			WebUI.sendKeys(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/input_poliza_SUSEP'), Keys.chord(Keys.TAB))
			WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/div_Filtrar'))
		} catch (Exception e ){ }
			WebUI.delay(5)
			System.out.println();
			
			try {
			   if (WebUI.verifyElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/td_error.PREO9918.9524'),2) ) {
					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/div_Filtrar'))
			   }
			} catch (Exception e){
			  System.err.println('[IDENTIFICADOR DE LUPA]')
			}
				
			if (alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1))) {
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/div_Filtrar'))
			}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static int telaDadosBasicos(int i, int swIndex){
		System.err.println("TELA - D. BÁSICOS - ALERTA - swIndex ["+swIndex+"]")
		try{
			WebUI.switchToWindowIndex(swIndex)
			bErrorPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Menu Produo/td_Poliza de este estudio bloqueada'), 2)
			if (bErrorPresent == true) { //System.err.println('bErrorPresent')
				bProximoRegistro = true
				sAlerta = 'Poliza de este estudio bloqueada'
				sProposta = ''
				sApoliceRenov = ''
				sLog = ''
				WebUI.closeWindowTitle('Menu Produção')
				WebUI.switchToWindowTitle('RENOVAÇÕES')
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_RENOVACIONES/div_Voltar'))
			}
			bErrorPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Error/td_Erro no DB2. Abrir chamado'), 2)
			if (bErrorPresent == true) {
				bProximoRegistro = true
				sAlerta = 'Erro no DB2. Abrir chamado no Service Desk'
				sProposta = ''
				sApoliceRenov = ''
				sLog = ''
			}

			/**
			 * SASAM
			 */
			RenovaSantanderSasanFuncionalidades.preencherTelaDadosBasicos(swIndex)

			/**
			 * SASAM 2
			 */
			RenovaSantanderSasan2Funcionalidades.telaDadosBasicosSasam2()

			try{
				if(identificaAlertas('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent')){
					clickXpath('//*[@id="btnAceptar"]', 2);
					WebUI.delay(5)
				}
			} catch (Exception e){}

		} catch (Exception e){
			System.out.println("Erro tela -  Dados Basicos");
		}
		return i
	}

	/**
	 * @author T_DCDias
	 * @ Tela Dados de Risco
	 * @param i
	 * @return
	 */
	public static telaDadosRisco(int i, int swIndex){
		System.err.println()
		if(bProximoRegistro == false) {


			if(aba.equals("RenovaSantander") ){

				/**
				 * Valida a tela D. Bsicos e avança para Dados de Risco
				 */
				//WebUI.switchToWindowTitle('Tren')
				WebUI.switchToWindowIndex(swIndex)
				WebUI.maximizeWindow()
				try{
					WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_D. Bsicos  1211-AUTOMVEL 21'), 10, FailureHandling.STOP_ON_FAILURE)
				} catch (Exception e){}
				try{
					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
				} catch (Exception e){}

			}
			/**
			 * Tela Dados de Risco
			 */
			String telaDadosDeRisco = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
			System.err.println("\ntelaDadosRisco - tela ["+telaDadosDeRisco+"]")
			if(telaDadosDeRisco.contains("DADOS DE RISCO".toString())){
				WebUI.delay(5)
				RenovaSantanderSasanFuncionalidades.preencherTelaDadosRisco(swIndex)

				RenovaSantanderSasan2Funcionalidades.preencherTelaDadosRiscoSasam2(swIndex)

				if(aba.equals("RenovaEmissaoIncorreta") && bProximoRegistro.equals(false)){
					try{
						sAlerta = recuperaValor( '//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 2)
						System.err.println("sAlerta ["+sAlerta+"]")
						WebUI.delay(1)
						if( sAlerta.contains(" Restrição técnica para o condutor".toString()) ||
						sAlerta.contains(" Tipo de renovação divergente".toString()) ||
						sAlerta.contains(" Valor de Franquia não encontrado".toString()) ||
						sAlerta.contains(" Número apólice anterior obrigatório".toString()) ||
						sAlerta.contains(" Filial Anterior Obrigatório".toString()) ||
						sAlerta.contains(" Ano Modelo sem Aceitação".toString()) ||
						sAlerta.contains("Final de vigência divergente da apólice anterior".toString()))
						{
							validaTela = true
							alertaRenovacao = false
							alertaDadosRisco = true
							alertatelaTabelaPrecos = false
							alertaSistemaPD = false
							alertaDadosEmissao = false
							alertaDadosCliente = false
							alertaConfirmacaoEmissao = false
							alertaEmissaoRealizada = false
							bProximoRegistro = true
						}
					} catch (Exception e){}
				}

				//validaTela = true
				alertaDadosRisco = false
				try{
					WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/td_DADOS DE RISCO  1211-AUTOMV'), 10, FailureHandling.STOP_ON_FAILURE)
				} catch (Exception e){}

				//Placa em branco
				if(aba.equals("RenovaSantander")){
					validaPlaca()
				}

				//Proposta Corretor
				if(aba.equals("RenovaSantander")){
					iPropCorretor = data.getValue(5, i)
					System.err.println(iPropCorretor)
					String xpathIPropCorretor ='//*[@id="AutoComparaSantanderBean$propostaCorretor"]'
					limparCamposInput(xpathIPropCorretor)
					WebUI.delay(2)
					escreverCamposInput(xpathIPropCorretor, iPropCorretor)
					System.err.println()
				}

				//Grupo Segurado
				if(aba.equals("RenovaSantander")){
					sGrupoSeg = data.getValue(9, i)
					System.err.println(sGrupoSeg)
					selecionarDadosDeRisco(sGrupoSeg)
					System.err.println()
				}

				//Grupo Segurado
				if(aba.equals("RenovaSantanderSASAM2")){
					sGrupoSeg = data.getValue(9, i)
					System.err.println(sGrupoSeg)
				}

				if(aba.equals("RenovaSantander") || aba.equals("RenovaSantanderSASAM")|| aba.equals("RenovaSantanderSASAM2")){
					for (int i2 = 1; i2 <= dataCap.getRowNumbers(); i2++) {
						System.err.println("["+i2+"] ["+dataCap.getValue(2, i2)+"]")
						if (dataCap.getValue(2, i2) == sGrupoSeg) {
							iComissaoCap = dataCap.getValue(5,i2)
							iDescontoCap = dataCap.getValue(6,i2)
							break;
						}
					}
				} else if(aba.equals("RenovaEmissaoIncorreta")){
					iComissaoCap = registros[81]
					iDescontoCap = registros[80]
				}

				System.err.println("["+iComissaoCap+"] ["+iDescontoCap+"] ["+sGrupoSeg+"] ["+sPacote+"] ["+bProximoRegistro+"]")

				WebUI.delay(2);
				if(aba.equals("RenovaSantander")){
					System.err.println(sPacote)
					//Desativado 26/02/2024
					//selecionaAssistencia(plano(sPacote))
					try{
						//Desativado 26/02/2024
						//	selecionaCarroResenrva(diasCarroResenva(sPacote))
					} catch (Exception e){}
				
					
					try{
						WebUI.delay(2);
						WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
						WebUI.delay(15)
					} catch (Exception e){}
					
					try{
						//Veículo possui kit gás instalado?
						String alertakitGas = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]/b', 'textContent', 5)
						if(alertListContinueList(alertakitGas)){
							selecionaItemLista('//*[@id="InfVeicBean$veicPossKitGas"]','option', 'Não' )
							WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
							WebUI.delay(15)
						}
					}catch (Exception e){}
					

					String msgTela2 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr[2]/td[2]', 'innerText', 1)
					System.err.println(msgTela2)
					if(alertListBlocList(msgTela2) ){
						msgTela2 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
						System.err.println(msgTela2)
						if(alertListBlocList(msgTela2) ){
							//Ajuste de placa
							try{
								if(alertListAjustList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerHTML', 1))){
									validaTabelaPlaca()
									WebUI.delay(2)
								}
							}catch (Exception e){}


							try{
								if(identificaAlertas('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerHTML')){
									if(sAlerta.contains("Campo 'Finalidade de Uso' é obrigatório.")){
										//Finalidade de Uso
										selecionaItemLista('//*[@id="InfVeicBean$finalidadeUso"]','option', 'Utilização Empresarial' )
										WebUI.delay(2);
										//Aceitar
										WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
										WebUI.delay(15)
									}
								}
							} catch (Exception e){}
							WebUI.delay(5);
							try{
								if(identificaAlertas('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerHTML')){
									WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
									WebUI.delay(15)
								}
							} catch (Exception e){}

						} else {
							String campo1 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]', 'innerText', 1)
							System.err.println(campo1);
							String campo2 = msgTela2;
							msgTela2 = ""
							msgTela2 = campo1+" "+campo2;
							System.err.println(msgTela2)
							sAlerta=""
							sAlerta = campo2
							System.err.println(sAlerta)
							alertaDadosDeRisco = true;
							alertaNR = false;
							alertaSistemaPD = false
							alertaEmissaoRealizada = false
							alertaConfirmacaoEmissao = false
							alertatelaClausulas = false
							alertaConfirmarPagamento = false
							alertaDadosCliente = false
							alertaDadosEmissao = false
							alertatelaTabelaPrecos = false
							alertaDadosRisco = false
							alertaRenovacao = false
							validaTela = false
							return i
						}
					}  else {
						String campo1 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]', 'innerText', 1)
						System.err.println(campo1);
						String campo2 = msgTela2;
						msgTela2 = ""
						msgTela2 = campo1+" "+campo2;
						System.err.println(msgTela2)
						sAlerta=""
						sAlerta = campo2
						System.err.println(sAlerta)
						alertaDadosDeRisco = true;
						alertaNR = false;
						alertaSistemaPD = false
						alertaEmissaoRealizada = false
						alertaConfirmacaoEmissao = false
						alertatelaClausulas = false
						alertaConfirmarPagamento = false
						alertaDadosCliente = false
						alertaDadosEmissao = false
						alertatelaTabelaPrecos = false
						alertaDadosRisco = false
						alertaRenovacao = false
						validaTela = false
						return i
					}

				}//END


				String msgTela1 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]', 'innerText', 1)
				if(alertListBlocList(msgTela1.replaceAll("[0-9]", "").trim())){

					String msgTela = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
					String msgTela2 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr[2]/td[2]', 'innerText', 1)
					System.err.println(msgTela+"\n"+msgTela2)
					if(alertListBlocList(msgTela2)){

						if(alertListBlocList(msgTela)){

							if(aba.equals("RenovaSantanderSASAM2")){
								if(alertListAjustList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerHTML', 1))){
									validaTabelaPlaca()
									WebUI.delay(2)
								}

								try{
									if( alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', "innerText", 2))){
										WebUI.delay(5)
										WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
										WebUI.delay(15)
										WebUI.delay(5)
										WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
										WebUI.delay(15)
									}
								}catch (Exception e){}

							}

							if(aba.equals("RenovaSantander")){


								try{
									validaTabelaChassi()
								} catch (Exception e){}
								WebUI.delay(2)

								try{
									ajusteCampoNecessario()
								} catch (Exception e){}

								WebUI.delay(10)
								try{
									clickXpath(selecionaPacote(sPacote), 3)
								} catch (Exception e){
									sAlerta = 'Pacote não selecionado.'
								}

								System.err.println('[0] ['+bProximoRegistro+"]")

								try{
									bErrorPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/td_A verso informada no corres'), 5)
									if (bErrorPresent == true) {
										WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/img_Chassi_consultarChassi'))
										WebUI.delay(1)
										WebUI.selectOptionByLabel(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/select_0KM'), 'Não', false)
										WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
									}
								} catch (Exception e) {}

								try{
									bErrorPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/td_Aplice a renovar j utilizada'), 5)
									if (bErrorPresent == true) {
										validaTela = false
										bProximoRegistro = true
										sAlerta = "Apólice a renovar já utilizada"
										sProposta = ''
										sApoliceRenov = ''
										sLog = ''
									}
								} catch (Exception e){}

								System.err.println('[1] ['+bProximoRegistro+"]")

								try{
									bErrorPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/td_Classe de bnus divergente'), 2, FailureHandling.OPTIONAL)
									if (bErrorPresent == true) {
										validaTela = false
										bProximoRegistro = true
										sAlerta = "Classe de bônus divergente"
										sProposta = ''
										sApoliceRenov = ''
										sLog = ''
									}
								} catch (Exception e){}

								System.err.println('[2] ['+bProximoRegistro+"]")

								try{
									bErrorPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/td_Nome alterado conforme a receita federal favor avaliar'), 5)
									if (bErrorPresent == true) {
										WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
									}
								} catch (Exception e){}

							} //END

							System.err.println('[3] ['+bProximoRegistro+"]")

							sAlerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
							if(sAlerta.contains(" Final de vigência divergente da apólice anterior".toString())){
								bProximoRegistro = true
							}


							if (bProximoRegistro == false) { //Proximo Registro

								String sistemapd = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
								System.err.println("sistemapd [ "+sistemapd+" ]")
								System.err.println("")
								if(!sistemapd.contains("Sistema".toString())){
									verifyElementtelaDadosRisco(sistemapd); System.err.println("verifyElementtelaDadosRisco...")
									telaTabelaPreços(i, aba);           System.err.println("telaTabelaPreços...")
									telaDadosEmissao(i, swIndex);           System.err.println("telaDadosEmissao...")
									propostaBloqueada();           System.err.println("propostaBloqueada...")

								} else {
									alertaSistemaPD = true
									alertaEmissaoRealizada = false
									alertaConfirmacaoEmissao = false
									alertatelaClausulas = false
									alertaConfirmarPagamento = false
									alertaDadosCliente = false
									alertaDadosEmissao = false
									alertatelaTabelaPrecos = false
									alertaDadosRisco = false
									alertaRenovacao = false
									validaTela = false
									return i
								}
							}//Proximo Registro
						} else { // MSG1
							subAlertaDadosRisco = false
							alertaDadosRisco = false
							alertaRenovacao = false
							validaTela = true
							sAlerta = msgTela
							return i
						}

					} else { // MSG2
						subAlertaDadosRisco = false
						alertaDadosRisco = false
						alertaRenovacao = false
						validaTela = true
						sAlerta = msgTela2
						return i
					}

				} else {
					subAlertaDadosRisco = false
					alertaDadosRisco = false
					alertaRenovacao = false
					validaTela = true
					//		sAlerta = msgTela1.replaceAll("[0-9]", "").trim()
					return i
				}


				/// novo ajuste


			} else {
				alertaDadosRisco = true
				alertaRenovacao = false
				validaTela = false
				subAlertaDadosRisco = false
				return i
			}
		} //Tela Dados de Risco
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static ajusteCampoNecessario(){
		try{
			WebUI.delay(2)
			String xpathEmpresa = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]', 'innerText', 2)

			if(xpathEmpresa.contains('Empresa'.toString())){
				clickXpath('//*[@id="AutoComparaSantanderBean$codEmpreSantander"]', 1)
				WebUI.delay(10)
				selecionaListaDeElementos('//*[@id="AutoComparaSantanderBean$codEmpreSantander"]','option')
				for(int i = 0; i < listOfElements.size(); i++){
					System.err.println('['+i+'] ['+listOfElements.get(i).getText()+']')
					if(listOfElements.get(i).getText().contains('SANTANDER'.toString())){
						System.err.println('click')
						listOfElements.get(i).click()
						clickXpath('//*[@id="AutoComparaSantanderBean$codEmpreSantander"]', 1)
						System.err.println('click to close list')
						break
					}
				}
			}
			WebUI.delay(2);
			WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
			WebUI.delay(2)
		} catch (Exception e){}
	}


	/**
	 * @author T_DCDias
	 * @return
	 */
	public static verifyElementtelaDadosRisco(String alerta){
		try{
			if (WebUI.verifyElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/td_Vistoria Prvia Obrigatria'), 3, FailureHandling.OPTIONAL)) {
				WebUI.click(findTestObject('Object Repository/Page_Trem/div_Aceitar'))
			}
			if (WebUI.verifyElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/td_Aceitao Condicionada a Insp'), 3, FailureHandling.OPTIONAL)) {
				WebUI.selectOptionByLabel(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/select_Fipe'),"90", false)
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
			}
			if (WebUI.verifyElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/td_Inspeo Veicular obrigatria'), 3, FailureHandling.OPTIONAL)) {
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
			}
			if (WebUI.verifyElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/td_A placa informada no corres'), 3, FailureHandling.OPTIONAL)) {
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/img_Chassi_consultarChassi'))
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
			}
			if(alerta.contains(" Restrição técnica para o segurado")){
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
			}
		} catch (Exception e){}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static propostaBloqueada(){
		if(bElementPresent == true) {
			//sAlerta = 'Proposta bloqueada.'
			sAlerta = 'Bloqueio: 715 - Vistoria Prévia Presencial Obrigatória.'
		}
	}

	/**
	 * @author T_DCDias
	 * Tela Tabela de Preços
	 * @param i
	 * @return
	 */
	public static telaTabelaPreços(int i, String aba){
		WebUI.delay(20)
		String telaTabelaPrecos = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
		System.err.println("telaTabelaPreços - tela ["+telaTabelaPrecos+"] validaTela ["+validaTela+"] Aba ["+aba+"]")

		System.out.println('iDescontoCap ['+iDescontoCap+'] iComissaoCap ['+iComissaoCap+'] sFormaPagamento[ '+sFormaPagamento+']')

		if(telaTabelaPrecos.contains("TABELA DE PREÇOS".toString()) && validaTela.equals(false)){

			if(aba.equals("RenovaEmissaoIncorreta")){
				clickXpath(selecionaPacote(sPacote), 3)
			}

			String valor
			//validaTela = true
			alertatelaTabelaPrecos = false
			alertaSistemaPD = false
			WebUI.delay(2)
			try{
				WebUI.selectOptionByLabel(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/select_0     1     2     3'),iDescontoCap,false)
			} catch (Exception e){}
			WebUI.delay(2)
			tipoComissionamento("Livre")
			WebUI.delay(2)
			try{
				WebUI.setText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/input_ComisionamientoBeanporcC'),iComissaoCap)
			} catch (Exception e){}
			try{
				WebUI.sendKeys(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/input_ComisionamientoBeanporcC'), Keys.chord(Keys.TAB))
			} catch (Exception e){}

			if(aba.equals("RenovaSantanderSASAM")){
				//CALCULAR
				clickXpath('//*[@id="btnCalcular"]', 1)
				WebUI.delay(2)
				//PARCELADO
				clickXpath('//*[@id="ParrillaBean$modalidades[1]rad"]', 1)
				WebUI.delay(2)
				WebUI.selectOptionByLabel(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/select_Selecione    Dbito em c'),sFormaPagamento,false)
				//Dia do pagamento
				preencheXpath('//*[@id="ParrillaBean$diaPago"]', registros[53], 1);
				//CALCULAR
				clickXpath('//*[@id="btnCalcular"]', 1)
				WebUI.delay(5)
				WebUI.selectOptionByLabel(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/select_1    2    3    4    5') ,registros[51],false)
				dQtdParcelas =registros[51]
				WebUI.delay(2)
				clickXpath(selecionaPacote(sPacote), 3)
				WebUI.delay(2)
			}


			if(aba.equals("RenovaSantanderSASAM2")){
				//CALCULAR
				clickXpath('//*[@id="btnCalcular"]', 1)
				WebUI.delay(2)
				//PARCELADO
				clickXpath('//*[@id="ParrillaBean$modalidades[1]rad"]', 1)
				WebUI.delay(2)
				//Forma de pagamento
				WebUI.selectOptionByLabel(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/select_Selecione    Dbito em c'),sFormaPagamento,false)
				//Dia do Pagamento
				validaDiaPagamento(recuperaValor('//*[@id="ParrillaBean$diaPago"]', "defaultValue", 2), registros[3], '//*[@id="ParrillaBean$diaPago"]')
				//Parcelas
				dQtdParcelas = registros[19]
				WebUI.selectOptionByLabel(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/select_1    2    3    4    5') ,registros[19],false)
				WebUI.delay(2)
				clickXpath(selecionaPacote(registros[20]), 3)
				WebUI.delay(2)
				//Emitir
				clickXpath('//*[@id="ParrillaBean$btnEmitir"]', 1)
				WebUI.delay(5)
				fechaAlerta();

				if( alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', "innerText", 2))){
					//Emitir
					clickXpath('//*[@id="ParrillaBean$btnEmitir"]', 1)
					WebUI.delay(5)
					fechaAlerta();
				}


			}

			if(aba.equals("RenovaEmissaoIncorreta")){
				//CALCULAR
				clickXpath('//*[@id="btnCalcular"]', 1)
				WebUI.delay(5)
				//Dia do pagamento
				preencheXpath('//*[@id="ParrillaBean$diaPago"]', registros[53], 1);
				WebUI.delay(5)
				//CAPTURA VALOR AVISTA
				valor = recuperaPremioTabelaPrecos()
				WebUI.delay(5)
				//IC GLOBAL
				preencheXpath('//*[@id="PrimaTotalParrillaBean$icglobal"]', calculoICGlobal(valor, registros[82]), 1);
				WebUI.delay(15)
				//FORMA DE PAGAMENTO
				selecionaItemLista('//*[@id="ParrillaBean$primRecibo"]', 'option', registros[52])
				//CALCULAR
				clickXpath('//*[@id="btnCalcular"]', 1)
				//CAPTURA VALOR AVISTA APÓS CALCULO DO IC GLOBAL
				valor = recuperaPremioTabelaPrecos()
				//PARCELADO
				clickXpath('//*[@id="ParrillaBean$modalidades[1]rad"]', 1)
				WebUI.delay(2)
				//PARCELAS
				WebUI.selectOptionByLabel(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/select_1    2    3    4    5') ,dQtdParcelas,false)
				WebUI.delay(2)
			}

			if(aba.equals("RenovaEmissaoIncorreta")){
				if(calculoDeLimite(valor, registros[82], registros[92])){
					System.err.println("["+sAlerta+"]")
					validaTela = true
					alertaRenovacao = false
					alertaDadosRisco = true
					alertatelaTabelaPrecos = false
					alertaSistemaPD = false
					alertaDadosEmissao = false
					alertaDadosCliente = false
					alertaConfirmacaoEmissao = false
					alertaEmissaoRealizada = false
					bProximoRegistro = true
					coringa = true
				}
			}


			if(aba.equals("RenovaSantander")){


				try{
					//Dia do Pagamento
					validaDiaPagamentoSantander(recuperaValor('//*[@id="ParrillaBean$diaPago"]', "defaultValue", 2), registros[1], '//*[@id="ParrillaBean$diaPago"]')
				} catch (Exception e){}

				//CALCULAR
				clickXpath('//*[@id="btnCalcular"]', 1)
				WebUI.delay(5)

				System.err.println()

				/**
				 * Atualização : 28/02/2024
				 * Este método não contempla adicional de juros
				 */
				sdPrliqAtuCalc = capturaValorParcela(sPacote);

				System.err.println("sdPrliqAtuCalc ["+sdPrliqAtuCalc+"]")

				//PARCELADO
				clickXpath('//*[@id="ParrillaBean$modalidades[1]rad"]', 1)
				try{
					WebUI.selectOptionByLabel(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/select_Selecione    Dbito em c'),sFormaPagamento,false)
				} catch (Exception e){}
				try{
					WebUI.selectOptionByLabel(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/select_1    2    3    4    5') ,data.getValue(10, i),false)
					dQtdParcelas = data.getValue(10, i)
				} catch (Exception e){}
				
				
			}

			if(!aba.equals("RenovaSantanderSASAM2")){
				'Verifica Pacote'
				System.err.println('Pacote informado ['+pacotesNovos(sPacote)+"] Pacote Selecionado ["+ verificaPacoteSelecionado()+"] dQt de Parcelas ["+dQtdParcelas+"]")


				String msgTela2 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
				System.err.println(msgTela2)
				if(alertListBlocList(msgTela2) ){


					if(pacotesNovos(sPacote).contains(verificaPacoteSelecionado()) || pacotesNovos(sPacote).contains(verificaPacoteSelecionadoOpt()) ){

						if(dQtdParcelas.equals("1")){
							try{
								clickXpath('//*[@id="btnCalcular"]', 1)
								WebUI.delay(10)
							} catch (Exception e){}
						}

						try{
							WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Emitir'))
						} catch (Exception e){}
						WebUI.acceptAlert()
						WebUI.waitForPageLoad(30)

						try{
							if(identificaAlertas('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerHTML')){
								WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Emitir'))
								WebUI.acceptAlert()
								WebUI.waitForPageLoad(30)
							}
						} catch (Exception e){}

						try{
							if(alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', "innerText", 2))){
								//Emitir
								WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Emitir'))
								WebUI.acceptAlert()
								WebUI.waitForPageLoad(30)
							}
						} catch (Exception e){}

					} else {
						alertatelaTabelaPrecos = true
						alertaDadosEmissao = false
						alertatelaTabelaPrecos = true
						alertaDadosRisco = false
						alertaRenovacao = false
						validaTela = false
						alertaSistemaPD = false
						sAlerta = "Pacote divergente"
					}


				} else {

					alertatelaTabelaPrecos = true
					alertaDadosRisco = false
					alertaRenovacao = false
					validaTela = false
					alertaSistemaPD = false
					System.err.println(msgTela2)
					sAlerta=""
					sAlerta = msgTela2
				}

			}

		} else {
			alertatelaTabelaPrecos = true
			alertaDadosRisco = false
			alertaRenovacao = false
			validaTela = false
			alertaSistemaPD = false
			return i
		}
	}



	/**
	 * *********************************************
	 */

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static String verificaPacoteSelecionado(){
		String pack1="";
		String pack2="";
		String pacote
		String xpath = '//*[@id="blParrillaCellContent"]'
		String tag = 'input'
		WebUI.delay(5)
		selecionaListaDeElementos(xpath,tag)
		for(int j = 0; j < listOfElements.size(); j++){
			//	System.err.println("["+j+"] ["+listOfElements.get(j).getAttribute("id")+"] ["+listOfElements.get(j).getAttribute("checked")+"]")
			if(listOfElements.get(j).getAttribute("checked").equals('true') ){
				System.err.println("["+j+"] ["+listOfElements.get(j).getAttribute("id")+"] ["+listOfElements.get(j).getAttribute("checked")+"]")
				pacote = pacotesIndex(j);; 
			} 
		}
		return pacote
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static String verificaPacoteSelecionadoOpt(){
		String pack1="";
		String pack2="";
		String pacote
		String xpath = '//*[@id="blParrillaCellContent"]'
		String tag = 'input'
		WebUI.delay(5)
		selecionaListaDeElementos(xpath,tag)
		for(int j = 0; j < listOfElements.size(); j++){
			//	System.err.println("["+j+"] ["+listOfElements.get(j).getAttribute("id")+"] ["+listOfElements.get(j).getAttribute("checked")+"]")
			if(listOfElements.get(j).getAttribute("checked").equals('true') ){
				System.err.println("["+j+"] ["+listOfElements.get(j).getAttribute("id")+"] ["+listOfElements.get(j).getAttribute("checked")+"]")
				pacote = pacotesIndexOpt(j);;
			}
		}
		return pacote
	}

	/**
	 * plano(String pacote)
	 * @param assistencia
	 * @return
	 */
	public static selecionaAssistencia(String assistencia){
		String xpath = '//*[@id="ServicosBean$codTpAssist"]'
		String tag ="option";
		clickXpath(xpath, 2)
		WebUI.delay(5)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().equals(assistencia)){
				System.err.println("["+i+"] ["+tag+"] ["+listOfElements.get(i).getText()+"]")
				listOfElements[i].click()
				break
			}
		}
	}

	/**
	 * diasCarroResenva(String reserva)
	 * @param carroResenrva
	 * @return
	 */
	public static selecionaCarroResenrva(String carroResenrva){
		String xpath = '//*[@id="ServicosBean$codCarroReserva"]'
		String tag ="option";
		clickXpath(xpath, 2)
		WebUI.delay(5)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().equals(carroResenrva)){
				System.err.println("["+i+"] ["+tag+"] ["+listOfElements.get(i).getText()+"]")
				listOfElements[i].click()
				break
			}
		}
	}

	/**
	 * @author ARAntunes
	 * @param reserva
	 * @return
	 */
	public static String diasCarroResenva(String reserva){
		Map<String, String> dias = new HashMap<String, String>()
		dias.put('Essencial', 'Não contratado');
		dias.put('Compacto 1', '15 dias');
		dias.put('Básico', '15 dias');
		dias.put('Ampliado', '15 dias');
		dias.put('Compacto 2', '15 dias');
		dias.put('Ampliado 1', '30 dias');
		dias.put('Completo', '30 dias');
		dias.put('Ampliado 2', '30 dias');
		dias.put('Especial', '30 dias');
		dias.put('Master', '30 dias');
		dias.put('Exclusivo', '30 dias');

		return dias.get(reserva.toString());
	}

	/**
	 * @author ARAntunes
	 * @param pacote
	 * @return
	 */
	public static String plano(String pacote){
		Map<String, String> planos = new HashMap<String, String>()
		planos.put('Roubo e Furto', 'Roubo e Furto');
		planos.put('Essencial', 'Plano 1');
		planos.put('Compacto 1', 'Plano 3');
		planos.put('Básico', 'Plano 3');
		planos.put('Ampliado', 'Plano 3');
		planos.put('Compacto 2', 'Plano 3');
		planos.put('Ampliado 1', 'Plano 3');
		planos.put('Completo', 'Plano3');
		planos.put('Ampliado 2', 'Plano 3');
		planos.put('Especial', 'Plano 3');
		planos.put('Master', 'Plano 3');
		planos.put('Exclusivo', 'Plano 3');

		return planos.get(pacote.toString());
	}


	/**
	 * @author ARAntunes
	 * @param pacote
	 * @return
	 */
	public static String pacotesNovos(String pacote){
		Map<String, String> pacotes = new HashMap<String, String>()
		pacotes.put('Roubo e Furto', 'Roubo e Furto');
		pacotes.put('Essencial', 'Essencial');
		pacotes.put('Compacto 1', 'Básico');
		pacotes.put('Básico', 'Básico');
		pacotes.put('Ampliado', 'Ampliado');
		pacotes.put('Compacto 2', 'Ampliado');
		pacotes.put('Ampliado 1', 'Completo');
		pacotes.put('Completo', 'Completo');
		pacotes.put('Ampliado 2', 'Master');
		pacotes.put('Especial', 'Master');
		pacotes.put('Master', 'Master');
		pacotes.put('Exclusivo', 'Exclusivo');

		return pacotes.get(pacote.toString());
	}

	/**
	 * *********************************************
	 */



	/**
	 * @author T_DCDias
	 * Tela D. Emissão
	 * @param i
	 * @return
	 */
	public static telaDadosEmissao(int i, int swIndex){

		String telaDadosEmissao = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
		System.err.println("telaDadosEmissao - tela ["+telaDadosEmissao+"] validaTela ["+validaTela+"]")
		if(telaDadosEmissao.contains("Emissão".toString()) || telaDadosEmissao.contains("EMISSÃO".toString())){
			if(validaTela.equals(false)){
				validaTela = false
				alertaDadosEmissao = false
				alertaSistemaPD = false
				sAlerta =''
				boolean fluxoTelaDadosEmissao = true

				if (aba.equals("RenovaEmissaoIncorreta")) {

					if(registros[91].toString().equals("Titular")){
						//É TITULAR - SIM
						clickXpath('//*[@id="DatosBancariosBean$esTitular"]', 2);
						WebUI.delay(2)
						//TIPO DE CONTA
						selecionaItemLista('//*[@id="DatosBancariosBean$ccc_cuenta_typeAccount"]',"option",registros[85])
						//Banco / Agencia / DCO
						selecionaItemLista('//*[@id="DatosBancariosBean$ccc_cuenta_vBank"]',"option",banco(registros[86]))
						WebUI.delay(2)
						//AGENCIA
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vOffice"]', registros[87], 1);
						//DCO
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlOffice"]', registros[88], 1);
						//CONTA
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vAccNumber"]', registros[89], 1);
						//DIGITO CONTA
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlAcc"]', registros[90], 1);
						//confirma cc
						clickXpath('//*[@id="DatosBancariosBean$ccc_cuenta_btnConfirm"]', 1);

						try{
							sAlerta = pegaMSGAlerta()
							System.err.println(sAlerta)
							WebUI.delay(2)
							// if(identificaAlertasPopUp(sAlerta)){
							if(sAlerta.equals("Error: Dados da conta bancária inválidos")){
								validaTela = true
								WebUI.acceptAlert()
								fluxoTelaDadosEmissao = false
							} else {
								validaTela = false
							}

						} catch (Exception e){}

					}
				}

				if (aba.equals("RenovaSantanderSASAM")) {

					if(registros[	62].toString().equals("Titular")){
						//É TITULAR - SIM
						clickXpath('//*[@id="DatosBancariosBean$esTitular"]', 2);
						WebUI.delay(2)
						//TIPO DE CONTA
						selecionaItemLista('//*[@id="DatosBancariosBean$ccc_cuenta_typeAccount"]',"option",registros[56])
						//Banco / Agencia / DCO
						selecionaItemLista('//*[@id="DatosBancariosBean$ccc_cuenta_vBank"]',"option",banco(registros[57]))
						WebUI.delay(2)
						//AGENCIA
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vOffice"]', registros[58], 1);
						//DCO
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlOffice"]', registros[59], 1);
						//CONTA
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vAccNumber"]', registros[60], 1);
						//DIGITO CONTA
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlAcc"]', registros[61], 1);
						//confirma cc
						clickXpath('//*[@id="DatosBancariosBean$ccc_cuenta_btnConfirm"]', 1);

						try{
							sAlerta = pegaMSGAlerta()
							System.err.println(sAlerta)
							WebUI.delay(2)
							// if(identificaAlertasPopUp(sAlerta)){
							if(sAlerta.equals("Error: Dados da conta bancária inválidos")){
								validaTela = true
								WebUI.acceptAlert()
								fluxoTelaDadosEmissao = false
							} else {
								validaTela = false
							}

						} catch (Exception e){}

					}
				}

				if (aba.equals("RenovaSantanderSASAM2")) {
					if(registros[31].toString().equals("T")){
						//É TITULAR - SIM
						clickXpath('//*[@id="DatosBancariosBean$esTitular"]', 2);
						//TITULAR
						WebUI.delay(2)
						//TIPO DE CONTA
						selecionaItemLista('//*[@id="DatosBancariosBean$ccc_cuenta_typeAccount"]',"option",tipoConta(registros[23]) )
						//Banco / Agencia / DCO
						selecionaItemLista('//*[@id="DatosBancariosBean$ccc_cuenta_vBank"]',"option",banco(registros[24]))
						WebUI.delay(2)
						//AGENCIA
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vOffice"]', registros[25], 1);
						//DCO
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlOffice"]', registros[26], 1);
						//CONTA
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vAccNumber"]', registros[27], 1);
						//DIGITO CONTA
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlAcc"]', registros[28], 1);
						//confirma cc
						clickXpath('//*[@id="DatosBancariosBean$ccc_cuenta_btnConfirm"]', 1);

						try{
							sAlerta = pegaMSGAlerta()
							System.err.println(sAlerta)
							WebUI.delay(2)
							// if(identificaAlertasPopUp(sAlerta)){
							if(sAlerta.equals("Error: Dados da conta bancária inválidos")){
								validaTela = true
								WebUI.acceptAlert()
								fluxoTelaDadosEmissao = false
							} else {
								validaTela = false
							}

						} catch (Exception e){}

					} else if(registros[31].toString().equals("N")){


						//NOME TITULAR
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vAccOwner"]', registros[30], 1);
						//CPF
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_IDNumResp_doc"]', registros[29], 1)

						WebUI.delay(2)
						//TIPO DE CONTA
						selecionaItemLista('//*[@id="DatosBancariosBean$ccc_cuenta_typeAccount"]',"option",tipoConta(registros[23]) )
						//Banco / Agencia / DCO
						selecionaItemLista('//*[@id="DatosBancariosBean$ccc_cuenta_vBank"]',"option",banco(registros[24]))
						WebUI.delay(2)
						//AGENCIA
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vOffice"]', registros[25], 1);
						//DCO
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlOffice"]', registros[26], 1);
						//CONTA
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vAccNumber"]', registros[27], 1);
						//DIGITO CONTA
						preencheXpath('//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlAcc"]', registros[28], 1);
						//confirma cc
						clickXpath('//*[@id="DatosBancariosBean$ccc_cuenta_btnConfirm"]', 1);

						try{
							sAlerta = pegaMSGAlerta()
							System.err.println(sAlerta)
							WebUI.delay(2)
							// if(identificaAlertasPopUp(sAlerta)){
							if(sAlerta.equals("Error: Documento principal inválido")){
								validaTela = true
								WebUI.acceptAlert()
								fluxoTelaDadosEmissao = false
							} else {
								validaTela = false
							}

						} catch (Exception e){}
					}

				}


				if(fluxoTelaDadosEmissao){

					selecionaItemLista('//*[@id="DocumentacionBean$envioDoc"]', 'option', destinatário)
					WebUI.delay(2)
					selecionaItemLista('//*[@id="DocumentacionBean$destinatario"]', 'option', enderecoEntrega )
					try{
						WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_D. Emisso  1211-AUTOMVEL 20'), 10, FailureHandling.STOP_ON_FAILURE)
						WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
					} catch (Exception e){}

					System.out.println()
					sAlerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
					String str = sAlerta
					String nSTR = str.replaceAll("[0-9]", "")
					System.out.println("["+sAlerta+"]\n")
					System.out.println("["+nSTR+"]")

					if(alertListBlocList(nSTR)){

						try{
							selecionaItemLista('//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]', "option","Até R\$1.500,00")
						} catch (Exception e){}
						WebUI.delay(5);

						WebUI.delay(5)
						if (WebUI.verifyElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_Campo Placa da tela dados de risco  obrigatrio para emisso'), 2, FailureHandling.OPTIONAL)) {
							sAlerta = "Campo Placa da tela dados de risco é obrigatório para emissão"
							sProposta = ''
							sApoliceRenov = ''
							sLog = ''
							validaTela = true
							alertaRenovacao = false
							alertaDadosRisco = false
							alertatelaTabelaPrecos = false
							alertaSistemaPD = false
							alertaDadosEmissao = false
							alertaDadosCliente = false
							alertaConfirmacaoEmissao = false
							alertaEmissaoRealizada = false
							bProximoRegistro = true
						}

						if (identificaAlertaList('//*[@id="dataErrorSection"]/table/tbody/tr','td')) {
							sAlerta = "Dados de conta corrente não pode ser de um CNPJ como Pagador"
							sProposta = ''
							sApoliceRenov = ''
							sLog = ''
							validaTela = true
							alertaRenovacao = false
							alertaDadosRisco = false
							alertatelaTabelaPrecos = false
							alertaSistemaPD = false
							alertaDadosEmissao = false
							alertaDadosCliente = false
							alertaConfirmacaoEmissao = false
							alertaEmissaoRealizada = false
							bProximoRegistro = true
						}

						String alert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','innerText', 2)
						if(!alertListBlocListVareaveis(StringFormatter.removeNumerosDeStrings(alert))  || !alertListBlocList(alert)  ){
							sAlerta = alert
							sProposta = ''
							sApoliceRenov = ''
							sLog = ''
							validaTela = true
							alertaRenovacao = false
							alertaDadosRisco = false
							alertatelaTabelaPrecos = false
							alertaSistemaPD = false
							alertaDadosEmissao = false
							alertaDadosCliente = false
							alertaConfirmacaoEmissao = false
							alertaEmissaoRealizada = false
							bProximoRegistro = true
						}

						if (bProximoRegistro == false) {
							sAlerta=""


							/**
							 *
							 * TELA DADOS DO CLIENTE
							 *
							 */
							telaDadosCliente(swIndex)
							WebUI.delay(10);
							validaAlertas()

							String msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', "innerText", 2)
							System.err.println("["+msg+"]")
							if(msg.equals("atributo nao encontrado")){
								/**
								 * Opcional
								 */
								telaListaClientes()
								System.err.println()

								/**
								 * Opcional
								 */
								telaDetalhesPessoaisCondutor()
								msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', "innerText", 2)
								System.err.println("["+msg+"]")
								if(msg.equals("atributo nao encontrado")){
									WebUI.delay(10)
									telaDadosComuns()
									telaConfirmarPagamento()
									telaClausulas()
									telaConfirmarPagamento()
									telaConfirmacaoEmissao()
									telaEmissaoRealizada(aba)
									msgEmissaoErronea()
								} else {
									sAlerta = msg
									validaTela = false
									alertaRenovacao = false
									alertaDadosRisco = false
									alertatelaTabelaPrecos = false
									alertaSistemaPD = false
									alertaDadosEmissao = false
									alertaDadosCliente = false
									alertaConfirmacaoEmissao = false
									alertaEmissaoRealizada = false
									bProximoRegistro = true
									coringa = false

								}
							} else {
								sAlerta = msg
								validaTela = false
								alertaRenovacao = false
								alertaDadosRisco = false //
								alertatelaTabelaPrecos = false
								alertaSistemaPD = false
								alertaDadosEmissao = false
								alertaDadosCliente = false
								alertaConfirmacaoEmissao = false
								alertaEmissaoRealizada = false
								bProximoRegistro = true
								coringa = false
							}
						} // D. EMISSÃO
					} else {
						validaTela = false
						alertaRenovacao = false
						alertaDadosRisco = false
						alertatelaTabelaPrecos = false
						alertaSistemaPD = false
						alertaDadosEmissao = false
						alertaDadosCliente = false
						alertaConfirmacaoEmissao = false
						alertaEmissaoRealizada = false
						bProximoRegistro = true
						coringa = false
					}
					//////////AJUSTE
				}
			} else {
				validaTela = false//
				alertaDadosEmissao = false
				alertatelaTabelaPrecos = false
				alertaDadosRisco = false
				alertaRenovacao = false
				return i
			}
		} else {
			alertaDadosEmissao = true
			alertatelaTabelaPrecos = false
			alertaDadosRisco = false
			alertaRenovacao = false
			validaTela = false
			return i
		}

	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static telaDadosCliente(int swIndex){

		String telaDadosCliente = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
		System.err.println("telaDadosCliente - tela ["+telaDadosCliente+"] validaTela ["+validaTela+"] ["+aba+"]")
		System.err.println()

		if(validaTela.equals(false)){
			if(telaDadosCliente.contains("Cliente".toString())){
				validaTela = false
				alertaDadosCliente = false

				verificaTelefones();

				String num = recuperaValor('//*[@id="DomicilioClienteBeanTomador$numero"]', 'value', 1)
				System.err.println("Numero "+num+" "+num.length())
				if(num.length()==0){
					preencheXpath('//*[@id="DomicilioClienteBeanTomador$numero"]','0' , 1);
				}


				RenovaSantanderSasanFuncionalidades.preencherTelaDadosCliente(swIndex)

				/**
				 * SASAM 2
				 */
				RenovaSantanderSasan2Funcionalidades.preencherTelaDadosClienteSasam2Opcional(swIndex)
				// TESTE
				//				try{
				//					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))//
				//				}catch (Exception e){}

				//				validaAlertas()//
				/**
				 * Opvional
				 */
				RenovaSantanderSasan2Funcionalidades.detalhesPessoaisCondutor()
				// TESTE
				//				try{
				//					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))//
				//				}catch (Exception e){}

				//				validaAlertas()//

				//LISTA DE CLIENTES
				/**
				 * Opcional
				 */
				telaListaClientes()

				if(aba.equals("RenovaSantanderSASAM2")){
					/**
					 * SASAM 2
					 */
					RenovaSantanderSasan2Funcionalidades.preencherTelaDadosClienteSasam2(swIndex)//
				}

				// TESTE
				//				try{
				//					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
				//				}catch (Exception e){}

				try{
					WebUI.delay(5)
					selecionaItemLista("Até R\$1.500,00", '//*[@id="OutrosDadosClienteBeanConductor$rendaMensal"]', "option")
				} catch (Exception e){}

				//// TESTE
				//				try{
				//					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
				//				}catch (Exception e){}
				//				try{
				//					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
				//				}catch (Exception e){}
				/////






				//LISTA DE CLIENTES
				/**
				 * Opcional
				 */
				telaListaClientes()

				if(aba.equals("RenovaSantander")){
					try{
						/**
						 * DETALHES PESSOAIS DO CONDUTOR - OPCIONAL
						 *
						 */
						WebUI.delay(2)
						preencheXpath('//*[@id="DadosGeraisClienteBeanConductor$dataNascimento"]', sDTNascimento, 1);
						//SEXO
						selecionaItemLista('//*[@id="OutrosDadosClienteBeanConductor$sexo"]',"option", sSexo)
						//ESTADO CIVIL
						selecionaItemLista('//*[@id="OutrosDadosClienteBeanConductor$estadoCivil"]',"option",sEstadoCivil)
					} catch (Exception e){}


					if(aba.equals("RenovaSantander") || aba.equals("RenovaSantanderSASAM")){
						try{
							bErrorPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_Cdigo de profisso obrigatrio'), 5)
							if (bErrorPresent == true) {
								WebUI.selectOptionByLabel( findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/select_Profissao'), 'Engenheiro', false)
								WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
							}
						} catch (Exception e){}
					} else {
						try{
							WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
						} catch (Exception e){}
					}

					try{
						WebUI.delay(5)
						selecionaItemLista("Até R\$1.500,00", '//*[@id="OutrosDadosClienteBeanConductor$rendaMensal"]', "option")
					} catch (Exception e){}
				}

				/**
				 * Opvional
				 */
				RenovaSantanderSasan2Funcionalidades.detalhesPessoaisCondutor()


				//AVANÇAR E VALIDA ALERTAS
				try{
					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
				}catch (Exception e){}




				//VALIDAE TELEFONES
				System.err.println()
				for(int i = 0; i<3;i++){
					System.err.println(i+" - "+validaMensagemDeAlertaDeTelefones());
				}

				System.err.println()


				validaAlertas()
				WebUI.delay(5)
				/**
				 * Opcional
				 */
				telaListaClientes()

				/**
				 * . Tratamento caso o segurado é diferente do condutor, fazendo com que a tela de cliente repita 2x
				 */
				WebUI.delay(5)

				System.err.println("telaDadosCliente - tela ["+telaDadosCliente+"] validaTela ["+validaTela+"] ["+aba+"]")
				try{
					if(telaDadosCliente.contains("Cliente".toString())){
						WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
					}
				}catch (Exception e){}

				//				String msgTela1 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]', 'innerText', 1)
				//				 if(alertListBlocList(msgTela1.replaceAll("[0-9]", "").trim())){
				//					 clickXpath('//*[@id="DomicilioClienteBeanProprietario1$numero"]', 0);
				//					 preencheXpath('//*[@id="DomicilioClienteBeanProprietario1$numero"]', '0', 0);
				//				 }
				//

				validaAlertas()

				WebUI.delay(5)

				System.err.println();
				//AVANÇAR E VALIDA ALERTAS
				//			try{
				//				fechaAlerta()
				//			}catch (Exception e){}

				WebUI.delay(5)


			} else {
				alertaDadosCliente = true
				alertaDadosEmissao = false
				alertatelaTabelaPrecos = false
				alertaDadosRisco = false
				alertaRenovacao = false
				validaTela = true

				System.err.println("tela ["+telaDadosCliente+"] validaTela ["+validaTela+"] alertaDadosCliente ["+alertaDadosCliente+"]")
				System.err.println()

			}
		}
	}



	public static validaMensagemDeAlertaDeTelefones(){
		WebUI.delay(3)
		System.err.println()
		String msgTel = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
		System.err.println("Tel msg ["+msgTel+"] ")
		String tel = "11911111111";
		switch (msgTel){
			case " Telefone 1 não válido":
			limpaCampo('//*[@id="DomicilioClienteBeanTomador$telefone"]', 1)
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone"]', tel, 1);
			try{
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
			}catch (Exception e){}
			break;
			case " Telefone 2 não válido":
			limpaCampo('//*[@id="DomicilioClienteBeanTomador$telefone2"]', 1)
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone2"]', tel, 1);
			try{
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
			}catch (Exception e){}
			break;
			case " Celular não válido":
			limpaCampo('//*[@id="DomicilioClienteBeanTomador$celular"]', 1)
			preencheXpath('//*[@id="DomicilioClienteBeanTomador$celular"]', tel, 1);
			try{
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
			}catch (Exception e){}
			break;
		}

		try{
			fechaAlerta()
		}catch (Exception e){}
	}







	/**
	 * @author be54955
	 * @return
	 */
	public static validaAlertas(){

		System.out.println()

		if(aba.equals("RenovaSantander")){
			//Numero vazio
			try{
				String msgTela1 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]', 'innerText', 1)
				if(alertListBlocList(msgTela1.replaceAll("[0-9]", "").trim())){
					clickXpath('//*[@id="DomicilioClienteBeanProprietario1$numero"]', 0);
					preencheXpath('//*[@id="DomicilioClienteBeanProprietario1$numero"]', '0', 2);
					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
				}

			}catch (Exception e){}
		}


		if(aba.equals("RenovaSantander") || aba.equals("RenovaSantanderSASAM") || aba.equals("RenovaSantanderSASAM2")){
			try{
				String alertToFound = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr[1]/td[2]', 'textContent', 5)
				System.out.println("["+alertToFound+"]")

				boolean elementoPresent = validaElemento('//*[@id="blOutrosDadosClienteCellContent"]', 'label', 'Natureza' )
				System.err.println("elementoPresent ["+elementoPresent+"]")
				if(elementoPresent == true){
					'Consiste Data Expedição'
					sDataExpedicao = WebUI.getAttribute(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/input_Data Expedio_DadosGerais'),'value')
					if (!sDataExpedicao.toString().contains("/".toString()) || sDataExpedicao != "") {
						WebUI.clearText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/input_Data Expedio_DadosGerais'))
					}
				}

				//Valida camp telefone vazio
				if(campoVazio(recuperaValor('//*[@id="DomicilioClienteBeanTomador$telefone"]', 'defaultValue', 5))){
					'Consiste Telefone'
					WebUI.setText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/input_Telefone_DomicilioClient'), sFoneVazio)
				}

				'Consiste E-mail'
				sEmailCliente = WebUI.getAttribute(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/input_E-mail 1_DomicilioClient'),'value')
				if (sEmailCliente == "") {
					WebUI.setText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/input_E-mail 1_DomicilioClient'), sEmailVazio)
				}

				'Consiste Numero de Endereço vazio'
				sNrEndCliente = WebUI.getAttribute(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/input_Nmero_DomicilioClienteBe'),'value')
				if (sNrEndCliente == "") {
					WebUI.setText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/input_Nmero_DomicilioClienteBe'),sNrEndVazio )
				}

				'Consiste Endereço vazio'
				if(alertToFound.contains(alertListBloc(alertToFound.toString()))){
					'RUA'
					selecionaItemLista('//*[@id="DomicilioClienteBeanTomador$moradatype"]' , 'option',registros[36])
					'LOGRADOURO'
					preencheXpath('//*[@id="DomicilioClienteBeanTomador$moradaaddressPart2"]', registros[37], 1);
					try{
						WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
					}catch (Exception e){}
				}


				"alertListContinue(String alert)"
				if(alertToFound.contains(alertListContinue(alertToFound.toString()))){
					//Lupa
					clickXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostalAjaxLocFinderImg"]', 1);
					try{
						WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
					}catch (Exception e){}
				}


				//recuperaValorSelecionadoNaLista('//*[@id="OutrosDadosClienteBeanTomador$profissao"]', 'option', 'selected')

				'Dados Pessoais do Tomador'
				if (WebUI.verifyElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_DADOS PESSOAIS DO TOMADOR'), 2, FailureHandling.CONTINUE_ON_FAILURE)) {
					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
				}

			} catch (Exception e){}

			if(aba.equals("RenovaEmissaoIncorreta")){
				try{
					'Consiste Numero de Endereço vazio'
					sNrEndCliente = WebUI.getAttribute(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/input_Nmero_DomicilioClienteBe'),'value')
					if (sNrEndCliente == "") {
						WebUI.setText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/input_Nmero_DomicilioClienteBe'),sNrEndVazio )
					}
				} catch (Exception e){}
			}
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
	public static telaDetalhesPessoaisCondutor(){
		try{
			if (WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_DETALHES PESSOAIS DO CONDUT'),5)) {
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
			}
		} catch (Exception e){}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static telaDadosComuns(){
		String telaDadosComuns = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
		System.err.println("telaDadosComuns tela ["+telaDadosComuns+"] validaTela ["+validaTela+"]")
		alertaConfirmacaoEmissao= false
		if(telaDadosComuns.contains("Comuns".toString()) ){
			validaTela = false
			try{
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
			} catch (Exception e){}
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static telaConfirmarPagamento(){
		System.err.println()
		String telaConfirmarPagamento = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
		System.err.println("telaConfirmarPagamento - tela ["+telaConfirmarPagamento+"] validaTela ["+validaTela+"]")
		System.err.println()
		alertaConfirmarPagamento = false
		alertaConfirmacaoEmissao= false
		if(validaTela.equals(false)){
			if(telaConfirmarPagamento.contains("CONFIRMAR".toString())){
				validaTela = false
				alertaConfirmarPagamento = false

				'Calcular Premio Atual'
				String iPrliqAtu="";
				WebUI.delay(10)

				/**
				 * @author T_DCDias
				 * Trecho desabilitado, valor da parcela está com responsabilidade do método : capturaValorParcela()
				 */

				//iPrliqAtu = WebUI.getText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/td_ValorParcela'))
				//System.err.println("iPrliqAtu ["+iPrliqAtu+"] dQtdParcelas ["+dQtdParcelas+"]")

				//if(!iPrliqAtu.contains("R\$")){
				//	WebUI.delay(15)
				//	System.out.println( "Length "+iPrliqAtu.length()+"");
				//	System.out.println( "Contains "+iPrliqAtu.contains("R\$"));
				//	iPrliqAtu = WebUI.getText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/td_ValorParcela'))
				//	System.err.println("iPrliqAtu ["+iPrliqAtu+"] dQtdParcelas ["+dQtdParcelas+"]")
				//	WebUI.delay(5)
				//}

				//System.err.println()
				//iPrliqAtu = iPrliqAtu.substring(2, iPrliqAtu.length()).trim();
				//iPrliqAtu = iPrliqAtu.replaceAll("\\.", "").replaceAll(",", ".");
				//dPrliqAtuCalc = 0.0;
				//dPrliqAtuCalc = (Double.parseDouble(iPrliqAtu) * Double.parseDouble(dQtdParcelas));

				//if(dPrliqAtuCalc == 0.0){
				//	iPrliqAtu = WebUI.getText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/td_ValorParcela'))
				//	System.err.println("iPrliqAtu ["+iPrliqAtu+"] dQtdParcelas ["+dQtdParcelas+"]")
				//	iPrliqAtu = iPrliqAtu.substring(2, iPrliqAtu.length()).trim();
				//	iPrliqAtu = iPrliqAtu.replaceAll("\\.", "").replaceAll(",", ".");
				//	dPrliqAtuCalc = 0.0;
				//	dPrliqAtuCalc = (Double.parseDouble(iPrliqAtu) * Double.parseDouble(dQtdParcelas));
				//}
				try{
					WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_ConfirmarPagamento'), 10, FailureHandling.STOP_ON_FAILURE)
					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
				} catch (Exception e){}

			} else {
				alertaConfirmarPagamento = false // teste - valor original true
				alertaDadosCliente = false
				alertaDadosEmissao = false
				alertatelaTabelaPrecos = false
				alertaDadosRisco = false
				alertaRenovacao = false
				validaTela = false

				//	System.err.println("tela ["+telaConfirmarPagamento+"] validaTela ["+validaTela+"] alertaDadosCliente ["+alertaConfirmarPagamento+"]")
				//	System.err.println()
			}
		}
	}

	/**
	 * @author T_DCDiass
	 * @return
	 *
	 * Ajuste 19/06/24:
	 *   Inclusão do método "alertListContinueList" para tratamento de mensagens de alerta.
	 */
	public static telaClausulas(){
		WebUI.waitForPageLoad(30)
		String telaClausulas = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
		System.err.println("telaClausulas - tela ["+telaClausulas+"] validaTela ["+validaTela+"]")

		if(validaTela.equals(false)){
			if(telaClausulas.contains("CLÁUSULAS".toString())){
				validaTela = false
				alertatelaClausulas = false
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))


				try{
					if( alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', "innerText", 2))){
						WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))

					}
				} catch (Exception e){
				}


				alertaConfirmacaoEmissao= false
			} else {
				alertatelaClausulas = true
				alertaConfirmarPagamento = false
				alertaDadosCliente = false
				alertaDadosEmissao = false
				alertatelaTabelaPrecos = false
				alertaDadosRisco = false
				alertaRenovacao = false
				validaTela = false
				System.err.println("tela ["+telaClausulas+"] validaTela ["+validaTela+"] alertaDadosCliente ["+alertatelaClausulas+"]")
				System.err.println()
			}
		}

		try{

			WebUI.delay(5)
			String v = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
			if(!alertListBlocList(v)){
				sAlerta = v
				alertatelaClausulas = false
				alertaConfirmarPagamento = false
				alertaDadosCliente = false
				alertaDadosEmissao = false
				alertatelaTabelaPrecos = false
				alertaDadosRisco = false
				alertaRenovacao = false
				validaTela = true
				System.err.println("tela ["+telaClausulas+"] validaTela ["+validaTela+"] alertaDadosCliente ["+sAlerta+"]")
			}
		} catch (Exception e){}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static telaConfirmacaoEmissao(){
		String telaConfirmacaoEmissao = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
		System.err.println("tela ["+telaConfirmacaoEmissao+"] validaTela ["+validaTela+"]")
		alertaConfirmacaoEmissao= false
		if(validaTela.equals(false)){
			if(telaConfirmacaoEmissao.contains("CONFIRMAÇÃO".toString())){
				validaTela = false
				alertaConfirmacaoEmissao = false

				WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_CONFIRMAO DE EMISSO  1211-A'), 10, FailureHandling.STOP_ON_FAILURE)
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/div_Avanar'))
				WebUI.waitForPageLoad(30)

			} else {
				alertaConfirmacaoEmissao = true
				alertatelaClausulas = false
				alertaConfirmarPagamento = false
				alertaDadosCliente = false
				alertaDadosEmissao = false
				alertatelaTabelaPrecos = false
				alertaDadosRisco = false
				alertaRenovacao = false
				validaTela = true

				System.err.println("tela ["+telaConfirmacaoEmissao+"] validaTela ["+validaTela+"] alertaConfirmacaoEmissao ["+alertaConfirmacaoEmissao+"]")
				System.err.println()
			}
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static telaEmissaoRealizada(String tipoAba){

		String telaEmissaoRealizada = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
		System.err.println("tela ["+telaEmissaoRealizada+"] validaTela ["+validaTela+"]")
		System.err.println()

		//		if(validaTela.equals(false)){
		if(telaEmissaoRealizada.contains("EMISSÃO".toString())){

			validaTela = false
			alertaEmissaoRealizada = false
			alertaSistemaPD = false
			sAlerta = ""
			WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_EMISSO REALIZADA  1211-AUTO'), 10, FailureHandling.STOP_ON_FAILURE)
			sProposta = WebUI.getText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_propostaFinal'), FailureHandling.CONTINUE_ON_FAILURE)
			switch (tipoAba){
				case "RenovaSantander":
				sApoliceRenov = recuperaValor(' //*[@id="CabeceraBean$endosoSusepSUSEP"]', 'innerText', 5)
				break;
				case "RenovaSantanderSASAM":
				sApoliceRenov = recuperaValor('//*[@id="CabeceraBean$sucursalSUSEP"]', 'innerText', 5)
				break;
				case "RenovaSantanderSASAM2":
				sApoliceRenov = recuperaValor('//*[@id="CabeceraBean$sucursalSUSEP"]', 'innerText', 5)
				break;
				case "RenovaEmissaoIncorreta":
				sApoliceRenov = recuperaValor(' //*[@id="CabeceraBean$endosoSusepSUSEP"]', 'innerText', 5)
				break;
			}
			sLog = WebUI.getText(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_EMISSO REALIZADA  1211-AUTO'), FailureHandling.CONTINUE_ON_FAILURE)
			bElementPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_LISTAGEM DE BLOQUEIOS'), 2)

		} else {
			alertaEmissaoRealizada = true
			alertaConfirmacaoEmissao = false
			alertatelaClausulas = false
			alertaConfirmarPagamento = false
			alertaDadosCliente = false
			alertaDadosEmissao = false
			alertatelaTabelaPrecos = false
			alertaDadosRisco = false
			alertaRenovacao = false
			validaTela = true
			System.err.println("tela ["+telaEmissaoRealizada+"] validaTela ["+validaTela+"] alertaDadosCliente ["+alertaConfirmacaoEmissao+"]")
			System.err.println()
		}
		//		}
	}


	public static String resetValue(String v){
		String n
		if(v.contains("atributo nao encontrado".toString())){
			n=""
		}
		return n
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static msgEmissaoErronea(){
		String alertaEmissao = recuperaValor('//*[@id="mensajeEmision"]/table/tbody/tr[1]/td/table/tbody/tr/td[2]', 'textContent', 5)
		if(alertaEmissao.contains("EMISSÃO ERRÔNEA".toString())){
			sAlerta = alertaEmissao
			System.err.println("["+sAlerta+"]")
			alertaEmissaoRealizada = false
			alertaConfirmacaoEmissao = false
			alertatelaClausulas = false
			alertaConfirmarPagamento = false
			alertaDadosCliente = false
			alertaDadosEmissao = false
			alertatelaTabelaPrecos = false
			alertaDadosRisco = false
			alertaRenovacao = false
			validaTela = false
		}
	}

	/**
	 * @return
	 *  @author T_DCDias
	 */
	public static selecionarDadosDeRisco(String sGrupoSeg){
		try{
			String xpath = '//*[@id="AutoComparaSantanderBean$codGrupoSantander"]'
			String tag ="option";
			clickXpath(xpath, 2)
			WebUI.delay(5)
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				if(listOfElements[i].getText().equals(sGrupoSeg)){
					System.err.println("["+i+"] ["+tag+"] ["+listOfElements.get(i).getText()+"]")
					listOfElements[i].click()
					break
				}
			}
		} catch (Exception e){
			e.printStackTrace()
		}
	}

	/**
	 * @author T_DCDias
	 */
	public static String pacotesIndex(int pacote){
		Map<Integer, String> pacotes = new HashMap<Integer, String>()
		pacotes.put(2, "Essencial")
		pacotes.put(6, "Básico")
		pacotes.put(10,"Ampliado")
		pacotes.put(14,"Completo")
		pacotes.put(18,"Master")
		pacotes.put(22,"Exclusivo")
		return pacotes.get(pacote);
	}
	
	/**
	 * @author T_DCDias
	 */
	public static String pacotesIndexOpt(int pacote){
		Map<Integer, String> pacotes = new HashMap<Integer, String>()
		pacotes.put(2, "Roubo e Furto")
		pacotes.put(6, "Básico")
		pacotes.put(10,"Ampliado")
		pacotes.put(14,"Completo")
		pacotes.put(18,"Master")
		pacotes.put(22,"Exclusivo")
		return pacotes.get(pacote);
	}


	/**
	 * @author T_DCDias
	 * @return
	 */
	public static String selecionaPacote(String pacote){
		System.err.println("PACOTE PARA SELECIONAR ["+pacote+"] PACOTE ATUAL ["+pacotesNovos(pacote)+"]")
		switch(pacotesNovos(pacote)){
			case 'Essencial':
			return '//*[@id="blParrillaContent"]/table[6]/tbody/tr//*[@id="ParrillaBean$modalidades[0]rad"]'
			break
			case 'Básico':
			return '//*[@id="blParrillaContent"]/table[7]/tbody/tr//*[@id="ParrillaBean$modalidades[1]rad"]'
			break
			case 'Ampliado':
			return '//*[@id="blParrillaContent"]/table[8]/tbody/tr//*[@id="ParrillaBean$modalidades[2]rad"]'
			break
			case 'Completo':
			return '//*[@id="blParrillaContent"]/table[9]/tbody/tr//*[@id="ParrillaBean$modalidades[3]rad"]'
			break
			case 'Master':
			return '//*[@id="blParrillaContent"]/table[10]/tbody/tr//*[@id="ParrillaBean$modalidades[4]rad"]'
			break
			case 'Exclusivo':
			return '//*[@id="blParrillaContent"]/table[11]/tbody/tr//*[@id="ParrillaBean$modalidades[5]rad"]'
			break
		}
	}

	/**
	 * @author T_DCDias
	 * @param xpath
	 * @param tag
	 * @param valor
	 * @return
	 */
	public static boolean validaElemento(String xpath, String tag, String valor ){
		boolean elemento = true
		WebUI.delay(5)
		selecionaListaDeElementos(xpath,tag)
		for(int j = 0; j < listOfElements.size(); j++){
			System.err.println("Found Eleent ["+j+"] ["+listOfElements.get(j).getText()+"]")
			if(listOfElements.get(j).getText().equals(valor) ){
				elemento = false
			}
		}
		System.err.println("Found Eleent ["+elemento+"]")
		return elemento
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static boolean enviarEmailRenovaSantander(String tipoAba, String dataParaExecutar){
		boolean b = false;
		System.err.println("Enviar Email Renova Santander / Recursivo ")
			data = getData()
			if(!validaExecutadosPorData(data, 'x', 3 , dataParaExecutar, 2 )){
				try{
				iniciaAplicacaoMatrizCommands('IntranetProdução');
				realizaLoginIntraNetProducaoCommands(tipoAba);
				WebUI.delay(5)
				menuRenovaSantander()
				fluxoRenovaSantander(tipoAba, dataParaExecutar)
				enviarEmailRenovaSantander(tipoAba, dataParaExecutar)
				} catch (Exception e){}
			} else{
				try{
				pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Auto/RenovaSantander/ConfiguracoesRenovaSantander.txt";
				lerConfigurações(pathTxtEmail)
				String pathAnexos = "C:/Users/Public/RPA_ALLIANZ/Emails/Auto/RenovaSantander/Anexos.txt"
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				//sendEmail.CreateEmail.createEmailSendEmissaoSantander(sender, emaildestination, FileInput.readFile(pathAnexos), tipoAba, dataDoDocumento );
				b = true;
				} catch (Exception e){}
			}
		
		return b
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
		 *
		 */
		public static validaTabelaChassi(){
			System.err.println()
			WebUI.delay(5)
			//if(WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/td_A placa informada no corres'), 10)){
			System.err.println()
			String xpath = "//select[@id='InfVeicBean\$versao']"
			String tag ="option";
			String value = getSelectedValueCombo(xpath, tag)
			value
			System.err.println('get value ['+value+']')
			System.err.println()
			WebUI.delay(2)
			WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/img_Chassi_consultarChassi'))
			WebUI.delay(2)

			System.err.println()

			if(validaListaDeChassi()){
				System.err.println()
				WebUI.switchToWindowIndex(3)
				WebUI.maximizeWindow()
				foundSelectedValueCombo("//tbody[@id='tabelaChassi_tbody']", "td", value)
				fechaAlerta()
				WebUI.delay(2)
				WebUI.switchToWindowIndex(2)
			}

			WebUI.delay(2)
			verificaZeroKM('Não')
			WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
			//	}
		}


		/**
		 * @author T_DCDias
		 * @return
		 * Utilizado quanto a auxência de dados do veículo
		 * SASAM2
		 */
		public static validaTabelaPlaca(){
			System.err.println()
			WebUI.delay(5)
			//		if(WebUI.waitForElementPresent(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/td_A placa informada no corres'), 10)){
			String value = getSelectedValueCombo("//select[@id='InfVeicBean\$versao']", "option")
			System.err.println('get value ['+value+']')
			WebUI.delay(2)
			WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/img_Chassi_consultarChassi'))
			try{
				alteraJanejaGenerica(3, 2)
				selecionaListaDeElementos('//*[@id="tabelaChassi"]','td')
				for(int i = 0; i < listOfElements.size(); i++){
					if(listOfElements[i].getText().contains(value.toString())){
						System.err.println("FOUND CAR ["+i+"] ["+listOfElements[i].getText()+"]")
						listOfElements[i+1].click()
						WebUI.acceptAlert()
						break
					}
				}
			} catch (Exception e){}
			alteraJanejaGenerica(2, 3)
			selecionaItemLista('//*[@id="InfVeicBean$zeroKM"]',"option",'Não')
			WebUI.delay(2)
			WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Trem/div_Aceitar'))
			//		}
		}

		/**
		 * @author T_DCDias
		 * @return
		 */
		public static boolean validaListaDeChassi(){
			boolean lista = false
			try{
				System.err.println()
				WebUI.delay(5)
				WebUI.switchToWindowIndex(3)
				String decodificador = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
				System.err.println("Decodificador ["+decodificador+"]")
				System.err.println()
				if(decodificador.contains("Decodificador de Chassi".toString())){
					lista = true
				}
				System.err.println("Decodificador ["+decodificador+"] lista ["+lista+"]")
				System.err.println()
			} catch (Exception e){
				WebUI.switchToWindowIndex(2)
			}
			return lista
		}

		/**
		 * @author ALZ_ServiceQA
		 * @param date
		 * @return
		 */
		public static boolean validaDataParaPorcessar(String date){
			boolean dataEncontrada = false
			//initDataFiles()
			System.err.println(data.getRowNumbers())
			for (int i = 1; i <= data.getRowNumbers(); i++) {
				if (data.getValue(3, i).equals('x')) {
					if(auxiliares.ExtractDates.compareDate(date, data.getValue(2, i))){
						dataEncontrada = true
						break
					}
				}
			}
			System.err.println('Data Encontrada ['+dataEncontrada+']')
			return dataEncontrada
		}

		/**
		 * @author ALZ_ServiceQA
		 * @param date
		 * @return
		 */
		public static boolean validaDataParaPorcessarSasam(String date){
			boolean dataEncontrada = false
			//initDataFiles()
			System.err.println(data.getRowNumbers())
			for (int i = 1; i <= data.getRowNumbers(); i++) {
				System.out.println("["+i+"] ["+data.getValue(5, i)+"] ["+data.getValue(4, i)+"]")
				if (data.getValue(5, i).equals('x')) {
					if(auxiliares.ExtractDates.compareDate(date, data.getValue(4, i))){
						dataEncontrada = true
						break
					}
				}
			}
			System.out.println('Data Encontrada ['+dataEncontrada+']')
			return dataEncontrada
		}

		/**
		 *@author ALZ_ServiceQA
		 * @return
		 */
		public static boolean identificaAlertas(String xpath, String properties){
			System.err.println('[ Check alerts ]')
			try{
				boolean msg = true
				String str = recuperaValor( xpath, properties, 1)
				str = str.replace('&nbsp;','')
				String alertToFound = str

				//System.err.println('Valor recuperado ['+alertToFound+'] alertListBloc ['+alertListBloc(alertToFound.toString())+'] alertListContinue ['+alertListContinue(alertToFound.toString())+"]")

				System.err.println('Valor recuperado ['+alertToFound+']')

				//if(alertToFound.contains(alertListBloc(alertToFound.toString()))){
				if(!alertListBlocList(alertToFound.toString())){
					System.err.println('alertListBloc [ true ] ['+alertToFound+']')
					msg = false
					sAlerta = str;
					validaTela = false
					bProximoRegistro = true
					alertaRenovacao = false
					alertaDadosRisco = false
					alertatelaTabelaPrecos = false
				} else if (str.contains(alertListContinue(str.toString()))){
					System.err.println('alertListContinue [ true ]')
					msg = true
					sAlerta = str
				} else if(str.contains('atributo nao encontrado'.toString())){
					msg = true
					//sAlerta =""
				}

				System.err.println('-----['+msg+'] Alertas ['+sAlerta+']-----')
				return msg
			} catch (Exception e){
			}
		}

		/**
		 * @author ALZ_ServiceQA
		 * @param alert
		 * @return
		 */
		public static String alertListBloc(String alert){
			System.err.println("motodo alertListBloc ["+alert+"]")
			Map<String, String> alertList = new HashMap<String, String>()
			alertList.put("Erro on epac2020 on QA mode", "Erro on epac2020 on QA mode")
			alertList.put("Apólice a renovar já utilizada", "Apólice a renovar já utilizada")
			alertList.put("Classe de bônus divergente", "Classe de bônus divergente")
			alertList.put(" Dados de conta corrente não pode ser de um CNPJ como Pagador", " Dados de conta corrente não pode ser de um CNPJ como Pagador")
			alertList.put("Nome não retornado no Serviço", "Nome não retornado no Serviço")
			alertList.put(" Política se aplica ao estado errado"," Política se aplica ao estado errado")
			alertList.put("Error: Dados da conta bancária inválidos","Error: Dados da conta bancária inválidos")
			alertList.put(" Restrição técnica para o condutor"," Restrição técnica para o condutor")
			alertList.put(" Restrição técnica para o segurado"," Restrição técnica para o segurado")
			alertList.put(" Endereço é obrigatório"," Endereço é obrigatório" )
			alertList.put(" Tipo da via é obrigatório"," Tipo da via é obrigatório")
			alertList.put(" Condutor deve ser pessoa física"," Condutor deve ser pessoa física")
			return  alertList.get(alert.toString()).toString();
		}

		/**
		 * @author ALZ_ServiceQA
		 * @param alert
		 * @return
		 */
		public static String alertListContinue(String alert){
			System.err.println("motodo alertListContinue ["+alert+"]")
			Map<String, String> alertList = new HashMap<String, String>()
			alertList.put("Tipo de renovação divergente", "Tipo de renovação divergente")
			alertList.put("A placa informada não corresponde ao chassi do veículo.", "A placa informada não corresponde ao chassi do veículo.")
			alertList.put("Campo necessário", "Campo necessário")
			alertList.put("Nome alterado conforme a receita federal, favor avaliar","Nome alterado conforme a receita federal, favor avaliar")
			alertList.put("Nome do condutor alterado conforme a receita federal, favor avaliar","Nome do condutor alterado conforme a receita federal, favor avaliar")
			alertList.put(" Vistoria Prévia Presencial Obrigatória","Vistoria Prévia Presencial Obrigatória")
			alertList.put("Nome do condutor alterado conforme a receita federal, favor avaliar","Nome do condutor alterado conforme a receita federal, favor avaliar")
			alertList.put("Campo 'Finalidade de Uso' é obrigatório.", "Campo 'Finalidade de Uso' é obrigatório.")
			alertList.put(" Vigência retroativa não permitida", " Vigência retroativa não permitida")
			alertList.put(" Erro em la conversión de Datos", " Erro em la conversión de Datos")
			return  alertList.get(alert).toString();
		}


		/**
		 * @author ARAntunes
		 * @param tipo
		 * @return
		 */
		public static tipoComissionamento(String tipo){
			String xpath = '//*[@id="ComisionamientoBean$tipoComisionamiento"]'
			String tag ="option";
			clickXpath(xpath, 2)
			WebUI.delay(5)
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				if(listOfElements[i].getText().equals(tipo)){
					System.err.println("["+i+"] ["+tag+"] ["+listOfElements.get(i).getText()+"]")
					listOfElements[i].click()
					break
				}
			}
		}

		/**
		 * @author ALZ_ServiceQA
		 * @param x
		 * @param t
		 * @param v
		 * @return
		 */
		public static selecionaItemLista(String x, String t, String v ){
			selecionaListaDeElementos(x,t);
			int size = listOfElements.size()
			System.err.println('listOfElements['+size+']')
			for(int i = 0; i < size; i++){
				System.err.println('['+i+'] ['+listOfElements.get(i).getText()+"] SEARCH ["+v+"]")
				if(listOfElements.get(i).getText().contains(v)){
					System.err.println('['+i+'] ['+listOfElements.get(i).getText()+"] FOUND ["+v+"]")
					listOfElements.get(i).click()
					break
				}
			}
		}

		/**
		 * @author be54955
		 * @param xpath
		 * @param tag
		 * @return
		 */
		public static boolean identificaAlertaList(String xpath,String tag ){
			boolean result = false
			try{
				WebUI.delay(5)
				selecionaListaDeElementos(xpath,tag)
				for(int i = 0; i < listOfElements.size(); i++){
					if(listOfElements[i].getText().equals(alertListBloc(listOfElements[i].getText()))){
						System.err.println("["+i+"] ["+tag+"] ["+listOfElements.get(i).getText()+"]")
						result = true
						break
					}
				}
			} catch (Exception e){result = false}
			return result
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
		 *
		 * @param aba
		 * @param corretor
		 * @return
		 */
		public static String selecionaCorretor(String aba, String novoCorretor){
			String corretor
			switch(aba) {
				case 'RenovaSantander':
				corretor =  "0188010"
				break
				case 'RenovaSantanderSASAM':
				corretor =  "0188010"
				break
				case 'RenovaEmissaoIncorreta':
				corretor =  novoCorretor
				break
			}
			return corretor
		}

		/**
		 * @author be54955
		 * @param premio
		 * @param premioTotal
		 * @return
		 */
		public static String calculoICGlobal(String premioRecuperado, String premioTotal){

			premioRecuperado = premioRecuperado.replace("R\$","")
			premioRecuperado = premioRecuperado.replace(".","")
			premioRecuperado = premioRecuperado.replace(",",".").trim()
			premioTotal = premioTotal.replace(".","")
			premioTotal = premioTotal.replace(",",".")
			BigDecimal bigPremioRecuperado = new BigDecimal(premioRecuperado.toString());
			BigDecimal bigPremioTotal = new BigDecimal(premioTotal.toString());
			BigDecimal ic = new BigDecimal(bigPremioTotal*100/bigPremioRecuperado)
			BigDecimal icArredondado = ic.setScale(2, BigDecimal.ROUND_UP);
			String strngic = icArredondado.toPlainString()
			strngic = strngic.replace(".", ",")
			System.err.println("Premio Recuperado ["+bigPremioRecuperado+"] Premio total ["+bigPremioTotal+"] IC Global ["+strngic+"]")

			return strngic
		}

		/**
		 * @author be54955
		 * @return
		 */
		public static String recuperaPremioTabelaPrecos(){
			String premio
			WebUI.delay(5)
			String pacoteSelecionado =  verificaPacoteSelecionado()
			System.err.println("Pacote Selecionado ["+pacoteSelecionado+"]")
			premio = recuperaValor(premioTabelaPreco(pacoteSelecionado), 'value', 3)
			System.err.println("Valor do Pacote Selecionado ["+premio+"]")
			return premio
		}

		/**
		 * @author ALZ_ServiceQA
		 * @param alert
		 * @return
		 */
		public static String premioTabelaPreco(String premio){
			Map<String, String> precoList = new HashMap<String, String>()
			precoList.put("Essencial", '//*[@id="modalidad_0_0_primaRecibo"]')
			precoList.put("Básico", '//*[@id="modalidad_1_0_primaRecibo"]')
			precoList.put("Ampliado", '//*[@id="modalidad_2_0_primaRecibo"]')
			precoList.put("Completo", '//*[@id="modalidad_3_0_primaRecibo"]')
			precoList.put("Master", '//*[@id="modalidad_4_0_primaRecibo"]')
			precoList.put("Exclusivo",'//*[@id="modalidad_0_0_primaRecibo"]')
			return  precoList.get(premio).toString();
		}

		/**
		 * @author be54955
		 * @param premio
		 * @return
		 */
		public static String banco(String banco){
			Map<String, String> bancList = new HashMap<String, String>()
			bancList.put("237", '0237 - BANCO BRADESCO S.A.')
			bancList.put("756", '0756 - BANCO COOPERATIVO DO BRASIL S.A.')
			bancList.put("991", '0991 - BANCO CREDICOAMO')
			bancList.put("1", '0001 - BANCO DO BRASIL S.A.')
			bancList.put("47", '0047 - BANCO DO ESTADO DE SERGIPE S.A.')
			bancList.put("992", '0992 - BANCO MONDIAL')
			bancList.put("422",'0422 - BANCO SAFRA S.A.')
			bancList.put("33",'0033 - BANCO SANTANDER S.A.')
			bancList.put("70",'0070 - BRB - BANCO DE BRASILIA S.A.')
			bancList.put("104",'0104 - CAIXA ECONOMICA FEDERAL')
			bancList.put("341",'0341 - ITAU')
			return  bancList.get(banco).toString();
		}

		/**
		 *@author ALZ_ServiceQA
		 * @return
		 */
		public static boolean identificaAlertasPopUp(String mensagem){
			System.err.println('[ Check Pop Up Alerts ] ['+mensagem+']')
			boolean msg = true
			try{
				if(mensagem.contains(alertListBloc(mensagem))){
					alerta = mensagem
					msg = false
				}
				return msg
			} catch (Exception e){
			}
			System.err.println('-----['+msg+'] Alertas ['+alerta+']-----')
			return msg
		}

		/**
		 *
		 * @param premioRecuperado
		 * @param premioTotal
		 * @param limite
		 * @return
		 * premioTotal = "1083,40"
		 * premioRecuperado = "R\$ 1.287,69"
		 * limite = "100"
		 */
		public static boolean calculoDeLimite(String premioRecuperado, String premioTotal, String limite){
			boolean limit = false
			premioRecuperado = premioRecuperado.replace("R\$","")
			premioRecuperado = premioRecuperado.replace(".","")
			premioRecuperado = premioRecuperado.replace(",",".").trim()
			premioTotal = premioTotal.replace(".","")
			premioTotal = premioTotal.replace(",",".")
			BigDecimal bigLimite = new BigDecimal(limite.toString());
			if(bigLimite>0){
				BigDecimal bigPremioRecuperado = new BigDecimal(premioRecuperado.toString());
				BigDecimal bigPremioTotal = new BigDecimal(premioTotal.toString());
				BigDecimal result = new BigDecimal(bigPremioTotal.subtract(bigPremioRecuperado))
				result = result.abs()
				BigDecimal resultCalculado = result.setScale(2, BigDecimal.ROUND_UP);
				String strngic = resultCalculado.toPlainString()
				strngic = strngic.replace(".", ",")
				if(result > bigLimite){
					limit = true
					sAlerta = 'Diferença do prêmio maior que o permitido. Premio Recuperado [R\$ '+bigPremioRecuperado+"] Premio total [ R\$ "+bigPremioTotal+"] Result [R\$ "+strngic+"] Limite [R\$ "+limite+",00]"
				} else {
					limitePremio = 'Diferença permitida. Premio Recuperado [R\$ '+bigPremioRecuperado+"] Premio total [ R\$ "+bigPremioTotal+"] Result [R\$ "+strngic+"] Limite [R\$ "+limite+",00]"
				}

			}
			System.err.println(sAlerta+"\n"+limitePremio)
			return limit
		}

		/**
		 * @author ALZ_ServiceQA
		 * @param alert
		 * @return
		 */
		public static String tipoConta(String alert){
			System.err.println("Tipo Conta ["+alert+"]")
			Map<String, String> alertList = new HashMap<String, String>()
			alertList.put("1", "Conta corrente")
			alertList.put("2", "Conta poupança")
			return  alertList.get(alert.toString()).toString();
		}

		/**
		 * @author be54955
		 * @param valorRcuperado
		 * @param FINAL_VIG
		 * @param xpath
		 * @return
		 */
		public static String validaDiaPagamento(String valorRcuperado, String FINAL_VIG, String xpath){
			System.out.println("valorRcuperado ["+valorRcuperado+"] FINAL_VIG["+FINAL_VIG+"]")
			String dia
			int valor = Integer.parseInt(valorRcuperado);

			if(valor==0){
				dia = FINAL_VIG.substring(7, 8);
				preencheXpath(xpath, dia, 1);
			}
			return dia
		}

		/**
		 * @author be54955
		 * @param valorRcuperado
		 * @param FINAL_VIG
		 * @param xpath
		 * @return
		 */
		public static validaDiaPagamentoSantander(String valorRcuperado, String FINAL_VIG, String xpath){
			System.out.println("valorRcuperado ["+valorRcuperado+"] ["+valorRcuperado.length()+"] FINAL_VIG["+FINAL_VIG+"]")
			String dia
			if(valorRcuperado.length()==0){
				dia = FINAL_VIG.substring(6, 8);
				preencheXpath(xpath, dia, 1);
			}
		}

		/**
		 * @author be54955
		 * @param d
		 * @return
		 */
		public static boolean pessoaJuridica(String d){
			boolean pj = false
			if(d.length()==14){
				pj = true
			}
			return pj
		}


		/**
		 * @author ALZ_ServiceQA
		 * @param alert
		 * @return
		 */
		public static boolean alertListContinueList(String alert){
			boolean r = false
			System.err.println("alertListContinue ["+alert+"]")
			List<String> listOfAlerts = new ArrayList<String>()
			listOfAlerts.add("Tipo de renovação divergente")
			listOfAlerts.add("A placa informada não corresponde ao chassi do veículo.")
			listOfAlerts.add("Campo necessário")
			listOfAlerts.add("Nome alterado conforme a receita federal, favor avaliar")
			listOfAlerts.add("Nome do condutor alterado conforme a receita federal, favor avaliar")
			listOfAlerts.add(" Vistoria Prévia Presencial Obrigatória")
			listOfAlerts.add("Nome do condutor alterado conforme a receita federal, favor avaliar")
			listOfAlerts.add("Campo 'Finalidade de Uso' é obrigatório.")
			listOfAlerts.add(" Vigência retroativa não permitida")
			listOfAlerts.add(" Ano Modelo sem Aceitação")
			listOfAlerts.add(" Aceitação Condicionada a Inspeção Veicular e contratação de 90% FIPE")
			listOfAlerts.add(" Verifique se o email está correto antes de continuar")
			listOfAlerts.add(" Vistoria Prévia Dispensada")
			listOfAlerts.add(" Vistoria Prévia Digital Obrigatória")
			listOfAlerts.add(" Não mostra as modalidade cujo o prêmio é inferior ao mínimo da parcela")
			listOfAlerts.add(" error.BREO9918.9524")
			listOfAlerts.add("Veículo possui kit gás instalado?")
			System.out.println("Alert ["+alert+"]")
			for(int i = 0; i < listOfAlerts.size(); i++){
				System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
				if(listOfAlerts[i].contains(alert.toString())){
					System.err.println("FOUND ALERT TO CONTINUE ["+i+"] ["+listOfAlerts[i]+"]")
					r = true
					break
				}
			}
			System.out.println("Alert ["+r+"]")
			return  r
		}

		/**
		 * @author be54955
		 * @param alert
		 * @return
		 */
		public static boolean alertListBlocList(String alert){
			System.err.println("\nalertListBloc ["+alert+"]")
			boolean r = true
			List<String> listOfAlerts = new ArrayList<String>()
			listOfAlerts.add("Erro on epac2020 on QA mode")
			//listOfAlerts.add("Apólice a renovar já utilizada")
			listOfAlerts.add(" Apólice a renovar já utilizada")
			listOfAlerts.add("Classe de bônus divergente")
			listOfAlerts.add(" Dados de conta corrente não pode ser de um CNPJ como Pagador")
			listOfAlerts.add("Nome não retornado no Serviço")
			listOfAlerts.add(" Política se aplica ao estado errado")
			listOfAlerts.add("Error: Dados da conta bancária inválidos")
			listOfAlerts.add(" Restrição técnica para o condutor")
			listOfAlerts.add(" Restrição técnica para o segurado")
			listOfAlerts.add(" Vistoria Prévia Presencial Obrigatória")
			listOfAlerts.add(" Data de início de vigência deve ser após")
			listOfAlerts.add(" Dados de conta corrente não pode ser de um CNPJ como Pagador")
			listOfAlerts.add(" Campo Data Saída Nota Fiscal 0KM da tela dados de risco é obrigatório na emissão")
			listOfAlerts.add(" Campo Placa da tela dados de risco é obrigatório para emissão")
			listOfAlerts.add(" Não existem dados para esta consulta.")
			listOfAlerts.add(" Risco com Cobertura pela Apólice ")
			listOfAlerts.add(" Apólice a renovar já utilizada")
			listOfAlerts.add("  Tipo de Franquia")
			//listOfAlerts.add("  A versão informada não corresponde ao chassi do veículo.")
			listOfAlerts.add(" A versão informada não corresponde ao chassi do veículo.")
			listOfAlerts.add("  Veículo não localizado. Contate a sua filial para análise do cadastramento.")
			listOfAlerts.add("Número")
			listOfAlerts.add(" Fora da política de aceitação da Cia.")
			listOfAlerts.add(" CEP Inválido")
			listOfAlerts.add(" Campo necessário")
			listOfAlerts.add(" Prêmio parcela inferior ao prêmio mínima da parcela")
			listOfAlerts.add(" Contratação Acessório Blindagem acima do limite permitido, o máximo é de ")
			listOfAlerts.add(" Veículo em duplicidade com proposta ")
			listOfAlerts.add(" Ocorreu um erro interno ao recuperar dados para mostrar a tabela de preços")
			listOfAlerts.add(" Cobertura não permitida para Acessório Blindagem")
			listOfAlerts.add(" RADAR (AP) - Erro na Precificação da cotação")
			listOfAlerts.add(" Erro no base de dados. Abrir chamado no Service Desk")
			listOfAlerts.add("Erro on epac2020 on QA mode")
			listOfAlerts.add(" Endereço é obrigatório")
			listOfAlerts.add(" Tipo da via é obrigatório")
			listOfAlerts.add(" Condutor deve ser pessoa física")
			
			System.out.println("Alert ["+alert+"]")
			for(int i = 0; i < listOfAlerts.size(); i++){
				System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
				//		if(listOfAlerts[i].contains(alert.toString())){
				if(alert.toString().contains(listOfAlerts[i])){
					System.err.println("FOUND ALERT TO BLOC ["+i+"] ["+listOfAlerts[i]+"]**********************************************")
					r = false
					break
				}
			}
			System.out.println("\nAlert ["+r+"]")
			return  r
		}

		/**
		 * @author be54955
		 * @param alert
		 * @return
		 */
		public static boolean alertListBlocListVareaveis(String alert){
			System.err.println("alertListBlocVareaveis ["+alert+"]")
			boolean r = true
			List<String> listOfAlerts = new ArrayList<String>()
			listOfAlerts.add(" Risco com Cobertura pela Apólice ")
			System.out.println("Alert ["+alert+"]")
			for(int i = 0; i < listOfAlerts.size(); i++){
				System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
				if(listOfAlerts[i].contains(alert.toString())){
					System.err.println("FOUND ALERT ["+i+"] ["+listOfAlerts[i]+"]")
					r = false
					break
				}
			}
			System.out.println("Alert ["+r+"]")
			return  r
		}

		/**
		 * @author be54955
		 * @param alert
		 * @return
		 */
		public static boolean alertListAjustList(String alert){
			System.err.println("alertListBloc ["+alert+"]")
			boolean r = false
			List<String> listOfAlerts = new ArrayList<String>()
			listOfAlerts.add("&nbsp;A placa informada não corresponde ao chassi do veículo.")
			listOfAlerts.add("&nbsp;A placa não corresponde ao chassi do veículo ou não foi informada.")
			System.out.println("Alert ["+alert+"]")
			for(int i = 0; i < listOfAlerts.size(); i++){
				System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
				if(listOfAlerts[i].contains(alert.toString())){
					System.err.println("FOUND ALERT ["+i+"] ["+listOfAlerts[i]+"]")
					r = true
					break
				}
			}
			System.out.println("Alert ["+r+"]")
			return  r
		}

		/**
		 * @author be54955
		 * @return
		 */
		public static logradouroDadosClienteSasam2(){
			try{
				String v = recuperaValor('//*[@id="DomicilioClienteBeanTomador$moradaaddressPart2"]', 'value', 5)
				int t = v.length()
				System.out.println("Get Value ["+t+"]")
				if(t==0){
					//LOGRADOURO
					selecionaItemLista('//*[@id="DomicilioClienteBeanTomador$moradatype"]', "option",registros[36],)
					preencheXpath('//*[@id="DomicilioClienteBeanTomador$moradaaddressPart2"]', registros[37], 1);
				}
			} catch (Exception e){}
		}

		/**
		 * @author be54955
		 * @param xpath
		 * @param tag
		 * @param attribute
		 * @return
		 */
		public static String recuperaValorSelecionadoNaLista(String xpath, String tag, String attribute){
			String v

			WebUI.delay(2)
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				String x = listOfElements[i].getAttribute(attribute)
				System.err.println("["+i+"] ["+x+"]")
				if(listOfElements[i].getAttribute(attribute).toString().equals(x)){
					String a = listOfElements[i].getAttribute('title')
					System.err.println("FOUND ["+i+"] ["+a+"]")
					if(a.equals("--")){
						listOfElements[154].click()
					}
					break
				}
			}
			return v
		}

		/**
		 * @author be54955
		 */
		public static validaPlaca(){
			System.err.println()
			String v
			String vs
			try{
				v = recuperaValor('//*[@id="InfVeicBean$placa"]', 'value', 5)
				v.length()
				System.out.println("Get Value ["+v+"] ["+v.length()+"]")
			} catch (Exception e){}

			try{
				if(v.length()==0){
					selecionaListaDeElementos('//*[@id="InfVeicBean$versao"]','option')
					for(int i = 0; i < listOfElements.size(); i++){
						System.err.println("["+i+"] ["+listOfElements[i].getAttribute('selected')+"]")
						if(listOfElements[i].getAttribute('selected')){
							System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
							vs = listOfElements[i].getText()
							break
						}
					}
				}
				System.out.println("Versão ["+vs+"]")
				WebUI.delay(2)
			} catch (Exception e){}

			try{
				if(v.length()==0){
					//Lupa chassi
					clickXpath('//*[@id="consultarChassi"]', 1);
					WebUI.delay(2)
					if(vs.length()>0){
						decodificadorChassi(vs)
					}
					WebUI.delay(2)
					selecionaItemLista('//*[@id="InfVeicBean$zeroKM"]',"option",'Não')
					WebUI.delay(2)
				}
			} catch (Exception e){}
		}

		/**
		 * @author be54955
		 * @param x
		 * @return
		 */
		public static decodificadorChassi(String x){
			try{
				WebUI.switchToWindowIndex(3)
				String telaDecodificadorChassi = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
				System.err.println("\ntelaDECODIFICADOR DE CHASSI - tela ["+telaDecodificadorChassi+"]")
				if(telaDecodificadorChassi.contains("Decodificador de Chassi".toString())){
					WebUI.delay(2)
					selecionaListaDeElementos('//*[@id="tabelaChassi"]','tr')
					for(int i = 0; i < listOfElements.size(); i++){
						System.err.println("["+i+"] ["+x+"] ["+listOfElements[i].getText()+"]")
						if(listOfElements[i].getText().toString().contains(x)){
							System.err.println("FOUND ["+i+"] ["+x+"]")
							listOfElements[i].click()
							WebUI.delay(2)
							fechaAlerta()
							break
						}
					}
				}
				WebUI.switchToWindowIndex(2)
			} catch (Exception e){
				WebUI.switchToWindowIndex(2)
			}
		}

		/**
		 * @author BE54955
		 * @param pacote
		 * @return
		 */
		public static String ramos(String pacote){
			Map<String, String> ramos = new HashMap<String, String>()
			ramos.put('1211', '1211-0-0-AUTOMOVEL SEGMENTO 1');
			ramos.put('1212', '1212-0-0-MOTO');
			ramos.put('1213', '1213-0-0-AUTOMOVEL SEGMENTO 34');
			return ramos.get(pacote.toString());
		}

		/**
		 * @author BE54955
		 * @return
		 */
		public static boolean verificaApolicesNR(){
			boolean rn = true
			alertaNR = false
			try{
				String a = recuperaValor('//*[@id="idPoliza_0_9"]', 'innerText', 5)
				String [] list = a.split(" ")
				for(int i = 0; i<list.size();i++){
					if(list[i].toString().equals("NR")){
						System.err.println()
						System.out.println("FOUND ["+i+"] ["+list[i].toString()+"]")
						rn = false
						alertaNR = true
						alertaDadosDeRisco = false
						alertaRenovacao = false
						alertaEmissaoRealizada = false
						break;
					}
				}
			} catch (Exception e){

			}
			return rn;
		}

		/**
		 * @author BE54955
		 * @return
		 */
		public static limpaAlertas(){
			alertaRenovacao = false
			alertaDadosRisco = false
			alertatelaTabelaPrecos = false
			alertaSistemaPD = false
			alertaDadosEmissao = false
			alertaDadosCliente = false
			alertaConfirmacaoEmissao = false
			alertaEmissaoRealizada = false
			alertaNR = false
			alertaDadosDeRisco = false

		}


		public static String capturaValorParcela(String sPacote){

			String s;
			try{
				System.err.println("sPacote ["+sPacote+"]");
				//recuperaValor('//*[@id="modalidad_5_0_primaRecibo"]', 'value', 5)
				String xpath = '//*[@id="blParrillaCellContent"]'
				String tagLabel ="label";
				clickXpath(xpath, 2)
				WebUI.delay(5)
				selecionaListaDeElementos(xpath,tagLabel)
				int p = 0;
				for(int i = 0; i < listOfElements.size(); i++){
					if(listaDePacotes(listOfElements[i].getText())){
						if( listOfElements[i].getText().equals(sPacote) ){
							// System.err.println("FOUND PACK ["+p+"] ["+i+"] ["+tagLabel+"] ["+listOfElements.get(i).getText()+"]");
							s = recuperaValor("//*[@id='modalidad_"+p+"_0_primaRecibo']", 'value', 5)
							break;
						}
						p = p+1;
					}
				}
			} catch (Exception e ){}
			return s;
		}


		public static boolean listaDePacotes(String pacote){
			System.err.println("\nPacote ["+pacote+"]")
			boolean r = false
			List<String> listOfPacs = new ArrayList<String>()
			listOfPacs.add("Roubo e Furto")
			listOfPacs.add("Essencial")
			listOfPacs.add("Básico")
			listOfPacs.add("Ampliado")
			listOfPacs.add("Completo")
			listOfPacs.add("Master")
			listOfPacs.add("Exclusivo")
			System.out.println("Pacote -["+pacote+"]-")
			for(int i = 0; i < listOfPacs.size(); i++){
				//	System.err.println("["+i+"] ["+listOfPacs[i]+"]")
				if(listOfPacs[i].equals(pacote.toString())){
					System.err.println("FOUND ["+i+"] ["+listOfPacs[i]+"]")
					r = true
					break
				}
			}
			System.out.println("\nPacote ["+r+"]")
			return  r
		}

		public static void verificaTelefones(){
			String telefone = recuperaValor('//*[@id="DomicilioClienteBeanTomador$telefone"]', 'value', 0);
			String telefone2 = recuperaValor('//*[@id="DomicilioClienteBeanTomador$telefone2"]', 'value', 0);
			String celular = recuperaValor('//*[@id="DomicilioClienteBeanTomador$celular"]', 'value', 0);

			System.out.println("Telefone ["+telefone.length()+"] Telefone 2 ["+telefone2.length()+"] Celular ["+celular.length()+"]")

			if(telefone.length() == 0 && telefone2.length()== 0 && celular.length() == 0){
				preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone"]', "11 99999 8888", 1);
			}

			try{
				fechaAlerta()
			}catch (Exception e){}
		}


		public static void ajusteTelaTabelaDePrecos(List<String> registro){
			System.out.println()
			System.out.println("RECURSIVO...")
			WebUI.delay(3)
			try{
			boolean r
			String msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			if(msg.equals(" Nova Apólice0Nova Apólice")){
			   r=true;
			} else {
			   r=false;
			}
			System.out.println(r)
			if(r.equals(true)){
				Random gerador = new Random(); // 1. Cria um objeto da classe Random
				int numeroAleatorio = gerador.nextInt(9) + 1;
				String n = String.valueOf(numeroAleatorio)
				selecionaItemListaContains(n, '//*[@id="ParrillaBean$numFracciones"]', 'option');
				WebUI.delay(3)
				//NUMERO DE PERCELAS - FORMULARIO
				String p = registro[9];
				//NUMERO PARCELAMENTO -FORMULARIO
				selecionaItemListaContains(p, '//*[@id="ParrillaBean$numFracciones"]', 'option');
				WebUI.delay(5)
				r=false;
				ajusteTelaTabelaDePrecos(registro)
			}
			
			
			} catch (Exception e){}
			System.out.println("SAINDO DO RECURSIVO...")
		}
		
		
		/**
		 * @author ALZ_ServiceQA
		 * @param date
		 * @return
		 */
		public static boolean calculoDadasProcessadas(String date){
			boolean dataEncontrada = false
			//initDataFiles()
			System.err.println(data.getRowNumbers())
			for (int i = 1; i <= data.getRowNumbers(); i++) {
				if (data.getValue(3, i).equals('x')) {
					if(auxiliares.ExtractDates.compareDate(date, data.getValue(2, i))){
						
						dataSelecionada
						dataEncontrada = true
						break
					}
				}
			}
			System.err.println('Data Encontrada ['+dataEncontrada+']')
			return dataEncontrada
		}
		
		
		/**
		 * @author Alz_sup54955
		 * @param tipoAba
		 * @param timeLapnse
		 * @param dataInicio
		 * @param dataFinal
		 * @param horaInicial
		 * @param horaFinal
		 * @return
		 */
		public static enviarEmailSantanderDinamico(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal, List<String> msgAlertas){
		 System.out.println()
		 try{
				iniURLDateFiles()
				initDataFiles(testDataPath, tabCapPath )
				String pathTxtEmailConfig = "C:/Users/Public/RPA_ALLIANZ/Emails/Auto/RenovaSantander/ConfiguracoesRenovaSantander.txt";
				lerConfigurações(pathTxtEmailConfig)
				System.out.println(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				System.out.println(emaildestination)
				// FORMATA SAIDA DO TEMPO DE EXECUÇÃO
				long segds = timeLapnse.toSeconds();
				String hours = timeLapnse.toHours()+"h "+
							timer.converteMinutos(timeLapnse.toHours(), timeLapnse.toMinutes())+"m "+
							timer.converteSegundos(timeLapnse.toMinutes(),segds)+"s";
				// ENVIO DE E-MAIL
				// DADOS DO DOCUMENTO
				// ENVIA E-MAIL DINAMICO
				System.out.println(sender+" - "+ emaildestination+" - "+ tipoAba+" - "+ path+" - "+ docName+" - "+msgAlertas.size()+" - "+ hours+" - "+ horaInicial+" - "+ horaFinal+" - "+  GetHost.getHostname() +" - "+ dataInicio+" - "+ dataFinal+" - "+ dataDoDocumento)
				emailDinamico.enviaEmailDinamico(sender, emaildestination, tipoAba, path, docName, msgAlertas  , hours, horaInicial, horaFinal,  GetHost.getHostname() , dataInicio, dataFinal, dataDoDocumento+" - "+GetHost.getHostname() );
				FileInput.deletaArquivoPdf(pathFile);
		  } catch (Exception e){}
		}


		/**
		 * @author T_DCDias
		 * @return
		 */
		public static confrmaData(String tipoAba, String[] dataParaExecutar){
			try{
				List<String> msgAlertas = new ArrayList<String>()
				for(int i = 0; i < dataParaExecutar.size(); i++){
					System.err.println('Data ['+i+'] ['+dataParaExecutar[i]+']')
					if(i==0){
						dataDoDocumento = dataParaExecutar[i]
					}
					if(validaDataParaPorcessar(dataParaExecutar[i])){
					System.out.println()
					msgAlertas.addAll(listaDeApolices(tipoAba, dataParaExecutar))
					}
				}
				return msgAlertas;
			} catch (Exception e){
			}
		}
	
		/**
		 * @author ALZ_SUP54955
		 * @param tipoAba
		 * @param dataParaExecutar
		 * @return
		 */
		public static List<String> listaDeApolices(String tipoAba, String[] dataParaExecutar){
			System.out.println()
			apolices = new ArrayList<String>()
			System.out.println("LISTA DE APOLICES ["+data.getRowNumbers()+"] LISTA DE DATAS "+dataParaExecutar.size()+"")
			for (int i = 1; i <= data.getRowNumbers(); i++) {
				for( int j = 0; j < dataParaExecutar.size(); j++){
					if(data.getValue(3, i).toString().equals("N") || data.getValue(fluxoSantander(tipoAba), i).toLowerCase().equals('x')){
						if (auxiliares.ExtractDates.formatDateToBR(data.getValue(2, i).toString()).equals(dataParaExecutar[j]) ) {
							apolices.add(data.getValue(1, i).trim())
						}
					}
				}
			}
	//		System.out.println("APÓLICES ["+apolices.size()+"]")
	//		System.out.println()
			return recuperaAlertas( apolices)
		}
		
		/**
		 * @author ALZ_SUP54955
		 * @param apolices
		 * @return
		 */
		public static List<String> recuperaAlertas(List<String> apolices){
			List<String> msg = new ArrayList<String>();
			for(int i = 1; data.getRowNumbers(); i++){
				for( int j = 0; j < apolices.size(); j++){
					if(data.getValue(1, i).trim().equals(apolices[j])){
						msg.add(alerta(data.getValue(31, i), data.getValue(32, i))+data.getValue(31, i))
					}
				}
				if(apolices.size() == msg.size()){
					break
				}
			}
			System.out.println("\n")
			for(int k = 0; k < msg.size(); k++){
				System.out.println("["+msg[k].trim()+"] ["+msg[k].length()+"]")
			}
			System.out.println("\nRECUPERA ALERTAS ["+msg.size()+"]")
			System.out.println()
			return msg;
		}

		/**
		 * @author ALZ_SUP54955
		 * @param a
		 * @param b
		 * @return
		 */
		public static String alerta(String a, String b){
			String txt;
			if(a.length()>0){
				txt="Alerta: "
			} else {
				txt=b;
			}
			return txt;
		}
		
}

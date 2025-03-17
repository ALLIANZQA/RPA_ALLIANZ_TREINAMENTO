package sinistros

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
import internal.GlobalVariable
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import java.awt.image.BufferedImage
import java.text.NumberFormat
import java.util.List
import com.kms.katalon.core.testobject.ConditionType
import io.cucumber.datatable.DataTable
import rpacommands.rpaCommands
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.openqa.selenium.WebElement
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import auxiliares.switchAtualizarExcel
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.MenuMatriz
import auxiliares.ExtractDates

public class ReabreReavaliaSinistroFuncionalidades extends rpaCommands{

	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Sinistro/ReabreReavaliaSinistro/ReabreReavaliaSinistroData'
	private static String path
	//private static String confirmacaoReserva
	//private static String alerta
	//private static String evidencia
	private static int cell = 8
	private static boolean encerrado  = false
	private static String cobertura = "49 Ressarcimento"


	private static String sTablaGaratia
	//	private static String sResultado
	private static String sSinJud
	private static String sStatus
	private static boolean bJumpIf
	//	private static boolean bNextReg
	private static boolean sElementPresent

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
	public static boolean carregaDados(){
		boolean carregar = false
		initDataFiles()
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if(data.getValue(cell, i).equals("Processar")){
				System.err.println(i+' - Status - '+ data.getValue(cell, i))
				carregar = true
				break
			}
		}
		System.err.println('carregaDados ['+ carregar+']')
		return carregar
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static lista( List<String> sinistro , int v){
		for (int i = 1; i <= data.getColumnNumbers(); i++) {
			int d = i -1
			//if(i==3){
			//sinistro[d] = ExtractDates.dateFormatteTypes("dd/MM/yyyy", "ddMMyyyy", data.getValue(i, v) )
			//} else if(i==5){
			//sinistro[d] = data.getValue(i, v).toUpperCase().trim()
			//} else if(i==12){
			//sinistro[d] = data.getValue(i, v).replaceAll("[R\$.,]","").trim()
			//} else if(i==16){
			//sinistro[d] = data.getValue(i, v)
			//} else {
			sinistro[d] = data.getValue(i, v)
			//}
		}

		for (int j = 0; j < sinistro.size();j++ ){
			System.err.println("["+j+"] [ "+sinistro[j]+"]")
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static menuGIS(){
		MenuMatriz.producaoSinistro()
		WebUI.delay(5);
		MenuMatriz.subMenu('/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div/div/div[2]/ul', 'a',"GIS")
	}

	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static fluxoReabreReavaliaSinistro(String tipoAba){
		fluxoCompletoReabreReavaliaSinistro(tipoAba)
		reprocessaReabreReavaliaSinistro(tipoAba)
		enviarEmailReabreReavaliaSinistro(tipoAba)
	}

	/**
	 * @
	 * @param tipoAba
	 * @return
	 */
	public static fluxoCompletoReabreReavaliaSinistro(String tipoAba){
		if(carregaDados()){
			bJumpIf = false

			//	sResultado = ''
			iniciaAplicacaoMatrizCommands('IntranetProdução');
			realizaLoginIntraNetProducaoCommands(tipoAba);
			WebUI.delay(15);
			menuGIS()
			WebUI.delay(15);
			/**
			 * TELA GESTÃO INTEGRADA
			 */
			alteraJanejaGenerica(1,2);
			WebUI.maximizeWindow()
			for (int i = 1; i <= data.getRowNumbers(); i++) {
				if(data.getValue(cell, i).equals("Processar")){
					alerta = ""
					encerrado = false
					try{
						List<String> sinistro = new ArrayList<>();
						lista(sinistro,i)
						if(reabreSinistro(sinistro,i)){
							try{
								auxiliares.WriteExcelFactory.updateSheetLine('',  i-1, path, "Processado", 7 , 'ReabreReavaliaSinistro');
								auxiliares.WriteExcelFactory.updateSheetLine('', i-1, path, sinistro[9] , 8 , 'ReabreReavaliaSinistro');
							}catch (Exception e){System.err.println("[ Cannot invoke method setCellValue() on null object ]")}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine('',  i-1, path, "Processado", 7 , 'ReabreReavaliaSinistro');
								auxiliares.WriteExcelFactory.updateSheetLine('', i-1, path, sinistro[9], 8 , 'ReabreReavaliaSinistro');
								System.out.println()
								alteraJanejaGenerica(0,2);
								menuGIS()
								alteraJanejaGenerica(1,2);
								WebUI.maximizeWindow()
							}catch (Exception e){System.err.println("[ Cannot invoke method setCellValue() on null object ]")}
						}
					}catch (Exception e){  }
				}
			}
			WebUI.closeBrowser();
		}
	}

	/**
	 * @author be54955
	 * @param sinistro
	 * @param i
	 * @return
	 */
	public static boolean reabreSinistro(List<String> sinistro, int i){

		boolean bNextReg = false

		System.err.println()

		try{
			WebUI.setText(findTestObject('Object Repository/ReabreReavaliaSinistro/Page_Gesto Integrada de Sinistros/input_Referncia_refsiniestro'), data.getValue('Susep', i))
			WebUI.click(findTestObject('Object Repository/ReabreReavaliaSinistro/Page_Gesto Integrada de Sinistros/td_Pesquisar'))
			WebUI.waitForPageLoad(30)
			sSinJud = WebUI.getAttribute(findTestObject('Object Repository/ReabreReavaliaSinistro/Page_Gesto Integrada de Sinistros/input_Judicial_judicial'), 'value')
			if(sSinJud == 'Sinistro judicial'){
				sinistro[9] = ' Sinistro Judicial'
				sStatus = 'KO'
				bNextReg = true
			}

			if(bNextReg == false) {
				sElementPresent = WebUI.verifyTextPresent("Reabrir", false, FailureHandling.OPTIONAL)
				if(sElementPresent == true){
					WebUI.click(findTestObject('Object Repository/ReabreReavaliaSinistro/Page_Gesto Integrada de Sinistros/div_Reabrir'))
					WebUI.click(findTestObject('Object Repository/ReabreReavaliaSinistro/Page_Gesto Integrada de Sinistros/div_Aceitar'))
				}else {
					WebUI.click(findTestObject('Object Repository/ReabreReavaliaSinistro/Page_Gesto Integrada de Sinistros/td_Dados Econmicos'))
				}

				if(DescontosEspecíficosSinistroFuncionalidades.selecionarRessarcimento(sinistro[6], sinistro[4])){
					// WebUI.click(findTestObject('Object Repository/ReabreReavaliaSinistro/Page_Gesto Integrada de Sinistros/div_Aceitar_1'))
					sinistro[9] = WebUI.getText(findTestObject('Object Repository/ReabreReavaliaSinistro/Page_Gesto Integrada de Sinistros/td_Operao realizada.'))
					sStatus = 'OK'
					bNextReg = true
				}else{
					WebUI.click(findTestObject('Object Repository/ReabreReavaliaSinistro/Page_Gesto Integrada de Sinistros/div_Aceitar_1'))
					sElementPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/ReabreReavaliaSinistro/Page_Gesto Integrada de Sinistros/td_Reserva  Pagamentos ultrapassa a cobertura'), 2)
					if(sElementPresent == true){
						sinistro[9] = WebUI.getText(findTestObject('Object Repository/ReabreReavaliaSinistro/Page_Gesto Integrada de Sinistros/td_Reserva  Pagamentos ultrapassa a cobertura'))
						sStatus = 'KO'
						bNextReg = true
					}

					sElementPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/ReabreReavaliaSinistro/Page_Gesto Integrada de Sinistros/td_Inserir Senha para valores superiores a 30000000 BRL'), 2)
					if(sElementPresent == true){
						sinistro[9] = WebUI.getText(findTestObject('Object Repository/ReabreReavaliaSinistro/Page_Gesto Integrada de Sinistros/td_Inserir Senha para valores superiores a 30000000 BRL'))
						sStatus = 'KO'
						bNextReg = true
					}
				}
			}
		} catch (Exception e){

		}
		return bNextReg
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static reprocessaReabreReavaliaSinistro(String tipoAba){
		if(carregaDados()){
			System.err.println("[ Reprocessa Descontos Específicos Sinistro")
			fluxoCompletoReabreReavaliaSinistro(tipoAba)
			reprocessaReabreReavaliaSinistro(tipoAba)
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailReabreReavaliaSinistro(String tipoAba){
		if(!carregaDados()){
			pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Sinistros/ReabreReavaliaSinistro/ConfiguracoesReabreReavaliaSinistro.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Sinistro\\ReabreReavaliaSinistro\\ReabreReavaliaSinistro.xlsx"
			anexos[1] = "ReabreReavaliaSinistro.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
		}
	}














}

package infraestrutura

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
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import io.cucumber.datatable.DataTable
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import auxiliares.updateExcel
import sendEmail.envioDeEmail;
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.switchLeitorExcel
import auxiliares.WriteExcelFactory;
import auxiliares.ExtractDates


public class UMAByPassFuncionalidades extends rpaCommands {
	
	private static boolean bAlertPresent
	private static String dataUltimoByPass
	private static String alerta 
	private static String log
	private static String rolUsuario
	private static boolean alertaResultados
	private static String testDataPath = 'Data Files/infraestrutura/ByPassData'
	private static TestData data
	private static String path
	
	
	/**
	 * @author T_DCDias
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoByPass(String aba){
		fluxoByPass(aba)
	}
	
	/**
	 * @author T_DCDias
	 * @param aba
	 * @return
	 */
	public static fluxoByPass(String aba){
		if(carregaDados()){
		   iniciaAplicacaoMatrizCommands('IntranetProdução');
		   realizaLoginIntraNetProducaoCommands(aba);
		   WebUI.delay(5)
		   menuByPass()
		   WebUI.delay(5)
		   gerenciaUsuarios()
		   fluxobypass(aba)
		   finalizar()
		}
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static menuByPass(){
		clickXpath('/html/body/div/div/app-root/app-private/app-private-header/nx-epac-header/nx-epac-navigation/div/div[1]/nx-epac-navigation-desktop/div[1]/div/div/ul/li[5]/a/span', 5)
		clickXpath('/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div/div/div[2]/ul/li[1]/a', 5)
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static gerenciaUsuarios(){
		WebUI.waitForPageLoad(30)
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static fluxobypass(String aba){
		
		path = data.sourceUrl
		
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(1, i) == 'x') {
		
		        WebUI.delay(2)
		        preencheXpath('//*[@id="codigo"]', findTestData(testDataPath).getValue('BE', i), 5)
		        clickXpath('//*[@id="queryButton"]',2)//
	            alertaResultados = validaElemento('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]')
		        System.err.println('alertaResultados ['+alertaResultados+']')
		        System.err.println()
		
		        if(!alertaResultados){
			
			       clickXpath('//*[@id="o_7"]',2)//bypass
				   
				   String dataByPass = ExtractDates.retornaDataSuperior(3652)
				   
				   limpaCampo('//*[@id="futureallconnectbypassdate"]',2)
				   preencheXpath('//*[@id="futureallconnectbypassdate"]', dataByPass.replaceAll("/", ""), 2);
		           clickXpath('//*[@id="queryButton"]',2)//Executar bypass
		
		           bAlertPresent = WebUI.waitForAlert(4)
		           if (bAlertPresent == true) {
			           WebUI.acceptAlert()
		           }
		
		           clickXpath('//*[@id="o_7"]',2)//bypass
		           dataUltimoByPass = recuperaValor('//*[@id="allconnectbypassdate"]', 'value', 5)
		           System.err.println('dataUltimoByPass ['+dataUltimoByPass+']')
			 			
				   salvarRegistro(i, path, aba, dataUltimoByPass)
				   System.err.println()
				   
		        } else {
		          log = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr[1]/td[2]', 'textContent', 5)
		          System.err.println('['+log+']')
		          System.err.println()
				  salvarFalha(i, path, log, aba)
		        }
		     clickXpath('//*[@id="imgOptMenuBackHome"]',2)//Home
			}
		}
		
		System.err.println()
	}
	
	/**
	 * @author T_DCDias
	 * @param
	 * @return
	 */
	public static boolean validaElemento(String xpath){
		boolean result
		try{
	 			WebDriver driver = DriverFactory.getWebDriver()
	 			WebElement element = driver.findElement(By.xpath(xpath))
	 			result = element.enabled.booleanValue()
			}  catch (Exception e) {
	 			result = false
	 		}
			return result
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
	 * @param i
	 * @param path
	 * @param aba
	 * @return
	 */
	public static salvarRegistro(int i, String path, String aba, String dataUltimoByPass){
		i = i-1;
		WriteExcelFactory.updateSheetLine('',i, path,'',0,aba);
		WriteExcelFactory.updateSheetLine('',i, path,'x',1,aba);
		WriteExcelFactory.updateSheetLine('',i, path,dataUltimoByPass,5,aba);
	}
	
	/**
	 * @author T_DCDias
	 * @param i
	 * @param path
	 * @param aba
	 * @return
	 */
	public static salvarFalha(int i, String path, String log, String aba){
		i = i-1;
		WriteExcelFactory.updateSheetLine('',i, path,'',0,aba);
		WriteExcelFactory.updateSheetLine('x',i, path,'x',1,aba);
		WriteExcelFactory.updateSheetLine('',i, path,log,6,aba);
	}
	
	/**
	 * @author T_DCDias
	 */
	public static finalizaProcesso(){
		finalizar()
	}
	
}

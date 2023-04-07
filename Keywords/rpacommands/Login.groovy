package rpacommands

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

import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.chrome.ChromeOptions

public class Login {

	private static String ultimoAcesso

	/**
	 * @author T_DCDias
	 * @param url
	 * @return
	 */
	def static homePage(def url){
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		WebUI.navigateToUrl(url)
	}

	/**
	 * @author T_DCDias
	 * @param sUsuario
	 * @param sSenha
	 * @return
	 */
	def static efetuarLogin(def sUsuario, def sSenha ){
		WebUI.setText(findTestObject('PageAuto/Page_Inicio/input_Usurio__58_login'), sUsuario)
		WebUI.waitForPageLoad(10)
		//WebUI.setEncryptedText(findTestObject('PageAuto/Page_Inicio/input_Senha__58_password'), sSenha)
		WebUI.setText(findTestObject('PageAuto/Page_Inicio/input_Senha__58_password'), sSenha)
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	def static acessarHome(){
		WebUI.waitForPageLoad(10)
		WebUI.click(findTestObject('PageAuto/Page_Inicio/button_Aceitar'))
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	def static pegaUltimoAcesso(){
		WebUI.waitForPageLoad(30)
		def xpathtime = "//iframe[contains(@id,'appReloj')]//..//span[contains(@class,'all-ddhora') and contains(text(),'Ultimo')]"
		//ultimoAcesso = commands.AutoComandos.pegaValorDaTela(xpathtime).toString()
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static String getUltimoAcesso() {
		return ultimoAcesso;
	}

	/**
	 * @author T_DCDias
	 */
	public static void setUltimoAcesso(String ultimoAcesso) {
		Login.ultimoAcesso = ultimoAcesso;
	}
}

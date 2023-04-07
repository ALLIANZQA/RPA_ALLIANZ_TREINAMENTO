package infraestrutura

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

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.sun.xml.internal.org.jvnet.mimepull.DataFile
import internal.GlobalVariable as GlobalVariable
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium as WebDriverBackedSelenium
//import static org.junit.Assert.*
import java.util.regex.Pattern as Pattern
import static org.apache.commons.lang3.StringUtils.join
import org.openqa.selenium.By.ByClassName
import org.openqa.selenium.By.ById
import org.openqa.selenium.Keys as Keys
import rpacommands.rpaCommands

public class UMAVinculaCodigoCorretorFuncionalidades extends rpaCommands{
	
	//Abrir o Navegador e Apresentar URL
//	WebUI.openBrowser('')
//	WebUI.navigateToUrl(sURL)
//	WebUI.waitForPageLoad(60)
	
	//Logar
//	WebUI.maximizeWindow()
//	WebUI.setText(findTestObject('Object Repository/Page_Intranet Transacional/input_Bem-vindo(a)  Intranet Transacional_username'), sLogin)
//	WebUI.setEncryptedText(findTestObject('Object Repository/Page_Intranet Transacional/input_Usurio_password'), sSenha)
//	WebUI.click(findTestObject('Object Repository/Page_Intranet Transacional/span_Iniciar sesso'))
//	WebUI.waitForPageLoad(30)
	
	//Navegar pelos menus
//	WebUI.click(findTestObject('Object Repository/Page_Intranet Transacional/span_Manuteno Geral'))
//	WebUI.click(findTestObject('Object Repository/Page_Intranet Transacional/a_UMA'))
	
	//Acessar a aplicação
//	WebUI.switchToWindowTitle('UMA')
//	WebUI.waitForPageLoad(30)
//	WebUI.maximizeWindow()
	
	//Referenciar BD
	TestData data = findTestData('Data Files/Base')
	String path = data.sourceUrl
	
	//Inicio do Loop
//	for (int i = 1; i <= data.getRowNumbers(); i++) {
//		if (data.getValue('STATUS', i) == "x") {
//			String sUsuario = data.getValue('USUARIO BA', i)
//			String sMediador = data.getValue('COD. CORRETOR (VINCULO)', i)
//			String sSubMediador = data.getValue('COD. SUB-CORRETOR (VINCULO)', i)
//			String sPerfil = data.getValue('PERFIL ALLIANZNET', i)
//			String sStatus = ''
//			String sResultado = ''
//			Boolean bElementPresent = false
//			Boolean bProxReg = false
//			WebUI.waitForPageLoad(30)
//			WebUI.setText(findTestObject('Object Repository/Page_UMA/input_Descrio_codigo'), sUsuario)
//			WebUI.click(findTestObject('Object Repository/Page_UMA/div_Pesquisar'))
//			WebUI.click(findTestObject('Object Repository/Page_UMA/td_Allianznet_BR'))
//			WebUI.click(findTestObject('Object Repository/Page_UMA/div_Ativar Filial'))
//			WebUI.waitForPageLoad(30)
//			WebUI.setText(findTestObject('Object Repository/Page_UMA/input_Corretor_AtivaFilial'), sMediador)
//			WebUI.setText(findTestObject('Object Repository/Page_UMA/input_Sub-corretor_AtivaFilial'), sSubMediador)
//			WebUI.selectOptionByLabel(findTestObject('Object Repository/Page_UMA/select_Perfil_AzNet_AtivaFilial'), sPerfil, false)
//			WebUI.click(findTestObject('Object Repository/Page_UMA/div_Ativar_AtivaFilial'))
//			sStatus = 'OK'
//			sResultado = 'Código vinculado'
//			//sResultado = WebUI.getAttribute(findTestObject('Object Repository/Page_UMA/input_Operao_descripcionEstado'), 'value')
//			WebUI.click(findTestObject('Object Repository/Page_UMA/div_Incio'))
//			
//			CustomKeywords.'br.com.allianz.Classes.WriteExcel'(sStatus,sResultado,i,path,"C")
//		}
//	}
	//Fechar a aplicação, dar logout no portal e fechar o browser
//	WebUI.closeWindowTitle('UMA')
//	WebUI.switchToWindowTitle('Intranet Transacional')
//	WebUI.click(findTestObject('Object Repository/Page_Intranet Transacional/nx-icon_chevron-down'))
//	WebUI.click(findTestObject('Object Repository/Page_Intranet Transacional/span_Fechar Sesso'))
//	WebUI.closeBrowser()
	
	//bElementPresent = WebUI.waitForElementPresent(findTestObject('Object Repository/Page_UMA/table_01Nome completoerrorvalidationtamao42'), 2)
	//if (bElementPresent==true) {
	//}
	//if (bProxReg == false) {
	//}
	
	
	
}

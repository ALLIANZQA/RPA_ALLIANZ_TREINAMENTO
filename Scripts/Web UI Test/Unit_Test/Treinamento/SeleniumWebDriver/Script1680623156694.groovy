import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.util.List

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webui.driver.DriverFactory






/**
 * Selenium WebDriver: Introdução à automação de teste
 *
 *https://www.browserstack.com/guide/selenium-webdriver-tutorial
 *https://www.selenium.dev/downloads/
 *
 *import org.openqa.selenium.WebDriver
 *import org.openqa.selenium.WebElement
 *
 *chromedriver_win32 (1)
 *C:\Users\Public\Katalon_Studio_Windows_64-7.2.1\configuration\resources\drivers\chromedriver_win32
 *
 *
 *
 *
 *XPath Tutorial
 *https://www.selenium.dev/downloads/
 *
 *XPath Syntax
 *https://www.w3schools.com/xml/xpath_syntax.asp
 *
 */

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.SessionId
import org.openqa.selenium.WebElement


import rpacommands.rpaCommands




/**
 * 
 * @param xpath
 * @param texto
 * @param delay
 * @return
 * 
 * Preenche campos na tela  
 * 
 */
public static preencheXpath(String xpath, String texto, int delay){
	if(delay >0){
		WebUI.delay(delay);
	}
	WebDriver driver = DriverFactory.getWebDriver();
	WebElement element = driver.findElement(By.xpath(xpath));
	element.sendKeys(texto);
}

/**
 * 
 * @param xpath
 * @param delay
 * @return
 * 
 * Clica em elementos na tela 
 */
public static clickXpath(String xpath, int delay){
	WebUI.delay(delay);
	WebDriver driver = DriverFactory.getWebDriver();
	WebElement element = driver.findElement(By.xpath(xpath));
	element.click();
}

/**Inicia Brwoser Recebendo link por parametro
 * @author T_dvlopes
 * @param link
 */
public static void abreBrowser(String url){
	WebUI.openBrowser('');//Abre Google Chrome
	WebUI.delay(15); // Delay de 15 segundos 
	WebUI.maximizeWindow();// Maximisa o Chrome 
	WebUI.navigateToUrl(url); // Preenche a URL no Brwouser
}


/**
 * Cenário : SOLICITE UMA COTAÇÃO
 * 
 * O que usar :
 * 
 * 05/04/2023
 * clickXpath(xpath, 0)
 * preencheXpath( xpath,'Arnold Schwarzenegger', 0)
 * rpaCommands.alteraJanejaGenerica(1, 2)
 * 
 * 06/04/2023
 * selecionaItemListaEquals(String tipo,String xpath , String tag )
 * Explicando o método acima
 * List<WebElement> listOfElements
 * 
 * https://www.guru99.com/find-element-selenium.html
 * https://stackoverflow.com/questions/30773431/how-to-use-listwebelement-webdriver
 * 
 * 
 */

System.out.println()

/**
 * Exemplo
 */
String url = "https://www.allianz.com.br/"
abreBrowser(url)
String xpath
String valorTelaSolicitacao




System.out.println()











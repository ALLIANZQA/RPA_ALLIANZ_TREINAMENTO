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
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebElement







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

public static selecionaItemListaEquals(String tipo,String xpath , String tag ){

		}


/**
 * Cenário : SOLICITE UMA COTAÇÃO
 * 
 * O que usar:
 * 
 * 
 * clickXpath(xpath, 0)
 * preencheXpath( xpath,'Arnold Schwarzenegger', 0)
 * rpaCommands.alteraJanejaGenerica(1, 2)
 * 
 * https://www.guru99.com/find-element-selenium.html
 * https://stackoverflow.com/questions/30773431/how-to-use-listwebelement-webdriver
 * 
 *  
 * Método selecionaItemListaEquals:
 * 
 * Utilizado para receber uma seleção de elementos de uma página web selecionados por uma tag.
 * Exemplos de tags:  div, a, li, span. Dentre outros.
 *  
 * Lista de elemntos:
 * List<WebElement> listOfElements
 * https://stackoverflow.com/questions/30773431/how-to-use-listwebelement-webdriver*  
 * 
 * Especificações:
 * valor = texto á ser localizado na tag da lista de elementos 
 * tag =   div, a, li, span. Dentre outros 
 * xpath = usado para navegar por elementos e atributos 
 *  
 * selecionaItemListaEquals('Valor','xpath' , 'tag' )
 * 
 * 
 * Instrução import
 * https://www.geeksforgeeks.org/import-statement-in-java/
 * 
 * 
 * 
 * 
 * 
 */

/**
 * Métodos
 */
String url = "https://www.allianz.com.br/"
abreBrowser(url)
String xpath
String valorTelaSolicitacao
WebUI.delay(10);

//banner X 
xpath  = '//*[@id="onetrust-close-btn-container"]/button'
clickXpath(xpath, 0)
WebUI.delay(05);
 
//SEGUROS
xpath  = '//*[@id="seguros"]'
clickXpath(xpath, 0)
WebUI.delay(05);

//auto
xpath = '/html/body/div[1]/div/div[1]/div/div/div/header/div[2]/div/div/div/nav/ul/li[1]/div/div/ul/li[1]/ul/li[1]/a'
clickXpath(xpath, 0)

//Solicitaçao uma cotaçao
xpath = '/html/body/div[1]/div/main/div[1]/div/div/div[2]/div/div[4]/div/div/a'
clickXpath(xpath, 0)
rpaCommands.alteraJanejaGenerica(1, 2)
WebUI.delay(05);

//banner X
xpath  = '//*[@id="onetrust-close-btn-container"]/button'
clickXpath(xpath, 0)
WebUI.delay(05);

// Nome
xpath  = '//*[@id="name"]/div/div/label'
clickXpath(xpath, 0)

//NOME
preencheXpath( xpath,'Victor Lima', 0)

//CPF
xpath  ='//*[@id="i-registrationNumber"]'
preencheXpath( xpath,'48701891863', 0)

//E-MAIL
xpath  ='//*[@id="i-email"]'
preencheXpath( xpath,'victor.slima@outlook.com.br', 0)

//CONFIRMAR E-MAIL
xpath  ='//*[@id="i-confirm_email"]'
preencheXpath( xpath,'victor.slima@outlook.com.br', 0)

//TELEFONE
xpath  ='//*[@id="i-phone"]'
preencheXpath( xpath,'11991394134', 0)

//Mensagem
xpath = '//*[@id="whatsapp-contact"]/div/label/span'
clickXpath(xpath, 0)
WebUI.delay(05);

//Proximo
xpath = '//*[@id="step1"]/div[2]/button'
clickXpath(xpath, 0)

// CEP
xpath = '//*[@id="i-zipCode"]'
clickXpath(xpath, 0)

//CEP
preencheXpath( xpath,'06023901', 0)

//ESTADO
preencheXpath( xpath,'', 0)

//CIDADE
preencheXpath( xpath,'', 0)

//Proximo
WebUI.delay(02);
xpath = '//*[@id="step2"]/div[2]/button[1]'
clickXpath(xpath, 0)


//selecionaItemListaEquals('Valor','xpath' , 'tag' )
//Solicite aqui sua cotação!
selecionaItemListaEquals('Carro','//*[@id="interest1Title"]' , '//*[@id="interest1Title"]/option[2]' )
WebUI.delay(02);

//Nacional
selecionaItemListaEquals('Nacional','//*[@id="nationality"]' , '//*[@id="nationality"]/option[2]' )
WebUI.delay(02);

//0km
selecionaItemListaEquals('0Km','//*[@id="interest6Title"]' , '//*[@id="interest6Title"]/option[2]' )
WebUI.delay(02);

//Honda 
selecionaItemListaEquals('Honda','//*[@id="i-interest3Title"]'  )
 WebUI.delay(02);
 
xpath  ='//*[@id="i-interest5Title"]'
preencheXpath( xpath,'hvl30i20', 0)

System.out.println()











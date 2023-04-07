import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable

try {
	/**
	 * BDM-3036 - Robô - envio de pendência automática por e-mail – Santander
	 * Desenvolvimento:  18/08/2020
	 * 
	 * Atualizado:08/09/2020
	 */
     backoffice.EnvioPendenciaAutomaticaSantander.fluxoEnvioPendênciaAutomaticaSantander('PendenciaEmailSantander'); 
     WebUI.closeBrowser();
    } catch (Exception e) {
      System.err.println("Falha no envio de pendência automatica Santander"+ e);
      WebUI.closeBrowser();
    } 




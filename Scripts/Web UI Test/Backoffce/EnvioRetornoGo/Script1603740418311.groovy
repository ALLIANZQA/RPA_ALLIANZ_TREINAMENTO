
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


/**
 * 
 * @author be54955
 * BDM-3329 [BO] Robô de envio de e-mail retorno GO Gestão Operacional
 * Desenvolvimento: 26/10/2020
 */

try {
	  backoffice.EnvioRetornoGo.fluxoEnvioEnvioRetornoGo('RetornoGo');
      WebUI.closeBrowser();
    } catch (Exception e) {
      System.err.println("Falha no envio de retorno GO"+ e);
      WebUI.closeBrowser();
}


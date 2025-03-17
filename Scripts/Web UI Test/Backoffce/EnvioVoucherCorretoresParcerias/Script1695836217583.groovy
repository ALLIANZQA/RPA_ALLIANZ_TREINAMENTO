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
	 * 
	 * Desenvolvimento:  27/09/2023
	 * Atualização 24/11/2023 - 19:30 
	 * 
	 */
	 backoffice.EnvioVoucherCorretoresParcerias.fluxoEnvioVoucherCorretoresParcerias('EnvioVoucherCorretoresParcerias', 'PRD');
	 WebUI.closeBrowser();
	} catch (Exception e) {
	  backoffice.EnvioVoucherCorretoresParcerias.enviarEmailError("Error", 'EnvioVoucherCorretoresParcerias' );
	  System.err.println("Falha no envio de Envio Voucher Corretores Parceriasr"+ e);
	  WebUI.closeBrowser();
	}









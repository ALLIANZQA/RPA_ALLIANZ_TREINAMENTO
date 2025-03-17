import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

try {
	/**
	 * BDM-2861
	 * Distribuição automática Inspeção de risco
	 * Dev. 22/06/2020
	 * 
	 */
	 gcf.DistribuicaoAutomaticaInspecaoRiscoFuncionalidades.fluxoDistribuicaoAutomaticaInspecaoRiscoFuncionalidades('DistribuicaoAutomaticaIR')
	 WebUI.closeBrowser();
	}
	 catch (Exception e) {
	   gcf.DistribuicaoAutomaticaInspecaoRiscoFuncionalidades.enviarEmailError("Error", 'DistribuicaoAutomaticaIR' );
	   System.err.println("Falha ao realizar Distribuição automática Inspeção de risco\n" +e);
	   WebUI.closeBrowser();
	}
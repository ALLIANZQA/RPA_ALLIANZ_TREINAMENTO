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
	 * BDM-2176 / BRF-2797   edição mensal das Pré-Liquidações - Processos de Inspeção de Risco
	 * Atualizado 05/09/2020
	 * 
	 * Para executar: enviar
	 */
	sinistros.ProcessosInspecaoRisco.InspecaoRisco('InspecaoRisco');
	WebUI.closeBrowser();
	}
	 catch (Exception e) {
	   System.err.println("Falha ao realizar Agendamento Inspeções" +e);
	   WebUI.closeBrowser();
	}

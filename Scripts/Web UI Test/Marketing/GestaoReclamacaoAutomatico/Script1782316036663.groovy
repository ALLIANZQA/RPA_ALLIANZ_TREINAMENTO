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
import rpacommands.rpaCommands

try {
	/**
	 * Demanda IBLBDM-98285
	 * RPA - Hugme x Gestão de Reclamação Fase 2
	 * Desenvolvimento: 22/06/2026
	 *
	 */
	marketing.GestaoReclamacaoAutomaticoFuncionalidades.fluxoCotacaoRenovacaoInterna('GestaoReclamacaoAutomatico',"PRD");
	WebUI.closeBrowser();
 }
   catch (Exception e) {
   System.err.println("Falha ao executar fluxo de Gestao Reclamacao"+ e);
   marketing.GestaoReclamacaoAutomaticoFuncionalidades.enviarEmailError("Error", 'GestaoReclamacao' );
   WebUI.closeBrowser();
}




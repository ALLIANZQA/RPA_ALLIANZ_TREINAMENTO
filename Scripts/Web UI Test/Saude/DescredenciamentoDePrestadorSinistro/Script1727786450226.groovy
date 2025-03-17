import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
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



try {
	/**
	 * @author be54955
	 * IBLBDM-60099 
	 * [SAUDE] robô para descredenciamento de prestadores do Saúde Sinistros
	 * Saúde  Roteiro do Robô Descredenciamento - parte 2 - Campo Obrigatório 01 Código Profissional 1 - JMP JIRA (allianz.net)
	 * Data : 30/09/2024
	 *
	 * INT
	 *
	 * Para execuções em ambientes de produção usar:
	 * PRD
	 */
   saude.DescredenciamentoDePrestadorSinistroFuncionalidades.fluxoDescredenciamentoDePrestadorSinistro('DescredenciamentoSinistro', "PRD")
   WebUI.closeBrowser();
  } catch (Exception e) {
	saude.DescredenciamentoDePrestadorSinistroFuncionalidades.enviarEmailError("Error", 'DescredenciamentoSinistro' );
	System.err.println("Falha ao Realizar Descredenciamento de Prestadores Sinistro"+e);
	WebUI.closeBrowser();
 }
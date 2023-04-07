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


try{
	auto.RenovaEmissaoIncorretaFuncionalidades.fluxoCompletoRenovaEmissaoIncorreta("RenovaEmissaoIncorreta", "21/12/2020","23/12/2020","24/12/2020","25/12/2020","26/12/2020","27/12/2020","28/12/2020","29/12/2020","30/12/2020","31/12/2020","01/01/2021","02/01/2021","03/01/2021","04/01/2021","05/01/2021","06/01/2021","07/01/2021","08/01/2021","09/01/2021","10/01/2021","11/01/2021","12/01/2021","13/01/2021","18/01/2021","22/01/2021")
   } catch (Exception e){
	 System.err.println("Falha ao executar Renova Emissao Incorreta\n"+e)
   }


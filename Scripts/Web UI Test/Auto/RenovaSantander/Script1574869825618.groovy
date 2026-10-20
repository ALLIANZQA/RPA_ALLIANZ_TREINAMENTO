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



/**
 * @author be54955
 * 
 * Atualização 18/01/2024
 * 
 * Atualização na - classe RenovaSantanderFuncionalidades - método : telaDadosBasicos()
 * linha 977  de: if (bErrorPresent == true) para: if (bErrorPresent == !true) 
 * linha 988  de: if (bErrorPresent == true) para: if (bErrorPresent == !true) 
 * 
 * Atualização 04/03/2024
 * 
 * Atualização na - classe RenovaSantanderFuncionalidades - método : telaDadosBasicos()
 * linha 998  de: if (bErrorPresent == !true) para: if (bErrorPresent == true) 
 * linha 1008  de: if (bErrorPresent == !true) para: if (bErrorPresent == true)   
 * 
 * Atualização 05/04/2024 
 * 
 * PROD - Produção 
 * DEV - Desenvolvimento 
 * 
 * Desenvolvimento da classe "realizaLoginDevNetProducaoTransacionalCommands"
 * 
 */
try{
	 auto.RenovaSantanderFuncionalidades.fluxoInicialRenovaSantander("RenovaSantander",'PRD', "24/08/2026" )
	 WebUI.closeBrowser();
   } catch (Exception e){
	// auto.RenovaSantanderFuncionalidades.enviarEmailError("Error", "RenovaSantander" );
	 System.err.println("Falha ao executar Renova Santander\n"+e)
   }
   
   
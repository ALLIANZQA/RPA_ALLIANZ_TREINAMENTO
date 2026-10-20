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

try{
	/**
	 * NR - Carta NR
	 * 15 - Carta (-15d) 
	 * 30 - Carta (-30d)
	 * Atualização : 27/11/2023
	 * 
	 * * Atualização 11/04/2024 
     * 
     * PROD - Produção 
     * DEV - Desenvolvimento 
     * INT - Integração
     * 
     * Atualização: 20/05/2024
     * 
     * IBLBDM-49701 - [Nome Social]- Renov Autom Santander
     * Desenvolvimento de regras para preenchimento do nome do segurado na ausência do nome social.
     * 
     * Atualização 08/08/2024 
     * 
     * 
     * Remoção do texto : Você já sabe, mas vale lembrar, a SulAmérica Auto agora é Allianz!
     * Do envio de cartas 15D 
     * 
     * Atualização 22/10/2025 - IBLBDM-74087 
     * Ajuste na comunicação HTML de renovação automática
     * 
	 */
	 backoffice.SantanderEnvioDeMensagens.enviaMensagem('PRD','RenovaSantander','15',"01/11/2026")
	 WebUI.closeBrowser()
   } catch (Exception e){
	 System.err.println("Falha ao enviar cartas de aviso santander")
   }
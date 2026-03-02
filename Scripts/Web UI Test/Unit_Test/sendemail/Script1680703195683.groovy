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
import auxiliares.StringFormatter
import rpacommands.rpaCommands



String tipoAba = 'DescontosEspecíficosSinistro'
String path = 'C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Sinistro\\DescontosEspecíficosSinistro.xlsx'
String file = "DescontosEspecíficosSinistro.xlsx"

enviaEmailContactCenter(tipoAba, path, file)


public static enviaEmailContactCenter(String tipoAba, String path, String file){

	try{
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = file
//			sendEmail.CreateEmail.createEmailSendMultipleFiles('denilson.inmetricsext@allpronet.com.br',
//															   'denilson.inmetricsext@allpronet.com.br',
//																anexos,
//																'TMU-CS.MAIL.ALLIANZ',
//																'587',
//																tipoAba)
	
		
//			sendEmail.CreateEmail.createEmailSend('denilson.inmetricsext@allpronet.com.br', 
//				                                  'denilson.inmetricsext@allpronet.com.br', 
//												   anexos, 
//												   'TMU-CS.MAIL.ALLIANZ', 
//												   '587', 
//												   "CadastroDeRestricao",
//												   "2025"
//												    );
			
			sendEmail.CreateEmail.createEmailSendSantanderAnexos('denilson.inmetricsext@allpronet.com.br', 
				                                                 'denilson.inmetricsext@allpronet.com.br', 
																 'TMU-CS.MAIL.ALLIANZ', 
												                 '587', 
																 'Carta_AvisoRenovacao_15D', 
																 'DENILSON DIAS', 
																 '20252024', 
																 '2025', 
																 '2026', 
																 '10', 
																 anexos )
			
			} catch (Exception e){
	}
}
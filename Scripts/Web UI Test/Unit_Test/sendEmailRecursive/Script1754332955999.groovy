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







recursiveSendEmail()



public static void recursiveSendEmail(){
	System.out.println("\nRECURSIVO...\n")
	String tipoAba = 'DescontosEspecíficosSinistro'
	String path = 'C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Sinistro\\DescontosEspecíficosSinistro.xlsx'
	String file = "DescontosEspecíficosSinistro.xlsx"
	boolean b = true;
	while(b.equals(true)){
		enviaEmailContactCenter(tipoAba, path, file)
		b = true;
	}
	System.out.println("\nSAINDO DO RECURSIVO...\n")
}



public static enviaEmailContactCenter(String tipoAba, String path, String file){
	try{
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = file
			sendEmail.CreateEmail.createEmailSendMultipleFiles('denilson.inmetricsext@allpronet.com.br',
															   'teste@teste.com',
																'' ,
																anexos,
																'smtp.intrallianz.es',
																'25',
																tipoAba)
	} catch (Exception e){
	}
}
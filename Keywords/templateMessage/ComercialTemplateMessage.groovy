package templateMessage

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import sendEmail.MessageFactory

public class ComercialTemplateMessage extends MessageFactory{

	/**
	 * @author T_DCDias
	 * @return
	 * Altera presfil corretor
	 */
	public static recadastramentoSUSEP(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p style='background-color:DarkGray;' style='color:White;'><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				"Prezados,<br>"+
				"todos os dados foram processados.<br>"+
				"Att.."+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}
	
	/**
	 * @author be54955
	 * @param cabecalho
	 * @param rodape
	 * @return
	 */
	public static DesvinculaCodigoCorretor(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p style='background-color:DarkGray;' style='color:White;'><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				"Prezados,<br>"+
				"todos os dados foram processados.<br>"+
				"Att.."+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}
}

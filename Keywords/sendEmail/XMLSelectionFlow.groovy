package sendEmail

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

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

import internal.GlobalVariable

import org.jdom2.Namespace
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format
import org.jdom2.output.XMLOutputter;
import bean.MassaEmail

public class XMLSelectionFlow extends Xml{


	/**
	 * @author T_DCDias
	 * @param tipoDeSeguro
	 * @param cotacaoRequest
	 * @param casoDeTeste
	 * @param sch
	 * @return
	 */
	public static Element selectionFlowAuto(String tipoDeSeguro, Element cotacaoRequest, MassaEmail emailMassa, Namespace sch){
		switch (tipoDeSeguro){
			case "EmailPDF":
				return cotacaoRequest = sendEmail(cotacaoRequest, emailMassa, sch);
				break;
		}
	}
}

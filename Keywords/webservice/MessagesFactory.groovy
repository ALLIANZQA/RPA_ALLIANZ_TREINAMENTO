package webservice

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import javax.xml.soap.SOAPMessage

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

import javax.xml.bind.JAXBContext
import javax.xml.bind.Unmarshaller
import javax.xml.soap.MessageFactory
import javax.xml.stream.XMLInputFactory;

import org.w3c.dom.*;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;

public class MessagesFactory {




	/**
	 * @author Inmetrics
	 * @param xml
	 * @return
	 * 
	 * Recebe caminho do arquivo.
	 */
	private static SOAPMessage messageFactory(String xml){
		return MessageFactory.newInstance().createMessage(null, new FileInputStream(xml));
	}


	/**
	 * @author Inmetrics
	 * @param xml
	 * @return
	 * 
	 * Recebe o arquivo do tipo String (Formato XML), formata para o tipo inputString e retorna SOAPMessage
	 * 
	 */
	private static SOAPMessage messageFactoryRequestString(String xml){
		InputStream is = new ByteArrayInputStream(xml.getBytes());
		return MessageFactory.newInstance().createMessage(null, is);
	}
}

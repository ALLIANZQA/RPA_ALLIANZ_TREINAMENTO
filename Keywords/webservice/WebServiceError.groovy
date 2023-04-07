package webservice

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
import javax.xml.soap.SOAPMessage

public class WebServiceError {

	private String mensagemPacote
	private String statusPacote
	private String mensagemCobertura
	private String statusCobertura

	public void isEmpty(String isEmpty) throws AutomacaoException {

		if (isEmpty.isEmpty()) {
			throw new AutomacaoException("String está vazia");
		}
	}

	public void isEmptySOAPMessage(SOAPMessage soapMessage) throws AutomacaoException {

		if (soapMessage.toString().isEmpty()) {
			throw new AutomacaoException("SOAPMessage vazio no retorno do resquest");
		}
	}


	public boolean hasErrorResponse(String erro){

		boolean isErro = false;

		if (erro == "ERRO")	{

			isErro = true
		}
		return isErro;
	}

	public String getMensagemPacote() {
		return mensagemPacote;
	}

	public void setMensagemPacote(String mensagemPacote) {
		this.mensagemPacote = mensagemPacote;
	}

	public String getStatusPacote() {
		return statusPacote;
	}

	public void setStatusPacote(String statusPacote) {
		this.statusPacote = statusPacote;
	}

	public String getMensagemCobertura() {
		return mensagemCobertura;
	}

	public void setMensagemCobertura(String mensagemCobertura) {
		this.mensagemCobertura = mensagemCobertura;
	}

	public String getStatusCobertura() {
		return statusCobertura;
	}

	public void setStatusCobertura(String statusCobertura) {
		this.statusCobertura = statusCobertura;
	}
}


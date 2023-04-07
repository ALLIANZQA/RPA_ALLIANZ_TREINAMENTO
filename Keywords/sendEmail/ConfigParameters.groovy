package sendEmail;

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint;
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase;
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData;
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject;

import com.kms.katalon.core.annotation.Keyword;
import com.kms.katalon.core.checkpoint.Checkpoint;
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW;
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile;
import com.kms.katalon.core.model.FailureHandling;
import com.kms.katalon.core.testcase.TestCase;
import com.kms.katalon.core.testdata.TestData;
import com.kms.katalon.core.testobject.TestObject;
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS;
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI;

import internal.GlobalVariable;

public class ConfigParameters {

	protected String sender;
	protected String pwrsender;
	protected String emaildestination;
	protected String subject;
	protected String messagebory;


	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getPwrsender() {
		return pwrsender;
	}
	public void setPwrsender(String pwrsender) {
		this.pwrsender = pwrsender;
	}
	public String getEmaildestination() {
		return emaildestination;
	}
	public void setEmaildestination(String emaildestination) {
		this.emaildestination = emaildestination;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessagebory() {
		return messagebory;
	}
	public void setMessagebory(String messagebory) {
		this.messagebory = messagebory;
	}
}

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.util.List

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


String sender = 'denilson.inmetricsext@allpronet.com.br'
String ramo="Carta_AvisoRenovacao_15DA"
String emaildestination = 'denilson.inmetricsext@allpronet.com.br, andre.antunes@allianz.com.br'
String segurado = " MARTHA"
String dsmodelo = "HONDA CIVIC "
String versao = "SEDAN(N.Geracao) LXR 2.0 16V I-VTEC FLEXAut.4p";
String placa = "CAR2025"
String apolice = "411812662"
String vigencia = "12/12/2025"
String proposta = "196907756"

//enviarEmailAnexo15DA(sender,emaildestination,"Carta_AvisoRenovacao_15DA", segurado, dsmodelo,versao, placa, apolice,vigencia, proposta)

enviarEmailAnexoNR(sender, emaildestination,"Carta_AvisoNaoRenovacao", segurado, dsmodelo,versao, placa, apolice,vigencia, proposta)


public static boolean enviarEmailAnexo15DA(String sender, String emaildestination, String ramo, String segurado, String dsmodelo,String versao, String placa , String apolice, String vigencia, String proposta){
	System.err.println('Sender ['+sender+'] Ramo ['+ramo+"] Proposta ["+proposta+"]")
	List<String> anexos = new ArrayList<>();
	anexos[0] = "C:/Users/Public/"+proposta+".pdf"
	anexos[1] = proposta+".pdf"
	sendEmail.CreateEmail.createEmailSendSantanderAnexos(sender, emaildestination, ramo, segurado, apolice, vigencia, "", "",placa, "", dsmodelo, versao, anexos )
	return true;
}


public static boolean enviarEmailAnexoNR(String sender, String emaildestination, String ramo, String segurado, String dsmodelo,String versao, String placa , String apolice, String vigencia, String proposta){
	System.err.println('Sender ['+sender+'] Ramo ['+ramo+"] Proposta ["+proposta+"]")
	sendEmail.CreateEmail.createEmailSendSantanderSemAnexos(sender, emaildestination, ramo, segurado, apolice, vigencia, "", "",placa, "", dsmodelo, versao)
	return true;
}
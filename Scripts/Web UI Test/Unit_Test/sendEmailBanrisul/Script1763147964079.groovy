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

//String ramo ="AlteracoesRenovacaoBanrisul"
//String ramo ="naoRenovacaoBanrisul"
String sender ="denilson.inmetricsext@allpronet.com.br";
String emaildestination="denilson.inmetricsext@allpronet.com.br, andre.antunes@allianz.com.br";
String corretor="MARCELO DUARTE CORRETORA DE SEGUROS E TU"; 
String email="mdseguros@mdseguros.com.br"; 
String telefone="1332258000"; 
String susep="202075653"; 
String proponente="WLADIMIR DOS SANTOS PASSARELLI"; 
String cpfcnpj="070.048.818-90"; 
String apolice="01068431258040";
String finalVigencia="21/08/2025"; 
String nome="WLADIMIR DOS SANTOS PASSARELLI";


alteracoesRenovacaoBanrisul("AlteracoesRenovacaoBanrisul", sender, emaildestination, corretor,  email, telefone, susep,  proponente, cpfcnpj, apolice, finalVigencia, nome)

naoRenovacaoBanrisul("naoRenovacaoBanrisul", sender, emaildestination, corretor,  email, telefone, susep,  proponente, cpfcnpj, apolice, finalVigencia, nome)

//IBLBDM-77786
public static boolean alteracoesRenovacaoBanrisul(String ramo, String sender, String emaildestination,String corretor, String email, String telefone, String susep, String proponente, String cpfcnpj, String apolice, String finalVigencia, String nome){
	System.err.println('Sender ['+sender+'] Ramo ['+ramo+"] Proposta ["+corretor+"]")
	sendEmail.CreateEmail.createEmailBanrisul(ramo, sender, emaildestination, corretor, email, telefone, susep, proponente, cpfcnpj, apolice, finalVigencia, nome )
	return true;
}


//IBLBDM-77786
public static boolean naoRenovacaoBanrisul(String ramo, String sender, String emaildestination,String corretor, String email, String telefone, String susep, String proponente, String cpfcnpj, String apolice, String finalVigencia, String nome){
	System.err.println('Sender ['+sender+'] Ramo ['+ramo+"] Proposta ["+corretor+"]")
	sendEmail.CreateEmail.createEmailBanrisul(ramo, sender, emaildestination, corretor, email, telefone, susep, proponente, cpfcnpj, apolice, finalVigencia, nome )
	return true;
}





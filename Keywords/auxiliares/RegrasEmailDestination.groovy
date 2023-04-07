package auxiliares

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

public class RegrasEmailDestination {


	public static String retornarEmailDestination(String nome){

		String corretor = nome.trim();

		Map<String, String> email = new HashMap<String, String>()
		//email.put('AON HOLDINGS CORRETORES DE SEGUROS LTDA', 'Apoliceeletronica@aon.com');
		email.put('AON HOLDINGS CORRETORES DE SEGUROS LTDA', 'danillo.inmetricsext@allpronet.com.br');
		email.put('JLT', 'Opsib@jltbrasil.com');
		email.put('LOCKTON BRASIL CORRETORA DE SEGUROS LTDA', 'Operacional@br.lockton.com');
		email.put('MARSH CORRETORA DE SEGUROS LTDA.', 'mesaadm.brasil@marsh.com');
		email.put('LAZAM-MDS CORRETORA E ADMINISTRADORA DE SE', 'sp5@mdsinsure.com.br');
		email.put('Telefonica', 'reginaldo.paiva@telefonica.com');
		email.put('THB SP CONSULTORIA,GERENCIA DE RISCOS E CO', 'mesadeoperacoes@br.thbgroup.com');
		email.put('Willis', 'emissão@willis.com');
		println 'Email Destinatario ['+email.get(corretor)+']'

		return email.get(corretor.toString());
	}
}










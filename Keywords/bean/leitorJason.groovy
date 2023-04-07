package bean

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.fasterxml.jackson.databind.ObjectMapper
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

import internal.GlobalVariable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JTabbedPane.Page
import org.eclipse.persistence.internal.oxm.record.json.JSONParser
import org.json.JSONObject

//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

public class leitorJson {

	ObjectMapper objectMapper = new ObjectMapper();
	
	//private String user;
	//private String senha;

//	public static void leituraJson(){
//
//		try {
//
//			Page page = objectMapper.readValue(new File("sampleJSONFile.json"), Page.class);
//			
//		private	String user = JsonPath.read(user, "$.pageInfo.pageUser");
//		private	String senha = JsonPath.read(senha, "$.pageInfo.pageSenha");
//
//			printParsedObject(page);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
}




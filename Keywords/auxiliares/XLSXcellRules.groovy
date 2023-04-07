package auxiliares

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row

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

import bean.MassaDados
import internal.GlobalVariable

public class XLSXcellRules {

	/**
	 * @author T_DCDias
	 * @param row
	 * @param line
	 * @param value
	 * @return
	 */
	public static boolean verificaStatus(Row row, int line, String value){
		try{
			if(row.getCell(line).getStringCellValue().trim().equals(value)){
				return true;
			} else {
				return false
			}
		} catch (Exception e){
			e
		}
	}

	/**@author T_Dvlopes
	 * @param row
	 * @param line
	 * @param value
	 * @return
	 */
	public static boolean verificaStop(Row row, int line, String value){
		try{
			if(row.getCell(line).getStringCellValue().trim().contains('Stop')){
				return true;
			} else {
				return false
			}
		} catch (Exception e){
			e
		}
	}
}
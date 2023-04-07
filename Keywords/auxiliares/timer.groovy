package auxiliares

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

public class timer {

	/**
	 * @author BE54955
	 * @param t
	 * @param p
	 * @return
	 */
	public static boolean executeAtTime(int t, int p){
		boolean hs = false
		for(int i = 0 ; i<1000 ; i++){
			if(compareTime(t)){
				hs = true
				break
			}
			WebUI.delay(p)
		}
		return hs
	}

	/**
	 * @author BE54955
	 * @param t
	 * @return
	 */
	public static boolean compareTime(int t){
		boolean h = false
		String hora = ExtractDates.horaAtualizada()
		int nHora = Integer.parseInt(hora.replace(":", ""))
		System.out.println("Hora atual ["+nHora+"] Hora esperada ["+t+"]")
		if(nHora==t){
			h = true
		}
		return h
	}
}

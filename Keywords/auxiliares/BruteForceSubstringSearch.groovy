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

public class BruteForceSubstringSearch {



	/**
	 * @author be54955
	 * @param pat
	 * @param txt
	 * @return
	 */
	public static int searchIndex(String pat, String txt) {
		int j, M = pat.length();
		int i, N = txt.length();

		for (i = 0; i <= N - M; i++) {
			for (j = 0; j < M; j++)
				if (txt.charAt(i+j) != pat.charAt(j))
					break;
			if (j == M) return i;
		}
		return N;
	}

	/**
	 * @author be54955	
	 * @param t
	 * @param index1
	 * @param index2
	 * @return
	 */
	public static String substringValue(String t, int index1, int index2){
		return t.substring(index1, index2)
	}
}

package auxiliares;

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

import java.text.NumberFormat;


import internal.GlobalVariable;

public class CurrencyMonetary {

	/**
	 * @author T_DCDias
	 * @param valor
	 * @return
	 * Recebe um valor em String , ex.: 3000 e retorna o valor monetario R$ 3000,00
	 */
	public static String currencyMonetaryValue(String valor){
		String sValor;
		BigDecimal big = new BigDecimal(valor);
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return sValor = nf.format(big).toString().trim();
	}

	/**
	 * @author T_DCDias
	 * @param valor
	 * @return
	 * Recebe um valor monetário e retorna apenas os números 
	 */
	public static String removeValorMonetario(String[] valor){
		StringBuffer sbf = new StringBuffer();
		String snum;
		for(int i=0; i < valor.size(); i++ ) {
			if(!valor[i].equals("R") && !valor[i].equals(",") && !valor[i].equals(".") && !valor[i].equals("\$") ) {
				sbf.append(valor[i].toString());
			}
		}
		snum = sbf.toString();
		return snum;
	}
}

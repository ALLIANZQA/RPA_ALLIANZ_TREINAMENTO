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

public class RegrasLeitura {

	/**
	 * Recebe um valor por parametro, realiza depara.
	 *  
	 * 
	 *  A = PROCESSAR
	 *  P = PROCESSADO 
	 *  
	 *  E = ERRO - REPROCESSAR
	 *  NÃO ENVIADO = REPROCESSAR	
	 *    
	 *  ENVIADO = FINALIZOU	  
	 *  PROCESSADO = FINALIZOU
	 *   	   
	 *  N = ERRO - NÃO REPROCESSAR
	 *  Não Enviado [ERRO] = NÃO REPOROCESSAR
	 *  APLICA INEXISTENTE = NÃO REPOROCESSAR
	 *  
	 *  
	 * @param conteudo
	 * @return
	 */
	public static String regrasLeituraExcel(String conteudo){
		String valorRetornado
		Map<String, String> conteudoAprovado = new HashMap<String, String>();
		conteudoAprovado.put("P",'P');
		conteudoAprovado.put("N",'N');
		conteudoAprovado.put("enviado",'enviado');
		conteudoAprovado.put("Processado",'Processado');
		conteudoAprovado.put("Não Enviado [ERRO]",'Não Enviado [ERRO]');
		conteudoAprovado.put("APLICA INEXISTENTE",'APLICA INEXISTENTE');
		valorRetornado = conteudoAprovado.get(conteudo);
		if(valorRetornado.equals(null)){
			valorRetornado = '*';
		}


		return valorRetornado
	}

	/**
	 * Recebe um valor por parametro, realiza depara.
	 * 
	 *  Stop = break
	 * 
	 * @param conteudo
	 * @return
	 */
	public static String regrasLeituraExcelStop(String conteudo){
		String valorRetornado
		Map<String, String> conteudoAprovado = new HashMap<String, String>();
		conteudoAprovado.put("Stop",'Stop');
		valorRetornado = conteudoAprovado.get(conteudo);
		if(valorRetornado.equals(null)){
			valorRetornado = '*';
		}
		return valorRetornado
	}
}

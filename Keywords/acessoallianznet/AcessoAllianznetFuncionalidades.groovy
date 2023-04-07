package acessoallianznet

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

import backoffice.backofficeFuncionalidades
import internal.GlobalVariable
import rpacommands.rpaCommands
import auxiliares.ExtractDates

public class AcessoAllianznetFuncionalidades  extends backofficeFuncionalidades {
	
	private static TestData data
	private static String testDataPath = 'Data Files/AcessoAllianznetProducao/AcessoAllianznetProducaoData'
	private static String path
	private static String alerta
	private static int cellCheck = 1

	
	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static AcessoAllianznetProducao(String tipoAba){
		initDataFiles()
		fluxoAcessoAllianznetProducao(tipoAba)
		reprocessaFalhas(tipoAba)
	}
	
	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static fluxoAcessoAllianznetProducao(String aba){
		try{
			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				System.out.println("["+i+"] [" +data.getValue(cellCheck, i)+"]")
				if(data.getValue(cellCheck, i).equals('Processar')) {
			        if(acesso(aba)){
				       try{
					       auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 0, aba);
					       auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta+" Data atual: "+ExtractDates.dataAtualizada(), 1, aba);
				       } catch (Exception e){}
					   WebUI.closeBrowser()
					   WebUI.delay(600);
			        }else {
			           try{
				           auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 0, aba);
				           auxiliares.WriteExcelFactory.updateSheetLine("", j, path,alerta+" Data atual: "+ExtractDates.dataAtualizada(), 2, aba);
			           } catch (Exception e){}
					   WebUI.closeBrowser()
					   WebUI.delay(600);
			        }
				  }
				
			  }
		 } catch (Exception e){}
	}
	
	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static boolean acesso(String tipoAba){
		System.out.println()
		boolean a
		try{
			alerta = ""
			iniciaAplicacaoMatriz('AllianznetProdução');
			realizaLoginAllianzNet(tipoAba);
			WebUI.waitForPageLoad(15)
			alerta = recuperaValor('/html/body/div[1]/div/app-root/app-public/app-public-home/div/div/div/app-login/div/form/div[1]/div/nx-message/div/div/span', "innerText", 0)
			if(!alertListBlocList(alerta)){
			   alerta = ""
			   if(listaDeTelas(recuperaValor('//*[@id="link_new_policy"]/span', "outerText", 0))){
				  alerta = recuperaValor('/*/body/div/div/app-root/app-private/app-private-header/app-header/div/div[1]/div[3]/app-session-timer/div', "innerText", 0)
				  a = true 
 			   } else {
			      a = false 
			      alerta = "Tela - Nova Cotação - Não apresentada"
 			   }
			} else {
			   a = false
			   alerta
			}
		}catch (Exception e){}
		//System.out.println("["+a+"]")
		return a 
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String tipoAba){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
			fluxoAcessoAllianznetProducao(tipoAba)
			reprocessaFalhas(tipoAba)
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		path = data.sourceUrl
	}
	
}

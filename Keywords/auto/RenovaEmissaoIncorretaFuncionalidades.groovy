package auto

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
import auto.RenovaSantanderFuncionalidades
import auxiliares.MenuMatriz
import auxiliares.ExtractDates

public class RenovaEmissaoIncorretaFuncionalidades extends RenovaSantanderSasanFuncionalidades{



	private static String testDataPathRenIncorreta
	private static String tabCapPathsasam


	/**
	 * @author be54955
	 * @return
	 */
	public static iniURLDateFiles(){
		testDataPathRenIncorreta = 'Data Files/Auto/RenovacoesSantanderData/RenovaEmissaoIncorretaData'
		tabCapPathsasam = 'Data Files/Auto/RenovacoesSantanderData/TabCap (1)'
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static fluxoCompletoRenovaEmissaoIncorreta(String tipoAba, String[] dataParaExecutar){
		aba = tipoAba
		iniURLDateFiles()
		initDataFiles(testDataPathRenIncorreta, tabCapPathsasam)
		try{
			for(int i = 0; i < dataParaExecutar.size(); i++){
				System.err.println('Data ['+i+'] ['+dataParaExecutar[i]+']')
				if(validaDataParaPorcessar(dataParaExecutar[i])){
					renovaEmissaoIncorreta(tipoAba, dataParaExecutar[i])
					enviarEmailRenovaSantander(tipoAba, dataParaExecutar[i])
				}
			}
		} catch (Exception e){
			System.err.println("["+e+"]")
			fluxoCompletoRenovaEmissaoIncorreta(tipoAba, dataParaExecutar)
			//finalizar()
		}
		WebUI.closeBrowser()
		finalizar()
	}


	/**
	 * @author T_DCDias
	 * @return
	 */
	public static renovaEmissaoIncorreta(String tipoAba, String dataParaExecutar){
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands(tipoAba);
		WebUI.delay(10)
		System.err.println()
		menuProducaoSantander()
		fluxoRenovaSantander(tipoAba, dataParaExecutar)
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static menuProducaoSantander(){
		String xpathProducaoSinistros = '/html/body/div/div/app-root/app-private/app-private-header/nx-epac-header/nx-epac-navigation/div/div[1]/nx-epac-navigation-desktop/div[1]/div/div/ul/li[1]/a/span'
		clickXpath(xpathProducaoSinistros, 5)
		//Produção
		MenuMatriz.subMenu('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul', "a","Produção")
		//Menu Produção
		WebUI.waitForPageLoad(30)
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()
	}
}

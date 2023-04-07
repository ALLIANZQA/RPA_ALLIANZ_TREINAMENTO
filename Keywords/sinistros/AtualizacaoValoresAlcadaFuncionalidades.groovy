package sinistros


import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import java.util.List
import org.openqa.selenium.WebElement
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
import org.openqa.selenium.remote.RemoteWebDriver
import rpacommands.rpaCommands
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import org.openqa.selenium.Keys as Keys
import auxiliares.WriteExcelFactory;
import sendEmail.envioDeEmail
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.MenuMatriz
import auxiliares.ExtractDates

public class AtualizacaoValoresAlcadaFuncionalidades extends rpaCommands {

	private static TestData data
	private static String testDataPath = 'Data Files/Sinistro/AtualizacaoValoresAlcadaData'
	private static String path
	private static String alerta
	private static int cellCheck = 5
	private static String status





	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		System.out.println(data.getRowNumbers())
		path = data.sourceUrl
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static boolean validaDados(int cellStatus){
		boolean value
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(cellStatus, i).equals('Processar')) {
				value = true
				break;
			} else {
				value = false
			}
		}
		System.err.println('Data ['+value+']')
		return value
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static List<String> lista( List<String> registros , int v){
		try{
			for (int i = 1; i <= data.getColumnNumbers(); i++) {
				int d = i -1
				registros[d] = data.getValue(i, v).trim()
			}

			for (int j = 0; j < registros.size();j++ ){
				System.err.println("["+j+"] ["+registros[j]+"]")
			}

			return registros
		}  catch (Exception e){
			System.err.println('\n['+e+']')
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoAtualizacaoValoresAlcada(String aba, String a){
		initDataFiles()
		fluxoCompletoAtualizacaoValoresAlcada(aba,a)
		reprocessaFalhas(aba,a)
		enviaEmailAtualizacaoValoresAlcada(aba)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoAtualizacaoValoresAlcada(aba,String a){
		System.err.println()
		try{
			if(validaDados(cellCheck)){
				acessoAmbiente(aba,a)
				WebUI.delay(15);
				MenuMatriz.manutancaoGeral()
				MenuMatriz.subMenu("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]", "a","Nivel de autorização de pagamentos")
				WebUI.delay(20);
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					if (data.getValue(cellCheck, i).equals('Processar')) {
						alerta=""
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						if(alcada(registros, aba)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("",j, path, status, 4, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("",j, path, alerta+" - "+ExtractDates.dataAtualizada(), 5,  aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 4, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta+" - "+ExtractDates.dataAtualizada(), 5,  aba);
							} catch (Exception e){}
						}
					}
				}
			} else {
				System.err.println('Não existem dados para processar')
			}
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param aba
	 * @return
	 */
	public static boolean alcada( List<String> registros, String aba){
		System.out.println()
		boolean enviado
		String msg
		try{
			alteraJanejaGenerica(1, 2)
			WebUI.maximizeWindow()
			//NIVEL AUTORIZACIÓN PAGOS
			String telaNivelAutorizacao = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(telaNivelAutorizacao )
			if(listaDeTelas(telaNivelAutorizacao)){
				//Ramo
				limpaCampo('//*[@id="ramo"]', 0)
				preencheXpath('//*[@id="ramo"]', registros[0],0);
				//Garantia
				limpaCampo('//*[@id="garantia"]', 0)
				preencheXpath('//*[@id="garantia"]', registros[1],0);
				//Alta
				clickXpath('//*[@id="o_4"]', 1);
				if(verificavALORiMP()){
					//Imp. hasta
					clickXpath('//*[@id="impohasta"]', 0);
					limpaCampo('//*[@id="impohasta"]', 0)
					preencheXpath('//*[@id="impohasta"]', registros[3],0);
					commandTAB('//*[@id="impohasta"]')
					WebUI.delay(2);
					//Nivel
					selecionaItemLista(registros[2], '//*[@id="nivel"]', 'option')
					//Alta Confirmação
					clickXpath('//*[@id="o_3"]', 1);
					msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
					if(alertListBlocList(msg)){
						enviado = false
						alerta=msg
						status = "Processado"
						clickXpath('//*[@id="imgOptMenuBackHome"]', 1);
						WebUI.delay(2);
					} else {
						enviado = true
						alerta=msg
						status = "Processado"
						clickXpath('//*[@id="imgOptMenuBackHome"]', 1);
						WebUI.delay(2);
					}

				} else {
					enviado = false
					alerta="Registro não efetuado, verificar a importância"
					status = "Processado"
					clickXpath('//*[@id="imgOptMenuBackHome"]', 1);
					WebUI.delay(2);
				}
			} else {
				enviado = false
				alerta="Tela 'NIVEL AUTORIZACIÓN PAGOS.' não apresentada"
				status = "Processar"
				WebUI.closeBrowser();
				iniciaAplicacaoMatriz('IntranetProdução');
				realizaLoginIntraNetProducao(aba);
				WebUI.delay(15);
				MenuMatriz.manutancaoGeral()
				MenuMatriz.subMenu("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]", "a","Nivel de autorização de pagamentos")
				WebUI.delay(20);
			}
		} catch (Exception e) {
			enviado = false
			alerta="Tela 'NIVEL AUTORIZACIÓN PAGOS.' não apresentada"
			status = "Processar"
			alteraJanejaGenerica(0, 2)
			iniciaAplicacaoMatriz('IntranetProdução');
			realizaLoginIntraNetProducao(aba);
			WebUI.delay(15);
			MenuMatriz.manutancaoGeral()
			MenuMatriz.subMenu("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]", "a","Nivel de autorização de pagamentos")
			WebUI.delay(15);
		}
		return enviado
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba, String a){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoAtualizacaoValoresAlcada(aba, a)
			reprocessaFalhas(aba, a)
		}
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailAtualizacaoValoresAlcada(String tipoAba){
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Sinistros\\AtualizacaoValoresAlcada\\ConfiguracoesAtualizacaoValoresAlcada.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "AtualizacaoValoresAlcada.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
			}
		} catch (Exception e){
		}
	}

	/**
	 * @author BE54955
	 * @return
	 * 19/01/23:"imp" fixado em "true" para editar campo "Imp. hasta" da tela "NIVEL AUTORIZACIÓN PAGOS"
	 */
	public static boolean verificavALORiMP(){
		System.out.println()
		boolean imp
		try{
			String strImp = recuperaValor('//*[@id="impodesde"]', 'defaultValue', 1)
			String t = strImp.replace(",","")
			int n = Integer.parseInt(t)
			if(n==0){
				imp = true
			} else{
				imp = true
			}
			System.out.println("["+n+"] ["+imp+"]")
		}catch (Exception e){
			imp = false
		}
		return imp
	}

}

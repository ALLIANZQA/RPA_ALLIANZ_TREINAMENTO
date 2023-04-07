package backoffice

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.util.List

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
import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import auxiliares.RegrasEmailDestination
import auxiliares.MenuMatriz
import internal.GlobalVariable
import rpacommands.rpaCommands

public class GeraBoletoFuncionalidades extends rpaCommands {

	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/GeraBoleto/GeraBoletoData'
	private static String path
	private static String alerta
	private static int cellCheck = 4
	private static String filepath = "C:\\Users\\Public\\"

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
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
		for (int i = 1; i <= data.getColumnNumbers(); i++) {
			int d = i -1
			registros[d] = data.getValue(i, v).trim()
		}

		for (int j = 0; j < registros.size();j++ ){
			System.err.println("["+j+"] ["+registros[j]+"]")
		}

		return registros
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param aba
	 * @return
	 */
	public static acessoAmbiente(String aba){
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands(aba);
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoGeraBoleto(String aba){
		initDataFiles()
		fluxoCompletoGeraBoleto(aba)
		reprocessaFalhas(aba)
		enviarEmailGeraBoleto(aba)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoGeraBoleto(aba){
		try{
			if(validaDados(cellCheck)){
				acessoAmbiente(aba)
				WebUI.delay(10);
				MenuMatriz.producaoSinistro()
				MenuMatriz.subMenu("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[4]/div/div[1]/ul", "a", "Duplicados Recibos")
				//WebUI.delay(17);
				WebUI.waitForPageLoad(20)
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						if(gerarBoleto(registros, aba)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 3, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 4, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 3, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Falha ao gerar boleto. "+alerta, 4, aba);
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
	 * @param emaildestination
	 * @param corretor
	 * @param intermediario
	 * @param cpfcnpj
	 * @param user
	 * @param pwr
	 * @param ramo
	 * @return
	 */
	public static boolean gerarBoleto( List<String> registros, String aba){
		boolean enviado
		System.out.println()
		try{
			alerta=""

			/**
			 * TELA - GESTÃO PROCESSOS
			 */
			alteraJanejaGenerica(1, 5)
			/**
			 * TELA - 2ª VIA-RECIBO
			 */
			//Numero Recibo
			limpaCampo('//*[@id="numRecibo"]',0)
			preencheXpath('//*[@id="numRecibo"]', registros[0], 0);
			//Limite Máximo
			limpaCampo('//*[@id="fechajuros"]',0)
			preencheXpath('//*[@id="fechajuros"]', registros[1], 0);
			commandTAB('//*[@id="fechajuros"]')
			validaJuros(registros[2])
			//Aceitar
			clickXpath('//*[@id="Inadimplencia"]', 0);
			WebUI.delay(10);
			alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 2)
			System.err.println("ALERTA ["+alerta+"]")
			WebUI.delay(2);
			alteraJanejaGenerica(2, 2)
			fecharJanela()
			alteraJanejaGenerica(1, 5)

			if(alerta.contains("atributo nao encontrado")){
				alerta = ""
			}
			enviado = true

			try{
				deleteFileDiretory("C:\\Users\\Public\\AllianzSegurosBrasil.pdf")
			} catch (Exception e){}

			try{
				deleteFileDiretory("C:\\Users\\Public\\AllianzSegurosBrasil (1).pdf")
			} catch (Exception e){}

			try{
				deleteFileDiretory("C:\\Users\\Public\\AllianzSegurosBrasil (2).pdf")
			} catch (Exception e){}

		} catch (Exception e){
			enviado = false
		}
		return enviado
	}


	/**
	 * @author be54955
	 * @param j
	 * @return
	 */
	public static validaJuros(String j){
		if(j.equals("Sim")){
			//checkbox juros
			clickXpath('//*[@id="juros"]', 0);
		}
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailGeraBoleto(String tipoAba){
		initDataFiles()
		if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\GeraBoleto\\ConfiguracoesGeraBoleto.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = "GeraBoleto.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoGeraBoleto(aba)
			reprocessaFalhas(aba)
		}
	}
}

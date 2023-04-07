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

public class RecusaPropostaFuncionalidades extends rpaCommands{

	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/RecusaProposta/RecusaPropostaData'
	private static String path
	private static String alerta
	private static int cellCheck = 2


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
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoGeraRecusaProposta(String aba, String a){
		initDataFiles()
		fluxoCompletoRecusaProposta(aba,a)
		reprocessaFalhas(aba,a)
		enviarEmailRecusaProposta(aba)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @param a
	 * @return
	 */
	public static init(String aba, String a){
		try{
			acessoAmbiente(aba,a)
			WebUI.delay(10);
			MenuMatriz.producaoSinistro()
			MenuMatriz.producaoINTPRD()
			alteraJanejaGenerica(1, 10)
			WebUI.delay(10);
		} catch (Exception e){}
	}


	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoRecusaProposta(String aba, String a){
		try{
			if(validaDados(cellCheck)){
				init(aba,a)
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					alerta = ''
					if (data.getValue(cellCheck, i).equals('Processar')) {
						if (menuProdução()){
							List<String> registros = new ArrayList<>();
							lista( registros , i)
							if(recusaProposta(registros)) {
								try{
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 1, aba);
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 2, aba);
								} catch (Exception e){}
							} else {
								try{
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 1, aba);
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 2, aba);
								} catch (Exception e){}
							}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 1, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 2, aba);
							} catch (Exception e){}
							WebUI.closeBrowser();
							init(aba,a)
						}
					}
				}
			}
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean recusaProposta(List<String> registros){
		boolean b = true
		try{
			if(listaBloqueios(registros)){
				if(resultadoOperacional()){
				} else {
					b = false
				}
			} else {
				b = false
			}
			//HOME
			clickXpath('//*[@id="imgOptMenuBackHome"]', 2);
		} catch (Exception e){
			b = false
		}
		//System.out.println('Recusa Proposta ['+b+']')
		return b
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean menuProdução(){
		System.err.println()
		boolean b
		try{
			WebUI.waitForPageLoad(10)
			String s = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).toString()
			System.err.println(s)
			if(listaDeTelas(s)){
				clickXpath('//*[@id="pBloqueadas"]', 2);
				b = true
			} else {
				alerta = "Tela - Menu Produção não apresentada"
				b = false
			}
		} catch (Exception e){
			b = false
		}
		System.err.println("Tela - Menu Proção não apresentada ["+b+"]")
		return b
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean listaBloqueios(List<String> registros){
		System.err.println()
		boolean b
		try{
			WebUI.waitForPageLoad(10)
			String s = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).toString()
			//System.err.println(s)
			if(listaDeTelas(s)){
				//Proposta
				preencheXpath('//*[@id="poliza_Policy"]', registros[0], 0);
				//Pesquisar
				clickXpath('//*[@id="btnBuscar"]', 2);
				if(!registrarMSGAlerta()){
					//LISTA DESBLOQUEIOS
					if(selecionaItemListaDesbloqueio("Excluir", '//*[@id="tablaBloqueos_tbody"]', 'img', 'title')){
						String alert = pegaMSGAlerta()
						System.err.println(alert)
						if(alertListBlocList(alert)){
							fechaAlerta()
						}
					}
					b = true
				} else {
					// System.err.println(alerta)
					b = false
				}
			} else {
				alerta = "Tela - Lista Bloqueios"
				b = false
			}
		} catch (Exception e){
			b = false
		}
		//System.err.println("Tela - Lista Bloqueios ["+b+"]")
		return b
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean resultadoOperacional(){
		System.err.println()
		boolean b
		try{
			alteraJanejaGenerica(2, 5)
			WebUI.delay(5);
			String s = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0).toString()
			//System.err.println(s)
			if(listaDeTelas(s)){
				String msgResultado = recuperaValor('//*[@id="null"]', 'innerText', 0)
				String resultado = recuperaValor('//*[@id="poliza"]', 'innerText', 0)
				alerta = msgResultado+' '+resultado
				System.err.println(alerta)
				//FECHAR
				clickXpath('//*[@id="o_2"]', 2);
				b = true
				alteraJanejaGenerica(1, 5)
			} else {
				alerta = "Tela - Resultado Operacional"
				b = false
			}
		} catch (Exception e){
			b = false
		}
		//System.err.println("Tela - Resultado Operacional ["+b+"]")
		return b
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaItemListaDesbloqueio(String tipo,String xpath , String tag, String attrib ){
		boolean f = false
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getAttribute(attrib).contains(tipo)){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getAttribute(attrib)+"]")
				listOfElements[i].click()
				f = true
				break
			}
		}
		return f
		WebUI.delay(2)
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailRecusaProposta(String tipoAba){
		initDataFiles()
		if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\RecusaProposta\\ConfiguracoesRecusaProposta.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = "RecusaProposta.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
		}
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
			fluxoCompletoRecusaProposta(aba,a)
			reprocessaFalhas(aba)
		}
	}


	/**
	 * @author be54955
	 */
	public static boolean registrarMSGAlerta(){
		boolean b
		String msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 0)
		if(alertListBlocList(msg)){
			alerta = msg
			b = true
		} else {
			b = false
		}
		return b
	}

}

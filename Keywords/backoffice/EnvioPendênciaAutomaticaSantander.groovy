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

public class EnvioPendenciaAutomaticaSantander extends rpaCommands {

	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/EnvioPendenciaAutomaticaEmailSantander/EnvioPendenciaAutomaticaEmailSantandeData'
	private static String path
	private static String alerta
	private static int cellCheck = 25
	private static String filepath = "C:\\Users\\Public\\"
	private static texto


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
	public static fluxoEnvioPendênciaAutomaticaSantander(String aba){
		initDataFiles()
		fluxoCompletoEnvioPendênciaAutomaticaSantander(aba)
		reprocessaFalhas(aba)
		enviaEmailPendneciaFinalProcesso(aba)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoEnvioPendênciaAutomaticaSantander(aba){
		if(validaDados(cellCheck)){
			acessoAmbiente(aba)
			WebUI.delay(10);
			MenuMatriz.consultasEstatisticas()
			MenuMatriz.fichaGestao()
			WebUI.delay(17);
			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				if (data.getValue(cellCheck, i).equals('Processar')) {
					List<String> registros = new ArrayList<>();
					lista( registros , i)
					if(pendencia(registros, aba)) {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 24, aba);
						} catch (Exception e){}
					} else {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 24, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Pendência não localizada", 25, aba);
						} catch (Exception e){}
					}
				}
			}
		} else {
			System.err.println('Não existem dados para processar')
		}
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
	public static boolean pendencia( List<String> registros, String aba){
		boolean enviado
		try{
			if(registros[22].equals("7012")){
				/**
				 * TELA - GESTÃO PROCESSOS
				 */
				alteraJanejaGenerica(1, 5)
				//Processo
				selecionaProcesso(registros[1])
				//apolice
				limpaCampo('//*[@id="referencia"]',1)
				preencheXpath('//*[@id="referencia"]', registros[0], 1);
				//pesquisar
				clickXpath('//*[@id="btBuscar"]', 5);
				//PSeleciona Nota
				WebUI.delay(10);
				if(selecionaItemNaTabela(registros[22])){
					if(enviaEmailPendnecia(texto, registros[21])){
						enviado = true
					}else {
						enviado = false
					}
				}else {
					enviado = false
				}
			}else if(registros[22].equals("Não")) {
				if(enviaEmailPendnecia(registros[23], registros[21])){
					enviado = true
				}else {
					enviado = true
				}
			}

		} catch (Exception e){
			enviado = false
		}
		return enviado
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
			fluxoCompletoEnvioPendênciaAutomaticaSantander(aba)
			reprocessaFalhas(aba)
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaProcesso(String processo){
		String xpath = '//*[@id="tipoReferencia"]'
		String tag ="option";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().contains(processo.toString())){
				listOfElements[i].click()
				break
			}
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean selecionaItemNaTabela(String codNota){
		boolean encontrado
		int paginas = identificaNumPaginas()
		boolean paginar = true
		System.err.println('paginas ['+paginas+']')
		WebUI.delay(2)
		for(int p = 0; p < paginas; p++ ){
			System.err.println('pagina ['+p+'] paginar ['+paginar+']')
			if(paginar){
				paginar = encontraItemNaTabela(codNota)

				if(!paginar){
					encontrado = true
				} else {
					encontrado = false
				}
			}
			if(paginar){
				try{
					clickXpath('//*[@id="o_7"]', 5);
				} catch (Exception e){}
			}
		}
		return encontrado
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static int identificaNumPaginas(){
		return  Integer.parseInt(recuperaValor('//*[@id="lbPagina"]', 'innerText', 2).replaceAll('Página 1 de ', ''))
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param data
	 * @param nota
	 * @return
	 */
	public static boolean encontraItemNaTabela(String codNota){
		boolean paginar
		String xpath = '//*[@id="SectionListContent"]'
		String tag ="td";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		int size = listOfElements.size()
		//		System.err.println('size  ['+ size+']' )
		for(int i = 0; i < size; i++){
			String linha =  recuperaValor('//*[@id="row_'+i+'_listDatos"]', 'innerText', 2)
			System.err.print('*')
			if(linha.contains(codNota.toString()) ){
				System.err.println('\n['+i+'] FOUND ['+ linha+']\n' )
				clickXpath('//*[@id="row_'+i+'_listDatos"]', 5);
				WebUI.delay(15);
				alteraJanejaGenerica(2, 5)
				WebUI.delay(2)
				if(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2).equals("CONSULTA NOTAS - LI")){
					//RECUPERAR TEXTO DA PENDENCIA
					texto = recuperaValor('//*[@id="textAreaNota"]', 'defaultValue', 2)
					clickXpath('//*[@id="lnkTopLink_Close"]', 5);
					fechaAlerta();
					alteraJanejaGenerica(1, 5)
				}
				paginar = false
				break
			} else if(linha.contains('atributo nao encontrado'.toString())){
				paginar = true
				i = size
				System.err.println('index  ['+i+']' )
			}
		}
		return  paginar
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static boolean enviaEmailPendnecia(String t, String s){
		boolean enviado
		try{
			System.err.println(t)
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\EnvioPendenciaAutomaticaSantander\\ConfiguracoesEnvioPendenciaAutomaticaSantander.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
	//		String emaildestination = "denilson.inmetricsext@allpronet.com.br, bruna.klassmann@allianz.com.br, carlos.gomes@allianz.com.br, andre.antunes@allianz.com.br"
			
			if(sendEmail.CreateEmail.createEmailSendSantanderPendencia(sender, emaildestination,pwdsender, host, porta, "PendenciaSantander", t, "TESTE - "+s+" - TESTE")){
				enviado = true
			} else {
				enviado = false
			}
		} catch (Exception e){
			enviado false
		}
		System.err.println("Enviado ["+enviado+"]")
		return enviado
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailPendneciaFinalProcesso(String tipoAba){
		try{
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\EnvioPendenciaAutomaticaSantander\\ConfiguracoesEnvioPendenciaAutomaticaSantanderFinalProcesso.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = "EnvioPendenciaAutomaticaEmailSantande.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
		} catch (Exception e){

		}

	}


}

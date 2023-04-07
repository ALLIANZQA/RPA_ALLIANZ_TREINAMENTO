package backoffice

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.List

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

import org.openqa.selenium.WebElement
import rpacommands.rpaCommands
import auxiliares.switchLeitorExcel
import backoffice.backofficeFuncionalidades
import auxiliares.switchAtualizarExcel
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.ExtractDates
import auxiliares.MenuMatriz

public class InclusaoVCSantanderFuncionalidades extends rpaCommands{

	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/InclusaoVCSantander/InclusaoVCSantanderData'
	private static String path
	private static String alerta
	private static int cellStatus = 4
	private static String numApolice
	private static int paginas
	private static String tela

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
	public static boolean validaDados(){
		boolean value
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(cellStatus, i).equals('Processar')) {
				value = true
				break;
			} else {
				value = false
				//		break;
			}
		}
		System.err.println('Data ['+value+']')
		return value
	}

	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static fluxoInclusaoVCSantander(String tipoAba){
		initDataFiles()
		if(validaDados()){
			fluxoCompletoInclusaoVCSantander(tipoAba)
			processaFalhas(tipoAba)
			enviarEmailInclusaoVCSantander(tipoAba)
		} else {
			System.err.println('Não existem dados para processar')
		}
		finalizar();
	}

	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static fluxoCompletoInclusaoVCSantander(String tipoAba){
		init(tipoAba)
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			int lina = i-1
			alerta=""
			numApolice=""
			paginas = 0
			try{
				if (data.getValue(cellStatus, i).equals('Processar')) {
					System.err.println('Line ['+i+']')
					List<String> inativacao = new ArrayList<>();
					lista( inativacao , i)
					WebUI.delay(3);
					inclusaoVCSantander(inativacao)
					System.err.println("Alerta ["+alerta+"]")
					if(alerta.equals("atributo nao encontrado") || alerta.equals("PROCESSADO")){
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, 'Processado', 3, 'InclusaoVCSantander');
							auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, numApolice, 4, 'InclusaoVCSantander');
							auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, "", 5, 'InclusaoVCSantander');
						} catch (Exception e){}
					} else {
						if(alerta.equals("Sistema indisponível")){
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, 'Processar', 3, 'InclusaoVCSantander');
								auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, alerta, 5, 'InclusaoVCSantander');
								WebUI.closeBrowser();
								init(tipoAba)
							} catch (Exception e){}
						}else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, 'Processado', 3, 'InclusaoVCSantander');
								auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, alerta, 5, 'InclusaoVCSantander');
							} catch (Exception e){}
						}
					}
				}
			} catch (Exception e) {
				System.err.println(e)
				alerta = 'Falha ao carregar sistema '
				auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, 'Processar', 3, 'InclusaoVCSantander');
				auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, alerta, 5, 'InclusaoVCSantander');
			}
		}
	}

	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static init(String tipoAba){
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands(tipoAba);
		WebUI.delay(7);
		MenuMatriz.producaoSinistro()
		WebUI.delay(10);
		MenuMatriz.producao()
		WebUI.delay(15);
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()
		WebUI.delay(5);
	}

	/**
	 * @author be54955
	 * @param inclusao
	 * @return
	 */
	public static inclusaoVCSantander(List<String> inclusao){
		selecaoDados(inclusao);
		selecaoDadosBasicos(inclusao) ;
		selecaoDadosRisco(inclusao);
		dadosEmissao();
		dadosCliente();
		clausuras() ;
		confirmaEmissao()
		emissaoRealizada()
	}

	/**
	 * @author be54955
	 * @param inclusao
	 * @return
	 */
	public static selecaoDados(List<String> inclusao){
		try{
			//Outras Operações
			clickXpath('//*[@id="tipoDeSeleccion2"]', 1);
			//Apolice
			preencheXpath('//*[@id="polSusep_SUSEP"]', inclusao[0], 1);
			commandTAB('//*[@id="polSusep_SUSEP"]')
			//Selecionar
			clickXpath('//*[@id="btnSeleccionar"]', 1);
			selecionaOperativa(inclusao[2])
			//Avançar
			clickXpath('//*[@id="btnSeleccionOperativa"]', 5);
			fechaAlerta();
			WebUI.delay(5);
		} catch (Exception e){
			alerta = "Sistema indisponível"
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaOperativa(String operativa){
		String xpath = '//*[@id="codOperativa"]'
		String tag ="option";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().contains(operativa.toString())){
				listOfElements[i].click()
				break
			}
		}
	}

	/**
	 * @author be54955
	 * @param inclusao
	 * @return
	 */
	public static selecaoDadosBasicos(List<String> inclusao){
		try{
			//Avançar
			clickXpath('//*[@id="btnAceptar"]', 5);
		} catch (Exception e){
			alerta = "Sistema indisponível"
		}
	}

	/**
	 * @author be54955
	 * @param inclusao
	 * @return
	 */
	public static selecaoDadosRisco(List<String> inclusao){
		//captura alerta
		WebUI.delay(5)
		try{

			/**
			 * DADOS DE RISCO
			 */
			alerta =  recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'outerText', 3);
			System.err.println(alerta)
			if(alerta.equals("atributo nao encontrado")){
				tela =  recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3);
				System.err.println(tela)
				//Proposta Corretor
				limpaCampo('//*[@id="AutoComparaSantanderBean$propostaCorretor"]',1)
				//clickXpath('//*[@id="AutoComparaSantanderBean$propostaCorretor"]', 1);
				preencheXpath('//*[@id="AutoComparaSantanderBean$propostaCorretor"]', inclusao[1], 1);
				//Aceitar
				clickXpath('//*[@id="o_3"]', 5);
			}else {
				//Menu
				clickXpath('//*[@id="0"]', 5);
			}
		} catch (Exception e){
			//	  alerta = "Sistema indisponível"
		}

	}

	/**
	 * @author be54955
	 * @return
	 */
	public static confirmaEmissao(){
		if(alerta.equals("atributo nao encontrado")){
			/**
			 * CONFIRMAÇÃO DE EMISSÃO
			 */
			tela =  recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3);
			System.err.println(tela)

			if(tela.contains("CONFIRMAÇÃO DE EMISSÃO ".toString())){
				clickXpath('//*[@id="btnAceptar"]', 5);
			}else {
				alerta = "Sistema indisponível"
			}
		}
		System.err.println("Confirmação emissão - "+alerta)
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static emissaoRealizada(){
		if(alerta.equals("atributo nao encontrado")){
			WebUI.delay(15)
			tela =  recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3);
			System.err.println(tela)
			if(tela.contains("EMISSÃO REALIZADA ".toString())){
				try{
					alerta = "PROCESSADO"
					numApolice = ""
					numApolice =  recuperaValor('//*[@id="CabeceraBean$endosoSusepSUSEP"]', 'innerText', 3);
					String s = numApolice
					numApolice = s.replaceAll("[-/]","")
					System.err.println("numApolice ["+numApolice+"]")
					WebUI.delay(5)
					//Avançar
					clickXpath('//*[@id="btnValida"]', 1);
				} catch (Exception e){
					System.err.println("[ exception confirma emissão ]")
					alerta = "Sistema indisponível"
					//Avançar
					clickXpath('//*[@id="btnValida"]', 1);
				}
			}
		}
		System.err.println("Emissão Realizada - "+alerta)
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static dadosEmissao(){
		WebUI.delay(5)
		alerta =  recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'outerText', 3);
		WebUI.delay(2)
		System.err.println(alerta)
		if(alerta.equals("atributo nao encontrado")){
			/**
			 * D. EMISSÃO
			 */
			tela =  recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3);
			if(tela.contains("D. EMISSÃO".toString())){
				clickXpath('//*[@id="btnAceptar"]', 5);
			}else {
				alerta = "Sistema indisponível"
			}
		}else {
			//Menu
			clickXpath('//*[@id="0"]', 5);
		}
		System.err.println("Dados emissão - "+alerta)
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static listaDeClientes(){
		WebUI.delay(5)
		alerta =  recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'outerText', 3);
		WebUI.delay(5)
		if(alerta.equals("atributo nao encontrado")){
			/**
			 * LISTA CLIENTES 
			 */
			tela =  recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'outerText', 3);
			if(tela.contains("LISTA CLIENTES ".toString())){
				clickXpath('//*[@id="ListaClientesBean$listaClientes_0_0"]', 5);
			}
		}
		System.err.println("Lsta de clientes - "+alerta)
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static dadosCliente(){
		//	  WebUI.delay(5)
		//	  alerta =  recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'outerText', 3);
		WebUI.delay(2)
		if(alerta.equals("atributo nao encontrado")){
			/**
			 * D. Cliente
			 */
			tela =  recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3);
			System.err.println(tela)
			if(tela.contains("D. CLIENTE".toString())){
				alerta =  recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'outerText', 3);
				if(alerta.contains("Verifique se o email está correto antes de continuar".toString())){
					//Avançar
					clickXpath('//*[@id="btnAceptar"]', 5);
					alerta = "atributo nao encontrado"
				} else if(alerta.equals("atributo nao encontrado") ){
					//Avançar
					clickXpath('//*[@id="btnAceptar"]', 5);
				} else {
					//Menu
					clickXpath('//*[@id="0"]', 5);
				}
			}else {
				//		  alerta = "Sistema indisponível"
			}

			listaDeClientes();

			/**
			 * D. Cliente - DETALHES PESSOAIS DO CONDUTOR
			 */
			if(alerta.equals("atributo nao encontrado") ){
				tela =  recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3);
				System.err.println(tela)
				if(tela.contains("D. CLIENTE".toString())){
					//Avançar
					clickXpath('//*[@id="btnAceptar"]', 5);
				}
			}
		}
		System.err.println("Dados do Clente - "+alerta)
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static clausuras(){
		if(alerta.equals("atributo nao encontrado")){
			/**
			 * CLAUSULAS
			 */
			WebUI.delay(2)
			tela =  recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3);
			System.err.println(tela)
			if(tela.contains("CLÁUSULAS".toString())){
				clickXpath('//*[@id="btnAceptar"]', 5);
			}else {
				alerta = "Sistema indisponível"
			}
		}
		System.err.println("Clausuras - "+alerta)
	}


	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailInclusaoVCSantander(String tipoAba){
		pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\InclusaoVCSantander\\ConfiguracoesInclusaoVCSantander.txt";
		lerConfigurações(pathTxtEmail);
		path1 = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\InclusaoVCSantander\\InclusaoVCSantander.xlsx"
		nome1 = "InclusaoVCSantander.xlsx";

		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
		initDataFiles()
		if(!validaDados()){
			List<String> anexos = new ArrayList<>();
			anexos[0] = path1;
			anexos[1] = nome1;
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba )
		} else {
			System.err.println('Existem dados para processar')
		}
	}

	/**
	 * @author T_Dvlopes
	 * @param processoFinalizado
	 * @param tipoAba
	 * @return
	 * Processa as falhas de envio de pendências ao final do processo, caso ocorra mais falha o processo é re-feito até concluir
	 */
	public static processaFalhas(String tipoAba){
		System.err.println('processa Falhas ['+tipoAba+']')
		initDataFiles()
		if(validaDados()){
			fluxoCompletoInclusaoVCSantander(tipoAba)
			processaFalhas(tipoAba)
			finalizar();
		} else {
			System.err.println('Não existem dados para processar')
			finalizar();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static List<String> lista( List<String> sinistro , int v){
		for (int i = 1; i <= data.getColumnNumbers(); i++) {
			int d = i -1
			sinistro[d] = data.getValue(i, v).trim()
		}

		for (int j = 0; j < sinistro.size();j++ ){
			System.err.println("["+j+"] ["+sinistro[j]+"]")
		}

		return sinistro
	}

}

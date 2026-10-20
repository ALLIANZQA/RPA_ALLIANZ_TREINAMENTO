package backoffice

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.time.Duration
import java.time.Instant
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
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import auxiliares.MenuMatriz
import internal.GlobalVariable
import rpacommands.rpaCommands
import auxiliares.ExtractDates
import auxiliares.StringFormatter
import auxiliares.FileInput
import sendEmail.emailDinamico;
import auxiliares.timer;
import auxiliares.GetHost;
import auxiliares.CurrencyMonetary;
import auxiliares.WriteExcelFactory;

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject



public class GestaoVerificacoesCancelamentoAtribuicaoFuncionalidades extends rpaCommands{
	
	
	private static TestData data
	private static TestData dataOperacao
	private static String testDataPath = 'Data Files/Backoffce/GestaoVerificacoesCancelamentoAtribuicao/GestaoVerificacoesCancelamentoAtribuicaoData'
	private static String path
	private static String alerta
	private static String retorno
	private static int cellCheck = 6;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "GestaoVerificacoesCancelamentoAtribuicao.xlsx"
	private static int cellCheckAlert = 7;
	private static boolean enviado = true;
	private static String xpath;
	private static String tela;
	private static boolean erroTela = false;
	private static String msgAlerta
	private static String status
//	private static Map<String, Integer> cellNames;

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoGestaoVerificacoesCancelamentoAtribuicao(String aba, String a){
		System.out.println()
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		initDataFiles()
		fluxoCompletoGestaoVerificacoes(aba,a)
		reprocessaFalhas(aba,a)
		FileInput.atualizaLog(Instant.now().toString(), pathFile)
		List<String> times = rpaCommands.convertToInstant(pathFile);
		String dataInicio = times[0];
		String horaInicial =  times[1];
		timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
			enviaEmailDinamico(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
		} catch (Exception e){
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoGestaoVerificacoes(aba,a){
		System.out.println()
		try{
			retornaHashDeColunasExcell(path);
			cellCheck = 1 + cellNames.get("Status");
			cellCheckAlert = 1 + cellNames.get("Mensagem");
			int process = cellNames.get("Status");
			int alet = cellNames.get("Mensagem");
			System.out.println(cellCheck)
			System.out.println(cellCheckAlert)
			if(validaDados(cellCheck)){
				init(aba,a);
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					enviado = true;
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						if(GestaoVerificacoes(registros,aba,a)) {
						    try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado" , process, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta , alet, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, process, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: "+alerta, alet, aba);
							} catch (Exception e){}
						}
					}
				}
			} else {
				System.err.println('Não existem dados para processar')
			}
		} catch (Exception e){}
		WebUI.closeBrowser();
		init(aba,a);
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean GestaoVerificacoes(List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado = true;
		String msgAlert;
		String tela = true;
		boolean situacao;

		try{
			tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(tela)
			if(listaDeTelas(tela)){
				WebUI.delay(3);
				//Menu 
				selecionaItemLista("Seleção",'//*[@id="tableOptMenu_"]/tbody' , "td" )
				WebUI.delay(5);
				tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
				System.out.println(tela)
				if(listaDeTelas(tela)){
				  alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				  System.out.println("Alerta ["+alerta+"]")
				  if(!alertListBlocList(alerta)){
					  //Proposta
					  preencheXpath('//*[@id="cotiPolizaCert_campo1"]', registros[cellNames.get("Proposta")], 0)
					  //Item
					  preencheXpath('//*[@id="cotiPolizaCert_campo2"]', registros[cellNames.get("Item")], 0)
					  //Pesquisar
					  selecionaItemLista("Pesquisar",'//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td' , "div" )
					  WebUI.delay(10);
					  tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
					  System.out.println(tela)
					  if(listaDeTelas(tela)){
						  System.out.println()
						  if(identificaItemAveriguacaoLista("CURSO",'//*[@id="lista_tbody"]' ,"tr" )){
							  System.out.println()
							  
						  } else {
						    enviado = false
						    alerta="Averiguação encontra-se cancelada";
						    status = "Processado"
							selecionaItemListaAtributo("Início",'//*[@id="cellMenuIconsAppNaviHeaderFilledArea"]' ,"img", "title" )
						  }
					  } else {
				        enviado = false
				        alerta="Tela "+tela+" não apresentada"
				        status = "Processado"
			          }
				  } else {
					enviado = false
					status = "Processado"
				}
			  } else {
				 enviado = false
				 alerta="Tela "+tela+" não apresentada"
				 status = "Processado"
			 }
			} else {
				enviado = false
				alerta="Tela "+tela+" não apresentada"
				status = "Processado"
			}
		} catch (Exception e) {
			enviado = false
			status = "Processado"
			alerta="Falha ao processar ressarcimento'"
		}
		System.out.println("\nenviado ["+ enviado+"] \nmsgAlerta ["+alerta+"]");
		return enviado;
	}

	/**
	 * @author Alz_sup54955
	 * @param tipoAba
	 * @param timeLapnse
	 * @param dataInicio
	 * @param dataFinal
	 * @param horaInicial
	 * @param horaFinal
	 * @return
	 */
	public static enviaEmailDinamico(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
		System.out.println()
		try{
			if(!validaDados(cellCheck)){
				String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\GestaoVerificacoesCancelamentoAtribuicao\\ConfiguracoesGestaoVerificacoesCancelamentoAtribuicao.txt";
				lerConfigurações(pathTxtEmailConfig)
				System.out.println(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				// FORMATA SAIDA DO TEMPO DE EXECUÇÃO
				long segds = timeLapnse.toSeconds();
				String hours = timeLapnse.toHours()+"h "+
						timer.converteMinutos(timeLapnse.toHours(), timeLapnse.toMinutes())+"m "+
						timer.converteSegundos(timeLapnse.toMinutes(),segds)+"s";
				// ENVIO DE E-MAIL
				//DADOS DO DOCUMENTO
				initDataFiles();
				// ENVIA E-MAIL DINAMICO
				emailDinamico.enviaEmailDinamico(sender, emaildestination, tipoAba, path, docName, emailDinamico.listaDeAlertas(data, cellCheckAlert)  , hours, horaInicial, horaFinal,  GetHost.getHostname() , dataInicio, dataFinal, "" );
				FileInput.deletaArquivoPdf(pathFile);
			}
		} catch (Exception e){}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		path = data.sourceUrl
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
			WebUI.delay(20);
			MenuMatriz.producaoSinistro();
			MenuMatriz.subMenuNome("a","Gestão de Verificações")
			alteraJanejaGenerica(1, 5)
		} catch (Exception e){}
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
	 * @author ALZ_SUP54955
	 * @return
	 */
//	public static colunas(){
//		cellNames = WriteExcelFactory.readExcelAllFileOfColunm(path);
//	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba, String a){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoGestaoVerificacoes(aba,a)
			reprocessaFalhas(aba,a)
		}
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




	public static boolean recuperaValorInput(String xpathSubMenu, String tag, String situacao){
		boolean b = false;
		selecionaListaDeElementos(xpathSubMenu,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			System.out.println("["+i+"] ["+listOfElements[i].getAttribute("value")+"]" )
			if(listOfElements[i].getAttribute("value").contains(situacao.toString())){
				//			   listOfElements[i].click()
				b=true;
				break
			}
		}
		return b;
	}

	public static String identificaItemAveriguacaoLista(String tipo,String xpath , String tag ){
		String t;
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		System.out.println("[TIPO  ["+tipo+"]")
		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println("["+i+"] Attr ["+listOfElements[i].getAttribute('value')+"] Text ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains(tipo.toString().trim())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				t = listOfElements[i].getText();
				listOfElements[i].click();
				break
			}
		}
		return t;
		WebUI.delay(2)
	}


	
	
	
	
	
	
	
	
	
	
	
}

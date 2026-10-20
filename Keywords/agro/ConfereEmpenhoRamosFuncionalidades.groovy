package agro


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



public class ConfereEmpenhoRamosFuncionalidades extends rpaCommands {
	
	private static TestData data
	private static TestData dataOperacao
	private static String testDataPath = 'Data Files/Agro/ConfereEmpenhoRamos/ConfereEmpenhoRamosData'
	private static String path
	private static String alerta
	private static String retorno
	private static int cellCheck = 25;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "ConfereEmpenhoRamos.xlsx"
	private static int cellCheckAlert = 26;
	private static boolean enviado = true;
	private static String xpath;
	private static String tela;
	private static boolean erroTela = false;
	private static String msgAlerta
	private static String status
	
	private static String arquivo = "fichero";
	private static String renamePathFileValue = "C:\\Users\\Public\\fichero.PDF";
	
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoConfereEmpenhoRamos(String aba, String a){
		System.out.println()
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		initDataFiles()
		fluxoCompletoGestaoReclamacao(aba,a)
		reprocessaFalhas(aba,a)
		FileInput.atualizaLog(Instant.now().toString(), pathFile)
		List<String> times = rpaCommands.convertToInstant(pathFile);
		String dataInicio = times[0];
		String horaInicial =  times[1];
		timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
			enviaEmailGestaoReclamacao(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
		} catch (Exception e){
		}
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
	public static enviaEmailGestaoReclamacao(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
		System.out.println()
		try{
			if(!validaDados(cellCheck)){
				String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Marketing\\GestaoReclamacao\\ConfiguracoesGestaoReclamacaoAutomatico.txt";
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
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoGestaoReclamacao(aba,a){
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
						if(confere(registros,aba,a)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, process, aba);
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
	 * @return
	 */
	public static reprocessaFalhas(String aba, String a){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoGestaoReclamacao(aba,a)
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
	 * @param a
	 * @return
	 */
	public static init(String aba, String a){
		try{
			acessoAmbiente(aba,a)
			WebUI.delay(20);
		} catch (Exception e){}
	}
	
	
	
	/**
	 * @author be54955
	 * @return
	 */
	public static boolean confere(List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado = true;
		String msgAlert;
		String tela = true;
		String xpath;
		StringBuilder strbldr = new StringBuilder();
		try{
			MenuMatriz.consultasEstatisticas();
			MenuMatriz.subMenuNome("a","GO!")
			alteraJanejaGenerica(1, 10)
			tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println("["+tela+"]")
			if(listaDeTelas(tela)){
				//Operativo
				//clicarEmElementoDaTela('//*[@id="operat"]', 5);
				//Painel
				//clicarEmElementoDaTela('//*[@id="4"]/div[1]/span', 2);
				//Insp Cultivo
				//clicarEmElementoDaTela('//*[@id="902"]/div[1]/span', 2);
				System.out.println()
				listaOperacoes()
				
				
				
				System.out.println()
			} else {
				enviado = false
				alerta="Tela 'GESTOR DE OPERAÇÕES' não apresentada"
				status = "Processar"
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 5)
				init(aba, a)
			}
		} catch (Exception e) {
			enviado = false
			status = "Processar"
			alerta="Falha ao preencher 'GESTÃO DE RECLAMAÇÕES'"
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0, 5)
			init(aba, a)
		}
		System.out.println("\nenviado ["+ enviado+"] \nmsgAlerta ["+alerta+"]");
		return enviado;
	}
	
	/**
	 * @author ALZ_SUP54955
	 */
	public static void listaOperacoes(){
		 String xpath = '//*[@id="listSimulaciones_tbody"]'
		 String tag ="tr";
		 int p = 1
		 WebUI.delay(2)
		 selecionaListaDeElementos(xpath,tag)
		 System.out.println()
		 for(int i = 0; i < listOfElements.size(); i++){
			 if(listOfElements[i].getText().length() > 0){
				 if(listOfElements[i].getText().contains("137982316/00002") ){
					System.out.println("["+i+"] index ["+listOfElements[i].getText()+"] ["+listOfElements[i].getText().length()+"]")
				    listOfElements[i].click();
					WebUI.delay(10);
					String processo = recuperaValor('//*[@id="lbReferencia_label"]', 'innerText', 0)
					System.out.println();
					if(processo.equals("Processo")){
						 processo = recuperaValor('//*[@id="lbReferencia"]', 'defaultValue', 0)
						 System.out.println("Processo recuperado ["+processo+"] Item ["+p+"]")
						 //Imagem
						 clicarEmElementoDaTela('//*[@id="97"]/div[1]/span', 5);
						 System.out.println();
						 WebUI.delay(10);
						 transfereArquivoDePasta(arquivo)
						 WebUI.delay(1);
						 renomearArquivoExtencao(renamePathFileValue , processo+"-"+String.valueOf(p) , ".pdf")
						 System.out.println();
						 
						 p = p+1
					 } else {
					   break;
					 }
					 
				 }
					 
			 }
			 
			 deleteFileDiretory("C:\\Users\\Public\\"+arquivo+".pdf")
		 }
		 System.out.println("Arquivos modificados ["+p+"]")
		 System.out.println()
	}
	
	

	
	
}

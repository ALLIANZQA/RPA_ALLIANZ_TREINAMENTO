package backoffice

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
import auxiliares.StringFormatter
import internal.GlobalVariable
import rpacommands.rpaCommands
import auxiliares.FileInput

import java.time.Duration
import java.util.List
import auxiliares.MenuMatriz
import auxiliares.ExtractDates
import auxiliares.timer
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject




import auxiliares.timer
import sendEmail.emailDinamico;
import auxiliares.GetHost;
import auxiliares.leitorPdf;
import java.time.Duration
import java.time.Instant



public class GestaoRegularizacaoIncidenciasFuncionalidades extends rpaCommands {

	private static int cellCheck = 3
	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/GestaoRegularizacaoIncidencias/GestaoRegularizacaoIncidenciasData'
	private static String path
	private static String msgAlerta
	private static String status
	private static String referencia

	
	private static int cellCheckAlert = 4;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	
	
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
	 * @author be54955
	 * @param aba
	 * @param a
	 * @return
	 */
	public static init(String aba, String a){
		System.out.println();
		try{
			MenuMatriz.manutancaoGeral();
			MenuMatriz.avisoDeCredito();
			alteraJanejaGenerica(1, 10)
			WebUI.delay(10);
		} catch (Exception e){}
	}

	/**
	 *
	 * @param aba
	 * @param a
	 * @return
	 */
	public static fluxoGestaoRegularizacaoIncidencias(String aba, String a){
		
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		initDataFiles()
		fluxoCompletoGestaoRegularizacaoIncidencias(aba,a)
		reprocessaFalhas(aba,a)
		FileInput.atualizaLog(Instant.now().toString(), pathFile)
		List<String> times = rpaCommands.convertToInstant(pathFile);
		String dataInicio = times[0];
		String horaInicial =  times[1];
		timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
		   enviaEmailGestaoRegularizacaoIncidenciasDinamico(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
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
	public static enviaEmailGestaoRegularizacaoIncidenciasDinamico(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
		System.out.println()
	 try{
		 if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\RegularizaIncidencia\\ConfiguracoesRegularizaIncidencia.txt"
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			String docName = "GestaoRegularizacaoIncidencias.xlsx";
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
	public static fluxoCompletoGestaoRegularizacaoIncidencias(String aba, String a){
		System.out.println();
		try{
			if(validaDados(cellCheck)){
				acessoAmbiente(aba,a)
				WebUI.delay(10);
				init(aba,a);
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					msgAlerta = ''
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						if(regulariza(registros, aba, a)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 2, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, msgAlerta , 3, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 2, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path,"Alerta: "+msgAlerta, 3, aba);
							} catch (Exception e){}
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
	public static boolean regulariza(List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado
		try{
			//Gestão Ficheiros Bancarios
			WebUI.delay(10);
			String telaConsultaAvisoCredito = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(telaConsultaAvisoCredito )
			if(listaDeTelas(telaConsultaAvisoCredito)){
				//Gestão de Créditos
				selecionaItemLista("Documentos a Regularizar",'//*[@id="tableOptMenu_"]' , "td" )
				//Gestão Créditos
				String telaConsultaDocumentosRegularizar = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
				System.out.println(telaConsultaDocumentosRegularizar )
				if(listaDeTelas(telaConsultaDocumentosRegularizar)){
					//Recibo Original
					limparCamposInput('//*[@id="recibo"]')
					preencheXpath('//*[@id="recibo"]', registros[0], 1)
					//Pesquisar
					selecionaItemLista("Pesquisar",'//*[@id="secaoFiltroSectionBar"]/table/tbody' , "td" )
					System.out.println()
					String msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
					if(!alertListBlocList(msgAlert)){
						System.out.println()
						selecionaDocumento()
						//Recibo
						limparCamposInput('//*[@id="formRegManual"]/table/tbody/tr/td[2]/input')
						preencheXpath('//*[@id="formRegManual"]/table/tbody/tr/td[2]/input', registros[0], 1)
						//Regularizar
						selecionaItemLista("Regularizar",'//*[@id="secaoRegManualSectionBar"]/table/tbody' , "div" )
						WebUI.delay(1);
						fechaAlerta();
						WebUI.delay(1);
						String confirm = pegaMSGAlerta();
						String n = confirm.replace("Confirma", "Confirmada").replace("?", "");
						msgAlerta=  "Tela '"+telaConsultaDocumentosRegularizar+"': "+ n;
						System.out.println("["+msgAlerta+"]")
						fechaAlerta();
						WebUI.delay(1);
						enviado = true;
						//String confirm =  recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 1)
						status = "Processado"
						selecionaItemLista("Aviso de Crédito",'//*[@id="tableOptMenu_"]' , "td" )
					} else {
						enviado = false
						msgAlerta=  "Tela '"+telaConsultaDocumentosRegularizar+"': "+msgAlert;
						//Pesquisar
						selecionaItemLista("Aviso de Crédito",'//*[@id="tableOptMenu_"]' , "td" )
						status = "Processado"
					}
				} else {
					enviado = false
					msgAlerta="Tela 'Consulta de Documentos a Regularizar' não apresentada"
					status = "Processar"
					alteraJanejaGenerica(0, 10)
					fecharJanelaAtual(1)
					init(aba, a)
				}
			} else {
				enviado = false
				msgAlerta="Tela 'Consulta Aviso Crédito' não apresentada"
				status = "Processador"
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 10)
				init(aba, a)
			}
		} catch (Exception e) {
			enviado = false
			status = "Processado"
			msgAlerta="Falha ao preencher 'Gestao Ficheiros Bancarios'"
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0, 10)
			init(aba, a)
		}
		System.out.println("\nenviado ["+ enviado+"] \nmsgAlerta ["+msgAlerta+"]");
		return enviado;
	}

	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static boolean selecionaDocumento(){
		System.out.println();
		boolean r = false;
		try{
			String xpath = '//*[@id="row_0_tableResult"]'
			String tag ="td";
			selecionaListaDeElementos(xpath,tag)
			if( listOfElements.size() > 0){
				clickXpath('//*[@id="row_0_tableResult"]', 1);
		        r = true;	
		    }
		
		} catch (Exception e){ }
		return r;
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
			fluxoCompletoGestaoRegularizacaoIncidencias(aba, a)
			reprocessaFalhas(aba, a)
		}
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailGestaoRegularizacaoIncidencias(String tipoAba){
		System.out.println()
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\RegularizaIncidencia\\ConfiguracoesRegularizaIncidencia.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "GestaoRegularizacaoIncidencias.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
			}
		} catch (Exception e){
		}
	}

}

package backoffice


import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.time.Duration
import java.time.Instant
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
import auxiliares.ExtractDates
import auxiliares.timer;
import sendEmail.emailDinamico;
import auxiliares.GetHost;
import auxiliares.leitorPdf;


public class ProatividadePerceriasFuncionalidades extends backofficeFuncionalidades{
	
	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/ProatividadePerceriasData/ProatividadePerceriasData'
	private static String path
	private static String alerta
	private static int cellCheck = 16
	private static String filepath = "C:\\Users\\Public\\"
	private static texto
	private static String status
	private static boolean download = false;
	private static String novoNomePDF;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	

	private static String docName = "ProatividadeParcerias.xlsx"
	private static pathTxtEmailDinamico = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\ProatividadePercerias\\ConfiguracoesProatividadePercerias.txt"
	private static int cellCheckAlert = 17;

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
	 * @return
	 */
	public static reprocessaFalhas(String aba, String a){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoProatividadePercerias(aba,a)
			reprocessaFalhas(aba,a)
		}
	}
	
	public static void fluxoProatividadePercerias(String aba, String a){
		System.out.println()
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		initDataFiles()
		fluxoCompletoProatividadePercerias(aba,a)
		reprocessaFalhas(aba,a)
		FileInput.atualizaLog(Instant.now().toString(), pathFile)
		List<String> times = rpaCommands.convertToInstant(pathFile);
		String dataInicio = times[0];
		String horaInicial =  times[1];
		timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
		   enviaEmailDuplicadoRecibosDinamico(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
		} catch (Exception e){
		}
		
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoProatividadePercerias(String aba, String a){
		System.out.println()
	  try{
		  
		  if(validaDados(cellCheck)){
			acessoAmbiente(aba,a)
			WebUI.delay(15);
			MenuMatriz.newFluxoGestaoNome();
			alteraJanejaGenerica(1, 10)
			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				if (data.getValue(cellCheck, i).equals('Processar')) {
					status = "Processar";
					alerta = "";
					List<String> registros = new ArrayList<>();
					lista( registros , i)
					registros[17] = "0";
					registros[18] = "P";
					registros[19] = "9255";
					System.out.println(path)
					if(parcerias(registros, aba, a)) {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[20], 4, aba);
                            auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[21], 5, aba);
                            auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[22], 6, aba);
                            auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[23], 7, aba);
                            auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[24], 8, aba);
                            auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 15, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 16, aba);
						} catch (Exception e){}
					} else {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 15, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: "+alerta, 16,  aba);
						} catch (Exception e){}
					}
				}
			}
		  } else {
			System.err.println('Não existem dados para processar')
		}
	   } catch (Exception e){
		 System.err.println('EXCEPTION\n'+e.printStackTrace())
	  }
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @param aba
	 * @param a
	 * @return
	 */
	public static boolean parcerias(List<String> registros, String aba, String a){
	    System.out.println()
		boolean enviado = true;
		String msgAlert;
		String tela = true;
		boolean situacao;
		try{
			tela = recuperaValor('/*/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/div/div/div', 'innerText', 10)
			System.out.println(tela)
			if(listaDeTelas(tela)){
				/**
				 * Nova Ficha Gestão
				 * Recebe : lista, index proposta, index apolice
				 *
				 */
				novaFichaGestaoProcesso(registros,1,17, registros[18]);
				WebUI.delay(10);
				System.out.println();
				if(selecionaNotaFG(registros)){
				   System.out.println();
				   ArrayList<ArrayList<String>> pdf = new ArrayList<ArrayList<String>>();
				   pdf.add(leitorPdf.pdfReaderNPagesList("C:\\Users\\Public\\"+novoNomePDF+".pdf"));
				   WebUI.delay(5);
				   String codigoCooperativa = retornaValorStop(pdf, "Código Cooperativa:",1); 
				   registros[20] = codigoCooperativa
				   System.out.println("["+registros[20]+"]");
				   
				   String codigoUnidadeAtendimento = retornaValorStop(pdf, "Código Unidade de Atendimento:",3);
				   registros[21] = codigoUnidadeAtendimento;
				   System.out.println("["+registros[21]+"]");
				   
				   String dadosBancarios = retornaValorStop(pdf, "Forma de pagamento Dados bancários:", 9);
				   registros[22] = dadosBancarios;
				   System.out.println("["+registros[22]+"]");
				   
				   String nome = retornaValorStart(pdf, "Nome:"); 
				   registros[23] = nome;
				   System.out.println("["+registros[23]+"]");
				   
				   String documento = retornaValorStop(pdf, "CPF/CNPJ:",0); 
				   registros[24] = documento; 
				   System.out.println("["+registros[24]+"]");
				   
				   registros[25] = "";
				   System.out.println();
				   if(!enviarDocumentoPorEmail("emailProatividadePercerias", registros[13], "denilson.inmetricsext@allpronet.com.br", registros[1], registros[19] , registros[14], "TESTE PARCERIAS ", "denilson.inmetricsext@allpronet.com.br")){
				      enviado = false;
					  alerta = "Falha ao enviar e-mail";
				   }
				} else {
				}
				System.out.println(enviado);
				status = "Processado";
				alerta = "TESTE TESTE ";
				
			} else {
				enviado = false
				alerta="Tela 'FICHA GESTÃO' não apresentada"
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
	 * @author ALZ_SUP54955
	 * @param pdf
	 * @param text
	 * @param s
	 * @return
	 */
	public static String retornaValorStop(ArrayList<ArrayList<String>> pdf, String text, int s){
		String value;
		for(int i = 0; i<pdf.size();i++ ){
			int l = i+1;
			System.out.println("\nList: "+l+" **************************************************************************\n")
			for(int j = 0; j<pdf.get(i).size();j++){
					   System.out.println("["+j+"] - ["+pdf[i][j].toString()+"]");
					   if(pdf[i][j].toString().contains(text)){
						  value = StringFormatter.nextStop(pdf[i][j].toString(), text, s);
						  System.out.println(value)
						   break;
					   }
			}
		}
		return value; 
	}

	/**
	 * @author ALZ_SUP54955	
	 * @param pdf
	 * @param text
	 * @return
	 */
	public static String retornaValorStart(ArrayList<ArrayList<String>> pdf, String text){
		String value;
		for(int i = 0; i<pdf.size();i++ ){
			int l = i+1;
			System.out.println("\nList: "+l+" **************************************************************************\n")
			for(int j = 0; j<pdf.get(i).size();j++){
					   System.out.println("["+j+"] - ["+pdf[i][j].toString()+"]");
					   if(pdf[i][j].toString().contains(text)){
						  value = StringFormatter.startWhitch(pdf[i][j].toString(), text);
						  System.out.println(value)
						   break;
					   }
			}
		}
		return value;
	}
	
	/**
	 * @author ALZ_SUP54955	
	 * @param registros
	 * @return
	 */
	public static boolean selecionaNotaFG(List<String> registros){
		boolean seleciona = false; 
		if(selecionaItemListaFG(registros[0] , registros[19] )){
			WebUI.delay(10);
			downloadPDF();
			WebUI.delay(15);
			fechaCONSULTAIMAGEMLI()
			WebUI.delay(15);
			seleciona = manipulaArquivo(registros[1], registros[19], registros[2], registros[17])
		}
		return seleciona;
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean selecionaItemListaFG(String dt,String nt ){
		boolean f = false
		WebUI.delay(2)
		String xpath = "//*[@id=\"tablaLista\"]/app-list-file/div/div/div/table/tbody";
		String tag = "tr";
		selecionaListaDeElementos(xpath,tag)
		System.out.println("[TIPO  ["+dt+"] ["+nt+"]\n")
		for(int i = 0; i < listOfElements.size(); i++){
			System.out.println("["+i+"] Text ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains(dt.toString().trim()) && listOfElements[i].getText().contains(nt.toString().trim())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				listOfElements[i].click()
			    f = true
		        break
			}
		}
		return f
		WebUI.delay(2)
	}
	
	/**
	 * @author BE54955
	 * @return
	 */
	public static fechaCONSULTAIMAGEMLI(){
		alteraJanejaGenerica(2, 10)
		clickXpath('//*[@id="lnkTopLink_Close"]', 2);
		fechaAlerta()
		alteraJanejaGenerica(1, 2)
	}
	
	/**
	 * @author ALZ_SUP54955
	 * @return
	 */
	public static downloadPDF(){
		selecionaListaDeElementos('//*[@id="nx-tab-content-0-0"]/div/div[6]','button')
		 for(int k = 0; k < listOfElements.size(); k++){
			 System.out.println("["+k+"] ["+listOfElements[k].getText() +"]" )
			 if(listOfElements[k].getText().contains("Acesso a detalhes estendidos".toString())){
				 listOfElements[k].click()
			 }
		 }
	}
	
	/**
	 * @author ALZ_ServiceQA
	 * @param apolice
	 * @return
	 */
	public static boolean manipulaArquivo(String apolice, String txnota, String mediador, String aplica){
		boolean b = false;
		String renamePathFileValue = "C:\\Users\\Public\\fichero.PDF";
		try{
			if(FileInput.arquivoExiste(renamePathFileValue)){
			   novoNomePDF = apolice+'_'+txnota+'_'+aplica
			   b = FileInput.renomearArquivo(renamePathFileValue, novoNomePDF);
			}
		} catch (Exception e){
				b = false
	    }
		 return b;
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
	public static enviaEmailDuplicadoRecibosDinamico(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
	 try{
		 if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\ProatividadePercerias\\ConfiguracoesProatividadePercerias.txt"
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			// FORMATA SAIDA DO TEMPO DE EXECUÇÃO
			long segds = timeLapnse.toSeconds();
			String hours = timeLapnse.toHours()+"h "+
						timer.converteMinutos(timeLapnse.toHours(), timeLapnse.toMinutes())+"m "+
						timer.converteSegundos(timeLapnse.toMinutes(),segds)+"s";
			// ENVIO DE E-MAIL
			//String emaildestination = "denilson.inmetricsext@allpronet.com.br"
			//DADOS DO DOCUMENTO
			//TestData data = findTestData(testDataPath);
			initDataFiles();
			// ENVIA E-MAIL DINAMICO
			emailDinamico.enviaEmailDinamico(sender, emaildestination, tipoAba, path, docName, emailDinamico.listaDeAlertas(data, cellCheckAlert)  , hours, horaInicial, horaFinal,  GetHost.getHostname() , dataInicio, dataFinal, "");
			FileInput.deletaArquivoPdf(pathFile);
		  }
	  } catch (Exception e){}
	}
	
	/**
	 * @author ALZ_ServiceQA
	 * @param corretor
	 * @return
	 */
	public static boolean enviarDocumentoPorEmail(String aba, String de, String para, String apolice, String nota, String corpoEmail, String assunto, String cc){
		boolean enviar
		try{
			List<String> anexos = new ArrayList<>();
			anexos[0] = 'C:\\Users\\Public\\'+novoNomePDF+'.PDF';
			anexos[1] = novoNomePDF+'.PDF';
			System.out.println("["+anexos[0]+" - "+anexos[1]+"]")
			System.out.println("["+nota+"]")
			if(sendEmail.CreateEmail.createEmailSendMultipleFilesParcerias(de, para, anexos, aba, nota, apolice, corpoEmail, assunto, cc)){
				WebUI.delay(10);
				deleteFileDiretory("C:\\Users\\Public\\"+novoNomePDF+".PDF")
			}
			enviar = true
		} catch (Exception e){
			enviar = false
		}
		return enviar
	}
}

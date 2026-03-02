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


public class IndexacaoFGFuncionalidades extends rpaCommands{

	private static TestData data
	private static String path
	private static String alerta
	private static int cellCheck = 5
	private static String filepath = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\IndexacaoFG\\Arquivos\\"
	private static String filepathIndex = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\Index\\"
	private static String completeFilePath
	private static String nomeIndex;
	private static String msgAlert;
    private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "IndexacaoFG.xlsx"
	private static pathTxtEmailDinamico = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\IndexacaoFG\\ConfiguracoesIndexacaoFG.txt";
	private static int cellCheckAlert = 6;
	private static String testDataPathHONDA = 'Data Files/Backoffce/Index/IndexHONDA'
	private static String testDataPathMDS = 'Data Files/Backoffce/Index/IndexMDS'
	private static String testDataPathSICOOB = 'Data Files/Backoffce/Index/IndexSICOOB'
	private static String testDataPathSICRED ='Data Files/Backoffce/Index/IndexSIDREDI'
	private static String testDataPathNUP = 'Data Files/Backoffce/Index/IndexNUP'
    private static String testDataPathSANTANDER = "Data Files/Backoffce/Index/IndexSANTANDER"
	
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(String index){
		switch (index){
			case "HONDA":
			data = findTestData(testDataPathHONDA)
			path = data.sourceUrl
			break;
			
			case "MDS":
			data = findTestData(testDataPathMDS)
			path = data.sourceUrl
			break;
			
			case "SICOOB":
			data = findTestData(testDataPathSICOOB)
			path = data.sourceUrl
			break;
			
			case "SICREDI":
			data = findTestData(testDataPathSICRED)
			path = data.sourceUrl
			break;
			
			case "NUP":
			data = findTestData(testDataPathNUP)
			path = data.sourceUrl
			break;
			
			case "SANTANDER":
			data = findTestData(testDataPathSANTANDER)
			path = data.sourceUrl
			break;
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
			WebUI.delay(10);
			WebUI.delay(10);
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoIndexacaoFG(String aba, String a, String ...args ){
		String[] valores = args;
		System.out.println()
		for(int i = 0; i<valores.size();i++){
			System.out.println("["+i+"] INDEX FG ["+valores[i]+"]")
			initDataFiles(valores[i].toString())
			nomeIndex = retornaTitulo()
			if(validaDados(cellCheck)){
		       String dataInicio = ExtractDates.dataAtual();
		       String horaInicial =  ExtractDates.horaAtualizada();
		       start = Instant.now();
		       fluxoCompletoIndexacaoFG(aba,a)
		       reprocessaFalhas(aba,a, valores[i].toString())
		       end = Instant.now();
		       timeLapnse = Duration.between(start, end);
		       String dataFinal = ExtractDates.dataAtual();
		       String horaFinal =  ExtractDates.horaAtualizada();
		       try{
		          enviaEmailIndexFGDinamico(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal, nomeIndex, valores[i].toString() );
		       } catch (Exception e){}
		   }
		}
	}
	
	public static boolean fluxoCompletoIndexacaoFG(String aba, String a){
		try{
			if(validaDados(cellCheck)){
				init(aba,a);
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						String arquivo = registros[3].toString()
						if(!validaExistenciaDeArquivo(filepathIndex+arquivo+".xlsx")){
							if(indecacao(registros, aba)) {
								try{
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 4, aba);
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 5, aba);
									//				deleteFileDiretory(completeFilePath)
								} catch (Exception e){}
							} else {
								try{
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 4, aba);
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: Falha ao anexar arquivo. "+alerta, 5, aba);
								} catch (Exception e){}
							}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 4, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: Arquivo "+arquivo+" não encontrado para indexação", 5, aba);
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
	public static boolean indecacao( List<String> registros, String aba){
		WebUI.delay(15);
		System.out.println()
		MenuMatriz.consultasEstatisticas();
		MenuMatriz.consultasGerais();
		boolean enviado
		String tela
		try{
			alerta=""
			completeFilePath=""
			alteraJanejaGenerica(1, 5)
			System.err.println("alteraJanejaGenerica")
			"Valida campo proposta devido a auxência do nome da tela"
			String campoProposta = recuperaValor('//*[@id="sectionApoliceContent"]/table/tbody/tr[1]/td[1]/table/tbody/tr/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[1]/table/tbody/tr/td[1]', 'innerText', 5)
			if(campoProposta.equals("Proposta")){
				//Proposta
				preencheXpath('//*[@id="apolice"]', registros[0], 1);
				//Pesquisar
				selecionaItemLista('Pesquisar', '//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td', 'td')
				WebUI.delay(5);
				msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
				System.out.println("alertaFG ["+msgAlert+"] ["+msgAlert.length()+"]")
				if(!alertListBlocList(msgAlert)){
					// innerText : "DADOS GERAIS (AZR)"
					WebUI.delay(3);
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
					if(listaDeTelas(tela)){

						//Ficha Gestão
						selecionaItemLista('Ficha Gestão', '//*[@id="menuLateral"]', 'span')
						alteraJanejaGenerica(2, 15)
						WebUI.delay(5);
						//Ficha Gestão
						tela = recuperaValor( '/*/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/div/div/div[1]', 'innerText', 2)
						if(listaDeTelas(tela)){
							//Indexação
							try{
								selecionaItemLista('Indexação', '/*/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div', 'span')
							} catch (Exception e){}

							try{
								selecionaItemLista('Indexación', '/*/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div', 'span')
							} catch (Exception e){}

							WebUI.delay(10);
							alteraJanejaGenerica(3, 5)
							//Valida tela "ANEXAR DOCUMENTOS - LI"
							tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
							if(listaDeTelas(tela)){
								System.out.println()
								//Modelo/Descrição
								preencheXpath('//*[@id="codigo"]', registros[1], 1);
								commandTAB('//*[@id="codigo"]')
								
								msgAlert  = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
				                System.out.println("alertaFG ["+msgAlert+"] ["+msgAlert.length()+"]")
			                    if(!alertListBlocList(msgAlert)){
								  WebUI.delay(10);
								  String fileName
								  String pathIndex = '//*[@id="upFile1"]'
								  try{
								  //File Path
								  fileName = identificaArquivosDiretorio( filepathIndex, registros[3]+".xlsx");
								  
								  System.err.println(fileName);
								  } catch (Exception e){}
								
								  try{
								     fileUpload(pathIndex,  filepathIndex+fileName , 5)
								  } catch (Exception e){}
								
								  //Observações/texto
								  //preencheXpath('//*[@id="texto"]', registros[2], 1);
								  preencheXpath('//*[@id="texto"]', registros[2], 1);
								  //Salvar
								  selecionaItemLista('Salvar', '//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table', 'td')
								  WebUI.delay(5);
								  alerta = pegaMSGAlerta()
								  System.out.println(alerta)
								  fechaAlerta()
								  enviado = true;
								  //fecharJanelaAtual(3)
								  alteraJanejaGenerica(2, 0)
								  fecharJanelaAtual(2)
								  alteraJanejaGenerica(1, 0)
								  fecharJanelaAtual(1)
								  alteraJanejaGenerica(0, 0)
								} else {
								alerta = msgAlert;;
								enviado = false;
								fecharJanelaAtual(3)
								alteraJanejaGenerica(2, 0)
								fecharJanelaAtual(2)
								alteraJanejaGenerica(1, 0)
								fecharJanelaAtual(1)
								alteraJanejaGenerica(0, 0)
								}
							} else {
								alerta = "Tela - ANEXAR DOCUMENTOS - LI - Não apresentada";
								enviado = false;
							}
						} else {
							alerta = "Tela - Ficha de Gestão  - Não apresentada";
							enviado = false;
						}
					} else {
						alerta = "Tela - Ficha de Gestão - Não apresentada";
						enviado = false;
					}

				}else {
					enviado = false
					alerta=msgAlert;
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 1)

				}
			}
		} catch (Exception e){
			enviado = false;
			alerta = "Falha no processo de indexação ";
		}
		return enviado
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailIndexacaoFG(String tipoAba){
		initDataFiles()
		if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\IndexacaoFG\\ConfiguracoesIndexacaoFG.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = "IndexacaoFG.xlsx"
			//sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
			sendEmail.CreateEmail.createEmailSend(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba, nomeIndex );
		}
		try{
			WebUI.closeBrowser()
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba, String a, String index){
		System.err.println('Reprocessa Falhas')
		initDataFiles(index )
		if(validaDados(cellCheck)){
			fluxoCompletoIndexacaoFG(aba,a)
			reprocessaFalhas(aba,a)
		}
	}

	/**
	 * @author Alz_sup54955
	 * @param p
	 * @param n
	 * @return
	 */

	public static String identificaArquivosDiretorio(String p, String n){
		String fileName;
		File f = new File(p);
		File [] files = f.listFiles();
		for(int i = 0; i < files.size(); i++){
			if(files[i].getName().toString().contains(n)){
				fileName = files[i].getName();
			}
		}
		return fileName;
	}

	/**
	 * @author BE54955
	 * @param t
	 * @param f
	 * @return
	 */

	public static String textoCampoObs(String t, String f, String d){
		Map<String, String> obs = new HashMap<String, String>()
		obs.put('HONDA','Envio Pro Atividade Honda, Vistoria e Laudo de Inspeção '+d);
		obs.put('SICREDI',f.replace(".xlsx", ""));
		return obs.get(t.toString());
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
	public static enviaEmailIndexFGDinamico(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal, String assunto, String index){
	 try{
		 System.out.println()
		 if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\IndexacaoFG\\ConfiguracoesIndexacaoFG.txt";
			
			switch (index){
				case "HONDA":
				docName = "IndexacaoFG_Bloqueios_Honda.xlsx"
				break;
				
				case "MDS":
				docName = "IndexacaoFG_RECUSA_MDS.xlsx"
				break;
				
				case "SICOOB":
				docName = "IndexacaoFGRECUSA_SICOOB_MINASEG.xlsx"
				break;
				
				case "SICREDI":
				docName = "IndexacaoFG_SIDRED.xlsx"
				break;
				
				case "NUP":
				docName = "IndexacaoFG_NUP.xlsx"
				break;
				
				case "SANTANDER":
				docName = "IndexacaoFG_SANTANDER.xlsx"
				break;
			}
			
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			// FORMATA SAIDA DO TEMPO DE EXECUÇÃO
			long segds = timeLapnse.toSeconds();
			String hours = timeLapnse.toHours()+"h "+
						timer.converteMinutos(timeLapnse.toHours(), timeLapnse.toMinutes())+"m "+
						timer.converteSegundos(timeLapnse.toMinutes(),segds)+"s";
			// ENVIO DE E-MAIL
			initDataFiles(index);
			// ENVIA E-MAIL DINAMICO
			emailDinamico.enviaEmailDinamico(sender, emaildestination, tipoAba, path, docName, emailDinamico.listaDeAlertas(data, cellCheckAlert)  , hours, horaInicial, horaFinal,  GetHost.getHostname() , dataInicio, dataFinal, assunto);
		 }
	  } catch (Exception e){}
	}
	
	
	
	
	public static String retornaTitulo(){
		List<String> registros = new ArrayList<>();
		System.out.println()
		for(int i = 1; i <= data.getRowNumbers(); i++) {
			registros.add(data.getValue(3, i));
		}
		return formataTituloIndexFG(registros)
	}
	
	
	public static String formataTituloIndexFG(List<String> registros){
		String index;
		Map<Integer,String> hasdates = new HashMap<Integer, String>();
		StringBuilder strb = new StringBuilder()
		StringBuilder strdts = new StringBuilder()
		
		for(int i=0 ; i < registros.size();i++){
			System.out.println(registros[i])
			strdts.append(registros[i])
		}
		hasdates.putAll(ExtractDates.getSubDates(strdts.toString()))
		HashSet<String> strDT = new HashSet<String>();
		HashSet<String> strIDX = new HashSet<String>();
		
		for(int j = 0; j <hasdates.size(); j++){
			if(j==0){
			  strIDX.add(StringFormatter.removeNumerosDeStrings(registros[j]))
			}
			strDT.add(" - "+hasdates.get(j))
		}
		index = strIDX[0]
		for(int k=0; k<strDT.size();k++){
			strb.append(strDT[k])
		}
		System.out.println("formataTituloIndexFG ["+index+strb.toString()+"]")
		return index+strb.toString()
	}
}

package auto


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



public class ReentradaPropostaAutoFuncionalidades  extends rpaCommands {

	private static TestData data
	private static String testDataPath = 'Data Files/Auto/ReentradaPropostaAuto/ReentradaPropostaAutoData'
	private static String path
	private static String alerta
	private static int cellCheck = 4;
	private static String filepath = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Auto\\ReentradaPropostaAuto\\Arquivos\\"
	private static String completeFilePath
	private static String nomeIndex;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "ReentradaPropostaAuto.xlsx"
	private static int cellCheckAlert = 5;
	private static String pathPDFValue ="C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Auto\\ReentradaPropostaAuto\\Arquivos\\";
	private static ArrayList<ArrayList<String>> cotacoes = new ArrayList<ArrayList<String>>();
	private static List<String> cotacao = new ArrayList<String>()
	private static ArrayList<ArrayList<String>> formularios = new ArrayList<ArrayList<String>>();
	private static List<String> formulario = new ArrayList<String>()
	private static Map<Integer,String> hasdates = new HashMap<Integer, String>();
	private static boolean enviado = true;
	private static String xpath;
	private static String tela;
	private static String msgAlert;
	private static int linha;
	private static String texto;
	private static String cpfcnpjTomador;
	private static String abaGestorOperacoes = "GestorOperacoes";
	private static String aGOS = "GOS";
	private static List<WebElement> listOfTickets;
	private static filePathGO = "C:\\Users\\Public\\";
	private static String produto;
	private static String cotTicket; 
	private static boolean ticket = true;
	
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
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static boolean validaDadosGestorOperacao(){
		boolean value = true;
		try{
			acessoAmbiente(abaGestorOperacoes,aGOS)
			System.out.println();
			// AGENT PAINEL
			clicarEmElementoDaTela('//*[@id="side-menu"]/li[2]', 0);
			WebUI.delay(5)
		    listOfTickets = listaDeElementos('//*[@id="layout-wrapper"]/div[2]/div[1]/div/div/div[1]/div[3]','h5')
		   if(listOfTickets.size()==0){
			  value = false;
		   }
		} catch (Exception e){
		  value = false 
		}
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
	 * @author Alz_sup54955
	 */
	public static void carregarTodosOsArquivos(){
		//for(int i = 1; i <= data.getRowNumbers(); i++) {
		//	List<String> registros = new ArrayList<>();
		//	lista( registros , i)
			carregaArquivosCotacoes(leitorPdf.pegaNomeDoArquivo("PDF da cotação"))
			carregaArquivosFormularios(leitorPdf.pegaNomeDoArquivo("ticket"))
		//}
	}
	
	/**
	 * @author Alz_sup54955
	 */
	public static void deletarTodosOsArquivos(){
		if(leitorPdf.existeArquivo("PDF da cotação")){
		  deletaArquivoPdf(leitorPdf.pegaNomeDoArquivo("PDF da cotação"))
		}
		
		if(leitorPdf.existeArquivo("ticket")){
		  deletaArquivoPdf(leitorPdf.pegaNomeDoArquivo("ticket"))
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param arquivo
	 * @return
	 */
	public static carregaArquivosCotacoes(String arquivo){
		System.out.println("carregaArquivosCotacoes\n["+arquivo+"]")
		try{
			cotacoes.add(leitorPdf.pdfReaderNPagesList(arquivo));
		} catch (Exception e){}
	}
	
	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static List<String> pegaCotacao(){
		List<String> linha = new ArrayList<String>()
		for(int i = 0; i<cotacoes.size();i++ ){
			int l = i+1;
			System.out.println("\nList: "+l+" **************************************************************************\n")
			for(int j = 0; j<cotacoes.get(i).size();j++){
			    linha.add(cotacoes[i][j].toString());
			}
		}
		
		for(int i = 0;i< linha.size();i++){
			System.out.println("["+i+"] ["+linha.get(i)+"]")
		}
		
		return linha;
	}
	
	/**
	 * @author Alz_sup54955
	 * @param i
	 * @return
	 */
	public static List<String> pegaCotacaoIndex(int i){
		List<String> linha = new ArrayList<String>()
			System.out.println("\nList: "+i+" **************************************************************************\n")
			for(int j = 0; j<cotacoes.get(i).size();j++){
				linha.add(cotacoes[i][j].toString());
			}
		
		for(int x = 0;x< linha.size();x++){
			System.out.println("["+x+"] ["+linha.get(x)+"]")
		}
		
		return linha;
	}
	
	/**
	 * @author Alz_sup54955
	 * @param arquivo
	 * @return
	 */
	public static carregaArquivosFormularios(String arquivo){
		try{
			formularios.add(leitorPdf.pdfReaderNPagesList(arquivo));
		} catch (Exception e){}
	}
	
	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static List<String> pegaFormulario(){
		List<String> linha = new ArrayList<String>()
		for(int i = 0; i<formularios.size();i++ ){
			int l = i+1;
			System.out.println("\nList: "+l+" **************************************************************************\n")
			for(int j = 0; j<formularios.get(i).size();j++){
				linha.add(formularios[i][j].toString());
			}
		}
		
		for(int i = 0;i< linha.size();i++){
			System.out.println("["+i+"] ["+linha.get(i)+"]")
		}
		
		return linha;
	}
	
	/**
	 * @author Alz_sup54955
	 * @param i
	 * @return
	 */
	public static List<String> pegaFormularioIndex(int i){
		List<String> linha = new ArrayList<String>()
		System.out.println("\nList: "+i+" **************************************************************************\n")
		for(int j = 0; j<formularios.get(i).size();j++){
				linha.add(formularios[i][j].toString());
		}
		for(int x = 0;x< linha.size();x++){
			System.out.println("["+x+"] ["+linha.get(x)+"]")
		}
		return linha;
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
	public static fluxoReentradaPropostaAuto(String aba, String a){
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		initDataFiles()
		fluxoCompletoReentradaPropostaAuto(aba,a)
		reprocessaFalhas(aba,a)
		FileInput.atualizaLog(Instant.now().toString(), pathFile)
		List<String> times = rpaCommands.convertToInstant(pathFile);
		String dataInicio = times[0];
		String horaInicial =  times[1];
		timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
		   enviaEmailReentradaPropostaAutoDinamico(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
		} catch (Exception e){
		}
	}

	

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoReentradaPropostaAuto(String aba, String a){
		try{
			enviado = true
			identificaTicketNaTela(aba, a)
		} catch (Exception e){}
	//	WebUI.closeBrowser();
	}

	
	
	public static void identificaTicketNaTela(String aba, String a){
		System.out.println("RECURSIVO...")
		ticket = existeTicket()
		while(ticket.equals(true)){
			reentradaFrota( aba, a)
			ticket = existeTicket()
		}
		System.out.println("SAINDO DO RECURSIVO...")
	}
	
	public static boolean existeTicket(){
		boolean b = false;
		if(enviado){
		  acessoAmbiente(abaGestorOperacoes,aGOS)
		}
		System.out.println();
		// AGENT PAINEL
		clicarEmElementoDaTela('//*[@id="side-menu"]/li[2]', 0);
		WebUI.delay(15)
		selecionaListaDeElementos('//*[@id="layout-wrapper"]/div[2]/div[1]/div/div/div[1]/div[3]','h5')
		int list = listOfElements.size()
		System.out.println(list);
		System.out.println();
	    if(list>0){
			b=true
		}	
		System.out.println("Ticket ["+b+"]");
		return b;
	}
	
	public static reentradaFrota(String aba,String a){
		System.out.println()
	//		for(int i = 1; i <= data.getRowNumbers(); i++) {
		        int i = 1
				int j =  i - 1
		//		if (data.getValue(cellCheck, i).equals('Processar')) {
					if(gestorOperacoesBaixaDeArquivos()){
					  System.out.println()
					  carregarTodosOsArquivos()
					  List<String> registros = new ArrayList<>();
					  List<String> cotacao = new ArrayList<String>()
					  List<String> formulario = new ArrayList<String>()
					  cotacao = pegaCotacaoIndex(0);
					  formulario = pegaFormularioIndex(0)
					  lista( registros , i)
					  init(aba,a);
						if(reentradaPropostaAuto(registros, cotacao, formulario, aba, a)) {
							try{
								deletarTodosOsArquivos()
								fecharTiketGestorOperacoes()
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 3, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 4, aba);
							} catch (Exception e){}
						} else {
							try{
								fecharTiketGestorOperacoesComErro(alerta)
								deletarTodosOsArquivos()
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 3, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: Falha ao importar os dados. "+alerta, 4, aba);
							} catch (Exception e){}
						}
						registros.clear();
						cotacao.clear();
						formulario.clear();
					} 
				//	} else {
				//	    try{
				//		  auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 3, aba);
				//		  auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: Falha ao importar arquivos do gestor de operações. "+alerta, 4, aba);
				//	    } catch (Exception e){}
				//		WebUI.closeBrowser();
				//	}
		
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
	public static boolean reentradaPropostaAuto( List<String> registros, List<String> cotacao, List<String> formulario, String aba, String a){
		menuMatriz()
		System.out.println("Cotação ["+cotacao.size()+"] Formulario ["+formulario.size()+"]")
		System.out.println()
		try{
			if(validaLeituraDeArquivo(cotacao, formulario)){
			alerta=""
			recuperaProduto(cotacao)
			alteraJanejaGenerica(1, 5)
			menuProducao();
			telaCotacao(cotacao)
			dadosBasicos(cotacao);
			dadosDeRisco(cotacao, formulario);
			tabelaDePrecos(cotacao, formulario);
			dadosEmissao(formulario);
			dadosClientes(cotacao, formulario);
			dadosComuns();
			if(produto.equals("Moto 1212")){ clausulas(cotacao, formulario); confirmarPagamento();} 
			else {confirmarPagamento(); clausulas(cotacao, formulario);}
			confirmacaoDeEmissao();
			emissaoRealizada();
			
			} else {
			  enviado = false
			}
		} catch (Exception e){
			enviado = false;
			alerta = "Falha: Processo não inicializado";
		}
		return enviado
	}
	

	public static boolean validaLeituraDeArquivo(List<String> cotacao, List<String> formulario){
		boolean v = true;
		boolean cot = true;
		boolean form = true;
		String strCot = "";
		String strForm = "";
		
		System.out.println( "["+cotacao[0].toString().length()+"] ["+formulario[0].toString().length()+"]")
		
		if( cotacao[0].toString().length() == 0 ){
			cot = false;
			strCot = "PDF da Cotação "
		 }
		 
		 if( formulario[0].toString().length() == 0 ){
			 form = false;
			 strForm = "Formulario"
		 }
		 
		 if( cot.equals(false) || form.equals(false) ){
			 v = false
			 alerta = "Não foi possível ler o arquivo : \n"+strCot +" \n"+ strForm;
		 }
		return v;
	}
	
	
	public static boolean  gestorOperacoesBaixaDeArquivos(){
		System.out.println();
		boolean b = true;
		try{
		//acessoAmbiente(abaGestorOperacoes,aGOS)
		System.out.println();
		// AGENT PAINEL 
		clicarEmElementoDaTela('//*[@id="side-menu"]/li[2]', 0);
		WebUI.delay(5)
		int index = 0;
		selecionaListaDeElementos('//*[@id="layout-wrapper"]/div[2]/div[1]/div/div/div[1]/div[3]','h5')
		System.out.println(listOfElements.size());
		System.out.println();
		for(int k = 0; k < listOfElements.size(); k++){
			System.out.println("["+k+"] ["+listOfElements[k].getText() +"] ["+index+"]" )
			if( k == index){
				int linha = k+1;
				//String xpath = '//*[@id="chat-670adebe-ab6c-4bac-9e71-7a924522120f"][1]['+linha+']'
				String xpath ='//*[@id="layout-wrapper"]/div[2]/div[1]/div/div/div[1]/div[3]/div/div[1]/div/ul/div/li/div'
				System.out.println(xpath)
				try{
					clicarEmElementoDaTelaJavascrip(xpath , 0);
				 } catch (Exception e){ System.err.println(e)}
				 break;
			 }
		}
		
		try{
		if(!leitorPdf.existeArquivo("ticket")){
		//DOWNLOAD TICKET
		clicarEmElementoDaTela('//*[@id="engagementScroll"]/div[1]/div/div[2]/div[2]/div/div[1]/div[2]/div[1]/div', 2);
		clicarEmElementoDaTela('//*[@id="downloadButton"]', 2);
		}
		} catch (Exception e){
		  b = false;
		}
		
		try{
		if(!leitorPdf.existeArquivo("PDF da cotação")){
		//DOWNLOAD COTAÇÃO 
		clicarEmElementoDaTela('//*[@id="layout-wrapper"]/div[2]/div[1]/div/div/div[3]/div/div[1]/div/div/div/div[2]/div[1]/div[1]', 2);
		WebUI.delay(5)
		selecionaListaDeElementos('//*[@id="layout-wrapper"]/div[2]/div[1]/div/div/div[3]/div/div[1]/div/div/div/div[2]/div[1]/div[2]','h6')
		System.out.println();
		for(int k = 0; k < listOfElements.size(); k++){
			 int linha = k+1;
			 System.out.println("["+k+"] ["+listOfElements[k].getText() +"] ["+index+"]" )
				 //String xpath = '//*[@id="chat-670adebe-ab6c-4bac-9e71-7a924522120f"][1]['+linha+']'
				 if( listOfElements[k].getText().contains("PDF da cotação (1).pdf")){
				 try{
					 clicarEmElementoDaTela('//*[@id="layout-wrapper"]/div[2]/div[1]/div/div/div[3]/div/div[1]/div/div/div/div[2]/div[1]/div[2]/div/div['+linha+']/div[3]', 2);
				  } catch (Exception e){ System.err.println(e)}
				  break;
				 }
		}
		}
		} catch (Exception e){}
		
		WebUI.delay(10)
		System.out.println();
		if(validaDownloadArquivos("PDF da cotação", "ticket")){
		
		try{
		//SAIR 
		//clicarEmElementoDaTela('//*[@id="page-header-user-dropdown"]/div/div[1]', 2);
		//clicarEmElementoDaTela('//*[@id="page-topbar"]/div/div[2]/div/div/div/button', 2);
		} catch (Exception e){
		  b = false;
		}
		
		try{
		//SAIR
		alteraJanejaGenerica(0, 0)
		//clicarEmElementoDaTela('/html/body/div[4]/div/div[1]/div/div/div[2]/button[2]', 2);
		//fecharJanelaAtual(0)
		} catch (Exception e){
		  b = false;
		}
		
		} else {
		String msg = "Devolução - não foi possível fazer o download do "+nomeArquivo();
		enviado = true;
		fecharTiketGestorOperacoesComErro(msg)
		deletarTodosOsArquivos()
		b = false;
		}
		} catch (Exception e){
		 b = false;
		}
	 return b;	 
	}
	
	public static fecharTiketGestorOperacoesComErro(String msg){
		System.out.println(msg+" "+enviado)
		System.out.println();
		
		if(!enviado){
		  acessoAmbiente(abaGestorOperacoes,aGOS)
		  WebUI.delay(15)
		  // AGENT PAINEL
		  clicarEmElementoDaTela('//*[@id="side-menu"]/li[2]', 0);
		  WebUI.delay(10)
		  int index = 0;
		  selecionaListaDeElementos('//*[@id="layout-wrapper"]/div[2]/div[1]/div/div/div[1]/div[3]','h5')
		  System.out.println(listOfElements.size());
		  System.out.println();
		  for(int k = 0; k < listOfElements.size(); k++){
			  System.out.println("["+k+"] ["+listOfElements[k].getText() +"] ["+index+"]" )
			  if( k == index){
				  int linha = k+1;
				  //String xpath = '//*[@id="chat-670adebe-ab6c-4bac-9e71-7a924522120f"][1]['+linha+']'
				  String xpath ='//*[@id="layout-wrapper"]/div[2]/div[1]/div/div/div[1]/div[3]/div/div[1]/div/ul/div/li/div'
				  System.out.println(xpath)
				  try{
					  clicarEmElementoDaTelaJavascrip(xpath , 0);
				   } catch (Exception e){ System.err.println(e)}
				   break;
			   }
		  }
		}
		WebUI.delay(5)
		try{
			//MENU
			clicarEmElementoDaTela('//*[@id="engagementScroll"]/div[1]/div/div[1]/div[4]/li/div', 2);
		} catch (Exception e){}
		WebUI.delay(5)
		
		try{
			//Inserir comentário
			 selecionaItemLista('Inserir comentário', '//*[@id="layout-wrapper"]/div[2]/div[1]/div/div/div[2]', 'label');
		} catch (Exception e){}
		WebUI.delay(5)
		try{
		 // Public Note
		 xpath = '//*[@id="commentary"]'
		 preencheXpath(xpath, msg, 0);
		} catch (Exception e){}
		WebUI.delay(12)
		try{
			// Add Comment
			xpath = '/html/body'
			selecionaItemLista('Add Comment', xpath, 'span');
		} catch (Exception e){}
		
		WebUI.delay(2)
		
		try{
			//MENU
			clicarEmElementoDaTela('//*[@id="engagementScroll"]/div[1]/div/div[1]/div[4]/li/div', 2);
		} catch (Exception e){}
		
		WebUI.delay(2)
		
		try{
			//Transfer chat
			 selecionaItemLista('Transfer chat', '//*[@id="layout-wrapper"]/div[2]/div[1]/div/div/div[2]', 'label');
		} catch (Exception e){}
		
		WebUI.delay(2)
		
		try{
			//ALLIANZ_ENTRADA_MANUAL_AUTO
			 selecionaItemLista('ALLIANZ_ENTRADA_MANUAL_AUTO', '/*/body/div[4]/div/div[1]/div/div/form/div[2]/div[2]/div/div/div/div/div/div[2]', 'div');
		} catch (Exception e){}
		
		try{
			// Transfer
			xpath = '/html/body/div[4]/div/div[1]/div/div'
			selecionaItemLista('Transfer', xpath, 'button');
		} catch (Exception e){}
		
		enviado = false
		
	}
	
	
	public static boolean fecharTiketGestorOperacoes(){
		boolean b = true;
		if(enviado){
		try{
		acessoAmbiente(abaGestorOperacoes,aGOS)
		System.out.println();
		// AGENT PAINEL
		clicarEmElementoDaTela('//*[@id="side-menu"]/li[2]', 0);
		WebUI.delay(5)
		
		int index = 0;
		selecionaListaDeElementos('//*[@id="layout-wrapper"]/div[2]/div[1]/div/div/div[1]/div[3]','h5')
		System.out.println();
		for(int k = 0; k < listOfElements.size(); k++){
			System.out.println("["+k+"] ["+listOfElements[k].getText() +"] ["+index+"]" )
			if( k == index){
				int linha = k+1;
				//String xpath = '//*[@id="chat-670adebe-ab6c-4bac-9e71-7a924522120f"][1]['+linha+']'
				
				String xpath ='//*[@id="layout-wrapper"]/div[2]/div[1]/div/div/div[1]/div[3]/div/div[1]/div/ul/div/li/div'
				
				System.out.println(xpath)
				try{
					clicarEmElementoDaTelaJavascrip(xpath , 0);
				 } catch (Exception e){ System.err.println(e)}
				 break;
			 }
		}
		WebUI.delay(5)
		try{
			//MENU 
			clicarEmElementoDaTela('//*[@id="engagementScroll"]/div[1]/div/div[1]/div[4]/li/div', 2);
		} catch (Exception e){}
		WebUI.delay(5)
		try{
			//Tabulation
			 selecionaItemLista('Tabulation', '//*[@id="layout-wrapper"]/div[2]/div[1]/div/div/div[2]', 'label');
		} catch (Exception e){}
		
		xpath = '/html/body/div[4]/div/div[1]/div/div'
		try{
			//Solicitsç~so finalizada
			selecionaItemLista('Choose...', xpath, 'div');
			selecionaItemLista('Solicitação Finalizada', xpath, 'div');
		} catch (Exception e){}
		
		try{
			//Emissão manual – outros
			selecionaItemLista('Choose...', xpath, 'div');
			selecionaItemLista('Emissão manual – outros', xpath, 'div');
		} catch (Exception e){}
		
		try{
			// Public Note
		xpath = '/*/body/div[4]/div/div[1]/div/div/form/div[3]/div[2]/div[1]/textarea'
		//cotTicket = '133503785';
		String txt = "Prezado Corretor.\n\n"+
                     "Proposta gerada em sistema através do número "+cotTicket+" com\n"+ 
                     "PDF do pedido em Ficha de Gestão.\n\n"+
                     "Allianz Seguros"+
                     "Gestão Operacional" 
					 preencheXpath(xpath, txt, 0);
		} catch (Exception e){}
		
		try{
			// Confirm Tabulation
			xpath = '/html/body/div[4]/div/div[1]/div/div'
			selecionaItemLista('Confirm Tabulation', xpath, 'button');
		} catch (Exception e){}
		
		try{
		//SAIR
			enviado = false;
		//MENU
		//clicarEmElementoDaTela('//*[@id="page-header-user-dropdown"]/div/div[1]', 2);
		//Logout
		//selecionaItemLista('Logout', '//*[@id="page-topbar"]/div/div[2]/div/div/div', 'button');
		//fecharJanelaAtual(0)
		} catch (Exception e){
		  b = false;
		  enviado = true
		}
		
		} catch (Exception e){
		 b = false;
		 enviado = true
		}
	 return b;
	}
	
	}
	
	public static recuperaProduto(List<String> cotacao){
		//Produto
		linha = StringFormatter.indexLine(cotacao, "Tipo de Seguro:")
		System.out.println(linha)
		produto = StringFormatter.startWhitch(cotacao[linha], "Produto:" )
		System.out.println("Produto :"+produto)
	}
	
	public static menuMatriz(){
		WebUI.delay(15);
		System.out.println()
		MenuMatriz.producaoSinistro();
		MenuMatriz.subMenu("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul", "a", "Produção");
	}
	
	public static menuProducao(){
		tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
		 if(listaDeTelas(tela)){ // TELA - MENU PRODUÇÃO
			  //Pesquisar Cotações
			  selecionaItemLista('Pesquisar Cotações', '//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table', 'td');
			  enviado = true;
		 } else {
		   fecharJanelaAtual(1)
		   alteraJanejaGenerica(0, 0)
		   alerta = "Falha: Tela - Ficha de Gestão  - Não apresentada";
		   enviado = false;
	   }
	}
	
	public static telaCotacao(List<String> cotacao){
		if(enviado){
			WebUI.delay(15);
			tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
			 if(listaDeTelas(tela)){ // TELA - COTAÇÃO
				 //Numero Coração
				 xpath = '//*[@id="txtPresupuesto"]';
				 linha = StringFormatter.indexLine(cotacao, "Cotação:");
				 System.out.println(linha)
				 limpaCampo(xpath, 0)
				 String cotacaor = StringFormatter.startWhitch(cotacao[linha], "Cotação:")
				 System.out.println("Cotação "+StringFormatter.checkStringValue(cotacaor.toString()))
				 if(StringFormatter.checkStringValue(cotacaor.toString())){
				 preencheXpath(xpath, cotacaor, 0);
				 //Pesquisar
				 selecionaItemLista('Pesquisar', '//*[@id="sectionBSectionBar"]/table/tbody/tr/td/table', 'td');
				 //Alterar
				 selecionaItemLista('Alterar', '//*[@id="detalleSectionBar"]/table/tbody/tr/td/table', 'td');
				 } else {
				   alerta = "Numero da cotação não encontrado. Falta o pdf da cotação."
				   enviado = false;
				 }
			 } else {
			   fecharJanelaAtual(1)
			   alteraJanejaGenerica(0, 0)
			   alerta = "Falha: Tela - Cotação  - Não apresentada";
			   enviado = false;
		   }
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param cotacao
	 * @return
	 */
	public static dadosBasicos(List<String> cotacao){
		if(enviado){
		WebUI.delay(15);
		System.out.println()
		tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
		 if(tela.contains("D. BÁSICOS")){ // TELA DADOS BASICOS
			 System.out.println()
			 
			 linha = StringFormatter.buscaComAntecessor(cotacao, "INFORMAÇÕES DO SEU SEGURO", "Vigência" )
			 hasdates = ExtractDates.getSubDates(cotacao[linha].replaceAll("24H",""))
			 // INICIO VIGENCIA
			 xpath = '//*[@id="DatosGeneralesBean$fechaEfecto"]';
			 limpaCampo(xpath, 0)
			 preencheXpath(xpath, hasdates.get(0), 0);
			 
			 try{
			 // TIPO SEGURO
			 linha = StringFormatter.indexLine(cotacao, "Tipo de Seguro:");
			 String tipoSeguro = StringFormatter.betweenStrings(cotacao[linha], "Tipo de Seguro:" , "Produto:")
			 selecionaItemLista(tipoSeguro.replace("Renovação","Renovacao")  , '//*[@id="DatosGeneralesBean$tipoSeguro"]', 'option');
			 } catch (Exception e){}
					
			 //CPF CNPJ TOMADOR
			 xpath = '//*[@id="IntervinientesBean$nifTomador_doc"]';
			 linha = StringFormatter.indexLine(cotacao, "CPF/CNPJ:");
			 System.out.println(linha)
			 limpaCampo(xpath, 0)
			 String cpfcnpjTomador = StringFormatter.startWhitch(cotacao[linha], "CPF/CNPJ:")
			 preencheXpath(xpath, cpfcnpjTomador.replaceAll("[./-]",""), 0);
			 
			 //NOME TOMADOR
			 xpath = '//*[@id="IntervinientesBean$nomFigTomadorValue"]';
			 limpaCampo(xpath, 0)
			 String nomeTomador = StringFormatter.betweenStrings(cotacao[linha], "Nome:" , "CPF/CNPJ:")
			 preencheXpath(xpath, nomeTomador, 0);
			 //limpaCampo(xpath, 0)
			 
			 
			 //CPF/CNPJ Tomador SE OS DOIS CPFS SÃ IGUAIS = SIM 
			 linha = StringFormatter.buscaComAntecessor(cotacao, "INFORMAÇÕES DO CONDUTOR PRINCIPAL", "CPF" )
			 String cpfcnpjCondutor = StringFormatter.startWhitch(cotacao[linha], "CPF:")
			 if(!cpfcnpjTomador.equals(cpfcnpjCondutor)){
				 
			   try{ 
			   //CPF CNPJ PROPRIETARIO
			   xpath = '//*[@id="IntervinientesBean$nifPropietario_doc"]'
			   linha = StringFormatter.indexLine(cotacao, "CPF/CNPJ:")
			   limpaCampo( xpath , 0)
			   String cpfcnpjProprietario = StringFormatter.startWhitch(cotacao[linha], "CPF/CNPJ:")
			   preencheXpath( xpath , cpfcnpjProprietario.replace("[./-]",""), 0);
			   } catch (Exception e){}
				
			   try{
			   //NOME PROPRIETARIO
			   xpath = '//*[@id="IntervinientesBean$nomFigPropietarioValue"]'
			   limpaCampo(xpath, 0)
			   String nomeProprietario = StringFormatter.betweenStrings(cotacao[linha], "Nome:" , "CPF/CNPJ:")
			   preencheXpath(xpath, nomeProprietario, 0);
			   } catch (Exception e){}
			   
			   try{
			   //CPF CNPJ CONDUTOR
			   xpath = '//*[@id="IntervinientesBean$nifConductor_doc"]'
			   limpaCampo(xpath, 0)
			   preencheXpath(xpath, cpfcnpjCondutor.replaceAll("[./-]",""), 0);
			   } catch (Exception e){}
			   
			   try{
			   //NOME CONDUTOR
			   linha = StringFormatter.indexLine(cotacao, "INFORMAÇÕES DO CONDUTOR PRINCIPAL")
			   System.out.println(linha);
			   xpath = '//*[@id="IntervinientesBean$nomFigConductorValue"]'
			   limpaCampo(xpath, 0)
			   String nomeCondutor = StringFormatter.betweenStrings(cotacao[linha+1], "Nome:" , "CPF/CNPJ:")
			   preencheXpath(xpath, nomeCondutor, 0);
			   } catch (Exception e){}
			   
			   try{
				   //NOME CONDUTOR
				   
				   String nomeCondutor = StringFormatter.betweenStrings(cotacao[linha+1], "Nome:" , "CPF:")
				   preencheXpath(xpath, nomeCondutor, 0);
			  } catch (Exception e){}
			   
			 }
			 // CATEGORIA DE RISCO
			 linha = StringFormatter.indexLine(cotacao, "Categoria de Risco:")
			 String categoriaRisco = StringFormatter.betweenStrings(cotacao[linha], "Categoria de Risco:" , "")
			 System.out.println(linha+" "+categoriaRisco);
			 selecionaItemLista(categoriaRisco , '//*[@id="CategoriaRiesgoBean$catRiesgo"]', 'option');
			 //Avançar
			 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
			 WebUI.delay(5);
			 try{
				 String alert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
				 if(alertListContinueList(alert)){
					 //Avançar
					 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
				 }
			 } catch (Exception e){}
			 
		 } else {
		   fecharJanelaAtual(1)
		   alteraJanejaGenerica(0, 0)
		   alerta = "Falha: Tela - D. BÁSICOS  - Não apresentada";
		   enviado = false;
	    }
		 
		} 
		 
	}
	
	/**
	 * @author Alz_sup54955
	 * @param cotacao
	 * @param formulario
	 * @return
	 */
	public static  dadosDeRisco(List<String> cotacao, List<String> formulario){
		if(enviado){
			WebUI.delay(10);
			msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			System.out.println("alertaFG ["+msgAlert+"] ["+msgAlert.length()+"]")
			if(!alertListBlocList(msgAlert)){
				System.out.println()
				 tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
				 if(tela.contains("DADOS DE RISCO")){
					 
					 String dispositivoAtual = "Nenhum";
					 String ramo = "31";
					 String numeroItem = "1";
					 String tipoRelacionamento="Pessoa Física para Pessoa Física";
					 /**
					  * @author BE54955
					  * INFORMAÇÕES DO VEÍCULO
					  */
					 //PLACA
					 xpath = '//*[@id="InfVeicBean$placa"]'
					 linha = StringFormatter.indexLine(cotacao, "Placa:")
					 limpaCampo(xpath, 0)
					 preencheXpath(xpath, StringFormatter.betweenStrings(cotacao[linha], "Placa:" , "Classe"), 0);
					 //CHASSI
					 xpath = '//*[@id="InfVeicBean$chassi"]'
					 linha = StringFormatter.indexLine(cotacao, "Chassi:")
					 limpaCampo(xpath, 0)
					 preencheXpath(xpath, StringFormatter.betweenStrings(cotacao[linha], "Chassi:" , "Grupo:"), 0);
					 
					 //FIPE
					 linha = StringFormatter.buscaComAntecessor(cotacao, "Casco", "FIPE" )
					 System.out.println(linha)
					 selecionaItemLista(StringFormatter.nextStopLength(cotacao[linha], "-", 0, 1).replace("%","")   , '//*[@id="InfVeicBean$percFipe"]', 'option');
					 //FIPE - AUTO
					 selecionaItemLista(StringFormatter.stopString(cotacao[linha], "FIPE" ).replace("%","").trim()   , '//*[@id="InfVeicBean$percFipe"]', 'option');
					 fechaAlerta()
					 
					 //TIPO FRANQUIA
					 linha = StringFormatter.buscaComAntecessor(cotacao, 'Franquia Valor (R$)', "Franquia" )
					 System.out.println(linha);
					 selecionaItemLista(StringFormatter.nextStop(cotacao[linha], "Franquia", 0)  , '//*[@id="InfVeicBean$tipoFranquia"]', 'option');
					 System.out.println()
					 
					 //TIPO FRANQUIA - AUTO
					 linha = StringFormatter.buscaComAntecessor(cotacao, 'Franquia Valor (R$)', "Normal" )
					 System.out.println(linha);
					 selecionaItemLista(StringFormatter.sequenceString(cotacao[linha], "Normal")  , '//*[@id="InfVeicBean$tipoFranquia"]', 'option');
					 System.out.println()
					 
					 //TIPO FRANQUIA - MOTO 
					 linha = StringFormatter.buscaComAntecessor(cotacao, 'Franquia Valor (R$)', "Normal" )
					 System.out.println(linha);
					 String tipoFranquia = tipoFranquiaMoto(StringFormatter.sequenceString(cotacao[linha], "Normal") ) 
					 selecionaItemListaAtributo(tipoFranquia, '//*[@id="InfVeicBean$tipoFranquia"]' ,"option", "value" )
					 
					 //CEP PERNOITE
					 xpath = '//*[@id="InfVeicBean$cepPernoite"]'
					 linha = StringFormatter.indexLine(cotacao, "Pernoite:")
					 limpaCampo(xpath, 0)
					 preencheXpath(xpath, StringFormatter.startWhitch(cotacao[linha], "Pernoite:" ).replace("-",""), 0);
					 // VEICULO BLINDADO
					 //selecionaItemLista(StringFormatter.betweenStrings(cotacao[21], "Categoria de Risco:" , ""), '//*[@id="InfVeicBean$veicBlind"]', 'option');
					 // DISPOSITIVO ATUAL
					 selecionaItemLista(dispositivoAtual, '//*[@id="InfVeicBean$dispositivoAtual"]', 'option');
					 
					 System.out.println()
					 
					 
					 
					 /**
					  * @author BE54955
					  * 	INFORMAÇÕES DE TDO
					  */
					 try{
						 //Tipo de Relacionamento
						 selecionaItemLista(tipoRelacionamento, '//*[@id="InfoTDOBean$grauParentesco"]', 'option');
						 //Data de Nascimento do Segurado
						 xpath = '//*[@id="InfoTDOBean$dataNascimento"]'
						 linha = StringFormatter.indexLine(formulario, "Data de Nascimento:")
						 hasdates = ExtractDates.getSubDates(formulario[linha])
						// preencheXpathFormulario(xpath, hasdates.get(0), 0);
					 } catch (Exception e){}
					 
					 
					 
					 /**
					  * @author BE54955
					  * ACESSÓRIOS 
					  */
					 //
					 try{
						 //TIPO FRANQUIA
						 xpath = '//*[@id="InfVeicBean$cepPernoite"]'
					     linha = StringFormatter.indexLine(cotacao, "Kit gás:")
						 String kitGas = StringFormatter.startWhitch(cotacao[linha], "gás:" )
						 if(kitGas.equals("Sim")){
							 // Veículo possui kit gás instalado?
							 selecionaItemLista("Sim", '//*[@id="InfVeicBean$veicPossKitGas"]', 'option');
							 // Deseja incluir a cobertura do kit gás?
							 selecionaItemLista("Sim", '//*[@id="InfVeicBean$kitGasContratado"]', 'option');
							 //Acessório - Kit Gás
							 linha = StringFormatter.indexLineIgnoreDuplicates(cotacao, "Acessório", "Não Contratado")
							 kitGas = StringFormatter.nextStop(cotacao[linha], "Gás", 0)
							 System.out.println(kitGas)
							 xpath = '//*[@id="AcessoriosBean$vlrAcessKitGas"]'
							 limpaCampo(xpath, 0)
							 preencheXpath(xpath,kitGas.replace(".","")  , 0);
							 commandTAB(xpath)
						 }
					 }catch (Exception e){}
					 
					 System.out.println()
					 /**
					  * @author BE54955
					  * DADOS COMPLEMENTARES
					  */
					 // Grupo de Renovação
					 try{
					 linha = StringFormatter.indexLine(cotacao, "Grupo:")
					 String grupo = cotacao[linha]
					 selecionaItemLista(StringFormatter.startWhitch(grupo.replace("0",""), "Grupo:" ).trim() , '//*[@id="DadosCompBean$grpRenovacao"]', 'value');
					 } catch (Exception e){}
				
					 // CLASSE DE BONUS
					 try{
					 linha = StringFormatter.indexLine(cotacao, "Bônus")
					 //selecionaItemLista(StringFormatter.startWhitch(cotacao[linha], "Bônus:" ).trim() , '//*[@id="DadosCompBean$classeBonus"]', 'option');
					 selecionaItemListaAtributo(StringFormatter.startWhitch(cotacao[linha], "Bônus:" ).trim() , '//*[@id="DadosCompBean$classeBonus"]', 'option', 'value');
					 } catch (Exception e){}
					 
					 System.out.println()
					 
					 // Veículo igual apólice anterior
					 try{
					 linha = StringFormatter.indexLine(cotacao, "Veículo Igual ao Anterior:")
					 switch (StringFormatter.startWhitch(cotacao[linha], "Anterior:" ).trim()){
						 case "Sim":
							 selecionaItemListaAtributo("S" , '//*[@id="DadosCompBeanContent"]/table/tbody/tr[2]/td/table/tbody/tr/td[2]/table/tbody/tr/td[2]', 'input', 'value');
							 break;
						 case "Não":
						     selecionaItemListaAtributo("N" , '//*[@id="DadosCompBeanContent"]/table/tbody/tr[2]/td/table/tbody/tr/td[2]/table/tbody/tr/td[2]', 'input', 'value');
							 break;
					 }
					 } catch (Exception e){}
					 
					 System.out.println()
					 
					 /**
					  * @author BE54955
					  * DADOS COMPLEMENTARES - MOTO 
					  */
					 // Grupo de Renovação - MOTO
					 try{
					 linha = StringFormatter.indexLine(cotacao, "Grupo:")
					 String grupo = cotacao[linha]
					 String ngrupo = StringFormatter.startWhitch(grupo, "Grupo:" ).trim()
					 int n = Integer.parseInt(ngrupo)
					 ngrupo = String.valueOf(n)
					 selecionaItemLista( grupoRenovacao( ngrupo)  , '//*[@id="DadosCompBean$grpRenovacao"]', 'option');
					 } catch (Exception e){}
				
					 // CLASSE DE BONUS - MOTO
					 try{
					 linha = StringFormatter.indexLine(cotacao, "Bônus")
					 String bonus = cotacao[linha]
					 String nbonus = StringFormatter.startWhitch(cotacao[linha], "Bônus:" ).trim()
					 int n = Integer.parseInt(nbonus)
					 nbonus = String.valueOf(n)
					 selecionaItemLista(nbonus , '//*[@id="DadosCompBean$classeBonus"]', 'option');
					 } catch (Exception e){}
					 
					 
					 /**
					  * @author BE54955
					  * INFORMAÇÕES APÓLICE ANTERIOR
					  */
					 //RAMO
					 try{
					 selecionaItemLista(ramo , '//*[@id="InfoApoAnteriorBean$ramo"]', 'option');
					 //Número item
					 xpath = '//*[@id="InfoApoAnteriorBean$nmrItem"]'
					 limpaCampo(xpath, 0)
					 preencheXpath(xpath, numeroItem, 0);
					 //APOLICE
					 xpath = '//*[@id="InfoApoAnteriorBean$nmrApolice"]'
					 linha = StringFormatter.indexLine(formulario, "Apolice")
					 //limpaCampo(xpath, 0)
					 preencheXpathFormulario(xpath, StringFormatter.startWhitch(formulario[linha], "Anterior:" ), 0);
					 } catch (Exception e){}
					 
					 try{
					 //CI
					 xpath = '//*[@id="InfoApoAnteriorBean$nmrCI"]'
					 linha = StringFormatter.indexLine(formulario, "CI:")
					 //limpaCampo(xpath, 0)
					 preencheXpathFormulario(xpath, StringFormatter.startWhitch(formulario[linha].replace(".",""), "CI:" ), 0);
					 } catch (Exception e){
					   preencheXpathFormulario(xpath, StringFormatter.startWhitch(formulario[linha], "CI:" ), 0);
					 }
					 System.out.println()
					 
					 
					 System.out.println()
					 
					 try{
					 //FIM DA VIGENCIA
					 xpath = '//*[@id="InfoApoAnteriorBean$fimVigencia"]'
					 linha = StringFormatter.indexLine(formulario, "Vigencia Termino:")
					 //preencheXpathFormulario(xpath, StringFormatter.startWhitch(formulario[linha].replaceAll("/", ""), "Termino:" ), 0);
					 } catch (Exception e){}
					 
					 
					 
					 
					 try{
						 linha = StringFormatter.buscaComAntecessor(cotacao, "INFORMAÇÕES DO SEU SEGURO", "Vigência" )
						 hasdates = ExtractDates.getSubDates(cotacao[linha].replaceAll("24H",""))
						 //FIM DA VIGENCIA
						 xpath = '//*[@id="InfoApoAnteriorBean$fimVigencia"]'
						 linha = StringFormatter.indexLine(cotacao, "Fim da vigência anterior")
						 System.out.println(linha)
						 // preencheXpathFormulario(xpath, StringFormatter.startWhitch(cotacao[linha].replaceAll("/", ""), "anterior:" ), 0);
						 System.out.println(hasdates.get(0))
						 preencheXpath(xpath, hasdates.get(0), 0);
					 } catch (Exception e){}
					 
					 
					 
					 try{
					 hasdates = ExtractDates.getSubDates(formulario[linha])
					 //limpaCampo(xpath, 0)
					 // preencheXpathFormulario(xpath, hasdates.get(0), 0);
					 } catch (Exception e){}
					 
					 
					 
					 /**
					  * @author BE54955
					  * INFORMAÇÕES DE TDO
					  */
					 try{
						 //Data de Nascimento do Segurado
						 xpath = '//*[@id="InfoTDOBean$dataNascimento"]'
						 linha = StringFormatter.indexLine(formulario, "Data de Nascimento:")
						 hasdates = ExtractDates.getSubDates(formulario[linha])
						// preencheXpathFormulario(xpath, hasdates.get(0), 0);
					 } catch (Exception e){}
					 
					 System.out.println()
					 
					 
					 /**
					  * @author BE54955
					  * ACESSÓRIOS
					  */
					 
					 
					 /**
					  * @author BE54955
					  * CARGA(S) TRANSPORTADA(S) MAIS FREQUENTE(S)
					  */
					 try{
						 linha = StringFormatter.indexLine(cotacao, "Carga(s) selecionada(s):")
						 String tipoMaterial = StringFormatter.startWhitch(cotacao[linha], "selecionada(s):" )
						 if(validaListaDeCargas(tipoMaterial)){
							 selecionaItemLista(tipoMaterial , '//*[@name="CargasBean$codigoCarga"]', 'option');
							 //ADICIONAR
							 clicarEmElementoDaTela('//*[@id="button"]', 0);
						 }
					 }catch (Exception e){}
					 
					 
					 
						 /**
						  * @author BE54955
						  * QUESTIONARIO AVALIAÇÃO DE RISCO  - Caminhão 1213
						  */
					      //Condutor Principal
					      xpath = '//*[@id="nmeCondutor"]'
					      linha = StringFormatter.buscaComAntecessor(cotacao, 'INFORMAÇÕES DO CONDUTOR PRINCIPAL', "utilizado:" )
					      try{
					      limpaCampo(xpath, 0)
					      String cpfCondutor = StringFormatter.startWhitch(cotacao[linha], "utilizado:" ).trim()
					      preencheXpath(xpath, cpfCondutor, 0);
					      } catch (Exception e){}
					 
					 
						 //CPF CONDUTOR
						 xpath = '//*[@id="QuestCaminhaoBean$cpfCondutor"]'
						 linha = StringFormatter.buscaComAntecessor(cotacao, 'INFORMAÇÕES DO CONDUTOR PRINCIPAL', "CPF" )
						 try{
						 limpaCampo(xpath, 0)
						 String cpfCondutor = StringFormatter.startWhitch(cotacao[linha], "CPF:" ).trim()
						 preencheXpath(xpath, cpfCondutor.replaceAll("[./-]",""), 0);
						 } catch (Exception e){}
						 
						 
						 //NOME CONDUTOR
						 xpath = '//*[@id="nmeCondutor"]'
						 try{
						 limpaCampo(xpath, 0)
						 preencheXpath(xpath, StringFormatter.startWhitch(cotacao[linha], "Nome:" ).trim(), 0);
						 } catch (Exception e){}
						 
						 
						 //DATA NASCIMENTO
						 linha = StringFormatter.indexLine(formulario, "Data de Nascimento:")
						 xpath = '//*[@id="QuestCaminhaoBean$dtaNascimento"]';
						 try{
						 hasdates = ExtractDates.getSubDates(formulario[linha])
						 //limpaCampo(xpath, 0)
						 preencheXpathFormulario(xpath, hasdates.get(0), 0);
						 } catch (Exception e){}
						 
						 try{
							 linha = StringFormatter.indexLine(cotacao, "Tipo de Seguro:")
							 String produto = StringFormatter.startWhitch(cotacao[linha], "Produto:" )
							 System.out.println("Produto :"+produto)
							 
							 
							 // Validar quando for cotação de automovel
							 try{
							 switch (produto){
								 case "Automoveis 1211":
									 //Motorista principal utilizado:
						             linha = StringFormatter.indexLine(cotacao, "Motorista Principal utilizado:")
						             selecionaItemLista(StringFormatter.startWhitch(cotacao[linha], "utilizado:" ), '//*[@id="QuestCaminhaoBean$respostaMotorista"]', 'option');
									 break;
								 case "Caminhão 1213":
									 //Motorista principal utilizado:
						             linha = StringFormatter.indexLine(cotacao, "Condutor Principal utilizado:")
						             selecionaItemLista(StringFormatter.startWhitch(cotacao[linha], "utilizado:" ), '//*[@id="QuestCaminhaoBean$respostaMotorista"]', 'option');
									 break;
							 }
							 } catch (Exception e){}
							 
						 
						 // SEXO CONDUTOR
						 
						 try{
						 //Estado Civil
						 linha = StringFormatter.indexLine(cotacao, "Estado Civil:")
						 selecionaItemLista(StringFormatter.startWhitch(cotacao[linha], "Civil:" ), '//*[@id="QuestCaminhaoBean$respostaEstCivil"]', 'option');
						 } catch (Exception e){}
						 
						 try{
						 //Possui seguro da carga transportada durante da viagem?
						 linha = StringFormatter.indexLine(cotacao, "Possui seguro da carga transportada durante a viagem?")
						 selecionaItemLista(StringFormatter.startWhitch(cotacao[linha], "viagem?" ), '//*[@id="QuestCaminhaoBean$respostaSegCarga"]', 'option');
						 } catch (Exception e){}
						 
						 try{
						 //O veículo está alienado?
						 linha = StringFormatter.indexLine(cotacao, "O veículo está alienado?")
						 selecionaItemLista(StringFormatter.startWhitch(cotacao[linha], "alienado?" ), '//*[@id="QuestCaminhaoBean$respostaAlienado"]', 'option');
						 } catch (Exception e){}
						 
						 try{
						 //Região(ões) de circulação mais freqüente(s):
						 linha = StringFormatter.indexLine(cotacao, "Região(ões) de circulação mais frequente(s)?")
						 selecionaItemLista(StringFormatter.startWhitch(cotacao[linha], "frequente(s)?" ), '//*[@id="QuestCaminhaoBean$respostaCirculacao"]', 'option');
						 } catch (Exception e){}
						 
						 try{
						 //Mantém o veículo em garagem quando não está em serviço?
						 linha = StringFormatter.indexLine(cotacao, "Mantém o veículo em garagem quando não está em serviço?")
						 selecionaItemLista(StringFormatter.startWhitch(cotacao[linha], "serviço?" ), '//*[@id="QuestCaminhaoBean$respostaGaragem"]', 'option');
						 } catch (Exception e){}
						 
						 try{
						 //Mirige após 22h por mais de 15% do tempo semanal?
						 linha = StringFormatter.indexLine(cotacao, "Dirige após 22h por mais de 15% do tempo semanal?")
						 selecionaItemLista(StringFormatter.startWhitch(cotacao[linha], "semanal?" ), '//*[@id="QuestCaminhaoBean$respostaDirige22h"]', 'option');
						 } catch (Exception e){}
						 
						 try{
						 //Sistema de gerenciamento de risco:
						 linha = StringFormatter.indexLine(cotacao, "Sistema de gerenciamento de risco:")
						 selecionaItemLista(StringFormatter.startWhitch(cotacao[linha], "risco:" ), '//*[@id="QuestCaminhaoBean$respostaRisco"]', 'option');
						 } catch (Exception e){}
						 
					 }catch (Exception e){}
					 System.out.println()
					 try{
					 /**
					  * @author BE54955
					  * QUESTIONARIO AVALIAÇÃO DE RISCO  - Automoveis 1211
					  */
					 //CPF CONDUTOR
					 xpath = '//*[@id="QuestAutoBean$cpfCondutor"]'
					 linha = StringFormatter.buscaComAntecessor(cotacao, 'INFORMAÇÕES DO CONDUTOR PRINCIPAL', "CPF" )
					 limpaCampo(xpath, 0)
					 preencheXpath(xpath, StringFormatter.startWhitch(cotacao[linha], "CPF:" ).trim(), 0);
					 //NOME CONDUTOR
					 xpath = '//*[@id="QuestAutoBean$nmeCondutor"]'
					 limpaCampo(xpath, 0)
					 preencheXpath(xpath, StringFormatter.startWhitch(cotacao[linha], "Nome:" ).trim(), 0);
					 } catch (Exception e){}
					 
					 
					 try{
					 //DATA NASCIMENTO
					 linha = StringFormatter.indexLine(formulario, "Data de Nascimento:")
					 xpath = '//*[@id="QuestAutoBean$dtaNascimento"]';
					 hasdates = ExtractDates.getSubDates(formulario[linha])
					 //limpaCampo(xpath, 0)
					 preencheXpathFormulario(xpath, hasdates.get(0), 0);
					 } catch (Exception e){}
					 
					 // SEXO CONDUTOR
					 //selecionaItemLista(StringFormatter.betweenStrings(cotacao[21], "Categoria de Risco:" , "") , '//*[@id="QuestAutoBean$respostaSexo"]', 'option');
					 
					 try{
					 // ESTADO CIVIL CONDUTOR
					 linha = StringFormatter.indexLine(cotacao, "Estado Civil:")
					 selecionaItemLista(StringFormatter.startWhitch(cotacao[linha], "Civil:" ).trim() , '//*[@id="QuestAutoBean$respostaEstCivil"]', 'option');
					 // DESEJA AMPLIAR COBERTURA DO SEGURO
					 linha = StringFormatter.indexLine(cotacao, "Deseja ampliar a cobertura do seguro para condutores do veículo segurado com idade entre 18 a 25 anos:")
					 int next = linha +1;
					 selecionaItemLista(ampliaCobertura(StringFormatter.startWhitch(cotacao[linha], "anos:"), cotacao[next]) , '//*[@id="QuestAutoBean$respostaFilhos"]', 'option');
					 // PRINCIPAL CONDUTOR RESIDE EM
					 linha = StringFormatter.indexLine(cotacao, "O principal condutor reside em:")
					 selecionaItemLista(StringFormatter.nextStop(cotacao[linha], "em:",0 ).trim(), '//*[@id="QuestAutoBean$respostaResid"]', 'option');
					 } catch (Exception e){}
					 
					 
					 System.out.println()
					 
					 
					 /**
					  * @author BE54955
					  * SERVIÇOS
					  */
					 try{
					 //ASSISTENCIA 24 HORAS
					 linha = StringFormatter.indexLine(cotacao, "Assistência 24 hs")
					 selecionaItemLista(StringFormatter.nextStopLength(cotacao[linha], "hs" , 0, 3).replace("  ", " ") , '//*[@id="ServicosBean$codTpAssist"]', 'option');
					 
					 //VIDRO
					 linha = StringFormatter.indexLine(cotacao, "Vidros, lanternas, faróis e retrovisores")
					 String v = StringFormatter.nextStopLength(cotacao[linha], "retrovisores" , 0, 2)
                     //String vidroFormatado = formataVidros(v);
					 System.out.println(v);
					 selecionaItemLista(v, '//*[@id="ServicosBean$codigoPlanoVidros"]', 'option');
					 } catch (Exception e){}
					 
					 try{
					 //Díarias carro reserva
					 linha = StringFormatter.indexLine(cotacao, "Carro Reserva")
					 String cr = StringFormatter.nextStopLength(cotacao[linha], "Reserva" , 0, 2).toLowerCase().trim()
					 System.out.println(cr)
					 selecionaItemLista(cr, '//*[@id="ServicosBean$codCarroReserva"]', 'option');
					 } catch (Exception e){}
					 
					 try{
					 //Tipo de carro reserva
					 linha = StringFormatter.indexLine(cotacao, "Tipo de Carro Reserva")
					 String cr = StringFormatter.nextStop(cotacao[linha], "Reserva",0 ).trim()
					 System.out.println(cr)
					 selecionaItemLista(cr, '//*[@id="ServicosBean$tipoCarroReserva"]', 'option');
					 } catch (Exception e){}
					 
					
					 System.out.println()
					
					 /**
					  * @author BE54955
					  * QUESTIONARIO AVALIAÇÃO DE RISCO  - Moto 1212
					  */
					 //CPF CONDUTOR
					 xpath = '//*[@id="QuestMotoBean$cpfCondutor"]'
					 linha = StringFormatter.buscaComAntecessor(cotacao, 'INFORMAÇÕES DO CONDUTOR PRINCIPAL', "CPF" )
					 try{
					 limpaCampo(xpath, 0)
					 String cpfCondutor = StringFormatter.startWhitch(cotacao[linha], "CPF:" ).trim()
					 preencheXpath(xpath, cpfCondutor.replaceAll("[./-]",""), 0);
					 } catch (Exception e){}
					 
					 
					 //NOME CONDUTOR
					 xpath = '//*[@id="QuestMotoBean$nmeCondutor"]'
					 try{
					 limpaCampo(xpath, 0)
					 preencheXpath(xpath, StringFormatter.startWhitch(cotacao[linha], "Nome:" ).trim(), 0);
					 } catch (Exception e){}
					 
					 
					 //DATA NASCIMENTO
					 linha = StringFormatter.indexLine(formulario, "Data de Nascimento:")
					 xpath = '//*[@id="InfoTDOBean$dataNascimento"]';
					 try{
					 hasdates = ExtractDates.getSubDates(formulario[linha])
					 //limpaCampo(xpath, 0)
					 preencheXpathFormulario(xpath, hasdates.get(0), 0);
					 } catch (Exception e){}
					 
					 
					 //Sexo Condutor 
					 //linha = StringFormatter.indexLine(cotacao, "Estado Civil:")
					 //selecionaItemLista(StringFormatter.startWhitch(cotacao[linha], "Civil:" ), '//*[@id="QuestCaminhaoBean$respostaEstCivil"]', 'option');
					 try{
					 //Estado Civil
					 linha = StringFormatter.indexLine(cotacao, "Estado Civil:")
					 selecionaItemLista(StringFormatter.startWhitch(cotacao[linha], "Civil:" ), '//*[@id="QuestMotoBean$respostaEstCivil"]', 'option');
					 } catch (Exception e){}
					 
					 try{
					 //Deseja ampliar a cobertura do seguro para condutores do veículo segurado com idade entre 18 a 25 anos?
					 linha = StringFormatter.indexLine(cotacao, "Deseja ampliar")
					 String cobertura = StringFormatter.startWhitch(cotacao[linha], "anos:" )
					 selecionaItemLista(textoAmpliarCobertura(cobertura), '//*[@id="QuestMotoBean$respostaFilhos"]', 'option');
					 } catch (Exception e){}
					 
					 try{
					 //O principal condutor reside em:
					 linha = StringFormatter.indexLine(cotacao, "O principal condutor reside em:")
					 selecionaItemLista( StringFormatter.startWhitch(cotacao[linha], "em:" ) , '//*[@id="QuestMotoBean$respostaResid"]', 'option');
					 } catch (Exception e){}
					 
					 try{
					 //Mantém o veículo em garagem na residência?
					 linha = StringFormatter.indexLine(cotacao, "Mantém o veículo em garagem na residência?")
					 selecionaItemLista( StringFormatter.startWhitch(cotacao[linha], "residência?" ) , '//*[@id="QuestMotoBean$respostaGarResid"]', 'option');
					 } catch (Exception e){}
					 
					 try{
					 //Mantém o veículo em garagem no trabalho?
					 linha = StringFormatter.indexLine(cotacao, "Mantém o veículo em garagem no trabalho?")
					 selecionaItemLista( StringFormatter.startWhitch(cotacao[linha], "trabalho?" ) , '//*[@id="QuestMotoBean$respostaGarTrab"]', 'option');
					 } catch (Exception e){}
					 System.out.println()
					 try{
					 //Mantém o veículo em garagem na faculdade / universidade / escola / pós-graduação / cursos?
					 linha = StringFormatter.indexLine(cotacao, "Mantém o veículo em garagem na faculdade")
					 selecionaItemLista( veiculoEmGaragem(StringFormatter.startWhitch(cotacao[linha], "cursos?" )) , '//*[@id="QuestMotoBean$respostaGarFacul"]', 'option');
					 } catch (Exception e){}
					 
					 System.out.println()
					 /**
					  * @author BE54955
					  * SERVIÇOS MOTO 
					  */
					 try{
					 //Lant. Faróis e Retrovisores
					 linha = StringFormatter.indexLine(cotacao, "RETROVISORES")
					 String retrovisor = StringFormatter.startWhitch(cotacao[linha], "-" ).trim()
					 selecionaItemLista(retrovisoresMoto(retrovisor) , '//*[@id="ServicosBean$codigoPlanoVidros"]', 'option');
					 } catch (Exception e){}
					
					 //try{
					 //Vidros
					 //linha = StringFormatter.buscaComAntecessorSelecionavel(cotacao, "Vidros", 1 )
					 //String v = vidros(cotacao[linha])
					 //System.out.println(v)
					 //selecionaItemLista(v, '//*[@id="ServicosBean$codigoPlanoVidros"]', 'option');
					 //} catch (Exception e){}
					 
					 //ACEITAR
					 clicarEmElementoDaTela('//*[@id="o_3"]', 0);
					 
					 WebUI.delay(5);
					 try{
						 String alert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
						 if(alertListContinueList(alert)){
							 //Avançar
							 clicarEmElementoDaTela('//*[@id="o_3"]', 0);
						 }
					 } catch (Exception e){}
					 
				 } else {
				 fecharJanelaAtual(1)
				 alteraJanejaGenerica(0, 0)
				 alerta = "Falha: Tela - DADOS DE RISCO   - Não apresentada";
				 enviado = false;
			   }
		  } else {
			 fecharJanelaAtual(1)
			 alteraJanejaGenerica(0, 0)
			 alerta = msgAlert;
			 enviado = false;
		 }
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param formulario
	 * @return
	 */
	public static tabelaDePrecos(List<String> cotacao, List<String> formulario){
		try{
		if(enviado){
			
			String premioTotal; 
			String nPremioTotal; 
			String nValorPacote; 
			String valorPacote;
			double limite = 2.00;
			double dif;
			String vr;
			
			WebUI.delay(10);
			msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			if(!alertListBlocList(msgAlert)){
				
				tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
				if(tela.contains("TABELA DE PREÇOS")){
					System.out.println()
					//Produto
					linha = StringFormatter.indexLine(cotacao, "Tipo de Seguro:")
					System.out.println(linha)
					produto = StringFormatter.startWhitch(cotacao[linha], "Produto:" )
					System.out.println("Produto :"+produto)
					
					//Oferta Contratada
					//linha = StringFormatter.indexLine(formulario, "Oferta Contratada:")
					//String sPacote = StringFormatter.nextStop(formulario[linha], "Contratada:",0 ).trim()
					linha = StringFormatter.indexLine(cotacao, "Parcelas Juros")
					String sPacote;
				//	String sPacote = StringFormatter.startWhitch(cotacao[linha], "Juros" ).trim()
				//	System.out.println("Pacote :"+sPacote)
					
					if(produto.equals("Automoveis 1211")){
				//		if(sPacote.length() == 0){
						linha = StringFormatter.indexLine(formulario, "Oferta Contratada:")
						sPacote = StringFormatter.startWhitch(formulario[linha], "Contratada:" ).trim()
						System.out.println("Pacote :"+ sPacote)
					//	}
					}
					
					if(produto.equals("Moto 1212")){
						linha = StringFormatter.indexLine(formulario, "Oferta Contratada")
						sPacote = StringFormatter.startWhitch(formulario[linha], "Contratada:" ).trim()
						System.out.println("Pacote :"+ sPacote)
					}
					
					if(produto.equals("Caminhão 1213")){
						linha = StringFormatter.indexLine(formulario, "Oferta Contratada:")
						sPacote = StringFormatter.startWhitch(formulario[linha], "Contratada:" ).trim()
						System.out.println("Pacote :"+ sPacote)
					}
					
					
					//NUMERO DE PERCELAS - FORMULARIO
					linha = StringFormatter.indexLine(formulario, "Numero de Parcelas:")
					String p = StringFormatter.nextStop(formulario[linha],"Parcelas:", 0).replace("x","");
					
					try{
					switch (produto){
						case "Automoveis 1211":
							clickXpath(selecionaPacoteAuto(sPacote), Integer.parseInt(p))
							break;
						case "Caminhão 1213":
							//clickXpath(selecionaPacoteCaminhao(sPacote), Integer.parseInt(p))
						    clickXpath(selecionaPacoteAuto(sPacote), Integer.parseInt(p))
							break;
						case "Moto 1212":
							//clickXpath(selecionaPacoteCaminhao(sPacote), Integer.parseInt(p))
							clickXpath(selecionaPacoteAuto(sPacote), Integer.parseInt(p))
							break;
					}
					} catch (Exception e){}
					
					
					
					//WebUI.delay(5);
						//NUMERO DE PERCELAS
					if(Integer.parseInt(p)>0){
						//PERCELADO
						clicarEmElementoDaTela('//*[@id="blParrillaContent"]/table[4]/tbody/tr/td[3]', 0);
						WebUI.delay(5);
					}
					
					//NUMERO PARCELAMENTO -FORMULARIO
					selecionaItemListaContains(p, '//*[@id="ParrillaBean$numFracciones"]', 'option');
					
					ajusteTelaTabelaDePrecos(formulario)
					
					//FORMA DE PAGAMENTO - FORMULARIO
					linha = StringFormatter.indexLine(formulario, "Forma de Pagamento:")
					selecionaItemListaContains(StringFormatter.startWhitch( formulario[linha], "Pagamento:"), '//*[@id="ParrillaBean$primRecibo"]', 'option');
					
					
					//DIA DE PAGAMENTO - FORMULARIO
					xpath = '//*[@id="ParrillaBean$diaPago"]'
					linha = StringFormatter.indexLine(formulario, "Melhor Dia de Pagamento:")
					//limpaCampo(xpath, 0)
					preencheXpathFormulario(xpath, StringFormatter.nextStop(formulario[linha], "Pagamento:",0 ).trim(), 0);
					
					
					
					//SALDO ONLINE
					
					try{
					//DESC CAP
					linha = StringFormatter.indexLine(formulario, "comerciais:")
					String CAP = StringFormatter.nextStop(formulario[linha], "Cap", 0)
					//selecionaItemListaContains(CAP, '//*[@id="DescuentosParrillaBean$descCAP"]', 'option');
					} catch (Exception e ){}
					//TIPO COMISSIONAMENTO
					
					//ANTECIPAÇÃO DE COMISSÃO
					
					//AGRAVO
					
					//COMISSÃO
					linha = StringFormatter.indexLine(formulario, "Percentual Comissão:")
					System.out.println(StringFormatter.nextStop(formulario[linha], "Comissão:",0 ).trim())
					xpath = '//*[@id="ComisionamientoBean$porcComision"]'
					//limpaCampo(xpath, 0)
					//preencheXpathFormulario(xpath, StringFormatter.nextStop(formulario[linha], "Comissão:", 0), 0);
					System.out.println()
					
					//CALCULAR
					clicarEmElementoDaTela('//*[@id="btnCalcular"]', 0);
					ajusteTelaTabelaDePrecos(formulario)
					System.out.println()
					//WebUI.delay(15);
					valorPacote = recuperaValorPremio(produto, sPacote, p)
					
					try{
						//IC GLOBAL
						xpath = '//*[@id="PrimaTotalParrillaBean$icglobal"]'
						//limpaCampo(xpath, 0)
						System.out.println()
						//if(produto.equals("Moto 1212")){
						  linha = StringFormatter.indexLine(formulario, "Premio Total: ")
						  nPremioTotal = StringFormatter.startWhitch( formulario[linha], "Total:")
						  premioTotal = nPremioTotal.replace("R\$", "")
						//} else {
						//  linha = StringFormatter.indexLine(cotacao, "Preço Total")
						//  premioTotal = StringFormatter.startWhitch( cotacao[linha], "Total")
						//}
						
						//nPremioTotal = premioTotal.replace("R\$", "")
						//nValorPacote = valorPacote.replace("R\$", "")
						//System.out.println(nPremioTotal+" - "+nValorPacote)
						System.out.println("["+premioTotal+"] ["+valorPacote+"]")
					}catch (Exception e ){}
					
					
					if(  premioTotal.length()>0 && valorPacote.length()>0 ){
					try{
						  if(!validarPremios(premioTotal, valorPacote, Integer.parseInt(p))){
							System.out.println("VALOR PACOTE "+valorPacote+" PREMIO TOTAL "+ premioTotal);
							String icGlobal = calculoICGlobal(valorPacote, premioTotal, Integer.parseInt(p));
							System.out.println("IC GLOBAL "+ icGlobal);
							preencheXpathFormulario(xpath, icGlobal , 1);
							//CALCULAR
							clicarEmElementoDaTela('//*[@id="btnCalcular"]', 0);
							WebUI.delay(5);
							ajusteTelaTabelaDePrecos(formulario)
							WebUI.delay(5);
							vr = recuperaValorPremio(produto, sPacote, p)
							dif = calculaDiferenca(premioTotal,vr, Integer.parseInt(p) );
							System.out.println("Dif "+dif)
							}
						} catch (Exception e ){}
						
						
					if(limiteDeDIferençaDeCalculoProposta(dif, limite)){	
					//EMITIR
					clicarEmElementoDaTela('//*[@id="ParrillaBean$btnEmitir"]', 0);
					WebUI.delay(3);
					fechaAlerta();
					WebUI.delay(3);
					try{
						String alert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
						if(alertListContinueList(alert)){
							try{
							//Avançar
							clicarEmElementoDaTela('//*[@id="o_3"]', 0);
							
							} catch (Exception e){}
							
							try{
							//Emitir
							clicarEmElementoDaTela('//*[@id="ParrillaBean$btnEmitir"]', 0);
							} catch (Exception e){}
							
							WebUI.delay(3);
							fechaAlerta();
							WebUI.delay(3);
						}
					} catch (Exception e){}
		
					} else {
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  alerta = "Falha: Diferença de "+dif+" do valor calculado. Total: "+premioTotal+" - "+valorPacote+" Valor recuperado: "+vr+"";
					  enviado = false;
					}
					} else {
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  alerta = "Falha: Valores dos pacotes não apresentada na tela Tabela de Preços";
					  enviado = false;
					}
				} else {
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  alerta = "Falha: Tela - TABELA DE PREÇOS - Não apresentada";
				  enviado = false;
			  }
		 } else {
		   fecharJanelaAtual(1)
		   alteraJanejaGenerica(0, 0)
		   alerta = msgAlert;
		   enviado = false;
		 }
		}
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  alerta = msgAlert;
		  enviado = false;
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param dif
	 * @param limite
	 * @return
	 */
	public static boolean limiteDeDIferençaDeCalculoProposta(double dif, double limite){
		boolean v = false;
		if(dif<limite){
			v=true;
		}
		return v;
	}
	
	/**
	 * @author Alz_sup54955
	 * @param formulario
	 * @return
	 */
	public static dadosEmissao(List<String> formulario){
		if(enviado){
			WebUI.delay(10);
			msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
			if(!alertListBlocList(msgAlert)){
				tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
				if(tela.contains("D. EMISSÃO")){
					/**
					 * @author BE54955
					 * DADOS BANCÁRIOS
					 * Conta Corrente												 */
					//
					System.out.println()
					
					try{
					// titular
					linha = StringFormatter.indexLine(formulario, "Segurado Titular da Conta:")
					String t = StringFormatter.nextStop(formulario[linha], "Conta:",0 ).trim()
					clicarEmElementoDaTela(titular(t), 0);
					} catch (Exception e){}
					
					
					try{
						linha = StringFormatter.indexLine(formulario, "Forma de Pagamento:")
						String formaPgto = StringFormatter.nextStop(formulario[linha], "Pagamento:",0 )
						if(formaPgto.equals("Débito em conta")){
						//EDITAR CC
						clicarEmElementoDaTela('//*[@id="DatosBancariosBean$ccc_cuenta_btnEdit"]', 0);
						}
					}catch (Exception e){}
					
					try{
						// CONTA CORRENTE
						selecionaItemListaContains('Conta corrente', '//*[@id="DatosBancariosBean$ccc_cuenta_typeAccount"]', 'option');
						// BANCO  AGENCIA DCD
						linha = StringFormatter.indexLine(formulario, "Codigo do Banco:")
						String banco = StringFormatter.nextStop(formulario[linha], "Banco:",0 ).trim()
						linha = StringFormatter.indexLine(formulario, "Agencia:")
						String agencia = StringFormatter.nextStop(formulario[linha], "Agencia:",0 ).trim()
						selecionaItemListaContains(banco, '//*[@id="DatosBancariosBean$ccc_cuenta_vBank"]', 'option');
						xpath = '//*[@id="DatosBancariosBean$ccc_cuenta_vOffice"]';
						//limpaCampo(xpath, 0)
						preencheXpathFormulario(xpath, agencia, 0);
						// DIGITOAGENCIA 
						linha = StringFormatter.indexLine(formulario, "Digito da Agencia:")
						String digAgencia = StringFormatter.nextStop(formulario[linha], "Agencia:",0 ).trim()
						xpath = '//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlOffice"]';
						preencheXpathFormulario(xpath, digAgencia, 0);
						// NUMERO CONTA DCC
						System.out.println(StringFormatter.indexLine(formulario, "Conta:"))
						String conta =  StringFormatter.nextStop(formulario[27], "Conta:",0 ).trim()
						xpath = '//*[@id="DatosBancariosBean$ccc_cuenta_vAccNumber"]';
						//limpaCampo(xpath, 0)
						preencheXpathFormulario(xpath, conta, 0);
						System.out.println(StringFormatter.indexLine(formulario, "Digita da Conta:"))
						String digito = StringFormatter.nextStop(formulario[30], "Conta:",0 ).trim()
						xpath = '//*[@id="DatosBancariosBean$ccc_cuenta_vDigCtrlAcc"]';
						//limpaCampo(xpath, 0)
						preencheXpathFormulario(xpath, digito, 0);
						//CHECK
						clicarEmElementoDaTela('//*[@id="DatosBancariosBean$ccc_cuenta_btnConfirm"]', 0);
						WebUI.delay(3);
						fechaAlerta();
					} catch(Exception e){}
					
					/**
					 * @author BE54955
					 * DADOS BANCÁRIOS
					 * CC REEMBOLSO												 */
					//
					try{
						// NOME TITULLAR
						//System.out.println(StringFormatter.nextStop(formulario[41], "Titular:",0 ).trim())
						//xpath = '//*[@id="ComisionamientoBean$porcComision"]'
						//limpaCampo(xpath, 0)
						//preencheXpath(xpath, StringFormatter.nextStop(formulario[41], "Comissão:", 0), 0);
						// CPF
						//System.out.println(StringFormatter.nextStop(formulario[65], "Titular:",0 ).trim())
						//xpath = '//*[@id="ComisionamientoBean$porcComision"]'
						//limpaCampo(xpath, 0)
						//preencheXpath(xpath, StringFormatter.nextStop(formulario[65], "Comissão:", 0), 0);
						// TIPO CONTA
						//selecionaItemListaContains('Conta corrente', '//*[@id="DatosBancariosBean$ccc_cuenta_vBank"]', 'option');
					}catch (Exception e){}
					//AVANÇAR
					clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
					
				} else  {
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 0)
				alerta = "Falha: Tela - D. Emissão - Não apresentada";
				enviado = false;
			   }
		   } else {
			 fecharJanelaAtual(1)
			 alteraJanejaGenerica(0, 0)
			 alerta = msgAlert;
			 enviado = false;
		   }
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param cotacao
	 * @param formulario
	 * @return
	 */
	public static dadosClientes(List<String> cotacao, List<String> formulario){
		if(enviado){
			System.out.println()
			WebUI.delay(10);
			
			String ddd = "11";
			String sexo = "Masculino";
			String profissao = "Engenheiro";
			String estadoCivil = "Casado";
			String nomeMae = "ISA PACE";
			String pis = "0";
			String inss = "0";
			String rendaMensal = "Até R\$1.500,00";
			String nomeCliente
			
			msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
			if(!alertListBlocList(msgAlert)){
				
				tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
				if(tela.contains("LISTA CLIENTES")){
					linha = StringFormatter.buscaComAntecessor(cotacao, 'SUAS INFORMAÇÕES', "Nome" )
					String tipo =  StringFormatter.betweenStrings(cotacao[linha], "Nome:" , "CPF/CNPJ:")
					System.out.println("TIPO "+tipo)
					selecionaItemListaContains(tipo, '//*[@id="ListaClientesBean$listaClientes"]', 'tr');
					WebUI.delay(5);
				}
				
				tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
				if(tela.contains("D. CLIENTE")){
					
					try{
					// DADOS PESSOAIS DO TOMADOR
					
					System.out.println()
					//CPF CNPJ TOMADOR
					xpath = '//*[@id="DadosGeraisClienteBeanTomador$_doc"]';
					linha = StringFormatter.indexLine(formulario, "CPF do cliente:");
					cpfcnpjTomador = StringFormatter.startWhitch(formulario[linha], "cliente:")
					System.out.println(cpfcnpjTomador)
					//limpaCampo(xpath, 0)
					preencheXpathFormulario(xpath, cpfcnpjTomador, 0);
		
					// DATA EXPEDIÇÃO
					//xpath = '//*[@id="DadosGeraisClienteBeanTomador$dataExpedicao"]';
					//linha = StringFormatter.buscaComAntecessor(formulario, "INFORMAÇÕES DO SEU SEGURO", "Vigência" )
					//hasdates = ExtractDates.getSubDates(formulario[linha].replaceAll("24H",""))
					//limpaCampo(xpath, 0)
					
					
					// ESTRANGEIRO
					xpath = '//*[@id="DadosGeraisClienteBeanTomador$tipoEstrangeiro"]';
					
					// NOME
					xpath = '//*[@id="DadosGeraisClienteBeanTomador$nombre"]';
					linha = StringFormatter.indexLine(formulario, "Nome do cliente:");
					nomeCliente = StringFormatter.startWhitch(formulario[linha], "cliente:")
					//limpaCampo(xpath, 0)
					preencheXpathFormulario(xpath, nomeCliente, 0);
					
					// DATA NASCIMENTO
					xpath = '//*[@id="DadosGeraisClienteBeanTomador$dataNascimento"]';
					linha = StringFormatter.indexLine(formulario, "Data de Nascimento:");
					hasdates = ExtractDates.getSubDates(formulario[linha].replaceAll("/",""))
					//limpaCampo(xpath, 0)
					preencheXpathFormulario(xpath, hasdates.get(0), 0);
					commandTAB(xpath)
					} catch (Exception e ){}
					
					// DADOS CONTATO
					try{
					// ENDEREÇO
					xpath = '//*[@id="DomicilioClienteBeanTomador$moradaaddressPart2"]';
					linha = StringFormatter.indexLine(formulario, "Endereco:");
					String enderecoCliente = StringFormatter.startWhitch(formulario[linha], "Endereco:")
					//limpaCampo(xpath, 0)
					preencheXpathFormulario(xpath, enderecoCliente, 0);
					
					// NUMERO
					xpath = '//*[@id="DomicilioClienteBeanTomador$numero"]';
					linha = StringFormatter.indexLine(formulario, "Numero:");
					String numeroCliente = StringFormatter.removeStrings(formulario[linha])
					//limpaCampo(xpath, 0)
					preencheXpathFormulario(xpath, numeroCliente, 0);
					
					// COMPLEMENTO
					xpath = '//*[@id="DomicilioClienteBeanTomador$restoMorada"]';
					linha = StringFormatter.indexLine(formulario, "Complemento:");
					String complementoCliente = StringFormatter.startWhitch(formulario[linha], "Complemento:")
					//limpaCampo(xpath, 0)
					preencheXpathFormulario(xpath, complementoCliente, 0);
					
					// CEP
					xpath = '//*[@id="DomicilioClienteBeanTomador$codigoPostal"]';
					linha = StringFormatter.indexLine(formulario, "CEP:");
					String cepCliente = StringFormatter.removeStrings(formulario[linha].replace("-", ""))
					String cep = cepCliente.substring(0, 5);
					//limpaCampo(xpath, 0)
					preencheXpathFormulario(xpath, cep, 0);
					
					xpath = '//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]';
					String subCep = cepCliente.substring(5, 8);
					System.out.println(subCep)
					//limpaCampo(xpath, 0)
					preencheXpathFormulario(xpath, subCep, 0);
						
					// CIDADE
					xpath = '//*[@id="IntervinientesBean$nifTomador_doc"]';
					linha = StringFormatter.indexLine(formulario, "Cidade:");
					String cidadeCliente = StringFormatter.startWhitch(formulario[linha], "Cidade:")
					//limpaCampo(xpath, 0)
					preencheXpathFormulario(xpath, cidadeCliente, 0);
					
					// ESTADO
					xpath = '//*[@id="DomicilioClienteBeanTomador$distrito"]';
					linha = StringFormatter.indexLine(formulario, "Estado:");
					String estadoCliente = StringFormatter.startWhitch(formulario[linha], "Estado:")
					//limpaCampo(xpath, 0)
					preencheXpathFormulario(xpath, estadoCliente, 0);
					
					System.out.println()
					
					linha = StringFormatter.buscaComAntecessor(formulario, 'Informação do segurado', "DDD:" )
					String d =  ajusteDDD(StringFormatter.startWhitch(formulario[linha], "DDD:"))
					if(d.length()>0){ ddd = d }
					System.out.println("DDD: "+ddd)
					
					// TELEFONE 
					xpath = '//*[@id="DomicilioClienteBeanTomador$telefone"]';
					linha = StringFormatter.indexLine(formulario, "Celular:");
					String celularCliente = StringFormatter.startWhitch(formulario[linha], "Telefone:")
					//limpaCampo(xpath, 0)
					preencheXpathFormulario(xpath, ddd+celularCliente, 0);
					
					// TELEFONE 2
					//xpath = '//*[@id="DomicilioClienteBeanTomador$telefone2"]';
					//linha = indexLine(formulario, "Telefone:");
					//String telefoneDoisCliente = StringFormatter.startWhitch(formulario[linha], "Telefone:")
					//limpaCampo(xpath, 0)
					//preencheXpathFormulario(xpath, ddd+telefoneDoisCliente, 0);
					
					// TELEFONE 3 - CELULAR
					xpath = '//*[@id="DomicilioClienteBeanTomador$celular"]';
					linha = StringFormatter.indexLine(formulario, "Celular:");
					String telefoneDoisClienteTres = StringFormatter.startWhitch(formulario[linha], "Celular:")
					//limpaCampo(xpath, 0)
					preencheXpathFormulario(xpath, ddd+telefoneDoisClienteTres, 0);
					// E-MAIL
					xpath = '//*[@id="DomicilioClienteBeanTomador$email1"]';
					linha = StringFormatter.indexLine(formulario, "Bairro:");
					String emailCliente = StringFormatter.startWhitch(formulario[linha-1], "Email:")
					//limpaCampo(xpath, 0)
					preencheXpathFormulario(xpath, emailCliente, 0);
					} catch (Exception e){}
					
					// DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS
					try{
						// Sexo 
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$sexo"]';
						selecionaItemListaContains(sexo, xpath, 'option');
						// Profissão
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$profissao"]';
						selecionaItemListaContains(profissao, xpath, 'option');
						// Estado Civil
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$estadoCivil"]';
						selecionaItemListaContains(estadoCivil, xpath, 'option');
						// Nome da Mãe
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$nomeMae"]';
						preencheXpathFormulario(xpath,nomeMae, 0);
						// PIS
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$pis"]';
						preencheXpathFormulario(xpath,pis, 0);
						// INSS
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$inss"]';
						preencheXpathFormulario(xpath,inss, 0);
						// Renda Mensal
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]';
						selecionaItemListaContains(rendaMensal, xpath, 'option');
					}catch (Exception e){}
					
					
					// DADOS SOCIOECONÓMICOS E SOCIODEMOGRÁFICOS CAMINHÃO 
					try{
						String atividadePrincipal = "AAA";
						String INSS = "0";
						String natureza = "ADMINISTRAÇÃO PÚBLICA, DEFESA E SEGURIDADE SOCIAL";
						String ocupacao ="A1 - AGRICULTURA, PECUÁRIA E SERVIÇOS RELACIONADOS";
						String faturamento = "Até R\$120.000,00";
						String qtdfuncionarios = "Até 9";
						String patrimonio = "Até R\$300.000,00";
						
						// Nome Fantasia 
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia"]';
						preencheXpathFormulario(xpath,nomeCliente, 0);
						// Atividade Principal 
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$actividadePrincipal"]';
						preencheXpathFormulario(xpath,atividadePrincipal, 0);
						// INSS
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$inss"]]';
						preencheXpathFormulario(xpath,INSS, 0);
						// Natureza
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$naturezaRendimento"]';
						selecionaItemListaContains(natureza, xpath, 'option');
						// Ocupação
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$ocupacao"]';
						selecionaItemListaContains(ocupacao, xpath, 'option');
						// Faturamento
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$faturamento"]';
						selecionaItemListaContains(faturamento, xpath, 'option');
						// QTD Funcionarios
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$numeroFuncionarios"]';
						selecionaItemListaContains(qtdfuncionarios, xpath, 'option');
						// Patrimonio
						xpath = '//*[@id="OutrosDadosClienteBeanTomador$patrimonio"]';
						selecionaItemListaContains(patrimonio, xpath, 'option');
					}catch (Exception e){}
					
					
					System.out.println()
					// AVANÇAR
					clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
					fechaAlerta();
					WebUI.delay(5);
					System.out.println()
					try{
						// DATA NASCIMENTO
						xpath = '//*[@id="DadosGeraisClienteBeanConductor$dataNascimento"]';
						linha = StringFormatter.indexLine(formulario, "Data de Nascimento:");
						hasdates = ExtractDates.getSubDates(formulario[linha].replaceAll("/",""))
						//limpaCampo(xpath, 0)
						preencheXpathFormulario(xpath, hasdates.get(0), 0);
						//commandTAB(xpath)
						// Sexo
						xpath = '//*[@id="OutrosDadosClienteBeanConductor$sexo"]';
						selecionaItemListaContains(sexo, xpath, 'option');
						// Estado Civil
						xpath = '//*[@id="OutrosDadosClienteBeanConductor$estadoCivil"]';
						selecionaItemListaContains(estadoCivil, xpath, 'option');
						// Profissão
						xpath = '//*[@id="OutrosDadosClienteBeanConductor$profissao"]';
						selecionaItemListaContains(profissao, xpath, 'option');
						// Renda Mensal
						xpath = '//*[@id="OutrosDadosClienteBeanConductor$rendaMensal"]';
						selecionaItemListaContains(rendaMensal, xpath, 'option');
						// AVANÇAR
						clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
						fechaAlerta();
						WebUI.delay(5);
					} catch (Exception e){}
					
					try{
						String alert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr[1]/td[2]', 'innerText', 0)
						if(alertListContinueList(alert)){
							if(alert.contains(" Celular não válido")){
								// TELEFONE 3 - CELULAR
								xpath = '//*[@id="DomicilioClienteBeanTomador$celular"]';
								preencheXpathFormulario(xpath, "11 999999999", 0);
							}
							
							if(alert.contains(" Endereço é obrigatório")){
								// ENDEREÇO
								xpath = '//*[@id="DomicilioClienteBeanTomador$moradatype"]';
								selecionaItemListaContains("Rua", xpath, 'option');
								// ENDEREÇO
								xpath = '//*[@id="DomicilioClienteBeanTomador$moradaaddressPart2"]';
								linha = StringFormatter.indexLine(formulario, "Logradouro:");
								String logradouroCliente = StringFormatter.startWhitch(formulario[linha], "Logradouro:")
								preencheXpathFormulario(xpath, logradouroCliente, 0);
							}
							System.out.println()
							//Avançar
							clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
							WebUI.delay(3);
							fechaAlerta();
							WebUI.delay(3);
							//Avançar
							//clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
						}
					} catch (Exception e){}
					//INFORMAÇÕES DO CONDUTOR PRINCIPAL
					WebUI.delay(5);
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
					 if(tela.contains("LISTA CLIENTES")){
						 linha = StringFormatter.buscaComAntecessor(cotacao, 'INFORMAÇÕES DO CONDUTOR PRINCIPAL', "Nome" )
						 String tipo =  StringFormatter.betweenStrings(cotacao[linha], "Nome:" , "CPF:") 
						 System.out.println("TIPO "+tipo)
						 selecionaItemListaContains(tipo, '//*[@id="ListaClientesBean$listaClientes"]', 'tr');
						 WebUI.delay(5);
						 // AVANÇAR
						 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
					 }
					
				} else {
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 0)
				alerta = "Falha: Tela - D. CLIENTE - Não apresentada";
				enviado = false;
			  }
		  } else {
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0, 0)
			alerta = msgAlert;
			enviado = false;
		  }
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static dadosComuns(){
		if(enviado){
			WebUI.delay(5);
			msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
			if(!alertListBlocList(msgAlert)){
				
				tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
				if(tela.contains("D. COMUNS")){
					// AVANÇAR
					clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
				} else {
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 0)
				alerta = "Falha: Tela - D. COMUNS - Não apresentada";
				enviado = false;
			  }
		  } else {
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0, 0)
			alerta = msgAlert;
			enviado = false;
		  }
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static confirmarPagamento(){
		if(enviado){
			WebUI.delay(5);
			msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
			if(!alertListBlocList(msgAlert)){
				tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
				if(tela.contains("CONFIRMAR PAGAMENTO")){
					// AVANÇAR
					clicarEmElementoDaTela('//*[@id="o_3"]', 0);
				} else {
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 0)
				alerta = "Falha: Tela - CONFIRMAR PAGAMENTO - Não apresentada";
				enviado = false;
			  }
		  } else {
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0, 0)
			alerta = msgAlert;
			enviado = false;
		  }
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static  clausulas(List<String> cotacao, List<String> formulario){
		if(enviado){
			WebUI.delay(10);
			msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
			if(!alertListBlocList(msgAlert)){
				tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
				 if(tela.contains("CLÁUSULAS")){
					 System.out.println()
					 // AVANÇAR
					 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
					 WebUI.delay(5);
					 try{
						 String alert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
						 if(alertListContinueList(alert)){
							 //Avançar
							  clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
						 }
					 } catch (Exception e){}
					 
					 try{
					    msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
					    if(msgAlert.equals(" Preencha os dados de TDO na tela de dados de riscos.")){
							ajusteTDO(cotacao, formulario);
						}
					 } catch (Exception e){}
					 
					 WebUI.delay(5);
					 try{
						 String alert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
						 if(alertListContinueList(alert)){
							 //Avançar
							  clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
						 }
					 } catch (Exception e){}
					 
				 } else {
				 fecharJanelaAtual(1)
				 alteraJanejaGenerica(0, 0)
				 alerta = "Falha: Tela - CLÁUSURAS - Não apresentada";
				 enviado = false;
			   }
			} else {
			  fecharJanelaAtual(1)
			  alteraJanejaGenerica(0, 0)
			  alerta = msgAlert;
			  enviado = false;
			}
		}
	}
	
	public static ajusteTDO(List<String> cotacao, List<String> formulario){
		dadosDeRiscoTDO(cotacao, formulario);
		tabelaDePrecos(cotacao, formulario);
		dadosEmissaoDTO();
		dadosClientesDTO(cotacao);
		dadosComuns();
		if(produto.equals("Moto 1212")){ clausulas(cotacao, formulario); confirmarPagamento();}
		else {confirmarPagamento(); clausulas(cotacao, formulario);}
	}
	
	public static dadosDeRiscoTDO(List<String> cotacao, List<String> formulario){
		WebUI.delay(10);
		//DADOS DE RISCO 
		xpath = '//*[@id="divNavigationArea"]';
		selecionaItemListaContains("Dados de Risco", xpath, 'td');
		if(enviado){
			WebUI.delay(10);
			msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			System.out.println("alertaFG ["+msgAlert+"] ["+msgAlert.length()+"]")
			if(!alertListBlocList(msgAlert)){
				System.out.println()
				 tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
				 if(tela.contains("DADOS DE RISCO")){
					 
					 //INFORMAÇÕES DE TDO
					 
					 //Tipo de Relacionamento
					 linha = StringFormatter.indexLine(formulario, "CPF/CNPJ do segurado:")
					 String doc = StringFormatter.startWhitch(formulario[linha], "segurado:")
					 String tipoDeRelacionamento
					 if(StringFormatter.isValidCNPJ(doc)){
                       tipoDeRelacionamento = "Pessoa Jurídica para Pessoa Jurídica"
					 }
					 
					 if(StringFormatter.isValidCPF(doc)){
					   tipoDeRelacionamento = "Pessoa Física para Pessoa Física"
					 }
					 
					 selecionaItemLista( tipoDeRelacionamento  , '//*[@id="InfoTDOBean$grauParentesco"]', 'option');
					 //DATA NASCIMENTO
					 linha = StringFormatter.indexLine(formulario, "Data de Nascimento:")
					 xpath = '//*[@id="InfoTDOBean$dataNascimento"]';
					 try{
					   hasdates = ExtractDates.getSubDates(formulario[linha])
					   //limpaCampo(xpath, 0)
					   preencheXpathFormulario(xpath, hasdates.get(0), 0);
					 } catch (Exception e){}
					 //ACEITAR
					 clicarEmElementoDaTela('//*[@id="o_3"]', 0);
					 WebUI.delay(5);
					 try{
						 String alert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
						 if(alertListContinueList(alert)){
							 //Avançar
							 clicarEmElementoDaTela('//*[@id="o_3"]', 0);
						 }
					 } catch (Exception e){}
					 
				 } else {
				 fecharJanelaAtual(1)
				 alteraJanejaGenerica(0, 0)
				 alerta = "Falha: Tela - DADOS DE RISCO   - Não apresentada";
				 enviado = false;
			   }
		  } else {
			 fecharJanelaAtual(1)
			 alteraJanejaGenerica(0, 0)
			 alerta = msgAlert;
			 enviado = false;
		 }
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param cotacao
	 * @param formulario
	 * @return
	 */
	public static dadosClientesDTO(List<String> cotacao){
		if(enviado){
			System.out.println()
			WebUI.delay(10);
			
			msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
			if(!alertListBlocList(msgAlert)){
				
				tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
				if(tela.contains("LISTA CLIENTES")){
					linha = StringFormatter.buscaComAntecessor(cotacao, 'SUAS INFORMAÇÕES', "Nome" )
					String tipo =  StringFormatter.betweenStrings(cotacao[linha], "Nome:" , "CPF/CNPJ:")
					System.out.println("TIPO "+tipo)
					selecionaItemListaContains(tipo, '//*[@id="ListaClientesBean$listaClientes"]', 'tr');
					WebUI.delay(5);
				}
				
				tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
				if(tela.contains("D. CLIENTE")){
					
					System.out.println()
					// AVANÇAR
					clicarEmElementoDaTela('//*[@id="btnAceptar"]', 5);
					fechaAlerta();
					WebUI.delay(5);
					
				} else {
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 0)
				alerta = "Falha: Tela - D. CLIENTE - Não apresentada";
				enviado = false;
			  }
		  } else {
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0, 0)
			alerta = msgAlert;
			enviado = false;
		  }
		}
	}
	
	
	/**
	 * @author Alz_sup54955
	 * @param formulario
	 * @return
	 */
	public static dadosEmissaoDTO(){
		if(enviado){
			WebUI.delay(10);
			msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
			if(!alertListBlocList(msgAlert)){
				tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
				if(tela.contains("D. EMISSÃO")){
					/**
					 * @author BE54955
					 * DADOS BANCÁRIOS
					 * Conta Corrente												 */
					//
					System.out.println()
					//AVANÇAR
					clicarEmElementoDaTela('//*[@id="btnAceptar"]', 5);
				} else  {
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 0)
				alerta = "Falha: Tela - D. Emissão - Não apresentada";
				enviado = false;
			   }
		   } else {
			 fecharJanelaAtual(1)
			 alteraJanejaGenerica(0, 0)
			 alerta = msgAlert;
			 enviado = false;
		   }
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static confirmacaoDeEmissao(){
		if(enviado){
			WebUI.delay(5);
			msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
			if(!alertListBlocList(msgAlert)){
				tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
				 if(tela.contains("CONFIRMAÇÃO DE EMISSÃO")){
					 // AVANÇAR
					 	 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
				 } else {
				 fecharJanelaAtual(1)
				 alteraJanejaGenerica(0, 0)
				 alerta = "Falha: Tela - CONFIRMAÇÃO DE EMISSÃO - Não apresentada";
				 enviado = false;
			   }
		  } else {
		   fecharJanelaAtual(1)
		   alteraJanejaGenerica(0, 0)
		   alerta = msgAlert;
		   enviado = false;
		  }

		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static emissaoRealizada(){
		if(enviado){
			WebUI.delay(10);
			msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
			if(!alertListBlocList(msgAlert)){
				tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
				 if(tela.contains("EMISSÃO REALIZADA")){
					 String sApoliceRenov = recuperaValor('//*[@id="CabeceraBean$sucursal"]', 'innerText', 5)
					 cotTicket = sApoliceRenov.replaceAll(" / 0 / 0", "");
					 alerta = "Emissão realizada: "+sApoliceRenov;
					 //Avançar
					 clicarEmElementoDaTela('//*[@id="btnValida"]', 0);
				 } else {
				   fecharJanelaAtual(1)
				   alteraJanejaGenerica(0, 0)
				   alerta = "Falha: Tela - EMISSÃO REALIZADA - Não apresentada";
				   enviado = false;
				 }
			} else {
			  fecharJanelaAtual(1)
			  alteraJanejaGenerica(0, 0)
			  alerta = msgAlert;
			  enviado = false;
			}
	
		}
	}
	
	
	
	public static String formataVidros(String v){
		Map<String, String> combo = new HashMap<String, String>()
		       combo.put('NÃO CONTRATADA', 'Não contratado');
		       combo.put('VIDROS BLINDADOS', 'Vidros blindados');
			   combo.put('PLANO 1', 'Plano 1');
			   combo.put('PLANO 2', 'Plano 2');
			   combo.put('PLANO 3', 'Plano 3');
			   combo.put('PLANO 4', 'Plano 4');
		return combo.get(v.toString());
	}
	
	public static String carroReserva(String linha){
	 String r;
	 String[] reserva = new String[4];
	          reserva[0] = "Não Contratado";
	          reserva [1]="07 dias";
	          reserva [2]="15 dias";
	          reserva [3]="30 dias";
	    for(int i = 0; i<reserva.length;i++){
		   if(linha.contains(reserva[i])){
			   r = reserva[i];
		 	   break;
		   }
	    }
	
	    String t = formataCarroReserva(r)
	    if(!t.equals(null)){
		   r = t;
	    }
		
	 return r;
	}
	
	public static String vidros(String linha){
		String r;
		String[] vidro = new String[4];
				 vidro[0] = "Não Contratado";
				 vidro [1]="Plano 1";
				 vidro [2]="Plano 2";
				 vidro [3]="Plano 3";
		   for(int i = 0; i<vidro.length;i++){
			  if(linha.contains(vidro[i])){
				  r = vidro[i];
				   break;
			  }
		   }
	   
		   String t = formataCarroReserva(r)
		   if(!t.equals(null)){
			  r = t;
		   }
		   
		return r;
	   }
	
	
	public static String formataCarroReserva(String v){
		Map<String, String> combo = new HashMap<String, String>()
			   combo.put('Não Contratado', 'Não contratado');
		return combo.get(v.toString());
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
	public static reprocessaFalhas(String aba, String a){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoReentradaPropostaAuto(aba,a)
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
     * @param t
     * @return
     */
	public static String titular(String t){
		Map<String, String> obs = new HashMap<String, String>()
		obs.put('Sim','//*[@id="blDatosBancariosContent"]/table[3]/tbody/tr/td[1]/table/tbody/tr/td[2]/table/tbody/tr/td[1]');
		obs.put('Não','//*[@id="blDatosBancariosContent"]/table[3]/tbody/tr/td[1]/table/tbody/tr/td[3]/table/tbody/tr/td[1]');
		return obs.get(t.toString());
	}
	
	/**
	 * @author Alz_sup54955
	 * @param line26
	 * @param line27
	 * @return
	 */
	public static String ampliaCobertura(String line26, String line27){
		String t;
		StringBuilder sb = new StringBuilder();
		
		if(line26.contains("Sim")){
			t = line26;
		} else {
		  sb.append(line26)
		  sb.append(" ")
		  sb.append(line27);
		  t = sb.toString();
		}
		return t;
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
	public static enviaEmailReentradaPropostaAutoDinamico(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
		System.out.println()
	 try{
		 if(!validaDados(cellCheck)){
			String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Auto\\ReentradaPropostaAuto\\ConfiguracoesReentradaPropostaAuto.txt";
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
	
	
	
	private static preencheXpathFormulario(String xpath, String txt, int i){
		if(!txt.equals("--")){
			limpaCampo(xpath, 0)
		    preencheXpath(xpath, txt, i);
		}
	}
	
	
	
	
	
	
	
	
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static String selecionaPacoteAuto(String pacote){
		System.err.println("PACOTE PARA SELECIONAR ["+pacote+"] PACOTE ATUAL ["+pacotesNovos(pacote)+"]")
		switch(pacotesNovos(pacote)){
			case 'Essencial':
			return '//*[@id="blParrillaContent"]/table[6]/tbody/tr//*[@id="ParrillaBean$modalidades[0]rad"]'
			break
			case 'Básico':
			return '//*[@id="blParrillaContent"]/table[7]/tbody/tr//*[@id="ParrillaBean$modalidades[1]rad"]'
			break
			case 'Ampliado':
			return '//*[@id="blParrillaContent"]/table[8]/tbody/tr//*[@id="ParrillaBean$modalidades[2]rad"]'
			break
			case 'Completo':
			return '//*[@id="blParrillaContent"]/table[9]/tbody/tr//*[@id="ParrillaBean$modalidades[3]rad"]'
			break
			case 'Master':
			return '//*[@id="blParrillaContent"]/table[10]/tbody/tr//*[@id="ParrillaBean$modalidades[4]rad"]'
			break
			case 'Exclusivo':
			return '//*[@id="blParrillaContent"]/table[11]/tbody/tr//*[@id="ParrillaBean$modalidades[5]rad"]'
			break
		}
	}
	
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static String selecionaPacoteCaminhao(String pacote){
		System.err.println("PACOTE PARA SELECIONAR ["+pacote+"] PACOTE ATUAL ["+pacotesNovos(pacote)+"]")
		switch(pacotesNovos(pacote)){
			case 'Básico':
			return '//*[@id="blParrillaContent"]/table[6]/tbody/tr/td//*[@name="ParrillaBean$modalidadPago"]'
			break
			case 'Ampliado':
			return '//*[@id="blParrillaContent"]/table[7]/tbody/tr/td//*[@name="ParrillaBean$modalidadPago"]'
			break
			case 'Completo':
			return '//*[@id="blParrillaContent"]/table[8]/tbody/tr/td//*[@name="ParrillaBean$modalidadPago"]'
			break
			case 'Master':
			return '//*[@id="blParrillaContent"]/table[9]/tbody/tr/td//*[@name="ParrillaBean$modalidadPago"]'
			break
			case 'Exclusivo':
			return '//*[@id="blParrillaContent"]/table[10]/tbody/tr/td//*[@name="ParrillaBean$modalidadPago"]'
			break
		}
	}
	
	

	
	/**
	 * @author be54955
	 * @return
	 */
	public static String recuperaPremioTabelaPrecosAuto(String pacoteSelecionado, int p){
		String premio
		System.err.println("Pacote Selecionado ["+pacoteSelecionado+"] Parcelas ["+p+"]")
		
		if( p > 0){
			premio = recuperaValor(premioTabelaPrecoParceladoAuto(pacoteSelecionado), 'value', 3)
		}
		
		if(p == 0){
			premio = recuperaValor(premioTabelaPrecoAvistaAuto(pacoteSelecionado), 'value', 3)
		}
		
		System.err.println("Valor do Pacote Selecionado ["+premio+"]")
		return premio
	}
	
	/**
	 * @author be54955
	 * @return
	 */
	public static String recuperaPremioTabelaPrecosCaminhao(String pacoteSelecionado, int p){
		String premio
		System.err.println("Pacote Selecionado ["+pacoteSelecionado+"] Parcelas ["+p+"]")
		
		if( p > 0){
			premio = recuperaValor(premioTabelaPrecoParceladoCaminhao(pacoteSelecionado), 'value', 3)
		}
		
		if(p == 0){
			premio = recuperaValor(premioTabelaPrecoAvistaCaminhao(pacoteSelecionado), 'value', 3)
		}
		
		System.err.println("Valor do Pacote Selecionado ["+premio+"]")
		return premio
	}
	
	/**
	 * @author ARAntunes
	 * @param pacote
	 * @return
	 */
	public static String pacotesNovos(String pacote){
		Map<String, String> pacotes = new HashMap<String, String>()
		pacotes.put('Essencial', 'Essencial');
		pacotes.put('Compacto 1', 'Básico');
		pacotes.put('Básico', 'Básico');
		pacotes.put('Ampliado', 'Ampliado');
		pacotes.put('Compacto 2', 'Ampliado');
		pacotes.put('Ampliado 1', 'Completo');
		pacotes.put('Completo', 'Completo');
		pacotes.put('Ampliado 2', 'Master');
		pacotes.put('Especial', 'Master');
		pacotes.put('Master', 'Master');
		pacotes.put('Exclusivo', 'Exclusivo');

		return pacotes.get(pacote.toString());
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static String verificaPacoteSelecionado(){
		String pacote
		String xpath = '//*[@id="blParrillaCellContent"]'
		String tag = 'input'
		WebUI.delay(5)
		selecionaListaDeElementos(xpath,tag)
		for(int j = 0; j < listOfElements.size(); j++){
			//	System.err.println("["+j+"] ["+listOfElements.get(j).getAttribute("id")+"] ["+listOfElements.get(j).getAttribute("checked")+"]")
			if(listOfElements.get(j).getAttribute("checked").equals('true') ){
				System.err.println("["+j+"] ["+listOfElements.get(j).getAttribute("id")+"] ["+listOfElements.get(j).getAttribute("checked")+"]")
				pacote = pacotesIndex(j)
			}
		}
		return pacote
	}
	
	/**
	 * @author T_DCDias
	 */
	public static String pacotesIndex(int pacote){
		Map<Integer, String> pacotes = new HashMap<Integer, String>()
		pacotes.put(2, "Essencial")
		pacotes.put(6, "Básico")
		pacotes.put(10,"Ampliado")
		pacotes.put(14,"Completo")
		pacotes.put(18,"Master")
		pacotes.put(22,"Exclusivo")
		return pacotes.get(pacote);
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param alert
	 * @return
	 */
	public static String premioTabelaPrecoAvistaAuto(String premio){
		Map<String, String> precoList = new HashMap<String, String>()
		precoList.put("Essencial", '//*[@id="modalidad_0_0_primaRecibo"]')
		precoList.put("Básico", '//*[@id="modalidad_1_0_primaRecibo"]')
		precoList.put("Ampliado", '//*[@id="modalidad_2_0_primaRecibo"]')
		precoList.put("Completo", '//*[@id="modalidad_3_0_primaRecibo"]')
		precoList.put("Master", '//*[@id="modalidad_4_0_primaRecibo"]')
		precoList.put("Exclusivo",'//*[@id="modalidad_0_0_primaRecibo"]')
		return  precoList.get(premio).toString();
	}
	
	/**
	 * @author ALZ_ServiceQA
	 * @param alert
	 * @return
	 */
	public static String premioTabelaPrecoAvistaCaminhao(String premio){
		Map<String, String> precoList = new HashMap<String, String>()
		precoList.put("Básico", '//*[@id="modalidad_0_0_primaRecibo"]')
		precoList.put("Ampliado", '//*[@id="modalidad_1_0_primaRecibo"]')
		precoList.put("Completo", '//*[@id="modalidad_2_0_primaRecibo"]')
		precoList.put("Master", '//*[@id="modalidad_3_0_primaRecibo"]')
		precoList.put("Exclusivo",'//*[@id="modalidad_4_0_primaRecibo"]')
		return  precoList.get(premio).toString();
	}
	
	/**
	 * @author ALZ_ServiceQA
	 * @param alert
	 * @return
	 */
	public static String premioTabelaPrecoParceladoAuto(String premio){
		Map<String, String> precoList = new HashMap<String, String>()
		precoList.put("Essencial", '//*[@id="modalidad_0_1_primaRecibo"]')
		precoList.put("Básico", '//*[@id="modalidad_1_1_primaRecibo"]')
		precoList.put("Ampliado", '//*[@id="modalidad_2_1_primaRecibo"]')
		precoList.put("Completo", '//*[@id="modalidad_3_1_primaRecibo"]')
		precoList.put("Master", '//*[@id="modalidad_4_1_primaRecibo"]')
		precoList.put("Exclusivo",'//*[@id="modalidad_5_1_primaRecibo"]')
		return  precoList.get(premio).toString();
	}
	
	/**
	 * @author ALZ_ServiceQA
	 * @param alert
	 * @return
	 */
	public static String premioTabelaPrecoParceladoCaminhao(String premio){
		Map<String, String> precoList = new HashMap<String, String>()
		precoList.put("Básico", '//*[@id="modalidad_0_1_primaRecibo"]')
		precoList.put("Ampliado", '//*[@id="modalidad_1_1_primaRecibo"]')
		precoList.put("Completo", '//*[@id="modalidad_2_1_primaRecibo"]')
		precoList.put("Master", '//*[@id="modalidad_3_1_primaRecibo"]')
		precoList.put("Exclusivo",'//*[@id="modalidad_4_1_primaRecibo"]')
		return  precoList.get(premio).toString();
	}
	
	
	
	/**
	 * @author be54955
	 * @param premio
	 * @param premioTotal
	 * @return
	 */
	public static String calculoICGlobal(String premioRecuperado, String premioTotal, int p){
		
		premioRecuperado = premioRecuperado.replace("R\$","")
		premioRecuperado = premioRecuperado.replace(".","")
		premioRecuperado = premioRecuperado.replace(",",".").trim()
		//premioTotal = premioTotal.replace("R\$","")
		premioTotal = premioTotal.replace(".","")
		premioTotal = premioTotal.replace(",",".").trim()
		BigDecimal bigPremioRecuperado = new BigDecimal(premioRecuperado.toString());
		BigDecimal bigPremioTotal = new BigDecimal(premioTotal.toString());
		
		BigDecimal bigPremioRecuperadoCalculado = bigPremioRecuperado*p;
		
		BigDecimal ic = new BigDecimal(bigPremioTotal*100/bigPremioRecuperadoCalculado)
		BigDecimal icArredondado = ic.setScale(2, BigDecimal.ROUND_UP);
		String strngic = icArredondado.toPlainString()
		strngic = strngic.replace(".", ",")
		System.err.println("Premio Recuperado ["+bigPremioRecuperado+"] Premio total ["+bigPremioTotal+"] IC Global ["+strngic+"]")

		return strngic
	}
	
	
	public static boolean validaListaDeCargas(String tipoMaterial){
		 boolean f = true;
		 String xpath = '//*[@id="CargasBean$codigoCarga_tbody"]'
		 String tag = "tr"
		 selecionaListaDeElementos(xpath,tag)
		 for(int i = 0; i < listOfElements.size(); i++){
			 //		System.out.println("["+i+"] index ["+n+"]")
			 if(listOfElements[i].getText().contains(tipoMaterial)){
				 System.out.println("\nFound index ["+i+"] ["+listOfElements[i].getText()+"]")
				 f = false
			 }
		 }
		return f;
	}
	
	
	public static boolean validarPremios(String premioTotal, String premioRecuperado, int p){
		System.out.println()
		boolean b;
		double pt
		double pr
		try{
		    pt = Double.parseDouble(premioTotal.replace(".","").replace(",","."));
		} catch (Exception e){}
		try{
			pt = Double.parseDouble(premioTotal.replace(",","."));
		}catch (Exception e){}
		try{
			pt = Double.parseDouble(premioTotal.replace(".",""));
		}catch (Exception e){}
		try{
		   pr = Double.parseDouble(premioRecuperado.replace(".","").replace(",","."));
		} catch (Exception e){}
		try{
			pr = Double.parseDouble(premioRecuperado.replace(",","."));
		}catch (Exception e){}
		try{
			pr = Double.parseDouble(premioRecuperado.replace(".",""));
		}catch (Exception e){}
		
		System.out.println("["+ pr+"] ["+p +"]" )
		
		double prTotal = pr*p;
		
		System.out.println("["+ pt+"] ["+ prTotal+"]" )
		
		if(pt == prTotal){
			b = false;
		}
		
		System.out.println(b)
		return b;
	}
	
	
	public static String recuperaValorPremio(String produto, String sPacote, String p){
		String valorPacote
		try{
			switch (produto){
				case "Automoveis 1211":
					// Recupera Valor do pacote selecionado
					System.out.println("valorPacote "+sPacote)
					valorPacote = recuperaPremioTabelaPrecosAuto(sPacote, Integer.parseInt(p))
					System.out.println("valorPacote "+valorPacote)
					break;
				case "Caminhão 1213":
					System.out.println("valorPacote "+sPacote)
					//valorPacote = recuperaPremioTabelaPrecosCaminhao(sPacote, Integer.parseInt(p))
					valorPacote = recuperaPremioTabelaPrecosAuto(sPacote, Integer.parseInt(p))
					System.out.println("valorPacote "+valorPacote)
					break;
			   case "Moto 1212":
					System.out.println("valorPacote "+sPacote)
					//valorPacote = recuperaPremioTabelaPrecosCaminhao(sPacote, Integer.parseInt(p))
					valorPacote = recuperaPremioTabelaPrecosAuto(sPacote, Integer.parseInt(p))
					System.out.println("valorPacote "+valorPacote)
					break;
			}
			} catch (Exception e){}
		return valorPacote;
	}
	
	public static double calculaDiferenca(String nPremioTotal, String recuperaValorPremio, int p ){
		double dif;
	    try{
			String rvp = recuperaValorPremio.replace("R\$","")
			double pt
			try{
				pt = Double.parseDouble(rvp.replace(".","").replace(",","."));
			} catch (Exception e){}
			try{
				pt = Double.parseDouble(rvp.replace(",","."));
			}catch (Exception e){}
			try{
				pt = Double.parseDouble(rvp.replace(".",""));
			}catch (Exception e){}
			
			double pm
			try{
				pm = Double.parseDouble(nPremioTotal.replace(".","").replace(",","."));
			} catch (Exception e){}
			try{
				pm = Double.parseDouble(nPremioTotal.replace(",","."));
			}catch (Exception e){}
			try{
				pm = Double.parseDouble(nPremioTotal.replace(".",""));
			}catch (Exception e){}
			
	        double vpTotal = pt*p;
	        dif = Math.abs(vpTotal - pm)
	   } catch (Exception e){}
	return dif;
	}
	
	
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static int localizaPacote(){
		int pacote
		String xpath = '//*[@id="blParrillaContent"]'
		String tag = 'label'
		WebUI.delay(5)
		selecionaListaDeElementos(xpath,tag)
		for(int j = 0; j < listOfElements.size(); j++){
			//System.err.println("["+j+"] ["+listOfElements.get(j).getText()+"]")
			if(pacote(listOfElements.get(j).getText())){
			   System.err.println("["+j+"] ["+listOfElements.get(j).getText()+"]")
				pacote = pacote+1;
			}
		}
		System.out.println("TOTAL DE PACOTES ["+pacote+"]")
		return pacote
	}
	
	public static boolean pacote(String pacote){
		boolean r;
		List<String> listOfAlerts = new ArrayList<String>()
	
		listOfAlerts.add("Essencial")
		listOfAlerts.add("Básico")
		listOfAlerts.add("Ampliado")
		listOfAlerts.add("Completo")
		listOfAlerts.add("Master")
		
		for(int i = 0; i < listOfAlerts.size(); i++){
			System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
			if(listOfAlerts[i].contains(pacote.toString())){
				System.err.println("FOUND PACK ["+i+"] ["+listOfAlerts[i]+"]")
				r = true
				break
			}
		}
	   System.out.println("\nPACK ["+r+"]")
	   return r;
	}
	
	public static String tipoFranquiaMoto(String f){
		Map<String, String> precoList = new HashMap<String, String>()
		precoList.put("Normal", '1')
		precoList.put("50% da Normal", '4')
		precoList.put("25% da Normal", '6')
		precoList.put("150% da Normal", '7')
		precoList.put("200% da Normal",'8')
		System.out.println("["+precoList.get(f).toString()+"]")
		return  precoList.get(f).toString();
	}
	
	public static String grupoRenovacao(String f){
		Map<String, String> precoList = new HashMap<String, String>()
		precoList.put("1", 'Grupo 01')
		precoList.put("2", 'Grupo 02')
		precoList.put("3", 'Grupo 03')
		precoList.put("4", 'Grupo 04')
		precoList.put("5", 'Grupo 05')
		precoList.put("6", 'Grupo 06')
		precoList.put("7", 'Grupo 07')
		System.out.println("["+precoList.get(f).toString()+"]")
		return  precoList.get(f).toString();
	}

	public static String textoAmpliarCobertura(String f){
		Map<String, String> precoList = new HashMap<String, String>()
		precoList.put("Sim", 'Sim')
		precoList.put("Não. Estou", 'Não. Estou ciente que não haverá cobertura para condutores entre 18 a 25 anos.')
		System.out.println("["+precoList.get(f).toString()+"]")
		return  precoList.get(f).toString();
	}
	
	public static String primcipalCondutorReside(String f){
		Map<String, String> precoList = new HashMap<String, String>()
		precoList.put("Sim", 'Sim')
		precoList.put("Não. Estou", 'Não. Estou ciente que não haverá cobertura para condutores entre 18 a 25 anos.')
		System.out.println("["+precoList.get(f).toString()+"]")
	}
	

	public static veiculoEmGaragem(String alert){
		String t;
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add("Sim, mantém o veículo segurado em garagem")
		listOfAlerts.add("Não mantém o veículo segurado em garagem")
		listOfAlerts.add("Não utiliza o veículo segurado para ir à faculdade/universidade/escola/pós/curso")
		listOfAlerts.add("Não estuda/Não frequenta cursos")
		System.out.println("MSG -["+alert+"]-")
		for(int i = 0; i < listOfAlerts.size(); i++){
			System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
			if(listOfAlerts[i].contains(alert.toString())){
				System.err.println("FOUND MSG ["+i+"] ["+listOfAlerts[i]+"]")
				t = listOfAlerts[i].toString()
				break
			}
		}
		return t;
	}
	
	public static String retrovisoresMoto(String f){
		Map<String, String> precoList = new HashMap<String, String>()
		precoList.put("NÃO CONTRATADA", 'Não contratado')
		precoList.put("Plano 1", 'Plano 1')
		precoList.put("Plano 2", 'Plano 2')
		precoList.put("Plano 3", 'Plano 3')
		System.out.println("["+precoList.get(f).toString()+"]")
		return  precoList.get(f).toString();
	}
	
	public static String ajusteDDD(String s){
		String d
		if(s.equals("--")){
			d = "11"
		} else {
		  d=s
		}
		return d
	}
		
	public static boolean validaArquivos(String arquivoA, String arquivoB){
	boolean arq1 = leitorPdf.existeArquivo(arquivoA)
	boolean arq2 = leitorPdf.existeArquivo(arquivoB)
	boolean b=false;
	System.out.println("["+arq1+"] ["+arq2+"]");
	if(arq1.equals(false) && arq2.equals(false)){  b=true}
	System.out.println("["+b+"]");
	return b
	}
	
	public static boolean validaDownloadArquivos(String arquivoA, String arquivoB){
		boolean arq1 = leitorPdf.existeArquivo(arquivoA)
		boolean arq2 = leitorPdf.existeArquivo(arquivoB)
		boolean b=false;
		System.out.println("["+arq1+"] ["+arq2+"]");
		if(arq1.equals(true) && arq2.equals(true)){  b=true}
		System.out.println("["+b+"]");
		return b
	}
	
	public static String nomeArquivo(){
		boolean arq1 = leitorPdf.existeArquivo("PDF da cotação")
		boolean arq2 = leitorPdf.existeArquivo("ticket")
		String nome;
		System.out.println("["+arq1+"] ["+arq2+"]");
		if(arq1.equals(false)){  nome="PDF da cotação"}
		
		if(arq2.equals(false)){  nome="ticket"}
		System.out.println("["+nome+"]");
		return nome
	}
	
	
	
	public static void ajusteTelaTabelaDePrecos(List<String> formulario){
		System.out.println()
		System.out.println("RECURSIVO...")
		WebUI.delay(3)
		try{
	    boolean r 
		String msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
		if(msg.equals(" Nova Apólice0Nova Apólice")){
		   r=true;
		} else {
		   r=false;
		}
		System.out.println(r)
		if(r.equals(true)){
			Random gerador = new Random(); // 1. Cria um objeto da classe Random
			int numeroAleatorio = gerador.nextInt(9) + 1;
			String n = String.valueOf(numeroAleatorio)
			selecionaItemListaContains(n, '//*[@id="ParrillaBean$numFracciones"]', 'option');
			WebUI.delay(3)
			//NUMERO DE PERCELAS - FORMULARIO
			linha = StringFormatter.indexLine(formulario, "Numero de Parcelas")
			String p = StringFormatter.nextStop(formulario[linha],"Parcelas:", 0).replace("x","");
			//NUMERO PARCELAMENTO -FORMULARIO
			selecionaItemListaContains(p, '//*[@id="ParrillaBean$numFracciones"]', 'option');
			WebUI.delay(5)
			r=false;
			ajusteTelaTabelaDePrecos(formulario)
		}
		
		
		} catch (Exception e){}
		System.out.println("SAINDO DO RECURSIVO...")
	}
	
	
	
	
}

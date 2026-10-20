package autofrota


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

public class DigitacaoSeguroNovoFrotaFuncionalidades extends rpaCommands{
	
	private static TestData data
	private static TestData dataOperacao
	private static TestData dataOperacaoSinistros
	private static String testDataPath = 'Data Files/AutoFrota/DigitacaoSeguroNovoFrotaData'
//	private static String testDataSinistroPath = 'Data Files/AutoFrota/CotacaoRenovacaoInternaListaSinistrosData'
	
	private static String path
	private static String alerta="";
	private static String alertaItem
	private static int cellCheck;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "DigitacaoSeguroNovoFrota.xlsx"
	private static int cellCheckAlert;
	private static boolean enviado = true;
	private static String xpath;
	private static String tela;
	private static String operacao;
    private static tipoSeguro="Renovação Externa sem sinistro";
	
	private static boolean erroTela = false;
	private static String msgAlerta;
	private static String status;
	private static String opcao = "por Itens"
	private static boolean itemGravado=true;
	private static Map<Integer, String> alertMap;
	private static Set<String> set; 
	
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
		   WebUI.delay(10);
		   WebUI.delay(10);
	   } catch (Exception e){}
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
	* @author be54955
	* @param aba
	* @return
	*/
   public static reprocessaFalhas(String aba, String a){
	   System.err.println('Reprocessa Falhas')
	   initDataFiles()
	   if(validaDados(cellCheck)){
		   fluxoCompletoDigitacaoSeguroNovoFrota(aba,a)
		   reprocessaFalhas(aba,a)
	   }
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
			   String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\AutoFrota\\DigitacaoSeguroNovoFrota\\ConfiguracoesDigitacaoSeguroNovoFrota.txt";
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
   public static fluxoDigitacaoSeguroNovoFrota(String aba, String a){
	   System.out.println();
	   if(FileInput.validaExistenciaDeArquivo(pathFile)){
		   FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
		   FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
		   FileInput.atualizaLog( Instant.now().toString(), pathFile)
	   }
	   initDataFiles();
	   fluxoCompletoDigitacaoSeguroNovoFrota(aba,a);
	   reprocessaFalhas(aba,a);
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
   public static fluxoCompletoDigitacaoSeguroNovoFrota(aba,a){
	   try{
		   retornaHashDeColunasExcell(path);
		   cellCheck = 1 + cellNames.get("Status");
		   cellCheckAlert = 1 + cellNames.get("Mensagem");
		   int process = cellNames.get("Status");
		   int alet = cellNames.get("Mensagem");
		   System.out.println(cellCheck)
		   System.out.println(cellCheckAlert)
		   documentList();
		   init(aba,a);
		   WebUI.delay(10);
		   System.out.println()
		   for (String elemento : set) {
			   System.out.println("\n"+elemento);
		       registratens(aba, elemento);
		       if(validaDados(cellCheck)){
			    //  init(aba,a);
			      for(int i = 1; i <= data.getRowNumbers(); i++) {
				   int j =  i - 1
				   enviado = true;
				   System.out.println(data.getValue(cellCheck, i) )
				   if (data.getValue(cellCheck, i).equals('Processar') && data.getValue(3, i).equals(elemento)) {
					   List<String> registros = new ArrayList<>();
					   lista( registros , i)
					   if(!digitacao(registros,aba,a, elemento)) {
						   try{
							   salvarEvidenciasApoliceItens(aba, registros[cellNames.get("CPF/CNPJ SEGURADO")] )
						   } catch (Exception e){}
					   }
				    }
			      }
		        } else {
			      System.err.println('Não existem dados para processar')
		        }
		    }
	   } catch (Exception e){}
	   WebUI.closeBrowser();
   }
   
  /**
   * @author ALZ_SUP54955
   */
   public static void documentList(){
	   set = new LinkedHashSet<String>();
	   for(int i = 1; i <= data.getRowNumbers(); i++) {
			 set.add( data.getValue(3, i) );
	   }
	   for (String elemento : set) {
         System.out.println(elemento);
       }
   }
   
   /**
    * @author ALZ_SUP54955
    * @param aba
    */
   public static void  registratens(String aba, String doc){
	   System.out.println()
	   Set<String> mediador = new HashSet<String>();
	   List<String> mediadorList = new ArrayList<String>();
	   for(int i = 1; i <= data.getRowNumbers(); i++) {
		//   System.out.println("["+data.getValue(cellCheck, i)+"] ["+data.getValue(3, i).trim() +"] ["+doc+"]")
	//	   if(data.getValue(3, i).trim().equals(doc.trim()) && data.getValue(cellCheck, i).equals("Processar")){
		     mediador.add(data.getValue(3, i).trim());
		     mediadorList.add(data.getValue(3, i).trim())
//		   }
	   }
	   System.out.println("Mediador ["+mediador.size()+"] Mediador List ["+mediadorList.size()+"]")
	   int i = 1;
	   for(int j = 0; j < mediador.size(); j++){
		   for(int k = 0; k < mediadorList.size(); k++){
			   System.out.println("["+mediador[j]+"] ["+mediadorList[k]+"]")
               if(mediador[j].toString().equals(mediadorList[k].toString()) ){
				   System.out.println("ITEM ["+i+"] ["+cellNames.get("Item")+"] ["+i+"]");
			       auxiliares.WriteExcelFactory.updateSheetLine("", k, path, String.valueOf(i) , cellNames.get("Item") , aba);
				   i++;
               } else {
			       i=1;
               }
		   }
	   }
	   initDataFiles();
   }
   
   /**
	* @author be54955
	* @return
	*/
   public static boolean digitacao(List<String> registros, String aba, String a, String doc){
	   System.out.println()
	   boolean enviado = true;
	   String msgAlert;
	   String tela = true;
	   String xpath;
	   alerta="";
	   try{
		   alertMap = new HashMap<Integer, String>()
		   WebUI.delay(5);
		   System.out.println()
		   MenuMatriz.producaoSinistro();
		   MenuMatriz.subMenuNome('a','Produção');
		   System.out.println()
		   menuProducao(registros);  
		   dadosBasicos(registros);
		   applicationPageDadosApolFrota(registros);
		   System.out.println()
		   enviado = itensDelFrota(registros,aba,doc);
		   WebUI.delay(5);
		   System.out.println()
		   String msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
		   System.out.println("Alerta ["+msg+"]")
		   if(alertListBlocList(msg)){
			   alerta = msg;
			   fecharJanelaAtual(1)
			   alteraJanejaGenerica(0, 0)
			   enviado = true;
		   } else {
		   ajusteAlerta( registros[cellNames.get("CPF/CNPJ SEGURADO")] , registros[cellNames.get("Item")]  )
		   
		   if(enviado){
		   tabelaDePrecos(registros);
		   
		   }
		   System.out.println(alerta)
		 //  salvarEvidenciasApoliceItens(aba, registros[cellNames.get("MEDIADOR")]);
		   salvarEvidenciasApolices(aba, registros[cellNames.get("CPF/CNPJ SEGURADO")] , "" )
		   System.out.println()
		   initDataFiles();
		   
		   }
		   
	   } catch (Exception e) {
		   enviado = false
		   status = "Processar"
		   alerta="Falha ao preencher - Menu Produção"
		   fecharJanelaAtual(1)
		   alteraJanejaGenerica(0, 5)
		   init(aba, a)
	   }
	   System.out.println("\nenviado ["+ enviado+"]");
	   return enviado;
   }
   
   /**
	* @author Alz_sup54955
	* @param registros
	* @return
	*/
   public static boolean menuProducao(List<String> registros){
	   try{
		   if(enviado){
			   WebUI.delay(15);
			   alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			   System.out.println("Alerta ["+alerta+"]")
			   if(!alertListBlocList(alerta)){
				   alteraJanejaGenerica(1, 5)
				   tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
				   if(listaDeTelas(tela)){
						//Tipo Seleção
						clicarEmElementoDaTela(tipoEmissao("Emissão"), 0);
						//Agrupamento
						selecionaItemLista("Auto Frota", '//*[@id="agrupacion"]', 'option');
						//Produto
						selecionaItemLista("Novo Frota - Automovel", '//*[@id="producto"]', 'option');
						//Corretor
						xpath = '//*[@id="mediador"]';
						limpaCampo(xpath, 5)
						preencheXpath(xpath, registros[cellNames.get("MEDIADOR")], 0);
						commandTAB(xpath)
						//Selecionar
						clicarEmElementoDaTela('//*[@id="btnSeleccionar"]', 2);
						//Operatuva
						selecionaItemLista("Emitir Apólice de Multisituação Ampliação", '//*[@id="codOperativa"]', 'option');
						//Avançar
						clicarEmElementoDaTela('//*[@id="btnSeleccionOperativa"]', 0);
				   } else {
					 fecharJanelaAtual(1)
					 alteraJanejaGenerica(0, 0)
					 alerta = "Alerta: Falha Tela - Menu Produção - Não apresentada";
					 enviado = false;
				   }
			   }else {
				 fecharJanelaAtual(1)
				 alteraJanejaGenerica(0, 0)
				 enviado = false;
				 
				 if(alerta.equals(" Campo necessário")){
					 String msg = alerta;
					 String msgAlert= recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]/b', 'innerText', 0)
					 alerta = msg+": "+msgAlert
					 System.out.println("Alerta ["+alerta+"]")
				  }
			   }
		   }
		   } catch (Exception e){
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0, 0)
			alerta = "Alerta: Falha ao carregar a tela - Menu Produção";
			enviado = false;
			status = "Processado";
		  }
   }
   
   /**
	* @author Alz_sup54955
	* @param tipo
	* @return
	*/
   public static String tipoEmissao(String tipo){
	   String xpath;
	   Map<String, String> process = new HashMap<String, String>()
	   process.put('Emissão', '//*[@id="tipoDeSeleccion1"]');
	   process.put('OutrasOperações', '//*[@id="tipoDeSeleccion2"]');
	   return process.get(tipo.toString());
   }
   
   /**
	* @author Alz_sup54955
	* @param cotacao
	* @return
	*/
   public static boolean dadosBasicos(List<String> registros){
	   try{
		   if(enviado){
			   WebUI.delay(15);
			   alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			   System.out.println("Alerta ["+alerta+"]")
			   if(!alertListBlocList(alerta)){
				   System.out.println()
				   tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
					if(tela.contains("D. BÁSICOS")){ // TELA DADOS BASICOS
						System.out.println()
						// INICIO VIGENCIA
						xpath = '//*[@id="DatosGeneralesBean$fechaEfecto"]';
						limpaCampo(xpath, 0)
						preencheXpath(xpath,  ExtractDates.formatDateToBR(registros[cellNames.get("VIGÊNCIA")]), 0);
						//Forma de pagamento
						selecionaItemLista("Boleto Bancário", '//*[@id="DatosGeneralesBean$primRecibo"]', 'option');
						// Dia do Pagamento
						xpath = '//*[@id="DatosGeneralesBean$diaPago"]';
						limpaCampo(xpath, 0)
						preencheXpath(xpath, "1", 0);
						// CPF/CNPJ Tomador - Tipo Doc Tomador
						xpath = '//*[@id="IntervinientesBean$nifTomador_doc"]';
						limpaCampo(xpath, 0)
						preencheXpath(xpath, registros[cellNames.get("CPF/CNPJ SEGURADO")], 0);
						//Nome do Tomador
						xpath = '//*[@id="IntervinientesBean$nomFigTomadorValue"]';
						limpaCampo(xpath, 0)
						preencheXpath(xpath, registros[cellNames.get("SEGURADO")], 0);
						WebUI.delay(5);
						//Avançar
						clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
						avancaTelaDadosBasicos()
						WebUI.delay(5);
					} else {
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  alerta = "Alerta: Falha Tela - D. BÁSICOS  - Não apresentada";
					  enviado = false;
					  status = "Processado";
				   }
			   } else {
				 
				 if(alerta.equals(" Campo necessário")){
					 String msg = alerta;
					 String msgAlert= recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]/b', 'innerText', 0)
					 alerta = msg+": "+msgAlert
					 System.out.println("Alerta ["+alerta+"]")
				  }
				 
				 fecharJanelaAtual(1)
				 alteraJanejaGenerica(0, 0)
				 enviado = false;
				 status = "Processado";
			   }
		   }
	   } catch (Exception e){
		 fecharJanelaAtual(1)
		 alteraJanejaGenerica(0, 0)
		 alerta = "Alerta: Falha ao carregar a tela - D. BÁSICOS";
		 enviado = false;
		 status = "Processado";
	 }
   }
   
   public static void avancaTelaDadosBasicos(){
	   WebUI.delay(5);
	   tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
		if(tela.contains("D. BÁSICOS")){
			clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
			avancaTelaDadosBasicos()
		}
   }
   
   
   
   /**
	* @author Alz_sup54955
	* @param registros
	* @return
	*/
   public static boolean applicationPageDadosApolFrota(List<String> registros){
	   try{
		   if(enviado){
			   WebUI.delay(10);
			   alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			   System.out.println("Alerta ["+alerta+"]")
			   if(!alertListBlocList(alerta)){
				 System.out.println()
				 tela = StringFormatter.pointStop(recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0),"APPLICATION.PAGE.PDADOSAPOLFROTA");
				 if(listaDeTelas(tela)){
				 //Tipo de Seguro
				 selecionaItemLista( registros[cellNames.get("TIPO DE SEGURO")], '//*[@id="DadosApolFrotaBean$tipoSeguro"]', 'option');
				 try{
				 //Seguradora Anterior
				 selecionaTipoSelecao(registros[cellNames.get("SEGURADORA ANTERIOR")], '//*[@id="DadosApolFrotaBean$congenereAnt"]', 'option');
				 } catch (Exception e){}
				 //Tipo de Transporte
				 selecionaItemLista("Não se Aplica", '//*[@id="QuestionarioAvaliacaoRiscoFrotaBean$tipoDeTransporte"]', 'option');
				 //Carga Transportada 1
				 selecionaItemLista("Não se aplica", '//*[@id="QuestionarioAvaliacaoRiscoFrotaBean$cargaTransportada1"]', 'option');
				 //Carga Transportada 2
				 selecionaItemLista("Não se aplica", '//*[@id="QuestionarioAvaliacaoRiscoFrotaBean$cargaTransportada2"]', 'option');
				 //Carga Transportada 3
				 selecionaItemLista("Não se aplica", '//*[@id="QuestionarioAvaliacaoRiscoFrotaBean$cargaTransportada3"]', 'option');
				 //Em caso de viagem, onde os veículos pernoitam?
				 selecionaItemLista(ajusteCargaTransportadaTres(registros[cellNames.get("CARGA TRANSPORTADA 3")] ) , '//*[@id="QuestionarioAvaliacaoRiscoFrotaBean$pernoiteDosVeiculos"]', 'option');
				 //Qual a região de circulação mais frequente?
				 selecionaTipoSelecao(registros[cellNames.get("QUAL A REGIÃO DE CIRCULAÇÃO")], '//*[@id="QuestionarioAvaliacaoRiscoFrotaBean$regiaoCirculacaoMaisFrequente"]', 'option');
				 //Gerenciamento de Riscos
				 xpath = '//*[@id="x_gerenciamentosDeRiscos"]';
				 limpaCampo(xpath, 0)
				 preencheXpath(xpath, "Não Possui", 0);
				 //Declaração de aceite
				 clicarEmElementoDaTela('//*[@id="x_declaradoAceite"]', 0);
				 //Avançar
				 clicarEmElementoDaTela('//*[@id="o_4"]', 0);
				 WebUI.delay(5);
				 try{
					 alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
					 System.out.println("Alerta ["+alerta+"]")
					 if(alerta.equals(" Apólice possui Sinistro. Favor selecionar Renovação Interna Com Sinistro.")){
						 //Carga Transportada 3
						 selecionaItemLista(registros[cellNames.get("TIPO DE SEGURO")], '//*[@id="DadosApolFrotaBean$tipoSeguro"]', 'option');
						 //Avançar
						 clicarEmElementoDaTela('//*[@id="o_4"]', 0);
					 }
					 
					 if(alerta.contains(" Já existe cotação para esse CNPJ, número")){
						 //Avançar
						 clicarEmElementoDaTela('//*[@id="o_4"]', 0);
					 }
					 
				 } catch (Exception e){}
				 WebUI.delay(5);
				 try{
					 alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
					 System.out.println("Alerta ["+alerta+"]")
					 if(alertListContinueList(alerta)){
						 //Avançar
						 clicarEmElementoDaTela('//*[@id="o_4"]', 0);
					 }
				 } catch (Exception e){}
				 
				 } else {
				   fecharJanelaAtual(1)
				   alteraJanejaGenerica(0, 0)
				   alerta = "Alerta: Falha Tela - Application Page Dados Apol Frota - Não apresentada";
				   enviado = false;
				   status = "Processado";
				 }
			   } else {
				
				 
				 if(alerta.equals(" Campo necessário")){
				    String msg = alerta;
					String msgAlert= recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]/b', 'innerText', 0)
					alerta = msg+": "+msgAlert
					System.out.println("Alerta ["+alerta+"]")
				 }
				 
				 fecharJanelaAtual(1)
				 alteraJanejaGenerica(0, 0)
				 enviado = false;
				 status = "Processado";
				 
			   }
			 }
		   } catch (Exception e){
			 fecharJanelaAtual(1)
			 alteraJanejaGenerica(0, 0)
			 alerta = "Alerta: Falha ao carregar a tela - Application Page Dados Apol Frota";
			 enviado = false;
			 status = "Processado";
		  }
   }
   
   /**
	* @author ALZ_SUP54955
	* @param s
	* @return
	*/
   public static String ajusteCargaTransportadaTres(String s){
	   Map<String, String> seguro = new HashMap<String, String>();
	   seguro.put("Não se aplica",'Não se aplica / Não viaja');
	   seguro.put("Não viaja",'Não se aplica / Não viaja');
	   return seguro.get(s).toString();
   }
   
   /**
	* @author Alz_sup54955
	* @param registros
	* @param aba
	* @return
	*/
   public static boolean itensDelFrota(List<String> registros, String aba , String doc){
	   try{
		   if(enviado){
			   WebUI.delay(10);
			   alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			   System.out.println("Alerta ["+alerta+"]")
			   if(!alertListBlocList(alerta)){
				 tela = StringFormatter.stopString(recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0),":");
				 if(listaDeTelas(tela)){
					 System.out.println()
					 operacao = StringFormatter.nextStop(recuperaValor('//*[@id="cellAppPromptHeaderArea"]','innerText', 0 ).replaceAll("[\\[\\]]", ""), "AUTOMOVEL",0);
					 System.out.println("Operação: "+operacao)
					 System.out.println(data.getRowNumbers()+" - "+cellCheck+" "+cellNames.get("MEDIADOR") );
					 System.out.println("Documento: ["+registros[cellNames.get("CPF/CNPJ SEGURADO")] +"] ["+doc.trim()+"]")
					 int k=1; // USADO  COMO REFERENCIA DE ITEM 
					 for(int i = 1; i <= data.getRowNumbers(); i++) {
						 System.out.println(i +" - "+data.getValue(cellCheck, i));
						 enviado = true;
						 System.out.println("["+k+"] ["+i+"]");
						 System.out.println("["+data.getValue(cellCheck, 1)+"]" );
						 System.out.println("["+ data.getValue(1, 1)+"]");
						 System.out.println(itemGravado)
						 int d = cellNames.get("CPF/CNPJ SEGURADO") + 1;
						 System.out.println("["+data.getValue(d, i).trim()+"] ["+doc.trim()+"]" )
						// if(data.getValue(cellCheck, i).equals('Processar') && data.getValue(1, i).trim().equals(registros[cellNames.get("MEDIADOR")].trim()) ) {
						 if(data.getValue(cellCheck, i).equals('Processar') && data.getValue(d, i).trim().equals(doc.trim())){
							try{
								 listOfElements.clear();
								 selecionaListaDeElementos('/html/body','tr');
								 int n=0
								 for(int x = 0; x < listOfElements.size(); x++){
									 if( listOfElements[x].getAttribute("id").contains("tr_") ){
										 System.out.println("["+x+"] ["+listOfElements[x].getText()+"]");
										 n++
									 }
								 }
								 System.out.println()
								 System.out.println("Lista itens ["+n+"]")
								 if(n>=1){
									 k = n+1;
								 }							
							} catch (Exception e){}
							 
							if(k>1){
								if(itemGravado){
								 //Voltar
								 selecionaItemLista("Voltar",'/html/body' , "td" )
								 okAlerta();
								}
							}
							List<String> item = new ArrayList<>();
							lista( item , i)
							System.out.println("["+item[cellNames.get("MEDIADOR")]+"]");
							System.out.println("["+item[cellNames.get("Item")]+"]");
							System.out.println("Lista itens ["+k+"]");
							System.out.println()
							
							if(gravarItem(item ,aba, k)){
							//	k = k+1;
							} else {
							  enviado = false;
							}
						 }
				    }
					System.out.println()
					//AVANÇAR
					selecionaItemLista("Avançar",'/html/body' , "td" );
					alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 2)
					System.out.println("Alerta ["+alerta+"]")
					if(alertListContinueList(alerta)){
						 //AVANÇAR
						 selecionaItemLista("Avançar",'/html/body' , "td" );
				    }
					alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 2)
					System.out.println("Alerta ["+alerta+"]")
					if(alertListContinueList(alerta)){
						  //AVANÇAR
						  selecionaItemLista("Avançar",'/html/body' , "td" );
					}
					enviado = true;
				    System.out.println()
					
					if(alertListBlocList(alerta)){
						fecharJanelaAtual(1)
						alteraJanejaGenerica(0, 0)
						System.out.println("Alerta ["+alerta+"]")
						enviado = false;
					} else {
					    enviado = true;
					}
				 } else {
				   fecharJanelaAtual(1)
				   alteraJanejaGenerica(0, 0)
				   alerta = "Alerta: Falha Tela - Itens de Frota - Não apresentada";
				   enviado = false;
				 }
			   } else {
				
				 
				 if(alerta.equals(" Campo necessário")){
					 String msg = alerta;
					 String msgAlert= recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]/b', 'innerText', 0)
					 alerta = msg+": "+msgAlert
					 System.out.println("Alerta ["+alerta+"]")
				  }
				 
				 System.out.println("alerta ["+alerta+"]\n")
				 fecharJanelaAtual(1)
				 alteraJanejaGenerica(0, 0)
				 enviado = false;
				 
			   }
			 }
		   } catch (Exception e){
			 fecharJanelaAtual(1)
			 alteraJanejaGenerica(0, 0)
			 enviado = false;
		  }
   }
   
   /**
	* @author Alz_sup54955
	* @param item
	* @return
	*/
   public static boolean gravarItem(List<String> item, String aba, int i){
	   System.out.println("MEDIADOR ["+item[1]+"] ITEM ["+item[ cellNames.get("Item") ]+"] CHASSI ["+item[ cellNames.get("CHASSI") ]+"]")
	   boolean itemUmSelecionado = false;
	   String modelo
	   int t;
	   int v;
	   System.out.println()
	   try{
		   //ABRIR CAMPO
		   clicarEmElementoDaTela('//*[@id="incluirItem"]/table/tbody/tr[1]/td/table/tbody/tr/td[2]', 0);
		   //ITEM
		   preencheXpath('//*[@id="x_item"]', item[cellNames.get("Item")], 2);
		   //CHASSI 
		   preencheXpath('//*[@id="chassi"]', item[cellNames.get("CHASSI")], 2);
		   //LUPA
		   clicarEmElementoDaTela('//*[@id="consultarChassi"]', 0);
		   WebUI.delay(10);
		   okAlerta();
		   WebUI.delay(2);
		   //Clique na linha para escolher o veículo desejado...
		   selecionaPrimeiroItemListaDuploClick('//*[@id="myModal"]', 'tr') 
		   //CLASSE DE BONUS 
		   WebUI.delay(5);
		   selecionaTipoSelecao(ajustBnus(item[cellNames.get("BÔNUS")]), '//*[@id="x_classeBonus"]', 'option');
		   //VALOR REF. (COB CASCO VD)
		   //preencheXpath('//*[@id="x_valorReferencia"]', valorRef( item[cellNames.get("VALOR REF. (COB CASCO VD)")], 0) );
		   //Zero km
		   selecionaItemLista(zeroKM(item[ cellNames.get("Zero KM")] ), '//*[@id="x_zeroKMFrota"]', 'option');
		   //Estado
		   selecionaItemLista(item[ cellNames.get("ESTADO")] , '//*[@id="x_estado"]', 'option');
		   WebUI.delay(2);
		   //Municipio
		   selecionaItemLista(item[ cellNames.get("MUNICÍPIO")], '//*[@id="x_municipio"]', 'option');
		   //Propriedade
		   String s = retornaItemSelecionadoLista('//*[@id="x_propriedadeVeiculo"]', 'option', "selected" )
		   System.out.println("["+s+"] ["+item[ cellNames.get("PROPRIEDADE") ]+"]")
		   if(s.contains("[SELECIONE...]")){
			   selecionaItemLista(item[ cellNames.get("PROPRIEDADE")], '//*[@id="x_propriedadeVeiculo"]', 'option');
		   }
		   //CPF PROPRIETARIO 
		   limpaCampo('//*[@id="x_documentoProprietario"]', 1)
		   preencheXpath('//*[@id="x_documentoProprietario"]', item[ cellNames.get("CPF/CNPJ SEGURADO")], 0);
		   //Gravar
		   clicarEmElementoDaTela('//*[@id="x_btngravar"]', 0);
		   WebUI.delay(15);
		  
		   alerta = pegaMSGAlerta();
		   System.out.println("["+alerta+"]")
		   
		   if(alerta.equals("Valor do veículo não preenchido!")){
			  System.out.println()
			  okAlerta();
		      //VALOR REF. (COB CASCO VD)
			  preencheXpath('//*[@id="x_valorReferencia"]', "100.000", 0);
			  commandTAB('//*[@id="x_valorReferencia"]')
			  //Gravar
			  clicarEmElementoDaTela('//*[@id="x_btngravar"]', 0);
			  WebUI.delay(10);
		   }
		   
		   alerta = pegaMSGAlerta();
		   System.out.println("["+alerta+"]")
		   
		   if(alertListBlocList(alerta)){
			   okAlerta();
			   enviado = false;
			   itemGravado = false;
			   System.out.println("Saving Alert item...["+alerta+"] ["+ item[ cellNames.get("Item") ]+"]")
		       salvarEvidenciasApoliceItem(  "Alerta: "+alerta, Integer.parseInt(item[ cellNames.get("Item") ]))
			   okAlerta();
			   //Limpar 
			   clicarEmElementoDaTela('//*[@id="x_btnlimpar"]', 0);
			   okAlerta();
			   //ABRIR CAMPO
			   clicarEmElementoDaTela('//*[@id="incluirItem"]/table/tbody/tr[1]/td/table/tbody/tr/td[2]', 0);
			   okAlerta();
		   } else {
		   okAlerta();
		   //Avançar
		   clicarEmElementoDaTela('//*[@id="o_4"]', 0);
		   WebUI.delay(15);
		   System.out.println("ITEM ["+item[ cellNames.get("Item") ] +"]")
		   //Filtro
		   selecionaItemLista(opcao, '//*[@id="x_filtro"]', 'option');
		   //Seleciona item
		   selecionaItemLista( item[ cellNames.get("Item") ] , '//*[@id="coberturasSectionContent"]/table/tbody/tr/td[1]/table/tbody/tr[3]/td/table/tbody/tr[1]/td/table/tbody/tr/td[2]/table', 'option');
		   
		   //Contrata Casco e(ou) Val. Determinado
		   selecionaItemLista("Sim", '//*[@id="x_val_det"]', 'option');
		   //FIPE
		   selecionaItemLista( item[ cellNames.get("% FIPE") ], '//*[@id="x_perc_fipe"]', 'option');
		   //TIPO FRANQUIA
		   selecionaItemLista(tipoFranquia(item[cellNames.get("FRANQUIA") ]), '//*[@id="x_tipo_franquia"]', 'option');
		   //RCF - Danos Materiais
		   selecionaItemLista(CurrencyMonetary.formataValorMonetario(item[ cellNames.get("RCF - DANOS MATERIAIS") ]).trim(), '//*[@id="x_danos_materiais"]', 'option');
		   //RCF - Danos Corporais
		   selecionaItemLista(CurrencyMonetary.formataValorMonetario(item[ cellNames.get("RCF - DANOS CORPORAIS")]).trim(), '//*[@id="x_danos_corporais"]', 'option');
		   //Gastos com Defesa
		   selecionaItemListaAtributo(item[  cellNames.get("GASTOS COM DEFESA") ], '//*[@id="x_gastos_defesa"]', 'option', "value");
		   //RCF - Danos Morais e Estéticos
		   selecionaItemLista(CurrencyMonetary.formataValorMonetario(item[ cellNames.get("RCF - DANOS MORAIS") ]).trim(), '//*[@id="x_danos_morais"]', 'option');
		 
		   if(item[ cellNames.get("APP - MORTE") ].toString().length() > 0){
		   t = Integer.parseInt(item[ cellNames.get("APP - MORTE") ])
		   System.out.println("APP - Morte ["+t+"]")
		   if(t>0){
			 //APP - Morte
			 xpath = '//*[@id="x_app_morte"]';
			 preencheXpath(xpath,item[ cellNames.get("APP - MORTE")].trim(), 0);
			 commandTAB(xpath)
		   }
		   }
			   
		   if(item[ cellNames.get("APP - INVALIDEZ") ].toString().length() > 0){
		   t = Integer.parseInt(item[cellNames.get("APP - INVALIDEZ")] )
		   System.out.println("APP - Invalidez ["+t+"]")
		   if(t>0){
			 //APP - Invalidez
			 xpath = '//*[@id="x_app_invalidez"]';
			 preencheXpath(xpath,item[ cellNames.get("APP - INVALIDEZ")].trim(), 0);
			 commandTAB(xpath)
		   }
		   }
		 
		   //Vidros, Lant., Faróis e Retr.
		   selecionaItemLista(validaValorVidroContratado(item[ cellNames.get("Vidros, Lant., Faróis e Retr.") ]), '//*[@id="x_vidros"]', 'option');
		   //Acessórios gerais
		   xpath = '//*[@id="x_aces_gerais"]';
		   if(item[ cellNames.get("ACESSÓRIOS GERAIS") ].toString().length() > 0){
		   t = Integer.parseInt(item[cellNames.get("ACESSÓRIOS GERAIS")])
		   System.out.println(t)
		   if(t>0){ preencheXpath(xpath, item[ cellNames.get("ACESSÓRIOS GERAIS")], 0);
			 commandTAB(xpath)}
		   }
		   
		   //Blindagem
		   xpath = '//*[@id="x_blindagem"]';
		   if(item[ cellNames.get("BLINDAGEM") ].toString().length() > 0){
		   t = item[cellNames.get("BLINDAGEM")].length()
		   System.out.println(t)
		   if(t>0){preencheXpath(xpath, item[cellNames.get("BLINDAGEM")], 0);
		   commandTAB(xpath)}
		   }
		   //Assistência 24 horas
		   selecionaItemLista(validaValorContratadoAss24hs(item[ cellNames.get("ASSISTÊNCIA 24H") ]), '//*[@id="x_assist24h"]', 'option');
		   //Carro Reserva
		   selecionaItemLista(validaValorContratadoCarroReserva(item[ cellNames.get("CARRO RESERVA") ]), '//*[@id="x_carro_reserva"]', 'option');
		   //Aplicar Configuração
		   clicarEmElementoDaTela('//*[@id="o_3"]', 0);
	
		   WebUI.delay(15);
		   String msg = pegaMSGAlerta();
		   System.out.println("["+msg+"]")
		   System.out.println()
		   okAlerta();
		   WebUI.delay(10)
		   alerta = "Cobertura gravada conforme escolhido"
		   enviado = true;
		   itemGravado = true;
		   System.out.println("Saving item...["+alerta+"] ["+ item[ cellNames.get("Item") ]+"]")
		   salvarEvidenciasApoliceItem(  alerta, Integer.parseInt(item[ cellNames.get("Item") ]))
		   }
		   
	   } catch (Exception e){
	     alerta = "Cobertura não gravada "
	     enviado = false;
	   
	   }
	   return enviado;
   }
   
   /**
    * @author ALZ_SUP54955
    */
   public static String ajustBnus(String b){
	   String n;
	   int i = Integer.parseInt(b)
	   n = String.valueOf(i)
	   return n;
   }
   
   /**
	* @author Alz_sup54955
	* @param item
	* @return
	*/
   public static boolean listaDeModelos(List<String> item){
	   WebUI.delay(5);
	   System.out.println()
	   boolean b = false;
	   try{
		   tela = recuperaValor( '//*[@id="myModal"]/div/h3', 'innerText', 0);
		   if(listaDeTelas(tela)){
			   System.out.println()
			 //Versão
			 if(selecionaItemListaDuploClick(item[21], '//*[@id="tbodylistaVeiculos"]', 'tr')){
			   WebUI.delay(2);
			   fechaAlerta();
			   b = true
			 }
		   }
	   }catch (Exception e){}
	   return b;
   }
   
   /**
	* @author T_DCDias
	* @return
	*/
   public static boolean selecionaItemListaDuploClick(String tipo,String xpath , String tag ){
	   boolean b = false;
	   selecionaListaDeElementos(xpath,tag)
	   System.out.println()
	   System.out.println("LIST SIZE "+listOfElements.size())
	   for(int i = 0; i < listOfElements.size(); i++){
		   System.err.println("["+i+"]  ["+listOfElements[i].getAttribute('value')+"] ["+listOfElements[i].getText()+"] ["+tipo+"]")
		   if(listOfElements[i].getText().contains(tipo.toString().replaceAll("  ", " "))){
			   System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")  //*[@id="dlv1linha1"]
			   String dlv1linha = "dlv1linha"
			   String num = Integer.toString(i)+'"';
			   String xpth = '//*[@id="'+dlv1linha+num+"]"
			   System.out.println(xpth)
			   duploClickEmElementoDaTela(xpth, 0)
			   b = false;
			   break
		   } else {
			 b = true;
		   }
	   }
	   return b;
   }
   
   /**
	* @author T_DCDias
	* @return
	*/
   public static String selecionaPrimeiroItemListaDuploClick(String xpath , String tag ){
	   String str;
	   try{
	   selecionaListaDeElementos(xpath,tag)
	   System.out.println()
	   System.out.println("LIST SIZE "+listOfElements.size())
	   for(int i = 0; i < listOfElements.size(); i++){
		   System.err.println("["+i+"]  ["+listOfElements[i].getAttribute('value')+"] ["+listOfElements[i].getText()+"]")
		   String dlv1linha = "dlv1linha"
		   String num = Integer.toString(i)+'"';
		   String xpth = '//*[@id="'+dlv1linha+num+"]"
		   System.out.println(xpth)
		   str = listOfElements[i].getText()
		   duploClickEmElementoDaTela(xpth, 0)
		   break
	   }
	   } catch (Exception e){}
	   return str;
   }
   
   /**
	* @author Alz_sup54955
	* @param tipo
	* @return
	*/
   public static String zeroKM(String tipo){
	   if(tipo.length()>0){
	     String xpath;
	     Map<String, String> process = new HashMap<String, String>()
	     process.put('S', 'Sim');
	     process.put('N', 'Não');
	     return process.get(tipo.toString());
	   }
   }
	
   /**
	* @author Alz_sup54955
	* @param tipo
	* @return
	*/
   public static String valorRef(String tipo){
	   if(tipo.length()>0){
		  String ref = "100.000";
		  return ref;
	   }
   }
   
   /**
	* @author Alz_sup54955
	* @param f
	* @return
	*/
   public static String tipoFranquia(String f){
	   Map<String, String> seguro = new HashMap<String, String>();
	   seguro.put("0",'Sem Franquia');
	   seguro.put("1",'Normal');
	   seguro.put("4",'Reduzida');
	   seguro.put("7",'Especial');
	   return seguro.get(f).toString();
   }
   
   /**
	* @author Alz_sup54955
	* @param f
	* @return
	*/
   public static String validaValorVidroContratado(String f){
   Map<String, String> seguro = new HashMap<String, String>();
	   seguro.put("00",'Não Contratado');
	   seguro.put("01",'Vidros Plano 1');
	   seguro.put("02",'Vidros Plano 2');
	   seguro.put("03",'Vidro Blindado');
	   return seguro.get(f).toString();
   }
   
   /**
	* @author Alz_sup54955
	* @param f
	* @return
	*/
   public static String validaValorContratadoAss24hs(String f){
	   Map<String, String> seguro = new HashMap<String, String>();
	   seguro.put("00",'Não contratada');
	   seguro.put("01",'Plano 1');
	   seguro.put("02",'Plano 2');
	   seguro.put("03",'Plano 3');
	   return seguro.get(f).toString();
   }
   
   
   /**
	* @author Alz_sup54955
	* @param f
	* @return
	*/
   public static String validaValorContratadoCarroReserva(String f){
	   Map<String, String> seguro = new HashMap<String, String>();
	   seguro.put("00",'Não contratado');
	   seguro.put("07",'7 dias');
	   seguro.put("15",'15 dias');
	   seguro.put("30",'30 dias');
	   return seguro.get(f).toString();
   }
   
   /**
	* @author Alz_sup54955
	* @param aba
	* @param apolice
	* @param item
	* @return
	*/
   public static salvarEvidenciasApoliceItem(String msg, int j ){
	   System.out.println("Salving item ["+j+"]")
	  // int k =  j - 1
	   alertMap.put(j,msg);
	   System.out.println("Add... "+alertMap.get(j).toString())
   }
   
   
   /**
	* @author Alz_sup54955
	* @param aba
	* @param apolice
	* @param item
	* @return
	*/
   public static salvarEvidenciasApoliceItens(String aba, String mediador ){
	   try{
	   initDataFiles();
	   System.out.println( "["+aba+"] ["+mediador.trim()+"] ["+data.getRowNumbers()+"] ["+cellNames.get("CPF/CNPJ SEGURADO")+"] ["+cellNames.get("Item")+"] ["+operacao+"]")
	   int m = 1 + cellNames.get("CPF/CNPJ SEGURADO");
	   for(int i = 1; i <= data.getRowNumbers(); i++) {
	       int k =  i - 1
		   System.out.println( "["+i+"] ["+  data.getValue( m , i) +"]  ["+alertMap.get(i).toString()+"]"  );
		   if(data.getValue(m, i).trim().equals(mediador.trim())){
			   auxiliares.WriteExcelFactory.updateSheetLine("", k, path, "Alerta: "+alerta +" Documento: "+ mediador +" Item: "+ i , cellNames.get("Alerta"), aba);
			   auxiliares.WriteExcelFactory.updateSheetLine("", k, path, "Processado", cellNames.get("Status"), aba);
		   }
	   }
	   } catch (Exception e){}
   }
   
   /**
	* @author Alz_sup54955
	* @param aba
	* @param apolice
	* @param item
	* @return
	*/
   public static salvarEvidenciasApolices(String aba, String mediador, String msg ){
	   try{
	   initDataFiles();
	   System.out.println( "["+aba+"] ["+mediador.trim()+"] ["+data.getRowNumbers()+"] ["+cellNames.get("MEDIADOR")+"] ["+cellNames.get("Item")+"] ")
	   int m = 1 + cellNames.get("CPF/CNPJ SEGURADO");
	   for(int i = 1; i <= data.getRowNumbers(); i++) {
		   int k =  i - 1
		   int x = 1;
		   System.out.println( "["+i+"] ["+data.getValue( m , i).trim() +"]  ["+msg+"]  ["+alertMap.get(x).toString()+"]"  );
		   if(data.getValue(m, i).trim().equals(mediador.trim())){
			   auxiliares.WriteExcelFactory.updateSheetLine("", k, path, alertMap.get(x).toString()+" Item: "+ i  , cellNames.get("Mensagem"), aba);
			   auxiliares.WriteExcelFactory.updateSheetLine("", k, path, "Processado", cellNames.get("Status"), aba);
			   auxiliares.WriteExcelFactory.updateSheetLine("", k, path, operacao, cellNames.get("Operacao"), aba);
			   x = x+1;
		   }
	   }
	   } catch (Exception e){}
   }
   
   /**
	* @author Alz_sup54955
	* @param formulario
	* @return
	*/
   public static tabelaDePrecos(List<String> registros){
	   try{
	   System.out.println()
	   if(enviado){
		   WebUI.delay(10);
		   alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
		   if(!alertListBlocList(alerta)){
			   tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
			   if(tela.contains("TABELA DE PREÇOS")){
				   
				   operacao = StringFormatter.nextStop(tela, "AUTOMOVEL", 0).replaceAll("[\\[\\]]", "");
				   System.out.println("Operação recuperada: ["+operacao+"]")
				   xpath = '//*[@id="ComisionamientoBean$porcComision"]'
				   limpaCampo(xpath, 0)
				   preencheXpath(xpath,registros[cellNames.get("COMISSÃO")], 0);
				   WebUI.delay(3);
				   //MENU
				   selecionaItemLista("Menu", '//*[@id="tableOptMenu_"]', 'td');
				   fecharJanelaAtual(1)
				   alteraJanejaGenerica(0, 0)
				   enviado = true;
			   }
		   } else {
		   fecharJanelaAtual(1)
		   alteraJanejaGenerica(0, 0)
		   enviado = false;
		   
		   if(alerta.equals(" Campo necessário")){
			   String msg = alerta;
			   String msgAlert= recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]/b', 'innerText', 0)
			   alerta = msg+": "+msgAlert
			   System.out.println("Alerta ["+alerta+"]")
			}
		   
		   }
	   } else {
		 System.out.println("Erro Tabela de preços ["+alerta+"] ["+enviado+"]")
	   }
	   } catch (Exception e){
		 fecharJanelaAtual(1)
		 alteraJanejaGenerica(0, 0)
		 enviado = false;
	   }
   }
   
   
   /**
	* @author Alz_sup54955
	* @param apolice
	*/
   public static void ajusteAlerta(String mediador, String itemFrota){
	   System.err.println()
	   try{
	   ArrayList<String> itensList = new ArrayList<String>()
	   String attrib = "style";
	   String tag ="tr";
	   String xpath='//*[@id="x_itensGrid"]'
	   selecionaListaDeElementos(xpath,tag)
	   System.out.println("NUNBER OF TAGS ["+listOfElements.size()+"]")
	   for(int i = 0; i < listOfElements.size(); i++){
		   int j = i+1
		   // ITEM
		   String item = recuperaValor('//*[@id="td_1_'+j+'"]', 'textContent', 0)
		   System.out.println("ITEM ["+i+"] ["+listOfElements[i].getText()+"] ["+item+"]")
		   List<WebElement> listOfElementstags = listaDeElementos('//*[@id="tr_'+j+'"]', "img")
		   for(int k = 0; k < listOfElementstags.size(); k++){
			   System.out.println("IMG ["+k+"] ["+listOfElementstags.get(k).getTagName()+"]  ["+listOfElementstags[k].getAttribute("style")+"]" )
			   if(listOfElementstags[k].getAttribute(attrib).equals("background-color: transparent;".toString())){
				   System.out.println("FOUND IMG ["+k+"] ["+listOfElementstags[k].getAttribute("style")+"]" )
				   itensList.add(item);
			   }
		   }
	   }
	   System.out.println("ITENS ["+itensList.size()+"]")
	   for(int n = 0; n < itensList.size(); n++){
		   System.out.println("ITEM ["+itensList.get(n)+"]")
		   ajustaItem(mediador, itensList.get(n))
	   }
	   WebUI.delay(5);
	   //AVANÇAR
	   selecionaItemLista("Avançar",'/html/body' , "td" );
	   alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 2)
		System.out.println("Alerta ["+alerta+"]")
		if(alertListContinueList(alerta)){
			 //AVANÇAR
			 selecionaItemLista("Avançar",'/html/body' , "td" );
		}
		alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 2)
		System.out.println("Alerta ["+alerta+"]")
		if(alertListContinueList(alerta)){
			  //AVANÇAR
			  selecionaItemLista("Avançar",'/html/body' , "td" );
		}
        } catch (Exception e){}
		enviado = true;
		System.out.println()
   }
   
   /**
	* @author Alz_sup54955
	* @param numeroItem
	* @param numeroApolice
	* @return
	*/
   public static ajustaItem(String numeroMediador , String numeroItem ){
	   System.out.println("CPF/CNPJ SEGURADO ["+numeroMediador+"] ITEM ["+numeroItem+"]");
	   boolean itemUmSelecionado = false;
	   String modelo
	   int t;
	   int v;
	   
	   System.out.println(data.getRowNumbers())
	   
	   int m = 1 + cellNames.get("CPF/CNPJ SEGURADO");
	   int e = 1 + cellNames.get("Item");
	 
	   for(int i = 1; i <= data.getRowNumbers(); i++) {
		   int j =  i - 1
		   enviado = true;
		   System.out.println( "["+i+"] ["+  data.getValue( m , i) +"]  ["+ numeroMediador +"]  ["+ data.getValue( e , i) +"] ["+ numeroItem+"]"  );
		   if ( data.getValue(m, i).equals(numeroMediador) && data.getValue(e, i).equals(numeroItem)){
			   List<String> item = new ArrayList<>();
			   lista( item , i)
				   System.out.println("ITEM ["+item[ cellNames.get("Item") ] +"]")
				   //Filtro
				   selecionaItemLista(opcao, '//*[@id="x_filtro"]', 'option');
				   //Seleciona item
				   selecionaItemLista( item[ cellNames.get("Item") ] , '//*[@id="coberturasSectionContent"]/table/tbody/tr/td[1]/table/tbody/tr[3]/td/table/tbody/tr[1]/td/table/tbody/tr/td[2]/table', 'option');
				   
				   //Contrata Casco e(ou) Val. Determinado
				   selecionaItemLista("Sim", '//*[@id="x_val_det"]', 'option');
				   //FIPE
				   selecionaItemLista( item[ cellNames.get("% FIPE") ], '//*[@id="x_perc_fipe"]', 'option');
				   //TIPO FRANQUIA
				   selecionaItemLista(tipoFranquia(item[cellNames.get("FRANQUIA") ]), '//*[@id="x_tipo_franquia"]', 'option');
				   //RCF - Danos Materiais
				   selecionaItemLista(CurrencyMonetary.formataValorMonetario(item[ cellNames.get("RCF - DANOS MATERIAIS") ]).trim(), '//*[@id="x_danos_materiais"]', 'option');
				   //RCF - Danos Corporais
				   selecionaItemLista(CurrencyMonetary.formataValorMonetario(item[ cellNames.get("RCF - DANOS CORPORAIS")]).trim(), '//*[@id="x_danos_corporais"]', 'option');
				   //Gastos com Defesa
				   selecionaItemListaAtributo(item[  cellNames.get("GASTOS COM DEFESA") ], '//*[@id="x_gastos_defesa"]', 'option', "value");
				   //RCF - Danos Morais e Estéticos
				   selecionaItemLista(CurrencyMonetary.formataValorMonetario(item[ cellNames.get("RCF - DANOS MORAIS") ]).trim(), '//*[@id="x_danos_morais"]', 'option');
				 
				   if(item[ cellNames.get("APP - MORTE") ].toString().length() > 0){
				   t = Integer.parseInt(item[ cellNames.get("APP - MORTE") ])
				   System.out.println("APP - Morte ["+t+"]")
				   if(t>0){
					 //APP - Morte
					 xpath = '//*[@id="x_app_morte"]';
					 preencheXpath(xpath,item[ cellNames.get("APP - MORTE")].trim(), 0);
					 commandTAB(xpath)
				   }
				   }
				   
				   if(item[ cellNames.get("APP - INVALIDEZ") ].toString().length() > 0){
				   t = Integer.parseInt(item[cellNames.get("APP - INVALIDEZ")] )
				   System.out.println("APP - Invalidez ["+t+"]")
				   if(t>0){
					 //APP - Invalidez
					 xpath = '//*[@id="x_app_invalidez"]';
					 preencheXpath(xpath,item[ cellNames.get("APP - INVALIDEZ")].trim(), 0);
					 commandTAB(xpath)
				   }
				   }
				 
				   //Vidros, Lant., Faróis e Retr.
				   selecionaItemLista(validaValorVidroContratado(item[ cellNames.get("Vidros, Lant., Faróis e Retr.") ]), '//*[@id="x_vidros"]', 'option');
				   //Acessórios gerais
				   xpath = '//*[@id="x_aces_gerais"]';
				   if(item[ cellNames.get("ACESSÓRIOS GERAIS") ].toString().length() > 0){
				   t = item[cellNames.get("ACESSÓRIOS GERAIS")].length()
				   System.out.println(t)
				   if(t>0){ preencheXpath(xpath, item[ cellNames.get("ACESSÓRIOS GERAIS")], 0);
					 commandTAB(xpath)}
				   }
				   
				   //Blindagem
				   xpath = '//*[@id="x_blindagem"]';
				   if(item[ cellNames.get("BLINDAGEM") ].toString().length() > 0){
				   t = item[cellNames.get("BLINDAGEM")].length()
				   System.out.println(t)
				   if(t>0){preencheXpath(xpath, item[cellNames.get("BLINDAGEM")], 0);
				   commandTAB(xpath)}
				   }
				   
				   //Assistência 24 horas
				   selecionaItemLista(validaValorContratadoAss24hs(item[ cellNames.get("ASSISTÊNCIA 24H") ]), '//*[@id="x_assist24h"]', 'option');
				   //Carro Reserva
				   selecionaItemLista(validaValorContratadoCarroReserva(item[ cellNames.get("CARRO RESERVA") ]), '//*[@id="x_carro_reserva"]', 'option');
				   //Aplicar Configuração
				   clicarEmElementoDaTela('//*[@id="o_3"]', 0);
				   WebUI.delay(5);
				   fechaAlerta();
				   alerta = "Cobertura gravada conforme escolhido"
				   enviado = true;
				   itemGravado = true;
				   System.out.println("Saving item...["+alerta+"] ["+ item[ cellNames.get("Item") ]+"]")
				   salvarEvidenciasApoliceItem(  alerta, Integer.parseInt(item[ cellNames.get("Item") ]))
		   }   
	   
	   }
	  
   }
}

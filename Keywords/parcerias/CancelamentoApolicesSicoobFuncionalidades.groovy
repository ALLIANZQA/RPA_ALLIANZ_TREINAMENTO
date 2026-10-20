package parcerias


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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CancelamentoApolicesSicoobFuncionalidades extends rpaCommands{
	
	
	private static TestData data
	private static TestData dataOperacao
	private static String testDataPath = 'Data Files/Parcerias/ParceriasData/CancelamentoApolicesSicoobData'
	private static String path
	private static String alerta
	private static String retorno
	private static int cellCheck = 6;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "CancelamentoApolicesSicoob.xlsx"
	private static int cellCheckAlert;
	private static boolean enviado = true;
	private static String xpath;
	private static String tela;
	private static boolean erroTela = false;
	private static String msgAlerta
	private static String status
//	private static Map<String, Integer> cellNames;
	
	private static boolean parcelaUnica = false;
	private static boolean nParcelas = false;

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCancelamentoApolicesSicoob(String aba, String a){
		System.out.println()
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		initDataFiles()
		fluxoCompletoCancelamentoApolicesSicoob(aba,a)
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
	public static fluxoCompletoCancelamentoApolicesSicoob(aba,a){
		System.out.println()
		try{
			retornaHashDeColunasExcell(path);
			cellCheck = 1 + cellNames.get("Status");
			cellCheckAlert = 1 + cellNames.get("Mensagem");
			int process = cellNames.get("Status");
			int alet = cellNames.get("Mensagem");
			int aletnParcelas = cellNames.get("MSG N PARCELAS");
			int aletParcelaUnica = cellNames.get("MSG PARCELA ÚNICA");
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
						
						if(CancelamentoApolicesSicoob(registros,aba,a)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado" , process, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta , alet, aba);
								if(parcelaUnica){
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta , aletParcelaUnica, aba);
								}
								if(nParcelas){
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta , aletnParcelas, aba);
								}
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, process, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: "+alerta, alet, aba);
								if(parcelaUnica){
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta , aletParcelaUnica, aba);
								}
								if(nParcelas){
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta , aletnParcelas, aba);
								}
							} catch (Exception e){}
						}
					}
				}
			} else {
				System.err.println('Não existem dados para processar')
			}
		} catch (Exception e){}
		WebUI.closeBrowser();
	//	init(aba,a);
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean CancelamentoApolicesSicoob(List<String> registros, String aba, String a){
		String numParcelas = registros[cellNames.get("Nº DA PARCELA")]
		int n = Integer.parseInt(numParcelas);
		System.out.println('['+n+']')
		switch (n){
			case 1:
			MenuMatriz.consultasEstatisticas()
			MenuMatriz.subMenuNome("a","Consultas Gerais")
			alteraJanejaGenerica(1, 5)
			enviado = CancelamentoApolicesSicoobParcelaUnica(registros,aba,a);
			if(enviado){
				parcelaUnica = true;
			} 
			break;
			case retornaParcela(n):
			MenuMatriz.producaoSinistro();
			MenuMatriz.subMenuNome("a","Cancelamento")
			alteraJanejaGenerica(1, 5)
			enviado = CancelamentoApolicesSicoobNParcelas(registros,aba,a);
			if(enviado){
				nParcelas = true;
			} 
			break;
		}
			
		System.out.println("\nenviado ["+ enviado+"] \nmsgAlerta ["+alerta+"]");
		return enviado;
	}
	
	/**
	 * @author be54955
	 * @return
	 */
	public static boolean CancelamentoApolicesSicoobParcelaUnica(List<String> registros, String aba, String a){
		boolean enviado = true;
		String msgAlert;
		String tela = true;
		boolean situacao;
		String IOF;
		String modelo = "530";
		String descricao = "DEVOLUÇÃO IOF";
		WebUI.delay(5);
		System.out.println()
		try{
			tela = recuperaValor('//*[@id="apoliceSusep_label"]', 'innerText', 5)
			System.out.println(tela)
			if(tela.equals("Apólice SUSEP")){
				//Proposta
				preencheXpath('//*[@id="apolice"]',registros[cellNames.get("Nº PROPOSTA")].toString().trim() , 0);
				//Pesquisar
				selecionaItemLista("Pesquisar",'//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table' , "div" )
				//Lista recibos
				WebUI.delay(10);
				selecionaItemLista("Lista Recibos",'//*[@id="dadosgeraisSectionBar"]/table/tbody/tr/td' , "div" )
				WebUI.delay(1);
				System.out.println();
				//Verifica lista de apolices 
				if(!enviaRecibo(registros)){
				   IOF = retornaValorInput('//*[@id="sectionDadosFinanceirosContent"]', 'label', 'input', "I.O.F.")	
				   //Fecher 
				   clickXpath('//*[@id="o_6"]', 0);
				   alteraJanejaGenerica(0, 0)
				   MenuMatriz.newFluxoGestao('/html/body/div[1]/main/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]')
				   alteraJanejaGenerica(1, 10)
				   tela = recuperaValor('/html/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/div', 'innerText', 1)
				   System.out.println(tela)
				   if(listaDeTelas(tela)){
					   novaFichaGestaoProcesso(registros, 5, 0, 'P-Cancelamento')
					   WebUI.delay(3);
					   //Menu Ficha Gestão 
					   selecionaItemLista("Nota/Agenda",'/html/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div' , "a" )
					   alteraJanejaGenerica(2, 10)
					   tela = recuperaValor('//*[@id="tblHeaderArea"]/tbody/tr[2]', 'innerText', 1)
					   System.out.println(tela)
					   if(listaDeTelas(tela)){
							//Modelo
						   preencheXpath('//*[@id="numModCodi"]', modelo, 0);
						   //Descrição
						   preencheXpath('//*[@id="txtModeloDescL"]', descricao, 0);
						   //Aceitar
						   selecionaItemLista("Aceitar",'//*[@id="sectionDatosDocumentoSectionBar"]/table' , "div" );
						   WebUI.delay(3);
						   limpaCampo('//*[@id="texto"]',0)
						   clickXpath('//*[@id="texto"]', 0);
						   preencheXpath('//*[@id="texto"]', texo(registros, IOF), 0);
						   WebUI.delay(2);
						   System.out.println();
						   //Gravar
						   selecionaItemLista("Gravar",'//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td' , "div" );
						   
						   System.out.println();
						   try{ alteraJanejaGenerica(3, 0) } catch (Exception e) {}
						   try{ fecharJanelaAtual(3) } catch (Exception e) {}
						   try{ alteraJanejaGenerica(2, 0) } catch (Exception e) {}
						   try{ fecharJanelaAtual(2)} catch (Exception e) {}
						   try{ alteraJanejaGenerica(1, 0)} catch (Exception e) {}
						   try{ fecharJanelaAtual(1)} catch (Exception e) {}
						   
						   enviado = true;
						   alerta = "Nota Go: "+modelo+"-"+descricao+" foi gerada em Ficha de Gestão";
					   } else {
				          enviado = false
				          alerta="Tela Editor de Notas/Agenda não apresentada";
				          status = "Processado"
						  try{ fecharJanelaAtual(1) } catch (Exception e) {}
						  try{ alteraJanejaGenerica(1, 0)} catch (Exception e) {}
						  try{ fecharJanelaAtual(1)} catch (Exception e) {}
					      try{ alteraJanejaGenerica(0, 0)} catch (Exception e) {}
				        }
				   } else {
				     enviado = false
				     alerta="Tela Ficha Gestão não apresentada";
				     status = "Processado"
					 alteraJanejaGenerica(0, 0)
				  }
				} else {
				  enviado = false
				  alerta="Recibo não encontrado na lista"
				  status = "Processado"
				}
			} else {
				enviado = false
				alerta="Tela de Pesquisa não apresentada"
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
	 * @param registros
	 * @param IOF
	 * @return
	 */
	public static String texo(List<String> registros, String IOF){
		System.out.println()
		StringBuilder nstrbdr = new StringBuilder();
			nstrbdr.append("Origem: Projeto Sicoob SC/RS Digital\n")
			       .append("Motivo: Trata-se de CANCELAMENTO DEVIDO POR INADIMPLÊNCIA\n\n")
				   .append("Ações:\n\n")
				   .append("-  Abertura de nova devolução de IOF.\n\n")
				   .append("Dados do Segurado:\n\n")
				   .append("-  NOME (Segurado): "+registros[cellNames.get("NOME")]+"\n")
				   .append("-  CPF/CNPJ (Segurado): "+registros[cellNames.get("CPF ou CNPJ (alfanumérico)")]+"\n")
				   .append("-  Nº Apólice SUSEP: "+registros[cellNames.get("APÓLICE_SUSEP")]+"\n")
				   .append("-  Valor do IOF: "+IOF+"\n\n\n")
				   .append("Dados Bancários para Devolução:\n\n")
				   .append("-  Nome do titular da conta: "+retornaDadosBancarios("sicoobNomeTitular")+"\n")
				   .append("-  CNPJ: "+retornaDadosBancarios("sicoobCnpj")+"\n")
				   .append("-  Nº agência/cooperativa: "+retornaDadosBancarios("sicoobAgencia")+"\n")
				   .append("-  Nº Conta Corrente: "+retornaDadosBancarios("sicoobConta")+"-")
				   .append(retornaDadosBancarios("sicoobDig"));
				   System.out.println("\n\n"+nstrbdr.toString());
		return nstrbdr.toString();	
	}
	
	/**
	 * @author ALZ_SUP54955
	 * @param registros
	 * @return
	 */
	public static boolean enviaRecibo(List<String> registros){
		System.out.println();
		String parcela = registros[cellNames.get("Nº DA PARCELA")]+"/"+registros[cellNames.get("TOTAL DE PARCELAS")];
	    boolean nota = selecionaRecibo(parcela); 
		if(!nota){
		//	enviaRecibo(registros);
			nota = selecionaRecibo(parcela);
		}
		System.out.println("enviaRecibo ["+nota+"]")
		return nota;
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean verificaBotaoPagSeguinte(String tipo,String xpath , String tag, String attrib ){
		boolean f = false
		WebUI.delay(2)
		System.out.println("TIPO ["+tipo+"]")
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"] ["+listOfElements[i].getAttribute(attrib)+"]")
			if(listOfElements[2].getAttribute(attrib).contains(tipo.toString().trim())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getAttribute(attrib)+"]")
				f = true
				break
			}
		}
		System.out.println("TIPO ["+tipo+"] ["+f+"]")
		return f
	}
	
	/**
	 * @author ALZ_SUP54955
	 * @param valor
	 * @return
	 */
	public static boolean selecionaRecibo(String parcela){
		 System.out.println();
		 boolean recibo = false;
		 String xpath = '//*[@id="tableRecibos_tbody"]';
		 String tag = 'tr';
		 int j;
		 selecionaListaDeElementos(xpath,tag)
		 System.out.println("[SIZE  ["+listOfElements.size()+"]")
		 for(int i = 0; i <listOfElements.size(); i++){
			 System.out.println("["+i+"] ["+listOfElements[i].getText()+"] ["+parcela+"]")
			 j = j+1;
			 if(identificaParcelaDoRecibo(listOfElements[i].getText(), parcela)) {
				System.err.println("FOUND ["+i+"] Text ["+listOfElements[i].getText()+"]")
				recibo = true;
				listOfElements[i].click();
				break
			 }
		 }
		 
		 System.out.println("["+j +"] ["+listOfElements.size()+"]");
		 
		 if( recibo.equals(false) && j == listOfElements.size() ){
			 if( !verificaBotaoPagSeguinte("sectionButtonDisabled",'//*[@id="SectionRecibosContent"]/table[2]/tbody/tr/td[1]/table' , "div", "class" ) ){
				  selecionaItemLista("Pág.Seg",'//*[@id="SectionRecibosContent"]/table[2]/tbody/tr/td[1]/table' , "div" )
				  WebUI.delay(5);
				  recibo = false;
			  } else {
			      recibo = true;
			  }
		 } else if (recibo.equals(true) && j <  listOfElements.size()){
		   recibo = false;
		 }
		 
		 System.out.println("selecionaRecibo ["+recibo+"]")
		 return recibo;
	}
	
	/**
	 * @author ALZ_SUP54955
	 * @param txt
	 * @param stop
	 * @return
	 */
	public static boolean identificaParcelaDoRecibo(String txt, String stop){
		boolean p = false;
		StringBuilder sb = new StringBuilder();
		String [] stringList = txt.split(" ");
		for(int i = 0; i< stringList.size(); i++){
			System.out.println("["+i+"] ["+stringList[i]+"]")
			if(stringList[i].toString().equals(stop)){
				p = true;
				break;
			}
		}
		return p;
	}
	
	/**
	 * @author be54955
	 * @return
	 */
	public static boolean CancelamentoApolicesSicoobNParcelas(List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado = true;
		String msgAlert;
		String tela = true;
		boolean situacao;

		try{
			tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 5)
			System.out.println(tela)
			if(listaDeTelas(tela)){
				//Apolice
				preencheXpath('//*[@id="susep_SUSEP"]', registros[cellNames.get("APÓLICE_SUSEP")], 0);
				commandTAB('//*[@id="susep_SUSEP"]');
				WebUI.delay(1);
				//Pesquisar motivos
				selecionaItemLista("Pesquisar motivos",'//*[@id="sectionPaginaCompletaSectionBar"]/table' , "div" )
				//Motivo
				WebUI.delay(1);
				selecionaItemListaAtributo(registros[cellNames.get("MOTIVO DO CANCELAMENTO")],'//*[@id="div_motivo"]/tbody/tr/td/table/tbody/tr/td[2]/table', "option", "value" )
				//Data
				limpaCampo('//*[@id="fecha"]',0)
			//	preencheXpath('//*[@id="fecha"]', registros[cellNames.get("DATA INÍCIO VIGÊNCIA")], 0);
				
				preencheXpath('//*[@id="fecha"]',calculaData(Integer.parseInt(registros[cellNames.get("Nº DA PARCELA")]), Double.parseDouble(registros[cellNames.get("VALOR")]) , Integer.parseInt(registros[cellNames.get("TOTAL DE PARCELAS")]) , registros[cellNames.get("DATA INÍCIO VIGÊNCIA")]  ), 0);
				
				
				commandTAB('//*[@id="fecha"]');
				//Anlar
				selecionaItemLista("Anular",'//*[@id="sectionPaginaCompletaSectionBar"]/table' , "div" )
				alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+alerta+"]")
				if(!alertListBlocList(alerta)){
				
					//Valor Estorno
					limpaCampo('//*[@id="importeExtorno"]',0)
					preencheXpath('//*[@id="importeExtorno"]', registros[cellNames.get("VALOR")], 0);
					commandTAB('//*[@id="importeExtorno"]');
					//Editer
					selecionaItemListaAtributo('Edição','//*[@id="bankDataValue_divBnkCnt"]/table/tbody/tr[1]/td[2]/table', "img", "title" );
					
					String cnpj = recuperaValor('//*[@id="bankDataValue_vAccOwner"]', 'defaultValue', 0);
					String nomeTitular = recuperaValor('//*[@id="bankDataValue_IDNumResp_doc"]', 'defaultValue', 0);
					String conta = recuperaValor('//*[@id="bankDataValue_vAccNumber"]', 'defaultValue', 0);
					String dig = recuperaValor('//*[@id="bankDataValue_vDigCtrlAcc"]', 'defaultValue', 0);
					String banco = retornaItemSelecionadoLista('//*[@id="bankDataValue_vBank"]' , "option", "selected");
					
					if(!cnpj.equals(retornaDadosBancarios("sicoobNomeTitular"))){
						limpaCampo('//*[@id="bankDataValue_vAccOwner"]',0)
						preencheXpath('//*[@id="bankDataValue_vAccOwner"]', retornaDadosBancarios("sicoobCnpj"), 0);
					}
					
					if(!nomeTitular.equals(retornaDadosBancarios("sicoobCnpj"))){
						limpaCampo('//*[@id="bankDataValue_IDNumResp_doc"]',0)
						preencheXpath('//*[@id="bankDataValue_IDNumResp_doc"]', retornaDadosBancarios("sicoobNomeTitular"), 0);
					}
					
					if(!conta.equals(retornaDadosBancarios("sicoobConta"))){
						limpaCampo('//*[@id="bankDataValue_vAccNumber"]',0)
						preencheXpath('//*[@id="bankDataValue_vAccNumber"]', retornaDadosBancarios("sicoobConta"), 0);
					}
					
					if(!dig.equals(retornaDadosBancarios("sicoobDig"))){
						limpaCampo('//*[@id="bankDataValue_vDigCtrlAcc"]',0)
						preencheXpath('//*[@id="bankDataValue_vDigCtrlAcc"]', retornaDadosBancarios("sicoobDig"), 0);
					}
					
					System.out.println("["+retornaDadosBancarios("sicoobBanco")+"] ["+banco+"]")
					if(!(retornaDadosBancarios("sicoobBanco").toString().contains(banco))){
						//Banco / Agencia / DCO
						selecionaItemListaContains(retornaDadosBancarios("sicoobBanco"),'//*[@id="bankDataValue_vBank"]' , "option" )
					}
					
					//Li e confirmo os valores apresentados.
					clickXpath('//*[@id="chkConfirmacionValores"]', 0);
					//Pesquisar motivos
					selecionaItemLista("Confirmar cancelamento",'//*[@id="SectionCSectionBar"]/table' , "div" )
					alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
					System.out.println("Alerta ["+alerta+"]")
					if(!alertListBlocList(alerta)){
						enviado = true;
						alerta=recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0);
					} else {
					  enviado = false;
					  status = "Processado";
					  selecionaItemLista("Anulação",'//*[@id="tableOptMenu_"]' , "td" )
					}
				} else {
				   enviado = false;
				   status = "Processado";
				   selecionaItemLista("Anulação",'//*[@id="tableOptMenu_"]' , "td" )
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
			cellCheck = cellNames.get("Status");
			if(!validaDados(cellCheck)){
				String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Parcerias\\CancelamentoApolicesSicoob\\ConfiguracoesCancelamentoApolicesSicoob.txt";
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
			WebUI.delay(1);
	//		MenuMatriz.producaoSinistro();
	//		MenuMatriz.subMenuNome("a","Cancelamento")
	//		alteraJanejaGenerica(1, 5)
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
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba, String a){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoCancelamentoApolicesSicoob(aba,a)
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
     * @author ALZ_SUP54955
     * @param aba
     * @return
     */
	public static String retornaDadosBancarios(String aba){
		Map<String, String> dadosBancarios = new HashMap<String, String>()
		dadosBancarios.put("sicoobCnpj","41.953.108/0001-86");
		dadosBancarios.put("sicoobNomeTitular","SICOOB SC/RS DIGITAL CORRETORADE SEGUROS S/A");
		dadosBancarios.put("sicoobAgencia", "3039-2");
		dadosBancarios.put("sicoobConta", "113703");
		dadosBancarios.put("sicoobDig","4");
		dadosBancarios.put("sicoobBanco","SICOOB");
		return dadosBancarios.get(aba.toString());
	}
	
	public static int retornaParcela(int p){
		if(p>1){
			return p
		}
	}
	
	/**
	 * @author ALZ_SUP54955
	 * @param parcelasPagas
	 * @param valorAllianz
	 * @param parcala
	 * @param vencimento
	 * @return
	 */
	public static String calculaData(int parcelasPagas, double valorAllianz, int parcala, String vencimento  ){
		String dtVencimento;
		try {
			System.out.println("Data de entrada: ["+vencimento+"] NU PARC ["+parcelasPagas+"] VALOR ["+valorAllianz+"] PARCELA ["+parcala+"]");
			if(parcala>2){
		
		      int res = (parcelasPagas*valorAllianz-valorAllianz)/valorAllianz;
		      // 1. Define o formato correspondente ao texto da data
		      SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		      // Opcional: Garante que o Java rejeite datas inválidas como 32/10/2026
		      formatador.setLenient(false);
		      // 2. Converte a String para um objeto Date
		      Date dataConvertida = formatador.parse(vencimento);
		      // 3. Cria o Calendar e define o tempo com a Date obtida
		      Calendar calendario = Calendar.getInstance();
		      calendario.setTime(dataConvertida);
		      // Teste: Exibe o ano para confirmar que a conversão funcionou
		      // 1. Extrair o dia do mês
		      int dia = calendario.get(Calendar.DAY_OF_MONTH);
		      // 2. Extrair o mês (LEMBRETE: Soma-se 1 porque Janeiro começa em 0)
		      int mes = calendario.get(Calendar.MONTH) + 1;
		      // 3. Extrair o ano
		      int ano = calendario.get(Calendar.YEAR);
		      //System.out.println("Data: ["+ dia+"/"+mes+"/"+ano+"]");
		      LocalDate dataInicial;
		      System.out.println("Resultado do calculo interno: ["+res+"]");
		      // 2. Soma o número de meses desejado
		      //int mesesParaSomar = 5;
		      calendario.add(Calendar.MONTH, res);
		      // 1. Extrair o dia do mês
		      dia = calendario.get(Calendar.DAY_OF_MONTH);
		     // 2. Extrair o mês (LEMBRETE: Soma-se 1 porque Janeiro começa em 0)
		      mes = calendario.get(Calendar.MONTH) + 1;
		      // 3. Extrair o ano
		      ano = calendario.get(Calendar.YEAR);
	 	      System.out.println("Data calculada: ["+dia+"/"+mes+"/"+ano+"]");
		      // 2. Reduz 2 dias passando o valor negativo
		      calendario.add(Calendar.DAY_OF_MONTH, -2);
		      // 1. Extrair o dia do mês
		      dia = calendario.get(Calendar.DAY_OF_MONTH);
		      // 2. Extrair o mês (LEMBRETE: Soma-se 1 porque Janeiro começa em 0)
		      mes = calendario.get(Calendar.MONTH) + 1;
		      // 3. Extrair o ano
		      ano = calendario.get(Calendar.YEAR);
		      String dataFinal = dia+"/"+mes+"/"+ano;
		      System.out.println("Data Final: ["+dataFinal+"]");
		      System.out.println("Conversão realizada com sucesso!");
		
		   dtVencimento = dataFinal;
		
		} else {
		
		dtVencimento = vencimento;
		}

		} catch (ParseException e) {
			System.err.println("Erro: A string não está no formato correto (dd/MM/yyyy). " + e.getMessage());
		}
		return dtVencimento;
	}
	
	
}

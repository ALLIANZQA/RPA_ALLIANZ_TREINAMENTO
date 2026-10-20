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

public class CotacaoRenovacaoInternaFuncionalidades extends rpaCommands{
	
	
	private static TestData data
	private static TestData dataOperacao
	private static TestData dataOperacaoSinistros
	private static String testDataPath = 'Data Files/AutoFrota/CotacaoRenovacaoInternaData'
	private static String testDataSinistroPath = 'Data Files/AutoFrota/CotacaoRenovacaoInternaListaSinistrosData'
	private static String path
	private static String alerta
	private static String alertaItem
	private static int cellCheck = 40;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "CotacaoRenovacaoInterna.xlsx"
	private static int cellCheckAlert = 42;
	private static boolean enviado = true;
	private static String xpath;
	private static String tela;
	private static String operacao;
	private static tipoSeguro="Renovação Interna com sinistro";
	private static boolean erroTela = false;
		
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCotacaoRenovacaoInterna(String aba, String a){
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		initDataFiles();
		initDataFilesListaSinistros();
		fluxoCompletoCotacaoRenovacaoInterna(aba,a);
		reprocessaFalhas(aba,a);
		
		FileInput.atualizaLog(Instant.now().toString(), pathFile)
		List<String> times = rpaCommands.convertToInstant(pathFile);
		String dataInicio = times[0];
		String horaInicial =  times[1];
		timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
		   enviaEmailCotacaoRenovacaoInterna(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
		} catch (Exception e){
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
			fluxoCompletoCotacaoRenovacaoInterna(aba,a)
			reprocessaFalhas(aba,a)
		}
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoCotacaoRenovacaoInterna(String aba,String a){
		
		System.out.println();
		try{
			if(validaDados(cellCheck)){
			init(aba,a);
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					enviado = true;
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						System.out.println();
						regrasDeSinistros(registros[0], registros[2], registros[15], registros[5], registros[6]);
						System.out.println(regrasDeCoberturasDeGastosDefesa(registros[37], registros[27], registros[28]));
						
						if(renovacaointerna(registros,aba,a)) {
							if(!erroTela){
						 //     salvarEvidencias(aba, registros[1])
							}
						} else {
						   if(!erroTela){
							   salvarEvidenciasErro(aba, registros[1])
						   }
						}
						initDataFiles()
					}
				}
			} else {
				System.err.println('Não existem dados para processar')
			}
		} catch (Exception e){}
		WebUI.closeBrowser();
	}
	
	/**
	 * @author ALZ_SUP54955
	 * @param apolice
	 * @param item
	 * @param bonus
	 * @param dtInicio
	 * @param dtFim
	 * @return
	 */
	public static String regrasDeSinistros(String apolice, String item, String bonus, String dtInicio, String dtFim ){
		System.out.println("["+apolice+"] ["+item+"] ["+bonus+"] ["+dtInicio+"] ["+dtFim+"]" );
		String valorFinalBonus;
		//boolean b = existeSinistros(apolice,item);
		//System.out.println("Existe sinistro ["+b+"]")
		if(existeSinistros(apolice,item)){
			if(Integer.valueOf(bonus)>0){
		       int s = 0
		       for(int i = 1; i <= dataOperacaoSinistros.getRowNumbers(); i++) {
				   List<String>sinistros = new ArrayList<>();
				   listaSinistros(sinistros , i)
				   if(sinistros[1].toString().equals(apolice) && sinistros[2].toString().equals(item) && listaSinistros(sinistros[13].toString().trim()).equals(true) ){
				     s++;  
			       }
		       }
		       System.out.println("\nSINISTROS ["+s+"]");
			   System.out.println("\nBONUS ["+bonus+"]");
		       int novoBonus = Integer.valueOf(bonus) - s;
		       valorFinalBonus = Integer.toString(novoBonus);
		    } else {
		      valorFinalBonus = bonus;
		    }
		    System.out.println("\nLISTA DE SINISTROS - VALOR DE BONUS ATUALIZADO DE ["+bonus+"] PARA ["+valorFinalBonus+"]");
		} else {
		
		  if( ExtractDates.difDiasDatas(dtInicio, dtFim) > 334 ){
			  int valorBonus = Integer.parseInt(bonus);
			  System.out.println("Classe de bonus: ["+valorBonus+"]");
			  if(valorBonus==10){
				valorFinalBonus = bonus;
			  } else {
			    int novoBonus = valorBonus+1;
			    valorFinalBonus = String.valueOf(novoBonus);
			  }
		  } else {
		  valorFinalBonus = bonus;
		}
		  System.out.println("\nPRAZO MINIMO DE VIGÊNCIA - VALOR DE BONUS ATUALIZADO DE ["+bonus+"] PARA ["+valorFinalBonus+"]");
		}
		System.out.println("["+valorFinalBonus+"]")
		return  valorFinalBonus; 
	}

	
	public static String regrasDeCoberturasDeGastosDefesa(String c, String rcfdm, String rcfdc){
		String valor;
		int n = Integer.parseInt(c);
		int dm = Integer.parseInt(rcfdm);
		int dc = Integer.parseInt(rcfdc);
		if(n==0 && dm>0 && dc>0 ){
			valor = "10000";
		} else {
		  valor = c;
		}
		return valor;
	}
	/**
	 * @author ALZ_SUP54955
	 * @param sinistro
	 * @return
	 */
	public static boolean listaSinistros(String sinistro){
		boolean r = false
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add("Assistência")
		listOfAlerts.add("Vidros")
		System.out.println("Alert -["+sinistro+"]-")
		for(int i = 0; i < listOfAlerts.size(); i++){
			System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
			if(listOfAlerts[i].contains(sinistro.toString())){
				r = true
				break
			}
		}
		System.out.println("\nAlert ["+r+"]")
		return  r
	}	
	
	/**
	 * @author ALZ_SUP54955
	 * @param apolice
	 * @param item
	 * @return
	 */
	public static boolean existeSinistros(String apolice, String item){
		boolean  s = false;
		for(int i = 1; i <= dataOperacaoSinistros.getRowNumbers(); i++) {
				List<String>sinistros = new ArrayList<>();
				listaSinistros(sinistros , i)
				if(sinistros[1].toString().equals(apolice) && sinistros[2].toString().equals(item)){
					s = true;
					break;
				}
		}
		System.out.println("Existe sinistro ["+s+"]")
		return s;
	}
	
	/**
	 * @author Alz_sup54955
	 * @param aba
	 * @param apolice
	 * @return
	 */
	public static salvarEvidencias(String aba, String apolice){
		try{
			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				if (data.getValue(2, i).trim().equals(apolice)) {
					auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 39, aba);
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
	public static salvarEvidenciasApoliceItem(String aba, String apolice, String item ){
		System.out.println()
		try{
			initDataFiles()
			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				System.out.println("ITEM TO SAVE ["+j+"] ["+data.getValue(2, i).trim()+"] ["+apolice+"] ["+data.getValue(3, i)+"] ["+item+"]" )
				if (data.getValue(2, i).trim().equals(apolice) && data.getValue(3, i).equals(item)) {
					System.err.println("FOUND ["+j+"] ["+data.getValue(2, i)+"] ["+apolice+"] ["+data.getValue(3, i)+"] ["+item+"]" )
					auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 41, aba);
					auxiliares.WriteExcelFactory.updateSheetLine("", j, path, operacao, 40, aba);
					auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 39, aba);
		//			auxiliares.WriteExcelFactory.updateSheetLine("", j, path, bonus, 38, aba);
					break;
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
	public static salvarEvidenciasClasseDeBonus(int j, String aba, String  bonus ){
		System.out.println()
		try{
			auxiliares.WriteExcelFactory.updateSheetLine("", j, path, bonus, 38, aba);
		} catch (Exception e){}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param aba
	 * @param apolice
	 * @return
	 */
	public static salvarEvidenciasErro(String aba, String apolice){
		System.out.println(data.getRowNumbers())
		try{
			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				System.out.println("["+data.getValue(2, i).trim()+"] ["+apolice+"]")
				if (data.getValue(2, i).trim().equals(apolice)) {
					auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 39, aba);
					auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: "+alerta, 41, aba);
				}
			}
			
		} catch (Exception e){}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @param aba
	 * @param a
	 * @return
	 */
	public static boolean renovacaointerna(List<String> registros, String aba, String a){
	 System.out.println()
	 boolean b = false;
	 if(retornaExistenciaDeItem(registros[1])){
		WebUI.delay(5);
		System.out.println()
		MenuMatriz.producaoSinistro()
		MenuMatriz.subMenuNome('a','Produção')
		System.out.println()
		menuProducao(registros)
		dadosBasicos(registros)
		applicationPageDadosApolFrota(registros)
		enviado = itensDelFrota(registros,aba)
		System.out.println()
		if(enviado){
		   if(!erroTela){
		      fluxoCompletoCotacaoRenovacaoInternaOperacaoRecursivo(aba,a,registros[1])
		      try{
			      ajusteAlerta(registros[1])
		      }catch (Exception e){}
		 
		      try{
			     alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			     System.out.println("Alerta ["+alerta+"]")
			     if(alertListContinueList(alerta)){
				    String telaEsperada="COBERTURAS";
				    tela = StringFormatter.stopString(recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0),":");
				    identificaTela(tela, telaEsperada)
			     } else {
			       //Avançar
			        clicarEmElementoDaTela('//*[@id="o_6"]', 3);
			     }
		      } catch (Exception e){}
		   }
		}
		
		if(enviado){
		   System.out.println()
		   verificaItens(aba, a, registros[2])
		}
		
	 } else {
		System.out.println()
		fluxoCompletoCotacaoRenovacaoInternaOperacaoRecursivo(aba,a,registros[1])
	 }
	 
	 System.out.println(enviado)
	
	 alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]', 'innerText', 0)
	 System.out.println("Alerta ["+alerta+"]")
	 if(alerta.equals(" 01 Alerta")){ 
	   if(!enviado){
	      avancarTelaItens()
		  System.out.println()
		  tabelaDePrecos(registros);
		  System.out.println(processado())
		  b = processado()
		  return b
	   }
	 }
     return b;
	}
	
	/**
	 * @author ALZ_SUP54955
	 */
	public static void avancarTelaItens(){
		WebUI.delay(5)
		alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
		System.out.println("Alerta ["+alerta+"]")
		if(alertListContinueList(alerta)){
			//Avançar
			clicarEmElementoDaTela('//*[@id="o_6"]', 0);
			avancarTelaItens()
		}
	}
	
	
	
	/**
	 * @author Alz_sup54955
	 * @param aba
	 * @param a
	 * @param apolice
	 */
	public static void verificaItens(String aba, String a, String apolice){
		//	init(aba,a);
		fecharJanelaAtual(1)
		alteraJanejaGenerica(0, 0)
		fluxoCompletoCotacaoRenovacaoInternaOperacaoRecursivo(aba,a,apolice)
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoCotacaoRenovacaoInternaOperacaoRecursivo(String aba,String a, String apolice){
		fluxoCompletoCotacaoRenovacaoInternaOperacao(aba,a, apolice)
	}
	
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoCotacaoRenovacaoInternaOperacao(String aba,String a, String apolice){
		System.out.println()
		try{
			initDataFilesOperacao()
			if(!retornaExistenciaDeItemProposta(apolice)){
				String proposta = retornaProposta(apolice)
				System.out.println("Proposta: "+proposta)
				for(int i = 1; i <= dataOperacao.getRowNumbers(); i++) {
					int j =  i - 1
					enviado = true;
					if ( dataOperacao.getValue(2, i).equals(apolice) && dataOperacao.getValue(40, i).equals("Processar")) {
						System.out.println("\nApólice: "+dataOperacao.getValue(2, i)+"\nItem: "+dataOperacao.getValue(3, i)+"\nStatus: "+dataOperacao.getValue(37, i))
						List<String> registros = new ArrayList<>();
						listaOperacao( registros , i)
						inclusaoIens(registros,a,aba, proposta)
						break;
					}
				}
				fluxoCompletoCotacaoRenovacaoInternaOperacao( aba,a, apolice)
			}
		} catch (Exception e){}
	}
	
	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static  boolean retornaExistenciaDeItemProposta(String apolice){
		boolean b=false;
		try{
			int numProcessados;
			int numItens;
			for(int i = 1; i <= dataOperacao.getRowNumbers(); i++) {
					int j =  i - 1
					System.out.println(dataOperacao.getValue(37, i))
					if (dataOperacao.getValue(37, i).equals("Processado") && dataOperacao.getValue(2, i).equals(apolice)) {
						numProcessados = numProcessados + 1;
					}
			}
			
			for(int i = 1; i <= dataOperacao.getRowNumbers(); i++) {
				int j =  i - 1
				if (dataOperacao.getValue(2, i).equals(apolice)) {
					numItens = numItens + 1;
				}
		}
		System.out.println("OP - Apólice: ["+apolice+"]  Processados: ["+ numProcessados+"] Itens: ["+numItens+"]")
		if(numProcessados==numItens){
			b=true;
		}
		
		if(numProcessados==0){
			b=true;
		}
		
		} catch (Exception e){}
		return b;
	}
	
	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static  boolean retornaExistenciaDeItem(String apolice){
		boolean b=false;
		try{
			int numProcessados;
			int numItens;
			for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					if (data.getValue(40, i).equals("Processado") && data.getValue(2, i).equals(apolice)) {
						numProcessados = numProcessados + 1;
					}
			}
			
			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				if (data.getValue(2, i).equals(apolice)) {
					numItens = numItens + 1;
				}
		}
		System.out.println("Apólice: ["+apolice+"]  Processados: ["+ numProcessados+"] Itens: ["+numItens+"]")
		if(numProcessados==numItens){
			b=true;
		}
		
		if(numProcessados==0){
			b=true;
		}
		
		} catch (Exception e){}
		return b;
	}
	
	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static  String retornaProposta(String apolice){
		String p;
		try{
			List<String> listaProposta = new ArrayList<>();
			for(int i = 1; i <= dataOperacao.getRowNumbers(); i++) {
					int j =  i - 1
		//			System.out.println("Proposta: "+ dataOperacao.getValue(38, i) +" Apólice "+dataOperacao.getValue(2, i))
					if (dataOperacao.getValue(2, i).equals(apolice)) {
						listaProposta.add(dataOperacao.getValue(38, i));
					}
				}
			p = listaProposta.get(1);
			
			return p;
		} catch (Exception e){}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @param a
	 * @param aba
	 * @param proposta
	 * @return
	 */
	public static boolean inclusaoIens( List<String> registros,String a, String aba , String proposta ){
		WebUI.delay(5);
		System.out.println()
		MenuMatriz.producaoSinistro()
		MenuMatriz.subMenuNome('a','Produção')
		System.out.println()
		menuProducaoReprocessa()
		operacoesPendentes(proposta)
		operacoesDadosBasicos(registros)
		applicationPageDadosApolFrota(registros)
		itensDelFrotaOperacao(registros,aba)
		System.out.println(processado())
		return processado()
	}
	
	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static boolean menuProducaoReprocessa(){
		try{
			if(enviado){
				WebUI.delay(15);
				alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+alerta+"]")
				if(!alertListBlocList(alerta)){
					alteraJanejaGenerica(1, 5)
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
					if(listaDeTelas(tela)){
						 //Operatuva
						 selecionaItemLista("Operações Pendentes", '//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td', 'div');
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
				}
			}
			} catch (Exception e){
			 fecharJanelaAtual(1)
			 alteraJanejaGenerica(0, 0)
			 alerta = "Falha ao carregar a tela - Menu Produção";
			 enviado = false;
		   }
	}
	
	/**
	 * @author Alz_sup54955
	 * @param proposta
	 * @return
	 */
	public static boolean operacoesPendentes(String proposta){
		System.out.println()
		try{
			if(enviado){
				WebUI.delay(5);
				alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+alerta+"]")
				if(!alertListBlocList(alerta)){
					alteraJanejaGenerica(1, 5)
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
					if(listaDeTelas(tela)){
						//Nº Operação
						preencheXpath('//*[@id="numOperacion"]', proposta, 0);
						//Operatuva
						selecionaItemLista("Pesquisar", '//*[@id="filtroSectionBar"]/table/tbody/tr/td/table', 'div');
						WebUI.delay(2);
						if(!identificaOperacao()){
							//Pesquisar
							clicarEmElementoDaTela('//*[@alt="Editar"]', 0);
						}
					} else {
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  alerta = "Alerta: Falha Tela - Operações Pendentes - Não apresentada";
					  enviado = false;
					}
				}else {
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  enviado = false;
				}
			}
			} catch (Exception e){
			 fecharJanelaAtual(1)
			 alteraJanejaGenerica(0, 0)
			 alerta = "Alerta: Falha ao carregar a tela - Operações Pendentes";
			 enviado = false;
		   }
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @return
	 */
	public static boolean operacoesDadosBasicos(List<String> registros){
		try{
			if(enviado){
				WebUI.delay(10);
				alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+alerta+"]")
				if(!alertListBlocList(alerta)){
					System.out.println()
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
					 if(tela.contains("D. BÁSICOS")){ // TELA DADOS BASICOS
						 //Avançar
						 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
					 } else {
					   fecharJanelaAtual(1)
					   alteraJanejaGenerica(0, 0)
					   alerta = "Alerta: Falha Tela - D. BÁSICOS  - Não apresentada";
					   enviado = false;
					}
				} else {
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  enviado = false;
				}
			}
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  alerta = "Alerta: Falha ao carregar a tela - D. BÁSICOS";
		  enviado = false;
	  }
	}

	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static boolean identificaOperacao(){
		boolean b = false;
		WebUI.delay(2);
		alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
		System.out.println("Alerta ["+alerta+"]")
		if(alertListBlocList(alerta)){
			b=true;
		}
		return b;
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
						 preencheXpath(xpath, registros[3], 0);
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
				}
			}
			} catch (Exception e){
			 fecharJanelaAtual(1)
			 alteraJanejaGenerica(0, 0)
			 alerta = "Alerta: Falha ao carregar a tela - Menu Produção";
			 enviado = false;
		   }
	}
	
	/**
	 * @author Alz_sup54955
	 * @param cotacao
	 * @return
	 */
	public static boolean dadosBasicos(List<String> registros){
		try{
			if(enviado){
				WebUI.delay(10);
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
						 preencheXpath(xpath,  ExtractDates.formatDateToBR(registros[6]), 0);
						 //Forma de pagamento
						 selecionaItemLista("Boleto Bancário", '//*[@id="DatosGeneralesBean$primRecibo"]', 'option');
						 // Dia do Pagamento
						 xpath = '//*[@id="DatosGeneralesBean$diaPago"]';
						 limpaCampo(xpath, 0)
						 preencheXpath(xpath, "1", 0);
						 // CPF/CNPJ Tomador
						 xpath = '//*[@id="IntervinientesBean$nifTomador_doc"]';
						 limpaCampo(xpath, 0)
						 preencheXpath(xpath, registros[9], 0);
						 //Nome do Tomador
						 xpath = '//*[@id="IntervinientesBean$nomFigTomadorValue"]';
						 limpaCampo(xpath, 0)
						 preencheXpath(xpath, registros[8], 0);
						 WebUI.delay(5);
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
					   alerta = "Alerta: Falha Tela - D. BÁSICOS  - Não apresentada";
					   enviado = false;
					}
				} else {
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  enviado = false;
				}
			}
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  alerta = "Alerta: Falha ao carregar a tela - D. BÁSICOS";
		  enviado = false;
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
					selecionaItemLista("Renovação Interna sem sinistro", '//*[@id="DadosApolFrotaBean$tipoSeguro"]', 'option');
					try{
					  // No. da apólice
					  xpath = '//*[@id="DadosApolFrotaBean$nmrApolice"]';
					  limpaCampo(xpath, 2)
					  preencheXpath(xpath, registros[1], 2);
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
				  selecionaItemLista("Não se aplica / Não viaja", '//*[@id="QuestionarioAvaliacaoRiscoFrotaBean$pernoiteDosVeiculos"]', 'option');
				  //Qual a região de circulação mais frequente?
				  selecionaItemLista("Território nacional, com circulação na região de São Paulo e/ou Rio de Janeiro", '//*[@id="QuestionarioAvaliacaoRiscoFrotaBean$regiaoCirculacaoMaisFrequente"]', 'option');
				  //Gerenciamento de Riscos
				  xpath = '//*[@id="x_gerenciamentosDeRiscos"]';
				  try{
				  limpaCampo(xpath, 0)
				  } catch (Exception e){}
				  
				  preencheXpath(xpath, "Não Possui", 0);
				  //Declaração de aceite
				  clicarEmElementoDaTela('//*[@id="x_declaradoAceite"]', 0);
				  WebUI.delay(5);
				  //Avançar
				  clicarEmElementoDaTela('//*[@id="o_4"]', 0);
				  WebUI.delay(15);
				  System.out.println();
				  try{
					  alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
					  System.out.println("Alerta ["+alerta+"]")
					  if(alerta.equals(" Apólice possui Sinistro. Favor selecionar Renovação Interna Com Sinistro.")){
						  //Carga Transportada 3
						  selecionaItemLista(tipoSeguro, '//*[@id="DadosApolFrotaBean$tipoSeguro"]', 'option');
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
				  }
				} else {
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  enviado = false;
				}
			  }
			} catch (Exception e){
			  fecharJanelaAtual(1)
			  alteraJanejaGenerica(0, 0)
			  alerta = "Alerta: Falha ao carregar a tela - Application Page Dados Apol Frota";
			  enviado = false;
		   }
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @param aba
	 * @return
	 */
	public static boolean itensDelFrota(List<String> registros, String aba){
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
					  int k=1;
					  for(int i = 1; i <= data.getRowNumbers(); i++) {
						  int j =  i - 1
						  enviado = true;
					      System.out.println("["+data.getValue(cellCheck, i)+"] ["+ data.getValue(2, i).trim()+"] ["+registros[1].trim()+"]")
						  if(  data.getValue(cellCheck, i).equals('Processar') && data.getValue(2, i).trim().equals(registros[1].trim()) ) {
							 List<String> item = new ArrayList<>();
							 lista( item , i)
							 item[15]=regrasDeSinistros(item[0], item[2], item[15], item[5], item[6]);
							 item[42]="Não";
							 item[43]="1 - Próprios (PJ)";
							 item[44]="por Itens"
							 item[45]="N"
							 item[46]= regrasDeCoberturasDeGastosDefesa(item[37], item[27], item[28]);
							 System.out.println("INDEX ["+i+"] ITEM ["+k+"] SITUAÇÃO ["+item[11]+"] NOVA CLASSE DE BÔNUS ["+item[15]+"] COB GASTO DEFESA ["+item[46]+"]")
							 String it = item[2]
							 String ap = item[1]
							 salvarEvidenciasClasseDeBonus(j, aba,  item[15] );
							 //Apolice
							 if(situacao(item[12])){
								item[2]=k;
								System.out.println("ITEM ["+item[2]+"] ITEM ["+it+"] APOLICE ["+ap+"] ["+item[15]+"]")
								if(!erroTelaPantallaEspecificaPorDefinir()){
								   if(gravarItem(item)) {
								//	  alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
									  System.out.println("Alerta ["+alerta+"]")
									  if(!alertListBlocList(alerta)){
									    salvarEvidenciasApoliceItem(aba ,ap,it )
									  } else {
									    fecharJanelaAtual(1)
									    alteraJanejaGenerica(0, 0)
									    enviado = false;
									  }
								   } else {
									 alerta = "Alerta: "+item[12]
									 salvarEvidenciasApoliceItem(aba ,ap,it)
								   }
								   k = k+1;
								} else {
								  break;
								}
								 
								} else {
								  alerta = "Alerta: "+item[12]
								  salvarEvidenciasApoliceItem(aba ,ap,it )
							 }
						  }
					}
					System.out.println("erroTela ["+erroTela+"]")
					if(!erroTela){
					//Avançar
					 clicarEmElementoDaTela('//*[@id="o_4"]', 3);
					}
					
					System.out.println()
				  } else {
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 0)
					alerta = "Alerta: Falha Tela - Itens de Frota - Não apresentada";
					enviado = false;
				  }
				} else {
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
	 * @param registros
	 * @param aba
	 * @return
	 */
	public static boolean itensDelFrotaOperacao(List<String> registros, String aba){
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
					  System.out.println("Itens: "+dataOperacao.getRowNumbers())
					  int k;
					  for(int i = 1; i <= dataOperacao.getRowNumbers(); i++) {
						  int j =  i - 1
						  enviado = true;
						  if(  dataOperacao.getValue(cellCheck, i).equals('Processar') && dataOperacao.getValue(2, i).equals(registros[1]) ) {
							 List<String> item = new ArrayList<>();
							 listaOperacao( item , i)
							 item[15]=regrasDeSinistros(item[0], item[2], item[15], item[5], item[6]);
							 item[42]="Não";
							 item[43]="1 - Próprios (PJ)";
							 item[44]="por Itens"
							 item[45]="N"
							 item[46]= regrasDeCoberturasDeGastosDefesa(item[37], item[27], item[28]);
							 System.out.println("INDEX ["+i+"] ITEM ["+k+"] SITUAÇÃO ["+item[11]+"] ITEM ["+item[2]+"]")
							 String it = item[2]
							 String ap = item[1]
							 //Apolice
							 if(situacao(item[11])){
								selecionaListaDeElementos('//*[@id="itensBody-scroll"]','tr')
								int itens = listOfElements.size()
								System.out.println("ITENS ["+itens+"]")
								//k = item[2];
								item[2]=itens+1;
								System.out.println("ITEM ["+item[2]+"] ITEM ["+it+"] APOLICE ["+ap+"]")
								if(!erroTelaPantallaEspecificaPorDefinir()){
								   if(gravarItem(item)) {
									 salvarEvidenciasApoliceItem(aba ,ap,it )
								   } else {
									 alerta = "Alerta: "+item[11]
									 salvarEvidenciasApoliceItem(aba ,ap,it)
								   }
								   System.out.println("PROXIMO ITEM ["+item[2]+"] ITEM ["+it+"] APOLICE ["+ap+"]")
								} else {
								  break;
								}
								 
								} else {
								  alerta = "Alerta: "+item[12]
								  salvarEvidenciasApoliceItem(aba ,ap,it )
							 }
						  }
					 }
					 System.out.println(erroTela)
					 
					if(!erroTela){
					//Avançar
					 clicarEmElementoDaTela('//*[@id="o_4"]', 3);
					 try{
						 ajusteAlerta(registros[1])
					 }catch (Exception e){}
					 
					 try{
						alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
						System.out.println("Alerta ["+alerta+"]")
						if(alertListContinueList(alerta)){
							String telaEsperada="COBERTURAS";
							tela = StringFormatter.stopString(recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0),":");
							identificaTela(tela, telaEsperada)
						} else {
						//Avançar
						clicarEmElementoDaTela('//*[@id="o_6"]', 3);
						}
					} catch (Exception e){}
					}
					
					System.out.println()
				  } else {
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 0)
					alerta = "Alerta: Falha Tela - Itens de Frota - Não apresentada";
					enviado = false;
				  }
				} else {
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
	 * @param apolice
	 */
	public static void identificaAlerta(List<String> registros, String apolice){
		System.out.println("RECURSIVO...")
		alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
		System.out.println("Alerta ["+alerta+"]")
		while(alertListContinueList(alerta)){
			  ajusteAlerta(registros, apolice)
			  identificaAlerta( registros , apolice)
		}
		System.out.println("SAINDO DO RECURSIVO...")
	}
	
	/**
	 * @author Alz_sup54955
	 * @param apolice
	 */
	public static void ajusteAlerta(List<String> registros, String apolice){
		System.err.println()
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
			ajustaItem(registros, itensList.get(n), apolice)
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param numeroItem
	 * @param numeroApolice
	 * @return
	 */
	public static ajustaItem(List<String> registros, String numeroItem, String numeroApolice){
		System.out.println()
		for(int i = 1; i <= data.getRowNumbers(); i++) {
			int j =  i - 1
			enviado = true;
			if(  data.getValue(2, i).equals(numeroApolice) && data.getValue(3, i).equals(numeroItem) ) {
			   List<String> item = new ArrayList<>();
			   lista( item , i)
			   item[15]=regrasDeSinistros(item[0], item[2], item[15], item[5], item[6]);
			   item[42]="Não";
			   item[43]="1 - Próprios (PJ)";
			   item[44]="por Itens"
			   item[45]="N"
			   item[46]= regrasDeCoberturasDeGastosDefesa(item[37], item[27], item[28]);
			   System.out.println("INDEX ["+i+"] ITEM ["+item[2]+"] APOLICE ["+item[1]+"] CHASSI ["+item[13]+"]")
			   WebUI.delay(5);
			   //Valor de referência
			   int v = Integer.parseInt(item[17])
			   System.out.println("Valor de referência ["+v+"]")
			   //Filtro
			   selecionaItemLista(item[39], '//*[@id="x_filtro"]', 'option');
			   //Seleciona item
			   selecionaItemLista(item[2].toString(), '//*[@id="coberturasSectionContent"]/table/tbody/tr/td[1]/table/tbody/tr[3]/td/table/tbody/tr[1]/td/table/tbody/tr/td[2]/table', 'option');
			   //TIPO COBERTURA CASCO
			   int t = Integer.parseInt(item[23])
			   System.out.println(t)
			   if(t==0){
				   //Contrata Casco e(ou) Val. Determinado
				   selecionaItemLista("Não", '//*[@id="x_val_det"]', 'option');
				   //FIPE
				   selecionaItemLista("0", '//*[@id="x_perc_fipe"]', 'option');
				   //selecionaItemListaAtributo("O",'//*[@id="x_perc_fipe"]' , 'option', 'value' )
			   } else {
			   
			   //FIPE
			   regraFipe(item, v)
			   //FRANQUIA
			   selecionaItemLista(tipoFranquia(item[25]), '//*[@id="x_tipo_franquia"]', 'option');
			   }
			   
			   //RCF - Danos Materiais
			   selecionaItemLista(CurrencyMonetary.formataValorMonetario(item[26]).trim(), '//*[@id="x_danos_materiais"]', 'option');
			   //RCF - Danos Corporais
			   selecionaItemLista(CurrencyMonetary.formataValorMonetario(item[27]).trim(), '//*[@id="x_danos_corporais"]', 'option');
			   //RCF - Danos Morais e Estéticos
			   selecionaItemLista(CurrencyMonetary.formataValorMonetario(item[28]).trim(), '//*[@id="x_danos_morais"]', 'option');
			   
			   t = Integer.parseInt(item[29])
			   System.out.println("APP - Morte ["+t+"]")
			   if(t>0){
			   //APP - Morte
			   xpath = '//*[@id="x_app_morte"]';
			   preencheXpath(xpath,item[29].trim(), 0);
			   commandTAB(xpath)
			   }
			   
			   t = Integer.parseInt(item[30].replace("{", ""))
			   System.out.println("APP - Invalidez ["+t+"]")
			   if(t>0){
			   //APP - Invalidez
			   xpath = '//*[@id="x_app_invalidez"]';
			   preencheXpath(xpath,item[30].trim(), 0);
			   commandTAB(xpath)
			   }
			   
			   //Vidros, Lant., Faróis e Retr.
			   selecionaItemLista(validaValorVidroContratado(item[31]), '//*[@id="x_vidros"]', 'option');
			   //Acessórios gerais
			   xpath = '//*[@id="x_aces_gerais"]';
			   t = Integer.parseInt(item[32])
			   System.out.println(t)
			   if(t>0){ preencheXpath(xpath ,item[32], 0);
			   commandTAB(xpath)}
			   //Blindagem
			   xpath = '//*[@id="x_blindagem"]';
			   t = item[33].length()
			   System.out.println(t)
			   if(t>0){preencheXpath(xpath , item[33], 0);
			   commandTAB(xpath)}
			   //Assistência 24 horas
			   selecionaItemLista(validaValorContratadoAss24hs(item[34]), '//*[@id="x_assist24h"]', 'option');
			   //Carro Reserva
			   selecionaItemLista(validaValorContratadoCarroReserva(item[35]), '//*[@id="x_carro_reserva"]', 'option');
			   //Aplicar Configuração
			   clicarEmElementoDaTela('//*[@id="o_3"]', 0);
			   WebUI.delay(10);
			   String msg = pegaMSGAlerta();
			   System.out.println("["+msg+"]")
			   System.out.println()
			   okAlerta();
			   WebUI.delay(10);
			   //Avançar
			   clicarEmElementoDaTela('//*[@id="o_6"]', 3);
			   WebUI.delay(10);
			   System.out.println()
			   
			   erroTelaPantallaEspecificaPorDefinir()
			}
	   }
	}
	
	/**
	 * @author Alz_sup54955
	 */
	public static void identificaAlertaItem(){
		String attrib = "style";
		String tag ="img";
		String xpath='//*[@id="gridCoberturasItensContent"]/div[1]/table'
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
		//	System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"] ["+listOfElements[i].getAttribute(attrib)+"]")
			if(listOfElements[i].getAttribute(attrib).equals("background-color: transparent;".toString())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getAttribute(attrib)+"]")
				//Avançar
				clicarEmElementoDaTela('//*[@id="o_4"]', 10);
			}
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param item
	 * @return
	 */
	public static boolean gravarItem(List<String> item){
		System.out.println("ITEM ["+item[2]+"]")
		boolean itemUmSelecionado = false;
		String modelo
		try{
			int w
			int v
			alertaItem="";
			String vrVeiculosEspeciais = "1000";
			WebUI.delay(15);
			System.out.println()
			//Incluir item
			clicarEmElementoDaTela('//*[@id="incluirItem"]/table/tbody/tr[1]/td/table/tbody/tr/td[2]', 0);
			//Classe de Bonus 
			//regrasClasseBonus(item);
			selecionaItemLista(StringFormatter.removeZeroDoInicioDeNumeroString(item[15]), '//*[@id="x_classeBonus"]', 'option');
			//Chasse
			xpath = '//*[@id="chassi"]';
			limpaCampo(xpath, 0)
			preencheXpath(xpath, item[14], 0);
			//Lupa
			clicarEmElementoDaTela('//*[@id="consultarChassi"]', 0);
			System.out.println()
			
			
			//Clique na linha para escolher o veículo desejado...
			if(!listaDeModelos(item)){
			//Valor de referência
			v = Integer.parseInt(item[19])
			System.out.println("Valor de referência ["+v+"]")
			
			} else {
			  System.out.println()
			  modelo = selecionaPrimeiroItemListaDuploClick('//*[@id="tbodylistaVeiculos"]', 'tr')
			  alerta = "Versão do veículo não localizada. Foi selecionada a primeira opção da lista.: "+modelo;
			  itemUmSelecionado = true
			  enviado = true;
			}
			
			
			String VDX
			if (!item[23].toString().contains(",")) {
				w = Integer.parseInt(item[23])
			} else {
			  VDX = item[23]
			}
			
			System.out.println("Valor de referência COB Casco  VD ["+w+" "+VDX+"]")
			if(v==9995200 || v==999005200){
			   xpath = '//*[@id="x_valorReferencia"]';
			   if(w==0){
				  preencheXpath(xpath, "1000", 0);
				  commandTAB(xpath)
			   } else if(w>0){
				  if(VDX.toString().equals("null")){
					 String VD = Integer.toString(w)
					 preencheXpath(xpath, ajusteRefCobCascoVD(VD), 0);
					 commandTAB(xpath)
				  } else {
					preencheXpath(xpath, VDX, 0);
					commandTAB(xpath)
				  }
			  }
			}
			
			//Zero km
			selecionaItemLista(zeroKM(item[45]), '//*[@id="x_zeroKMFrota"]', 'option');
			//Estado
			selecionaItemLista(item[16], '//*[@id="x_estado"]', 'option');
			//Municipio
			selecionaItemLista(item[17], '//*[@id="x_municipio"]', 'option');
			//Propriedade
			String s = retornaItemSelecionadoLista('//*[@id="x_propriedadeVeiculo"]', 'option', "selected" )
			System.out.println("["+s+"] ["+item[43]+"]")
			if(s.contains("[SELECIONE...]")){
				selecionaItemLista(item[43], '//*[@id="x_propriedadeVeiculo"]', 'option');
			}
			//Gravar
			clicarEmElementoDaTela('//*[@id="x_btngravar"]', 0);
			WebUI.delay(20);
			//alerta = "Intem gravado";
			//Avançar
			clicarEmElementoDaTela('//*[@id="o_4"]', 0);
			WebUI.delay(15);
			System.out.println()
			try{
				alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+alerta+"]")
				if(alerta.contains(" Tipo de Seguro alterado para Renovação Interna Com Sinistro.")){
					//Avançar
					clicarEmElementoDaTela('//*[@id="o_4"]', 0);
				}
				
			} catch (Exception e){}
	
			if(alerta.contains(" Houve um erro.")){
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 0)
				System.out.println()
				fluxoCompletoCotacaoRenovacaoInternaOperacaoRecursivo('CotacaoRenovacaoInterna',"PRD",item[1])
				
			} else {
			
			  WebUI.delay(15);
			  System.out.println(item[2])
			  //Filtro
			  selecionaItemLista(item[44], '//*[@id="x_filtro"]', 'option');
			  //Seleciona item
			  selecionaItemLista(item[2].toString(), '//*[@id="coberturasSectionContent"]/table/tbody/tr/td[1]/table/tbody/tr[3]/td/table/tbody/tr[1]/td/table/tbody/tr/td[2]/table', 'option');
			
			  //TIPO COBERTURA CASCO
			  int t = Integer.parseInt(item[24])
		
			  System.out.println(t)
			  if(t==0){
				//Contrata Casco e(ou) Val. Determinado
				selecionaItemLista("Não", '//*[@id="x_val_det"]', 'option');
				//FIPE
				selecionaItemLista("0", '//*[@id="x_perc_fipe"]', 'option');
				//selecionaItemListaAtributo("O",'//*[@id="x_perc_fipe"]' , 'option', 'value' )
			  } else {
			    //FIPE
			    regraFipe(item, v)
			    //FRANQUIA
			    selecionaItemLista(tipoFranquia(item[26]), '//*[@id="x_tipo_franquia"]', 'option');
			  }
			
			  //RCF - Danos Materiais
			  selecionaItemLista(CurrencyMonetary.formataValorMonetario(item[27]).trim(), '//*[@id="x_danos_materiais"]', 'option');
			  //RCF - Danos Corporais
			  selecionaItemLista(CurrencyMonetary.formataValorMonetario(item[28]).trim(), '//*[@id="x_danos_corporais"]', 'option');
			  //Gastos com Defesa
			  selecionaItemListaAtributo(item[46], '//*[@id="x_gastos_defesa"]', 'option', "value");
			  //RCF - Danos Morais e Estéticos
			  selecionaItemLista(CurrencyMonetary.formataValorMonetario(item[29]).trim(), '//*[@id="x_danos_morais"]', 'option');
			
			  t = Integer.parseInt(item[30])
			  System.out.println("APP - Morte ["+t+"]")
			  if(t>0){
			    //APP - Morte
			    xpath = '//*[@id="x_app_morte"]';
			    preencheXpath(xpath,item[30].trim(), 0);
			    commandTAB(xpath)
			  }
			
			  t = Integer.parseInt(item[31].replace("{", ""))
			  System.out.println("APP - Invalidez ["+t+"]")
			  if(t>0){
			    //APP - Invalidez
			    xpath = '//*[@id="x_app_invalidez"]';
			    preencheXpath(xpath,item[31].trim(), 0);
			    commandTAB(xpath)
			  }
			
			  //Vidros, Lant., Faróis e Retr.
			  selecionaItemLista(validaValorVidroContratado(item[32]), '//*[@id="x_vidros"]', 'option');
			  //Acessórios gerais
			  xpath = '//*[@id="x_aces_gerais"]';
			  t = Integer.parseInt(item[33])
			  System.out.println(t)
			  if(t>0){ preencheXpath(xpath, item[33], 0);
			    commandTAB(xpath)}
			  //Blindagem
			  xpath = '//*[@id="x_blindagem"]';
			  t = item[34].length()
			  System.out.println(t)
			  if(t>0){preencheXpath(xpath, item[34], 0);
			  commandTAB(xpath)}
			  //Assistência 24 horas
			  selecionaItemLista(validaValorContratadoAss24hs(item[35]), '//*[@id="x_assist24h"]', 'option');
			  //Carro Reserva
			  selecionaItemLista(validaValorContratadoCarroReserva(item[36]), '//*[@id="x_carro_reserva"]', 'option');
			  //Aplicar Configuração
			  clicarEmElementoDaTela('//*[@id="o_3"]', 0);
			  WebUI.delay(15);
			  String msg = pegaMSGAlerta();
			  System.out.println("["+msg+"]")
			  System.out.println()
			  okAlerta();
			  WebUI.delay(25)
			  //Voltar
			  clicarEmElementoDaTela('//*[@id="o_5"]', 0);
			  System.out.println()
			  if(itemUmSelecionado){
			     alerta = "Versão do veículo não localizada. Foi selecionada a primeira opção da lista.: "+modelo;
			  } else {
			     alerta = "Cobertura gravada conforme escolhido"
				 enviado = true;
			  }
			  enviado = true;
			}
		} catch (Exception e){}
		return enviado;
	}
	
	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static boolean  erroTelaPantallaEspecificaPorDefinir(){
		boolean b = false;
		try{
			WebUI.delay(10);
		   tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0);
		   if(tela.contains("PANTALLA ESPECIFICA POR DEFINIR")){
			  fecharJanelaAtual(1)
			  alteraJanejaGenerica(0, 0)
			  erroTela = true;
			  b = true;
		   }
		} catch (Exception e){}
		return b;
	}
	
	/**
	 *
	 * @param item
	 * @param v
	 */
	public static void regraFipe(List<String> item, int v){
		System.out.println("Regra FIPE ["+v+"] ["+item[24]+"] ["+item[25]+"]")
		if( v==9995200 && item[24].equals("1")){
			selecionaItemLista("100", '//*[@id="x_perc_fipe"]', 'option');
		} else {
		  selecionaItemLista(formataFIPE(item[25]), '//*[@id="x_perc_fipe"]', 'option');
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param formulario
	 * @return
	 */
	public static tabelaDePrecos(List<String> registros){
		try{
		System.out.println()
		if(!enviado){
			WebUI.delay(10);
			alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			if(!alertListBlocList(alerta)){
				tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
				System.out.println()
				if(tela.contains("TABELA DE PREÇOS")){
					System.out.println()
					//COMISSÃO
					String c = recuperaValor( '//*[@id="ComisionamientoBean$porcComision"]', 'value', 0).replaceAll(",00", "")
					int comRec = Integer.parseInt(c);
					int comBas = Integer.parseInt(registros[13].toString().replaceAll(",00", ""));
					System.out.println("comRec ["+comRec+"] comBas ["+comBas+"]")
					if(comRec!=comBas){
					   xpath = '//*[@id="ComisionamientoBean$porcComision"]'
					   limpaCampo(xpath, 0)
					   preencheXpath(xpath,registros[13].trim(), 0);
					   //CALCULAR
					   clicarEmElementoDaTela('//*[@id="btnCalcular"]', 0);
					}
					//Malerta = tela.replaceAll("[TABELA DE PREÇOS :", "");
					operacao = StringFormatter.nextStop(tela, "AUTOMOVEL", 0).replaceAll("[\\[\\]]", "");
					alerta= "Itens adicionados"
					System.out.println("["+alerta+"] ["+operacao+"]")
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
			}
		}
		  System.out.println("Erro Tabela de preços ["+alerta+"] ["+enviado+"]")
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  enviado = false;
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param telaAtual
	 * @param telaEsperada
	 */
	public static void identificaTela(String telaAtual, String telaEsperada){
		System.out.println("RECURSIVO...")
		System.out.println("["+telaAtual+"] ["+ telaEsperada+"]")
		while(telaAtual.equals(telaEsperada)){
			//Avançar
			WebUI.delay(10);
			selecionaItemLista("Avançar", '//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table', 'div');
			identificaTela(telaAtual, telaEsperada )
		}
		System.out.println("SAINDO DO RECURSIVO...")
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
		selecionaListaDeElementos(xpath,tag)
		System.out.println()
		System.out.println("LIST SIZE "+listOfElements.size())
		String str;
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
		return str;
	}

	/**
	 * @author Alz_sup54955
	 * @param apolice
	 * @param item
	 * @param aba
	 * @return
	 */
	public static boolean salvarEvidenciaItem(String apolice, String item, String aba){
		try{
			System.out.println();
			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				enviado = true;
				System.out.println(data.getValue(cellCheck, i))
				if(data.getValue(cellCheck, i).equals('Processar')) {
					System.out.println()
					if (data.getValue(2, i).trim().equals(apolice) && data.getValue(3, i).equals(item)) {
						auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 38, aba);
						auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 40, aba);
						break
					}
				}
		   }
		} catch (Exception e){}
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
	public static enviaEmailCotacaoRenovacaoInterna(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
		System.out.println()
	 try{
		 if(!validaDados(cellCheck)){
			String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\AutoFrota\\CotacaoRenovacaoInterna\\ConfiguracoesCotacaoRenovacaoInterna.txt";
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
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static List<String> listaSinistros( List<String> registros , int v){
		for (int i = 1; i <= dataOperacaoSinistros.getColumnNumbers(); i++) {
			int d = i -1
			registros[d] = dataOperacaoSinistros.getValue(i, v).trim()
		}

		for (int j = 0; j < registros.size();j++ ){
			System.err.println("["+j+"] ["+registros[j]+"]")
		}
		return registros
	}
	
	
	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static List<String> listaOperacao( List<String> registros , int v){
		for (int i = 1; i <= dataOperacao.getColumnNumbers(); i++) {
			int d = i -1
			registros[d] = dataOperacao.getValue(i, v).trim()
		}

		for (int j = 0; j < registros.size();j++ ){
			System.err.println("["+j+"] ["+registros[j]+"]")
		}

		return registros
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
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFilesOperacao(){
		dataOperacao = findTestData(testDataPath)
		path = dataOperacao.sourceUrl
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFilesListaSinistros(){
		dataOperacaoSinistros = findTestData(testDataSinistroPath)
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
	 * @return
	 */
	public static boolean processado(){
		boolean b = false;
		if(!enviado){
			b = true;
		}
		return b;
	}
	
	/**
	 * @author Alz_sup54955
	 * @param tipo
	 * @return
	 */
	public static String zeroKM(String tipo){
		String xpath;
		Map<String, String> process = new HashMap<String, String>()
		process.put('S', 'Sim');
		process.put('N', 'Não');
		return process.get(tipo.toString());
	}
	
	/**
	 * @author Alz_sup54955
	 * @param tipo
	 * @return
	 */
	public static String formataFIPE(String tipo){
		String xpath;
		Map<String, String> process = new HashMap<String, String>()
		process.put('00000', '0');
		process.put('08000', '80');
		process.put('09000', '90');
		process.put('10000', '100');
		process.put('11000', '110');
		return process.get(tipo.toString());
	}
	
	/**
	 * @author Alz_sup54955
	 * @param v
	 * @return
	 */
	public static String ajusteRefCobCascoVD(String v){
		StringBuilder strb = new StringBuilder();
		String s;
		String[] l = v.split("")
		System.out.println("INVERT LIST\n")
		for(int i = l.size(); i--;  i<0   ){
			System.out.println("["+i+"] ["+l[i]+"]")
			if(i==5){
				strb.append(",")
			}
			strb.append(l[i])
		}
		String inverOrder = strb.toString()
		strb = new StringBuilder();
		l =inverOrder.split("")
		System.out.println("\nREORG LIST\n")
		for(int i = l.size(); i--;  i<0   ){
			System.out.println("["+i+"] ["+l[i]+"]")
			strb.append(l[i])
			
		}
		System.out.println("\n")
		s=strb.toString();
		System.out.println(s)
		return s;
	}
	
	/**
	 * @author Alz_sup54955
	 * @param alert
	 * @return
	 */
	public static boolean situacao(String alert){
		System.err.println("\nSITUAÇÃO ["+alert+"]")
		boolean r = false
		if(alert.length()>4){
			List<String> listOfAlerts = new ArrayList<String>()
			listOfAlerts.add("Emitida")
			listOfAlerts.add("REATIVAÇÃO")
			for(int i = 0; i < listOfAlerts.size(); i++){
				System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
				if(alert.contains(listOfAlerts[i])){
					System.err.println("FOUND SITUATION ["+i+"] ["+listOfAlerts[i]+"]")
					r = true
					break
				}
			}
		}
		System.out.println("\nAlert ["+r+"]")
		return  r
	}
	
	/**
	 * @author ALZ_SUP54955
	 * @param item
	 */
	public static void regrasClasseBonus(List<String> item){
		if( ExtractDates.difDiasDatas(item[5], item[6]) > 334 ){
			int bonus = Integer.parseInt(item[15]);
			System.out.println("Classe de bonus: ["+bonus+"]");
			if(bonus==10){
				selecionaItemLista(StringFormatter.removeZeroDoInicioDeNumeroString(item[15]), '//*[@id="x_classeBonus"]', 'option');
			} else {
			  int novoBonus = bonus+1;
			  selecionaItemLista(StringFormatter.removeZeroDoInicioDeNumeroString( String.valueOf(novoBonus) ), '//*[@id="x_classeBonus"]', 'option');
			}
		} else {
		  selecionaItemLista(StringFormatter.removeZeroDoInicioDeNumeroString(item[15]), '//*[@id="x_classeBonus"]', 'option');
		}
	}
	
	
}

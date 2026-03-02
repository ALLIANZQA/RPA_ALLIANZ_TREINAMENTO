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



public class EndossoClausulasFuncionalidades  extends rpaCommands{
	
	
	
	private static TestData data
	private static TestData dataOperacao
	private static String testDataPath = 'Data Files/Agro/EndossoClausulas/EndossoClausulasData'
	private static String path
	private static String alerta
	private static String retorno
	private static int cellCheck = 3;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "EndossoClausulas.xlsx"
	private static int cellCheckAlert = 4;
	private static boolean enviado = true;
	private static String xpath;
	private static String tela;
	private static boolean erroTela = false;
	private static String msgAlerta
	private static String status
	private static String xPath
	private static String textoComplementar
	private static boolean propostaBloqueada=false
	
	private static String emissao
	private static String desbloq
	private static String ramo
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoEndossoClausulasFuncionalidades(String aba, String a){
		System.out.println()
		String dataInicio = ExtractDates.dataAtual();
		String horaInicial =  ExtractDates.horaAtualizada();
		start = Instant.now();
		initDataFiles()
		fluxoCompletoEndossoClausulasFuncionalidades(aba,a)
		reprocessaFalhas(aba,a)
		end =  Instant.now();
		timeLapnse = Duration.between(start, end);
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
	public static fluxoCompletoEndossoClausulasFuncionalidades(aba,a){
		System.out.println()
		try{
			if(validaDados(cellCheck)){
				init(aba,a);
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					enviado = true;
					alerta="";
					status = "Processado"
					emissao="";
					desbloq="";
					propostaBloqueada=true;
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						registros[4] = "Endosso";
						registros[5] = "1";
						registros[6] = registros[0]+"@gmail.com";
						if(endosso(registros,aba,a, j)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado" , 2, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 3, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 2, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: "+alerta, 3, aba);
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
	 * @return
	 */
	public static boolean endosso(List<String> registros, String aba, String a, int j){
		boolean e = false; 
		System.out.println()
		alteraEndosso(registros, aba, j)
		if(propostaBloqueada){
			enviado = true;
		}
		if(enviado){
		   listaDeBloqueios(registros, aba, j)
		}
		e = enviado
		
	//	if(enviado){
	//		alerta = "OK"
	//	}else{
	//	    alerta = "Alerta: NOK"
	//	}
		
		switch(enviado){
			case true:
				alerta = "OK"
				break;
			
			case false:
				alerta = "NOK"
				break;
		}
		System.out.println("["+e+"] ["+alerta+"]")
		return e;
	}
	
	/**
	 * @author ALZ_SUP54955
	 * @param registros
	 * @return
	 */
	public static alteraEndosso(List<String> registros, String aba, int j){
		try{
		System.out.println()
		MenuMatriz.producaoSinistro()
		MenuMatriz.subMenuNome('a','Produção')
		menuProducao( registros)
		dadosDaApolice()
		if(ramos()){
		   dirIten(registros)
		   dadosDaRisco()
		   coberturas()
		   dirItens()
		}
		tabelaDePrecos()
		dadosLocalizador(registros)
		dadosEmissao()
		dadosCliente()
		dadosComuns()
		clausulas(registros)
		confirmacaoEmissao()
		} catch (Exception e){
		  enviado=false;
		  propostaBloqueada=false;
		}
//		if(enviado){
		  try{
	    	auxiliares.WriteExcelFactory.updateSheetLine("", j, path, emissao, 4, aba);
		  } catch (Exception e){}
//		}
	}
	
	/**
	 * @author ALZ_SUP54955
	 * @param registros
	 * @return
	 */
	public static listaDeBloqueios(List<String> registros, String aba, int j){
		try{
		producao()
		listaBloqueios(registros)
		} catch (Exception e){
		  enviado=false;
		  propostaBloqueada=false;
		}
//		if(enviado){
		try{
			auxiliares.WriteExcelFactory.updateSheetLine("", j, path, desbloq, 5, aba);
		} catch (Exception e){}
//		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @return
	 */
	public static boolean menuProducao(List<String> registros){
		try{
			if(enviado){
		//		WebUI.delay(10);
				alteraJanejaGenerica(1, 10)
				emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+emissao+"]")
				if(!alertListBlocList(emissao)){
					System.out.println()
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
					 if(listaDeTelas(tela)){
						 //Outras Operações
						 clickXpath('//*[@id="tipoDeSeleccion2"]', 0);
						 WebUI.delay(2)
						 //Apolice
						 xPath = '//*[@id="polSusep_SUSEP"]';
						 preencheXpath( xPath, registros[0], 0)
						 commandTAB(xPath)
						 //Selecionar
						 clickXpath('//*[@id="btnSeleccionar"]', 0);
						 WebUI.delay(5)
						 String msg = pegaMSGAlerta()
						 System.out.println("MSG "+msg)
						 if(!msg.equals("A proposta está bloqueada.")){
							 try{
							 System.out.println("2")
							 fechaAlerta();
						     }catch (Exception e){}
						     try{
							 //Selecionar
							 clickXpath('//*[@id="btnSeleccionar"]', 0);
							 WebUI.delay(2)
						     } catch (Exception e){}
							 WebUI.delay(5)
							 msg = pegaMSGAlerta()
							 System.out.println("MSG "+msg)
							 if(!msg.equals("A proposta está bloqueada.")){
						     //Operativas
						     selecionaItemLista('Endosso de Apólice de Multisituação','//*[@id="codOperativa"]' , "option" )
						     //Avançar
						     clicarEmElementoDaTela('//*[@id="btnSeleccionOperativa"]', 0);
						     WebUI.delay(2)
						     try{
							   System.out.println("3")
							   fechaAlerta();
						     }catch (Exception e){}
						     //Motivo
						     preencheXpath('//*[@id="motivo"]', registros[4], 3)
						     //Avançar
						     clicarEmElementoDaTela('//*[@id="btnModificacion"]', 0);
							 } else {
							 emissao = msg
							 propostaBloqueada = true;
							 enviado = false;
							 fechaAlerta();
							 fecharJanelaAtual(1)
							 alteraJanejaGenerica(0, 0)
						   }
						 } else {
						   emissao = msg
						   propostaBloqueada = true;
						   enviado = false;
						   fechaAlerta();
						   fecharJanelaAtual(1)
						   alteraJanejaGenerica(0, 0)
						 } 
					 } else {
					   fecharJanelaAtual(1)
					   alteraJanejaGenerica(0, 0)
					   emissao = "Falha: Tela - Menu Produção - Não apresentada";
					   enviado = false;
					   propostaBloqueada=false;
					}
				} else {
				  alertaCampoNecessarioEmissao()
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  emissao = false;
				  propostaBloqueada=false;
				}
			}
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  emissao = "Falha ao carregar a tela - Menu Produção";
		  enviado = false;
		  propostaBloqueada=false;
	  }
	}

	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @return
	 */
	public static boolean dadosDaApolice(){
		try{
			if(enviado){
				WebUI.delay(15);
				//alteraJanejaGenerica(1, 15)
				emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+emissao+"]")
				if(!alertListBlocList(emissao)){
					System.out.println()
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,16)
					System.out.println(tela)
					ramo = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(19,23)
					System.out.println("RAMO ["+ramo+"]")
					System.out.println()
					 if(listaDeTelas(tela)){
						 //Avançar
						 clicarEmElementoDaTela('//*[@id="o_3"]', 0);
						 WebUI.delay(2)
						 emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
						 System.out.println("Alerta ["+emissao+"]")
						 if(alertListContinueList(emissao)){
							  //Avançar
							  clicarEmElementoDaTela('//*[@id="o_3"]', 0);
						 }
					 } else {
					   fecharJanelaAtual(1)
					   alteraJanejaGenerica(0, 0)
					   emissao = "Falha: Tela - Dados Da Apolice - Não apresentada";
					   enviado = false;
					   propostaBloqueada=false;
					}
				} else {
				  WebUI.delay(2)
				  alertaCampoNecessarioEmissao()
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  enviado = false;
				  propostaBloqueada=false;
				}
			}
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  emissao = "Falha ao carregar a tela - Dados Da Apolice";
		  enviado = false;
		  propostaBloqueada=false;
	  }
	}

	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @return
	 */
	public static boolean dirIten(List<String> registros){
		try{
			if(enviado){
				WebUI.delay(15);
		//		alteraJanejaGenerica(1, 10)
				emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+emissao+"]")
				if(!alertListBlocList(emissao)){
					System.out.println()
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,9)
					System.out.println(tela)
					 if(listaDeTelas(tela)){
						 //Item
						 preencheXpath( '//*[@id="OperacionItemBean$item"]', registros[5], 0)
						 //Pesquisar
						 clicarEmElementoDaTela('//*[@id="OperacionItemBean$btnBuscar"]', 0);
						 //Editar
						 //selecionaItemLista('Editar','//*[@id="blListadoDirectoraContent"]/table/tbody/tr[2]/td[2]/table/tbody' , "img" )
						 selecionaItemListaAtributo('Editar','//*[@id="blListadoDirectoraContent"]/table/tbody/tr[2]/td[2]/table/tbody' , "img", 'title' )
						 //Avançar
						 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 2);
						 //Aceitar
						 clicarEmElementoDaTela('//*[@id="ParamItemBean$btnAceptar"]', 2);
					 } else {
					   fecharJanelaAtual(1)
					   alteraJanejaGenerica(0, 0)
					   emissao = "Falha: Tela - dir Itens - Não apresentada";
					   enviado = false;
					   propostaBloqueada=false;
					}
				} else {
				  alertaCampoNecessarioEmissao()
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  enviado = false;
				  propostaBloqueada=false;
				}
			}
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  emissao = "Falha ao carregar a tela - dir Itens";
		  enviado = false;
		  propostaBloqueada=false;
	  }
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @return
	 */
	public static boolean dirItens(){
		try{
			if(enviado){
				WebUI.delay(20);
				//alteraJanejaGenerica(1, 20)
				emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+emissao+"]")
				if(!alertListBlocList(emissao)){
					System.out.println()
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,9)
					System.out.println(tela)
					 if(listaDeTelas(tela)){
						 //Aceitar
						 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
					 } else {
					   fecharJanelaAtual(1)
					   alteraJanejaGenerica(0, 0)
					   emissao = "Falha: Tela - dir Itens - Não apresentada";
					   enviado = false;
					   propostaBloqueada=false;
					}
				} else {
				  alertaCampoNecessarioEmissao()
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  enviado = false;
				  propostaBloqueada=false;
				}
			}
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  emissao = "Falha ao carregar a tela - dir Itens";
		  enviado = false;
		  propostaBloqueada=false;
	  }
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @return
	 */
	public static boolean dadosDaRisco(){
		try{
			if(enviado){
				WebUI.delay(15);
			//	alteraJanejaGenerica(1, 15)
				emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+emissao+"]")
				if(!alertListBlocList(emissao)){
					System.out.println()
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,14)
					System.out.println(tela)
					 if(listaDeTelas(tela)){
						 //Avançar
						 clicarEmElementoDaTela('//*[@id="o_3"]', 0);
						 WebUI.delay(2)
						 emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
						 System.out.println("Alerta ["+emissao+"]")
						 if(alertListContinueList(emissao)){
							  //Avançar
							  clicarEmElementoDaTela('//*[@id="o_3"]', 0);
						 }
					 } else {
					   fecharJanelaAtual(1)
					   alteraJanejaGenerica(0, 0)
					   emissao = "Falha: Tela - Dados Da Risco - Não apresentada";
					   enviado = false;
					   propostaBloqueada=false;
					}
				} else {
				  alertaCampoNecessarioEmissao()
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  enviado = false;
				  propostaBloqueada=false;
				}
			}
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  emissao = "Falha ao carregar a tela - Dados Da Apolice";
		  enviado = false;
		  propostaBloqueada=false;
	  }
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @return
	 */
	public static boolean coberturas(){
		try{
			if(enviado){
				WebUI.delay(15);
				//alteraJanejaGenerica(1, 15)
				emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+emissao+"]")
				if(!alertListBlocList(emissao)){
					System.out.println()
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,10)
					System.out.println(tela)
					 if(listaDeTelas(tela)){
						 //Continuar
						 clicarEmElementoDaTela('//*[@id="btnContinuar"]', 1);
						 //Calcular
						 clicarEmElementoDaTela('//*[@id="btnCalcular"]', 1);
						 //Avançar
						 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 1);
					 } else {
					   fecharJanelaAtual(1)
					   alteraJanejaGenerica(0, 0)
					   emissao = "Falha: Tela - Coberturas - Não apresentada";
					   enviado = false;
					   propostaBloqueada=false;
					}
				} else {
				  alertaCampoNecessarioEmissao()
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  enviado = false;
				  propostaBloqueada=false;
				}
			}
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  emissao = "Falha ao carregar a tela - Coberturas";
		  enviado = false;
		  propostaBloqueada=false;
	  }
	}

	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @return
	 */
	public static boolean tabelaDePrecos(){
		try{
			if(enviado){
				WebUI.delay(15);
				//alteraJanejaGenerica(1, 15)
				emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+emissao+"]")
				if(!alertListBlocList(emissao)){
					System.out.println()
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,16)
					System.out.println(tela)
					 if(listaDeTelas(tela)){
						 //Emitir
						 clicarEmElementoDaTela('//*[@id="ParrillaBean$btnEmitir"]', 0);
						 try{
							 fechaAlerta();
						 }catch (Exception e){}
					 } else {
					   fecharJanelaAtual(1)
					   alteraJanejaGenerica(0, 0)
					   emissao = "Falha: Tela - Tabela de Preços - Não apresentada";
					   enviado = false;
					   propostaBloqueada=false;
					}
				} else {
				  alertaCampoNecessarioEmissao()
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  enviado = false;
				  propostaBloqueada=false;
				}
			}
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  emissao = "Falha ao carregar a tela - Tabela de Preços";
		  enviado = false;
	  }
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @return
	 */
	public static boolean dadosLocalizador(List<String> registros){
		try{
			if(enviado){
				WebUI.delay(15);
				//alteraJanejaGenerica(1, 15)
				emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
				System.out.println("Alerta ["+emissao+"]")
				if(!alertListBlocList(emissao)){
					System.out.println()
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,17)
					System.out.println(tela)
					 if(listaDeTelas(tela)){
						 
						 String latitudeHora = recuperaValor('//*[@id="DadosRiscoLocBean$latitude"]', 'innerText', 0)
						 if(latitudeHora.length()==0){
							  preencheXpath( '//*[@id="DadosRiscoLocBean$latitude"]', "01", 0)
						 }
						 String latitudeMin = recuperaValor('//*[@id="DadosRiscoLocBean$latitudeMinuto"]', 'innerText', 0)
						 if(latitudeMin.length()==0){
							   preencheXpath( '//*[@id="DadosRiscoLocBean$latitudeMinuto"]', "01", 0)
						 }
						 String latitudeSeg = recuperaValor('//*[@id="DadosRiscoLocBean$latitudeSegundo"]', 'innerText', 0)
						 if(latitudeSeg.length()==0){
							 preencheXpath( '//*[@id="DadosRiscoLocBean$latitudeSegundo"]', "01", 0)
						 }
						 
						 
						 String longitudeHora = recuperaValor('//*[@id="DadosRiscoLocBean$longitude"]', 'innerText', 0)
						 if(longitudeHora.length()==0){
							   preencheXpath( '//*[@id="DadosRiscoLocBean$longitude"]', "01", 0)
						 }
						 String longitudeMin = recuperaValor('//*[@id="DadosRiscoLocBean$longitudeMinuto"]', 'innerText', 0)
						 if(longitudeMin.length()==0){
							   preencheXpath( '//*[@id="DadosRiscoLocBean$longitudeMinuto"]', "01", 0)
						 }
						 String longitudeSeg = recuperaValor('//*[@id="DadosRiscoLocBean$longitudeSegundo"]', 'innerText', 0)
						 if(longitudeSeg.length()==0){
							  preencheXpath( '//*[@id="DadosRiscoLocBean$longitudeSegundo"]', "01", 0)
						 }
						 
						 //E-MAIL SEGURADO 
						 try{
							 limpaCampo('//*[@id="DadosRiscoLocBean$emailSegurado"]', 0)
						 } catch (Exception e){}
						 preencheXpath( '//*[@id="DadosRiscoLocBean$emailSegurado"]', registros[6], 0)
						 //Aceitar
						 clicarEmElementoDaTela('//*[@id="o_5"]', 0);
						 try{
							 fechaAlerta();
						 }catch (Exception e){}
					 } else {
					   fecharJanelaAtual(1)
					   alteraJanejaGenerica(0, 0)
					   emissao = "Falha: Tela - Dados Localizados - Não apresentada";
					   enviado = false;
					   propostaBloqueada=false;
					}
				} else {
				  alertaCampoNecessarioEmissao()
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  enviado = false;
				  propostaBloqueada=false;
				}
			}
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  emissao = "Falha ao carregar a tela - Dados Localizados";
		  enviado = false;
		  propostaBloqueada=false;
	  }
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @return
	 */
	public static boolean dadosEmissao(){
		try{
			if(enviado){
				WebUI.delay(15);
				//alteraJanejaGenerica(1, 15)
				emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("dadosEmissao - Alerta ["+emissao+"]")
				if(!alertListBlocList(emissao)){
					System.out.println()
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,10)
					System.out.println(tela)
					 if(listaDeTelas(tela)){
						 //Aceitar
						 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
						 emissao = recuperaValor( '//*[@id="dataErrorSection"]/table/tbody/tr/td[1]/b', 'innerText', 5)
						 System.out.println(tela)
						 if(emissao.equals("Dia do Pagamento")){
							 //Dia do Pagamento
							 preencheXpath( '//*[@id="DatosBancariosBean$diaPago"]', "01", 0)
							 //Aceitar
							 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
						 }
						 try{
							 fechaAlerta();
						 }catch (Exception e){}
					 } else {
					   fecharJanelaAtual(1)
					   alteraJanejaGenerica(0, 0)
					   emissao = "Falha: Tela - Dados Emissão - Não apresentada";
					   enviado = false;
					   propostaBloqueada=false;
					}
				} else {
				  alertaCampoNecessarioEmissao()
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  enviado = false;
				  propostaBloqueada=false;
				}
			}
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  emissao = "Falha ao carregar a tela - Dados Emissão";
		  enviado = false;
		  propostaBloqueada=false;
	  }
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @return
	 */
	public static boolean dadosCliente(){
		try{
			if(enviado){
				WebUI.delay(15);
				//alteraJanejaGenerica(1, 15)
				emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+emissao+"]")
				if(!alertListBlocList(emissao)){
					System.out.println()
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,10)
					System.out.println(tela)
					 if(listaDeTelas(tela)){
						 String n = recuperaValor( '//*[@id="DomicilioClienteBeanTomador$numero"]', 'innerText', 0)
						 if(n.length()==0){
							 preencheXpath('//*[@id="DomicilioClienteBeanTomador$numero"]', "0", 0)
						 }
						 //Aceitar
						 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
						 try{
							 fechaAlerta();
						 }catch (Exception e){}
						 tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2).subSequence(0,10)
					     System.out.println(tela)
					     if(tela.equals("D. CLIENTE")){
						  try{
							//Aceitar
							 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
							} catch (Exception e){}
						 }
						 
						 ajusteTelefone()
					 } else {
					   fecharJanelaAtual(1)
					   alteraJanejaGenerica(0, 0)
					   emissao = "Falha: Tela - Dados Cliente - Não apresentada";
					   enviado = false;
					   propostaBloqueada=false;
					}
				} else {
				  alertaCampoNecessarioEmissao()
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  enviado = false;
				  propostaBloqueada=false;
				}
			}
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  emissao = "Falha ao carregar a tela - Dados Cliente";
		  enviado = false;
		  propostaBloqueada=false;
	  }
	}
	
	public static  ajusteTelefone(){
		System.out.println()
		if(preencheTelefone()){
		try{
			//Aceitar
			 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
			 WebUI.delay(2);
			 try{
				 fechaAlerta();
			 }catch (Exception e){}
			 
			} catch (Exception e){}
			WebUI.delay(2);
			ajusteTelefone()
		}
	}
	
	
	public static boolean preencheTelefone(){
		System.out.println()
		String xpath
		boolean b;
		String msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
		
		switch(msg){
			case " Telefone 1 não válido":
			xpath = '//*[@id="DomicilioClienteBeanTomador$telefone"]';
			limpaCampo(xpath, 0)
			preencheXpath(xpath, "11999999999", 0)
			b = true;
			break;
			
			case " Telefone 2 não válido":
			xpath = '//*[@id="DomicilioClienteBeanTomador$telefone2"]';
			limpaCampo(xpath, 0)
			preencheXpath(xpath, "11999999999", 0)
			b = true;
			break;
			
			case " Celular não válido":
			xpath = '//*[@id="DomicilioClienteBeanTomador$celular"]';
			limpaCampo(xpath, 0)
			preencheXpath(xpath, "11999999999", 0)
			b = true;
			break;
		
			default:
			b = false;
		}
		return b;
	}
	
	
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @return
	 */
	public static boolean dadosComuns(){
		try{
			if(enviado){
				WebUI.delay(15);
				//alteraJanejaGenerica(1, 15)
				emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+emissao+"]")
				if(!alertListBlocList(emissao)){
					System.out.println()
					tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,9)
					System.out.println(tela)
					 if(listaDeTelas(tela)){
						 //Aceitar
						 clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
						 try{
							 fechaAlerta();
						 }catch (Exception e){}
					 } else {
					   fecharJanelaAtual(1)
					   alteraJanejaGenerica(0, 0)
					   emissao = "Falha: Tela - Dados Comuns - Não apresentada";
					   enviado = false;
					   propostaBloqueada=false;
					}
				} else {
				  alertaCampoNecessarioEmissao()
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  enviado = false;
				  propostaBloqueada=false;
				}
			}
		} catch (Exception e){
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 0)
		  emissao = "Falha ao carregar a tela - Dados Comuns";
		  enviado = false;
		  propostaBloqueada=false;
	  }
	}
	
	/**
	* @author Alz_sup54955
	* @param registros
	* @return
	*/
   public static boolean clausulas(List<String> registros){
	   try{
		   if(enviado){
	   		   WebUI.delay(15);
			 //alteraJanejaGenerica(1, 0)
			   emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			   System.out.println("Alerta ["+emissao+"]")
			   if(!alertListBlocList(emissao)){
				   System.out.println()
				   tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,9)
				   System.out.println(tela)
					if(listaDeTelas(tela)){
						//Editar
						if(selecionaItemListaAtributo('Editar Texto','//*[@id="blClausulasSeleccionadasContent"]/table/tbody' , "img", 'title' )){
						  WebUI.delay(2);
						  xpath='//*[@id="TextoClausulaBean$textoClausulaLibre"]';
						  limpaCampo(xpath, 0)
						  preencheXpath(xpath,registros[1], 0)
						  //Aceitar
						  clicarEmElementoDaTela('//*[@id="btnAceptar"]', 3);
						  //Avançar
						  clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
						  WebUI.delay(2);
						  try{
							fechaAlerta();
						  }catch (Exception e){}
						   textoComplementar = "Texto complementar alterado.";
						} else {
						  System.out.println()
						  fecharJanelaAtual(1)
						  alteraJanejaGenerica(0, 0)
						  emissao = "Falha: Texto não editado.";
						  enviado = false;
						  propostaBloqueada=false;
						}
					} else {
					  alertaCampoNecessarioEmissao()
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  emissao = "Falha: Tela - Clausulas - Não apresentada";
					  enviado = false;
					  propostaBloqueada=false;
				   }
			   } else {
				 fecharJanelaAtual(1)
				 alteraJanejaGenerica(0, 0)
				 enviado = false;
				 propostaBloqueada=false;
			   }
		   }
	   } catch (Exception e){
		 fecharJanelaAtual(1)
		 alteraJanejaGenerica(0, 0)
		 emissao = "Falha ao carregar a tela - Clausulas ";
		 enviado = false;
		 status = "Processar"
		 propostaBloqueada=false;
	 }
   }
	
   /**
	* @author Alz_sup54955
	* @param registros
	* @return
	*/
   public static boolean confirmacaoEmissao(){
	   try{
		   if(enviado){
	   		   WebUI.delay(15);
			   //alteraJanejaGenerica(1, 15)
			   emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			   System.out.println("Alerta ["+emissao+"]")
			   if(!alertListBlocList(emissao)){
				   System.out.println()
				   tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,22)
				   System.out.println(tela)
					if(listaDeTelas(tela)){
						//Avançar
						clicarEmElementoDaTela('//*[@id="btnAceptar"]', 0);
						WebUI.delay(5);
						try{
							fechaAlerta();
						}catch (Exception e){}
						String op = recuperaValor( '//*[@id="CabeceraBean$endosoSusep"]', 'innerText', 0)
						fecharJanelaAtual(1)
						alteraJanejaGenerica(0, 0)
						propostaBloqueada = true;
						emissao = "Endosso emitido "+op
					} else {
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  alerta = "Falha: Tela - Confirmação Emissão - Não apresentada";
					  enviado = false;
					  propostaBloqueada=false;
				   }
			   } else {
			     alertaCampoNecessarioEmissao()
				 fecharJanelaAtual(1)
				 alteraJanejaGenerica(0, 0)
				 enviado = false;
				 propostaBloqueada=false;
			   }
		   }
	   } catch (Exception e){
		 fecharJanelaAtual(1)
		 alteraJanejaGenerica(0, 0)
		 emissao = "Falha ao carregar a tela - Dados Emissão";
		 enviado = false;
		 status = "Processado"
		 propostaBloqueada=false;
	 }
   }
   
   /**
	* @author Alz_sup54955
	* @param registros
	* @return
	*/
   public static boolean emissaoRealizada(){
	   try{
		   if(enviado){
	   //		WebUI.delay(10);
			   alteraJanejaGenerica(1, 15)
			   emissao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			   System.out.println("Alerta ["+emissao+"]")
			   if(!alertListBlocList(emissao)){
				   System.out.println()
				   tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,18)
				   System.out.println(tela)
					if(listaDeTelas(tela)){
						emissao = recuperaValor('//*[@id="CabeceraBean$endosoSusep"]', 'innerText', 2)
						//Avançar
						clicarEmElementoDaTela('//*[@id="btnValida"]', 0);
						WebUI.delay(2);
						try{
							fechaAlerta();
						}catch (Exception e){}
					} else {
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  emissao = "Falha: Tela - Dados Emissão - Não apresentada";
					  enviado = false;
					  status = "Processar"
					  propostaBloqueada=false;
				   }
			   } else {
			     alertaCampoNecessarioEmissao()
				 fecharJanelaAtual(1)
				 alteraJanejaGenerica(0, 0)
				 enviado = false;
				 propostaBloqueada=false;
			   }
		   }
	   } catch (Exception e){
		 fecharJanelaAtual(1)
		 alteraJanejaGenerica(0, 0)
		 emissao = "Falha ao carregar a tela - Dados Emissão";
		 enviado = false;
		 status = "Processar"
		 propostaBloqueada=false;
	 }
   }
   
   /**
	* @author Alz_sup54955
	* @param registros
	* @return
	*/
   public static boolean producao(){
	   try{
		   if(enviado){
			   WebUI.delay(15);
			   System.out.println()
			   MenuMatriz.producaoSinistro()
			   MenuMatriz.subMenuNome('a','Produção')
	   //		WebUI.delay(10);
			   alteraJanejaGenerica(1, 10)
			   desbloq = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			   System.out.println("Alerta ["+desbloq+"]")
			   if(!alertListBlocList(desbloq)){
				   System.out.println()
				   tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,13)
				   System.out.println(tela)
					if(listaDeTelas(tela)){
						//Avançar
						clicarEmElementoDaTela('//*[@id="pBloqueadas"]', 5);
						WebUI.delay(2);
						try{
							fechaAlerta();
						}catch (Exception e){}
						textoComplementar = "Texto complementar alterado.";
					} else {
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  desbloq = "Falha: Tela - Produção - Não apresentada";
					  enviado = false;
				   }
			   } else {
			     alertaCampoNecessarioDesbloqueio()
				 fecharJanelaAtual(1)
				 alteraJanejaGenerica(0, 0)
				 enviado = false;
			   }
		   }
	   } catch (Exception e){
		 fecharJanelaAtual(1)
		 alteraJanejaGenerica(0, 0)
		 desbloq = "Falha ao carregar a tela - Produção";
		 enviado = false;
		 
	 }
   }
	
   /**
	* @author Alz_sup54955
	* @param registros
	* @return
	*/
   public static boolean listaBloqueios(List<String> registros){
	   try{
		   if(enviado){
	   //		WebUI.delay(10);
			   alteraJanejaGenerica(1, 15)
			   desbloq = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			   System.out.println("Alerta ["+desbloq+"]")
			   if(!alertListBlocList(desbloq)){
				   System.out.println()
				   tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,15)
				   System.out.println(tela)
					if(listaDeTelas(tela)){
						//Avançar
						preencheXpath('//*[@id="poliza_SUSEP"]',registros[0], 2)
						//Pesquisar
						clicarEmElementoDaTela('//*[@id="btnBuscar"]', 2);
						//Pesquisar
						clicarEmElementoDaTela('//*[@id="btnBuscar"]', 2);
						WebUI.delay(2);
						desbloq = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
						System.out.println("Alerta ["+desbloq+"]")
						if(!alertListBlocList(desbloq)){
						   //Autorizar/Emitir
						   WebUI.delay(2);
						   selecionaItemListaAtributo('Autorizar/Emitir','//*[@id="tablaBloqueos_tbody"]' , "img", 'title' )
						   WebUI.delay(2);
						   try{
							  fechaAlerta();
						   }catch (Exception e){}
						   resultadoOperacional()
						   //fecharJanelaAtual(1)
						   WebUI.delay(1);
						   selecionaItemListaAtributo('Autorizar/Emitir','//*[@id="tablaBloqueos_tbody"]' , "img", 'title' )
						   WebUI.delay(2);
						   try{
								fechaAlerta();
						   }catch (Exception e){}
						   resultadoOperacional()
						   fecharJanelaAtual(1)
						   alteraJanejaGenerica(0, 0)
						   enviado = true;
						   System.out.println()
						   System.out.println("["+enviado+"]")
						   alerta = "OK"
						   System.out.println("["+alerta+"]")
						   System.out.println()
						   } else {
						alertaCampoNecessarioDesbloqueio()
						fecharJanelaAtual(1)
						alteraJanejaGenerica(0, 0)
						enviado = false;
					  }
					} else {
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  desbloq = "Falha: Tela - Lista de Bloqueios - Não apresentada";
					  enviado = false;
				   }
			   } else {
			     alertaCampoNecessarioDesbloqueio()
				 fecharJanelaAtual(1)
				 alteraJanejaGenerica(0, 0)
				 enviado = false;
			   }
		   }
	   } catch (Exception e){
		 fecharJanelaAtual(1)
		 alteraJanejaGenerica(0, 0)
		 alerta = "Falha ao carregar a tela - Lista de Bloqueios";
		 enviado = false;
	 }
   }
   
   /**
	* @author Alz_sup54955
	* @param registros
	* @return
	*/
   public static boolean resultadoOperacional(){
	   try{
		   if(enviado){
	   		   WebUI.delay(15);
			   alteraJanejaGenerica(2, 0)
			   desbloq = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
			   System.out.println("Alerta ["+desbloq+"]")
			   if(!alertListBlocList(desbloq)){
				   System.out.println()
				   tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0).subSequence(0,21)
				   System.out.println(tela)
					if(listaDeTelas(tela)){
						desbloq = recuperaValor('//*[@id="sectionPaginaCompletaContent"]', 'innerText', 5)
						WebUI.delay(2);
						try{
							fechaAlerta();
						}catch (Exception e){}
						fecharJanelaAtual(2)
						alteraJanejaGenerica(1, 0)
						enviado = true;
					} else {
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  desbloq = "Falha: Tela - Lista de Bloqueios - Não apresentada";
					  enviado = false;
				    }
			   } else {
			     alertaCampoNecessarioDesbloqueio()
				 fecharJanelaAtual(1)
				 alteraJanejaGenerica(0, 0)
				 enviado = false;
			   }
		   }
	   } catch (Exception e){
		 fecharJanelaAtual(1)
		 alteraJanejaGenerica(0, 0)
		 desbloq = "Falha ao carregar a tela - Lista de Bloqueios";
		 enviado = false;
		  
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
	public static enviaEmailDinamico(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
		System.out.println()
		try{
			if(!validaDados(cellCheck)){
				String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Agro\\TextoComplementar\\ConfiguracoesEndossoClausulas.txt";
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
			}
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
			fluxoCompletoEndossoClausulasFuncionalidades(aba,a)
			reprocessaFalhas(aba,a)
		}
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaDesbloqueio(){
		System.err.println('Reprocessa Desbloqueio')
		if(selecionaItemListaAtributo('Autorizar/Emitir','//*[@id="tablaBloqueos_tbody"]' , "img", 'title' )){
			selecionaItemListaAtributo('Autorizar/Emitir','//*[@id="tablaBloqueos_tbody"]' , "img", 'title' )
			reprocessaDesbloqueio()
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
	 * @author ALZ_SUP54955
	 */
	public static void alertaCampoNecessarioEmissao(){
		if(emissao.equals(" Campo necessário")){
			String msg1 = emissao
			String msg2 = recuperaValor( '//*[@id="dataErrorSection"]/table/tbody/tr/td[1]', 'innerText', 5)
			emissao = msg1+" "+msg2;
		}
	}
	
	/**
	 * @author ALZ_SUP54955
	 */
	public static void alertaCampoNecessarioDesbloqueio(){
		if(desbloq.equals(" Campo necessário")){
			String msg1 = desbloq
			String msg2 = recuperaValor( '//*[@id="dataErrorSection"]/table/tbody/tr/td[1]', 'innerText', 5)
			desbloq = msg1+" "+msg2;
		}
	}
	
	/**
	 * @author ALZ_SUP54955
	 * @return
	 */
	public static boolean ramos(){
		boolean r = true
		switch(ramo){
			case "0609":
			    r = false;
			break;
		}
	return r;	
	}
	
}

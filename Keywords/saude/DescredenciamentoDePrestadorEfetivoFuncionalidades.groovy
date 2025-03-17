package saude

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
import java.util.List
import auxiliares.MenuMatriz
import auxiliares.ExtractDates
import auxiliares.timer



public class DescredenciamentoDePrestadorEfetivoFuncionalidades extends rpaCommands  {
	
	private static int cellCheck = 5
	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Saude/DescredenciamentoDePrestadorEfetivo/DescredenciamentoDePrestadorEfetivoData'
	private static String path
	private static String msgAlerta
	private static String status
	private static String referencia
	
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
			MenuMatriz.producaoSinistro()
			MenuMatriz.prestadores()
			alteraJanejaGenerica(1, 10)
			WebUI.delay(10);
		} catch (Exception e){}
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @param a
	 * @return
	 */
	public static initSinistroSaude(String aba, String a){
		System.out.println();
		try{
			MenuMatriz.producaoSinistro()
			MenuMatriz.subMenuSinistroSaude()
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
	public static fluxoDescredenciamentoDePrestadorEfetivo(String aba, String a){
		initDataFiles()
		fluxoCompletoDescredenciamentoDePrestadorEfetivo(aba,a)
		reprocessaFalhas(aba,a)
		enviaEmailDescredenciamentoDePrestadorEfetivo(aba)
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoDescredenciamentoDePrestadorEfetivo(String aba, String a){
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
						registros[0] =  ajusteCNPJ(registros[0]);
						if(efetivar(registros, aba, a)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 4, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, msgAlerta , 5, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 4, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path,msgAlerta, 5, aba);
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
	public static boolean efetivar(List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado
		try{
			//PRESTADORES
			String telaPrestadores = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(telaPrestadores )
			if(listaDeTelas("PRESTADORES")){
				//Gestão Prestadore
				selecionaItemLista("Gestão prestadores",'//*[@id="tableOptMenu_"]' , "td" )
				//GESTÃO PRESTADORES
				String telaGestaoPrestadores = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
				System.out.println(telaGestaoPrestadores )
				if(listaDeTelas("GESTÃO PRESTADORES")){
				   //CNPJ
				   preencheXpath('//*[@id="nif1_doc"]', registros[0], 1)
				   //Tipo Prestador
					selecionaItemLista("Saúde",'//*[@id="tipusProveidor"]' , "option" )
				   //Pesquisar
				   selecionaItemLista("Pesquisar",'//*[@id="SeccioRecercaSectionBar"]/table/tbody/tr/td' , "div" )
				   WebUI.delay(5);
				   System.out.println()
				   String msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
				   if(!alertListBlocList(msgAlert)){ 
					  boolean t= true;
				      String xpath = '//*[@id="taula_tbody"]'
				      String tag ="tr";
				      selecionaListaDeElementos(xpath,tag)
				      for(int i = 0; i < listOfElements.size(); i++){
					      if(!tabelaPrestadores(registros, i)){
						     t = false;
						     break;
					      }
				      }
					  if(t){
					     if(telaSinistroSaude(registros, aba, a)){
							enviado = true;
						 } else {
						    enviado = false;
						 }
					  } else {
					     enviado = false;
					  }
				   } else {
					  enviado = false
					  msgAlerta=telaGestaoPrestadores+": "+msgAlert;
					  status = "Processado"
					  //Limpar
					  selecionaItemLista("Limpar",'//*[@id="SeccioRecercaSectionBar"]/table/tbody/tr/td/table' , "td" )
				  }
				} else {
				  enviado = false
				  msgAlerta="Tela 'GESTÃO PRESTADORES' não apresentada"
				  status = "Processar"
				  alteraJanejaGenerica(0, 10)
				  WebUI.closeBrowser();
				  init(aba, a)
				}
			} else {
			  enviado = false
			  msgAlerta="Tela 'PRESTADORES' não apresentada"
			  status = "Processar"
			  alteraJanejaGenerica(0, 10)
			  WebUI.closeBrowser();
			  init(aba, a)
			}
		} catch (Exception e) {
			enviado = false
			status = "Processado"
			msgAlerta="Falha ao preencher 'Ficha de Gestão'"
			alteraJanejaGenerica(0, 10)
			WebUI.closeBrowser();
			init(aba, a)
		}
		System.out.println("\nenviado ["+ enviado+"] \nmsgAlerta ["+msgAlerta+"]");
		return enviado;
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @param i
	 * @return
	 */
	public static boolean tabelaPrestadores(List<String> registros, int i){
		boolean b;
		System.out.println()
		try{
			String msgAlertDados = recuperaValor('//*[@id="taula_tbody"]/tr/td', 'innerText', 5)
			System.out.println("["+msgAlertDados+"]")
			if(!alertListBlocList(msgAlertDados)){
			 System.out.println()
			 clickXpath('//*[@id="row_'+i+'_taula"]', 1);
			 //Editar
			 selecionaItemLista("Editar",'//*[@id="MantenimientoViewForm"]/table[21]/tbody/tr[2]/td/table' , "td" )
			 WebUI.delay(5);
			 //Situação
			 selecionaItemLista("Histórico",'//*[@id="prestador.situacaoPrest"]' , "option" )
			 //Salvar
			 selecionaItemLista("Salvar",'//*[@id="MantenimientoViewForm"]/table[21]/tbody/tr[2]/td/table' , "td" )
			 String msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
			 System.out.println("["+msgAlert+"]")
			  if(alertListBlocList(msgAlert)){
				  try{
				     String msgAlert2 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]', 'textContent', 5)
				     msgAlerta = msgAlert+": "+msgAlert2 ;
				     System.out.println("ALERTA ["+msgAlerta+"]");
				     b = false;
				     status = "Processado"
				     //Salvar
				     selecionaItemLista("Voltar",'//*[@id="MantenimientoViewForm"]/table[21]/tbody/tr[2]/td/table' , "td" )
				     //Limpar
				     selecionaItemLista("Limpar",'//*[@id="SeccioRecercaSectionBar"]/table/tbody/tr/td/table' , "td" )
				  } catch (Exception e){
				    b = false;
				  }
			   } else {
				 try{
				    //Salvar
				    selecionaItemLista("Salvar",'//*[@id="manutencaoBrasilForm"]/table/tbody/tr[2]/td' , "td" )
				    String msgAlert3 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr[1]/td[2]', 'textContent', 5)
				    if(alertListContinueList(msgAlert3)){
					   msgAlerta = msgAlert3;
					  
				    } else {
				      String msgAlert4 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
				      if(alertListBlocList(msgAlert4)){
					     String msgAlert2 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]', 'textContent', 5)
					     msgAlerta = msgAlert+": "+msgAlert4 ;
				      }
				   }
				   b = true;
				 } catch (Exception e ){
				 
				 }
			  }
			} else {
			  msgAlerta = msgAlertDados ;
		 	  System.out.println("ALERTA ["+msgAlerta+"]");
			  b = false;
			  status = "Processado"
			}   
		} catch (Exception e){
		  b = false;
		}
		return b;
	}
	
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @param aba
	 * @param a
	 * @return
	 */
	public static boolean telaSinistroSaude(List<String> registros, String aba, String a){
		boolean enviado;
		try{
		   fecharJanelaAtual(1)
		   alteraJanejaGenerica(0, 1)
		   initSinistroSaude(aba, a);
		   //Sinistros de Saúde
		   String telaSinistrosSaude = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
		   System.out.println(telaSinistrosSaude )
		   if(listaDeTelas("Sinistros de Saúde")){
		      //Gestão Prestadore
		      selecionaItemLista("Convencionados (»)",'//*[@id="tableOptMenu_"]' , "td" )
		      //Prestadores
		      selecionaItemLista("Prestadores",'//*[@id="tableOptMenu_1"]' , "td" )
		      //Número Documento
		      preencheXpath('//*[@id="nif"]', registros[0], 1)
		      //Tipo Prestador
		      selecionaItemLista("Pesquisar",'//*[@id="sectionPaginaCompletaSectionBar"]/table/tbody/tr/td/table' , "td" )
		      String msgAlertSaude = recuperaValor('//*[@id="resposta_tbody"]/tr/td', 'textContent', 5)
		      if(alertListBlocList(msgAlertSaude)){
			     fecharJanelaAtual(1)
			     alteraJanejaGenerica(0, 1)
			     init(aba,a);
			     enviado = false
			     msgAlerta=telaSinistrosSaude+": "+msgAlertSaude;
			     status = "Processado"
		      } else {
			    //Pesquisar
			    clickXpath('//*[@id="o_2"]', 1);
			    //Endereço
			    selecionaItemLista("Endereço",'//*[@id="tabs2"]/ul' , "label" )
			    //Detalhe Endereço
			    clickXpath('//*[@id="o_14"]', 1);
			    //Editar
			    selecionaItemLista("Editar",'//*[@id="sectionPaginaCompletaSectionBar"]/table/tbody/tr/td' , "td" )
			    //Motivo de inativação
			    preencheXpath('//*[@id="domicilio.motivoInactivacao"]', registros[2], 1)
			    //Gavar
			    clickXpath('//*[@id="o_13"]', 1);
			    fecharJanelaAtual(1)
			    alteraJanejaGenerica(0, 1)
			    init(aba,a);
			    enviado = true
			    status = "Processado"
		      }
		   } else {
		     enviado = false
		     msgAlerta="Tela 'Sinistros de Saúde' não apresentada"
		     status = "Processar"
		     alteraJanejaGenerica(0, 10)
		     WebUI.closeBrowser();
		     init(aba, a)
		   }
		} catch (Exception e){
		  enviado = false
		}
		return  enviado;
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
			fluxoCompletoDescredenciamentoDePrestadorEfetivo(aba, a)
			reprocessaFalhas(aba, a)
		}
	}
	
	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailDescredenciamentoDePrestadorEfetivo(String tipoAba){
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Saude\\DescredenciamentoDePrestadorEfetivo\\ConfiguracoesDescredenciamentoDePrestadorEfetivo.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "DescredenciamentoDePrestadorEfetivo.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
			}
		} catch (Exception e){
		}
	}
	
	
	
}

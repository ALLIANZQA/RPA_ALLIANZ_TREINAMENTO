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



public class DescredenciamentoDePrestadorSinistroFuncionalidades extends rpaCommands{
	
	
	private static int cellCheck = 5
	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Saude/DescredenciamentoDePrestadorSinistro/DescredenciamentoDePrestadorSinistroData'
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
			MenuMatriz.subMenuSinistroSaude()
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
	public static fluxoDescredenciamentoDePrestadorSinistro(String aba, String a){
		initDataFiles()
		fluxoCompletoDescredenciamentoDePrestadorSinistro(aba,a)
		reprocessaFalhas(aba,a)
		enviaEmailDescredenciamentoDePrestadorSinistro(aba)
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoDescredenciamentoDePrestadorSinistro(String aba, String a){
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
						if(descredenciar(registros, aba, a)) {
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
	public static boolean descredenciar(List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado
		try{
			 if(telaSinistroSaude(registros, aba, a)){
				enviado = true;
			 } else {
			    enviado = false;
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
	public static boolean tabelaGestaoPresentesLegais(List<String> registros){
		boolean b;
		System.out.println()
		try{
			String telaGestaoPresentesLegais = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
		    System.out.println(telaGestaoPresentesLegais)
		    if(listaDeTelas(telaGestaoPresentesLegais)){
				//CNPJ/CPF
				preencheXpath('//*[@id="reprLegal.id_doc"]', registros[0], 1)
				//Nome
				preencheXpath('//*[@id="reprLegal.nome"]', registros[1], 1);
				//Guardar
			    clickXpath('//*[@id="o_3"]', 1);
			    b = true;
			} else {
			  msgAlerta = telaGestaoPresentesLegais  ;
			  System.out.println("Tela ["+msgAlerta+"] não apresentada");
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
		System.out.println();
		boolean enviado;
		String msgAlert;
		try{
		   //Sinistros de Saúde
		   String telaSinistrosSaude = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
		   System.out.println(telaSinistrosSaude )
		   if(listaDeTelas(telaSinistrosSaude)){
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
				//Editar Prestador na PI
				selecionaItemLista("Editar Prestador na PI",'//*[@id="sectionPaginaCompletaSectionBar"]/table/tbody/tr/td' , "div" )
				alteraJanejaGenerica(2, 5)
				//Gestão Saúde
				String telaGestaoSaude = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
				System.out.println(telaGestaoSaude )
				try{
				  if(listaDeTelas(telaGestaoSaude)){
					//Editar 
					selecionaItemLista("Editar",'//*[@id="MantenimientoViewForm"]/table[21]' , "div" )
					//Situação
					selecionaItemLista("Histórico",'//*[@id="prestador.situacaoPrest"]' , "option" )
					//Salvar
					selecionaItemLista("Salvar",'//*[@id="MantenimientoViewForm"]/table[21]/tbody/tr[2]/td/table' , "div" )
					
					msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 10)
					System.out.println(msgAlert);
					if( !alertListBlocList(msgAlert) ){
					  msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 10)
					  System.out.println(msgAlert);
					  if(alertListContinueList(msgAlert)){
						alteraJanejaGenerica(3, 5);
						tabelaGestaoPresentesLegais(registros);
						fecharJanelaAtual(3);
						alteraJanejaGenerica(2, 2)
						selecionaItemLista("Salvar",'//*[@id="MantenimientoViewForm"]/table[21]/tbody/tr[2]/td/table' , "div" )
					  }
					  System.out.println(msgAlert);
					  if(!msgAlert.contains(" Prestador Corretamente Modificado")){
						try{
					       //Salvar
					       selecionaItemLista("Salvar",'//*[@id="manutencaoBrasilForm"]/table/tbody/tr[2]/td/table' , "div" )
						} catch (Exception e){
						
						}
					  }
					  String msg1 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr[1]/td[2]', 'innerText', 1)
					  System.out.println(msg1);
					  fecharJanelaAtual(2);
					  alteraJanejaGenerica(1, 5)
					  //Endereço 
					  selecionaItemLista("Endereço",'//*[@id="tabs2"]/ul' , "label" )
					  //Pesquisar Detalhes 
					  detalhes();
					  //Editar Prestador
					  //Editar Prestador na PI
					  selecionaItemLista("Editar Endereço na PI",'//*[@id="sectionPaginaCompleta"]/table' , "div" )
					  selecionaItemLista("Editar Prestador na PI",'//*[@id="sectionPaginaCompleta"]/table' , "div" )
					  
					  alteraJanejaGenerica(2, 5)
					  //Editar
					  selecionaItemLista("Editar",'//*[@id="MantenimientoViewForm"]/table[21]' , "div" )
					  //Situação
					  selecionaItemLista("Histórico",'//*[@id="prestador.situacaoPrest"]' , "option" )
					  //Salvar 
					  selecionaItemLista("Salvar",'//*[@id="MantenimientoViewForm"]/table[21]/tbody/tr[2]/td/table' , "div" )
					  
					  msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 10)
					  System.out.println(msgAlert)
					  if(msgAlert.equals(" Campo Obrigatório")){
							  String msgAlert2 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[1]', 'textContent', 0)
							  System.out.println(msgAlert2)
							  msgAlerta = telaGestaoSaude +" - "+ msgAlert+" - "+ msgAlert2;
							  fecharJanelaAtual(2)
							  alteraJanejaGenerica(1, 1)
							  fecharJanelaAtual(1)
							  alteraJanejaGenerica(0, 1)
							  init(aba,a);
							  enviado = false;
							  status = "Processado"
					  } else {
					  msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 10)
					  System.out.println(msgAlert);
					  if(alertListContinueList(msgAlert)){
						 alteraJanejaGenerica(3, 5);
						 tabelaGestaoPresentesLegais(registros);
						 fecharJanelaAtual(3);
						 alteraJanejaGenerica(2, 2)
						 selecionaItemLista("Salvar",'//*[@id="MantenimientoViewForm"]/table[21]/tbody/tr[2]/td/table' , "div" )
					  }
					  
					  if(!msgAlert.contains(" Prestador Corretamente Modificado")){
					    //Salvar
					    selecionaItemLista("Salvar",'//*[@id="manutencaoBrasilForm"]/table/tbody/tr[2]/td' , "div" )
					  }
					  fecharJanelaAtual(2);
					  alteraJanejaGenerica(1, 1)
					  System.out.println();
					  //Editar
					  //clickXpath('//*[@id="o_12"]', 1);
					  selecionaItemLista("Editar",'//*[@id="sectionPaginaCompleta"]/table' , "div" )
					  //Informação Comum 
					  clickXpath('//*[@id="detalhesComuns"]/table/tbody/tr[1]/td/table/tbody/tr/td[2]', 1);
					  // Motivação parte 1
					  //Motivo de inativação
					  limpaCampo('//*[@id="domicilio.motivoInactivacao"]', 1);
					  preencheXpath('//*[@id="domicilio.motivoInactivacao"]', registros[2], 1)
					  //Gavar
					  //clickXpath('//*[@id="o_13"]', 1);
					  selecionaItemLista("Gravar",'//*[@id="sectionPaginaCompleta"]/table' , "div" )
					  WebUI.delay(5);
					  // Motivação parte 2 
					  //Voltar 
					  //selecionaItemLista("Voltar",'//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table/tbody/tr/td' , "div" )
					  selecionaItemLista("Voltar",'//*[@id="divAppContent"]/table' , "div" )
					  //Editar
					  //selecionaItemLista("Editar",'//*[@id="sectionPaginaCompletaSectionBar"]/table/tbody' , "div" )
					  selecionaItemLista("Editar",'//*[@id="sectionPaginaCompleta"]/table' , "div" )
					  //Informação Comum
					  clickXpath('//*[@id="detalhesComuns"]/table/tbody/tr[1]/td/table/tbody/tr/td[2]', 5);
					  //Motivo de inativação
					  limpaCampo('//*[@id="editEntity.motivoInactivacao"]', 1);
					  preencheXpath('//*[@id="editEntity.motivoInactivacao"]', registros[2], 1)
					  //Gavar
					  //selecionaItemLista("Gravar",'//*[@id="sectionPaginaCompletaSectionBar"]/table/tbody' , "div" )
					  selecionaItemLista("Gravar",'//*[@id="sectionPaginaCompleta"]/table' , "div" )
					  WebUI.delay(5);
					  String msg2 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr[1]/td[2]', 'innerText', 1)
					  msgAlerta = msg1+". "+msg2;
					  System.out.println(msgAlerta);
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 1)
					  init(aba,a);
					  enviado = true
					  status = "Processado"
					  
					  } 
					  
					} else {
					  msgAlerta = telaGestaoSaude +" - "+ msgAlert
					  fecharJanelaAtual(2)
					  alteraJanejaGenerica(1, 1)
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 1)
					  init(aba,a);
					  enviado = false;
					  status = "Processado"
				   }
				 } 
				} catch (Exception e) {
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 1)
				  init(aba,a);
				  enviado = false;
				  status = "Processar"
				}
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
	
	public static void detalhes(){
		 String xpath = '//*[@id="divAppContent"]/table';
		 String tag = 'input'
		 selecionaListaDeElementos(xpath,tag)
		 for(int i = 0; i < listOfElements.size(); i++){
			 System.err.println("["+i+"]  ["+listOfElements[i].getAttribute('title')+"] ["+listOfElements[i].getText()+"]")
			 if(listOfElements[i].getAttribute('title').toString().equals("Detalhes".toString())){
				 //System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				 listOfElements[i].click()
				
				 break
			 }
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
			fluxoCompletoDescredenciamentoDePrestadorSinistro(aba, a)
			reprocessaFalhas(aba, a)
		}
	}
	
	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailDescredenciamentoDePrestadorSinistro(String tipoAba){
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Saude\\DescredenciamentoDePrestadorSinistro\\ConfiguracoesDescredenciamentoDePrestadorSinistro.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "DescredenciamentoDePrestadorSinistro.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos, tipoAba)
			}
		} catch (Exception e){
		}
	}
	
	
	
	
	
	
}

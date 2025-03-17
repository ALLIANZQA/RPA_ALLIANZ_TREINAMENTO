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
import java.util.List
import auxiliares.MenuMatriz
import auxiliares.ExtractDates
import auxiliares.timer



public class DuplicadoRecibosFuncionalidades extends rpaCommands {
	
	
	
	private static int cellCheck = 18
	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/DuplicadoRecibos/DuplicadoRecibosData'
	private static String path
	private static String msgAlerta
	private static String status
	private static String referencia
	private static String pathFileValue = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\EditorCorrespondencia\\Anexos\\";
	private static fileName = "C:\\Users\\Public\\AllianzSegurosBrasil.pdf";
	private static fileRootName = "C:\\Users\\Public\\";
	
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
	public static initRecibo(){
		System.out.println();
		try{
			WebUI.delay(5);
			MenuMatriz.producaoSinistro()
			WebUI.delay(10);
			MenuMatriz.subMenu('/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[4]/div/div[1]/ul', 'a', 'Duplicados Recibos')
		} catch (Exception e){}
	}
	
	
	public static initFG(){
		System.out.println();
		try{
			WebUI.delay(5);
			MenuMatriz.newFluxoGestao('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]');
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
	public static fluxoDuplicadoRecibos(String aba, String a){
		initDataFiles()
		fluxoCompletoDuplicadoRecibos(aba,a)
		reprocessaFalhas(aba,a)
		enviaEmailDuplicadoRecibos(aba)
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoDuplicadoRecibos(String aba, String a){
		System.out.println();
		try{
			if(validaDados(cellCheck)){
				acessoAmbiente(aba,a)
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					msgAlerta = ''
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						if(editor(registros, aba, a, j, path)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 17, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, msgAlerta , 19, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 17, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 18, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path,msgAlerta, 19, aba);
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
	public static boolean editor(List<String> registros, String aba, String a, int linha, String path){
		System.out.println()
		boolean enviado = false;
		try{
			if(recibo(registros, aba, linha, path)){ 
				if(fichaGestao(registros, aba, a)){
					enviado = true;
				}
			} 
		} catch (Exception e) {
			enviado = false
			status = "Processado"
			//msgAlerta="Falha ao preencher '2ª VIA-RECIBO'"
			alteraJanejaGenerica(0, 10)
			WebUI.closeBrowser();
		}
		System.out.println("\nenviado ["+ enviado+"] \nmsgAlerta ["+msgAlerta+"]");
		return enviado;
	}
	
	
	public static boolean recibo(List<String> registros, String aba, int linha, String path){
		System.out.println()
		boolean r = true;
		try{
		if(registros[18].toString().equals("Processar")){
		   initRecibo();
		   alteraJanejaGenerica(1, 1);
		   String telaSegundaViaRecibo = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
		   System.out.println(telaSegundaViaRecibo)
	 	   if(listaDeTelas("2ª VIA-RECIBO")){
			   //Numero Recibo 
			   clickXpath('//*[@id="numRecibo"]', 0)
			   preencheXpath('//*[@id="numRecibo"]', registros[0], 0)
			   //Limite Maximo
			   preencheXpath('//*[@id="fechajuros"]', registros[1], 1)
			   if(registros[2].toString().equals("Sim")){
			      //Sem Juros
			      clickXpath('//*[@id="juros"]', 1)
			   }
			   if(!validaExistenciaDeArquivo(fileName)){
				   deletaArquivoPdf(fileName);
			   }
			   //Aceitar
			   selecionaItemListaTimer("Aceitar",'//*[@id="seccionBusquedaRecibosSectionBar"]/table/tbody/tr/td' , "div",0 )
			   String msgErroAlerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
			   System.out.println("2ª VIA-RECIBO ALERTA  ["+msgAlerta+"] ["+msgAlerta.length()+"]")
			   if(alertListBlocList(msgErroAlerta)){
				   r = false;
				   status = "Processado"
				   msgAlerta = "Alerta: "+msgErroAlerta
				   fecharJanelaAtual(1)
				   alteraJanejaGenerica(0, 2)
			   } else {
			   WebUI.delay(10);
			   if(!validaExistenciaDeArquivo(fileName)){
			      renomearArquivo(fileName,  registros[3])
				  try{
				    auxiliares.WriteExcelFactory.updateSheetLine("", linha, path, "Processado", 18, aba);
				  } catch (Exception e){}
				  fecharJanelaAtual(2)
				  alteraJanejaGenerica(1, 2)
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 2)
			 	  r = true;
			   }
			 }
		   }
		 }
		} catch (Exception e){
		  r = false;
		  status = "Processar"
		  msgAlerta="Falha ao preencher '2ª VIA-RECIBO'"
		}
		return r;
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 * @param aba
	 * @param a
	 * @return
	 */
	public static boolean fichaGestao(List<String> registros, String aba, String a){
		System.out.println();
		boolean enviado;
		boolean arquivoAnexo;
		String msgAlert;
		try{
			initFG()
			alteraJanejaGenerica(1, 1);
			//FICHA GESTÃO
			String telaFichaGestao = recuperaValor('/html/body/div/div/app-root/file-management/app-header/div/nx-ibl-header/div/div/div[1]', 'innerText', 1)
			System.out.println(telaFichaGestao )
			if(telaFichaGestao.contains("Ficha de Gestão") || telaFichaGestao.contains("Ficha Gestión") ){
				/**
				 * Nova Ficha Gestão
				 * Recebe : lista, index proposta, index apolice
				 *
				 */
				//novaFichaGestaoProcesso(registros,0,1, registros[8])
				tipoReferencia(registros, 6, 7,8)
				WebUI.delay(5);
				msgAlert = recuperaValor('//*[@id="componenteReferencia"]/div/div[2]/div/nx-message/div', 'innerText', 1)
				System.out.println("alertaFG ["+msgAlert+"] ["+msgAlert.length()+"]")
				if(alertListBlocList(msgAlert)){
					fecharJanelaAtual(1);
					alteraJanejaGenerica(0, 1)
					arquivoAnexo = false
					msgAlerta=msgAlert +" - "+registros[1];
					status = "Processado"
				    
				} else{
				  //Editor de correspondencia
				  selecionaItemLista("Editor correspondencia",'/*/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div' , "span" )
				  alteraJanejaGenerica(2, 10);
				  //Editor Correspondencia
				  String telaEditorCorrespondencia = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
				  System.out.println(telaEditorCorrespondencia )
				  if(listaDeTelas(telaEditorCorrespondencia)){
					  
					  //Meio de envio
					  selecionaItemListaTimer(registros[9],'//*[@id="cbMedioEnvio"]' , "option",0 )
					  //Tipo Destino
					  selecionaItemListaTimer(registros[10],'//*[@id="cbTipoDest"]' , "option",0 )
					  //Aceitar
					  selecionaItemListaTimer("Aceitar",'//*[@id="SectionDatosEnvioSectionBar"]/table/tbody/tr/td' , "div",0 )
					  
					  //Nome
					  limpaCampo('//*[@id="txtNombre"]', 1);
					  preencheXpath('//*[@id="txtNombre"]', registros[11], 1)
					  
					  //E-mail destino
					  limpaCampo('//*[@id="txtEmail"]', 1);
					  preencheXpath('//*[@id="txtEmail"]', registros[12], 1)
					  
					  //Email copia
					  limpaCampo('//*[@id="txtEmailCC"]', 1);
					  preencheXpath('//*[@id="txtEmailCC"]', registros[13], 1)
					  
					  //Continuar
					  selecionaItemLista("Continuar",'//*[@id="divAppContent"]/table' , "div" )
					  //Modelo descrição
					  limpaCampo('//*[@id="numModCodi"]', 1);
					  preencheXpath('//*[@id="numModCodi"]', registros[14], 1)
					  
					  System.out.println();
					  //Aceitar
					  selecionaItemListaTimer("Aceitar",'//*[@id="sectionFiltroDocumentoSectionBar"]/table/tbody/tr/td' , "div",0 )
					  
					  System.out.println();
					  //Retorno Cobrança
					  limpaCampo('//*[@id="txtModDesLibre"]', 3);
					  preencheXpath('//*[@id="txtModDesLibre"]', registros[15], 1)
					  
					  System.out.println();
					  if(!validaExistenciaDeArquivo(fileRootName+registros[3].toString()+".pdf")  ){
						if(anexarPDF(registros, fileRootName+registros[3].toString()+".pdf")){
						  arquivoAnexo = true;
						} else {
						  arquivoAnexo = false
						  msgAlerta="Falha ao anexar o arquivo em 'Editor Correspondência'";
						  status = "Processado"
						  fecharJanelaAtual(2)
						  alteraJanejaGenerica(1, 2)
						  fecharJanelaAtual(1)
						  alteraJanejaGenerica(0, 1)
						}
					  }
					  System.out.println();
					  if(arquivoAnexo.equals(true)){
						//Documento
						selecionaItemLista("Documento",'//*[@id="tableOptMenu_"]/tbody' , "td" )
						//Corpo E-mail
						limpaCampo('//*[@id="999992"]', 3);
						preencheXpath('//*[@id="999992"]', registros[16], 1)
						//Gravar
						selecionaItemLista("Gravar",'//*[@id="divAppContent"]/table/tbody/tr[1]/td/table' , "div" )
						enviado = true;
						String alrt = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5);
						msgAlerta = "OK - "+alrt+" em 'Editor Correspondência'";
						status = "Processado"
						fecharJanelaAtual(2)
						alteraJanejaGenerica(1, 2)
						fecharJanelaAtual(1)
						alteraJanejaGenerica(0, 1)
					  } else {
						enviado = false;
						msgAlerta="Falha ao anexar o arquivo em 'Editor Correspondência'";
						status = "Processado"
						//fecharJanelaAtual(2)
						//alteraJanejaGenerica(1, 2)
						fecharJanelaAtual(1)
						alteraJanejaGenerica(0, 1)
					  }
				  } else {
					enviado = false
					msgAlerta="Tela 'Editor de Correspondência' não apresentada"
					status = "Processar"
					alteraJanejaGenerica(1, 10)
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 1)
				  }
				}
			} else {
			  enviado = false
			  msgAlerta="Tela 'Fiche Gestão ' não apresentada"
			  status = "Processar"
			  alteraJanejaGenerica(0, 1)
			}
		} catch (Exception e){
		  enviado = false
		}
		
		if(!enviado){
			try{
			   deletaArquivoPdf(fileName);
			} catch (Exception e){}
		}
		
		return  enviado;
	}
	
	public static boolean anexarPDF(List<String> registros , String fileRootName){
		boolean anexo = false;
		try{
			//Anexar
			selecionaItemLista("Anexar",'//*[@id="tableOptMenu_"]/tbody' , "td" )
			System.err.println("pathValue ["+fileRootName+"]");
			boolean result = validaExistenciaDeArquivo(fileRootName);
			if(!result){
			  fileUpload('//*[@id="upFile1"]', fileRootName, 0);
			}
			String file = recuperaValor('//*[@id="inputFile1"]', 'Value', 1)
			if(file.equals(registros[3].toString()+".pdf")){
				anexo = true
				deletaArquivoPdf(fileRootName)
			}
		} catch (Exception e){
		  anexo = false;
		}
		return anexo;
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
			fluxoCompletoDuplicadoRecibos(aba, a)
			reprocessaFalhas(aba, a)
		}
	}
	
	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailDuplicadoRecibos(String tipoAba){
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\EditorCorrespondencia\\ConfiguracoesEditorCorrespondencia.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "DuplicadoRecibos.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
				WebUI.closeBrowser();
			}
		} catch (Exception e){
		}
	}
	
	
	
	
	
}

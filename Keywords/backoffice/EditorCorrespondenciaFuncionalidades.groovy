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

import java.time.Duration
import java.time.Instant
import java.util.List
import auxiliares.MenuMatriz
import auxiliares.ExtractDates
import auxiliares.timer

import sendEmail.emailDinamico;
import auxiliares.GetHost;

public class EditorCorrespondenciaFuncionalidades extends rpaCommands {
	
	
	private static int cellCheck = 14
	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/EditorCorrespondencia/EditorCorrespondenciaData'
	private static String path
	private static String msgAlerta
	private static String status
	private static String referencia
	private static String pathFileValue = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\EditorCorrespondencia\\Anexos\\";
	
    private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "IndexacaoFG.xlsx"
	private static int cellCheckAlert = 15;
	
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
	public static init(){
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
	public static fluxoEditorCorrespondencia(String aba, String a){
		
		
		String dataInicio = ExtractDates.dataAtual();
		String horaInicial =  ExtractDates.horaAtualizada();
		start = Instant.now();
		initDataFiles()
		fluxoCompletoEditorCorrespondencia(aba,a)
		reprocessaFalhas(aba,a)
		//enviaEmailEditorCorrespondencia(aba)
		end =  Instant.now();
		timeLapnse = Duration.between(start, end);
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
		   enviaEmailDEditorCorrespondenciaDinamico(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal, "" );
		} catch (Exception e){
		}
		  
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoEditorCorrespondencia(String aba, String a){
		System.out.println();
		try{
			if(validaDados(cellCheck)){
				acessoAmbiente(aba,a)
				init();
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					msgAlerta = ''
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						if(editor(registros, aba, a)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 13, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, msgAlerta , 14, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 13, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path,"Alerta: "+msgAlerta, 14, aba);
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
	public static boolean editor(List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado = false;
		try{
			 if(fichaGestao(registros, aba, a)){
				enviado = true;
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
			WebUI.delay(10);
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
				tipoReferencia(registros)
				WebUI.delay(5);
				msgAlert = recuperaValor('//*[@id="componenteReferencia"]/div/div[2]/div/nx-message/div', 'innerText', 1)
				System.out.println("alertaFG ["+msgAlert+"] ["+msgAlert.length()+"]")
				if(alertListBlocList(msgAlert)){
					fecharJanelaAtual(1);
					alteraJanejaGenerica(0, 1)
					arquivoAnexo = false
					msgAlerta=msgAlert +" - "+registros[1];
					status = "Processado"
					init()
				} else{
				  //Editor de correspondencia
				  selecionaItemLista("Editor correspondencia",'/*/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div' , "span" )
				  alteraJanejaGenerica(2, 10);
				  //Editor Correspondencia
				  String telaEditorCorrespondencia = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
				  System.out.println(telaEditorCorrespondencia )
				  if(listaDeTelas(telaEditorCorrespondencia)){
					  //Meio de envio
					  selecionaItemListaTimer(registros[3],'//*[@id="cbMedioEnvio"]' , "option",0 )
					  //Tipo Destino
					  selecionaItemListaTimer(registros[4],'//*[@id="cbTipoDest"]' , "option",0 )
					  //Aceitar 
					  selecionaItemListaTimer("Aceitar",'//*[@id="SectionDatosEnvioSectionBar"]/table/tbody/tr/td' , "div",0 )
					  //Nome 
					  limpaCampo('//*[@id="txtNombre"]', 1);
					  preencheXpath('//*[@id="txtNombre"]', registros[5], 1)
					  //E-mail destino
					  limpaCampo('//*[@id="txtEmail"]', 1);
					  preencheXpath('//*[@id="txtEmail"]', registros[6], 1)
					  //Email copia
					  limpaCampo('//*[@id="txtEmailCC"]', 1);
					  preencheXpath('//*[@id="txtEmailCC"]', registros[7], 1)
					  //Continuar
					  selecionaItemLista("Continuar",'//*[@id="divAppContent"]/table' , "div" )
					  msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
					  System.out.println("alertaFG ["+msgAlert+"] ["+msgAlert.length()+"]")
					  if(!alertListBlocList(msgAlert)){
					    //Modelo descrição 
					    limpaCampo('//*[@id="numModCodi"]', 1);
					    preencheXpath('//*[@id="numModCodi"]', registros[8], 1)
					    System.out.println();
					    //Aceitar
					    selecionaItemListaTimer("Aceitar",'//*[@id="sectionFiltroDocumentoSectionBar"]/table/tbody/tr/td' , "div",0 )
					    System.out.println();
					    //Retorno Cobrança
					    limpaCampo('//*[@id="txtModDesLibre"]', 3);
					    preencheXpath('//*[@id="txtModDesLibre"]', registros[9], 1)
					    System.out.println();
					    if(registros[10].toString().equals("Sim")){
					      if(anexarPDF(registros)){
						    arquivoAnexo = true;
					    } else {
					      arquivoAnexo = false
					      msgAlerta="Falha ao anexar o arquivo em 'Editor Correspondência'";
					      status = "Processado"
						  fecharJanelaAtual(2)
						  alteraJanejaGenerica(1, 2)
						  fecharJanelaAtual(1)
						  alteraJanejaGenerica(0, 1)
						  init()
					    }
					  }
					  
					  if(registros[10].toString().equals("Não") || registros[10].toString().equals("Sim") && arquivoAnexo.equals(true)){
					    //Documento
					    selecionaItemLista("Documento",'//*[@id="tableOptMenu_"]/tbody' , "td" )
					    //Corpo E-mail
					    limpaCampo('//*[@id="999992"]', 3);
					    preencheXpath('//*[@id="999992"]', registros[12], 1)
					    //Gravar
					    selecionaItemLista("Gravar",'//*[@id="divAppContent"]/table/tbody/tr[1]/td/table' , "div" )
					    enviado = true;
						String alrt = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5);
					    msgAlerta = "OK - "+alrt+" em 'Editor Correspondência'";
					    status = "Processado"
						fecharJanelaAtual(2)
						alteraJanejaGenerica(1, 5)
						fecharJanelaAtual(1)
						alteraJanejaGenerica(0, 5)
						init()
					  } else {
					    enviado = false;
					    msgAlerta="Falha ao anexar o arquivo em 'Editor Correspondência'";
					    status = "Processado"
						//fecharJanelaAtual(2)
						//alteraJanejaGenerica(1, 2)
						fecharJanelaAtual(1)
						alteraJanejaGenerica(0, 1)
						init()
					  }
				    } else {
				      arquivoAnexo = false
				      msgAlerta=msgAlert;
				      status = "Processado"
				      fecharJanelaAtual(2)
				      alteraJanejaGenerica(1, 2)
				      fecharJanelaAtual(1)
				      alteraJanejaGenerica(0, 1)
				      init()
				    }
				  } else {
				    enviado = false
				    msgAlerta="Tela 'Editor de Correspondência' não apresentada"
				    status = "Processar"
				    alteraJanejaGenerica(1, 10)
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 1)
					init()
				  }
				}
			} else {
			  enviado = false
			  msgAlerta="Tela 'Fiche Gestão ' não apresentada"
			  status = "Processar"
			  alteraJanejaGenerica(0, 1)
			  init()
			}
		} catch (Exception e){
		  enviado = false
		}
		return  enviado;
	}
	
	public static boolean anexarPDF(List<String> registros ){
		boolean anexo = false;
		try{
			//Anexar
			selecionaItemLista("Anexar",'//*[@id="tableOptMenu_"]/tbody' , "td" )
			String fileName =pathFileValue+""+registros[11].toString()+".xlsx";
			System.err.println("pathValue ["+fileName+"]");
			boolean result = validaExistenciaDeArquivo(fileName);
			if(!result){
			  fileUpload('//*[@id="upFile1"]', fileName, 0);
			}
			String file = recuperaValor('//*[@id="inputFile1"]', 'Value', 1)
			if(file.equals(registros[11].toString()+".xlsx")){
				anexo = true 
				//deletaArquivoPdf(fileName)
			}
		} catch (Exception e){
		  anexo = false;
		}
		return anexo;
	}
	
	public static tipoReferencia(List<String> registros){
		System.out.println();
		//ABRIR SELEÇÃO
		abreReferencia();
		//SELECIONAR
		System.out.println("Processo ["+registros[0]+"]")
		MenuMatriz.subMenu('//*[@id="cdk-overlay-0"]/div/div[2]', "div",processoFGEditorCorrespondencia(registros[0]))
		switch(registros[0]){
			case "Apólice":
			//Proposta
				preencheXpath('//*[@id="nx-input-1"]', registros[1],0);
			//Aplica
				preencheXpath('//*[@id="nx-input-2"]', registros[2],0);
			//Pesquisar
				clickXpath('//*[@id="componenteReferencia"]/div/div[1]/div[11]/nx-icon', 1);
				break;
			case "Cliente":
			//Documento
				preencheXpath('//*[@id="nx-input-4"]', registros[1],0);
			//Pesquisar
				clickXpath('//*[@id="componenteReferencia"]/div/div[1]/div[11]/nx-icon', 1);
				break;
			case "Agente Susep":
			//Referencia
				preencheXpath('//*[@id="nx-input-0"]', registros[1],0);
			//Pesquisar
				clickXpath('//*[@id="componenteReferencia"]/div/div[1]/div[11]/nx-icon', 1);
				break;
		}
	}
	
	public static void abreReferencia(){
		String xpath ='//*[@id="componenteReferencia"]/div'
		 String tag="nx-icon"
		 try{
			 selecionaListaDeElementos(xpath,tag)
			 for(int i = 0; i < listOfElements.size(); i++){
				 System.out.println("["+i+"] ["+listOfElements[i].getTagName() +"]" )
				 if(listOfElements[i].getTagName().contains(tag)){
					 listOfElements[i].click()
					 //		break
				 }
			 }
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
			fluxoCompletoEditorCorrespondencia(aba, a)
			reprocessaFalhas(aba, a)
		}
	}
	
	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailEditorCorrespondencia(String tipoAba){
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\EditorCorrespondencia\\ConfiguracoesEditorCorrespondencia.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "EditorCorrespondencia.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
			}
		} catch (Exception e){
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
	public static enviaEmailDEditorCorrespondenciaDinamico(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal, String assunto){
	 try{
		 if(!validaDados(cellCheck)){
			 pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\EditorCorrespondencia\\ConfiguracoesEditorCorrespondencia.txt"
			 lerConfigurações(pathTxtEmail)
			 String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			// FORMATA SAIDA DO TEMPO DE EXECUÇÃO
			long segds = timeLapnse.toSeconds();
			String hours = timeLapnse.toHours()+"h "+
						timer.converteMinutos(timeLapnse.toHours(), timeLapnse.toMinutes())+"m "+
						timer.converteSegundos(timeLapnse.toMinutes(),segds)+"s";
			// ENVIO DE E-MAIL
			//String emaildestination = "denilson.inmetricsext@allpronet.com.br"
			initDataFiles();
			// ENVIA E-MAIL DINAMICO
			emailDinamico.enviaEmailDinamico(sender, emaildestination, tipoAba, path, docName, emailDinamico.listaDeAlertas(data, cellCheckAlert)  , hours, horaInicial, horaFinal,  GetHost.getHostname() , dataInicio, dataFinal, assunto);
		 }
	  } catch (Exception e){}
	}
	
	
	
}

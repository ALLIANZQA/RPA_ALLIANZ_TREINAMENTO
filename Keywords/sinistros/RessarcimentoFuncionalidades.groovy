package sinistros

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

public class RessarcimentoFuncionalidades extends rpaCommands{
	
	private static TestData data
	private static TestData dataOperacao
	private static String testDataPath = 'Data Files/Sinistro/Ressarcimento/RessarcimentoData'
	private static String path
	private static String alerta
	private static String retorno
	private static int cellCheck = 6;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "Ressarcimento.xlsx"
	private static int cellCheckAlert = 7;
	private static boolean enviado = true;
	private static String xpath;
	private static String tela;
	private static boolean erroTela = false;
	private static String msgAlerta
	private static String status
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoRessarcimento(String aba, String a){
		System.out.println()
		String dataInicio = ExtractDates.dataAtual();
		String horaInicial =  ExtractDates.horaAtualizada();
		start = Instant.now();
		initDataFiles()
		fluxoCompletoRessarcimento(aba,a)
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
	public static fluxoCompletoRessarcimento(aba,a){
		System.out.println()
		try{
			if(validaDados(cellCheck)){
				init(aba,a);
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					enviado = true;
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						if(ressarcimento(registros,aba,a)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado" , 5, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta , 6, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 5, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: "+alerta, 6, aba);
							} catch (Exception e){}
						}
					}
				}
			} else {
				System.err.println('Não existem dados para processar')
			}
		} catch (Exception e){}
		  WebUI.closeBrowser();
		  init(aba,a);
	}
	
	/**
	 * @author be54955
	 * @return
	 */
	public static boolean ressarcimento(List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado = true;
		String msgAlert;
		String tela = true;
		boolean situacao;
		
		try{
	//		MenuMatriz.producaoSinistro();
	//		MenuMatriz.subMenuNome("a","GIS")
	//		alteraJanejaGenerica(1, 5)
			tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(tela)
			if(listaDeTelas(tela)){
				WebUI.delay(3);
				//Referência
				preencheXpath('//*[@id="refsiniestro"]', registros[1], 0)
				//Pesquisar
				clicarEmElementoDaTela('//*[@id="cellTarget_busqueda"]', 0);
				WebUI.delay(10);
				alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Alerta ["+alerta+"]")
				if(!alertListBlocList(alerta)){
				  situacao = recuperaValorInput('//*[@id="divAppContent"]', "input", "Aberto")
				  if(situacao){
					System.out.println()
				    //MENU
				    selecionaItemLista("Prestadores",'//*[@id="tableOptMenu_"]' , "td" )
					WebUI.delay(3);
					//Emitir
					selecionaItemLista("Emitir",'//*[@id="SectionDetectivesSectionBar"]/table/tbody/tr/td/table' , "div" )
					alteraJanejaGenerica(2, 10)
					//TIPO PRESTADOR
					selecionaItemLista(registros[2],'//*[@id="supplierType"]' , "option" )
					//RESSARCIMENTO
					selecionaItemLista(registros[3],'//*[@id="orderType"]' , "option" )
					//OBSERVAÇÕES
					preencheXpath('//*[@id="observations"]', registros[4], 0)
					//ALOCAÇÃO
					selecionaItemLista("Alocação",'//*[@id="SectionASectionBar"]/table/tbody/tr/td/table' , "div" )
					System.out.println()
					//ACEITAR
					selecionaItemLista("Aceitar",'//*[@id="SectionASectionBar"]/table/tbody/tr/td/table' , "div" )
					System.out.println()
					alteraJanejaGenerica(1, 5)
					alerta = selecionaItemPendenteLista("Pendente",'//*[@id="tablaDetectives_tbody"]' , "tr" )
					System.out.println(alerta)
					enviado = true;
					System.out.println()
					
				  } else {	
				    enviado = false
				    alerta="Sinistro Fechado";
				    status = "Processado"
				  }
				} else {
				  enviado = false
				  status = "Processado"
				}
			} else {
				enviado = false
				alerta="Tela 'GESTÃO INTEGRADA DE SINISTROS' não apresentada"
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
			if(!validaDados(cellCheck)){
				String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Sinistros\\Ressarcimento\\ConfiguracoesRessarcimento.txt";
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
			WebUI.delay(20);
			MenuMatriz.producaoSinistro();
			MenuMatriz.subMenuNome("a","GIS")
			alteraJanejaGenerica(1, 5)
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
			fluxoCompletoRessarcimento(aba,a)
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
	
	
	
	
	public static boolean recuperaValorInput(String xpathSubMenu, String tag, String situacao){
	boolean b = false;
	selecionaListaDeElementos(xpathSubMenu,tag)
	    for(int i = 0; i < listOfElements.size(); i++){
		   System.out.println("["+i+"] ["+listOfElements[i].getAttribute("value")+"]" )
		   if(listOfElements[i].getAttribute("value").contains(situacao.toString())){
//			   listOfElements[i].click()
			   b=true;
			   break
		   }
	    }
		return b;
    }
	
	public static String selecionaItemPendenteLista(String tipo,String xpath , String tag ){
		String t;
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		System.out.println("[TIPO  ["+tipo+"]")
		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println("["+i+"] Attr ["+listOfElements[i].getAttribute('value')+"] Text ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains(tipo.toString().trim())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				t = listOfElements[i].getText()
				break
			}
		}
		return t;
		WebUI.delay(2)
	}
	
	
	
	
	
}

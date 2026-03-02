package backoffice

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.time.Duration
import java.time.Instant
import java.util.List

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
import sendEmail.emailDinamico;
import auxiliares.timer;
import auxiliares.GetHost;
import auxiliares.CurrencyMonetary;

public class BoletoAvulsoFuncionalidades extends rpaCommands {

	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/BoletoAvulso/BoletoAvulsoData'
	private static String path
	private static String alerta
	private static int cellCheck = 8
	private static String filepath = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\BoletoAvulso\\arquivo\\"
	private static String completeFilePath

	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName =  "BoletoAvulso.xlsx"
	private static int cellCheckAlert = 10;
	private static boolean enviado = true;
	private static String xpath;
	private static String tela;
	private static boolean erroTela = false;
	private static String msgAlerta
	private static String status
	
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
	 * @param aba
	 * @return
	 */
	public static acessoAmbiente(String aba){
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands(aba);
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoBoletoAvulso(String aba){
		System.out.println()
		String dataInicio = ExtractDates.dataAtual();
		String horaInicial =  ExtractDates.horaAtualizada();
		start = Instant.now();
		initDataFiles()
		fluxoCompletoBoletoAvulso(aba)
		reprocessaFalhas(aba)
		//enviarEmailBoletoAvulso(aba)
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
				String pathTxtEmailConfig ="C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\BoletoAvulso\\ConfiguracoesBoletoAvulso.txt";
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
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoBoletoAvulso(aba){
		try{
			if(validaDados(cellCheck)){
				acessoAmbiente(aba)
				WebUI.delay(10);
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						String arquivo = registros[4].toString()
						if(!validaExistenciaDeArquivo('C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\BoletoAvulso\\arquivo\\'+arquivo+extensaoArquivo(registros))){
							if(exportarArquivo(registros, aba)) {
								try{
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 7, aba);
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 8, aba);
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 9, aba);
									deleteFileDiretory(completeFilePath)
								} catch (Exception e){}
							} else {
								try{
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 7, aba);
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 8, aba);
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: Falha ao anexar arquivo. "+alerta, 9, aba);
								} catch (Exception e){}
							}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 7, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", cellCheck, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: Arquivo "+arquivo+" não encontrado para indexação", 9, aba);
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
	 * @param emaildestination
	 * @param corretor
	 * @param intermediario
	 * @param cpfcnpj
	 * @param user
	 * @param pwr
	 * @param ramo
	 * @return
	 */
	public static boolean exportarArquivo( List<String> registros, String aba){
		WebUI.delay(15);
		System.out.println()
		MenuMatriz.consultasEstatisticas()
		MenuMatriz.subMenuNome('a','Nova Ficha de Gestão')
		boolean enviado
		try{
			alerta=""
			completeFilePath=""
			System.err.println("TELA - FICHA GESTÃO ")
			/**
			 * TELA - FICHA GESTÃO 
			 */
			alteraJanejaGenerica(1, 5)
			System.err.println("alteraJanejaGenerica")
			WebUI.delay(15);
			if(registros[2].toString().contains("Apólice".toString())){
				novaFichaGestao(registros, 0, 1)
			} else {
				novaFichaGestaoCliente(registros, 0)
			}

			//INDEXAÇÃO
			MenuMatriz.indexacaoFichaGestao()

			/**
			 * TELA - ANEXAR DOCUMENTOS
			 */
			//WebUI.delay(5);
			alteraJanejaGenerica(2, 5)
			WebUI.maximizeWindow()

			String ind = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
			System.err.println("["+ind+"]")
			if(ind.contains('Anexar Documentos - LI')){
				//MODELO DESCRIÇÃO
				preencheXpath('//*[@id="codigo"]', registros[3], 1);

				//Confidencial
				switch(registros[3]){
					case '242':
						selecionaItemLista('Não', '//*[@id="idPrivado"]', 'option')
						break;
				}

				//GERAR AVISO - SIM
				clickXpath('//*[@id="sectionAvisosImg"]', 1);
				clickXpath('//*[@id="sectionAvisosImg"]', 1);
				clickXpath('//*[@id="tramitar"]', 6);
				//Utilizador
				preencheXpath('//*[@id="txtUsuario"]', registros[6], 1);
				//BOTÃO GERAR
				clickXpath('//*[@id="imgBuscarUsuario"]', 1);
				WebUI.delay(5);
				telaConsulta(registros)
				WebUI.delay(3);
				//File Path
				String path = '//*[@id="upFile1"]'
				completeFilePath = filepath+registros[4]+extensaoArquivo(registros)
				System.err.println(completeFilePath)
				fileUpload(path, completeFilePath, 5)
				//TEXTO
				preencheXpath('//*[@id="texto"]', registros[5], 5);
				if(registros[2].toString().contains("Cliente".toString())){
					//Data Aviso
					preencheXpath('//*[@id="idFechaAviso"]',ExtractDates.dataFutura(1), 2);
				}

				//Salvar
				clickXpath('//*[@id="o_5"]', 0);
				WebUI.delay(2);
				alerta = pegaMSGAlerta()
				WebUI.delay(2);
				fechaAlerta();
				WebUI.delay(5);
				alteraJanejaGenerica(1, 5)
				String s = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','innerText', 3)
				System.err.println("["+s+"]")
				if(alertListBlocList(s)){
					alerta = s;
					//Cancelar
					selecionaItemLista('Cancelar','//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table' , 'div' )
					fechaAlerta();
					enviado = false
					//alteraJanejaGenerica(1, 5)
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 5)

				} else {
					WebUI.delay(5);
					//Fecha Alerta
					fechaAlerta();
					enviado = true
					System.err.println("Indexado ["+enviado+"]")
					//alteraJanejaGenerica(1, 5)
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 5)
				}

			} else {
				enviado = false
				alteraJanejaGenerica(0, 5)
			}
		} catch (Exception e){
			enviado = false
			//FECHAR -ANEXAR DOCUMENTOS - LI
			clickXpath('//*[@id="lnkTopLink_Close"]', 5);
			fechaAlerta();
			alteraJanejaGenerica(1, 5)
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0, 5)
		}
		return enviado
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param valor
	 * @return
	 */
	public static telaConsulta(List<String> registros){
		try{
			String BE = registros[6]
			WebUI.delay(5);
			alteraJanejaGenerica(3, 5)
			String xpath = '//*[@id="tblHeaderArea"]/tbody/tr[2]'
			String tag ="td";
			WebUI.delay(2)
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
				if(listOfElements[i].getText().contains("CONSULTAS".toString())){
					String xpathTable = '//*[@id="sectionDatosContent"]/div[1]'
					String tagTable ="tr";
					WebUI.delay(2)
					selecionaListaDeElementos(xpathTable,tagTable)
					for(int j = 0; j < listOfElements.size(); j++){
						System.err.println("["+j+"] ["+listOfElements[j].getText()+"] BE ["+BE.toUpperCase()+"]")
						if(listOfElements[j].getText().contains(BE.toUpperCase())){
							listOfElements[j].click()
							break
						}
					}
					alteraJanejaGenerica(2, 5)
					break
				}
			}
		} catch (Exception e){}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param valor
	 * @return
	 */
	public static boolean msgAlerta(List<String> registros){
		boolean alerta
		try{
			String xpath = '//*[@id="dataErrorSection"]/table/tbody/tr'
			String tag ="td";
			WebUI.delay(2)
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
				if(listOfElements[i].getText().contains("ERRO VALIDAÇÃO:Indique um Empregado.".toString())){
					//Utilizador
					preencheXpath('//*[@id="txtUsuario"]', registros[6], 1);
					//BOTÃO GERAR
					clickXpath('//*[@id="imgBuscarUsuario"]', 1);
					WebUI.delay(3);
					//Salvar
					clickXpath('//*[@id="o_5"]', 5);
					alerta = true
					break
				}
				if(listOfElements[i].getText().contains("Não é possível indexar. Contacte com o Centro de Atenção ao Cliente.".toString())){
					alerta = "Não é possível indexar. Contacte com o Centro de Atenção ao Cliente."
					WebUI.delay(3);
					//Cancelar
					clickXpath('//*[@id="o_6"]', 5);
					alerta = false
					break
				} else {
					alerta = true
				}


			}
		} catch (Exception e){
			alerta = true
		}
		return alerta
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaProcesso(String processo){
		String xpath = '//*[@id="tipoReferencia"]'
		String tag ="option";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().contains(processo.toString())){
				listOfElements[i].click()
				break
			}
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaTipoProcesso(String processo){
		String xpath = '//*[@id="referencia_tipoDoc"]'
		String tag ="option";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().contains(processo.toString())){
				listOfElements[i].click()
				break
			}
		}
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailBoletoAvulso(String tipoAba){
		initDataFiles()
		if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\BoletoAvulso\\ConfiguracoesBoletoAvulso.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = "BoletoAvulso.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos, tipoAba)
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
	public static reprocessaFalhas(String aba){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoBoletoAvulso(aba)
			reprocessaFalhas(aba)
		}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @return
	 */
	public static String extensaoArquivo(List<String> registros){
		String e
		if(registros[2].toString().contains("Apólice")){
			e='.pdf'
		} else {
			e='.JPG'
		}
		System.out.println("Arquivo "+e)
		return e
	}
}

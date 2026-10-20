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
import auxiliares.timer;
import sendEmail.emailDinamico;
import auxiliares.GetHost;
import auxiliares.leitorPdf;

public class ComunicacaoBloqueioFuncionalidades extends rpaCommands{

	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/ComunicacaoBloqueio/ComunicacaoBloqueioData'
	private static String path
	private static String alerta
	private static int cellCheck = 23
	private static String nota = "não verificar "
	private static String status
	private static String referencia
	
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static int cellCheckAlert = 24;
	

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
	 * @author be54955
	 * @param aba
	 * @param a
	 * @return
	 */
	public static init(String aba, String a){
		try{
			acessoAmbiente(aba,a)
			WebUI.delay(10);
			MenuMatriz.newFluxoGestaoNome()
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
	public static fluxoComunicacaoBloqueio(String aba, String a){
		
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		initDataFiles()
		fluxoCompletoComunicacaoBloqueio(aba,a)
		reprocessaFalhas(aba,a)
		FileInput.atualizaLog(Instant.now().toString(), pathFile)
		List<String> times = rpaCommands.convertToInstant(pathFile);
		String dataInicio = times[0];
		String horaInicial =  times[1];
		timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
		   enviaEmailComunicacaoBloqueiooDinamico(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
		} catch (Exception e){
		}
		
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoComunicacaoBloqueio(String aba, String a){
		try{
			if(validaDados(cellCheck)){
				init(aba,a)
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					alerta = ''
					if (data.getValue(cellCheck, i).equals('Processar')) {
						referencia = ""
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						if(bloqueio(registros, aba, a)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 22, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, StringFormatter.startWhitch(referencia,"de") , 23, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 22, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, +"Alerta: "+alerta, 23, aba);
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
	public static boolean bloqueio(List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado
		String msg
		String alertaFG
		try{
			alteraJanejaGenerica(1, 10)
			WebUI.maximizeWindow()

			//FICHA GESTÃO
			String telaFichaGestao = recuperaValor('/html/body/div/div/app-root/file-management/app-header/div/nx-ibl-header/div/div/div[1]', 'innerText', 1)
			System.out.println(telaFichaGestao )
			if(telaFichaGestao.contains("Ficha de Gestão") || telaFichaGestao.contains("Ficha Gestión") ){
				/**
				 * Nova Ficha Gestão
				 * Recebe : lista, index proposta, index apolice 
				 * 
				 */
				novaFichaGestaoProcesso(registros,1,2, registros[0]+a)
				WebUI.delay(5);
				alertaFG = recuperaValor('//*[@id="componenteReferencia"]/div/div[2]/div/nx-message/div', 'innerText', 1)
				System.out.println(alertaFG)
				if(!verificaPendenciaSelectItem(nota)){
					if(!alertListBlocList(ajusteMSGFG(alertaFG))){
						WebUI.delay(10);
						//EDITOR CORRESPONDÊNCIA - Ficha Gestão
						MenuMatriz.subMenu('/html/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div', 'span','Editor correspondencia')
						alteraJanejaGenerica(2, 10)
						String telaGestao
						String telaEditor = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
						System.out.println(telaEditor)
						WebUI.delay(10);
						if(telaEditor.contains("EDITOR CORRESPONDÊNCIA")){
							try{
								//Meio de Envio
								selecionaItemListaContains(registros[14], '//*[@id="cbMedioEnvio"]' , 'option')
								//Tipo Destino
								selecionaItemListaContains(registros[15], '//*[@id="cbTipoDest"]' , 'option')
								//Aceitar
								clickXpath('//*[@id="btMainAcceptar"]', 1);
								//Ao cuidado de
								preencheXpath('//*[@id="txtAtencion"]', registros[21],0);
								//CC
								preencheXpath('//*[@id="txtEmailCC"]', registros[17],0);
								//CCO
								preencheXpath('//*[@id="txtEmailCCO"]', registros[18],0);
								//Continuar
								clickXpath('//*[@id="btContinuar"]', 1);
								WebUI.delay(15);
								String alert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 2)
								if(!alertListBlocList(alert)){
									//Modelo/Descrição
									preencheXpath('//*[@id="numModCodi"]', registros[19],0);
									//Aceitar
									clickXpath('//*[@id="o_5"]', 1);
									referencia = recuperaValor('//*[@id="lbOperacion"]', 'innerText', 2)
									System.out.println("["+referencia+"]")
									System.out.println()
									//Aceitar
									clickXpath('//*[@id="o_5"]', 1);
									//Seleciona Parágrafo
									selecionaParagrafo(registros[20])
									//Gravar
									clickXpath('//*[@id="btGrabar"]', 1);
									enviado = true
									status = "Processado"
									alteraJanejaGenerica(1, 2)
									MenuMatriz.inicioFichaGestao()
									WebUI.delay(2);
									System.out.println()
								} else {
									enviado = false
									alerta=alert
									status = "Processado"
									//home
									clickXpath('//*[@id="o_3"]', 1)
									fechaAlerta()
									fecharJanelaAtual(1)
									alteraJanejaGenerica(0, 2)
									MenuMatriz.newFluxoGestao('/html/body/div[1]/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]')
									WebUI.delay(10);
								}


							} catch (Exception e){
								enviado = false
								alerta="Falha ao preencher dados na tela 'EDITOR CORRESPONDÊNCIA'"
								status = "Processado"
							}
						} else {
							enviado = false
							alerta="Tela 'EDITOR CORRESPONDÊNCIA' não apresentada"
							status = "Processar"
						}
						//ALERTA FG
					} else {
						enviado = false
						status = "Processado"
						alerta = alertaFG
						MenuMatriz.inicioFichaGestao()
					}

				} else {
					alerta  = retornaLinhaTabelaFichaGestao(indexPendenciaSelectItem(nota))
					System.out.println("["+alerta+"]")
					registros[11] = alerta
					enviado = true
					status = "Processado"
					//alerta = selecionaPendencia(registros[7])
					alteraJanejaGenerica(1, 2)
					MenuMatriz.inicioFichaGestao()
					WebUI.delay(2);
				}
				//TELA FICHA GESTÃO - ELSE
			} else {
				enviado = false
				alerta="Tela 'Ficha de Gestão' não apresentada"
				status = "Processar"
				alteraJanejaGenerica(0, 10)
				WebUI.closeBrowser();
				init(aba, a)
			}
		} catch (Exception e) {
			enviado = false
			status = "Processado"
			alerta="Falha ao preencher 'Ficha de Gestão'"
			alteraJanejaGenerica(0, 10)
			WebUI.closeBrowser();
			init(aba, a)
		}


		return enviado
	}


	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarComunicacaoBloqueio(String tipoAba){
		initDataFiles()
		if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\ComunicacaoBloqueio\\ConfiguracoesComunicacaoBloqueio.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = "ComunicacaoBloqueio.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba, String a){
		System.err.println('Reprocessa Falhas')
		try{
			initDataFiles()
			if(validaDados(cellCheck)){
				fluxoCompletoComunicacaoBloqueio(aba,a)
				reprocessaFalhas(aba)
			}
		} catch (Exception e){
			reprocessaFalhas(aba, a)
		}
	}


	/**
	 * @author be54955
	 * 
	 * @param b
	 * @return
	 */
	public static selecionaParagrafo(String b){
		System.out.println("["+b.length()+"] ["+b+"]")
		if(b.length()>0){
			List<String> registros = new ArrayList<>();
			String[]t = b.split("-")
			int j = 0
			for(int i = 0; i < t.size(); i++){
				System.out.println("["+i+"] ["+t[i]+"]")
				selecionaItemListaContains(t[i].toString(), '//*[@id="rightcolumn"]' , 'b')
			}
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
	public static enviaEmailComunicacaoBloqueiooDinamico(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
		System.out.println()
	 try{
		 if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\ComunicacaoBloqueio\\ConfiguracoesComunicacaoBloqueio.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			String docName = "ComunicacaoBloqueio.xlsx"
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






}

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


public class EnvioPendenciaGOInspecaoFuncionalidades extends backofficeFuncionalidades{

	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/PendenciaGOInspecao/PendenciaGOInspecaoData'
	private static String path
	private static String alerta
	private static int cellCheck = 10
	private static String filepath = "C:\\Users\\Public\\"
	private static texto
	private static String status
	private static String nota = "Demais Bloqueios BE53298"
	
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	

	private static String docName = "PendenciaGOInspecao.xlsx"
	private static pathTxtEmailDinamico = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\EnvioRetornoGo\\ConfiguracoesEnvioRetornoGoFinalProcesso.txt"
	private static int cellCheckAlert = 12;


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
	 * @return
	 */
	public static fluxoEnvioPendenciaGOInspecao(String aba){
		
		String dataInicio = ExtractDates.dataAtual();
		String horaInicial =  ExtractDates.horaAtualizada();
		start = Instant.now();
		initDataFiles()
		fluxoCompletoEnvioPendenciaGOInspecao(aba)
		reprocessaFalhas(aba)
		//enviaEmailEnvioPendenciaGOInspecao(aba)
	
		end =  Instant.now();
		timeLapnse = Duration.between(start, end);
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
		   enviaEmailDuplicadoRecibosDinamico(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
		} catch (Exception e){
		}
		
	}

	
	public static long converteMinutos(long h, long ms){
		long v;
		if(h>0){
			long m = h*60;
			long r = ms-m;
			v = r;
		} else {
		v = ms;
		}
		return v;
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoEnvioPendenciaGOInspecao(aba){
		if(validaDados(cellCheck)){
			iniciaAplicacaoMatriz('IntranetProdução');
			WebUI.delay(15);
			realizaLoginIntraNetProducao(aba);
			WebUI.delay(15);
			MenuMatriz.newFluxoGestao('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[2]/div/div[1]/ul');
			WebUI.delay(10);

			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				if (data.getValue(cellCheck, i).equals('Processar')) {
					status = "Processar"
					alerta = ""
					List<String> registros = new ArrayList<>();
					lista( registros , i)
					if(inspecao(registros, aba)) {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", Integer.parseInt(registros[10]), path, status, 9, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", Integer.parseInt(registros[10]), path, alerta.substring(16), 11,  aba);
						} catch (Exception e){}
					} else {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", Integer.parseInt(registros[10]), path, status, 9, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", Integer.parseInt(registros[10]), path, "Alerta: "+alerta, 11,  aba);
						} catch (Exception e){}
					}
				}
			}
		} else {
			System.err.println('Não existem dados para processar')
		}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param aba
	 * @return
	 */
	public static boolean inspecao( List<String> registros, String aba){
		System.out.println()
		boolean enviado
		String msg
		String alertaFG
		try{
			alteraJanejaGenerica(1, 2)
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
				novaFichaGestaoProcesso(registros,0,1, registros[8])
				WebUI.delay(5);
				alertaFG = recuperaValor('//*[@id="componenteReferencia"]/div/div[2]/div/nx-message/div', 'innerText', 1)
				System.out.println("alertaFG ["+alertaFG+"]")
				if(!verificaPendenciaSelectItem(nota)){
					if(!alertListBlocList(ajusteMSGFG(alertaFG))){
						//WebUI.delay(10);
						//Nota/Agenda - Ficha Gestão
						MenuMatriz.subMenu('/html/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div', 'span','Nota/Agenda')
						alteraJanejaGenerica(2, 2)
						String telaGestao
						System.err.println("Ramo ["+registros[2]+"] Nota ["+registros[7]+"]")
						//trocaJanela('Editor de Notas/Agenda')
						//WebUI.delay(20);
						String telaEditor = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
						System.out.println(telaEditor)
						//WebUI.delay(10);
						if(telaEditor.contains("EDITOR DE NOTAS/AGENDA")){
							//Nota
							preencheXpath('//*[@id="numModCodi"]', registros[7],0);
							//ACEITAR
							clickXpath('//*[@id="btnAceptar"]', 1);
							WebUI.delay(5);
							alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 2)
							if(!alertListBlock(alerta )){
								//Gravar
								clickXpath('//*[@id="o_11"]', 2);
								//GESTÃO PROCESSOS
								alteraJanejaGenerica(1, 2)
								String telaGestaoConfirmacao = recuperaValor('/html/body/div/div/app-root/file-management/app-header/div/nx-ibl-header/div/div/div[1]', 'innerText', 1)
								System.out.println(telaGestaoConfirmacao)
								if(telaGestaoConfirmacao.contains("Ficha de Gestão") || telaGestaoConfirmacao.contains("Ficha Gestión") ){
									alerta = ""
									enviado = true
									alerta = selecionaPendencia(registros[7])
									registros[11] = alerta
									//System.out.println(registros[11])
									alteraJanejaGenerica(1, 2)
									MenuMatriz.inicioFichaGestao()
									WebUI.delay(2);
									status = "Processado"
								} else {
									alerta="Tela de confirmação não apresentada"
									enviado = false
									status = "Processar"
								}
							} else {
								alerta = " FAIL"
							}
						} else {
							enviado = false
							alerta="Tela 'EDITOR DE NOTAS/AGENDA' não apresentada"
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
				WebUI.closeBrowser();
				iniciaAplicacaoMatriz('IntranetProdução');
				realizaLoginIntraNetProducao(aba);
				WebUI.delay(15);
				MenuMatriz.newFluxoGestao('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[2]/div/div[1]/ul');
				WebUI.delay(20);
			}
		} catch (Exception e) {
			enviado = false
			iniciaAplicacaoMatriz('IntranetProdução');
			realizaLoginIntraNetProducao(aba);
			WebUI.delay(15);
			MenuMatriz.newFluxoGestao('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[2]/div/div[1]/ul');
			WebUI.delay(30);
		}
		return enviado
	}

	/**
	 * @author be54955
	 * @param nota
	 * @return
	 */
	public static String selecionaPendencia(String nota){
		//	WebUI.delay(1);
		System.err.println()
		String xpath = '//*[@id="tablaLista"]/app-list-file/div/div/div/table'
		String tag = 'tr'
		String linha
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			//System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains(nota.toString())){
				System.err.println("FOUND NOTA ["+i+"] ["+listOfElements[i].getText()+"]")
				String valor = listOfElements[i].getText().toString()
				linha = valor.replace('/(\r\n|\n\r)/gm', " ")
				//System.err.println("LINHA NOTA ["+i+"] ["+listOfElements[i].getText()+"]")
				break
			}
		}
		return linha
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
			fluxoCompletoEnvioPendenciaGOInspecao(aba)
			reprocessaFalhas(aba)
		}
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailEnvioPendenciaGOInspecao(String tipoAba){
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\EnvioRetornoGo\\ConfiguracoesEnvioRetornoGoFinalProcesso.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "PendenciaGOInspecao.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
			}
		} catch (Exception e){
		}
	}

	/**
	 * @author be54955
	 * @param alert
	 * @return
	 */
	public static boolean alertListBlock(String alert){
		System.err.println("\nalertListBloc ["+alert+"]\n")
		boolean r = false
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add(" Código de UT inexistente")


		System.out.println("Alert ["+alert+"]\n")
		for(int i = 0; i < listOfAlerts.size(); i++){
			System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
			if(listOfAlerts[i].contains(alert.toString())){
				System.err.println("FOUND ALERT TO BLOC ["+i+"] ["+listOfAlerts[i]+"]")
				r = true
				break
			}
		}
		System.out.println("\nAlert ["+r+"]")
		return  r
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
	public static enviaEmailDuplicadoRecibosDinamico(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
	 try{
		 if(!validaDados(cellCheck)){
			//lerConfigurações(pathTxtEmailDinamico);
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\EnvioRetornoGo\\ConfiguracoesEnvioRetornoGoFinalProcesso.txt"
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			// FORMATA SAIDA DO TEMPO DE EXECUÇÃO
			long segds = timeLapnse.toSeconds();
			String hours = timeLapnse.toHours()+"h "+
						timer.converteMinutos(timeLapnse.toHours(), timeLapnse.toMinutes())+"m "+
						timer.converteSegundos(timeLapnse.toMinutes(),segds)+"s";
			// ENVIO DE E-MAIL
			//String emaildestination = "denilson.inmetricsext@allpronet.com.br"
			//DADOS DO DOCUMENTO
			//TestData data = findTestData(testDataPath);
			initDataFiles();
			// ENVIA E-MAIL DINAMICO
			emailDinamico.enviaEmailDinamico(sender, emaildestination, tipoAba, path, docName, emailDinamico.listaDeAlertas(data, cellCheckAlert)  , hours, horaInicial, horaFinal,  GetHost.getHostname() , dataInicio, dataFinal, "");
		 }
	  } catch (Exception e){}
	}

}

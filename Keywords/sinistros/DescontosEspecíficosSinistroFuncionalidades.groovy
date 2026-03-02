package sinistros

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.Duration
import java.time.Instant

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

import internal.GlobalVariable
import rpacommands.rpaCommands

import auxiliares.switchAtualizarExcel
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.MenuMatriz
import auxiliares.ExtractDates


import auxiliares.timer;
import auxiliares.ExtractDates
import sendEmail.emailDinamico;
import auxiliares.GetHost;

public class DescontosEspecíficosSinistroFuncionalidades extends rpaCommands{

	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Sinistro/DescontosEspecíficosSinistroData'
	private static String path
	private static String confirmacaoReserva
	private static String alerta
	private static String evidencia
	private static int cell = 12
	private static boolean encerrado  = false
	private static String cobertura = "49 Ressarcimento"
	private static String status = "";
	private static String baseData;
	
	private static int cellCheckAlert = 13;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	


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
	public static boolean carregaDados(){
		boolean carregar = false
		initDataFiles()
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if(data.getValue(cell, i).equals("Processar")){
				System.err.println(i+' - Status - '+ data.getValue(22, i))
				carregar = true
				break
			}
		}
		System.err.println('carregaDados ['+ carregar+']')
		return carregar
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static lista( List<String> sinistro , int v){
		for (int i = 1; i <= data.getColumnNumbers(); i++) {
			int d = i -1
			if(i==3){
				sinistro[d] = ExtractDates.dateFormatteTypes("dd/MM/yyyy", "ddMMyyyy", data.getValue(i, v) )
			} else if(i==5){
				sinistro[d] = data.getValue(i, v).toUpperCase().trim()
			} else if(i==12){
				sinistro[d] = data.getValue(i, v).replaceAll("[R\$.,]","").trim()
			} else if(i==16){
				sinistro[d] = data.getValue(i, v)
			} else {
				sinistro[d] = data.getValue(i, v)
			}
		}

		for (int j = 0; j < sinistro.size();j++ ){
			System.err.println("["+j+"] [ "+sinistro[j]+"]")
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param aba
	 * @return
	 */
	public static fluxoDescontosEspecíficosSinistros(String tipoAba){
		String dataInicio = ExtractDates.dataAtual();
		String horaInicial =  ExtractDates.horaAtualizada();
		start = Instant.now();
		fluxoCompletoDescontosEspecíficosSinistro(tipoAba)
		reprocessaDescontosEspecíficosSinistro(tipoAba)
		end =  Instant.now();
		timeLapnse = Duration.between(start, end);
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		
		try{
		   enviarEmailDescontosEspecíficosSinistroDinamico(tipoAba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal, baseData );
		} catch (Exception e){
		}
	}

	/**
	 * @
	 * @param tipoAba
	 * @return
	 */
	public static fluxoCompletoDescontosEspecíficosSinistro(String tipoAba){
		try{
			initDataFiles()
			if(carregaDados()){
				iniciaAplicacaoMatrizCommands('IntranetProdução');
				realizaLoginIntraNetProducaoCommands(tipoAba);
				//realizaLoginTelaIntraNetProducao('be02059','Barba888');
				WebUI.delay(15);
				System.out.println()
				menuGIS()
				WebUI.delay(15);

				/**
				 * TELA GESTÃO INTEGRADA
				 */
				alteraJanejaGenerica(1,2);
				WebUI.maximizeWindow()

				for (int i = 1; i <= data.getRowNumbers(); i++) {
					if(data.getValue(cell, i).equals("Processar")){
						confirmacaoReserva = ''
						alerta = ""
						evidencia=""
						encerrado = false
						status=""
						try{
							List<String> sinistro = new ArrayList<>();
							lista(sinistro,i)
							sinistro[14] = '49 Ressarcimento'
							sinistro[15] = '05425'
							sinistro[16] = '000'
							sinistro[17] = 'Rua'
							sinistro[18] = 'Local 1'
							baseData = sinistro[2]
							if(sinistro[10].toString().contains('(DESC. ESPEC.)')){
								if(fluxoDescontosEspecíficosSinistro(sinistro)){
									try{
										auxiliares.WriteExcelFactory.updateSheetLine('',  i-1, path,status, 11 , 'DescontosEspecíficosSinistro');
										auxiliares.WriteExcelFactory.updateSheetLine('', i-1, path, evidencia, 12 , 'DescontosEspecíficosSinistro');
									}catch (Exception e){System.err.println("[ Cannot invoke method setCellValue() on null object ]")}
								} else {
									try{
										auxiliares.WriteExcelFactory.updateSheetLine('',  i-1, path, status, 11 , 'DescontosEspecíficosSinistro');
										auxiliares.WriteExcelFactory.updateSheetLine('', i-1, path, "Alerta: "+alerta, 12 , 'DescontosEspecíficosSinistro');
										System.out.println()
										alteraJanejaGenerica(0,2);
										menuGIS()
										alteraJanejaGenerica(1,2);
										WebUI.maximizeWindow()
									}catch (Exception e){}
								}
							} else {
								if(fluxoCancelaDescontosEspecíficosSinistro(sinistro)){
									try{
										auxiliares.WriteExcelFactory.updateSheetLine('',  i-1, path, "Processado", 11 , 'DescontosEspecíficosSinistro');
										auxiliares.WriteExcelFactory.updateSheetLine('', i-1, path, sinistro[22].toString(), 12 , 'DescontosEspecíficosSinistro');
									}catch (Exception e){System.err.println("[ Cannot invoke method setCellValue() on null object ]")}
								} else {
									try{
										auxiliares.WriteExcelFactory.updateSheetLine('',  i-1, path, "Processado", 11 , 'DescontosEspecíficosSinistro');
										auxiliares.WriteExcelFactory.updateSheetLine('', i-1, path, "Alerta: Reserva não cancelada", 12 , 'DescontosEspecíficosSinistro');
										System.out.println()
										alteraJanejaGenerica(0,2);
										menuGIS()
										alteraJanejaGenerica(1,2);
										WebUI.maximizeWindow()
									}catch (Exception e){}
								}
							}
						}catch (Exception e){  }
					}
				}
				WebUI.closeBrowser();
			}
		} catch (Exception e){
			WebUI.closeBrowser();
			initDataFiles()
			
		}
	}

	/**
	 * @author be54955
	 */
	public static boolean fluxoDescontosEspecíficosSinistro(List<String> sinistro){
		boolean exec
		System.out.println()
		try{
			if(gestaoIntegradaDeSinistros(sinistro)){
				exec = true
			} else {
				exec = false
			}
		} catch (Exception e){
			exec = false
		}
		return exec
	}

	/**
	 * @author be54955
	 */
	public static boolean gestaoIntegradaDeSinistros(List<String> sinistro){
		boolean b = true
		try{
			if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0))){
				System.out.println()
				/**
				 * GESTÃO INTEGRADA DE SINISTROS
				 */
				//Referência
				preencheXpath('//*[@id="refsiniestro"]',sinistro[0], 2);
				//Pesquisar
				clickXpath('//*[@id="cellTarget_busqueda"]', 0);
				WebUI.delay(2);

				String situacao = recuperaValor('//*[@id="sectionDatosGenContent"]/table[1]/tbody/tr/td[2]/table/tbody//input', 'value', 2)
				System.out.println("Situação ["+situacao+"] ["+recuperaSituacao()+"]")

				try{
					descontosEncerrados(sinistro)
				} catch (Exception e){

				}

				//Menu
				MenuMatriz.subMenu('//*[@id="cellOptionMenuAppNaviHeaderFilledArea"]', 'td','Dados Econômicos')
				WebUI.delay(2);

				if(descontosAbertos( sinistro)){
					status ="Processado";
				} else {
					alerta = 'Alerta: Reserva não efetuada'
					b = false
				}
				System.out.println("["+b+"]")
				status ="Processado";
			} else {
				alerta = 'Alerta: tela "GESTÃO INTEGRADA DE SINISTROS" não apresentada'
				status ="Processar";
				b = false
			}
		} catch (Exception e){
		       
		}
	}

	/**
	 * @author be54955
	 */
	public static boolean fluxoCancelaDescontosEspecíficosSinistro(List<String> sinistro){
		boolean exec
		System.out.println()
		try{
			/**
			 * GESTÃO INTEGRADA DE SINISTROS
			 */
			//Referência
			preencheXpath('//*[@id="refsiniestro"]',sinistro[0], 2);
			//Pesquisar
			clickXpath('//*[@id="cellTarget_busqueda"]', 5);
			WebUI.delay(2);
			String situacao = recuperaValor('//*[@id="sectionDatosGenContent"]/table[1]/tbody/tr/td[2]/table/tbody//input', 'value', 2)
			System.out.println("Situação ["+situacao+"] ["+recuperaSituacao()+"]")
			//Menu
			MenuMatriz.subMenu('//*[@id="cellOptionMenuAppNaviHeaderFilledArea"]', 'td','Dados Econômicos')
			WebUI.delay(2);
			//sinistro[19]= '17/11/2021'
			sinistro[20]= 'Ressarcimento'
			sinistro[21]= 'BE54097'
			exec = cancelaDescontosAbertos(sinistro)
			WebUI.delay(3);
			System.out.println("["+exec+"]")
			fechaAlerta()
			WebUI.delay(3);
			capturaAlerta(sinistro)
			//HOME
			clickXpath('//*[@id="imgOptMenuBackHome"]', 1);
		} catch (Exception e){
			exec = false
		}
		return exec
	}

	/**
	 * @author be54955
	 * @param sinistro
	 * @return
	 */
	public static descontosEncerrados(List<String> sinistro){
		System.out.println()
		//Reabrir
		boolean a = selecionaItemLista('Reabrir', '//*[@id="FichaSiniestroViewForm"]/table/tbody/tr[2]/td/table', 'div')
		System.out.println(a)
		if(a) {
			encerrado = true
		}
		//Aceitar
		boolean b = selecionaItemLista('Aceitar', '//*[@id="reaperturaSectionBar"]/table/tbody/tr/td/table', 'div')
		System.out.println(b)
		if(b){
			encerrado = true
		}
	}

	/**
	 *
	 * @param sinistro
	 * @return
	 */
	public static boolean descontosAbertos(List<String> sinistro){
		System.out.println()
		boolean exec
		if(selecionarRessarcimento(sinistro[8], sinistro[14])){
			WebUI.delay(2);
			//Aceitar
			selecionaItemLista('Aceitar', '//*[@id="SectionGarantiasSectionBar"]/table/tbody/tr/td', 'div')
			WebUI.delay(2);
			alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 2)
			System.out.println(alerta)
			if(!alertListBlocListDecEsp(alerta)){
				System.out.println("Encerrar "+encerrado+"]")
				//Reembolsar
				selecionaItemLista('Reembolsar', '//*[@id="SectionPagosRecobrosSectionBar"]/table/tbody/tr/td', 'div')
				WebUI.delay(2);
				//Encerrar Sinistro
				if(encerrado){
					clickXpath('//*[@id="cerrarSiniestro"]', 5);
				}
				//Reembolsar de
				selecionaItemLista('Outro', '//*[@id="pagoa"]', 'option')
				//Reembolsado de
				preencheXpath('//*[@id="nombre"]',sinistro[7], 0);
				//CNPJ/CPF
				selecionaItemLista('CPF / CNPJ', '//*[@id="dniafavor_tipoDoc"]', 'option')
				preencheXpath('//*[@id="dniafavor_doc"]',sinistro[6], 0);
				commandTAB('//*[@id="dniafavor_doc"]')
				
//				alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 3)
//				if(alertListBlocList(alerta)){
//					 evidencia = alerta;
//					 exec = false
//					 //Home
//					 clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
//				} else {
				
				//Endereço
				selecionaItemLista(sinistro[17], '//*[@id="domiciliotype"]', 'option')
				preencheXpath('//*[@id="domicilioaddressPart2"]',sinistro[18], 0);
				//CEP
				preencheXpath('//*[@id="cp"]',sinistro[15], 0);
				preencheXpath('//*[@id="subCp"]',sinistro[16], 0);
				//Forma
				selecionaItemLista('Conta Central', '//*[@id="forma"]', 'option')
				//Cobertura  49 Ressarcimento
				selecionaItemLista(cobertura, '//*[@id="comboGarantias"]', 'option')
				//Custo
				preencheXpath('//*[@id="coste"]',sinistro[8].replaceAll("[.]","").trim(), 0);
				commandTAB('//*[@id="coste"]')
				//Aceitar
				selecionaItemLista('Aceitar', '//*[@id="DatosImporteGarantiaSectionBar"]/table/tbody/tr/td', 'div')
				WebUI.delay(2);
				selecionaItemLista('Aceitar', '//*[@id="sectionDatosPagRecSectionBar"]/table/tbody/tr/td', 'div')
				WebUI.delay(2);
				System.out.println()
				alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				
				if(alertListAjuste(alerta)){
					//Observação
					preencheXpath('//*[@id="textoObs"]',".", 0);
					selecionaItemLista('Aceitar', '//*[@id="sectionDatosPagRecSectionBar"]/table/tbody/tr/td', 'div')
					WebUI.delay(2);
				}
			
				alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 3)
				if(alertListBlocList(alerta)){
			        evidencia = "Alerta: "+alerta;
					exec = false
				} else {
				 String msg1 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				  try{
					  //Zerar ressarcimento
					  selecionarRessarcimento('0,00', sinistro[14])
					  //Aceitar
					  selecionaItemLista('Aceitar', '//*[@id="SectionGarantiasSectionBar"]/table/tbody/tr/td', 'div')
					  WebUI.delay(2);
				  } catch (Exception e){}
				
				  String msg2 = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				  evidencia = msg1+" - "+msg2
				  exec = true
				  
				}
				
				//Home
				clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
				
				}
				
				
				
//			    } else {
//				exec = false
				//Home
//				clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
//				alerta
//			}
		} else {
			exec = false
			alerta = "Ressarcimento não efetuado"
			//Home
			clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
		}
		System.out.println("["+exec+"] ALERTA ["+alerta+"]")
		return exec
	}


	/**
	 * @author be54955
	 * @param sinistro
	 * @param c
	 * @return
	 */
	public static boolean selecionarRessarcimento(String valor, String c){
		boolean r = false
		try{
			System.out.println("Cobertura ["+c+"] Valor ["+valor+"]\n")
			WebUI.delay(5);
			String x = '//*[@id="tablaGarantias_tbody"]'
			String t = 'tr'
			selecionaListaDeElementos(x,t);
			int size = listOfElements.size()
			for(int i = 0; i < size; i++){
				System.out.println('['+i+'] ['+listOfElements.get(i).getText()+"]")
				if(listOfElements.get(i).getText().contains(c.toString())){
					limpaCampo('//*[@id="row_'+i+'_tablaGarantias"]/td/input', 1)
					preencheXpath('//*[@id="row_'+i+'_tablaGarantias"]/td/input',valor.replaceAll("[.]","").trim(), 2);
					commandTAB('//*[@id="row_'+i+'_tablaGarantias"]/td/input')
					r = true
					break
				}
			}
		} catch (Exception e){
			r = false
		}
		return r
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static menuGIS(){
		MenuMatriz.producaoSinistro()
		WebUI.delay(5);
		MenuMatriz.subMenuNome('a',"GIS")
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static reprocessaDescontosEspecíficosSinistro(String tipoAba){
		if(carregaDados()){
			System.err.println("[ Reprocessa Descontos Específicos Sinistro")
			fluxoCompletoDescontosEspecíficosSinistro(tipoAba)
			reprocessaDescontosEspecíficosSinistro(tipoAba)
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailDescontosEspecíficosSinistro(String tipoAba){
		if(!carregaDados()){
			pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Sinistros/DescontosEspecificosSinistro/ConfiguracoesDescontosEspecificosSinistro.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Sinistro\\DescontosEspecíficosSinistro.xlsx"
			anexos[1] = "DescontosEspecíficosSinistro.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
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
	public static enviarEmailDescontosEspecíficosSinistroDinamico(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal, String assunto){
	  long segds = timeLapnse.toSeconds();
	  String hours = timeLapnse.toHours()+"h "+
	  timer.converteMinutos(timeLapnse.toHours(), timeLapnse.toMinutes())+"m "+
	  timer.converteSegundos(timeLapnse.toMinutes(),segds)+"s";
	   if(!carregaDados()){
		  pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Sinistros/DescontosEspecificosSinistro/ConfiguracoesDescontosEspecificosSinistro.txt";
		  lerConfigurações(pathTxtEmail)
		  String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
		  List<String> anexos = new ArrayList<>();
		  anexos[0] = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Sinistro\\DescontosEspecíficosSinistro.xlsx"
		  anexos[1] = "DescontosEspecíficosSinistro.xlsx"
		  emailDinamico.enviaEmailDinamico(sender, emaildestination, tipoAba, anexos[0], anexos[1], emailDinamico.listaDeAlertas(data, cellCheckAlert)  , hours, horaInicial, horaFinal,  GetHost.getHostname() , dataInicio, dataFinal, assunto);
	  }
	}


	/**
	 * @author be54955
	 * @return
	 */
	public static boolean verificaSituacao(){
		boolean alert
		WebUI.delay(5);
		String x = '//*[@id="sectionDatosGenContent"]/table[1]/tbody/tr/td[3]/table/tbody/tr'
		String t = 'input'
		selecionaListaDeElementos(x,t);
		int size = listOfElements.size()
		for(int i = 0; i < size; i++){
			System.err.println('['+i+'] SEARCH ['+listOfElements.get(i).getAttribute('value').toString()+"]")
			if(listOfElements.get(i).getAttribute('value').toString().contains('Encerrado'.toString())){
				alerta = listOfElements.get(i).getAttribute('value').toString()
				System.err.println('['+i+'] FOUND ['+alerta+"]")
				alert = true
			} else {
				alert = false
			}
		}
		return alert
	}

	/**
	 * @author be54955
	 * @param alert
	 * @return
	 */
	public static alertListBlocListDecEsp(String alert){
		System.err.println("\nalertListBloc ["+alert+"]")
		boolean r = false
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add(" Beneficiário/ Emissor obrigatório.")
		listOfAlerts.add(" Reserva + Pagamentos ultrapassa a cobertura.")
		listOfAlerts.add(" Erro pagamento: Cobertura não existe")
		listOfAlerts.add(" Erro de acceso às coberturas da apólice")
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
	 * @author be54955
	 * @param alert
	 * @return
	 */
	public static alertListAjuste(String alert){
		System.err.println("\nalertListBloc ["+alert+"]")
		boolean r = false
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add(" Observação obrigatória.")
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
	 * @author be54955
	 * @param alert
	 * @return
	 */
	public static alertListContinueList(String alert){
		System.err.println("\nalertListBloc ["+alert+"]")
		boolean r = false
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add(" Operação realizada.")

		System.out.println("Alert ["+alert+"]\n")
		for(int i = 0; i < listOfAlerts.size(); i++){
			System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
			if(listOfAlerts[i].contains(alert.toString())){
				System.err.println("FOUND ALERT TO CONT. ["+i+"] ["+listOfAlerts[i]+"]")
				r = true
				break
			}
		}
		System.out.println("\nAlert ["+r+"]")
		return  r
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static String recuperaSituacao(){
		String v
		try{
			WebUI.delay(5);
			String x = '//*[@id="sectionDatosGenContent"]'
			String t = 'input'
			selecionaListaDeElementos(x,t);
			int size = listOfElements.size()
			for(int i = 0; i < size; i++){
				System.err.println('['+i+'] ['+listOfElements.get(i).getAttribute('value')+"]")
				//v = listOfElements.get(i).getAttribute('value')
				//break
			}
		} catch (Exception e){}
		return v
	}


	/**
	 * @author be54955
	 * @param sinistro
	 * @return
	 */
	public static boolean cancelaDescontosAbertos(List<String> sinistro){
		boolean b = false
		String x = '//*[@id="tablaPagosRecobros_tbody"]'
		String t = 'tr'
		selecionaListaDeElementos(x,t);
		int size = listOfElements.size()
		for(int i = 0; i < size; i++){
			System.err.println('['+i+'] SEARCH ['+listOfElements.get(i).getText().toString()+"]")
			if(listOfElements.get(i).getText().toString().contains(sinistro[6]) ||
			listOfElements.get(i).getText().toString().contains(sinistro[20]) )
			{
				System.err.println('['+i+'] FOUND ['+listOfElements.get(i).getText().toString()+"]")
				listOfElements.get(i).click()
				if(cancala(sinistro)){
					b = true
					break
				}
			}
		}
		return b
	}

	/**
	 * @author be54955
	 * @param sinistro
	 * @return
	 */
	public static boolean cancala(List<String> sinistro){
		boolean b = false
		selecionaListaDeElementos('//*[@id="sectionPaginaCompletaContent"]/table[4]/tbody/tr/td[1]/table/tbody/tr','label');
		int size = listOfElements.size()
		for(int i = 0; i < size; i++){
			System.err.println('['+i+'] SEARCH ['+listOfElements.get(i).getText().toString()+"]")
			if(listOfElements.get(i).getText().contains(sinistro[21])){
				System.err.println("[CANCELADO] ["+listOfElements.get(i).getText()+"] ["+sinistro[21]+"]")
				//Cancelar
				//clickXpath('//*[@id="o_16"]', 5);
				selecionaItemLista('Cancelar', '//*[@id="sectionPaginaCompletaSectionBar"]/table/tbody/tr/td/table', 'div')
				b = true
			}
		}
		return b
	}

	/**
	 * @author be54955
	 * @param sinistro
	 * @return
	 */
	public static  capturaAlerta(List<String> sinistro){
		selecionaListaDeElementos('//*[@id="dataErrorSection"]/table','td');
		int size = listOfElements.size()
		for(int i = 0; i < size; i++){
			System.err.println('['+i+'] SEARCH ['+listOfElements.get(i).getText().toString()+"]")
			if(i==1){
				sinistro[22] = listOfElements.get(i).getText().toString()
			}
		}
	}




}

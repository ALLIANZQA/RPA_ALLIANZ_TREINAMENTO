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
import sendEmail.emailDinamico;
import auxiliares.GetHost;
import auxiliares.leitorPdf;
import java.time.Duration
import java.time.Instant

public class GestaoCreditoDescarteincidenciasFuncionalidades  extends rpaCommands{


	private static int cellCheck = 3
	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/GestaoCreditoDescarteincidencias/GestaoCreditoDescarteincidenciasData'
	private static String path
	private static String msgAlerta
	private static String status
	private static String referencia

	
		
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "GestaoCreditoDescarteincidencias.xlsx"
	private static int cellCheckAlert = 4;
	

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
			MenuMatriz.gestaoMarketing()
			MenuMatriz.gestaoArquivosBancarios();
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
	public static fluxoGestaoCreditoDescarteincidencias(String aba, String a){
		String dataInicio = ExtractDates.dataAtual();
		String horaInicial =  ExtractDates.horaAtualizada();
		start = Instant.now();
		initDataFiles()
		fluxoCompletoGestaoCreditoDescarteincidencias(aba,a)
		reprocessaFalhas(aba,a)
		//enviaEmailGestaoCreditoDescarteincidencias(aba)
		end =  Instant.now();
		timeLapnse = Duration.between(start, end);
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
			enviaEmailGestaoRDescredenciamentoIncidenciasDinamico(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
		} catch (Exception e){
		}
	}
	
	

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoGestaoCreditoDescarteincidencias(String aba, String a){
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
						if(descarte(registros, aba, a)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 2, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, msgAlerta , 3, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 2, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path,"Alerta: "+msgAlerta, 3, aba);
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
	public static boolean descarte(List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado;
		String msgAlert;
		try{
			//Gestão Ficheiros Bancarios
			WebUI.delay(10);
			String telaGestaoFicheirosBancarios = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(telaGestaoFicheirosBancarios )
			if(listaDeTelas(telaGestaoFicheirosBancarios)){
				//Gestão de Créditos
				selecionaItemLista("Gestão Créditos",'//*[@id="tableOptMenu_"]' , "td" )
				//Gestão Créditos
				String telaGestaoCreditos = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
				System.out.println(telaGestaoCreditos )
				if(listaDeTelas(telaGestaoCreditos)){
					//iNCIDENCIAS
					clickXpath('//*[@id="row_0_tablaCuentas"]', 1);
					//Ocorrências Agrupadas por Mês
					String telaOcorrenciaAgrupadaMes = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
					System.out.println(telaOcorrenciaAgrupadaMes )
					if(listaDeTelas(telaOcorrenciaAgrupadaMes)){
						System.out.println()
						WebUI.delay(5);
						selecionaOcorrenciaDoMes()
						System.out.println()
						msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
						if(!alertListBlocList(msgAlert)){
							//Pesquisar Recibo
							limparCamposInput('//*[@id="recibo"]')
							preencheXpath('//*[@id="recibo"]', registros[0], 1)
							//Pesquisar
							selecionaItemLista("Pesquisar",'//*[@id="filtrosBusquedaSectionBar"]/table/tbody/tr/td/table' , "td" )
							System.out.println()
							msgAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
							if(!alertListBlocList(msgAlert)){
								//Ocorrência Agrupada
								clickXpath('//*[@id="row_0_tablaIncidenciasMes"]', 1);
								//Ocorrências Agrupadas por Mês
								String telaDadosOcorrencia = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
								System.out.println(telaDadosOcorrencia )
								if(listaDeTelas(telaDadosOcorrencia)){
									System.out.println()
									if(descartar()){
										enviado = true
										String confirm =  recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 1)
										msgAlerta=  "Tela '"+telaDadosOcorrencia+"': "+ confirm;
										status = "Processado"
										//Gestão Ficheiros Bancarios
										selecionaItemLista("Gestão de Ficheiros Bancários",'//*[@id="tableOptMenu_"]' , "td" )
									} else  {
										enviado = true
										msgAlerta=  "Tela '"+telaDadosOcorrencia+"': Não existe incidência para descarte";
										status = "Processado"
										//Gestão Ficheiros Bancarios
										selecionaItemLista("Gestão de Ficheiros Bancários",'//*[@id="tableOptMenu_"]' , "td" )
									}



									//  if(descartar()){
									//	   enviado = false
									//	   String confirm =  recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 1)
									//	   msgAlerta=  "Tela '"+telaDadosOcorrencia+"': "+ confirm;
									//	   status = "Processado"
									//Gestão Ficheiros Bancarios
									//	   selecionaItemLista("Gestão de Ficheiros Bancários",'//*[@id="tableOptMenu_"]' , "td" )
									//   } else {
									//     enviado = false
									//	 String motivo =  recuperaValor('//*[@id="motivoIncidencia"]', 'value', 1)
									//     msgAlerta=  "Tela '"+telaDadosOcorrencia+"': "+motivo;
									//     status = "Processado"
									//	 //Gestão Ficheiros Bancarios
									//	 selecionaItemLista("Gestão de Ficheiros Bancários",'//*[@id="tableOptMenu_"]' , "td" )
									//   }

								} else {
									enviado = false
									msgAlerta=  "Tela '"+telaDadosOcorrencia+"': "+msgAlert;
									status = "Processado"
								}
							} else {
								System.out.println()
								enviado = false
								msgAlerta=  "Tela 'Ocorrências do mês': "+msgAlert;
								status = "Processado"
							}
						} else {
							System.out.println()
							enviado = false
							msgAlerta=  "Tela 'Ocorrências do mês': "+msgAlert;
							status = "Processado"
							fecharJanelaAtual(1)
							alteraJanejaGenerica(0, 5)
							init(aba, a)
						}
					} else {
						enviado = false
						msgAlerta="Tela 'Ocorrências Agrupadas por Mês' não apresentada"
						status = "Processar"
						fecharJanelaAtual(1)
						alteraJanejaGenerica(0, 5)
						init(aba, a)
					}
				} else {
					enviado = false
					msgAlerta="Tela 'GESTÃO DE CRÉDITOS' não apresentada"
					status = "Processar"
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 5)
					init(aba, a)
				}
			} else {
				enviado = false
				msgAlerta="Tela 'Gestão de Ficheiros Bancários' não apresentada"
				status = "Processador"
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 5)
				init(aba, a)
			}
		} catch (Exception e) {
			enviado = false
			status = "Processado"
			msgAlerta="Falha ao preencher 'Gestao Ficheiros Bancarios'"
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0, 5)
			init(aba, a)
		}
		System.out.println("\nenviado ["+ enviado+"] \nmsgAlerta ["+msgAlerta+"]");
		return enviado;
	}

	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static boolean descartar(){
		System.out.println()
		boolean r = false;
		try{
			selecionaItemLista("Descartar",'//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td' , "div" )
			WebUI.delay(5);
			try{
				fechaAlerta();
			} catch (Exception e){}
			WebUI.delay(5);
			r = true;
		} catch (Exception e){ }
		System.out.println("["+r+"]");
		return r;
	}

	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static boolean selecionaOcorrenciaDoMes(){
		boolean r = false;
		try{
			String d = ExtractDates.dataSetFormat("MM/yyyy");
			String xpath = '//*[@id="tablaIncidencias_tbody"]'
			String tag ="tr";
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				if(listOfElements.get(i).getText().contains(d)){
					System.out.println("["+i+"] [ "+listOfElements.get(i).getText()+"]");
					listOfElements.get(i).click();
					r = true;
				}
			}
		} catch (Exception e){ }
		return r;
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
			fluxoCompletoGestaoCreditoDescarteincidencias(aba, a)
			reprocessaFalhas(aba, a)
		}
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailGestaoCreditoDescarteincidencias(String tipoAba){
		System.out.println()
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\DescarteIncidencia\\ConfiguracoesDescarteIncidencia.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "GestaoCreditoDescarteincidencias.xlsx"
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
	public static enviaEmailGestaoRDescredenciamentoIncidenciasDinamico(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
		System.out.println()
	 try{
		 if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\DescarteIncidencia\\ConfiguracoesDescarteIncidencia.txt"
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			String docName = "GestaoCreditoDescarteincidencias.xlsx";
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





}

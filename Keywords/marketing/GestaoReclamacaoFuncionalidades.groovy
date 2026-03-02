package marketing

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


public class GestaoReclamacaoFuncionalidades extends rpaCommands{


	private static TestData data
	private static TestData dataOperacao
	private static String testDataPath = 'Data Files/Marketing/GestaoReclamacaoData'
	private static String path
	private static String alerta
	private static String retorno
	private static int cellCheck = 25;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "GestaoReclamacao.xlsx"
	private static int cellCheckAlert = 26;
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
	public static fluxoCotacaoRenovacaoInterna(String aba, String a){
		System.out.println()
		String dataInicio = ExtractDates.dataAtual();
		String horaInicial =  ExtractDates.horaAtualizada();
		start = Instant.now();
		initDataFiles()
		fluxoCompletoGestaoReclamacao(aba,a)
		reprocessaFalhas(aba,a)
		end =  Instant.now();
		timeLapnse = Duration.between(start, end);
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
			enviaEmailGestaoReclamacao(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
		} catch (Exception e){
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoGestaoReclamacao(aba,a){
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
						registros[26] = "Dados Alternativos Segurado/Reclamante"
						registros[27] = "Gestão de Reclamações"
						registros[28] = "06. Reclame Aqui"
						registros[30] = "Allianz Seguros"
						if(gestao(registros,aba,a)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 24, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado" , 25, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta , 26, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 24, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: "+alerta, 25, aba);
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
	 * @return
	 */
	public static boolean gestao(List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado = true;
		String msgAlert;
		String tela = true;
		try{
			MenuMatriz.gestaoMarketing()
			MenuMatriz.subMenuNome("a","Reclamações Allianz")
			alteraJanejaGenerica(1, 5)
			tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(tela)
			if(listaDeTelas(tela)){
				//Nova Reclamação
				selecionaItemLista("Nova Reclamação",'//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table' , "td" )
				WebUI.delay(5);
				tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
				System.out.println(tela)
				if(listaDeTelas(tela)){
					switch (registros[7]){
						case "Apólice":
						//Referência
							selecionaItemLista(registros[7],'//*[@id="origenReclamacion.referencia.value"]' , "option" )
							preencheXpath('//*[@id="origenReclamacion.numero.value"]', registros[22], 0)
							break;
						case "CPF/CNPJ":
						//Referência
							selecionaItemLista(registros[7],'//*[@id="origenReclamacion.referencia.value"]' , "option" )
							preencheXpath('//*[@id="origenReclamacion.nif._doc"]', registros[22], 1)
							break;
						case "Sinistro":
						//Referência
							selecionaItemLista(registros[7],'//*[@id="origenReclamacion.referencia.value"]' , "option" )
							preencheXpath('//*[@id="origenReclamacion.numero.value"]', registros[22], 0)
							break;
						case "Agente SUSEP":
						//Referência
							selecionaItemLista(registros[7],'//*[@id="origenReclamacion.referencia.value"]' , "option" )
							preencheXpath('//*[@id="origenReclamacion.numero.value"]', registros[22], 0)
							break;
					}

					//Lupa
					clicarEmElementoDaTela('//*[@id="btnBuscarReferencia"]', 0);
					WebUI.delay(3);
					try{
						fechaAlerta()
					}catch (Exception e){}
					alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
					System.out.println("Alerta ["+alerta+"]")
					if(alertListBlocList(alerta)){
						fecharJanelaAtual(1)
						alteraJanejaGenerica(0, 0)
						status = "Processado"
						enviado = false;
					} else {
						//Entidades Env.
						selecionaItemLista(registros[30],'//*[@id="origenReclamacion.companyia.value"]' , "option" )
						//Tipo Reclamante
						selecionaItemLista(registros[26],'//*[@id="datosTomador.tipoReclamante.value"]' , "option" )
						//E-mail
						try{
							limpaCampo('//*[@id="datosReclamante.email.value"]', 5)
						}catch (Exception e){}
						//Tipo Reclamação
						selecionaItemLista(registros[27],'//*[@id="datosExpediente.tipoReclamacion.value"]' , "option" )
						//Entrada
						selecionaItemLista(registros[28],'//*[@id="datosExpediente.entrada.value"]' , "option" )
						//Referencia
						preencheXpath('//*[@id="datosExpediente.suReferencia.value"]', registros[1], 0)
						//Linha de negocio
						//selecionaItemLista(fromataLinhaDeNegocios(registros[8])  ,'//*[@id="origenReclamacion.lineaNegocio.value"]' , "option" )
						//Línea de Negocio
						selecionaItemLista(registros[8].toUpperCase(),'//*[@id="origenReclamacion.lineaNegocio.value"]' , "option" )
						//Motivo Principal
						selecionaItemLista(registros[9].toString().toUpperCase(),'//*[@id="tramitacionReclamacion.motivoPrincipal.value"]' , "option" )
						//Submotivos
						selecionaItemLista(registros[10].toString().toUpperCase(),'//*[@id="tramitacionReclamacion.secundario.value"]' , "option" )
						//Descrição
						preencheXpath('//*[@id="datosExpediente.descripcion.value"]', registros[23], 0)
						//Área Técnica
						switch (registros[9]){
							case "Assistência Não Danos":
								selecionaItemLista("BACK OFFICE",'//*[@id="tramitacionReclamacion.unidOrganizativa.value"]' , "option" )
								break;
							case "Assistência Danos":
								selecionaItemLista("BACK OFFICE",'//*[@id="tramitacionReclamacion.unidOrganizativa.value"]' , "option" )
								break;
							case "Apólice":
								selecionaItemLista("BACK OFFICE",'//*[@id="tramitacionReclamacion.unidOrganizativa.value"]' , "option" )
								break;
							case "Canal de Atendimento":
								selecionaItemLista("BACK OFFICE",'//*[@id="tramitacionReclamacion.unidOrganizativa.value"]' , "option" )
								break;
							case "Sinistro":
								selecionaItemLista("SINISTRO",'//*[@id="tramitacionReclamacion.unidOrganizativa.value"]' , "option" )
								break;
						}
						System.out.println()
						//Gravar
						selecionaItemLista("Gravar", '//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody', 'div');
						WebUI.delay(3);
						alerta = pegaMSGAlerta()
						fechaAlerta()
						enviado = true;
						String msgAlerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
						System.out.println("Alerta ["+msgAlerta+"]")
						if(alertListBlocList(msgAlerta)){
							enviado = false;
							alerta=msgAlerta
							System.out.println("Alerta ["+alerta+"]")
						}

						if(msgAlerta.contains(" Campo obrigatório.")){
							enviado = false;
							alerta=recuperaValor('//*[@id="cellDataErrorSection"]', 'innerText', 0)
							System.out.println("Alerta ["+alerta+"]")
						}
						
						if(msgAlerta.contains("atributo nao encontrado")){
					//		enviado = false;
					//		alerta="Não Processado. Ocorreu um erro na gravação do processo."
							System.out.println("Alerta ["+alerta+"]")
						}

						fecharJanelaAtual(1)
						alteraJanejaGenerica(0, 0)
						status = "Processado"
					}
				} else {
					enviado = false
					alerta="Tela 'DETALHE RECLAMAÇÃO' não apresentada"
					status = "Processado"
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 5)
					init(aba, a)
				}
			} else {
				enviado = false
				alerta="Tela 'GESTÃO DE RECLAMAÇÕES' não apresentada"
				status = "Processado"
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 5)
				init(aba, a)
			}
		} catch (Exception e) {
			enviado = false
			status = "Processado"
			alerta="Falha ao preencher 'GESTÃO DE RECLAMAÇÕES'"
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0, 5)
			init(aba, a)
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
	public static enviaEmailGestaoReclamacao(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
		System.out.println()
		try{
			if(!validaDados(cellCheck)){
				String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Marketing\\GestaoReclamacao\\ConfiguracoesGestaoReclamacao.txt";
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
	 * @return
	 */
	public static reprocessaFalhas(String aba, String a){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoGestaoReclamacao(aba,a)
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
			WebUI.delay(10);
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


	public static String fromataLinhaDeNegocios(String t){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('Auto', 'AUTOMÓVEL');
		urls.put('Linhas Corporativas', 'LINHAS CORPORATIVAS');
		urls.put('Residencial', 'RESIDÊNCIA');
		urls.put('Vida/Acidentes Pessoais', 'VIDA / ACIDENTES PESSOAIS');

		return urls.get(t.toString());
	}

}

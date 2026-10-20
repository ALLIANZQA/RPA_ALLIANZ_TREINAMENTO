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


public class GeraBoletoFuncionalidades extends rpaCommands {

	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/GeraBoleto/GeraBoletoData'
	private static String path
	private static String alerta
	private static int cellCheck = 4
	private static String filepath = "C:\\Users\\Public\\"
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "GeraBoleto.xlsx"
	private static int cellCheckAlert = 5;
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
	public static fluxoGeraBoleto(String aba){
		System.out.println()
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		initDataFiles()
		fluxoCompletoGeraBoleto(aba)
		reprocessaFalhas(aba)
		FileInput.atualizaLog(Instant.now().toString(), pathFile)
		List<String> times = rpaCommands.convertToInstant(pathFile);
		String dataInicio = times[0];
		String horaInicial =  times[1];
		timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
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
					String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\GeraBoleto\\ConfiguracoesGeraBoleto.txt";;
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
					FileInput.deletaArquivoPdf(pathFile);
				}
			} catch (Exception e){}
		}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoGeraBoleto(aba){
		try{
			if(validaDados(cellCheck)){
				acessoAmbiente(aba)
				WebUI.delay(10);
				MenuMatriz.producaoSinistro()
				MenuMatriz.subMenuNome("a", "Duplicados Recibos")
				//WebUI.delay(17);
				WebUI.waitForPageLoad(20)
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						if(gerarBoleto(registros, aba)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 3, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 4, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 3, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: Falha ao gerar boleto. "+alerta, 4, aba);
							} catch (Exception e){}
						}
					}
				}
			} else {
				System.err.println('Não existem dados para processar')
			}
		} catch (Exception e){}
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
	public static boolean gerarBoleto( List<String> registros, String aba){
		boolean enviado
		System.out.println()
		try{
			alerta=""

			/**
			 * TELA - GESTÃO PROCESSOS
			 */
			alteraJanejaGenerica(1, 5)
			/**
			 * TELA - 2ª VIA-RECIBO
			 */
			//Numero Recibo
			limpaCampo('//*[@id="numRecibo"]',0)
			preencheXpath('//*[@id="numRecibo"]', registros[0], 0);
			//Limite Máximo
			limpaCampo('//*[@id="fechajuros"]',0)
			preencheXpath('//*[@id="fechajuros"]', registros[1], 0);
			commandTAB('//*[@id="fechajuros"]')
			validaJuros(registros[2])
			//Aceitar
			clickXpath('//*[@id="Inadimplencia"]', 0);
			WebUI.delay(15);
            System.out.println();
            transfereArquivoDePasta("AllianzSegurosBrasil")
            WebUI.delay(10);
			alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 2)
			System.err.println("ALERTA ["+alerta+"]")
			WebUI.delay(2);
			
			if(!alertListBlocList(alerta)){
			   alteraJanejaGenerica(2, 2)
			   fecharJanela()
			   alteraJanejaGenerica(1, 5)

			   if(alerta.contains("atributo nao encontrado")){
				  alerta = "Boleto gerado."
			   }
			
			   enviado = true

			   try{
				  deleteFileDiretory("C:\\Users\\Public\\AllianzSegurosBrasil.pdf")
			   } catch (Exception e){}

			   try{
				  deleteFileDiretory("C:\\Users\\Public\\AllianzSegurosBrasil (1).pdf")
			   } catch (Exception e){}

			   try{
				  deleteFileDiretory("C:\\Users\\Public\\AllianzSegurosBrasil (2).pdf")
			   } catch (Exception e){}
			
			
			} else {
			  System.out.println()
			  enviado = false
			  alteraJanejaGenerica(2, 2)
			  fecharJanela()
			  alteraJanejaGenerica(1, 5)
			  MenuMatriz.producaoSinistro()
			  MenuMatriz.subMenuNome("a", "Duplicados Recibos")
			}
			

		} catch (Exception e){
			enviado = false
		}
		return enviado
	}


	/**
	 * @author be54955
	 * @param j
	 * @return
	 */
	public static validaJuros(String j){
		if(j.equals("Sim")){
			//checkbox juros
			clickXpath('//*[@id="juros"]', 0);
		}
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailGeraBoleto(String tipoAba){
		initDataFiles()
		if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\GeraBoleto\\ConfiguracoesGeraBoleto.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = "GeraBoleto.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos, tipoAba)
		}
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
			fluxoCompletoGeraBoleto(aba)
			reprocessaFalhas(aba)
		}
	}
}

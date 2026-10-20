package auto


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

public class BanrisulFuncionalidades extends rpaCommands{
	
	
	private static String pathName = "Data Files/Auto/Banrisul/BanrisulData/EnvioBanrisulData"
	private static TestData data
	private static TestData dataOperacao
	private static String testDataPath; 
	private static String path
	private static String alerta
	private static String retorno
	private static int cellCheck = 6;
	private static int cellCheck2 = 8;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "EnvioBanrisul.xlsx"; 
	private static int cellCheckAlert = 10;
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
	public static fluxoBanrisul(String aba, String a){
		System.out.println()
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		initDataFiles()
		fluxoCompletoBanrisul(aba)
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
	 * @param aba
	 * @param a
	 * @return
	 */
	public static fluxoCompletoBanrisul(String aba){
		System.out.println()
		try{
			if(validaDados(cellCheck, cellCheck2)){
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					enviado = true;
					//System.out.println("["+data.getValue(cellCheck, i)+"] ["+data.getValue(cellCheck2, i)+"]")
					if (data.getValue(cellCheck, i).equals('x') || data.getValue(cellCheck2, i).equals('x')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						registros[10]="BANRISUL CORRETORA DE SEGUROS S/A";
						registros[11]="corretora@cabergsseguros.com.br";
						registros[12]="51 32629250";
						registros[13]="202106228";
						//E-MAIL DE ENVIO 
						registros[14]="arquivorenovacaobanrisul@allianz.com.br, andre.antunes@allianz.com.br";
						if(envioEmail(registros, j, aba)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 9, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: "+alerta, 9, aba);
							} catch (Exception e){}
						}
					}
				}
			} else {
				System.err.println('Não existem dados para processar')
			}
		} catch (Exception e){}
	//	WebUI.closeBrowser();
	}
	
	
	/**
	 * @author be54955
	 * @return
	 */
	public static boolean envioEmail(List<String> registros, int i, String aba){
		System.out.println()
		boolean enviado = true;
		String msgAlert;
		String tela = true;
		try{
		  //String sender ="denilson.inmetricsext@allpronet.com.br";
			String sender =  registros[14];
			//DADOS FIXOS 
			String corretor=registros[10]
			String email=registros[11]
	//		String email="denilson.inmetricsext@allpronet.com.br"
			String telefone=registros[12]
			String susep=registros[13]
		    //DADOS DA  PLANILHA 
	//	    String emaildestination="denilson.inmetricsext@allpronet.com.br, andre.antunes@allianz.com.br";
			String emaildestination=registros[4];
			String proponente=registros[3];
			String cpfcnpj=registros[2];
			String apolice=registros[0];
			String finalVigencia= ExtractDates.formatDateToBR(registros[1])
			String nome=registros[3];

			switch (tipoEnvio(registros)){
				case "AlteracoesRenovacaoBanrisul":
				    try{
				    alteracoesRenovacaoBanrisul("AlteracoesRenovacaoBanrisul", sender, emaildestination, corretor,  email, telefone, susep,  proponente, cpfcnpj, apolice, finalVigencia, nome)
					alerta="E-mail enviado com sucesso"
					auxiliares.WriteExcelFactory.updateSheetLine("", i, path, "", 5, aba);
					auxiliares.WriteExcelFactory.updateSheetLine("", i, path, "x", 6, aba);
					break;
				    } catch (Exception e){
					  enviado = false
					  alerta="E-mail não enviadol: "+e.getMessage()
					  auxiliares.WriteExcelFactory.updateSheetLine("", i, path, "", 5, aba);
					  auxiliares.WriteExcelFactory.updateSheetLine("", i, path, "x", 6, aba);
					}
					
				case "naoRenovacaoBanrisul":
	                try{			
			        naoRenovacaoBanrisul("naoRenovacaoBanrisul", sender, emaildestination, corretor,  email, telefone, susep,  proponente, cpfcnpj, apolice, finalVigencia, nome)
					alerta="E-mail enviado com sucesso"
					auxiliares.WriteExcelFactory.updateSheetLine("", i, path, "", 7, aba);
					auxiliares.WriteExcelFactory.updateSheetLine("", i, path, "x", 8, aba);
					break;
	                } catch (Exception e){
					  enviado = false
					  auxiliares.WriteExcelFactory.updateSheetLine("", i, path, "", 7, aba);
					  auxiliares.WriteExcelFactory.updateSheetLine("", i, path, "x", 8, aba);
					}
			}
			
		} catch (Exception e) {
			enviado = false
			status = "Processado"
			alerta="Falha ao enviar e-mail de alerta."
		}
		System.out.println("\nenviado ["+ enviado+"] \nmsgAlerta ["+alerta+"]");
		return enviado;
	}
	
	
	public static String tipoEnvio(List<String> registros){
		String tipo;
		
		if(registros[5].equals("x")){
			tipo = "AlteracoesRenovacaoBanrisul";
		}
		
		if(registros[7].equals("x")){
			tipo = "naoRenovacaoBanrisul";
	    }
		
		return tipo;
	}
	
	/**
	 * @author Alz_sup54955
	 */
	public static boolean alteracoesRenovacaoBanrisul(String ramo, String sender, String emaildestination,String corretor, String email, String telefone, String susep, String proponente, String cpfcnpj, String apolice, String finalVigencia, String nome){
		System.err.println('Sender ['+sender+'] Ramo ['+ramo+"] Proposta ["+corretor+"]")
		sendEmail.CreateEmail.createEmailBanrisul(ramo, sender, emaildestination, corretor, email, telefone, susep, proponente, cpfcnpj, apolice, finalVigencia, nome )
		return true;
	}
	
	/**
	 * @author Alz_sup54955
	 */
	public static boolean naoRenovacaoBanrisul(String ramo, String sender, String emaildestination,String corretor, String email, String telefone, String susep, String proponente, String cpfcnpj, String apolice, String finalVigencia, String nome){
		System.err.println('Sender ['+sender+'] Ramo ['+ramo+"] Proposta ["+corretor+"]")
		sendEmail.CreateEmail.createEmailBanrisul(ramo, sender, emaildestination, corretor, email, telefone, susep, proponente, cpfcnpj, apolice, finalVigencia, nome )
		return true;
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
			if(!validaDados(cellCheck,cellCheck2)){
				String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Auto\\Banrisul\\ConfiguracoesBanrisul.txt";
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
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(pathName)
		System.out.println(data.getRowNumbers())
		path = data.sourceUrl
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck, cellCheck2)){
			fluxoCompletoBanrisul(aba)
			reprocessaFalhas(aba)
		}
	}
	
	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static boolean validaDados(int cellStatus, int cellStatus2){
		boolean value
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			System.out.println("["+data.getValue(cellStatus, i)+"] ["+data.getValue(cellStatus2, i)+"]")
			if (data.getValue(cellStatus, i).equals('x') || data.getValue(cellStatus2, i).equals('x')) {
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

	
}

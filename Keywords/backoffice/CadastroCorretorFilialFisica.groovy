package backoffice

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

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

import internal.GlobalVariable


import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import auxiliares.RegrasEmailDestination
import bean.MassaDados



public class CadastroCorretorFilialFisica extends backofficeFuncionalidades{

	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/CadastroCorretorFilialFisica/CadastroCorretorFilialFisicaData'
	private static String path
	private static String alerta
	private static int cellCheck = 16


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
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static cadastroCorretorFilialFisica(String aba){
		fluxoCadastroCorretorFilialFisica(aba)
		reprocessaFalhas(aba)
		enviarCadastroCorretorFilialFisica(aba)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCadastroCorretorFilialFisica(aba){
		initDataFiles()
		if(validaDados(cellCheck)){
			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				if (data.getValue(cellCheck, i).equals('Processar')) {
					List<String> corretores = new ArrayList<>();
					lista( corretores , i)
					if(enviarEmail(corretores[3], corretores[2], corretores[9], corretores[1], corretores[11], corretores[12],corretores[13],corretores[14],corretores[4],corretores[5], aba)) {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 15, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "E-mail enviado para: "+alerta, 16, aba);
						} catch (Exception e){}
					} else {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 15, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Falha ao enviar e-mail: "+alerta, 16, aba);
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
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCadastroCorretorFilialFisica(aba)
			reprocessaFalhas(aba)
		}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static salvarEnvios(String status, int linha, String aba, String msg){
		int i = 1 - linha
		auxiliares.WriteExcelFactory.updateSheetLine("", i, path, status, 13, aba);
	}

	/**
	 * @author T_DCDias
	 * @param executados
	 * @return
	 */
	public static boolean enviarEmail(String emaildestination, String corretor,String intermediario, String cpfcnpj, String user, String pwr,String NOMEACCOUNT,String EMAILACCOUNT,String ddd, String telefone, String ramo){

		boolean enviado
		try{
			pathTxtEmail = "C://Users//Public//RPA_ALLIANZ//Emails//backoffice//CadastroCorretorFilialFisica//ConfiguracoesEnvioCorretor.txt";
			lerConfigurações(pathTxtEmail)
			System.err.println('Sender ['+sender+'] Corretor ['+corretor+"] Destino ["+emaildestination.trim()+"]")
			System.err.println()
			alerta = emaildestination
			enviado = sendEmail.CreateEmail.createEmailCadastroCorretorFilialFisica(sender, emaildestination.trim()+", "+EMAILACCOUNT.trim()+', retornoprecadastro@allianz.com.br',pwdsender, host,  porta, ramo, corretor,intermediario, cpfcnpj, user, pwr,NOMEACCOUNT,EMAILACCOUNT, ddd,  telefone)
		} catch (Exception e){
			enviado = false
		}
		return enviado
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static List<String> lista( List<String> sinistro , int v){
		for (int i = 1; i <= data.getColumnNumbers(); i++) {
			int d = i -1
			sinistro[d] = data.getValue(i, v).trim()
		}

		for (int j = 0; j < sinistro.size();j++ ){
			System.err.println("["+j+"] ["+sinistro[j]+"]")
		}

		return sinistro
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarCadastroCorretorFilialFisica(String tipoAba){
		initDataFiles()
		if(!validaDados(cellCheck)){
			pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/backoffice/CadastroCorretorFilialFisica/ConfiguracoesCadastroCorretorFilialFisica.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = "CadastroCorretor.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, "CadastroCorretorFilialFisicaFinalProcesso")
		}
	}
}

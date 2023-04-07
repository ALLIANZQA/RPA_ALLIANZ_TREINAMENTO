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
import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import auxiliares.RegrasEmailDestination
import auxiliares.MenuMatriz
import internal.GlobalVariable
import rpacommands.rpaCommands

public class EnvioRetornoGo  extends rpaCommands{


	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/EnvioRetornoGo/EnvioRetornoGoData'
	private static String path
	private static String alerta
	private static int cellCheck = 4
	private static String filepath = "C:\\Users\\Public\\"
	private static texto

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
	public static fluxoEnvioEnvioRetornoGo(String aba){
		initDataFiles()
		fluxoCompletoEnvioEnvioRetornoGo(aba)
		reprocessaFalhas(aba)
		enviaEmailRetornoFinalProcesso(aba)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoEnvioEnvioRetornoGo(aba){
		if(validaDados(cellCheck)){
			WebUI.delay(10);
			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				if (data.getValue(cellCheck, i).equals('Processar')) {
					List<String> registros = new ArrayList<>();
					lista( registros , i)
					if(retorno(registros, aba)) {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 3, aba);
						} catch (Exception e){}
					} else {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 3, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Envio de e-mail retorno GO não enviado", 4, aba);
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
	public static boolean retorno( List<String> registros, String aba){
		boolean enviado
		try{
			if(enviaEmailRetorno(registros[0], registros[2], registros[1])){
				enviado = true
			} else {
				enviado = false
			}
		} catch (Exception e){
			enviado = false
		}
		return enviado
	}

	/**
	 * @author be54955
	 * @param emaildestination
	 * @param t
	 * @param s
	 * @return
	 */
	public static boolean enviaEmailRetorno(String emaildestination, String t, String s){
		boolean enviado
		try{
			System.err.println(t)
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\EnvioRetornoGo\\ConfiguracoesEnvioRetornoGo.txt"
			lerConfigurações(pathTxtEmail)
			if(sendEmail.CreateEmail.createEmailSendEnvioEnvioRetornoGo(sender, emaildestination,pwdsender, host, porta, "EmailRetornoGo", t, s)){
				enviado = true
			} else {
				enviado = false
			}
		} catch (Exception e){
			enviado false
		}
		System.err.println("Enviado ["+enviado+"]")
		return enviado
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailRetornoFinalProcesso(String tipoAba){
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\EnvioRetornoGo\\ConfiguracoesEnvioRetornoGoFinalProcesso.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "EnvioRetornoGo.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
			}
		} catch (Exception e){
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
			fluxoCompletoEnvioEnvioRetornoGo(aba)
			reprocessaFalhas(aba)
		}
	}
}

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
import rpacommands.rpaCommands
import auxiliares.StringFormatter
import auxiliares.ExtractDates
import auxiliares.FileInput
import auxiliares.MenuMatriz

public class DistribuicaoPropostasGOFuncionalidades extends EnvioPendenciaGOInspecaoFuncionalidades{

	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/DistribuicaoPropostasGO/DistribuicaoPropostasGOData'
	private static String path
	private static int cellCheck = 10

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
	public static fluxoDistribuicaoPropostasGO(String aba){
		initDataFiles()
		fluxoCompletoDistribuicaoPropostasGO(aba)
		reprocessaFalhas(aba)
		enviaEmailDistribuicaoPropostasGO(aba)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoDistribuicaoPropostasGO(aba){
		if(validaDados(cellCheck)){
			iniciaAplicacaoMatriz('IntranetProdução');
			realizaLoginIntraNetProducao(aba);
			WebUI.delay(15);
			MenuMatriz.newFluxoGestao('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[2]/div/div[1]/ul');
			WebUI.delay(20);

			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				if (data.getValue(cellCheck, i).equals('Processar')) {
					List<String> registros = new ArrayList<>();
					lista( registros , i)
					if(inspecao(registros, aba)) {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", Integer.parseInt(registros[10]), path, "Processado", 9, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", Integer.parseInt(registros[10]), path, registros[11], 11, aba);
						} catch (Exception e){}
					} else {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", Integer.parseInt(registros[10]), path, "Processado", 9, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", Integer.parseInt(registros[10]), path, registros[11], 11, aba);
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
			fluxoCompletoDistribuicaoPropostasGO(aba)
			reprocessaFalhas(aba)
		}
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailDistribuicaoPropostasGO(String tipoAba){
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\DistribuicaoPropostasGO\\ConfiguracoesDistribuicaoPropostasGO.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "DistribuicaoPropostasGO.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos,tipoAba)
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
}

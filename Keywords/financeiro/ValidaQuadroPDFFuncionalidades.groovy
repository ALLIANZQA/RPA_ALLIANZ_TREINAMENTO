package financeiro

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
import backoffice.FluxoEnvioPDF
import auxiliares.leitorPdf
import auxiliares.BruteForceSubstringSearch
import auxiliares.ExtractDates

public class ValidaQuadroPDFFuncionalidades extends rpaCommands{




	private static TestData data
	private static String testDataPath = 'Data Files/Financeiro/validarQuadros/validaQuadrosData'
	private static String path
	private static String alerta
	private static int cellCheck = 5
	private static String caminhoSaida = "C:\\Users\\Public"
	private static String nome = "fichero.pdf"

	private static String ESPDATAINICIORD
	private static String ESPDATAFIMRD
	private static String ESPDATAEMISSRD
	private static String ESPVALORMOVRD



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
	public static  fluxoValidaQuadroPDF(String aba){
		initDataFiles()
		fluxoCompletoValidaQuadroPDF(aba)
		reprocessaFalhas(aba)
		enviarEmailValidaQuadroPDF(aba)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static  fluxoCompletoValidaQuadroPDF(aba){
		if(validaDados(cellCheck)){
			acessoAmbiente(aba)
			WebUI.delay(10);
			MenuMatriz.consultasEstatisticas()
			MenuMatriz.subMenu("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]/ul","a","Ficha de Gestão")
			WebUI.delay(17);
			alteraJanejaGenerica(1, 5)
			WebUI.maximizeWindow()
			WebUI.delay(17);

			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1

				if (data.getValue(cellCheck, i).equals('Processar')) {
					List<String> registros = new ArrayList<>();
					lista( registros , i)
					if(validarQuadros(registros, aba)) {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 4, aba);

							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, ESPDATAINICIORD, 6, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, ESPDATAFIMRD, 7, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, ESPDATAEMISSRD, 8, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, ESPVALORMOVRD, 9, aba);
						} catch (Exception e){}
					} else {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 4, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Falha no ficha e gestão. "+alerta, 5, aba);
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
	 * @param emaildestination
	 * @param corretor
	 * @param intermediario
	 * @param cpfcnpj
	 * @param user
	 * @param pwr
	 * @param ramo
	 * @return
	 */
	public static boolean validarQuadros( List<String> registros, String aba){
		boolean enviado
		try{
			alerta=""
			ESPDATAINICIORD=""
			ESPDATAFIMRD=""
			ESPDATAEMISSRD=""
			ESPVALORMOVRD=""

			String filePath = caminhoSaida+"\\"+nome
			deletaArquivoGenerico(filePath);
			FluxoEnvioPDF.fichaGestao(registros[1], "", registros[3], "", registros[2])
			WebUI.delay(15);
			if (!validaExistenciaDeArquivo(filePath)) {
				StringBuffer strbffr = new StringBuffer()
				strbffr = leitorPdf.pdfReader(filePath)
				String txt = strbffr.toString()
				String substr1 = BruteForceSubstringSearch.substringValue(txt, BruteForceSubstringSearch.searchIndex("Vigência das 24h de",txt), BruteForceSubstringSearch.searchIndex("Dados do Segurado",txt))
				substr1 = substr1.replace("24h","")
				System.err.println("\nSUBSTRING ["+substr1 +"]")
				Map<Integer,String> hasdates = new HashMap<Integer, String>();
				hasdates = ExtractDates.getSubDates(substr1)
				ESPDATAINICIORD=hasdates.get(0)
				ESPDATAFIMRD=hasdates.get(1)
				ESPDATAEMISSRD=hasdates.get(2)
				ESPVALORMOVRD = BruteForceSubstringSearch.substringValue(txt, BruteForceSubstringSearch.searchIndex("Prêmio líquido",txt), BruteForceSubstringSearch.searchIndex("Custo da apólice",txt))
				ESPVALORMOVRD = ESPVALORMOVRD.replace("Prêmio líquido", "")
				System.err.println("\nSUBSTRING ["+ESPVALORMOVRD+"]")
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
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailValidaQuadroPDF(String tipoAba){
		initDataFiles()
		if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Financeiro\\validaQuadrosPDF\\ConfiguracoesvalidaQuadrosPDF.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = "validaQuadros.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos, tipoAba)
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba){
		System.err.println('--[Reprocessa Falhas]--')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoValidaQuadroPDF(aba)
			reprocessaFalhas(aba)
		}
	}
}

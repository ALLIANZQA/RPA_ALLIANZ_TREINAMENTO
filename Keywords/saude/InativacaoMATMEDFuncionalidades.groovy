package saude

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.List

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

import internal.GlobalVariable

import org.openqa.selenium.WebElement
import rpacommands.rpaCommands
import auxiliares.switchLeitorExcel
import backoffice.backofficeFuncionalidades
import auxiliares.switchAtualizarExcel
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.ExtractDates
import auxiliares.MenuMatriz

public class InativacaoMATMEDFuncionalidades extends rpaCommands{
	
	private static TestData data
	private static String testDataPath = 'Data Files/Saude/InativacaoMATMED/InativacaoMATMED (1)'  
	private static String path
	private static String alerta
	private static String alertaDados
	private static String msgInativacao
	private static String msgNaoExistemDados
	private static TestData multiCentroData
//	private static String multCentersDataPath = 'Data Files/Saude/ListaMultiCentrosInativacao/ListaMultiCentroData'
	

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
	public static boolean validaDados(){
		boolean value
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(5, i).equals('Processar')) {
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
	 * @param tipoAba
	 * @return
	 */
	public static fluxoInativacaoMATMED(String tipoAba){
	//	initMultiCenroDataFiles()
		initDataFiles()
		if(validaDados()){
			fluxoCompletoInativacaoMATMED(tipoAba)
			//processaFalhas(tipoAba)
			enviarEmailInativacaoMATMED(tipoAba)
		} else {
			System.err.println('Não existem dados para processar')
		}
		finalizar();
	}
	
	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static fluxoCompletoInativacaoMATMED(String tipoAba){
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands(tipoAba);
		WebUI.delay(15);
		MenuMatriz.producaoSinistro()
		MenuMatriz.subMenu("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div/div/div[2]", "a","Sinistros de Saude")
		WebUI.delay(15);
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()
		WebUI.delay(5);
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			int lina = i-1
			alerta=""
			alertaDados=""
			msgNaoExistemDados=""
			try{
				if (data.getValue(5, i).equals('Processar')) {
					System.err.println('Line ['+i+']')
					List<String> inativacao = new ArrayList<>();
					lista( inativacao , i)
					if(inativacaoMATMED(inativacao)){
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, 'Processado', 4, 'InativacaoProcedimento');
							auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, alerta, 5, 'InativacaoProcedimento');
						} catch (Exception e){}
					} else {
						auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, 'Processado', 4, 'InativacaoProcedimento');
						auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, alerta, 5, 'InativacaoProcedimento');
					}
				}

			} catch (Exception e) {
				System.err.println(e)
				auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, 'Processar', 4, 'InativacaoProcedimento');
				auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, alerta+" "+alertaDados, 5, 'InativacaoProcedimento');
			}
		}
	}
	
	/**
	 * @author be54955
	 * @param inativacao
	 * @return
	 */
	public static boolean inativacaoMATMED(List<String> inativacao){
		boolean inativo
		System.out.println()
		try{
			
			//PROCEDIMENTOS 
			clickMenu("Procedimentos (»)",'//*[@id="divNavigationArea"]',"td")
			WebUI.delay(5)
			//PESQUISAR PROCEDIMENTO
			clickMenu("Pesquisar Procedimentos",'//*[@id="tableOptMenu_2"]',"td")
			WebUI.delay(2)
			//CÓDIGO
			preencheXpath('//*[@id="codigoAM"]', inativacao[0], 1);
			WebUI.delay(2)
			//PESQUISAR
			clickXpath('//*[@id="o_3"]', 5);
			WebUI.delay(2)
			//CONVENCIONADOS
			clickMenu("Convencionados ",'//*[@id="tableOptMenu_"]',"td")
			WebUI.delay(2)
			//Prestadores
			clickMenu("Prestadores",'//*[@id="tableOptMenu_1"]',"td")
			
			//SELECIONAR ICONE EXCLAMAÇÃO
			clickXpath('//*[@id="o_2"]', 5);
			WebUI.delay(2)
			//ENDEREÇO
			clickMenu("Endereço",'//*[@id="tabs2"]/ul',"label")
			
			//Recupera o numero de paginas
//			inativo = selecionaMulticentroUnitario(paginasTabelaRedesPrestador(),inativacao,multicentro)
//			if(!inativo){
//				selecionaMulticentro(paginasTabelaRedesPrestador(),inativacao)
//			}
			
		} catch (Exception e){
			inativo=false
			return inativo
		}
	}
	
	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailInativacaoMATMED(String tipoAba){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Saude/InativacaoMATMED/ConfiguracoesInativacaoMATMED.txt";
		lerConfigurações(pathTxtEmail);
		path1 = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Saude\\InativacaoProcedimento.xlsx"
		nome1 = "InativacaoProcedimento.xlsx";

		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
		initDataFiles()
		if(!validaDados()){
			List<String> anexos = new ArrayList<>();
			anexos[0] = path1;
			anexos[1] = nome1;
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination,anexos, tipoAba )
		} else {
			System.err.println('Existem dados para processar')
		}
	}

	/**
	 * @author T_Dvlopes
	 * @param processoFinalizado
	 * @param tipoAba
	 * @return
	 * Processa as falhas de envio de pendências ao final do processo, caso ocorra mais falha o processo é re-feito até concluir
	 */
	public static processaFalhas(String tipoAba){
		System.err.println('processa Falhas ['+tipoAba+']')
		initDataFiles()
		if(validaDados()){
			fluxoCompletoInativacaoMATMED(tipoAba)
			processaFalhas(tipoAba)
			finalizar();
		} else {
			System.err.println('Não existem dados para processar')
			finalizar();
		}
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
			if(i==4){
				sinistro[d] = ExtractDates.dateFormatteTypes("dd/MM/yyyy", "ddMMyyyy", data.getValue(i, v).toString() )
			} else {
				sinistro[d] = data.getValue(i, v).trim()
			}
		}

		for (int j = 0; j < sinistro.size();j++ ){
			System.err.println("["+j+"] ["+sinistro[j]+"]")
		}

		return sinistro
	}
	
	/**
	 * @author be54955
	 * @param menu
	 * @param xpath
	 * @param tag
	 * @return
	 */
	public static clickMenu(String menu, String xpath, String tag ){
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().contains(menu)){
				listOfElements[i].click()
				break
			}
		}
	}
}

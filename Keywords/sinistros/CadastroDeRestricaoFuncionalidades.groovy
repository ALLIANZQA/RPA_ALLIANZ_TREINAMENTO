package sinistros

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
import auxiliares.MenuMatriz;
import auxiliares.FileInput
import auxiliares.StringFormatter


/**
 * @author BE54955
 * 
 * Dev.: 23/02/2-24
 */

public class CadastroDeRestricaoFuncionalidades  extends rpaCommands {


	private static TestData data
	private static String testDataPath = 'Data Files/Sinistro/CadastroDeRestricao/CadastroDeRestricaoData'
	private static String path
	private static String alerta
	private static int cellCheck = 7
	private static boolean p ;
	private static String dataDoDocumento;


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
	public static fluxoCadastroDeRestricao(String aba){
		initDataFiles()
		fluxoCompletooCadastroDeRestricao(aba)
		reprocessaFalhas(aba)
		enviarEmailCadastroDeRestricao(aba)
	}

	
	public static String pegaDatas(TestData data ){
		Set<String> hasDatas = new HashSet<String>();
		
		for(int i = 1; i <= data.getRowNumbers(); i++) {
			hasDatas.add(data.getValue(1, i));
		}

       List<String> listData = new ArrayList<String>();
       listData.addAll(hasDatas);
       StringBuilder strbldr = new StringBuilder();
       if(listData.size()>1){ 
           strbldr.append("Datas: ")
       } else {
           strbldr.append("Data: ")
       }
	   
       for(int i = 0 ; i< listData.size();i++){
         if(listData.size()==1){
	        strbldr.append(listData[i].toString())
	        break;
         }
         strbldr.append(listData[i].toString()+" ")
       }
     return strbldr.toString();
	}
	
	
	/**
	 * @author BE54955
	 * @return
	 */
	public static menuGestaoIVS(){
		try{
			//Menu Produção & Sinistros
			MenuMatriz.producaoSinistro();
			//Gestão IVS
			clickXpath('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div/div/div[2]/ul/li[4]/a', 5);
			WebUI.delay(17);
			alteraJanejaGenerica(1,5);
			WebUI.maximizeWindow()

		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletooCadastroDeRestricao(aba){
		System.err.println()
		try{
			if(validaDados(cellCheck)){
				acessoAmbiente(aba)
				WebUI.delay(10);
				menuGestaoIVS()
				dataDoDocumento =  pegaDatas(data);
				System.out.println("\n["+dataDoDocumento+"]\n")
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						
						if(restricao(registros)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 6, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta,7 ,aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 6, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Falha no cadastro de restricao. "+alerta, 7, aba);
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
	 * @author BE54955
	 * @param sinistro
	 * @param aba
	 * @return
	 */
	public static boolean restricao(List<String> cadastro){
		System.out.println();
		boolean p;
		try{
			/**
			 * //TELA GESTÃO DE IVS
			 */

			// validar tela
			String telaGestãoIVS = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
			System.err.println("tela ["+telaGestãoIVS+"]")
			System.err.println()
			if(telaGestãoIVS.contains("Gestão de IVS".toString())){
				//IVS ativado / Irregularidade (»)
				clickXpath('//*[@id="menu1"]', 1);
				//Reivindicações especiais
				clickXpath('//*[@id="submenu2"]',1);
				//Matrícula
				limpaCampo('//*[@id="matricula"]', 0)
				preencheXpath('//*[@id="matricula"]', cadastro[1], 2);
				//Chassi
				limpaCampo('//*[@id="chasis"]', 0)
				preencheXpath('//*[@id="chasis"]', cadastro[2], 2);
				//Motivo del Fraude
				String motivo= cadastro[3]+"\n"+cadastro[4]+"\n"+cadastro[5];
				limpaCampo('//*[@id="motivo"]', 0)
				preencheXpath('//*[@id="motivo"]', motivo, 2);
				//Incluir
				clickXpath('//*[@id="sectionInformacionSectionBar"]/table/tbody/tr/td/table/tbody/tr/td[3]', 5);
				WebUI.delay(5);
				String msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
				if(alertListContinueList(msg)){
					alerta = msg;
					p = true;
				} else {
					p = false;
					alerta =" Ativação não realizada";
				}
				//Home
				clickXpath('//*[@id="optionMenuBack"]/tbody/tr/td[3]', 5);
			}

		} catch (Exception e ){
			System.out.println(e);
			alerta = "Tela [ Gestão de IVS ] não apresentada"
			p = false;
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0,1);
			menuGestaoIVS()
		}
		System.out.println()
		return p;
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
			fluxoCompletooCadastroDeRestricao(aba)
			reprocessaFalhas(aba)
		}
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviarEmailCadastroDeRestricao(String tipoAba){
		System.out.println();
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Sinistros\\CadastroDeRestricao\\ConfiguracoesCadastroDeRestricao.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "CadastroRestricao.xlsx"
				sendEmail.CreateEmail.createEmailSend(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba, dataDoDocumento );
			}
		} catch (Exception e){
		}

		try{
			WebUI.closeBrowser();
		} catch (Exception e){
		}
	}


}

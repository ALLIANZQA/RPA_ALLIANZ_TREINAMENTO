package comercial

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



public class DesvinculaCodigoCorretor extends rpaCommands{
	
	private static TestData data
	private static String testDataPath = 'Data Files/Comercial/DesvinculaCodigoCorretorData'
	private static String path
	private static String alerta
	private static String statusFail
	private static int cellCheck = 6
	
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
	 * @param tipoAba
	 * @return
	 */
	public static DesvinculaCodigoCorretorFuncionalidades(String tipoAba){
		DesvinculaCodigoCorretorFuncionalidadesCompleto(tipoAba)
		processaFalhas(tipoAba)
		enviarEmailDesvinculaCodigoCorretor(tipoAba)
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static DesvinculaCodigoCorretorFuncionalidadesCompleto(String aba){
		initDataFiles()
		if(validaDados(cellCheck)){
			acessoAmbiente(aba)
			WebUI.delay(15);
			MenuMatriz.manutancaoGeral()
			WebUI.delay(2);
			MenuMatriz.subMenu("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div/div/div[2]/ul", "a", "UMA")
			WebUI.delay(17);
			alteraJanejaGenerica(1, 5)
			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				if (data.getValue(cellCheck, i).equals('Processar')) {
					List<String> registros = new ArrayList<>();
					lista( registros , i)
					if(fluxoDesvinculaCodigoCorretor(registros, aba)) {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 5, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 6, aba);
						} catch (Exception e){}
					} else {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, statusFail, 5, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Falha ao Desvincular Codigo Corretor. "+alerta, 6, aba);
						} catch (Exception e){}
					}
				}
			}
		} else {
			System.err.println('Não existem dados para processar')
		}
	}
	
	
	public static boolean fluxoDesvinculaCodigoCorretor( List<String> registros, String aba){
		boolean enviado
		System.out.println()
		try{
			alerta=""
			statusFail = ""
			/**
			 * TELA - APLICAÇÃO DE GERENCIAMENTO DE USUARIOS
			 */
			//DESCRICAO
			preencheXpath('//*[@id="codigo"]', registros[0], 3);
			//PESQUISAR
			clickXpath('//*[@id="queryButton"]', 3);
			//ALLIANZNET
			clickXpath('//*[@id="listaAppsWeb_0_0"]', 3);
			WebUI.delay(10);
			//TABELA DE NOMES 
			if(selecionaNome(registros[4])){
			  //DESATIVAR
			  selecionaBotao("Desativar",'//*[@id="sectionDetallSucursalEpacSectionBar"]/table',"div")
			  WebUI.delay(1);
			  //DESATIVAR
			  selecionaBotao("Desativar",'//*[@id="sectionDetallSucursalEpacSectionBar"]/table',"div")
			  WebUI.delay(10);
			  String msg = pegaMSGAlerta()
			  System.err.println(msg)
			  if(msg.contains("Confirmar operação")){
				alerta = "Operação confirmada"
				try{
				     WebUI.acceptAlert()
				     enviado = true
					 selecionaBotao("Início",'//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td',"div")
				   } catch (Exception e){
				     enviado = false
					 selecionaBotao("Início",'//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td',"div")
				   }
			  }
			} else {
			  enviado = false
			  alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]',"innerText", 2)
			  if(alerta.contains("atributo nao encontrado")){
				  alerta="Código do corretor não encontrado"
				  statusFail = "Processado"
			  }
			  System.err.println(alerta)
			  clickXpath('//*[@id="imgOptMenuBackHome"]', 1);
			}
		} catch (Exception e){
		    alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]',"innerText", 2)
			if(alerta.contains("atributo nao encontrado")){
				alerta=""
				statusFail = "Processar"
			} else {
			    statusFail = "Processado"
			}
			clickXpath('//*[@id="imgOptMenuBackHome"]', 1);
		 	enviado = false
		}
		return enviado
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean selecionaNome(String nome){
		boolean found = false
		String xpath = '//*[@id="listaSucursalesForm"]/div[1]'
		String tag ="tr";
		WebUI.delay(5)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().contains(nome.toString())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				listOfElements[i].click()
				found = true
				break
			}
		}
		return found
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaBotao(String nome,String xpath, String tag ){
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().contains(nome.toString())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				listOfElements[i].click()
				break
			}
		}
	}
	
	/**
	 * @author T_Dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailDesvinculaCodigoCorretor(String tipoAba){
		initDataFiles()
		if(!validaDados(cellCheck)){
			try{
		      pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Comercial\\DesvinculaCodigoCorretor\\ConfiguracoesDesvinculaCodigoCorretor.txt";
		      lerConfigurações(pathTxtEmail);
		      String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
		      path1 = 'C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Comercial\\DesvinculaCorretor.xlsx';
		      String nomeArquivo = 'DesvinculaCorretor.xlsx';
		      List<String> anexos = new ArrayList<>();
				   anexos[0] = path1;
				   anexos[1] = nomeArquivo;
			  sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba);
		} catch (Exception e){
			enviarEmailDesvinculaCodigoCorretor(tipoAba);
		}
		}
	}
	
	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static processaFalhas(String tipoAba){
		System.err.println('[processa Falhas]');
		initDataFiles()
		if(validaDados(cellCheck)){
		   DesvinculaCodigoCorretorFuncionalidadesCompleto(tipoAba)
		   processaFalhas(tipoAba)
		}
	}
	
}

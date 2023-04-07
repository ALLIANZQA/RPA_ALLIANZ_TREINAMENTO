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

public class InativacaoProcedimentoFuncionalidades extends rpaCommands{


	//	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Saude/InativacaoProcedimentoData'
	private static String path
	private static String alerta
	private static String alertaDados
	private static String msgInativacao
	private static String msgNaoExistemDados
	private static TestData multiCentroData
	private static String multCentersDataPath = 'Data Files/Saude/ListaMultiCentrosInativacao/ListaMultiCentroData'
	private static String botaoProximoEndereco = '//*[@id="o_47"]'

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
	 * @return
	 */
	public static initMultiCenroDataFiles(){
		multiCentroData = findTestData(multCentersDataPath)
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 * Planilha : QA_MULTICENTROS_ANULACAO_PROCED
	 * multiCentroData.getValue(1, i) = Coluna A
	 * multiCentroData.getValue(7, i) = Coluna G
	 * 
	 */
	public static foudMultiCenroDataFiles(List<String> inativacao){
		System.err.println()
		List<String> lista = new ArrayList<>();
		for (int i = 1; i <= multiCentroData.getRowNumbers(); i++) {
		//	System.err.println("\nLine ["+i+"] ["+multiCentroData.getValue(1, i)+"] ["+inativacao[0]+"] ["+multiCentroData.getValue(7, i)+"] ["+inativacao[1]+"]")
					if(multiCentroData.getValue(1, i).equals(inativacao[0]) && multiCentroData.getValue(7, i).equals(inativacao[1])){
						System.err.println("\nLine ["+i+"] Found ["+multiCentroData.getValue(1, i)+"] ["+inativacao[0]+"] ["+multiCentroData.getValue(7, i)+"] ["+inativacao[1]+"]")
						listaMultiCentro( lista , i)
						break
					}
		}
		return lista
	}
	
	
	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static List<String> listaMultiCentro( List<String> lista , int v){
		for (int i = 1; i <= multiCentroData.getColumnNumbers(); i++) {
			int d = i -1
			lista[d] = multiCentroData.getValue(i, v).trim()
		}

		for (int j = 0; j < lista.size();j++ ){
			System.err.println("["+j+"] ["+lista[j]+"]")
		}
		return lista
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
	 * @param tipoAba
	 * @return
	 */
	public static fluxoInativacaoProcedimento(String tipoAba){
	    initMultiCenroDataFiles()
		initDataFiles()
		if(validaDados()){
			fluxoCompletoInativacaoProcedimento(tipoAba)
			//processaFalhas(tipoAba)
			enviarEmailInativacaoProcedimento(tipoAba)
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
	public static fluxoCompletoInativacaoProcedimento(String tipoAba){
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
					if(inativacaoProcedimento(inativacao, foudMultiCenroDataFiles(inativacao))){
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
	public static boolean inativacaoProcedimento(List<String> inativacao, List<String> multicentro){
		boolean inativo
		try{
			WebUI.delay(10)
			//CONVENCIONADOS
			clickMenu("Convencionados ",'//*[@id="tableOptMenu_"]',"td")
			WebUI.delay(2)
			//Prestadores
			clickMenu("Prestadores",'//*[@id="tableOptMenu_1"]',"td")
			WebUI.delay(2)
			//NUMERO DOCUMENTO
			preencheXpath('//*[@id="nif"]', inativacao[0], 0);
			WebUI.delay(2)
			//PESQUISAR
			clickXpath('//*[@id="o_3"]', 5);
			WebUI.delay(2)
			//SELECIONAR ICONE EXCLAMAÇÃO
			clickXpath('//*[@id="o_2"]', 5);
			WebUI.delay(2)
			//ENDEREÇO
			clickMenu("Endereço",'//*[@id="tabs2"]/ul',"label")
			//Recupera o numero de paginas 
			inativo = selecionaMulticentroUnitario(paginasTabelaRedesPrestador(),inativacao,multicentro)
			if(!inativo){
				selecionaMulticentro(paginasTabelaRedesPrestador(),inativacao)
			}
			
		} catch (Exception e){
			inativo=false
			return inativo
		}
	}

	
	/**
	 * @author be54955
	 * @param p
	 * @param inativacao
	 * @return
	 */
	public static boolean selecionaMulticentroUnitario(int p, List<String> inativacao, List<String> multicentro) {
		
		System.err.println("Paginas ["+p+"]")
		
		boolean inativo
		int index
		int b
		
		for(int i = 0; i< p; i++){
			b = 6
			int l = linhasTabelaRedesPrestador()
			System.err.println("Linhas ["+l+"]")
			selecionaListaDeElementos("//*[@id='domiciliosList_tbody']","tr")
			int listSize = listOfElements.size()
			 for(int j = 0; j < listSize; j++){
			//	 System.err.println("["+j+"] ["+listOfElements.get(j).getText()+"] ["+multicentro[3]+"]")
				 if(listOfElements.get(j).getText().contains(multicentro[3])){
				    System.err.println("Found ["+j+"] ["+listOfElements.get(j).getText()+"]")
					clickXpath('//*[@id="domiciliosList_'+j+'_3"]', 5);
					inativo = inativaProcedimento(inativacao, i)
					System.err.println(inativo)
					break
				 }
				 b++
				 
				 if(j == listSize-1){
					 try{
						 //Próximo
						  clickXpath(botaoProximoEndereco, 2); 
						 WebUI.delay(2)
						 selecionaListaDeElementos("//*[@id='domiciliosList_tbody']","tr")
						 WebUI.delay(2)
						 System.err.println("New Table Size ["+listOfElements.size()+"]")
					   } catch (Exception e){
						 System.err.println("[End pages of table]")
						 
					   }
				 }
			 }
		}
		System.err.println(inativo)
		return inativo
	}
	
	/**
	 * @author be54955
	 * @param p
	 * @param inativacao
	 * @return
	 */
	public static boolean selecionaMulticentro(int p, List<String> inativacao) {
		boolean inativo
	    int index 
		int b 
		System.err.println("Paginas ["+p+"]")
		for(int i = 0; i< p; i++){
			
			//INDEX DA PRIMEIRA LINHA DA TABELA DE MULTICENTRO
			b = 10
			int l = linhasTabelaRedesPrestador()
			System.err.println("Linhas ["+l+"]")
			selecionaListaDeElementos("//*[@id='domiciliosList_tbody']","tr")
			int listSize = listOfElements.size()
			 for(int j = 0; j < listSize; j++){
				 System.err.println("["+j+"] Botão ["+b+"]")
				 String strb = String.valueOf(b)
				 clickXpath('//*[@id="o_'+strb+'"]', 5);
				 inativo = inativaProcedimento(inativacao, i)
				 System.err.println(inativo)
				 if(inativo){
					 break
				 }
				
				 b++
				 if(j == listSize-1){
					 try{
						 //Próximo
				 	     clickXpath(botaoProximoEndereco, 2);
					     WebUI.delay(2)
					     selecionaListaDeElementos("//*[@id='domiciliosList_tbody']","tr")
					     WebUI.delay(2)
					     System.err.println("New Table Size ["+listOfElements.size()+"]")
					   } catch (Exception e){
					     System.err.println("[End pages of table]")
						 
					   }
				 }
			 }
		}
		return inativo
	}
	
    /**
     * @author be54955	
     * @return
     */
	public static int linhasTabelaRedesPrestador(){
		selecionaListaDeElementos("//*[@id='domiciliosList_tbody']","tr")
		return listOfElements.size()
	}
	
	/**
	 * @author be54955
	 * @param inativacao
	 * @return
	 */
	public static boolean inativaProcedimento(List<String> inativacao, int pagina){
		boolean inativo
		String multicentro = recuperaValor('//*[@id="domicilio.contaMul"]', 'value', 3);
		
		System.err.println("Multicentro Tela ["+multicentro+"] Multicentro para selecionar ["+inativacao[1]+"]")
		
		WebUI.delay(2)
		try{
		if(multicentro.equals(inativacao[1])){
		
			System.err.println("Multicentro ["+multicentro+"] ["+inativacao[1]+"] encontrado ")
			//PROCEDIMENTOS
			clickMenu("Procedimentos",'//*[@id="sectionPaginaCompletaSectionBar"]/table',"div")
			//Código Procedimento
			preencheXpath('//*[@id="codigoActoMedico"]', inativacao[2], 0);
			//PESQUISAR
			clickXpath('//*[@id="o_13"]', 10);
			
			WebUI.delay(5)
			msgNaoExistemDados = auxenciaDeDados()
			System.err.println("["+msgNaoExistemDados+"]")
			if(msgNaoExistemDados.contains("Não existem dados".toString())){
				alerta = "Não existem dados"
				inativo=false
			}
			
			//SELECIONAR ICONE EXCLAMAÇÃO
			clickXpath('//*[@id="o_2"]', 10);
			//EDITAR
			clickXpath('//*[@id="o_3"]', 5);
			//DATA FIM
			limpaCampo('//*[@id="dataFimStr"]',1)
			preencheXpath('//*[@id="dataFimStr"]', inativacao[3], 0);
			//GRAVAR
			clickXpath('//*[@id="o_4"]', 5);

			//ALERTA
			WebUI.delay(5)
			msgInativacao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 3);
			System.err.println("["+msgInativacao+"]")
			if(msgInativacao.contains("Operação realizada com sucesso.".toString())){
				alerta = "Operação realizada com sucesso."
				inativo=true
			} else if(msgInativacao.contains("A data final tem que ser maior que a data de início.".toString())){
				alerta = "A data final tem que ser maior que a data de início."
				inativo=false
			} else {
				inativo=false
			}
		} else {
		  alerta = "Multicentro não encontrado"
		  inativo=false
		  //Voltar
		  clickXpath('//*[@id="o_19"]',5);
		  //ENDEREÇO
		  clickMenu("Endereço",'//*[@id="tabs2"]/ul',"label")
		  if(pagina >0){
			  //Próximo
			  clickXpath(botaoProximoEndereco, 2);  
	       }
		}
		} catch (Exception e){
		//  alerta = "Procedimento não encontrado"
		  inativo=false
	  }
		return inativo
	}
	
	/**
	 * @author be54955
	 * @return
	 */
	public static int paginasTabelaRedesPrestador(){
		int paginas
		selecionaListaDeElementos('//*[@id="domiciliosListSectionContent"]',"div")
		 for(int i = 0; i < listOfElements.size(); i++){
			 if(i==3){
				 System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
				 String p = listOfElements[i].getText().subSequence(2, 5)
				 p = p.replace(" / "," ")
				 String[] l = p.split(" ")
				 for(int j=0;j<l.size();j++){
					 if(j==1){
						paginas = Integer.parseInt(l[j])
					 }
				 }
				 break
			 }
		 }
		 return paginas
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

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailInativacaoProcedimento(String tipoAba){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Saude/InativacaoProcedimento/ConfiguracoesInativacaoProcedimento.txt";
		lerConfigurações(pathTxtEmail);
		path1 = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Saude\\InativacaoProcedimento.xlsx"
		nome1 = "InativacaoProcedimento.xlsx";

		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
		initDataFiles()
		if(!validaDados()){
			List<String> anexos = new ArrayList<>();
			anexos[0] = path1;
			anexos[1] = nome1;
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba )
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
			fluxoCompletoInativacaoProcedimento(tipoAba)
			processaFalhas(tipoAba)
			finalizar();
		} else {
			System.err.println('Não existem dados para processar')
			finalizar();
		}
	}

	
	/**
	 * @author be54955
	 * @return
	 */
	public static String auxenciaDeDados(){
		String msg
		selecionaListaDeElementos('//*[@id="resposta_tbody"]/tr',"td")
		 for(int i = 0; i < listOfElements.size(); i++){
				 System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
				 msg = listOfElements[i].getText()
				 break
		 }
	 return msg
   }
	
	
}

package gcf

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
import auxiliares.MenuMatriz
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.ExtractDates

public class DistribuicaoAutomaticaInspecaoRiscoFuncionalidades extends rpaCommands{

	private static TestData data
	private static String testDataPath = 'Data Files/GCF/DistribuicaoAutomaticaInspecaoRiscoData'
	private static String path
	private static int cellStatus = 23
	private static boolean selecionatemTabelaVerificacao = true
	private static String alerta
	private static boolean registroModificado



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
			if (data.getValue(cellStatus, i).equals('Processar')) {
				value = true
				break;
			} else {
				value = false
				//		break;
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
	public static fluxoDistribuicaoAutomaticaInspecaoRiscoFuncionalidades(String aba){
		initDataFiles()
		if(validaDados()){
			fluxoCompletoDistribuicaoAutomaticaInspecaoRiscoFuncionalidades(aba)
			processaFalhas(aba)
			enviarEmailDistribuicaoAutomaticaInspecaoRiscoFuncionalidades(aba)
		} else {
			System.err.println('Não existem dados para processar')
		}
		finalizar();
	}

	/**
	 * @author T_Dvlopes
	 * @param processoFinalizado
	 * @param tipoAba
	 * @return
	 * Processa as falhas de envio de pendências ao final do processo, caso ocorra mais falha o processo é re-feito até concluir
	 */
	public static processaFalhas(String tipoAba){
		System.err.println('---- Processa Falhas ['+tipoAba+'] ----')
		initDataFiles()
		if(validaDados()){
			fluxoCompletoDistribuicaoAutomaticaInspecaoRiscoFuncionalidades(tipoAba)
			processaFalhas(tipoAba)
			finalizar();
		} else {
			System.err.println('Não existem dados para processar')
			finalizar();
		}
	}

	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static fluxoCompletoDistribuicaoAutomaticaInspecaoRiscoFuncionalidades(String tipoAba){
		init(tipoAba)
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			int lina = i-1
			alerta = ''
			try{
				if (data.getValue(cellStatus, i).equals('Processar')) {
					System.err.println('\nLine ['+i+']\n')
					registroModificado = false
					alerta = ""
					List<String> distribuicao = new ArrayList<>();
					lista( distribuicao , i)
					WebUI.delay(3);
					if(modificarRegistro(distribuicao)){
					if(selecionatemTabelaVerificacao){
						criarRegistro(distribuicao)
					}

					try{
						if(registroModificado){
							System.out.println("alerta ["+alerta+"]")
							auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, 'Processado', 22, 'DistribuicaoAutomaticaIR');
							auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, alerta+" - "+ExtractDates.dataAtualizada(), 24, 'DistribuicaoAutomaticaIR');
						} else {
							auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, 'Processado', 22, 'DistribuicaoAutomaticaIR');
							auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, 'Distribuição não confirmada'+" - "+ExtractDates.dataAtualizada(), 23, 'DistribuicaoAutomaticaIR');
							WebUI.closeBrowser();
							init(tipoAba)
						}
					} catch (Exception e){}
				   
				    } else {
				      auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, 'Processado', 22, 'DistribuicaoAutomaticaIR');
				      auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, alerta, 23, 'DistribuicaoAutomaticaIR');
				    }
			   }
			} catch (Exception e) {
				auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, 'Processado', 22, 'DistribuicaoAutomaticaIR');
				auxiliares.WriteExcelFactory.updateSheetLine("", lina,path, alerta, 23, 'DistribuicaoAutomaticaIR');
			}
		}
	}

	/**
	 * @author be54955
	 * @param distribuicao
	 * @return
	 */
	public static boolean modificarRegistro(List<String> distribuicao){
		System.out.println()
		boolean modificar
		try{
			//Assignação Automática
			clickXpath('//*[@id="1"]', 10);

			//Tipo Profissão
			selecionaList(distribuicao[10], '//*[@id="tipoProf"]', 'option', 0 )

			//Cod Prof
			selecionaList(distribuicao[1], '//*[@id="grupoProf"]', 'option', 0 )

			//Opcoes Disponiveis
			selecionaList(distribuicao[9], '//*[@id="opcao"]', 'option', 0 )

			//Aceitar
			//clickXpath('//*[@id="o_4"]', 5);
			selecionaList("Aceitar", '//*[@id="sectionEscolhaInicialSectionBar"]/table/tbody', 'div', 5 )

			//Zona
			limpaCampo('//*[@id="pesquisaZona"]', 0)
			preencheXpath('//*[@id="pesquisaZona"]', distribuicao[2], 0);

			//Aceitar
			clickXpath('//*[@id="o_3"]', 5);

			//TABELA VERIFICAÇÕES
			selecionatemTabelaVerificacao(distribuicao)
			WebUI.delay(5)
			alerta = recuperaValor('//*[@id="tabelaManutencao_tbody"]/tr', 'innerText', 2)
			
			if( alertListBlocList(alerta)){
				modificar = false;
			}else{
			    modificar = true;
			}
		} catch (Exception e){
			modificar = false
			alerta = 'Falha ao modificar registro'
			selecionatemTabelaVerificacao = true
		}
		return modificar;
	}

	/**
	 * @author be54955
	 * @param distribuicao
	 * @return
	 */
	public static criarRegistro(List<String> distribuicao){
		boolean criar
		try{
			//ALTA
			clickXpath('//*[@id="botaoAlta"]', 5);

			//ZONA
			preencheXpath('//*[@id="zonaNew"]', distribuicao[2], 0);

			//NIF
			preencheXpath('//*[@id="nif_doc"]', distribuicao[3], 0);

			//CARGA DE TRABALHO
			limparCamposInput('//*[@id="cargaTrabajo"]')
			preencheXpath('//*[@id="cargaTrabajo"]', distribuicao[6], 0);

			//ALTA
			clickXpath('//*[@id="o_4"]', 5);
			fechaAlerta();
			WebUI.delay(5)
			alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 2)

			//Inicio
			clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
			criar = true
			registroModificado = true
		} catch (Exception e){
			criar = false
			registroModificado = false
		}
	}



	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionatemTabelaVerificacao(List<String> distribuicao ){
		try{
			WebUI.delay(5)
			String xpath = '//*[@id="tabelaManutencao_tbody"]'
			//		int tableLines = listOfElements.size()
			//		System.out.println("Table Lines ["+tableLines+"]")
			String tag = 'tr'
			selecionaListaDeElementos(xpath,tag)
			int tableLines = listOfElements.size()
			System.out.println("Table Lines ["+tableLines+"]")
			String t = StringFormatter.removeEspacosEmBranco(distribuicao[3]);
			
			for(int i = 0; i < tableLines; i++){
				System.out.print("*")
				System.out.println("\n["+i+"] ["+listOfElements[i].getText()+"] ["+t+"]")
				if(listOfElements[i].getText().contains(t.trim())){
					System.out.println("\n["+i+"] ["+listOfElements[i].getText()+"] ["+t+"]")
					String index = String.valueOf(i)
					System.out.print("index: ["+index+"]")
					String xpathCheck = '//*[@id="checkBox'+index+'"]'
					clickXpath(xpathCheck, 1);
					//Modificar
					clickXpath('//*[@id="botaoModif"]', 5);
					//Carga de trabalho
					limparCamposInput('//*[@id="cargaTrabajo"]')
					preencheXpath('//*[@id="cargaTrabajo"]', distribuicao[6], 1);
					//Modificar
					clickXpath('//*[@id="o_3"]', 5);
					fechaAlerta();
					selecionatemTabelaVerificacao = false
					registroModificado = true
					break
				}
				selecionatemTabelaVerificacao = true
			}
		} catch (Exception e){
			registroModificado = false
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaList(String operativa, String xpath, String tag, int delay ){
		if(delay>0){
			WebUI.delay(delay)
		}
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().contains(operativa.toString())){
				listOfElements[i].click()
				break
			}
		}
	}

	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static init(String tipoAba){
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands(tipoAba);
		WebUI.delay(15);
		MenuMatriz.producaoSinistro()
		WebUI.delay(15);
		MenuMatriz.subMenu("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div/div/div[2]/ul", "a","Manutençao Atribuição Professionais")
		WebUI.delay(15);
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()
		WebUI.delay(5);
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
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailDistribuicaoAutomaticaInspecaoRiscoFuncionalidades(String tipoAba){
		pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Gcf\\DistribuicaoAutomaticaInspecaoRisco\\ConfiguracoesDistribuicaoAutomaticaInspecaoRisco.txt";
		lerConfigurações(pathTxtEmail);
		path1 = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\GCF\\DistribuicaoAutomaticaInspecaoRisco.xlsx"
		nome1 = "DistribuicaoAutomaticaInspecaoRisco.xlsx";

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
}

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
import auxiliares.ExtractDates

public class IndexacaoFGFuncionalidades extends rpaCommands{

	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/IndexacaoFG/IndexacaoFGData'
	private static String path
	private static String alerta
	private static int cellCheck = 4
	private static String filepath = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\IndexacaoFG\\Arquivos\\"
	private static String completeFilePath

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
	 * @author be54955
	 * @param aba
	 * @param a
	 * @return
	 */
	public static init(String aba, String a){
		try{
			acessoAmbiente(aba,a)
			WebUI.delay(10);
			WebUI.delay(10);
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoIndexacaoFG(String aba, String a){
		initDataFiles()
		fluxoCompletoIndexacaoFG(aba,a)
		reprocessaFalhas(aba,a)
		enviarEmailIndexacaoFG(aba)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoIndexacaoFG(aba,a){
		try{
			if(validaDados(cellCheck)){
				init(aba,a);
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						String arquivo = registros[4].toString()
						registros[5] = "SICREDI"
						registros[6] = "12/06/2024"
						if(!validaExistenciaDeArquivo(filepath)){
							if(indecacao(registros, aba)) {
								try{
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 3, aba);
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 4, aba);
									//				deleteFileDiretory(completeFilePath)
								} catch (Exception e){}
							} else {
								try{
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 3, aba);
									auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Falha ao anexar arquivo. "+alerta, 4, aba);
								} catch (Exception e){}
							}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 3, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Arquivo "+arquivo+" não encontrado para indexação", 4, aba);
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
	public static boolean indecacao( List<String> registros, String aba){
		WebUI.delay(15);
		System.out.println()
		MenuMatriz.consultasEstatisticas();
		MenuMatriz.consultasGerais();
		boolean enviado
		String tela
		try{
			alerta=""
			completeFilePath=""
			alteraJanejaGenerica(1, 5)
			System.err.println("alteraJanejaGenerica")
			"Valida campo proposta devido a auxência do nome da tela"
			String campoProposta = recuperaValor('//*[@id="sectionApoliceContent"]/table/tbody/tr[1]/td[1]/table/tbody/tr/td/table/tbody/tr[1]/td/table/tbody/tr[1]/td[1]/table/tbody/tr/td[1]', 'innerText', 5)
			if(campoProposta.equals("Proposta")){
				//Proposta
				preencheXpath('//*[@id="apolice"]', registros[0], 1);
				//Pesquisar
				selecionaItemLista('Pesquisar', '//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td', 'td')
				// innerText : "DADOS GERAIS (AZR)"
				WebUI.delay(15);
				tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
				if(listaDeTelas(tela)){
					//Ficha Gestão
					selecionaItemLista('Ficha Gestão', '//*[@id="menuLateral"]', 'span')
					alteraJanejaGenerica(2, 15)
					//Ficha Gestão
					tela = recuperaValor( '/*/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/div/div/div[1]', 'innerText', 2)
					if(listaDeTelas(tela)){
						//Indexação
						try{
							selecionaItemLista('Indexação', '/*/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div', 'span')
						} catch (Exception e){}

						try{
							selecionaItemLista('Indexación', '/*/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div', 'span')
						} catch (Exception e){}

						alteraJanejaGenerica(3, 5)
						//Valida tela "ANEXAR DOCUMENTOS - LI"
						tela = recuperaValor( '//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
						if(listaDeTelas(tela)){
							//Modelo/Descrição
							preencheXpath('//*[@id="codigo"]', registros[1], 1);
							commandTAB('//*[@id="codigo"]')
							WebUI.delay(3);
							//File Path
							String path = '//*[@id="upFile1"]'
							String fileName = identificaArquivosDiretorio( filepath, registros[5]);
							System.err.println(fileName);
							fileUpload(path,  filepath+fileName , 5)
							//Observações/texto
							//preencheXpath('//*[@id="texto"]', registros[2], 1);
							preencheXpath('//*[@id="texto"]', textoCampoObs(registros[5], fileName, registros[6]), 1);
							//Salvar
							selecionaItemLista('Salvar', '//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table', 'td')
							WebUI.delay(5);
							alerta = pegaMSGAlerta()
							System.out.println(alerta)
							fechaAlerta()
							enviado = true;
							//fecharJanelaAtual(3)
							alteraJanejaGenerica(2, 0)
							fecharJanelaAtual(2)
							alteraJanejaGenerica(1, 0)
							fecharJanelaAtual(1)
							alteraJanejaGenerica(0, 0)
						} else {
							alerta = "Tela - ANEXAR DOCUMENTOS - LI - Não apresentada";
							enviado = false;
						}
					} else {
						alerta = "Tela - Ficha de Gestão  - Não apresentada";
						enviado = false;
					}
				} else {
					alerta = "Tela - Ficha de Gestão - Não apresentada";
					enviado = false;
				}
			}
		} catch (Exception e){
			enviado = false;
			alerta = "Falha no processo de indexação";
		}
		return enviado
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailIndexacaoFG(String tipoAba){
		initDataFiles()
		if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\IndexacaoFG\\ConfiguracoesIndexacaoFG.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = "IndexacaoFG.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
		}
		try{
			WebUI.closeBrowser()
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba, String a){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoIndexacaoFG(aba,a)
			reprocessaFalhas(aba,a)
		}
	}

	/**
	 * @author Alz_sup54955
	 * @param p
	 * @param n
	 * @return
	 */

	public static String identificaArquivosDiretorio(String p, String n){
		String fileName;
		File f = new File(p);
		File [] files = f.listFiles();
		for(int i = 0; i < files.size(); i++){
			if(files[i].getName().toString().contains(n)){
				fileName = files[i].getName();
			}
		}
		return fileName;
	}

	/**
	 * @author BE54955
	 * @param t
	 * @param f
	 * @return
	 */

	public static String textoCampoObs(String t, String f, String d){
		Map<String, String> obs = new HashMap<String, String>()
		obs.put('HONDA','Envio Pro Atividade Honda, Vistoria e Laudo de Inspeção '+d);
		obs.put('SICREDI',f.replace(".xlsx", ""));
		return obs.get(t.toString());
	}
}

package backoffice


import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
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
import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import auxiliares.RegrasEmailDestination
import auxiliares.MenuMatriz
import internal.GlobalVariable
import rpacommands.rpaCommands
import auxiliares.ExtractDates



public class ReativacaoApoliceFuncionalidades extends rpaCommands{

	private static TestData data
	private static TestData datatplog
	private static String dataPath = 'Data Files/Backoffce/ReativacaoApolice/ReativacaoApoliceData'
	private static String path
	private static int rollCheck = 4



	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(dataPath)
		path = data.sourceUrl
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static boolean validaDados(int cellStatus){
		System.out.println("RowNumber "+data.getRowNumbers())
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
	public static acessoAmbiente(String aba, String a){
		iniciaAplicacaoMatrizCommands(retornaAnbiente(a));
		String abaAmbiente = aba+a
		System.out.println('['+abaAmbiente+']')
		switch (a){
			case "INT":
				realizaLoginIntraNetProducaoTransacionalCommands(abaAmbiente,'Produção e recibos');
				break;
			case "PRD":
				realizaLoginIntraNetProducaoCommands(abaAmbiente)
				break;
		}
	}

	public static String retornaAnbiente(String aba){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('PRD','IntranetProdução');
		urls.put('INT','IntranetTransacional');
		return urls.get(aba.toString());
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static menuProducaoSinistros(){
		MenuMatriz.subMenuEquals('/*/body/div/div/app-root/app-private/app-private-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div/ul', "span", "Produção & Sinistros")
		MenuMatriz.subMenuEquals("/*/body/div[1]/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul", "a", "Cancelamento 2")
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoReativacaoApolice(String aba, String a){
		fluxoCompletoReativacaoApolice(aba, a)
		reprocessaFalhas(aba,a)
		enviarEmailReativacaoApolice(aba)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba, String a ){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(rollCheck)){
			fluxoCompletoReativacaoApolice(aba, a)
			reprocessaFalhas(aba,a)
		}
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailReativacaoApolice(String tipoAba){
		System.out.println()
		initDataFiles()
		try{
			if(!validaDados(rollCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\ReativacaoApolice\\ConfiguracoesReativacaoApolice.txt";
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "ReativacaoApolice.xlsx"
				System.out.println(anexos[0]+" - "+anexos[1])
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba )
			}
		} catch (Exception e){
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoReativacaoApolice(String aba, String a){
		System.out.println()
		try{
			initDataFiles()
			System.out.println('['+aba+'] ['+a+']')
			if(validaDados(rollCheck)){
				acessoAmbiente(aba,a)
				WebUI.waitForPageLoad(20)
				menuProducaoSinistros()
				WebUI.waitForPageLoad(15)
				alteraJanejaGenerica(1, 15)
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					alerta=""
					if (data.getValue(rollCheck, i).equals('Processar')) {
						System.out.println("["+i+"] ["+a+"]")
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						if(fluxoReativa(registros,aba, a)){
							try{
								//System.err.println("[PASSED] ["+registros[4]+"] ["+registros[3]+"] ["+ path+"]")
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[3],3, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[4]+" "+ExtractDates.dataAtualizada(),4, aba);
							} catch (Exception e){}
						} else {
							try{
								System.err.println("[N] ["+registros[4]+"]")
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[3],3, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[4]+" "+ExtractDates.dataAtualizada(),4, aba);
							} catch (Exception e){}
						}
					}
				}
			}
		}catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param aba
	 * @return
	 */
	public static boolean fluxoReativa(List<String> registros, String aba, String a){
		boolean b
		String s
		System.out.println("")
		WebUI.waitForPageLoad(15)
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0))){
			try{
				//Reposição em vigor
				clickXpath('//*[@id="menu13"]', 0);
				//Proposta
				preencheXpath('//*[@id="susep_Policy"]', registros[0], 0);
				commandTAB('//*[@id="susep_Policy"]')
				//Item Proposta
				limpaCampo('//*[@id="susep_Application"]', 0)
				preencheXpath('//*[@id="susep_Application"]', registros[1], 0);
				//Pesquisar Motivos
				selecionaItemLista('Pesquisar motivos', '//*[@id="sectionPaginaCompletaSectionBar"]', 'div')


				s = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
				System.out.println("Erro ["+s+"]")

				if(alertListBlocList(s)){
					//Home
					clicarEmElementoDaTela('//*[@id="imgOptMenuBackHome"]', 1)
					registros[3] = 'Processado'
					registros[4] = s
					b = false
				} else {
					s=''
					//Reavaliar
					selecionaItemLista('Revalidar', '//*[@id="sectionPaginaCompletaSectionBar"]', 'div')
					//Justificativa
					try{
						limpaCampo('//*[@id="justificacion"]', 0)
						preencheXpath('//*[@id="justificacion"]', registros[2], 0);
					} catch (Exception e){}
					//Confirmar
					selecionaItemLista('Confirmar reposição', '//*[@id="SectionCSectionBar"]/table/tbody/tr/td', 'div')
					WebUI.delay(5);
					s = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0)
					System.out.println("Confirmação ["+s+"]")
					//Home
					clicarEmElementoDaTela('//*[@id="imgOptMenuBackHome"]', 1)
					registros[3] = 'Processado'
					registros[4] = s
					b = true
				}
			} catch (Exception e){
				//Home
				clicarEmElementoDaTela('//*[@id="imgOptMenuBackHome"]', 1)
				selecionaItemLista('Confirmar reposição', '//*[@id="anulacionesForm"]/sea:sendthreadid/sea:sendsynctoken/table/tbody/tr[2]/td', 'div')
				registros[4] = "erro "
				registros[3] = 'Processado'
				b = false
			}

		} else {
			registros[4] = " CANCELAMENTO E SUSPENSÕES"
			registros[3] = 'Processado'
			WebUI.closeBrowser();
			acessoAmbiente(aba,a)
			WebUI.delay(20);
			//menuEmissao()
			WebUI.delay(15);
			alteraJanejaGenerica(1, 15)
			b = false
		}
		System.out.println("["+	b+"] ["+s+"]")
		return b
	}



}

package auto


//import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
//import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
//import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
//import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.util.List

//import com.kms.katalon.core.annotation.Keyword
//import com.kms.katalon.core.checkpoint.Checkpoint
//import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
//import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
//import com.kms.katalon.core.model.FailureHandling
//import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
//import com.kms.katalon.core.testobject.TestObject
//import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
//import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import auxiliares.FileInput
import auxiliares.StringFormatter
//import auxiliares.switchAtualizarExcel
//import auxiliares.switchLeitorExcel
//import auxiliares.RegrasEmailDestination
import auxiliares.MenuMatriz
//import internal.GlobalVariable
import rpacommands.rpaCommands
import auxiliares.ExtractDates
//import auxiliares.timer

public class RecusaPropostaAutoFuncionalidades extends rpaCommands{

	private static TestData data
	private static String testDataPath = 'Data Files/Auto/RecusaPropostaAuto/RecusaPropostaAutoData'
	private static String path
	private static String alerta
	private static int cellCheck = 2
	private static String status

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		System.out.println(data.getRowNumbers())
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
	public static fluxoRecusaPropostaAuto(String aba, String a, int t, int p){
//		if(timer.executeAtTime(t,p)){
			initDataFiles()
			fluxoCompletoRecusaPropostaAuto(aba,a)
			reprocessaFalhas(aba,a)
			enviaEmailRecusaPropostaAuto(aba)
//		}
	}





	/**
	 * @author T_DCDias
	 * @return
	 */
	public static navegarMenu(){
		wainting(5);
		MenuMatriz.producaoSinistro()
		wainting(5);
		MenuMatriz.subMenu("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul", "a","Produção")
		WebUI.waitForPageLoad(30)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoRecusaPropostaAuto(String aba,String a){
		System.err.println()
		try{
			if(validaDados(cellCheck)){
				acessoAmbiente(aba,a)
				WebUI.delay(15);
				navegarMenu()
				WebUI.delay(15);
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					if (data.getValue(cellCheck, i).equals('Processar')) {
						alerta=""
						List<String> registros = new ArrayList<>();
						lista(registros, i)
						alteraJanejaGenerica(1, 15)
						selecionaItemLista('Propostas Bloqueadas','//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td' ,'div' )
						WebUI.delay(5);
						WebUI.maximizeWindow()
						if(bloqueio(registros, aba)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("",j, path, status, 1, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("",j, path, alerta+" "+ExtractDates.dataAtualizada(), 2,  aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 1, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta+" "+ExtractDates.dataAtualizada(), 2,  aba);
							} catch (Exception e){}
						}
					}
				}
			} else {
				System.err.println('Não existem dados para processar')
			}
		} catch (Exception e){
		  throw e;
		}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param aba
	 * @return
	 */
	public static boolean bloqueio( List<String> registros, String aba){
		System.out.println("1")
		boolean enviado
		String msg
		try{
			//Lista Bloqueios
			String listaBloqueios = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(listaBloqueios)
			if(listaDeTelas(listaBloqueios)){
				//Proposta
				preencheXpath('//*[@id="poliza_Policy"]', registros[0],0);
				//Pesquisar
				clickXpath('//*[@id="btnBuscar"]', 1);
				WebUI.delay(5);
				alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
				System.out.println(alerta)
				if(!alertListBlocList(alerta )){
					enviado = true
					status = "Processado"
					selecionaItemListaAtributo("Excluir",'//*[@id="tablaBloqueos_0_15"]/table' , 'img', 'title' )
					WebUI.delay(5);
					fechaAlerta()
					WebUI.delay(10);
					alteraJanejaGenerica(2, 5)
					String resultadoOperacional = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
					System.out.println(resultadoOperacional)
					if(listaDeTelas(resultadoOperacional)){
						alerta = recuperaValor('//*[@id="poliza"]', 'innerText', 5)
						System.out.println("\n0")
					
						try{
						//FECHAR
						//clickXpath('//*[@id="o_2"]', 5);
						fecharJanelaAtual(2);
						System.out.println("\n1")
						alteraJanejaGenerica(1, 5)
						System.out.println("\n2")
						//FECHAR
						clickXpath('//*[@id="lnkTopLink_Close"]', 5);
						System.out.println("\n3")
						fechaAlerta()
						System.out.println("\n4")
						alteraJanejaGenerica(0, 5)
						System.out.println("\n5")
						navegarMenu()
						System.out.println("\n6")
						} catch (Exception e){
						System.out.println(e)
						
						}
					} else {
						enviado = false
						alerta="RESULTADO OPERACIONAL' não apresentada"
						status = "Processar"
					}
				} else {
					enviado = false
					status = "Processado"
					//clickXpath('//*[@id="imgOptMenuBackHome"]', 1);
					fechaAlerta()
					//FECHAR
					clickXpath('//*[@id="lnkTopLink_Close"]', 1);
					fechaAlerta()
					alteraJanejaGenerica(0, 2)
					navegarMenu()
				}
			} else {
				System.out.println()
				enviado = false
				alerta="Tela 'Lista Bloqueios' não apresentada"
				status = "Processar"
				alteraJanejaGenerica(1, 2)
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 2)
				navegarMenu()
			}
		} catch (Exception e) {
			enviado = false
			alerta="Tela 'Lista bloqueios' não apresentada"
			status = "Processar"
		}
		return enviado
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
			fluxoCompletoRecusaPropostaAuto(aba, a)
			reprocessaFalhas(aba, a)
		}
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailRecusaPropostaAuto(String tipoAba){
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Auto\\RecusaPropostaAuto\\ConfiguracoesPropostaAuto.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "RecusaPropostasAuto.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
			}
		} catch (Exception e){
		}

		try{
			WebUI.closeBrowser();
		} catch (Exception e){
		}
	}


}

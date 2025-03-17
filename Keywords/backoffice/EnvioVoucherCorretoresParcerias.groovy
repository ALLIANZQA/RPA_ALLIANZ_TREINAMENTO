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


public class EnvioVoucherCorretoresParcerias extends rpaCommands{


	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/EnvioVoucherCorretoresParcerias/EnvioVoucherCorretoresParceriasData'
	private static String path
	private static String alerta
	private static int cellCheck = 5;
	private static String filepath = "C:\\Users\\Public\\"
	private static String status


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
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaProcesso(String processo){
		String xpath = '//*[@id="tipoReferencia"]'
		String tag ="option";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().contains(processo.toString())){
				listOfElements[i].click()
				break
			}
		}
	}

	public static fluxoEnvioVoucherCorretoresParcerias(String aba, String a){
		initDataFiles()
		fluxoCompletoEnvioVoucherCorretoresParcerias(aba,a)
		reprocessaFalhas(aba, a)
		enviarEmailVoucherAnexoFinalDoProcesso(aba)
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
			MenuMatriz.newFluxoGestao('/html/body/div[1]/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]')
			alteraJanejaGenerica(1, 10)
			WebUI.delay(10);
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoEnvioVoucherCorretoresParcerias(String aba,String a){
		System.out.println()
		if(validaDados(cellCheck)){
			init(aba,a);
			WebUI.delay(17);
			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				if (data.getValue(cellCheck, i).equals('Processar')) {
					List<String> registros = new ArrayList<>();
					lista( registros , i)
					registros[6]="APÓLICE";
					registros[7]="0";
					registros[8]="Voucher Vistoria"
					if(voucher(registros, aba, a)) {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 4, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[9], 5, aba);
						} catch (Exception e){}
					} else {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 4, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[9], 5, aba);
						} catch (Exception e){}
					}
				}
			}
		} else {
			System.err.println('Não existem dados para processar')
		}
	}

	public static boolean voucher( List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado
		String msg
		String alertaFG

		try{
			deletaArquivoGenerico(filepath+"documento.pdf");
		}catch (Exception e){}

		try{
			alteraJanejaGenerica(1, 10)
			WebUI.maximizeWindow()
			//FICHA GESTÃO
			String telaFichaGestao = recuperaValor('/html/body/div/div/app-root/file-management/app-header/div/nx-ibl-header/div/div/div[1]', 'innerText', 1)
			System.out.println(telaFichaGestao )
			if(telaFichaGestao.contains("Ficha de Gestão") || telaFichaGestao.contains("Ficha Gestión") ){
				/**
				 * Nova Ficha Gestão
				 * Recebe : lista, index proposta, index apolice
				 *
				 */
				novaFichaGestaoProcesso(registros,0,7, "P")

				/**
				 * TELA - GESTÃO PROCESSOS
				 */
				//PSeleciona Nota
				WebUI.delay(10);
				System.out.println()
				if(selecionaItemNaTabelaFichaGestao("", registros[8])){
					if( enviarEmailVoucherAnexo(registros[1], registros[2], registros[3], "documento", "EnvioVoucher")  ){
						enviado = true
						registros[9] ="Voucher enviado"
					}else {
						enviado = false
						registros[9] ="Voucher não enviado, falha no envio do e-mail"
					}

					try{
						WebUI.delay(15);
						deletaArquivoGenerico(filepath+"documento.pdf");
					} catch (Exception e){}

					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 2)
					MenuMatriz.newFluxoGestao('/html/body/div[1]/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]')
					alteraJanejaGenerica(1, 10)
					WebUI.delay(10);

				}else {
					enviado = false
					registros[9] = "Voucher não localizada"
				}

				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 2)
				MenuMatriz.newFluxoGestao('/html/body/div[1]/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]')
				alteraJanejaGenerica(1, 10)
				WebUI.delay(10);


				//TELA FICHA GESTÃO - ELSE
			} else {
				enviado = false
				alerta="Tela 'Ficha de Gestão' não apresentada"
				status = "Processar"
				alteraJanejaGenerica(0, 10)
				WebUI.closeBrowser();
				init(aba, a)
			}

		} catch (Exception e) {
			enviado = false
			status = "Processado"
			alerta="Falha ao preencher 'Ficha de Gestão'"
			alteraJanejaGenerica(0, 10)
			WebUI.closeBrowser();
			init(aba, a)
		}
		return enviado

	}

	/**
	 * @author T_DCDias
	 * @return
	 * Metodo de seleção de itens na tela de ficha e gestão
	 */
	public static boolean selecionaItemNaTabelaFichaGestao(String data, String txnota){
		System.out.println()
		boolean d = false
		selecionaListaDeElementos('//*[@id="tablaLista"]/app-list-file/div/div/div/table','tr')
		int size = listOfElements.size()
		System.out.println("SIZE ["+size+"]")
		try{
			for(int i=0; i<size; i++){
				if(identificaDadosEmUmaColunaNaLinha(txnota, retornaLinhaTabelaFichaGestao(i) )){
					System.out.println()
					d = painelDownload(i)
					break
				}
			}
			System.out.println("selecionaItemNaTabelaFichaGestao ["+d+"]")
			return d
		} catch (Exception e){
			System.err.println("Exception - selecionaItemNaTabela")
		}
	}




	/**
	 * @author T_DCDias
	 * @param executados
	 * @return
	 */
	public static boolean enviarEmailVoucherAnexo(String sender, String emaildestinatio, String assunto, String documento, String ramo){
		System.out.println()
		WebUI.delay(30);
		boolean env = false
		try{
			pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/backoffice/EnvioVoucherCorretoresParcerias/ConfiguracoesEnvioVoucherCorretoresParcerias.txt";
			lerConfigurações(pathTxtEmail)
			System.err.println('Sender ['+sender+'] Ramo ['+ramo+"]")
			List<String> anexos = new ArrayList<>();
			anexos[0] = "C:/Users/Public/"+documento+".pdf"
			anexos[1] = documento+".pdf"
			env = sendEmail.CreateEmail.createEmailSendVoucherAnexo(sender, emaildestinatio,pwdsender, host,  porta, ramo, anexos, assunto );
			return env;
		} catch (Exception e){

		}


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
			fluxoCompletoEnvioVoucherCorretoresParcerias(aba,a);
			reprocessaFalhas(aba, a)
		}
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviarEmailVoucherAnexoFinalDoProcesso(String tipoAba){
		System.out.println()
		try{
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\EnvioVoucherCorretoresParcerias\\ConfiguracoesEnvioVoucherCorretoresParceriasFinal.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = "EnvioVoucherCorretoresParcerias.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
		} catch (Exception e){

		}

	}


}

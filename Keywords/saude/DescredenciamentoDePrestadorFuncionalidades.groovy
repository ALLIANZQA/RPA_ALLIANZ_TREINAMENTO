package saude
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
import auxiliares.timer
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject


public class DescredenciamentoDePrestadorFuncionalidades extends BaseCancelamentoFuncionalidades  {



	private static int cellCheck = 21
	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Saude/DescredenciamentoDePrestador/DescredenciamentoDePrestadorData'
	private static String path
	private static String msgAlerta
	private static String status
	private static String referencia
	private static String alerta

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
	public static navegarMenu(){
		WebUI.delay(5)
		MenuMatriz.manutancaoGeral()
		WebUI.delay(5)
		MenuMatriz.subMenu("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul", "a","Queries en Producción_BR")
		WebUI.waitForPageLoad(30)
		WebUI.switchToWindowIndex(1)
	}

	/**
	 *
	 * @param aba
	 * @param a
	 * @return
	 */
	public static fluxoDescredenciamentoDePrestador(String aba, String a){
		initDataFiles()
		fluxoCompletoDescredenciamentoDePrestador(aba,a)
		reprocessaFalhas(aba,a)
		enviaEmailDescredenciamentoDePrestador(aba)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @param a
	 * @return
	 */
	public static init(String aba, String a){
		System.out.println();
		try{
			acessoAmbiente(aba,a)
			WebUI.delay(10);
			MenuMatriz.newFluxoGestao("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]")
			alteraJanejaGenerica(1, 10)
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoDescredenciamentoDePrestador(String aba, String a){
		try{
			if(validaDados(cellCheck)){
				init(aba,a)
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					alerta = ''
					if (data.getValue(cellCheck, i).equals('Processar')) {
						referencia = "";
						alerta="";
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						registros[22] = "6-Correio Electronico";
						registros[23] = "7-Outros";
						registros[24] = "Rescisão Contratual";
						registros[25] = "Prestadores/Oficinas";
						if( descredenciamentoPrestador(registros, aba, a)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 20, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, referencia , 21, aba);
							} catch (Exception e){}
						} else {
							try{
								System.out.println("Alerta ["+alerta+"]")
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 20, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta+" - "+ExtractDates.dataAtualizada(), 21, aba);
							} catch (Exception e){}
						}
					}
				}
			}
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean descredenciamentoPrestador(List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado
		String msg
		String alertaFG
		String telaFichaGestao
		try{
			alteraJanejaGenerica(1, 10)
			WebUI.maximizeWindow()
			//FICHA GESTÃO
			telaFichaGestao = recuperaValor('/html/body/div/div/app-root/file-management/app-header/div/nx-ibl-header/div/div/div[1]', 'innerText', 1)
			System.out.println(telaFichaGestao )
			if(telaFichaGestao.contains("Ficha de Gestão") || telaFichaGestao.contains("Ficha Gestión") ){
				/**
				 * Nova Ficha Gestão
				 * Recebe : lista, index proposta, index apolice
				 *
				 */
				novaFichaGestaoProcesso(registros,0,1, registros[25])
				WebUI.delay(10);
				String alert = recuperaValor('//*[@id="componenteReferencia"]/div/div[2]/div/nx-message/div', 'innerText', 2)
				System.out.println(alert.trim())
				if(!alertListBlocList(alert.trim())){
					//Editor de correspondência
					clickXpath('//*[@id="11"]/span', 1);
					alteraJanejaGenerica(2, 10)
					enviado = descredenciamento(registros,aba)
					System.out.println("descredenciamento ["+enviado+"]")
				} else {
					enviado = false
					alerta= alert+" - "+registros[0]+" "
					status = "Processado"
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 2)
					MenuMatriz.newFluxoGestao('/html/body/div[1]/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]')
					alteraJanejaGenerica(1, 10)
					WebUI.delay(10);

				}

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
	 * @author BE54955
	 * @param registros
	 * @param aba
	 * @return
	 */
	public static boolean descredenciamento(List<String> registros, String aba){
		System.out.println()
		String alert
		boolean enviado
		String telaEditor = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
		System.out.println(telaEditor)
		WebUI.delay(10);
		if(telaEditor.contains("EDITOR CORRESPONDÊNCIA")){
			try{
				//Meio de Envio
				selecionaItemListaContains(registros[22], '//*[@id="cbMedioEnvio"]' , 'option')
				//Tipo Destino
				selecionaItemListaContains(registros[23], '//*[@id="cbTipoDest"]' , 'option')
				//Aceitar
				clickXpath('//*[@id="btMainAcceptar"]', 1);
				//Nome
				limpaCampo('//*[@id="txtNombre"]',0)
				preencheXpath('//*[@id="txtNombre"]', registros[1],0);
				//Endereço
				//selecionaItemListaContains(registros[10], '//*[@id="direcciontype"]' , 'option')
				limpaCampo('//*[@id="direccionaddressPart2"]',0)
				preencheXpath('//*[@id="direccionaddressPart2"]', registros[2],0);
				//cep/bairro - Região / Identificador
				String [] ceps = StringFormatter.formataCep(registros[3])
				limpaCampo('//*[@id="lfCodigo"]',0)
				preencheXpath('//*[@id="lfCodigo"]', ceps[0],0);
				limpaCampo('//*[@id="lfSubCodigo"]',0)
				preencheXpath('//*[@id="lfSubCodigo"]', ceps[1],0);
				//E-mail
				limpaCampo('//*[@id="txtEmail"]',0)
				preencheXpath('//*[@id="txtEmail"]', registros[7],0);
				//Continuar
				clickXpath('//*[@id="btContinuar"]', 1);
				WebUI.delay(15);
				alert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 2)
				System.out.println(alert)
				if(!alertListBlocList(alert)){
					//Modelo/Descrição
					preencheXpath('//*[@id="numModCodi"]', registros[8],0);
					commandTAB('//*[@id="numModCodi"]')
					preencheXpath('//*[@id="txtModeloDesc"]', registros[24],3);
					//Assunto
					limpaCampo('//*[@id="subject"]',0)
					preencheXpath('//*[@id="subject"]', registros[10],0);
					//Documento
					limpaCampo('//*[@id="txtAreaDocumento"]',0)
					System.out.println(registros[19])
					preencheXpath('//*[@id="txtAreaDocumento"]', registros[19] ,0);
					//Enviar modelo como PDF anexo
					clickXpath('//*[@id="chkEnvioPdf"]', 1);
					//Gravar
					clickXpath('//*[@id="btGrabar"]', 1);
					WebUI.delay(5);
					alert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 2)
					System.out.println(alert)
					if(!alertListBlocList(alert)){
						enviado = true
						status = "Processado"
						alteraJanejaGenerica(1, 5)
						referencia = retornaLinhaTabelaFichaGestao(indexPendenciaSelectItem("E-MAIL AUT"))
						System.out.println("Referencia\n"+referencia)
						//fecharJanelaAtual(2)
						fecharJanelaAtual(1)
						alteraJanejaGenerica(0, 0)
						MenuMatriz.newFluxoGestao("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]")
						alteraJanejaGenerica(1, 10)
						WebUI.delay(2);
						System.out.println()
					} else {
						enviado = false
						alerta=alert
						status = "Processado"
						fecharJanelaAtual(2)
						fecharJanelaAtual(1)
						alteraJanejaGenerica(0, 2)
						MenuMatriz.newFluxoGestao('/html/body/div[1]/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]')
						alteraJanejaGenerica(1, 10)
						WebUI.delay(10);
					}

				} else {
					enviado = false
					alerta=alert
					status = "Processado"
					//home
					//clickXpath('//*[@id="o_3"]', 1)
					fechaAlerta()
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 2)
					MenuMatriz.newFluxoGestao('/html/body/div[1]/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]')
					alteraJanejaGenerica(1, 10)
					WebUI.delay(10);
				}

			} catch (Exception e){
				enviado = false
				alerta="Falha ao preencher dados na tela 'EDITOR CORRESPONDÊNCIA'"
				status = "Processado"
				fecharJanelaAtual(2)
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 2)
				MenuMatriz.newFluxoGestao('/html/body/div[1]/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]')
				alteraJanejaGenerica(1, 10)
				WebUI.delay(10);
			}
		} else {
			enviado = false
			alerta="Tela 'EDITOR CORRESPONDÊNCIA' não apresentada"
			status = "Processar"
		}
		return enviado

	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailDescredenciamentoDePrestador(String tipoAba){
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Saude\\DescredenciamentoDePrestador\\ConfiguracoesDescredenciamentoDePrestador.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "DescredenciamentoDePrestador.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination,anexos, tipoAba)
			}
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
			fluxoCompletoDescredenciamentoDePrestador(aba,a)
			reprocessaFalhas(aba, a)
		}
	}


}

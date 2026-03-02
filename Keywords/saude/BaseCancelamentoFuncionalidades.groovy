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


public class BaseCancelamentoFuncionalidades extends rpaCommands{
	
	
	private static int cellCheck = 15
	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Saude/BaseCancelamento/BaseCancelamentoData'
	private static String path
//	private static String diretorio = '\\C:\\Users\\Public\\Evidencias'
//	private static String excelCopiado
//	private static String emissao
	private static String msgAlerta
	private static String status
	private static String referencia

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
	public static fluxoBaseCancelamento(String aba, String a){
		initDataFiles()
		fluxoCompletoComunicacaoBloqueio(aba,a)
		reprocessaFalhas(aba,a)
		enviaEmailBaseCancelamento(aba)
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
			switch (aba)
			{
			case 'BaseCancelamento':
			acessoAmbiente(aba,a)
			WebUI.delay(10);
			MenuMatriz.consultasEstatisticas()
            MenuMatriz.consultasGerais()
			break;
			
			case 'DescredenciamentoDePrestador':
			acessoAmbiente(aba,a)
			WebUI.delay(10);
			MenuMatriz.newFluxoGestao("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]")
			WebUI.delay(10);
			break;
			}
			alteraJanejaGenerica(1, 10)
			WebUI.delay(10);
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoComunicacaoBloqueio(String aba, String a){
		try{
			if(validaDados(cellCheck)){
				init(aba,a)
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					alerta = ''
					if (data.getValue(cellCheck, i).equals('Processar')) {
						referencia = ""
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						
						registros[9] = "6-Correio Electronico";
						registros[10] = "6-Segurado / Propietário";
						registros[11] = "9999";
						registros[12] = "Cancelamento";
						
						if(cancelamento(registros, aba, a)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 14, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, referencia , 15, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 14, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta+" - "+ExtractDates.dataAtualizada(), 15, aba);
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
	public static boolean cancelamento(List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado
		String msg
		String alertaFG
		try{
			alteraJanejaGenerica(1, 10)
			WebUI.maximizeWindow()
		    //Proposta 
			preencheXpath('//*[@id="apolice"]', registros[0],0);
			//Pesquisar 
			clickXpath('//*[@id="o_10"]', 1);
			WebUI.delay(10);
			//DADOS GERAIS
			String telaDadosGerais = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(telaDadosGerais )
			if(telaDadosGerais.contains("DADOS GERAIS")){
			    //Ficha Gestão
				clickXpath('//*[@id="-61"]/div[1]/span', 1);
				alteraJanejaGenerica(2, 10)
				//FICHA GESTÃO
				String telaFichaGestao = recuperaValor('/html/body/div/div/app-root/file-management/app-header/div/nx-ibl-header/div/div/div[1]', 'innerText', 1)
				System.out.println(telaFichaGestao )
				if(telaFichaGestao.contains("Ficha de Gestão") || telaFichaGestao.contains("Ficha Gestión") ){
					 //Editor correspondencia 
					 clickXpath('//*[@id="11"]/span', 1);
					 alteraJanejaGenerica(3, 10)
					 editorCorrespondencia(registros,aba)
					 
			    } else {
				    enviado = false
				    alerta="Tela 'Ficha de Gestão' não apresentada"
				    status = "Processar"
				    alteraJanejaGenerica(0, 10)
				    WebUI.closeBrowser();
				    init(aba, a)
			    }
				
		      }else {
				enviado = false
				alerta="Tela 'Dados Gerais' não apresentada"
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
	public static editorCorrespondencia(List<String> registros, String aba){
		System.out.println()
		boolean enviado
		String telaEditor = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
		System.out.println(telaEditor)
		WebUI.delay(10);
		if(telaEditor.contains("EDITOR CORRESPONDÊNCIA")){
			try{
				//Meio de Envio
				selecionaItemListaContains(registros[9], '//*[@id="cbMedioEnvio"]' , 'option')
				//Tipo Destino
				selecionaItemListaContains(registros[10], '//*[@id="cbTipoDest"]' , 'option')
				//Aceitar
				clickXpath('//*[@id="btMainAcceptar"]', 1);
				
				if(aba.equals('DescredenciamentoDePrestador')){
					//Nome 
					limpaCampo('//*[@id="txtNombre"]',0)
					preencheXpath('//*[@id="txtNombre"]', registros[3],0);
					//Endereço
					selecionaItemListaContains(registros[10], '//*[@id="direcciontype"]' , 'option')
					limpaCampo('//*[@id="direccionaddressPart2"]',0)
					preencheXpath('//*[@id="direccionaddressPart2"]', registros[3],0);
					
					//cep/bairro
					limpaCampo('//*[@id="txtEmail"]',0)
					preencheXpath('//*[@id="txtEmail"]', registros[3],0);
					//Cidade
					limpaCampo('//*[@id="txtEmail"]',0)
					preencheXpath('//*[@id="txtEmail"]', registros[3],0);
					//Estado
					limpaCampo('//*[@id="txtEmail"]',0)
					preencheXpath('//*[@id="txtEmail"]', registros[3],0);
				}
				
				//E-mail
				limpaCampo('//*[@id="txtEmail"]',0)
				preencheXpath('//*[@id="txtEmail"]', registros[3],0);
				
				if(aba.equals('BaseCancelamento')){
				// CC
				limpaCampo('//*[@id="txtEmailCC"]',0)
				preencheXpath('//*[@id="txtEmailCC"]', registros[4],0);
				//E-MAIL Cco
				limpaCampo('//*[@id="txtEmailCCO"]',0)
				preencheXpath('//*[@id="txtEmailCCO"]', registros[5],0);
				}
				
				
				//Continuar
				clickXpath('//*[@id="btContinuar"]', 1);
				WebUI.delay(15);
				String alert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 2)
				System.out.println(alert)
				if(!alertListBlocList(alert)){
					//Modelo/Descrição
					preencheXpath('//*[@id="numModCodi"]', registros[11],0);
					commandTAB('//*[@id="numModCodi"]')
					preencheXpath('//*[@id="txtModeloDesc"]', registros[12],3);
					//Assunto
					limpaCampo('//*[@id="subject"]',0)
					preencheXpath('//*[@id="subject"]', registros[7],0);
					//Documento
					limpaCampo('//*[@id="txtAreaDocumento"]',0)
					preencheXpath('//*[@id="txtAreaDocumento"]', registros[13],0);
					//Gravar
					clickXpath('//*[@id="btGrabar"]', 1);
					enviado = true
					status = "Processado"
					alteraJanejaGenerica(2, 5)
					referencia = retornaLinhaTabelaFichaGestao(indexPendenciaSelectItem("E-MAIL AUT"))
					fecharJanelaAtual(2)
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 0)
					MenuMatriz.consultasEstatisticas()
					MenuMatriz.consultasGerais()
					alteraJanejaGenerica(1, 5)
					WebUI.delay(2);
					System.out.println()
				} else {
					enviado = false
					alerta=alert
					status = "Processado"
					//home
					clickXpath('//*[@id="o_3"]', 1)
					fechaAlerta()
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 2)
					MenuMatriz.newFluxoGestao('/html/body/div[1]/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]')
					WebUI.delay(10);
				}

			} catch (Exception e){
				enviado = false
				alerta="Falha ao preencher dados na tela 'EDITOR CORRESPONDÊNCIA'"
				status = "Processado"
			}
		} else {
			enviado = false
			alerta="Tela 'EDITOR CORRESPONDÊNCIA' não apresentada"
			status = "Processar"
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
			fluxoCompletoComunicacaoBloqueio(aba, a)
			reprocessaFalhas(aba, a)
		}
	}
	
	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailBaseCancelamento(String tipoAba){
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Saude\\BaseCancelamento\\ConfiguracoesBaseCancelamento.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "BaseCancelamento.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination,anexos, tipoAba)
			}
		} catch (Exception e){
		}
	}
	
	
	
	
	
	
	
	
	
	
}

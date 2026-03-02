package contactcenter

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

public class ContactCenterFuncionalidades extends rpaCommands{

	private static TestData data
	private static String testDataPath = 'Data Files/ContactCenter/ContactCenterData'
	private static String path
	private static String alerta
	private static int cellCheck = 12
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
	public static fluxoContactCenter(String aba, String a){
		initDataFiles()
		fluxoCompletoContactCenter(aba,a)
		reprocessaFalhas(aba,a)
		enviaEmailContactCenter(aba)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoContactCenter(aba,String a){
		System.err.println()
		try{
			if(validaDados(cellCheck)){
				acessoAmbiente(aba,a)
				WebUI.delay(15);
				MenuMatriz.newFluxoGestao('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser');
				WebUI.delay(20);

				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					if (data.getValue(cellCheck, i).equals('Processar')) {
						alerta=""
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						registros[34] = "1082"
						if(connect(registros, aba)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("",j, path, status, 11, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("",j, path, alerta, 12,  aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 11, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 12,  aba);
							} catch (Exception e){}
						}
					}
				}
			} else {
				System.err.println('Não existem dados para processar')
			}
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param aba
	 * @return
	 */
	public static boolean connect( List<String> registros, String aba){
		System.out.println()
		boolean enviado
		String msg
		try{
			alteraJanejaGenerica(1, 2)
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
				novaFichaGestaoMediador(registros,0,1)
				WebUI.delay(10);
				alerta = recuperaValor('/html/body/div[1]/div/app-root/file-management/div/div/div/nx-message/div', 'innerHTML', 2)
				System.out.println("["+alerta+"]")
				if(!alertListBlock(alerta )){

					if(!verificaPendenciaSelectItemData("Nome do account",ExtractDates.dataAtual() )){
						//		if(!verificaPendenciaSelectItem("qualquer coisa")){
						//Nota/Agenda - Ficha Gestão
						MenuMatriz.subMenu('/html/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div', 'span','Nota/Agenda')
						alteraJanejaGenerica(2, 2)
						String telaEditor = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
						System.out.println(telaEditor)
						WebUI.delay(15);
						if(telaEditor.contains("EDITOR DE NOTAS/AGENDA")){
							System.out.println()
							//Nota Fixada registros[34] = "1082"
							preencheXpath('//*[@id="numModCodi"]', registros[34],0);
							//ACCOUNT
							preencheXpath('//*[@id="txtModeloDescL"]', "Nome do Account",0);
							//ACEITAR
							clickXpath('//*[@id="btnAceptar"]', 1);
							WebUI.delay(5);
							alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 2)

							if(!alertListBlock(alerta )){
								//TEXTO
								preencheXpath('//*[@id="texto"]', "FILIAL_DS: "+registros[2]+
										"\nNOME CONTATO: "+registros[3]+
										"\nDDD: "+registros[4]+
										"\nCELULAR: "+registros[5]+
										"\nDDD: "+registros[6]+
										"\nTELEFONE FIXO: "+registros[7]+
										"\nE-MAIL: "+registros[8]+
										"\nNOME ASSESSORIA: "+registros[9]+
										"\nNOME CORRETORA: "+registros[10],0);
								//Gravar
								clickXpath('//*[@id="o_11"]', 2);
								//GESTÃO PROCESSOS
								alteraJanejaGenerica(1, 2)
								String telaGestaoConfirmacao = recuperaValor('/html/body/div/div/app-root/file-management/app-header/div/nx-ibl-header/div/div/div[1]', 'innerText', 1)
								System.out.println(telaGestaoConfirmacao)
								if(telaGestaoConfirmacao.contains("Ficha de Gestão") || telaGestaoConfirmacao.contains("Ficha Gestión") ){
									alerta  = retornaLinhaTabelaFichaGestao(indexPendenciaSelectItem("DADOS RELACIONAMENTO "))
									System.out.println("["+alerta+"] ["+alerta.length()+"]")
									enviado = true
									//alerta = selecionaPendencia(registros[7])
									alteraJanejaGenerica(1, 2)
									MenuMatriz.inicioFichaGestao()
									WebUI.delay(2);
									status = "Processado"
								} else {
									alerta="Tela de confirmação não apresentada"
									enviado = false
									status = "Processado"
								}
							}
						} else {
							enviado = false
							alerta="Tela 'EDITOR DE NOTAS/AGENDA' não apresentada"
							status = "Processar"
						}
					} else {
						alerta  = retornaLinhaTabelaFichaGestao(indexPendenciaSelectItem("Nome do account"))
						System.out.println("["+alerta+"] ["+alerta.length()+"]")
						status = "Processado"
						enviado = true
						//alerta = selecionaPendencia(registros[7])
						alteraJanejaGenerica(1, 2)
						MenuMatriz.inicioFichaGestao()
						WebUI.delay(2);
					}
					//TELA FICHA GESTÃO - ELSE


				} else {
					System.out.println("["+alerta+"]")
					status = "Processado"
					enviado = true
					//alerta = selecionaPendencia(registros[7])
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 2)
					MenuMatriz.newFluxoGestao('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser');
					WebUI.delay(2);
				}


			} else {
				enviado = false
				alerta="Tela 'Ficha de Gestão' não apresentada"
				status = "Processar"
				WebUI.closeBrowser();
				iniciaAplicacaoMatriz('IntranetProdução');
				realizaLoginIntraNetProducao(aba);
				WebUI.delay(15);
				MenuMatriz.newFluxoGestao('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[2]/div/div[1]/ul');
				WebUI.delay(20);
			}
		} catch (Exception e) {
			enviado = false
			alerta="Tela 'Ficha de Gestão' não apresentada"
			status = "Processar"
			alteraJanejaGenerica(0, 2)
			iniciaAplicacaoMatriz('IntranetProdução');
			realizaLoginIntraNetProducao(aba);
			WebUI.delay(15);
			MenuMatriz.newFluxoGestao('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser');
			WebUI.delay(15);
		}
		return enviado
	}


	/*
	 * 
	 */
	public static novaFichaGestaoMediador(List<String> registros, int p, int a){
		System.out.println()

		//ABRIR SELEÇÃO
		String xpath ='//*[@id="componenteReferencia"]/div'
		String tag="nx-icon"
		try{
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				System.out.println("["+i+"] ["+listOfElements[i].getTagName() +"]" )
				if(listOfElements[i].getTagName().contains(tag)){
					listOfElements[i].click()
					//		break
				}
			}
		} catch (Exception e){}

		System.out.println()
		//SELECIONAR
		MenuMatriz.subMenu('//*[@id="cdk-overlay-0"]/div/div[2]', "div","MEDIADOR")
		//Proposta
		preencheXpath('//*[@id="nx-input-0"]', registros[p],0);
		//Aplica
		//preencheXpath('//*[@id="nx-input-2"]', registros[a],0);
		//Pesquisar
		clickXpath('//*[@id="componenteReferencia"]/div/div[1]/div[11]/nx-icon', 1);

	}






	/**
	 * @author be54955
	 * @param nota
	 * @return
	 */
	public static String selecionaPendencia(String nota){
		//	WebUI.delay(1);
		System.err.println()
		String xpath = '//*[@id="tablaLista"]/app-list-file/div/div/div/table'
		String tag = 'tr'
		String linha
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			//System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains(nota.toString())){
				System.err.println("FOUND NOTA ["+i+"] ["+listOfElements[i].getText()+"]")
				String valor = listOfElements[i].getText().toString()
				linha = valor.replace('/(\r\n|\n\r)/gm', " ")
				break
			}
		}
		return linha
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
			fluxoCompletoContactCenter(aba, a)
			reprocessaFalhas(aba, a)
		}
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailContactCenter(String tipoAba){
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\ContactCenter\\ConfiguracoesContactCenter.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "ContactCenter.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos, tipoAba)
			}
		} catch (Exception e){
		}
	}

	/**
	 * @author be54955
	 * @param alert
	 * @return
	 */
	public static boolean alertListBlock(String alert){
		System.err.println("\nalertListBloc ["+alert+"]\n")
		boolean r = false
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add(" Código de UT inexistente")
		listOfAlerts.add(" Código de nota inválido para esta referência")
		listOfAlerts.add(" El usuario no tiene permisos sobre la referencia facilitada ")

		System.out.println("Alert ["+alert+"]\n")
		for(int i = 0; i < listOfAlerts.size(); i++){
			System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
			if(listOfAlerts[i].contains(alert.toString())){
				System.err.println("FOUND ALERT TO BLOC ["+i+"] ["+listOfAlerts[i]+"]")
				r = true
				break
			}
		}
		System.out.println("\nAlert ["+r+"]")
		return  r
	}











}

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
import internal.GlobalVariable
import rpacommands.rpaCommands
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.WriteExcelFactory;
import auxiliares.switchLeitorExcel
import backoffice.backofficeFuncionalidades
import auxiliares.switchAtualizarExcel
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import auxiliares.ExtractDates
import auxiliares.MenuMatriz
import auto.RenovaSantanderFuncionalidades

import templateMessage.ReposicaoValoresMessage




public class ReposicaoValoresFuncionalidades extends rpaCommands{

	private static TestData data
	private static String testDataPath = 'Data Files/Saude/ReposicaoValores/ReposicaoValoresData'
	private static String path
	private static String alerta
	private static int cellCheck = 31
	private static int cellCheckEmissao = 23
	private static int cellCheckEmail = 24
	private static String dellFilePath = 'C:\\Users\\Public\\fichero.PDF'
	private static String apolice
	private static boolean selecionado
	private static boolean emitir
	private static String statusEmissao



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
	 * @param aba
	 * @return
	 */
	public static fluxoReposicaoValoresFuncionalidades(String aba){
		fluxoCompletoReposicaoValoresFuncionalidades(aba)
		reprocessaFalhas(aba)
		enviarEmailReposicaoValores(aba)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoReposicaoValoresFuncionalidades(String aba){
		deleteFileDiretory(dellFilePath)
		//		fluxoEmissao(aba)
		//		fluxoEnvioEmail(aba)
		reposicaoValores(aba)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reposicaoValores(String aba){

		initDataFiles()
		if(validaDados(cellCheck)){


			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				alerta=""
				emitir = false
				statusEmissao = "reprocessa"

				if (data.getValue(cellCheckEmissao, i).equals('Processar')) {
					System.out.println("["+i+"] Emissão")
					acessoAmbiente(aba)
					WebUI.delay(10);
					List<String> registros = new ArrayList<>();
					lista( registros , i)
					emissao(registros, aba)
					if(emitir) {
						try{
							System.err.println(path)
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, statusEmissao, 22, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[25],25, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, apolice, 28, aba);
						} catch (Exception e){}
					} else {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 22, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 23, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 26, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 30, aba);
						} catch (Exception e){}
					}
				} else {
					emitir = true
					statusEmissao = data.getValue(cellCheckEmissao, i)
				}


				//initDataFiles()
				System.out.println("["+i+"] Emitir ["+emitir+"] Emissão ["+statusEmissao+"] Email ["+data.getValue(cellCheckEmail, i)+"]")
				if(emitir && statusEmissao.equals('Processado') && data.getValue(cellCheckEmail, i).equals('Processar')){
					if (data.getValue(cellCheckEmail, i).equals('Processar')) {
						System.out.println("["+i+"] Email")
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						if(registros[27].equals("Falha ao enviar e-mail")){
							acessoAmbiente(aba)
							WebUI.delay(10);
						}
						acessoAmbiente(aba)
						WebUI.delay(10);

						if(email(registros, aba)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 23, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "", 27, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "E-mail corretamente enviado", 29, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 30, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 23, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 27, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 30, aba);
							} catch (Exception e){}
						}
					}
				}
			}
			WebUI.closeBrowser();
		} else {
			System.err.println('Não existem dados para processar')
		}
		WebUI.closeBrowser();

	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoEmissao(String aba){
		initDataFiles()
		if(validaDados(cellCheck)){
			acessoAmbiente(aba)
			WebUI.delay(10);
			menuEmissao()

			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				alerta=""
				if (data.getValue(cellCheck, i).equals('Processar')) {
					List<String> registros = new ArrayList<>();
					lista( registros , i)
					if(emissao(registros, aba)) {
						try{
							System.err.println(path)
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 22, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, registros[25],25, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, apolice, 28, aba);
						} catch (Exception e){}
					} else {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 22, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 23, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 26, aba);
						} catch (Exception e){}
					}
				}
			}
		} else {
			System.err.println('Não existem dados para processar')
		}
		WebUI.closeBrowser();
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param aba
	 * @return
	 */
	public static emissao(List<String> registros, String aba){
		System.out.println("[Emissão]")

		//	boolean emissao
		apolice = ""
		try{
			clickXpath('//*[@id="lnkTopLink_Close"]', 5);
			WebUI.delay(5)
			WebUI.acceptAlert()
			alteraJanejaGenerica(0, 5)
		} catch (Exception e){}

		try{
			menuEmissao()
			WebUI.delay(15)
			/**
			 * TELA - MENU PRODUÇÃO
			 */
			System.out.println('menu produção')
			//Tipo de Seleção : Emissão / Outras Operações
			selecionaTipoSelecao("Outras Operações",'//*[@id="datosSeleccionContent"]/table[1]/tbody/tr',"td")
			//Apolice
			preencheXpath('//*[@id="polSusep_SUSEP"]', registros[4], 1);
			commandTAB('//*[@id="polSusep_SUSEP"]')
			//Selecionar
			clickXpath('//*[@id="btnSeleccionar"]', 2);
			WebUI.delay(2)
			String alerta = pegaMSGAlerta()
			System.err.println(alerta)
			if(identificaAlertasPopUp(alerta)){
				WebUI.delay(2)
				//Operativa
				selecionaItemLista("Endossso de recibo adicional",'//*[@id="codOperativa"]',"option")
				//AVANÇAR
				clickXpath('//*[@id="btnSeleccionOperativa"]', 5);
				WebUI.delay(2)
				try{
					WebUI.acceptAlert()
				} catch (Exception e){}
				WebUI.delay(2)
				if(identificaAlertas('//*[@id="dataErrorSection"]/table/tbody/tr','td')){
					/**
					 * D. BÁSICOS
					 */
					System.out.println('dados basicos')
					//Subestipulante
					selecionaItemLista(registros[6],'//*[@id="DatosFlotaBean$centro"]',"option")
					//Data vigência alter.
					limpaCampo('//*[@id="DatosSuplementoBean$fechaEfectoSupl"]',1)
					preencheXpath('//*[@id="DatosSuplementoBean$fechaEfectoSupl"]', registros[8], 1);
					//Data fim vigência alter.
					limpaCampo('//*[@id="DatosSuplementoBean$fechaTermSupl"]',1)
					preencheXpath('//*[@id="DatosSuplementoBean$fechaTermSupl"]', registros[9], 1);
					//AVANÇAR
					clickXpath('//*[@id="btnAceptar"]', 5);
					WebUI.delay(3)
					identificaAlertas('//*[@id="dataErrorSection"]/table', 'td')

					/**
					 * COBERTURAS
					 */
					System.out.println('coberturas')
					//	COBERTURAS
					selecionaCobertura("HOSPITALAR",'//*[@id="blCoberturas"]/table/tbody',"label",ajustaValor(registros[13]))
					//CALCULAR
					clickXpath('//*[@id="btnCalcular"]', 2);
					WebUI.delay(3)
					identificaAlertas('//*[@id="dataErrorSection"]/table', 'td')
					//AVANÇAR
					clickXpath('//*[@id="btnAceptar"]', 2);
					//Tipo Comissionamento - Livre
					selecionaItemLista('Livre','//*[@id="ComisionamientoBean$tipoComisionamiento"]',"option")
					//Agenciamento
					limpaCampo('//*[@id="ComisionamientoBean$porcAgenciamiento"]',1)
					preencheXpath('//*[@id="ComisionamientoBean$porcAgenciamiento"]', ajustaValor(registros[20]), 1);
					//Comissão
					limpaCampo('//*[@id="ComisionamientoBean$porcComision"]',1)
					preencheXpath('//*[@id="ComisionamientoBean$porcComision"]', ajustaValor(registros[19]), 1);
					//Calcular
					clickXpath('//*[@id="btnCalcular"]', 2);
					try{
						clickXpath('//*[@id="btnCalcular"]', 2);
					} catch (Exception e){}

					WebUI.delay(5);
					//EMITIR
					clickXpath('//*[@id="ParrillaBean$btnEmitir"]', 2);
					WebUI.delay(5);

					/**
					 * TELA - CONFIRMAÇÃO DE EMISSÃO
					 */
					System.out.println('confirmação emissão')
					//AVANÇAR
					clickXpath('//*[@id="btnAceptar"]', 2);
					WebUI.delay(10);

					/**
					 * TELA - EMISSÃO REALIZADA
					 */
					System.out.println('emissão realizada')
					//apolice = ajustaApolice(recuperaValor('//*[@id="CabeceraBean$polizaSusepSUSEP"]', 'innerText', 5))
					apolice = recuperaValor('//*[@id="CabeceraBean$polizaSusepSUSEP"]', 'innerText', 5)
					System.err.println("apolice ["+apolice+"]")
					//		WebUI.delay(5);
					if(apolice.contains("atributo nao encont")){
						//AVANÇAR
						clickXpath('//*[@id="btnValida"]', 5);
						WebUI.delay(5);
						emitir = false
					} else {
						//AVANÇAR
						clickXpath('//*[@id="btnValida"]', 5);
						WebUI.delay(5);
						emitir = true
						statusEmissao = "Processado"
					}

				} else {
					//LIMPAR
					clickXpath('//*[@id="btnLimpiar"]', 5);
					emitir = false
				}

			} else{
				emitir = false
				System.err.println("apolice ["+alerta+"]")
				fechaAlerta()
				//HOME
				clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
				WebUI.delay(10);
			}

			registros[25] = ExtractDates.dataAtual()

		} catch (Exception e){
			emitir = false
			//alerta = "Corretor inválido ou com bloqueio"
			//HOME
			clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
			WebUI.delay(10);
		}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static menuEmissao(){
		WebUI.delay(15);
		MenuMatriz.producaoSinistro()
		MenuMatriz.subMenu("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul", "a","Produção")
		WebUI.delay(15);
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static menuEnvioEmail(){
		WebUI.delay(10);
		MenuMatriz.consultasEstatisticas()
		MenuMatriz.subMenu("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul", "a","Consultas Gerais")
		WebUI.delay(15);
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoEnvioEmail(String aba){
		initDataFiles()
		if(validaDados(cellCheckEmail)){
			acessoAmbiente(aba)
			WebUI.delay(10);
			menuEnvioEmail()

			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1

				if (data.getValue(cellCheckEmail, i).equals('Processar')) {
					List<String> registros = new ArrayList<>();
					lista( registros , i)
					if(email(registros, aba)) {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 23, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "", 27, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "E-mail corretamente enviado", 29, aba);
						} catch (Exception e){}
					} else {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 23, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 27, aba);
						} catch (Exception e){}
					}
				}
			}
		} else {
			System.err.println('Não existem dados para processar')
		}

	}

	/**
	 * @author be54955
	 * @param registros
	 * @param aba
	 * @return
	 */
	public static email(List<String> registros, String aba){
		System.out.println()
		System.out.println("[Email]")
		boolean email
		selecionado = false
		try{
			clickXpath('//*[@id="lnkTopLink_Close"]', 5);
			WebUI.acceptAlert()
			alteraJanejaGenerica(0, 5)
		} catch (Exception e){}

		try{

			menuEnvioEmail()
			WebUI.delay(5)
			if("Proposta".equals(recuperaValor('//*[@id="apolice_label"]', 'textContent', 1))){
				//Proposta
				preencheXpath('//*[@id="apolice"]', registros[0], 1);
				//Pesquisar
				clickXpath('//*[@id="o_10"]', 5);
				WebUI.delay(5)
				//Ficha Gestão
				clickXpath('//*[@id="-61"]/div[1]/span', 5);
				WebUI.delay(20)
				alteraJanejaGenerica(2, 5)
				selecionaItemNaTabelaFichaGestao(registros[25], 'Boleto')

				if(!selecionado){
					//Fechar
					clickXpath('//*[@id="lnkTopLink_Close"]', 5);
					fechaAlerta();
					alteraJanejaGenerica(0, 5)
					menuEnvioEmail()
					email = false
					alerta = "Não encontrado boleto para envio do e-mail"
				} else {
					WebUI.delay(10)
					alteraJanejaGenerica(3, 5)
					WebUI.maximizeWindow()
					//E-MAIL
					clickXpath('//*[@id="btCorreo"]', 5);
					//ENDEREÇO
					preencheXpath('//*[@id="txtDireccion"]', registros[10], 1);
					//ASSUNTO
					preencheXpath('//*[@id="txtAsunto"]', registros[18], 1);
					//MENSAGEM
					preencheXpath('//*[@id="areaMensaje"]', ReposicaoValoresMessage.bodyMessage(registros), 1);
					//ENVIAR
					clickXpath('//*[@id="bttEmail"]', 5);
					WebUI.delay(5)
					if(confirmaEnvioEmail()){
						//FECHAR
						clickXpath('//*[@id="o_6"]', 5);
						WebUI.delay(2)
						fechaAlerta();
						alteraJanejaGenerica(1, 5)
						//NOVA CONSULTA
						clickXpath('//*[@id="-809"]/div[1]/span', 5);
						WebUI.delay(10);
						deleteFileDiretory(dellFilePath)
						email = true
					} else {
						email = false
						alerta = "Falha ao enviar e-mail"
						//FECHAR
						clickXpath('//*[@id="o_6"]', 5);
						WebUI.delay(2)
						fechaAlerta();
						alteraJanejaGenerica(1, 5)
						//NOVA CONSULTA
						clickXpath('//*[@id="-809"]/div[1]/span', 5);
						WebUI.delay(10);
					}
				}
			} else {
				email = false
			}
		} catch (Exception e){
			email = false
			alerta = "Falha ao enviar e-mail"
		}
		System.err.println('['+email+']')
		deleteFileDiretory(dellFilePath)
		alerta = "Falha ao enviar e-mail"
		//WebUI.acceptAlert()
		//retornaMenu(alerta, aba)
		return email
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static telaFichaGestao(){
		alteraJanejaGenerica(2, 5)
		selecionaItemLista('Ficha de Gestão','/*/body/div/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div/ul' , 'a')
	}

	/**
	 * @author be54955
	 * @param alerta
	 * @param aba
	 * @return
	 */
	public static retornaMenu(String alerta, String aba){
		if(alerta.equals("Falha ao enviar e-mail")){
			WebUI.closeBrowser();
			acessoAmbiente(aba)
			WebUI.delay(10);
			menuEnvioEmail()
		}
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailReposicaoValores(String tipoAba){
		initDataFiles()
		if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Saude\\ReposicaoValores\\ConfiguracoesReposicaoValores.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = "ReposicaoValores.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoReposicaoValoresFuncionalidades(aba)
			reprocessaFalhas(aba)
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaCobertura(String tipo,String xpath , String tag, String premio ){
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			//			System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains(tipo.toString())){
				int j = i - 2
				String checked = recuperaValor('//*[@id="CoberturasBean$lineasCoberturas['+j+'].selecCobertura"]', 'checked',1)
				System.err.println("["+i+"] ["+j+"] ["+checked+"]")
				if(checked.equals('true') ){
					System.err.println("["+j+"] ["+listOfElements[i].getText()+"]")
					limpaCampo('//*[@id="CoberturasBean$lineasCoberturas['+j+'].capitales[0].primerRec"]',1)
					preencheXpath('//*[@id="CoberturasBean$lineasCoberturas['+j+'].capitales[0].primerRec"]', premio, 1);
				} else{
					System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
					i = i-2
					clickXpath('//*[@id="CoberturasBean$lineasCoberturas['+i+'].selecCobertura"]', 5);
					preencheXpath('//*[@id="CoberturasBean$lineasCoberturas['+i+'].capitales[0].primerRec"]', premio, 1);
					break
				}
			}
		}
		WebUI.delay(2)
	}

	/**
	 * @author T_DCDias
	 * @return
	 * Metodo de seleção de itens na tela de ficha e gestão
	 */
	public static selecionaItemNaTabelaFichaGestao(String data, String txnota){
		try{
			for(int i=1; i<100; i++){
				if(identificaDadosNaLinha(data, txnota, retornaLinhaTabelaFichaGestao(i))){
					painelEnvioDeEmail(i)
					selecionado = true
					break
				}
			}
		} catch (Exception e){
			System.err.println("Exception - selecionaItemNaTabela")
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaItemNaTabela(String data, String txnota){
		try{
			int paginas = identificaNumPaginas()
			boolean paginar = true
			System.err.println('paginas ['+paginas+']')
			WebUI.delay(5)
			for(int p = 0; p < paginas; p++ ){
				System.err.println('pagina ['+p+'] paginar ['+paginar+']')
				if(paginar){
					paginar = encontraItemNaTabela(data, txnota)
				}
				if(paginar){
					try{
						clickXpath('//*[@id="o_7"]', 5);
					} catch (Exception e){}
				}
			}
		} catch (Exception e){
			System.err.println("Exception - selecionaItemNaTabela")
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param data
	 * @param nota
	 * @return
	 */
	public static boolean encontraItemNaTabela(String data, String txnota){
		boolean paginar
		String xpath = '//*[@id="SectionListContent"]'
		String tag ="td";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		int size = listOfElements.size()
		for(int i = 0; i < size; i++){
			String linha =  recuperaValor('//*[@id="row_'+i+'_listDatos"]', 'innerText', 2)
			System.err.print('*')
			if(linha.contains(data.toString()) && linha.contains(txnota)){
				selecionado = true
				System.err.println('\n['+i+'] FOUND ['+ linha+']\n' )
				clickXpath('//*[@id="row_'+i+'_listDatos"]', 5);
				WebUI.delay(15);
				paginar = false
				break
			} else if(linha.contains('atributo nao encontrado'.toString())){
				paginar = true
				selecionado = false
				i = size
				System.err.println('index  ['+i+']' )
			}
			if(i == size){
				paginar = false
				break
			}
		}
		return  paginar
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static int identificaNumPaginas(){
		return  Integer.parseInt(recuperaValor('//*[@id="lbPagina"]', 'innerText', 2).replaceAll('Página 1 de ', ''))
	}

	/**
	 * @author be54955
	 * @param valor
	 * @return
	 */
	public static String ajustaValor(String valor){
		String novoValor
		novoValor = valor.replace(".","")
		novoValor = novoValor.replace(",",".")
		System.err.println("["+novoValor+"]")
		return novoValor
	}

	/*
	 *
	 *
	 */
	public static String ajustaApolice(String numeroApoliceRecuperada){
		String newNumeroApolice
		numeroApoliceRecuperada = numeroApoliceRecuperada.replace('-', '').replace('/', '');
		println numeroApoliceRecuperada
		newNumeroApolice = numeroApoliceRecuperada.substring(0, 19);
		println newNumeroApolice;
		return newNumeroApolice
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean confirmaEnvioEmail(){
		boolean valor = false
		WebUI.delay(2)
		selecionaListaDeElementos('//*[@id="dataErrorSection"]/table','td')
		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains("E-mail corretamente indexado".toString()) || listOfElements[i].getText().contains("E-mail corretamente enviado".toString())){
				valor = true
				break
			}
		}
		return valor
	}

	/**
	 *@author ALZ_ServiceQA
	 * @return
	 */
	public static boolean identificaAlertas(String xpath, String tag){
		System.err.println('[ Check alerts ]')
		boolean msg = true
		try{
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
				if(listOfElements[i].getText().contains(alertListBloc(listOfElements[i].getText()))){
					alerta = listOfElements[i].getText()
					msg = false
					break
				}
			}
		} catch (Exception e){
		}
		System.err.println('-----['+msg+'] Alertas ['+alerta+']-----')
		return msg
	}

	/**
	 *@author ALZ_ServiceQA
	 * @return
	 */
	public static boolean identificaAlertasPopUp(String mensagem){
		System.err.println('[ Check Pop Up Alerts ]')
		boolean msg = true
		try{
			if(mensagem.contains(alertListBloc(mensagem))){
				alerta = mensagem
				msg = false
			}
			return msg
		} catch (Exception e){
		}
		System.err.println('-----['+msg+'] Alertas ['+alerta+']-----')
		return msg
	}




	/**
	 * @author ALZ_ServiceQA
	 * @param alert
	 * @return
	 */
	public static String alertListBloc(String alert){
		Map<String, String> alertList = new HashMap<String, String>()
		alertList.put(" Política se aplica ao estado errado"," Política se aplica ao estado errado")
		alertList.put("Corretor inválido ","Corretor inválido ")
		alertList.put(" Data início endosso maior a data de cancelamento"," Data início endosso maior a data de cancelamento")
		alertList.put(" A primeira parcela deve ser informado"," A primeira parcela deve ser informado")
		alertList.put("A proposta está bloqueada.","A proposta está bloqueada.")
		return  alertList.get(alert).toString();
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param alert
	 * @return
	 */
	public static String alertListContinue(String alert){
		Map<String, String> alertList = new HashMap<String, String>()
		alertList.put("Campo 'Finalidade de Uso' é obrigatório.", "Campo 'Finalidade de Uso' é obrigatório.")
		return  alertList.get(alert).toString();
	}

}

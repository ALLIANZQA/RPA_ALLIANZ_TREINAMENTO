package subscricaoMassificados

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


public class SubscricaoMassificadosFuncionalidades extends rpaCommands{

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
	public static fluxoSubscricaoMassificadosFuncionalidades(String aba){
		fluxoCompletoSubscricaoMassificadosFuncionalidades(aba)
		reprocessaFalhas(aba)
		enviarEmailSubscricaoMassificadosFuncionalidades(aba)
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
			fluxoCompletoSubscricaoMassificadosFuncionalidades(aba)
			reprocessaFalhas(aba)
		}
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailSubscricaoMassificadosFuncionalidades(String tipoAba){
		initDataFiles()
		if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Saude\\ReposicaoValores\\ConfiguracoesReposicaoValores.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = "ReposicaoValores.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos, tipoAba)
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoSubscricaoMassificadosFuncionalidades(String aba){
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
				}
			}
		}
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





}

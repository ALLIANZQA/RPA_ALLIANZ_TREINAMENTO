package marketing


import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.time.Duration
import java.time.Instant
import java.util.List

import org.openqa.selenium.WebElement

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

import auxiliares.MenuMatriz
import internal.GlobalVariable
import rpacommands.rpaCommands

import auxiliares.ExtractDates
import auxiliares.StringFormatter
import auxiliares.FileInput
import sendEmail.emailDinamico;
import auxiliares.timer;
import auxiliares.GetHost;
import auxiliares.CurrencyMonetary;


import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

public class GestaoReclamacaoAutomaticoFuncionalidades extends rpaCommands{
	
	private static TestData data
	private static TestData dataOperacao
	private static String testDataPath = 'Data Files/Marketing/GestaoReclamacaoAutomatico/GestaoReclamacaoAutomaticoDataData'
	private static String path
	private static String alerta
	private static String retorno
	private static int cellCheck = 25;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "GestaoReclamacaoAutomatico.xlsx"
	private static int cellCheckAlert = 26;
	private static boolean enviado = true;
	private static String xpath;
	private static String tela;
	private static boolean erroTela = false;
	private static String msgAlerta
	private static String status

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCotacaoRenovacaoInterna(String aba, String a){
		System.out.println()
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		initDataFiles()
		fluxoCompletoGestaoReclamacao(aba,a)
		reprocessaFalhas(aba,a)
		FileInput.atualizaLog(Instant.now().toString(), pathFile)
		List<String> times = rpaCommands.convertToInstant(pathFile);
		String dataInicio = times[0];
		String horaInicial =  times[1];
		timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
			enviaEmailGestaoReclamacao(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
		} catch (Exception e){
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoGestaoReclamacao(aba,a){
		System.out.println()
		try{
			retornaHashDeColunasExcell(path);
			cellCheck = 1 + cellNames.get("Status");
			cellCheckAlert = 1 + cellNames.get("Mensagem");
			int process = cellNames.get("Status");
			int alet = cellNames.get("Mensagem");
			System.out.println(cellCheck)
			System.out.println(cellCheckAlert)
			if(validaDados(cellCheck)){
				init(aba,a);
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					enviado = true;
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						registros[14]="17 - Sites ou aplicativos privados de registro de reclamação (por ex. Reclame Aqui)";
						registros[15]="Gestão de reclamações";
						if(gestao(registros,aba,a)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, process, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta , alet, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, process, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: "+alerta, alet, aba);
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
	 * @return
	 */
	public static boolean gestao(List<String> registros, String aba, String a){
		System.out.println()
		boolean enviado = true;
		String msgAlert;
		String tela = true;
		String xpath;
		StringBuilder strbldr = new StringBuilder();
		try{
			WebUI.delay(10);
			MenuMatriz.consultasEstatisticas();
			MenuMatriz.subMenuNome("a","Gestor de Operações - Reclamações")
			alteraJanejaGenerica(1, 25)
			tela = recuperaValor('//*[@id="root"]/div[2]/div[3]/span', 'innerText', 1)
			System.out.println("["+tela+"]")
			if(tela.equals("Olá, escolha a opção desejada:")){
				//Nova Reclamação
				selecionaItemLista("Nova reclamação",'//*[@id="root"]/div[2]/div[4]' , "span" )
				WebUI.delay(15);
				tela = recuperaValor('//*[@id="root"]/div[2]/div[3]/div[1]/span', 'innerText', 1)
				System.out.println(tela)
				if(tela.equals("Dados da Reclamação")){
					
					
					
					try{
					//TIPO DE RECLAMAÇÃO
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[1]/div[1]/div[2]/div/input', 0);
					if(verificaExistenciaDeItemLista(registros[15],'//*[@id="workflow-form"]/div[1]/div[1]/div[1]/div[2]/div/div' , "div" ) && enviado.equals(true)){
					selecionaItemLista(registros[15],'//*[@id="workflow-form"]/div[1]/div[1]/div[1]/div[2]/div/div' , "div" )
					} else {
					  strbldr.append("Tipo de reclamação "+registros[15]+" não apresentado ")
					  clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[1]/div[1]/div[2]/div/input', 0);
					  enviado = false;
					} 
					} catch (Exception e){}
					
			
					
					
					try{
					//Referência sinistro / apólice
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[1]/div[2]/div/div/input', 0);
					if(verificaExistenciaDeItemLista(registros[cellNames.get("Referência sinistro / apólice")],'//*[@id="workflow-form"]/div[1]/div[1]/div[2]/div/div/div' , "div") && enviado.equals(true)){
					selecionaItemLista(registros[cellNames.get("Referência sinistro / apólice")],'//*[@id="workflow-form"]/div[1]/div[1]/div[2]/div/div/div' , "div" )
					} else {
					strbldr.append("Canal de entrada "+registros[cellNames.get("Referência sinistro / apólice")]+" não apresentado ");
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/input', 0);
					enviado = false;
					}
					} catch (Exception e){}
					
					
					try{
					//SELECIONA DOCUMENTO 
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[1]/div[5]/div/div/input', 0);
					if(verificaExistenciaDeItemLista(registros[cellNames.get("Referência sinistro / apólice")],'//*[@id="workflow-form"]/div[1]/div[1]/div[5]/div/div/div' , "div") && enviado.equals(true)){
					selecionaItemLista(registros[cellNames.get("Referência sinistro / apólice")],'//*[@id="workflow-form"]/div[1]/div[1]/div[5]/div/div/div' , "div" )
					} else {
					strbldr.append("Canal de entrada "+registros[cellNames.get("Referência sinistro / apólice")]+" não apresentado ");
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[1]/div[5]/div/div/input', 0);
					enviado = false;
					}
					} catch (Exception e){}
					
					try{
					//NUMERO DO DOCUMENTO 
					if( recuperaValor('/html/body/div/div[2]/form/div[1]/div[1]/div[6]/div/div/div/input', "defaultValue", 0).length() == 0 ){
					preencheXpath('/html/body/div/div[2]/form/div[1]/div[1]/div[6]/div/div/div/input', registros[cellNames.get("CPF/CNPJ")], 0)
					}
					} catch (Exception e){}
					
					try{
						//SINISTRO
						preencheXpath('//*[@id="workflow-form"]/div[1]/div[1]/div[9]/div/div/div/input', registros[cellNames.get("Referência")], 0)
					} catch (Exception e){}
						
					
					try{
					//APOLICE
					preencheXpath('/html/body/div/div[2]/form/div[1]/div[1]/div[3]/div/div/div/input', registros[cellNames.get("Referência")], 0)
					commandTAB('/html/body/div/div[2]/form/div[1]/div[1]/div[3]/div/div/div/input');
					} catch (Exception e){}
					
					WebUI.delay(3);
					
					try{
					//TIPO DE RECLAMAÇÃO 
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/input', 0);
					if(verificaExistenciaDeItemLista(registros[cellNames.get("06 Detalhes")],'//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/div' , "div") && enviado.equals(true)){
					selecionaItemLista(registros[cellNames.get("06 Detalhes")],'//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/div' , "div" )
					} else {
					strbldr.append("Canal de entrada "+registros[cellNames.get("06 Detalhes")]+" não apresentado ");
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/input', 0);
					enviado = false;
					}
					} catch (Exception e){}
					 
					 
					 
					
					try{
					//CANAL DE ENTRADA
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/input', 0);
					if(verificaExistenciaDeItemLista(registros[cellNames.get("06 Detalhes")],'//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/div' , "div") && enviado.equals(true)){
					selecionaItemLista(registros[cellNames.get("06 Detalhes")],'//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/div' , "div" )
					} else {
					strbldr.append("Canal de entrada "+registros[cellNames.get("06 Detalhes")]+" não apresentado ");
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/input', 0);
					enviado = false;
					} 
					} catch (Exception e){}
					
					WebUI.delay(5);
					
					try{
						alteraJanejaGenerica(2, 0)
						System.out.println(pegaMSGAlerta())
						fechaAlerta()
						alteraJanejaGenerica(1, 0)
					}catch (Exception e){}
					
					
					alteraJanejaGenerica(3, 5)
			//		alerta = recuperaValor('/html/body/div/div[2]/form/div[1]/div[9]/div[2]/div/div[2]/div[1]/span', 'innerText', 0)
			//		System.out.println("Alerta ["+alerta+"]")
			//		if(alertListBlocList(alerta)){
					try{
					clicarEmElementoDaTela('/html/body/div/div[2]/form/div[1]/div[9]/div[2]/div/div[2]/div[2]/button', 0);
					clicarEmElementoDaTela('/html/body/div/div[2]/form/div[1]/div[9]/div[2]/div/div[2]/div[2]/button', 0);
					}catch (Exception e){}
					alteraJanejaGenerica(2, 0)
			//			fecharJanelaAtual(2)
			//			alteraJanejaGenerica(1, 0)
			//			fecharJanelaAtual(1)
			//			alteraJanejaGenerica(0, 0)
			//			status = "Processado"
			//			enviado = false;
			//		} else {
			//		alteraJanejaGenerica(2, 5)
					
					try{
					//TIPO DE RECLAMAÇÃO
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/input', 0);
					
					verificaSeAbriuCombo(registros[ cellNames.get("06 Detalhes")],'//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/div' , '//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/input', "div");

					if(verificaExistenciaDeItemLista(registros[cellNames.get("06 Detalhes")],'//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/div' , "div") && enviado.equals(true)){
					selecionaItemLista(registros[cellNames.get("06 Detalhes")],'//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/div' , "div" )
					} else {
					strbldr.append("Canal de entrada "+registros[cellNames.get("06 Detalhes")]+" não apresentado ");
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/input', 0);
					enviado = false;
					}
					} catch (Exception e){}
					
					try{
						alteraJanejaGenerica(2, 0)
						System.out.println(pegaMSGAlerta())
						fechaAlerta()
						alteraJanejaGenerica(1, 0)
					}catch (Exception e){}
					
					
					try{
					//Nome 
					if( recuperaValor('/html/body/div/div[2]/form/div[1]/div[3]/div[2]/div/div/div/input', "defaultValue", 0).length() == 0 ){
					preencheXpath('//*[@id="workflow-form"]/div[1]/div[3]/div[2]/div/div/div/input', registros[cellNames.get("Nome do reclamante")], 0)
					}
				    } catch (Exception e){}
					
					try{
					//CPF/CNPJ
					if( recuperaValor('/html/body/div/div[2]/form/div[1]/div[3]/div[3]/div/div/div/input', "defaultValue", 0).length() == 0 ){
					preencheXpath('//*[@id="workflow-form"]/div[1]/div[3]/div[3]/div/div/div/input', registros[cellNames.get("CPF/CNPJ")], 0)
					}
					} catch (Exception e){}
					
					
					
					
					try{
					//TIPO DO RECLAMANTE
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/input', 0);
					verificaSeAbriuCombo(registros[ cellNames.get("06 Detalhes")],'/html/body/div/div[2]/form/div[1]/div[3]/div[1]/div[2]/div/div' , '//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/input', "div");
					if(verificaExistenciaDeItemLista(registros[cellNames.get("06 Detalhes")],'/html/body/div/div[2]/form/div[1]/div[3]/div[1]/div[2]/div/div' , "div") && enviado.equals(true)){
					selecionaItemLista(registros[cellNames.get("06 Detalhes")],'/html/body/div/div[2]/form/div[1]/div[3]/div[1]/div[2]/div/div' , "div" )
					} else {
					strbldr.append("Canal de entrada "+registros[cellNames.get("06 Detalhes")]+" não apresentado ");
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[3]/div[1]/div[2]/div/input', 0);
					enviado = false;
					}
					} catch (Exception e){}
					
					try{
					//Nome Do reclamante
					if( recuperaValor('/html/body/div/div[2]/form/div[1]/div[3]/div[2]/div/div/div/input', "defaultValue", 0).length() == 0 ){
					preencheXpath('//*[@id="workflow-form"]/div[1]/div[3]/div[2]/div/div/div/input', registros[cellNames.get("Nome do reclamante")], 0)
					}
					} catch (Exception e){}
					
					try{
					//CPF/CNPJ DO RECLAMANTE 
					if( recuperaValor('/html/body/div/div[2]/form/div[1]/div[3]/div[3]/div/div/div/input', "defaultValue", 0).length() == 0 ){
					preencheXpath('//*[@id="workflow-form"]/div[1]/div[3]/div[3]/div/div/div/input', registros[cellNames.get("CPF/CNPJ")], 0)
					}
					} catch (Exception e){}
					
					
					try{
					//CEP 
					xpath = '//*[@id="workflow-form"]/div[1]/div[3]/div[4]/div/div/div/input'
					preencheXpath(xpath, registros[cellNames.get("CEP do reclamante")], 0);
					commandTAB(xpath);
					WebUI.delay(2);
					} catch (Exception e){}
					
					WebUI.delay(3);
					
					
					try{
					//DATA ABERTURA
					preencheXpath('//*[@id="workflow-form"]/div[1]/div[4]/div[1]/div[2]/div/div/input', registros[cellNames.get("Data da Reclamação")], 0)
					} catch (Exception e){}
					
					try{
					//CANAL DE ENTRADA
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[4]/div[2]/div/div/input', 0);
					if(verificaExistenciaDeItemLista(registros[14],'//*[@id="workflow-form"]/div[1]/div[4]/div[2]/div/div/div' , "div"  ) && enviado.equals(true)){
					selecionaItemLista(registros[14],'//*[@id="workflow-form"]/div[1]/div[4]/div[2]/div/div/div' , "div" )
					} else {
					strbldr.append("Canal de entrada "+registros[14]+" não apresentado ");
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[4]/div[2]/div/div/input', 0);
					enviado = false;
					}
					} catch (Exception e){}
					
					try{
					//ID RECLAME AQUI 
					preencheXpath('//*[@id="workflow-form"]/div[1]/div[4]/div[4]/div/div/div/input', registros[cellNames.get("Id Origem")], 0)
					} catch (Exception e){}
					
					try{
					//LINHA DE NEGÓCIOS
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[4]/div[6]/div/div/input', 0);
					if(verificaExistenciaDeItemLista(registros[cellNames.get("03 Línea de Negocio")],'//*[@id="workflow-form"]/div[1]/div[4]/div[6]/div/div/div' , "div") && enviado.equals(true)){
					selecionaItemLista(registros[cellNames.get("03 Línea de Negocio")],'//*[@id="workflow-form"]/div[1]/div[4]/div[6]/div/div/div' , "div" )
					} else {
					strbldr.append("Linha de negócios "+registros[cellNames.get("03 Línea de Negocio")]+" não apresentado ");
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[4]/div[6]/div/div/input', 0);
					enviado = false;
					}
					} catch (Exception e){}
					
					try{
					//MOTIIVO
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[4]/div[7]/div/div/input', 0);
					if(verificaExistenciaDeItemLista(registros[cellNames.get("04 Motivo Principal")].replace("Não","não"),'//*[@id="workflow-form"]/div[1]/div[4]/div[7]/div/div/div' , "div" ) && enviado.equals(true) ){
					  selecionaItemLista(registros[cellNames.get("04 Motivo Principal")].replace("Não","não"),'//*[@id="workflow-form"]/div[1]/div[4]/div[7]/div/div/div' , "div" )
					} else {
					  strbldr.append("Motivo "+registros[cellNames.get("04 Motivo Principal")]+" não apresentado ");
					  clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[4]/div[7]/div/div/input', 0);
					  enviado = false;
					}
					} catch (Exception e){}
					
					try{
					//SUBMOTIVO
					clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[4]/div[8]/div/div/input', 0);
					if(verificaExistenciaDeItemLista(registros[cellNames.get("05 Submotivos")],'//*[@id="workflow-form"]/div[1]/div[4]/div[8]/div/div/div' , "div") && enviado.equals(true)){
					  selecionaItemLista(registros[cellNames.get("05 Submotivos")],'//*[@id="workflow-form"]/div[1]/div[4]/div[8]/div/div/div' , "div" )
					} else {
					  strbldr.append("Submotivo "+registros[cellNames.get("05 Submotivos")]+" não apresentado ");
					  clicarEmElementoDaTela('//*[@id="workflow-form"]/div[1]/div[4]/div[8]/div/div/input', 0);
					  enviado = false;
					}
					} catch (Exception e){}
					
					System.out.println()
					if(enviado){
					  //DESCRIÇÃO
					  preencheXpath('//*[@id="workflow-form"]/div[1]/div[4]/div[9]/div/div[1]/textarea', registros[cellNames.get("Texto da Reclamação")], 0)
					  WebUI.delay(3);
					  //Continuar
					  clicarEmElementoDaTela('//*[@id="workflow-form"]/div[2]/button', 0);
					  WebUI.delay(20);
					  System.out.println()
					  if(!identificaCampoObrigatorioVazio()){
					    enviado = true;
					    String txt = recuperaValor('//*[@id="root"]/div[2]/div[3]/div[1]/div[1]/div/span', 'innerText', 1)
					    String id = retornaID();
					    alerta= registros[cellNames.get("Id Origem")] +" - "+ txt +" : "+ id;
					    status = "Processado"
					  } else {
					    enviado = false;
						status = "Processado"
					  }
					
					} else {
					  alerta=strbldr.toString();
					  status = "Processado"
					}
					
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 0)
					System.out.println("["+enviado+"] ["+alerta+"]")
					System.out.println()
					
					
		//			}
					
					
					
				} else {
					enviado = false
					alerta="Tela 'Dados da Reclamação' não apresentada"
					status = "Processar"
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 5)
					init(aba, a)
				}
				
			} else {
				enviado = false
				alerta="Tela 'GESTÃO DE RECLAMAÇÕES' não apresentada"
				status = "Processar"
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 5)
				init(aba, a)
			}
		} catch (Exception e) {
			enviado = false
			status = "Processar"
			alerta="Falha ao preencher 'GESTÃO DE RECLAMAÇÕES'"
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0, 5)
			init(aba, a)
		}
		System.out.println("\nenviado ["+ enviado+"] \nmsgAlerta ["+alerta+"]");
		return enviado;
	}


    /**
     * @author ALZ_SUP54955
     * @return
     */
	public static boolean identificaCampoObrigatorioVazio(){
		System.out.print()
		boolean exec = true;
		boolean execA = true;
		boolean execB = true;
		String txt;
		StringBuilder strbldr = new StringBuilder();
	//	List<String> listaComDuplicados = new ArrayList<String>();
		try{
			selecionaListaDeElementosCSS('//*[@id="workflow-form"]','span')
			for(int i = 0; i < listOfElements.size(); i++){
				 if( alertListBlocList(listOfElements[i].getText()) ){
					 System.out.println("["+i+"] ["+listOfElements[i].getText()+"]\n");
					 strbldr.append(" "+listOfElements[i].getText())
				 }
			}
			
			if(strbldr.length() == 0){
				execA = false;
			} else {
			    execA = true;
			}
			
			System.out.println("["+strbldr+"]")
		    selecionaListaDeElementosCSS('//*[@id="workflow-form"]','div, span')
		    for(int i = 0; i < listOfElements.size(); i++){
			 //   System.out.println("["+i+"] ["+listOfElements[i].getText()+"]\n");
			    txt = listOfElements[i].getText()
			    txt = StringFormatter.buscaListaDeAntecessoresEmTexto(txt, 1, "Campo")
			    System.out.println("["+txt+"]")
			    if(txt.length() == 0){
				    execB = false;
			    } else {
			     // alerta=" Campo obrigatório "+txt+" "+strbldr;
			        execB = true;
			    }
			  break;
		    }
			
			if(execA||execB){
				alerta=" Campo obrigatório "+txt+" "+strbldr;
				exec = true
			} else {
			    exec = false
			}
			
			System.out.print(alerta)
			
		} catch (Exception e){
		  exec = false;
		}
		return exec; 
	}
	
	/**
	 * @author ALZ_SUP54955
	 * @return
	 */
	public static String retornaID(){
		String id;
		try{
		 selecionaListaDeElementos('//*[@id="root"]/div[2]/div[3]','span')
		 for(int i = 0; i < listOfElements.size(); i++){
			 if(i==2){
				 System.out.println("["+listOfElements[i].getText()+"]")
	   	         id = listOfElements[i].getText()
			 }
		  }
		} catch (Exception e){}
		return id;
	}

	/**
	 * @author Alz_sup54955
	 * @param tipoAba
	 * @param timeLapnse
	 * @param dataInicio
	 * @param dataFinal
	 * @param horaInicial
	 * @param horaFinal
	 * @return
	 */
	public static enviaEmailGestaoReclamacao(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
		System.out.println()
		try{
			if(!validaDados(cellCheck)){
				String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Marketing\\GestaoReclamacao\\ConfiguracoesGestaoReclamacaoAutomatico.txt";
				lerConfigurações(pathTxtEmailConfig)
				System.out.println(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				// FORMATA SAIDA DO TEMPO DE EXECUÇÃO
				long segds = timeLapnse.toSeconds();
				String hours = timeLapnse.toHours()+"h "+
						timer.converteMinutos(timeLapnse.toHours(), timeLapnse.toMinutes())+"m "+
						timer.converteSegundos(timeLapnse.toMinutes(),segds)+"s";
				// ENVIO DE E-MAIL
				//DADOS DO DOCUMENTO
				initDataFiles();
				// ENVIA E-MAIL DINAMICO
				emailDinamico.enviaEmailDinamico(sender, emaildestination, tipoAba, path, docName, emailDinamico.listaDeAlertas(data, cellCheckAlert)  , hours, horaInicial, horaFinal,  GetHost.getHostname() , dataInicio, dataFinal, "" );
				FileInput.deletaArquivoPdf(pathFile);
			 }
		} catch (Exception e){}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		path = data.sourceUrl
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
			fluxoCompletoGestaoReclamacao(aba,a)
			reprocessaFalhas(aba,a)
		}
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
	 * @author be54955
	 * @param aba
	 * @param a
	 * @return
	 */
	public static init(String aba, String a){
		try{
			acessoAmbiente(aba,a)
			WebUI.delay(20);
		} catch (Exception e){}
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


	public static String fromataLinhaDeNegocios(String t){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('Auto', 'AUTOMÓVEL');
		urls.put('Linhas Corporativas', 'LINHAS CORPORATIVAS');
		urls.put('Residencial', 'RESIDÊNCIA');
		urls.put('Vida/Acidentes Pessoais', 'VIDA / ACIDENTES PESSOAIS');

		return urls.get(t.toString());
	}
	
	/**
	 * @author ALZ_SUP54955
	 * @param tipo
	 * @param xpath
	 * @param xpathClick
	 * @param tag
	 * @return
	 */
	public static boolean verificaSeAbriuCombo(String tipo, String xpath , String xpathClick, String tag){
		System.out.println()
		try{
		  verificaExistenciaDeItemLista(tipo ,xpath , tag) 
		} catch (Exception e) {
			clicarEmElementoDaTela(xpathClick, 0);
		} 
	}
	
	
	
}

package auto

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.time.Duration
import java.time.Instant
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
import org.openqa.selenium.WebElement
import sendEmail.emailDinamico;
import auxiliares.GetHost;
import auxiliares.CurrencyMonetary;

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject


public class ApolicesHomologacaoFuncionalidades extends rpaCommands{


	private static TestData data
	private static String testDataPath = 'Data Files/Auto/ApolicesHomologacaoData/ApolicesHomologacao'
	private static String path
	private static String alerta
	private static int cellCheck = 81
	private static String status
	private static String OEOrigen = "Allianz BR"
	private static String entornoDestino = ""
	private static String tipoTraspaso = "Poliza"
	
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "ApolicesHomologacao.xlsx"
	private static int cellCheckAlert = 82;
	private static boolean enviado = true;
	private static String xpath;
	private static String tela;
	private static boolean erroTela = false;
	private static String msgAlerta
	private static String xPath
	private static String textoComplementar
	private static String desbloqueio;
	
	

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoApolicesHomologacaoFuncionalidades(String aba, String a, String b){
		System.out.println()
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		entornoDestino = retornoDestino(b)
		initDataFiles()
		fluxoCompletoApolicesHomologacao(aba,a)
		reprocessaFalhas(aba,a)
		//enviaEmailApolicesHomologacao(aba)
		FileInput.atualizaLog(Instant.now().toString(), pathFile)
		List<String> times = rpaCommands.convertToInstant(pathFile);
		String dataInicio = times[0];
		String horaInicial =  times[1];
		timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
			enviaEmailDinamico(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
		} catch (Exception e){
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		path = data.sourceUrl
		System.out.println(data.getRowNumbers())
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
			fluxoCompletoApolicesHomologacao(aba, a)
			reprocessaFalhas(aba, a)
		}
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailApolicesHomologacao(String tipoAba){
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Auto\\ApolicesHomologacao\\ConfiguracoesApolicesHomologacao.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "ApolicesHomologacao.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos, host, porta, tipoAba)
			}
		} catch (Exception e){
		}
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
	public static enviaEmailDinamico(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
		System.out.println()
		try{
			if(!validaDados(cellCheck)){
				String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Auto\\ApolicesHomologacao\\ConfiguracoesApolicesHomologacao.txt"
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
		System.err.println()
		try{
		MenuMatriz.subMenuNome("a","Queries en Producción_BR")
		} catch (Exception e){}
		
		try{
			MenuMatriz.subMenuNome("a","Queries en Producción")
		} catch (Exception e){}
			
		WebUI.waitForPageLoad(30)
		WebUI.switchToWindowIndex(1)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoApolicesHomologacao(String aba,String a){
		System.err.println()
		try{
			if(validaDados(cellCheck)){
				List<List> matriz = new ArrayList<>();
				List<Integer> linha = new ArrayList<>();
				iniciaQueriesEmProducao(aba, a)
				System.err.println()
				fluxoListaCompleta(matriz,linha,aba, a)
				//menuTraspasos()
				fluxoListaIncompleta(matriz,linha, aba, a)
			} else {
				System.err.println('Não existem dados para processar')
			}
		} catch (Exception e){}
	}

	/**
	 * @author BE54955	
	 * @param matriz
	 * @param linha
	 * @param numPeticao
	 * @param msgConfirmacao
	 * @param aba
	 * @param a
	 * @return
	 */
	public static fluxoListaCompleta(List<List> matriz,List<Integer> linha, String aba,String a){
		try{
			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				if (data.getValue(cellCheck, i).equals('Processar')) {
					alerta=""
					List<String> registros = new ArrayList<>();
					lista(registros, i)
					matriz.add(lista(registros, i))
					System.out.println("MATRIZ ["+matriz.size()+"] LINHAS "+linha.size()+"]")
					if(matriz.size()==10){
						System.err.println()
						listaMatrz(matriz,linha,aba,  i,a)
						menuTraspasos()
					}
				}
			}
			matriz.clear()
			linha.clear()
		} catch (Exception e){
			alerta = "Falha ao completar lista para carga "
		}
	}

	/**
	 * @author BE54955
	 * @param matriz
	 * @param linha
	 * @param numPeticao
	 * @param msgConfirmacao
	 * @param aba
	 * @param a
	 * @return
	 */
	public static fluxoListaIncompleta(List<List> matriz,List<Integer> linha, String aba,String a){
		System.err.println()
		try{
			initDataFiles()
			int rows = getRows()
			System.err.println(rows+" - "+data.getRowNumbers()+" - "+matriz.size())
			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1
				if (data.getValue(cellCheck, i).equals('Processar')) {
					System.err.println()
					alerta=""
					List<String> registros = new ArrayList<>();
					lista(registros, i)
					matriz.add(lista(registros, i))
					System.out.println("["+i+"] MATRIZ ["+ matriz.size()+"] DATA ROWS ["+rows+"]")
					if(matriz.size()==rows){
						System.err.println()
						listaMatrz(matriz,linha, aba,i,a)
						break
					}
				}
			}
		} catch (Exception e){
			alerta = "Falha ao completar lista para carga "
		}
	}

	/**
	 * @author BE54955
	 * @return
	 */
	public static int getRows(){
		int rows
		for(int i = 1; i <= data.getRowNumbers(); i++) {
			int j =  i - 1
			if (data.getValue(cellCheck, i).equals('Processar')) {
				rows = rows+1
			}
		}
		return rows
	}

	/**
	 * @author BE54955
	 * @param aba
	 * @param a
	 * @return
	 */
	public static iniciaQueriesEmProducao(String aba, String a){
		System.err.println()
		acessoAmbiente(aba,a)
		WebUI.delay(15);
		navegarMenu()
		WebUI.delay(20);
		//WebUI.maximizeWindow()
		alteraJanejaGenerica(1, 2)
		menuTraspasos()
	}

	/**
	 * @author BE54955
	 * @return
	 */
	public static menuTraspasos(){
		//TRASPASOS
		clickXpath('//*[@id="menuOptTraspasosBD"]', 1);
		//OE Origen
		selecionaItemListaAtributo(OEOrigen,'//*[@id="oeOrigen"]' , 'option', 'title' )
		//Entorno Destino
		selecionaItemListaAtributo(entornoDestino,'//*[@id="entornoDestino"]' , 'option', 'title' )
		//Tipo Traspaso
		selecionaItemListaAtributo(tipoTraspaso,'//*[@id="tipoTraspaso"]' , 'option', 'title' )
	}

	/**
	 * @author BE54955
	 * @param matriz
	 * @param linha
	 * @param numPeticao
	 * @param msgConfirmacao
	 * @param aba
	 * @param i
	 * @return
	 */
	public static listaMatrz(List<List> matriz,List<Integer> linha,String aba, int i, String a){
		System.err.println()
		for (int m = 0; m < matriz.size();m++ ){
			System.err.println("["+m+"] Número Póliza ["+matriz[m][9]+"] Aplica Póliza ["+matriz[m][10]+"] LINHA ["+matriz[m][79]+"]") //MATRIZ
			carga(matriz, aba,m)
			linha.add(matriz[m][79])
		}

		System.err.println()
		//ACEITAR
		clickXpath('//*[@id="o_3"]', 1);
		WebUI.delay(5);
		String msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
		WebUI.delay(5);
		if(alertListContinueList(msg)) {
			String peticao = recuperaValor('//*[@id="numPetition"]', 'value', 1)
			alerta = "Petição: "+peticao+" - "+msg
			//REINICIAR
			clickXpath('//*[@id="o_5"]', 1);
			status = "Processado"
			for(int l = 0; l <linha.size(); l++){
				try{
					auxiliares.WriteExcelFactory.updateSheetLine("",Integer.parseInt(linha[l]), path, status, 80, aba);
					auxiliares.WriteExcelFactory.updateSheetLine("",Integer.parseInt(linha[l]), path,alerta+" - "+ExtractDates.dataAtualizada()+"/"+matriz[l][79], 81,  aba);
				} catch (Exception e){}
			}
		} else {
			alerta = "Queries em Produção não inserida "
			status = "Processado"
			for(int l = 0; l <linha.size(); l++){
				try{
					auxiliares.WriteExcelFactory.updateSheetLine("", Integer.parseInt(linha[l]), path, status, 80, aba);
					auxiliares.WriteExcelFactory.updateSheetLine("",Integer.parseInt(linha[l]), path,"Alerta: "+alerta+" - "+ExtractDates.dataAtualizada()+"/"+matriz[l][79], 81,  aba);
				} catch (Exception e){}
			}
		}

		matriz.clear()
		linha.clear()
	}

	/**
	 * @author be54955
	 * @param registros
	 * @param aba
	 * @return
	 */
	public static boolean carga( List<List> matriz, String aba, int i){
		System.out.println("ITEM ["+i+"] ["+matriz[i][9]+"] ["+matriz[i][9]+"]")
		boolean enviado
		try{
			//Tela - CONSULTAS BD PRODUÇÃO
			String listaBloqueios = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(listaBloqueios)
			if(listaDeTelas(listaBloqueios)){
				preencheXpath('//*[@id="numero'+Integer.toString(i+1)+'"] ', matriz[i][9],0);
				//Aplica Póliza
				preencheXpath('//*[@id="aplica'+Integer.toString(i+1)+'"] ', matriz[i][10],0);
			} else {
				enviado = false
				alerta="Tela 'CONSULTAS BD PRODUÇÃO"
				status = "Processar"
			}
		} catch (Exception e) {
			enviado = false
			alerta="Tela 'CONSULTAS BD PRODUÇÃO"
			status = "Processar"
		}
		return enviado
	}

	/**
	 * @author BE54955
	 * @param r
	 * @return
	 */
	public static String retornoDestino(String r){
		Map<String, String> retorno = new HashMap<String, String>()
		retorno.put('DES', 'Desarrollo');
		retorno.put('INT', 'Integración');
		retorno.put('PRE', 'Pre-Producción');
		return retorno.get(r.toString());
	}
}

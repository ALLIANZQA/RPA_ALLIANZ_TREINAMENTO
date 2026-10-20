package backoffice

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

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
import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import auxiliares.RegrasEmailDestination
import auxiliares.MenuMatriz
import internal.GlobalVariable
import rpacommands.rpaCommands


import auxiliares.timer
import sendEmail.emailDinamico;
import auxiliares.GetHost;
import auxiliares.leitorPdf;
import java.time.Duration
import java.time.Instant
import auxiliares.ExtractDates



public class GeraBlocoBCadastroCorretorFuncionalidades extends rpaCommands{

	private static TestData data
	private static TestData dataRamos
	private static String testDataPath = 'Data Files/Backoffce/GeraBlocoBCadastroCorretorData/GeraBlocoBCadastroCorretor'
	private static String testDataPathRamos = 'Data Files/Backoffce/GeraBlocoBCadastroCorretorData/RamosBlocoB (1)'
	private static String path
	private static String alerta
	private static int cellCheck = 2
	
	private static int cellCheckAlert = 3;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		dataRamos = findTestData(testDataPathRamos)
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
	public static fluxoGeraBlocoBCadastroCorretor(String aba){
		initDataFiles()
		if(validaDados(cellCheck)){
		  if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		  }
		  fluxoCompletoGeraBlocoBCadastroCorretor(aba)
		  reprocessaFalhas(aba)
		  
		  FileInput.atualizaLog(Instant.now().toString(), pathFile)
		  List<String> times = rpaCommands.convertToInstant(pathFile);
		  String dataInicio = times[0];
		  String horaInicial =  times[1];
		  timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
		  String dataFinal = ExtractDates.dataAtual();
		  String horaFinal =  ExtractDates.horaAtualizada();
		  try{
			 enviarEmailGeraBlocoBCadastroCorretor(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
		  } catch (Exception e){
		  }
		  
		  
		  
		} else {
		  System.out.println("Não existem dados para processar ");
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoGeraBlocoBCadastroCorretor(aba){
		if(validaDados(cellCheck)){
			acessoAmbiente(aba)
			WebUI.delay(10);
			MenuMatriz.gestaoMarketing()
			MenuMatriz.subMenuNome("a", "Gestão Corretores")
			WebUI.delay(17);

			for(int i = 1; i <= data.getRowNumbers(); i++) {
				int j =  i - 1

				if (data.getValue(cellCheck, i).equals('Processar')) {
					List<String> registros = new ArrayList<>();
					lista( registros , i)
					if(geraBlocoB(registros, aba)) {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 1, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta, 2, aba);
						} catch (Exception e){}
					} else {
						try{
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 1, aba);
							auxiliares.WriteExcelFactory.updateSheetLine("", j, path,"Alerta: "+alerta, 2, aba);
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
	 * @param emaildestination
	 * @param corretor
	 * @param intermediario
	 * @param cpfcnpj
	 * @param user
	 * @param pwr
	 * @param ramo
	 * @return
	 */
	public static boolean geraBlocoB( List<String> registros, String aba){
		System.out.println()
		boolean enviado
		try{
			alerta=""

			/**
			 * TELA - GESTÃO PROCESSOS
			 */
			alteraJanejaGenerica(1, 5)
	
			/**
			 * GESTÃO DE MEDIADORES
			 */
			//Tipo de seleção
			/**
			 * @author be54955
			 * String tipo,String xpath , String tag, String attrib
			 * Tipo de seleção 
			 */
			selecionaItemListaAtributo("codigo",'//*[@id="SeleccionContent"]/table[1]/tbody/tr' , 'input', "value" )
			//Preenche Codigo
			preencheXpath('//*[@id="codigo"]', registros[0], 5);
			//Pesquisar
			clickXpath('//*[@id="buscar"]', 1);
			//Mediador
			//sucUCMediador("Alterar Código")
			/**
			 * @author be54955
			 * String tipo,String xpath , String tag, String attrib
			 * Mediador 
			 */
			selecionaItemListaAtributo("Alterar Código",'//*[@id="listaCodigos_tbody"]' , 'img', "title" )
			//Info. Geral
			clickXpath('//*[@id="informacionGenerica"]/div[1]', 1);
			//Dados Allianz
			clickXpath('//*[@id="datosAllianz"]/div[1]', 1);
			WebUI.delay(10);
			System.out.println()
			if(!codigoFilial(recuperaValor('//*[@id="sucursalText"]', 'defaultValue', 0).trim())){	
			//Encerra produção
			limpaCampo('//*[@id="fechaCierreProduccion"]',1)
			WebUI.delay(3);
			}
			System.out.println()
			//Aceitar
			selecionaItemLista("Aceitar",'//*[@id="datosAllianzViewForm"]/table' , "div" )
			//Info. Comercial
			clickXpath('//*[@id="informacionComercial"]/div[1]/span', 1);
			//Comissões
			clickXpath('//*[@id="comisiones"]/div[1]/span', 1);
			alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','innerText', 2)
			if(!alertListBlock(alerta)){
				/**
				 * @author be54955
				 * String tipo,String xpath , String tag, String attrib
				 * Bloco
				 */
				selecionaItemListaAtributo("B",'//*[@id="bloque1"]' , 'option', 'value' )
				//Aceitar Bloqueios
				selecionaItemLista("Aceitar Bloqueios",'//*[@id="bloquesComisionesSectionBar"]/table/tbody/tr/td/table' , "div" )
				//Comissões Ramo
				selecionaItemLista("Comissões Ramo",'//*[@id="bloquesComisionesSectionBar"]/table/tbody/tr/td/table' , "div" )
				//Aceitar comissões
				selecionaItemLista("Aceitar comissões",'//*[@id="comisionesRamoSectionBar"]/table/tbody/tr/td/table' , "div" )
				//Info. Geral
				clickXpath('//*[@id="informacionGenerica"]/div[1]', 1);
				//Dados Allianz
				clickXpath('//*[@id="datosAllianz"]/div[1]', 1);
				/**
				 * @author be54955
				 * String tipo,String xpath , String tag, String attrib 
				 * Comissão Antecipada
				 */
				selecionaItemListaAtributo("imgComisionAnticipada",'//*[@id="otrasCondicionesProd"]/table' , 'img', 'id' )
				selecionaAgrupamentoRamos()

				if(ramoSelecionados(dataRamos.getRowNumbers())){
					enviado = true
					alerta ="Ramos ativados"
				} else{
					enviado = false
				}
				//Home
				clickXpath('//*[@id="imgOptMenuBackHome"]', 1);
			} else {
				//Home
				clickXpath('//*[@id="imgOptMenuBackHome"]', 1);
				enviado = false
			}
		} catch (Exception e){
			enviado = false
		}
		return enviado
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static selecionaAgrupamentoRamos(){
		
		for (int i = 1; i <= dataRamos.getRowNumbers(); i++) {
			System.out.println("["+i+"] SELECIONAR RAMO ["+dataRamos.getValue(1, i)+"]")
			if(agrupamentos(dataRamos.getValue(1, i))){

			}
		}
	}

	/**
	 * @author be54955
	 * @param ramo
	 * @return
	 */
	public static boolean agrupamentos(String ramo){
		boolean sel = false
		String xpath ='//*[@id="secAgrupacionesContent"]/table[2]/tbody/tr/td[1]/table'
		String tag = 'input'
		String attrib = 'value'
		try{
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				if(listOfElements[i].getAttribute(attrib).equals(ramo)){
					boolean s =  ramoSelecionado(ramo)
					System.out.println("["+s+"]")
					if(s.equals(false)){
						listOfElements[i].click()
						clickXpath('//*[@id="bAnadir"]', 1);
						sel = true
					}
				}
			}
		}catch (Exception e){}
		return sel
	}

	/**
	 * @author be54955
	 * @param ramo
	 * @return
	 */
	public static boolean  ramoSelecionados(int totalRamos){
		boolean sel = false
		String xpath ='//*[@id="secAgrupacionesContent"]/table[2]/tbody/tr/td[5]/table'
		String tag = 'input'
		String attrib = 'value'
		try{
			List<WebElement> listOfElements= listaDeElementos(xpath, tag)
			System.out.println("RAMOs SELECIONADOs ["+listOfElements.size()+"] RAMOS LISTA ["+totalRamos+"]")
			if(listOfElements.size()==totalRamos){
				sel = true
			}
		}catch (Exception e){
			sel = false
		}
		return sel
	}

	/**
	 * @author be54955
	 * @param ramo
	 * @return
	 */
	public static boolean  ramoSelecionado(String ramo){
		boolean sel = false
		String xpath ='//*[@id="secAgrupacionesContent"]/table[2]/tbody/tr/td[5]/table'
		String tag = 'input'
		String attrib = 'value'
		try{
			List<WebElement> listOfElements= listaDeElementos(xpath, tag)
			for(int i = 0; i < listOfElements.size(); i++){
				if(listOfElements[i].getAttribute(attrib).equals(ramo)){
					sel = true
					System.out.println("RAMO ATIVADO["+i+"] ["+listOfElements[i].getAttribute(attrib)+"] ["+ramo+"] ["+sel+"]")
				}
			}
		}catch (Exception e){
			sel = false
		}
		return sel
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailGeraBlocoBCadastroCorretorOLD(String tipoAba){
		initDataFiles()
		if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\GeraBlocoBCadastroCorretor\\ConfiguracoesGeraBlocoBCadastroCorretor.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = "GeraBlocoBCadastroCorretor.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
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
	public static enviarEmailGeraBlocoBCadastroCorretor(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
		System.out.println()
	 try{
		 if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\GeraBlocoBCadastroCorretor\\ConfiguracoesGeraBlocoBCadastroCorretor.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			String docName = "GeraBlocoBCadastroCorretor.xlsx";
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
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoGeraBlocoBCadastroCorretor(aba)
			reprocessaFalhas(aba)
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
		listOfAlerts.add(" 991306 - O código de mediador não tem comissões.")


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

	/**
	 * @author be54955
	 * @param alert
	 * @return
	 */
	public static boolean alertListContinue(String alert){
		System.err.println("\nalertListContinue ["+alert+"]")
		boolean r = false
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add(" 121: Restrição Técnica - Risco sem Aceitação")
		listOfAlerts.add(" Já existe cotação para esse CNPJ, número - ")
		listOfAlerts.add(" Existe(m) item(ns) com aviso(s)")
		listOfAlerts.add(" 103: Recusa Técnica Segmento Sem aceitação para AZ")
		listOfAlerts.add(" Restrição técnica para o segurado")
		listOfAlerts.add(" 110: Sistema momentaneamente indisponível - Tente mais tarde")
		listOfAlerts.add(" Existe(m) item(ns) com erro(s)")

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

	public static boolean codigoFilial(String codFilial){
		boolean r = false
		List<String> listFiliais = new ArrayList<String>()
		listFiliais.add("168-INT - ATNP 1")
		listFiliais.add("70-INT - ATNP 2")
		listFiliais.add("177-MG/CO - ATNP")
		listFiliais.add("174-N/NE - ATNP")
		listFiliais.add("182-RJ - ATNP")
		listFiliais.add("102-SP - ATNP 2")
		listFiliais.add("305-SP - ATNP 1")
		listFiliais.add("172-SUL - ATNP 1")
		listFiliais.add("107-SUL - ATNP 2")
		listFiliais.add("240 - MG/CO - ATNP 2")
		listFiliais.add("250 - N/NE – ATNP 2")
		listFiliais.add("37 - SP - ATNP 3")
		System.out.println("Filial ["+codFilial+"]\n")
		for(int i = 0; i < listFiliais.size(); i++){
			System.out.println("["+i+"] ["+listFiliais[i]+"]")
			if(listFiliais[i].contains(codFilial.toString())){
				System.err.println("FOUND COD FILIAL ["+i+"] ["+listFiliais[i]+"]")
				r = true
				break
			}
		}
		System.out.println("\nFILIAL ["+r+"]")
		return  r
	}




}

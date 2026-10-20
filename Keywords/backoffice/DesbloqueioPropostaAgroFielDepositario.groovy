package backoffice

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

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

import bean.MassaDados
import internal.GlobalVariable

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import org.openqa.selenium.Keys as Keys
import auxiliares.switchLeitorExcel
import auxiliares.switchAtualizarExcel
import auxiliares.updateExcel
import auxiliares.StringFormatter;
import auxiliares.FileInput;
import auxiliares.MenuMatriz


import auxiliares.ExtractDates
import auxiliares.timer;
import sendEmail.emailDinamico;
import auxiliares.GetHost;
import auxiliares.leitorPdf;


public class DesbloqueioPropostaAgroFielDepositario extends backofficeFuncionalidades{

	private static String aba = "FIEL"
	private static String path
	private static String sResult
	private static String sSusep
	private static String sProc
	private static Boolean bProxReg
	private static int tamanhoListaOriginal
	private static int countEnviado
	private static TestData data
	private static String sAlert
	private static String testDataPath = 'Data Files/Backoffce/FielDepositarioData/FielDepositario'
	
	private static int cellCheckAlert = 14;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName;
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		path = data.sourceUrl
	}
	
	
	/**
	 * @author t_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static desbloqueioPropostaAgroFielDepositario(){
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		fluxoCompletoDesbloqueioPropostaAgroFielDepositario()
		processaFalhas();
		//enviarEmailDesbloqueioPropostaAgroFielDepositario(aba)
		finalizar();
		FileInput.atualizaLog(Instant.now().toString(), pathFile)
		List<String> times = convertToInstant(pathFile);
		String dataInicio = times[0];
		String horaInicial =  times[1];
		timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
		   enviaEmailFielDepositarioDinamico(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
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
	public static enviaEmailFielDepositarioDinamico(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
		System.err.println("[Enviar Email desbloqueio...]")
		try{
		   path1 = 'C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\FielDepositarioData\\EmissaoFielDepositario.xlsx';
		   docName = 'EmissaoFielDepositario.xlsx';
		   pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\DesbloqueioPropostaAgroFielDepositario\\ConfiguracoesDesbloqueioPropostaAgroFielDepositario.txt";
		   lerConfigurações(pathTxtEmail);
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
		   emailDinamico.enviaEmailDinamico(sender, emaildestination, tipoAba, path1, docName, emailDinamico.listaDeAlertas(data, cellCheckAlert)  , hours, horaInicial, horaFinal,  GetHost.getHostname() , dataInicio, dataFinal, "" );
		   FileInput.deletaArquivoPdf(pathFile);
		} catch (Exception e){}
	}

	/**
	 * @author T_DCDias
	 */
	public static fluxoCompletoDesbloqueioPropostaAgroFielDepositario(){
		try{
		   iniciaAplicacaoMatrizCommands('IntranetProdução');
		   realizaLoginIntraNetProducaoCommands("userSenhaBloqueioFiel");
		   WebUI.delay(17);
		   navegarMenu()
		   fluxoDesbloqueioPropostaAgroFielDepositario()
		} catch (Exception e){
		  System.out.println("\n["+e.getMessage()+"]\n")
		}
	}

	/**
	 * @author T_DCDia
	 * @return
	 */
	public static fluxoDesbloqueioPropostaAgroFielDepositario(){
		carregaDados()
		WebUI.delay(10);
		setUpMessage(" ", " "," ")
		bProxReg
		countEnviado = 0;
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			try{
				if(data.getValue(15, i).equals('B'.toString()) || data.getValue(15, i).equals('E'.toString())){
					System.err.println('['+i+'] Process ['+data.getValue(15, i).toString()+"]")
					System.err.println()
					sAlert = ""
					'Pesquisar '
					bProxReg = false
					pesquisar(i)
					//WebUI.delay(10);
					if (mensagemErro()){
						setUpMessage("Alerta: Não foram encontrados dados para a ação selecionada.", " ", "N" )
						bProxReg = true
					}

					sAlert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', "innerText", 5)
					System.err.println("["+sAlert+"]")

					if (sAlert.contains(" A apólice não é bloqueada".toString())  ){
						setUpMessage("Alerta: "+sAlert, " ", "N" )
						bProxReg = true
						errooperaoMaiorQueValorDaCarteiraAtual()
					}

					if (bProxReg == false){
					   'Desbloquear'
					   desbloquear()
					   WebUI.delay(5)
					   //WebUI.switchToWindowIndex(1)
					   String errorMessagePage =  recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'outerText', 5)
					   System.out.println("Erro Page "+errorMessagePage);
					   if( !alertListBlocList(errorMessagePage) ){
						WebUI.switchToWindowIndex(2)
						String errorPage =  recuperaValor('//*[@id="divAppContent"]/table[1]/tbody/tr[2]/td/label', 'outerText', 5)
						System.out.println("Erro Page "+errorPage);
						if( !alertListBlocList(errorPage) ){
						 try{
							if (sAlert.contains(" A apólice não é bloqueada".toString())  ){
								setUpMessage("Alerta: "+sAlert, " ", "N" )
								bProxReg = true
								//FECHAR
								clickXpath('//*[@id="lnkTopLink_Close"]', 5);
								fechaAlerta();
								WebUI.switchToWindowIndex(0)
								navegarMenu()
								WebUI.switchToWindowIndex(1)
							}
						 } catch (Exception e){}

						try{
							WebUI.delay(5)
							if (pegaMsgErroSubvencao()){
								setUpMessage("Alerta: O valor da subvenção federal da operação é maior que o valor da carteira atual.", " ", "N" )
								bProxReg = true
								errooperaoMaiorQueValorDaCarteiraAtual()
							}
						} catch (Exception e){
							System.err.println("catch - operao Maior Que Valor Da Carteira Atual ")
						}

						try{
							WebUI.delay(5)
							if (pegaMsgErroSubvencaoEstadual()){
								setUpMessage("Alerta: O valor de subvenção estadual da operação é maior que o valor da carteira atual.", " ", "N" )
								bProxReg = true
								errooperaoMaiorQueValorDaCarteiraAtual()
							}
						} catch (Exception e){
							System.err.println("catch - operao Maior Que Valor Da Carteira Atual ")
						}

						try{
							if (pegaMsgErroEmissãoGenerico()){
								setUpMessage("Alerta: Erro no processo de emissão", " ", "N" )
								bProxReg = true
								erroEmissão99999()
							}
						} catch (Exception e){
							System.err.println("catch - Erro Emissão ")
						}


						try{
							if(pegaMsgapoliceItensBloqueados()){
								System.err.println()
								
								setUpMessage("Alerta: Não pode ser autorizado, porque apólice tem itens bloqueados ou com erro. Entre por modificação.", " ", "N" )
								bProxReg = true
								erroApoliceItensBloqueados()
							}
						} catch (Exception e){
							System.err.println("catch - itens bloqueados ou com erro ")
						}
						System.err.println('setUpMessage'+ bProxReg)
						if (bProxReg == false){
							try{
								WebUI.switchToWindowIndex(2)
							} catch (Exception e) {}
							WebUI.maximizeWindow()
							'Verificar Resultado'
							if (operaoRealizadaCorreta()) {
								try{
									setUpMessage(WebUI.getText(findTestObject('PageBackoffice/Page_Menu Produo/label_Operao realizada correta')).toString(), WebUI.getText(findTestObject('PageBackoffice/Page_Menu Produo/label_susep')).toString(), "L" )
								} catch (Exception e){
									System.out.println("Liberação não efetuada! Proposta Não Liberada!")
								}
							}
							else {
								setUpMessage("Alerta: Liberação não efetuada!", "Alerte: Proposta Não Liberada!", "E" )
							}
						}
					  } else {
						setUpMessage(errorPage, "Alerta: Liberação não efetuada!" , "N" )
						 fecharJanelaAtual(2)
						fecharJanelaAtual(1)
						WebUI.switchToWindowIndex(0)
						navegarMenu()
						WebUI.switchToWindowIndex(1)
					  }
					  
				   } else {
					setUpMessage(errorMessagePage, "Alerte: Liberação não efetuada!" , "N" )
					fecharJanelaAtual(1)
					WebUI.switchToWindowIndex(0)
					navegarMenu()
					WebUI.switchToWindowIndex(1)
				   }
				}
				System.err.println('ABA ['+aba+"] sResult ["+sResult+"] sSusep ["+sSusep+"] sProc ["+sProc+"] i ["+i+"] path ["+path+"]")
				System.err.println()
				updateExcel.atualizaDesbloqueioPropostaAgroFiel(aba, sResult,sSusep, sProc, i, path )
				System.err.println()

				if (sProc.equals('N')){
					pageMenuProduoLimpar()
				}else {
					//pageMenuProduo()
					fecharJanelaAtual(2);
					fecharJanelaAtual(1);
					alteraJanejaGenerica(0, 5)
					WebUI.delay(5)
					MenuMatriz.subMenuNome("a","Produção")
					WebUI.waitForPageLoad(30)
					WebUI.switchToWindowIndex(1)
					WebUI.waitForPageLoad(30)
					WebUI.click(findTestObject('PageBackoffice/Page_E/div_Propostas Bloqueadas'))
					WebUI.maximizeWindow()
				}
					countEnviado++
				}
			} catch (Exception e){
			  System.out.println("\n["+e.getMessage()+"]\n")
			  WebUI.closeBrowser();
			  iniciaAplicacaoMatrizCommands('IntranetProdução');
			  realizaLoginIntraNetProducaoCommands("userSenhaBloqueioFiel");
			  WebUI.delay(17);
			  navegarMenu()
			}
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 * Finaliza processo após verifica se todas as pendências foram enviadas, caso não e retornado um 'false'
	 */
	//	public static boolean finalizaProcesso(){
	//		boolean processoFinalizado = false;
	//		if(countEnviado == tamanhoListaOriginal){
	//			processoFinalizado = true;
	//		}
	//		return processoFinalizado;
	//	}

	/**
	 * @author ALZ_ServiceQA
	 * @param processoFinalizado
	 * @param tipoAba
	 * @return
	 * Processa as falhas de desbloqueios ao final do processo, caso ocorra mais falha o processo é re-feito até concluir
	 */
	public static processaFalhas(){
		System.err.println("[Re-processa desbloqueio...]")
		carregaDados()
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if(data.getValue(15, i).equals('B'.toString()) || data.getValue(15, i).equals('E'.toString())){
				fluxoCompletoDesbloqueioPropostaAgroFielDepositario()
			}
		}
	}

	/**
	 * @author T_DCDias
	 * @param data
	 * @return
	 */
	public static carregaDados(){
		data = findTestData('Data Files/Backoffce/FielDepositarioData/FielDepositario')
		path = data.sourceUrl
	}

	/**
	 * @author T_DCDias
	 * @param data
	 * @param i
	 * @return
	 */
	public static boolean regraDesbloqueio(TestData data , int i){
		return data.getValue(15, i).equals('B') || data.getValue(15, i).equals('E');
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static String operaoMaiorQueValorDaCarteiraAtual(){
		WebUI.switchToWindowIndex(1)
		return recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5);
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static desbloquear(){
		WebUI.click(findTestObject('PageBackoffice/Page_Menu Produo/img_fil20'))
		WebUI.acceptAlert()
		WebUI.waitForPageLoad(30)
	}

	/**
	 * @author T_DCDias
	 * @param i
	 * @return
	 */
	public static pesquisar(int i){
		WebUI.setText(findTestObject('PageBackoffice/Page_Menu Produo/input_poliza_Policy'), data.getValue(1, i))
		WebUI.setText(findTestObject('PageBackoffice/Page_Menu Produo/input_poliza_Application'), '0')
		WebUI.click(findTestObject('PageBackoffice/Page_Menu Produo/div_Pesquisar'))
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static navegarMenu(){
		System.out.println()
		WebUI.delay(5)
		MenuMatriz.producaoSinistro()
		WebUI.delay(10)
		MenuMatriz.subMenuNome("a","Produção")
		WebUI.waitForPageLoad(30)
		
		
		WebUI.switchToWindowIndex(1)
		WebUI.maximizeWindow()
		WebUI.delay(5)
		WebUI.click(findTestObject('PageBackoffice/Page_E/div_Propostas Bloqueadas'))
		WebUI.waitForPageLoad(30)
		WebUI.switchToWindowTitle('Menu Produção')
		WebUI.maximizeWindow()
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean pegaMsgErroSubvencaoEstadual(){
		boolean containserror = false
		String xpath = '//*[@id="dataErrorSection"]/table'
		String tag = "td";
		listOfElements = listaDeElementos(xpath,tag)
		WebUI.delay(2);
		for(int j = 0; j < listOfElements.size(); j++){
			if(listOfElements[j].getText().contains('O valor de subvenção estadual da operação é maior que o valor da carteira atual.'.toString())){
				System.err.println(j+' - '+listOfElements[j].getText())
				containserror = true
			}
		}
		return containserror
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean pegaMsgErroSubvencao(){
		boolean containserror = false
		String xpath = '//*[@id="dataErrorSection"]/table'
		String tag = "td";
		listOfElements = listaDeElementos(xpath,tag)
		WebUI.delay(2);
		for(int j = 0; j < listOfElements.size(); j++){
			if(listOfElements[j].getText().contains('O valor da subvenção federal da operação é maior que o valor da carteira atual.'.toString())){
				System.err.println(j+' - '+listOfElements[j].getText())
				containserror = true
			}
		}
		return containserror
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean pegaMsgErroEmissão99999(){
		boolean containserror = false
		String xpath = '//*[@id="dataErrorSection"]/table'
		String tag = "td";
		listOfElements = listaDeElementos(xpath,tag)
		WebUI.delay(2);
		for(int j = 0; j < listOfElements.size(); j++){
			if(listOfElements[j].getText().contains('Erro no processo de emissão (99999)'.toString())){
				System.err.println(j+' - '+listOfElements[j].getText())
				containserror = true
			}
		}
		return containserror
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean pegaMsgErroEmissão00010(){
		boolean containserror = false
		String xpath = '//*[@id="dataErrorSection"]/table'
		String tag = "td";
		listOfElements = listaDeElementos(xpath,tag)
		WebUI.delay(2);
		for(int j = 0; j < listOfElements.size(); j++){
			if(listOfElements[j].getText().contains('Erro no processo de emissão (00010)'.toString())){
				System.err.println(j+' - '+listOfElements[j].getText())
				containserror = true
			}
		}
		return containserror
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean pegaMsgErroEmissão00024(){
		boolean containserror = false
		String xpath = '//*[@id="dataErrorSection"]/table'
		String tag = "td";
		listOfElements = listaDeElementos(xpath,tag)
		WebUI.delay(2);
		for(int j = 0; j < listOfElements.size(); j++){
			if(listOfElements[j].getText().contains(' Erro no processo de emissão (00204)'.toString())){
				System.err.println(j+' - '+listOfElements[j].getText())
				containserror = true
			}
		}
		return containserror
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean pegaMsgErroEmissãoGenerico(){
		boolean containserror = false
		String xpath = '//*[@id="dataErrorSection"]/table'
		String tag = "td";
		listOfElements = listaDeElementos(xpath,tag)
		WebUI.delay(2);
		for(int j = 0; j < listOfElements.size(); j++){
			if(listOfElements[j].getText().contains(' Erro no processo de emissão'.toString())){
				System.err.println(j+' - '+listOfElements[j].getText())
				containserror = true
			}
		}
		return containserror
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static errooperaoMaiorQueValorDaCarteiraAtual(){
		WebUI.switchToWindowIndex(1)
		//FECHAR
		clickXpath('//*[@id="lnkTopLink_Close"]', 5);
		fechaAlerta();
		WebUI.switchToWindowIndex(0)
		navegarMenu()
	}


	/**
	 * @author T_DCDias
	 * @return
	 */
	public static erroEmissão99999(){
		System.err.println()
		WebUI.click(findTestObject('PageBackoffice/Page_E/div_Fechar'))
		WebUI.switchToWindowIndex(0)
		navegarMenu()
		System.err.println()
	}

	public static  erroEmissão00010(){
		System.err.println()
		WebUI.click(findTestObject('PageBackoffice/Page_E/div_Fechar'))
		WebUI.switchToWindowIndex(0)
		navegarMenu()
		System.err.println()
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean pegaMsgapoliceItensBloqueados(){
		boolean msg = false
		String msgErro = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
		System.err.println(msgErro)

		
		
		//	if(msgErro.contains("Não pode ser autorizado, porque apólice tem itens bloqueados ou com erro. Entre por modificação.".toString())){
			
		if(msgErro.contains(" Não pode ser autorizado, porque apólice tem itens bloqueados ou com erro. Entre por modificação.".toString())){
			msg = true
		} else if (msgErro.contains("Erro no processo de emissão (00516)".toString())){

			setUpMessage("Alerta: Erro no processo de emissão (00516)", " ", "N" )
			bProxReg = true
			WebUI.click(findTestObject('PageBackoffice/Page_E/div_Fechar'))
			WebUI.switchToWindowIndex(0)
			navegarMenu()

		} else if (msgErro.contains("Nível de autorização insuficiente para desbloqueio".toString())){

			setUpMessage("Alerta: Nível de autorização insuficiente para desbloqueio", " ", "N" )
			bProxReg = true


			clickXpath('//*[@id="lnkTopLink_Close"]', 2)
			WebUI.delay(2)
			WebUI.acceptAlert()
			WebUI.delay(2)



			//		 WebUI.click(findTestObject('PageBackoffice/Page_E/div_Fechar'))   //*[@id="lnkTopLink_Close"]
			WebUI.switchToWindowIndex(0)
			navegarMenu()

		}

		System.err.println('msg ['+msg+']')
		System.err.println()
		return msg
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static erroApoliceItensBloqueados(){
		System.err.println()
		WebUI.closeWindowIndex(1)
		WebUI.switchToWindowIndex(0)
		navegarMenu()
		System.err.println()
	}


	/**
	 * @author T_DCDias
	 * @return
	 */
	public static pageMenuProduo(){
		try{
			WebUI.click(findTestObject('PageBackoffice/Page_Menu Produo/div_Fechar'))
			WebUI.switchToWindowTitle('Menu Produção')
			pageMenuProduoLimpar()
		} catch (Exception e ){

		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static pageMenuProduoLimpar(){
		WebUI.click(findTestObject('PageBackoffice/Page_Menu Produo/div_Limpar'))
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean operaoRealizadaCorreta(){
		try{

			//*[@id="sectionPaginaCompletaCellContent"]
			//label


			boolean operaoRealizada = false
			String xpath = '//*[@id="sectionPaginaCompletaCellContent"]'
			String tag = "label";
			listOfElements = listaDeElementos(xpath,tag)
			WebUI.delay(2);
			for(int j = 0; j < listOfElements.size(); j++){
				if(listOfElements[j].getText().contains('Operação realizada corretamente.'.toString())){
					System.err.println("["+j+']  ['+listOfElements[j].getText()+"]")
					operaoRealizada = true
				}
			}
			return operaoRealizada
			//	    return WebUI.verifyElementPresent(findTestObject('PageBackoffice/Page_Menu Produo/label_Operao realizada correta'), 5, FailureHandling.CONTINUE_ON_FAILURE);
		} catch (Exception e){
			System.out.println("Operação não realizada corretamente")
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean mensagemErro(){
		return WebUI.waitForElementPresent(findTestObject('PageBackoffice/Page_Menu Produo/td_No foram encontrados dados'), 5)
	}

	/**
	 * @author T_DCDias
	 * @param setsResult
	 * @param setsSusep
	 * @param setsProc
	 * @return
	 */
	public static setUpMessage(String setsResult, String setsSusep, String setsProc ){
		sResult = setsResult
		sSusep = setsSusep
		sProc = setsProc

		System.err.println("setUpMessag ["+sResult+"] ["+sSusep+"] ["+sProc+"]")

	}


	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailDesbloqueioPropostaAgroFielDepositario(String tipoAba){
		boolean validaMassa = true
		try {
		  carregaDados()
		  for (int i = 1; i <= data.getRowNumbers(); i++) {
			   if(data.getValue(15, i).equals('B') || data.getValue(15, i).equals('E')){
				  validaMassa = false
				  break
			  }
		  }

		  if(validaMassa){
			 System.err.println("[Enviar Email desbloqueio...]")
			 pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\DesbloqueioPropostaAgroFielDepositario\\ConfiguracoesDesbloqueioPropostaAgroFielDepositario.txt";
			 lerConfigurações(pathTxtEmail);
			 path1 = 'C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\FielDepositarioData\\EmissaoFielDepositario.xlsx';
			 nome1 = 'EmissaoFielDepositario.xlsx';
			 List<String> anexos = new ArrayList<>();
			 anexos[0] = path1;
			 anexos[1] = nome1;
			 String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			 sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba )
		  }
		} catch (Exception e){}
	}



}

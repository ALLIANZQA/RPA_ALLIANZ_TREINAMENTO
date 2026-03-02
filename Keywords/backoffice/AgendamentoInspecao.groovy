package backoffice

import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.SessionId

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import bean.MassaDados
import sendEmail.envioDeEmail;
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import auxiliares.MenuMatriz

public class AgendamentoInspecao extends backofficeFuncionalidades{

	public static String caminhoDiretorioExcel = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\AgendamentoInspecaData\\RoboAgendamento.xlsx";
	public static int countEnviado = 0;
	public static String ExcelCopiado;
	public static String dataRecuerada = getDateHour();
	public static String diretorio;
	public static String diretorioToDelete;
	public static int tamanhoListaOriginal;
	public static List<MassaDados> listaExcel;


	/**
	 * @author t_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static fluxoAgendamentoInspecoes(String tipoAba){
		fluxoCompletoAgendamentoInspecoes(tipoAba)
		enviarEmailAgendamento(tipoAba)
		finalizar();
		//finalizaChromeCMD();
		System.err.println('\nFinalizado...\nCasos finalizados  ['+ tamanhoListaOriginal+']\n');
	}

	/**
	 * @author t_dvlopes
	 * @param tipoAba
	 * @return
	 * Fluxo Completo Envio Pendencia Go
	 */
	public static fluxoCompletoAgendamentoInspecoes(String tipoAba){

		diretorio = createDiretory('Agendamento Inspecoes - PROCESSANDO-'+dataRecuerada);
		diretorioToDelete = diretorio;
		ExcelCopiado = copiarExcel(caminhoDiretorioExcel, diretorio+"\\Agendamento Inspeções Processando.xlsx");
		listaExcel = switchLeitorExcel.switchExcel(ExcelCopiado, tipoAba);

		tamanhoListaOriginal = listaExcel.size();

		if(tamanhoListaOriginal>0){
			/**
			 * Efetua inicialização da aplicação Matriz e faz o fluxo de envio de pendência
			 */
			iniciaAplicacaoMatriz('IntranetProdução');
			realizaLoginIntraNetProducao(tipoAba);
			WebUI.delay(5);
			fichaGestao();
			WebUI.delay(20);
			alteraJanejaGenerica(1, 5)
			WebUI.maximizeWindow()
			fluxoAgendamentoInspecoesCompleto(listaExcel, tipoAba);
			/**
			 * Processa as pendências que apresentaram falhas
			 */
			processaFalhas(finalizaProcesso(), tipoAba);
		}
	}

	/**
	 *  @author t_dvlopes
	 * @param listaExcel
	 */
	public static void fluxoAgendamentoInspecoesCompleto(List<MassaDados> listaExcel, String tipoAba){
		countEnviado = 0;
		String validaErro;
		for (int i = 0; i < listaExcel.size();i++){
			try{

				String fg = recuperaValor('/*/body/div/div/app-root/file-management/app-header/div/nx-ibl-header/div/div/div[1]', 'innerText', 1)
				System.out.println(fg)
				if(fg.contains('Ficha de Gestão') || fg.contains('Ficha Gestión')){
					novaFichaGestaoPorAtributo(listaExcel.get(i).getApolice(), listaExcel.get(i).getAplica())
					MenuMatriz.notaAgendaFichaGestao()
					alteraJanejaGenerica(2, 5)
					WebUI.delay(5);

					String nota = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 1)
					System.out.println(nota)
					if(nota.contains('Editor de Notas/Agenda')){
						fluxoEnviaAgendamentoInspecao(listaExcel.get(i).getRamo());
						validarEnviado();
						switchAtualizarExcel.switchUpdateExcel('Agendamento', ExcelCopiado ,listaExcel.get(i).getApolice(),Integer.parseInt(listaExcel.get(i).getLinha()));
						countEnviado++
						// fecharJanelaAtual(1)
						alteraJanejaGenerica(1, 5)
						MenuMatriz.inicioFichaGestao()

					}else {
						auxiliares.WriteExcelFactory.updateSheetLine(listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()), ExcelCopiado, 'tela Editor de Notas/Agenda não apresentada', 11, 'Agendamento');
					}
				} else {
					auxiliares.WriteExcelFactory.updateSheetLine(listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()), ExcelCopiado, 'tela Ficha Gestão não apresentada', 11, 'Agendamento');
					fecharJanelaAtual(1)
					alteraJanejaGenerica(0, 5)
					fichaGestao();
				}
			}catch (Exception e) {
				alteraJanejaGenerica(0, 5)
				iniciaAplicacaoMatriz('IntranetProdução');
				realizaLoginIntraNetProducao(tipoAba);
				fichaGestao();
			}
		}
	}

	/**
	 * @author T_Dvlopes
	 * @param processoFinalizado
	 * @param tipoAba
	 * @return
	 * Processa as falhas de envio de pendências ao final do processo, caso ocorra mais falha o processo é re-feito até concluir
	 */
	public static processaFalhas(boolean processoFinalizado, String tipoAba){
		System.err.println('processa Falhas - processoFinalizado ['+processoFinalizado+'] tipoAba ['+tipoAba+']')
		if(processoFinalizado==false){
			tamanhoListaOriginal = 0;

			/**
			 * Nova tentativa
			 * 
			 * 
			 */
			listaExcel = switchLeitorExcel.switchExcel('\\C:\\Users\\Public\\Agendamento Inspecoes - PROCESSANDO-'+dataRecuerada+'\\Agendamento Inspeções Processando.xlsx', tipoAba);
			//iniciaAplicacaoMatriz('MatrizProdução');
			iniciaAplicacaoMatriz('IntranetProdução');
			//realizaLoginMatrizProdução(tipoAba);
			realizaLoginIntraNetProducao(tipoAba);
			//fluxoFichaGestao();
			fichaGestao();
			//newFluxoGestao();
			WebUI.delay(10);
			fluxoAgendamentoInspecoesCompleto(listaExcel, tipoAba);

			/**
			 * 'tamanhoListaOriginal' recebe tamanho da nova lista com pendencias com falha
			 */
			tamanhoListaOriginal = listaExcel.size()
			/**
			 * Chamada racursiva para re-processar falhas
			 */
			processaFalhas(finalizaProcesso(), tipoAba);
		}
	}

	/**
	 * @author T_Dvlopes
	 * @return
	 */
	public static boolean finalizaProcesso(){
		System.err.println('finaliza Processo - countEnviado ['+countEnviado+'] tamanhoListaOriginal ['+tamanhoListaOriginal+']')
		boolean processoFinalizado = false;
		if(countEnviado == tamanhoListaOriginal){
			diretorio = createDiretory('Evidencias//RoboAgendamento Processado '+dataRecuerada);
			copiarExcel(ExcelCopiado, diretorio+"\\RoboAgendamento Processado-"+dataRecuerada+".xlsx");
			//copiarExcel(ExcelCopiado, '\\x:\\RoboAgendamento Processado-'+dataRecuerada+'.xlsx');
			deletarArquivosExcel(diretorioToDelete);
			//deleteFileDiretory(caminhoDiretorioExcel);
			processoFinalizado = true;
		}
		return processoFinalizado;
	}

	/**
	 * @author ALZ_ServiceQA
	 */
	public static void fichaGestao(){
		MenuMatriz.consultasEstatisticas()
		MenuMatriz.newFluxoGestao('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[2]/div/div[1]/ul')
	}


	/**
	 * @author T_DCDias
	 * Fecha a instância do Selenium WebDriver e todas os navegadores associados.
	 * @return
	 */
	public static finalizar(){
		WebUI.delay(2);
		try{
			valueCheck = 'Não enviado';
			WebDriver driver = DriverFactory.getWebDriver();
			SessionId session = ((RemoteWebDriver)driver).getSessionId();
			if(session.toString()!=null){
				WebUI.delay(5);
				WebUI.closeBrowser();
				driver.quit();
			}
		} catch (Exception e){
			System.err.println("\n -[Aplicação finalizada]- \n");
		}
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailAgendamento(String tipoAba){
		pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\AgendamentoGestaoOperacional\\ConfiguracoesAgendamentoGestaoOperacional.txt";
		lerConfigurações(pathTxtEmail)
		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
		path1 = '\\C:\\Users\\Public\\Evidencias\\RoboAgendamento Processado '+dataRecuerada+'\\RoboAgendamento Processado-'+dataRecuerada+'.xlsx';
		String nomeArquivo = 'RoboAgendamento Processado-'+dataRecuerada+'.xlsx';


		try{
			listaExcel = switchLeitorExcel.switchExcel(path1, tipoAba);

			System.err.println('size - '+listaExcel.size())

			if(listaExcel.size()==0){
				List<String> anexos = new ArrayList<>();
				anexos[0] = path1;
				anexos[1] = nomeArquivo;
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos, tipoAba )
			}
		} catch (Exception e){
			//enviarEmailAgendamento(tipoAba);
		}
	}
}

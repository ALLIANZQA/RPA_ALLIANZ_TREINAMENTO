package backoffice

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import bean.MassaDados
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
import internal.GlobalVariable
import sendEmail.envioDeEmail
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import auxiliares.MenuMatriz

public class EnvioPendenciaGo extends backofficeFuncionalidades{

	public static String caminhoDiretorioExcel = "C://Users//Public//RPA_ALLIANZ//Data Files//Backoffce//EnvioPendenciaGo//RoboPendenciaGo.xlsx";
	public static int countEnviado = 0;
	public static String ExcelCopiado;
	public static String dataRecuerada = getDateHour();
	public static String diretorio;
	public static String diretorioToDelete;
	public static int tamanhoListaOriginal;
	public static List<MassaDados> listaExcel;

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static fluxoEnvioPendenciaGo(String tipoAba){
		fluxoCompletoEnvioPendenciaGo(tipoAba)
		enviarEmailPendenciaGo(tipoAba)
		finalizar();
		System.err.println('\nFinalizado...\n Casos finalizados  ['+ tamanhoListaOriginal+']\n');
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 * Fluxo Completo Envio Pendencia Go
	 */
	public static fluxoCompletoEnvioPendenciaGo(String tipoAba){
		diretorio = createDiretory('Processamento [GO]');
		diretorioToDelete = diretorio;
		ExcelCopiado = copiarExcel(caminhoDiretorioExcel, diretorio+"\\Produção.xlsx");
		listaExcel = switchLeitorExcel.switchExcel(copiarExcel(caminhoDiretorioExcel, diretorio+"\\Produção.xlsx"), tipoAba);
		tamanhoListaOriginal = listaExcel.size();
		println tamanhoListaOriginal;
		if(tamanhoListaOriginal>0){
			/**
			 * Efetua inicialização da aplicação Matriz e faz o fluxo de envio de pendência
			 */			
			iniciaAplicacaoMatriz('IntranetProdução');
			realizaLoginIntraNetProducao(tipoAba);
			WebUI.delay(10)
			newFluxoGestao();
			fluxoPendenciaGo(listaExcel, tipoAba);
			/**
			 * Processa as pendências que apresentaram falhas
			 */
			processaFalhas(finalizaProcesso(), tipoAba);
		}
	}



	/**NAVEGA NOS NOVOS MENUS!!!
	 * Consultas & Estatísticas
	 * Arquivo Digital
	 * Ficha de Gestão
	 *
	 */
	public static void newFluxoGestao(){
		try{
			MenuMatriz.consultasEstatisticas()
			MenuMatriz.subMenu("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[2]/div/div[1]/ul/li[2]", "a","Ficha de Gestão")
		} catch (Exception e){
		}
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param listaExcel
	 * @return
	 * Efetua o fluxo de envio de pendência e retoma caso ocorra alguma falha 
	 */
	public static void fluxoPendenciaGo(List<MassaDados> listaExcel, String tipoAba){
		WebUI.delay(15);
		countEnviado = 0;
		String validaErro;
		for (int i = 0; i < listaExcel.size();i++){
			try{
				validaErro = fluxoNotaAgenda(listaExcel.get(i).getApolice());
				println validaErro;
				if(validaErro.equals('APLICA INEXISTENTE')){
					auxiliares.WriteExcelFactory.updateSheetLine(listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()), ExcelCopiado, 'APLICA INEXISTENTE', 7, 'Bloqueios');
					countEnviado++
					finalizar();
					iniciaAplicacaoMatriz('IntranetProdução');
					realizaLoginIntraNetProducao(tipoAba);
					newFluxoGestao();
				}else {
					System.err.println("Ramo ["+listaExcel.get(i).getRamo()+"] Corretor ["+listaExcel.get(i).getCodigoCorretor()+"] Codigo ["+listaExcel.get(i).getBloqueio()+"] Nota ["+listaExcel.get(i).getNota()+"]")
					trocaJanela('Editor de Notas/Agenda')
					//Nota
					preencheXpath('//*[@id="numModCodi"]', listaExcel.get(i).getNota(),2);
					switchAtualizarExcel.switchUpdateExcel('pendenciaGO', ExcelCopiado ,listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()));
					//Clica ACEITAR
					clickXpath('//*[@id="btnAceptar"]', 1);
					//Clica gravar
					clickXpath('//*[@id="o_11"]', 2);
					selecionarApolice = false;
					validarEnviado();
					countEnviado++
				}
			} catch (Exception e) {
				iniciaAplicacaoMatriz('IntranetProdução');
				realizaLoginIntraNetProducao(tipoAba);
				newFluxoGestao();
			}
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 * Finaliza processo após verifica se todas as pendências foram enviadas, caso não e retornado um 'false'
	 */
	public static boolean finalizaProcesso(){
		System.err.println('finaliza Processo - countEnviado ['+countEnviado+'] tamanhoListaOriginal ['+tamanhoListaOriginal+']');
		boolean processoFinalizado = false;
		if(countEnviado == tamanhoListaOriginal){
			diretorio = createDiretory('Evidencias//Processado GO - '+dataRecuerada);
			copiarExcel(ExcelCopiado, diretorio+"//Produção GO Processado-"+dataRecuerada+".xlsx");
			try{
				copiarExcel(ExcelCopiado, '\\x:\\Produção GO Processado-'+dataRecuerada+'.xlsx');
			} catch (Exception e){
				System.err.println("Permissão bloqueada!")
			}
			deletarArquivosExcel(diretorioToDelete);
			deleteFileDiretory(caminhoDiretorioExcel);
			processoFinalizado = true;
		}
		return processoFinalizado;
	}

	/**
	 * @author ALZ_ServiceQA
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
			 */			
			listaExcel = switchLeitorExcel.switchExcel('C:\\Users\\Public\\Processamento [GO]\\Produção.xlsx', tipoAba);

			iniciaAplicacaoMatriz('IntranetProdução');
			realizaLoginIntraNetProducao(tipoAba);
			newFluxoGestao();
			fluxoPendenciaGo(listaExcel, tipoAba);

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
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailPendenciaGo(String tipoAba){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/PendenciaGo/ConfiguracoesPendenciaGo.txt";
		lerConfigurações(pathTxtEmail);

		path1 = '\\C:\\Users\\Public\\Evidencias\\Processado GO - '+dataRecuerada+'\\Produção GO Processado-'+dataRecuerada+'.xlsx';
		nome1 = 'Produção GO Processado-'+dataRecuerada+'.xlsx';

		//				path1 = 'C:\\Users\\Public\\Evidencias\\Processado GO - 31-08-2020-18-39-02\\Produção GO Processado-31-08-2020-18-39-02.xlsx';
		//				nome1 = 'Produção GO Processado-31-08-2020-18-39-02.xlsx';

		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));

		try{
			listaExcel = switchLeitorExcel.switchExcel(path1, tipoAba);
			if(listaExcel.size()==0){
				List<String> anexos = new ArrayList<>();
				anexos[0] = path1;
				anexos[1] = nome1;
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos, tipoAba )
			}
		} catch (Exception e){
			enviarEmailPendenciaGo(tipoAba);
		}
	}
}

package backoffice

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

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
import auxiliares.switchLeitorExcel
import auxiliares.switchAtualizarExcel
import auxiliares.StringFormatter
import auxiliares.FileInput
import auxiliares.updateExcel

import bean.MassaDados
import internal.GlobalVariable

public class SeguroFuncionarioRenovResidencia extends backofficeFuncionalidades{

	public static String caminhoDiretorioExcel = "C://Users//Public//RPA_ALLIANZ//Data Files//Backoffce//SeguroFuncionarioRenovData//RoboRenovResidencia.xlsx";
	//public static String caminhoDiretorioExcel = "Y:\\Ntxnas1\\brasil\\DIRETORIAS\\DIR EXE OPERACOES\\DIR TI\\SUPERINT DESENV PRODUTOS\\RPA\\RPA\\Backoffce\\RoboRenovResidencia.xlsx";
	public static int countEnviado = 0;
	public static String ExcelCopiado;
	public static String dataRecuerada = getDateHour();
	public static String diretorio;
	public static String diretorioToDelete;
	public static int tamanhoListaOriginal;
	public static List<MassaDados> listaExcel;
	public static premioAtual;
	public static premioAnterior;
	private static msgErro
	private static valueCheckSendEmail

	public static fluxoSeguroFuncionario(String ramo, String tipoAba, String... dataParaExecutar){
		fluxoCompletoSeguroFuncionarios(ramo, tipoAba, dataParaExecutar)
		finalizar();
		System.err.println('\nFinalizado...\n Casos finalizados  ['+ tamanhoListaOriginal+']\n');
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 * Fluxo Completo Envio Pendencia Go
	 */
	public static fluxoCompletoSeguroFuncionarios(String ramo, String tipoAba, String... dataParaExecutar){
		diretorio = createDiretory('SeguroFuncionario !RESIDENCIA!');
		diretorioToDelete = diretorio;
		ExcelCopiado = copiarExcel(caminhoDiretorioExcel, diretorio+"\\RoboRenovResidenciaProcessamento.xlsx");
		listaExcel = switchLeitorExcel.switchExcel(ExcelCopiado, tipoAba);
		tamanhoListaOriginal = listaExcel.size();
		println 'tamanhoOriginal '+tamanhoListaOriginal;
		println 'lista '+listaExcel.size();
		if(tamanhoListaOriginal>0){
			/**
			 * Efetua inicialização da aplicação Matriz e faz o fluxo de envio de pendência
			 */
			iniciaAplicacaoMatriz('AllianznetProdução');
			realizaLoginAllianzNet(tipoAba);
			WebUI.delay(15);
			fluxoCompletoPesquisaApolice(verificadataParaExecutar(listaExcel, dataParaExecutar,ramo), tipoAba);
			finalizar();
			/**
			 * Processa as pendências que apresentaram falhas
			 */
			processaFalhas(tipoAba, dataParaExecutar);
			emailFinalRenovResidenciaFinal()
		}else{

			finalizaProcessoMassaZerada();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param listaExcel
	 * @return
	 * Efetua o fluxo de Renovacao apolice
	 */
	public static boolean fluxoCompletoPesquisaApolice(List<MassaDados> listaExcel, String tipoAba){
		WebUI.delay(15);
		boolean p = true
		System.out.println()
		countEnviado = 0;
		String downloadOk
		for (int i = 0; i < listaExcel.size();i++){
			System.err.println(i+" - "+listaExcel.get(i).getRenovarProposta()+" "+listaExcel.size()+" "+listaExcel.get(i).getStatus())
			if(listaExcel.get(i).getRenovarProposta().toLowerCase().trim().equals("sim")   &&  listaExcel.get(i).getStatus().trim().equals("Processar")){
				try{
					String CPF = listaExcel.get(i).getCpf();
					System.err.println("CPF: "+CPF+" "+CPF.length())
					if(CPF.length()==11){
					valueCheckSendEmail = ""
					msgErro = ""
					deletarDocrequest()
					fluxoNavegaTelaRenovacao();
					fluxoPesquisaApolice(listaExcel.get(i).getApoliceSucep());
					downloadOk = recuperaValoresPremio(listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()));
					WebUI.delay(10);
					println downloadOk;
					if(downloadOk.equals('true')){
						WebUI.delay(5);
						enviarPdfSeguroRenovResidencia(tipoAba, listaExcel.get(i).getEmailColab(), listaExcel.get(i).getCpf());
						validarProcessado();
						switchAtualizarExcel.switchUpdateExcel('SeguroRenovResidencia', ExcelCopiado ,listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()));
						System.err.println("valueCheck ["+valueCheckSendEmail+"]")
						if(valueCheckSendEmail.equals('Enviado')){
							updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado ,listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()), 16, premioAtual, 'SeguroRenovResidencia');
							updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 17, premioAnterior, 'SeguroRenovResidencia');
							updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()), 16, premioAtual, 'SeguroRenovResidencia');
							updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 17, premioAnterior, 'SeguroRenovResidencia');
							updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 14, 'Processado', 'SeguroRenovResidencia');
							updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email Enviado Com Sucesso!', 'SeguroRenovResidencia');
							updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel, "" , Integer.parseInt(listaExcel.get(i).getLinha()), 14, 'Processado', 'SeguroRenovResidencia');
							updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email Enviado Com Sucesso!', 'SeguroRenovResidencia');
						} else if(valueCheckSendEmail.equals('Não enviado')){
							updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 14, 'Processado', 'SeguroRenovResidencia');
							updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email não enviado! '+msgErro, 'SeguroRenovResidencia');
							updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 14, 'Processado', 'SeguroRenovResidencia');
							updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email não enviado! '+msgErro, 'SeguroRenovResidencia');
						}
					}else {
						finalizar();
						updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 14, 'Processado', 'SeguroRenovResidencia');
						updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email não enviado! '+msgErro, 'SeguroRenovResidencia');
						
						updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 14, 'Processado', 'SeguroRenovResidencia');
						updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email não enviado! '+msgErro, 'SeguroRenovResidencia');
						fluxoNavegaTelaRenovacao();
					}
					
					} else {
					  enviarAlertaDeFalhaSeguroRenovResidencia(tipoAba,listaExcel.get(i).getCpf());
					  switchAtualizarExcel.switchUpdateExcel('SeguroRenovResidencia', ExcelCopiado ,listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()));
					  updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email não enviado CPF: '+listaExcel.get(i).getCpf()+" inválido", 'SeguroRenovResidencia');
					  switchAtualizarExcel.switchUpdateExcel('SeguroRenovResidencia', caminhoDiretorioExcel ,listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()));
					  updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email não enviado CPF: '+listaExcel.get(i).getCpf()+" inválido", 'SeguroRenovResidencia');
				  }
					countEnviado++
				} catch (Exception e) {
					p = false
					UpdateStatusNaoProcessado();
					iniciaAplicacaoMatriz('AllianznetProdução');
					realizaLoginAllianzNet(tipoAba);
				}
			}else {
				try{
					p = false
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 14, 'Processado', 'SeguroRenovResidencia');
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Não Processado - Não Renovar', 'SeguroRenovResidencia');
					updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel, "" , Integer.parseInt(listaExcel.get(i).getLinha()), 14, 'Processado', 'SeguroRenovResidencia');
					updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Não Processado - Não Renovar', 'SeguroRenovResidencia');
				} catch (Exception e){}
			}
		}
		return p
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param processoFinalizado
	 * @param tipoAba
	 * @return
	 * Processa as falhas de envio de pendências ao final do processo, caso ocorra mais falha o processo é re-feito até concluir
	 */
	public static processaFalhas( String tipoAba, String[] dataParaExecutar){
		/**
		 * Efetua inicialização da aplicação Matriz e faz o fluxo de envio de pendência
		 */
		iniciaAplicacaoMatriz('AllianznetProdução');
		realizaLoginAllianzNet(tipoAba);
		WebUI.delay(15);
		if(!fluxoCompletoPesquisaApolice(verificadataParaExecutar(listaExcel, dataParaExecutar,ramo), tipoAba)){
			finalizar();
			processaFalhas(tipoAba, dataParaExecutar)
		}
	}


	public static String recuperaValoresPremio(String numeroApolice, int linha){
		String downloadOk
		try{
			premioAtual = recuperaValor('//*[@id="idPoliza_0_7"]', 'textContent', 1);
			premioAnterior = recuperaValor('//*[@id="idPoliza_0_8"]', 'outerText', 1);
			System.err.println "Atualiza Premio Atual ["+premioAtual+"] Premio Anterior ["+premioAnterior+"]"
//			updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice, linha, 16, premioAtual, 'SeguroRenovResidencia');
//			updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice , linha, 17, premioAnterior, 'SeguroRenovResidencia');
//			updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice, linha, 16, premioAtual, 'SeguroRenovResidencia');
//			updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice , linha, 17, premioAnterior, 'SeguroRenovResidencia');
			downloadOk = realizaDownloadApoliceSeguroFuncionarioResidencia();
			if(downloadOk.equals('true')){
				downloadOk = 'true'
			}else {
				msgErro = 'Não Processado - [Erro ao realizar cálculo desta Renovação]'+botaoRenovacao;
				updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice , linha, 14, 'Processado', 'SeguroRenovResidencia');
				updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice , linha, 18, msgErro , 'SeguroRenovResidencia');
				updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice , linha, 14, 'Processado', 'SeguroRenovResidencia');
				updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice , linha, 18, msgErro , 'SeguroRenovResidencia');
				downloadOk = 'false'
			}
			return downloadOk;
		} catch (Exception e){
			downloadOk = 'false'
			return downloadOk;
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
			diretorio = createDiretory('Evidencias//Renove_Seguro_Residencia - '+dataRecuerada);
			copiarExcel(ExcelCopiado, diretorio+"//Renove_Seguro_Residencia - "+dataRecuerada+".xlsx");
			//	copiarExcel(ExcelCopiado, '\\x:\\Renove_Seguro_Residencia - '+dataRecuerada+'.xlsx');
			copiarExcel(ExcelCopiado, 'C://Users//Public//RPA_ALLIANZ//MassaAuto//RoboRenovResidencia'+dataRecuerada+'.xlsx');
			deletarArquivosExcel(diretorioToDelete);
			//deleteFileDiretory(caminhoDiretorioExcel);
			processoFinalizado = true;
		}
		return processoFinalizado;
	}

	/**
	 * @author Dvlopes
	 * @return
	 * Finaliza processo após verifica se todas as pendências foram enviadas, caso não e retornado um 'false'
	 */
	public static finalizaProcessoMassaZerada(){
		System.err.println('finaliza Processo - countEnviado ['+countEnviado+'] tamanhoListaOriginal ['+tamanhoListaOriginal+']');

		diretorio = createDiretory('Evidencias//Renove_Seguro_Residencia - '+dataRecuerada);
		copiarExcel(ExcelCopiado, diretorio+"//Renove_Seguro_Residencia - "+dataRecuerada+".xlsx");
		copiarExcel(ExcelCopiado, 'C://Users//Public//RPA_ALLIANZ//MassaAuto//RoboRenovResidencia'+dataRecuerada+'.xlsx');
		deletarArquivosExcel(diretorioToDelete);
		//deleteFileDiretory(caminhoDiretorioExcel);
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarPdfSeguroRenovResidencia(String tipoAba, String emaildestination, String cpf){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/SeguroFuncionarioRenovResidencia/ConfiguracoesSeguroFuncionarioRenovResidencia.txt";
		lerConfigurações(pathTxtEmail)
		String destinatarioEmail = emaildestination+',retorno.renovacaoaz@allianz.com.br, helena.nishida@allianz.com.br, rosiete.dias@allianz.com.br, retornoaz@allianz.com.br';
		path1 = '\\C:\\Users\\Public\\docrequest.pdf';
		nome1 ='cálculo-Cotação.pdf';
		try{
			String assunto = 'SEGURO COLABORADOR - CPF: '+cpf+';'
			//String assunto = ' ### - ###: TESTE '+emaildestination+' TESTE :### - ### '
			List<String> anexos = new ArrayList<>();
			anexos[0] = path1;
			anexos[1] = nome1;
			sendEmail.CreateEmail.createEmailSendMultipleFilesSeguroFuncionario(sender, destinatarioEmail, anexos, tipoAba, assunto)
			valueCheckSendEmail="Enviado"
		} catch (Exception e){
			msgErro = e
			finalizar();
			valueCheckSendEmail="Não enviado"
		}
	}
	
	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarAlertaDeFalhaSeguroRenovResidencia(String tipoAba, String cpf){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/SeguroFuncionarioRenovAuto/ConfiguracoesSeguroFuncionarioRenovAuto.txt";
		lerConfigurações(pathTxtEmail)
		path1 = 'C://Users//Public//RPA_ALLIANZ//Data Files//Backoffce//SeguroFuncionarioRenovData//RoboRenovResidencia.xlsx';
		nome1 ='RoboRenovResidencia.xlsx';
		String emaildestination = 'denilson.inmetricsext@allpronet.com.br, andre.antunes@allianz.com.br, victor.novigoext@allpronet.com.br'
		String assunto = 'Email não enviado CPF: '+cpf+" inválido"
		try{
			System.out.println("Assunto :"+assunto)
			
			List<String> anexos = new ArrayList<>();
			anexos[0] = path1;
			anexos[1] = nome1;
			if(sendEmail.CreateEmail.createEmailSendMultipleFilesSeguroFuncionario(sender, emaildestination, anexos, tipoAba, assunto)){
				valueCheckSendEmail="Enviado"
				validaErro = ""
			} else {
				valueCheckSendEmail="Não enviado"
			}
		} catch (Exception e){
			msgErro = e
			finalizar();
			valueCheckSendEmail="Não enviado"
		}
	}
	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static emailFinalRenovResidenciaFinal(){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/SeguroFuncionarioRenovAuto/ConfiguracoesSeguroFuncionarioRenovAuto.txt";
		lerConfigurações(pathTxtEmail)
		String destinatarioEmail = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
		//path1 = '\\C:\\Users\\Public\\Evidencias\\Renove_Seguro_Residencia - '+dataRecuerada+'\\Renove_Seguro_Residencia - '+dataRecuerada+'.xlsx';
		//nome1 ='Renove_Seguro_Residencia -'+dataRecuerada+'.xlsx';
		try{
			List<String> anexos = new ArrayList<>();
			anexos[0] = caminhoDiretorioExcel;
			anexos[1] = "RoboRenovResidencia.xlsx";
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, destinatarioEmail, anexos, 'finalRenovAutoResidencia')
			finalizar();
		} catch (Exception e){
			finalizar();
			UpdateStatusNaoEnviado();
		}
	}



}




package backoffice

import auxiliares.ExtractDates
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import bean.MassaDados
import auxiliares.updateExcel
import auxiliares.StringFormatter;
import auxiliares.FileInput;
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class SeguroFuncionarioRenovAuto extends backofficeFuncionalidades{

	public static String caminhoDiretorioExcel = "C://Users//Public//RPA_ALLIANZ//Data Files//Backoffce//SeguroFuncionarioRenovData//RoboRenovAuto.xlsx";
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
		try{
			fluxoCompletoSeguroFuncionariosAuto(ramo, tipoAba, dataParaExecutar)
			finalizar();
			System.err.println('\nFinalizado...\n Casos finalizados  ['+ tamanhoListaOriginal+']\n');
		} catch (Exception e){}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 * Fluxo Completo Envio Pendencia Go
	 */
	public static fluxoCompletoSeguroFuncionariosAuto(String ramo, String tipoAba, String[] dataParaExecutar){
		diretorio = createDiretory('SeguroFuncionario !AUTO!');
		diretorioToDelete = diretorio;
		ExcelCopiado = copiarExcel(caminhoDiretorioExcel, diretorio+"\\RoboRenovAutoProcessamento.xlsx");
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

			enviarPdfSeguroRenovAutoFinal();
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
		countEnviado = 0;
		System.err.println(listaExcel.size())
		for (int i = 0; i < listaExcel.size();i++){
			System.err.println(i+" - "+listaExcel.get(i).getRenovarProposta()+" "+listaExcel.size()+" "+listaExcel.get(i).getStatus())
			if(listaExcel.get(i).getRenovarProposta().toLowerCase().trim().equals("sim")   &&  listaExcel.get(i).getStatus().trim().equals("Processar") ){
				String downloadOk;
				try{
					String CPF = listaExcel.get(i).getCpf();
					System.err.println("CPF: "+CPF+" "+CPF.length())
					if(CPF.length()==11){
						valueCheckSendEmail = ""
						msgErro = ""
						validaErro = ""
						deletarGerenciaonPdf();
						fluxoNavegaTelaRenovacao();
						fluxoPesquisaApolice(listaExcel.get(i).getApoliceSucep());
						downloadOk = recuperaValoresPremio(listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()));
						WebUI.delay(10);
						System.out.println("validaErro  ["+validaErro +"] downloadOk ["+downloadOk+"]")
						if(downloadOk.equals('true')){
							WebUI.delay(5);
							enviarPdfSeguroRenovAuto(tipoAba, listaExcel.get(i).getEmailColab(), CPF);
							validarProcessado();
							if(valueCheckSendEmail.equals('Enviado')){
								updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()), 16, premioAtual, 'SeguroRenovAuto');
								updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()), 17, premioAnterior, 'SeguroRenovAuto');
								updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel, listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()), 16, premioAtual, 'SeguroRenovAuto');
								updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()), 17, premioAnterior, 'SeguroRenovAuto');
								switchAtualizarExcel.switchUpdateExcel('SeguroRenovAuto', ExcelCopiado ,listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()));
								updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email Enviado Com Sucesso!', 'SeguroRenovAuto');
								switchAtualizarExcel.switchUpdateExcel('SeguroRenovAuto', caminhoDiretorioExcel ,listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()));
								updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email Enviado Com Sucesso!', 'SeguroRenovAuto');
							} else if(valueCheckSendEmail.equals('Não enviado')){
								updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email não enviado! '+msgErro+" "+validaErro, 'SeguroRenovAuto');
								updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 14, 'Processado', 'SeguroRenovAuto');
								updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email não enviado! '+msgErro+" "+validaErro, 'SeguroRenovAuto');
								updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 14, 'Processado', 'SeguroRenovAuto');
							}
						} else {
						  finalizar();
						  updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 14, 'Processado', 'SeguroRenovAuto');
						  updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email não enviado! '+msgErro, 'SeguroRenovAuto');
						  updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 14, 'Processado', 'SeguroRenovAuto');
						  updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email não enviado! '+msgErro, 'SeguroRenovAuto');
						  fluxoNavegaTelaRenovacao();
						}
					} else {
						enviarAlertaDeFalhaSeguroRenovAuto(tipoAba,listaExcel.get(i).getCpf());
						switchAtualizarExcel.switchUpdateExcel('SeguroRenovAuto', ExcelCopiado ,listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()));
						updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email não enviado CPF: '+listaExcel.get(i).getCpf()+" inválido", 'SeguroRenovAuto');
						switchAtualizarExcel.switchUpdateExcel('SeguroRenovAuto', caminhoDiretorioExcel ,listaExcel.get(i).getApoliceSucep(), Integer.parseInt(listaExcel.get(i).getLinha()));
						updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , listaExcel.get(i).getApoliceSucep() , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Email não enviado CPF: '+listaExcel.get(i).getCpf()+" inválido", 'SeguroRenovAuto');
					}

					countEnviado++
				} catch (Exception e) {
					p = false
					iniciaAplicacaoMatriz('AllianznetProdução');
					realizaLoginAllianzNet(tipoAba);
				}
			} else {
				p = false
				try{
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 14, 'Processado', 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Não Processado - Não Renovar', 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 14, 'Processado', 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , "" , Integer.parseInt(listaExcel.get(i).getLinha()), 18, 'Não Processado - Não Renovar', 'SeguroRenovAuto');
				} catch (Exception e){}
			}
		}
		System.out.println("Process ["+p+"]")
		return p
	}


	public static String recuperaValoresPremio(String numeroApolice, int linha){
		String downloadOk;
		try{
			premioAtual = recuperaValor('//*[@id="idPoliza_0_7"]', 'textContent', 1);
			premioAnterior = recuperaValor('//*[@id="idPoliza_0_8"]', 'outerText', 1);
			downloadOk = realizaDownloadApoliceSeguroFuncionario();
			println downloadOk;
			if(downloadOk.equals('true')){
//				updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice, linha, 16, premioAtual, 'SeguroRenovAuto');
//				updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice , linha, 17, premioAnterior, 'SeguroRenovAuto');
//				updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel, numeroApolice, linha, 16, premioAtual, 'SeguroRenovAuto');
//				updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice , linha, 17, premioAnterior, 'SeguroRenovAuto');
				downloadOk = 'true'
			}else{
				if(downloadOk.equals('FAILPDF')){
					msgErro ='Não Processado - Não foi gerado Recalculo'
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice , linha, 14, 'Processado', 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice , linha, 18, msgErro , 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice , linha, 14, 'Processado', 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice , linha, 18,  msgErro , 'SeguroRenovAuto');
					downloadOk = 'FAILPDF'
				}else if(downloadOk.equals('false')){
					msgErro ='Não Processado - Não foi gerado Recalculo'
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice , linha, 14, 'Processado', 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice , linha, 18,  msgErro , 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice , linha, 14, 'Processado', 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice , linha, 18,  msgErro , 'SeguroRenovAuto');
					downloadOk = 'false'
					println downloadOk
				}else if(downloadOk.equals('erroChassi')){
					msgErro ='Não Processado - "Placa do veículo ou chassis inválido."'
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice , linha, 14, 'Processado', 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice , linha, 18, msgErro , 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice , linha, 14, 'Processado', 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice , linha, 18,  msgErro , 'SeguroRenovAuto');
					downloadOk = 'false'
					println downloadOk
				}else if(downloadOk.equals('erroLupa')){
					msgErro = 'Não Processado - "A apólice já foi renovada."'
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice , linha, 14, 'Processado', 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice , linha, 18,  msgErro , 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice , linha, 14, 'Processado', 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice , linha, 18,  msgErro , 'SeguroRenovAuto');
					downloadOk = 'false'
					println downloadOk
				}else if(downloadOk.equals('erroBotaoRenovar')){
					msgErro ='Botão "Renovar com alteração" indisponível'
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice , linha, 14, 'Processado', 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice , linha, 18,  msgErro , 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice , linha, 14, 'N', 'SeguroRenovAuto');
					updateExcel.atualizaExcelSeguroResidenciaAuto(caminhoDiretorioExcel , numeroApolice , linha, 18,  msgErro , 'SeguroRenovAuto');
					downloadOk = 'false'
					println downloadOk
				}
			}
			return downloadOk;
		} catch (Exception e){
			downloadOk = 'false'
			return downloadOk;
		}
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
			;
			finalizar();
			processaFalhas(tipoAba, dataParaExecutar)
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
		//		if(countEnviado == tamanhoListaOriginal){
		//			diretorio = createDiretory('Evidencias//Renove_Seguro_Auto - '+dataRecuerada);
		//			copiarExcel(ExcelCopiado, diretorio+"//Renove_Seguro_Auto - "+dataRecuerada+".xlsx");
		//			copiarExcel(ExcelCopiado, 'C://Users//Public//RPA_ALLIANZ//MassaAuto//RoboRenovAuto'+dataRecuerada+'.xlsx');
		//			//	copiarExcel(ExcelCopiado, '\\x:\\Renove_Seguro_Auto - '+dataRecuerada+'.xlsx');
		//
		//			deletarArquivosExcel(diretorioToDelete);
		//			//deleteFileDiretory(caminhoDiretorioExcel);
		//			processoFinalizado = true;
		//		}
		return processoFinalizado;
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 * Finaliza processo após verifica se todas as pendências foram enviadas, caso não e retornado um 'false'
	 */
	public static boolean finalizaProcessoMassaZerada(){
		System.err.println('finaliza Processo - countEnviado ['+countEnviado+'] tamanhoListaOriginal ['+tamanhoListaOriginal+']');
		boolean processoFinalizado = false;
		diretorio = createDiretory('Evidencias//Renove_Seguro_Auto - '+dataRecuerada);
		copiarExcel(ExcelCopiado, diretorio+"//Renove_Seguro_Auto - "+dataRecuerada+".xlsx");
		copiarExcel(ExcelCopiado, 'C://Users//Public//RPA_ALLIANZ//MassaAuto//RoboRenovAuto'+dataRecuerada+'.xlsx');
		deletarArquivosExcel(diretorioToDelete);
		//deleteFileDiretory(caminhoDiretorioExcel);
		processoFinalizado = true;
		return processoFinalizado;
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarPdfSeguroRenovAuto(String tipoAba, String emaildestination, String cpf){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/SeguroFuncionarioRenovAuto/ConfiguracoesSeguroFuncionarioRenovAuto.txt";
		lerConfigurações(pathTxtEmail)

		System.err.println("emaildestination ["+emaildestination+"]")
		String destinatarioEmail = emaildestination+',retorno.renovacaoaz@allianz.com.br, helena.nishida@allianz.com.br, rosiete.dias@allianz.com.br, retornoaz@allianz.com.br';
		//String destinatarioEmail = emaildestination+',danillo.inmetricsext@allpronet.com.br,andre.antunes@allianz.com.br';
		System.err.println("destinatarioEmail ["+destinatarioEmail+"]")
		path1 = '\\C:\\Users\\Public\\RenovAuto.pdf';
		nome1 ='pré-cálculo.pdf';

		try{
			//String cpf = "17778936828";
			String assunto = 'SEGURO COLABORADOR - CPF: '+cpf;

			System.out.println("Assunto :"+assunto)

			List<String> anexos = new ArrayList<>();
			anexos[0] = path1;
			anexos[1] = nome1;
			if(sendEmail.CreateEmail.createEmailSendMultipleFilesSeguroFuncionario(sender, destinatarioEmail, anexos, tipoAba, assunto)){
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
	public static enviarAlertaDeFalhaSeguroRenovAuto(String tipoAba, String cpf){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/SeguroFuncionarioRenovAuto/ConfiguracoesSeguroFuncionarioRenovAuto.txt";
		lerConfigurações(pathTxtEmail)
		path1 = 'C://Users//Public//RPA_ALLIANZ//Data Files//Backoffce//SeguroFuncionarioRenovData//RoboRenovAuto.xlsx';
		nome1 ='RoboRenovAuto.xlsx';
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
	public static enviarPdfSeguroRenovAutoFinal(){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/SeguroFuncionarioRenovAuto/ConfiguracoesSeguroFuncionarioRenovAuto.txt";
		lerConfigurações(pathTxtEmail)
		String destinatarioEmail = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
		System.err.println("destinatarioEmail:\n "+ destinatarioEmail)
		System.err.println()
		//path1 = '\\C:\\Users\\Public\\Evidencias\\Renove_Seguro_Auto - '+dataRecuerada+'\\Renove_Seguro_Auto - '+dataRecuerada+'.xlsx';
		//nome1 ='Renove_Seguro_Auto-'+dataRecuerada+'.xlsx';
		try{
			List<String> anexos = new ArrayList<>();
			anexos[0] = caminhoDiretorioExcel;
			anexos[1] = "RoboRenovAuto.xlsx";
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, destinatarioEmail, anexos, 'finalRenovAuto')
			finalizar();
		} catch (Exception e){
			finalizar();
			UpdateStatusNaoEnviado();
		}
	}
}




package transporte

import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import backoffice.backofficeFuncionalidades
import bean.MassaDados
import auxiliares.updateExcel
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import auxiliares.MenuMatriz

public class EmissaoApoliceTransportador extends backofficeFuncionalidades{


	//	public static String caminhoDiretorioExcel = "C://Users//Public//RPA_ALLIANZ//MassaAuto//EmissaoApoliceTransporte//RoboEmissaoApoliceTransporte.xlsx";
	public static String caminhoDiretorioExcel = "C://Users//Public//RPA_ALLIANZ//Data Files//Transporte//RoboEmissaoApoliceTransporte.xlsx"
	public static int countEnviado = 0;
	public static String ExcelCopiado;
	public static String dataRecuerada = getDateHour();
	public static String diretorio;
	public static String diretorioToDelete;
	public static String numeroProposta
	public static int tamanhoListaOriginal;
	public static List<MassaDados> listaExcel;

	/**
	 * @author T_Dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static ApoliceTransportador(String tipoAba){
		fluxoCompletoEmissaoApoliceTransportador(tipoAba);
		processaFalhas(finalizaProcesso(tipoAba),tipoAba)
		enviarEmailEmissaoApoliceTransportador(tipoAba)
		finalizar();
	}


	/**
	 * @author T_Dvlopes
	 * @param tipoAba
	 * @return
	 * Fluxo Completo Envio Pendencia Go
	 */
	public static fluxoCompletoEmissaoApoliceTransportador(String tipoAba){
		System.err.println()
		carregaDados(tipoAba)
		if(verificaDados(listaExcel)){
			System.out.println("fluxo Debloqueio Apolice Transportador")
			fluxoDebloqueioApoliceTransportador(listaExcel, tipoAba);

			System.out.println("fluxo Emissão Apolice Transportador")
			carregaDados(tipoAba)
			fluxoEmissaoApoliceTransportador(listaExcel, tipoAba)
			System.err.println()
		}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static init(){
		System.err.println("EXECUTANDO...")
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands("userSenhaBloqueioFiel");
	}

	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static carregaDados(String tipoAba){
		listaExcel = switchLeitorExcel.switchExcel(caminhoDiretorioExcel,tipoAba);
		int t = listaExcel.size();
		println t
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean verificaDados(List<MassaDados> listaExcel){
		boolean carregar = false
		for (int i = 0; i <= listaExcel.size(); i++) {
			if(listaExcel.get(i).getStatus().equals("Processar")){
				System.err.println(i+' - Status - '+ listaExcel.get(i).getStatus())
				carregar = true
				break
			}
		}
		System.err.println('carregaDados ['+ carregar+']')
		return carregar
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param listaExcel
	 * @return
	 * Efetua o fluxo de envio de pendência e retoma caso ocorra alguma falha
	 */
	public static void fluxoDebloqueioApoliceTransportador(List<MassaDados> listaExcel, String tipoAba){
		init()
		navegarMenu()
		clickXpath('//*[@id="pBloqueadas"]', 5)
		for (int i = 0; i < listaExcel.size();i++){
			if(listaExcel.get(i).getDesbloqueioproposta().equals("Processar")){
				System.err.println("Fluxo Debloqueio Apolice Transportador - LINHA ["+listaExcel.get(i).getLinha()+"] ["+listaExcel.get(i).getApolice()+"]")
				try{
					desbloqueio(listaExcel, i, tipoAba)
				} catch (Exception e) {
					println validaErro
					auxiliares.WriteExcelFactory.updateSheetLine('',  Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, "Processado", 10 ,'apoliceTransportador');
					auxiliares.WriteExcelFactory.updateSheetLine('',  Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, validaErro , 13 ,'apoliceTransportador');
					init()
					navegarMenu()
					clickXpath('//*[@id="pBloqueadas"]', 5)
				}
			}
		}
		//FECHAR
		alteraJanejaGenerica(0,2);
		WebUI.closeBrowser();

	}

	/**
	 * @author be54955
	 * @param listaExcel
	 * @param i
	 * @param tipoAba
	 * @return
	 */
	public static desbloqueio(List<MassaDados> listaExcel, int i, String tipoAba){
		validaErro = 'SemErro'
		fluxoPesquisaPropostaBloqueadaNovo(listaExcel.get(i).getProposta().trim());
		WebUI.delay(10);
		String msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 2)
		System.out.println("Alerta ["+msg+"]")

		String numeroProposta = fluxoLiberacao();


		System.err.println("numeroProposta  " +numeroProposta )
		if(validaErro.equals('SemErro')){
			auxiliares.WriteExcelFactory.updateSheetLine('',  Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, "Processado", 10 ,'apoliceTransportador');
			auxiliares.WriteExcelFactory.updateSheetLine('',  Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, numeroProposta , 11 ,'apoliceTransportador');
			validaErro = ""
		}else if(msg.equals(" Não foram encontrados dados para a ação selecionada.")){
			auxiliares.WriteExcelFactory.updateSheetLine('',  Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, "Processado", 9 ,'apoliceTransportador');
			auxiliares.WriteExcelFactory.updateSheetLine('',  Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, "Processado", 10 ,'apoliceTransportador');
			auxiliares.WriteExcelFactory.updateSheetLine('',  Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, msg , 13 ,'apoliceTransportador');
			fluxoFinalizar();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param listaExcel
	 * @return
	 * Efetua o fluxo de envio de pendência e retoma caso ocorra alguma falha
	 */
	public static void fluxoEmissaoApoliceTransportador(List<MassaDados> listaExcel, String tipoAba){
		init()
		WebUI.delay(2);
		navegarMenu()
		alteraJanejaGenerica(1,2);
		WebUI.delay(17);
		for (int i = 0; i < listaExcel.size();i++){
			System.err.println("["+i+"] Fluxo Emissao Apolice Transportador ["+listaExcel.get(i).getDesbloqueioproposta()+"]")
			if(listaExcel.get(i).getDesbloqueioproposta().equals("Processado")){
				System.err.println("Fluxo Emissao Apolice Transportador - LINHA ["+listaExcel.get(i).getLinha()+"] APOLICE ["+listaExcel.get(i).getApolice()+"]")
				try{
					emissao(listaExcel, i, tipoAba)
				} catch (Exception e) {
					println validaErro
					auxiliares.WriteExcelFactory.updateSheetLine('',  Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, "Processado", 9 ,'apoliceTransportador');
					auxiliares.WriteExcelFactory.updateSheetLine('',  Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, "Processado", 10 ,'apoliceTransportador');
					auxiliares.WriteExcelFactory.updateSheetLine('',  Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, validaErro , 13 ,'apoliceTransportador');
					init()
					fluxoProducao();
					navegarMenu()
					alteraJanejaGenerica(1,2);
				}
			}
		}
		//FECHAR
		alteraJanejaGenerica(0,2);
		WebUI.closeBrowser();
	}

	/**
	 * @author be54955
	 * @param listaExcel
	 * @param i
	 * @param tipoAba
	 * @return
	 */
	public static emissao(List<MassaDados> listaExcel, int i, String tipoAba){
		System.err.println()
		WebUI.delay(5);
		//Selecionar outras Operações
		clickXpath('//*[@id="tipoDeSeleccion2"]', 5);

		//PREENCHE CAMPO APOLICE TELA PRODUÇÃO
		System.out.println("APOLICE ["+listaExcel.get(i).getApolice()+"]")

		preencheXpath('//*[@id="polSusep_SUSEP"]', listaExcel.get(i).getApolice(), 2);
		commandTAB('//*[@id="polSusep_SUSEP"]');

		//clica em selecionar
		clickXpath('//*[@id="btnSeleccionar"]', 5);

		//Selecionar Operativa
		clickXpath('//*[@id="codOperativa"]', 2);
		//Selecionar Operativa - Emitir Aplicação ITEM
		clickXpath('//*[@id="codOperativa"]/option[3]', 1);

		//clica AVANÇAr
		clickXpath('//*[@id="btnSeleccionOperativa"]', 1);

		//clica LINHA [ramo modalidade subRamo]
		clickXpath('//*[@id="row_0_tableRamos"]', 1);

		fluxoLiberacaoApolice(listaExcel.get(i).getSubGrupo(), listaExcel.get(i).getDataInicioVigencia(), listaExcel.get(i).getCnpjCpf(), listaExcel.get(i).getNomeTomador(),  listaExcel.get(i).getValorPremioLiquido(), listaExcel.get(i).getQtdParcelas());System.err.println("1")
		fluxoAvancar();System.err.println("2")

		//Opcional
		listaClientes()
		fluxoTextoComplementar(listaExcel.get(i).getTextoComplementar());System.err.println("3")
		fluxoDesbloqueio(listaExcel.get(i).getProposta());System.err.println("4")

		if(validaErro.equals('SemErro')){
			auxiliares.WriteExcelFactory.updateSheetLine('',  Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, "Processado", 9 ,'apoliceTransportador');
			auxiliares.WriteExcelFactory.updateSheetLine('',  Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, apolice, 12 ,'apoliceTransportador');
		}else{
			fluxoFinalizar();
		}

		//Home
		clickXpath('//*[@id="imgOptMenuBackHome"]', 5);
		WebUI.delay(10);
	}

	/**
	 * 
	 * @return
	 */
	public static listaClientes(){
		WebUI.delay(5);
		if(listaDeTelas(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 0))){


			//*[@id="ListaClientesBean$listaClientes_tbody"] tr

		}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static fluxoFinalizar(){
		clickXpath('//*[@id="o_2"]', 1);
		finalizar();
	}



	public static fluxoErro(String numeroProposta, int linha, String tipoAba){
		finalizar();
		validarEnviado(numeroProposta);
		updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , listaExcel.get(linha).getProposta() , linha, 9, "Processado", 'apoliceTransportador');
		updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , listaExcel.get(linha).getProposta() , linha, 11, numeroProposta, 'apoliceTransportador');
		iniciaAplicacaoMatriz('MatrizProdução');
		realizaLoginMatrizProdução(tipoAba);
		fluxoProducao();
	}





	/**
	 * @author T_Dvlopes
	 * @return
	 * Finaliza processo após verifica se todas as pendências foram enviadas, caso não e retornado um 'false'
	 */
	public static boolean finalizaProcesso(String tipoAba){
		System.err.println('Finaliza Processo - countEnviado ['+countEnviado+'] tamanhoListaOriginal ['+tamanhoListaOriginal+']');
		boolean processoFinalizado = false;
		try{
		//	carregaDados(tipoAba)
			if(countEnviado == tamanhoListaOriginal){
				processoFinalizado = true
			}
//		if(countEnviado == tamanhoListaOriginal){
//			diretorio = createDiretory('Evidencias//EmissaoApoliceTransporte- '+dataRecuerada);
//			copiarExcel(ExcelCopiado, diretorio+"//EmissaoApoliceTransporte - Finalizado -"+dataRecuerada+".xlsx");
//			//copiarExcel(ExcelCopiado, '\\x:\\EmissaoApoliceTransporte - Finalizado - '+dataRecuerada+'.xlsx');
//			deletarArquivosExcel(diretorioToDelete);
//			deleteFileDiretory(caminhoDiretorioExcel);
//			processoFinalizado = true;
//		}
		} catch (Exception e){
		  System.err.println('Finaliza Processo\n'+e)
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
		System.err.println('Processa Falhas - processoFinalizado ['+processoFinalizado+'] tipoAba ['+tipoAba+']');
		if(!processoFinalizado){
			fluxoDebloqueioApoliceTransportador(listaExcel, tipoAba);
			processaFalhas( processoFinalizado,  tipoAba)
		}
		
		
		
		
//		if(processoFinalizado==false){
//			tamanhoListaOriginal = 0;
//			/**
//			 * Nova tentativa
//			 */
//			listaExcel = switchLeitorExcel.switchExcel(ExcelCopiado, tipoAba);
//
//			iniciaAplicacaoMatriz('MatrizProdução');
//			realizaLoginMatrizProdução(tipoAba);
//			//
//			fluxoDebloqueioApoliceTransportador(listaExcel, tipoAba);
//			/**
//			 * 'tamanhoListaOriginal' recebe tamanho da nova lista com pendencias com falha
//			 */
//			tamanhoListaOriginal = listaExcel.size();
//			/**
//			 * Chamada racursiva para re-processar falhas
//			 */
//			processaFalhas(finalizaProcesso(), tipoAba);
//		}
	}


	/**
	 * @author T_Dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailEmissaoApoliceTransportador(String tipoAba){
		System.out.println()
		
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Transporte/ConfiguracoesEmissaoApoliceTransportador.txt";
		lerConfigurações(pathTxtEmail);
		//		path1 = '\\C:\\Users\\Public\\Evidencias//EmissaoApoliceTransporte- '+dataRecuerada+'//EmissaoApoliceTransporte - Finalizado -'+dataRecuerada+'.xlsx';
		//		nome1 = 'Emissao Apolice Trasnportador - '+dataRecuerada+'.xlsx';
		path1 = 'C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Transporte\\RoboEmissaoApoliceTransporte.xlsx';
		nome1 = 'RoboEmissaoApoliceTransporte.xlsx';
		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
		try{
			listaExcel = switchLeitorExcel.switchExcel(path1, tipoAba);
			if(listaExcel.size()==0){
				List<String> anexos = new ArrayList<>();
				anexos[0] = path1;
				anexos[1] = nome1;
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos, tipoAba);
			}
		} catch (Exception e){
			enviarEmailEmissaoApoliceTransportador(tipoAba);
		}
	}


	/**
	 * @author T_DCDias
	 * @return
	 */
	public static navegarMenu(){
		WebUI.delay(15)
		MenuMatriz.producaoSinistro()
		MenuMatriz.subMenu('/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul', 'a', 'Produção')
		WebUI.delay(5)
		alteraJanejaGenerica(1, 1)
		WebUI.delay(5)
		WebUI.maximizeWindow()
		WebUI.delay(5)

	}


	/**
	 * 
	 * @param numeroApolice
	 * @return
	 */
	public static fluxoPesquisaPropostaBloqueadaNovo(String numeroApolice){
		try{
			System.err.println('Numero proposta ['+numeroApolice+']')
			preencheXpath('//*[@id="poliza_Policy"]', numeroApolice, 5);
			//clica em pesquisar
			clickXpath('//*[@id="btnBuscar"]', 2);
		}catch (Exception e){
			System.err.println("Numero Apolice não encontrada");
			finalizar();
			validarEnviado("Numero Apolice não encontrada");
		}
	}
}

package saude

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.switchLeitorExcel
import bean.MassaDados
import auxiliares.updateExcel
import backoffice.backofficeFuncionalidades

public class SegundaViaCarterinhaFuncionalidades extends backofficeFuncionalidades{

	public static String caminhoDiretorioExcel = "C://Users//Public//RPA_ALLIANZ//MassaAuto//RoboSegundaVia.xlsx";
	public static int countEnviado = 0;
	public static String ExcelCopiado;
	public static String dataRecuerada = getDateHour();
	public static String diretorio;
	public static String diretorioToDelete;
	public static int tamanhoListaOriginal;
	public static List<MassaDados> listaExcel;
	public static String confirmacao


	public static fluxoSegundaViaCarterinha(String tipoAba){
		fluxoCompletoSegundaViaCarterinha(tipoAba)
		//finalizaChromeCMD();
		finalizar();
		enviarSegundaViaCarterinha(tipoAba)
		System.err.println('\nFinalizado...\n Casos finalizados  ['+ tamanhoListaOriginal+']\n');
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 * Fluxo Completo Envio Pendencia Go
	 */
	public static fluxoCompletoSegundaViaCarterinha(String tipoAba){
		diretorio = createDiretory('Processamento !Segunda via Carterinha!');
		diretorioToDelete = diretorio;
		ExcelCopiado = copiarExcel(caminhoDiretorioExcel, diretorio+"\\SegundaVia.xlsx");
		listaExcel = switchLeitorExcel.switchExcel(copiarExcel(caminhoDiretorioExcel, diretorio+"\\SegundaVia.xlsx"), tipoAba);
		tamanhoListaOriginal = listaExcel.size();

		println tamanhoListaOriginal;

		if(tamanhoListaOriginal>0){

			/**
			 * Efetua inicialização da aplicação Matriz e faz o fluxo de envio de pendência
			 */
			iniciaFluxo(tipoAba);
			fluxoSegundaVia(listaExcel, tipoAba);
			/**
			 * Processa as pendências que apresentaram falhas
			 */
			processaFalhas(finalizaProcesso(), tipoAba);
		}
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param listaExcel
	 * @return
	 * Efetua o fluxo de envio de pendência e retoma caso ocorra alguma falha
	 */
	public static void fluxoSegundaVia(List<MassaDados> listaExcel, String tipoAba){
		countEnviado = 0;
		String envioSegundavia;
		for (int i = 0; i < listaExcel.size();i++){
			try{
				pesquisaProposta(listaExcel.get(i).getProposta(), listaExcel.get(i).getItem());
				envioSegundavia = fluxoSelecionaListados(listaExcel.get(i).getNumeroCarteirinha());
				if(envioSegundavia.contains('Pedido de 2ª via já realizado. Em breve a carteirinha será enviada')||envioSegundavia.contains('APÓLICE NÃO ESTÁ EM VIGOR')){
					auxiliares.WriteExcelFactory.updateSheetLine(listaExcel.get(i).getProposta(), Integer.parseInt(listaExcel.get(i).getLinha()) ,ExcelCopiado, envioSegundavia, 7, 'SegundaVia');
					auxiliares.WriteExcelFactory.updateSheetLine(listaExcel.get(i).getProposta(), Integer.parseInt(listaExcel.get(i).getLinha()) ,ExcelCopiado, 'Processado', 5, 'SegundaVia');
					iniciaFluxo(tipoAba);
				}else{
					updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , listaExcel.get(i).getProposta(), Integer.parseInt(listaExcel.get(i).getLinha()), 5, envioSegundavia, 'SegundaVia');
					auxiliares.WriteExcelFactory.updateSheetLine(listaExcel.get(i).getProposta(), Integer.parseInt(listaExcel.get(i).getLinha()) ,ExcelCopiado, 'OK', 7, 'SegundaVia');
					consutaGerais();
				}
			} catch (Exception e) {
				auxiliares.WriteExcelFactory.updateSheetLine(listaExcel.get(i).getProposta(), Integer.parseInt(listaExcel.get(i).getLinha()) ,ExcelCopiado, 'NãoProcessado', 5, 'SegundaVia');
				auxiliares.WriteExcelFactory.updateSheetLine(listaExcel.get(i).getProposta(), Integer.parseInt(listaExcel.get(i).getLinha()) ,ExcelCopiado, 'Falha no Site', 7, 'SegundaVia');
				iniciaFluxo(tipoAba)
			}
		}
	}

	public static void iniciaFluxo(tipoAba){
		iniciaAplicacaoMatriz('MatrizProdução');
		realizaLoginMatrizProdução(tipoAba);
		FluxoAbaConsultasGerais();
	}


	/**
	 * @author ALZ_ServiceQA
	 * @return
	 * Finaliza processo após verifica se todas as pendências foram enviadas, caso não e retornado um 'false'
	 */
	public static boolean finalizaProcesso(){
		System.err.println('finaliza Processo - countEnviado ['+countEnviado+'] tamanhoListaOriginal ['+tamanhoListaOriginal+']');
		boolean processoFinalizado = false;
		finalizar();
		if(countEnviado == tamanhoListaOriginal){
			diretorio = createDiretory('Evidencias//SegundaVia - '+dataRecuerada);
			copiarExcel(ExcelCopiado, diretorio+"//SegundaVia Processado-"+dataRecuerada+".xlsx");
			//copiarExcel(ExcelCopiado, '\\x:\\SegundaVia Processado-'+dataRecuerada+'.xlsx');
			deletarArquivosExcel(diretorioToDelete);
			deleteFileDiretory(caminhoDiretorioExcel);
			processoFinalizado = true;
		}
		return processoFinalizado;
	}

	public static void consutaGerais(){
		alteraJanejaGenerica(0, 5);
		clickXpath('//*[@id="topicNav"]/li[2]/a', 1)
	}
	//

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
			listaExcel = switchLeitorExcel.switchExcel(ExcelCopiado, tipoAba);
			iniciaFluxo(tipoAba)
			fluxoSegundaVia(listaExcel, tipoAba);
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


	public static void FluxoAbaConsultasGerais(){
		try{
			UpdateStatusNaoEnviado();
			clicaNoMenuTelaMatriz("Consultas & Estatísticas");
			clicaNoSubMenuTelaMatriz("Consultas");
			clickXpath('//*[@id="topicNav"]/li[2]/a', 1)
		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}



	/**Realiza Pesquisa Numero Proposta
	 * @param numeroProspota
	 */
	public static void pesquisaProposta(String numeroProspota, String qtdItens){
		try{
			//trocaJanela('Critérios de Pesquisa');
			//Critérios de Pesquisa
			alteraJanejaGenerica(1, 5);
			//preenche numeroProposta
			preencheXpath('//*[@id="apolice"]', numeroProspota, 5);

			preencheXpath('//*[@id="aplica"]', qtdItens, 3);
			//clica pesquisar
			clickXpath('//*[@id="o_10"]', 1);
			//Altera Janela dados gerais
			alteraJanejaGenerica(1, 5);
			//clica Serviços
			clickXpath('//*[@id="7082"]', 2);
			//clica Clicar na aba Sol. 2via
			clickXpath('//*[@id="7093"]', 2);

		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}


	/**Realiza Pesquisa Numero Proposta
	 * @param numeroProspota
	 */
	public static String fluxoSelecionaListados(String ncarteirinha){
		try{
			confirmacao = '';
			//Altera PEDIDO DA 2ª VIA DE CARTEIRINHAS
			alteraJanejaGenerica(2, 5);
			fluxoSelecionarSegurado(ncarteirinha);
			//aceitar
			clickXpath('//*[@id="o_5"]', 1);
			fechaAlertaSegundaVia();
			confirmacao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 3);
			if(confirmacao.contains('Pedido de 2ª via já realizado. Em breve a carteirinha será enviada')||confirmacao.contains('APÓLICE NÃO ESTÁ EM VIGOR')){
				finalizar();
				valueCheck = 'N';
				return confirmacao;
			}else{
				confirmacao = 'Processado'
			}
			fecharJanelaPedidoSegundaCarteirinha();

			//CLICAR FICHA GESTAO
			//	clickXpath('//*[@id="-61"]/div[1]', 1);
			//Altera FICHA GESTAO
			//	alteraJanejaGenerica(2, 5);

			println 'VALIDAR ENVIO'

			return confirmacao;
		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
			return confirmacao;
		}
	}



	public static fluxoSelecionarSegurado(String nCarteirinha){

		//int qtdMaxima = 99;
		String recuperarTextoSegurado
		String recuperaNumeroCarteirinha
		println 'Numero carteirinha ['+nCarteirinha+']';
		String xpath = '//*[@id="resultsContent"]/table'
		String tag = "input";
		List<WebElement> listOfElements
		listOfElements = listaDeElementos(xpath,tag)
		println listOfElements.size();
		int qtdMax =  listOfElements.size()
		for(int i = 1; i < qtdMax; i++){
			//recuperarTextoSegurado = recuperaValor('/html/body/div[1]/div[2]/table/tbody/tr[1]/td/form/div[2]/div/table/tbody/tr[2]/td/div/table/tbody/tr['+i+']/td[2]/table/tbody/tr/td/input', 'value', 1);
			recuperaNumeroCarteirinha = recuperaValor('/html/body/div[1]/div[2]/table/tbody/tr[1]/td/form/div[2]/div/table/tbody/tr[2]/td/div/table/tbody/tr['+i+']/td[1]/table/tbody/tr/td/input', 'value', 1);

			println recuperaNumeroCarteirinha
			//println recuperarTextoSegurado;
			//			if(recuperarTextoSegurado.contains(nCarteirinha.toString())){
			//				clickXpath('/html/body/div[1]/div[2]/table/tbody/tr[1]/td/form/div[2]/div/table/tbody/tr[2]/td/div/table/tbody/tr['+i+']/td[4]/input', 1);
			//				break;
			//			}

			if(recuperaNumeroCarteirinha.contains(nCarteirinha.toString())){
				clickXpath('/html/body/div[1]/div[2]/table/tbody/tr[1]/td/form/div[2]/div/table/tbody/tr[2]/td/div/table/tbody/tr['+i+']/td[4]/input', 1);
				break;
			}
		}

	}


	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarSegundaViaCarterinha(String tipoAba){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/SegundaViaCarterinha/ConfiguracoesSegundaViaCarterinha.txt";
		lerConfigurações(pathTxtEmail);

		path1 = '\\C:\\Users\\Public\\Evidencias//SegundaVia - '+dataRecuerada+'\\SegundaVia Processado-'+dataRecuerada+'.xlsx';
		nome1 = 'SegundaVia Processado-'+dataRecuerada+'.xlsx';

		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));

		try{
			listaExcel = switchLeitorExcel.switchExcel(path1, tipoAba);
			if(listaExcel.size()==0){
				List<String> anexos = new ArrayList<>();
				anexos[0] = path1;
				anexos[1] = nome1;
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba )
			}


		} catch (Exception e){
			enviarSegundaViaCarterinha(tipoAba);
		}
	}


	public static fecharJanelaPedidoSegundaCarteirinha(){
		WebUI.delay(5);
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		String parent =  it.next();
		String child =it.next();
		String child2 =it.next();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
		driver.switchTo().window(child2);
		driver.close();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
		driver.close();
		driver.switchTo().window(parent);
	}



	public static atualizaExcel(String envioSegundavia, String numeroApolice, int linha){
		if(envioSegundavia.equals('Enviado')){
			updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice, linha, 5, 'enviado', 'SegundaVia');
		}else{
			updateExcel.atualizaExcelSeguroResidenciaAuto(ExcelCopiado , numeroApolice, linha, 5, 'N', 'SegundaVia');
		}
	}






	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static void fechaAlertaSegundaVia(){
		try{
			WebDriver driver = DriverFactory.getWebDriver();
			driver.switchTo().alert().accept();
		}catch (Exception e){
		}
	}




}















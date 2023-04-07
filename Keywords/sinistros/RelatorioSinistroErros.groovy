package sinistros

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import auxiliares.FileInput
import auxiliares.StringFormatter
import backoffice.backofficeFuncionalidades


public class RelatorioSinistroErros  extends backofficeFuncionalidades{


	public static String caminhoDiretorioExcel = "C://Users//Public//RPA_ALLIANZ//MassaAuto//RoboSegundaVia.xlsx";

	public static String dataRecuerada = getDateHour();
	public static String diretorio;





	public static fluxoSinistroErros(String tipoAba){

		fluxoCompletoSinistroErros(tipoAba)
		//finalizaChromeCMD();
		finalizar();

		enviarSegundaViaCarterinha(tipoAba)

	}


	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 * Fluxo Completo Envio Pendencia Go
	 */
	public static fluxoCompletoSinistroErros(String tipoAba){
		//diretorio = createDiretory('Processamento !Segunda via Carterinha!');



		/**
		 * Efetua inicialização da aplicação Matriz e faz o fluxo de envio de pendência
		 */
		iniciaFluxo(tipoAba);

		fluxoErros(tipoAba);



	}


	/**
	 * @author ALZ_ServiceQA
	 * @param listaExcel
	 * @return
	 * Efetua o fluxo de envio de pendência e retoma caso ocorra alguma falha
	 */
	public static void fluxoErros(String tipoAba){

		try{
			editaFiltros()
		} catch (Exception e) {

			//auxiliares.WriteExcelFactory.updateSheetLine(listaExcel.get(i).getProposta(), Integer.parseInt(listaExcel.get(i).getLinha()) ,ExcelCopiado, 'Falha no Site', 7, 'SegundaVia');
			iniciaFluxo(tipoAba)
		}
	}
	/**
	 * @author T_Dvlopes
	 */
	public static void iniciaFluxo(tipoAba){
		iniciaAplicacaoMatriz('IntranetSinistro');
		realizaLoginCrivoIntraNet(tipoAba);
		//FluxoAbaConsultasGerais();
	}
	/**
	 * @author T_Dvlopes
	 */
	public static void editaFiltros(){
		fluxoEditarFiltros();
		fluxoConfig();
		fluxoEditarColunas();
	}


	/**
	 * @author T_Dvlopes
	 */
	public static void listaIgual(){
		//	clickXpath('//*/filtro[1]/div/div[2]/div/div/div[2]/select');
		//	clickXpath('//*/filtro[1]/div/div[2]/div/div/div[2]/select/option[1]');
		//
		//	clickXpath('//*/filtro[2]/div/div[2]/div/div/div[2]/select');
		//	clickXpath('//*/filtro[2]/div/div[2]/div/div/div[2]/select/option[1]');
		//
		//	clickXpath('//*/filtro[4]/div/div[2]/div/div/div[2]/select');
		//	clickXpath('//*/filtro[4]/div/div[2]/div/div/div[2]/select/option[1]');
		//
		//	clickXpath('//*/filtro[5]/div/div[2]/div/div/div[2]/select');
		//	clickXpath('//*/filtro[5]/div/div[2]/div/div/div[2]/select/option[1]');
		//
		//	clickXpath('//*/filtro[6]/div/div[2]/div/div/div[2]/select');
		//	clickXpath('//*/filtro[6]/div/div[2]/div/div/div[2]/select/option[1]');
		//
		//	clickXpath('//*/filtro[7]/div/div[2]/div/div/div[2]/select');
		//	clickXpath('//*/filtro[7]/div/div[2]/div/div/div[2]/select/option[1]');
		//
		//	clickXpath('//*/div[2]/div/filtro-campo/div/div[1]/div[2]');
		//	clickXpath('//*/div/filtro-campo/div/div[1]/div[2]/select/option[1]');
	}

	/**
	 * @author T_Dvlopes
	 */		
	public static void fluxoEditarFiltros(){
		//ACESSAR O MENU LATERAL ESQUERDO /EXTRAÇÕS > OPERAÇÕES
		clickXpath('//sidebar/div[1]/div/ul/li[2]/a', 10);
		//3.1 CÓDIGO DA OPERAÇÃO: (NÃO PREENCHER)
		//3.2 PERÍODO (CALENDÁRIO)  // 28/11/2019 23:59:59
		limpaCampo('//*/filtros-extracao/fieldset/div[1]/div/input',5);
		preencheXpath('//*/filtros-extracao/fieldset/div[1]/div/input', '28/11/2019 23:59:59', 2);
		commandTAB('//*/filtros-extracao/fieldset/div[1]/div/input');
		//3.3 SERVIDORES: (NÃO PREENCHER)
		//3.4 CRITÉRIOS: (NÃO PREENCHER)
		//3.5 GRUPOS: (NÃO PREENCHER)
		//3.6 USUÁRIOS: analisesinistro
		editarUsuarios();
		//3.7 RESPOSTAS: (NÃO PREENCHER)
		//3.8 SISTEMAS: (NÃO PREENCHER)
		//3.9 CAMPO: (NÃO PREENCHER)
		//3.10 TIPO DE EXECUÇÃO DE OPERAÇÕES: (NÃO PREENCHER)
		//3.11 EXISTE UM SUB FILTRO EM CADA LINHA (COMBO DE SELEÇÃO): SELECIONAR (Igual)
		listaIgual();
	}



	/**
	 * @author T_Dvlopes
	 */
	public static void fluxoConfig(){
		//Clicar Configurações
		clickXpath('//*[@id="headingTwo"]/h4/a/span', 2);
		//4.1 IDENTIFICAÇÃO: (NOME PREENCHIDO AUTOMATICAMENTE PELO SISTEMA)
		//4.2 SEPARAR EM VÁRIOS ARQUIVOS (CAIXA DE SELEÇÃO): NÃO SELECIONAR
		//4.3 GRAVAR ARQUIVO DE SAÍDA EM DISCO (CAIXA DE SELEÇÃO): NÃO SELECIONAR
		//4.4 FORMATO DATA (COMBO DE SELEÇÃO): SELECIONAR FORMATO (yyyy-MM-dd HH:mm:ss (2016-01-25 23:59:59))
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[6]/div[1]/select',2);
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[6]/div[1]/select/option[2]', 2);
		//4.5 FUSO HORÁRIO (COMBO DE SELEÇÃO): SELECIONAR (Fuso horário do servidor)
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[6]/div[2]/select', 2);
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[6]/div[2]/select/option[1]', 1);
		//4.6 FORMATO LÓGICO (COMBO DE SELEÇÃO): SELECIONAR (True/False)
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[8]/div/select',1);
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[8]/div/select/option[1]', 0);
		//4.7 FORMATO NÚMERICO (COMBO DE SELEÇÃO): SELECIONAR (2 casas decimais)
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[10]/div[1]/select',1);
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[10]/div[1]/select/option[3]',1);
		//4.8 UTILIZAR VÍRGULA COMO SEPARADOR DECIMAL (CAIXA DE SELEÇÃO): SELECIONAR
		clickXpath('//*[@id="collapseTwo"]/div/config-extracao/div[10]/div[2]/div[1]/label/input',1);
	}

	public static void fluxoEditarColunas(){
		//5.1 CONFIGURAR COLUNAS DO ARQUIVO DE SAÍDA (CAIXA DE SELEÇÃO): SELECIONAR
		clickXpath('//*[@id="headingThree"]/h4/a',2);
		clickXpath('//*[@id="collapseThree"]/div/div[1]/div/div/label/input',2);
		//5.1.1 FORMATO DO CABEÇALHO (COMBO DE SELEÇÃO): SELECIONAR (Formato Crivo 4.5)
		clickXpath('//*[@id="collapseThree"]/div/div[2]/div[2]/select',15);
		clickXpath('//*[@id="collapseThree"]/div/div[2]/div[2]/select/option[1]',2);
		//5.1.2 UTILIZAR ARQUIVO DE CONFIGURAÇÃO: ESCOLHER ARQUIVO (OPÇÃO PARA SELECIONAR UM ARQUIVO NA REDE COM LAYOUT JÁ DEFINIDO DE COLUNAS).
		//5.1.2.1 ACESSAR A PASTA DE REDE PARA INSERIR O ARQUIVO COM LAYOUT JÁ DEFINIDO.
		//CAMINHO DE REDE: \\ntxnas1.intrallianz.es\Brasil\DIRETORIAS\DIR EXE SINISTROS\DESENV_ESTRATEGICO\BUSINESS_INTELLIGENCE\Relatorios_Gerenciais\Projetos\Rotinas\Diario\Operacoes_Crivo\config_filtro
		//OBS.: PASTA DE REDE DEPARTAMENTAL (SOLICITAR LIBERAÇÃO DE ACESSO)
		//5.1.2.2 SELECIONAR ARQUIVO DE LAYOUT: (colunas_dash.json)
		//(O SISTEMA IRÁ SELECIONAR OS FILTROS DE COLUNAS AUTOMATICAMENTE COM BASE NO ARQUIVO INSERIDO).
		//5.1.3 PESQUISAR: (NÃO PREENCHER – “Vazio”)
		//5.1.4 COMBO DE SELEÇÃO: (Selecionadas / Não Selecionadas)
		//5.1.5 COMBO DE SELEÇÃO: (“Vazio”)
		//5.1.6 INICIAR (BOTÃO): CLICAR
	}


	/**
	 * @author ALZ_ServiceQA
	 * @return
	 * Finaliza processo após verifica se todas as pendências foram enviadas, caso não e retornado um 'false'
	 */
	public static boolean finalizaProcesso(){
		//System.err.println('finaliza Processo - countEnviado ['+countEnviado+'] tamanhoListaOriginal ['+tamanhoListaOriginal+']');
		boolean processoFinalizado = false;
		finalizar();
		//if(countEnviado == tamanhoListaOriginal){
		diretorio = createDiretory('Evidencias//SegundaVia - '+dataRecuerada);
		//	copiarExcel(ExcelCopiado, diretorio+"//SegundaVia Processado-"+dataRecuerada+".xlsx");
		//copiarExcel(ExcelCopiado, '\\x:\\SegundaVia Processado-'+dataRecuerada+'.xlsx');
		//	deletarArquivosExcel(diretorioToDelete);
		deleteFileDiretory(caminhoDiretorioExcel);
		processoFinalizado = true;
		return processoFinalizado;
	}


	public static void editarUsuarios(){
		clickXpath('//*/filtro[5]/div/div[2]/div/div/div[1]/div/div/input', 1)
		clickXpath('//*[@id="ui-select-choices-row-5-2"]/a', 1)
	}


	public static void consutaGerais(){
		alteraJanejaGenerica(0, 5);
		clickXpath('//*[@id="topicNav"]/li[2]/a', 1)
	}
	
	
	public static void sobeArquivo(){		
//	
	//	Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
	//	Runtime.getRuntime().exec("wscript.exe " + file.getPath())
		
		
	//	String script = "C:\\work\\selenium\\chrome\\test.vbs";
	// search for real path:
//	String executable = "C:\\windows\\...\\vbs.exe";
//	String cmdArr [] = {executable, script};	
//	Runtime.getRuntime ().exec (cmdArr);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//

	//	/**
	//	 * @author ALZ_ServiceQA
	//	 * @param processoFinalizado
	//	 * @param tipoAba
	//	 * @return
	//	 * Processa as falhas de envio de pendências ao final do processo, caso ocorra mais falha o processo é re-feito até concluir
	//	 */
	//	public static processaFalhas(boolean processoFinalizado, String tipoAba){
	//		System.err.println('processa Falhas - processoFinalizado ['+processoFinalizado+'] tipoAba ['+tipoAba+']')
	//		if(processoFinalizado==false){
	//			tamanhoListaOriginal = 0;
	//			/**
	//			 * Nova tentativa
	//			 */
	//			listaExcel = switchLeitorExcel.switchExcel(ExcelCopiado, tipoAba);
	//			iniciaFluxo(tipoAba)
	//			fluxoSegundaVia(listaExcel, tipoAba);
	//			/**
	//			 * 'tamanhoListaOriginal' recebe tamanho da nova lista com pendencias com falha
	//			 */
	//			tamanhoListaOriginal = listaExcel.size()
	//			/**
	//			 * Chamada racursiva para re-processar falhas
	//			 */
	//			processaFalhas(finalizaProcesso(), tipoAba);
	//		}
	//	}


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
			//			listaExcel = switchLeitorExcel.switchExcel(path1, tipoAba);
			//			if(listaExcel.size()==0){
			List<String> anexos = new ArrayList<>();
			anexos[0] = path1;
			anexos[1] = nome1;
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba )
			//}


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

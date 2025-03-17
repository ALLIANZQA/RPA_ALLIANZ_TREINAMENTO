package backoffice

import static org.junit.Assert.*

import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.leitorPdf
import bean.MassaDados
import bean.Pendencia
import rpacommands.rpaCommands
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import auxiliares.ExtractDates
import auxiliares. MenuMatriz

public class backofficeFuncionalidades extends rpaCommands {

	static String caminhoEntrada = "C:\\Users\\Public\\fichero.pdf";
	static String apolice;
	static String tomador;
	static String caminhoSaida;
	static String caminhoPasta;
	static String assegurado;
	static String user;
	static String senha;
	static String valueCheck;
	static String nomePdfApolice;
	static String nomePdfBoleto;
	public static String botaoRenovacao
	static String alerta


	/**
	 * iniciaAplicacaoMatriz
	 * @author T_DVLopes
	 */
	public static void iniciaAplicacaoMatriz(String url){
		try{
			abreBrowser(url);
			valueCheck = 'enviado';
			valueCheckFuncionarioAutoResidencia = 'Processado';
			selecionarApolice = true;
		} catch (Exception e) {
			System.err.println('Falha ao iniciar APlicação Tela Matriz' + e);
			finalizar();
		}
	}

	/**
	 * realizaLoginMatrizProdução
	 * @author T_DVLopes
	 */
	public static void realizaLoginMatrizProdução(String nomeArquivo){
		try{
			auxiliares.UserPassword.getUsuarioSenha(nomeArquivo)
			realizaLoginTelaMatriz(auxiliares.UserPassword.getUser(),auxiliares.UserPassword.getSenha());
		} catch (Exception e) {
			System.err.println('Falha ao realizar Login MatrizProdução' + e);
			finalizar();
		}
	}

	/**
	 * realizaLoginMatrizProdução
	 * @author T_DVLopes
	 */
	public static void realizaLogin(String nomeArquivo){
		try{
			auxiliares.UserPassword.getUsuarioSenha(nomeArquivo)
			realizaLoginTelaMatriz(auxiliares.UserPassword.getUser(),auxiliares.UserPassword.getSenha());
		} catch (Exception e) {
			System.err.println('Falha ao realizar Login MatrizProdução' + e);
			finalizar();
		}
	}


	/**
	 * realizaLoginAllianzNet
	 * Recebe por parametro nome da aba 'excel' localiza qual user/pword utilizar e realiza login
	 * @author T_DVLopes
	 */
	public static void realizaLoginAllianzNet(String nomeArquivo){
		try{
			auxiliares.UserPassword.getUsuarioSenha(nomeArquivo)
			realizaLoginTelaAllianzNet(auxiliares.UserPassword.getUser(),auxiliares.UserPassword.getSenha());
		} catch (Exception e) {
			System.err.println('Falha ao realizar Login MatrizProdução' + e);
			finalizar();
		}
	}



	/**
	 * realizaLoginAllianzNet INTRANET
	 * Recebe por parametro nome da aba 'excel' localiza qual user/pword utilizar e realiza login
	 * @author T_DVLopes
	 */
	public static void realizaLoginIntraNetProducao(String nomeArquivo){
		try{
			auxiliares.UserPassword.getUsuarioSenha(nomeArquivo)
			realizaLoginTelaIntraNetProducao(auxiliares.UserPassword.getUser(),auxiliares.UserPassword.getSenha());
		} catch (Exception e) {
			System.err.println('Falha ao realizar Login MatrizProdução' + e);
			finalizar();
		}
	}



	/**
	 * Realiza login CRIVO
	 * Recebe por parametro nome da aba 'excel' localiza qual user/pword utilizar e realiza login
	 * @author T_DVLopes
	 */
	public static void realizaLoginCrivoIntraNet(String nomeArquivo){
		try{
			auxiliares.UserPassword.getUsuarioSenha(nomeArquivo)
			realizaLoginCrivo(auxiliares.UserPassword.getUser(),auxiliares.UserPassword.getSenha());
		} catch (Exception e) {
			System.err.println('Falha ao realizar Login MatrizProdução' + e);
			finalizar();
		}
	}


	/**
	 * fluxoGestaoProcessos
	 * @author T_dvlopes
	 */
	public static void fluxoGestaoProcessos(){
		try{
			clicaNoMenuTelaMatriz("Consultas & Estatísticas");
			clicaNoSubMenuTelaMatriz("Consultas");
			clicaNaSubOpcao("Consultas Gerais");
		} catch (Exception e) {
			System.err.println('Falha ao realizar Fluxo Gestão Pocessos' + e);
			finalizar();
			deletarArquivos();
			valueCheck = 'Não enviado';
		}
	}

	/**fluxoFichaGestao
	 *
	 */
	public static void fluxoFichaGestao(){
		try{
			clicaNoMenuTelaMatriz("Consultas & Estatísticas");
			clicaNoSubMenuTelaMatriz("Arquivo Digital");
			clicaNaSubOpcao("Ficha de Gestão");
		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}


	/**fluxoFichaGestao
	 *
	 */
	public static void fluxoGestaoClientes(){
		try{
			clicaNoMenuTelaMatriz("Gestão & Marketing");
			clicaNoSubMenuTelaMatriz("Mediadores/Clientes");
			clicaNaSubOpcao("Gestão Clientes");
		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}

	public static void fluxoConsultasGerais(){
		try{
			clicaNoMenuTelaMatriz("Consultas & Estatísticas");
			clicaNoSubMenuTelaMatriz("Consultas");
			//clicaNaSubOpcao("Consultas Gerais");
		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}


	public static void fluxoCompletoConsultasGerais(){
		try{
			UpdateStatusNaoEnviado();
			clicaNoMenuTelaMatriz("Consultas & Estatísticas");
			clicaNoSubMenuTelaMatriz("Consultas");
			//clicaNaSubOpcao("Consultas Gerais");
			////*[@id="topicNav"]/li[2]/a
			clickXpath('//*[@id="topicNav"]/li[2]/a', 3)
		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}

	/**fluxoFichaGestao
	 *
	 */
	public static void fluxoProducao(){
		try{
			clicaNoMenuTelaMatriz("Producao & Sinistros");
			clicaNoSubMenuTelaMatriz("Produção Diversos");
			//clicaNaSubOpcao("Produção");
			//clickXpath('//*[@id="110"]', 2)
			//clickXpath('//*[@id="topicNav"]/li[4]/a', 2)
			clickXpath('//*[@id="topicNav"]/li[6]/a', 2)

		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}


	public static String recuperarTipoSeguro(){
		try{
			String x = recuperaValor('//*[@id="detalhesTableSusep_tbody"]/tr/td','textContent' , 2);
			println x;
			println x;
			return x;
		} catch (Exception e) {
		}

		return null;
	}


	public static String retornaTipoRenovação(String quantidade){
		String tipoRenovacao
		if(quantidade.equals('0')){
			tipoRenovacao = "Renovação Interna sem Sinistro"
		}else{
			tipoRenovacao = "Renovação Interna com Sinistro"
		}

		return tipoRenovacao;
	}





	/**
	 * fluxoCotacoesEspeciais
	 */
	public static void fluxoCotacoesEspeciais(){
		try{
			clicaNoMenuTelaMatriz("Producao & Sinistros");
			clicaNoSubMenuTelaMatriz("Produção Diversos");
			//clicaNaSubOpcao("Cotações especiais");
			clickXpath('//*[@id="topicNav"]/li[4]/a', 15)
		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}



	/**Realiza a pesquisa do numero recuperado e realiza o fluxo para a tela GESTÃO PROCESSOS
	 * @param valor
	 */
	public static void pesquisarValorRecuperado(valor){
		try{
			preencheXpath('//*[@id="claveSolicitud"]', valor, 10);
			clickXpath('//*[@id="botonBuscar"]', 1);
			clickXpath('//*[@id="tblList_0_10"]', 1);
			trocaJanela('Detalhe Pedido');
			clickXpath('//*[@id="botonFicha"]', 1);
		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}


	/**
	 * FLUXO CLICA EM NOTA AGENDA
	 * ALTERA JANEJA
	 * PREENCHE CAMPO COM VALOR 175
	 * CLICA EM ACEITAR
	 * @author T_DVLOPES
	 */
	public static void fluxoPreencherModeloDescricao(String CD_RAMO_PLATAFORMA ){
		try{
			WebUI.maximizeWindow()
			preencheXpath('//*[@id="numModCodi"]', notaAGCS(CD_RAMO_PLATAFORMA), 2);
			//clica aceitar
			clickXpath('//*[@id="btnAceptar"]', 1);
		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}

	/**
	 * @author be54955
	 * @param nota
	 * @return
	 */
	public static String notaAGCS(String nota){
		String n
		if( nota.equals(ramoAGCS(nota)) ){
			n="648"
		} else {
			n="175"
		}
		return n
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param ramo
	 * @return
	 */
	public static String ramoAGCS(String ramo){
		Map<String, String> hashRamo = new HashMap<String, String>()
		hashRamo.put('337','337');
		hashRamo.put('336','336');
		hashRamo.put('329','329');
		hashRamo.put('335','335');
		hashRamo.put('339','339');
		hashRamo.put('338','338');
		return hashRamo.get(ramo.toString());
	}


	/**
	 * alteraJanelaGestaoProcessos
	 * @author T_dvlopes
	 */
	public static void alteraJanelaCriteriosPesquisa(){
		try{
			trocaJanela('Critérios Pesquisa');
		} catch (Exception e) {
			System.err.println('falha ao alterar Janeja Criteris Pesquisa '+e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}



	/**fluxoDownloadApoliceBoleto
	 *
	 *
	 * @author T_dvlopes
	 */
	public static void fluxoDownloadApoliceBoleto(String nApolice){
		pesquisaNumeroApolice(nApolice);
		recuperaNomeTomador();
		downloadApolice();
		if(enviarApolice){
			fluxoRenomearArquivoApolice();
		}
		downloadBoleto();
		if(enviarBoleto){
			fluxoRenomearArquivoBoleto();
		}
	}

	public static void fluxoDownloadApoliceBoleto2(){
		downloadApolice();
		if(enviarApolice){
			fluxoRenomearArquivoApolice();
		}
		downloadBoleto();
		if(enviarBoleto){
			fluxoRenomearArquivoBoleto();
		}
	}

	/**pesquisaNumeroApolice
	 *
	 */
	public static void pesquisaNumeroApolice(String nApolice){
		trocaJanela('Consultas & Estatísticas');
		clicaNaSubOpcao("Consultas Gerais");
		trocaJanela('Critérios de Pesquisa');
		preencheXpath('//*[@id="apolice"]', nApolice, 5);
		clickXpath('//*[@id="o_10"]', 0);
	}







	/**pesquisaNumeroApoliceSusep
	 *Pesquisa numero
	 *Clica na opção Sim
	 *Clica em pesquisar
	 */
	public static void pesquisaNumeroApoliceSusep(String nApolice){
		trocaJanela('Critérios de Pesquisa');
		//	preencheXpath('//*[@id="apoliceSusep"]', nApolice, 5);
		preencheXpathSetText('//*[@id="apoliceSusep"]', nApolice, 5);
		clickXpath('//*[@id="relacaoAplicas"]', 0);
		clickXpath('//*[@id="relacaoAplicas"]/option[1]', 1);
		clickXpath('//*[@id="o_10"]', 2);
	}


	/** Realiza o fluxo consultas e Estatisticas
	 * Realiza a pesquisa da apolice
	 * Realiza a seleção da opção sim
	 * Realiza o click em pesquisar
	 * @param nApolice
	 */
	public static void FluxoPesquisaNumeroApoliceTelaConsultasGerais(String nApolice, String ramo){
		try{
			System.err.println(nApolice +" - "+ramo)
			String ramoAQUI = ramo.trim();
			
			System.out.println("\nRAMOS "+ramoAQUI );
			//			trocaJanela('Critérios de Pesquisa');
			WebUI.delay(10);
			alteraJanejaGenerica(1, 5)
			WebUI.maximizeWindow()
			preencheXpath('//*[@id="apolice"]', nApolice, 5);
			clickXpath('//*[@id="relacaoAplicas"]', 2);
			//if(ramoAQUI.equals('307' || '308'|| '309'|| '310'|| '311'|| '312'|| '329'|| '335'|| '336'|| '337'|| '338'|| '339')){
			if( ramoAQUI.equals('307') || 
				ramoAQUI.equals('308') ||
				ramoAQUI.equals('309') || 
				ramoAQUI.equals('310') || 
				ramoAQUI.equals('311') ||
				ramoAQUI.equals('312') ||
                ramoAQUI.equals('329') || 
				ramoAQUI.equals('335') || 
				ramoAQUI.equals('336') || 
				ramoAQUI.equals('337') || 
				ramoAQUI.equals('338') || 
				ramoAQUI.equals('339')){
				clickXpath('//*[@id="relacaoAplicas"]/option[1]', 10);
			}else{
				clickXpath('//*[@id="relacaoAplicas"]/option[2]', 10);
			}
			clickXpath('//*[@id="o_10"]', 10);
		} catch (Exception e) {
			System.err.println('Falha ao Consultar Apolice' + e);
			finalizar();
			valueCheck = 'Não enviado [Falha ao Consultar Apolice]';
		}
	}


	/**
	 * @author t_dvlopes
	 */
	public static void clicarRecibosFrota(String ramo){
		try{
			String ramoAQUI = ramo.trim();
			//if(ramo.equals('307' || '308'|| '309'|| '310'|| '311'|| '312'|| '329'|| '335'|| '336'|| '337'|| '338'|| '339')){
			if( ramoAQUI.equals('307') || 
				ramoAQUI.equals('308') ||
				ramoAQUI.equals('309') || 
				ramoAQUI.equals('310') || 
				ramoAQUI.equals('311') ||
				ramoAQUI.equals('312') || 
				ramoAQUI.equals('329') || 
				ramoAQUI.equals('335') || 
				ramoAQUI.equals('336') || 
				ramoAQUI.equals('337') ||
				ramoAQUI.equals('338') || 
				ramoAQUI.equals('339')){
				try{
					//clickXpath('//*[@id="o_8"]',5);
					selecionaItemListaContains('Recibos Frota','//*[@id="sectionaplicacoesapoliceSectionBar"]/table/tbody/tr' , 'div' )
				} catch (Exception e){}
				try{
					clickXpath('//*[@id="sectionaplicacoesapoliceSectionBar"]/table/tbody/tr/td/table/tbody/tr/td[1]/div',5);
				} catch (Exception e){}

			}else{
				clickXpath('//*[@id="listarecibos"]',5);
			}
		} catch (Exception e) {
			//clickXpath('//*[@id="o_8"]',5);
		}
	}

	/**
	 * @author t_dvlopes
	 */
	public static void clicarBotaoResultado(){
		//		try{
		//		  clickXpath('//*[@id="-3"]/div[1]',10);
		//		} catch (Exception e){}

		try{
			selecionaItemListaContains('Resultado','//*[@id="menuLateral"]' , 'span' )
		} catch (Exception e){}
	}



	public static String recuperarQuantidadePendencia (String ramoAqui){
		String returnValuer;
		qtdPendenciaRecuperada = recuperarPendenciaTelaListagemRecibos(ramoAqui, 20);
		returnValuer = qtdPendenciaRecuperada;
		System.out.println("Pendências recuperadas ["+returnValuer+"]")
		return returnValuer;
	}


	/**recuperaNomeTomador
	 *
	 */
	public static void recuperaNomeTomador(){
		try{
			tomador = recuperaValor('//*[@id="dadosgeraisContent"]/table[1]/tbody/tr/td[2]', 'outerHTML', 5);
			tomador = cortavalor(tomador);
		} catch (Exception e) {
			System.err.println('Falha ao Recuperar Nome Tomador' + e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}


	/**Fluxo realiza download apolice
	 *
	 */
	public static void ClicarBotaoDownload(){
		try{
			//Clica sub menu Ficha\\
			clickXpath('//*[@id="-61"]/div[1]', 0);
			fecharJanelaDownload();
			trocaJanela('Gestão Processos');
		} catch (Exception e) {
			System.err.println('Falha ao Realizar flux Download "APOLICE"' + e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}


	/**Fluxo realiza download apolice
	 *
	 */
	public static void downloadApolice(){
		try{
			//Clica sub menu Ficha\\
			clickXpath('//*[@id="-61"]/div[1]', 0);
			fecharJanelaDownload();
			trocaJanela('Gestão Processos');
			downloadPDFApolice('Apólice, Endosso ...');
		} catch (Exception e) {
			System.err.println('Falha ao Realizar flux Download "APOLICE"' + e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}


	/**downloadBoleto
	 * Troca janela e realiza download PDF BOLETO
	 */
	public static void downloadBoleto(){
		try{
			downloadPDFBoleto('Recibos boletos');
		} catch (Exception e) {
			System.err.println('Falha ao Realizar flux Download "BOLETO"' + e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}

	public static String realizaDownloadApoliceSeguroFuncionario(){
		//		int contMax = 10;
		System.err.println()
		System.err.println('realizaDownloadApoliceSeguroFuncionario' );
		String downloadOK;
		String element;
		try{
			element = validarElemento('//*[@id="idPoliza_0_12"]','onclick',1);
			validaErro = element
			println element;
			if(element.equals(null)){

				element=''
				element = validarElemento('//*[@id="idPoliza_0_11"]','onclick',1);
				System.err.println(element)
				validaErro = element

				if(!element.equals(null)){
					validaErro=""
					//clica na lupa
					clickXpath('//*[@id="idPoliza_0_11"]',7);
					WebUI.delay(5);
					if(identificaBotaoRenovar()){
						downloadOK = 'erroBotaoRenovar'
					}

					downloadOK = realizaDownloadApoliceSeguroFuncionarioAutoLupa();
					System.err.println("downloadOK ["+downloadOK+"]")
					if(downloadOK.equals('erro')){
						downloadOK = 'erroChassi'
						return downloadOK;
					}else{
						downloadOK = 'true'
					}
				} else {
					downloadOK = 'erroLupa'
					return downloadOK;
				}

			}else{
				clickXpath('//*[@id="idPoliza_0_12"]',1);
				//GeneracionPdf
				WebUI.delay(10);
				downloadOK = validaDownload('C:\\Users\\Public\\GeneracionPdf.PDF','//*[@id="idPoliza_0_12"]');
				if(downloadOK.equals('true')){
					renomearArquivo('C:\\Users\\Public\\GeneracionPdf.PDF' ,'RenovAuto');
				}
				AlteraAbDownloadAuto();
				println downloadOK;
			}

			return downloadOK;
		} catch (Exception e) {
			downloadOK = 'false';
			valueCheckFuncionarioAutoResidencia = 'Não enviado - [Erro Ao realizar Download APOLICE]';
			return downloadOK;
		}
		System.err.println("realizaDownloadApoliceSeguroFuncionario - validaErro ["+validaErro+"]")
		return downloadOK;
	}


	public static String realizaDownloadApoliceSeguroFuncionarioAutoLupa(){
		String downloadOK = '';
		String validaMensagemErro = ''
		try{
			// RENOVAR COM ALTERAÇÃO
			clickXpath('//*[@id="divContentFooterButtonBar"]/div[3]/button',10);
			fechaAlerta();
			alteraJanejaGenerica(1, 15)
			WebUI.maximizeWindow()

			//
			//			fechaAlerta();
			//
			//
			//
			//			//altera aba
			//			AlteraAbDownloadAutoLupa();
			//			WebUI.delay(30);

			//Plano 3
			clickXpath('//*[@id="Plano 3"]',10);
			//Clicar Avançar
			clickXpath('//*[@id="NextButton"]',20);
			WebUI.delay(10);
			validaMensagemErro = sidentificaAlerta("//*[@id='divContentErrorSection']/div/div/div", "p" )
			System.err.println(validaMensagemErro)

			if(validaMensagemErro.equals("false")){

				//SALVAR PDF
				clickXpath('//*[@id="saveStudyIcon"]',20);


				//			//VALIDA ERRO Avançar
				//validaMensagemErro = validarElementoErro('//*[@id="divContentErrorSection"]/div/div/div','textContent',8);
				//println validaMensagemErro
				//			//clicar Imagem pdf
				//			clickXpath('//*[@id="saveStudyIcon"]',5);


				//clicar download
				clickXpath('//*[@id="b_generatePDF"]',5);
				WebUI.delay(35);
				downloadOK =  validaDownloadTamanho('C:\\Users\\Public\\retrievedoc.PDF', '//*[@id="b_generatePDF"]');
				if (downloadOK.equals('true')){
					renomearArquivo('C:\\Users\\Public\\retrievedoc.PDF' ,'RenovAuto');
				}
				FecharAbDownloadAutoLupa();
				WebUI.delay(10);
				return downloadOK;
			} else {
				downloadOK = 'erro'
				return downloadOK;

			}

		} catch (Exception e) {
			if(validaMensagemErro.contains("  Placa do veículo ou chassis inválido.")){
				valueCheckFuncionarioAutoResidencia = validaMensagemErro;
				downloadOK = 'erro'
				return downloadOK;
			}else{
				valueCheckFuncionarioAutoResidencia = 'Não enviado - [Erro Ao realizar Download APOLICE]';
			}
		}

	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static String sidentificaAlerta(String xpath, String tag){
		String alerta = "false"
		try{
			StringBuilder strb = new StringBuilder()
			WebUI.delay(2)
			selecionaListaDeElementos(xpath,tag)
			if(listOfElements.size() > 0){
				for(int i = 0; i < listOfElements.size(); i++){
					System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
					strb.append(listOfElements[i].getText()+" ")
				}
				alerta = strb.toString()
			} else {
				alerta = "false"
			}
		} catch (Exception e){
			alerta = "false"
		}
		return alerta
	}





	/**
	 * **
	 *
	 * @return
	 */
	public static String realizaDownloadApoliceSeguroFuncionarioResidencia(){
		System.out.println()
		String downloadOK = '';
		String ramoAntigo = '';
		botaoRenovacao = ''
		try{
			WebUI.delay(5);

			//Luoa
			clickXpath('//*[@id="o_2"]',5);
			WebUI.delay(5);
			if(identificaBotaoRenovar()){
				botaoRenovacao =' Botão "Renovar com alteração" indisponível '
			}

			ramoAntigo = validarElemento('//*[@id="body_sectionPaginaCompleta"]/div/div[5]/div/div/div/div/button','textContent',2);
			println ramoAntigo;

			if(!ramoAntigo.contains('Residência Digital'.toString())){

				try{
				//ramo
				clickXpath('//*[@id="body_sectionPaginaCompleta"]/div/div[5]/div/div/div/div/button',3);
				} catch (Exception e){}
				
				try{
				//residencia
				clickXpath('//*[@id="body_sectionPaginaCompleta"]/div/div[5]/div/div/div/div/div/ul/li[2]/a/span[1]',0);
				} catch (Exception e){}
				
				//clica Renovar com alteração
				clickXpath('//*[@id="divContentFooterButtonBar"]/div[3]/button',5);
				WebUI.delay(10);
				fechaAlerta();
				WebUI.delay(20);
				//NOVA TELA
				alteraJanejaGenerica(1, 2)
				WebUI.maximizeWindow()
				//Avançar - Dados Pessoais
				try{
				clickXpath('//*[@id="nextPageRiskData"]',5);
				} catch (Exception e){}
				//Avançar
				clickXpath('//*[@id="nextPagePrice"]',5);

			}else {
				clickXpath('//*[@id="divContentFooterButtonBar"]/div[3]/button',5);
				WebUI.delay(10);
				fechaAlerta();
				//altera aba
				AlteraAbDownloadResidencia();
				WebUI.delay(20);
				//Clicar Avançar
				WebUI.maximizeWindow()
				try{
				clickXpath('//*[@id="nextPageRiskData"]',10);
			    } catch (Exception e){}
				clickXpath('//*[@id="nextPagePrice"]',5);
			}

			//Salvar cotação
			clickXpath('//*[@id="btnSaveQuotation"]',10);
			WebUI.delay(20);
			AlteraAbDownloadResidencia();
			//Baixar PDF
			clickXpath('//*[@id="btnGeneratePDF"]',10);

			WebUI.delay(35);
			System.out.println()
			downloadOK =  validaDownloadTamanho('C:\\Users\\Public\\docrequest.PDF', '//*[@id="btnSaveQuotation"]');
			System.err.println('downloadOK ['+downloadOK+"]")
			FecharAbDownloadResidencia();
			WebUI.delay(10);
			return downloadOK;
		} catch (Exception e) {
			valueCheckFuncionarioAutoResidencia = 'Não enviado - [Erro Ao realizar Download APOLICE]';
		}

		System.out.println()

		try{
			valueCheckFuncionarioAutoResidencia = ""
			//alteraJanejaGenerica(0, 2)
			//Renovar com alteração
			clickXpath('//*[@id="divContentFooterButtonBar"]/div[3]/button',5);
			WebUI.delay(10);
			//MSG
			try{
				fechaAlerta()
			}catch (Exception e){}

			//NOVA TELA
			alteraJanejaGenerica(1, 2)
			WebUI.maximizeWindow()
			//Avançar - Dados Pessoais
			clickXpath('//*[@id="nextPageRiskData"]',5);
			// Verifica se o tipo de Assistência desejada está selecionada
			verificaTipoDeAssistência("VIP")
			//Avançar
			clickXpath('//*[@id="nextPagePrice"]',5);
			WebUI.delay(20);
			//Salvar cotação
			clickXpath('//*[@id="btnSaveQuotation"]',10);
			AlteraAbDownloadResidencia();
			//Baixar PDF
			clickXpath('//*[@id="btnGeneratePDF"]',10);

			WebUI.delay(20);
			downloadOK =  validaDownloadTamanho('C:\\Users\\Public\\docrequest.PDF', '//*[@id="btnSaveQuotation"]');
			System.err.println('downloadOK ['+downloadOK+"]")
			FecharAbDownloadResidencia();
			WebUI.delay(10);
			return downloadOK;
		} catch (Exception e){
			valueCheckFuncionarioAutoResidencia = 'Não enviado - [Erro Ao realizar Download APOLICE]';
		}
	}

	/**
	 * @author BE54955
	 * @param assistencia
	 * @return
	 */
	public static verificaTipoDeAssistência(String assistencia){
		System.err.println()
		int index
		int numAssistencia
		int numeroBotoes
		boolean assistenciaSelecionada = false
		String xpath = '//*[@id="btnGroupAssistanceType"]'
		String tag = "button";
		List<WebElement> listOfElements
		listOfElements = listaDeElementos(xpath,tag)
		numeroBotoes = listOfElements.size()
		//Verifica a existência da assistência selecionado
		if(existeAssistencia(listOfElements,assistencia)){
			System.err.println()
			//Retorna o INDEX da assistência selecionada
			for(int j = 0; j < listOfElements.size(); j++){
				if(listOfElements[j].getText().toString().equals(assistencia)){
					System.err.println("FOUND ["+j+"] ["+listOfElements[j].getText()+"]")
					index = j
					break;
				}
			}
			//Identificar a assistência selecionada que não seja indicada no método
			for(int i = 0; i < listOfElements.size(); i++){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"] ["+listOfElements[i].getAttribute('class')+"]")
				if(listOfElements[i].getAttribute('class').toString().contains('active')){
					assistenciaSelecionada = true
					break;
				}
			}
			//Seleciona a assistência indicada no método
			if(!assistenciaSelecionada){
				for(int k = 0; k < listOfElements.size(); k++){
					if(listOfElements[k].getText().toString().equals(assistencia)){
						listOfElements[k].click();
						break;
					}
				}
			}
		}
	}

	/**
	 * @author BE54955
	 * @param listOfElements
	 * @param assistencia
	 * @return
	 */
	public static boolean existeAssistencia(List<WebElement> listOfElements, String assistencia){
		boolean a = false
		for(int j = 0; j < listOfElements.size(); j++){
			if(listOfElements[j].getText().toString().equals(assistencia)){
				a = true
				System.err.println("FOUND ["+j+"] ["+listOfElements[j].getText()+"] ["+a+"]")
				break;
			}
		}
		return a;
	}

	public static void edicaoCalculo(){
		try{
			//Clica edição de calculo
			clickXpath('//*[@id="heading_sectionPaginaCompleta"]/a/div',5);
			//Clica CAP %
			clickXpath('//*[@id="cb_calculo"]/div/div[2]/div/div/div/div/div',1);
			//Seleciona CAP %
			clickXpath('//*[@id="cb_calculo"]/div/div[2]/div/div/div/div/div/div/ul/li/a',1);
			//PreencherAgravo
			deletaCampoXpath('//*[@id="appealPercentage"]', 1,5);
			preencheXpath('//*[@id="appealPercentage"]','0,00',1);
			//Preencher Comição
			deletaCampoXpath('//*[@id="comboComissao"]', 1,5);
			preencheXpath('//*[@id="comboComissao"]','1,0',1);
			//Clica botao recalcular
			commandTAB('//*[@id="comboComissao"]');

			String recalcular2 = recuperaValor('//*[@id="btnRecalcular2"]', 'disabled', 1)

			println recalcular2

			if(recalcular2.equals(null)){
				println 'null'
				clickXpath('//*[@id="btnRecalcular2"]',1);
			}
		} catch (Exception e) {
			println 'deu ruim!'
			valueCheckFuncionarioAutoResidencia = 'Não enviado - [Falha ao editar Calculo]';
		}

	}



	public static void fluxoRenomearArquivo(String nomeArquivo){
		try{
			//
			String path = 'C:\\Users\\Public\\GeneracionPdf.PDF';
			String pathParaAlterar = 'C:\\Users\\Public\\'+nomeArquivo+'.PDF';
			leitorPdf.renamefileGenerico(path, pathParaAlterar);
			deletarPDFGenerico(path);
		} catch (Exception e) {
			System.err.println('Falha ao realizar Fluxo renomear ARQUIVO "APOLICE"' + e);
			finalizar();
			valueCheck = 'Não enviado Arquiv Não renomeado';
		}
	}



	/**fluxoRenomearArquivoApolice
	 * Renomeia pdf fichero Para Nome e  tomador e Tipo de PDF {APOLICE}
	 */
	public static void fluxoRenomearArquivoApolice(){
		try{
			nomePdfApolice = tomador+" - Apolice";
			nomePdfApolice = nomePdfApolice.replace("/", "-").replace(".", "-");
			leitorPdf.renamefile('C:\\Users\\Public\\', nomePdfApolice);
			println nomePdfApolice;
			deletarPDF();
		} catch (Exception e) {
			System.err.println('Falha ao realizar Fluxo renomear ARQUIVO "APOLICE"' + e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}

	/**
	 * Renomeia pdf fichero Para Nome e  tomador e Tipo de PDF {BOLETO}
	 */
	public static void fluxoRenomearArquivoBoleto(){
		try{
			nomePdfBoleto = tomador+" - Boleto";
			nomePdfBoleto = nomePdfBoleto.replace("/", "-").replace(".", "-");
			leitorPdf.renamefile('C:\\Users\\PUBLIC\\', nomePdfBoleto);
			println nomePdfBoleto;
			deletarPDF();
		} catch (Exception e) {
			System.err.println('Falha ao realizar Fluxo renomear ARQUIVO "BOLETO"' + e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}


	public static void renomearExcel(String caminhoExcel){
		try{
			String nomeExcel = " - Boleto";
			leitorPdf.renamefileExcel(caminhoExcel, nomeExcel);
			//deletarPDF();
		} catch (Exception e) {
			System.err.println('Falha ao realizar Fluxo renomear ARQUIVO "BOLETO"' + e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}


	/**fluxoFichaGestao
	 *
	 */
	public static void fluxoSinistroProduto(){
		try{
			clicaNoMenuTelaMatriz("Producao & Sinistros");
			clicaNoSubMenuTelaMatriz("Produção Diversos");
			clicaNaSubOpcao("Produção");

		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}



	/**criandoDiretorio Assegurado
	 *
	 */
	public static void criandoDiretorio(String assegurado){
		try{
			caminhoSaida = createDiretory(assegurado);
			caminhoSaida = caminhoSaida.toString();
		} catch (Exception e) {
			System.err.println('Falha ao criar Diretorio "'+assegurado+'"' + e);
			finalizar();
			//	valueCheck = 'Não enviado';
		}
	}


	/**
	 * recebe por parametro e realiza copia do PDF
	 *
	 * caminhoEntrada
	 * caminhoSaida
	 */
	public static void transfereArquivo(){
		try{
			caminhoPasta = trasferirArquivo(caminhoEntrada, caminhoSaida);
		} catch (Exception e) {
			System.err.println('Falha ao trasnferir Arquivo' + e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}


	/**
	 * Localiza PDF FICHERO E DELETA
	 * @author T_dvlopes
	 */
	public static void deletarPDFGenerico(String path){
		try{
			deletaArquivoPdf(path);
		} catch (Exception e) {
			System.err.println('Falha ao deletar PDF"' + e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}


	/**
	 * Localiza PDF FICHERO E DELETA
	 * @author T_dvlopes
	 */
	public static void deletarPDF(){
		try{
			deletaArquivoPdf("C:\\Users\\Public\\fichero.PDF");
		} catch (Exception e) {
			System.err.println('Falha ao deletar PDF"' + e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}



	/**
	 * deleta arquivo PDF [FICHERO] no diretorio
	 */
	public static void limpezaFicheiro(){
		try{
			//Thread.sleep(1000)
			deletaArquivoPdf("C:\\Users\\Public\\fichero.PDF");
			deletaArquivoPdf("C:\\Users\\Public\\fichero (1).PDF");
		} catch (Exception e) {
			System.err.println('Falha ao deletar PDF"' + e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}


	/**
	 * deleta arquivo PDF [FICHERO] no diretorio
	 */
	public static void limpezaApoliceBoleto(){
		try{
			deletaArquivoPdf('\\C:\\Users\\Public\\'+nomePdfApolice+'.pdf');
			deletaArquivoPdf('\\C:\\Users\\Public\\'+nomePdfBoleto+'.pdf');
		} catch (Exception e) {
			System.err.println('Falha ao deletar PDF"' + e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}

	/**
	 * Localiza diretorio 'PASTA' e deleta
	 * @author T_dvlopes
	 */
	public static void deletarArquivos(String assegurado){
		try{
			//deletarPasta(caminhoPasta);
			deletarPasta(assegurado);
			println "Diretorio: C:\\Users\\Public\\"+ assegurado + "  -- Deletado com sucesso! --";
		} catch (Exception e) {
			System.err.println('Falha ao deletar Arquivo' + e);
			finalizar();
			//	valueCheck = 'Não enviado';
		}
	}


	public static void deletarArquivosExcel(String diretorio){
		try{
			println diretorio;
			deletarPastaExcel(diretorio);
			println "Diretorio: {"+diretorio+"}- Deletado com sucesso! --";
		} catch (Exception e) {
			System.err.println('Falha ao deletar Arquivo' + e);
			finalizar();
			//	valueCheck = 'Não enviado';
		}
	}




	public static String cortavalor(String valor){
		String valorCortado = valor.substring(53, 100).trim();
		return valorCortado;
	}


	/**Realiza a limpeza de PDF'S baixado como fichero
	 * @return
	 */
	public static fluxoLimpeza(){
		limpezaFicheiro();
	}




	/**
	 * @return
	 */
	public static finalizarGestaoProcessos(){
		fecharJanelaDownload();
	}


	/**Realiza fluxo para tela Nota Agenda Recebendo por parametro Numero da Apolice
	 * @param apolice
	 * @return
	 */
	public static String fluxoNotaAgenda(String apolice ){
		String validaErro;
		//trocaJanela('Gestão Processos');
		alteraJanejaGenerica(1, 2)
		if(selecionarApolice){
			println 'Selecionar tipo Apolice';
			selecionarOpcaoProcesso("P-APOLICE");
		}else{
			println 'Não seleciona tipo Apolice';
			//Clica APOLICE
			clickXpath('//*[@id="referencia"]', 0);
			deletaCampoXpath('//*[@id="referencia"]', 0, apolice.length());
		}
		//Preenche Apolice
		preencheXpath('//*[@id="referencia"]', apolice, 0);
		//Clica Pesquisar
		clickXpath('//*[@id="btBuscar"]', 1);
		validaErro = validarElementoErro('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','textContent',2);
		println validaErro;
		if (!validaErro.contains(' (9) 109-APÓLICE/APLICA INEXISTENTE0'.toString())){
			//Clica Nota/Agenda
			clickXpath('//*[@id="20"]/div[1]', 2);
		}else {
			validaErro = 'APLICA INEXISTENTE'
		}
		return validaErro;
	}

	/**Realiza fluxo para tela Nota Agenda Recebendo por parametro Numero da Apolice
	 * @param apolice
	 * @return
	 */
	public static String fluxoNotaAgendaInspecao(String apolice, String aplica ){

		String validaErro;
		//trocaJanela('Gestão Processos');
		//	alteraJanejaGenerica(1, 2)

		if(selecionarApolice){
			println 'Selecionar tipo Apolice';
			selecionarOpcaoProcesso("P-APOLICE");
		}else{
			println 'Não seleciona tipo Apolice';
			//Clica APOLICE
			clickXpath('//*[@id="referencia"]', 0);
			deletaCampoXpath('//*[@id="referencia"]', 0, apolice.length());
		}
		//Preenche Apolice
		limpaCampo('//*[@id="referencia"]', 1)
		preencheXpath('//*[@id="referencia"]', apolice, 0);
		//Preenche Aplica
		limpaCampo('//*[@id="aplica"]', 1)
		preencheXpath('//*[@id="aplica"]', aplica, 0);
		//Clica Pesquisar
		clickXpath('//*[@id="btBuscar"]', 1);
		validaErro = validarElementoErro('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','textContent',2);
		System.out.println("["+validaErro+"]")
		System.out.println()
		//	if (validaErro.contains(' (9) 109-APÓLICE/APLICA INEXISTENTE0'.toString()) || validaErro.contains(" (9) 109-POLIZA/APLICA INEXISTENTE0".toString())){
		if ( validaErro.contains('INEXISTENTE0') ){
			validaErro = 'APLICA INEXISTENTE'
		}else {
			//Clica Nota/Agenda
			clickXpath('//*[@id="20"]/div[1]', 2);
		}
		return validaErro;
	}

	/**Realiza fluxo para tela Nota Agenda Recebendo por parametro Numero da Apolice
	 * @param ramo
	 * @return
	 * Preenche campo / Clica botão aceitar / Clica em Gravar / "ATUALIZA VARIAVEL (VALUECHECK)PARA 'ENVIADO'"
	 *
	 */
	public static  fluxoEnviaAgendamentoInspecao(String ramo){

		trocaJanela('Editor de Notas/Agenda');
		//Preenche Campo
		preencheXpath('//*[@id="numModCodi"]', "590",0);
		//Clica no aceitar
		clickXpath('//*[@id="btnAceptar"]', 0);
		//Clica gravar
		clickXpath('//*[@id="o_11"]', 1);
		validarEnviado();
		selecionarApolice = false;
	}




	/**
	 * @param codigo
	 * @return
	 * Bloqueio de bonus - (1736)
	 * Bloqueio de Vistoria - (715 - 717 - 1701)
	 * Bloqueio de analise de vistoria - (1701)
	 * Bloqueio de auto Demais - (1702 - 1735 - 1768 - 1813)
	 * Bloqueio de mult - (714 - 1701 - 2279 - 8001 - 8002 - 8004 - 8013)
	 * Bloqueio Santander - (188010)
	 *
	 * nota 690, preciso que as proposta do ramo 1221
	 */
	public static boolean fluxoEnviarNotaAuto(String ramo, String corretor, String codigo){
		boolean bRamo = false
		System.err.println("Ramo ["+ramo+"] Corretor ["+corretor+"] Codigo ["+codigo+"]")

		trocaJanela('Editor de Notas/Agenda');

		Map<String, String> bloqueio = new HashMap<String, String>();

		if(corretor.equals("188010")){

			//Santander
			bloqueio.put("188010",'606');
			preencheXpath('//*[@id="numModCodi"]', bloqueio.get(corretor),2);
			System.err.println("Santander -" + bloqueio.get(corretor));
			bRamo = true

		}
		else if(ramo.equals('1211') || ramo.equals('1212') || ramo.equals('1213') || ramo.equals('1221')){

			//Bloqueio de Vistoria
			bloqueio.put("715",'602');
			bloqueio.put("717",'602');

			//Bloqueio de analise de vistoria
			bloqueio.put("1701",'603');

			//Bloqueio de auto Demais
			bloqueio.put("1702",'604');
			bloqueio.put("1735",'604');
			bloqueio.put("1768",'604');
			bloqueio.put("1813",'604');

			//Bloqueio de bonus
			bloqueio.put("1736",'600');

			bloqueio.put("8001",'604');
			bloqueio.put("8019",'604');
			bloqueio.put("8020",'604');
			bloqueio.put("8021",'604');

			//Genéricos
			bloqueio.put("1111",'638');
			bloqueio.put("5555",'653');
			bloqueio.put("6666",'654');
			bloqueio.put("7777",'655');
			bloqueio.put("8888",'656');
			bloqueio.put("9999",'690');

			preencheXpath('//*[@id="numModCodi"]', bloqueio.get(codigo),2);
			System.err.println("Auto -" + bloqueio.get(codigo));
			if(!bloqueio.get(codigo).equals(null)){
				bRamo = true
			}

		}else if(ramo.equals('1102') || ramo.equals('602') || ramo.equals('605') ){
			//Bloqueio de mult
			bloqueio.put("8001",'601');
			bloqueio.put("8002",'601');
			bloqueio.put("8003",'601');
			bloqueio.put("8004",'601');
			bloqueio.put("8013",'601');
			bloqueio.put("8019",'601');

			bloqueio.put("714",'601');
			bloqueio.put("716",'601');

			bloqueio.put("1140",'601');
			bloqueio.put("1143",'601');
			bloqueio.put("1575",'601');
			bloqueio.put("1652",'601');
			bloqueio.put("1701",'601');

			//Genéricos
			bloqueio.put("1111",'638');
			bloqueio.put("5555",'653');
			bloqueio.put("6666",'654');
			bloqueio.put("7777",'655');
			bloqueio.put("8888",'656');
			bloqueio.put("9999",'690');

			preencheXpath('//*[@id="numModCodi"]', bloqueio.get(codigo),2);
			System.err.println("Auto -" + bloqueio.get(codigo));
			if(!bloqueio.get(codigo).equals(null)){
				bRamo = true
			}
		}
		else if(ramo.equals('601') || ramo.equals('603')){
			//Bloqueio de mult
			bloqueio.put("8004",'851');
			bloqueio.put("8013",'851');

			bloqueio.put("8001",'851');
			bloqueio.put("8003",'851');

			//Genéricos
			bloqueio.put("1111",'638');
			bloqueio.put("5555",'653');
			bloqueio.put("6666",'654');
			bloqueio.put("7777",'655');
			bloqueio.put("8888",'656');
			bloqueio.put("9999",'690');

			preencheXpath('//*[@id="numModCodi"]', bloqueio.get(codigo),2);
			System.err.println("Auto -" + bloqueio.get(codigo));
			if(!bloqueio.get(codigo).equals(null)){
				bRamo = true
			}
		}
		else{
			//Bloqueio de mult
			bloqueio.put("714",'605');
			bloqueio.put("1701",'605');
			bloqueio.put("2279",'605');
			bloqueio.put("8001",'605');
			bloqueio.put("8002",'605');
			bloqueio.put("8004",'605');
			bloqueio.put("8013",'605');
			bloqueio.put("8021",'605');

			//Genéricos
			bloqueio.put("1111",'638');
			bloqueio.put("5555",'653');
			bloqueio.put("6666",'654');
			bloqueio.put("7777",'655');
			bloqueio.put("8888",'656');
			bloqueio.put("9999",'690');

			preencheXpath('//*[@id="numModCodi"]', bloqueio.get(codigo),2);
			System.err.println("Mult -" + bloqueio.get(codigo));
			if(!bloqueio.get(codigo).equals(null)){
				bRamo = true
			}

		}
		//Clica ACEITAR
		clickXpath('//*[@id="btnAceptar"]', 1);
		//Clica gravar

		clickXpath('//*[@id="o_11"]', 2);

		selecionarApolice = false;
		validarEnviado();

		return bRamo
	}


	/**
	 * @param codigo
	 * @return
	 * Bloqueio de bonus - (1736)
	 * Bloqueio de Vistoria - (715 - 717 - 1701)
	 * Bloqueio de analise de vistoria - (1701)
	 * Bloqueio de auto Demais - (1702 - 1735 - 1768 - 1813)
	 * Bloqueio de mult - (714 - 1701 - 2279 - 8001 - 8002 - 8004 - 8013)
	 * Bloqueio Santander - (188010)
	 *
	 * nota 690, preciso que as proposta do ramo 1221
	 */
	public static boolean validaNotaAuto(String ramo, String corretor, String codigo){
		boolean bRamo = false
		System.err.println("Ramo ["+ramo+"] Corretor ["+corretor+"] Codigo ["+codigo+"]")
		Map<String, String> bloqueio = new HashMap<String, String>();

		if(corretor.equals("188010")){

			//Santander
			bloqueio.put("188010",'606');
			System.err.println("Santander -" + bloqueio.get(corretor));
			bRamo = true

		}
		else if(ramo.equals('1211') || ramo.equals('1212') || ramo.equals('1213') || ramo.equals('1221')){

			//Bloqueio de Vistoria
			bloqueio.put("715",'602');
			bloqueio.put("717",'602');

			//Bloqueio de analise de vistoria
			bloqueio.put("1701",'603');

			//Bloqueio de auto Demais
			bloqueio.put("1702",'604');
			bloqueio.put("1735",'604');
			bloqueio.put("1768",'604');
			bloqueio.put("1813",'604');

			//Bloqueio de bonus
			bloqueio.put("1736",'600');

			bloqueio.put("8001",'604');
			bloqueio.put("8019",'604');
			bloqueio.put("8020",'604');
			bloqueio.put("8021",'604');

			//Genéricos
			bloqueio.put("1111",'638');
			bloqueio.put("5555",'653');
			bloqueio.put("6666",'654');
			bloqueio.put("7777",'655');
			bloqueio.put("8888",'656');
			bloqueio.put("9999",'690');

			System.err.println("Auto -" + bloqueio.get(codigo));
			if(!bloqueio.get(codigo).equals(null)){
				bRamo = true
			}

		}else if(ramo.equals('1102') || ramo.equals('602') || ramo.equals('605') ){
			//Bloqueio de mult
			bloqueio.put("8001",'601');
			bloqueio.put("8002",'601');
			bloqueio.put("8003",'601');
			bloqueio.put("8004",'601');
			bloqueio.put("8013",'601');
			bloqueio.put("8019",'601');

			bloqueio.put("714",'601');
			bloqueio.put("716",'601');

			bloqueio.put("1140",'601');
			bloqueio.put("1143",'601');
			bloqueio.put("1575",'601');
			bloqueio.put("1652",'601');
			bloqueio.put("1701",'601');

			//Genéricos
			bloqueio.put("1111",'638');
			bloqueio.put("5555",'653');
			bloqueio.put("6666",'654');
			bloqueio.put("7777",'655');
			bloqueio.put("8888",'656');
			bloqueio.put("9999",'690');

			System.err.println("Auto -" + bloqueio.get(codigo));
			if(!bloqueio.get(codigo).equals(null)){
				bRamo = true
			}
		}
		else if(ramo.equals('601') || ramo.equals('603')){
			//Bloqueio de mult
			bloqueio.put("8004",'851');
			bloqueio.put("8013",'851');

			bloqueio.put("8001",'851');
			bloqueio.put("8003",'851');

			//Genéricos
			bloqueio.put("1111",'638');
			bloqueio.put("5555",'653');
			bloqueio.put("6666",'654');
			bloqueio.put("7777",'655');
			bloqueio.put("8888",'656');
			bloqueio.put("9999",'690');

			System.err.println("Auto -" + bloqueio.get(codigo));
			if(!bloqueio.get(codigo).equals(null)){
				bRamo = true
			}
		}
		else{
			//Bloqueio de mult
			bloqueio.put("714",'605');
			bloqueio.put("1701",'605');
			bloqueio.put("2279",'605');
			bloqueio.put("8001",'605');
			bloqueio.put("8002",'605');
			bloqueio.put("8004",'605');
			bloqueio.put("8013",'605');
			bloqueio.put("8021",'605');

			//Genéricos
			bloqueio.put("1111",'638');
			bloqueio.put("5555",'653');
			bloqueio.put("6666",'654');
			bloqueio.put("7777",'655');
			bloqueio.put("8888",'656');
			bloqueio.put("9999",'690');

			System.err.println("Mult -" + bloqueio.get(codigo));
			if(!bloqueio.get(codigo).equals(null)){
				bRamo = true
			}
		}
		return bRamo
	}

	/**
	 * @author T_DVLOPES
	 * @param massa
	 * @param nrFluxo
	 * @return
	 */
	public static fluxoEditarTexto(MassaDados massa,String nrFluxo){
		System.out.println()
		nrFluxo = nrFluxo.replace('\\s+','');
		String texto = 'Prezado Corretor,\n Informamos que foi criado o pedido de cotação nº "'+nrFluxo.trim()+'" \n para análise da renovação da apólice "'+massa.getApoliceSucep()+'" que irá \n vencer em "'+massa.getDataVencRisco()+'". \n Diante do exposto, solicitamos o envio das informações\n atualizadas do risco\n (via ficha de gestão). \n - Questionário Atualizado;';
		trocaJanela('Editor de Notas/Agenda');
		preencheXpath('//*[@id="texto"]', texto,5);
		//Clica gravar
		clickXpath('//*[@id="o_11"]', 2);
		validarUT();
	}

	public static validarUT(){
		String valorUt = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 2);
		if(valorUt.equals(' Campo obrigatório.')){
			finalizar();
			valueCheck = 'processado COM FALHA, ['+valorUt+' Campo UT]';
		}else{
			validarEnviado();
		}
	}


	public static fluxoFecharJanelas(){
		fecharJanelaAtual(2)
		fecharJanelaAtual(1)
		validarEnviado();
	}


	/**leitura Excel EnvioPDF
	 * @return
	 */
	public static List<MassaDados> leituraExcelEnvioPDF(){
		String caminho_arquivo_xlsx = "C://Users//Public//RPA_ALLIANZ//MassaAuto//BackOfficeMacroEmissõesCorporate.xlsx";
		String aba = "EnvioPDF";
		int lineCheck = 17;

		//Ignora as linhas que tiverem
		String valueCheck = 'enviado';
		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminho_arquivo_xlsx, aba,lineCheck,valueCheck);
		System.err.println("List Massa Dados ["+ listMassaDados.size()+"]\n");
		for(int i = 0; i < listMassaDados.size(); i++){
			System.err.println("Apolice ["+listMassaDados.get(i).getApolice()+"] NomeCoretor ["+listMassaDados.get(i).getNomeCorretor().trim()+"] Status ["+listMassaDados.get(i).getStatus()+"]");
		}
		return listMassaDados;
	}


	/**atualizaExcelEnvioPDF
	 * @param linha
	 * @return
	 *
	 * Atualiza linha
	 */
	public static List<MassaDados> atualizaExcelEnvioPDF(String numeroApolice, int linha){
		String caminho_arquivo_xlsx = "C://Users//Public//RPA_ALLIANZ//MassaAuto//BackOfficeMacroEmissõesCorporate.xlsx";
		String aba = "EnvioPDF";
		int lineCheck = 17;
		System.err.println(linha);
		auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha,caminho_arquivo_xlsx, valueCheck, lineCheck, aba);
	}


	/**leitura Excel PendenciaGoAuto
	 * @return
	 */
	public static List<MassaDados> leituraExcelPendenciaGoAuto(String caminhoExcel, String aba){

		//String aba = "Auto";
		int lineCheck = 6;

		String caminho_arquivo_xlsx = caminhoExcel;
		System.err.println(caminho_arquivo_xlsx);

		//Ignora as linhas que tiverem
		String valueCheck = 'enviado';

		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminho_arquivo_xlsx, aba,lineCheck,valueCheck);
		System.err.println("List Massa Dados ["+ listMassaDados.size()+"]\n");
		for(int i = 0; i < listMassaDados.size(); i++){
			System.err.println("Apolice ["+listMassaDados.get(i).getApolice()+"] Bloqueio ["+listMassaDados.get(i).getBloqueio()+"] Status ["+listMassaDados.get(i).getStatus()+"]  Linha ["+listMassaDados.get(i).getLinha()+"]");
		}
		return listMassaDados;
	}



	/**leitura Excel PendenciaGoAuto
	 * @return
	 */
	public static List<MassaDados> leituraExcelAgendamentoInspecao(String caminhoExcel){
		String aba = "Agro";
		int lineCheck = 9;
		String caminho_arquivo_xlsx = caminhoExcel;
		System.err.println(caminho_arquivo_xlsx);
		//Ignora as linhas que tiverem
		String valueCheck = 'enviado';
		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminho_arquivo_xlsx, aba,lineCheck,valueCheck);
		System.err.println("List Massa Dados ["+ listMassaDados.size()+"]\n");
		for(int i = 0; i < listMassaDados.size(); i++){
			System.err.println("Apolice ["+listMassaDados.get(i).getApolice()+"] Status ["+listMassaDados.get(i).getStatus()+"]  Linha ["+listMassaDados.get(i).getLinha()+"]");
		}
		return listMassaDados;
	}

	/**atualizaExecelPendnciaGo
	 * @param linha
	 * @return
	 *
	 * Atualiza linha
	 */
	public static List<MassaDados> atualizaExcelPendenciaGo(String caminho_arquivo_xlsx, String numeroApolice, int linha){
		String aba = "Bloqueios";
		int lineCheck = 6;
		auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha,caminho_arquivo_xlsx, valueCheck, lineCheck, aba);
	}


	/**Atualiza Excel Agendamento Inspeção
	 * @param caminho_arquivo_xlsx
	 * @param numeroApolice
	 * @param linha
	 * @return
	 */
	public static List<MassaDados> atualizarExcelAgendamentoInspecao(String caminho_arquivo_xlsx, String numeroApolice, int linha){
		String aba = "Agendamento";
		int lineCheck = 11;
		println valueCheck;
		auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha,caminho_arquivo_xlsx, valueCheck, lineCheck, aba);
	}

	/**
	 * @return
	 */
	public static leituraExcelEnvioPdf(){
		String caminho_arquivo_xlsx = "C://Users//Public//RPA_ALLIANZ//MassaAuto//BackOfficeMacroEmissõesCorporate.xlsx";
		String aba = "EnvioPDF";
		int lineCheck = 17;
		String valueCheck = 'não enviado';
		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminho_arquivo_xlsx, aba,lineCheck,valueCheck);
		System.err.println("List Massa Dados ["+ listMassaDados.size()+"]\n");
		for(int i = 0; i < listMassaDados.size(); i++){
			System.err.println("Apolice ["+listMassaDados.get(i).getApolice()+"] Corretor ["+listMassaDados.get(i).getNomeCorretor()+"] Status ["+listMassaDados.get(i).getStatus()+"]");

		}

	}


	/**usuarioSenha
	 * @return
	 */
	public static usuarioSenha(String nomeArquivo){
		println 'Iniciando Leitura dados de Login'
		String caminho_arquivo_xlsx = "C://Users//Public//RPA_ALLIANZ//MassaAuto//"+nomeArquivo+".xlsx";
		String aba = "userPssword";
		int lineCheck = 2;
		String valueCheck = 'Login';
		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminho_arquivo_xlsx, aba,lineCheck,valueCheck);
		System.err.println("List Massa Dados ["+ listMassaDados.size()+"]\n");
		for(int i = 0; i < listMassaDados.size(); i++){
			user = listMassaDados.get(i).getUser();
			senha = listMassaDados.get(i).getSenha();
			System.err.println("Usario [OK] Senha [Ok]");
			System.err.println("Iniciando Login");
		}
	}

	public static finalizarPendenciaGo(){
		finalizar();
		iniciaAplicacaoMatriz();
		realizaLoginMatrizProdução();
		fluxoFichaGestao();
	}


	/**fluxoAtivaçãoPedidoSelecionarRamoModalidade
	 * @return
	 */
	public static fluxoAtivaçãoPedidoSelecionarRamoModalidade(String corretor, String tipoRenovacao, String agrupamentoRamo, String ramoModalidade){

		System.err.println('corretor ['+corretor+'] tipoRenovacao ['+tipoRenovacao+"] agrupamentoRamo ["+agrupamentoRamo+'] ramoModalidade ['+ ramoModalidade+']')

		String tipoSeguro;
		if(tipoRenovacao.equals('0')){
			tipoSeguro = "Renovação Interna sem Sinistro"
		}else{
			tipoSeguro = "Renovação Interna com Sinistro"
		}
		// altarar para uma outra janela
		trocaJanela('Pesquisa Pedido');
		clickXpath('//*[@id="botonAlta"]', 5)
		//preenche corretor
		preencheXpath('//*[@id="mediador"]', corretor, 10);
		selecionarTipoSeguro(tipoSeguro);
		selecionarAgrupamentoRamo(agrupamentoRamo);
		switchAgrupamentoRamo(agrupamentoRamo, ramoModalidade)

		System.err.println()
	}



	/**fluxoPreencherCamposClicarBotaoGravar
	 * @param cpfCnpj
	 * @param nomeRazao
	 * @param data
	 * @param cPublic
	 * @param descricao
	 * @return
	 */
	public static fluxoPreencherCamposDadosCorretor(String cpfCnpj, String nomeRazao, String data, String cPublic){
		System.out.println(cpfCnpj+' '+ nomeRazao+" "+data+" "+cPublic)
		//preenche CPF
		preencheXpath('//*[@id="nifIdent_doc"]', cpfCnpj, 1);
		//clickLUpa
		//preencher nome / razão social
		preencheXpath('//*[@id="nombre"]', nomeRazao, 1);
		//preenche data
		println '['+data+']';
		preencheXpath('//*[@id="fechaVencimiento"]', data, 2);
		//preenche concurso publico
		Map<String, String> concurso = new HashMap<String, String>();
		clickXpath('//*[@id="concurso"]', 1);
		concurso.put("SIM",'//*[@id="concurso"]/option[2]');
		concurso.put("NÃO",'//*[@id="concurso"]/option[3]');
		clickXpath(concurso.get(cPublic), 1);
	}

	/**preencherCampoInformativoCliente
	 *
	 * recebe por parametro valores Apolice/ totalPremio/ totalSinistro/ qtdSinistro/ porcentagelTotal
	 *
	 * E clica em gravar.
	 *
	 * @param massa
	 * @return
	 */
	public static preencherCampoInformativoCliente(MassaDados massa){

		System.out.println()

		String texto1 = ' - Numero Apolice: '+ massa.getApoliceSucep().toString();
		String texto2 = '\n - Total Prêmio: '+massa.getTotalPremio().toString();
		String texto3=  '\n - Total Sinistro: ' + massa.getTotalSinistro().toString();
		String texto4=  '\n - Quantidade de Sinistro: ' +massa.getQtdSinistro().toString();
		String texto5= '\n - Sinistralidade(%): '+massa.getPorcentagemTotal().toString();
		String texto6= '\n - Recibos Pendentes: '+qtdPendenciaRecuperada.toString();
		String todosTextos = texto1 + texto2 + texto3+ texto4 + texto5 + texto6;

		System.out.println(todosTextos

				+'\n'+massa.getApoliceSucep().toString()
				+'\n'+massa.getTotalPremio().toString()
				+'\n'+ massa.getTotalSinistro().toString()
				+'\n'+massa.getPorcentagemTotal().toString()
				+'\n'+qtdPendenciaRecuperada.toString()

				)


		preencheXpath('//*[@id="descripcion"]', todosTextos, 1);

		//Clicar Botao Gravar
		clickXpath('//*[@id="botonGrabar"]', 1);
	}


	public static String recuperarNumeroRenovacaApolice(){
		//Ativação Pedido
		String valorRecuperado = recuperaValorAgcs('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','textContent',5).replace("O pedido com o número", "").replace("foi corretamente gravado.", "").trim();
		return valorRecuperado;
	}

	public static validaValueCheck(String valorRecuperado){
		if(valorRecuperado.contains('Não Existe Dados')||valorRecuperado.contains('Data vencimiento incorrecta')){
			finalizar()
			valueCheck = 'processado COM FALHA, [' +valorRecuperado  +']';
		}
	}

	/**RecuperarSinistros
	 *
	 */
	public static Object recuperarSinistros(MassaDados massa, String ramo){
		premioTotal = 0
		System.err.println('ramo ['+ramo+']')
		String ramoAQUI = ramo;
		try{

			if( ramoAQUI.equals('307') ||
			ramoAQUI.equals('308') ||
			ramoAQUI.equals('309') ||
			ramoAQUI.equals('310') ||
			ramoAQUI.equals('311') ||
			ramoAQUI.equals('312') ||
			ramoAQUI.equals('329') ||
			ramoAQUI.equals('335') ||
			ramoAQUI.equals('336') ||
			ramoAQUI.equals('337') ||
			ramoAQUI.equals('338') ||
			ramoAQUI.equals('339')){

				massa.setTotalPremio(recuperaValor('//*[@id="detalhesResultadoTable2_0_2"]','textContent',10).replace("BRL", "").trim());
				println "Total Premio: "+massa.getTotalPremio();
				massa.setTotalSinistro(recuperaValor('//*[@id="detalhesResultadoTable2_0_4"]','textContent',1).replace("BRL", "").trim());
				println "Total Sinistro: "+massa.getTotalSinistro();
				massa.setQtdSinistro(recuperaValor('//*[@id="detalhesResultadoTable2_0_5"]','textContent',1).replace("BRL", "").trim());
				println "Quantidade Sinistro: "+massa.getQtdSinistro();
				massa.setPorcentagemTotal(recuperaValor('//*[@id="detalhesResultadoTable2_0_6"]','textContent',1).replace("BRL", "").trim());
				println "Porcentagem Total: "+massa.getPorcentagemTotal();
				fecharJanela();
				//			alteraJanejaGenerica(1, 5)
				//			fecharJanela();
			}else {
				massa.setTotalPremio(recuperaValor('//*[@id="detalhesResultadoTable_0_2"]','textContent',10).replace("BRL", "").trim());
				println "Total Premio: "+massa.getTotalPremio();
				massa.setTotalSinistro(recuperaValor('//*[@id="detalhesResultadoTable_0_4"]','textContent',1).replace("BRL", "").trim());
				println "Total Sinistro: "+massa.getTotalSinistro();
				massa.setQtdSinistro(recuperaValor('//*[@id="detalhesResultadoTable_0_5"]','textContent',1).replace("BRL", "").trim());
				println "Quantidade Sinistro: "+massa.getQtdSinistro();
				massa.setPorcentagemTotal(recuperaValor('//*[@id="detalhesResultadoTable_0_6"]','textContent',1).replace("BRL", "").trim());
				println "Porcentagem Total: "+massa.getPorcentagemTotal();
				fecharJanela();
				//			alteraJanejaGenerica(1, 5)
				//			fecharJanela();
			}

			//try{
			String s = massa.getTotalPremio()
			String newStr = s.replaceAll("[,.]","")
			premioTotal = Integer.parseInt(newStr)
			System.err.println("premio total "+premioTotal)
		} catch (Exception e){
			valueCheck = "Não existem dados em valores de carteira e detalhes do resultado"
		}



		return massa;
	}



	///////////////////////////////////////////////////////////////////////////
	//Altera javela

	public static alteraJanejaConsultasEstatisticas(){
		trocaJanela('Consultas & Estatísticas')
	}


	public static alteraJanejaProducaoSinistro(){
		trocaJanela('Produção & Sinistros');
	}

	public static alteraJanejaAtivacaoPedido(){
		trocaJanela('Ativação Pedido');
	}

	public static alteraJanejaAllianzNovaCotação(){
		trocaJanela('app');
	}

	public static alteraJanejaLiquidacaoesPrestadores(){
		trocaJanela('Liquidações Prestadores');
	}

	public static alteraJanelaMenuProducao(){
		trocaJanela('Menu Produção');
	}

	public static alteraJanelaResultadoOperacional(){
		trocaJanela('Menu Produção');
	}

	public static alteraJanelaDadosGerais(){
		trocaJanela('Dados Gerais');
	}


	/**
	 * Pagina 0 / 1 / 2
	 * Recebe a quantidade de paginas para navergar e delay
	 * @param qtd
	 * @return
	 */
	public static alteraJanejaGenerica(int qtd, int delay){
		WebUI.delay(delay);
		String x;
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		for(int i=0; i<=qtd; i++){
			x = i.toString();
			x = it.next();
			driver.switchTo().window(x);
		}

	}



	//	public static alteraJanejaMenuProducaoPopUp(){
	//		WebUI.delay(20);
	//		WebDriver driver = DriverFactory.getWebDriver();
	//		Set  st= driver.getWindowHandles();
	//		Iterator it = st.iterator();
	//		String parent =  it.next();
	//		String child =it.next();
	//		driver.switchTo().window(parent);
	//		driver.switchTo().window(child);
	//	}


	public static alteraJanelaListaBloqueioClose(){
		WebUI.delay(10);
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		String parent =  it.next();
		String child =it.next();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
		driver.close();
		driver.switchTo().window(parent);
	}

	//	public static alteraResultadoOperacional(){
	//		WebUI.delay(20);
	//		WebDriver driver = DriverFactory.getWebDriver();
	//		Set  st= driver.getWindowHandles();
	//		Iterator it = st.iterator();
	//		String parent =  it.next();
	//		String child =it.next();
	//		String child2 =it.next();
	//		driver.switchTo().window(parent);
	//		driver.switchTo().window(child);
	//		driver.switchTo().window(child2);
	//	}

	//	public static alteraResultadoOperacionalListaBloqueios(){
	//		WebUI.delay(20);
	//		WebDriver driver = DriverFactory.getWebDriver();
	//		Set  st= driver.getWindowHandles();
	//		Iterator it = st.iterator();
	//		String parent =  it.next();
	//		String child =it.next();
	//		driver.switchTo().window(parent);
	//		driver.switchTo().window(child);
	//
	//	}

	public static alteraJanelaLISTADESBLOQUEIOS(){
		WebUI.delay(10);
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

	/** EXCLUIR !!!!!
	 * @return
	 */
	public static alteraJanejaClose(){
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		String parent =  it.next();
		String child =it.next();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
		driver.close();
		driver.switchTo().window(parent);
	}

	//	public static alteraJanejaMenuProducao(){
	//		WebUI.delay(10);
	//		WebDriver driver = DriverFactory.getWebDriver();
	//		Set  st= driver.getWindowHandles();
	//		Iterator it = st.iterator();
	//		String parent =  it.next();
	//		String child =it.next();
	//		driver.switchTo().window(parent);
	//		driver.switchTo().window(child);
	//	}


	//////////////////////////////////////////////////////////////////////////////////////////////////
	/////alterajanelas FIM




	/**
	 * recuperarPendencias
	 * Para cada for cria um novo objeto e adiciona a list de pendencia
	 *
	 * @param massa
	 * @return
	 */
	public static MassaDados recuperarPendencias(MassaDados massa){


		Thread.sleep(5000);

		List<Pendencia> pendencias = new ArrayList<Pendencia>();

		for(int i = 0; i <= 19;i++){

			Pendencia pendenciaRecuperada = new Pendencia();
			pendenciaRecuperada.setnAdesao(recuperaValor('//*[@id="DetalhesAplicacoesApoliceTable_'+i+'_0"]','textContent',0).trim());
			pendenciaRecuperada.setRecibo(recuperaValor('//*[@id="DetalhesAplicacoesApoliceTable_'+i+'_1"]','textContent',0).trim());
			pendenciaRecuperada.setDataEmissao(recuperaValor('//*[@id="DetalhesAplicacoesApoliceTable_'+i+'_2"]','textContent',0).trim());
			pendenciaRecuperada.setPremioTotal(recuperaValor('//*[@id="DetalhesAplicacoesApoliceTable_'+i+'_5"]','textContent',0).trim());

			pendencias.add(pendenciaRecuperada);

		}
		return massa.setPendencias(pendencias);;
	}

	public static clicarFichaGestao(){
		//clicar Ficha Gestão
		clickXpath('//*[@id="-61"]/div[1]', 0);
		trocaJanela('APLICAÇÕES DE UMA APÓLICE');
		fecharJanela();
		trocaJanela('Gestão Processos');
	}


	public static fluxoClicarNotaAgendClicarAceitar(){
		//clicar NotaAgenda
		clickXpath('//*[@id="20"]/div[1]', 0);
		trocaJanela('Editor de Notas/Agenda');
		preencheXpath('//*[@id="numModCodi"]', '175', 5);
		//clicar aceitar
		clickXpath('//*[@id="btnAceptar"]', 0);
		//fluxoSubstituirTextoEditavel();
	}


	/*nAdesao
	 Recibo
	 DataEmissao
	 PremioTotal*/
	public static fluxoSubstituirTextoEditavel(MassaDados massa){
		println massa.getApolice();
		println massa.getApoliceSucep();
		println massa.getDataVencRisco();
		println qtdPendenciaRecuperada;
		String texto = 'Prezado Corretor, \n Informamos que foi criado o pedido de cotação nº "NR FLUXO" \n para análise da renovação da apólice "NR_APOLICE_SUSEP" que irá \nvencer em "DT_FIM_VIGENCIA_APOLICE". \nDiante do exposto, solicitamos o envio das informações\n atualizadas do risco\n (via ficha de gestão). \n - Questionário Atualizado;'
		preencheXpath('//*[@id="texto"]', texto, 0);
		fecharJanela();
	}




	public static fluxoSelecionaCorretora(String ramo, String corretor){
		//Clica Lupa Ramo filial
		clickXpath('//*[@id="formEmulacionMediador"]/a[1]',1);
		//Seleciona Ramo filial
		if(ramo.equals('509')){
			clickXpath('//*[@id="listTable"]/tbody/tr[146]',1);
			//*[@id="listTable"]/tbody/tr[146]/td[1]
		}else if (ramo.equals('999')){
			clickXpath('//*[@id="listTable"]/tbody/tr[160]',1);
		}
		//Preenche Campo Corretor
		preencheXpathSetText('//*[@id="mediadorId"]', corretor, 1);

		//ClicaLupa Corretor
		clickXpath('//*[@id="formEmulacionMediador"]/a[2]',0);

		//Seleciona Corretor
		clickXpath('//*[@id="drlf44-emulacion-mediador-modal-dialog-lg"]/div/div/div[2]',1);

		//Clica Simulação!
		clickXpath('//*[@id="formEmulacionMediador"]/div/button',2);
	}


	public static fluxoNavegaTelaRenovacao(){
		//Clica Vendas
		clickXpath('/*/body/div[1]/div/app-root/app-private/app-private-header/div[1]/nx-extended-navigation/div/div[1]/nx-extended-navigation-desktop/div[1]/div/div/ul/li[1]/a',1);

		//Clica Renovação
		WebUI.delay(5)
		selecionaListaDeElementos('/*/body/div[1]/div/app-root/app-private/app-private-header/div[1]/nx-extended-navigation/div/div[1]/nx-extended-navigation-desktop/div[2]/div/div/div/div/div/div/div/div/div[1]/ul','span')
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().equals('Renovações')){
				listOfElements[i].click()
				break
			}
		}
		WebUI.delay(20)
	}


	public static fluxoPesquisaApolice(String apolice){
		//PesquisaApolise
		preencheXpathInframe('//*[@id="poliza_SUSEP"]', apolice, 5);

		//FILTRAR
		clickXpath('//*[@id="null"]/div[1]/button',1);
		WebUI.delay(5)
		clickXpath('//*[@id="null"]/div[1]/button',1);

		WebUI.delay(5)
		//	clickXpath('//*[@id="o_2"]',1);

		//		try{
		//			//Verifica se a mensagem de erro foi apresentada
		//			if(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr', 'textContent', 2).contains('PREO9918')){
		//				//clickXpath('//*[@id="o_4"]',1);
		//				clickXpath('//*[@id="null"]/div[1]/button',1);
		//			}
		//		} catch (Exception e){
		//		}
		//
	}

	public static selecionaTipoLiquidacao(String tipo){
		clickXpath('//*[@id="tipoLiquidacion"]',1);
		Map<String, String> tipoLiquidacao = new HashMap<String, String>();
		tipoLiquidacao.put("Pendentes PreLiquidação", '//*[@id="tipoLiquidacion"]/option[2]');
		tipoLiquidacao.put("PréLiquidações", '//*[@id="tipoLiquidacion"]/option[3]');
		tipoLiquidacao.put("Liquidadas", '//*[@id="tipoLiquidacion"]/option[4]');
		tipoLiquidacao.put("Pendentes", '//*[@id="tipoLiquidacion"]/option[5]');
		tipoLiquidacao.put("Anuladas", '//*[@id="tipoLiquidacion"]/option[6]');
		clickXpath(tipoLiquidacao.get(tipo),0);
	}



	/**Clica na opcao de download PDF
	 *
	 * Apólice, Endosso ...
	 * Recibos boletos
	 * CARTA INADIMPLÊNCIA - AVISO
	 *
	 *  @author T_dvlopes
	 */
	public static void downloadPDFApolice(String apolice){
		try{
			WebUI.delay(5);
			String xpath = '//*[@id="listDatos"]'
			String tag = "tr";
			WebDriver driver = DriverFactory.getWebDriver();
			String data = driver.findElement(By.xpath('//*[@id="listDatos_0_0"]')).getText();
			List<WebElement> listOfElements
			listOfElements = listaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				System.err.println("["+i+"] "+tag+" ["+listOfElements[i].getText()+"]")
				if(listOfElements[i].getText().toString().contains(data.toString())){
					if(listOfElements[i].getText().toString().contains(apolice.toString())){
						listOfElements[i].click();
						WebUI.delay(15);
						fluxoRenomearArquivoApolice();
						enviarApolice = true;
						break;
					}
				}
			}
		}catch (Exception e){
			System.err.println("Não foi encontrado APOLICE")
			enviarApolice = false;
		}

	}


	public static void downloadPDFBoleto(String boleto){
		try{
			AlteraAbaApoliceBoleto();
			WebUI.delay(2);
			String xpath = '//*[@id="listDatos"]'
			String tag = "tr";
			WebDriver driver = DriverFactory.getWebDriver();
			String data = driver.findElement(By.xpath('//*[@id="listDatos_0_0"]')).getText();
			listOfElements = listaDeElementos(xpath,tag)
			for(int j = 0; j < listOfElements.size(); j++){
				System.err.println("["+j+"] "+tag+" ["+listOfElements[j].getText()+"]")
				if(listOfElements[j].getText().toString().contains(data.toString())){
					if(listOfElements[j].getText().toString().contains(boleto.toString())){
						listOfElements[j].click();
						WebUI.delay(15);
						fluxoRenomearArquivoBoleto();
						enviarBoleto = true;
						break;
					}
				}
			}
		}catch (Exception e){
			System.err.println("Não foi encontrado BOLETO");
			enviarBoleto = false;
		}

	}


	public static fluxoPesquisaPropostaBloqueada(String numeroApolice){
		try{
			//alteraJanejaMenuProducao();
			alteraJanejaGenerica(1,5);
			//clica Proposta Bloqueada
			clickXpath('//*[@id="pBloqueadas"]', 2);
			//preenche campo Proposta
			preencheXpath('//*[@id="poliza_Policy"]', numeroApolice, 5);
			//clica em pesquisar
			clickXpath('//*[@id="btnBuscar"]', 2);
		}catch (Exception e){
			System.err.println("Numero Apolice não encontrada");
			finalizar();
			validarEnviado("Numero Apolice não encontrada");
		}
	}

	public static String fluxoLiberacao(){
		System.out.println()
		try{
			String recuperaErro = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 3);
			println recuperaErro;
			if(recuperaErro.contains(' Não foram encontrados dados para a ação selecionada.')){
				finalizar();
				validarEnviado(recuperaErro);
				return recuperaErro;
			}

			String newNumeroApolice;
			println 'Liberar'
			//clica Emitir
			clickXpath('//*[@id="fil20"]', 1);
			fechaAlerta();

			alteraJanejaGenerica(2,20);
			WebUI.delay(20)
			String numeroApoliceRecuperada = recuperaValor('//*[@id="SUSEP"]', 'textContent', 5);
			println numeroApoliceRecuperada

			if(numeroApoliceRecuperada.equals('atributo nao encontrado')){
				//clica Fechar
				println  'Fechar'
				clickXpath('//*[@id="o_2"]', 5);
				alteraJanejaGenerica(1,2);

				//clica Emitir NOVAMENTE
				println 'Liberar NOVAMENTE'
				clickXpath('//*[@id="fil20"]', 5);
				fechaAlerta();
				alteraJanejaGenerica(2,10);
				println 'recupera'
				numeroApoliceRecuperada = recuperaValor('//*[@id="SUSEP"]', 'textContent', 2);
				println numeroApoliceRecuperada
				numeroApoliceRecuperada = numeroApoliceRecuperada.replace('-', '').replace('/', '');
				println numeroApoliceRecuperada
				newNumeroApolice = numeroApoliceRecuperada.substring(0, 19);
				println newNumeroApolice;

				//clica Fechar
				clickXpath('//*[@id="o_2"]', 1);
				alteraJanejaGenerica(1,10);

				validaErro = 'SemErro'
			}else {
				numeroApoliceRecuperada = numeroApoliceRecuperada.replace('-', '').replace('/', '');
				println numeroApoliceRecuperada
				newNumeroApolice = numeroApoliceRecuperada.substring(0, 19);
				println newNumeroApolice;
				//clica Fechar
				clickXpath('//*[@id="o_2"]', 2);
				alteraJanejaGenerica(1,10);
			}
			return newNumeroApolice
		}catch (Exception e){
			System.err.println("Falha fluxo liberação");
			finalizar();
			validarEnviado("Falha fluxo liberação");
			return 'falha ao realizar fluxo liberação '
		}
	}

	/**
	 * @author be54955
	 * @param subGrupo
	 * @param inicioVigencia
	 * @param cnpjtualizado
	 * @param nomeTomador
	 * @param valorPremioLiquido
	 * @param parcelas
	 * @return
	 */
	public static fluxoLiberacaoApolice(String subGrupo, String inicioVigencia, String cnpjtualizado, String nomeTomador, String valorPremioLiquido, String parcelas){
		WebUI.delay(10);
		try{

			System.err.println("nomeTomador length " +nomeTomador.length())
			System.err.println()

			int length = nomeTomador.length()
			System.err.println("length "+length)

			if(length > 58){
				nomeTomador = nomeTomador.substring(0, 58);
			}

			System.err.println("subGrupo ["+subGrupo+"] inicioVigencia ["+inicioVigencia+"] cnpjtualizado ["+cnpjtualizado+"] nomeTomador ["+nomeTomador+"] valorPremioLiquido ["+valorPremioLiquido+"] parcelas ["+parcelas+"]")

			//clickXpath('//*[@id="DatosFlotaBean$centro"]', 5);


			if(subGrupo.equals('1')){
				try{
					clickXpath('//*[@id="DatosFlotaBean$centro"]/option[1]', 1);
				} catch (Exception e){}
			}else {
				try{
					clickXpath('//*[@id="DatosFlotaBean$centro"]/option[2]', 1);
				} catch (Exception e){}
			}
			//DELETAR O QUE ESTIVER LÁ!
			deletaCampoXpath('//*[@id="DatosGeneralesBean$fechaEfecto"]', 0, 11);
			//Preenche com a data
			preencheXpath('//*[@id="DatosGeneralesBean$fechaEfecto"]', inicioVigencia, 1);
			//preenche campo CNPJ
			cnpjtualizado = cnpjtualizado.replace('.','').replace('/','').replace('-','');
			preencheXpath('//*[@id="IntervinientesBean$nifTomador_doc"]', cnpjtualizado, 1);
			//preenche Nome tomador
			preencheXpath('//*[@id="IntervinientesBean$nomFigTomadorValue"]', nomeTomador, 1);
			//Clica duplicata
			clickXpath('//*[@id="IntervinientesBean$esAsegurado1"]', 1);

			//CLICA AVANÇAR
			clickXpath('//*[@id="btnAceptar"]', 1);
			//CLICA RECIBO PRIMEIRO MANUAL
			clickXpath('//*[@id="OpcionesCapitalesBean$primerRecibManual"]', 5);
			//CLICA continuar
			clickXpath('//*[@id="btnContinuar"]', 1);

			valorPremioLiquido = valorPremioLiquido.replace('.','');
			println valorPremioLiquido;
			//Preenche Valor PremioLiquido
			preencheXpath('//*[@id="CoberturasBean$lineasCoberturas[0].capitales[0].primerRec"]', valorPremioLiquido, 1);
			//clica calcular
			clickXpath('//*[@id="btnCalcular"]', 1);
			//CLICA AVANÇAR
			clickXpath('//*[@id="btnAceptar"]', 1);

			if(parcelas.equals('0')){
				clickXpath('//*[@id="ParrillaBean$modalidades[0]rad"]', 1);
			}else{
				clickXpath('//*[@id="ParrillaBean$modalidades[1]rad"]', 1);
				clickXpath('//*[@id="ParrillaBean$numFracciones"]', 1);
				String xpath = selecionarQtdParcela(parcelas);
				clickXpath(xpath, 1);
			}
		}catch (Exception e){
			System.err.println("Falha fluxo liberação");
			finalizar();
			validarEnviado("Falha fluxo liberação");
		}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static fluxoAvancar(){
		try{
			//EMITIR
			clickXpath('//*[@id="ParrillaBean$btnEmitir"]', 1);
			//CLICA AVANÇAR
			clickXpath('//*[@id="btnAceptar"]', 1);
			validarListaClientes();
			//CLICA AVANÇAR
			clickXpath('//*[@id="btnAceptar"]', 2);
			validaCampoObrigatorio()
			validarListaClientes();
			//CLICA AVANÇAR
			clickXpath('//*[@id="btnAceptar"]', 3);
			if(alertListContinueList(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 0))){
				//CLICA AVANÇAR
				clickXpath('//*[@id="btnAceptar"]', 3);
			}
			//Gestão de Centros
			clickXpath('//*[@id="btCentros"]', 2);

			//CLICA AVANÇAR
			clickXpath('//*[@id="btnAceptar"]', 2);

		}catch (Exception e){
			System.err.println("Falha fluxo Avançar / emitir");
			finalizar();
			validarEnviado("Falha fluxo Avançar / Emitir");
		}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static validaCampoObrigatorio(){
		WebUI.delay(1);
		try{
			String end =  recuperaValor('//*[@id="DomicilioClienteBeanTomador$moradaaddressPart2"]	', 'value', 2)
			System.out.println(end)

			//Valida Numero
			String numero = recuperaValor('//*[@id="DomicilioClienteBeanTomador$numero"]', 'value', 2)
			System.out.println(numero)

			int iNum = Integer.valueOf(numero.length())
			System.out.println(iNum)
			if(iNum == 0){
				preencheXpath('//*[@id="DomicilioClienteBeanTomador$numero"]', '0', 1);
			}
		} catch (Exception e) {}

	}

	/**
	 * @author be54955
	 * @return
	 */
	public static validarListaClientes(){
		String valorRecueprado = recuperaValor('//*[@id="blListaClientes"]/table/tbody/tr[1]/td/table/tbody/tr/td[2]', 'textContent', 5)
		if(valorRecueprado.contains('LISTA CLIENTES')){
			clickXpath('//*[@id="ListaClientesBean$listaClientes_0_0"]', 2)
		}
	}

	/**
	 * @author be54955
	 * @param textoComplementar
	 * @return
	 */
	public static fluxoTextoComplementar(String textoComplementar){
		WebUI.delay(10);
		System.err.println("\nFluxo TextoComplementar\n\n");
		String tela
		try{
			/**
			 * Fluxo texto complementar
			 */
			//AVANÇAR
			clickXpath('//*[@id="btnAceptar"]', 5);
			WebUI.delay(3);
			tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
			WebUI.delay(2);
			if(tela.contains("MODIFICAÇÃO CLÁUSULA") || tela.contains("CLÁUSULAS")){

				System.err.println("\nTexto Complementar ["+textoComplementar+"]");

				if(validaClausuaSelecionada("300")){
					selecionaClausuaSelecionada("300", "Editar Texto")
					if(textoComplementar.equals("null") || textoComplementar.equals("")){
						limpaCampo('//*[@id="TextoClausulaBean$textoClausulaLibre"]', 2)
						preencheXpath('//*[@id="TextoClausulaBean$textoClausulaLibre"]', textoComplementar, 1);
					}
					//clicar Salvar
					clickXpath('//*[@id="btnAceptar"]', 5);
					//CLICA AVANÇAR
					clickXpath('//*[@id="btnAceptar"]', 5);
				} else {
					selecionaItemListaAtributo("300",'//*[@id="ClausulasOpcionalesBean$clausula"]' , 'option', 'value' )
					clickXpath('//*[@alt="Adicionar Cláusula"]', 1);
					//WebUI.delay(3);
					selecionaClausuaSelecionada("300", "Editar Texto")
					if(textoComplementar.equals("null") || textoComplementar.equals("")){
						limpaCampo('//*[@id="TextoClausulaBean$textoClausulaLibre"]', 2)
						preencheXpath('//*[@id="TextoClausulaBean$textoClausulaLibre"]', textoComplementar, 1);
					}
					//clicar Salvar
					clickXpath('//*[@id="btnAceptar"]', 5);
					//CLICA AVANÇAR
					clickXpath('//*[@id="btnAceptar"]', 5);
				}
			}

			tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
			if(tela.contains("CONFIRMAÇÃO DE EMISSÃO")){
				//CLICA AVANÇAR
				clickXpath('//*[@id="btnAceptar"]', 5);
			}

			tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
			if(tela.contains("EMISSÃO REALIZADA")){
				//CLICA AVANÇAR
				clickXpath('//*[@id="btnValida"]', 5);
			}

		}catch (Exception e){
			System.err.println("Falha fluxo TextoComplementar\n\n"+e);
			finalizar();
			validarEnviado("Falha fluxo Texto Complementar");
		}
	}



	/**
	 * @author BE54955
	 * @param clausula
	 * @return
	 */
	public static boolean validaClausuaSelecionada(String clausula){
		boolean c
		try {
			selecionaListaDeElementos('//*[@id="blClausulasSeleccionadasContent"]/table[2]','input')
			for(int i = 0; i < listOfElements.size(); i++){
				System.err.println("["+i+"] ["+listOfElements[i].getAttribute('value')+"]")
				if(listOfElements[i].getAttribute('value').equals(clausula.toString())){
					c = true
					System.err.println("FOUND ["+i+"] ["+listOfElements[i].getAttribute('value')+"] ["+c+"]")
					break
				}
			}

		} catch (Exception e){
			c = false
		}
		return c
	}

	/**
	 * @author BE54955
	 * @param clausula
	 * @return
	 */
	public static boolean selecionaClausuaSelecionada(String c, String o){
		System.err.println()
		boolean cl
		try {
			selecionaListaDeElementos('//*[@id="blClausulasSeleccionadasContent"]/table[2]','input')
			int j = 0
			for(int i = 0; i < listOfElements.size(); i++){
				System.err.println("["+i+"] ["+listOfElements[i].getAttribute('value')+"]")
				//	if(StringFormatter.checkStringSUSEP(listOfElements[i].getAttribute('value'))){
				if(!StringFormatter.removeNumerosDeStrings(listOfElements[i].getAttribute('value'))){
					//  if(StringFormatter.checkStringSUSEP(StringFormatter.removeNumerosDeStrings(o) )){
					if(listOfElements[i].getAttribute('value').contains(c.toString())){
						System.err.println("FOUND ["+i+"] ["+listOfElements[i].getAttribute('value')+"] ["+c+"] ["+j+"]")
						if(opcao(j)){
							cl = true
						} else {
							cl = false
						}
						break
					}
					j++
					//	   }
				}
			}
		} catch (Exception e){
			cl = false
		}
		return cl
	}

	/**
	 * @author BE54955
	 * @param j
	 * @param opcao
	 * @return
	 */
	public static boolean opcao(int j){
		boolean o
		try{
			clickXpath('//*[@onclick="modificar('+j+')"]', 1);
			o = true
		} catch (Exception e){
			o = false
		}
		return o
	}

	/**
	 * @author be54955
	 * @param numeroProposta
	 * @return
	 */
	public static fluxoDesbloqueio(String numeroProposta){
		System.out.println('fluxoDesbloqueio...')
		try{

			WebUI.delay(15);
			//clicar Desbloqueio
			clickXpath('//*[@id="pBloqueadas"]', 2);

			preencheXpath('//*[@id="poliza_Policy"]', numeroProposta, 2);

			//clica presquisar
			clickXpath('//*[@id="btnBuscar"]', 2);

			WebUI.delay(10);
			//clica check verde
			clickXpath('//*[@id="fil20"]', 2);
			fechaAlerta();
			WebUI.delay(20)
			alteraJanejaGenerica(2,5);
			WebUI.delay(20)
			String numeroApoliceRecuperada = recuperaValor('//*[@id="SUSEP"]', 'textContent', 5);
			println numeroApoliceRecuperada

			if(!numeroApoliceRecuperada.equals("atributo nao encontrado")){
				numeroApoliceRecuperada = numeroApoliceRecuperada.replace('-', '').replace('/', '');
				println numeroApoliceRecuperada
				apolice = numeroApoliceRecuperada.substring(0, 19);
				println apolice
				clickXpath('//*[@id="o_2"]', 2)
				validarEnviado();
				alteraJanejaGenerica(1,5);
			} else {
				clickXpath('//*[@id="o_2"]', 2)
				alteraJanejaGenerica(1,5);
				WebUI.delay(5)
				//clica check verde
				clickXpath('//*[@id="fil20"]', 2);
				fechaAlerta();
				WebUI.delay(20)
				alteraJanejaGenerica(2,5);
				WebUI.delay(20)
				String numeroApoliceRecuperadaNovo = recuperaValor('//*[@id="SUSEP"]', 'textContent', 5);
				println numeroApoliceRecuperada
				numeroApoliceRecuperadaNovo = numeroApoliceRecuperadaNovo.replace('-', '').replace('/', '');
				println numeroApoliceRecuperadaNovo
				apolice = numeroApoliceRecuperadaNovo.substring(0, 19);
				println apolice
				clickXpath('//*[@id="o_2"]', 2)
				validarEnviado();
				alteraJanejaGenerica(1,5);
			}

			//FECHAR
			//			clickXpath('//*[@id="lnkTopLink_Close"]', 2)
			//			fechaAlerta();
			//			alteraJanejaGenerica(0,5)
			validaErro = 'SemErro'
		}catch (Exception e){
			System.err.println("Falha fluxo Desbloqueio Proposta");
			finalizar();
			validarEnviado("Falha fluxo Desbloqueio Proposta");
		}


	}




	public static taskkill(){

		String pathKill = "cmd /c C:/Users/Public/FinalizarProcessos/Finalizar ChromeDriver.bat";
		Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+pathKill);
	}




	public static enviarEmailPdfTESTE(){

		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/EnvioPdf/ConfiguracoesEnvioPdf - Cópia.txt";
		//		lerConfiguraçõesEmailPdf(pathTxtEmail)
		path1 = '\\C:\\Users\\Public\\teste.pdf';
		nome1 = 'teste.pdf';
		String texto1= 'Segue anexa proposta de renovação do seu seguro "ALLIANZ AUTO" já com as condições \n imbativeis e exclusivas para você, colaborador.';
		String texto2= '\n Esperamos que você continue usufruindo desta vantagem e aguardamos o seu \n retorno a este e-mail até o dia "XX/XX/XXXX" para efetuvarnis a sua renovação.'
		String texto3 = '\n\nCaso queira fazer outras cotações do "ALLIANZ AUTO" e "ALLIANZ RESIDÊNCIA", aproveite este \nbenefícios que também é extensivo à sua familia: pais, filhos e cônjuges,';
		String texto4 = '\n\n"Acesse o menu lateral direito da IntraAllianz > seguro para colaborador"';
		String texto5= ' e faça já a sua \ncotação ou endosso no canal exclusivo com a corretora Corsetec.';
		texto =  texto1+texto2+texto3+texto4+texto5;
		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));

		//		enviarEmail(sender, emaildestination,pwdsender, path1, nome1, texto, subject, host, porta);
		primeiroFluxo = false;
	}



}

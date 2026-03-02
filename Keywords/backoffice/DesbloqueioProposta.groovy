package backoffice

import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import bean.MassaDados

public class DesbloqueioProposta  extends backofficeFuncionalidades{

	public static String caminhoDiretorioExcel = "C://Users//Public//RPA_ALLIANZ//MassaAuto//RoboDesbloqueioProposta.xlsx"
	public static int countEnviado = 0
	//public static String ExcelCopiado
	public static String dataRecuerada = getDateHour()
	public static String diretorio
	public static String diretorioToDelete
	public static int tamanhoListaOriginal
	public static List<MassaDados> listaExcel
	public static String atualizaExcel;



	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static fluxoDesbloqueioProposta(String tipoAba){
		fluxoCompletodesbloqueioProposta(tipoAba)
		finalizar()
		//finalizaChromeCMD();
		enviarDesbloqueioProposta(tipoAba)
		System.err.println('\nFinalizado...\n Casos finalizados  ['+ tamanhoListaOriginal+']\n')
	}




	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static fluxoCompletodesbloqueioProposta(String tipoAba){

		listaExcel = switchLeitorExcel.switchExcel(caminhoDiretorioExcel, tipoAba)
		tamanhoListaOriginal = listaExcel.size()
		if(tamanhoListaOriginal>0){
			/**
			 * Efetua inicialização da aplicação Matriz e faz o fluxo de envio de pendência
			 */
			inicia(tipoAba);
			fluxoSinistroProdutoDesbloqueio()
			fluxoDesbloqueio(listaExcel, tipoAba);
			/**
			 * Processa as pendências que apresentaram falhas
			 */
			finalizaProcesso();
		}
	}


	public static inicia(String tipoAba){
		iniciaAplicacaoMatriz('IntranetProdução');
		realizaLoginIntraNetProducao(tipoAba);
	}





	public static void fluxoDesbloqueio(List<MassaDados> listaExcel, String tipoAba){
		String gravaExcel = 'false';
		String validarApolice;
		countEnviado = 0;
		for (int i = 0; i < listaExcel.size();i++){
			try{
				atualizaExcel = ' ';
				validarApolice = PesquisaApoliceDesbloqueio(listaExcel.get(i).getApolice());
				if(validarApolice.contains('Não foram encontrados'.toString())){
					atualizaExcel = 'ErroApolice';
					atualizaExcel(listaExcel.get(i).getApolice(),Integer.parseInt(listaExcel.get(i).getLinha()));
					recomecaAplicacao(tipoAba);
					countEnviado++;
				}else{
					gravaExcel = fluxoLiberacaoApoliceBloqueios(5);
					if(gravaExcel.equals('true')){
						switchAtualizarExcel.switchUpdateExcel('DesbloqueioProposta', caminhoDiretorioExcel ,listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()));
					}else if(gravaExcel.equals('false')){
						atualizaExcel = 'SemBloqueio';
						atualizaExcel(listaExcel.get(i).getApolice(),Integer.parseInt(listaExcel.get(i).getLinha()));
					}else if(gravaExcel.equals('SemItens')){
						atualizaExcel = 'SemItens';
						atualizaExcel(listaExcel.get(i).getApolice(),Integer.parseInt(listaExcel.get(i).getLinha()));
					}else if(validaNumeroPoposta(gravaExcel)) {
						println 'Grava numero da apolise -' + gravaExcel;
						auxiliares.WriteExcelFactory.updateSheetLine(listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, 'Processado', 2, 'DesbloqueioProposta');
						auxiliares.WriteExcelFactory.updateSheetLine(listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, gravaExcel, 4, 'DesbloqueioProposta');
					}else{
						atualizaExcel = 'ErroSiteErro'
						auxiliares.WriteExcelFactory.updateSheetLine(listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, 'Processado', 2, 'DesbloqueioProposta');
						auxiliares.WriteExcelFactory.updateSheetLine(listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()), caminhoDiretorioExcel, gravaExcel, 4, 'DesbloqueioProposta');
					}
					clicarProducao();
					countEnviado++;
				}
			} catch (Exception e) {
				countEnviado++;
				atualizaExcel = 'ErroSite';
				atualizaExcel(listaExcel.get(i).getApolice(),Integer.parseInt(listaExcel.get(i).getLinha()));
				inicia(tipoAba);
				fluxoSinistroProdutoDesbloqueio();
			}
		}
	}


	/**
	 * Realiza pesquisa apolice desbloquio
	 * @param apolice
	 * @return
	 */
	public static String PesquisaApoliceDesbloqueio(String apolice){
		alteraJanejaGenerica(1,10)
		//Clica propostaBloqueada
		clickXpath('//*[@id="pBloqueadas"]', 1);
		//altera Lista bloqueios?
		preencheXpath('//*[@id="poliza_Policy"]', apolice, 2);
		//clica Pesquisar
		clickXpath('//*[@id="btnBuscar"]', 1);
		String valida = validarElementoErro('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 2);
		println valida
		return valida

	}

	/**
	 * Producao & Sinistros
	 * Produção Diversos
	 * PRODUÇÃO
	 */
	public static void fluxoSinistroProdutoDesbloqueio(){
		//clicaNoMenuTelaMatriz("Producao & Sinistros");
		//clicaNoSubMenuTelaMatriz("Produção Diversos");
		//clickXpath('//*[@id="topicNav"]/li[5]/a', 1);
		
		clickXpath('//*/nx-epac-header/nx-epac-navigation/div/div[1]/nx-epac-navigation-desktop/div[1]/div/div/ul/li[1]/a/span', 20);
		clicarProducao();
	}


	public static void clicarProducao(){
		clickXpath('/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul/li[6]/a', 1);
	}


	/**Atualiza planilha conforme a necessidade
	 * @param numeroApolice
	 * @param linha
	 */
	public static void atualizaExcel(String numeroApolice, int linha){
		println atualizaExcel;
		if(atualizaExcel.equals("SemBloqueio")){
			auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha, caminhoDiretorioExcel, 'Processado', 2, 'DesbloqueioProposta');
			auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha, caminhoDiretorioExcel, 'Sem nota [422 - Proposta não desbloqueada', 4, 'DesbloqueioProposta');
		}else if (atualizaExcel.equals("ErroSite")){
			auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha, caminhoDiretorioExcel, 'Processado', 2, 'DesbloqueioProposta');
			auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha, caminhoDiretorioExcel, 'Erro ao realizar Fluxo desbloqueio Proposta [Erro no Site]', 4, 'DesbloqueioProposta');
		}else if (atualizaExcel.equals("ErroApolice")){
			auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha, caminhoDiretorioExcel, 'Processado', 2, 'DesbloqueioProposta');
			auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha, caminhoDiretorioExcel, 'Não foram encontrados dados para a ação selecionada.', 4, 'DesbloqueioProposta');
		}else if (atualizaExcel.equals("missing 931")){
			auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha, caminhoDiretorioExcel, 'Processado', 2, 'DesbloqueioProposta');
			auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha, caminhoDiretorioExcel, 'Sem nota [931] - Proposta não desbloqueada', 4, 'DesbloqueioProposta');
		}else if (atualizaExcel.equals("SemItens")){
			auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha, caminhoDiretorioExcel, 'Processado', 2, 'DesbloqueioProposta');
			auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha, caminhoDiretorioExcel, 'Proposta Não possui Itens para desbloqueio', 4, 'DesbloqueioProposta');
		}
		else if (atualizaExcel.equals("ErroSiteErro")){
			auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha, caminhoDiretorioExcel, 'NProcessado', 2, 'DesbloqueioProposta');
			auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha, caminhoDiretorioExcel, 'Erro ao realizar Fluxo desbloqueio Proposta [Erro no Site] - não reprocessar', 4, 'DesbloqueioProposta');
		}
	}


	public static void recomecaAplicacao(String tipoAba){
		finalizar();
		inicia(tipoAba);
		fluxoSinistroProdutoDesbloqueio()
	}


	/**Recebe valor padroniza valores valida se contem numero
	 * @param valor
	 */
	public static boolean validaNumeroPoposta(String valor) {
		boolean valornumero = false;
		String valorPadronizado = valor.replaceAll("\\s+" , " ").replaceAll("-" , " ").replaceAll("/" , " ").replaceAll(" " , "");


		String [] vertor = valorPadronizado.split('');

		for(int i = 0; i<vertor.size(); i++){
			if(vertor[i].equals('0')||vertor[i].equals('1')||vertor[i].equals('2')||vertor[i].equals('3')||vertor[i].equals('4')||vertor[i].equals('5')||vertor[i].equals('6')||vertor[i].equals('7')||vertor[i].equals('8')||vertor[i].equals('9')){
				valornumero = true;
				break
			}
		}
		return valornumero;
	}




	/**
	 * @author T_Dvlopes
	 * @return
	 */
	public static boolean finalizaProcesso(){
		System.err.println('finaliza Processo - countEnviado ['+countEnviado+'] tamanhoListaOriginal ['+tamanhoListaOriginal+']')
		boolean processoFinalizado = false;
		if(countEnviado == tamanhoListaOriginal){
			diretorio = createDiretory('Evidencias//RoboDesbloqueioProposta Processado '+dataRecuerada);
			copiarExcel(caminhoDiretorioExcel, diretorio+"\\RoboDesbloqueioProposta Processado-"+dataRecuerada+".xlsx");
			//copiarExcel(ExcelCopiado, '\\x:\\RoboAgendamento Processado-'+dataRecuerada+'.xlsx');
			//deletarArquivosExcel(diretorioToDelete);
			deleteFileDiretory(caminhoDiretorioExcel);
			processoFinalizado = true;
		}
		return processoFinalizado;
	}



	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarDesbloqueioProposta(String tipoAba){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/DesbloqueioProposta/ConfiguracoesDesbloqueio.txt";
		lerConfigurações(pathTxtEmail);

		path1 = '\\C:\\Users\\Public\\Evidencias\\RoboDesbloqueioProposta Processado '+dataRecuerada+'\\RoboDesbloqueioProposta Processado-'+dataRecuerada+'.xlsx';
		nome1 = 'Desbloqueio Proposta Processado-'+dataRecuerada+'.xlsx';

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
			enviarDesbloqueioProposta(tipoAba);
		}
	}













}
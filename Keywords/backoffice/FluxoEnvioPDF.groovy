package backoffice

import static org.junit.Assert.*

import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import auxiliares.RegrasEmailDestination
import bean.MassaDados

import auxiliares.MenuMatriz
import java.util.Set;
import java.util.HashSet;
import java.util.List

public class FluxoEnvioPDF extends backofficeFuncionalidades{

	private static renamePathFileValue = "C:\\Users\\Public\\fichero.PDF"
	private static renamePathFileValueEML = "C:\\Users\\Public\\documento.EML"
	private static List<MassaDados> listaExcel;
	private static String caminhoExcel = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\EnvioPDF\\RoboEnvioPdf.xlsx";
	private static String novoNomePDF
	private static String novoNomeEML
	private static String novoDiretorio
	private static String apolice
	private static String tomador
	private static boolean bfichaGestao
	private static boolean bmanipulaArquivo
	private static boolean bpesquisaDadosGerais
	private static boolean benviaDocumentoNoEmail
	private static int corretores
	private static boolean download = false

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static fluxoEnvioDePDF(String aba){
		carregalistaExcel(aba)
		if(carregaDados()){
			fluxoCompletoEnvioDePDF(aba)
			//reprocessaFalhas(aba)
			enviarEmailPdf(aba)
		}
		finalizar();
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoEnvioDePDF(String aba){

		fluxoLimpeza()
		acessoAmbiente(aba)
		System.err.println()
		fluxoDownload(aba)
		acessoAmbiente(aba)
		fluxoEnvioEmail(aba)
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param aba
	 * @return
	 */
	public static acessoAmbiente(String aba){
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands(aba);
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static fluxoDownload(String aba){
		if(carregaDadosDownload()){
			System.err.println('Fluxo Download - Registros ['+listaExcel.size()+']')
			WebUI.delay(10);
			MenuMatriz.newFluxoGestao("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[2]/div/div[1]/ul")
			WebUI.delay(17);
			alteraJanejaGenerica(1, 5)
			WebUI.maximizeWindow()
			for (int i = 0; i < listaExcel.size();i++){
				//System.err.println("Linha ["+listaExcel.get(i).getLinha()+']')
				if(listaExcel.get(i).getDocumento().contains("Processar".toString())){
					download = false
					System.err.println("["+listaExcel.get(i).getLinha()+'] Status ['+ listaExcel.get(i).getStatus()+"] TX NOTA ["+listaExcel.get(i).getTxnota()+"] ENVIO ["+listaExcel.get(i).getEnvio()+"]")
					WebUI.delay(5);
					fichaGestao(listaExcel.get(i).getApolice(), listaExcel.get(i).getAplica(),listaExcel.get(i).getEnvio(), listaExcel.get(i).getNota(), listaExcel.get(i).getTxnota())
					WebUI.delay(3);
					if(download){
						manipulaArquivo(listaExcel.get(i).getApolice(), listaExcel.get(i).getTxnota(), listaExcel.get(i).getRamol().replace(".",""), listaExcel.get(i).getAplica() )
					}
					salvarFluxoDownload(Integer.parseInt(listaExcel.get(i).getLinha()), aba, listaExcel.get(i).getRamol(), listaExcel.get(i).getTxnota()+" "+listaExcel.get(i).getEnvio())
					MenuMatriz.inicioFichaGestao()
					WebUI.delay(5)
				}
				//				MenuMatriz.inicioFichaGestao()
				//				WebUI.delay(5)
			}
			//			MenuMatriz.inicioFichaGestao()
			//			WebUI.delay(5)

			fecharJanelaAtual(1)
			fecharJanelaAtual(0)
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param aba
	 * @return
	 */
	public static fluxoEnvioEmail(String aba){
		carregalistaExcel(aba)
		if(carregaDadosEmail()){
			listaExcel = removeCorretoresDuplicados( listaExcel)

			//		for(int j = 0; j < listaExcel.size(); j++){
			//			System.err.println("LINHA ["+Integer.parseInt(listaExcel.get(j).getLinha())+"] APOLICE ["+listaExcel.get(j).getApolice()+"]")
			//		}

			System.err.println(' Fluxo Envio de Email - Registros ['+listaExcel.size()+']')
			alteraJanejaGenerica(0, 5)
			MenuMatriz.consultasEstatisticas()
			MenuMatriz.consultasGerais()
			WebUI.delay(15);
			alteraJanejaGenerica(1, 5)
			WebUI.maximizeWindow()
			for (int i = 0; i < listaExcel.size();i++){
				if(listaExcel.get(i).getEmail().contains("Enviar".toString())){
					System.err.println("LINHA ["+Integer.parseInt(listaExcel.get(i).getLinha())+"] APOLICE ["+listaExcel.get(i).getApolice()+"]")
					WebUI.delay(3);
					pesquisaDadosGerais(listaExcel.get(i).getApolice())
					WebUI.delay(3);
					enviaDocumentoNoEmail(aba, listaExcel.get(i).getEmailde(), listaExcel.get(i).getEmailpara(), listaExcel.get(i).getApolice(), listaExcel.get(i).getRamol().replace(".",""))
					salvarFluxoEnvioEmail(Integer.parseInt(listaExcel.get(i).getLinha()), aba)
				}
			}
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param listaExcel
	 * @return
	 */
	public static List<MassaDados> removeCorretoresDuplicados(List<MassaDados> listaExcel){
		List<MassaDados> listaCorretores = new ArrayList<MassaDados>()
		HashSet<String> corretor = new HashSet<String>();

		for(int e = 0 ; e < listaExcel.size(); e++){
			corretor.add(listaExcel.get(e).getApolice())
		}

		Object[] myArr = corretor.toArray();

		for(int i = 0; i<myArr.length; i++){
			for(int j = 0; j < listaExcel.size(); j++)
				if(listaExcel.get(j).getApolice().contains(myArr[i].toString())){
					listaCorretores.add(listaExcel.get(j))
					break
				}
		}
		return listaCorretores
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param linha
	 * @param aba
	 * @return
	 */
	public static salvarFluxoDownload(int linha , String aba,String ramol, String txNota){
		System.err.println("bfichaGestao ["+bfichaGestao+"]" )
		System.err.println("bmanipulaArquivo ["+bmanipulaArquivo+']')
		System.err.println("download ["+download+']')

		if(!download){
			salvarEvidencia(linha,caminhoExcel, "Processado", 13,  aba)
			salvarEvidencia(linha,caminhoExcel, "Processado", 14,  aba)
			salvarEvidencia(linha,caminhoExcel, "Não Enviado", 15,  aba)
			salvarEvidencia(linha,caminhoExcel, "Arquivo - "+txNota.replaceAll(" ...", "")+" Não encontrado para download ", 16,  aba)
		}
		else if (!bfichaGestao){
			salvarEvidencia(linha,caminhoExcel, "Processar", 14,  aba)
			salvarEvidencia(linha,caminhoExcel, "Falha no ficha e gestão", 16,  aba)
		}
		else if (!bmanipulaArquivo){
			salvarEvidencia(linha,caminhoExcel, "Processar", 14,  aba)
			salvarEvidencia(linha,caminhoExcel, "Falha ao manipular arquivo - "+ramol+" Não encontrado", 16,  aba)
		}
		if(bfichaGestao && bmanipulaArquivo && download ){
			salvarEvidencia(linha,caminhoExcel, "Processado", 14,  aba)
			salvarEvidencia(linha,caminhoExcel, "Enviar", 15,  aba)
			salvarEvidencia(linha,caminhoExcel, "Arquivo - "+txNota.replaceAll(" ...", "")+" localizado ", 16,  aba)
		}
	}

	/**
	 * @author be54955
	 * @param download
	 * @return
	 */
	public static String msgDownloaded(boolean download){
		System.out.println("MSG ALERT ["+download+"]")
		String msg
		if(download){
			msg = "Arquivo indisponível para download"
		} else {
			msg=""
		}
		return msg;
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param linha
	 * @param aba
	 * @return
	 */
	public static salvarFluxoEnvioEmail(int linha , String aba){
		System.err.println(bpesquisaDadosGerais)
		System.err.println(benviaDocumentoNoEmail)

		if(bpesquisaDadosGerais && benviaDocumentoNoEmail){
			salvarEvidencia(linha,caminhoExcel, "Enviado", 15,  aba)
		}
		else if (!bpesquisaDadosGerais){
			salvarEvidencia(linha,caminhoExcel, "Enviar", 15,  aba)
			salvarEvidencia(linha,caminhoExcel, "Falha na pesquisa em dados gerais", 16,  aba)
		}
		else if (!benviaDocumentoNoEmail){
			salvarEvidencia(linha,caminhoExcel, "Processar", 14,  aba)
			salvarEvidencia(linha,caminhoExcel, "Enviar", 15,  aba)
			salvarEvidencia(linha,caminhoExcel, "Falha ao enviar documento por email", 16,  aba)
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param apolice
	 * @param aplica
	 * @param envio
	 * @param nota
	 * @return
	 */
	public static fichaGestao(String apolice, String aplica, String envio, String nota, String txnota){
		System.out.println()
		try{
			novaFichaGestaoPorAtributo(apolice,aplica)
			WebUI.delay(10);
			verMais()
			selecionaItemNaTabela(envio, txnota,totalRegistros())
			WebUI.delay(3);
			bfichaGestao = true
			bmanipulaArquivo = true
			bpesquisaDadosGerais= true
			System.err.println("fichaGestao - bfichaGestao ["+bfichaGestao+"] bmanipulaArquivo ["+bmanipulaArquivo+"] download ["+download +"]")
			System.err.println()

		} catch (Exception e){
			bfichaGestao = false
			bmanipulaArquivo = false
			bpesquisaDadosGerais = false
			benviaDocumentoNoEmail = false
		}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static void verMais(){
		System.out.println()
		while(existeVerMais()){
			clicarVerMais()
		}
	}

	/**
	 * 
	 * @return
	 */
	public static void clicarVerMais(){
		String valor = "Ver Mais"
		String xpath = '/html/body/div[1]/div/app-root/file-management/app-listfilemanagement/div'
		String tag ="button";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().contains(valor.toString())){
				listOfElements[i].click()
				break
			}
		}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean existeVerMais(){
		String valor = "Ver Mais"
		boolean button = false
		WebUI.delay(10)
		String xpath = '/html/body/div[1]/div/app-root/file-management/app-listfilemanagement/div'
		String tag ="button";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().contains(valor.toString())){
				button = true
				break
			}
		}
		System.out.println("EXISTE VER MAIS ["+button+"]")
		return button
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static int totalRegistros(){
		int linhas
		try{
			WebUI.delay(2)
			String xpath = '//*[@id="tablaLista"]/app-list-file/div/div/div/table/tbody'
			String tag ="tr";
			WebUI.delay(2)
			selecionaListaDeElementos(xpath,tag)
			linhas = listOfElements.size()
			System.out.println("["+linhas+"]")
		} catch (Exception e){}
		return linhas

	}

	/**
	 * @author ALZ_ServiceQA
	 * @param apolice
	 * @param nomeCorretor
	 * @return
	 */
	public static pesquisaDadosGerais(String proposta){
		System.err.println("pesquisa dados gerias - bmanipulaArquivo ")
		try{
			//Proposta
			preencheXpath('//*[@id="apolice"]', proposta, 1);
			//Pesquisar
			clickXpath('//*[@id="o_10"]', 5);
			WebUI.delay(10);
			apolice = recuperaValor('//*[@id="apoliceSusep"]', 'value', 2);
			tomador = recuperaValor('//*[@id="dadosgeraisContent"]/table[1]/tbody/tr/td[2]/input', 'value', 2);
			System.err.println("apolice ["+apolice+"] tomador ["+tomador+"]")
			bpesquisaDadosGerais = true
			benviaDocumentoNoEmail = true
		} catch (Exception e){
			bpesquisaDadosGerais = false
			benviaDocumentoNoEmail = false
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param nomeCorretor
	 * @return
	 */
	public static enviaDocumentoNoEmail(String aba, String de, String para, String apolice, String ramol){
		System.err.println("["+aba+"] ["+de+"] ["+para+"] ["+apolice+"] ["+ramol+"]")
		try{
			//ramol = "RESPONSABILIDADE CIVIL PROFISSIONAL AGCS"
			WebUI.delay(10);
			if(enviarDocumentoPorEmail(aba, de, para, apolice ,ramol)){
				WebUI.delay(10);
				deletarPasta(novoDiretorio)
				deleteFileDiretory("C:\\Users\\Public\\"+novoDiretorio+".zip")
				WebUI.delay(5);
				alteraJanejaGenerica(1, 5)
				WebUI.delay(5);
				//Nova pesquisa
				clickXpath('//*[@id="-809"]/div[1]/span', 5);
				benviaDocumentoNoEmail = true
			} else {
				WebUI.delay(5);
				alteraJanejaGenerica(1, 5)
				WebUI.delay(5);
				//Nova pesquisa
				clickXpath('//*[@id="-809"]/div[1]/span', 5);
				benviaDocumentoNoEmail = false
			}
		} catch (Exception e){

		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param apolice
	 * @return
	 */
	public static manipulaArquivo(String apolice, String txnota, String ramol, String aplica){
		System.err.println("manipulaArquivo - bfichaGestao ["+bfichaGestao+"] bmanipulaArquivo ["+bmanipulaArquivo+"]")
		System.err.println(ramol)
		if(bfichaGestao){
			try{
				novoDiretorio = apolice+"-"+ramol
				System.err.println("novoDiretorio "+novoDiretorio)
				createDiretory(novoDiretorio)
				WebUI.delay(5);
				novoNomePDF = apolice+'_'+txnota+'_'+aplica
				renomearArquivo(renamePathFileValue, novoNomePDF,".pdf")
				WebUI.delay(10);
				if(!validaExistenciaDeArquivo('C:\\Users\\Public\\'+novoNomePDF+'.pdf')){
					WebUI.delay(2);
					moveArquivoPDF('C:\\Users\\Public\\'+novoNomePDF+".pdf", 'C:\\Users\\Public\\'+novoDiretorio, novoNomePDF+'.pdf')
					WebUI.delay(5);
					//bmanipulaArquivo = true
				} else {
					bfichaGestao = false
				}
				WebUI.delay(4);
				zipDirectory('C:/Users/Public/'+novoDiretorio, 'C:/Users/Public/'+novoDiretorio+'.zip')
				bfichaGestao = true

			} catch (Exception e){
				bmanipulaArquivo = false
			}
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param i
	 * @param caminhoExcel
	 * @param status
	 * @param cell
	 * @param aba
	 * @return
	 */
	public static salvarEvidencia(int i,String caminhoExcel, String status , int cell, String aba){
		auxiliares.WriteExcelFactory.updateSheetLine("", i,caminhoExcel, status, cell, aba);
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param corretor
	 * @return
	 */
	public static boolean enviarDocumentoPorEmail(String aba, String de, String para, String apolice, String ramol){
		boolean enviar
		try{
			novoDiretorio = apolice+"-"+ramol
			pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Auto/EnviaPDF/ConfiguracoesEnvioPdfDocumento.txt";
			lerConfigurações(pathTxtEmail)
			List<String> anexos = new ArrayList<>();
			anexos[0] = 'C:\\Users\\Public\\'+novoDiretorio+'.zip';
			anexos[1] = novoDiretorio+'.zip';
			System.out.println(anexos[0]+" - "+anexos[1])
			
			if(sendEmail.CreateEmail.createEmailSendMultipleFilesCorretor(de, para+", enviopdfcorporate@allianz.com.br", pwdsender, anexos, host, porta, "emailCorretor", tomador, apolice.replaceAll("—", ""))){
				WebUI.delay(10);
				deletarPasta(novoDiretorio)
				deleteFileDiretory("C:\\Users\\Public\\"+novoDiretorio+".zip")
			}
			enviar = true
		} catch (Exception e){
			enviar = false
		}
		return enviar
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param pathFileValue
	 * @param name
	 * @return
	 */
	public static renomearArquivo(String pathFileValue, String name, String extencao){
		System.err.println('pathFileValue: '+pathFileValue)
		File f = new File(pathFileValue);
		if (f.renameTo(new File("C:\\Users\\Public\\"+name+extencao)))
		{
			System.out.println("Arquivo renomeado");
		}
		else{
			System.out.println("Arquivo não renomeado");
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaItemNaTabela(String data, String txnota, int linhas){
		try{
			selecionaItemNaTabelaFichaGestao(data,txnota,linhas)
		} catch (Exception e){
			System.err.println("Exception - selecionaItemNaTabela")
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 * Metodo de seleção de itens na tela de ficha e gestão
	 */
	public static selecionaItemNaTabelaFichaGestao(String data, String txnota,int linhas){
		System.err.println()
		download = false
		try{
			for(int i=0; i<=linhas; i++){
				System.out.println("Linha ["+i+"]")
				if(identificaDadosNaLinha(data, txnota, retornaLinhaTabelaFichaGestao(i))){
					//if(painelDownload(i)){
					if(clickAnexoFichaGestão(i, data, txnota)){
						fechaCONSULTAIMAGEMLI()
						download = true
					}
					break
				}
			}
		} catch (Exception e){
			System.err.println("Exception - selecionaItemNaTabela")
		}
	}

	/**
	 * @author BE54955
	 * @return
	 */
	public static fechaCONSULTAIMAGEMLI(){
		alteraJanejaGenerica(2, 10)
		clickXpath('//*[@id="lnkTopLink_Close"]', 2);
		fechaAlerta()
		alteraJanejaGenerica(1, 2)
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaProcesso(String processo){
		String xpath = '//*[@id="tipoReferencia"]'
		String tag ="option";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().contains(processo.toString())){
				listOfElements[i].click()
				break
			}
		}
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailPdf(String tipoAba){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/EnvioPdf/ConfiguracoesEnvioPdf.txt";
		lerConfigurações(pathTxtEmail)
		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
		carregalistaExcel(tipoAba)
		//		if(!carregaDados()){
		List<String> anexos = new ArrayList<>();
		anexos[0] = caminhoExcel
		anexos[1] = "RoboEnvioPdf.xlsx"
		sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
		//		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean carregaDados(){
		boolean carregar = false
		for (int i = 0; i < listaExcel.size();i++){
			if(listaExcel.get(i).getStatus().contains("Processar".toString())){
				System.err.println(i+' - Status - '+ listaExcel.get(i).getStatus())
				carregar = true
				break
			}
		}
		System.err.println('carregaDados ['+ carregar+']')
		return carregar
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean carregaDadosDownload(){
		boolean carregar = false
		for (int i = 0; i < listaExcel.size();i++){
			if(listaExcel.get(i).getDocumento().contains("Processar".toString())){
				System.err.println(i+' - Download - '+ listaExcel.get(i).getDocumento())
				carregar = true
				break
			}
		}
		System.err.println('carregaDados ['+ carregar+']')
		return carregar
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean carregaDadosEmail(){
		boolean carregar = false
		for (int i = 0; i < listaExcel.size();i++){
			if(listaExcel.get(i).getEmail().contains("Enviar".toString())){
				System.err.println(i+' - Email - '+ listaExcel.get(i).getEmail())
				carregar = true
				break
			}
		}
		System.err.println('carregaDados ['+ carregar+']')
		return carregar
	}

	/**
	 * @author T_DCDias
	 * @param aba
	 * @return
	 */
	public static carregalistaExcel(String aba){
		listaExcel = switchLeitorExcel.switchExcel(caminhoExcel, aba);
	}

	/**
	 * @author T_DCDias
	 * @param caminhoFonte
	 * @param caminhoSaida
	 * @return
	 */
	public static moveArquivoPDF(String caminhoFonte, String caminhoSaida, String nome){
		try{
			deletaArquivoGenerico(caminhoSaida+"\\"+nome);
			// Arquivo a ser movido
			File arquivo = new File(caminhoFonte);
			if (!arquivo.exists()) {
				System.out.println("Arquivo não encontrado");
			} else {

				// Diretorio de destino
				File diretorioDestino = new File(caminhoSaida);

				// Move o arquivo para o novo diretorio
				boolean sucesso = arquivo.renameTo(new File(diretorioDestino, arquivo.getName()));
				if (sucesso) {
					bmanipulaArquivo = true
					System.out.println("Arquivo movido para '" + diretorioDestino.getAbsolutePath() + "'"+bmanipulaArquivo );
				} else {
					System.out.println("Erro ao mover arquivo '" + arquivo.getAbsolutePath() + "' para '"
							+ diretorioDestino.getAbsolutePath() + "'");
				}
			}
		} catch (Exception e){}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static reprocessaFalhas(tipoAba){
		carregalistaExcel(tipoAba)
		if(carregaDados()){
			fluxoCompletoEnvioDePDF(tipoAba)
			reprocessaFalhas(tipoAba)
		}
	}
}



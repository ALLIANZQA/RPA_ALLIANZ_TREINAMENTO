package agcs

import java.util.List

import backoffice.backofficeFuncionalidades
import bean.MassaDados
import auxiliares.switchLeitorExcel
import auxiliares.switchAtualizarExcel
import sendEmail.envioDeEmail;
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.updateExcel
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import auxiliares.MenuMatriz

public class CotacoesEspeciais extends backofficeFuncionalidades{



	public static String caminhoDiretorioExcel = "C://Users//Public//RPA_ALLIANZ//Data Files//AGCS//RoboCotacaoEspecial.xlsx";
	public static String ExcelCopiado;
	public static String dataRecuerada = getDateHour();
	public static String diretorio;
	private static String diretorioIMG;
	public static String diretorioToDelete;
	public static int tamanhoListaOriginal;
	public static List<MassaDados> listaExcel;
	public static String valorRecuperado
	public static String emailCDAGRUP

	private static boolean geraPedido

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static cotacoesEspeciais(String tipoAba){
		fluxoCompletoCotacoesEspeciais(tipoAba);

		try{
			finalizar();
		} catch (Exception e){}

		enviarCotacoesEspeciais(tipoAba);
		System.err.println('\nFinalizado...\n Casos finalizados  ['+ tamanhoListaOriginal+']\n');
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static fluxoCompletoCotacoesEspeciais(String tipoAba){
		diretorioIMG = createDiretory('Renovações Cotação Especiais - Evidencias')
		diretorio = createDiretory('Renovações Cotação Especiais - PROCESSANDO '+dataRecuerada);
		diretorioToDelete = diretorio;
		ExcelCopiado = copiarExcel(caminhoDiretorioExcel, diretorio+"\\RenovacaoCotacoes.xlsx");
		listaExcel = switchLeitorExcel.switchExcel(ExcelCopiado, tipoAba);
		tamanhoListaOriginal = listaExcel.size();

		if(tamanhoListaOriginal>0){
			/**
			 * Efetua inicialização da aplicação Matriz e faz o fluxo de envio de pendência
			 */
			iniciaAplicacaoMatriz('IntranetProdução');
			realizaLoginIntraNetProducao(tipoAba);
			WebUI.delay(10);
			//		    salvarEvidencia( diretorioIMG, '110142550')
			emailCDAGRUP = emailCDAGRUPRAMO(listaExcel)
			fluxoRenovacoes(listaExcel, tipoAba);
			/**
			 * Processa as pendências que apresentaram falhas
			 */
			finalizaProcesso();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param listaExcel
	 * @param tipoAba
	 */
	public static void fluxoRenovacoes(List<MassaDados> listaExcel, String tipoAba){
		for (int i = 0; i < listaExcel.size();i++){
			geraPedido = true
			System.err.println( "RAMO ["+listaExcel.get(i).getRamoModalidade() +"] GERA PEDIDO ["+listaExcel.get(i).getGeraPedido() +"] NOTA GERADA ["+ listaExcel.get(i).getNotaGerada()+"]")
			try{
				if(listaExcel.get(i).getGeraPedido().equals("Gerar") ){
					WebUI.waitForPageLoad(20)
					NewfluxoCompletoConsultasGerais();  System.err.println(".....1")
					FluxoPesquisaNumeroApoliceTelaConsultasGerais(listaExcel.get(i).getApolice(), listaExcel.get(i).getRamoModalidade());System.err.println(".....2")
					WebUI.delay(10);
					clicarRecibosFrota(listaExcel.get(i).getRamoModalidade());System.err.println(".....3")
					WebUI.delay(5);
					validaValueCheck(recuperarQuantidadePendencia(listaExcel.get(i).getRamoModalidade()));System.err.println(".....4")
				}
				if(!alertListBlocList(valueCheck)){
					if(listaExcel.get(i).getGeraPedido().equals("Gerar") ){
						WebUI.delay(5);
						clicarBotaoResultado();System.err.println(".....5")
						recuperarSinistros(listaExcel.get(i), listaExcel.get(i).getRamoModalidade());  System.err.println(".....6")
					}
					if(!alertListBlocList(valueCheck)){
						if(listaExcel.get(i).getGeraPedido().equals("Gerar") ){
							if(premioTotal>0){
								alteraJanejaGenerica(0, 5)
								newfluxoCotacoesEspeciais(); System.err.println(".....7")
								fluxoAtivaçãoPedidoSelecionarRamoModalidade(listaExcel.get(i).getCodigoCorretor(), listaExcel.get(i).getQtdSinistro(), listaExcel.get(i).getAgrupamentoRamo(), listaExcel.get(i).getRamoModalidade());System.err.println(".....8")
								fluxoPreencherCamposDadosCorretor(listaExcel.get(i).getCnpjCpf(), listaExcel.get(i).getNomeRazaoSocial(),saparaDataPorRamoPlataforma(listaExcel ,i,listaExcel.get(i).getRamoModalidade()) , 'NÃO'); System.err.println(".....9")
								preencherCampoInformativoCliente(listaExcel.get(i)); System.err.println(".....10")
								alteraJanejaAtivacaoPedido();
								validaValueCheck(valorRecuperado = recuperarNumeroRenovacaApolice()); System.err.println(".....11")
								System.err.println("valorRecuperado "+valorRecuperado)
								String pedido  = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','innerText' , 2);
								salvarInfoTelaAtualizarExcel(pedido, Integer.parseInt(listaExcel.get(i).getLinha()), ExcelCopiado , tipoAba, 37)
								valueCheck = 'enviado';
								switchAtualizarExcel.switchUpdateExcel('CotacoesEspeciais', ExcelCopiado ,listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()));
								auxiliares.WriteExcelFactory.updateSheetLine("", Integer.parseInt(listaExcel.get(i).getLinha()),ExcelCopiado, "Gerado", 38, 'CotacoesEspeciais');
								fecharJanela();
								alteraJanejaGenerica(0, 5)
							} else {
								geraPedido = false
								String msg = valueCheck
								valueCheck = 'Pedido não gerado, erro ao obter informações.';
								salvarEvidencia( diretorioIMG, listaExcel.get(i).getLinha()+" "+listaExcel.get(i).getApolice()+" "+dataRecuerada)
								switchAtualizarExcel.switchUpdateExcel('CotacoesEspeciais', ExcelCopiado ,listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()));
								iniciaAplicacaoMatriz('IntranetProdução');
								realizaLoginIntraNetProducao(tipoAba);
							}
						}

						System.out.println("geraPedido ["+geraPedido+"]")

						if(geraPedido){
							try{
								if(listaExcel.get(i).getGeraPedido().equals("Gerado")){
									System.out.println(listaExcel.get(i).getPedidoGerado())
									valorRecuperado = listaExcel.get(i).getPedidoGerado().replaceAll("[a-zA-Z ú .]", "")
									System.out.println(valorRecuperado)
								}
							} catch (Exception e){}

							newfluxoCotacoesEspeciais(); System.err.println(".....12")
							trocaJanela('Pesquisa Pedido');
							pesquisarValorRecuperado(valorRecuperado); System.err.println(".....13")
							fichaGestao()
							String msgFG = recuperaValor('//*[@id="tablaLista"]/app-ngx-message-error/div/div/div/nx-message','innerText' , 2);
							System.out.println(msgFG )

							if(!verificaPendencia()){
								MenuMatriz.notaAgendaFichaGestao()
								alteraJanejaGenerica(3, 5)
								fluxoPreencherModeloDescricao(listaExcel.get(i).getRamoModalidade()); System.err.println(".....14")
								fluxoEditarTexto(listaExcel.get(i),valorRecuperado); System.err.println(".....15")
								alteraJanejaGenerica(2, 5)
							}

							String pendencia  = retornaLinhaTabelaFichaGestao(indexPendencia())
							System.out.println(pendencia)
							//PRINT DA TELA
							salvarEvidencia( diretorioIMG, listaExcel.get(i).getLinha()+" "+listaExcel.get(i).getApolice()+" "+dataRecuerada)
							fluxoFecharJanelas();
							alteraJanejaGenerica(0, 5)
							valueCheck = 'enviado';
							salvarInfoTelaAtualizarExcel(valueCheck, Integer.parseInt(listaExcel.get(i).getLinha()), ExcelCopiado , tipoAba, 34)
							salvarInfoTelaAtualizarExcel("", Integer.parseInt(listaExcel.get(i).getLinha()), ExcelCopiado , tipoAba, 36)
							salvarInfoTelaAtualizarExcel(pendencia, Integer.parseInt(listaExcel.get(i).getLinha()), ExcelCopiado , tipoAba, 39)
							salvarInfoTelaAtualizarExcel("Gerado", Integer.parseInt(listaExcel.get(i).getLinha()), ExcelCopiado , tipoAba, 40)
						}
					} else {

						valueCheck = 'Não Enviado [ERRO]';
						String msg = valueCheck
						salvarEvidencia( diretorioIMG, listaExcel.get(i).getLinha()+" "+listaExcel.get(i).getApolice()+" "+dataRecuerada)
						switchAtualizarExcel.switchUpdateExcel('CotacoesEspeciais', ExcelCopiado ,listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()));
						auxiliares.WriteExcelFactory.updateSheetLine("", Integer.parseInt(listaExcel.get(i).getLinha()),  ExcelCopiado, msg, 34,'CotacoesEspeciais');
						auxiliares.WriteExcelFactory.updateSheetLine("", Integer.parseInt(listaExcel.get(i).getLinha()),  ExcelCopiado, "Não há dados para mostrar em FG", 37,'CotacoesEspeciais');
						iniciaAplicacaoMatriz('IntranetProdução');
						realizaLoginIntraNetProducao(tipoAba);
					}//RESULTADO DE UMA FROTA

					//ALERTA
				}else {

					valueCheck = 'Não Enviado [ERRO]';
					String msg = valueCheck
					salvarEvidencia( diretorioIMG, listaExcel.get(i).getLinha()+" "+listaExcel.get(i).getApolice()+" "+dataRecuerada)
					switchAtualizarExcel.switchUpdateExcel('CotacoesEspeciais', ExcelCopiado ,listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()));
					auxiliares.WriteExcelFactory.updateSheetLine("", Integer.parseInt(listaExcel.get(i).getLinha()),  ExcelCopiado, msg, 34,'CotacoesEspeciais');
				}//LISTAGEM DE RECIBOS
			} catch (Exception e) {

				valueCheck = 'Não Enviado [ERRO]';
				String msg = valueCheck
				salvarEvidencia( diretorioIMG, listaExcel.get(i).getLinha()+" "+listaExcel.get(i).getApolice()+" "+dataRecuerada)
				switchAtualizarExcel.switchUpdateExcel('CotacoesEspeciais', ExcelCopiado ,listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()));
				auxiliares.WriteExcelFactory.updateSheetLine("", Integer.parseInt(listaExcel.get(i).getLinha()),  ExcelCopiado, msg, 34,'CotacoesEspeciais');
				iniciaAplicacaoMatriz('IntranetProdução');
				realizaLoginIntraNetProducao(tipoAba);
			}

			//for
		}
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static boolean verificaPendencia(){
		System.out.println()
		boolean b = false
		try{
			alteraJanejaGenerica(2, 5)
			String xpath = '//*[@id="tablaLista"]/app-list-file/div/div/div/table'
			String tag ="tr";
			WebUI.delay(2)
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				//System.out.println("LINE ["+i+"] ["+listOfElements[i].getText()+"]")
				if(listOfElements[i].getText().toString().contains("Pendência - CotaçõesEspeciais ")){
					System.out.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
					b = true
					break
				}
			}

		} catch (Exception e){
			b = false
		}
		return b
	}


	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static int indexPendencia(){
		System.out.println()
		int index
		alteraJanejaGenerica(2, 5)
		String xpath = '//*[@id="tablaLista"]/app-list-file/div/div/div/table'
		String tag ="tr";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().toString().contains("Pendência - CotaçõesEspeciais ")){
				System.out.println("FOUND INDEX ["+i+"]")
				index = i
				break
			}
		}
		return index
	}


	/**
	 * Nevaga menu cotacoes especiais
	 */
	public static void NewfluxoCompletoConsultasGerais(){
		try{
			UpdateStatusNaoEnviado();
			MenuMatriz.consultasEstatisticas()
			WebUI.delay(5);
			//Consultas Gerais
			MenuMatriz.consultasGerais()
		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}

	/**
	 * nAVEGA MENUS
	 * produção sinistro
	 * COTACOES ESPECIAIS
	 */
	public static void newfluxoCotacoesEspeciais(){
		try{
			//Produção Sinistros
			MenuMatriz.producaoSinistro()
			MenuMatriz.subMenu("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul", "a", "Cotações especiais")
			WebUI.delay(15);
		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}


	/**
	 * @author ALZ_ServiceQA
	 * @return
	 * Finaliza processo após verifica se todas as pendências foram enviadas, caso não e retornado um 'false'
	 */
	public static boolean finalizaProcesso(){
		boolean processoFinalizado = false;
		diretorio = createDiretory('Evidencias//CotacoesEspeciais'+dataRecuerada);
		copiarExcel(ExcelCopiado, diretorio+"//CotacoesEspeciais-"+dataRecuerada+".xlsx");
		//copiarExcel(ExcelCopiado, '\\x:\\Processados\\CotacoesEspeciais AGCS -'+dataRecuerada+'.xlsx');
		//		copiarExcel(ExcelCopiado, '\\x:\\CotacoesEspeciais AGCS -'+dataRecuerada+'.xlsx');
		deletarArquivosExcel(diretorioToDelete);
		deleteFileDiretory(caminhoDiretorioExcel);
		processoFinalizado = true;
		return processoFinalizado;
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static enviarCotacoesEspeciais(String tipoAba){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/ConfiguracoesCotacoesEspeciaisAGCS/ConfiguracoesCotacoesEspeciaisAGCS.txt";
		lerConfigurações(pathTxtEmail)
		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
		path1 = '\\C:\\Users\\Public\\Evidencias\\CotacoesEspeciais'+dataRecuerada+"\\CotacoesEspeciais-"+dataRecuerada+".xlsx";
		String nomeArquivo = 'CotacoesEspeciais '+dataRecuerada+'.xlsx';

		zipDirectory(diretorioIMG, diretorioIMG+'.zip')
		WebUI.delay(5);

		try{
			listaExcel = switchLeitorExcel.switchExcel(path1, tipoAba);
			if(listaExcel.size()==0){
				List<String> anexos = new ArrayList<>();
				anexos[0] = path1;
				anexos[1] = nomeArquivo;
				//anexos[2] = diretorioIMG+'.zip';
				//anexos[3] = "Renovações Cotação Especiais - Evidencias.zip";
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination+", "+emailCDAGRUP, pwdsender, anexos, host, porta, tipoAba )

			}
		} catch (Exception e){
			enviarCotacoesEspeciais(tipoAba);
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static enviarCotacoesEspeciaisAvulso(String tipoAba){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/ConfiguracoesCotacoesEspeciaisAGCS/ConfiguracoesCotacoesEspeciaisAGCS.txt";
		lerConfigurações(pathTxtEmail)
		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));

		String path1 = 'C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\AGCS\\CotacoesEspeciais-18-11-2021-23-30-13.xlsx';
		String nomeArquivo = 'CotacoesEspeciais-18-11-2021-23-30-13.xlsx';

		try{
			listaExcel = switchLeitorExcel.switchExcel(path1, tipoAba);
			if(listaExcel.size()==0){
				List<String> anexos = new ArrayList<>();
				anexos[0] = path1;
				anexos[1] = nomeArquivo;
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination+", "+emailCDAGRUP('AGCS'), pwdsender, anexos, host, porta, tipoAba )
			}
		} catch (Exception e){
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param listaExcel
	 * @param i
	 * @param ramo
	 * @return
	 */
	public static String saparaDataPorRamoPlataforma(List<MassaDados> listaExcel, int i, String ramo){
		System.err.println('Ramo ['+ramoAGCS(ramo)+']')
		if(ramo.equals(ramoAGCS('500'))){
			return listaExcel.get(i).getDataVencRisco() // DT_FIM_VIGENCIA_ITEM - 26 - COLUNA AA
		} else {
			return listaExcel.get(i).getDataFimVigenciaApolice() //DT_FIM_VIGENCIA_APOLICE   - 13 - COLUNA N
		}
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
	 * @author ALZ_ServiceQA
	 * @param listaExcel
	 * @param tipoAba
	 * @return
	 */
	public static String emailCDAGRUPRAMO(List<MassaDados> listaExcel){
		String email
		for (int i = 0; i < listaExcel.size();i++){
			email = emailCDAGRUP( listaExcel.get(i).getAgrupamentoRamo())
			System.err.println(email)
			break
		}
		return email
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param email
	 * @return
	 */
	public static String emailCDAGRUP(String email){
		Map<String, String> hashRamo = new HashMap<String, String>()
		//hashRamo.put('Transportes/Náutica','thiago.alves@allianz.com.br');
		hashRamo.put('AGCS','mariana.silva@allianz.com.br');
		return hashRamo.get(email.toString());
	}

	/**
	 * @author ALZ_ServiceQA
	 */
	public static void fichaGestao(){
		System.out.println()
		try{
			//WebUI.delay(5);
			alteraJanejaGenerica(2, 5)
			WebUI.maximizeWindow()
			WebUI.delay(3);
			selecionaItemLista('Ficha de Gestão','/html/body/div/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div/ul' , 'span')
			WebUI.delay(3);
		} catch(Exception e){}
	}
}




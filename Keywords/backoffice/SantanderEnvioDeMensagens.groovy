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

import internal.GlobalVariable
import rpacommands.rpaCommands
import auxiliares.ExtractDates
import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.MenuMatriz

public class SantanderEnvioDeMensagens extends rpaCommands{

	private static String tipoProcesso
	private static String alerta
	private static String dataAtual
	private static int coringa = 3
	private static String path
	private static TestData data
	private static TestData dataCap
	private static int  linha
	private static String segurado
	private static String apolice
	private static String FINAL_VIG
	private static String carta
	private static String cartaPeriodo
	private static String tapCap
	private static pathDataFiles = 'Data Files/Auto/RenovacoesSantanderData/RenovaSantander'
	private static pathDataFilessasam2 =  'Data Files/Auto/RenovacoesSantanderData/RenovaSantander_Sasam2'
	private static pathDataFilesCap = 'Data Files/Auto/RenovacoesSantanderData/TabCap (1)'



	/**
	 * @author T_DCDias
	 * @return
	 */
	public static enviaMensagem(String a, String tipo, String cartaEnviar, String[] dataParaExecutar){
		tipoProcesso = tipo
		carta = cartaEnviar
		initDataFilesCap()
		initData(tipo)
		renovaSantanderSasam(a,tipo, cartaEnviar)
		fluxoCompletoEnvioDeMensagens(tipo, dataParaExecutar)
		processaFalhas(tipo,cartaEnviar,dataParaExecutar)
		//	enviaEmailSantanderEnvioDeMensagens(cartaEnviar, dataParaExecutar, tipo)
	}

	/**
	 * @author be54955
	 * @param tipo
	 * @return
	 */
	public static initData(String tipo){
		switch (tipo){
			case "RenovaSantander":
				data = findTestData(pathDataFiles)
				break
			case "RenovaSantanderSASAM2":
				data = findTestData(pathDataFilessasam2)
				break
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static renovaSantanderSasam(String a, String tipo, String cartaEnviar){
		if(tipo.equals("RenovaSantanderSASAM2") && cartaEnviar.contentEquals("15") || tipo.equals("RenovaSantander") && cartaEnviar.contentEquals("15")){
			acessoAmbiente(tipo, a);
			//iniciaAplicacaoMatrizCommands('IntranetProdução');
			//realizaLoginIntraNetProducaoCommands(tipo+a);
		}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static menuSasam2(){
		if(carta.equals("15")){
			WebUI.delay(3)
			//CONSULT. ESTATISTICAS
			MenuMatriz.consultasEstatisticas()
			//CONSUL GERAIS
			MenuMatriz.subMenu('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul', "a","Consultas Gerais")
			WebUI.waitForPageLoad(30)
			alteraJanejaGenerica(1, 5)
			WebUI.maximizeWindow()
		}
	}


	/**
	 * @author T_DCDias
	 * @param setor
	 * @return
	 */
	public static fluxoCompletoEnvioDeMensagens(String tipo, String[] dataParaExecutar){
		System.err.println('fluxo Completo Envio De Mensagens ')
		try{
			for(int h = 0; h < dataParaExecutar.size(); h++){
				System.err.println('\nData ['+h+'] ['+dataParaExecutar[h]+'] ['+tipoProcesso+"] ["+ retornaFINALVIG(tipo)+"]")
				if(validaDataParaPorcessar(dataParaExecutar[h], retornaFINALVIG(tipo), tipo)){
					for (int i = 1; i <= data.getRowNumbers(); i++) {
						System.err.println('\nCompare Data ['+h+'] ['+dataParaExecutar[h]+'] ['+data.getValue('FINAL_VIG', i).toString() +"]")
						if( auxiliares.ExtractDates.compareDate(dataParaExecutar[h], data.getValue('FINAL_VIG', i).toString()  )){
							System.err.println()
							switch (tipo){
								case "RenovaSantanderSASAM2":
									System.err.println("["+i+"] 15D	["+data.getValue('Carta (-15d) - Enviar', i)+"] 30D ["+data.getValue('Carta (-30d) - Enviar', i)+"] NR ["+data.getValue('Carta NR - Enviar', i)+"]")
									try{
										if ( data.getValue('Carta (-15d) - Enviar', i).equals('x')) {
											List<String> notificacoes = new ArrayList<>();
											lista( notificacoes , i)
											menuSasam2()
											cartaPeriodo = retornaTipoDeMensagem(data.getValue('Carta (-15d) - Enviar', i),data.getValue('Carta (-30d) - Enviar', i), data.getValue('Carta NR - Enviar', i))
											if(SantanderEnvioDeMensagensSasam.pesquisaProposta(notificacoes, carta, tipo)){
												System.err.println("["+i+"] - ["+data.getValue('SEGURADO', i)+"] - ["+data.getValue('E-mail', i)+"] - ["+data.getValue('APOLICE', i)+"] - ["+data.getValue('FINAL_VIG', i)+"]")
												segurado = data.getValue('SEGURADO', i)
												apolice = data.getValue('APOLICE', i)
												FINAL_VIG = ExtractDates.formatDateToBR(data.getValue('FINAL_VIG', i))
												dataAtual = ExtractDates.dataAtual()
												tapCap = renocacaoCap( data.getValue('GRUPO SEGURADO ATUAL', i) )

												if ( data.getValue('Carta (-15d) - Enviar', i).equals('x') ){

													if( enviarEmailAnexo(data.getValue('E-mail', i)+', renovacaosantander@allianz.com.br',
													retornaTipoDeMensagem(data.getValue('Carta (-15d) - Enviar', i),data.getValue('Carta (-30d) - Enviar', i), data.getValue('Carta NR - Enviar', i)),
													segurado,
													apolice,
													FINAL_VIG,
													dataAtual,
													data.getValue('PROPOSTA', i).substring(0, 9),
													tapCap
													)
													) {
														alerta=" "
														salvarEnvios(retornaTipoDeMensagem(data.getValue('Carta (-15d) - Enviar', i),data.getValue('Carta (-30d) - Enviar', i),data.getValue('Carta NR - Enviar', i) ), 'RenovaSantanderSASAM2', i,alerta, tipoProcesso, cartaPeriodo)
													} else {
														alerta = " "
														salvarEnvios("FAIL", 'RenovaSantanderSASAM2', i,alerta, tipoProcesso, cartaPeriodo)
													}
												}
											} else {
												alerta = " "
												salvarEnvios("FAIL", 'RenovaSantanderSASAM2', i,alerta, tipoProcesso, cartaPeriodo)
												WebUI.closeBrowser()
												renovaSantanderSasam(tipo)
											}
											SantanderEnvioDeMensagensSasam.finalizaPesquisaProposta(notificacoes)
										}

										try{
											if ( data.getValue('Carta (-30d) - Enviar', i).equals('x') && carta.equals("30")) {
												envioCarta30NR(i, "Carta_RenovacaoApolice_30D")
											}

											if ( data.getValue('Carta NR - Enviar', i).equals('x') && carta.equals("NR") ) {
												envioCarta30NR(i, "Carta_AvisoNaoRenovacao_NR")
											}
										} catch (Exception e){}
									} catch (Exception e){
										//FALHA DE LEITURA DO ARQUIVO DATA
										salvarEnvios("FAIL", 'RenovaSantanderSASAM2', i,alerta, tipoProcesso, cartaPeriodo)
										continue
									}
									break;

								case "RenovaSantander":
									try{
										System.err.println("["+i+"] [15 ]["+data.getValue('Carta (-15d) - Enviar', i)+"]" +
												"[30]["+data.getValue('Carta (-30d) - Enviar', i)+"]"+
												"[NR]["+data.getValue('Carta NR - Enviar', i)+"]")

										if ( data.getValue('Carta (-15d) - Enviar', i).equals('x') ) {
											envioCarta15(i, "Carta_AvisoRenovacao_15D", tipo, '15')
										}
									} catch (Exception e){
										//FALHA DE LEITURA DO ARQUIVO DATA
										alerta = "Falha no envio do e-mail "
										salvarEnvios("FAIL", 'RenovaSantander', i,alerta, tipoProcesso, cartaPeriodo)
										WebUI.closeBrowser()
										renovaSantanderSasam(tipo)
										continue
									}

									try{
										System.err.println("["+carta +"] "+"Carta (-30d) ["+
												data.getValue('Carta (-30d) - Enviar', i)+"] Carta NR  ["+
												data.getValue('Carta NR - Enviar', i)+"]")

										List<String> notificacoes = new ArrayList<>();
										lista( notificacoes , i)

										if ( data.getValue('Carta (-30d) - Enviar', i).equals('x') && carta.equals("30")) {
											envioCarta30NR(i, "Carta_RenovacaoApolice_30D")
										}

										if ( data.getValue('Carta NR - Enviar', i).equals('x') && carta.equals("NR") ) {
											envioCarta30NR(i, "Carta_AvisoNaoRenovacao_NR")
										}
									} catch (Exception e){
										//FALHA DE LEITURA DO ARQUIVO DATA
										alerta = " "
										salvarEnvios("FAIL", 'RenovaSantander', i,alerta, tipoProcesso, cartaPeriodo)
										WebUI.closeBrowser()
										renovaSantanderSasam(tipo)
										continue
									}
									break;
							}
						}
					}
				}
			}
		} catch (Exception e){
			System.err.println('Exception\n'+e)
			//			finalizar()
		}
	}

	public static envioCarta15(int i, String carta, String tipo, String d){
		System.err.println()
		List<String> notificacoes = new ArrayList<>();
		lista( notificacoes , i)
		menuSasam2()
		//	cartaPeriodo = retornaTipoDeMensagem(data.getValue('Carta (-15d) - Enviar', i),data.getValue('Carta (-30d) - Enviar', i), data.getValue('Carta NR - Enviar', i))
		cartaPeriodo = carta
		if(SantanderEnvioDeMensagensSasam.pesquisaProposta(notificacoes, d, tipo)){
			System.err.println("["+i+"] - ["+data.getValue('SEGURADO', i)+"] - ["+
					data.getValue('E-mail', i)+"] - ["+
					data.getValue('APOLICE ANTERIOR', i)+"] - ["+
					data.getValue('FINAL_VIG', i)+"]")

			segurado = seguradoNomeSossial( data.getValue('SEGURADO', i),  data.getValue('NOME SOCIAL', i) )
			//segurado = data.getValue('SEGURADO', i)
			apolice = data.getValue('APOLICE ANTERIOR', i)
			FINAL_VIG = ExtractDates.formatDateToBR(data.getValue('FINAL_VIG', i))
			dataAtual = ExtractDates.dataAtual()
			tapCap = renocacaoCap( data.getValue('GRUPO SEGURADO ATUAL', i) )

			if( enviarEmailAnexo(data.getValue('E-mail', i)+', renovacaosantander@allianz.com.br',
			carta,
			segurado,
			apolice,
			FINAL_VIG,
			dataAtual,
			data.getValue('PROPOSTA', i).substring(0, 9),
			tapCap ))
			{
				try{
					alerta = ""
					salvarEnvios(cartaPeriodo, 'RenovaSantander', i, alerta, tipoProcesso, cartaPeriodo)
				} catch (Exception e){}

				try{
					deleteFileDiretory( "C:/Users/Public/"+data.getValue('PROPOSTA', i).substring(0, 9)+".pdf")
				} catch (Exception e){}
			} else {
				System.err.println()
				alerta = "Falha no envio do e-mail"
				salvarEnvios("FAIL", 'RenovaSantander', i,alerta, tipoProcesso, cartaPeriodo)
				deleteFileDiretory( "C:/Users/Public/"+data.getValue('PROPOSTA', i).substring(0, 9)+".pdf")
			}
		}

	}

	/**
	 * @author BE54955
	 * @param i
	 * @return
	 */
	public static envioCarta30NR(int i, String carta){
		cartaPeriodo = carta
		System.err.println("["+carta+"] ["+i+"] - ["+data.getValue('SEGURADO', i)+"] - ["+
				data.getValue('E-mail', i)+"] - ["+
				data.getValue('APOLICE ANTERIOR', i)+"] - ["+
				data.getValue('FINAL_VIG', i)+"]")

		segurado = seguradoNomeSossial( data.getValue('SEGURADO', i),  data.getValue('NOME SOCIAL', i) )
		//segurado = data.getValue('SEGURADO', i)
		apolice = data.getValue('APOLICE ANTERIOR', i)
		FINAL_VIG = ExtractDates.formatDateToBR(data.getValue('FINAL_VIG', i))
		dataAtual = ExtractDates.dataAtual()
		tapCap = renocacaoCap(data.getValue('GRUPO SEGURADO ATUAL', i) )

		if( enviarEmail(data.getValue('E-mail', i)+', renovacaosantander@allianz.com.br',
		carta,
		segurado,
		apolice,
		FINAL_VIG,
		dataAtual,
		tapCap ) ) {
			try{
				alerta = ""
				salvarEnvios(carta, 'RenovaSantander', i,alerta, tipoProcesso, cartaPeriodo)
			} catch (Exception e){}
		} else {
			alerta = "Falha no envio do e-mail"
			salvarEnvios("FAIL", 'RenovaSantander', i,alerta, tipoProcesso, cartaPeriodo)
		}
	}

	/**
	 * @author Alz_sup54955
	 * @param segurado
	 * @param nomeSocial
	 * @return
	 */
	public static String seguradoNomeSossial(String segurado, String nomeSocial ){
		System.out.println("\nSegurado ["+segurado +"] Nome Social ["+nomeSocial+"]\n");
		String nome
		if(nomeSocial.trim().length()==0){
			nome = segurado;
		} else {
			nome = nomeSocial;
		}
		return nome;
	}


	/**
	 * @author T_DCDias
	 * @param executados
	 * @return
	 */
	public static boolean enviarEmail(String emaildestination, String ramo, String segurado, String apolice,String FINAL_VIG, String dataAtual, String tapCap){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/EnvioMensagens/Santander/ConfiguracoesSantander.txt";
		lerConfigurações(pathTxtEmail)
		System.err.println('Sender ['+sender+']')
		System.err.println()
		//		return true
		return sendEmail.CreateEmail.createEmailSendSantander(sender, emaildestination,pwdsender, host,  porta, ramo, segurado, apolice, FINAL_VIG, dataAtual, tapCap )
	}

	/**
	 * @author T_DCDias
	 * @param executados
	 * @return
	 */
	public static boolean enviarEmailAnexo(String emaildestination, String ramo, String segurado, String apolice,String FINAL_VIG, String dataAtual, String proposta, String tapCap){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/EnvioMensagens/Santander/ConfiguracoesSantander.txt";
		lerConfigurações(pathTxtEmail)
		System.err.println('Sender ['+sender+'] Ramo ['+ramo+"] Proposta ["+proposta+"]")
		List<String> anexos = new ArrayList<>();
		anexos[0] = "C:/Users/Public/"+proposta+".pdf"
		anexos[1] = proposta+".pdf"
		return sendEmail.CreateEmail.createEmailSendSantanderAnexos(sender, emaildestination,pwdsender, host,  porta, ramo, segurado, apolice, FINAL_VIG, dataAtual, tapCap, anexos )
		//	return true;
	}

	/**
	 * @author T_DCDias
	 * @param str
	 * @return
	 */
	public static String retornaTipoDeMensagem(String ...str){
		String tipo
		Map<Integer, String> xpaths = new HashMap<Integer, String>()
		xpaths.put(0, "Carta_AvisoRenovacao_15D")
		xpaths.put(1, "Carta_RenovacaoApolice_30D")
		xpaths.put(2, "Carta_AvisoNaoRenovacao_NR")
		String[] valores = str;
		for(int i = 0; i < valores.length; i++){
			if(valores[i].equals("x")){
				tipo = xpaths.get(i);
			}
		}
		System.out.println(tipo)

		return tipo;
	}

	/**
	 * @author T_DCDias
	 */
	public static salvarEnvios(String tipo, String aba, int i, String alerta, String tipoProcesso, String carta){
		i = i-1;
		switch (tipoProcesso){
			case "RenovaSantander":
				salvarEnviosSantander(tipo, aba,i,alerta)
				break;

			case "RenovaSantanderSASAM2":
				salvarEnviosSasam2(tipo, aba, i,alerta, carta)
				break;
		}
	}

	/**
	 * @author T_DCDias
	 */
	public static salvarEnviosSantander(String tipo, String aba, int i, String alerta){
		switch (tipo){
			case "Carta_AvisoRenovacao_15D":
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'',17,aba);
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'x',18,aba);
				break;

			case "Carta_RenovacaoApolice_30D":
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'',15,aba);
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'x',16,aba);
				break;

			case "Carta_AvisoNaoRenovacao_NR":
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'',19,aba);
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'x',20,aba);
				break;
			case "FAIL":
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'',17,aba);
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'x',18,aba);
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'',15,aba);
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'x',16,aba);
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'',19,aba);
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'x',20,aba);
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,alerta,26,aba);
				break;
		}
	}

	/**
	 * @author T_DCDias
	 */
	public static salvarEnviosSasam2(String tipo, String aba, int i, String alerta, String carta){
		switch (tipo){
			case "Carta_AvisoRenovacao_15D":
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'',45,aba);
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'x',46,aba);
				break;

			case "Carta_RenovacaoApolice_30D":
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'',43,aba);
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'x',44,aba);
				break;

			case "Carta_AvisoNaoRenovacao_NR":
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'',47,aba);
				auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'x',48,aba);
				break;

			case "FAIL":
			//auxiliares.WriteExcelFactory.updateSheetLine('',i, path,alerta,53,aba);
				switch (carta){
					case "Carta_AvisoRenovacao_15D":
					auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'',45,aba);
					auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'F',46,aba);
					break;
					case "Carta_RenovacaoApolice_30D":
					auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'',43,aba);
					auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'F',44,aba);
					break;
					case "Carta_AvisoNaoRenovacao_NR":
					auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'',47,aba);
					auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'F',48,aba);
					break;
				}
				break;
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param date
	 * @return
	 */
	public static boolean validaDataParaPorcessar(String date, int FINAL_VIG, String tipo){
		System.out.println(date +" "+ FINAL_VIG +" "+ tipo+" "+returnCellValue(carta, tipo))
		boolean dataEncontrada = false
		initDataFilesSasam(tipo)
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			System.out.println(data.getValue(returnCellValue(carta, tipo), i))
			if (data.getValue(returnCellValue(carta, tipo), i).equals('x')) {
				if(auxiliares.ExtractDates.compareDate(date, data.getValue(FINAL_VIG, i))){
					dataEncontrada = true
					break
				}
			}
		}
		System.err.println('Data Encontrada ['+dataEncontrada+']')
		return dataEncontrada
	}

	/**
	 * @author be54955
	 * @param carta
	 * @param tipo
	 * @return
	 */
	public static int returnCellValue(String carta, String tipo){
		int cell
		switch(tipo){
			case "RenovaSantander":
				cell = returnCellValueCheck(carta)
				break

			case "RenovaSantanderSASAM2":
				cell = returnCellValueCheckSasam2(carta)
				break
		}
		return cell
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param i
	 * @return
	 */
	public static int returnCellValueCheck(String carta){
		int cellNum
		Map<String, String> cellNunber = new HashMap<String, String>()
		cellNunber.put('15', '18')
		cellNunber.put('30', '16')
		cellNunber.put('NR', '20')
		cellNum = Integer.parseInt(cellNunber.get(carta).toString())
		return cellNum
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param i
	 * @return
	 */
	public static int returnCellValueCheckSasam2(String carta){
		int cellNum
		Map<String, String> cellNunber = new HashMap<String, String>()
		cellNunber.put('15', '47')
		cellNunber.put('30', '45')
		cellNunber.put('NR', '49')
		cellNum = Integer.parseInt(cellNunber.get(carta).toString())
		return cellNum
	}



	/**
	 * @author ALZ_ServiceQA
	 * @param date
	 * @return
	 */
	public static boolean validaDataPorcessada(String date, String tipo, int FINAL_VIG){
		boolean dataEncontrada = false
		initDataFilesSasam(tipo)
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(returnCellValueCheckProcessed(carta, tipo), i).equals('x')) {
				System.err.println(date+" - "+data.getValue(FINAL_VIG, i).toString())
				if(auxiliares.ExtractDates.compareDate(date, data.getValue(FINAL_VIG, i))){
					dataEncontrada = true
					break
				}
			}
		}
		System.err.println('Data Encontrada ['+dataEncontrada+']')
		return dataEncontrada
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param i
	 * @return
	 */
	public static int returnCellValueCheckProcessed(String carta, String tipo){
		int cellNum
		switch(tipo){
			case "RenovaSantander":
				cellNum = returnCellValueCheckProcessedSantander(carta)
				break

			case "RenovaSantanderSASAM2":
				cellNum = returnCellValueCheckProcessedSasam2(carta)
				break
		}
		return cellNum
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param i
	 * @return
	 */
	public static int returnCellValueCheckProcessedSantander(String carta){
		int cellNum
		Map<String, String> cellNunber = new HashMap<String, String>()
		cellNunber.put('15', '17')
		cellNunber.put('30', '15')
		cellNunber.put('NR', '19')
		cellNum = Integer.parseInt(cellNunber.get(carta).toString())
		return cellNum
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param i
	 * @return
	 */
	public static int returnCellValueCheckProcessedSasam2(String carta){
		int cellNum
		Map<String, String> cellNunber = new HashMap<String, String>()
		cellNunber.put('15', '46')
		cellNunber.put('30', '44')
		cellNunber.put('NR', '48')
		cellNum = Integer.parseInt(cellNunber.get(carta).toString())
		return cellNum
	}

	/**
	 * @author be54955
	 */
	public static int retornaFINALVIG(String tipo){
		int n
		switch(tipo){
			case "RenovaSantander":
				n = 2
				break;

			case "RenovaSantanderSASAM2":
				n = 4
				break
		}

		return n
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(String tipo){
		data = findTestData(pathDataFiles)
		path = data.sourceUrl
	}

	public static initDataFilesCap(){
		dataCap = findTestData(pathDataFilesCap)
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFilesSasam(String tipo){
		switch(tipo){
			case "RenovaSantander":
				data = findTestData(pathDataFiles)
				path = data.sourceUrl
				break;

			case "RenovaSantanderSASAM2":
				data = findTestData(pathDataFilessasam2)
				path = data.sourceUrl
				break
		}
	}


	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailSantanderEnvioDeMensagens(String cartaEnviar, String[] dataParaExecutar, String tipo){
		String tipoAba = "RenovaSantanderCartasAviso"
		String assunto
		carta = cartaEnviar
		tipoProcesso = tipo
		carta = cartaEnviar
		initData(tipo)

		try{
			for(int h = 0; h < dataParaExecutar.size(); h++){
				System.err.println('\nSend e-mail: Data ['+h+'] ['+dataParaExecutar[h]+'] ['+ tipo+"]")

				if(validaDataPorcessada(dataParaExecutar[h].trim(), tipo, retornaFINALVIG(tipo))){
					for (int j = 1; j <= data.getRowNumbers(); j++) {
						if( auxiliares.ExtractDates.compareDate(dataParaExecutar[h], data.getValue('FINAL_VIG', j).toString())){
							for (int i = 1; i <= data.getRowNumbers(); i++) {
								assunto = retornaTipoDeMensagem(data.getValue('Carta (-15d) - Enviado', i),data.getValue('Carta (-30d) - Enviado', i), data.getValue('Carta NR - Enviado', i) )
								assunto = assunto.replace("_"," ")
								break;
							}
						}
					}
				}
			}
		} catch (Exception e){
			System.err.println("dataParaExecutar Exception\n"+e)
		}
		System.out.println("assunto ["+assunto+"]")
		try{
			if(!assunto.equals(null)){
				pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Auto/RenovaSantander/ConfiguracoesRenovaSantander.txt";
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				switch (tipoProcesso){
					case "RenovaSantander":
						anexos[1] = "RenovaSantander.xlsx"
						break;
					case "RenovaSantanderSASAM2":
						anexos[1] = "RenovaSantanderSasam2.xlsx"
						break;
				}
				sendEmail.CreateEmail.createEmailSendMultipleFilesCartasAvisoSantander(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba, assunto)
			}
		} catch (Exception e){
			System.err.println("sendEmail exception\n"+e)
		}
	}

	/**
	 * @author T_Dvlopes
	 * @param processoFinalizado
	 * @param tipoAba
	 * @return
	 * Processa as falhas de envio de pendências ao final do processo, caso ocorra mais falha o processo é re-feito até concluir
	 */
	public static processaFalhas(String tipo, String cartaEnviar, String[] dataParaExecutar){
		System.err.println('---- Processa Falhas ['+tipo+'] ----')
		tipoProcesso = tipo
		carta = cartaEnviar
		initData(tipo)
		if(validaDados(tipo, dataParaExecutar)){
			fluxoCompletoEnvioDeMensagens(tipo, dataParaExecutar)
			processaFalhas(tipo, cartaEnviar, dataParaExecutar)
		} else {
			System.err.println('Não existem dados para processar')
			finalizar();
		}
	}

	/**
	 * @author be54955
	 * @param tipo
	 * @param dataParaExecutar
	 * @return
	 */
	public static boolean validaDados(String tipo, String[] dataParaExecutar){
		boolean dados
		try{
			for(int h = 0; h < dataParaExecutar.size(); h++){
				System.err.println('\nData ['+h+'] ['+dataParaExecutar[h]+'] ['+tipoProcesso+"]")
				if(validaDataParaPorcessar(dataParaExecutar[h], retornaFINALVIG(tipo), tipo)){
					for (int i = 1; i <= data.getRowNumbers(); i++) {
						//System.err.println('\nCompare Data ['+h+'] ['+dataParaExecutar[h]+'] ['+data.getValue('FINAL_VIG', i).toString() +"]")
						if( auxiliares.ExtractDates.compareDate(dataParaExecutar[h], data.getValue('FINAL_VIG', i).toString()  )){
							switch (tipo){
								case "RenovaSantander":
								//System.err.println("["+i+"] ["+data.getValue('Carta (-15d) - Enviar', i)+"] ["+data.getValue('Carta (-30d) - Enviar', i)+"] ["+data.getValue('Carta NR - Enviar', i)+"]")
									if ( data.getValue('Carta (-15d) - Enviar', i) == 'x' || data.getValue('Carta (-30d) - Enviar', i) == 'x' || data.getValue('Carta NR - Enviar', i) == 'x') {
										dados = true
										break
									} else {
										dados = false
										continue
									}

								case "RenovaSantanderSASAM2":
								// System.err.println("["+i+"] 15D	["+data.getValue('Carta (-15d) - Enviar', i)+"] 30D ["+data.getValue('Carta (-30d) - Enviar', i)+"] NR ["+data.getValue('Carta NR - Enviar', i)+"]")
									if ( data.getValue('Carta (-15d) - Enviar', i) == 'x' || data.getValue('Carta (-30d) - Enviar', i) == 'x' || data.getValue('Carta NR - Enviar', i) == 'x') {
										dados = true
										break
									}else {
										dados = false
										continue
									}
							}
						}
					}
				}
			}

		} catch (Exception e){
			dados = false
		}
		//System.err.println("Valida dados ["+dados+"]")
		return dados
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static List<String> lista( List<String> registros , int v){
		for (int i = 1; i <= data.getColumnNumbers(); i++) {
			int d = i -1
			registros[d] = data.getValue(i, v).trim()
		}

		for (int j = 0; j < registros.size();j++ ){
			System.err.println("["+j+"] ["+registros[j]+"]")
		}

		return registros
	}


	/**
	 * @author be54955
	 * @param cap
	 * @return
	 */
	public static String renocacaoCap(String cap){
		String tapCap
		for (int i = 1; i <= dataCap.getRowNumbers(); i++) {
			if( dataCap.getValue('Descrição', i).toString().equals(cap)  ){
				tapCap = dataCap.getValue('Comissão renovação', i).toString()
				break
			}
		}
		return tapCap
	}

}

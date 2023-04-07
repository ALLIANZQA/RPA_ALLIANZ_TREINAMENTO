package auto

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

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
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import auto.RenovaSantanderFuncionalidades
import auxiliares.MenuMatriz
import auxiliares.ExtractDates

public class RenovaSantanderSasanFuncionalidades extends RenovaSantanderFuncionalidades{


	private static String testDataPathsasam
	private static String tabCapPathsasam


	/**
	 * @author be54955
	 * @return
	 */
	public static iniURLDateFiles(){
		testDataPathsasam = 'Data Files/Auto/RenovacoesSantanderData/RenovaSantander_Sasam'
		tabCapPathsasam = 'Data Files/Auto/RenovacoesSantanderData/TabCap (1)'
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static fluxoCompletoRenovaSantanderSasam(String tipoAba, String[] dataParaExecutar){
		aba = tipoAba
		iniURLDateFiles()
		initDataFiles(testDataPathsasam, tabCapPathsasam)
		try{
			for(int i = 0; i < dataParaExecutar.size(); i++){
				System.err.println('Data ['+i+'] ['+dataParaExecutar[i]+']')
				if(validaDataParaPorcessar(dataParaExecutar[i])){
					renovaSantanderSasam(tipoAba, dataParaExecutar[i])
					enviarEmailRenovaSantander(tipoAba, dataParaExecutar[i])
				}
			}
		} catch (Exception e){
			System.err.println("["+e+"]")
			fluxoCompletoRenovaSantanderSasam(tipoAba, dataParaExecutar)
			//finalizar()
		}
		WebUI.closeBrowser()
		finalizar()
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static renovaSantanderSasam(String tipoAba, String dataParaExecutar){
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands(tipoAba);
		WebUI.delay(10)
		System.err.println()
		menuProducaoSantander()
		fluxoRenovaSantander(tipoAba, dataParaExecutar)
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static menuProducaoSantander(){
		String xpathProducaoSinistros = '/html/body/div/div/app-root/app-private/app-private-header/nx-epac-header/nx-epac-navigation/div/div[1]/nx-epac-navigation-desktop/div[1]/div/div/ul/li[1]/a/span'
		clickXpath(xpathProducaoSinistros, 5)
		//Produção
		MenuMatriz.subMenu('/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul', "a","Produção")
		//Menu Produção
		WebUI.waitForPageLoad(30)
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()
	}

	/**
	 * @author T_DCDias
	 * @param i
	 * @return
	 */
	public static telaMenuProducao(int i, String aba){
		WebUI.delay(15)
		String telaMenuProducao = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
		System.err.println("tela ["+telaMenuProducao+"]")
		System.err.println()
		if(telaMenuProducao.contains("Menu Produção".toString())){
			alertaRenovacao = false
			//filtrar(i)
			//errorPresent()
			System.err.println('bProximoRegistro ['+bProximoRegistro+"]")
			System.err.println()
			if (bProximoRegistro == false) {
				//Tipo de Seleção : Emissão / Outras Operações
				selecionaTipoSelecao("Emissão",'//*[@id="datosSeleccionContent"]/table[1]/tbody/tr',"td")
				WebUI.delay(2)
				//Agrupamento
				selecionaItemLista("Automóvel",'//*[@id="agrupacion"]',"option")
				//selecionaTipoSelecao("Automóvel",'//*[@id="agrupacion"]',"td")
				WebUI.delay(2)
				//Produto
				selecionaItemLista("Automoveis 1211",'//*[@id="producto"]',"option")
				//Corretor
				limpaCampo('//*[@id="mediador"]',1)
				preencheXpath('//*[@id="mediador"]', selecionaCorretor(aba, registros[79]), 1);
				commandTAB('//*[@id="mediador"]')
				WebUI.delay(2)
				//Selecionar
				clickXpath('//*[@id="btnSeleccionar"]', 5);
				WebUI.delay(2)
				//Operativa
				selecionaItemLista("Emitir apólice",'//*[@id="codOperativa"]',"option")
				//AVANÇAR
				clickXpath('//*[@id="btnSeleccionOperativa"]', 5);

				telaDadosBasicos(i,1)
				telaDadosRisco(i,1)
				alertas(i, aba)

			}//TELA MENU PRODUÇÃO

			System.err.println("validaTela ["+validaTela+"]")
			System.err.println("sAlerta ["+sAlerta+"]")
			System.err.println("bProximoRegistro ["+bProximoRegistro+"]")
			System.err.println("coringa ["+coringa+"]")
			System.err.println("limitePremio ["+limitePremio+"]")
			System.err.println()

			if(validaTela){
				System.err.println('validaTela ['+validaTela+']')
				System.err.println()
				//sAlerta = ""
				salvarExecutados(i, path, aba,pacotesNovos(sPacote), iPrLiqAtlAnt, dPrliqAtuCalc , sProposta, sApoliceRenov,sAlerta+" "+limitePremio,sLog)
				System.err.println()
				WebUI.delay(5)
				telaInicial()
				coringa = false
				sAlerta =""
				limitePremio = ""
			} else if(validaTela && !bProximoRegistro) {
				//TELA RENOVAÇÃO NÃO CARREGADA
				System.err.println("!validaTela ["+validaTela+"] !bProximoRegistro ["+!bProximoRegistro+"]")
				System.err.println()
				coringa = false
			}

			if(bProximoRegistro){
				System.err.println('[bProximoRegistro]')

				if(sAlerta.equals("Apólice a renovar já utilizada")){
					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_Fechar'))
					WebUI.acceptAlert()
					WebUI.switchToWindowTitle('RENOVACIONES')
					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_RENOVACIONES/div_Voltar'))
					WebUI.delay(2)
					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/div_Limpar'))
					WebUI.delay(5)
					coringa = false

				} else {
					WebUI.delay(2)
					WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/div_Limpar'))
					WebUI.delay(5)
					coringa = false
				}

				System.err.println()
				salvarFalhaProximoRegistro(i, path, aba, sAlerta)
				System.err.println()
			}

			System.err.println("coringa ["+coringa+"]")
			if(coringa){
				salvarFalhaProximoRegistro(i, path, aba, "Falha no fluxo do RPA")
				WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_Fechar'))
				WebUI.acceptAlert()
			}

		} else {
			alertaRenovacao = true
			alertaDadosRisco = false
			alertatelaTabelaPrecos = false
			alertas(i, aba)
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaProprietarioCondutor(String tipo,String xpath , String tag ){
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			//System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains(tipo.toString())){
				listOfElements[i+1].click()
			}
		}
		WebUI.delay(2)
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaIOF(String tipo,String xpath , String tag ){
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			//System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains(tipo.toString())){
				listOfElements[i+1].click()
			}
		}
		WebUI.delay(2)
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaItemLista(String tipo,String xpath , String tag ){
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains(tipo.toString())){
				listOfElements[i].click()
				break
			}
		}
		WebUI.delay(2)
	}

	/**
	 * @author be54955
	 * @param swIndex
	 * @return
	 */
	public static preencherTelaDadosBasicos(int swIndex){
		System.err.println("TELA - D. BÁSICOS PREENCHER CAMPOS OBRIGATÓRIOS - swIndex ["+swIndex+"] - aba ["+aba+"]")
		WebUI.switchToWindowIndex(swIndex)
		try{
			if(aba.equals("RenovaSantanderSASAM") || aba.equals("RenovaEmissaoIncorreta") ){
				if(bProximoRegistro == false ){
					//INICIO VIGÊNCIA
					limpaCampo('//*[@id="DatosGeneralesBean$fechaEfecto"]',1)
					fechaAlerta();
					preencheXpath('//*[@id="DatosGeneralesBean$fechaEfecto"]', ExtractDates.formatDateToBR(registros[1]), 1);
					//TIPO DE SEGURO
					selecionaItemLista(registros[4],'//*[@id="DatosGeneralesBean$tipoSeguro"]' , 'option' )
					//TIPO DE PERIODO
					selecionaItemLista(registros[5],'//*[@id="DatosGeneralesBean$tipoPeriodo"]' , 'option' )
					//SEGURADO ISENTO IOF: Sim / Não
					selecionaIOF(registros[6],'//*[@id="blDatosImpuestosContent"]/table[2]/tbody/tr/td/table/tbody', 'td' )
					//CPF/CNPJ TOMADOR
					preencheXpath('//*[@id="IntervinientesBean$nifTomador_doc"]', registros[7], 1);
					//Nome Tomador
					preencheXpath('//*[@id="IntervinientesBean$nomFigTomadorValue"]', registros[8], 1);
					//PROPRIETARIO
					selecionaProprietarioCondutor(registros[10],'//*[@id="blIntervinientesContent"]/table[2]/tbody/tr/td[1]',"td" )
					if(registros[10].toUpperCase().equals("N")){
						//CPF/CNPJ PROPRIETARIO
						preencheXpath('//*[@id="IntervinientesBean$nifPropietario_doc"]', registros[12], 1);
						//Nome do Proprietario
						preencheXpath('//*[@id="IntervinientesBean$nomFigPropietarioValue"]', registros[13], 1);
					}
					//CONDUTOR
					selecionaProprietarioCondutor(registros[11],'//*[@id="blIntervinientesContent"]/table[3]/tbody/tr/td[1]/table/tbody',"td" )
					if(registros[11].toString().equals("N")){
						//CPF/CNPJ CONDUTOR
						preencheXpath('//*[@id="IntervinientesBean$nifConductor_doc"]', registros[14], 1);
						//Nome do Condutor
						preencheXpath('//*[@id="IntervinientesBean$nomFigConductorValue"]', registros[15], 1);
					}
					//CATEGORIA DE RISCO
					selecionaItemLista(registros[16],'//*[@id="CategoriaRiesgoBean$catRiesgo"]' , 'option' )
					//AVANÇAR
					clickXpath('//*[@id="btnAceptar"]', 5);
					bProximoRegistro = false
				}
			}
		} catch(Exception e){
			bProximoRegistro = true
		}
	}

	/**
	 * @author be54955
	 * @param swIndex
	 * @return
	 */
	public static preencherTelaDadosRisco(int swIndex){
		System.err.println("Ttela D. Bsicos e avança para Dados de Risco PREENCHER CAMPOS OBRIGATÓRIOS - swIndex ["+swIndex+"] - aba ["+aba+"]")
		WebUI.switchToWindowIndex(swIndex)
		try{
			if(aba.equals("RenovaSantanderSASAM") || aba.equals("RenovaEmissaoIncorreta")){
				if(bProximoRegistro == false) {

					String telaDadosDeRisco = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
					System.err.println("\ntelaDadosRisco - tela ["+telaDadosDeRisco+"]")
					if(telaDadosDeRisco.contains("DADOS DE RISCO".toString())){

						if(aba.equals("RenovaSantanderSASAM")){
							iPropCorretor = registros[27]
							sGrupoSeg = registros[29]
							sPacote = registros[50]
							dQtdParcelas= registros[51]
						} else if(aba.equals("RenovaEmissaoIncorreta")){
							//iPropCorretor = registros[27]
							//sGrupoSeg = registros[29]
							sPacote = registros[50]
							dQtdParcelas= registros[51]
						}

						//CHASSI
						preencheXpath('//*[@id="InfVeicBean$chassi"]', registros[17], 1);
						clickXpath('//*[@id="consultarChassi"]', 5);
						WebUI.delay(2)
						sAlerta = pegaMSGAlerta()
						System.err.println(sAlerta)
						WebUI.delay(2)
						if(sAlerta.equals("Chassi informado não pode ser emitido neste produto.")){
							validaTela = true
							WebUI.acceptAlert()
							alertaDadosRisco = true
							alertaRenovacao = false
							validaTela = false
							subAlertaDadosRisco = false
							bProximoRegistro = true
						} else {
							validaTela = false
							//DECODIFICADOR DE CHASSI
							decodificadorChassi(registros[18], 2, '//*[@id="tabelaChassi_tbody"]','td')
							//ZERO KM
							try{
								selecionaItemLista(registros[20],'//*[@id="InfVeicBean$zeroKM"]' , 'option')
							}catch (Exception e){}

							//FIPE
							selecionaItemLista(registros[21],'//*[@id="InfVeicBean$percFipe"]' , 'option')
							//FRANQUIA
							selecionaItemLista(registros[22],'//*[@id="InfVeicBean$tipoFranquia"]' , 'option')
							//CEP
							preencheXpath('//*[@id="InfVeicBean$cepPernoite"]', registros[23], 1);
							//Info_Veiculo_Dispositivo Atual
							selecionaItemLista(registros[25],'//*[@id="InfVeicBean$dispositivoAtual"]' , 'option')

							if(aba.equals("RenovaSantanderSASAM")){
								//Autocompara_Santander_Proposta_Corretor
								preencheXpath('//*[@id="AutoComparaSantanderBean$propostaCorretor"]', registros[27], 1);
								//Autocompara_Santander_Grupo_Segurado
								selecionaItemLista(registros[29],'//*[@id="AutoComparaSantanderBean$codGrupoSantander"]' , 'option')
								//Autocompara_Santander_Empresa
								selecionaItemLista(registros[30],'//*[@id="AutoComparaSantanderBean$codEmpreSantander"]' , 'option')
							}

							//Dados_Complementares_Grupo_Renovação
							selecionaItemLista(registros[31],'//*[@id="DadosCompBean$grpRenovacao"]' , 'option')
							//Dados_Complementares_Classe_Bonus
							selecionaItemLista(registros[32],'//*[@id="DadosCompBean$classeBonus"]' , 'option')
							//Dados_Complementares_Veiculo_igual_apólice_anterior (S/N)
							selecionaProprietarioCondutor(registros[33],'//*[@id="DadosCompBeanContent"]/table/tbody/tr[2]/td/table/tbody/tr/td[2]/table/tbody/tr' , 'td' )
							//Informacoes_Apolice_Anterior_Código de Companhia
							//limpaCampo('//*[@id="PesquisaCongenereBean$codigoCongenere"]',1)
							preencheXpath('//*[@id="PesquisaCongenereBean$codigoCongenere"]', registros[34], 1);
							//Informacoes_Apolice_Anterior_Filial
							preencheXpath('//*[@id="InfoApoAnteriorBean$filial"]', registros[35], 5);
							preencheXpath('//*[@id="InfoApoAnteriorBean$filial"]', registros[35], 1);
							//Informacoes_Apolice_Anterior_No._Apólice
							preencheXpath('//*[@id="InfoApoAnteriorBean$nmrApolice"]', registros[36], 1);
							//Informacoes_Apolice_Anterior_No._C.I.
							preencheXpath('//*[@id="InfoApoAnteriorBean$nmrCI"]', registros[37], 1);
							//Informacoes_Apolice_Anterior_Ramo
							selecionaItemLista(registros[38],'//*[@id="InfoApoAnteriorBean$ramo"]' , 'option')
							//Informacoes_Apolice_Anterior_No. Item
							preencheXpath('//*[@id="InfoApoAnteriorBean$nmrItem"]', registros[39], 5);
							//Informacoes_Apolice_Fim_Vigencia
							preencheXpath('//*[@id="InfoApoAnteriorBean$fimVigencia"]', ExtractDates.formatDateToBR(registros[40]), 1);
							//QAR_Data_Nascimento
							preencheXpath('//*[@id="QuestAutoBean$dtaNascimento"]', ExtractDates.formatDateToBR(registros[43]), 1);
							//QAR_Sexo_Condutor
							selecionaItemLista(registros[44],'//*[@id="QuestAutoBean$respostaSexo"]' , 'option')
							//QAR_Estado Civil
							selecionaItemLista(registros[45],'//*[@id="QuestAutoBean$respostaEstCivil"]' , 'option')
							//QAR_O_condutor_reside_com_pessoas_que_tem_entre_17_e_25_anos
							selecionaItemLista(registros[46],'//*[@id="QuestAutoBean$respostaFilhos"]' , 'option')
							//QAR_O_principal_condutor_reside_em
							selecionaItemLista(registros[47],'//*[@id="QuestAutoBean$respostaResid"]' , 'option')
							//Servicos_Assistencia_24_horas
							selecionaItemLista(registros[48],'//*[@id="ServicosBean$codTpAssist"]' , 'option')
							//Servicos_Carro Reserva
							selecionaItemLista(registros[49],'//*[@id="ServicosBean$codCarroReserva"]' , 'option')
							//Aceitar
							clickXpath('//*[@id="o_3"]', 5);

							WebUI.delay(10)

							/**
							 * REMEDIAÇÃO 
							 */
							//Dados_Complementares_Grupo_Renovação
							//selecionaItemLista(registros[31],'//*[@id="DadosCompBean$grpRenovacao"]' , 'option')
							try{
								//Aceitar
								clickXpath('//*[@id="o_3"]', 5);
							} catch (Exception e){}

							bProximoRegistro = false
							subAlertaDadosRisco = true
							alertaDadosRisco = true
							alertaRenovacao = false
							validaTela = false

						}

					}else {
						alertaDadosRisco = true
						alertaRenovacao = false
						validaTela = false
						subAlertaDadosRisco = false
						bProximoRegistro = true
						sAlerta = "Falha ao preencher tela de dadosde risco"
					}
				}
			}
		} catch(Exception e){
			bProximoRegistro = true
			sAlerta = "Falha ao preencher tela de dadosde risco"
		}
	}

	/**
	 * @author be54955
	 * @param swIndex
	 * @return
	 */
	public static preencherTelaDadosCliente(int swIndex){
		System.err.println("Ttela D. CLIENTE avança para Dados de Risco PREENCHER CAMPOS OBRIGATÓRIOS - swIndex ["+swIndex+"] - aba ["+aba+"]")
		WebUI.switchToWindowIndex(swIndex)
		try{
			if(aba.equals("RenovaSantanderSASAM") ){
				if(bProximoRegistro == false) {

					String telaDadosCliente = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
					System.err.println("telaDadosCliente - tela ["+telaDadosCliente+"] validaTela ["+validaTela+"]")
					System.err.println()
					if(telaDadosCliente.contains("Cliente".toString())){
						//Dados_Pessoais_Tomador_D_Nascimento
						preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$dataNascimento"]', ExtractDates.formatDateToBR(registros[65]), 1);
						//Dados_contacto_CEP / Bairro
						limparCamposInput('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]')
						preencheXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]', registros[66], 1);
						//Dados_contacto_CEP / Bairro (Sufixo)
						preencheXpath('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]', registros[67], 1);
						//Dados_contacto_Numero
						preencheXpath('//*[@id="DomicilioClienteBeanTomador$numero"]', registros[68], 1);
						//Dados_contacto_Complemento
						preencheXpath('//*[@id="DomicilioClienteBeanTomador$restoMorada"]', registros[69], 1);
						//Dados_contacto_Email_1
						//preencheXpath('//*[@id="blDomicilioClienteContent"]/table/tbody/tr/td/table[6]/tbody/tr/td[1]/table/tbody/tr/td[2]', registros[71], 1);
						//Dados_contacto_Telefone
						preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone"]', registros[70], 1);
						//Dados_socioeconomicos_sociodemograficos_Sexo
						selecionaItemLista(registros[72],'//*[@id="OutrosDadosClienteBeanTomador$sexo"]' , 'option')
						//Dados_socioeconomicos_sociodemograficos_Estado_Civil
						selecionaItemLista(registros[73],'//*[@id="OutrosDadosClienteBeanTomador$estadoCivil"]' , 'option')
						//Dados_socioeconomicos_sociodemograficos_Profissao
						selecionaItemLista(registros[74],'//*[@id="OutrosDadosClienteBeanTomador$profissao"]' , 'option')
						//Dados_socioeconomicos_sociodemograficos_Renda_Mensal
						selecionaItemLista(registros[75],'//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]' , 'option')

						bProximoRegistro = false
						alertaDadosCliente = false
					}else {
						alertaDadosCliente = true
					}
				}
			}
		} catch(Exception e){
			bProximoRegistro = true
		}
	}


	/**
	 * @author be54955	
	 * @param versao
	 * @param swIndex
	 * @param xpath
	 * @param tag
	 * @return
	 */
	public static decodificadorChassi(String versao, int swIndex, String xpath,String tag){

		try{
			WebUI.delay(2)
			WebUI.switchToWindowIndex(swIndex)
			WebUI.delay(2)
			String nVersao = remveEspacos(versao)
			String nList
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				nList = listOfElements[i].getText()
				nList =  remveEspacos(nList)
				System.err.println("["+i+"] ["+listOfElements[i].getText()+"] ["+nList+"] ["+versao.toString()+"]")
				if(nList.equals(nVersao)){
					System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
					listOfElements[i].click()
					WebUI.delay(2)
					WebUI.acceptAlert()
					break
				}
			}
			WebUI.switchToWindowIndex(1)
			WebUI.delay(2)
		}catch (Exception e){
		}
	}

	/**
	 * @
	 * @param valor
	 * @return
	 */
	public static String remveEspacos(String valor){
		String [] nValor = valor.split()
		StringBuilder strbr = new StringBuilder()
		for(int i = 0; i < nValor.size(); i++){
			strbr.append(nValor[i].trim())
		}
		System.err.println("\n"+strbr.toString())
		return strbr.toString()
	}

}

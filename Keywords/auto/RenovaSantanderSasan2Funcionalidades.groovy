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

public class RenovaSantanderSasan2Funcionalidades extends RenovaSantanderFuncionalidades{


	private static String testDataPathsasam
	private static String tabCapPathsasam


	/**
	 * @author be54955
	 * @return
	 */
	public static iniURLDateFiles(){
		testDataPathsasam = 'Data Files/Auto/RenovacoesSantanderData/RenovaSantander_Sasam2'
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
				if(validaDataParaPorcessarSasam(dataParaExecutar[i])){
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
		menuProducaoSantander()
		fluxoRenovaSantander(tipoAba, dataParaExecutar)
	}

	/**
	 * @author T_DCDias
	 * @param i
	 * @return
	 */
	public static telaMenuProducaoSasan2(int i, String aba){
		System.out.println()
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
				//PESQUISA COTAÇÕES
				clickXpath('//*[@id="pArchivados"]', 1);
				//NUMERO COTAÇÃO
				preencheXpath('//*[@id="txtPresupuesto"]', registros[1], 1);
				//PESQUISAR
				clickXpath('//*[@id="btnBusqueda"]', 5);

				String alert = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','innerText', 2)
				if(!alertListBlocList(alert)){
					sAlerta = alert
					sProposta = ''
					sApoliceRenov = ''
					sLog = ''
					validaTela = true
					alertaRenovacao = false
					alertaDadosRisco = false
					alertatelaTabelaPrecos = false
					alertaSistemaPD = false
					alertaDadosEmissao = false
					alertaDadosCliente = false
					alertaConfirmacaoEmissao = false
					alertaEmissaoRealizada = false
					bProximoRegistro = true
				} else {
					telaCotação()
					telaDadosBasicos(i,1)
					telaDadosRisco(i,1)
				}

				alertas(i, aba)

			}//TELA MENU PRODUÇÃO

			System.err.println("validaTela ["+validaTela+"]")
			System.err.println("sAlerta ["+sAlerta+"]")
			System.err.println("bProximoRegistro ["+bProximoRegistro+"]")
			System.err.println("coringa ["+coringa+"]")
			System.err.println("limitePremio ["+limitePremio+"]")
			System.err.println()

			if(!validaTela || validaTela){
				System.err.println('validaTela ['+validaTela+']')
				System.err.println()
				//sAlerta = ""
				salvarExecutados(i, path, aba,pacotesNovos(sPacote), iPrLiqAtlAnt, sdPrliqAtuCalc , sProposta, sApoliceRenov,sAlerta+" "+limitePremio,sLog)
				System.err.println()
				WebUI.delay(5)
				telaInicial(aba)
				coringa = false
				//sAlerta =""
				limitePremio = ""
			} else if(!validaTela && !bProximoRegistro) {
				//TELA RENOVAÇÃO NÃO CARREGADA
				System.err.println("!validaTela ["+validaTela+"] !bProximoRegistro ["+!bProximoRegistro+"]")
				System.err.println()
				coringa = false
			}

			if(bProximoRegistro){
				System.err.println('[bProximoRegistro]')

				if(sAlerta.equals("Apólice a renovar já utilizada")){
					try{
						WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Tren/td_Fechar'))
						WebUI.acceptAlert()
						WebUI.switchToWindowTitle('RENOVACIONES')
						WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_RENOVACIONES/div_Voltar'))
						WebUI.delay(2)
						WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/div_Limpar'))
						WebUI.delay(5)
						coringa = false
					} catch (Exception e){}

				} else {
					try{
						WebUI.delay(2)
						WebUI.click(findTestObject('Object Repository/PageBackoffice/RenovaSantander/Page_Renovaes/div_Limpar'))
						WebUI.delay(5)
						coringa = false
					} catch (Exception e){}
				}

				System.err.println()
				salvarFalhaProximoRegistro(i, path, aba, sAlerta)
				sAlerta =""
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
	public static preencherTelaDadosRiscoSasam2(int swIndex){
		System.err.println("Ttela D. Bsicos e avança para Dados de Risco PREENCHER CAMPOS OBRIGATÓRIOS - swIndex ["+swIndex+"] - aba ["+aba+"]")
		WebUI.switchToWindowIndex(swIndex)
		try{
			if(aba.equals("RenovaSantanderSASAM2")){
				if(bProximoRegistro == false) {
					String telaDadosDeRisco = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
					System.err.println("\ntelaDadosRisco - tela ["+telaDadosDeRisco+"]")
					if(telaDadosDeRisco.contains("DADOS DE RISCO".toString())){
						try{
							//Proposta anterior
							preencheXpath('//*[@id="AutoComparaSantanderBean$propostaCorretor"]', registros[6], 5);
							//Grupo Segurado
							selecionaItemLista(registros[10],'//*[@id="AutoComparaSantanderBean$codGrupoSantander"]' , 'option')
						} catch (Exception e){}


						//Filial
						preencheXpath('//*[@id="InfoApoAnteriorBean$filial"]', registros[12], 5);
						//RAMO_ANT
						selecionaItemLista(registros[16],'//*[@id="InfoApoAnteriorBean$ramo"]' , 'option')
						//N. ITEM
						preencheXpath('//*[@id="InfoApoAnteriorBean$nmrItem"]', registros[14], 5);

						//Servicos_Assistencia_24_horas
						selecionaItemLista(registros[17],'//*[@id="ServicosBean$codTpAssist"]' , 'option')

						try{
							//Servicos_Carro Reserva
							selecionaItemLista(registros[18],'//*[@id="ServicosBean$codCarroReserva"]' , 'option')
						}catch (Exception e){}

						//Aceitar
						clickXpath('//*[@id="o_3"]', 5);
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
	public static preencherTelaDadosClienteSasam2(int swIndex){
		System.err.println("Ttela D. CLIENTE avança para Dados de Risco PREENCHER CAMPOS OBRIGATÓRIOS - swIndex ["+swIndex+"] - aba ["+aba+"]")
		WebUI.switchToWindowIndex(swIndex)
		try{
			if(aba.equals("RenovaSantanderSASAM2") ){
				if(bProximoRegistro == false) {

					String telaDadosCliente = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
					System.err.println("telaDadosCliente - tela ["+telaDadosCliente+"] validaTela ["+validaTela+"]")
					System.err.println()
					if(telaDadosCliente.contains("Cliente".toString())){
						//Data Nascimento
						WebUI.delay(2)
						preencheXpath('//*[@id="DadosGeraisClienteBeanConductor$dataNascimento"]', registros[32], 1);
						//SEXO
						selecionaItemLista("Masculino",'//*[@id="OutrosDadosClienteBeanConductor$sexo"]' , 'option')
						//Estado Civil
						selecionaItemLista("Casado",'//*[@id="OutrosDadosClienteBeanConductor$estadoCivil"]' , 'option')
						//Profissão
						selecionaItemLista("Engenheiro",'//*[@id="OutrosDadosClienteBeanConductor$profissao"]' , 'option')
						//Renda Mensal
						selecionaItemLista("Até R\$1.500,00", '//*[@id="OutrosDadosClienteBeanConductor$rendaMensal"]', "option")
						bProximoRegistro = false
						alertaDadosCliente = false
					}else {
						/**
						 * 
						 */
						alertaDadosCliente = false
					}
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
	public static preencherTelaDadosClienteSasam2Opcional(int swIndex){
		System.err.println("Ttela D. CLIENTE avança para Dados de Risco PREENCHER CAMPOS OBRIGATÓRIOS - swIndex ["+swIndex+"] - aba ["+aba+"]")
		WebUI.switchToWindowIndex(swIndex)
		try{
			if(aba.equals("RenovaSantanderSASAM2") ){
				if(bProximoRegistro == false) {

					String telaDadosCliente = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
					System.err.println("telaDadosCliente - tela ["+telaDadosCliente+"] validaTela ["+validaTela+"]")
					System.err.println()
					if(telaDadosCliente.contains("Cliente".toString())){
						//LISTA DE CLIENTES
						/**
						 * Opcional
						 */
						telaListaClientes()

						if(pessoaJuridica(recuperaValor('//*[@id="DadosGeraisClienteBeanTomador$_doc"]', 'value', 5))){
							//CEP
							preencheXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]', registros[33], 1);
							//LOG
							preencheXpath('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]', registros[34], 1);
							//Numero
							preencheXpath('//*[@id="DomicilioClienteBeanTomador$numero"]', registros[38], 1);
							//Telefone
							preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone"]', registros[40], 1);
							//EMAIL
							preencheXpath('//*[@id="DomicilioClienteBeanTomador$email1"]', registros[22], 1);

							//Nome fantasia obrigatório
							preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$nomeFantasia"]', registros[21], 1);
							//Atividade principal obrigatória
							preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$actividadePrincipal"]', registros[21], 1);
							//Natureza
							selecionaItemLista("ADMINISTRAÇÃO PÚBLICA, DEFESA E SEGURIDADE SOCIAL",'//*[@id="OutrosDadosClienteBeanTomador$naturezaRendimento"]',"option")
							//Ocupação obrigatória
							selecionaItemLista("A1 - AGRICULTURA, PECUÁRIA E SERVIÇOS RELACIONADOS",'//*[@id="OutrosDadosClienteBeanTomador$ocupacao"]',"option")
							//Posição de faturamento obrigatório
							selecionaItemLista('Até R$120.000,00','//*[@id="OutrosDadosClienteBeanTomador$faturamento"]',"option")
							//QTD Funcionarios
							selecionaItemLista('Até 9','//*[@id="OutrosDadosClienteBeanTomador$numeroFuncionarios"]',"option")
							//Patrimônio obrigatório
							selecionaItemLista('Até R$300.000,00','//*[@id="OutrosDadosClienteBeanTomador$patrimonio"]',"option")

							//		avancar 2 x

							bProximoRegistro = false
							alertaDadosCliente = false
						} else {
							//Data Nascimento
							WebUI.delay(2)
							preencheXpath('//*[@id="DadosGeraisClienteBeanTomador$dataNascimento"]', registros[32], 1);
							//CEP
							preencheXpath('//*[@id="DomicilioClienteBeanTomador$codigoPostal"]', registros[33], 1);
							//LOG
							preencheXpath('//*[@id="DomicilioClienteBeanTomador$subCodigoPostal"]', registros[34], 1);
							//Numero
							limparCamposInput('//*[@id="DomicilioClienteBeanTomador$numero"]')
							preencheXpath('//*[@id="DomicilioClienteBeanTomador$numero"]', registros[38], 2);
							//TP_LOGRADOURO
							logradouroDadosClienteSasam2()
							//Telefone
							limparCamposInput('//*[@id="DomicilioClienteBeanTomador$telefone"]')
							preencheXpath('//*[@id="DomicilioClienteBeanTomador$telefone"]', registros[40], 5);
							//EMAIL
							limparCamposInput('//*[@id="DomicilioClienteBeanTomador$email1"]')
							preencheXpath('//*[@id="DomicilioClienteBeanTomador$email1"]', registros[22], 1);
							//SEXO
							selecionaItemLista( "Masculino",'//*[@id="OutrosDadosClienteBeanTomador$sexo"]',"option")
							//ESTADO CIVIL
							selecionaItemLista("Casado",'//*[@id="OutrosDadosClienteBeanTomador$estadoCivil"]',"option")
							//Profissão
							selecionaItemLista("Engenheiro",'//*[@id="OutrosDadosClienteBeanTomador$profissao"]' , 'option')
							WebUI.delay(2)
							//Renda Mnesal
							selecionaItemLista("Até R\$1.500,00", '//*[@id="OutrosDadosClienteBeanTomador$rendaMensal"]', "option")
							bProximoRegistro = false
							alertaDadosCliente = false
						}
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
	 * @return
	 */
	public static detalhesPessoaisCondutor(){
		try{
			/**
			 * Opcional - DETALHES PESSOAIS DO CONDUTOR
			 */
			//Data Nascimento
			WebUI.delay(2)
			preencheXpath('//*[@id="DadosGeraisClienteBeanConductor$dataNascimento"]', registros[32], 1);
			//SEXO
			selecionaItemLista( "Masculino",'//*[@id="OutrosDadosClienteBeanConductor$sexo"]',"option")
			//ESTADO CIVIL
			selecionaItemLista("Casado",'//*[@id="OutrosDadosClienteBeanConductor$estadoCivil"]',"option")
			//Profissão
			selecionaItemLista("Engenheiro",'//*[@id="OutrosDadosClienteBeanConductor$profissao"]' , 'option')
			//Renda Mnesal
			selecionaItemLista("Até R\$1.500,00", '//*[@id="OutrosDadosClienteBeanConductor$rendaMensal"]', "option")

		}catch (Exception e){}
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

	/**
	 * @author be54955
	 * @return
	 */
	public static telaCotação(){
		//Alterar
		WebUI.delay(5)
		MenuMatriz.subMenu('//*[@id="detalleSectionBar"]/table/tbody/tr/td', "td","Alterar")
		WebUI.delay(10)
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static telaDadosBasicosSasam2(){
		//Avançar
		clickXpath('//*[@id="btnAceptar"]', 5);
	}


}

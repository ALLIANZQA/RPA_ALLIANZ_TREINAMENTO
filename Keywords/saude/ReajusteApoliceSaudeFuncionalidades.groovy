package saude

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.text.SimpleDateFormat
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
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import auxiliares.RegrasEmailDestination
import auxiliares.MenuMatriz
import internal.GlobalVariable
import rpacommands.rpaCommands
import auxiliares.ExtractDates
import auxiliares.timer

public class ReajusteApoliceSaudeFuncionalidades extends rpaCommands{


	private static TestData data
	private static String testDataPath = 'Data Files/Saude/ReajusteApoliceSaude/ReajusteApoliceSaudeData'
	private static String path
	private static String alerta
	private static int cellCheck = 12
	private static int cellCheckRamo = 11
	private static String status

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoReajusteApoliceSaudeFuncionalidades(String aba, String a){
		initDataFiles()
		fluxoCompletoReajusteApoliceSaude(aba,a)
		reprocessaFalhas(aba,a)
		enviaEmailReajusteApoliceSaude(aba)
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static navegarMenu(){
		WebUI.delay(5)
		MenuMatriz.producaoSinistro()
		WebUI.delay(5)
		MenuMatriz.subMenu("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul", "a","Produção")
		WebUI.waitForPageLoad(30)
		WebUI.switchToWindowIndex(1)
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoReajusteApoliceSaude(String aba,String a){
		System.err.println()
		try{
			if(validaDados(cellCheck)){
				acessoAmbiente(aba,a)
				WebUI.delay(15);
				navegarMenu()
				//WebUI.delay(20);
				alteraJanejaGenerica(1, 20)
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					if (data.getValue(cellCheck, i).equals('Processar')) {
						if(validaRamo(data.getValue(cellCheckRamo, i).toString())){
						alerta=""
						List<String> registros = new ArrayList<>();
						lista(registros, i)
						if(reajuste(registros, aba)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("",j, path, status, 11, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("",j, path, alerta+" "+ExtractDates.dataAtualizada(), 12,  aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 11, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta+" "+ExtractDates.dataAtualizada(), 12,  aba);
							} catch (Exception e){}
						}
						} else {
						  auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Processado", 11, aba);
						  auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Ramo não encontrado "+" "+ExtractDates.dataAtualizada(), 12,  aba);
						}
					}
				}
			} else {
				System.err.println('Não existem dados para processar')
			}
		} catch (Exception e){}
	}
	
	/**
	 * @author BE54955
	 */
	public static boolean validaRamo(String ramo){
		System.err.println("\nRamo ["+ramo+"]")
		boolean r = false
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add("204.0")
		listOfAlerts.add("202.0")
		listOfAlerts.add("202.1")
		listOfAlerts.add("202.2")
		System.out.println("Ramo -["+ramo+"]-")
		for(int i = 0; i < listOfAlerts.size(); i++){
			System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
			if(listOfAlerts[i].contains(ramo.toString())){
				System.err.println("FOUND RAMO ["+i+"] ["+listOfAlerts[i]+"]")
				r = true
				break
			}
		}
		System.out.println("\nAlert ["+r+"]")
		return  r
	} 
	
	
	/**
	 * @author BE54955
	 * @param registros
	 * @param aba
	 * @return
	 */
	public static boolean reajuste( List<String> registros, String aba){
		System.out.println()
		boolean enviado
		String msg
		try{
			//alteraJanejaGenerica(1, 15)
			String telaMenuProducao = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'textContent', 5)
			System.err.println("tela ["+telaMenuProducao+"]")
			if(listaDeTelas(telaMenuProducao)){
				//Tipo de Seleção : Emissão / Outras Operações
				selecionaTipoSelecao("Outras Operações",'//*[@id="datosSeleccionContent"]/table[1]/tbody/tr',"td")
				WebUI.delay(2)
				//Apólice
				limpaCampo('//*[@id="polSusep_SUSEP"]',1)
				preencheXpath('//*[@id="polSusep_SUSEP"]', registros[1], 1);
				commandTAB('//*[@id="polSusep_SUSEP"]')
				//Selecionar
				clickXpath('//*[@id="btnSeleccionar"]', 5);
				WebUI.delay(2)

				try{
					alerta = pegaMSGAlerta()
				}catch (Exception e){}


				//Operativa
				selecionaItemLista(registros[2],'//*[@id="codOperativa"]',"option")
				//AVANÇAR
				clickXpath('//*[@id="btnSeleccionOperativa"]', 5);

				try{
					fechaAlerta()
				}catch (Exception e){}

				//Motivo
				preencheXpath('//*[@id="motivo"]', registros[3], 5);
				//AVANÇAR
				clickXpath('//*[@id="btnModificacion"]', 5);
				System.err.println()
				switch (telaRamo()) {
					case '0202':
						enviado = ramo202(registros, "202")
						break;
					case '0201':
						enviado = ramo201204(registros, "201")
						break;
					case '0204':
						enviado = ramo201204(registros, "204")
						break;
				}
				status = "Processado"
				System.out.println(enviado)
				System.out.println()
			} else {
				status = "Processado"
				alerta = "Tela -Menu Produção- não carregou "
				fecharJanelaAtual(1)
			}
		} catch (Exception e) {
			enviado = false
			//alerta="Tela -Menu Produção-não apresentada "
			status = "Processado"
		}
		return enviado
	}

	/**
	 * @author BE54955
	 * @return
	 */
	public static String telaRamo(){
		String telaRamo = StringFormatter.returnStringNumber(recuperaValor('//*[@id="contenedorMenuLarge"]/div[2]', 'innerText', 5))
		System.out.println("RAMO ["+telaRamo+"] ["+telaRamo.size()+"]")
		if(telaRamo.size()==0){
			telaRamo = StringFormatter.returnStringNumber(recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 5))
			System.out.println("RAMO ["+telaRamo+"]")
		}

		if(telaRamo.size()==5){
			telaRamo = telaRamo.substring(0, 4)
			System.out.println(telaRamo)
		}

		return telaRamo
	}

	/**
	 * @author BE54955
	 * @param registros
	 * @return
	 */
	public static boolean ramo202(List<String> registros, String ramo){
		boolean bramo = true
		System.out.println()
		boolean pg = true
		String strPgs
		// QTD DE PAGINAS 12
		//QTD DE PAGINAS 9
		for(int b = 0; b<indexRamo(registros[10]);b++){
			//Avançar
			String str1 = recuperaValor('//*[@id="contenedorMenuLarge"]/div[2]', 'innerText', 5)
			String str2

			if(registros[10].equals("202.2")||registros[10].equals("202-2")){
				str2 = sequenciaDeTelas2022(Integer. toString(b))
			} else {
				str2 = sequenciaDeTelas(Integer. toString(b))
			}

			System.out.println("["+str1+"] ["+str2+"]")
			if(str1.contains(str2)){
				preencherCampos202(str1, registros,registros[10] )
				if(!str1.contains("TABELA DE PREÇOS".toString())){
					//Avançar
					clickXpath('//*[@id="btnAceptar"]', 5);
					if(alertListContinueList( recuperaValor('//*[@id="divContentErrorSection"]/div/div/div/p/span', 'innerText', 5) )){
						//Avançar
						clickXpath('//*[@id="btnAceptar"]', 5);
					}
				} else {
					//Comprar
					System.out.println()
					selecionaListaDeElementos('//*[@id="ParrillaBean$modalidades[0]rad"]','button')
					for(int i = 0; i < listOfElements.size(); i++){
						System.err.println('['+i+'] ['+listOfElements.get(i).getText()+']')
						if(listOfElements.get(i).getText().contains('Comprar'.toString())){
							listOfElements.get(i).click()
							break
						}
					}
				}
				System.out.println("AVANÇAR ["+b+"]")
			} else {
				strPgs = "Tela ["+str2+"] não apresentada";
				pg = false
				break;
			}
		}
		System.out.println("["+pg+"]")

		if(pg){
			//MSG - CONFIRMAÇÃO
			alerta = recuperaValor('//*[@id="policy"]', 'innerText', 5)+" ("+ramo+")"
			System.out.println(alerta)
			//Avançar
			clickXpath('//*[@id="btnValida"]', 5);
			WebUI.delay(5);
			System.out.println()
		} else {
			bramo = false
			StringBuffer strbfr = new StringBuffer()
			selecionaListaDeElementos('/*/body','p')
			for(int i = 0; i < listOfElements.size(); i++){
				System.err.println('['+i+'] ['+listOfElements.get(i).getText()+']')
				strbfr.append(listOfElements.get(i).getText())+" ";
			}
			alerta = "Alerta: "+strbfr.toString()+" - "+strPgs
			System.out.println("["+alerta+"] ["+bramo+"]")
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0, 15)
			navegarMenu()
			alteraJanejaGenerica(1, 20)
		}
		return  bramo;
	}

	/**
	 * @author BE54955
	 * @param ramo
	 * @return
	 */
	public static int indexRamo(String ramo){
		int index
		if(ramo.equals("202.2")||ramo.equals("202-2")){
			index = 9
		} else {
			index = 12
		}
		return index
	}

	/**
	 * @author BE54955
	 * @param tela
	 * @return
	 */
	public static preencherCampos202(String tela, List<String> registros, String ramo){
		String xpath
		try{
			if(tela.contains("D. BÁSICOS".toString())){
				//Data vigência alter.
				xpath = '//*[@id="DatosSuplementoBean$fechaEfectoSupl"]'
				limpaCampo(xpath,1)
				preencheXpath(xpath, registros[5], 1);
				commandTAB(xpath)
			}
		} catch (Exception e){
			alerta = "Problemas em preencher os dados na tela: D. BÁSICOS"
		}

		try{
			if(tela.contains("DADOS DO RISCO".toString())){
				//Dados do risco
				//Coef. De Reembolso de Consulta
				xpath = '//*[@id="DadosRiscoSaudeBean$coefReembConsulta"]'
				limpaCampo(xpath,0)
				preencheXpath(xpath, registros[6], 0);
				//Coef. De Reembolso de Demais Procedimentos
				xpath = '//*[@id="DadosRiscoSaudeBean$coefReembProced"]'
				limpaCampo(xpath,0)
				preencheXpath(xpath, registros[7], 0);

				if(ramo.equals("202.2")){
					System.out.println()
					//Reajuste Técnico
					xpath = '//*[@id="TabelaPrecosBean$reajusteTecnico"]'
					limpaCampo(xpath,0)
					preencheXpath(xpath, registros[8], 0);
					//Reajuste Financeiro
					xpath = '//*[@id="TabelaPrecosBean$reajusteFinanceiro"]'
					limpaCampo(xpath,0)
					preencheXpath(xpath, registros[9], 0);
				}
				//Refletir as alterações nos Grupos Familiares
				clickXpath('//*[@id="ListaCarenciasCobsSubcobsBean$repCarencias_label"]', 1);
			}
		} catch (Exception e){
			alerta = "Problemas em preencher os dados na tela: DADOS DO RISCO"
		}

		try{
			if(tela.contains("TABELA DE PRÊMIOS POR PLANO")){
				System.out.println()
				//Reajuste Técnico
				xpath = '//*[@id="AplicReajustesBean$reajusteTecnico"]'
				limpaCampo(xpath,0)
				preencheXpath(xpath, registros[8], 0);
				//Reajuste Financeiro
				xpath = '//*[@id="AplicReajustesBean$reajusteFinanceiro"]'
				limpaCampo(xpath,0)
				preencheXpath(xpath, registros[9], 0);
			}
		} catch (Exception e){
			alerta = "Problemas em preencher os dados na tela: TABELA DE PRÊMIOS POR PLANO"
		}

		try{
			if(tela.contains("D. CLIENTE")){
				System.out.println()
				//VALIDA INSS
				String inssValue = recuperaValor('//*[@id="OutrosDadosClienteBeanTomador$inss"]', 'value', 5)
				System.out.println("INSS "+inssValue+""+inssValue.size()+"")
				if(inssValue.size()==0){
					preencheXpath('//*[@id="OutrosDadosClienteBeanTomador$inss"]', '999', 0);
				}
			}
		} catch (Exception e){
			alerta = "Problemas em preencher os dados na tela: D. CLIENTE"
		}

	}

	/**
	 * @author BE54955
	 * @param registros
	 * @return
	 */
	public static boolean ramo201204(List<String> registros, String ramo){
		boolean bramo = true
		System.out.println()
		boolean pg = true
		String strPgs
		// QTD DE PAGINAS 11
		for(int b = 0; b<12;b++){
			WebUI.delay(5);
			alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
			System.out.println(alerta)
			//Data início endosso maior a data de cancelamento
			if(!alertListBlocList(alerta)){
			//Avançar
			String str1 = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 5)
			String str2 = sequenciaDeTelasAntigas(Integer. toString(b))
			System.out.println("["+str1+"] ["+str2+"]")
			if(str1.contains(str2.toString())){
				preencherCampos201204(str1, registros)
				if(str1.contains("TABELA DE PREÇOS".toString())){
					//TABELA DE PREÇOS
					//Emitir
					clickXpath('//*[@id="ParrillaBean$btnEmitir"]', 5);
				} else {
					clickXpath('//*[@id="btnAceptar"]', 5);
				}
				System.out.println("AVANÇAR ["+b+"]")
			} else {
				strPgs = "Tela ["+str2+"] não apresentada na sequência";
				pg = false
				break;
			}
			
			} else {
				strPgs = alerta;
				pg = false
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 15)
				navegarMenu()
				alteraJanejaGenerica(1, 20)
				break;
			}
		}
		System.out.println("PÁGINA ["+pg+"]")
		if(pg){
			String prop1 = recuperaValor('//*[@id="CabeceraBean$polizaSusep"]', 'textContent', 1);
			String prop2 = recuperaValor('//*[@id="CabeceraBean$polizaSusepSUSEP"]', 'textContent', 1);
			StringBuilder strbdr = new StringBuilder()
			if(!prop1.equals("atributo nao encontrado")){
				strbdr.append(prop1+" - ")
			}
			if(!prop1.equals("atributo nao encontrado")){
				strbdr.append(prop2)
			}
			alerta = strbdr.toString()+" ("+ramo+")"
			System.out.println("["+alerta+"]")
			clickXpath('//*[@id="btnValida"]', 5);
			WebUI.delay(5);
		} else {
			bramo = false
			//alerta = strPgs
		}
		return  bramo;
	}

	/**
	 * @author BE54955
	 * @param tela
	 * @return
	 */
	public static preencherCampos201204(String tela, List<String> registros){
		System.out.println("Preencher Campos ["+tela+"]")
		String xpath
		try{
			if(tela.contains("D. BÁSICOS")){
				//D. BÁSICOS
				//Data vigência alter.
				xpath = '//*[@id="DatosSuplementoBean$fechaEfectoSupl"]'
				limpaCampo(xpath,1)
				preencheXpath(xpath, registros[5], 1);
				commandTAB(xpath)
			}
		} catch (Exception e){
			alerta = "Problemas em preencher os dados na tela: D. BÁSICOS"
			//Fechar
			clickXpath('//*[@id="o_2"]', 1);
			alteraJanejaGenerica(0, 15)
			navegarMenu()
		}

		try{
			if(tela.contains("DADOS DO RISCO")){
				//Dados do risco
				//Coef. De Reembolso de Consulta
				xpath = '//*[@id="DadosRiscoSaudeBean$coefReembConsulta"]'
				limpaCampo(xpath,0)
				preencheXpath(xpath, registros[6], 0);
				//Coef. De Reembolso de Demais Procedimentos
				xpath = '//*[@id="DadosRiscoSaudeBean$coefReembProced"]'
				limpaCampo(xpath,0)
				preencheXpath(xpath, registros[7], 0);
				//Refletir as alterações nos Grupos Familiares = Sim
				clickXpath('//*[@id="ListaCarenciasCobsSubcobsBean$repCarencias"]', 1);
			}
		}catch (Exception e){
			alerta = "Problemas em preencher os dados na tela: DADOS DO RISCO"
			//Fechar
			clickXpath('//*[@id="o_2"]', 1);
			alteraJanejaGenerica(0, 15)
			navegarMenu()
		}

		try{
			if(tela.contains("TABELA DE PRÊMIOS POR PLANO".toString())){
				//TABELA DE PRÊMIOS POR PLANO
				//Reajuste Técnico
				xpath = '//*[@id="AplicReajustesBean$reajusteTecnico"]'
				limpaCampo(xpath,0)
				preencheXpath(xpath, registros[8], 0);
				//Reajuste Financeiro
				xpath = '//*[@id="AplicReajustesBean$reajusteFinanceiro"]'
				limpaCampo(xpath,0)
				preencheXpath(xpath, registros[9], 0);
			}
		} catch (Exception e){
			alerta = "Problemas em preencher os dados na tela: TABELA DE PRÊMIOS POR PLANO"
			//Fechar
			clickXpath('//*[@id="o_2"]', 1);
			alteraJanejaGenerica(0, 15)
			navegarMenu()
		}
	}

	/**
	 * @author BE54955
	 * @param strDate
	 * @return
	 */
	public  static String sequenciaDeTelas(String index){
		Map<String, String> tela = new HashMap<String, String>();
		tela.put("0",'D. BÁSICOS');
		tela.put("1",'DADOS DO RISCO');
		tela.put("2",'PACOTE PERSONALIZADO');
		tela.put("3",'RESUMO DOS PACOTES');
		tela.put("4",'TABELA DE PRÊMIOS POR PLANO');
		tela.put("5",'TABELA DE PREÇOS');
		tela.put("6",'D. EMISSÃO');
		tela.put("7",'D. CLIENTE');
		tela.put("8",'D. CLIENTE');
		tela.put("9",'D. COMUNS');
		tela.put("10",'CLÁUSULAS');
		tela.put("11",'CONFIRMAÇÃO DE EMISSÃO');
		
		System.out.println("TELA ["+tela.get(index)+"]")
		return tela.get(index);
	}

	/**
	 * @author BE54955
	 * @param strDate
	 * @return
	 */
	public  static String sequenciaDeTelas2022(String index){
		Map<String, String> tela = new HashMap<String, String>();
		tela.put("0",'D. BÁSICOS');
		tela.put("1",'DADOS DO RISCO');
		tela.put("2",'TABELA DE PREÇOS');
		tela.put("3",'D. EMISSÃO');
		tela.put("4",'D. CLIENTE');
		tela.put("5",'D. CLIENTE');
		tela.put("6",'D. COMUNS');
		tela.put("7",'CLÁUSULAS');
		tela.put("8",'CONFIRMAÇÃO DE EMISSÃO');
		
		System.out.println("TELA ["+tela.get(index)+"]")
		return tela.get(index);
	}

	/**
	 * @author BE54955
	 * @param strDate
	 * @return
	 */
	public  static String sequenciaDeTelasAntigas(String index){
		Map<String, String> tela = new HashMap<String, String>();
		tela.put("0",'D. BÁSICOS');
		tela.put("1",'DADOS DO RISCO');
		tela.put("2",'PACOTE PERSONALIZADO');
		tela.put("3",'RESUMO DOS PACOTES');
		tela.put("4",'TABELA DE PRÊMIOS POR PLANO');
		tela.put("5",'TABELA DE PREÇOS');
		tela.put("6",'D. EMISSÃO');
		tela.put("7",'D. CLIENTE');
		tela.put("8",'D. CLIENTE');
		tela.put("9",'D. COMUNS');
		tela.put("10",'CLÁUSULAS');
		tela.put("11",'CONFIRMAÇÃO DE EMISSÃO');
		System.out.println("TELA ["+tela.get(index)+"]")
		return tela.get(index);
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		path = data.sourceUrl
		System.out.println(data.getRowNumbers())
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba, String a){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoReajusteApoliceSaude(aba, a)
			reprocessaFalhas(aba, a)
		}
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 */
	public static enviaEmailReajusteApoliceSaude(String tipoAba){
		initDataFiles()
		try{
			if(!validaDados(cellCheck)){
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Saude\\ReajusteApoliceSaude\\ConfiguracoesReajusteApoliceSaude.txt"
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				List<String> anexos = new ArrayList<>();
				anexos[0] = path
				anexos[1] = "ReajusteApoliceSaude.xlsx"
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
			}
		} catch (Exception e){
		}
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static boolean validaDados(int cellStatus){
		boolean value
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(cellStatus, i).equals('Processar')) {
				value = true
				break;
			} else {
				value = false
			}
		}
		System.err.println('Data ['+value+']')
		return value
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static List<String> lista( List<String> registros , int v){
		try{
			for (int i = 1; i <= data.getColumnNumbers(); i++) {
				int d = i -1
				registros[d] = data.getValue(i, v).trim()
			}

			for (int j = 0; j < registros.size();j++ ){
				System.err.println("["+j+"] ["+registros[j]+"]")
			}
			return registros
		}  catch (Exception e){
			System.err.println('\n['+e+']')
		}
	}







}

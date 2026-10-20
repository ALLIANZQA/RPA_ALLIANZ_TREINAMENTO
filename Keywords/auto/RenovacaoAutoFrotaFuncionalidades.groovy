package auto

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.List

import javax.media.bean.playerbean.MediaPlayer.popupActionListener

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

import auxiliares.switchAtualizarExcel
import bean.MassaDados

import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.MenuMatriz
import auxiliares.ExtractDates
import auxiliares.CurrencyMonetary

public class RenovacaoAutoFrotaFuncionalidades extends rpaCommands{



	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static TestData dataItens
	private static String testDataPath = 'Data Files/Auto/RenovacaoAutoFrotaData/RenovacaoAutoFrota'
	private static String path
	private static String confirmacaoReserva
	private static Map<String, String> alertasItens = new HashMap<String, String>();
	private static Map<String, String> mensagensItensPopUp = new HashMap<String, String>();
	private static Map<String, String> mensagensItens = new HashMap<String, String>();
	private static Map<String , String> mensagenCoberturas = new HashMap<String, String>();
	private static List<String> listaContratos = new ArrayList<String>()
	private static List<String> linhasComRestrincao = new ArrayList<String>()
	private static List<String> listaChassi = new ArrayList<String>()
	private static List<String> listaChassiFipe = new ArrayList<String>()
	private static Map<String , String> maplistaChassiFipe = new HashMap<String, String>();
	private static Map<String , String> listaFIPE = new HashMap<String, String>();
	private static String alertaValorReferencia
	private static String alertaDadosBasicos
	private static String alertaAceitacao
	private static String alertCobertura
	private static String alerta
	private static boolean alertaFIPE
	private static String evidencia
	private static boolean coberturaBloqueada
	private static String alertaFluxoDadosBasicos
	private static String chassiFipe

	private static int cell = 37

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		path = data.sourceUrl
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean carregaDados(){
		boolean carregar = false
		initDataFiles()
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if(data.getValue(cell, i).equals("Processar")){
				carregar = true
				break
			}
		}
		System.err.println('carregaDados ['+ carregar+']')
		return carregar
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static lista( List<String> renovacao , int v){
		for (int i = 1; i <= data.getColumnNumbers(); i++) {
			int d = i -1
			if(i==1){
				renovacao[d] = data.getValue(i, v).toString().replaceAll("ã", "a")
			}else if (i==5){
				renovacao[d] = data.getValue(i, v).toString().replaceAll("ã", "a")
			}else if(i==6){
				renovacao[d] = data.getValue(i, v).toString().replaceAll(".", "/")
			} else {
				renovacao[d] = data.getValue(i, v)
			}
		}

		for (int j = 0; j < renovacao.size();j++ ){
			System.err.println("["+j+"] [ "+renovacao[j]+"]")
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param listaExcel
	 * @return
	 */
	public static List<String> removeContratosDuplicados(){
		List<String> listaContratos = new ArrayList<String>()
		HashSet<String> corretor = new HashSet<String>();

		for (int i = 1; i <= data.getRowNumbers(); i++) {
			corretor.add(data.getValue(1, i))
		}

		Object[] myArr = corretor.toArray();
		for(int i = 0; i<myArr.length; i++){
			listaContratos.add(myArr[i].toString())
		}
		return listaContratos;
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param aba
	 * @return
	 */
	public static fluxoRenovacaoAutoFrotaFuncionalidades(String tipoAba){
		fluxoCompletoRenovacaoAutoFrota(tipoAba)
		reprocessafluxoCompletoRenovacaoAutoFrotaFuncionalidades(tipoAba)
		enviarEmailfluxoCompletoRenovacaoAutoFrotaFuncionalidades(tipoAba)
		WebUI.closeBrowser();
	}

	/**
	 * @
	 * @param tipoAba
	 * @return
	 */
	public static fluxoCompletoRenovacaoAutoFrota(String tipoAba){
		initDataFiles()
		if(carregaDados()){
			iniciar(tipoAba)

			/**
			 * Elimina itens duplicados
			 */
			listaContratos = removeContratosDuplicados()
			for(int j = 0; j < listaContratos.size();j++){
				System.out.println("\n["+j+"] SEARCH ["+listaContratos[j]+"]")
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					if(listaContratos[j].equals(data.getValue(1, i))){
						if(data.getValue(cell, i).equals("Processar")){
							System.out.println("--["+listaContratos[j] +"] FOUND ["+data.getValue(1, i)+"] STATUS ["+data.getValue(cell, i)+"] FOUND ["+data.getValue(1, i)+"]")
							alerta = ""
							evidencia=""
							alertaAceitacao=""
							alertaDadosBasicos=""
							coberturaBloqueada = false
							alertaFluxoDadosBasicos = ""
							chassiFipe =""
							try{
								List<String> renovacao = new ArrayList<>();
								lista(renovacao,i)
								if(!renovacaoAutoFrota(renovacao)){
									WebUI.closeBrowser();
									iniciar(tipoAba)
								}
							}catch (Exception e){}
							break
						}
					}
				}
			}
		}
	}

	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static iniciar(String tipoAba){
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands(tipoAba);
		WebUI.delay(10);
		menuProducao()
		WebUI.delay(10);
		/**
		 * TELA GESTÃO INTEGRADA
		 */
		alteraJanejaGenerica(1,2);
		WebUI.maximizeWindow()
	}

	/**
	 * @author be54955
	 */
	public static boolean renovacaoAutoFrota(List<String> renovacao){
		boolean exec
		System.out.println()
		try{
			/**
			 *MENU PRODUÇÃO
			 */
			//Emissão
			clickXpath('//*[@id="tipoDeSeleccion1"]', 2);
			//Agrupamento
			selecionaItemLista('Auto Frota', '//*[@id="agrupacion"]', 'option')
			//Produto
			selecionaItemLista('Novo Frota - Automovel', '//*[@id="producto"]', 'option')
			//Corretor
			limpaCampo('//*[@id="mediador"]', 2)
			preencheXpath('//*[@id="mediador"]',renovacao[4], 2);
	//		commandTAB('//*[@id="mediador"]')
			WebUI.delay(5);

			/**
			 * MENU PRODUÇÃO
			 */
			try{
				alertaFluxoDadosBasicos  = pegaMSGAlerta()
			} catch (Exception e){}
			System.out.println()
			System.out.println(alertaFluxoDadosBasicos)
			if(!alertListBlock(alertaFluxoDadosBasicos)){
				alertaFluxoDadosBasicos = ""
				//Selecionar
				selecionaItemLista('Selecionar', '//*[@id="datosSeleccionSectionBar"]/table/tbody/tr/td', 'div')
				//Operativa
				selecionaItemLista('Emitir Apólice de Multisituação Ampliação', '//*[@id="codOperativa"]', 'option')
				//Avançar
				selecionaItemLista('Avançar', '//*[@id="seleccionOperativaSectionBar"]/table/tbody/tr/td', 'div')
				/**
				 * D. BÁSICOS
				 */
				//Início de Vigência
				limpaCampo('//*[@id="DatosGeneralesBean$fechaEfecto"]', 2)
				preencheXpath('//*[@id="DatosGeneralesBean$fechaEfecto"]',renovacao[6], 2);
				//Tipo de Período
				selecionaItemLista('Anual', '//*[@id="DatosGeneralesBean$tipoPeriodo"]', 'option')
				//Forma de pagamento
				selecionaItemLista(renovacao[9], '//*[@id="DatosGeneralesBean$primRecibo"]', 'option')
				//Dia do Pagamento
				preencheXpath('//*[@id="DatosGeneralesBean$diaPago"]',renovacao[8], 2);
				//CPF/CNPJ Tomador
				preencheXpath('//*[@id="IntervinientesBean$nifTomador_doc"]',renovacao[11], 2);
				//Nome do Tomador
				preencheXpath('//*[@id="IntervinientesBean$nomFigTomadorValue"]',renovacao[11], 2);
				//Avançar
				selecionaItemLista('Avançar', '//*[@id="divAppContent"]', 'div')
				try{
					WebUI.delay(2);
					selecionaItemLista('Avançar', '//*[@id="divAppContent"]', 'div')
				} catch (Exception e){}

				//System Fail
				alertaFluxoDadosBasicos = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 2)
				if(alertListBlockSystem(alertaFluxoDadosBasicos)){

					String msgDadosBasicos = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','innerText', 2)
					if(alertListContinue(msgDadosBasicos.replaceAll('[0-9]',''))){
						alertaDadosBasicos = msgDadosBasicos
						selecionaItemLista('Avançar', '//*[@id="divAppContent"]', 'div')
					}

					//System Fail
					alertaFluxoDadosBasicos = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 2)
					if(alertListBlockSystem(alertaFluxoDadosBasicos)){

						String msgAceitacao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','innerText', 2)
						if(alertListContinue(msgAceitacao)){
							alertaAceitacao = msgAceitacao
						}

						/**
						 ********** INCLUIR CARRO (INTEM) **********
						 */
						List<List> itens = new ArrayList<>()
						listaItens(renovacao[0],itens)
						registraMensagemItem()
						WebUI.delay(5);
						selecionaListaChassi()
						selecionaListaFIPE()
						chassiAlteradoPorFIPE()

						//Avançar
						selecionaItemLista('Avançar', '//*[@id="dadosRiscoFrota"]/table[2]/tbody/tr[2]/td', 'div')
						WebUI.delay(5);
						deletaItemComRestrincao(itens,renovacao )

						//Alerta
						WebUI.delay(5);
						String msgItens = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','innerText', 2)
						try{
							if(alertListContinue(msgItens)){
								selecionaItemLista('Avançar', '//*[@id="dadosRiscoFrota"]/table[2]/tbody/tr[2]/td/table', 'div')
							}
						} catch (Exception e){}

						//Avançar Opcional
						try{
							selecionaItemLista('Avançar', '//*[@id="dadosRiscoFrota"]/table[2]/tbody/tr[2]/td', 'div')
							WebUI.delay(5);
						} catch (Exception e){}

						//Opção
						selecionaItemLista('por Itens', '//*[@id="x_filtro"]', 'option')

						/**
						 ********** CONFIGURAÇÃO DE COBERTURAS *********
						 */
						List<List> itensParaCoberturas = new ArrayList<>()
						configuracaoCoberturas(renovacao[0],itensParaCoberturas)

						//Registra Alertas das coberturas
						registraAlertaCoberturas()
						WebUI.delay(5);
						//Avançar
						selecionaItemLista('Avançar', '//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td', 'div')
						//Verifica se tem item com bloqueio e exclui dependendo do alerta
						alertaIbemBloqueado(itensParaCoberturas)

						if(coberturaBloqueada){
							WebUI.delay(2);
							fechaAlerta()
							WebUI.delay(10);
							//Avançar
							selecionaItemLista('Avançar', '//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td', 'div')
						}

						String erro = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','innerText', 2)
						if(alertListCobertura(erro)){
							WebUI.delay(2);
							fechaAlerta()
							WebUI.delay(10);
							//Avançar
							selecionaItemLista('Avançar', '//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td', 'div')
						}

						System.out.println()
						//Alerta
						String msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','innerText', 2)
						if(alertListContinue(msg)){
							selecionaItemLista('Avançar', '//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td', 'div')
						}

						/**
						 * TABELA DE PREÇOS
						 */
						String precoAnterior = recuperaValor('//*[@id="modalidad_0_0_primaRecibo"]', 'value', 2)
						//Comissão
						limpaCampo('//*[@id="ComisionamientoBean$porcComision"]', 1)
						preencheXpath('//*[@id="ComisionamientoBean$porcComision"]',renovacao[35], 2);

						try{
							//Calcular
							selecionaItemLista('Calcular', '//*[@id="divAppContent"]', 'div')
						} catch (Exception e){}

						String precoPostarior = recuperaValor('//*[@id="modalidad_0_0_primaRecibo"]', 'value', 2)
						//String preco = "Preço anterior ["+ precoAnterior+"] Preço postarior["+precoPostarior+"] Comissão ["+renovacao[35]+"]"
						//String preco = "Valor final ["+precoPostarior+"]"
						alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','innerText', 2)
						if(alertListBlock(alerta)){
							exec = false
						} else {
							alerta = ""
							try{
								fechaAlerta()
							} catch (Exception e){}

							System.out.println()
							WebUI.delay(5);
							String operacao = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 2)
							operacao.replaceAll('TABELA DE PREÇOS : 1251-FROTAS - AUTOMOVEL', '')
							String cotacao = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 2)
							salvarAlertas(itensParaCoberturas, cotacao, operacao, precoPostarior)
							exec = true
						}
						//Home
						home()

						// System Fail
					} else{
						exec = false
						salvarAlertasDeFluxo(renovacao[0])
						//Home
						home()
					}

					// System Fail
				} else{
					exec = false
					salvarAlertasDeFluxo(renovacao[0])
					//Home
					home()
				}

				/**
				 * MENU PRODUÇÃO
				 */
			} else {
				exec = false
				salvarAlertasDeFluxo(renovacao[0])
				//Home
				home()
			}

		} catch (Exception e){
			System.err.println(e)
			exec = false

		}
		System.err.println("["+exec+"]")
		return exec
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static deletaItemComRestrincao(List<List> itens, List<List> renovacao){
		try{
			int total = totalDeAlertaRestricao(itens)
			System.out.println("Total de restrinções ["+total+"]")

			for(int i = 0; i < total; i++){
				verificaAlertaRestricao()
			}
			WebUI.delay(5);
			selecionaListaChassi()
			selecionaListaFIPE()
			totalDelinhaComRestrincao()
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static verificaAlertaRestricao(){
		while(existeAlertaRestricao()){
			alertaRestricao()
		}
		WebUI.delay(2);
		selecionaItemLista('Avançar', '//*[@id="dadosRiscoFrota"]/table[2]/tbody/tr[2]/td/table', 'div')
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static alertaRestricao(){
		boolean b = false
		try{
			String x = '//*[@id="scrollTable"]'
			String t = 'img'
			selecionaListaDeElementos(x,t);
			for(int i = 0; i < listOfElements.size(); i++){
				String msg = recuperaValor('//*[@id="tr_'+i+'"]/td[1]/img[5]', 'title', 1)
				System.out.println("["+i+"] ["+msg+"]")
				if(msg.contains("Restrição técnica para o veículo".toString()) || msg.contains("- Nenhum código fipe válido informado.".toString())){
					clickScript('//*[@id="tr_'+i+'"]/td[1]//img[@title="Excluir"]')
					WebUI.delay(5);
					b = true
					break
				}
			}
		} catch (Exception e){}
		return b
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean existeAlertaRestricao(){
		boolean b = false
		try{
			String x = '//*[@id="scrollTable"]'
			String t = 'img'
			selecionaListaDeElementos(x,t);
			for(int i = 0; i < listOfElements.size(); i++){
				String msg = recuperaValor('//*[@id="tr_'+i+'"]/td[1]/img[5]', 'title', 1)
				if(msg.contains("Restrição técnica para o veículo".toString()) || msg.contains("- Nenhum código fipe válido informado.".toString()) ){
					System.out.println("["+i+"] ["+msg+"]")
					b = true
					break
				}
			}
		} catch (Exception e){}
		return b
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static int totalDeAlertaRestricao(List<List> itens){
		int total
		try{
			String x = '//*[@id="scrollTable"]'
			String t = 'img'
			selecionaListaDeElementos(x,t);
			for(int i = 0; i < listOfElements.size(); i++){
				String msg = recuperaValor('//*[@id="tr_'+i+'"]/td[1]/img[5]', 'title', 1)
				if(msg.contains("Restrição técnica para o veículo".toString()) || msg.contains("- Nenhum código fipe válido informado.".toString()) ){
					System.out.println("["+i+"] ["+msg+"]")
					String linha = retornaLinha(i, itens)
					salvarAlertaItem(i, msg,linha)
					linhasComRestrincao.add(linha)
					total = total+1
				}
			}
		} catch (Exception e){}
		return total
	}

	/**
	 * @author be54955
	 * @param item
	 * @param itens
	 * @return
	 */
	public static String retornaLinha(int item, List<List> itens){
		String linha
		int x = item -1
		System.out.println("["+x+"] ["+item+"]")
		for(int i = 0; i < listaChassi.size(); i++){
			if(i==x){
				//System.out.println("LINHA DO ITEM ["+ retornaLinhaDoItem( listaChassi.get(i).toString(), itens )+"]" )
				linha = retornaLinhaDoItem( listaChassi.get(i).toString(), itens )
			}
		}
		return linha
	}

	/**
	 * @author be54955
	 * @param chassi
	 * @param itens
	 * @return
	 */
	public static String retornaLinhaDoItem(String chassi, List<List> itens){
		String linha
		//System.out.println("Itens Size ["+itens.size()+"] CHASSI TO FOUND ["+chassi.trim()+"]")
		for (int j = 0; j < itens.size();j++ ){
			//System.err.println("LINHA ["+itens[j][37].toString().trim()+"] CHASSI ["+itens[j][14]+"]")
			if(itens[j][14].toString().trim().equals(chassi.trim())){
				//System.err.println("FOUND LINHA ["+itens[j][37]+"] CHASSI ["+itens[j][14]+"]")
				linha = itens[j][37]
				break
			}
		}
		return linha
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static selecionaListaChassi(List<List> renovacao){
		System.out.println()
		try{
			String x = '//*[@id="itensBody-scroll"]'
			String t = 'tr'
			selecionaListaDeElementos(x,t);
			for(int i = 0; i < listOfElements.size(); i++){
				String s = listOfElements.get(i).getText()
				System.out.println("["+i+"] [ "+s+"]")
				if(placaVazia(s)){
					if(identificaItem(s)){
						listaChassi.add( listOfElements.get(i).getText().substring(10, 28).toString())
					} else{
						if(randomPlacaVazia(s)){
							listaChassi.add( listOfElements.get(i).getText().substring(4, 22).toString())
						} else {
							listaChassi.add( listOfElements.get(i).getText().substring(9, 27).toString())
						}
					}

				} else {

					if(identificaItem(s)){
						listaChassi.add( listOfElements.get(i).getText().substring(10, 29).toString())
					} else {
						listaChassi.add( listOfElements.get(i).getText().substring(9, 28).toString())
					}

				}
			}

			for(int j = 0; j < listaChassi.size(); j++){
				System.out.println("["+j+"] ["+listaChassi.get(j).toString()+"]")
			}
			System.out.println("Total de chassis ["+listaChassi.size()+"]")
			System.out.println()
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param placa
	 * @return
	 */
	public static boolean placaVazia(String s){
		System.out.println()
		boolean p = false
		StringBuffer strbf = new StringBuffer()
		String []v = s.split("")
		for(int i = 0; v.size(); i++){
			//System.out.println("["+i+"] - ["+v[i]+"]")
			if(i>= 2){
				strbf.append(v[i])
				if(v[i].toString().contains(" ")){
					break
				}
			}
		}
		String nValueSrtbf = strbf.toString()
		if(nValueSrtbf.length()<7){
			p = true
		}
		System.out.println("PLACA ["+nValueSrtbf+"] LENGTH ["+nValueSrtbf.trim().length()+"] ["+p+"]")
		return p
	}

	/**
	 * @author be54955
	 * @param placa
	 * @return
	 */
	public static boolean randomPlacaVazia(String s){
		System.out.println()
		boolean p = false
		StringBuffer strbf = new StringBuffer()
		String ns = s.trim()
		String []v = ns.split("")
		for(int i = 0; v.size(); i++){
			//System.out.println("RP ["+i+"] - ["+v[i]+"]")
			if(i==2){
				System.out.println("PLACA ["+i+"] - ["+v[i]+"]")
				strbf.append(v[i].trim())
				break
			}
		}

		String nValueSrtbf = strbf.toString()
		//System.out.println("STR LENGTH ["+nValueSrtbf.length()+"]")
		if(nValueSrtbf.length()==1){
			p = true
		}
		System.out.println("RANDOM PLACA ["+nValueSrtbf+"] LENGTH ["+nValueSrtbf.trim().length()+"] ["+p+"]")
		return p
	}

	/**
	 * @author be54955
	 * @param s
	 * @return
	 */
	public static boolean identificaItem(String s){
		boolean item = false
		String strItem = s.substring(0, 2)
		String strNitem = strItem.trim()
		int numItem = Integer.parseInt(strNitem)
		if(numItem > 9){
			item = true
		}
		System.out.println("ITEM ["+numItem+"] ["+item+"]")
		return item
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static selecionaListaFIPE(){
		try{
			String x = '//*[@id="itensBody-scroll"]'
			String t = 'tr'
			selecionaListaDeElementos(x,t);
			for(int i = 0; i < listOfElements.size(); i++){
				int j = i+1
				//System.out.println("["+j+"] ["+recuperaValor('//*[@id="td_4_'+j+'"]', 'innerText', 2)+"]")
				listaFIPE.put(Integer.toString(j), recuperaValor('//*[@id="td_4_'+j+'"]', 'innerText', 1))
			}
			System.out.println("Total de FIPEs ["+listaFIPE.size()+"]")
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param itens
	 * @return
	 */
	public static alertaIbemBloqueado(List<List> itens){
		boolean v = false
		try{
			for (int j = 0; j < itens.size();j++ ){
				int x = j+1
				String msg = mensagenCoberturas.get( String.valueOf(x))
				System.err.println("["+x+"] ["+msg.substring( 19,msg.length())+"]")
				if(  alertListBlock(msg.substring( 19,msg.length())) ) {
					System.err.println("\nBLOQ. ITEM ["+x+"] LINHA ["+itens[j][37]+"] PLACA ["+itens[j][13]+"] Contrato ["+itens[j][0]+"]")
					//Voltar
					selecionaItemLista('Voltar', '//*[@id="divAppContent"]/table/tbody/tr[1]/td/table', 'div')
					excluirItem(itens[j][13].toString().trim())
					break
				}
			}
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param placa
	 * @return
	 */
	public static excluirItem(String placa){
		System.out.println()
		WebUI.delay(5);
		String x = '//*[@id="tb-itens"]'
		String t = 'tr'
		selecionaListaDeElementos(x,t);
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements.get(i).getText().contains(placa.toString())){
				System.out.println("["+i+"] ["+listOfElements.get(i).getText()+"] FOUND ["+placa+"]")
				clickScript('//*[@id="tr_'+i+'"]/td[1]//img[@title="Excluir"]')
				WebUI.delay(5);
				break
			}
		}
		//Avançar
		selecionaItemLista('Avançar', '//*[@id="dadosRiscoFrota"]/table/tbody/tr[2]/td', 'div')
		WebUI.delay(5);
		//Alerta
		String msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','innerText', 2)
		if(alertListContinue(msg)){
			selecionaItemLista('Avançar', '//*[@id="dadosRiscoFrota"]/table/tbody/tr[2]/td', 'div')
			WebUI.delay(5);
		}
		//Alerta
		String msgCoberturas = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','innerText', 2)
		if(alertListContinue(msgCoberturas)){
			selecionaItemLista('Avançar', '//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td', 'div')
			WebUI.delay(5);
		}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static home(){
		WebUI.delay(5);
		String x = '//*[@id="cellMenuIconsAppNaviHeaderFilledArea"]'
		String t = 'img'
		selecionaListaDeElementos(x,t);
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements.get(i).getAttribute("title").contains("Início".toString())){
				listOfElements.get(i).click()
				break
			}
		}
		WebUI.delay(5);
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static salvarAlertasDeFluxo(String contrato){
		try{
			List<List> itens = new ArrayList<>()
			System.out.println(data.getRowNumbers()+" "+contrato)
			for(int v = 1; v <= data.getRowNumbers(); v++) {
				if(contrato.equals(data.getValue(1, v))){
					List<String> item = new ArrayList<>();
					for(int i = 1; i <= data.getColumnNumbers(); i++) {
						int d = i-1
						item[d] = data.getValue(i, v)
					}
					itens.add(item);
				}
			}
			System.out.println(itens.size())
			alertaDadosBasicos = alertaFluxoDadosBasicos
			salvarAlertas(itens, "", "", "")
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static salvarAlertas(List<List> itens, String cotacao, String operacao, String precoCalculado){
		for (int j = 0; j < itens.size();j++ ){
			int x = j+1
			System.err.println("\nITEM ["+x+"] LINHA ["+itens[j][37]+"] PLACA ["+itens[j][13]+"] Contrato ["+itens[j][0]+"] Status ["+itens[j][36]+"]")
			if(!linhaComRestrincao(itens[j][37])){
				try{
					int line = Integer.parseInt(itens[j][37].toString())
					auxiliares.WriteExcelFactory.updateSheetLine('', line, path, "Processado", 36 , 'RenovacaoAutoFrota');
					auxiliares.WriteExcelFactory.updateSheetLine('', line, path, Integer.toString(x), 38 , 'RenovacaoAutoFrota');
					auxiliares.WriteExcelFactory.updateSheetLine('', line, path, alertaDadosBasicos, 39 , 'RenovacaoAutoFrota');
					auxiliares.WriteExcelFactory.updateSheetLine('', line, path, alertaAceitacao, 40 , 'RenovacaoAutoFrota');
					//Alerta Item
					String alertaItem = mensagensItens.get( String.valueOf(x))+" "+ mensagensItensPopUp.get( String.valueOf(x))+" "+alertasItens.get( String.valueOf(x))
					auxiliares.WriteExcelFactory.updateSheetLine('', line, path, alertaItem.replaceAll("null","") , 41 , 'RenovacaoAutoFrota');
					//Alerta da cobertura
					String alertaCobertura = mensagenCoberturas.get( String.valueOf(x)  )
					auxiliares.WriteExcelFactory.updateSheetLine('', line, path, alertaCobertura.replaceAll("null","") , 42 , 'RenovacaoAutoFrota');
					//Alerta da cotação
					//auxiliares.WriteExcelFactory.updateSheetLine('', line, path, cotacao, 43 , 'RenovacaoAutoFrota');
					//Alerta Operação
					auxiliares.WriteExcelFactory.updateSheetLine('', line, path, operacao.substring(44, 53), 44 , 'RenovacaoAutoFrota');
					auxiliares.WriteExcelFactory.updateSheetLine('', line, path, precoCalculado, 45 , 'RenovacaoAutoFrota');
					auxiliares.WriteExcelFactory.updateSheetLine('', line, path, listaFIPE.get( String.valueOf(x)) , 46 , 'RenovacaoAutoFrota');
				} catch (Exception e){}
			}
		}
	}

	/**
	 * @author be54955
	 */
	public static boolean linhaComRestrincao(String linha){
		boolean l = false
		for(int i = 0; i < linhasComRestrincao.size();i++){
			if(linhasComRestrincao.get(i).toString().equals(linha)){
				l = true
				System.out.println("LINHA COM RESTRINÇÃO ["+linha+"] ["+l+"]")
			}
		}
		return l
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static  totalDelinhaComRestrincao(){
		for(int i = 0; i < linhasComRestrincao.size();i++){
			System.out.println("LINHA COM RESTRINÇÃO ["+linhasComRestrincao.get(i)+"]")
		}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static salvarAlertaItem(int item, String alertaItem, String strLinha){
		System.err.println("\nITEM LINHA ["+item+"] ALERTA ITEM ["+alertaItem+"] LINHA "+strLinha+"]")
		System.out.println()
		int linha = Integer.parseInt(strLinha)
		try{
			auxiliares.WriteExcelFactory.updateSheetLine('', linha, path, "Processado", 36 , 'RenovacaoAutoFrota');
			auxiliares.WriteExcelFactory.updateSheetLine('', linha, path, Integer.toString(item), 38 , 'RenovacaoAutoFrota');
			auxiliares.WriteExcelFactory.updateSheetLine('', linha, path, alertaItem , 41 , 'RenovacaoAutoFrota');
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param c
	 * @return
	 */
	public static boolean verificaComissao(String c){
		boolean r = false
		int valor = Integer.parseInt(c)
		if(valor < 15){
			r = true
		}
		return r
	}

	/**
	 * @author be54955
	 * @param contrato
	 * @return
	 */
	public static listaItens(String contrato,List<List> itens ){
		for(int v = 1; v <= data.getRowNumbers(); v++) {
			if(contrato.equals(data.getValue(1, v))){
				List<String> item = new ArrayList<>();
				for(int i = 1; i <= data.getColumnNumbers(); i++) {
					int d = i-1
					item[d] = data.getValue(i, v)
				}
				itens.add(item);
			}
		}

		System.out.println("\n------Itens na lista ["+itens.size()+"]------\n")

		for (int j = 0; j < itens.size();j++ ){
			List<String> tokenItem = new ArrayList<>()
			//System.err.println("["+j+"] [ "+itens[j]+"]\n")
			if(itens[j][36].toString().equals("Processar")){
				for(int k = 0; k<= data.getColumnNumbers(); k++){
					System.out.println("["+k+"] ["+itens[j][k]+"]")
					tokenItem.add(itens[j][k])
				}
				incluirItem(tokenItem, j)
			}
		}
	}

	/**
	 * @author be54955
	 * @param renovacao
	 * @return
	 */
	public static incluirItem(List<String> renovacao, int item){

		System.out.println("\nITEM - PLACA ["+renovacao[13]+"] Chassi ["+renovacao[14]+"] ITEM ["+item+"]")
		System.out.println()
		alertaValorReferencia=""
		/**
		 * INCLUIR INTEM
		 */
		WebUI.delay(5);
		selecionaItemLista('INCLUIR ITEM', '//*[@id="incluirItem"]/table/tbody/tr[1]/td/table', 'td')
		if(renovacao[14].length()> 0){
			//Chassi
			preencheXpath('//*[@id="chassi"]',renovacao[14].trim(), 2);
			//Lupa
			clickXpath('//*[@id="consultarChassi"]', 2);
		} else {
			//Placa
			preencheXpath('//*[@id="placa"]',renovacao[13].trim(), 2);
			//Lupa
			clickXpath('//*[@id="consultarPlaca"]', 2);
		}

		/**
		 * Alertas de chassi ou placa
		 */
		WebUI.delay(10);
		String alertItem = pegaMSGAlerta()
		try{
			if(alertListBlock(alertItem)){
				//Placa
				limparCamposInput('//*[@id="placa"]')

				if(renovacao[16].contains("Sim") ){
					//Chassi
					preencheXpath('//*[@id="chassi"]',renovacao[14].trim(), 2);
					//Lupa
					clickXpath('//*[@id="consultarChassi"]', 2);
				} else {
					//Placa
					preencheXpath('//*[@id="placa"]',renovacao[13].trim(), 2);
					//Lupa
					clickXpath('//*[@id="consultarPlaca"]', 2);

					try{
						listaDeVeiculos(renovacao[19].replaceAll("-",""), item )
					} catch (Exception e){}

					/**
					 * Lista com chassi carregado plea plataforma após alerta de FIPE
					 */
					WebUI.delay(5)
					//listaChassiFipe.add(recuperaValor('//*[@id="chassi"]', 'value', 2))

					String chassi = recuperaValor('//*[@id="chassi"]', 'value', 2)
					System.out.println("["+chassi+"]")
					maplistaChassiFipe.put(chassi,chassi)
					System.out.println("MAP - ["+maplistaChassiFipe+"]")
					//	chassiAlteradoPorFIPE()
				}
			}
		} catch (Exception e){}

		//Opcional - Lista de veículos
		if(!listaDeVeiculos(renovacao[19].replaceAll("-",""), item )){
			//System.out.println("FIPE não encontrado")
			try{
				clickXpath('//*[@id="myModal"]/div/span', 2);
			} catch (Exception e){}
		}

		if(identificaSemiReboques(recuperaValor('//*[@id="x_marca_mod_ver"]','value', 2))){
			//Valor de referência
			String vr = renovacao[24].replaceAll(",00", "")
			String vd = vr.replace(".", "")
			System.out.println("VALOR DETERMINADO ["+vd+"]")
			int v = Integer.parseInt(vd)
			if(v==0){
				preencheXpath('//*[@id="x_valorReferencia"]','1000', 2);
			} else {
				preencheXpath('//*[@id="x_valorReferencia"]',vd, 2);
			}
		}

		//Classe de bonus
		selecionaItemLista(renovacao[15], '//*[@id="x_classeBonus"]', 'option')
		//ZeroKM
		selecionaItemLista(renovacao[16], '//*[@id="x_zeroKMFrota"]', 'option')
		//Estado
		selecionaItemListaEquals(renovacao[17].trim(), '//*[@id="x_estado"]', 'option')
		WebUI.delay(2);
		//Municipio
		selecionaItemLista(renovacao[18].trim(), '//*[@id="x_municipio"]', 'option')
		//Gravar
		selecionaItemLista('Gravar', '//*[@id="incluirItemSectionBar"]/table/tbody/tr/td', 'div')

		WebUI.delay(5);
		identificaPlacaNaoPreenchidaParaCarroNaoZero(renovacao)
		WebUI.delay(5);

		String badRequest
		try{
			// 400 Bad Request
			badRequest = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
			System.out.println("["+badRequest+"] ["+alertaValorReferencia+"] ["+alertaValorReferencia.length()+"]")
		} catch (Exception e){}

		try{
			if(alertaValorReferencia.length()>0){
				if(alertListBlock(alertaValorReferencia)){
					int i=item+1
					salvarAlertaItem(i, alertaValorReferencia, renovacao[37])
					selecionaItemLista('INCLUIR ITEM', '//*[@id="incluirItem"]/table/tbody/tr[1]/td/table', 'td')
				}
			}
		} catch (Exception e){}


		if(alertListBlock(badRequest)){
			alertaFluxoDadosBasicos = badRequest
			salvarAlertasDeFluxo(renovacao[0])
			//Home
			home()

		} else {
			//Registra mensagem
			WebUI.delay(5);
			try{
				registraMensagemItemPopUp(item, renovacao,alertItem )
			} catch (Exception e){}
		}
		WebUI.delay(5);
	}

	/**
	 * @author be54955
	 * @param vr
	 * @return
	 */
	public static boolean identificaSemiReboques(String s){
		boolean valor
		if(s.contains("semi-reboque")){
			valor = true
		} else {
			valor = false
		}
		return valor
	}

	/**
	 * @author be54955
	 * @param renovacao
	 * @return
	 */
	public static identificaPlacaNaoPreenchidaParaCarroNaoZero(List<String> renovacao){
		if(alertListBlock(pegaMSGAlerta())){
			try{
				fechaAlerta()
				fechaAlerta()
				fechaAlerta()
				fechaAlerta()
			} catch (Exception e){}

			if(renovacao[16].toString().equals("Não")){
				Random ran = new Random()
				int n = ran.nextInt(4)
				String v = Integer.toString(n)
				//Placa
				preencheXpath('//*[@id="placa"]',v, 2);
				//Gravar
				selecionaItemLista('Gravar', '//*[@id="incluirItemSectionBar"]/table/tbody/tr/td', 'div')
				alertaValorReferencia = pegaMSGAlerta()
				System.out.println("["+alertaValorReferencia+"]")
				if(alertListBlock(alertaValorReferencia)){
					try{
						fechaAlerta()
						fechaAlerta()
						fechaAlerta()
						fechaAlerta()
					} catch (Exception e){}
					//Limoar
					clickXpath('//*[@id="x_btnlimpar"]', 2);
				}

			}
		}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static registraMensagemItemPopUp(int item, List<String> renovacao, String alertItem){
		System.out.println()
		item = item +1
		String strItem = Integer.toString(item)
		String msg
		WebUI.delay(2);
		msg = pegaMSGAlerta()
		System.err.println("\nITEM ["+strItem+"] MENSAGEM ["+msg+"] ALERA ITEM ["+alertItem+"]\n")
		try{
			fechaAlerta()
			fechaAlerta()
			fechaAlerta()
			fechaAlerta()
		} catch (Exception e){}

		try{
			WebUI.delay(2);
			mensagensItensPopUp.put(strItem,msg.trim());
			if(msg.contains("Chassi não preenchido!".toString())){
				//Placa
				limpaCampo('//*[@id="placa"]', 2)
				//Chassi
				preencheXpath('//*[@id="chassi"]',renovacao[14].trim(), 2);
				//Lupa
				clickXpath('//*[@id="consultarChassi"]', 2);
				WebUI.delay(5);

				try{
					msg = pegaMSGAlerta()
					System.err.println("\nITEM ["+strItem+"] MENSAGEM ["+msg+"]\n")
				} catch (Exception e){}

				if(codigoFipeAusente()){
					//Opcional - Lista de veículos
					if(!listaDeVeiculos(renovacao[19].replaceAll("-",""), item )){
						//System.out.println("FIPE não encontrado")
						try{
							clickXpath('//*[@id="myModal"]/div/span', 2);
						} catch (Exception e){}
					}
					//Gravar
					selecionaItemLista('Gravar', '//*[@id="incluirItemSectionBar"]/table/tbody/tr/td', 'div')
					WebUI.delay(5);
					placaNaoPreenchida(renovacao)
				}

			} else if(alertListBlock(msg)){
				//Limpar
				selecionaItemLista('Limpar', '//*[@id="incluirItemSectionBar"]/table/tbody/tr/td/table', 'div')
				//Incluir Itens
				selecionaItemLista('INCLUIR ITEM', '//*[@id="incluirItem"]/table/tbody/tr[1]/td/table', 'td')
				salvarAlertaItem(item, msg+" "+alertItem, renovacao[14].trim())
			} else if(alertListContinue(msg)){
				//Limpar
				selecionaItemLista('Limpar', '//*[@id="incluirItemSectionBar"]/table/tbody/tr/td/table', 'div')
				//Incluir Itens
				selecionaItemLista('INCLUIR ITEM', '//*[@id="incluirItem"]/table/tbody/tr[1]/td/table', 'td')
			} else if(alertListBlock(alertItem)){
				//Limpar
				selecionaItemLista('Limpar', '//*[@id="incluirItemSectionBar"]/table/tbody/tr/td/table', 'div')
				//Incluir Itens
				selecionaItemLista('INCLUIR ITEM', '//*[@id="incluirItem"]/table/tbody/tr[1]/td/table', 'td')
				alertasItens.put(strItem,alertItem.trim());
				linhasComRestrincao.add(renovacao[37].trim())
				salvarAlertaItem(item, alertItem, renovacao[37].trim())
			}

		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean codigoFipeAusente(){
		boolean prosseguir = true
		try{
			String msg = pegaMSGAlerta()
			fechaAlerta()
			if(msg.contains("Nenhum código FIPE retornado.".toString())){
				//Limpar
				selecionaItemLista('Limpar', '//*[@id="incluirItemSectionBar"]/table/tbody/tr/td/table', 'div')
				//Incluir Itens
				selecionaItemLista('INCLUIR ITEM', '//*[@id="incluirItem"]/table/tbody/tr[1]/td/table', 'td')
				prosseguir = false
			}
		} catch (Exception e){}
		return prosseguir
	}

	/**
	 * @author be54955
	 * @param renovacao
	 * @return
	 */
	public static placaNaoPreenchida(List<String> renovacao){
		try{
			String msg = pegaMSGAlerta()
			fechaAlerta()
			if(msg.contains("Placa não preenchida!".toString())){
				//Placa
				preencheXpath('//*[@id="placa"]',renovacao[13].trim(), 2);
				//Gravar
				selecionaItemLista('Gravar', '//*[@id="incluirItemSectionBar"]/table/tbody/tr/td', 'div')
			}
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static registraMensagemItem(){
		try{
			WebUI.delay(5);
			String x = '//*[@id="itensBody-scroll"]'
			String t = 'tr'
			selecionaListaDeElementos(x,t);
			for(int i = 0; i < listOfElements.size(); i++){
				String strb = listOfElements.get(i).getAttribute("textContent")
				strb.replaceAll('\\s+',' ')
				int z = i+1
				String result = strb.substring(auxiliares.BruteForceSubstringSearch.searchIndex( 'Incluído',strb) , strb.length() )
				System.err.println('Item ['+i+'] Warn ['+result.replaceAll('\\s+',' ')+"]")
				mensagensItens.put(Integer.toString(z), result.replaceAll('\\s+',' '))
			}
		}catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param fipe
	 * @return
	 */
	public static boolean listaDeVeiculos(String fipe, int item){
		boolean f = false
		alertaFIPE = true
		try{
			int fipeValue = Integer.parseInt(fipe)
			String strFipe = Integer.toString(fipeValue);
			WebUI.delay(5);
			String x = '//*[@id="tbodylistaVeiculos"]'
			String t = 'tr'
			selecionaListaDeElementos(x,t);
			int size = listOfElements.size()
			if(size>0){
				for(int i = 0; i < size; i++){
					System.out.println("["+i+"] ["+listOfElements.get(i).getText()+"]")
					if(listOfElements.get(i).getText().contains(strFipe.toString())){
						doubleClickXpath('//*[@id="dlv1linha'+i+'"]', 2)
						f = true
						alertaFIPE = false
						break
					}
				}
				if(alertaFIPE){
					for(int i = 0; i < size; i++){
						doubleClickXpath('//*[@id="dlv1linha'+i+'"]', 2)
						f = true
						break
					}
				}
			}
		} catch (Exception e){}
		System.out.println(f)
		return f
	}

	/**
	 * @author be54955
	 * @param contrato
	 * @return
	 */
	public static configuracaoCoberturas(String contrato,List<List> itens ){
		for(int v = 1; v <= data.getRowNumbers(); v++) {
			if(contrato.equals(data.getValue(1, v))){
				if(retornaChassiSelecionado(data.getValue(15, v))  || retornaChassiAlteradoFipe(listaChassi) ){
					List<String> item = new ArrayList<>();
					for(int i = 1; i <= data.getColumnNumbers(); i++) {
						int d = i-1
						item[d] = data.getValue(i, v)
					}
					itens.add(item);
				}
			}
		}

		System.out.println("\nItens na lista para configurar cobertura ["+itens.size()+"]\n")

		for (int j = 0; j <itens.size();j++ ){
			List<String> tokenItem = new ArrayList<>()
			System.err.println("["+j+"] [ "+itens[j]+"]\n")
			if(itens[j][36].toString().equals("Processar")){
				for(int k = 0; k<= data.getColumnNumbers(); k++){
					System.out.println("["+k+"] ["+itens[j][k]+"]")
					tokenItem.add(itens[j][k])
				}
				int item = j+1
				filtroOpcao(tokenItem,item)
			}
		}
	}

	/**
	 * @author be54955
	 * @param chassi
	 * @return
	 */
	public static boolean retornaChassiSelecionado(String chassi){
		boolean c = false
		try{
			for(int i=0; i<=listaChassi.size();i++){
				System.out.println("["+i+"] CHASSI ["+listaChassi.get(i)+"] SEARCH ["+chassi.trim()+"]")
				if(listaChassi.get(i).trim().toUpperCase().contains(chassi.toUpperCase().trim())){
					System.out.println("["+i+"] CHASSI ["+listaChassi.get(i)+"] FOUND ["+chassi.trim()+"]")
					c = true
					break
				}
			}
		} catch (Exception e){}
		return c
	}

	/**
	 * @author be54955
	 * @param chassi
	 * @return
	 */
	public static boolean retornaChassiAlteradoFipe(List<String> listaChassi){
		boolean c = false
		try{
			int tList = listaChassi.size()+1
			for(int i=0; i<tList;i++){
				System.out.println("["+i+"] LISTA CHASSI ALTERADO FIPE ["+listaChassi.get(i).trim()+"] ["+listaFipe(listaChassi.get(i).trim())+"]" )
				if(listaChassi.get(i).trim().contains(listaFipe(listaChassi.get(i).trim()))){
					c = true
					break
				}
			}
		} catch (Exception e){}
		return c
	}

	/**
	 * @author be54955
	 * @param chassi
	 * @return
	 */
	public static String listaFipe(String chassi){
		return maplistaChassiFipe.get(chassi).toString()
	}

	/**
	 * @author be54955
	 * @param renovacao
	 * @return
	 */
	public static filtroOpcao(List<String> renovacao, int numItem){
		WebUI.delay(5);
		System.out.println()
		System.out.println("Placa ["+renovacao[13].trim()+"] Item ["+numItem+"]")
		if(numItem>0){
			String x = '//*[@id="x_list_itens"]'
			String t = 'option'
			selecionaListaDeElementos(x,t);
			for(int i = 0; i < listOfElements.size(); i++){
				if(numItem == Integer.parseInt(listOfElements.get(i).getText())){
					System.err.println('['+i+'] Cobertura ['+listOfElements.get(i).getText()+"]")
					listOfElements.get(i).click()
					WebUI.delay(2);
					//Contrata Casco e(ou) Val. Determinado
					selecionaItemLista(renovacao[21], '//*[@id="x_val_det"]', 'option')

					if(renovacao[21].toString().equals("Sim")){
						if(autorizaFipe(renovacao[22])){
							//FIPE
							selecionaItemLista(renovacao[22], '//*[@id="x_perc_fipe"]', 'option')
						}
					} else if(renovacao[21].toString().equals("Não")) {
						//FIPE
						selecionaItemLista(renovacao[22], '//*[@id="x_perc_fipe"]', 'option')
					}

					//Tp. Franquia
					selecionaItemLista(renovacao[23], '//*[@id="x_tipo_franquia"]', 'option')

					//RCF - Danos Materiais
					if(renovacao[25].trim().equals("Não")){
						selecionaItemLista("Não contratado" , '//*[@id="x_danos_materiais"]', 'option')
					} else {

						WebUI.delay(2);
						selecionaItemLista(renovacao[25].trim().replaceAll(",00", "") , '//*[@id="x_danos_materiais"]', 'option')
					}

					//RCF - Danos Corporais
					if(renovacao[26].trim().equals("Não")){
						selecionaItemLista("Não contratado" , '//*[@id="x_danos_corporais"]', 'option')
					} else {

						WebUI.delay(2);
						selecionaItemLista( renovacao[26].trim().replaceAll(",00", "") , '//*[@id="x_danos_corporais"]', 'option')
					}

					//RCF – Danos Morais
					if(contratar(renovacao[27])){
						selecionaItemLista( renovacao[27].replaceAll(",00", "") , '//*[@id="x_danos_morais"]', 'option')
					} else {
						selecionaItemLista(renovacao[27] , '//*[@id="x_danos_morais"]', 'option')
					}

					//APP – Morte
					clickXpath('//*[@id="x_app_morte"]', 2)
					String appm = renovacao[28].replaceAll(",00", "")
					preencheXpath('//*[@id="x_app_morte"]',appm.replace(".", ""), 2);
					//APP - Invalidez
					clickXpath('//*[@id="x_app_invalidez"]', 2)
					String appi = renovacao[29].replaceAll(",00", "")
					preencheXpath('//*[@id="x_app_invalidez"]',appi.replace(".", ""), 2);
					//Acessórios gerais
					clickXpath('//*[@id="x_aces_gerais"]', 2)
					String ag = renovacao[30].replaceAll(",00", "")
					preencheXpath('//*[@id="x_aces_gerais"]',ag.replace(".", ""), 2);
					//Blindagem
					clickXpath('//*[@id="x_blindagem"]', 2)
					String bl = renovacao[31].replaceAll(",00", "")
					preencheXpath('//*[@id="x_blindagem"]',bl.replace(".", ""), 2);
					//Assistência 24 horas
					selecionaItemLista(renovacao[32], '//*[@id="x_assist24h"]', 'option')
					WebUI.delay(5);
					//Vidros
					selecionaItemLista(renovacao[33].trim(), '//*[@id="x_vidros"]', 'option')
					//Carro reserva
					try{
						selecionaItemLista(renovacao[34], '//*[@id="x_carro_reserva"]', 'option')
					} catch (Exception e){}

					System.out.println()

					//Aplicar configuração
					selecionaItemLista('Aplicar Configuração', '//*[@id="coberturasSectionSectionBar"]/table/tbody/tr/td', 'div')
					WebUI.delay(2);
					String alerta = pegaMSGAlerta()
					System.out.println(alerta)
					if(alertListCobertura(alerta)){
						coberturaBloqueada = true
					}
					fechaAlerta()
					WebUI.delay(10);
					System.out.println(coberturaBloqueada)
					//Opção
					selecionaItemLista('Todos', '//*[@id="x_filtro"]', 'option')
					selecionaItemLista('por Itens', '//*[@id="x_filtro"]', 'option')
					break
				}
			}
		}
	}

	/**
	 * @author be54955
	 * @param placa
	 * @return
	 */
	public static int recuperaItem(String placa){
		int item
		String x = '//*[@id="x_itensGrid"]'
		String t = 'tr'
		selecionaListaDeElementos(x,t);
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements.get(i).getText().contains(placa.toString().trim())){
				System.err.println('['+i+'] Found ['+listOfElements.get(i).getText()+"]")
				item = Integer.parseInt(listOfElements.get(i).getText().substring(0, 2))
				break
			}
		}
		return item
	}

	/**
	 * @author be54955
	 * @param placa
	 * @return
	 */
	public static boolean verificaDadosDoCarro(String dados){
		boolean p = false
		try{
			WebUI.delay(5);
			String x = '//*[@id="gridCoberturasItensContent"]/div[1]/table'
			String t = 'td'
			selecionaListaDeElementos(x,t);
			for(int i = 0; i < listOfElements.size(); i++){
				if(listOfElements.get(i).getText().contains(dados.toString())){
					System.err.println('['+i+'] Found ['+listOfElements.get(i).getText()+"]")
					p = true
					break
				}
			}
		}catch (Exception e){}
		return p
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static menuProducao(){
		MenuMatriz.producaoSinistro()
		WebUI.delay(5);
		MenuMatriz.subMenuNome('a',"Produção")
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static reprocessafluxoCompletoRenovacaoAutoFrotaFuncionalidades(String tipoAba){
		//	if(carregaDados()){
		System.err.println("[ Reprocessa Descontos Específicos Sinistro")
		fluxoCompletoRenovacaoAutoFrota(tipoAba)
		//		reprocessafluxoCompletoRenovacaoAutoFrotaFuncionalidades(tipoAba)
		//	}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailfluxoCompletoRenovacaoAutoFrotaFuncionalidades(String tipoAba){
		//		if(!carregaDados()){
		pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Auto\\RenovacaoAutoFrota\\ConfiguracoesRenovacaoAutoFrota.txt";
		lerConfigurações(pathTxtEmail)
		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
		List<String> anexos = new ArrayList<>();
		anexos[0] = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Auto\\RenovacaoAutoFrotaData\\RenovacaoAutoFrota.xlsx"
		anexos[1] = "RenovacaoAutoFrota.xlsx"
		sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos, host, porta, tipoAba)
		//		}
	}

	/**
	 * @author be54955
	 * @param alert
	 * @return
	 */
	public static boolean alertListContinue(String alert){
		System.err.println("\nalertListContinue ["+alert+"]")
		boolean r = false
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add(" 121: Restrição Técnica - Risco sem Aceitação")
		listOfAlerts.add(" Já existe cotação para esse CNPJ, número - ")
		listOfAlerts.add(" Existe(m) item(ns) com aviso(s)")
		listOfAlerts.add(" 103: Recusa Técnica Segmento Sem aceitação para AZ")
		listOfAlerts.add(" Restrição técnica para o segurado")
		listOfAlerts.add(" 110: Sistema momentaneamente indisponível - Tente mais tarde")
		listOfAlerts.add(" Existe(m) item(ns) com erro(s)")

		System.out.println("Alert ["+alert+"]\n")
		for(int i = 0; i < listOfAlerts.size(); i++){
			System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
			if(listOfAlerts[i].contains(alert.toString())){
				System.err.println("FOUND ALERT TO BLOC ["+i+"] ["+listOfAlerts[i]+"]")
				r = true
				break
			}
		}
		System.out.println("\nAlert ["+r+"]")
		return  r
	}

	/**
	 * @author be54955
	 * @param alert
	 * @return
	 */
	public static boolean alertListBlock(String alert){
		System.err.println("\nalertListBloc ["+alert+"]\n")
		boolean r = false
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add(" O valor da comissão livre é maior ao limite cadastrado no produto: 30,00%")
		listOfAlerts.add("Contratação de Vidros Blindados Não Permitida Para Caminhão. ")
		listOfAlerts.add("Para contratar a Cobertura de Acessórios e Blindagem é necessário contratação da cobertura de Casco.")
		listOfAlerts.add(" Segurado Isento de IOF")
		listOfAlerts.add("Corretor inexistente ")
		listOfAlerts.add("Chassi ou placa inválido(s).")
		listOfAlerts.add("Chassi inválido.")
		listOfAlerts.add("Erro ao buscar informação de veículo")
		listOfAlerts.add("Nenhum código FIPE retornado.")
		listOfAlerts.add(" 400 Bad Request")
		listOfAlerts.add("Placa não preenchida!")
		listOfAlerts.add("Valor do veículo não preenchido!")

		System.out.println("Alert ["+alert+"]\n")
		for(int i = 0; i < listOfAlerts.size(); i++){
			System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
			if(listOfAlerts[i].contains(alert.toString())){
				System.err.println("FOUND ALERT TO BLOC ["+i+"] ["+listOfAlerts[i]+"]")
				r = true
				break
			}
		}
		System.out.println("\nAlert ["+r+"]")
		return  r
	}

	/**
	 * @author be54955
	 * @param alert
	 * @return
	 */
	public static boolean alertListBlockSystem(String alert){
		System.err.println("\nalertListBloc ["+alert+"]\n")
		boolean r = true
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add(" 110: Sistema indisponível - tente mais tarde")

		System.out.println("Alert ["+alert+"]\n")
		for(int i = 0; i < listOfAlerts.size(); i++){
			System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
			if(listOfAlerts[i].contains(alert.toString())){
				System.err.println("FOUND ALERT TO BLOC ["+i+"] ["+listOfAlerts[i]+"]")
				r = false
				break
			}
		}
		System.out.println("\nAlert ["+r+"]")
		return  r
	}
	/**
	 * @author be54955
	 * @param alert
	 * @return
	 */
	public static boolean alertListCobertura(String alert){
		System.err.println("\nalertListBloc ["+alert+"]\n")
		boolean r = false
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add("Para contratar a Cobertura de Acessórios e Blindagem é necessário contratação da cobertura de Casco.")
		listOfAlerts.add(" Existe(m) item(ns) com aviso(s)")
		listOfAlerts.add(" Existe(m) item(ns) com erro(s)")

		System.out.println("Alert ["+alert+"]\n")
		for(int i = 0; i < listOfAlerts.size(); i++){
			System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
			if(listOfAlerts[i].contains(alert.toString())){
				System.err.println("FOUND ALERT TO BLOC ["+i+"] ["+listOfAlerts[i]+"]")
				r = true
				break
			}
		}
		System.out.println("\nAlert ["+r+"]")
		return  r
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static registraAlertaCoberturas(){
		try{
			WebUI.delay(5);
			String x = '//*[@id="gridCoberturasItensContent"]/div[1]'
			String t = 'tr'
			selecionaListaDeElementos(x,t);
			for(int i = 0; i < listOfElements.size(); i++){
				String strb = listOfElements.get(i).getAttribute("textContent")
				strb.replaceAll('\\s+',' ')
				String result = strb.substring(auxiliares.BruteForceSubstringSearch.searchIndex( 'Incluído',strb) , strb.length() )
				System.err.println('Alerta Coberturas Item ['+i+'] Warn ['+result.replaceAll('\\s+',' ')+"]")
				mensagenCoberturas.put(Integer.toString(i),result.replaceAll('\\s+',' '))
			}
		}catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param f
	 * @return
	 */
	public static boolean autorizaFipe(String f){
		boolean b
		int n = Integer.parseInt(f)
		if(n==0){
			b = false
		} else if(n>0){
			b = true
		}
		return b
	}

	/**
	 * @author be54955
	 * @param r
	 * @return
	 */
	public static boolean autorizaRCF(String r){
		boolean b
		double n = Double.parseDouble(r.replace('.', ''))
		if(n==0){
			b = false
		} else {
			b = true
		}
		return b
	}

	/**
	 * @author be54955
	 * @param c
	 * @return
	 */
	public static boolean contratar(String c){
		boolean v
		try{
			if(c.equals("Não")){
				v = false
			} else {
				v = true
			}
		} catch (Exception e){}
		return v
	}

	/**
	 * @author be54955
	 * @param txt
	 * @param path
	 * @return
	 */
	public static boolean identificaSemiReboque(String txt, String path){
		boolean c = false
		if(txt.contains(path)){
			c= true
			System.out.println("TXT ["+txt+"] PATH ["+path+"] FOUND ["+c+"]")
		}
		return c
	}

	/**
	 *
	 * @return
	 */
	public static chassiAlteradoPorFIPE(){
		for(int i = 0; i < listaChassiFipe.size(); i++){
			System.out.println("["+i+"] ["+listaChassiFipe.get(i)+"]")
		}

	}







}

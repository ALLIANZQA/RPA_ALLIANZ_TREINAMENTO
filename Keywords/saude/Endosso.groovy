package saude

import org.openqa.selenium.WebElement
import rpacommands.rpaCommands
import auxiliares.switchLeitorExcel
import backoffice.backofficeFuncionalidades
import bean.MassaDados
import auxiliares.switchAtualizarExcel
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.List
import auxiliares.ExtractDates
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory
import auxiliares.MenuMatriz
import saude.StrategyCriticasSIB


public class Endosso extends rpaCommands{


	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Saude/CriticasSIBEndossoData'
	private static String path
	private static String diretorio = '\\C:\\Users\\Public\\Evidencias'
	private static String excelCopiado
	private static String emissao
	private static String msgAlerta

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
	 * @param tipoAba
	 * @return
	 */
	public static boolean validaDados(){
		boolean value
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(2, i).equals('Processar')) {
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
	 *  @author t_dvlopes
	 * @param listaExcel
	 */
	public static void fluxoCriticasSIBEndosso(String tipoAba){
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands(tipoAba);
		aplicacaoEndosso()
		initDataFiles()
		WebUI.delay(5);
		try{
			for (int i = 1; i <= data.getRowNumbers(); i++) {
				if (data.getValue(2, i) == 'Processar') {
					List<String> endocos = new ArrayList<>();
					endocos[0] = data.getValue(3, i); // apolice
					endocos[1] = data.getValue(4, i); //acordo
					endocos[2] = data.getValue(5, i);//CPF
					fluxoPesquisaProposta(endocos) //return supep
					endocos[3] = supep
					auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(data.getValue(1, i)), path, endocos[3], 9 , tipoAba);
					fluxoConsultaEndosso(endocos)
					WebUI.delay(2);

					//auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(data.getValue(1, i)), path, 'Processado', 1 , tipoAba);
					//auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(data.getValue(1, i)), path, capturaAlerta(), 18 , tipoAba);
				}
			}
		} catch (Exception e) {
		}

		finalizar();
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static String capturaAlerta(){
		String alertalRecuperado

		String msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)

		System.err.println(msg)

		if(msg.contains('Operação concluída com sucesso.'.toString())  ){
			alertalRecuperado = 'Operação concluída. '
		} else {
			alertalRecuperado = 'Operação não concluída. '+msgAlerta
		}
		return alertalRecuperado
	}

	/**
	 * 
	 * @return
	 */
	public static String capturaAlertaEndosso(){
		String alertalRecuperado

		String msg = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 5)

		System.err.println("["+msg+"]")


		if(msg.contains('EMISSÃO REALIZADA'.toString())  ){
			alertalRecuperado = 'Operação concluída. '+msg
		} else {
			alertalRecuperado = 'Operação não concluída. '+msgAlerta
		}
		return alertalRecuperado
	}

	/**
	 * @author T_DCDia
	 * @return
	 */
	public static aplicacaoEndosso(){
		WebUI.delay(15);
		//Consultas & Estatísticas
		MenuMatriz.consultasEstatisticas()
		//  Consultas Gerais
		clickXpath('/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul/li[4]/a', 1);
	}

	/**
	 * @author T_DCDias
	 * @param endoddos
	 */
	public static void fluxoConsultaEndosso(List<String> endocos){
		try{
			fluxoProducaoDiverso();
			fluxoPesquisaSusep(endocos);
		}catch (Exception e) {
		}
	}

	/**
	 * @author T_DCDias
	 * @param apolice
	 * @return
	 */
	public static fluxoPesquisaProposta(List<String> endocos){
		WebUI.delay(15);
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()

		/**
		 * Apolice
		 */
		preencheXpath('//*[@id="apolice"]', endocos[0], 3);
		clickXpath('//*[@id="o_10"]', 5);

		/**
		 * Recupera Apólice SUSEP
		 */
		supep = recuperaValor('//*[@id="apoliceSusep"]', 'value', 5);

		System.err.println(' supep ['+supep+']')
		System.err.println()

		if(supep.equals('atributo nao encontrado')){
			finalizar();
			valueCheck = 'Não enviado';
		} else{
			/**
			 * Recupera o ramo
			 */
			ramo = recuperaValor('//*[@id="ramo"]', 'value', 5);
			ramo = ramo.replaceAll("\\D+","");
			supep = supep.replaceAll("—","");
			System.err.println('ramo ['+ramo+'] supep ['+supep+']')
		}
	}

	/**
	 * @author T_DCDias
	 */
	public static void fluxoConsultasGeraisEndosso(){
		try{
			clicaNoMenuTelaMatriz("Consultas & Estatísticas");
			clicaNoSubMenuTelaMatriz("Consultas");
			clickXpath('//*[@id="topicNav"]/li[2]/a', 2);
		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static fluxoProducaoDiverso(){
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
		//Produção Sinistros
		clickXpath('/html/body/div/div/app-root/app-private/app-private-header/nx-epac-header/nx-epac-navigation/div/div[1]/nx-epac-navigation-desktop/div[1]/div/div/ul/li[1]/a/span', 2);
		//Produção
		MenuMatriz.subMenu("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul", "a","Produção")
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()
	}

	/**
	 * @author T_DCDias
	 * @param acordo
	 * @param susep
	 * @return
	 * acordo, susep
	 */
	public static fluxoPesquisaSusep(List<String> endocos){
		WebUI.delay(10);
		msgAlerta = ""
		//seleciona outras Operações
		clickXpath('//*[@id="tipoDeSeleccion2"]', 2);
		//preenche APOLICE
		preencheXpath('//*[@id="polSusep_SUSEP"]', endocos[3], 4);
		commandTAB('//*[@id="polSusep_SUSEP"]')

		//Seleciona o item de acordo com o ramo
		switch (ramo){
			case '202':
				preencheXpath('//*[@id="polSusep_Item"]', endocos[1], 4);
				break
		}


		//SELECIONAR
		clickXpath('//*[@id="btnSeleccionar"]',2);
		WebUI.delay(5);
		msgAlerta = pegaMSGAlerta()
		WebUI.delay(2);
		fechaAlerta();

		//OPERATIVA
		clickXpath('//*[@id="codOperativa"]', 2);
		//SELECIONAR MULTISITUAÇÃO
		selecionaOperativa(ramo)
		//AVANÇAR
		clickXpath('//*[@id="btnSeleccionOperativa"]', 10);
		fechaAlerta();
		//Motivo
		StrategyCriticasSIB.ajusteCriticaMotivo( endocos[4])
		//AVANÇA - TELA Dados basicos 1
		clickXpath('//*[@id="btnModificacion"]', 2);
		//AVANÇAR - TELA Dados basicos 2
		clickXpath('//*[@id="btnAceptar"]', 10);
		//Seleciona fluxo de acordo com o ramo
		switch (ramo){
			case '202':
				fluxoRamo202(endocos)
				break
			case '204':
				fluxoRamo204(endocos)
				break
			case '201':
				fluxoRamo204(endocos)
				break
		}

		//Seleciona fluxo de finalização de acordo com o ramo
		switch (ramo){
			case '202':
			//AVANÇAR - TELA tipo endoso

				calculadoraIMC()
				clickXpath('//*[@id="btnAceptar"]', 3);
			//ALERTA
			//capturaAlerta('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText')

				clickXpath('//*[@id="o_13"]', 3);
				fechaAlerta();

				clickXpath('//*[@id="btnAceptar"]', 3);
				clickXpath('//*[@id="btnAceptar"]', 3);
			//ALERTA
				capturaAlerta('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText')

				clickXpath('//*[@id="btnAceptar"]', 3);

			//AVANÇAR - D.EMISSÃO
				clickXpath('//*[@id="btnAceptar"]', 3);

			//AVANÇAR 0 CLÁUSULAS
				clickXpath('//*[@id="btnAceptar"]', 3);

			//AVANÇAR - CONFIRMAÇÃO EMISSÃO
				clickXpath('//*[@id="btnAceptar"]', 3);

				fechaAlerta();
				capturaAlerta('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText')
				System.err.println("["+ramo+"] Fluxo completo ")
				confirmaEmissao()

				break
			case '204':
			//AVANÇAR - TELA tipo endoso
				for(int i = 0; i<5; i++){
					clickXpath('//*[@id="btnAceptar"]', 3);
				}

			//EMITIR
				clickXpath('//*[@id="ParrillaBean$btnEmitir"]', 3);

			//AVANÇAR - TELA tipo endoso
				for(int i = 0; i<6; i++){
					clickXpath('//*[@id="btnAceptar"]', 3);
				}

				System.err.println("["+ramo+"] Fluxo completo ")
				System.err.println()
				confirmaEmissao()
				break

			case '201':

			/**
			 * Ajustar no momento em que a tela for apresentada 
			 */
			//AVANÇAR - TELA tipo endoso
				try{
					//clickXpath('//*[@id="btnAceptar"]', 3);
				} catch (Exception e){}
			//ACEITAR
				try{
					//clickXpath('//*[@id="o_13"]', 3);
					//fechaAlerta();
				} catch (Exception e){}

				if( recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3).contains("D. CLIENTE")){
					//AVANÇAR - TELA  CLIENTE
					clickXpath('//*[@id="btnAceptar"]', 3);
				} else{}

				if( recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3).contains("LISTA DE RISCOS")){
					//AVANÇAR - LISTA DE RISCOS
					clickXpath('//*[@id="btnAceptar"]', 3);
				} else{}

				if( recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3).contains("PACOTE PERSONALIZADO")){
					//AVANÇAR - PACOTE PERSONALIZADO
					clickXpath('//*[@id="btnAceptar"]', 3);
				} else {}

				if( recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3).contains("D. EMISSÃO")){
					//AVANÇAR - DADOS EMISSÃO
					clickXpath('//*[@id="btnAceptar"]', 3);
				} else {}

				if( recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3).contains("CLÁUSULAS")){
					//AVANÇAR - CLÁUSULAS
					clickXpath('//*[@id="btnAceptar"]', 3);
				} else {}

				if( recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3).contains("DIR.ITENS")){
					//AVANÇAR - DIR. ITENS
					clickXpath('//*[@id="btnAceptar"]', 3)
				} else {}

				if( recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3).contains("TABELA DE PREÇOS")){
					//EMITIR
					clickXpath('//*[@id="ParrillaBean$btnEmitir"]', 3);
				} else {}

				if( recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 3).contains("D. EMISSÃO")){
					//AVANÇAR - TELA tipo endoso
					for(int i = 0; i<6; i++){
						clickXpath('//*[@id="btnAceptar"]', 3);
					}
				}


				System.err.println("["+ramo+"] Fluxo completo ")
				System.err.println()
				confirmaEmissao()
				break
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static confirmaEmissao(){
		emissao = recuperaValor('//*[@id="CabeceraBean$polizaSusepSUSEP"]', 'innerText', 5)
		System.err.println('emissao ['+emissao+']')
		System.err.println()
	}

	/**
	 * @author T_DCDias
	 * @param endocos
	 * @return
	 */
	public static fluxoRamo202(List<String> endocos){
		System.err.println("fluxo ramo 202")
		System.err.println('Novo Valor ['+endocos[5]+']')
		System.err.println()
		WebUI.delay(5);
		capturaAlerta('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText')
		WebUI.delay(10);
		StrategyCriticasSIB.ajusteCriticasListaRiscosEndosso(endocos)
		selecionaItemLista("Até R\$1.500,00", '//*[@id="OutrosDadosClienteBeanSegurado$rendaMensal"]', "option")
		WebUI.delay(10);
		capturaAlerta('//*[@id="dataErrorSection"]/table/tbody/tr' , "innerText")
	}

	/**
	 * @author T_DCDias
	 * @param endocos
	 * @param index
	 * @return
	 */
	public static endossoRamo(List<String> endocos , String index){
		//ACEITAR
		clickXpath('//*[@id="ParamDirAseguradoBean$btGuardarSub"]', 4);
		WebUI.delay(5);
		capturaAlerta('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText')
		//DADOS DO SEGURADO - AVANÇAR
		clickXpath('//*[@id="btnAceptar"]', 10);
		WebUI.delay(5);
		capturaAlerta('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText')
		//TELA QUESTIONÁRIO
		calculadoraIMC()
		//AVANÇAR
		clickXpath('//*[@id="btnAceptar"]', 10);
		//TELA - DADOS RISCO SAÚDE
		clickXpath('//*[@id="o_13"]', 10);
		fechaAlerta();
		StrategyCriticasSIB.ajusteCriticasListaRiscosEndossoDadosRiscoSaude( endocos )
		//AVANÇAR
		clickXpath('//*[@id="btnAceptar"]', 10);
	}

	/*
	 * Calculo de IMC 
	 */
	public static calculadoraIMC(){
		//TELA QUESTIONÁRIO
		//Peso (Kg)
		String peso = recuperaValor('//*[@id="ValorizacaoRiscoBean$pesoSegurado"]', 'value', 5)
		peso = peso.replaceAll(",", "")
		int ipeso = Integer.parseInt(peso)
		if(ipeso==0){
			preencheXpath('//*[@id="ValorizacaoRiscoBean$pesoSegurado"]', '1', 1);
		}

		//Altura (mts)
		String altura =  recuperaValor('//*[@id="ValorizacaoRiscoBean$estaturaSegurado"]', 'value', 5)
		altura = altura.replaceAll(",", "")
		int ialtura = Integer.parseInt(altura)
		if(ialtura == 0){
			preencheXpath('//*[@id="ValorizacaoRiscoBean$estaturaSegurado"]', '1', 1);
		}

		//CALCULADORA
		if(ipeso==0 && ialtura == 0){
			clickXpath('//*[@id="null" and @title="Calcular IMC"]', 2);
		} else if(ipeso > 0 && ialtura > 0) {
			String imc =  recuperaValor('//*[@id="ValorizacaoRiscoBean$imcSegurado"]', 'value', 5)
			imc = imc.replaceAll(",", "")
			int iimc = Integer.parseInt(imc)
			if(iimc == 0){
				clickXpath('//*[@id="null" and @title="Calcular IMC"]', 2);
			}
		}

	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static capturaAlerta(String xpath , String propriedade){
		WebUI.delay(10);
		String alerta = recuperaValor(xpath, propriedade, 5)
		if(!alerta.contains("atributo nao encontrado")){
			msgAlerta = alerta
		}
	}


	/**
	 * @author T_DCDias
	 * @param endocos
	 * @return
	 */
	public static fluxoRamo204(List<String> endocos){

		//AVANÇA - TELA tipo endoso
		clickXpath('//*[@id="btnAceptar"]', 2);

		//informar o item de acordo com a planilha
		preencheXpath('//*[@id="OperacionItemBean$item"]',endocos[1], 2);
		//PESQUISAR
		clickXpath('//*[@id="OperacionItemBean$btnBuscar"]', 2);
		WebUI.delay(3);
		capturaAlerta('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText')

		//EDITAR
		clickXpath('//*[@id="0" and @title="Editar"]', 2);

		//ACEITAR
		clickXpath('//*[@id="ParamItemBean$btnAceptar"]', 2);

		//DATA DE VIGENCIA, ALTERA PARA DATA DO DIA CONFORME CRITICA
		StrategyCriticasSIB.ajusteCriticaEndossoDataAtual(endocos[4])

		//AVANÇAR
		clickXpath('//*[@id="btnAceptar"]', 2);


		WebUI.delay(5);
		fluxoRamo202(endocos)

	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailCriticaSibEndosso(String tipoAba){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Saude/CriticasSIBEndosso/ConfiguracoesCriticasSIBEndosso.txt";
		lerConfigurações(pathTxtEmail);
		path1 = excelCopiado
		nome1 = "RoboCriticasSIBEndosso-'"+dataRecuerada+"'.xlsx";
		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
		initDataFiles()
		if(!validaDados()){
			List<String> anexos = new ArrayList<>();
			anexos[0] = path1;
			anexos[1] = nome1;
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba )
		} else {
			System.err.println('Não existem dados para processar')
		}
	}

	/**
	 * @author T_DCDias
	 * @param ramo
	 * @return
	 */
	public static String ramos(String ramo){
		Map<String, String> ramos = new HashMap<String, String>();
		ramos.put("201",'Endosso de Apólice de Multisituação');
		ramos.put("204",'Endosso de Apólice de Multisituação');
		ramos.put("202",'Endosso de alteração/iclusão de itens');
		return ramos.get(ramo).toString()
	}

	/**
	 * @author T_DCDias
	 * @param ramo
	 * @return
	 */
	public static selecionaOperativa(String ramo){
		String xpath = '//*[@id="codOperativa"]'
		clickXpath(xpath, 2);
		String tag = 'option'
		selecionaListaDeElementos(xpath,tag);
		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println(i+' - '+listOfElements[i].getText())
			if(listOfElements[i].getText().contains( ramos(ramo) )){
				listOfElements[i].click()
				break;
			}
		}
	}

	/**
	 * @author T_DCDias
	 * @param ramo
	 * @return
	 */
	public static String endosso(String critica){
		Map<String, String> endossos = new HashMap<String, String>();
		endossos.put("[ENDEREÇO] - Complemento do endereço não pode ser superior a 15 caracteres",'[ENDEREÇO] - Complemento do endereço não pode ser superior a 15 caracteres');
		endossos.put("[DATA] - correção no campo data de nascimento.",'[DATA] - correção no campo data de nascimento.');
		endossos.put("[CPF] - correção no campo cpf.",'[CPF] - correção no campo cpf.');
		endossos.put("[E-MAIL] - correção no campo e-mail.",'[E-MAIL] - correção no campo e-mail.');
		return endossos.get(critica).toString()
	}
}

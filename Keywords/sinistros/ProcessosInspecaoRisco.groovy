package sinistros

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.List
import org.openqa.selenium.WebElement
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
import auxiliares.ExtractDates
import backoffice.backofficeFuncionalidades
import bean.MassaDados
import internal.GlobalVariable
import auxiliares.StringFormatter
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import auxiliares.FileInput
import auxiliares.MenuMatriz


public class ProcessosInspecaoRisco extends backofficeFuncionalidades{



	public static String caminhoDiretorioExcel = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Sinistro\\ProcessosInspecaoRiscoData\\RoboInspecaoRisco.xlsx";
	public static int countEnviado = 0;
	public static String ExcelCopiado;
	public static String dataHoje = getDateYMD().replace('-','');
	public static String dataRecuperada = getDateHour();
	public static String diretorio;
	public static String diretorioToDelete;
	public static int tamanhoListaOriginal;
	public static List<MassaDados> listaExcel;
	public static int qtdPagina = 0;
	private static int cellCheck = 12;


	/**
	 * @author BE54955
	 * @param tipoAba
	 * @return
	 */
	public static InspecaoRisco(String tipoAba){
		initDataFiles(tipoAba)
		if(validaDados()){
			fluxoCompletoProcessosInspecaoRiscos(tipoAba);
			enviarEmailProcessosInspecaoRisco(tipoAba);
			System.err.println('\nFinalizado...\n Casos finalizados  ['+ tamanhoListaOriginal+']\n');
		} else {
			System.err.println('InspecaoRisco: não existem dados para processar')
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(String tipoAba){
		listaExcel = switchLeitorExcel.switchExcel(caminhoDiretorioExcel, tipoAba);
	}

	/**
	 * @author t_dvlopes
	 * @param tipoAba
	 * @return
	 * Fluxo Completo Envio Pendencia Go
	 */
	public static fluxoCompletoProcessosInspecaoRiscos(String tipoAba){
		initDataFiles(tipoAba)
		tamanhoListaOriginal = listaExcel.size();
		if(tamanhoListaOriginal>0){
			/**
			 * Efetua inicialização da aplicação Matriz e faz o fluxo de envio de pendência
			 */
			iniciaAplicacaoMatrizCommands('IntranetProdução');
			realizaLoginIntraNetProducaoCommands(tipoAba);
			fluxoLiquidacaoProficional();
			WebUI.delay(15);
			fluxoProcessosInspecaoRiscos(listaExcel, tipoAba);
			/**
			 * Processa as pendências que apresentaram falhas
			 */
			processaFalhas(finalizaProcesso(tipoAba), tipoAba);
			System.out.println()
		}
	}



	/**
	 *  @author t_dvlopes
	 * @param listaExcel
	 */	
	public static void fluxoProcessosInspecaoRiscos(List<MassaDados> listaExcel, String tipoAba){
		countEnviado = 0;
		for (int i = 0; i < listaExcel.size();i++){
			if(listaExcel.get(i).getStatus().equals('enviar')){
				try{
					alerta=""
					alteraJanejaGenerica(1, 5)
					WebUI.maximizeWindow()
					WebUI.delay(15);
					System.err.println()
					qtdPagina = 0;
					fluxoPesquisarCnpj(listaExcel.get(i).getCnpjCpf(), listaExcel.get(i).getDataDesde(),dataHoje);
					selecionarEstadoPendente();
					alerta = recuperaValor('//*[@id="listaPreLiquidacionesNF_tbody"]/tr/td', 'innerText', 1)
					if(!alertListBlocList(alerta)){
						localizarCodVerificacao(listaExcel.get(i).getCodVerif());
						//			if(isento()){
						alterarTributavelIsento(listaExcel.get(i).getValorTributavel(), listaExcel.get(i).getValorIsento());
						validarEnviado();
						switchAtualizarExcel.switchUpdateExcel('InspecaoRisco', caminhoDiretorioExcel ,listaExcel.get(i).getCnpjCpf(),Integer.parseInt(listaExcel.get(i).getLinha()));
						//		} else {
						switchAtualizarExcel.switchUpdateExcel('InspecaoRisco', caminhoDiretorioExcel ,listaExcel.get(i).getCnpjCpf()+" - Valor atribuido ",Integer.parseInt(listaExcel.get(i).getLinha()));
						//		}
					} else {
						System.err.println("SALVANDO EVIDÊNCIA EM:\n"+ExcelCopiado)
						switchAtualizarExcel.switchUpdateExcel('InspecaoRisco', caminhoDiretorioExcel ,alerta, Integer.parseInt(listaExcel.get(i).getLinha())  );
					}
					System.err.println()
					//				alteraJanejaClose();
					retornaLiquidacoes();
					countEnviado++
				}catch (Exception e) {
					switchAtualizarExcel.switchUpdateExcel('InspecaoRisco', caminhoDiretorioExcel ,listaExcel.get(i).getCnpjCpf(),Integer.parseInt(listaExcel.get(i).getLinha()));
					iniciaAplicacaoMatrizCommands('IntranetProdução');
					realizaLoginIntraNetProducaoCommands(tipoAba);
					fluxoLiquidacaoProficional();
				}
			}
		}
	}

	/**
	 * @author BE54955
	 * @return
	 */
	public static boolean isento(){
		boolean i = false
		try{
			String isento = recuperaValor('//*[@id="importeExento"]', 'defaultValue', 2)
			System.out.println("ISENTO ["+isento+"]")
			if(isento.equals('0,00'))
				i = true
		} catch (Exception e){}
		return i
	}

	/**@author t_dvlopes
	 * @param cnpj
	 * @param dataDesde
	 * @param dataAte
	 * @return
	 */
	public static boolean fluxoPesquisarCnpj(String cnpj, String dataDesde, String dataAte){
		try{
			//clica Verificacoes
			clickXpath('//*[@id="menu13"]', 10);
			//PREENCHE CNPJ
			preencheXpath('//*[@id="nif_doc"]', cnpj, 3);
			selecionaTipoLiquidacao('PréLiquidações');
			preencherDataDesdeAte(dataDesde, dataAte);
			//ClicaEnviar
			clickXpath('//*[@id="sectionPaginaCompletaSectionBar"]/table/tbody/tr/td/table/tbody/tr', 1);
		}catch (Exception e){
			finalizar();
			valueCheck = 'N';
		}
	}


	/**
	 * Botão home
	 * @return
	 */
	public static retornaLiquidacoes(){
		clickXpath('//*[@id="imgOptMenuBackHome"]', 1);
	}


	/**@author t_dvlopes
	 * @param dataDesde
	 * @param dataAte
	 * @return
	 */
	public static preencherDataDesdeAte(String dataDesde, String dataAte){
		dataDesde = dataDesde.replace('/','');
		dataAte = dataAte.replace('/','');
		preencheXpath('//*[@id="fechaDesde"]', dataDesde, 1);
		commandTAB('//*[@id="fechaDesde"]')
		preencheXpath('//*[@id="fechaHasta"]', dataAte, 1);
		commandTAB('//*[@id="fechaHasta"]')
	}

	/**
	 * @author t_dvlopes
	 * @param delay
	 * @return
	 */
	public static selecionarEstadoPendente(){
		try{
			WebUI.delay(5);
			String xpath = '//*[@id="sectionListaPreliquidacionesContent"]/div[1]';
			String tag = "tr";
			String value= '';
			List<WebElement> listOfElements;
			listOfElements = listaDeElementos(xpath,tag);
			for(int i = 0; i < listOfElements.size(); i++){
				System.err.println("["+i+"] "+tag+" ["+listOfElements[i].getText()+"]");
				if(listOfElements[i].getText().toString().contains('Pend.Autorizar'.toString())){
					listOfElements.get(i).click();
					break;
				}
			}
		}catch (Exception e){
			String erroValueCheck = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','textContent',3);
			System.err.println('Falha ao Consultar Apolice' + e);
			finalizar();
			valueCheck = 'Não enviado ['+erroValueCheck+']';
		}
	}

	/**
	 * @author t_dvlopes
	 * @param codVerificacao
	 * @return
	 */
	public static localizarCodVerificacao(String codVerificacao){
		System.err.println()
		try{
			boolean localiza = false;
			if(qtdPagina <= 0){
				WebUI.delay(10);
			}else{
				WebUI.delay(2);
			}
			String xpath = '//*[@id="listaPreLiquidacionesNF_tbody"]';
			String tag = "tr";
			String value= '';
			List<WebElement> listOfElements;
			listOfElements = listaDeElementos(xpath,tag);
			System.err.println('\n');
			System.err.println('Pagina [' +qtdPagina+']');
			System.err.println('\n');
			for(int i = 0; i < listOfElements.size(); i++){
				print '*';
				if(listOfElements[i].getText().toString().contains(codVerificacao.toString())){

					System.err.println("["+codVerificacao.toString()+"]")

					clickXpath('//*[@id="tdExtdInfo_'+i+'_listaPreLiquidacionesNF"]', 1);
					localiza = true;
					break;
				}
			}

			if(localiza == false){
				if(qtdPagina <= 0){
					clickXpath('//*[@id="listadoDetallePreliquidacionSectionBar"]/table/tbody/tr/td/table/tbody/tr', 1);
				}else{
					clickXpath('//*[@id="o_11"]', 1);
				}
				qtdPagina = qtdPagina+1;
				localizarCodVerificacao(codVerificacao);
			}
		}catch (Exception e){
			System.err.println('Falha ao Consultar Localizar cod Verificao' + e);
			finalizar();
			valueCheck = 'Não enviado [Localizar cod Verificao]';
		}
	}

	/**
	 * @author t_dvlopes
	 * @param tributavel
	 * @param isento
	 * @return
	 */
	public static alterarTributavelIsento(String tributavel, String isento){
		System.err.println()
		try{
			WebUI.delay(2);
			//editar tributavel   #deletar string
			limpaCampo('//*[@id="importe"]', 5)
			preencheXpath('//*[@id="importe"]', tributavel, 1);
			limpaCampo('//*[@id="importeExento"]', 5)
			preencheXpath('//*[@id="importeExento"]', isento, 5);
			//clicar gravar
			clickXpath('//*[@id="detallePagoViewForm"]/table/tbody/tr[2]/td/table/tbody/tr/td[1]', 5);
		}catch (Exception e){
			String erroValueCheck = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]','textContent',3);
			System.err.println('Falha ao Consultar Apolice' + e);
			finalizar();
			valueCheck = 'Não enviado ['+erroValueCheck+']';
		}
	}

	/**
	 * @author t_dvlopes
	 * @return
	 */
	public static boolean finalizaProcesso(String tipoAba){
		boolean processoFinalizado = false;
		initDataFiles(tipoAba)
		System.err.println('Reprocessa Falhas ['+listaExcel.size()+"]")
		if(validaDados()){
			processoFinalizado = true
		}
		return processoFinalizado;
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static boolean validaDados(){
		boolean value
		try{
			for (int i = 1; i <= listaExcel.size(); i++) {
				if (listaExcel.get(i).getStatus().equals('enviar')) {
					value = true
					break;
				} else {
					value = false
				}
			}
		} catch (Exception e){}
		System.err.println('Data ['+value+']\n')
		return value
	}

	/**
	 * @author T_Dvlopes
	 * @param processoFinalizado
	 * @param tipoAba
	 * @return
	 * Processa as falhas de envio de pendências ao final do processo, caso ocorra mais falha o processo é re-feito até concluir
	 */
	public static processaFalhas(boolean processoFinalizado, String tipoAba){
		System.err.println('processa Falhas - processoFinalizado ['+processoFinalizado+'] tipoAba ['+tipoAba+']');
		if(processoFinalizado.equals(true)){
			tamanhoListaOriginal = 0;
			listaExcel = switchLeitorExcel.switchExcel(caminhoDiretorioExcel, tipoAba);
			/**
			 * Nova tentativa
			 */
			iniciaAplicacaoMatrizCommands('IntranetProdução');
			realizaLoginIntraNetProducaoCommands(tipoAba);
			fluxoLiquidacaoProficional();
			WebUI.delay(10);
			fluxoCompletoProcessosInspecaoRiscos(listaExcel, tipoAba);
			/**
			 * 'tamanhoListaOriginal' recebe tamanho da nova lista com pendencias com falha
			 */
			tamanhoListaOriginal = listaExcel.size();
			/**
			 * Chamada racursiva para re-processar falhas
			 */
			processaFalhas(finalizaProcesso(), tipoAba);
		}
	}

	/**
	 * @author t_dvlopes
	 */
	public static void fluxoLiquidacaoProficional(){
		System.err.println()
		try{
			WebUI.delay(2);
			MenuMatriz.producaoSinistro()
			WebUI.delay(2);
			MenuMatriz.subMenu("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div/div/div[2]/ul", "a","Liquidaciones Profesionales")
		} catch (Exception e) {
			finalizar();
			valueCheck = 'Não enviado';
		}
	}

	/**
	 * @author T_Dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailProcessosInspecaoRisco(String tipoAba){
		System.out.println()
		if(!finalizaProcesso(tipoAba)){
			pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Sinistros/ConfiguracoesSinistros.txt";
			lerConfigurações(pathTxtEmail);
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			try{
				List<String> anexos = new ArrayList<>();
				anexos[0] = caminhoDiretorioExcel;
				anexos[1] = 'RoboInspecaoRisco.xlsx';
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba);
			} catch (Exception e){
			}
		}
	}
}

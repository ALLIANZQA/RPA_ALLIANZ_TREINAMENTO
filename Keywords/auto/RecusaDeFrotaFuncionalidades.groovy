package auto

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
import auxiliares.updateExcel;
import bean.MassaDados
import internal.GlobalVariable
import rpacommands.rpaCommands
import org.openqa.selenium.WebDriver as WebDriver
import sendEmail.envioDeEmail
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.MenuMatriz

public class RecusaDeFrotaFuncionalidades extends rpaCommands {

	private static TestData data
	private static List<MassaDados> listaExcel;
	private static int coluna = 4
	private static String valor = "x"





	/**
	 * @author T_DCDias
	 * @return
	 */
	public static fluxoRecusaDeFrota(String tipoAba){
		fluxoCompletorecusaDeFrota(tipoAba)
		processaFalhas(verificaFalhas(valor,coluna), tipoAba);
		enviarEmailRecusaDeFrota(tipoAba)
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static fluxoCompletorecusaDeFrota(String tipoAba){
		if(carregaDados()){
			iniciaAplicacaoMatrizCommands('IntranetProdução');
			realizaLoginIntraNetProducaoCommands(tipoAba);
			System.err.println()
			menu();
			pesquisaPedidoDeRecusa(tipoAba);
		}
		finalizar();
	}




	/**
	 * @author T_DCDias
	 * @param nomeArquivo
	 */
	public static void realizaLoginMatrizProdução(String nomeArquivo){
		try{
			auxiliares.UserPassword.getUsuarioSenha(nomeArquivo)
			realizaLoginTelaMatriz(auxiliares.UserPassword.getUser(),auxiliares.UserPassword.getSenha());
		} catch (Exception e) {
			System.err.println('Falha ao realizar Login MatrizProdução' + e);
			finalizar();
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static init() {
		abreBrowser('MatrizProdução');
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean carregaDados(){
		boolean sizeTrue = false
		if(validaExecutados(getData(), 'x', 4)==false){
			if(getData().getRowNumbers()>0){
				sizeTrue = true;
			}else {
				sizeTrue = false;
			}
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static TestData getData(){
		return data = findTestData('Data Files/Auto/RecusaDeFrotaData/RecusaDeFrota')
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailRecusaDeFrota(String tipoAba){
		if(!carregaDados()){
			pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Auto/RecusaDeFrota/ConfiguracoesRecusaDeFrota.txt";
			lerConfigurações(pathTxtEmail)
			String pathAnexos = "C:/Users/Public/RPA_ALLIANZ/Emails/Auto/RecusaDeFrota/Anexos.txt"
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			data =  getData()
			if(validaExecutados(data, 'x', 3)){
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, FileInput.readFile(pathAnexos), host, porta, tipoAba )
			}
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static menu(){
		WebUI.delay(10)
		MenuMatriz.producaoSinistro()
		WebUI.delay(5)
		MenuMatriz.subMenu("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul", "a","Cotações especiais")
		WebUI.delay(10)
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static pesquisaPedidoDeRecusa(String tipoAba){
		System.err.println()
		WebUI.switchToWindowTitle('Pesquisa Pedido')
		WebUI.waitForPageLoad(30)
		WebUI.maximizeWindow()
		String path = data.sourceUrl
		for (int i = 1; i <= data.getRowNumbers(); i++) {

			try{
				alerta = null
				if (data.getValue(coluna, i) == valor) {
					String sFluxo = data.getValue(1, i)
					String sMotivoRecusa = data.getValue(2, i)
					String sTexto = data.getValue(3, i)
					String sResult = 'Solicitação Já se encontra recusada'
					System.out.println("Fluxo recusa")
					//Pesquisar Pedido
					WebUI.setText(findTestObject('Object Repository/PageAuto/RecusaFrotas/Page_Pesquisa Pedido/input_claveSolicitud'), sFluxo)
					clickXpath("//div[@id='botonBuscar']", 5)
					System.err.println()
					sResult = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
					System.err.println("["+sResult+"]")
					//verificar resultado
					if(!alertListBlocList(sResult)){
						sResult = ""
						try{
							clickXpath("//div[@id='botonBuscar']", 5)
						} catch (Exception e){}
						if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/PageAuto/RecusaFrotas/Page_Pesquisa Pedido/td_Solicitao de cotao recusada'),
						3, FailureHandling.CONTINUE_ON_FAILURE)) {
							sResult = 'Solicitação de Cotação Recusada'
							WebUI.click(findTestObject('Object Repository/PageAuto/RecusaFrotas/Page_Pesquisa Pedido/img_idetalle'))
							WebUI.waitForPageLoad(15)
							WebUI.maximizeWindow()
							selecionaItemLista('Ficha Gestão','//*[@id="sectionDetalleSectionBar"]/table/tbody/tr/td/table' , 'div')
							WebUI.delay(25)
							alteraJanejaGenerica(2, 5)
							WebUI.maximizeWindow()
							selecionaItemLista("Nota/Agenda",'/*/body/div/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div/ul', "span" )
							alteraJanejaGenerica(3, 5)
							WebUI.waitForPageLoad(15)
							WebUI.maximizeWindow()
							WebUI.setText(findTestObject('Object Repository/PageAuto/RecusaFrotas/Page_Editor de NotasAgenda/input_numModCodi'),'383')
							WebUI.setText(findTestObject('Object Repository/PageAuto/RecusaFrotas/Page_Editor de NotasAgenda/input_txtModeloDescL'),sMotivoRecusa)
							WebUI.click(findTestObject('Object Repository/PageAuto/RecusaFrotas/Page_Editor de NotasAgenda/div_Aceitar'))
							WebUI.setText(findTestObject('Object Repository/PageAuto/RecusaFrotas/Page_Editor de NotasAgenda/textarea_texto'), sTexto)
							WebUI.click(findTestObject('Object Repository/PageAuto/RecusaFrotas/Page_Editor de NotasAgenda/div_Gravar'))
							WebUI.delay(4)
							alteraJanejaGenerica(1, 5)
							WebUI.switchToWindowTitle('Detalhe Pedido')
							WebUI.click(findTestObject('Object Repository/PageAuto/RecusaFrotas/Page_Detalhe Pedido/div_Indeferir'))
							WebUI.acceptAlert()
							WebUI.click(findTestObject('Object Repository/PageAuto/RecusaFrotas/Page_Detalhe Pedido/div_Voltar'))

							sResult = 'Solicitação de Cotação Recusada'
							WebUI.switchToWindowTitle('Pesquisa Pedido')
							WebUI.click(findTestObject('Object Repository/PageAuto/RecusaFrotas/Page_Pesquisa Pedido/div_Limpar'))
							System.err.println("\n["+i+"] TIPO ABA ["+tipoAba+"] sResult ["+sResult+"]")
							System.err.println()
							updateExcel.atualizaRecusaDeFrotaFuncionalidades(tipoAba, sResult,"","x", i, path )
							System.err.println()

						} else {
							sResult = 'Solicitação de Cotação ja foi Recusada'
							WebUI.switchToWindowTitle('Pesquisa Pedido')
							WebUI.click(findTestObject('Object Repository/PageAuto/RecusaFrotas/Page_Pesquisa Pedido/div_Limpar'))
							System.err.println("\n["+i+"] TIPO ABA ["+tipoAba+"] sResult ["+sResult+"]")
							System.err.println()
							updateExcel.atualizaRecusaDeFrotaFuncionalidades(tipoAba, sResult,"","x", i, path )
							System.err.println()
						}
					} else {
						WebUI.switchToWindowTitle('Pesquisa Pedido')
						WebUI.click(findTestObject('Object Repository/PageAuto/RecusaFrotas/Page_Pesquisa Pedido/div_Limpar'))
						System.err.println("\n["+i+"] TIPO ABA ["+tipoAba+"] sResult ["+sResult+"]")
						System.err.println()
						updateExcel.atualizaRecusaDeFrotaFuncionalidades(tipoAba, sResult,"","x", i, path )
						System.err.println()
					}
				}
			} catch (Exception e){}
		}
		finalizar()
	}

	/**
	 * @author T_DCDias
	 * @param verifica
	 * @param tipoAba
	 * @return
	 */
	public static processaFalhas(boolean verifica, String tipoAba){
		System.err.println("verificaFalhas ["+verifica+"]")
		if(verifica==false){
			iniciaAplicacaoMatrizCommands('IntranetProdução');
			realizaLoginIntraNetProducaoCommands(tipoAba);
			menu();
			pesquisaPedido();
			processaFalhas(verificaFalhas(valor,coluna), tipoAba);
			finalizar()
		}
	}

	/**
	 * @author T_DCDias
	 * @param valueCheck
	 * @param column
	 * @return
	 */
	public static boolean verificaFalhas(String valueCheck, int column){
		TestData data = findTestData('Data Files/Auto/RecusaDeFrotaData/RecusaDeFrota')
		return validaExecutados(data, valueCheck, column);
	}



}

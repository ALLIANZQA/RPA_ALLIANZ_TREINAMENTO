package residencia

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

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

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.lang.reflect.WeakCache.Value
import java.text.DecimalFormat
import java.util.Hashtable.ValueCollection
import internal.GlobalVariable as GlobalVariable

import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.SessionId
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By

import rpacommands.rpaCommands

import auxiliares.updateExcel

public class CotacaoResidencia extends rpaCommands{

	private static double premioTarifaAnt
	private static double premioTarifaCalculado
	private static double ICG
	private static double premioliqcalculado
	private static boolean bProximoReg = false
	private static String sMsgCoatacao
	private static String sProposta
	private static String sApoliceSusep
	private static String sStatus
	private static String sCEP
	private static String sCEP5
	private static String sCEP3
	private static String sImovelPossuiSeguro
	private static String sSinistroUltimosMeses
	private static String sPremioAz
	private static String sPremioAnt
	private static String sCotacao
	private static String descontoICGlobal
	private static String premioLiquidoAjustado
	private static String path
	private static TestData data
	private static def TotalReg
	private static msg


	//VALORES FIXADOS NA FEATURE
	private static double iof =  0.0738
	private static double comissao = 0.1   // DEFINIR TALVEZ NA TELA
	private static double custoAssistencia = 108.75 // FIXO

	private static double limitadorICGinferior = 55
	private static double limitadorICGsuperior = 99.99
	private static double ajustaIOF = 1 + iof  // fatorIOF 1
	private static double ajustaComissao = 1 - comissao // fatorComissão 1

	public static executaCotacaoResidencia(){

		path = findTestData('Massa_Emissao_Residencia').sourceUrl
		TotalReg = findTestData('Massa_Emissao_Residencia').getRowNumbers()
		data = findTestData('Massa_Emissao_Residencia')

		for (int i = 1; i <= TotalReg ; i++) {
			try{
				if (controleDeExecucao(i)) { System.err.println("--["+i+"]--")

					if(validaNumeroDeParcelas(i)){

						//Acessa
						WebUI.openBrowser('')
						WebUI.navigateToUrl('www.br.intrallianz.com/drlf01/web/azb-intranet/inicio')
						WebUI.maximizeWindow()

						//Loga
						WebUI.click(findTestObject('0_Page_Inicio/strong_Login'))
						WebUI.setText(findTestObject('1_Page_Login SSO/input_Usurio__58_login'), 'BE00151')
						WebUI.setText(findTestObject('1_Page_Login SSO/input_Senha__58_password'), 'az201902')
						WebUI.click(findTestObject('1_Page_Login SSO/input_Senha_botonSubmitFormula'))

						//Navega - Menus
						WebUI.click(findTestObject('0_Page_Inicio/a_Produo  Sinistros'))
						WebUI.click(findTestObject('2_Page_Produo  Sinistros/a_Produo Diversos'))
						WebUI.click(findTestObject('2_Page_Produo  Sinistros/a_Produo'))

						//***Tela Menu de Produção***
						WebUI.switchToWindowTitle('')
						WebUI.maximizeWindow()
						WebUI.waitForPageLoad(30)
						WebUI.click(findTestObject('3_Page_Menu Producao/input_Emisso_tipoDeSeleccion'))
						WebUI.waitForElementClickable(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione um agrupament'), 30)
						WebUI.selectOptionByLabel(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione um agrupament'), 'Residência Digital', false)
						WebUI.waitForElementClickable(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione um ProdutoRes'), 30)
						WebUI.selectOptionByLabel(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione um ProdutoRes'), 'Residência Digital', false)
						WebUI.delay(3)
						WebUI.setText(findTestObject('3_Page_Menu Producao/input_Corretor_mediador'), '4000129')
						WebUI.delay(3)
						WebUI.click(findTestObject('3_Page_Menu Producao/div_Selecionar'))
						WebUI.delay(3)
						WebUI.waitForElementClickable(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione uma operativa'), 30)
						WebUI.selectOptionByLabel(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione uma operativa'), 'Emitir apólice', false)
						WebUI.click(findTestObject('3_Page_Menu Producao/div_Avanar'))

						//***Tela Dados Básicos***
						WebUI.waitForPageLoad(30)
						WebUI.setText(findTestObject('4_Page_Tren/input_Incio de Vigncia_DatosGe'), data.getValue('Inicio de Vigência', i))
						WebUI.setText(findTestObject('4_Page_Tren/input_CPFCNPJ Tomador_Intervin'), data.getValue('CPF', i))
						WebUI.setText(findTestObject('4_Page_Tren/input_Nome do Tomador_Intervin'), data.getValue('Segurado', i))
						WebUI.waitForElementClickable(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione um Sub-RamoTr'), 5)
						WebUI.selectOptionByLabel(findTestObject('4_Page_Tren/select_(Selecione)    Apartame'), data.getValue('Tipo Moradia', i), false)
						WebUI.click(findTestObject('Object Repository/4_Page_Tren/div_Avanar'))

						//***Tela Dados de Risco***
						WebUI.waitForPageLoad(30)
						sCEP = data.getValue('CEP', i)
						sCEP5 = sCEP.take(5)
						sCEP3 = sCEP.substring(5)
						WebUI.setText(findTestObject('5_Page_Dados do Risco/input_CEP  Bairro_COD_BR'), sCEP5)
						WebUI.setText(findTestObject('5_Page_Dados do Risco/input_CEP  Bairro_SUB_COD_BR'), sCEP3)
						WebUI.click(findTestObject('5_Page_Dados do Risco/img_CEP  Bairro_COD_BRAjaxLocF'))
						WebUI.setText(findTestObject('Object Repository/Page_Trem/input_Nmero_DadosRiscoResiDigi'), data.getValue('Número', i))
						WebUI.setText(findTestObject('Object Repository/Page_Trem/input_Complemento_DadosRiscoRe'), data.getValue('Complemento', i))
						WebUI.setText(findTestObject('Object Repository/Page_Trem/input_Valor Garantido_Informac'), data.getValue('Valor em Risco', i))
						WebUI.selectOptionByLabel(findTestObject('Object Repository/Page_Trem/select_Tipo de Cobertura'), data.getValue('Tipo de Cobertura', i), false)

						sImovelPossuiSeguro = data.getValue('O imovel possui Seguro', i)
						if (sImovelPossuiSeguro.equals(data.getValue('O imovel possui Seguro', i))){
							WebUI.click(findTestObject('Object Repository/Page_Trem/input_Imvel possui seguro_Info'))
							sSinistroUltimosMeses = data.getValue('Tem ultimos 12 meses', i)
							if (sSinistroUltimosMeses.equals(data.getValue('Tem ultimos 12 meses', i)           )){
								WebUI.click(findTestObject('Object Repository/Page_Trem/input_Sinistro ltimos 12 meses'))
							}
							else {
								WebUI.click(findTestObject('Object Repository/Page_Trem/input_ NAO Sinistro ltimos 12 meses'))
							}
						}
						else {
							WebUI.click(findTestObject('Object Repository/Page_Trem/input_Imvel NAO possui seguro_Info'))
						}
						WebUI.click(findTestObject('Object Repository/Page_Trem/div_Avanar'))

						//***Tela Tabela de Preços***
						WebUI.waitForPageLoad(30)
						WebUI.selectOptionByLabel(findTestObject('4_Page_Tren/select_Dbito em conta bancria'), 'Boleto Bancário', false)
						WebUI.setText(findTestObject('4_Page_Tren/input_Dia do Pagamento_Parrill'), '15')
						WebUI.setText(findTestObject('Object Repository/4_Page_Tren/input_Comisso_ComisionamientoB'), data.getValue('% Comissão', i))
						WebUI.click(findTestObject('Object Repository/4_Page_Tren/div_Calcular'))
						WebUI.waitForPageLoad(30)

						//WebUI.click(findTestObject('Object Repository/4_Page_Tren/input_Ampliado1_CheckBox'))
						premioliqcalculado = formarValoresParaDouble(WebUI.getAttribute(findTestObject('Object Repository/4_Page_Tren/input_Pega_Pacote'), 'value'))

						calculoPremioTarifaDesejado()
						calculoPremioLiquidoCalculado()
						calculoICG()
						if(ICG < limitadorICGinferior || ICG >limitadorICGsuperior ){
							descontoICGlobal = converterDoubleString(ICG)
							WebUI.setText(findTestObject('Object Repository/4_Page_Tren/input_I.C. Global_PrimaTotalPa'), descontoICGlobal)
							WebUI.click(findTestObject('Object Repository/4_Page_Tren/div_Calcular'))
							WebUI.click(findTestObject('4_Page_Tren/input_TABELA PREOS_ParrillaBea'))
							WebUI.selectOptionByLabel(findTestObject('4_Page_Tren/select_1    2    3    4    5'), data.getValue('N° de Parcelas', i), false)
							WebUI.click(findTestObject('Object Repository/4_Page_Tren/div_Salvar Cotao'))
							WebUI.setText(findTestObject('Object Repository/4_Page_Tren/input_Referncia Cotao_EstudioB'), 'TESTE_Cota')
							WebUI.setText(findTestObject('Object Repository/4_Page_Tren/input_Nome_EstudioBeannombreEs'), 'TESTE_Nome')
							WebUI.click(findTestObject('Object Repository/4_Page_Tren/div_Salvar'))
							WebUI.acceptAlert()
							WebUI.delay(5)
							sMsgCoatacao = WebUI.getText(findTestObject('Object Repository/4_Page_Tren/messageAlert')).toString().substring(33,42).trim()
							if(sMsgCoatacao.length()>=8){ sCotacao = sMsgCoatacao}
							else {sCotacao = "Nº de Cotação Inválida"}
							WebUI.delay(2)
							premioLiquidoAjustado = formarValoresParaDouble(WebUI.getAttribute(findTestObject('Object Repository/4_Page_Tren/input_Pega_Pacote'), 'value'))
						} else {
							msg='Desconto (I.C Global) superior ao limite'
							premioLiquidoAjustado=''
							sCotacao=''
						}
					}
					//Grava na planilha
					sProposta = '0001'
					sApoliceSusep = '0002'
					sStatus = 'OK'
					sCotacao = ''
					escreverNoExcel(i)
					mostraCotacao()
					WebUI.closeBrowser()
				}
			} catch(Exception e){
				WebUI.closeBrowser()
				continue
			}
		}
		finalizar()
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static escreverNoExcel(int i){
		updateExcel.atualizaExcelCotacaoResidencia(i,
				path,
				sProposta,
				sApoliceSusep,
				sStatus,
				sCotacao,
				descontoICGlobal,
				converterDoubleString(premioliqcalculado),
				converterDoubleString(premioTarifaCalculado),
				premioLiquidoAjustado,
				msg)
	}

	/**
	 * @author T_DCDias
	 * @param i
	 * @return
	 */
	public static boolean controleDeExecucao(int i){
		boolean controle
		if( findTestData('Massa_Emissao_Residencia').getValue('Processado', i).equals('N'))
		{
			controle = true
		} else {
			controle = false
		}
		return controle
	}

	/**
	 * @author T_DCDias
	 * @param i
	 * @return
	 */
	public static boolean validaNumeroDeParcelas(int i){
		boolean controle
		if( Integer.parseInt(findTestData('Massa_Emissao_Residencia').getValue('N° de Parcelas', i)) < 11)
		{
			controle = true
		} else {
			msg='Numero de parcelas superior ao limite'
			System.err.println("Alerta ["+msg+"]")
			controle = false
		}
		return controle
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static calculoPremioTarifaDesejado(){
		premioTarifaAnt = 331.28   // pegar da massa
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static calculoPremioLiquidoCalculado(){
		premioTarifaCalculado = 0
		premioTarifaCalculado = ajustaIOF
		premioTarifaCalculado = premioliqcalculado/premioTarifaCalculado
		premioTarifaCalculado = premioTarifaCalculado*ajustaComissao
		premioTarifaCalculado = premioTarifaCalculado-custoAssistencia
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static calculoICG(){
		ICG=premioTarifaAnt/premioTarifaCalculado*100
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static double formarValoresParaDouble(String valor){
		double bValor
		StringBuffer sbf = new StringBuffer()
		String[] valorSeparado = valor.split("");
		for(int i = 0; i< valorSeparado.size(); i++){
			if(!valorSeparado[i].equals("R")&&!valorSeparado[i].equals(" ")&&!valorSeparado[i].equals("\$")){
				if(!valorSeparado[i].equals(",")){
					sbf.append(valorSeparado[i])
				}
			}
			if(valorSeparado[i].equals(",")){
				sbf.append(".")
			}
		}
		return  Double.parseDouble(sbf.toString())
	}

	/**
	 * @author T_DCDias
	 * @param precoDouble
	 * @return
	 * Transforma um double em 2 casas decimais
	 */
	public static String converterDoubleString(double precoDouble) {
		DecimalFormat fmt = new DecimalFormat("0.00");
		String string = fmt.format(precoDouble);
		String[] part = string.split("[,]");
		String valor = part[0]+"."+part[1];
		return valor;
	}

	/**
	 * @author T_DCDias
	 * Fecha a instância do Selenium WebDriver e todas os navegadores associados.
	 * @return
	 */
	public static finalizar(){
		try{
			WebDriver driver = DriverFactory.getWebDriver()
			SessionId session = ((RemoteWebDriver)driver).getSessionId();
			if(session.toString()!=null){
				WebUI.delay(1)
				WebUI.closeBrowser()
				driver.quit()
			}
		} catch (Exception e){
			System.err.println("-[Aplicação finalizada]-")
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static mostraCotacao(){
		System.err.println("Prêmio Liquido Calculado ["+premioliqcalculado+"]")
		System.err.println("Calculo Premio Tarifa Desejado ["+premioTarifaAnt+"]")
		System.err.println("Calculo Premio Tarifa Calculado ["+premioTarifaCalculado+"] ")
		System.err.println("Calculo ICG ["+ICG+"]")
		System.err.println("Cotação ["+sCotacao+"]")
		System.err.println("Prêmio Liquido Ajustado ["+premioLiquidoAjustado+"]")
		System.err.println("Mensagem de Alerta ["+msg+"]")
	}

}

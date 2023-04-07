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

import internal.GlobalVariable
import rpacommands.rpaCommands

public class EmissaoResidencia extends rpaCommands{

	public static emissao = {
		boolean bProximoReg
		def sProposta
		def sApoliceSusep
		def sStatus
		def sCEP
		def sCEP5
		def sCEP3
		def sImovelPossuiSeguro
		def sSinistroUltimosMeses
		def sPremioAz
		def sPremioAnt
		String path = findTestData('/residencia/Massa_Emissao_Residencia').sourceUrl
		def TotalReg = findTestData('/residencia/Massa_Emissao_Residencia').getRowNumbers()
		TestData data = findTestData('/residencia/Massa_Emissao_Residencia')

		//Acessa
		WebUI.openBrowser('')
		WebUI.navigateToUrl('www.br.intrallianz.com/drlf01/web/azb-intranet/inicio')
		WebUI.maximizeWindow()

		//Loga
		WebUI.click(findTestObject('PageResidencia/0_Page_Inicio/strong_Login'))
		WebUI.setText(findTestObject('PageResidencia/1_Page_Login SSO/input_Usurio__58_login'), 'BE00151')
		WebUI.setEncryptedText(findTestObject('PageResidencia/1_Page_Login SSO/input_Senha__58_password'), 'ERLccQBG54wWQoFPVjwJ4w==')
		WebUI.click(findTestObject('PageResidencia/1_Page_Login SSO/input_Senha_botonSubmitFormula'))

		//Navega - Menus
		WebUI.click(findTestObject('PageResidencia/0_Page_Inicio/a_Produo  Sinistros'))
		WebUI.click(findTestObject('PageResidencia/2_Page_Produo  Sinistros/a_Produo Diversos'))
		WebUI.click(findTestObject('PageResidencia/2_Page_Produo  Sinistros/a_Produo'))

		//***Tela Menu de Produção***
		WebUI.switchToWindowTitle('')
		WebUI.maximizeWindow()
		WebUI.waitForPageLoad(30)
		WebUI.click(findTestObject('PageResidencia/3_Page_Menu Producao/input_Emisso_tipoDeSeleccion'))
		WebUI.waitForElementClickable(findTestObject('PageResidencia/3_Page_Menu Producao/select_Selecione um agrupament'), 30)
		WebUI.selectOptionByLabel(findTestObject('PageResidencia/3_Page_Menu Producao/select_Selecione um agrupament'), 'Residência Digital', false)
		WebUI.waitForElementClickable(findTestObject('PageResidencia/3_Page_Menu Producao/select_Selecione um ProdutoRes'), 30)
		WebUI.selectOptionByLabel(findTestObject('PageResidencia/3_Page_Menu Producao/select_Selecione um ProdutoRes'), 'Residência Digital', false)
		WebUI.delay(3)
		WebUI.setText(findTestObject('PageResidencia/3_Page_Menu Producao/input_Corretor_mediador'), '4000129')
		WebUI.delay(3)
		WebUI.click(findTestObject('PageResidencia/3_Page_Menu Producao/div_Selecionar'))
		WebUI.delay(3)
		WebUI.waitForElementClickable(findTestObject('PageResidencia/3_Page_Menu Producao/select_Selecione uma operativa'), 30)
		WebUI.selectOptionByLabel(findTestObject('PageResidencia/3_Page_Menu Producao/select_Selecione uma operativa'), 'Emitir apólice', false)
		WebUI.click(findTestObject('PageResidencia/3_Page_Menu Producao/div_Avanar'))

		//Inicia o loop na planilha
		for (int i = 1; i <= TotalReg ; i++) {
			if (findTestData('Massa_Emissao_Residencia').getValue('Processado', i) == 'x') {
				bProximoReg = false
				sProposta = ''
				sApoliceSusep = ''
				sStatus = ''
				sCEP = ''
				sCEP5 = ''
				sCEP3 = ''
				sImovelPossuiSeguro = ''
				sSinistroUltimosMeses = ''

				//***Tela Dados Básicos***
				WebUI.waitForPageLoad(30)
				WebUI.setText(findTestObject('PageResidencia/4_Page_Tren/input_Incio de Vigncia_DatosGe'), data.getValue('Inicio de Vigência', i))
				WebUI.setText(findTestObject('PageResidencia/4_Page_Tren/input_CPFCNPJ Tomador_Intervin'), data.getValue('CPF', i))
				WebUI.setText(findTestObject('PageResidencia/4_Page_Tren/input_Nome do Tomador_Intervin'), data.getValue('Segurado', i))
				WebUI.waitForElementClickable(findTestObject('PageResidencia/3_Page_Menu Producao/select_Selecione um Sub-RamoTr'), 5)
				WebUI.selectOptionByLabel(findTestObject('PageResidencia/4_Page_Tren/select_(Selecione)    Apartame'), data.getValue('Tipo Moradia', i), false)
				WebUI.click(findTestObject('PageResidencia/4_Page_Tren/div_Avanar'))

				//***Tela Dados de Risco***
				WebUI.waitForPageLoad(30)
				sCEP = data.getValue('CEP', i)
				sCEP5 = sCEP.take(5)
				sCEP3 = sCEP.substring(5)
				WebUI.setText(findTestObject('PageResidencia/5_Page_Dados do Risco/input_CEP  Bairro_COD_BR'), sCEP5)
				WebUI.setText(findTestObject('PageResidencia/5_Page_Dados do Risco/input_CEP  Bairro_SUB_COD_BR'), sCEP3)
				WebUI.click(findTestObject('PageResidencia/5_Page_Dados do Risco/img_CEP  Bairro_COD_BRAjaxLocF'))
				WebUI.setText(findTestObject('PageResidencia/Page_Trem/input_Nmero_DadosRiscoResiDigi'), data.getValue('Número', i))
				WebUI.setText(findTestObject('PageResidencia/Page_Trem/input_Complemento_DadosRiscoRe'), data.getValue('Complemento', i))
				WebUI.setText(findTestObject('PageResidencia/Page_Trem/input_Valor Garantido_Informac'), data.getValue('Valor em Risco', i))
				WebUI.selectOptionByLabel(findTestObject('PageResidencia/Page_Trem/select_Tipo de Cobertura'), data.getValue('Tipo de Cobertura', i), false)
				sImovelPossuiSeguro = data.getValue('O imovel possui Seguro', i)
				if (sImovelPossuiSeguro == 'Sim'){
					WebUI.click(findTestObject('PageResidencia/Page_Trem/input_Imvel possui seguro_Info'))
					sSinistroUltimosMeses = data.getValue('Tem ultimos 12 meses', i)
					if (sSinistroUltimosMeses == 'Sim'){
						WebUI.click(findTestObject('PageResidencia/Page_Trem/input_Sinistro ltimos 12 meses'))
					}
					else {
						WebUI.click(findTestObject('PageResidencia/Page_Trem/input_ NAO Sinistro ltimos 12 meses'))
					}
				}
				else {
					WebUI.click(findTestObject('PageResidencia/Page_Trem/input_Imvel NAO possui seguro_Info'))
				}
				WebUI.click(findTestObject('PageResidencia/Page_Trem/div_Avanar'))

				//***Tela Tabela de Preços***
				WebUI.waitForPageLoad(30)
				WebUI.selectOptionByLabel(findTestObject('PageResidencia/4_Page_Tren/select_Dbito em conta bancria'), 'Boleto Bancário', false)
				WebUI.setText(findTestObject('PageResidencia/4_Page_Tren/input_Dia do Pagamento_Parrill'), '15')
				WebUI.setText(findTestObject('PageResidencia/4_Page_Tren/input_Comisso_ComisionamientoB'), data.getValue('% Comissão', i))
				WebUI.setText(findTestObject('PageResidencia/4_Page_Tren/input_I.C. Global_PrimaTotalPa'), '95')
				WebUI.click(findTestObject('PageResidencia/4_Page_Tren/div_Calcular'))
				WebUI.waitForPageLoad(30)
				sPremioAz = WebUI.getAttribute(findTestObject('PageResidencia/4_Page_Tren/input_Anualizada_modalidad_1_0'), 'Value')
				sPremioAnt = data.getValue('Prêmio Líquido', i)
				if (sPremioAz < sPremioAnt) {
					WebUI.waitForPageLoad(30)
				}
				WebUI.click(findTestObject('4_Page_Tren/input_TABELA PREOS_ParrillaBea'))
				WebUI.selectOptionByLabel(findTestObject('PageResidencia/4_Page_Tren/select_1    2    3    4    5'), data.getValue('N° de Parcelas', i), false)
				WebUI.click(findTestObject('PageResidencia/4_Page_Tren/div_Emitir'))
				WebUI.waitForAlert(30)
				WebUI.acceptAlert()

				//***Tela Dados de Emissão***
				WebUI.waitForPageLoad(30)
				WebUI.selectOptionByLabel(findTestObject('PageResidencia/4_Page_Tren/select_-    Tomador    Segurad'), 'Tomador', false)
				WebUI.click(findTestObject('PageResidencia/4_Page_Tren/div_Avanar'))
				WebUI.waitForPageLoad(30)
				if (WebUI.verifyTextPresent('LISTA CLIENTES *', true, FailureHandling.OPTIONAL)) {
					//***Tela Lista de Clientes***
					WebUI.click(findTestObject('PageResidencia/4_Page_Tren/ListaClientesBeanlistaClientes'))
					WebUI.waitForPageLoad(30)
				}

				//***Tela Dados de Clientes***
				WebUI.verifyTextPresent("D. CLIENTE *", true, FailureHandling.STOP_ON_FAILURE)
				WebUI.click(findTestObject('PageResidencia/4_Page_Tren/div_Avanar'))

				//***Tela Dados Comuns***
				WebUI.waitForPageLoad(30)
				WebUI.verifyTextPresent('D. COMUNS *', true, FailureHandling.STOP_ON_FAILURE)
				WebUI.click(findTestObject('PageResidencia/4_Page_Tren/div_Avanar'))

				//***Tela de Cláusulas***
				WebUI.waitForPageLoad(30)
				WebUI.verifyTextPresent('CLÁUSULAS *', true, FailureHandling.STOP_ON_FAILURE)
				WebUI.click(findTestObject('PageResidencia/4_Page_Tren/div_Avanar'))

				//***Tela Confirmação de Emissão***
				WebUI.verifyTextPresent('CONFIRMAÇÃO DE EMISSÃO *', true, FailureHandling.STOP_ON_FAILURE)
				//WebUI.click(findTestObject('4_Page_Tren/div_Avanar'))

				//***Tela Emissão Realizada***

				//Grava na planilha
				sProposta = '0001'
				sApoliceSusep = '0002'
				sStatus = 'OK'
				CustomKeywords.'intrallianz.Grava_Excel.WriteExcel'(i, path, sProposta, sApoliceSusep, sStatus)

			}
		}
		WebUI.closeBrowser()
	}



}

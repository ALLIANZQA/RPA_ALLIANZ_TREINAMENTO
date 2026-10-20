import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

//Acessa
WebUI.openBrowser('')
WebUI.navigateToUrl('https://www.br.intrallianz.com/')
WebUI.maximizeWindow()

//Loga
WebUI.click(findTestObject('0_Page_Inicio/strong_Login'))


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
WebUI.selectOptionByLabel(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione um agrupament'), 'Residencia                    ',
	false)
WebUI.waitForElementClickable(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione um ProdutoRes'), 30)
WebUI.selectOptionByLabel(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione um ProdutoRes'), 'Residência                    ',
	false)
WebUI.waitForElementClickable(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione um Sub-RamoTr'), 30)
WebUI.selectOptionByLabel(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione um Sub-RamoTr'), 'Tradicional                   ',
	false)
WebUI.setText(findTestObject('3_Page_Menu Producao/input_Corretor_mediador'), '4000129')
WebUI.delay(3)
WebUI.click(findTestObject('3_Page_Menu Producao/div_Selecionar'))
WebUI.delay(3)
WebUI.waitForElementClickable(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione uma operativa'), 30)
WebUI.selectOptionByLabel(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione uma operativa'), 'Emitir apólice                                              ',
	false)
WebUI.click(findTestObject('3_Page_Menu Producao/div_Avanar'))

//***Tela Dados Básicos***
WebUI.waitForPageLoad(30)
WebUI.setText(findTestObject('4_Page_Tren/input_Incio de Vigncia_DatosGe'), '06102018')
WebUI.setText(findTestObject('4_Page_Tren/input_CPFCNPJ Tomador_Intervin'), '16806430880')
WebUI.setText(findTestObject('4_Page_Tren/input_Nome do Tomador_Intervin'), 'andre rodrigues antunes')
WebUI.waitForElementClickable(findTestObject('Object Repository/3_Page_Menu Producao/select_Selecione um Sub-RamoTr'), 5)
WebUI.selectOptionByLabel(findTestObject('4_Page_Tren/select_(Selecione)    Apartame'), 'Apartamento Habitual', false)
WebUI.click(findTestObject('Object Repository/4_Page_Tren/div_Avanar'))

//***Tela Dados de Risco***
WebUI.waitForPageLoad(30)
WebUI.setText(findTestObject('5_Page_Dados do Risco/input_CEP  Bairro_COD_BR'), '04145')
WebUI.setText(findTestObject('5_Page_Dados do Risco/input_CEP  Bairro_SUB_COD_BR'), '000')
WebUI.click(findTestObject('5_Page_Dados do Risco/img_CEP  Bairro_COD_BRAjaxLocF'))
WebUI.setText(findTestObject('5_Page_Dados do Risco/input_Nmero_DadosRiscoResiBean'), '71')
WebUI.setText(findTestObject('5_Page_Dados do Risco/input_Complemento_DadosRiscoRe'), 'ap 00')
WebUI.selectOptionByLabel(findTestObject('5_Page_Dados do Risco/select_(Selecionar)    Seguro'), 'Seguro Novo', false)
WebUI.click(findTestObject('5_Page_Dados do Risco/input_Residncia Simplificado_C'))
WebUI.click(findTestObject('5_Page_Dados do Risco/div_Aceitar'))

//***Tela Tabela de Preços***
WebUI.waitForPageLoad(30)
WebUI.selectOptionByLabel(findTestObject('4_Page_Tren/select_Dbito em conta bancria'), 'Boleto Bancário', false)
WebUI.setText(findTestObject('4_Page_Tren/input_Dia do Pagamento_Parrill'), '15')
WebUI.click(findTestObject('4_Page_Tren/div_Calcular_1'))
WebUI.click(findTestObject('4_Page_Tren/input_TABELA PREOS_ParrillaBea'))
WebUI.selectOptionByLabel(findTestObject('4_Page_Tren/select_1    2    3    4    5'), '4', false)
WebUI.click(findTestObject('4_Page_Tren/div_Emitir'))
WebUI.waitForAlert(30)
WebUI.acceptAlert()

//***Tela Dados de Emissão***
WebUI.waitForPageLoad(30)
WebUI.selectOptionByLabel(findTestObject('4_Page_Tren/select_-    Tomador    Segurad'), 'Tomador', false)
WebUI.click(findTestObject('4_Page_Tren/div_Avanar'))
WebUI.waitForPageLoad(30)
if (WebUI.verifyTextPresent('LISTA CLIENTES *', true, FailureHandling.OPTIONAL)) {
	//Tela Lista de Clientes
	WebUI.click(findTestObject('4_Page_Tren/ListaClientesBeanlistaClientes'))
	WebUI.waitForPageLoad(30)
}

//***Tela Dados de Clientes***
WebUI.verifyTextPresent("D. CLIENTE *", true, FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('4_Page_Tren/div_Avanar'))

//***Tela Dados Comuns***
WebUI.waitForPageLoad(30)
WebUI.verifyTextPresent('D. COMUNS *', true, FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('4_Page_Tren/div_Avanar'))

//***Tela de Cláusulas***
WebUI.waitForPageLoad(30)
WebUI.verifyTextPresent('CLÁUSULAS *', true, FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('4_Page_Tren/div_Avanar'))

//***Tela Confirmação de Emissão***
WebUI.verifyTextPresent('CONFIRMAÇÃO DE EMISSÃO *', true, FailureHandling.STOP_ON_FAILURE)
//WebUI.click(findTestObject('4_Page_Tren/div_Avanar'))

//***Tela Emissão Realizada***

WebUI.closeBrowser()


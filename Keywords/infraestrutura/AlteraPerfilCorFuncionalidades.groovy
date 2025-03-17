package infraestrutura

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.driver.DriverFactory
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

import io.cucumber.datatable.DataTable
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import auxiliares.updateExcel
import sendEmail.envioDeEmail;
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.MenuMatriz


public class AlteraPerfilCorFuncionalidades extends rpaCommands {


	public static String sPerfil
	public static String sPerfilAlt
	public static String sStatus
	public static String sObs
	public static String sCodCapturado
	public static boolean bProx = true
	public static String msgErro
	private static String coringa;
	private static int column
	private static TestData data

	/**
	 * @author T_DCDias
	 * @param setor
	 * @return
	 */
	public static alteraPerfilCor(String setor){
		data = findTestData(findTestDataPath(setor))
		//		if(!validaExecutados(data, 'x', 9)){
		fluxoCompletoalteraPerfilCor(setor)
		enviarEmail(verificaFalhas(setor))
		//		}
		finalizar();
	}

	/**
	 * @author T_DCDias
	 * @param setor
	 * @return
	 */
	public static fluxoCompletoalteraPerfilCor(String setor){
		System.out.println();
		int indexTableLine = 1
		String path = findTestData(findTestDataPath(setor)).sourceUrl
		def TotalReg = findTestData(findTestDataPath(setor)).getRowNumbers()
		init(setor)
		menuUMA()

		/**
		 * Faz a alteração do perfil.
		 */
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			System.err.println("Row Number ["+i+"]")
			try{
				if (data.getValue('STATUS', i) == 'x') {
					sPerfil = ''
					sPerfilAlt = ''
					sStatus = ''
					sObs = ''
					sCodCapturado = ''
					msgErro = ''
					bProx = false
					WebUI.setText(findTestObject('Object Repository/PageInfraestrutura/Page_UMA/input_Descrio_codigo'), data.getValue('LOGIN', i))
					WebUI.click(findTestObject('Object Repository/PageInfraestrutura/Page_UMA/div_Pesquisar'))

					try{
						WebUI.click(findTestObject('Object Repository/PageInfraestrutura/Page_UMA/td_Allianznet_BR'))
					} catch (Exception e ){
						msgErro = 'Aplicação Allianznet_BR não disponível para este código'
						sStatus = 'KO'
					}

					/**
					 * Click abaixo acha o código na tabela.
					 */
					String xpath = "//td[@id='sectionDetalleSucursalesCellContent']"
					String tag = "tr"
					WebUI.delay(2)
					List<WebElement> listOfElements = selecionaListaDeElementos(xpath,tag)

					try{
						for(int j = 0; j < listOfElements.size(); j++){
							System.err.println(listOfElements[j].getText().toString()+" "+data.getValue('COD_FORMAT',i));
							if(listOfElements[j].getText().toString().contains(data.getValue('COD_FORMAT',i)) ){
								indexTableLine = j - 1
								System.err.println("Found - List: ["+listOfElements[j].getText().toString()+"] Data: ["+data.getValue('COD_FORMAT',i)+"] j: ["+j+"] indexTableLine: ["+indexTableLine+"]\n")
								clicarEmElementoDaTela("//tr[@id='row_"+Integer.toString(indexTableLine)+"_listaSucursales']", 1)
								break
							}
						}

						WebUI.delay(2)
						WebUI.click(findTestObject('Object Repository/PageInfraestrutura/Page_UMA/div_Alterar'))
						sPerfil = WebUI.getAttribute(findTestObject('Object Repository/PageInfraestrutura/Page_UMA/select_AGROEMPR    AGROPRIN'), 'value')

						/**
						 * Seleção da regra de negócio para alteração de perfil de corretor
						 */
						regrasGeralInfraestrutura(data, i)
					} catch (Exception e){
						msgErro = 'Código não encontrado para este corretor'
						sStatus = 'KO'
						init(setor)
						menuUMA()
					}

					if (bProx == false) {
						WebUI.selectOptionByValue(findTestObject('Object Repository/PageInfraestrutura/Page_UMA/select_AGROEMPR    AGROPRIN'), sPerfilAlt, true)
						WebUI.click(findTestObject('Object Repository/PageInfraestrutura/Page_UMA/div_Alterar'))
						WebUI.acceptAlert()

						/**
						 * Click abaixo acha o código na tabela atualizada.
						 */
						String xpathAlt = "//td[@id='sectionDetalleSucursalesCellContent']"
						String tagAlt = "tr"
						WebUI.delay(2)
						List<WebElement> listOfElementsAlt = selecionaListaDeElementos(xpathAlt,tagAlt)

						for(int k = 0; k < listOfElementsAlt.size(); k++){
							if(listOfElementsAlt[k].getText().toString().contains(data.getValue('COD_FORMAT',i)) ){
								indexTableLine = k - 1
								sPerfilAlt = retornaValorDoElementoDaTela("//td[@id='listaSucursales_"+Integer.toString(indexTableLine)+"_3']")
								sCodCapturado = retornaValorDoElementoDaTela("//td[@id='listaSucursales_"+Integer.toString(indexTableLine)+"_1']")
								break
							}
						}
						WebUI.click(findTestObject('Object Repository/PageInfraestrutura/Page_UMA/td_Pesquisa'))
					}

					/**
					 * Grava retornos na planilha Excel.
					 */
					updateExcel.atualizaExcelAlteraPerfilCor(i, path, sPerfil, sPerfilAlt, sCodCapturado, sStatus, sObs)
					System.err.println()
				}
			} catch (Exception e){
				updateExcel.atualizaExcelAlteraPerfilCor(i, path, sPerfil, sPerfilAlt, sCodCapturado, sStatus, msgErro)
				init(setor)
				menuUMA()
			}
		}

		/**
		 * Desloga do portal e fecha o browser.
		 */
		//		WebUI.switchToWindowTitle('Manutenção Geral')
		//		WebUI.click(findTestObject('Object Repository/PageInfraestrutura/Page_Manuteno Geral/strong_Logout'))
		WebUI.closeBrowser()
	}


	/**
	 * @author T_DCDias
	 * @return
	 */
	public static init(String aba){
		/**
		 * Acessa e loga no portal.
		 */
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands(aba);
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static menuUMA(){
		/**
		 * Navega pelos menus até a aplicacao.
		 */
		WebUI.delay(10);
		MenuMatriz.manutancaoGeral()
		MenuMatriz.subMenu("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div/div/div[2]/ul", "a","UMA")
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()
		WebUI.delay(2);
	}

	/**
	 * @author T_DCDias
	 * @param path
	 * @return
	 */
	public static String findTestDataPath(String path){
		Map<String, String> listpath = new HashMap<String, String>();
		listpath.put('comercial','/Comercial/Altera_perfil_COR_COM');
		listpath.put('infraestrutura','/infraestrutura/Altera_perfil_COR');
		return listpath.get(path).toString()
	}

	/**
	 * @author T_DCDias
	 * @param data
	 * @return
	 */
	public static regrasGeralInfraestrutura(TestData data, int i){

		if (sPerfil.equals(data.getValue(4, i).toString().trim())   ){
			sPerfilAlt = data.getValue(5, i).toString().trim()
			sStatus = 'OK'
			sObs = 'Perfil alterado!'
		} else {
			sPerfilAlt = ''
			sStatus = 'KO'
			sObs = 'Perfil divergente'
			bProx = true
			WebUI.click(findTestObject('Object Repository/PageInfraestrutura/Page_UMA/td_Pesquisa'))
		}
	}

	/**
	 * @author T_DCDias
	 * @param executados
	 * @return
	 */
	public static enviarEmail(boolean executados){
		if(executados){
			pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/infraestrutura/ConfiguracoesalteraPerfilCor.txt";
			lerConfigurações(pathTxtEmail)
			String pathAnexos = "C:/Users/Public/RPA_ALLIANZ/Emails/infraestrutura/Anexos.txt"
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, FileInput.readFile(pathAnexos), host, porta, 'Planilha1' )
		}
	}

	/**
	 * @author T_DCDias
	 * @param setor
	 * @return
	 */
	public static boolean verificaFalhas(String setor){
		TestData data = findTestData(findTestDataPath(setor))
		return validaExecutados(data, 'x', 9)
	}
}

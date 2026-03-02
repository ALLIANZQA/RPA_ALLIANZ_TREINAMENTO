package infraestrutura

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
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.driver.DriverFactory
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

public class CadastroPerfilCorFuncionalidades extends rpaCommands {
	
	private static int coringa = 10;
	private static TestData data
	private static String path
	public static String bamotor 
	public static String ativacaousuario
	private static String tipoAba
	private static int  linha
	private static String msgerror 
	
	/**
	 * @author T_DCDias
	 * @param setor
	 * @return
	 */
	public static cadastroPerfilCor(String setor){
		data = findTestData(findTestDataPath(setor))
		if(!validaExecutados(data, 'NREALIZADA', coringa) || !validaExecutados(data, 'FALHA NO CADASTRO', coringa)  ){
			fluxoCompletoCadastroPerfilCor(setor)
			enviarEmail(verificaFalhas(setor))
		}
		finalizar();
	}
	
	
	/**
	 * @author T_DCDias
	 * @param setor
	 * @return
	 */
	public static fluxoCompletoCadastroPerfilCor(String setor){
		linha = 0
		path = findTestData(findTestDataPath(setor)).sourceUrl
		def TotalReg = findTestData(findTestDataPath(setor)).getRowNumbers()

		/**
		 * Inicializa Data File.
		 */
		init()
		menuUsuarios()
		/**
		 * Faz a alteração do perfil.
		 */
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			try{
				if (data.getValue('ATIVAÇÃO USUÁRIO', i) == 'NREALIZADA') {
					    linha = Integer.valueOf(data.getValue('LINHA', i))
						menuAtivacaoUsuario()
					    preencheCamposObrigatorios(i)
					    ativarUsuario()
						erroAtivacao('//*[@id="divAppContent"]/table', "td", 'Erro transferência AltaAgenteLDAP. [LDAP: error code 68 - Entry Already Exists]' )
						validaAtivacao('//*[@id="divAppContent"]/table', 'input', "REALIZADA" )
				}	
			  } catch (Exception e){
			    //FALHA DE LEITURA DO ARQUIVO DATA 
			    continue
			  }
	    }
		WebUI.closeBrowser()
	}
	
	/**
	 * @author ALZ_ServiceQA
	 * @param aba
	 * @return
	 */
	public static acessoAmbiente(String aba){
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands(aba);
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static init(){
		/**
		 * Acessa e loga no portal.
		 */
		tipoAba = 'CadastroCorretor'
		acessoAmbiente(tipoAba)
		WebUI.delay(10)
		
		/**
		 * Navega pelos menus até a aplicacao.
		 */
		WebUI.click(findTestObject('Object Repository/PageInfraestrutura/Page_Produo  Sinistros/a_Manuteno Geral'))
		WebUI.delay(4)
		MenuMatriz.subMenu("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div/div/div[2]/ul", "a","UMA")
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static menuUsuarios(){
		clickXpath('//*[@id="menu12"]', 2)
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static menuAtivacaoUsuario(){
		//Ativação
		clickXpath('//*[@id="menu22"]', 2)
		//Corretor
		clickXpath('//*[@id="menu223"]', 2)
	}
	
	/**
	 * @author T_DCDias	
	 * @param i
	 * @return
	 */
	public static preencheCamposObrigatorios(int i){
		System.err.println(data.getValue('AGENTE/ MEDIADOR', i))
		//Nº Intermediário
		preencheXpath('//*[@id="agente7"]',data.getValue('AGENTE/ MEDIADOR', i) , 2)
		
		//Lupa
		clickXpath('//*[@id="img1"]', 2)
		
		//Verifica mensagens de erro de cadastro
		validaMensagemDeErro('//*[@id="divAppContent"]/table',"td", 'Erro, O Corretor não existe em Db2.')
		System.err.println()
		
		//E-mail - ação provisória 
		preencheXpath('//*[@id="mail"]',data.getValue('E-MAIL', i), 2)
		
		//Rol
		clickXpath('//*[@id="allrol"]', 2)
		String rolxpath = '//*[@id="allrol"]'
		String roltag ="option";
		String rol = data.getValue('ROL', i)
		WebUI.delay(5)
		selecionaListaDeElementos(rolxpath,roltag)
		for(int k = 0; k < listOfElements.size(); k++){
			 if(listOfElements[k].getText().equals(rol.toString())){
				 System.err.println("CLICK ROL - ["+k+"] ["+roltag+"] ["+listOfElements.get(k).getText()+"]" )
				 listOfElements[k].click();
				 break;
			 }
		}
		
		WebUI.delay(2)
		//Partner associado
		clickXpath('//*[@id="preferredlanguage"]', 2)
		String xpath = '//*[@id="allpartner"]'
		String tag ="option";
		String bp = data.getValue('PARTNER ASSOCIADO', i)
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int j = 0; j < listOfElements.size(); j++){
			 if(listOfElements[j].getText().equals(bp.toString())){
				 System.err.println("CLICK PARTNER ASSOCIADO- ["+j+"] ["+tag+"] ["+listOfElements.get(j).getText()+"]" )
				 listOfElements[j].click();
				 break;
			 }
		}
		
		WebUI.delay(2)
		
		//Usuário - SALVAR
		bamotor = recuperaValor('//*[@id="uid"]', 'value', 2)
		System.err.println("bamotor ["+bamotor+"]")
		
		//Perfil Allianznet
		clickXpath('//*[@id="perfilEpac"]', 2)
		String paxpath = '//*[@id="perfilEpac"]'
		String patag ="option";
		String pa = data.getValue('PERFIL ALLIANZNET', i)
		selecionaListaDeElementos(paxpath,patag)
		for(int p = 0; p < listOfElements.size(); p++){
			 if(listOfElements[p].getText().equals(pa.toString())){
				 System.err.println("CLICK PERFIL ALLIANZNET - ["+p+"] ["+patag+"] ["+listOfElements.get(p).getText()+"]" )
				 listOfElements[p].click();
				 break;
			 }
		}
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static ativarUsuario(){
		clickXpath('//*[@id="queryButton"]', 5)
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static inicio(){
		clickXpath('//*[@id="o_2"]', 2)
	}
	
	/**
	 * @author T_DCDias
	 * @param i
	 * @return
	 */
	public static salvarEvidencia(int i){
		System.err.println("BA MOTOR ["+bamotor+"] Operação - Ativar usuário ["+ativacaousuario+"] msg error ["+msgerror+"]")
		updateExcel.atualizaCadastroCorretorExcelCorretagemFacil(path , bamotor, ativacaousuario , linha, tipoAba, msgerror)
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
		listpath.put('infraestruturaCadastro','Data Files/infraestrutura/Cadastro/Cadastro_perfil_COR');
		return listpath.get(path).toString()
	}

	/**
	 * @author T_DCDias
	 * @param executados
	 * @return
	 */
	public static enviarEmail(boolean executados){
		if(executados){
			pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/infraestrutura/ConfiguracoesCadastroPerfilCor.txt";
			lerConfigurações(pathTxtEmail)
			String pathAnexos = "C:/Users/Public/RPA_ALLIANZ/Emails/infraestrutura/cadastroAnexos.txt"
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, FileInput.readFile(pathAnexos), 'CadastroCorretor' )
		}
	}

	/**
	 * @author T_DCDias
	 * @param setor
	 * @return
	 */
	public static boolean verificaFalhas(String setor){
		boolean valor 
		TestData data = findTestData(findTestDataPath(setor))
		if(validaExecutados(data, 'NREALIZADA', coringa) || validaExecutados(data, 'FALHA NO CADASTRO', coringa)){
			valor = true;
		  } else {
		    valor = false;
		  }
		return valor
	}
	
	/**
	 * @author T_DCDias
	 * @param xpath
	 * @param tag
	 * @param msg
	 * @return
	 */
	public static  validaMensagemDeErro(String xpath, String tag, String msg ){
		 boolean valor 
		 WebUI.delay(2)
		 selecionaListaDeElementos(xpath, tag)
		 for(int k = 0; k < listOfElements.size(); k++){
			  if(listOfElements[k].getText().contains(msg.toString())){
				  valor = true
				  break;
			  }
		 }
		 
		 System.err.println("CHECK MSG ERROR ["+valor+"]")
		 
		 if(valor){
			 bamotor=''
			 msgerror = msg;
			 ativacaousuario = 'FALHA NO CADASTRO'
			 salvarEvidencia(int i)
			 WebUI.delay(2)
			 clickXpath('//*[@id="o_3"]', 5)
		 }
	}
	
	/**
	 * @author T_DCDias
	 * @param xpath
	 * @param tag
	 * @param msg
	 * @return
	 */
	public static  validaAtivacao(String xpath, String tag, String msg ){
		 boolean valor
		 WebUI.delay(2)
		 String msgvalor = recuperaValor('//*[@id="descripcionEstado"]', 'value', 2)
		 if(msgvalor.equals(msg)){
			 valor = true
		 } else {
		     valor = false
		 }
		 System.err.println("CHECK ACTIVIT ["+valor+"]")
		 if(valor){
			 bamotor
			 msgerror = ''
			 ativacaousuario = msg
			 salvarEvidencia(int i)
			 WebUI.delay(2)
			 clickXpath('//*[@id="o_2"]', 5)
		 }
	}
	
	/**
	 * @author T_DCDias
	 * @param xpath
	 * @param tag
	 * @param msg
	 * @return
	 */
	public static  erroAtivacao(String xpath, String tag, String msg ){
		 boolean valor = false
		 WebUI.delay(2)
		 selecionaListaDeElementos(xpath, tag)
		 for(int k = 0; k < listOfElements.size(); k++){
			  if(listOfElements[k].getText().contains(msg.toString())){
				  valor = true
				  break;
			  }
		 }
		 
		 System.err.println("CHECK MSG ERROR ["+valor+"]")
		 
		 if(valor){
			 bamotor=''
			 msgerror = msg;
			 ativacaousuario = 'FALHA NO CADASTRO'
			 salvarEvidencia(int i)
			 WebUI.delay(2)
			 clickXpath('//*[@id="o_3"]', 5)
		 }
	}
}

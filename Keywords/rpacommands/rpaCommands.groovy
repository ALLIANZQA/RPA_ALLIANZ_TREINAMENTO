package rpacommands

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static java.nio.file.StandardCopyOption.*;
import java.nio.channels.FileChannel
import java.text.DateFormat
import java.text.SimpleDateFormat
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.SessionId
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import auxiliares.FileInput;
import sendEmail.CreateEmail
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.itextpdf.text.Image;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import java.awt.image.BufferedImage
import java.text.NumberFormat
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import internal.GlobalVariable
import io.cucumber.datatable.DataTable
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.apache.http.util.Asserts
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium
import static org.junit.Assert.*
import bean.CasoDeTeste
import bean.MassaDados
import java.util.regex.Pattern
import static org.apache.commons.lang3.StringUtils.join
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.Wait
import org.openqa.selenium.support.ui.WebDriverWait
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import java.util.List
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable
import org.openqa.selenium.remote.Augmenter
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.StaleElementReferenceException;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import auxiliares.TakesScreenshots
import auxiliares.MenuMatriz
import auxiliares.ExtractDates

/**
 * @author T_dvlopes
 *
 */
public class rpaCommands  {

	protected static TestObject flexibleTestObject
	protected static List<WebElement> listOfElements
	protected static String valueCheck
	protected static String validaErro
	protected static String valueCheckFuncionarioAutoResidencia
	static boolean finalizado = false;
	static boolean selecionarApolice = true;
	static String  qtdPendenciaRecuperada;
	static String numeroRenovacaoApolice;
	static boolean primeiroFluxo = true;
	static boolean enviarApolice;
	static boolean enviarBoleto;
	static int cont = 0;


	/**
	 * Parametros de configuração para leitura de arquivo de configuração
	 */
	protected static String pathTxtEmail
	protected static String sender
	protected static String pwdsender
	protected static String subject
	protected static String texto
	protected static String path1
	protected static String nome1
	protected static String path2
	protected static String nome2
	protected static String host
	protected static String porta

	/**
	 * CRITICAS SIB
	 */
	protected static String supep
	protected static String ramo
	protected static boolean alerta


	/**
	 * Valida premio total do fluxo AGCS
	 */
	public static int premioTotal


	/**
	 * iniciaAplicacaoMatriz
	 * @author Dvlopes
	 */
	public static void iniciaAplicacaoMatrizCommands(String url){
		try{
			abreBrowser(url);
		} catch (Exception e) {
			System.err.println('Falha ao iniciar APlicação Tela Matriz' + e);
			finalizar();
		}
	}

	/**
	 * realizaLoginAllianzNet INTRANET
	 * Recebe por parametro nome da aba 'excel' localiza qual user/pword utilizar e realiza login
	 * @author Dvlopes
	 */
	public static void realizaLoginIntraNetProducaoCommands(String nomeArquivo){
		try{
			auxiliares.UserPassword.getUsuarioSenha(nomeArquivo)
			realizaLoginTelaIntraNetProducao(auxiliares.UserPassword.getUser(),auxiliares.UserPassword.getSenha());
		} catch (Exception e) {
			System.err.println('Falha ao realizar Login MatrizProdução' + e);
			finalizar();
		}
	}

	/**
	 * realizaLoginAllianzNet INTRANET
	 * Recebe por parametro nome da aba 'excel' localiza qual user/pword utilizar e realiza login
	 * @author Dvlopes
	 */
	public static void realizaLoginIntraNetProducaoTransacionalCommands(String nomeArquivo, String generico){
		try{
			auxiliares.UserPassword.getUsuarioSenha(nomeArquivo)
			preencheXpathSetText('//*[@id="nx-input-0"]', auxiliares.UserPassword.getUser(), 20);
			preencheXpathSetText('//*[@id="nx-input-1"]', auxiliares.UserPassword.getSenha(), 1);
			WebUI.delay(2)
			usuarioGenericoTransacional(generico)
			WebUI.delay(2)
			clickXpath('//*/div/button',0);
		} catch (Exception e) {
			System.err.println('Falha ao realizar Login MatrizProdução' + e);
			finalizar();
		}
	}

	/**
	 * @author be54955
	 * @param generico
	 * @return
	 */
	public static usuarioGenericoTransacional(String generico){
		//Pesquisar
		clickXpath('/*/body/div[1]/div/app-root/app-public-home/app-public-home/div/div[1]/div/app-login/div/form/div[1]/div/app-generic-users/nx-formfield/div/div/div/div/div/nx-dropdown/div/div[2]/nx-icon', 1);
		selecionaListaDeElementos('//*[@id="cdk-overlay-0"]/div/div[2]','div')
		for(int i = 0; i < listOfElements.size(); i++){
			//			System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains(generico.toString())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				listOfElements[i].click()
				break
			}
		}
	}
	//COMANDOS GENERICOS


	/**
	 * @author be54955 	
	 * @return
	 */
	public static openChomeBrowserHeadless(String url){
		String chromeDriverPath = "C:\\Users\\Public\\Katalon_Studio_Windows_64-7.2.1\\configuration\\resources\\drivers\\chromedriver_win32\\chromedriver.exe" ;
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
		WebDriver driver = new ChromeDriver(options);
		driver.get(url);
	}

	/**
	 * @author be54955
	 * @param url
	 * @return
	 */
	public static openChromeBrowser(String url){
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Public\\Katalon_Studio_Windows_64-7.2.1\\configuration\\resources\\drivers\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.navigate().to(url);
		driver.manage().window().maximize();
	}

	public static wainting(int delay){
		WebDriver driver = DriverFactory.getWebDriver();
		driver.manage().timeouts().implicitlyWait(delay, TimeUnit.SECONDS);
	}

	/**clickXpath
	 * executa Click na tela recebendo por parametro Xpath & delay
	 * @param xpath
	 * @param delay
	 * @return
	 * @author Dvlopes
	 */
	public static clickXpath(String xpath, int delay){
		WebUI.delay(delay);
		WebDriver driver = DriverFactory.getWebDriver();
		WebElement element = driver.findElement(By.xpath(xpath));
		element.click();
	}


	/**clickXpath
	 * executa Click na tela recebendo por parametro Xpath & delay
	 * @param xpath
	 * @param delay
	 * @return
	 * @author Dvlopes
	 */
	public static doubleClickXpath(String xpath, int delay){
		WebUI.delay(delay);
		WebDriver driver = DriverFactory.getWebDriver();
		Actions actions = new Actions(driver);
		WebElement elementLocator = driver.findElement(By.xpath(xpath));
		actions.doubleClick(elementLocator).perform();
	}

	/**
	 * Sobe para tela arquivo
	 * recebendo por parametro xpath / filepath / delay * 
	 * 
	 * @author Dvlopes
	 * @param path
	 * @param filepath
	 * @param delay
	 * @return
	 */
	public static fileUpload(String path, String filepath, int delay){
		WebUI.delay(delay);
		WebDriver driver = DriverFactory.getWebDriver();
		WebElement fileInput = driver.findElement(By.xpath(path)).sendKeys(filepath);
		WebUI.delay(delay);
	}

	/**clickXpath
	 * EXECUTA CLICK RECEBENDO POR PAAMETRO
	 * @param xpath
	 * @param delay
	 * @return
	 * @author Dvlopes
	 */
	public static moverMouse(String xpath, int delay){
		try{
			WebDriver driver = DriverFactory.getWebDriver();
			Actions actions = new Actions(driver);
			WebElement mainMenu = driver.findElement(By.xpath(xpath));
		}catch (Exception e){

		}

	}

	/** RECUPERA ELEMENTO RETURN SUA PROPRIEDADE
	 * @author Dvlopes
	 * @param xpath
	 * @param atribute
	 * @param delay
	 * @return
	 */
	public static String validarElemento(String xpath,String atribute, int delay){
		String elementreturn=""
		try{
			WebUI.delay(delay);
			WebDriver driver = DriverFactory.getWebDriver();
			WebElement element = driver.findElement(By.xpath(xpath));
			elementreturn = element.getAttribute(atribute);
		} catch (Exception e){

		}

		return elementreturn;
	}

	/**
	 * Recupera elemento e retorna o elemento ou 'SEM ERRO'
	 * @author Dvlopes
	 * @param xpath
	 * @param atribute
	 * @param delay
	 * @return
	 */
	public static String validarElementoErro(String xpath,String atribute, int delay){
		String elementreturn
		try{
			WebUI.delay(delay);
			WebDriver driver = DriverFactory.getWebDriver();
			WebElement element = driver.findElement(By.xpath(xpath));
			elementreturn = element.getAttribute(atribute);
			return elementreturn;
		}catch (Exception e){
			return elementreturn = 'sem erro';
		}

	}


	/**
	 * RECEBE POR PARAMETRO O NOME DO PROGRAMA A SER FECHADO
	 * Chama CMD para executar linha de comando *
	 *
	 * 	String cmd = "taskkill -im chrome.exe /f"
	 Runtime.getRuntime().exec(cmd)	 *
	 *
	 * @param programa
	 * @return
	 * @author Dvlopes
	 */
	public static chamarCMD(String programa){
		String cmd = "taskkill -im "+programa+".exe /f";
		System.err.println('CMD ["taskkill -im "+programa+".exe /f"]')
		Runtime.getRuntime().exec(cmd);
	}


	/**Finaliza aplicação chrome via CMD
	 * @author Dvlopes
	 * @return
	 */
	public static finalizaChromeCMD(){
		chamarCMD('chrome');
		chamarCMD('chromeDriver');
	}


	/**
	 * @author Dvlopes
	 * @param arquivoEntrada
	 * @param caminhoSaida
	 * @return	 * 
	 * Descompacta arquivo recebendo dois parametros [ENTRADA / SAIDA]	 * 
	 * Entrada [C:\\Users\\ALZ_ServiceQA\\Desktop\\teste.zip]
	 * Saida [C:\\Users\\ALZ_ServiceQA\\Desktop]
	 * 
	 */
	public static descompactaArquivo(String arquivoEntrada, String caminhoSaida){
		try{
			System.out.println(System.getProperties());
			String cmd = 'PowerShell Expand-Archive -Path '+arquivoEntrada+' -DestinationPath '+caminhoSaida+'';
			System.err.println('PowerShell Expand-Archive -Path '+arquivoEntrada+' -DestinationPath '+caminhoSaida+'')
			Runtime.getRuntime().exec(cmd);
			WebUI.delay(15);
		}catch(e){
			System.err.println('Não foi possivel descompactar arquivo ['+arquivoEntrada+'] Para o caminho ['+caminhoSaida+']');
		}
	}

	/**
	 * @author Dvlopes
	 * @param xpath
	 * @param delay
	 * @return
	 */
	public static clickXpathInframe(String xpath, int delay){
		WebUI.delay(delay);
		WebDriver driver = DriverFactory.getWebDriver();
		driver.switchTo().frame("appArea")
		WebElement element = driver.findElement(By.xpath(xpath));
		element.click();
	}

	/**
	 *@author Dvlopes
	 * @return
	 */
	public static validarEnviado(){
		valueCheck = 'Enviado';
	}

	/**
	 * @author Dvlopes
	 * @param erro
	 * @return
	 */
	public static validarEnviado(String erro){
		validaErro = '';
		validaErro = validaErro + erro;
		System.err.println("ERRO ["+validaErro+"]");
	}

	/**
	 * @author Dvlopes
	 * @param erro
	 * @return
	 */
	public static validarErro(String erro){
		validaErro = '';
		validaErro = validaErro + erro;
		System.err.println("ERRO ["+validaErro+"]");
	}


	/**
	 * @author Dvlopes
	 * @return
	 */
	public static validarProcessado(){
		valueCheckFuncionarioAutoResidencia = 'Processado';
	}


	public static UpdateStatusNaoProcessado(){
		valueCheckFuncionarioAutoResidencia = 'Nao Processado';
	}
	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static UpdateStatusNaoEnviado(){
		valueCheck = 'Não enviado';
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static fechaAlerta(){
		try{
			WebDriver driver = DriverFactory.getWebDriver();
			driver.switchTo().alert().accept();
		}catch (Exception e){
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static String pegaMSGAlerta(){
		String msg
		try{
			WebDriver driver = DriverFactory.getWebDriver();
			msg = driver.switchTo().alert().getText()
		} catch (Exception e){

		}
		return msg
	}


	/**Altera aba para pagina do download e fecha o mesmo
	 * @return
	 */
	public static AlteraAbA(){
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		String parent =  it.next();
		String child =it.next();
		String child2 = it.next();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
		driver.switchTo().window(child2);
		driver.close();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
	}


	public static AlteraAbaApoliceBoleto(){
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		String parent =  it.next();
		String child =it.next();
		String child2 = it.next();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
		driver.switchTo().window(child2);
		driver.close();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
	}


	/**
	 * Retorna para produção sinistro Finalizando 2 abas
	 * @return
	 */
	public static AlteraProducaoSinistroDesbloqueio(){
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		String parent =  it.next();
		String child =it.next();
		String child2 =it.next();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
		driver.switchTo().window(child2);
		driver.close();
		driver.switchTo().window(child);
		driver.close();
		driver.switchTo().window(parent);
	}

	public static AlteraProducaoSinistroDesbloqueioGeral(){
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		String parent =  it.next();
		String child =it.next();
		String child2 =it.next();
		String child3 =it.next();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
		driver.switchTo().window(child2);
		driver.switchTo().window(child3);
		driver.close();
		driver.switchTo().window(child2);
		driver.close();
		driver.switchTo().window(child);
		driver.close();
		driver.switchTo().window(parent);
	}


	/**Altera aba para pagina do download e fecha o mesmo
	 * @return
	 */
	public static AlteraAbDownloadAuto(){
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		String parent =  it.next();
		String child =it.next();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
		driver.close();
		driver.switchTo().window(parent);
	}



	/**Altera aba para pagina do download e fecha o mesmo
	 * @return
	 */
	public static AlteraAbDownloadResidencia(){
		WebUI.delay(5);
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		String parent =  it.next();
		String child =it.next();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
	}

	/**Altera aba para pagina do download e fecha o mesmo
	 * @return
	 */
	public static FecharAbDownloadResidencia(){
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		String parent =  it.next();
		String child =it.next();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
		driver.close();
		driver.switchTo().window(parent);
	}


	public static fecharJanela(){
		WebDriver driver = DriverFactory.getWebDriver();
		driver.close();
	}


	public static fecharJanelaAtual(int qtd){
		WebUI.delay(2);
		String x;
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		for(int i=0; i<=qtd; i++){
			x = i.toString();
			x = it.next();
			driver.switchTo().window(x);
			if(i==qtd){
				driver.close();
			}
		}
	}



	/**preencheXPATH
	 * Preenche INPUT RECEBENDO POR PARAMETRO
	 * @param xpath
	 * @param texto
	 * @param delay
	 * @return
	 * @author T_dvlopes
	 */
	public static preencheXpath(String xpath, String texto, int delay){
		if(delay >0){
			WebUI.delay(delay);
		}
		WebDriver driver = DriverFactory.getWebDriver();
		WebElement element = driver.findElement(By.xpath(xpath));
		element.sendKeys(texto);
	}


	/**
	 * @author be54955
	 * @param xpath
	 * @param texto
	 * @param delay
	 * @return
	 */
	public static preencheXpathInframe(String xpath, String texto, int delay){
		try{
			WebUI.delay(delay);
			WebDriver driver = DriverFactory.getWebDriver();
			driver.switchTo().frame("appArea")
			WebElement element = driver.findElement(By.xpath(xpath));
			element.sendKeys(texto);

		}catch (Exception e){
			System.err.println("Falha no Preencher Xpath");
		}
	}


	/**
	 * @param xpath
	 * @param texto
	 * @param delay
	 * @return
	 */
	public static preencheXpathSetText(String xpath, String texto, int delay){
		try{
			WebUI.delay(delay);
			WebDriver driver = DriverFactory.getWebDriver();
			WebElement element = driver.findElement(By.xpath(xpath));
			element.click();
			println	texto;
			element.sendKeys(texto);
		}catch (Exception e){
			System.err.println("Falha no Preencher Xpath");
			WebUI.closeBrowser();
		}
	}

	/**
	 * @author t_dvlopes
	 * @param xpath
	 * @param delay
	 * @param qtd
	 * @return
	 */
	public static deletaCampoXpath(String xpath,int delay, int qtd){
		try{
			WebUI.delay(delay);
			WebDriver driver = DriverFactory.getWebDriver();
			WebElement element = driver.findElement(By.xpath(xpath));
			element.sendKeys(Keys.DELETE);
			for(int i = 0; i<= qtd;){
				element.sendKeys(Keys.BACK_SPACE);
				i++
			}
		}catch (Exception e){
			System.err.println("Falha no deletar Xpath");
		}
	}


	/**
	 * LimpaCompletamente o campo
	 * 
	 * @author t_dvlopes	 * 
	 * @param xpath
	 * @param delay
	 * @param qtd
	 * @return
	 */
	public static limpaCampo(String xpath,int delay){
		try{
			if(delay>0){
				WebUI.delay(delay);
			}
			WebDriver driver = DriverFactory.getWebDriver();
			WebElement element = driver.findElement(By.xpath(xpath));
			element.clear();
		}catch (Exception e){
			System.err.println("Falha no limpar campo Xpath");
		}
	}


	/**recuperaValor
	 *
	 *
	 * @param xpath
	 * @param atributo
	 * @param delay
	 * @return
	 */
	public static String recuperaValorInframe(String xpath, String atributo, int delay){
		try{
			String atribut = atributo
			WebUI.delay(delay);
			WebDriver driver = DriverFactory.getWebDriver();
			driver.switchTo().frame("appArea")
			WebElement element = driver.findElement(By.xpath(xpath));
			String valor = element.getAttribute(atribut);
			return valor;
		}catch (Exception e){
		}
	}

	/**recuperaValor
	 * 
	 * 
	 * @param xpath
	 * @param atributo
	 * @param delay
	 * @return
	 */
	public static String recuperaValor(String xpath, String atributo, int delay){
		try{
			String atribut = atributo
			if(delay>0){
				WebUI.delay(delay);
			}
			WebDriver driver = DriverFactory.getWebDriver();
			WebElement element = driver.findElement(By.xpath(xpath));
			String valor = element.getAttribute(atribut);
			System.out.println("Valor recupetado ["+valor+"]")
			return valor;
		}catch (Exception e){
			//System.out.println(e)
			return 'atributo nao encontrado'
		}
	}



	public static String recuperaValorAgcs(String xpath, String atributo, int delay){
		try{
			String atribut = atributo
			WebUI.delay(delay);
			WebDriver driver = DriverFactory.getWebDriver();
			WebElement element = driver.findElement(By.xpath(xpath));
			String valor = element.getAttribute(atribut);
			return valor;
		}catch (Exception e){
			WebDriver driver = DriverFactory.getWebDriver();
			clickXpath('//*[@id="botonNueva"]', 1);
			WebUI.delay(5);
			WebElement element = driver.findElement(By.xpath('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]'));
			String valor = element.getAttribute('textContent');
			return valor;
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static commandTAB(String xpath){
		try{
			WebDriver driver = DriverFactory.getWebDriver()
			WebElement Element = driver.findElement(By.xpath(xpath))
			Element.sendKeys(Keys.TAB)
		} catch (Exception e){
			finalizar();
		}
	}

	//INICIA BROWSER

	/**Retorna URL DESEJADA PARA INCIAR BROWSER
	 * @param url
	 * @return
	 * @author T_dvlopes
	 */
	public static String retornaURL(String url){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('MatrizAceitação', 'https://wwwfdt.br.intrallianz.com/drlf01/web/azb-intranet/login-sso');
		urls.put('MatrizProdução', 'https://www.br.intrallianz.com/drlf01/web/azb-intranet');
		urls.put('AllianznetProdução', 'https://www.allianznet.com.br/ngx-epac/public/home');
		urls.put('IntranetProdução', 'https://www.br.intrallianz.com/ngx-intranet/public/home');
		urls.put('IntranetSinistro', 'http://crivo_reports.intrallianz.es/#/login');
		urls.put('IntranetTransacional', 'https://wwwi.br.intrallianz.com/ngx-intranet/public/home');
		return urls.get(url.toString());
	}

	/**Inicia Brwoser Recebendo link por parametro  
	 * @author T_dvlopes
	 * @param link
	 */
	public static void abreBrowser(String url){
		WebUI.openBrowser('');
		WebUI.delay(15);
		WebUI.maximizeWindow();
		WebUI.navigateToUrl(retornaURL(url));
	}

	/**Realiza Login TelaMatriz recebendo por parametro:
	 * @param user
	 * @param password
	 * 
	 * 
	 * @author T_dvlopes
	 */
	public static void realizaLoginTelaMatriz(String user, String password){

		System.err.println("User ["+user+"] Pwr ["+password+"]")

		try{
			WebUI.click(findTestObject('PageBackoffice/Page_Inicio/a_Login'));
			WebUI.setText(findTestObject('PageBackoffice/Page_Login SSO/input_Usurio__58_login'), user);
			WebUI.setText(findTestObject('PageBackoffice/Page_Login SSO/input_Senha__58_password'), password);
			WebUI.click(findTestObject('PageBackoffice/Page_Login SSO/input_Senha_botonSubmitFormulario'));
		}catch(Exception e)
		{
			System.err.println('Falha ao realizar Login "TELA MARIZ PRODUÇÃO"');
			finalizar();
		}
	}

	/**Realiza Login TelaMatriz recebendo por parametro:
	 * @param user
	 * @param password
	 *
	 *
	 * @author T_dvlopes
	 */
	public static void realizaLoginTelaAllianzNet(String user, String password){
		System.err.println("User ["+user+"] Pwr ["+password+"]")
		try{
			preencheXpathSetText('//*[@id="nx-input-0"]', user, 20);
			preencheXpathSetText('//*[@id="nx-input-1"]', password, 1);
			clickXpath('/*/body/div/div/app-root/app-public/app-public-home/div/div/div/app-login/div/form/div[2]/div/button/span',0);
		}catch(Exception e)
		{
			System.err.println('Falha ao realizar Login "TELA AllianzNet PRODUÇÃO"');
			finalizar();
		}
	}



	/**Realiza Login TelaMatriz recebendo por parametro:
	 * @param user
	 * @param password
	 *
	 *
	 * @author T_dvlopes
	 */
	public static void realizaLoginCrivo(String user, String password){
		System.err.println("User ["+user+"] Pwr ["+password+"]");
		try{
			preencheXpathSetText('/html/body/main/div[2]/div[2]/ng-view/div/div/div/div/form/input[1]', user, 20);
			preencheXpathSetText('/html/body/main/div[2]/div[2]/ng-view/div/div/div/div/form/input[2]', password, 1);
			clickXpath('/html/body/main/div[2]/div[2]/ng-view/div/div/div/div/form/button',0);
		}catch(Exception e)
		{
			System.err.println('Falha ao realizar Login "TELA Crivo"');
			finalizar();
		}
	}


	/**Realiza Login Intranet recebendo por parametro:
	 * @param user
	 * @param password
	 *
	 *
	 * @author T_dvlopes
	 */
	public static void realizaLoginTelaIntraNetProducao(String user, String password){
		System.err.println("User ["+user+"] Pwr ["+password+"]")
		try{
			preencheXpathSetText('//*[@id="nx-input-0"]', user, 20);
			preencheXpathSetText('//*[@id="nx-input-1"]', password, 1);
			clickXpath('//*/div/button',0);
		}catch(Exception e)
		{
			System.err.println('Falha ao realizar Login "TELA AllianzNet PRODUÇÃO"');
			finalizar();
		}
	}



	/**
	 * @author ALZ_ServiceQA
	 * @param download
	 */
	public static void downloadPDFBoleto(String download){
		//Realiza Download Apolice
		for (int i = 0; i < 7; i++) {
			WebDriver driver = DriverFactory.getWebDriver();
			String boletoDownload = driver.findElement(By.xpath('//*[@id="listDatos_1_'+i+'"]')).getText();
			if(boletoDownload.equals(download)){
				driver.findElement(By.xpath('//*[@id="listDatos_1_'+i+'"]')).click();
				WebUI.delay(15);
				enviarBoleto = true;

				break;
			}else{
				println 'NÃO ENVIA BOLETO'
				enviarBoleto = false;
			}
		}
	}

	/** Realiza o Switch `TROCA` de janela do navegador
	 * @param nomeJanela
	 * @author T_dvlopes
	 */
	public static void trocaJanela(String nomeJanela){
		WebUI.delay(2);
		WebUI.switchToWindowTitle(nomeJanela);
	}

	/**
	 * @author ALZ_ServiceQA
	 */
	public static void fecharJanelaDownload(){
		clickXpath('//*[@id="lnkTopLink_Close"]', 2);
		fechaAlerta();
	}


	/**
	 * @author be54955
	 * @param d
	 * @return
	 */
	public static boolean campoVazio(String d){
		boolean pj = false
		if(d.length()==0){
			pj = true
		}
		System.out.println("Campo vazio ["+pj+"]")
		return pj
	}


	/////////////////////////////
	//HASMAPS//
	///////////////////////////



	/**seleciona Opcao Processo
	 *recebendo por parametro a opção desejada
	 *@author T_dvlopes
	 */
	public static void selecionarOpcaoProcesso(String processo){
		//		try{
		clickXpath('//*[@id="tipoReferencia"]', 1);

		Map<String, String> menu = new HashMap<String, String>();
		menu.put("A-Mediador",'//*[@id="tipoReferencia"]/option[1]');
		menu.put("P-APOLICE",'//*[@id="tipoReferencia"]/option[2]');
		menu.put("S-Sinistro",'//*[@id="tipoReferencia"]/option[3]');
		menu.put("C-Cliente",'//*[@id="tipoReferencia"]/option[4]');
		menu.put("AS-Agente susep",'//*[@id="tipoReferencia"]/option[5]');
		menu.put("B-Corretores resseguro",'//*[@id="tipoReferencia"]/option[6]');
		menu.put("D-Companhias de co-seguro cedido",'//*[@id="tipoReferencia"]/option[7]');
		menu.put("E-Empregados",'//*[@id="tipoReferencia"]/option[8]');
		menu.put("F-Facultativos de resseguro",'//*[@id="tipoReferencia"]/option[9]');
		menu.put("G-Delegação/escritório",'//*[@id="tipoReferencia"]/option[10]');
		menu.put("H-Companhias de resseguro",'//*[@id="tipoReferencia"]/option[11]');
		menu.put("I-Imóveis",'//*[@id="tipoReferencia"]/option[12]');
		menu.put("J-Agentes financeiros",'//*[@id="tipoReferencia"]/option[13]');
		menu.put("K-Contrario siniestro",'//*[@id="tipoReferencia"]/option[14]');
		menu.put("L-Pessoa sinistrada",'//*[@id="tipoReferencia"]/option[15]');
		menu.put("LO-Lotes",'//*[@id="tipoReferencia"]/option[16]');
		menu.put("M-Informações de resseguro",'//*[@id="tipoReferencia"]/option[17]');
		menu.put("N-Contratos de resseguro",'//*[@id="tipoReferencia"]/option[18]');
		menu.put("O-Bancos resseguro",'//*[@id="tipoReferencia"]/option[19]');
		menu.put("Q-Contratos companhia",'//*[@id="tipoReferencia"]/option[20]');
		menu.put("R-Cessação resseguro",'//*[@id="tipoReferencia"]/option[21]');
		menu.put("T-Prestadores/oficinas",'//*[@id="tipoReferencia"]/option[22]');
		menu.put("U-Formação mediadores",'//*[@id="tipoReferencia"]/option[23]');
		menu.put("V-Potenciais clientes",'//*[@id="tipoReferencia"]/option[24]');
		menu.put("W-Não aplicável",'//*[@id="tipoReferencia"]/option[25]');
		menu.put("X-Não definida",'//*[@id="tipoReferencia"]/option[26]');
		menu.put("Y-Financiero",'//*[@id="tipoReferencia"]/option[27]');
		menu.put("Z-Potencial mediador",'//*[@id="tipoReferencia"]/option[28]');
		menu.put("2-Contactos",'//*[@id="tipoReferencia"]/option[29]');
		menu.put("3-Cliente potencial",'//*[@id="tipoReferencia"]/option[30]');
		menu.put("6-Cotação",'//*[@id="tipoReferencia"]/option[31]');

		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, menu.get(processo)));
		WebUI.delay(1);
	}


	/**Navega pelo menu Matriz recebendo Opção por parametro
	 * @param menuMatriz
	 * @return
	 * @author T_dvlopes
	 */
	public static clicaNoMenuTelaMatriz(String menuMatriz){
		try{
			WebUI.delay(5);
			Map<String, String> menu = new HashMap<String, String>();
			menu.put("Producao & Sinistros",'//*[@id="100"]');
			menu.put("Gestão & Marketing","//*[@id='200']");
			menu.put("Consultas & Estatísticas", "//*[@id='300']");
			menu.put("Serviços Corporativos","//*[@id='400']");
			flexibleTestObject = new TestObject();
			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, menu.get(menuMatriz)));
		} catch (Exception e ){
			System.err.println('Falha ao realizar Click "MENU TELA MATRIZ"');
			finalizar();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param menuMatriz
	 * @return
	 */
	public static clicaNoMenuTelaMatriz2(String menuMatriz){
		try{
			WebUI.delay(10);
			Map<String, String> menu = new HashMap<String, String>();
			//menu.put("Producao & Sinistros",'//*[@id="100"]');
			menu.put("Gestão & Marketing","//*[@id='100']");
			menu.put("Consultas & Estatísticas", "//*[@id='200']");
			menu.put("Serviços Corporativos","//*[@id='300']");
			flexibleTestObject = new TestObject();
			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, menu.get(menuMatriz)));
		} catch (Exception e ){
			System.err.println('Falha ao realizar Click "MENU TELA MATRIZ"');
			finalizar();
		}
	}

	/**clicarSubMenuTelaMatriz
	 * Naveja pelo Sub Menu Matriz recebendo Opão por parametro
	 * @return
	 * @author T_dvlopes
	 */
	public static clicaNoSubMenuTelaMatriz(String sSubMenus){
		WebUI.delay(5);
		try{
			Map<String, String> submenu = new HashMap<String, String>();
			//Manutenção
			submenu.put("Manutenção Geral", "//*[@id='500']");

			//Produção E Sinistros
			submenu.put("Produção Diversos", '//*[@id="110"]');
			submenu.put("Gestão Carteira", '//*[@id="120"]');
			submenu.put("Sinistros", '//*[@id="130"]');
			submenu.put("Produção Suporte", '//*[@id="140"]');

			//Gestão e Marketing
			submenu.put("Mediadores/Clientes", '//*[@id="210"]');
			submenu.put("Adiministração", '//*[@id="220"]');
			submenu.put("Administração Suporte", '//*[@id="230"]');
			submenu.put("Reaseguro", '//*[@id="240"]');
			submenu.put("AllianzNet", '//*[@id="250"]');

			//Consultas e Estatisticas
			submenu.put("Consultas", "//*[@id='310']");
			submenu.put("Arquivo Digital", "//*[@id='320']");
			submenu.put("Estatistica", "//*[@id='330']");

			//Serviços Corporativos
			submenu.put("Serviços Gerais", '//*[@id="410"]');

			//Manutenção Geral
			submenu.put("Manutenção Produção", '//*[@id="510"]');
			submenu.put("Manutenção Sinistros", '//*[@id="520"]');
			submenu.put("Manutenção Administração", '//*[@id="530"]');
			submenu.put("Manutenção GE GO e Imagem", '//*[@id="540"]');

			flexibleTestObject = new TestObject();
			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, submenu.get(sSubMenus)));
		} catch (Exception e ){
			System.err.println('Falha ao realizar Click "SUB MENU TELA MATRIZ"');
			finalizar();
		}
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param sSubMenus
	 * @return
	 */
	public static clicaNoSubMenuTelaMatriz2(String sSubMenus){
		WebUI.delay(5);
		try{
			Map<String, String> submenu = new HashMap<String, String>();
			//Manutenção
			submenu.put("Manutenção Geral", "//*[@id='500']");

			//Produção E Sinistros
			submenu.put("Produção Diversos", '//*[@id="110"]');
			submenu.put("Gestão Carteira", '//*[@id="120"]');
			submenu.put("Sinistros", '//*[@id="130"]');
			submenu.put("Produção Suporte", '//*[@id="140"]');

			//Gestão e Marketing
			submenu.put("Mediadores/Clientes", '//*[@id="210"]');
			submenu.put("Adiministração", '//*[@id="220"]');
			submenu.put("Administração Suporte", '//*[@id="230"]');
			submenu.put("Reaseguro", '//*[@id="240"]');
			submenu.put("AllianzNet", '//*[@id="250"]');

			//Consultas e Estatisticas
			submenu.put("Consultas", "//*[@id='210']");
			submenu.put("Arquivo Digital", "//*[@id='220']");
			submenu.put("Estatistica", "//*[@id='230']");

			//Serviços Corporativos
			submenu.put("Serviços Gerais", '//*[@id="410"]');

			//Manutenção Geral
			submenu.put("Manutenção Produção", '//*[@id="510"]');
			submenu.put("Manutenção Sinistros", '//*[@id="520"]');
			submenu.put("Manutenção Administração", '//*[@id="530"]');
			submenu.put("Manutenção GE GO e Imagem", '//*[@id="540"]');

			flexibleTestObject = new TestObject();
			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, submenu.get(sSubMenus)));
		} catch (Exception e ){
			System.err.println('Falha ao realizar Click "SUB MENU TELA MATRIZ"');
			finalizar();
		}
	}

	/**clicarSubOpcao
	 * Navega subOpçao recebendo Opção por parametro
	 * @return
	 * @author T_dvlopes
	 */
	public static clicaNaSubOpcao(String subMenus){
		WebUI.delay(5);
		try{
			Map<String, String> subOpcao = new HashMap<String, String>();

			//Produção e Sinistros
			//Produção Diversos
			//*[@id="topicNav"]/li[3]/a
			subOpcao.put("Produção", '//*[@id="topicNav"]/li[3]/a');
			//subOpcao.put("Produção", '//*[@id="topicNav"]/li[5]/a');
			subOpcao.put("2ª Via Cartões", '//*[@id="topicNav"]/li[1]/a');
			subOpcao.put("Agronegócios", '//*[@id="topicNav"]/li[2]/a');
			subOpcao.put("Alterações cadastrais", '//*[@id="topicNav"]/li[3]/a');
			subOpcao.put("Automóvel", '//*[@id="topicNav"]/li[4]/a');
			subOpcao.put("Automóvel-Ferramentas", '//*[@id="topicNav"]/li[5]/a');
			subOpcao.put("Faturamento Saúde AZB", '//*[@id="topicNav"]/li[6]/a');
			subOpcao.put("HUB de Serviços", '//*[@id="topicNav"]/li[7]/a');
			subOpcao.put("Manutenção VIDA", '//*[@id="topicNav"]/li[8]/a');
			subOpcao.put("Risk Grading", '//*[@id="topicNav"]/li[9]/a');
			subOpcao.put("Saúde - Fatura em Excel", '//*[@id="topicNav"]/li[10]/a');
			subOpcao.put("Saúde - Menu Saúde AZB", '//*[@id="topicNav"]/li[11]/a');
			subOpcao.put("Saúde - Reajuste de Premio", '//*[@id="topicNav"]/li[12]/a');
			subOpcao.put("Saúde - Renovação Mensal", '//*[@id="topicNav"]/li[13]/a');
			subOpcao.put("Transportes", '//*[@id="topicNav"]/li[14]/a');
			subOpcao.put("Faturamento de frotas. Parc. Única", '//*[@id="topicNav"]/li[15]/a');
			subOpcao.put("Alter. domiciliação Cobrança", '//*[@id="topicNav"]/li[16]/a');
			//Fluxo cotaçoes eseciais

			//
			subOpcao.put("Cotações especiais", '//*[@id="topicNav"]/li[3]/a');
			//subOpcao.put("Cotações especiais", '//*[@id="topicNav"]/li[6]/a');
			subOpcao.put("Desbloqueáveis Produção", '//*[@id="topicNav"]/li[18]/a');
			subOpcao.put("Criação de Planos", '//*[@id="topicNav"]/li[19]/a');
			subOpcao.put("Análise de Inspeções", '//*[@id="topicNav"]/li[20]/a');
			//subOpcao.put("Produção", '//*[@id="topicNav"]/li[21]/a');
			subOpcao.put("Cancelamento", '//*[@id="topicNav"]/li[22]/a');

			//Produção e Sinistros
			//Gestão Carteiras
			subOpcao.put("Tabela Depart.-Área", '//*[@id="topicNav"]/li[1]/a');
			subOpcao.put("Gestão renovações", '//*[@id="topicNav"]/li[2]/a');
			subOpcao.put("Manutenção Tarifas", '//*[@id="topicNav"]/li[3]/a');
			subOpcao.put("Renovação Frota", '//*[@id="topicNav"]/li[4]/a');
			subOpcao.put("Mant. revisión cartera", '//*[@id="topicNav"]/li[5]/a');

			//Produção e Sinistros
			//Sinistros
			subOpcao.put("Conta Corrente", '//*[@id="topicNav"]/li[1]/a');
			subOpcao.put("Fornecimento de Peças", '//*[@id="topicNav"]/li[2]/a');
			subOpcao.put("Gestão IVS", '//*[@id="topicNav"]/li[3]/a');
			subOpcao.put("Gestão de Verificações", '//*[@id="topicNav"]/li[4]/a');
			subOpcao.put("Gestão Precobros", '//*[@id="topicNav"]/li[5]/a');
			//subOpcao.put("Liquidaciones Profesionales", '//*[@id="topicNav"]/li[6]/a');
			//subOpcao.put("Liquidaciones Profesionales", '//*[@id="topicNav"]/li[17]/a');
			subOpcao.put("Liquidaciones Profesionales", '//*[@id="topicNav"]/li[4]/a');


			//Produção e Sinistros
			//Produção Suporte
			subOpcao.put("Alteração Apólices", '//*[@id="topicNav"]/li[1]/a');
			subOpcao.put("Gestor de Cargas Masivas", '//*[@id="topicNav"]/li[2]/a');
			subOpcao.put("Manutenção seguradoras", '//*[@id="topicNav"]/li[3]/a');
			subOpcao.put("Oficina de Produtos", '//*[@id="topicNav"]/li[4]/a');
			subOpcao.put("Radar Live", '//*[@id="topicNav"]/li[5]/a');
			subOpcao.put("Duplicados Recibos", '//*[@id="topicNav"]/li[6]/a');
			subOpcao.put("Mantenimiento IPC", '//*[@id="topicNav"]/li[7]/a');
			subOpcao.put("Alteração Frotas", '//*[@id="topicNav"]/li[8]/a');
			subOpcao.put("Mant. excepciones agente Allianz", '//*[@id="topicNav"]/li[9]/a');
			subOpcao.put("Condiçoes Frotas", '//*[@id="topicNav"]/li[10]/a');

			//Gestão e Marketing
			//Mediadores/Clientes
			subOpcao.put("Acompanhamento de Projectos", '//*[@id="topicNav"]/li[1]/a');
			subOpcao.put("Black List", '//*[@id="topicNav"]/li[2]/a');
			subOpcao.put("Gestão Solicitações Extrações", '//*[@id="topicNav"]/li[3]/a');
			subOpcao.put("Agenda comercial", '//*[@id="topicNav"]/li[4]/a');
			subOpcao.put("Gestão Corretores", '//*[@id="topicNav"]/li[5]/a');
			subOpcao.put("Gestão Clientes", '//*[@id="topicNav"]/li[1]/a');

			//Gestão e Marketing
			//Administração
			subOpcao.put("BDPO", '//*[@id="topicNav"]/li/a');

			//Gestão e Marketing
			//Administração Suporte
			subOpcao.put("Adicionais", '//*[@id="topicNav"]/li[1]/a');
			subOpcao.put("Embargos SEA", '//*[@id="topicNav"]/li[2]/a');

			//Gestão e Marketing
			//Reaseguro
			subOpcao.put("Resseguro", '//*[@id="topicNav"]/li/a');

			//Gestão e Marketing
			//AllianzNet
			subOpcao.put("e-Contact Allianz", '//*[@id="topicNav"]/li/a');

			//Consultas e Estatisticas
			//Consultas
			subOpcao.put("Central de Bonus", '//*[@id="topicNav"]/li[1]/a');
			subOpcao.put("Consulta de Vistoria", '//*[@id="topicNav"]/li[2]/a');
			subOpcao.put("Consultas Gerais", '//*[@id="topicNav"]/li[3]/a');
			//subOpcao.put("Consultas Gerais", '//*[@id="topicNav"]/li[2]/a');
			subOpcao.put("Gestão de Parcelas", '//*[@id="topicNav"]/li[4]/a');
			subOpcao.put("Listagens de Recibos", '//*[@id="topicNav"]/li[5]/a');
			subOpcao.put("Equivalência de Chaves", '//*[@id="topicNav"]/li[6]/a');
			subOpcao.put("Listas Restritivas", '//*[@id="topicNav"]/li[7]/a');



			//Consultas e Estatisticas
			//Arquivo Digital

			subOpcao.put("Ficha de Gestão", '//*[@id="topicNav"]/li[1]/a');
			subOpcao.put("Pre-Indexação SEA", '//*[@id="topicNav"]/li[2]/a');
			subOpcao.put("GO!", '//*[@id="topicNav"]/li[3]/a');
			subOpcao.put("Consulta documentación Pre-Indexada SEA", '//*[@id="topicNav"]/li[4]/a');

			//Consultas e Estatisticas
			//Estatistica
			subOpcao.put("Dia-Allz", '//*[@id="topicNav"]/li[1]/a');
			subOpcao.put("Manutenção Dia-Allz", '//*[@id="topicNav"]/li[2]/a');
			subOpcao.put("Mantenimiento Variables DiaAllz", '//*[@id="topicNav"]/li[3]/a');

			//Serviços Corporativos
			//Serviços Gerais
			subOpcao.put("Conectividade", '//*[@id="topicNav"]/li[1]/a');
			subOpcao.put("Controle de Impressão", '//*[@id="topicNav"]/li[2]/a');
			subOpcao.put("Link de Impressão", '//*[@id="topicNav"]/li[3]/a');
			subOpcao.put("Serviços de Impressão", '//*[@id="topicNav"]/li[4]/a');
			subOpcao.put("Pedido de Impressos (SEA)", '//*[@id="topicNav"]/li[5]/a');

			flexibleTestObject = new TestObject();
			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, subOpcao.get(subMenus)));
		} catch (Exception e ){
			System.err.println('Falha ao realizar Click "SUB OPÇÃO TELA MATRIZ"');
			finalizar();
		}
	}

	/**
	 * (selecionar Tipo Seguro)
	 * 
	 * 
	 * Seguro Novo
	 * Endosso
	 * Renovação Interna com Sinistro
	 * Renovação Externa sem Sinistro
	 * Renovação Externa com Sinistro
	 * Renovação Externa Especial
	 * Renovação Interna sem Sinistro
	 * 
	 * @param processo
	 */
	public static void selecionarTipoSeguro(String processo){
		clickXpath('//*[@id="tipoSeguro"]', 2);
		Map<String, String> seguro = new HashMap<String, String>();
		seguro.put("Seguro Novo",'//*[@id="tipoSeguro"]/option[2]');
		seguro.put("Endosso",'//*[@id="tipoSeguro"]/option[3]');
		seguro.put("Renovação Interna com Sinistro",'//*[@id="tipoSeguro"]/option[4]');
		seguro.put("Renovação Externa sem Sinistro",'//*[@id="tipoSeguro"]/option[5]');
		seguro.put("Renovação Externa com Sinistro",'//*[@id="tipoSeguro"]/option[6]');
		seguro.put("Renovação Externa Especial",'//*[@id="tipoSeguro"]/option[7]');
		seguro.put("Renovação Interna sem Sinistro",'//*[@id="tipoSeguro"]/option[8]');
		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, seguro.get(processo)));
	}

	/**selecionarAgrupamentoRamo
	 * 
	 * @param ramo
	 */
	public static void selecionarAgrupamentoRamo(String ramo){
		clickXpath('//*[@id="agrupacionRamo"]', 2);

		String xpath = '//*[@id="agrupacionRamo"]'
		String tag = "option";
		listOfElements = listaDeElementos(xpath,tag)
		WebUI.delay(2);
		for(int j = 0; j < listOfElements.size(); j++){
			if(listOfElements[j].getText().contains(ramo.trim())){
				System.err.println(j+' - '+listOfElements[j].getText())
				listOfElements[j].click()
				break
			}
		}
	}

	public static switchAgrupamentoRamo(String agrupamentoRamo, String ramoModalidade){
		try{
			switch (agrupamentoRamo.trim()) {
				case 'Acidentes Coletivo':
					selecionarRamoModalidadeAcidentesColetivo(ramoModalidade);
					println "Acidentes Coletivo"
					break;
				case 'Acidentes Individual':
					selecionarRamoModalidadeAcidentIndividual(ramoModalidade);
					println "Acidentes Individual"
					break;
				case 'Agricola Equipamento':
					selecionarRamoModalidadeAgronegocios(ramoModalidade);
					println "Agricola Equipamento"
					break;
				case 'Auto Frota':
					selecionarRamoModalidadeAutoFrota(ramoModalidade);
					println "Auto Frota"
					break;
				case 'AGCS':
					selecionarRamoModalidadeAgcs(ramoModalidade);
					println "AGCS"
					break;

				case 'Condomínio':
					selecionarRamoModalidadeCondomio(ramoModalidade);
					println "Condomínio"
					break;
				case 'Empresa/Master':
					selecionarRamoModalidadeEmpresaMaster(ramoModalidade);
					println "Empresa/Master"
					break;
				case 'Engenharia':
					selecionarRamoModalidadeEngenharia(ramoModalidade);
					println "Engenharia"
					break;
				case 'Garantia':
					selecionarRamoModalidadeGarantia(ramoModalidade);
					println "Garantia";
					break;
				case 'Outros Industriais':
					selecionarRamoModalidadeOutrosIndustriais(ramoModalidade);
					println "Outros Industriais"
					break;
				case 'Outros P&C':
					selecionarRamoModalidadeOutrosPeC(ramoModalidade);
					println "Outros P&C"
					break;
				case 'Residencia':
					selecionarRamoModalidadeResidencia(ramoModalidade);
					println "Residencia"
					break;
				case 'RC':
					selecionarRamoModalidadeRc(ramoModalidade);
					println "RC"
					break;
				case 'RD':
					selecionarRamoModalidadeRd(ramoModalidade);
					println "RD"
					break;
				case 'RN/RO/Patrimoniais':
					selecionarRamoModalidadeRnRoPatrimoniais(ramoModalidade);
					println "RN/RO/Patrimoniais"
					break;
				case 'Saúde Administrados':
					selecionarRamoModalidade(ramoModalidade);
					println "Saúde Administrados"
					break;
				case 'Saúde GG':
					selecionarRamoModalidadeSaudeGG(ramoModalidade);
					println "Saúde GG"
					break;
				case 'Saúde PME':
					selecionarRamoModalidadeSaudePME(ramoModalidade);
					println "Saúde PME"
					break;
				case 'Transportes/Náutica':
					selecionarRamoModalidadeTransporteNautica(ramoModalidade);
					println "Transportes/Náutica"
					break;
				case 'Vida Coletivo':
					selecionarRamoModalidadeVidaColetivo(ramoModalidade);
					println "Vida Coletivo"
					break;
				case 'Vida Individual':
					selecionarRamoModalidadeVidaIndividual(ramoModalidade);
					println "Vida Individual"
					break;
			}

		} catch (Exception e){
			e
			return null;
		}
	}



	public static void selecionarRamoModalidadeAcidentesColetivo(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Acidente coletivo
		modalidade.put('102','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('102','//*[@id="ramoModalidad"]/option[3]');
		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}


	public static void selecionarRamoModalidadeAcidentIndividual(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Acidente Individual
		modalidade.put('101','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('101','//*[@id="ramoModalidad"]/option[3]');
		modalidade.put('116','//*[@id="ramoModalidad"]/option[4]');

		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}

	public static void selecionarRamoModalidadeAgronegocios(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Agronegocios
		modalidade.put('601','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('602','//*[@id="ramoModalidad"]/option[3]');
		modalidade.put('603','//*[@id="ramoModalidad"]/option[4]');
		modalidade.put('604','//*[@id="ramoModalidad"]/option[5]');
		modalidade.put('605','//*[@id="ramoModalidad"]/option[6]');
		modalidade.put('606','//*[@id="ramoModalidad"]/option[7]');
		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}

	public static void selecionarRamoModalidadeAutoFrota(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Auto Frota
		modalidade.put('1221','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('1222','//*[@id="ramoModalidad"]/option[3]');
		modalidade.put('1223','//*[@id="ramoModalidad"]/option[4]');
		modalidade.put('1224','//*[@id="ramoModalidad"]/option[5]');
		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}

	public static void selecionarRamoModalidadeAgcs(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		String xpath = '//*[@id="ramoModalidad"]'
		String tag = "option";
		listOfElements = listaDeElementos(xpath,tag)
		WebUI.delay(2);
		for(int j = 0; j < listOfElements.size(); j++){
			if(listOfElements[j].getText().contains(ramoMod.trim())){
				System.err.println(j+' - '+listOfElements[j].getText())
				listOfElements[j].click()
				break
			}
		}
	}

	public static void selecionarRamoModalidadeCondomio(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();

		//Condomínio
		modalidade.put('2002','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('2002','//*[@id="ramoModalidad"]/option[3]');
		modalidade.put('2002','//*[@id="ramoModalidad"]/option[4]');

		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}

	public static void selecionarRamoModalidadeEmpresaMaster(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Empresa/Master
		modalidade.put('2004','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('2005','//*[@id="ramoModalidad"]/option[3]');

		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}



	public static void selecionarRamoModalidadeEngenharia(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Engenharia
		modalidade.put('1101','//*[@id="ramoModalidad"]/option[2]');
		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}


	public static void selecionarRamoModalidadeGarantia(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Garantia
		modalidade.put('1502','//*[@id="ramoModalidad"]/option[2]');

		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}


	public static void selecionarRamoModalidadeOutrosIndustriais(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Outros Industriais
		modalidade.put('2009','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('2032','//*[@id="ramoModalidad"]/option[3]');

		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}


	public static void selecionarRamoModalidadeOutrosPeC(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Outros P&C
		modalidade.put('2010','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('2010','//*[@id="ramoModalidad"]/option[3]');
		modalidade.put('2010','//*[@id="ramoModalidad"]/option[4]');
		modalidade.put('2011','//*[@id="ramoModalidad"]/option[5]');

		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}


	public static void selecionarRamoModalidadeResidencia(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Residencia
		modalidade.put('2001','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('2003','//*[@id="ramoModalidad"]/option[3]');
		modalidade.put('2003','//*[@id="ramoModalidad"]/option[4]');
		modalidade.put('2003','//*[@id="ramoModalidad"]/option[5]');

		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}


	public static void selecionarRamoModalidadeRc(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//RC
		modalidade.put('1402','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('1440','//*[@id="ramoModalidad"]/option[3]');

		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}

	public static void selecionarRamoModalidadeRd(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//RD
		modalidade.put('1102','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('2006','//*[@id="ramoModalidad"]/option[3]');

		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}


	public static void selecionarRamoModalidadeRnRoPatrimoniais(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//RN/RO/Patrimoniais
		modalidade.put('801','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('801','//*[@id="ramoModalidad"]/option[3]');
		modalidade.put('801','//*[@id="ramoModalidad"]/option[4]');
		modalidade.put('2007','//*[@id="ramoModalidad"]/option[5]');
		modalidade.put('2008','//*[@id="ramoModalidad"]/option[6]');
		modalidade.put('2030','//*[@id="ramoModalidad"]/option[7]');
		modalidade.put('2031','//*[@id="ramoModalidad"]/option[8]');
		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}

	public static void selecionarRamoModalidade(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Saúde Administrados
		modalidade.put('203','//*[@id="ramoModalidad"]/option[3]');
		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}

	public static void selecionarRamoModalidadeSaudeGG(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Saúde GG
		modalidade.put('201','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('204','//*[@id="ramoModalidad"]/option[3]');
		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}

	public static void selecionarRamoModalidadeSaudePME(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Saúde PME
		modalidade.put('202','//*[@id="ramoModalidad"]/option[2]');

		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}

	public static void selecionarRamoModalidadeTransporteNautica(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Transportes/Náutica
		modalidade.put('303','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('304','//*[@id="ramoModalidad"]/option[3]');
		modalidade.put('307','//*[@id="ramoModalidad"]/option[4]');
		modalidade.put('308','//*[@id="ramoModalidad"]/option[5]');
		modalidade.put('309','//*[@id="ramoModalidad"]/option[6]');
		modalidade.put('310','//*[@id="ramoModalidad"]/option[7]');
		modalidade.put('311','//*[@id="ramoModalidad"]/option[8]');
		modalidade.put('312','//*[@id="ramoModalidad"]/option[9]');
		modalidade.put('366','//*[@id="ramoModalidad"]/option[10]');

		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}

	public static void selecionarRamoModalidadeVidaColetivo(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Vida Coletivo
		modalidade.put('104','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('104','//*[@id="ramoModalidad"]/option[3]');
		modalidade.put('104','//*[@id="ramoModalidad"]/option[4]');
		modalidade.put('104','//*[@id="ramoModalidad"]/option[5]');
		modalidade.put('104','//*[@id="ramoModalidad"]/option[6]');
		modalidade.put('104','//*[@id="ramoModalidad"]/option[7]');
		modalidade.put('104','//*[@id="ramoModalidad"]/option[8]');
		modalidade.put('105','//*[@id="ramoModalidad"]/option[9]');
		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}


	public static void selecionarRamoModalidadeVidaIndividual(String ramoMod){
		clickXpath('//*[@id="ramoModalidad"]', 2);
		Map<String, String> modalidade = new HashMap<String, String>();
		//Vida Individual
		modalidade.put('103','//*[@id="ramoModalidad"]/option[2]');
		modalidade.put('115','//*[@id="ramoModalidad"]/option[3]');
		flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, modalidade.get(ramoMod)));
	}


	/////////////////////////////
	//LEITOR PDF//
	///////////////////////////


	static String pathFileValue;
	static StringBuffer strbffr ;

	/**
	 * @author ALZ_ServiceQA
	 * @author T_DCDias
	 * @return
	 */
	public static deletaArquivoPdf(String path){
		try{
			pathFileValue = path;
			System.err.println("pathValue ["+pathFileValue+"]");
			boolean result = validaExistenciaDeArquivo(pathFileValue);
			WebUI.delay(5);
			if( result == false){
				deleteFileDiretory(pathFileValue);
			}
			WebUI.delay(5);
		} catch (Exception e){
		}
	}

	public static String validaDownload(String path, String xpath){
		String downloadOK = 'false';
		double kilobytesLIMITE = 200;
		File file = new File(path);
		if (file.exists()) {
			double bytes = file.length();
			double kilobytes = bytes / 1024;
			System.out.println("O tamanho do arquivo é: " + kilobytes +" kilobytes");
			if(kilobytes <= kilobytesLIMITE){
				deletarGerenciaonPdf();
				downloadOK = 'false';
			}else{
				downloadOK = 'true';
				println downloadOK;
			}
		}
		return downloadOK;}


	public static void deletarGerenciaonPdf(){
		String path
		try{
			path = 'C:\\Users\\Public\\GeneracionPdf.PDF';
			deletarPDFGenerico(path);
		} catch (Exception e){}

		try{
			path = 'C:\\Users\\Public\\retrievedoc.PDF';
			deletarPDFGenerico(path);
		} catch (Exception e){}

		try{
			path = 'C:\\Users\\Public\\RenovAuto.PDF';
			deletarPDFGenerico(path);
		} catch (Exception e){}
	}


	public static String validaDownloadTamanho (String path, String xpath) {
		String downloadOK = '';
		File file = new File(path);
		if(cont > 3){
			System.err.println('Falha ao Realizar flux Download "APOLICE"');
			finalizar();
			downloadOK = 'false'
			valueCheck = 'Não enviado - [Erro Ao realizar Download APOLICE]';
		}else {
			if (file.exists()) {
				double bytes = file.length();
				double kilobytes = bytes / 1024;
				System.out.println("O tamanho do arquivo é: " + kilobytes +" kilobytes");
				double kilobytesLIMITE = 50;
				if(kilobytes < kilobytesLIMITE){
					deletarRetrievedoc()
					AlteraAbDownloadResidencia();
					clickXpath(xpath, 2);
					cont ++;
					validaDownloadTamanho(path, xpath);
				}else {
					downloadOK = 'true';
				}

			} else {
				System.out.println("O arquivo não existe");
				AlteraAbDownloadResidencia();
				clickXpathInframe(xpath, 2);
				cont ++;
				validaDownloadTamanho(path, xpath);
			}
		}
		return downloadOK;
	}

	public static void deletarRetrievedoc(){
		String path = 'C:\\Users\\Public\\retrievedoc.PDF';
		deletarPDFGenerico(path);
		path = 'C:\\Users\\Public\\retrievedoc (1).PDF';
		deletarPDFGenerico(path);
	}


	/**
	 * @author Dvlopes
	 * @author T_DCDias
	 * @return
	 */
	public static deletaArquivoGenerico(String path){
		try{
			pathFileValue = path;
			System.err.println("pathValue ["+pathFileValue+"]");
			boolean result = validaExistenciaDeArquivo(pathFileValue);
			WebUI.delay(5);
			if( result == false){
				deleteFileDiretory(pathFileValue);
			}
			WebUI.delay(5);
		} catch (Exception e){
		}
	}



	/**
	 * Localiza PDF FICHERO E DELETA
	 * @author T_dvlopes
	 */
	public static void deletarPDFGenerico(String path){
		try{
			deletaArquivoPdf(path);
		} catch (Exception e) {
			System.err.println('Falha ao deletar PDF"' + e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param caminhoSaida
	 * @throws IOException
	 */
	public static void deletarPasta(String nomePasta) throws IOException {

		try{
			System.err.println(nomePasta);
			String pathValue = 'C:\\Users\\Public\\'+nomePasta+'';
			File diretorio = new File(pathValue);
			String[] arqs;
			File arq;
			int i;

			arqs = diretorio.list();
			for(i=0; i<arqs.length; i++){
				arq = new File(diretorio.getPath(), arqs[i]);
				if(arq.isDirectory()){
					deletarPasta(arq);
				} else {
					arq.delete();
				}
			}
			diretorio.delete();
		} catch (Exception e){}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param nomePasta
	 * @throws IOException
	 */
	public static void deletarPastaExcel(String nomePasta) throws IOException {

		System.err.println(nomePasta);
		String pathValue = nomePasta;
		File diretorio = new File(pathValue);
		String[] arqs;
		File arq;
		int i;
		arqs = diretorio.list();
		for(i=0; i<arqs.length; i++){
			arq = new File(diretorio.getPath(), arqs[i]);
			if(arq.isDirectory()){
				deletarPasta(arq);
			} else {
				arq.delete();
			}
		}
		diretorio.delete();
	}


	/**
	 * @author T_DCDias
	 * @param path
	 * @return
	 */
	public static boolean validaExistenciaDeArquivo(String path){
		boolean resp
		File arquivo = new File(path);
		if(arquivo.exists()){
			System.err.println("[ O ARQUIVO EXISTE ]")
			resp = false
		}else{
			System.err.println("[ O ARQUIVO NÃO EXISTE ]")
			resp = true
		}
		return resp
	}

	/**
	 * @author T_DCDias
	 * @param pathValue
	 * @return
	 */
	public static boolean deleteFileDiretory(String pathValue ){
		try{
			boolean success = (new File(pathValue.toString())).delete();
			if (!success) {
				System.err.println("[ Arquivo ["+pathValue+"] não excluido ! ]")
			} else{
				System.err.println("[ Arquivo ["+pathValue+"] excluido ! ]")
			}

			while(validaExistenciaDeArquivo(pathValue)== false){
				deleteFileDiretory(pathValue)
			}
		} catch (Exception e){
			System.err.println("Não foi possível excluir o arquivo ["+pathValue+"]\n"+e )

		}
	}

	/**
	 * @author T_DCDias
	 * Fecha a instância do Selenium WebDriver e todas os navegadores associados.
	 * @return
	 */
	public static finalizar(){
		WebUI.delay(2);
		alteraJanejaGenerica(0, 1)
		try{
			valueCheck = 'Não enviado';
			WebDriver driver = DriverFactory.getWebDriver();
			SessionId session = ((RemoteWebDriver)driver).getSessionId();
			if(session.toString()!=null){
				WebUI.delay(5);
				WebUI.closeBrowser();
				driver.quit();
			}
		} catch (Exception e){
			System.err.println("\n -[Aplicação finalizada]- \n");
		}
	}

	/**
	 * @return
	 *  @author T_DCDias
	 */
	public static downloadPdfs(String tipoPdf){
		try{
			String xpath = '//*[@id="SectionListContent"]'
			String tag ="td";
			String botao
			WebUI.delay(5)
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){  System.err.println(i+" "+tag+" "+listOfElements.get(i))
				if(listOfElements[i].getText().contains(tipoPdf)){
					botao = tipoPdf
				}
			}
			clickXpath(botao, 0);
		}   catch (Exception e){

		}
	}

	/**
	 * @author T_DCDias
	 */
	public static selecionaListaDeElementos(String xpath, String tag){
		listOfElements = listaDeElementos(xpath, tag)
	}

	/**
	 * @author T_DCDias
	 * @param xpath
	 * @param tag
	 * Retorna uma lista de elementos
	 * @return
	 */
	public static List<WebElement> listaDeElementos(String xpath, String tag){
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement Table = driver.findElement(By.xpath(xpath))
		List<WebElement> rows_table = Table.findElements(By.tagName(tag))
		return rows_table
	}


	/**
	 * @author T_DCDias
	 */
	public static selecionaListaDeElementosCompleta(String xpath){
		listOfElements = listaDeElementosCompleta(xpath, "input", "div")
	}

	/**
	 * @author T_DCDias
	 * @param xpath
	 * @param tag
	 * Retorna uma lista de elementos
	 * @return
	 */
	public static List<WebElement> listaDeElementosCompleta(String xpath,String tag, String ...args){
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement Table = driver.findElement(By.xpath(xpath))

		String[] valores = args
		List<WebElement> rows_table
		for(int i = 0 ; i < valores.size(); i++){
			rows_table = Table.findElements(By.tagName(valores[i]))
		}
		//List<WebElement> rows_table = Table.findElements(By.tagName(tag))
		return rows_table
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param nomePasta
	 * @return
	 */
	public static String createDiretory(String nomePasta){
		nomePasta = nomePasta.replace(".","").replace("\\", "").replace(":", "-");
		String pathValue = 'C:\\Users\\Public\\'+nomePasta+'';
		println pathValue
		boolean result
		try{
			File dir = new File(pathValue);
			result = dir.mkdirs();
			System.out.println("mkdirs ["+result+"]");
		} catch (Exception e){}

		return pathValue;
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param caminhoEntrada
	 * @param caminhoSaida
	 * @return
	 * @throws IOException
	 */
	public static String trasferirArquivo(String caminhoEntrada, String caminhoSaida) throws IOException {
		String caminhoPastaFixa = caminhoSaida
		InputStream entrada1 = new FileInputStream(caminhoEntrada.trim());
		OutputStream saida1 = new FileOutputStream(caminhoSaida.trim()+"\\ficheiro.PDF");

		byte[] buf = new byte[1024];
		int len;
		while ((len = entrada1.read(buf)) > 0) {
			saida1.write(buf, 0, len);
		}
		entrada1.close();
		saida1.close();

		return caminhoPastaFixa;
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param caminhoFonte
	 * @param caminhoSaida
	 * @return
	 */
	public static String copiarExcel(String caminhoFonte, String caminhoSaida){
		FileChannel canalFont = null;
		FileChannel canalDest = null;
		try {
			canalFont = new FileInputStream(caminhoFonte).getChannel();
			canalDest = new FileOutputStream(caminhoSaida).getChannel();
			canalFont.transferTo(0, canalFont.size(), canalDest);
		} finally {
			if (canalFont != null && canalFont.isOpen()){
				canalFont.close();
			}
			if (canalDest != null && canalDest.isOpen()){
				canalDest.close();
			}
		}
		println caminhoSaida
		return caminhoSaida;
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public  static String getDate(){
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = dateFormat.format(date);
		System.out.println("Converted String: " + strDate);
		return strDate;
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public  static String getDateHour(){
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
		String strDate = dateFormat.format(date);
		System.out.println("Converted String: " + strDate);
		return strDate;
	}


	/**
	 * @author Dvlopes
	 * Recebe por parametro o formato de data e hora
	 * Retorna a data e hora em formato string; 
	 * Exemplo "dd/MM/yyyy HH:mm:ss" = 12/12/2019 15:07:28
	 * @return
	 */
	public  static String getDateHourGenerico(String formatoData){
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat(formatoData);
		String strDate = dateFormat.format(date);
		System.out.println("Data Recuperada [" + strDate + "]");
		return strDate;
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public  static String getDateYMD(){
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = dateFormat.format(date);
		System.out.println("Converted String: " + strDate);
		return strDate;
	}





	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public  static String getDateAgendamento(){
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM");
		String strDate = dateFormat.format(date);
		System.out.println("Converted String: " + strDate);
		return strDate;
	}

	/**
	 * @author Dvlopes
	 * @return
	 */
	public  static String getMes(){
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("MM");
		String strDate = dateFormat.format(date);
		Map<String, String> mes = new HashMap<String, String>();
		mes.put("01",'01 - Janeiro');
		mes.put("02",'02 - Feveeiro');
		mes.put("03",'03 - Março');
		mes.put("04",'04 - Abril');
		mes.put("05",'05 - Maio');
		mes.put("06",'06 - Junho');
		mes.put("07",'07 - Julho');
		mes.put("08",'08 - Agosto');
		mes.put("09",'09 - Setembro');
		mes.put("10",'10 - Outubro');
		mes.put("11",'11 - Novembro');
		mes.put("12",'12 - Dezembro');
		return mes.get(strDate);;
	}

	/**
	 * @author ALZ_ServiceQA 
	 * @return
	 */
	public  static String getMesDate(){
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("MM");
		String strDate = dateFormat.format(date);
		return strDate;
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static String getMesAnoDate(){
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
		String strDate = dateFormat.format(date);
		println strDate;
		return strDate;
	}


	/**
	 * @author T_Dvlopes
	 * @param delay
	 * @return
	 */
	public static String recuperarPendenciaTelaListagemRecibos(String ramoAQUI, int delay){
		try{
			WebUI.delay(delay);
			int qtdPendenciaRecuperada;
			String pendenciaRecuperada;
			String dataRecuperada;
			String totalQtdPencia;
			String naoExisteDados;
			String mesAnoRecuperado = getMesAnoDate();
			String valorRecuperado = recuperaValor('//*[@id="sectionDetalhesAplicacoesApoliceContent"]', 'textContent', 0)
			println valorRecuperado;
			if(valorRecuperado.contains('Nº Adesão')){
				if(ramoAQUI.equals('307') || ramoAQUI.equals('308') ||ramoAQUI.equals('309') || ramoAQUI.equals('310') || ramoAQUI.equals('311') ||ramoAQUI.equals('312') || ramoAQUI.equals('329') || ramoAQUI.equals('335') || ramoAQUI.equals('336') || ramoAQUI.equals('337') ||ramoAQUI.equals('338') || ramoAQUI.equals('339')){
					clickXpath('//*[@id="sectionaplicacoesapoliceSectionBar"]//td[1]/div', 2);
					String xpath = '//*[@id="SectionRecibosContent"]/div[1]'
					String tag = "tr";
					List<WebElement> listOfElements
					listOfElements = listaDeElementos(xpath,tag)
					for(int i = 0; i < listOfElements.size(); i++){
						System.err.println("["+i+"] "+tag+" ["+listOfElements[i].getText()+"]")
						if(listOfElements[i].getText().toString().contains('Pendente'.toString())){
							dataRecuperada = listOfElements[i].getText().toString();
							dataRecuperada = dataRecuperada.subSequence(14,32);
							dataRecuperada = extracaoDatas(dataRecuperada);
							dataRecuperada = dataRecuperada.subSequence(3,10);
							if(!dataRecuperada.equals(mesAnoRecuperado)){
								qtdPendenciaRecuperada++;
							}
						}else if(listOfElements[i].getText().toString().contains('Não existem dados'.toString())){
							naoExisteDados = 'Não existem Dados';
							validarErro('Não existem Dados');
							break;
						}
					}
					totalQtdPencia = qtdPendenciaRecuperada.toString();
					if(naoExisteDados.equals('Não existem Dados')){
						totalQtdPencia = "Não Existe Dados";
						valueCheck = 'Não Existe Dados';
					}else if(totalQtdPencia.equals('0')){
						totalQtdPencia = "Não Existe Pendencias";
					}
					return pendenciaRecuperada = totalQtdPencia;
				}
				else{
					String xpath = '//*[@id="sectionDetalhesAplicacoesApoliceContent"]'
					String tag = "tr";
					List<WebElement> listOfElements
					listOfElements = listaDeElementos(xpath,tag)
					for(int i = 0; i < listOfElements.size(); i++){
						System.err.println("["+i+"] "+tag+" ["+listOfElements[i].getText()+"]")
						if(listOfElements[i].getText().toString().contains('Pendente'.toString())){
							dataRecuperada = listOfElements[i].getText().toString();
							dataRecuperada = dataRecuperada.subSequence(14,32);
							dataRecuperada = extracaoDatas(dataRecuperada);
							dataRecuperada = dataRecuperada.subSequence(3,10);
							if(!dataRecuperada.equals(mesAnoRecuperado)){
								qtdPendenciaRecuperada++;
							}
						}else if(listOfElements[i].getText().toString().contains('Não existem dados'.toString())){
							naoExisteDados = 'Não existem Dados';
							validarErro('Não existem Dados');
							break;
						}
					}
					totalQtdPencia = qtdPendenciaRecuperada.toString();
					if(naoExisteDados.equals('Não existem Dados')){
						totalQtdPencia = "Não Existe Dados";
						valueCheck = 'Não Existe Dados';
					}else if(totalQtdPencia.equals('0')){
						totalQtdPencia = "Não Existe Pendencias";
					}
					return pendenciaRecuperada = totalQtdPencia;
				}
			}else{
				String xpath = '//*[@id="SectionRecibosContent"]/div[1]'
				String tag = "tr";
				List<WebElement> listOfElements
				listOfElements = listaDeElementos(xpath,tag)
				for(int i = 0; i < listOfElements.size(); i++){
					System.err.println("["+i+"] "+tag+" ["+listOfElements[i].getText()+"]")
					if(listOfElements[i].getText().toString().contains('Pendente'.toString())){
						dataRecuperada = listOfElements[i].getText().toString();
						dataRecuperada = dataRecuperada.subSequence(14,32);
						dataRecuperada = extracaoDatas(dataRecuperada);
						dataRecuperada = dataRecuperada.subSequence(3,10);
						if(!dataRecuperada.equals(mesAnoRecuperado)){
							qtdPendenciaRecuperada++;
						}
					}else if(listOfElements[i].getText().toString().contains('Não existem dados'.toString())){
						naoExisteDados = 'Não existem Dados';
						validarErro('Não existem Dados');
						break;
					}
				}
				totalQtdPencia = qtdPendenciaRecuperada.toString();
				if(naoExisteDados.equals('Não existem Dados')){
					totalQtdPencia = "Não Existe Dados";
					valueCheck = 'Não Existe Dados';
				}else if(totalQtdPencia.equals('0')){
					totalQtdPencia = "Não Existe Pendencias";
				}
				return pendenciaRecuperada = totalQtdPencia;
			}
		}catch (Exception e){
			String erroValueCheck = recuperaValor('//*[@id="DetalhesAplicacoesApoliceTable_tbody"]/tr/td','textContent',5);
			System.err.println('Falha ao Consultar Apolice\n' + e);
			finalizar();
			valueCheck = 'Não enviado ['+erroValueCheck+']';
		}
	}

	/**
	 * @author T_DCDias
	 * Teste de extração de datas em um conjunto de string
	 */
	public static String extracaoDatas(String textoRecuperado){
		Map<Integer,String> hasdates = new HashMap<Integer, String>();
		hasdates = auxiliares.ExtractDates.getSubDates(textoRecuperado)
		String sdata1=hasdates.get(0).toString().trim();
		System.err.println("Data: "+sdata1);/*"]\nData:2 ["+sdata2+"]\ndata:3 ["+sdata3+"]\nData:4 ["+sdata4+"]\nHasdates "+hasdates.toString())*/
		return sdata1;
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param xpath
	 * @return
	 */
	public static clicarEmElementoDaTela(String xpath, int dalayfor){
		if(dalayfor>0){
			WebUI.delay(dalayfor)
		}
		WebDriver driver = DriverFactory.getWebDriver()
		driver.findElement(By.xpath(xpath)).click()
		if(dalayfor>0){
			WebUI.delay(dalayfor)
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param xpath
	 * @return
	 */
	public static String retornaValorDoElementoDaTela(String xpath){
		String valor
		WebDriver driver = DriverFactory.getWebDriver()
		valor = driver.findElement(By.xpath(xpath)).getText();
		return valor
	}

	/**
	 * @author T_DCDias
	 * @param path
	 * @return
	 */
	public static lerConfigurações(String path){

		List<String> linhas = FileInput.readFile(path)
		for(int i = 0; i < linhas.size();i++){
			switch (i)  {
				case 0:
					pathTxtEmail = linhas.get(i).toString();
					break;
				case 1:
					sender = linhas.get(i).toString();
					break;
				case 2:
					pwdsender = linhas.get(i).toString();
					break;
				case 3:
					host = linhas.get(i).toString();
					break;
				case 4:
					porta = linhas.get(i).toString();
					break;
			}
		}
	}

	/**
	 * @author T_DCDias
	 * @param data
	 * @param coringa
	 * @param column
	 * @return
	 * Verifica se existe item não processado
	 */
	public static boolean validaExecutados(TestData data, String coringa, int column){
		boolean executados = true
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(column, i) == coringa) {
				System.err.println("["+i+"] Data Value ["+data.getValue(column, i)+"] coringa ["+coringa+"]")
				executados = false
				break
			}
		}
		return executados
	}


	/**
	 * @author T_DCDias
	 * @param data
	 * @param coringa
	 * @param column
	 * @return
	 * Verifica se existe item não processado
	 */
	public static boolean validaExecutadosPorData(TestData data, String coringa, int column, String dataSelecionada, int columnDate ){
		boolean executados = true
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(column, i) == coringa && data.getValue(columnDate, i) == dataSelecionada) {
				System.err.println("["+i+"] Data Value ["+data.getValue(column, i)+"] coringa ["+coringa+"] Data Selecionada "+dataSelecionada+"]")
				executados = false
				break
			}
		}
		return executados
	}




	/**
	 * Renomeia arquivo recebendo o PATH e o nome de saida
	 * @param pathFileValue
	 * @param namePdf
	 * @return
	 */
	public static renomearArquivo(String pathFileValue, String namePdf){
		File f = new File(pathFileValue);
		String nameReplace = namePdf.replace("/", "-").replace(".", "-");
		//if (f.renameTo(new File(pathFileValue+"C:\\Users\\Public\\"+nameReplace+".PDF")))
		if (f.renameTo(new File("C:\\Users\\Public\\"+nameReplace+".PDF")))
		{
			System.out.println("Arquivo renomeado");
		}
		else{
			System.out.println("Arquivo renomeado");
		}

	}

	/**Altera aba para pagina do download e fecha o mesmo
	 * @return
	 */
	public static AlteraAbDownloadAutoLupa(){
		WebUI.delay(5);
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		String parent =  it.next();
		String child =it.next();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
	}


	/**Altera aba para pagina do download e fecha o mesmo
	 * @return
	 */
	public static FecharAbDownloadAutoLupa(){
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		String parent =  it.next();
		String child =it.next();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
		driver.close();
		driver.switchTo().window(parent);
	}



	public static String selecionarQtdParcela(String parcelas){
		//		Map<String, String> qtdPARCELAS = new HashMap<String, String>();
		//		qtdPARCELAS.put("1",'//*[@id="ParrillaBean$numFracciones"]/option[1]');
		//		qtdPARCELAS.put("2",'//*[@id="ParrillaBean$numFracciones"]/option[2]');
		//		qtdPARCELAS.put("3",'//*[@id="ParrillaBean$numFracciones"]/option[3]');
		//		qtdPARCELAS.put("4",'//*[@id="ParrillaBean$numFracciones"]/option[4]');
		//		qtdPARCELAS.put("5",'//*[@id="ParrillaBean$numFracciones"]/option[5]');
		//		qtdPARCELAS.put("6",'//*[@id="ParrillaBean$numFracciones"]/option[6]');
		//		qtdPARCELAS.put("7",'//*[@id="ParrillaBean$numFracciones"]/option[7]');
		//		qtdPARCELAS.put("8",'//*[@id="ParrillaBean$numFracciones"]/option[8]');
		//		qtdPARCELAS.put("9",'//*[@id="ParrillaBean$numFracciones"]/option[9]');
		//		qtdPARCELAS.put("10",'//*[@id="ParrillaBean$numFracciones"]/option[10]');
		//
		//		return qtdPARCELAS.get(parcelas);
		return '//*[@id="ParrillaBean$numFracciones"]/option['+parcelas+']';
	}




	/**Realiza a leitura da lista valida se existe itens para desbloqueio;
	 * Caso não exista retorna 'Sem Itens'
	 * Caso possua entra no metodo libera Apolice Multipla
	 * @author Dvlopes
	 * @param delay
	 * @return
	 */
	public static String fluxoLiberacaoApoliceBloqueios(int delay){
		try{
			String validaDesbloqueio;
			String finalizarDesbloqueio;
			String liberaMult = 'false';
			WebUI.delay(delay);
			int qtdBloqueios = 0;
			int posicaoXpath = 0;
			List<String>linhasBloqueio = new ArrayList<String>();
			int qtdChar
			String gravaExcel = 'false';
			String xpath = '//*[@id="tablaBloqueos_tbody"]'
			String tag = "tr";
			List<WebElement> listOfElements
			listOfElements = listaDeElementos(xpath,tag);
			for(int i = 0; i < listOfElements.size(); i++){
				qtdChar = listOfElements[i].getText().length();
				if(qtdChar > 15){
					System.err.println("["+i+"] "+tag+" ["+listOfElements[i].getText()+"]")
					linhasBloqueio.add(listOfElements[i].getText().toString());
				}
			}
			qtdBloqueios = linhasBloqueio.size();
			if (qtdBloqueios == 1){
				gravaExcel = 'SemItens'
				AlteraProducaoSinistroDesbloqueio();
			}else if(qtdBloqueios > 1){
				liberaMult = liberaApoliceMultipla();
				if(liberaMult.equals('true')){
					trocaJanela('Menu Produção');
					clickXpath('//*[@id="o_7"]', 1);
					for(int i = linhasBloqueio.size()-1; i > -1; i--){
						println 'Quantidade de bloqueios restantes! ' +qtdBloqueios;
						if(linhasBloqueio[i].contains('1137'.toString())||linhasBloqueio[i].contains('1575'.toString())){
							while (validaXpathDesbloqueio()){
								clickXpath('//*[@id="fil214"]', 1);
								fechaAlerta();
								validaDesbloqueio = validaNumeroProposta('//*[@id="SUSEP"]', 'textContent', 2);
								println validaDesbloqueio
								if(validaDesbloqueio.contains('Numero da Proposta Não Encontrada'.toString())){
									retornaPaginaMenuProducao();
									if(qtdBloqueios == 1){
										liberaPrimeiraLinhaProposta();
										retornaPaginaMenuProducao();
										gravaExcel = 'true';
									}
								}
							}
							posicaoXpath = qtdBloqueios - 1;
							println '//*[@id="fil2'+posicaoXpath+'"]'
							clickXpath('//*[@id="fil2'+posicaoXpath+'"]', 1);
							fechaAlerta();
							validaDesbloqueio = validaNumeroProposta('//*[@id="SUSEP"]', 'textContent', 15);
							println validaDesbloqueio
							//qtdBloqueios --;
							if(validaDesbloqueio.contains('Numero da Proposta Não Encontrada'.toString())){
								retornaPaginaMenuProducao();
								if(qtdBloqueios == 1){
									liberaPrimeiraLinhaProposta();
									gravaExcel = 'true';

								}
								qtdBloqueios = qtdBloqueios - 1
							}else{
								AlteraProducaoSinistroDesbloqueioGeral();
								finalizarDesbloqueio= 'finalizar'
								println 'NUMERO DA APOLICE É '+validaDesbloqueio;
								break;
							}
							println 'Quantidade de bloqueios restantes! ' +qtdBloqueios;

						}else if(linhasBloqueio[i].contains('8004'.toString())||linhasBloqueio[i].contains('8013'.toString())){
							liberaPrimeiraLinhaProposta();
							gravaExcel = 'true';
							finalizarDesbloqueio = 'finalizarTotal'
						}

					}
					if(finalizarDesbloqueio.equals('finalizar')){
						gravaExcel = validaDesbloqueio;
					}else if(finalizarDesbloqueio.equals('finalizarTotal')){
						AlteraProducaoSinistroDesbloqueio();
					}else{
						liberaPrimeiraLinhaProposta();
						retornaPaginaInicial();
						gravaExcel = 'true';
					}
				}else {
					//retorna para pagina inicial
					AlteraProducaoSinistroDesbloqueio();
				}
			}
			return gravaExcel;
		}catch (Exception e){
			System.err.println('Falha ao Consultar Apolice' + e);
		}
	}





	/**
	 *	Realiza a liberação unica da apolice validando se existe codigo 931
	 * @author Dvlopes
	 * @return
	 */
	public static String liberaApoliceUnica(){
		String liberaUnica = 'false';
		//clica no botao para ir tela menu producao
		clickXpath('//*[@id="fil10"]', 0);
		trocaJanela('Menu Produção');
		//clica no botao para ficha gestao
		clickXpath('//*[@id="btFichaGest"]', 0);
		trocaJanela('Gestão Processos');

		String xpath = '//*[@id="listDatos_tbody"]'
		String tag = "tr";
		List<WebElement> listOfElements
		listOfElements = listaDeElementos(xpath,tag);

		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().contains("931".toString())){
				liberaUnica = 'true';
				break;
			}
		}
		if(liberaUnica.equals('true')){
			trocaJanela('Menu Produção');
			//clica em voltar
			clickXpath('//*[@id="o_7"]', 0);
			liberaPrimeiraLinhaProposta();
		}else {
			liberaUnica = 'missing 931';
		}
		//retorna para pagina inicial
		AlteraProducaoSinistroDesbloqueio();


		return liberaUnica;
	}

	/**Realiza leitura da tabela 
	 * Esperando receber 2 parametros
	 * 422 e 931
	 * Caso não encontre os 2 retorna liberaMult = não libera / false
	 * Caso encontre retorna TRUE / LIBERA
	 * @author Dvlopes
	 * @return
	 */
	public static String liberaApoliceMultipla(){
		println("Liberação Multipla");
		String liberaMult = 'false';
		//String liberaMultOk = 'naoLibera';
		//clica no botao para ir tela menu producao
		clickXpath('//*[@id="fil10"]', 0);
		//trocaJanela('Menu Produção');
		//clica no botao para ficha gestao
		clickXpath('//*[@id="btFichaGest"]', 0);
		trocaJanela('Gestão Processos');

		String xpath = '//*[@id="listDatos"]'
		//*[@id="listDatos_tbody"]
		String tag = "tr";
		List<WebElement> listOfElements
		listOfElements = listaDeElementos(xpath,tag);

		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println("["+i+"] "+tag+" ["+listOfElements[i].getText()+"]");
			if(listOfElements[i].getText().contains("422".toString())){
				liberaMult = 'true';
				break;
			}
			/**
			 * ESSA PARTE FOI COMETANDA, POIS RECEBEMOS ATUALIZACAO PARA REMOVER A VALIDAÇÃO DO CODIGO 422 & 931
			 * AGORA APENAS ENCONTRANDO 422 REALIZA A LIBERAÇÃO
			 */

			//			if(listOfElements[i].getText().contains("422".toString())){
			//				if(liberaMultOk.contains('931'.toString())){
			//					liberaMultOk = 'Libera';
			//				}else{
			//					liberaMultOk = '422';
			//				}
			//			}else if(listOfElements[i].getText().contains("931".toString())){
			//				if(liberaMultOk.contains('422'.toString())){
			//					liberaMultOk = 'Libera';
			//				}else {
			//					liberaMultOk = '931';
			//				}
			//				//liberaMultOk = liberaMultOk + 1;
			//			}
			//
			//			if(liberaMultOk.equals("Libera".toString())){
			//				liberaMult = 'true'
			//				break;
			//			}
		}
		return liberaMult;
	}


	/**
	 * Antes de liberar bloqueio da proposta valida se existe
	 * 
	 * 8002 < removido do SCOPO
	 * 8004
	 * 8013
	 * @author DVLOPES
	 * @return
	 */
	public static liberaPrimeiraLinhaProposta(){
		for (int i = 0; i < 2; i++){
			String	valorDesbloqueia = validarElemento('//*[@id="tablaBloqueos_0_12"]', 'textContent', 5);
			//	if(valorDesbloqueia.equals('8002')||valorDesbloqueia.equals('8004')||valorDesbloqueia.equals('8013')){
			if(valorDesbloqueia.equals('8004')||valorDesbloqueia.equals('8013')){
				//clica Libera proposta
				clickXpath('//*[@id="fil20"]', 2);
				fechaAlerta();
				WebUI.delay(15);
				break;
			}else {
				break;
			}
		}
	}


	/**
	 * @author DVLOPES
	 * @param xpath
	 * @param atribute
	 * @param delay
	 * @return
	 */
	public static String validaNumeroProposta(String xpath,String atribute, int delay){
		String elementreturn
		try{
			WebUI.delay(delay);
			WebDriver driver = DriverFactory.getWebDriver();
			//altera janela
			Set  st= driver.getWindowHandles();
			Iterator it = st.iterator();
			String parent =  it.next();
			String child =it.next();
			String child2 =it.next();
			String child3 =it.next();
			driver.switchTo().window(parent);
			driver.switchTo().window(child);
			driver.switchTo().window(child2);
			driver.switchTo().window(child3);
			//recupera elemento
			WebElement element = driver.findElement(By.xpath(xpath));
			elementreturn = element.getAttribute(atribute);
			driver.switchTo().window(child);
			//altera janela
			return elementreturn;
		}catch (Exception e){
			return elementreturn = 'Numero da Proposta Não Encontrada';
		}

	}


	public static retornaPaginaMenuProducao(){
		WebUI.delay(2);
		WebDriver driver = DriverFactory.getWebDriver();
		//altera janela
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		String parent =  it.next();
		String child =it.next();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
	}


	public static retornaPaginaInicial(){
		WebUI.delay(2);
		WebDriver driver = DriverFactory.getWebDriver();
		Set  st= driver.getWindowHandles();
		Iterator it = st.iterator();
		String parent =  it.next();
		String child =it.next();
		driver.switchTo().window(parent);
		driver.switchTo().window(child);
		driver.close();
		driver.switchTo().window(parent);
	}

	public static boolean validaXpathDesbloqueio(){
		boolean xpathExiste = false;
		try{
			WebUI.delay(0);
			WebDriver driver = DriverFactory.getWebDriver();
			WebElement element = driver.findElement(By.xpath('//*[@id="fil214"]'));
			xpathExiste = true;
			return xpathExiste
		}catch (Exception e){
			xpathExiste = false;
		}
		return xpathExiste

	}

	/**
	 * @author T_Dvlopes
	 * @param delay
	 * @return
	 */
	public static validarTextoComplementar(){
		try{
			WebUI.delay(2);
			String recuperarTextoComplementar;
			String recuperarTextoComplementar2;
			String xpath = '//*[@id="blClausulasSeleccionadasContent"]/table[2]/tbody'
			String tag = "input";
			List<WebElement> listOfElements;
			listOfElements = listaDeElementos(xpath,tag)
			int qtdMaxima = listOfElements.size();
			println qtdMaxima;
			int j = 0;
			for(int i = 0; i <= qtdMaxima; i++){
				recuperarTextoComplementar = recuperaValor('html/body/div[1]/div[2]/table/tbody/tr[1]/td/form/div['+j+']/table/tbody/tr[2]/td/div/table[2]/tbody/tr['+i+']/td[3]/table/tbody/tr/td/input', 'value', 0);
				println recuperarTextoComplementar;
				if(recuperarTextoComplementar.contains('TEXTO COMPLEMENTAR'.toString())){
					clickXpath('//*[@id="blClausulasSeleccionadasContent"]/table[2]/tbody/tr['+i+']/td[6]', 1);
					break;
				}
				j = j + 1;
				for(int x = 0; x <= qtdMaxima; x++){
					recuperarTextoComplementar2 = recuperaValor('html/body/div[1]/div[2]/table/tbody/tr[1]/td/form/div['+x+']/table/tbody/tr[2]/td/div/table[2]/tbody/tr['+i+']/td[3]/table/tbody/tr/td/input', 'value', 0);
					println recuperarTextoComplementar2;
					if(recuperarTextoComplementar2.contains('TEXTO COMPLEMENTAR'.toString())){
						clickXpath('//*[@id="blClausulasSeleccionadasContent"]/table[2]/tbody/tr['+i+']/td[6]', 1);
						break;
					}
				}
			}

		}catch (Exception e){
			System.err.println('Falha ao Consultar Apolice' + e);
			finalizar();
		}
	}



	public static clickScript(String xpath){
		try{
			WebDriver driver = DriverFactory.getWebDriver()
			WebElement element = driver.findElement(By.xpath(xpath));
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", element )
		}  catch (Exception e){e}
	}


	/**
	 * @author T_DCDias
	 * @param xpath
	 * @return
	 */
	public static limparCamposInput(String xpath){
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement Element = driver.findElement(By.xpath(xpath));
		Element.clear()
	}

	/**
	 * @author T_DCDias
	 * @param xpath
	 * @param value
	 * @return
	 */
	public static escreverCamposInput(String xpath, String value){
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement Element = driver.findElement(By.xpath(xpath));
		Element.sendKeys(value)
	}





	/**	
	 * @author T_Dvlopes	 
	 * @param criterio
	 * 
	 * Recebe text por parametro
	 * 
	 */
	public static void selecionaCriteirios(String criterio){
		clickXpath('//*/filtro[2]/div/div[2]/div/div/div[1]/div/div/input',2);
		String xpath = '//*[@id="collapseOne"]/div/filtros-extracao/fieldset/filtro[2]/div/div[2]/div/div/div[1]/div/ul'
		String tag = "a";
		List<WebElement> listOfElements
		listOfElements = listaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().toString().contains(criterio)){
				listOfElements[i].click();
				break;
			}
		}
	}

	/**
	 * Pagina 0 / 1 / 2
	 * Recebe a quantidade de paginas para navergar e delay
	 * @param qtd
	 * @return
	 */
	public static alteraJanejaGenerica(int qtd, int delay){

		if(delay==0){
			String x;
			WebDriver driver = DriverFactory.getWebDriver();
			Set  st= driver.getWindowHandles();
			Iterator it = st.iterator();
			for(int i=0; i<=qtd; i++){
				x = i.toString();
				x = it.next();
				driver.switchTo().window(x);
			}
		} else {
			WebUI.delay(delay);
			String x;
			WebDriver driver = DriverFactory.getWebDriver();
			Set  st= driver.getWindowHandles();
			Iterator it = st.iterator();
			for(int i=0; i<=qtd; i++){
				x = i.toString();
				x = it.next();
				driver.switchTo().window(x);
			}
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 */
	public static void deletarDocrequest(){
		String path = 'C:\\Users\\Public\\docrequest.PDF';
		deletarPDFGenerico(path);
		path = 'C:\\Users\\Public\\docrequest (1).PDF';
		deletarPDFGenerico(path);
	}

	/**
	 * @author T_DCDias
	 * @param caminhoFonte
	 * @param caminhoSaida
	 * @return
	 */
	public static moveArquivo(String caminhoFonte, String caminhoSaida){
		deletaArquivoGenerico(caminhoSaida+'\\001.txt');
		// Arquivo a ser movido
		File arquivo = new File(caminhoFonte);
		if (!arquivo.exists()) {
			System.out.println("Arquivo não encontrado");
		} else {

			// Diretorio de destino
			File diretorioDestino = new File(caminhoSaida);

			// Move o arquivo para o novo diretorio
			boolean sucesso = arquivo.renameTo(new File(diretorioDestino, arquivo.getName()));
			if (sucesso) {
				System.out.println("Arquivo movido para '" + diretorioDestino.getAbsolutePath() + "'");
			} else {
				System.out.println("Erro ao mover arquivo '" + arquivo.getAbsolutePath() + "' para '"
						+ diretorioDestino.getAbsolutePath() + "'");
			}
		}
	}

	/**
	 * @author T_DCDias
	 * @param zipFilePath
	 * @param destDir
	 */
	private static void unzip(String zipFilePath, String destDir) {
		System.out.println(System.getProperties());
		File dir = new File(destDir);
		// create output directory if it doesn't exist
		if(!dir.exists()) dir.mkdirs();
		FileInputStream fis;
		//buffer for read and write data to file
		byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while(ze != null){
				String fileName = ze.getName();
				File newFile = new File(destDir + File.separator + fileName);
				System.out.println("Unzipping to "+newFile.getAbsolutePath());
				//create directories for sub directories in zip
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				//close this ZipEntry
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			//close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author ALZ_ServiceQA
	 * @param path
	 * @param namezip
	 * @return
	 */
	public static zipDirectory(String path, String namezip){

		FileOutputStream fos = new FileOutputStream(namezip);
		ZipOutputStream zipOut = new ZipOutputStream(fos);
		File fileToZip = new File(path);

		zipFile(fileToZip, fileToZip.getName(), zipOut);
		zipOut.close();
		fos.close();
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param fileToZip
	 * @param fileName
	 * @param zipOut
	 * @throws IOException
	 */
	private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
		if (fileToZip.isHidden()) {
			return;
		}
		if (fileToZip.isDirectory()) {
			if (fileName.endsWith("/")) {
				zipOut.putNextEntry(new ZipEntry(fileName));
				zipOut.closeEntry();
			} else {
				zipOut.putNextEntry(new ZipEntry(fileName + "/"));
				zipOut.closeEntry();
			}
			File[] children = fileToZip.listFiles();
			for (File childFile : children) {
				zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
			}
			return;
		}
		FileInputStream fis = new FileInputStream(fileToZip);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zipOut.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}
		fis.close();
	}

	/**
	 * deleta arquivo PDF [FICHERO] no diretorio
	 */
	public static void limpezaFicheiro(String extencao){
		try{
			//Thread.sleep(1000)
			deletaArquivoPdf("C:\\Users\\Public\\fichero."+extencao);
			deletaArquivoPdf("C:\\Users\\Public\\fichero (1)."+extencao);
		} catch (Exception e) {
			System.err.println('Falha ao deletar "'+extencao+"\n"+ e);
			finalizar();
			valueCheck = 'Não enviado';
		}
	}

	/**
	 * @author T_DCDias
	 */
	public static salvarEvidencia(String path, String ScreenshotName){
		try{
			WebUI.delay(5);
			TakesScreenshots.takeScreen(path, ScreenshotName)
			WebUI.delay(5);
		} catch (Exception e){
		}
	}

	/**
	 * @author be54955
	 * @param pedido
	 * @param linha
	 * @param caminho_arquivo_xlsx
	 * @param aba
	 * @return
	 */
	public static salvarInfoTelaAtualizarExcel(String pedido, int linha, String caminho_arquivo_xlsx,String aba, int cell){
		try{
			WebUI.delay(2);
			auxiliares.WriteExcelFactory.updateSheetLine("", linha,caminho_arquivo_xlsx, pedido, cell, aba);
			WebUI.delay(2);
		} catch (Exception e){
			println "salvarInfoTelaAtualizarExcel\n"+e
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaTipoSelecao(String tipo,String xpath , String tag ){
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			//		System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains(tipo.toString())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				listOfElements[i+1].click()
				break
			}
		}
		WebUI.delay(2)
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaItemLista(String tipo,String xpath , String tag ){
		boolean f = false
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println("["+i+"]  ["+listOfElements[i].getAttribute('value')+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().equals(tipo.toString())){
				//System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				listOfElements[i].click()
				f = true
				break
			}
		}
		return f
		WebUI.delay(2)
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaItemListaAtributo(String tipo,String xpath , String tag, String attrib ){
		boolean f = false
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"] ["+listOfElements[i].getAttribute(attrib)+"]")
			if(listOfElements[i].getAttribute(attrib).contains(tipo.toString())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				listOfElements[i].click()
				f = true
				break
			}
		}
		return f
		WebUI.delay(2)
	}


	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaItemListaEquals(String tipo,String xpath , String tag ){
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().equals(tipo.toString())){
				//System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				listOfElements[i].click()
				break
			}
		}
		WebUI.delay(2)
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaItemListaContains(String tipo,String xpath , String tag ){
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains(tipo.toString())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				listOfElements[i].click()
				break
			}
		}
		WebUI.delay(2)
	}

	/**
	 *  *****************************8NOVA FICHA GESTÃO *****************************
	 * @param registros
	 * @param p
	 * @param a
	 * @return
	 */
	public static novaFichaGestao(List<String> registros, int p, int a){
		System.out.println()

		//ABRIR SELEÇÃO
		String xpath ='//*[@id="componenteReferencia"]/div'
		String tag="nx-icon"
		try{
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				System.out.println("["+i+"] ["+listOfElements[i].getTagName() +"]" )
				if(listOfElements[i].getTagName().contains(tag)){
					listOfElements[i].click()
					//		break
				}
			}
		} catch (Exception e){}

		//SELECIONAR
		MenuMatriz.subMenu('//*[@id="cdk-overlay-0"]/div/div[2]', "span","APÓLICE")
		//Proposta
		preencheXpath('//*[@id="nx-input-1"]', registros[p],0);
		//Aplica
		preencheXpath('//*[@id="nx-input-2"]', registros[a],0);
		//Pesquisar
		clickXpath('//*[@id="componenteReferencia"]/div/div[1]/div[11]/nx-icon', 1);

	}

	/**
	 *  *****************************8NOVA FICHA GESTÃO *****************************
	 * @param registros
	 * @param p
	 * @param a
	 * @return
	 */
	public static novaFichaGestaoProcesso(List<String> registros, int p, int a, String processo){
		System.out.println()

		//ABRIR SELEÇÃO
		String xpath ='//*[@id="componenteReferencia"]/div'
		String tag="nx-icon"
		try{
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				System.out.println("["+i+"] ["+listOfElements[i].getTagName() +"]" )
				if(listOfElements[i].getTagName().contains(tag)){
					listOfElements[i].click()
					//		break
				}
			}
		} catch (Exception e){}

		//SELECIONAR
		System.out.println("Processo ["+processo+"]")
		MenuMatriz.subMenu('//*[@id="cdk-overlay-0"]/div/div[2]', "div",processoFG(processo))

		switch(processo){
			case "P":
			//Proposta
				preencheXpath('//*[@id="nx-input-1"]', registros[p],0);
			//Aplica
				preencheXpath('//*[@id="nx-input-2"]', registros[a],0);
			//Pesquisar
				clickXpath('//*[@id="componenteReferencia"]/div/div[1]/div[11]/nx-icon', 1);
				break;
			case "C":
			//Proposta
				preencheXpath('//*[@id="nx-input-4"]', registros[p],0);
			//Pesquisar
				clickXpath('//*[@id="componenteReferencia"]/div/div[1]/div[11]/nx-icon', 1);
				break;
			case "P-PolizaPRD":
			//Proposta
				preencheXpath('//*[@id="nx-input-1"]', registros[p],0);
			//Aplica
				preencheXpath('//*[@id="nx-input-2"]', registros[a],0);
			//Pesquisar
				clickXpath('//*[@id="componenteReferencia"]/div/div[1]/div[11]/nx-icon', 1);
				break;
			case "P-PolizaINT":
			//Proposta
				preencheXpath('//*[@id="nx-input-1"]', registros[p],0);
			//Aplica
				preencheXpath('//*[@id="nx-input-2"]', registros[a],0);
			//Pesquisar
				clickXpath('//*[@id="componenteReferencia"]/div/div[1]/div[11]/nx-icon', 1);
				break;
			case "P-PropostaPRD":
			//Proposta
				preencheXpath('//*[@id="nx-input-1"]', registros[p],0);
			//Aplica
				preencheXpath('//*[@id="nx-input-2"]', registros[a],0);
			//Pesquisar
				clickXpath('//*[@id="componenteReferencia"]/div/div[1]/div[11]/nx-icon', 1);
				break;
		}

	}

	/**
	 * @author be54955
	 * @param p
	 * @return
	 */
	public static String  processoFG(String p){
		Map<String, String> process = new HashMap<String, String>()
		process.put('P', 'PÓLICE');
		process.put('C', 'CLIENTE');
		process.put('P-PolizaINT', 'POLIZA');
		process.put('P-PolizaPRD', 'APÓLICE');
		process.put('P-PropostaPRD', 'APÓLICE');
		return process.get(p.toString());
	}

	/**
	 *  *****************************8NOVA FICHA GESTÃO *****************************
	 * @param registros
	 * @param p
	 * @param a
	 * @return
	 */
	public static novaFichaGestaoCliente(List<String> registros, int d){
		System.out.println()

		//ABRIR SELEÇÃO
		String xpath ='//*[@id="componenteReferencia"]/div'
		String tag="nx-icon"
		try{
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				System.out.println("["+i+"] ["+listOfElements[i].getTagName() +"]" )
				if(listOfElements[i].getTagName().contains(tag)){
					listOfElements[i].click()
					//		break
				}
			}
		} catch (Exception e){}

		//SELECIONAR
		MenuMatriz.subMenu('//*[@id="cdk-overlay-0"]/div/div[2]', "span","CPF / CNPJ")
		//CPF/CNPJ
		preencheXpath('//*[@id="nx-input-4"]', registros[d],0);
		//Pesquisar
		clickXpath('//*[@id="componenteReferencia"]/div/div[1]/div[11]/nx-icon', 1);

	}

	/**
	 *  *****************************8NOVA FICHA GESTÃO *****************************
	 * @param registros
	 * @param p
	 * @param a
	 * @return
	 */
	public static novaFichaGestaoPorAtributo(String proposta, String aplica){
		System.out.println( proposta+" "+aplica)

		//ABRIR SELEÇÃO
		String xpath ='//*[@id="componenteReferencia"]/div'
		String tag="nx-icon"
		try{
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				System.out.println("["+i+"] ["+listOfElements[i].getTagName() +"]" )
				if(listOfElements[i].getTagName().contains(tag)){
					listOfElements[i].click()
					//		break
				}
			}
		} catch (Exception e){}

		//SELECIONAR
		MenuMatriz.subMenu('//*[@id="cdk-overlay-0"]/div/div[2]', "span","APÓLICE")
		//Proposta
		preencheXpath('//*[@id="nx-input-1"]', proposta,0);
		//Aplica
		preencheXpath('//*[@id="nx-input-2"]', aplica,0);
		//Pesquisar
		clickXpath('//*[@id="componenteReferencia"]/div/div[1]/div[11]/nx-icon', 1);

	}




	/**
	 *  *****************************8NOVA FICHA GESTÃO *****************************
	 * @author be54955
	 * @return
	 * Metodo de seleção de itens na tela de ficha e gestão
	 */
	public static String retornaLinhaTabelaFichaGestao(int index){
		System.out.println()
		String linha
		StringBuilder strb = new StringBuilder()
		String xpath = '//*[@id="tablaLista"]/app-list-file/div/div/div/table'
		String tag ="tr";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){

			System.out.println("LINE ["+i+"] INDEX ["+index+"]")
			if(i>0){
				if(i==index){
					System.out.println("LINE ["+i+"] ["+listOfElements[i].getText()+"] INDEX FOUND ["+index+"]")
					strb.append(listOfElements[i].getText()+" ")
				}
			}
		}
		linha = strb.toString()
		System.out.println("LINHA ["+linha+"]")
		return linha
	}


	/**
	 *  *****************************8NOVA FICHA GESTÃO *****************************
	 * @author be54955
	 * @return
	 * Metodo de seleção de itens na tela de ficha e gestão
	 * Envio de PDF
	 */
	public static String retornaLinhaTabelaUnicaFichaGestao(int index, int table){
		System.out.println("["+table+"] ["+index+"]")
		String linha
		try{
			StringBuilder strb = new StringBuilder()
			String xpath = '//*[@id="tablaLista"]/app-list-file/div/div/div/nx-ibl-list-table['+table+']/div/div[2]/div/nx-ibl-list-table-section/nx-ibl-list-table-element['+index+']/div/div/div/div/div/div/div/div/div[1]/div[1]'
			String tag ="div";
			WebUI.delay(2)
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				strb.append(listOfElements[i].getText()+" ")
			}
			linha = strb.toString()


		} catch (Exception e){}
		return linha
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static int retornaNumeroDeTabelasFichaGestao(){
		int linhas
		String xpath = '//*[@id="tablaLista"]/app-list-file/div'
		String tag ="h3";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			System.out.println("["+i+"] ["+listOfElements[i].getText() +"]" )
		}
		linhas = listOfElements.size()
		return linhas
	}

	/**
	 * *****************************8NOVA FICHA GESTÃO *****************************
	 * @param v1
	 * @param v2
	 * @param s
	 * @return
	 * Metodo de seleção de itens na tela de ficha e gestão
	 */
	public static boolean identificaDadosNaLinha(String v1, String v2, String s){
		System.out.println("["+v1+" - "+v2+" - "+s+"]")
		boolean b=false
		boolean bv1=false
		boolean bv2=false
		try{
			if(s.toString().contains(v1.toString())){
				bv1 = true
				System.out.println(bv1)
			}
			if(s.toString().contains(v2.toString())){
				bv2 = true
				System.out.println(bv2)
			}
			if(bv1&&bv2){b = true}
			System.out.println("\n["+s+"] ["+v1+"] ["+v2+"] ["+b+"]")
		} catch (Exception e){

		}
		return b
	}

	/**
	 *  *****************************8NOVA FICHA GESTÃO *****************************
	 * @author be54955
	 * @param i
	 * @return
	 */
	public static boolean painelDownload(int i){
		System.out.println()
		boolean b = false
		//SELECIONA ARQUIVO
		clickTable(i)
		WebUI.delay(5);
		try{
			selecionaListaDeElementos('/html/body/div[1]/div/app-root/file-management/app-listfilemanagement/div/div/div[2]','nx-expansion-panel-title')
			for(int j = 0; j < listOfElements.size(); j++){
				System.out.println("["+j+"] ["+listOfElements[j].getText() +"]" )
				if(listOfElements[j].getText().contains("Documento anexo".toString())){
					//	listOfElements[j].click()
				}
			}
		} catch (Exception e){}

		try{
			selecionaListaDeElementos('/html/body/div[1]/div/app-root/file-management/app-listfilemanagement/div/div/div[2]','img')
			for(int k = 0; k < listOfElements.size(); k++){
				System.out.println("["+k+"] ["+listOfElements[k].getTagName() +"]" )
				if(listOfElements[k].getTagName().contains('img'.toString())){
					listOfElements[k].click()
					b = true
				}
			}
		} catch (Exception e){}
		System.out.println("painelDownload ["+b+"]")
		return b
	}

	/**
	 *  *****************************8NOVA FICHA GESTÃO *****************************
	 * @author be54955
	 * @param i
	 * @return
	 * Envio de PDF - Não utilizado
	 */
	public static painelDownloadTable(int i, int table){
		//SELECIONA ARQUIVO
		clickTable(i)
		WebUI.delay(5);
		try{
			selecionaListaDeElementos('/html/body/div[1]/div/app-root/file-management/app-listfilemanagement/div/div/div[2]','nx-expansion-panel-title')
			for(int j = 0; j < listOfElements.size(); j++){
				System.out.println("["+j+"] ["+listOfElements[j].getText() +"]" )
				if(listOfElements[j].getText().contains("Documento anexo")){
					listOfElements[j].click()
				}
			}
		} catch (Exception e){}

		try{
			selecionaListaDeElementos('/html/body/div[1]/div/app-root/file-management/app-listfilemanagement/div/div/div[2]','img')
			for(int k = 0; k < listOfElements.size(); k++){
				System.out.println("["+k+"] ["+listOfElements[k].getTagName() +"]" )
				if(listOfElements[k].getTagName().contains('img')){
					listOfElements[k].click()
				}
			}
		} catch (Exception e){}
	}

	/**
	 * *****************************8NOVA FICHA GESTÃO *****************************
	 * @author be54955
	 * @param i
	 * @return
	 */
	public static painelEnvioDeEmail(int i){
		//SELECIONA ARQUIVO
		clickXpath('//*[@id="tablaLista"]/app-list-file/div/div/div/nx-ibl-list-table[1]/div/div[2]/div/nx-ibl-list-table-section/nx-ibl-list-table-element['+i+']/div/div/div/div/div/div/div/div/div[1]/div[2]/div[2]/span[1]', 2);
		//Acesso a detalhes estendidos
		clickXpath('//*[@id="nx-tab-content-0-0"]/div/div[6]/div/button/span', 2);
	}

	/**
	 * ***************8*******************************BANCO DE ALERTAS DO SISTEMA *******************************
	 * @author be54955
	 * @param alert
	 * @return
	 */
	public static boolean alertListBlocList(String alert){
		System.err.println("\nalertListBloc ["+alert+"]")
		boolean r = false

		if(alert.length()>0){
			List<String> listOfAlerts = new ArrayList<String>()
			//listOfAlerts.add("Erro on epac2020 on QA mode")
			listOfAlerts.add("Apólice a renovar já utilizada")
			listOfAlerts.add("Classe de bônus divergente")
			listOfAlerts.add(" Dados de conta corrente não pode ser de um CNPJ como Pagador")
			listOfAlerts.add("Nome não retornado no Serviço")
			listOfAlerts.add(" Política se aplica ao estado errado")
			listOfAlerts.add("Error: Dados da conta bancária inválidos")
			listOfAlerts.add(" Restrição técnica para o condutor")
			listOfAlerts.add(" Restrição técnica para o segurado")
			listOfAlerts.add(" Vistoria Prévia Presencial Obrigatória")
			listOfAlerts.add(" Data de início de vigência deve ser após")
			listOfAlerts.add("Dados de conta corrente não pode ser de um CNPJ como Pagador")
			listOfAlerts.add(" Campo Data Saída Nota Fiscal 0KM da tela dados de risco é obrigatório na emissão")
			listOfAlerts.add(" Campo Placa da tela dados de risco é obrigatório para emissão")
			listOfAlerts.add(" Não existem dados para esta consulta.")
			listOfAlerts.add(" CPF ou CNPJ inválido")
			listOfAlerts.add("Corretor inexistente ")
			listOfAlerts.add(" Emissão não permitida! Fora do prazo de 30 dias permitido pela SUSEP")
			listOfAlerts.add(" Período de contratação não permitido.")
			listOfAlerts.add(" Informar o valor da(s) cobertura(s) selecionada(s).")
			listOfAlerts.add(" Estado civil obrigatório")
			listOfAlerts.add(" application.msg.plataforma.errorTipoSalto Você não pode acessar trem com um tipo diferente de salto 1")
			listOfAlerts.add(" Obrigatorio informar um telefono movel")
			listOfAlerts.add(" O valor da comissão livre é maior ao limite cadastrado no produto")
			listOfAlerts.add(" Coloque um valor entre 1 a 31.")
			listOfAlerts.add(" LOCALIDADE INEXISTENTE")
			listOfAlerts.add(" Emissão não permitido! Risco Decorrido")
			listOfAlerts.add(" Comissão inferior a comissão mínima estipulada: 0,01")
			listOfAlerts.add("Error: Dados da conta bancária inválidos")
			listOfAlerts.add(" Obrigatorio informar um telefono movel")
			listOfAlerts.add(" Deve confirmar a conta bancária")
			listOfAlerts.add(" Estado civil obrigatório")
			listOfAlerts.add(" Campo necessário")
			listOfAlerts.add(" Categoria do risco obrigatória")
			listOfAlerts.add(" Código de compañía no valido")
			listOfAlerts.add(" Banco indisponível para emissão")
			listOfAlerts.add(" Email inválido ou utilizado em outros clientes")
			listOfAlerts.add(" Erro em la conversión de Datos")
			listOfAlerts.add("Tem certeza que deseja excluir esta Apólice?")
			listOfAlerts.add(" Não foram encontrados dados para a ação selecionada.")
			listOfAlerts.add("A proposta está bloqueada.")
			listOfAlerts.add(" O prazo para realização desse tipo de endosso foi excedido")
			listOfAlerts.add(" Data início endosso maior a data de cancelamento")
			listOfAlerts.add("javax.resource.spi.CommException: CTG9630E Se ha producido una excepción de E/S durante la comunicación con CICS ")
			listOfAlerts.add(" Apólice já cancelada.")
			listOfAlerts.add(" Apólice em vigor")
			listOfAlerts.add(" Apólice com sinistro de perda total.")
			listOfAlerts.add(" ERRO VALIDAÇÃO:Indique um Empregado.")
			listOfAlerts.add("Nome de usuário ou senha incorretos. Por favor, digite-os novamente marcando a caixa Eu não sou um robô e seguindo os passos indicados na tela.")
			//listOfAlerts.add(" Se este local não possui número, favor informar o complemento")
			listOfAlerts.add("Informar a Apólice/Item ")
			listOfAlerts.add(" Prêmio parcela  inferior ao  prêmio mínima da parcela")
			listOfAlerts.add(" O preenchimento do campo Bairro é obrigatório")
			listOfAlerts.add(" Cidade é obrigatório")
			listOfAlerts.add(" Endereço é obrigatório")
			listOfAlerts.add(" Estado é obrigatório")
			listOfAlerts.add(" O Valor Segurado deve ser entre 70% e 100% do Valor do Edifício.")
			listOfAlerts.add("processado COM FALHA, [Não Existe Dados]")
			listOfAlerts.add("Não existem dados em valores de carteira e detalhes do resultado")
			listOfAlerts.add(" Deve ser informado, pelo menos, um campo de pesquisa única (ID Pedido, Período, CNPJ/CPF ò Nome Cliente)")
			listOfAlerts.add(" O limite mínimo de contratação para o Valor Garantido é de R\$ 50.000,00")
			listOfAlerts.add(" Faixa de CEP Restrito, falha de acesso a informação")
			listOfAlerts.add("Referencia Inexistente")
			listOfAlerts.add("Não há dados para mostrar")
			listOfAlerts.add(" Data de início da frota menor que a data de início da apólice pai")
			listOfAlerts.add(" Risco com Cobertura pela Apólice ")
			listOfAlerts.add(" El importe final debe ser mayor que el inicial")
			listOfAlerts.add(" Preencha o e-mail do Destinatário")
			listOfAlerts.add(" Emissão não permitida! Fora do prazo de 30 dias permitido pela SUSEP")
			listOfAlerts.add(" Não e permitida a emissão futura acima de 30 dias")
			listOfAlerts.add("Não existem dadosr")
			System.out.println("Alert -["+alert+"]-")
			for(int i = 0; i < listOfAlerts.size(); i++){
				System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
				if(listOfAlerts[i].contains(alert.toString())){
					System.err.println("FOUND ALERT TO BLOC ["+i+"] ["+listOfAlerts[i]+"]")
					r = true
					break
				}
			}
		}
		System.out.println("\nAlert ["+r+"]")
		return  r
	}


	/**
	 * ***************8*******************************BANCO DE ALERTAS DO SISTEMA *******************************
	 * @author be54955
	 * @param alert
	 * @return
	 */
	public static boolean alertListContinueList(String alert){
		System.err.println("\nalertListBloc ["+alert+"]")
		boolean r = false
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add(" Verifique se o email está correto antes de continuar")
		listOfAlerts.add(" Se este local não possui número, favor informar o complemento")
		listOfAlerts.add(" Adaptou-se aos limites mínimo e máximo do produto")
		listOfAlerts.add(" 350- Restrição técnica")
		listOfAlerts.add(" Campo necessário")
		listOfAlerts.add(" O valor do campo Tipo de Endereco deve ser informado")
		listOfAlerts.add(" Alteramos automaticamente as informações de existência de seguro informado")
		listOfAlerts.add(" Restrição de cotação para o CPF/CNPJ Informado")
		listOfAlerts.add(" Não é possível prosseguir com o cálculo. Restrição técnica para o segurado (200)")
		listOfAlerts.add(" O preenchimento do campo Bairro é obrigatório")
		listOfAlerts.add(" Endereço é obrigatório")
		listOfAlerts.add(" Erro em la conversión de Datos")
		listOfAlerts.add(" O registro foi registrado corretamente no banco de dados")
		listOfAlerts.add(" Carências são obrigatórias para menos que 10 vidas. Favor rever.")

		System.out.println("Alert -["+alert+"]-")
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
	 * ***************8*******************************BANCO DE TELAS *******************************
	 * @author be54955
	 * @param alert
	 * @return
	 */
	public static boolean listaDeTelas(String alert){
		System.err.println("\nTela ["+alert+"]")
		boolean r = false
		List<String> listOfAlerts = new ArrayList<String>()
		listOfAlerts.add("Menu Produção")
		listOfAlerts.add("D. Básicos")
		listOfAlerts.add("COBERTURAS")
		listOfAlerts.add("COBERTURAS")
		listOfAlerts.add("TABELA DE PREÇOS")
		listOfAlerts.add("D. Emissão")
		listOfAlerts.add("D. Cliente")
		listOfAlerts.add("D. Comuns")
		listOfAlerts.add("CLÁUSULAS")
		listOfAlerts.add("CONFIRMAÇÃO DE EMISSÃO")
		listOfAlerts.add("EMISSÃO REALIZADA")
		listOfAlerts.add("COSSEGURO ACEITO")
		listOfAlerts.add("Lista Cliente")
		listOfAlerts.add("Gestão Integrada de Sinistros")
		listOfAlerts.add("Lista bloqueios")
		listOfAlerts.add("Resultado Operacional")
		listOfAlerts.add("Cancelamento e suspensões")
		listOfAlerts.add("NOVA COTAÇÃO")
		listOfAlerts.add("application.page.pDadosRiscoResiDigit")
		listOfAlerts.add("Dados de Riscos")
		listOfAlerts.add("NIVEL AUTORIZACIÓN PAGOS.")
		listOfAlerts.add("LISTA BLOQUEIOS")
		listOfAlerts.add("RESULTADO OPERACIONAL")
		listOfAlerts.add("CONSULTAS BD PRODUÇÃO")
		//	listOfAlerts.add(" Emissão não permitida! Fora do prazo de 30 dias permitido pela SUSEP")

		System.out.println("Tela -["+alert+"]-")
		for(int i = 0; i < listOfAlerts.size(); i++){
			System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
			if(listOfAlerts[i].equals(alert.toString())){
				System.err.println("FOUND PAGE ["+i+"] ["+listOfAlerts[i]+"]")
				r = true
				break
			}
		}
		System.out.println("\nTela ["+r+"]")
		return  r
	}

	/*
	 * 
	 * 
	 */
	public static clickTable(int index){
		//		System.out.println("\nindex ["+index+"]")
		StringBuilder strb = new StringBuilder()
		String xpath = '//*[@id="tablaLista"]/app-list-file/div/div/div/table/tbody'
		String tag ="tr";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		int n = index - 1
		System.out.println("\nindex ["+n+"]")
		//	int l = 1-index
		for(int i = 0; i < listOfElements.size(); i++){
			//		System.out.println("["+i+"] index ["+n+"]")
			if(i==n){
				//		System.out.println("\nFound index ["+i+"] ["+listOfElements[i].getText()+"]")
				listOfElements[i].click()
			}
		}
	}

	/**
	 * @author BE54955
	 * @param index
	 * @return
	 * Metodo para clicar no anexo "PDF" 
	 */
	public static clickAnexoFichaGestão(int index, String data, String txnota){
		System.out.println("\nindex ["+index+"] Data ["+data+"] TXNOTA ["+txnota+"]")
		boolean b = false
		//SELECIONA ARQUIVO
		clickTable(index)
		WebUI.delay(5);
		try{
			selecionaListaDeElementos('//*[@id="nx-tab-content-0-0"]/div/div[6]','button')
			for(int k = 0; k < listOfElements.size(); k++){
				System.out.println("["+k+"] ["+listOfElements[k].getText() +"]" )
				if(listOfElements[k].getText().contains("Acesso a detalhes estendidos".toString())){
					//		if(listOfElements[k].getTagName().contains('img'.toString())){
					listOfElements[k].click()
					b = true
				}
			}
		} catch (Exception e){}
		System.out.println("painelDownload ["+b+"]")
		return b
	}

	/**
	 * @author be54955
	 * @param proposta
	 * @param sequencia
	 * @return
	 */
	public static String formataProposta(String proposta, String sequencia){
		String nStr = proposta.replaceAll(sequencia,'')
		return nStr
	}

	/**
	 * @author be54955
	 * @return
	 * Identifica botão de renovação com alteração
	 */
	public static boolean identificaBotaoRenovar(){
		String xpath = '//*[@id="divFooterButtonBar"]'
		String tag = 'button'
		boolean f = true
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains('Renovar com alteração'.toString())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				f = false
				break
			}
		}
		return f
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param aba
	 * @return
	 */
	public static acessoAmbiente(String aba, String a){
		iniciaAplicacaoMatrizCommands(retornaAnbiente(a));
		String abaAmbiente = aba+a
		System.out.println('['+abaAmbiente+']')
		switch (a){
			case "INT":
				realizaLoginIntraNetProducaoTransacionalCommands(abaAmbiente,'Produção e recibos');
				break;
			case "PRD":
				realizaLoginIntraNetProducaoCommands(abaAmbiente)
				break;
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static String retornaAnbiente(String aba){
		Map<String, String> urls = new HashMap<String, String>()
		urls.put('PRD','IntranetProdução');
		urls.put('INT','IntranetTransacional');
		return urls.get(aba.toString());
	}

	/**
	 * @author be54955
	 * @param v
	 * @return
	 */
	public static String ajusteValorNaoEncontrado(String v){
		String txt
		if(v.equals('atributo nao encontrado')){
			txt=''
		}
		return txt
	}


	/**
	 * @author be54955
	 * @param v
	 * @return
	 */
	public static boolean valorZero(String v){
		boolean b = false
		if(v.equals('0')){
			b = true
		} else {
			String nStr
			nStr = v.replaceAll(',','')
			Integer number = Integer.valueOf(nStr);
			if(number==0){
				b = true
			}
		}
		return b
	}



	/**
	 * realizaLoginAllianzNet INTRANET
	 * Recebe por parametro nome da aba 'excel' localiza qual user/pword utilizar e realiza login
	 * @author T_DVLopes
	 */
	public static void realizaLoginIntraNetProducao(String nomeArquivo){
		try{
			auxiliares.UserPassword.getUsuarioSenha(nomeArquivo)
			realizaLoginTelaIntraNetProducao(auxiliares.UserPassword.getUser(),auxiliares.UserPassword.getSenha());
		} catch (Exception e) {
			System.err.println('Falha ao realizar Login MatrizProdução' + e);
			finalizar();
		}
	}

	/**
	 * @author be54955
	 * @param s
	 * @return
	 * 
	 * Ajuste de mensagem de alerta da tela ficha gestão
	 * 
	 */
	public static String ajusteMSGFG(String s){
		String str
		if(s.equals("Referencia Inexistente") || s.equals("Não há dados para mostrar")){
			str = s
		} else {
			str = "Alerta não apresentada"
		}
		System.out.println("Ajuste MSGFG IN ["+s+" OUT ["+str+"]")
		return str
	}


	/**
	 * @author be54955
	 * @param t
	 * @return
	 * Retorna index da tabela de dados da tela FG 
	 */
	public static int indexPendencia(){
		System.out.println()
		int index
		alteraJanejaGenerica(2, 5)
		String xpath = '//*[@id="tablaLista"]/app-list-file/div/div/div/table'
		String tag ="tr";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().toString().contains("Pendência - CotaçõesEspeciais ")){
				System.out.println("FOUND INDEX ["+i+"]")
				index = i
				break
			}
		}
		return index
	}


	/**
	 * iniciaAplicacaoMatriz
	 * @author T_DVLopes
	 */
	public static void iniciaAplicacaoMatriz(String url){
		try{
			abreBrowser(url);
			valueCheck = 'enviado';
			valueCheckFuncionarioAutoResidencia = 'Processado';
			selecionarApolice = true;
		} catch (Exception e) {
			System.err.println('Falha ao iniciar APlicação Tela Matriz' + e);
			finalizar();
		}
	}


	/**
	 * @author be54955
	 * @param t
	 * @return
	 * Retorna index da tabela de dados da tela FG
	 */
	public static int indexPendenciaSelectItem(String txt){
		System.out.println()
		int index
		//alteraJanejaGenerica(2, 5)
		String xpath = '//*[@id="tablaLista"]/app-list-file/div/div/div/table'
		String tag ="tr";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().toString().contains(txt)){
				System.out.println("FOUND INDEX ["+i+"]")
				index = i
				break
			}
		}
		return index
	}

	/**
	 * @author be54955
	 * @param t
	 * @return
	 * Retorna item selecionado caso exista
	 */
	public static boolean verificaPendenciaSelectItem(String txt){
		System.out.println()
		boolean b = false
		try{

			String xpath = '//*[@id="tablaLista"]/app-list-file/div/div/div/table'
			String tag ="tr";
			WebUI.delay(2)
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				System.out.println("LINE ["+i+"] ["+listOfElements[i].getText()+"]")
				if(listOfElements[i].getText().toString().contains(txt.toString())){
					System.out.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
					b = true
					break
				}
			}

		} catch (Exception e){
			b = false
		}
		return b
	}


	/**
	 * @author be54955
	 * @param t
	 * @return
	 * Retorna item selecionado caso exista
	 */
	public static boolean verificaPendenciaSelectItemData(String txt, String dt){
		System.out.println()
		boolean b = false
		try{

			String xpath = '//*[@id="tablaLista"]/app-list-file/div/div/div/table'
			String tag ="tr";
			WebUI.delay(2)
			selecionaListaDeElementos(xpath,tag)
			for(int i = 0; i < listOfElements.size(); i++){
				System.out.println("LINE ["+i+"] ["+listOfElements[i].getText()+"] ["+txt+"] [ "+dt+"]")
				if(listOfElements[i].getText().toString().contains(txt.toString()) && listOfElements[i].getText().toString().contains(dt.toString())){
					System.out.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
					b = true
					break
				}
			}

		} catch (Exception e){
			b = false
		}
		return b
	}

	/**
	 * @author BE54955
	 * @param listaExcel
	 * @param dataParaExecutar
	 * @return
	 * Dev 14/02/2023
	 * Identifica datas para execução de ao´polices para renovações Auto/Residência
	 */
	public static List<MassaDados> verificadataParaExecutar(List<MassaDados> listaExcel, String... dataParaExecutar, String ramo){
		System.err.println('Total de renovações  ['+listaExcel.size()+'] Total de vigências ['+dataParaExecutar.size()+"]")
		List<MassaDados> listaExcelDatas = new ArrayList<MassaDados>();
		for(int i = 0; i < dataParaExecutar.size(); i++){
			for(int j = 0; j< listaExcel.size();j++){
				String dt1 = dataParaExecutar[i];
				String dt2 = ExtractDates.formatDateToBR(listaExcel[j].getFinalVigencia())
				if(dt1.equals(dt2)&&ramo.equals(listaExcel[j].getRamo())){
					System.err.println('Data ['+i+'] ['+dataParaExecutar[i]+'] Final Vigencia ['+j+'] ['+ExtractDates.formatDateToBR(listaExcel[j].getFinalVigencia())+'] Ramo ['+listaExcel[j].getRamo()+"]")
					listaExcelDatas.add(listaExcel[j]);
				}
			}
		}
		System.err.println('Total de renovações por data ['+listaExcelDatas.size()+']')
		return listaExcelDatas
	}
}





















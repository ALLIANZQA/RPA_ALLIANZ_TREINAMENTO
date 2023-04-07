//package rpacommands
//
//import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
//import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
//import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
//import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
//import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
//import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
//import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
//import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
//
//import java.awt.image.BufferedImage
//import java.text.NumberFormat
//
//import com.kms.katalon.core.annotation.Keyword
//import com.kms.katalon.core.checkpoint.Checkpoint
//import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
//import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
//import com.kms.katalon.core.model.FailureHandling
//import com.kms.katalon.core.testcase.TestCase
//import com.kms.katalon.core.testdata.TestData
//import com.kms.katalon.core.testobject.TestObject
//import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
//import com.kms.katalon.core.testobject.ConditionType
//
//import internal.GlobalVariable
//import io.cucumber.datatable.DataTable
//import org.apache.poi.xssf.usermodel.XSSFSheet
//import org.apache.poi.xssf.usermodel.XSSFWorkbook
//import org.openqa.selenium.WebDriver
//import org.openqa.selenium.WebElement
//import org.openqa.selenium.chrome.ChromeDriver
//import org.openqa.selenium.chrome.ChromeOptions
//import org.openqa.selenium.By
//import org.openqa.selenium.JavascriptExecutor
//import org.openqa.selenium.Keys as Keys
//
//import org.apache.http.util.Asserts
//
//import com.kms.katalon.core.checkpoint.CheckpointFactory
//import com.kms.katalon.core.testcase.TestCaseFactory
//import com.kms.katalon.core.testdata.TestDataFactory
//import com.kms.katalon.core.testobject.ObjectRepository
//import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
//import com.kms.katalon.core.webui.driver.DriverFactory
//import com.kms.katalon.core.testobject.RequestObject
//import com.kms.katalon.core.testobject.ResponseObject
//import com.kms.katalon.core.testobject.TestObjectProperty
//import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
//import com.kms.katalon.core.util.KeywordUtil
//import com.kms.katalon.core.webui.exception.WebElementNotFoundException
//
//import cucumber.api.java.en.And
//import cucumber.api.java.en.Given
//import cucumber.api.java.en.Then
//import cucumber.api.java.en.When
//
//import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium
//import static org.junit.Assert.*
//
////import auxiliares.GeneratorPDF
//import bean.CasoDeTeste
//import java.util.regex.Pattern
//import static org.apache.commons.lang3.StringUtils.join
//
//import org.openqa.selenium.interactions.Actions as Actions
//import org.openqa.selenium.support.ui.FluentWait
//import org.openqa.selenium.support.ui.Wait
//import org.openqa.selenium.support.ui.WebDriverWait
//import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
//
//import java.util.List
//import java.util.concurrent.TimeUnit;
//
//
//import org.openqa.selenium.support.ui.ExpectedCondition;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.OutputType;
//
//import com.itextpdf.text.Image;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.pdf.PdfPTable
//import org.openqa.selenium.remote.Augmenter
//import org.openqa.selenium.remote.RemoteWebDriver
//import org.openqa.selenium.remote.SessionId
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.filefilter.WildcardFileFilter;
//
//import org.openqa.selenium.StaleElementReferenceException;
//
//public class AutoComandos extends GeneratorPDF{
//
//
//	////////////////////// usado na automação
//	protected static List<CasoDeTeste> ListCasoDeTeste = new ArrayList<CasoDeTeste>()
//	protected static bean.CasoDeTeste casodeteste
//	protected static List<Map<String, String>> issue_info;
//	protected static List<WebElement> listOfElements
//	protected static List<String> values;
//	protected static String data1;
//	protected static String data2;
//	protected static boolean validaLabelClasseDeBonus
//	protected static Map<Integer,String> hasdates = new HashMap<Integer, String>();
//	protected static TestObject flexibleTestObject
//	protected static totalDeLabels
//	protected static totalvalidationslabels
//	protected static totalCamposDefaultCotacaoAuto
//	protected static totalvalidationsdefault
//	//protected static path="C:\\Users\\Public\\QA_ALLIANZ\\MassaAuto\\MassaAutoAllianzPro.xlsx"
//	protected static path="C:\\Users\\Public\\QA_ALLIANZ\\MassaAuto\\MassaAutoAllianz.xlsx"
//	protected static pathRadarLive="C:\\Users\\Public\\QA_ALLIANZ\\MassaAuto\\MassaAutoAllianzRadarLive.xlsx"
//	protected static pathRadarLiveMoto="C:\\Users\\Public\\QA_ALLIANZ\\MassaAuto\\MassaMotoAllianzRadarLive.xlsx"
//	protected static pathEvidences = "C:\\Users\\Public\\Evidencias\\"
//	protected static createDiretorypath = "C://Users//Public//Evidencias//"
//	protected static String pathFileValue
//
//	//INTEGRAÇÃO
//	//protected static String url = 'https://wwwi.br.intrallianz.com/drlf01/web/allianznet'
//
//	//ACEITAÇÃO
//	//protected static String url = 'https://wwwfdt.br.intrallianz.com/drlf01/web/allianznet'
//
//	//DESENVOLVIMENTO
//	//protected static String url = 'https://wwwd.br.intrallianz.com/drlf01/web/allianznet/inicio'
//
//	//PRODUÇÃO
//	//protected static String url = 'https://www.allianznet.com.br/drlf01/web/allianznet'
//
//	//protected static String url ='integração'
//	protected static String url ='aceitação'
//
//
//	////////////////////////////////////////////DESENVOLVIMENTO
//	//pagamento
//	protected static String emailSegurado = "salvador.stefanelli@gmail.com"
//	protected static String telefone = "11999999999"
//	protected static String profissao = "Administrad"
//	protected static String rendaMensal ="index4"
//	protected static String numerodaApoliceAnterior = "1234567"
//	protected static String numeroDaCI = "646700D0D9YI97" // usado em tela de pagamentos
//	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//
//
//	/**
//	 * @author T_DCDias
//	 * @param url
//	 * @return
//	 */
//	public static String retornaURL(String url){
//		Map<String, String> urls = new HashMap<String, String>()
//		urls.put('integração', 'https://wwwi.br.intrallianz.com/drlf01/web/allianznet' )
//		urls.put('produção', 'https://www.allianznet.com.br/drlf01/web/allianznet')
//		urls.put('aceitação', 'https://wwwfdt.br.intrallianz.com/drlf01/web/allianznet')
//		urls.put('desenvolvimento', 'https://wwwd.br.intrallianz.com/drlf01/web/allianznet/inicio')
//		urls.put('matriz', 'https://wwwfdt.br.intrallianz.com/drlf01/web/azb-intranet/login-sso')
//		return urls.get(url.toLowerCase()).toString();
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static encerraSessao(){
//		clicarEmElementoDaTela("//b[@class='alz-icon-aspa-abajo all-daspaazu hidden-sm']", 2)
//		clicarEmElementoDaTela("//a[@class='btn allBtn white small btnBudget all-dbuttpers']", 2)
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param ct
//	 * @return
//	 */
//	public static leituraMassa(String ct){
//		try{
//			System.err.println("path "+path)
//			casodeteste = new  CasoDeTeste()
//			casodeteste = auxiliares.WriteExcelFactory.readingSheet(path,ct)
//		} catch (Exception e)
//		{
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível iniciar o teste", e)
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 */
//	public static adicionaImagem(String ct, String msg1, String status, int espera){
//		try{
//			WebUI.delay(espera)
//			porcentagemDeTela(60)
//			//Adiciona cenário no cabeçalho do documento e após adiciona a imagem
//			adicionaCenario(status, issue_info.get(0).get("Cenário"))
//			adicionarEvidencia(takeScreen())
//			porcentagemDeTela(100)
//		} catch (Exception e){
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 */
//	public static salvarEvidencia(String ct, String msg1, String status){
//		try{
//			Calendar calendar = new GregorianCalendar()
//			String dia = calendar.get(calendar.DAY_OF_MONTH)
//			String mes = calendar.get(calendar.MONTH)
//			String ano = calendar.get(calendar.YEAR)
//			String hour = calendar.get(calendar.HOUR)
//			String minute = calendar.get(calendar.MINUTE)
//			System.err.print("\n-- Salvar Evidencia ["+issue_info.get(0).get("CT")+" "+msg1+" "+status+"] Dia ["+dia+"/"+mes+"/"+ano+"] Hora ["+hour+":"+minute+"] Cenário ["+issue_info.get(0).get("Cenário")+"] --")
//			porcentagemDeTela(60)
//			//Adiciona cenário no cabeçalho do documento e após adiciona a imagem
//			adicionaCenario(status, issue_info.get(0).get("Cenário"))
//			adicionarEvidencia(takeScreen())
//			porcentagemDeTela(100)
//			auxiliares.WriteExcelFactory.updateSheet(path,issue_info.get(0).get("CT"),status,39)
//			auxiliares.WriteExcelFactory.updateSheet(path,issue_info.get(0).get("CT"),msg1,40)
//		} catch (Exception e){
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 */
//	public static salvarEvidenciaPDF(String ct, String msg1, String status){
//		try{
//			Calendar calendar = new GregorianCalendar()
//			String dia = calendar.get(calendar.DAY_OF_MONTH)
//			String mes = calendar.get(calendar.MONTH)
//			String ano = calendar.get(calendar.YEAR)
//			String hour = calendar.get(calendar.HOUR)
//			String minute = calendar.get(calendar.MINUTE)
//			System.err.print("\n-- Salvar Evidencia ["+issue_info.get(0).get("CT")+" "+msg1+" "+status+"] Dia ["+dia+"/"+mes+"/"+ano+"] Hora ["+hour+":"+minute+"] Cenário ["+issue_info.get(0).get("Cenário")+"] --")
//			//Adiciona cenário no cabeçalho do documento e após adiciona a imagem
//			adicionaCenario(status, issue_info.get(0).get("Cenário"))
//			adicionarEvidenciaContentPDF(takeContentPDF())
//			auxiliares.WriteExcelFactory.updateSheet(path,issue_info.get(0).get("CT"),status,39)
//			auxiliares.WriteExcelFactory.updateSheet(path,issue_info.get(0).get("CT"),msg1,40)
//		} catch (Exception e){
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 */
//	public static salvarEvidenciaRadarLive(int row, String msg1, String status, String executar){
//		try{
//			auxiliares.WriteExcelFactory.updateSheetLine(row,pathRadarLive,status,39)
//			auxiliares.WriteExcelFactory.updateSheetLine(row,pathRadarLive,msg1,40)
//			auxiliares.WriteExcelFactory.updateSheetLine(row,pathRadarLive,executar,42)
//		} catch (Exception e){
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param pathValue
//	 * @return
//	 */
//	public static createDiretory(String pathValue ){
//		File dir = new File(pathValue);
//		boolean result = dir.mkdirs();
//		System.out.println("mkdirs ["+result+"]");
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param path
//	 * @return
//	 */
//	public static boolean validaExistenciaDeArquivo(String path){
//		boolean resp
//		File arquivo = new File(path);
//		if(arquivo.exists()){
//			System.err.println("O ARQUIVO EXISTE")
//			resp = false
//		}else{
//			System.err.println("O ARQUIVO NÃO EXISTE")
//			resp = true
//		}
//		return resp
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param pathValue
//	 * @return
//	 */
//	public static boolean deleteFileDiretory(String pathValue ){
//		try{
//			boolean success = (new File(pathValue.toString())).delete();
//			if (!success) {
//				System.err.println("[ Arquivo ["+pathValue+"] não excluido ! ]")
//			} else{
//				System.err.println("[ Arquivo ["+pathValue+"] excluido ! ]")
//			}
//
//			while(validaExistenciaDeArquivo(pathValue)== false){
//				deleteFileDiretory(pathValue)
//			}
//		} catch (Exception e){
//			System.err.println("Não foi possível excluir o arquivo ["+pathValue+"]\n"+e )
//
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param ct
//	 * @param path
//	 * @return
//	 */
//	public static Image takeScreen( ){
//		WebDriver driver = DriverFactory.getWebDriver()
//		WebDriver augmentedDriver = new Augmenter().augment(driver);
//		byte[] input = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)
//		Image im = Image.getInstance(input)
//		im.scaleToFit(620, 620)
//		return im
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param ct
//	 * @param path
//	 * @return
//	 */
//	public static String takeContentPDF(){
//		return takePDFContent(pathFileValue)
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param
//	 * @return
//	 */
//	public static boolean validaElemento(String xpath){
//		boolean result
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			WebElement element = driver.findElement(By.xpath(xpath))
//			result = element.enabled.booleanValue()
//		}  catch (Exception e) {
//			result = false
//		}
//		return result
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param seguroNovo
//	 * @return
//	 */
//	public static boolean validaElementoIFrame(String xpath){
//		boolean result
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			driver.switchTo().frame("appArea")
//			result = driver.findElement(By.xpath(xpath)).enabled.booleanValue()
//		} catch (Exception e) {
//			result = false
//		}
//		return result
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Método pega qualquer valor na tela
//	 * @param xpath
//	 * @return
//	 */
//	private static String pegaValorDaTela(String xpath ){
//		def valorTela
//		try{
//			TestObject flexibleTestObject = new TestObject()
//			valorTela = WebUI.getText(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, xpath)).toString()
//			return valorTela
//		} catch ( Exception e) {
//			valorTela = null
//			return valorTela
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 */
//	public static clicarEmElementoDaTela(String xpath, int dalayfor){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			driver.findElement(By.xpath(xpath)).click()
//			WebUI.delay(dalayfor)
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT"),"Não foi possível clicar no elemento da tela.","FAIL")
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 */
//	public static clicarEmElementoDaTelaList(String xpath, int dalayfor){
//		WebDriver driver = DriverFactory.getWebDriver()
//		driver.findElement(By.xpath(xpath)).click()
//		WebUI.delay(dalayfor)
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 */
//	public static clicarEmElementoDaTelaFrame(String xpath, int dalayfor){
//		try{
//			WebUI.delay(dalayfor)
//			WebDriver driver = DriverFactory.getWebDriver()
//			driver.switchTo().frame("appArea");
//			driver.findElement(By.xpath(xpath)).click()
//			WebUI.delay(dalayfor)
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT"),"Não foi possível clicar no elemento da tela","FAIL")
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 */
//	public static clicarEmElementoDaTelaDeNovaJanela(String xpath, int dalayfor){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			Set<String> handles = driver.getWindowHandles()
//			String originalWindow = driver.getWindowHandle();
//			Iterator <String> iterator = handles.iterator();
//			while(iterator.hasNext()) {
//				String newWindow = iterator.next();
//				if(!originalWindow.equalsIgnoreCase(newWindow)){
//					driver.switchTo().window(newWindow);
//				}
//			}
//			driver.findElement(By.xpath(xpath)).click()
//			WebUI.delay(dalayfor)
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT"),"Não foi possível clicar no elemento da tela","FAIL")
//			System.err.println(e)
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 */
//	public static String retornaValorDeElementoDaTelaDeNovaJanela(String xpath, int dalayfor){
//		String valor
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			Set<String> handles = driver.getWindowHandles()
//			String originalWindow = driver.getWindowHandle();
//			Iterator <String> iterator = handles.iterator();
//			while(iterator.hasNext()) {
//				String newWindow = iterator.next();
//				if(!originalWindow.equalsIgnoreCase(newWindow)){
//					driver.switchTo().window(newWindow);
//				}
//			}
//			WebElement element = driver.findElement(By.xpath(xpath))
//			valor = element.getText().toString()
//			WebUI.delay(dalayfor)
//		} catch (Exception e){
//			valor = null
//			salvarEvidencia(issue_info.get(0).get("CT"),"Não foi possível retornar o valor do elemento da tela","FAIL")
//			System.err.println(e)
//			finalizar()
//		}
//		return valor
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 */
//	public static boolean validaElementoNovaJanela(String xpath, int dalayfor){
//		boolean result
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			Set<String> handles = driver.getWindowHandles()
//			String originalWindow = driver.getWindowHandle();
//			Iterator <String> iterator = handles.iterator();
//			while(iterator.hasNext()) {
//				String newWindow = iterator.next();
//				if(!originalWindow.equalsIgnoreCase(newWindow)){
//					driver.switchTo().window(newWindow);
//				}
//			}
//			WebElement element = driver.findElement(By.xpath(xpath))
//			result = element.enabled.booleanValue()
//			WebUI.delay(dalayfor)
//		} catch (Exception e){
//			result = false
//		}
//		return result
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static switchToNewWindow(){
//		WebDriver driver = DriverFactory.getWebDriver()
//		Set<String> handles = driver.getWindowHandles()
//		String originalWindow = driver.getWindowHandle();
//		Iterator <String> iterator = handles.iterator();
//		while(iterator.hasNext()) {
//			String newWindow = iterator.next();
//			if(!originalWindow.equalsIgnoreCase(newWindow)){
//				driver.switchTo().window(newWindow);
//			}
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static windowMaximize(){
//		WebDriver driver = DriverFactory.getWebDriver()
//		driver.manage().window().maximize()
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param nome
//	 */
//	public static clicarBotaoFrame(String nome){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			WebElement element = driver.findElement(By.xpath("//div[contains(text(),'"+nome+"')]"));
//			element.click()
//			WebUI.delay(10)
//		}   catch (Exception e){
//			def ct = issue_info.get(0).get("CT")
//			salvarEvidencia(ct," Não foi possível clicar no botão "+nome, "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @param tag
//	 * Retorna uma lista de elementos
//	 * @return
//	 */
//	public static List<WebElement> listaDeElementos(String xpath, String tag){
//		WebDriver driver = DriverFactory.getWebDriver()
//		WebElement Table = driver.findElement(By.xpath(xpath))
//		List<WebElement> rows_table = Table.findElements(By.tagName(tag))
//		return rows_table
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @param tag
//	 * Retorna uma lista de elementos
//	 * @return
//	 */
//	public static List<WebElement> listaDeElementosIFrame(String xpath, String tag){
//		WebDriver driver = DriverFactory.getWebDriver()
//		driver.switchTo().frame("appArea");
//		WebElement Table = driver.findElement(By.xpath(xpath))
//		List<WebElement> rows_table = Table.findElements(By.tagName(tag))
//		return rows_table
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Retorna todos os elementos da tag label
//	 * @return
//	 */
//	public static efetuarLeituraDeTodosOsLabelsCotacaoAuto(){
//		try{
//			String xpath="//div[contains(@id,'content_null')]"
//			String tag = "label"
//			selecionaListaDeElementos(xpath, tag)
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT"),"Falha ao efetuar a leitura de todos os labels de Cotação Auto","FAIL")
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * Retorna valor da tela, caso exista
//	 * @return
//	 */
//	public static String retornaValorDaTela(String xpath, int withTimeout, int pollingEvery, int pageLoadTimeout){
//		def ValorDaTela
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//					.withTimeout(withTimeout, TimeUnit.SECONDS)
//					.pollingEvery(pollingEvery, TimeUnit.SECONDS)
//					.ignoring(NoSuchElementException.class);
//			driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
//			ValorDaTela = driver.findElement(By.xpath(xpath)).getText();
//		} catch (Exception e){
//			ValorDaTela = null
//		}
//		return  ValorDaTela
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 * Posiciona a tela no elemento selecionado
//	 */
//	public static posicaoDaTela(String xpath){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			WebElement Element = driver.findElement(By.xpath(xpath));
//			JavascriptExecutor js = (JavascriptExecutor)driver;
//			js.executeScript("arguments[0].scrollIntoView();", Element);
//		}  catch (Exception e){e}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 */
//	public static limparCamposInput(String xpath){
//		WebDriver driver = DriverFactory.getWebDriver()
//		WebElement Element = driver.findElement(By.xpath(xpath));
//		Element.clear()
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 */
//	public static escreverCamposInput(String xpath, String value){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			WebElement Element = driver.findElement(By.xpath(xpath));
//			Element.sendKeys(value)
//		} catch (Exception e) {
//			salvarEvidencia(issue_info.get(0).get("CT"),"Não foi possível escrever no campos Input","FAIL")
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 */
//	public static escreverCamposFrame(String xpath, String value, int dalayfor){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			driver.switchTo().frame("appArea");
//			WebElement Element = driver.findElement(By.xpath(xpath));
//			Element.sendKeys(value)
//			WebUI.delay(dalayfor)
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT"),"Não foi possível escrever no campos Input (Frame)","FAIL")
//			finalizar()
//		}
//	}
//
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 */
//	public static escreverCamposInputList(String xpath, String value){
//		WebDriver driver = DriverFactory.getWebDriver()
//		WebElement Element = driver.findElement(By.xpath(xpath));
//		Element.sendKeys(value)
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param valor
//	 * @param rm
//	 * @return
//	 */
//	public static String removeValores(String valor, String rm){
//		StringBuffer strBuffer = new StringBuffer();
//		String[] lstr = valor.split("")
//		for(int i=0; i < lstr.size(); i++ )
//		{
//			if( !lstr[i].toString().equals(rm))
//			{
//				strBuffer.append(lstr[i].toString())
//			}
//		}
//		return strBuffer.toString()
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param valor
//	 * @return
//	 */
//	public static porcentagemDeTela(int valor){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			JavascriptExecutor executor = (JavascriptExecutor)driver;
//			executor.executeScript("document.body.style.zoom = '"+valor+"%'");
//		}  catch (Exception e){e}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 */
//	public static highlightElement(String xpath){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			WebElement element = driver.findElement(By.xpath(xpath));
//			((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", element )
//		}  catch (Exception e){e}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 */
//	public static unHighlightElement(String xpath){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			WebElement element = driver.findElement(By.xpath(xpath));
//			((JavascriptExecutor)driver).executeScript("arguments[0].style.border='0px'", element )
//		} catch (Exception e){e}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * Escreve em campo input auto complete e seleciona o primeiro valor
//	 */
//	public static void selectOptionWithAutoCompleteInPutText(String xpath, String text) {
//		escreverCamposInput(xpath, text )
//		WebUI.delay(5)
//		WebDriver driver = DriverFactory.getWebDriver()
//		String multiSelect = Keys.chord(Keys.DOWN, Keys.ENTER);
//		driver.findElement(By.xpath(xpath)).sendKeys(multiSelect);
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 */
//	public static esperarElementoCarregarNaTela(String xpath){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			WebDriverWait wait = new WebDriverWait(driver, 1);
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)))
//		} catch (Exception e){e}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static commandTAB(String xpath){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			WebElement Element = driver.findElement(By.xpath(xpath))
//			Element.sendKeys(Keys.TAB)
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao efetuar TAB no elemento", "FAIL" )
//			finalizar()
//		}
//	}
//
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static commandTABList(String xpath){
//		WebDriver driver = DriverFactory.getWebDriver()
//		WebElement Element = driver.findElement(By.xpath(xpath))
//		Element.sendKeys(Keys.TAB)
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static commandCLICK(String xpath){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			WebElement Element = driver.findElement(By.xpath(xpath))
//			Element.click()
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao clicar no elemento", "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static commandCLICKList(String xpath){
//		WebDriver driver = DriverFactory.getWebDriver()
//		WebElement Element = driver.findElement(By.xpath(xpath))
//		Element.click()
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
////	public static dataDeVigencia(){
////		try{
////			auxiliares.NavegarDatas.dataAtual(commands.Login.getUltimoAcesso(), issue_info.get(0).get("chave"))
////		} catch (Exception e){
////			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao executar o teste, não foi possível selecionar a data da vigência", "FAIL" )
////			finalizar()
////		}
////	}
//
////	/**
////	 * @author T_DCDias
////	 * @return
////	 */
////	public static navegacaoDatasIneriores(){
////		auxiliares.NavegarDatas.dataInferior(commands.Login.getUltimoAcesso())
////	}
//
//	/**
//	 * @author T_DCDias
//	 */
//	public static selecionaListaDeElementos(String xpath, String tag){
//		listOfElements = listaDeElementos(xpath, tag)
//	}
//
//	/**
//	 * @author T_DCDias
//	 */
//	public static selecionaListaDeElementosIFrame(String xpath, String tag){
//		listOfElements = listaDeElementosIFrame(xpath, tag)
//	}
//
////	/**
////	 * @author T_DCDias
////	 * @return
////	 */
////	public static acessaHomePage(DataTable data){
////		issue_info =  data.asMaps(String.class, String.class);
////		def ct = issue_info.get(0).get("CT")
////		leituraMassa(ct)
////		createDiretory(createDiretorypath+issue_info.get(0).get("topico") )
////		createDocument(ct, pathEvidences+issue_info.get(0).get("topico"))
////		createTitlePage()
////		try{
////			commands.Login.homePage(retornaURL(url))
////			commands.Login.efetuarLogin(casodeteste.getUsuario(), casodeteste.getSenha())
////			commands.Login.acessarHome()
////			commands.Login.pegaUltimoAcesso()
////		} catch (Exception e)
////		{
////			salvarEvidencia(ct," Falha ao executar o teste", "FAIL" )
////			finalizar()
////		}
////	}
//
////	/**
////	 * @author T_DCDias
////	 * @return
////	 */
////	public static efetuarLogin(){
////		commands.Login.efetuarLogin(casodeteste.getUsuario(), casodeteste.getSenha())
////	}
//
////	/**
////	 * @author T_DCDias
////	 * @return
////	 */
////	public static acessarHome(){
////		commands.Login.acessarHome()
////	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static clicaBotao(){
//		WebUI.click(findTestObject('PageAuto/Page_Home/button_Nova Cotao'))
//		WebUI.delay(2)
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static selecionaOpcaoAuto(){
//		try{
//			WebUI.click(findTestObject('PageAuto/Page_Home/div_Automvel'))
//			WebUI.delay(2)
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao selecionar a opção Nova Cotação", "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static preencheCpfCnpj(){
//		try{
//			WebUI.switchToFrame(findTestObject('PageAuto/Page_app/iframe_Incio_appArea'), 20)
//			WebUI.waitForElementPresent(findTestObject('PageAuto/Page_app/input_CPF ou CNPJ do segurado'),20)
//			WebUI.delay(5)
//			WebUI.setText(findTestObject('PageAuto/Page_app/input_CPF ou CNPJ do segurado'), casodeteste.getCpfcnpj())
//		} catch ( Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao preencher Cpf Cnpj", "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param valor
//	 * @return
//	 */
//	public static preencheCpfCnpjSemIFrame(){
//		WebUI.delay(20)
//		limparCamposInput("//input[@id='cpfCnpjSegurado']")
//		escreverCamposInput("//input[@id='cpfCnpjSegurado']", casodeteste.getCpfcnpj())
//		WebUI.delay(1)
//		commandTAB("//input[@id='cpfCnpjSegurado']")
//	}
//
//	/**
//	 * @author T_DCDias
//	 */
//	public static preencheCamposObrigatoriosCotacaoAutoUmIFrame(){
//		try{
//			WebUI.switchToFrame(findTestObject('PageAuto/Page_app/iframe_Incio_appArea'), 20)
//			preencheCamposObrigatorios()
//		}   catch (Exception e){
//			posicaoDaTela("//h2[text()='Preencha os dados do Segurado e Condutor']")
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao preencher campos obrigatorios", "FAIL" )
//			finalizar()
//		}
//	}
//
//
//	public static preencheCamposObrigatoriosCotacaoAutoUmIFrameSemPlacaChassi(){
//
//		try{
//			//WebUI.switchToFrame(findTestObject('PageAuto/Page_app/iframe_Incio_appArea'), 20)
//			preencheCamposObrigatoriosSemPlacaChassi();
//		}   catch (Exception e){
//			posicaoDaTela("//h2[text()='Preencha os dados do Segurado e Condutor']")
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao preencher campos obrigatorios", "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static preencherNumeroCI(){
//		try{
//			escreverCamposInput("//input[@id='numeroCI']", casodeteste.getNumeroci())
//			commandTAB("//input[@id='numeroCI']")
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao preencher campo Numero CI", "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static preencherNumeroDoChassi(){
//		try{
//			WebUI.switchToFrame(findTestObject('PageAuto/Page_app/iframe_Incio_appArea'), 20)
//			WebUI.waitForElementPresent(findTestObject('PageAuto/Page_app/input_CPF ou CNPJ do segurado'),20)
//			WebUI.delay(10)
//			WebUI.setText(findTestObject('PageAuto/Page_app/input_CPF ou CNPJ do segurado'), casodeteste.getCpfcnpj())
//			WebUI.setText(findTestObject('PageAuto/Page_app/input_Chassis do veculo'), casodeteste.getChassis())
//			WebUI.sendKeys(findTestObject('PageAuto/Page_app/input_Chassis do veculo'), Keys.chord(Keys.TAB))
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao preencher campo Numero do CHassi", "FAIL" )
//			finalizar()
//		}
//	}
//
//
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static preencherNumeroDoChassiSemIFrame(){
//		try{
//			WebUI.delay(5)
//			limparCamposInput('//*[@id="chassis"]')
//			escreverCamposInput('//*[@id="chassis"]', casodeteste.getChassis())
//			commandTAB('//*[@id="chassis"]')
//			WebUI.delay(5)
//			commandCLICK("//div[contains(@id,'FipeListLightbox_row_' ) ]")
//			WebUI.delay(5)
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao preencher campo Numero do CHassi", "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 */
//	public static substituiCampoPlaca(String placa){
//		try{
//			WebUI.delay(5)
//			limparCamposInput("//input[@id='placa']")
//			escreverCamposInput("//input[@id='placa']", placa)
//			commandCLICK("//input[ contains(@name,'chassis' ) ]")
//			WebUI.delay(5)
//			commandCLICK("//div[contains(@id,'FipeListLightbox_row_' ) ]")
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao preencher campo Placa", "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 */
//	public static substituiCampoPlaca(){
//		try{
//			WebUI.delay(5)
//			limparCamposInput("//input[@id='placa']")
//			escreverCamposInput("//input[@id='placa']", casodeteste.getPlaca())
//			commandCLICK("//input[ contains(@name,'chassis' ) ]")
//			WebUI.delay(5)
//			commandCLICK("//div[contains(@id,'FipeListLightbox_row_' ) ]")
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao preencher campo Placa", "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static botaoAvançar(){
//		finalDaPagina()
//		def botao = "//button[@id='NextButton']"
//		try{
//			clicarEmElementoDaTela(botao, 10)
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao executar o teste, botão 'Avançar' não encontrado", "FAIL" )
//			finalizar()
//		}
//
//		'Valida mensagem de erro na tela'
//		validarMensagemDeErro()  // verificar outra forma de validar a msg de erro com menos de 30 segundos
//	}
//
//	/**
//	 * @author T_DVLopes
//	 * @return
//	 */
//	public static botaoAvançarSemDalay(){
//		try{
//			commandCLICK("//button[@id='NextButton']")
//		}catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT"), 'Não foi possivel clicar botão Avançar', "FAIL" );
//			finalizar();
//		}
//	}
//
//
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static botaoComprar(){
//		def botao = "//div[contains(@class,'button-div')]//button[contains(@onclick,'executeBuyPackage(37)') and contains(@id,'button_0') and not(contains(@id,'footerbutton_0'))] "
//		clicarEmElementoDaTela(botao, 8)
//	}
//
////	/**
////	 * @author T_DCDias
////	 * @return
////	 */
////	public static calculoVigencia(){
////		try{
////			WebUI.delay(5)
////			def dias = commands.AutoComandos.calculoVigenciaDaApolice()
////			def totalDeDias = Integer.parseInt(issue_info.get(0).get("vigencia"))
////			if(dias==totalDeDias){
////				highlightElementCalculoVigencia()
////				salvarEvidencia(issue_info.get(0).get("CT")," Período de vigência ["+dias+"] dias", "PASSED")
////				unHighlightElementCalculoVigencia()
////			} else if(dias>totalDeDias) {
////				salvarEvidencia(issue_info.get(0).get("CT"),"  Período de vigência ["+dias+"] dias", "FAIL")
////			} else if (dias<totalDeDias){
////				salvarEvidencia(issue_info.get(0).get("CT")," Período de vigência ["+dias+"] dias", "FAIL")
////			}
////		} catch (Exception e){
////			salvarEvidenciaPDF(issue_info.get(0).get("CT")," Não foi possível calcular o período de vigência ", "FAIL")
////			finalizar()
////		}
////	}
////
////	/**
////	 * @author T_DCDias
////	 * @return
////	 */
////	public static calculoVigenciaPDF(){
////		try{
////			WebUI.delay(5)
////			def dias = commands.AutoComandos.calculoVigenciaDaApolice()
////			def totalDeDias = Integer.parseInt(issue_info.get(0).get("vigencia"))
////
////			System.err.println("Dias "+dias+" totalDeDias "+totalDeDias)
////
////			if(dias==totalDeDias){
////				salvarEvidenciaPDF(issue_info.get(0).get("CT")," Período de vigência ["+totalDeDias+"] dias", "PASSED")
////			} else if(dias>totalDeDias) {
////				salvarEvidenciaPDF(issue_info.get(0).get("CT"),"  Período de vigência superior à ["+totalDeDias+"] dias", "FAIL")
////			} else if (dias<totalDeDias){
////				salvarEvidenciaPDF(issue_info.get(0).get("CT")," Período de vigência inferior à ["+totalDeDias+"] dias", "FAIL")
////			}
////		} catch (Exception e){
////			salvarEvidenciaPDF(issue_info.get(0).get("CT")," Não foi possível calcular o período de vigência ", "FAIL")
////			finalizar()
////		}
////	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static highlightElementCalculoVigencia(){
//		highlightElement("//label[@id='validity']")
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static unHighlightElementCalculoVigencia(){
//		unHighlightElement("//label[@id='validity']")
//	}
//
//
////	/**
////	 * @author T_DCDias
////	 * @return
////	 */
////	public static identificaCampoDesabilitado(){
////		try{
////			WebUI.delay(4)
////			def command = commands.AutoComandos.identificaCampoDataDesabilitado(commands.Login.getUltimoAcesso()).toString()
////			if(command.equals("true")){
////				salvarEvidencia(issue_info.get(0).get("CT")," Campo dia anterior é exibido desabilitado para seleção ", "PASSED")
////			} else {
////				salvarEvidencia(issue_info.get(0).get("CT")," Campo dia anterior é exibido habilitado para seleção ", "FAIL")
////			}
////		} catch (Exception e){
////			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao identificar campo data desabilitado ", "FAIL")
////			finalizar()
////		}
////
////	}
//
////	/**
////	 * @author T_DCDias
////	 * @param seguroNovo
////	 * Seleciona opção do combo tipo de seguro
////	 * @return
////	 */
////	public static selecionaCcomboTipoDeSeguro(String tipoDeSeguro){
////		flexibleTestObject = new TestObject()
////		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, commands.AutoComandos.comboTipoDeSeguroEmCotacao(tipoDeSeguro)))
////	}
//
//	/**
//	 * @author T_DCDias
//	 * Valida elemento bloqueado
//	 * @return
//	 */
//	public static boolean validarComboTipoDeSeguroBloqueado(){
//		def xpath = "//div[contains(@class,'btn-group bootstrap-select block disabled')]//button[contains(@data-id,'comboSeguro')]"
//		return validaElemento(xpath)
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static validaElementoRenovaçãoDeOutraSeguradoraComSinistroBloqueado(){
//		boolean bloqueado = validarComboTipoDeSeguroBloqueado()
//		System.err.println("-- ["+bloqueado+"] -- ")
//		if( bloqueado == true){
//			highlightElementRenovaçãoDeOutraSeguradoraComSinistroBloqueado()
//			salvarEvidencia(issue_info.get(0).get("CT")," Elemento bloqueado Renovação de outra seguradora com sinistro ", "PASSED")
//			unHighlightElementRenovaçãoDeOutraSeguradoraComSinistroBloqueado()
//		} else if(bloqueado == false){
//			salvarEvidencia(issue_info.get(0).get("CT")," Elemento desbloqueado Renovação de outra seguradora com sinistro ", "FAIL")
//		}
//	}
//
//	public static highlightElementRenovaçãoDeOutraSeguradoraComSinistroBloqueado(){
//		highlightElement("//div[contains(@class,'btn-group bootstrap-select block disabled')]//button[contains(@data-id,'comboSeguro')]")
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static unHighlightElementRenovaçãoDeOutraSeguradoraComSinistroBloqueado(){
//		unHighlightElement("//div[contains(@class,'btn-group bootstrap-select block disabled')]//button[contains(@data-id,'comboSeguro')]")
//	}
//
////	/**
////	 * @author T_DCDias
////	 * @return
////	 */
////	public static validaElementoRenovaçãoDeOutraSeguradoraSemSinistroDesbloqueado(){
////		boolean bloqueado = commands.AutoComandos.validarComboTipoDeSeguroDesbloqueado()
////		System.err.println("-- ["+bloqueado+"] -- ")
////		if( bloqueado == false){
////			salvarEvidencia(issue_info.get(0).get("CT")," Elemento bloqueado Renovação de outra seguradora sem sinistro ", "FAIL")
////		} else if(bloqueado == true){
////			highlightElementRenovaçãoDeOutraSeguradoraSemSinistroDesbloqueado()
////			salvarEvidencia(issue_info.get(0).get("CT")," Elemento desbloqueado Renovação de outra seguradora sem sinistro ", "PASSED")
////			unHighlightElementRenovaçãoDeOutraSeguradoraSemSinistroDesbloqueado()
////		}
////	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static highlightElementRenovaçãoDeOutraSeguradoraSemSinistroDesbloqueado(){
//		highlightElement("//button[@data-id='comboSeguro']")
//	}
//
//	/**
//	 * @author T_DCDias]
//	 * @return
//	 */
//	public static unHighlightElementRenovaçãoDeOutraSeguradoraSemSinistroDesbloqueado(){
//		unHighlightElement("//button[@data-id='comboSeguro']")
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Valida elemento desbloqueado
//	 * @return
//	 */
//	public static boolean validarComboTipoDeSeguroDesbloqueado(){
//		def xpath = "//div[contains(@class,'btn-group bootstrap-select block')]//button[contains(@data-id,'comboSeguro')]"
//		return validaElemento(xpath)
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static selecionaTipoDeSeguroEmCotacao(){
//		if(issue_info.get(0).get("seguro").equals("Nova Cotação")){
//			clicarTipoDeSeguroEmCotacaoFormularioNaoPreenchido()
//		} else if (issue_info.get(0).get("seguro").equals("Renovação")) {
//			clicarTipoDeSeguroEmCotacaoFormularioPreenchido()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static validarLabelClasseDeBonus(){
//		if(validaLabelClasseDeBonus() == true){
//			validaLabelClasseDeBonus = true
//			highlightElementClasseDeBonus()
//			salvarEvidencia(issue_info.get(0).get("CT")," Labels 'Qual a sua classe de bônus?' encontrado ", "PASSED")
//			unHighlightElementClasseDeBonus()
//		} else {
//			salvarEvidencia(issue_info.get(0).get("CT")," Label 'Qual a sua classe de bônus?' não encontrado ", "FAIL")
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static highlightElementClasseDeBonus(){
//		highlightElement("//label[@id='classeBonusTxt']")
//		highlightElement("//button[@data-id='classeBonus']")
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static unHighlightElementClasseDeBonus(){
//		unHighlightElement("//label[@id='classeBonusTxt']")
//		unHighlightElement("//button[@data-id='classeBonus']")
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 * Validar campo 'Número da CI'.
//	 */
//	public static validarLabelNumeroDaCI(){
//		if( validaLabelNumeroDaCI() == true && validaLabelClasseDeBonus == true){
//			highlightElementNumeroCI()
//			salvarEvidencia(issue_info.get(0).get("CT")," Labels 'Qual a sua classe de bônus? 'Número da CI' encontrados ", "PASSED")
//			unHighlightElementNumeroCI()
//		} else if( validaLabelNumeroDaCI() == false && validaLabelClasseDeBonus == true) {
//			salvarEvidencia(issue_info.get(0).get("CT")," Label 'Número da CI' não encontrado ", "FAIL")
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static highlightElementNumeroCI(){
//		highlightElement("//div[@id='sectionNumeroCI']")
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static unHighlightElementNumeroCI(){
//		unHighlightElement("//div[@id='sectionNumeroCI']")
//	}
//
//	/**
//	 * @author T_DCDias
//	 *
//	 * @return
//	 */
//	public static apenasClicarTipoDeSeguroEmCotacao(){
//		if( issue_info.get(0).get("seguro").equals("Nova Cotação") ){
//			apenasClicarTipoDeSeguroEmCotacaoFormularioNaoPreenchido()
//		} else if ( issue_info.get(0).get("seguro").equals("Renovação") ){
//			apenasClicarTipoDeSeguroEmCotacaoFormularioPreenchido()
//		}else if ( issue_info.get(0).get("seguro").equals("Endosso") ){
//			apenasClicarTipoDeSeguroEmCotacaoFormularioPreenchido()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Apenas clica no combo para formulario cotação auto preenchida
//	 * @return
//	 */
//	public static apenasClicarTipoDeSeguroEmCotacaoFormularioPreenchido(){
//		try{
//			WebUI.delay(8)
//			clicarEmElementoDaTela('//*[@id="form-wrapper"]/div/div/div[2]/div/div/button', 2)
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao seleciona Tipo de Seguro", "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Apenas clica no combo
//	 * @return
//	 */
//	public static apenasClicarTipoDeSeguroEmCotacaoFormularioNaoPreenchido(){
//		try{
//			WebUI.switchToFrame(findTestObject('PageAuto/Page_app/iframe_Incio_appArea'), 20)
//			WebUI.waitForElementPresent(findTestObject('PageAuto/Page_app/input_CPF ou CNPJ do segurado'),20)
//			WebUI.delay(5)
//			WebUI.setText(findTestObject('PageAuto/Page_app/input_CPF ou CNPJ do segurado'), casodeteste.getCpfcnpj())
//			WebUI.delay(8)
//			clicarEmElementoDaTela('//*[@id="form-wrapper"]/div/div/div[2]/div/div/button', 5)
//		}   catch (Exception e){
//			def ct = issue_info.get(0).get("CT")
//			salvarEvidencia(ct," Falha ao clicar no combo Tipo de Seguro", "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 *
//	 * @return
//	 */
//	public static clicarTipoDeSeguroEmCotacao(){
//		if( issue_info.get(0).get("seguro").equals("Nova Cotação") ){
//			clicarTipoDeSeguroEmCotacaoFormularioNaoPreenchido()
//		} else if ( issue_info.get(0).get("seguro").equals("Renovação") ){
//			clicarTipoDeSeguroEmCotacaoFormularioPreenchido()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Clica no combo e seleciona o 'Tipo de seguro'
//	 * @return
//	 */
//	public static clicarTipoDeSeguroEmCotacaoFormularioNaoPreenchido(){
//		try{
//			WebUI.switchToFrame(findTestObject('PageAuto/Page_app/iframe_Incio_appArea'), 20)
//			WebUI.waitForElementPresent(findTestObject('PageAuto/Page_app/input_CPF ou CNPJ do segurado'),20)
//			WebUI.delay(5)
//			WebUI.setText(findTestObject('PageAuto/Page_app/input_CPF ou CNPJ do segurado'), casodeteste.getCpfcnpj())
//			WebUI.delay(8)
//			//WebUI.click(findTestObject('PageAuto/Page_app/listTipoDeSeguroEmCotacao'))
//			clicarEmElementoDaTela('//*[@id="form-wrapper"]/div/div/div[2]/div/div/button', 5)
//			//clicarEmElementoDaTela('//*[@id="form-wrapper"]/div/div/div[2]/div/div/div/ul/li[4]/a/span[1]', 2)
//			selecionaCcomboTipoDeSeguro(issue_info.get(0).get("tipo"))
//		}   catch (Exception e){
//			def ct = issue_info.get(0).get("CT")
//			salvarEvidencia(ct," Falha ao seleciona Tipo de Seguro", "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Clica no combo e seleciona o 'Tipo de seguro' para formulario cotação auto preenchida
//	 * @return
//	 */
//	public static clicarTipoDeSeguroEmCotacaoFormularioPreenchido(){
//		try{
//			WebUI.delay(8)
//			clicarEmElementoDaTela('//*[@id="form-wrapper"]/div/div/div[2]/div/div/button', 2)
//			selecionaCcomboTipoDeSeguro(issue_info.get(0).get("tipo"))
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao seleciona Tipo de Seguro", "FAIL" )
//			finalizar()
//		}
//	}
//
////	/**
////	 * @author T_DCDias
////	 * @return
////	 */
////	public static selecionaClasseDeBonus(){
////		try{
////			WebUI.delay(2)
////			clicarEmElementoDaTela("//button[@data-id='classeBonus']", 2)
////			flexibleTestObject = new TestObject()
////			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, commands.AutoComandos.comboClasseDeBonus( casodeteste.getClasseDeBonus())))
////		} catch (Exception e){
////			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao seleciona Classe de Bonus", "FAIL" )
////			finalizar()
////		}
////	}
//
////	/**
////	 * @author T_DCDias
////	 * @return
////	 */
////	public static preencheCamposObrigatorioPagamento(){
////		"Dados do segurado"
////		WebUI.setText(findTestObject('PageAuto/PagePagamento/emailSegurado'), emailSegurado)
////		WebUI.setText(findTestObject('PageAuto/PagePagamento/telefone'), telefone)
////
////		// Fazer asjustes
////		//		WebUI.setText(findTestObject('PageAuto/PagePagamento/profissao'), profissao)
////		//		WebUI.delay(3)
////		//		flexibleTestObject = new TestObject()
////		//		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, "//input[contains(@id,'comboProfAuto')] //..//span[contains(@role,'status') and contains(text(),'Administrador')]" ))
////
////		WebUI.click(findTestObject('PageAuto/PagePagamento/comboRendaMensal'))
////		flexibleTestObject = new TestObject()
////		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, commands.AutoComandos.comboRendaMensal(rendaMensal)))
////
////		"Informações da conta"
////
////
////		"Número da apólice anterior"
////		WebUI.setText(findTestObject('PageAuto/PagePagamento/numerodaApoliceAnterior'), numerodaApoliceAnterior)
////
////		"Número da CI "
////		WebUI.setText(findTestObject('PageAuto/PagePagamento/numeroDaCI'), numeroDaCI)
////
////		"Fim da vigência anterior"
////		WebUI.click(findTestObject('PageAuto/PagePagamento/fimVigenciaAnterior'))
////		auxiliares.NavegarDatas.selecionaData(commands.Login.getUltimoAcesso())
////
////		WebUI.delay(3)
////		WebUI.click(findTestObject('PageAuto/PagePagamento/btAvancar'))
////
////		WebUI.delay(3)
////		WebUI.click(findTestObject('PageAuto/PagePagamento/btEmitir'))
////
////		WebUI.delay(3)
////		WebUI.click(findTestObject('PageAuto/PagePagamento/btProposta'))
////
////	}
//
//	/**
//	 * @author T_DCDias
//	 * Lista do estado civil do condutor
//	 * @param EstadoCivil
//	 * @return
//	 */
//	@Keyword
//	public static String comboEstadoCivil(String estadoCivil){
//		Map<String, String> xpaths = new HashMap<String, String>()
//		xpaths.put("sasado convite uniao estavel", "//li[contains(@data-original-index,'1' ) ] //a[contains(@tabindex,'0') ]//span[contains(text(),'Casado[a] ou convive em união estável')]")
//		xpaths.put("solteiro", "//li[contains(@data-original-index,'2' ) ] //a[contains(@tabindex,'0') ]//span[contains(text(),'Solteiro[a]')]")
//		xpaths.put("separado divorciado", "//li[contains(@data-original-index,'3' ) ] //a[contains(@tabindex,'0') ]//span[contains(text(),'Separado[a] / Divorciado[a]')]")
//		xpaths.put("viuvo", "//li[contains(@data-original-index,'4' ) ] //a[contains(@tabindex,'0') ]//span[contains(text(),'Viúvo[a]')]")
//		return xpaths.get(estadoCivil.toLowerCase()).toString();
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Lista de tipo de residências do condutor
//	 * @param residenciaCondutor
//	 * @return
//	 */
//	@Keyword
//	public static String comboResidenciaCondutor(String residenciaCondutor){
//		Map<String, String> xpaths = new HashMap<String, String>()
//		xpaths.put("casa", ".//*[contains(@data-id,'valorResideEm') and not(contains(@data-id,'qcab_valorResid'))]//.. //li[contains(@data-original-index,'1')] //span[contains(text(),'Casa')]")
//		xpaths.put("condomínio", ".//*[contains(@data-id,'valorResideEm') and not(contains(@data-id,'qcab_valorResid'))]//.. //li[contains(@data-original-index,'2')] //span[contains(text(),'Casa em condomínio fechado')]")
//		xpaths.put("apartamento", ".//*[contains(@data-id,'valorResideEm') and not(contains(@data-id,'qcab_valorResid'))]//.. //li[contains(@data-original-index,'3')] //span[contains(text(),'Apartamento')]")
//		return xpaths.get(residenciaCondutor.toLowerCase()).toString();
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Lista de tipo de seguro em cotação
//	 * @param seguroNovo
//	 * @return
//	 */
//	public static String comboTipoDeSeguroEmCotacao( String tipoDeseguro){
//		Map<String, String> xpaths = new HashMap<String, String>()
//		xpaths.put("seguro novo", '//*[@id="form-wrapper"]/div/div/div[2]/div/div/div/ul/li[1]/a/span[1]')
//		xpaths.put("renovação allianz sem sinistro", '//*[@id="form-wrapper"]/div/div/div[2]/div/div/div/ul/li[2]/a/span[1]')
//		xpaths.put("renovação allianz com sinistro", '//*[@id="form-wrapper"]/div/div/div[2]/div/div/div/ul/li[3]/a/span[1]')
//		xpaths.put("renovação de outra seguradora sem sinistro", '//*[@id="form-wrapper"]/div/div/div[2]/div/div/div/ul/li[4]/a/span[1]')
//		xpaths.put("renovação de outra seguradora com sinistro", '//*[@id="form-wrapper"]/div/div/div[2]/div/div/div/ul/li[5]/a/span[1]')
//		return xpaths.get(tipoDeseguro.toLowerCase()).toString();
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Lista classe de bônus do segurado
//	 * @param classeDeBonus
//	 * @return
//	 */
//	public static String comboClasseDeBonus( String classeDeBonus){
//		Map<String, String> xpaths = new HashMap<String, String>()
//		xpaths.put("0", "//div[contains(@class,'dropdown-menu open')]//..//span[text()='0']")
//		xpaths.put("1", "//div[contains(@class,'dropdown-menu open')]//..//span[text()='1']")
//		xpaths.put("2", "//div[contains(@class,'dropdown-menu open')]//..//span[text()='2']")
//		xpaths.put("3", "//div[contains(@class,'dropdown-menu open')]//..//span[text()='3']")
//		xpaths.put("4", "//div[contains(@class,'dropdown-menu open')]//..//span[text()='4']")
//		xpaths.put("5", "//div[contains(@class,'dropdown-menu open')]//..//span[text()='5']")
//		xpaths.put("6", "//div[contains(@class,'dropdown-menu open')]//..//span[text()='6']")
//		xpaths.put("7", "//div[contains(@class,'dropdown-menu open')]//..//span[text()='7']")
//		xpaths.put("8", "//div[contains(@class,'dropdown-menu open')]//..//span[text()='8']")
//		xpaths.put("9", "//div[contains(@class,'dropdown-menu open')]//..//span[text()='9']")
//		xpaths.put("10","//div[contains(@class,'dropdown-menu open')]//..//span[text()='10']")
//		return xpaths.get(classeDeBonus.toLowerCase()).toString();
//	}
//
//	/**
//	 *
//	 * @param rendaMensal
//	 * @return
//	 */
//	public static String comboRendaMensal( String rendaMensal){
//		Map<String, String> xpaths = new HashMap<String, String>()
//		xpaths.put("index1", "//ul[contains(@class,'dropdown-menu inner')]//..//span[text()='Até "+auxiliares.CurrencyMonetary.removeEspacos(auxiliares.CurrencyMonetary.currencyMonetaryValue("1500"))+"']")
//		xpaths.put("index2", "//ul[contains(@class,'dropdown-menu inner')]//..//span[text()='De "+auxiliares.CurrencyMonetary.currencyMonetaryValue("1501")+" Até "+auxiliares.CurrencyMonetary.removeEspacos(auxiliares.CurrencyMonetary.currencyMonetaryValue("3000"))+"']")
//		xpaths.put("index3", "//ul[contains(@class,'dropdown-menu inner')]//..//span[text()='De "+auxiliares.CurrencyMonetary.currencyMonetaryValue("3301")+" Até "+auxiliares.CurrencyMonetary.removeEspacos(auxiliares.CurrencyMonetary.currencyMonetaryValue("5000"))+"']")
//		xpaths.put("index4", "//ul[contains(@class,'dropdown-menu inner')]//..//span[text()='De "+auxiliares.CurrencyMonetary.currencyMonetaryValue("5001")+" Até "+auxiliares.CurrencyMonetary.removeEspacos(auxiliares.CurrencyMonetary.currencyMonetaryValue("10000"))+"']")
//		xpaths.put("index5", "//ul[contains(@class,'dropdown-menu inner')]//..//span[text()='Acima de "+auxiliares.CurrencyMonetary.removeEspacos(auxiliares.CurrencyMonetary.currencyMonetaryValue("10000"))+"']")
//		return xpaths.get(rendaMensal.toLowerCase()).toString();
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param reside
//	 * @return
//	 */
//	public static String tipoCondutorResideComPessoas(String reside){
//		Map<String, String> xpaths = new HashMap<String, String>()
//		xpaths.put("index1", "//div[@class='genericCol errorClass col-md-6 col-sm-8 col-xs-12 ws-invalid']//li[contains(@data-original-index,'4')]//..//span[contains(text(),'Reside com mulheres que pode[m] vir a utilizar o veículo segurado.')]")
//		xpaths.put("index2", "//div[@class='genericCol errorClass col-md-6 col-sm-8 col-xs-12 ws-invalid']//li[contains(@data-original-index,'4')]//..//span[contains(text(),'Reside com homens que pode[m] vir a utilizar o veículo segurado.')]")
//		xpaths.put("index3", "//div[@class='genericCol errorClass col-md-6 col-sm-8 col-xs-12 ws-invalid']//li[contains(@data-original-index,'4')]//..//span[contains(text(),'Reside com pessoas que pode[m] vir a usar o veículo segurado.')]")
//		xpaths.put("index4", "//div[@class='genericCol errorClass col-md-6 col-sm-8 col-xs-12 ws-invalid']//li[contains(@data-original-index,'4')]//..//span[contains(text(),'Reside com pessoas que não usam, em hipótese alguma, o veículo segurado.')]")
//		return xpaths.get(reside).toString();
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param reside
//	 * @return
//	 */
//	public static String tipoAgrupamentoDeRamos(String agrupamento){
//		Map<String, String> xpaths = new HashMap<String, String>()
//		xpaths.put("automóvel", "//ul[@role='menu']//li[@data-original-index='23'] //..//span[contains(text(),'Automóvel')]")
//		return xpaths.get(agrupamento.toLowerCase()).toString();
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param reside
//	 * @return
//	 */
//	public static String tipoVencimento(String vencimento){
//		Map<String, String> xpaths = new HashMap<String, String>()
//		xpaths.put("index1", "//ul[@role='menu']//li[@data-original-index='1'] //..//span[contains(text(),'2019-01')]")
//		xpaths.put("index2", "//ul[@role='menu']//li[@data-original-index='1'] //..//span[contains(text(),'2018-12')]")
//		return xpaths.get(vencimento.toLowerCase()).toString();
//	}
//
//	/**
//	 * @author T_DCDias
//	 *
//	 * @return
//	 */
//	public static String cotacaoDataVigencia(String data){
//		String action
//		if( data.equals(null)){
//			//	action="//button[@id='NextButton']"
//		}
//		else if(!data.equals(null)) {
//			action="//div[contains(@id,'dataInicioVigenciaSeguro_div')]//span[contains(@class,'input-group-addon')]//i[contains(@class,'allianz-icon allianz-icon-calendar')]"
//			//Criar navegação de datas
//		}
//		return action
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Método posiciona o scroll no final da pagina
//	 * @return
//	 */
//	public static finalDaPagina(){
//		try{
//			WebUI.delay(8)
//			WebUI.scrollToPosition(9999999, 9999999)
//			WebUI.delay(5)
//		} catch (Exception e){
//			System.err.println("Unable to scroll to position x = 9999999, y = 9999999")
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static inicioDaPagina(){
//		try{
//			WebUI.scrollToPosition(0000000, 0000000)
//		} catch (Exception e){
//			System.err.println("Unable to scroll to position x = 0000000, y = 0000000")
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param data
//	 * @return
//	 */
//	public static navegarDatas(){
//		auxiliares.NavegarDatas.navegar()
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static boolean identificaCampoDataDesabilitado(String ultimoacesso){
//		boolean existe
//		Calendar calendar = new GregorianCalendar()
//		int dia = calendar.get(calendar.DAY_OF_MONTH)
//		int nDia = dia-1
//		return auxiliares.NavegarDatas.validaDataBloqueada(dia)
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param sMenu
//	 * @return
//	 */
//	public static MenuPrincipal(){
//		WebUI.delay(20)
//		String sMenu = issue_info.get(0).get("Menu")
//		try{
//			Map<String, String> menu = new HashMap<String, String>()
//			menu.put("vendas", "//div[@id='menuPrincipal1']/a")
//			menu.put("consultas", "//div[@id='menuPrincipal2']/a")
//			menu.put("gestão", "//div[@id='menuPrincipal3']/a")
//			WebDriver driver = DriverFactory.getWebDriver()
//			driver.switchTo().defaultContent()
//			driver.findElement( By.xpath(menu.get(sMenu.toLowerCase()).toString()) ).click()
//		} catch (Exception e)
//		{
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível selecionar o menu "+sMenu, "FAIL")
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static subMenus(){
//		String sSubMenus = issue_info.get(0).get("Opção")
//		try{
//			Map<String, String> submenu = new HashMap<String, String>()
//			submenu.put("cotações", "//ul[@id='ulMenuPrincipal1']/li[1]/a")
//			submenu.put("endossos", "//ul[@id='ulMenuPrincipal1']/li[2]/a")
//			submenu.put("consultapedidodecotacao", "//ul[@id='ulMenuPrincipal1']/li[3]/a")
//			submenu.put("gestaorenovacoes", "//ul[@id='ulMenuPrincipal1']/li[4]/a")
//			flexibleTestObject = new TestObject()
//			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, submenu.get(sSubMenus.toLowerCase().toString())))
//		} catch (Exception e ){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível selecionar a opção "+sSubMenus, "FAIL")
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static subMenuEndossos(){
//		String submenuendossos = issue_info.get(0).get("SubOpção") //"Endosso"
//		try{
//			Map<String, String> submenuendosso = new HashMap<String, String>()
//			submenuendosso.put("endosso", "//ul[@id='ulMenuPrincipal1']/li[2]/ul/li/a[contains(text(),'Endosso')]")
//			submenuendosso.put("cancelamento", "//ul[@id='ulMenuPrincipal1']/li[2]/ul/li/a[contains(text(),'Cancelamento')]")
//			flexibleTestObject = new TestObject()
//			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, submenuendosso.get(submenuendossos.toLowerCase()).toString()))
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível selecionar a opção "+submenuendossos, "FAIL")
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static subMenuCotacoes(String  submenucotacoes){
//		try{
//			Map<String, String> submenucotacao = new HashMap<String, String>()
//			submenucotacao.put("auto", "//ul[@id='ulMenuPrincipal1']/li[1]/ul/li/a[contains(text(),'Auto')]")
//			submenucotacao.put("massificados", "//ul[@id='ulMenuPrincipal1']/li[1]/ul/li/a[contains(text(),'Massificados')]")
//			submenucotacao.put("grandes riscos", "//ul[@id='ulMenuPrincipal1']/li[1]/ul/li/a[contains(text(),'Grandes Riscos')]")
//			submenucotacao.put("agro", "//ul[@id='ulMenuPrincipal1']/li[1]/ul/li/a[contains(text(),'Agro')]")
//			submenucotacao.put("benefícios", "//ul[@id='ulMenuPrincipal1']/li[1]/ul/li/a[contains(text(),'Benefícios')]")
//			flexibleTestObject = new TestObject()
//			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, submenucotacao.get(submenucotacoes.toLowerCase()).toString()))
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi prossível selecionar o sub Menu Cotações", "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static subMenuCotacoesConsulta(){
//		String  submenucotacoes = issue_info.get(0).get("Opção")
//		WebUI.delay(2)
//		try{
//			Map<String, String> submenucotacao = new HashMap<String, String>()
//			submenucotacao.put("consultas", '//*[@id="ulMenuPrincipal2"]/li[1]/a')
//			submenucotacao.put("solicitacaocotacao", '//*[@id="ulMenuPrincipal2"]/li[2]/a')
//			submenucotacao.put("paineldeavisos", '//*[@id="ulMenuPrincipal2"]/li[3]/a')
//			submenucotacao.put("operaçõesdiarias", '//*[@id="ulMenuPrincipal2"]/li[4]/a')
//			submenucotacao.put("documentacaodiaria", '//*[@id="ulMenuPrincipal2"]/li[5]/a')
//			submenucotacao.put("diaallz", '//*[@id="ulMenuPrincipal2"]/li[6]/a')
//			flexibleTestObject = new TestObject()
//			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, submenucotacao.get(submenucotacoes.toLowerCase()).toString()))
//		}   catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi prossível selecionar o sub Menu Cotações Consulta", "FAIL" )
//			finalizar()
//		}
//	}
//
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static subMenusCotacoes(){
//		String sSubMenus = issue_info.get(0).get("SubOpção")
//		WebUI.delay(4)
//		try{
//			Map<String, String> submenu = new HashMap<String, String>()
//			submenu.put("consultasgerais", '//*[@id="ulMenuPrincipal2"]/li[1]/ul/li[3]/a')
//			submenu.put("cotacoesrealizadas", '//*[@id="ulMenuPrincipal2"]/li[1]/ul/li[1]/a')
//			submenu.put("centralbonus", '//*[@id="ulMenuPrincipal2"]/li[1]/ul/li[2]/a')
//			submenu.put("vistoriasprevias", '//*[@id="ulMenuPrincipal2"]/li[1]/ul/li[4]/a')
//			WebDriver driver = DriverFactory.getWebDriver()
//			driver.switchTo().defaultContent()
//			driver.findElement( By.xpath(submenu.get(sSubMenus.toLowerCase().toString())) ).click()
//			WebUI.delay(20)
//		}   catch (Exception e ){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível selecionar a opção "+sSubMenus, "FAIL")
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static informarApolice(){
//		try{
//			WebUI.delay(30)
//			WebDriver driver = DriverFactory.getWebDriver()
//			driver.switchTo().frame("appArea");
//			System.err.println("\nCT ["+casodeteste.getCt()+"] APÓLICE ["+casodeteste.getApolice()+"] EXECUTAR ["+casodeteste.getExecutar()+"]")
//			driver.findElement(By.xpath("//*[contains(@name,'showPolicyExt_SUSEP')]")).sendKeys(casodeteste.getApolice());
//			driver.findElement(By.xpath("//*[contains(@name,'showPolicyExt_SUSEP')]")).sendKeys(Keys.TAB);
//			WebUI.delay(5)
//		}   catch (Exception e ){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi prossível informar a apólice", "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static clicarNoBotaoEmitirDoFrame(){
//		WebUI.delay(20)
//		clicarBotaoFrameSwitchTo(issue_info.get(0).get("Botão"))
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static clicarNoBotaoAlterarDoFrame(){
//		try{
//			String xpath = '//*[@id="detalleSectionBar"]/table/tbody/tr/td'
//			String tag ="div"
//			String botao
//			WebUI.delay(5)
//			selecionaListaDeElementos(xpath,tag)
//			for(int i = 0; i < listOfElements.size(); i++){  System.err.println(i+" "+tag+" "+listOfElements.get(i))
//				if(listOfElements[i].getText().contains(issue_info.get(0).get("Botão"))){
//					botao = issue_info.get(0).get("Botão")
//				}
//				else if(listOfElements[i].getText().contains(issue_info.get(1).get("Botão"))){
//					botao = issue_info.get(1).get("Botão")
//				}
//			}
//			clicarBotaoFrame(botao)
//		}   catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível clicar no botão "+issue_info.get(0).get("Botão") , "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param nome
//	 */
//	public static clicarBotaoFrameSwitchTo(String nome){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			driver.switchTo().frame("appArea");
//			driver.findElement(By.xpath("//div[contains(text(),'"+nome+"')]")).click()
//			WebUI.delay(10)
//		}   catch (Exception e ){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível clicar no botão "+nome , "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static informaCotacao(){
//		WebUI.setText(findTestObject('PageAuto/Page_Home/input_Encerrar sesso_searcherG'), '64035173')
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static pesquisarCotacao(){
//		WebUI.delay(5)
//		WebUI.click(findTestObject('PageAuto/Page_Home/a_Cotao'))
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static emitirCotacao(){
//		WebUI.delay(15)
//		//String xpath = "//div[contains(@class,'sectionButton') and contains(@title,'Emitir') and contains(@id,'o_2') and contains(@data-mask,';mask:SectionButton;disabled:false;typeText:false;strictWidth:false;;') and contains(text(),'Emitir')]"
//		//String xpath = "//div[contains(text(),'Emitir')]"
//		//String xpath = "//div[contains(@id,'o_2')]"
//		//String xpath = "//*[contains(@id,'o_2')]"
//		//String xpath = "//td//div[contains(@id,'o_2')]"
//		//String xpath = "//*[local-name() = 'Emitir']"
//		//String xpath = "//label[contains(@id,'btnChangeStyle')]"
//		//String xpath = ".//*[contains(@id,'o_2')]"
//		String xpath = "//td//div[contains(text(),'Emitir')]"
//
//	}
//
//
//	/**
//	 *
//	 * @param rendaMensal
//	 * @return
//	 */
//	public static String botoesUtilizaVeiculoComercialmente(String botao){
//		Map<String, String> xpaths = new HashMap<String, String>()
//		xpaths.put("sim", "//button[@id='BComercialSim']")
//		xpaths.put("não", "//button[@id='BComercialNao']")
//		return xpaths.get(botao.toLowerCase()).toString();
//	}
//
//
//	/**
//	 *
//	 * @param rendaMensal
//	 * @return
//	 */
//	public static String botoesCondutorResideComPessoas(String botao){
//		Map<String, String> xpaths = new HashMap<String, String>()
//		xpaths.put("sim", "//div[@id='button17a25']//div[@id='B17a25Sim_container']//button[@id='B17a25Sim']")
//		xpaths.put("não", "//div[@class='alz-wm-bs-btn-desktop-container alz-wm-bs-btn-group active']//button[@id='B17a25Nao']")
//		return xpaths.get(botao.toLowerCase()).toString();
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Recuperar informações dp combo cotação
//	 * @return
//	 */
//	public static recuperarInformacoesComboCotacao(){
//		String xpath = "//div[contains(@class,'btn-group bootstrap-select block open')]//div[contains(@class,'dropdown-menu open')]//ul[contains(@class,'dropdown-menu inner') and contains(@role,'menu')]"
//		String tag = "span"
//		selecionaListaDeElementos(xpath, tag)
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 * Validar se não há duplicidade de informação nas opções para o combo "O seguro que você está cotando é"
//	 */
//	public static verificaDuplicidade(){
//		if(verificaDuplicidadesCombo()==0 ) {
//			salvarEvidencia(issue_info.get(0).get("CT")," Não existem valores duplicados para o combo 'O seguro que você está cotando é' ", "PASSED")
//		}else {
//			salvarEvidencia(issue_info.get(0).get("CT")," Existem valores duplicados para o combo 'O seguro que você está cotando é' ", "FAIL")
//		}
//		WebUI.delay(2)
//		WebUI.click(findTestObject('PageAuto/Page_app/listTipoDeSeguroEmCotacao'))
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param data
//	 * @return
//	 */
//	public static Integer verificaDuplicidadesCombo(){
//		int resp = verificaDuplicidadesEmCombo()
//		return resp
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Verifica se existe itens duplicados em um combo list
//	 * @return
//	 */
//	public static Integer verificaDuplicidadesEmCombo(){
//		final Map<String, Integer> valuefrequency = new HashMap<>();
//		def resultado = 0
//		def supplierNames = []
//		def qtd = []
//		for(int i = 0; i < listOfElements.size(); i++){
//			if(!listOfElements.get(i).getText().equals("")){
//				supplierNames.add(listOfElements.get(i).getText())
//			}
//		}
//
//		for(int i = 0; i < supplierNames.size(); i++){
//			def frequency = Collections.frequency(supplierNames, supplierNames[i])
//			valuefrequency.put( supplierNames[i] , frequency)
//			qtd.add(frequency)
//		}
//		println("Itens "+valuefrequency)
//		String valores = qtd;
//		String[] lstr = valores.split("")
//		for(int i=0; i < lstr.size(); i++ )
//		{
//			if( lstr[i].toString().equals("2") ||
//			lstr[i].toString().equals("3") ||
//			lstr[i].toString().equals("4") ||
//			lstr[i].toString().equals("5") ||
//			lstr[i].toString().equals("6") ||
//			lstr[i].toString().equals("7") ||
//			lstr[i].toString().equals("8") ||
//			lstr[i].toString().equals("9") )
//			{
//				resultado = 1
//			}
//		}
//		return resultado
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param label
//	 * @return
//	 */
//	public static boolean validaTipoDeLabel(String label){
//		boolean existe = false
//		for(int i = 0; i < listOfElements.size(); i++){
//			if(!listOfElements.get(i).getText().equals("")){
//				if(listOfElements.get(i).getText().equals(label)){
//					existe = true
//				}
//			}
//		}
//		return existe
//	}
//
//	/**
//	 * @author T_DCDias
//	 *
//	 * @return
//	 */
//	public static boolean  validaLabelClasseDeBonus(){
//		efetuarLeituraDeTodosOsLabelsCotacaoAuto()
//		WebUI.delay(2)
//		return validaTipoDeLabel(issue_info.get(0).get("campos"))
//	}
//
//	/**
//	 * @author T_DCDias
//	 *
//	 * @return
//	 */
//	public static boolean validaLabelNumeroDaCI(){
//		return validaTipoDeLabel(issue_info.get(1).get("campos"))
//	}
//
//	/**
//	 * @author T_DCDias
//	 *
//	 * @return
//	 */
//	public static boolean validaLabelTipoDeSegutoCotado(){
//		efetuarLeituraDeTodosOsLabelsCotacaoAuto()
//		return validaTipoDeLabel("O seguro que você está cotando é:")
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static verificaInformacaoDoLabelDoCombo(){
//		WebUI.delay(20)
//		if(validaLabelTipoDeSegutoCotado() == true){
//			highlightElementformacaoDoLabelDoCombo()
//			salvarEvidencia(issue_info.get(0).get("CT")," Label encontrado 'O seguro que você está cotando é:' ", "PASSED")
//			unHighlightElementformacaoDoLabelDoCombo()
//		} else {
//			salvarEvidencia(issue_info.get(0).get("CT")," Label não encontrado 'O seguro que você está cotando é:' ", "FAIL")
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static highlightElementformacaoDoLabelDoCombo(){
//		highlightElement("//label[text()='O seguro que você está cotando é:'] ")
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static unHighlightElementformacaoDoLabelDoCombo(){
//		unHighlightElement("//label[text()='O seguro que você está cotando é:'] ")
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static clicarEmGerarCotacao(){
//		try{
//			def xpath = "//*[contains(@id,'saveStudyIcon')]"
//			clicarEmElementoDaTela(xpath, 1)
//		} catch (Exception e){
//			// não precisa tratar a msg de erro
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static pegarNumeroDaCotacaoNoModal(){
//		try{
//			WebUI.delay(5)
//			def xpath = "//div//span[contains(@id,'studyNumberPopUp')]"
//			casodeteste.setCotacao(pegaValorDaTela(xpath))
//			System.err.println("Cotação capturada ["+casodeteste.getCotacao()+"]")
//		} catch (Exception e){}
//	}
//
////	/**
////	 * @author T_DCDias
////	 * @return
////	 */
////	public static fecharModal(){
////		try{
////			def xpath = "//div[contains(@id,'lightboxSalvaCotacao_header')]//button[contains(@class,'close alz-wm-lightbox-dialog-header-close custom-close')]"
////			clicarEmElementoDaTela(xpath, 5)
////			posicaoDaTela("//h2[text()='A Allianz tem o pacote certo para você.']")
////			WebUI.delay(1)
////			highlightElementNumeroDaCotacaoNoModal()
////			adicionaImagem(issue_info.get(0).get("CT"),"Número da cotação", "PASSED", 3)
////			unHighlightElementNumeroDaCotacaoNoModal()
////			WebUI.delay(2)
////			//posicaoDaTela('//*[@id="menuPrincipal2"]/a')
////			//			WebUI.delay(1)
////			//			WebUI.navigateToUrl(url)
////			//			WebUI.delay(10)
////			commands.Login.homePage(url)
////			commands.Login.efetuarLogin(casodeteste.getUsuario(), casodeteste.getSenha())
////			commands.Login.acessarHome()
////
////		} catch (Exception e) {}
////	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static highlightElementNumeroDaCotacaoNoModal(){
//		highlightElement("//label[@id='quotationNum']")
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static unHighlightElementNumeroDaCotacaoNoModal(){
//		unHighlightElement("//label[@id='quotationNum']")
//	}
//
//	/**
//	 * @author T_DCDias
//	 *
//	 * @return
//	 */
//	public static preencherCampoCotacaoEPesquisar(){
//		WebUI.delay(10)
//		System.err.println("-- Cotação capturada para pesquisa [ "+casodeteste.getCotacao()+" ] -- ")
//		String xpath = '//*[@id="txtPresupuesto"]'
//		String xpathFrame = '//*[@id="sectionBContent"]'
//		String tag ="div"
//		String botao
//		try{
//			escreverCamposFrame(xpath, casodeteste.getCotacao(), 1)
//			selecionaListaDeElementos(xpathFrame,tag)
//			System.err.println("listOfElements size "+listOfElements.size())
//			for(int i = 0; i < listOfElements.size(); i++){
//				if(listOfElements[i].getText().contains(issue_info.get(2).get("Botão"))){
//					botao = issue_info.get(2).get("Botão")
//				}
//				else if(listOfElements[i].getText().contains(issue_info.get(3).get("Botão"))){
//					botao = issue_info.get(3).get("Botão")
//				}
//			}
//			clicarBotaoFrame(botao)
//
//		} catch (Exception e ){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível clicar no botão "+botao , "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Clica no item 'Cotação' do campo pesquisar
//	 * @return
//	 */
//	public static selecionarCotacao(){
//		try{
//			WebUI.delay(4)
//			def xpath = "//li//a[text()='"+issue_info.get(0).get("Menu")+"']"
//			WebDriver driverb = DriverFactory.getWebDriver()
//			WebElement inputelement = driverb.findElement(By.xpath(xpath));
//			inputelement.click()
//			adicionaImagem(issue_info.get(0).get("CT"),"Acesso a tela Cotação", "PASSED", 10)
//		} catch (Exception e) {
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível selecionar a opção:'"+issue_info.get(0).get("Menu")+"' ", "FAIL")
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Fecha a instância do Selenium WebDriver e todas os navegadores associados.
//	 * @return
//	 */
//	public static finalizar(){
//		WebUI.delay(2);
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			SessionId session = ((RemoteWebDriver)driver).getSessionId();
//
//			if( session.toString()!=null){
//				WebUI.delay(5)
//				closeDocument()
//				WebUI.closeBrowser()
//				driver.quit()
//			}
//		} catch (Exception e){
//			closeDocument()
//			System.err.println("-[Aplicação finalizada]-")
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Método recebe um texto e extrai as datas, retornando uma lista de tadas
//	 * @return
//	 */
//	public static Map<Integer,String> capturaVigenciaDaApoliceNoPDF(){
//		String xpath = "//label[contains(@class,'control-label alz-label alz-label-inline') and contains(@id,'validity')]"
//		hasdates = auxiliares.ExtractDates.getSubDates(pegaValorDaTela(xpath))
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Método recebe um texto e extrai as datas, retornando uma lista de tadas
//	 * @return
//	 */
//	public static Map<Integer,String> capturaVigenciaDaApolice(){
//		String xpath = "//label[contains(@class,'control-label alz-label alz-label-inline') and contains(@id,'validity')]"
//		hasdates = auxiliares.ExtractDates.getSubDates(pegaValorDaTela(xpath))
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Método recebe duas datas e extrai a quatidade de dias entre elas
//	 * @return
//	 */
//	public static int calculoVigenciaDaApolice(){
//		String sdata1=hasdates.get(0).toString().trim();
//		String sdata2=hasdates.get(1).toString().trim();
//		int dias = auxiliares.CountDays.verificadatas(sdata1,sdata2)
//		return dias;
//	}
//
//	/**
//	 *
//	 * @return
//	 */
//	public static validaDataFinalDaVigencia(){
//		def dtToCompate=hasdates.get(1).toString().trim();
//		if(issue_info.get(0).get("dtFinalVigencia").equals(dtToCompate)){
//			highlightElementDataFinalDaVigencia()
//			salvarEvidencia(issue_info.get(0).get("CT")," Data de fim de vigência "+dtToCompate+" exibida sem alteração ", "PASSED" )
//			unHighlightElementDataFinalDaVigencia()
//		} else {
//			salvarEvidencia(issue_info.get(0).get("CT")," Data de fim de vigência "+dtToCompate+" exibida com alteração ", "FAIL" )
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static highlightElementDataFinalDaVigencia(){
//		highlightElement("//label[@id='validity']")
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static unHighlightElementDataFinalDaVigencia(){
//		unHighlightElement("//label[@id='validity']")
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Método verifica se existe mensagem de falha na 1.Cotação Auto
//	 * @return
//	 */
//	public static validarMensagemDeErro(){
//		WebUI.delay(9)
//		String msgerror = null
//		try{
//			String xpath ="//div[contains(@class,'alz-wm-bs-col-xs-8 alz-wm-bs-col-sm-8 alz-wm-bs-col-md-9 alz-wm-error-col-center alz-wm-error-danger-error')]"
//			WebDriver driver = DriverFactory.getWebDriver()
//			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//					.withTimeout(2, TimeUnit.SECONDS)
//					.pollingEvery(2, TimeUnit.SECONDS)
//					.ignoring(NoSuchElementException.class);
//			driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
//			msgerror = driver.findElement(By.xpath(xpath)).getText();
//
//			if(!msgerror.equals(null)){
//				def ct = issue_info.get(0).get("CT")
//				salvarEvidencia(ct," Falha ao executar o teste - "+msgerror, "FAIL" )
//				finalizar()
//			}
//		}  catch (Exception e){
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * Na tela '2. Preço' clica na sessão 'Resumo dos dados'
//	 * @return
//	 */
//	public static clicarNaSessãoResumoDosDados(){
//		posicaoDaTela("//h2[text()='A Allianz tem o pacote certo para você.']")
//		clicarEmElementoDaTela("//div[contains(@id,'datablockicon')]", 2)
//		WebUI.delay(2)
//		posicaoDaTela("//b[text()='Nome Segurado: ']")
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static fecharSessãoEdiCAoDeCalculo(){
//		clicarEmElementoDaTela("//span[contains(@class,'allianz-icon pull-right alz-wm-accordion-section-icon-color allianz-icon-chevron-up')]", 2)
//	}
//
////	/**
////	 * @author T_DCDias
////	 * @return
////	 * Este método se mostrou mais ágil no preenchimento dos dados obrigatórios com relação aos método
////	 * "preencheCamposObrigatoriosCotacaoAutoUmIFrame()", caso necessite a utilização com IFrame usar o
////	 * metodo citado.
////	 */
////	public static preencheCamposObrigatoriosListFrame(int i){
////
////		System.err.println("Campos Obrigatorios List Frame")
////
////		WebUI.delay(10)
////		clicarEmElementoDaTelaList("//button[@id='newUser']", 3)
////		clicarEmElementoDaTelaList("//div[@class='alz-iconBtn-centertxt']//span[contains(text(),'Automóvel')]", 34)
////
////		'Navegar'
////		WebUI.switchToFrame(findTestObject('PageAuto/Page_app/iframe_Incio_appArea'), 35)
////
////		escreverCamposInputList("//input[@id='cpfCnpjSegurado']", ListCasoDeTeste.get(i).getCpfcnpj())
////		WebUI.delay(2)
////		commandTABList("//input[@id='cpfCnpjSegurado']")
////		if( ListCasoDeTeste.get(i).getCpfcnpj().toString().length()>11){
////			WebUI.delay(2)
////			escreverCamposInputList("//input[@id='cpfCnpjCondutor']", ListCasoDeTeste.get(i).getNumCpfCondutor())
////		}
////
////		escreverCamposInputList("//input[@id = 'CEP']", ListCasoDeTeste.get(i).getCep())
////		WebUI.delay(2)
////		commandTABList("//input[@id = 'CEP']")
////		WebUI.delay(2)
////
////		if(issue_info.get(0).get("Renovação").equals("Sim")){
////			clicarTipoDeSeguroEmCotacaoFormularioPreenchido()
////			selecionaCcomboTipoDeSeguro(issue_info.get(0).get("tipo"))
////			WebUI.delay(2)
////			WebUI.click(findTestObject('PageAuto/Page_app/listClasseDeBonus'))
////			flexibleTestObject = new TestObject()
////			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, commands.AutoComandos.comboClasseDeBonus( ListCasoDeTeste.get(i).getClasseDeBonus())))
////		}
////
////		'Combo Estado Civil'
////		commandCLICKList("//button[contains(@data-id,'comboCivilEstado' )]")
////		commandCLICKList(comboEstadoCivil(ListCasoDeTeste.get(i).getEstadoCivil()))
////		WebUI.delay(2)
////
////		'Placa do Veiculo ou Chassis'
////		if( ListCasoDeTeste.get(i).getPlaca().length() > 1){
////			escreverCamposInputList("//input[@id='placa']", ListCasoDeTeste.get(i).getPlaca() )
////			commandTABList("//input[@id='placa']")
////			WebUI.delay(5)
////			if( validaElemento("//div[contains(@id,'FipeListLightbox_row_' ) ]")){
////				commandCLICKList("//div[contains(@id,'FipeListLightbox_row_' ) ]")
////			}
////
////		} else if(ListCasoDeTeste.get(i).getPlaca().length() == 1 || ListCasoDeTeste.get(i).getPlaca().length() == 0) {
////			escreverCamposInputList("//input[@id='chassis']", ListCasoDeTeste.get(i).getChassis() )
////			commandTABList("//input[@id='chassis']")
////			WebUI.delay(5)
////			if( validaElemento("//div[contains(@id,'FipeListLightbox_row_' ) ]")){
////				commandCLICKList("//div[contains(@id,'FipeListLightbox_row_' ) ]")
////			}
////		}
////
////		'Tipo Residência'
////		WebUI.delay(2)
////		commandCLICKList(".//*[contains(@data-id,'valorResideEm') and not(contains(@data-id,'qcab_valorResid'))]")
////		commandCLICKList(comboResidenciaCondutor(ListCasoDeTeste.get(i).getResidenciaCondutor()))
////
////	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 * Este método se mostrou mais ágil no preenchimento dos dados obrigatórios com relação aos método
//	 * "preencheCamposObrigatoriosCotacaoAutoUmIFrame()", caso necessite a utilização com IFrame usar o
//	 * metodo citado.
//	 */
//	public static preencheCamposObrigatoriosFrame(){
//		'Navegar'
//		WebUI.switchToFrame(findTestObject('PageAuto/Page_app/iframe_Incio_appArea'), 20)
//		WebUI.waitForElementPresent(findTestObject('PageAuto/Page_app/input_CPF ou CNPJ do segurado'),20)
//		WebUI.delay(10)
//
//		escreverCamposInput("//input[@id='cpfCnpjSegurado']", casodeteste.getCpfcnpj())
//		WebUI.delay(2)
//		commandTAB("//input[@id='cpfCnpjSegurado']")
//		if( casodeteste.getCpfcnpj().toString().length()>11){
//			WebUI.delay(2)
//			escreverCamposInput("//input[@id='cpfCnpjCondutor']", casodeteste.getNumCpfCondutor())
//		}
//
//		escreverCamposInput("//input[@id = 'CEP']", casodeteste.getCep())
//		commandTAB("//input[@id = 'CEP']")
//		WebUI.delay(2)
//
//		'Combo Estado Civil'
//		commandCLICK("//button[contains(@data-id,'comboCivilEstado' )]")
//		commandCLICK(comboEstadoCivil(casodeteste.getEstadoCivil()))
//		WebUI.delay(2)
//
//		'Placa do Veiculo ou Chassis'
//		if( casodeteste.getPlaca().length() > 1){
//			escreverCamposInput("//input[@id='placa']", casodeteste.getPlaca() )
//			commandTAB("//input[@id='placa']")
//			WebUI.delay(5)
//			if( validaElemento("//div[contains(@id,'FipeListLightbox_row_' ) ]")){
//				commandCLICK("//div[contains(@id,'FipeListLightbox_row_' ) ]")
//			}
//
//		} else if(casodeteste.getPlaca().length() == 1 || casodeteste.getPlaca().length() == 0) {
//			escreverCamposInput("//input[@id='chassis']", casodeteste.getChassis() )
//			commandTAB("//input[@id='chassis']")
//			WebUI.delay(5)
//			if( validaElemento("//div[contains(@id,'FipeListLightbox_row_' ) ]")){
//				commandCLICK("//div[contains(@id,'FipeListLightbox_row_' ) ]")
//			}
//		}
//
//		'Tipo Residência'
//		WebUI.delay(2)
//		commandCLICK(".//*[contains(@data-id,'valorResideEm') and not(contains(@data-id,'qcab_valorResid'))]")
//		commandCLICK(comboResidenciaCondutor(casodeteste.getResidenciaCondutor()))
//
//	}
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static alteraFormaDePagamento(){
//		clicarEmElementoDaTela("//a[text()='"+issue_info.get(0).get("Tela")+"']", 5)
//		clicarEmElementoDaTela("//div[contains(@class,'combo-div')]//button[contains(@data-id,'comboPaymentMode')]", 1)
//		clicarEmElementoDaTela("//span[contains(text(),'"+issue_info.get(0).get("Pagamento")+"')]", 5)
//	}
//
//
//
//	/**
//	 * @author T_DCDias
//	 * Identifica a mensagem de alerta apresentada no topo da pagina
//	 */
//	public static validarMensagemDeAlerta(){
//		String mensagem = issue_info.get(0).get("mensagem")
//		try{
//			String xpath = "//div[@id='divContentErrorSection']"
//			String tag ="div"
//			boolean mensagemVistoria=false
//			WebUI.delay(10)
//			selecionaListaDeElementos(xpath,tag)
//			for(int i = 0; i < listOfElements.size(); i++){
//				System.err.println("["+i+"] DIV ["+listOfElements[i].getText()+"]")
//				if(listOfElements[i].getText().toString().contains(mensagem.toString())){
//					mensagemVistoria = true
//				}
//			}
//
//			if(mensagemVistoria){
//				highlightElement(xpath)
//				salvarEvidencia(issue_info.get(0).get("CT")," Mensagem '"+mensagem+"' apresentada com sucesso ", "PASSED")
//				unHighlightElement(xpath)
//			} else {
//				salvarEvidencia(issue_info.get(0).get("CT")," Mensagem '"+mensagem+"' não foi apresentada com sucesso ", "FAIL")
//			}
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível localizar a mensagem "+mensagem, "FAIL")
//			finalizar()
//		}
//	}
//
//
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 */
//	public static validarMensagemDeAlertaDePreenchimentoDeCamposCNPJ(){
//		try{
//			if(casodeteste.getCpfcnpj().toString().length()>11){
//				WebUI.delay(10)
//				boolean condutor=false
//				boolean nomeSegurado=false
//				boolean dataNascimento=false
//				String xpath = "//div[@id='divContentErrorSection']"
//				String tag ="div"
//				boolean mensagemVistoria=false
//				WebUI.delay(10)
//				selecionaListaDeElementos(xpath,tag)
//				for(int i = 0; i < listOfElements.size(); i++){
//
//					System.err.println("["+i+"] div ["+listOfElements[i].getText()+"]")
//
//					if(listOfElements[i].getText().equals(issue_info.get(1).get("mensagem"))){
//						System.err.println("["+i+"] div ["+listOfElements[i].getText()+"]")
//						condutor=true
//					}
//					if(listOfElements[i].getText().equals(issue_info.get(2).get("mensagem"))){
//						System.err.println("["+i+"] div ["+listOfElements[i].getText()+"]")
//						nomeSegurado=true
//					}
//					if(listOfElements[i].getText().equals(issue_info.get(3).get("mensagem"))){
//						dataNascimento=true
//					}
//					if(listOfElements[i].getText().equals(issue_info.get(4).get("mensagem"))){
//						condutor=true
//					}
//				}
//
//				System.err.println("condutor ["+condutor+"]")
//
//				if(condutor){
//					escreverCamposInput("//input[@id='inputPolicyHolderName']", casodeteste.getNomeCpfCondutor())
//				}
//
//				if(condutor){
//					String xpathSexo = "//button[@id='BDriverSex"+issue_info.get(0).get("sexo")+"']"
//					clicarEmElementoDaTela( xpathSexo, 1)
//				}
//				if(dataNascimento){
//
//				}
//
//				//	clicarEmElementoDaTela("//button[@id='NextButton']", 2)
//				botaoAvançar()
//			}
//		} catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível localizar a mensagem de alerta", "FAIL")
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 * Retorna lista com todos os item da planilha de massa
//	 */
//	public static List<CasoDeTeste> retornaListaDeCasosDeTeste(){
//		return auxiliares.WriteExcelFactory.readExcelFileToArray(path)
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 * Retorna lista com todos os item da planilha de massa
//	 */
//	public static List<CasoDeTeste> retornaListaDeCasosDeTesteRadarLive(){
//		return auxiliares.WriteExcelFactory.readExcelFileToArray(pathRadarLive)
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 * Este método se mostrou mais ágil no preenchimento dos dados obrigatórios com relação aos método
//	 * "preencheCamposObrigatoriosCotacaoAutoUmIFrame()", caso necessite a utilização com IFrame usar o
//	 * metodo citado.
//	 */
//	public static preencheCamposObrigatorios(){
//		'Navegar'
//		WebUI.waitForElementPresent(findTestObject('PageAuto/Page_app/input_CPF ou CNPJ do segurado'),20)
//		WebUI.delay(10)
//
//		escreverCamposInput("//input[@id='cpfCnpjSegurado']", casodeteste.getCpfcnpj())
//		WebUI.delay(2)
//		commandTAB("//input[@id='cpfCnpjSegurado']")
//		if( casodeteste.getCpfcnpj().toString().length()>11){
//			WebUI.delay(2)
//			escreverCamposInput("//input[@id='cpfCnpjCondutor']", casodeteste.getNumCpfCondutor())
//		}
//
//		if(casodeteste.getCep().length()>0){
//			escreverCamposInput("//input[@id = 'CEP']", casodeteste.getCep())
//			commandTAB("//input[@id = 'CEP']")
//			WebUI.delay(2)
//		}
//
//		'Combo Estado Civil'
//		commandCLICK("//button[contains(@data-id,'comboCivilEstado' )]")
//		commandCLICK(comboEstadoCivil(casodeteste.getEstadoCivil()))
//		WebUI.delay(2)
//
//		'Placa do Veiculo ou Chassis'
//		if( casodeteste.getPlaca().length() > 1){
//			escreverCamposInput("//input[@id='placa']", casodeteste.getPlaca() )
//			commandTAB("//input[@id='placa']")
//			WebUI.delay(5)
//			if( validaElemento("//div[contains(@id,'FipeListLightbox_row_' ) ]")){
//				commandCLICK("//div[contains(@id,'FipeListLightbox_row_' ) ]")
//			}
//
//		} else if(casodeteste.getPlaca().length() == 1 || casodeteste.getPlaca().length() == 0) {
//			escreverCamposInput("//input[@id='chassis']", casodeteste.getChassis() )
//			commandTAB("//input[@id='chassis']")
//			WebUI.delay(5)
//			if( validaElemento("//div[contains(@id,'FipeListLightbox_row_' ) ]")){
//				commandCLICK("//div[contains(@id,'FipeListLightbox_row_' ) ]")
//			}
//		}
//
//
//		'Tipo Residência'
//		WebUI.delay(2)
//		commandCLICK(".//*[contains(@data-id,'valorResideEm') and not(contains(@data-id,'qcab_valorResid'))]")
//		commandCLICK(comboResidenciaCondutor(casodeteste.getResidenciaCondutor()))
//
//	}
//
//	public static preencheCamposObrigatoriosSemPlacaChassi(){
//		'Navegar'
//		WebUI.waitForElementPresent(findTestObject('PageAuto/Page_app/input_CPF ou CNPJ do segurado'),20)
//		WebUI.delay(2)
//
//		escreverCamposInput("//input[@id='cpfCnpjSegurado']", casodeteste.getCpfcnpj())
//		WebUI.delay(2)
//		commandTAB("//input[@id='cpfCnpjSegurado']")
//		if( casodeteste.getCpfcnpj().toString().length()>11){
//			WebUI.delay(2)
//			escreverCamposInput("//input[@id='cpfCnpjCondutor']", casodeteste.getNumCpfCondutor())
//		}
//
//		if(casodeteste.getCep().length()>0){
//			escreverCamposInput("//input[@id = 'CEP']", casodeteste.getCep())
//			commandTAB("//input[@id = 'CEP']")
//			WebUI.delay(2)
//		}
//
//		'Combo Estado Civil'
//		commandCLICK("//button[contains(@data-id,'comboCivilEstado' )]")
//		commandCLICK(comboEstadoCivil(casodeteste.getEstadoCivil()));
//		WebUI.delay(2);
//
//		'Tipo Residência'
//		WebUI.delay(2)
//		commandCLICK(".//*[contains(@data-id,'valorResideEm') and not(contains(@data-id,'qcab_valorResid'))]")
//		commandCLICK(comboResidenciaCondutor(casodeteste.getResidenciaCondutor()));
//
//		'ZeroKm?'
//
//		String zeroKm =  issue_info.get(0).get("ZeroKm");
//		String xpath = '';
//		WebDriver driver = DriverFactory.getWebDriver();
//		//driver.switchTo().frame("appArea")
//		if(zeroKm.contains("SIM".toString())){
//			xpath = '//*[@id="BKM0Sim"]';
//			WebElement Element = driver.findElement(By.xpath(xpath));
//			Element.click();
//		}else{
//			xpath = '//*[@id="BKM0Nao"]';
//			WebElement Element = driver.findElement(By.xpath(xpath));
//			Element.click();
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @return
//	 */
//	public static clicarElementoFrameSwitchTo(String xpath){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			driver.switchTo().frame("appArea");
//			driver.findElement(By.xpath(xpath)).click()
//			WebUI.delay(10)
//		}   catch (Exception e ){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível clicar no elemnto", "FAIL" )
//			finalizar()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param br
//	 * @param es
//	 * @return
//	 * Verifica a existencia do botão no frame 'DETALHE DA COTAÇÃO' em português ou em espanhol
//	 */
//	public static boolean verificaBotaoNoFrame(String br, String es){
//		boolean botao = false
//		WebUI.delay(2)
//		String xpath = '//*[@id="detalleSectionBar"]/table/tbody/tr/td'
//		String tag ="div"
//		WebUI.delay(15)
//
//		selecionaListaDeElementos(xpath,tag)
//		for(int i = 0; i < listOfElements.size(); i++){
//			System.err.println("["+i+"] div ["+listOfElements[i].getText()+"]")
//			if(listOfElements[i].getText().contains(br.toString()) || listOfElements[i].getText().contains(es.toString())){
//				botao=true
//			}
//		}
//		return botao
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param valor
//	 * @return
//	 */
//	public static String setString(int valor){
//		String vlrstr = Integer.toString(valor)+",0"
//		return vlrstr
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @param valor
//	 * @return
//	 */
//	public static boolean validaCampoInPutBloqueado(String xpath, String valor){
//		boolean result
//		try{
//			WebDriver driver = DriverFactory.getWebDriver()
//			WebElement Element = driver.findElement(By.xpath(xpath));
//			Element.sendKeys(valor)
//			result = false
//		} catch (Exception e){
//			result = true
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 * metodo apenas para leitura do pdf
//	 */
//	public static stringTestPDF(){
//		//pathFileValue = issue_info.get(0).get("downloadPath")+issue_info.get(0).get("arquivo")
//		pathFileValue ='C://Users//Public//retrievedoc.pdf'
//
//		System.err.println("pathValue ["+pathFileValue+"]")
//		//return takePDFContent(pathFileValue)
//		return takeInfoPDFContent(pathFileValue)
//	}
//
//	/**'Faz a validação do campo desejado e evidencia recebendo por parametro as Strings:'
//	 * @param xpath
//	 * @param xpathFalha
//	 * @param mensagem
//	 * @param mensagemFalha
//	 * @author T_DVlopes
//	 * @return
//	 */
//	public static validaCampo(String xpath, String xpathFalha, String mensagem, String mensagemFalha, int delayTime){
//		try{
//			WebUI.delay(delayTime);
//			if (pegaValorDaTela(xpath).contains(mensagem)){
//				highlightElement(xpath);
//				adicionaImagem(issue_info.get(0).get("CT"),	mensagem, "PASSED" , 1);
//				unHighlightElement(xpath);
//			}else{
//				highlightElement(xpathFalha);
//				adicionaImagem(issue_info.get(0).get("CT"),	mensagemFalha, "FAIL" , 1);
//				unHighlightElement(xpathFalha);
//				finalizar();
//			}
//		}catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT"), mensagemFalha, "FAIL" );
//			finalizar();
//		}
//	}
//
//
//}

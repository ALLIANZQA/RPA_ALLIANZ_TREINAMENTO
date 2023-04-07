//package rpacommands
//
//import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
//import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
//import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
//import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
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
//
//import internal.GlobalVariable
//import io.cucumber.datatable.DataTable
//
//import org.openqa.selenium.By
//import org.openqa.selenium.Keys
//import org.openqa.selenium.WebDriver
//import org.openqa.selenium.WebElement
//import org.openqa.selenium.By.ByClassName
//
//import com.kms.katalon.core.webui.driver.DriverFactory
//
//import com.kms.katalon.core.testobject.ConditionType
//
//public class MatrizComandos extends AutoComandos{
//
//	public static acessaMatrizPage(DataTable data){
//		issue_info =  data.asMaps(String.class, String.class);
//		def ct = issue_info.get(0).get("CT");
//		leituraMassa(ct);
//		createDiretory(createDiretorypath+issue_info.get(0).get("topico"));
//		createDocument(ct, pathEvidences+issue_info.get(0).get("topico"));
//		createTitlePage();
//		try{
//			//	commands.Login.homePage(retornaURL('matriz'));
//			WebUI.delay(2);
//		} catch (Exception e) {
//			salvarEvidencia(issue_info.get(0).get("CT")," Falha ao executar o teste", "FAIL" );
//			finalizar();
//		}
//	}
//
//	public static clicaNoBotaoEntrarTelaMatriz(){
//		try{
//			clicarEmbotao('//*[@id="botonSubmitFormulario"]', 1);
//		} catch (Exception e) {
//			salvarEvidencia(issue_info.get(0).get("CT"), "Falha Ao realizar ação Clicar Botão Entrar na Tela Matriz" ,"FAIL" );
//			finalizar();
//		}
//	}
//
//	/**clicarMenuTelaMatriz
//	 * @return
//	 */
//	public static clicaNoMenuTelaMatriz(String menuMatriz){
//		//String menuMatriz = issue_info.get(0).get("Menu");
//		try{
//			println (menuMatriz);
//			WebUI.delay(10);
//			Map<String, String> menu = new HashMap<String, String>();
//			menu.put("Producao & Sinistros",'//*[@id="100"]');
//			menu.put("Gestão & Marketing","//*[@id='200']");
//			menu.put("Consultas & Estatísticas", "//*[@id='300']");
//			menu.put("Serviços Corporativos","//*[@id='400']");
//			flexibleTestObject = new TestObject();
//			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, menu.get(menuMatriz.toString())));
//		} catch (Exception e ){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível selecionar a opção "+menuMatriz, "FAIL");
//			finalizar();
//		}
//	}
//
//	/**clicarSubMenuTelaMatriz
//	 * @return
//	 */
//	public static clicaNoSubMenuTelaMatriz(String sSubMenus){
//		//String sSubMenus = issue_info.get(0).get("SubMenu");
//		WebUI.delay(2);
//		try{
//			Map<String, String> submenu = new HashMap<String, String>();
//			//Manutenção
//			submenu.put("Manutenção Geral", "//*[@id='500']");
//
//			//Produção E Sinistros
//			submenu.put("Produção Diversos", '//*[@id="110"]');
//			submenu.put("Gestão Carteira", '//*[@id="120"]');
//			submenu.put("Sinistros", '//*[@id="130"]');
//			submenu.put("Produção Suporte", '//*[@id="140"]');
//
//			//Gestão e Marketing
//			submenu.put("Mediadores/Clientes", '//*[@id="210"]');
//			submenu.put("Adiministração", '//*[@id="220"]');
//			submenu.put("Administração Suporte", '//*[@id="230"]');
//			submenu.put("Reaseguro", '//*[@id="240"]');
//			submenu.put("AllianzNet", '//*[@id="250"]');
//
//			//Consultas e Estatisticas
//			submenu.put("Consultas", "//*[@id='310']");
//			submenu.put("Arquivo Digital", "//*[@id='320']");
//			submenu.put("Estatistica", "//*[@id='330']");
//
//			//Serviços Corporativos
//			submenu.put("Serviços Gerais", '//*[@id="410"]');
//
//			//Manutenção Geral
//			submenu.put("Manutenção Produção", '//*[@id="510"]');
//			submenu.put("Manutenção Sinistros", '//*[@id="520"]');
//			submenu.put("Manutenção Administração", '//*[@id="530"]');
//			submenu.put("Manutenção GE GO e Imagem", '//*[@id="540"]');
//
//			flexibleTestObject = new TestObject();
//			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, submenu.get(sSubMenus.toString())));
//		} catch (Exception e ){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível selecionar a opção "+sSubMenus, "FAIL");
//			finalizar();
//		}
//	}
//
//	/**clicarSubOpcao
//	 * @return
//	 */
//	public static clicaNaSubOpcao(String subMenus){
//		//String subMenus = issue_info.get(0).get("SubOpcao");
//		WebUI.delay(2);
//		try{
//			Map<String, String> subOpcao = new HashMap<String, String>();
//
//			//Produção e Sinistros
//			//Produção Diversos
//			subOpcao.put("2ª Via Cartões", '//*[@id="topicNav"]/li[1]/a');
//			subOpcao.put("Agronegócios", '//*[@id="topicNav"]/li[2]/a');
//			subOpcao.put("Alterações cadastrais", '//*[@id="topicNav"]/li[3]/a');
//			subOpcao.put("Automóvel", '//*[@id="topicNav"]/li[4]/a');
//			subOpcao.put("Automóvel-Ferramentas", '//*[@id="topicNav"]/li[5]/a');
//			subOpcao.put("Faturamento Saúde AZB", '//*[@id="topicNav"]/li[6]/a');
//			subOpcao.put("HUB de Serviços", '//*[@id="topicNav"]/li[7]/a');
//			subOpcao.put("Manutenção VIDA", '//*[@id="topicNav"]/li[8]/a');
//			subOpcao.put("Risk Grading", '//*[@id="topicNav"]/li[9]/a');
//			subOpcao.put("Saúde - Fatura em Excel", '//*[@id="topicNav"]/li[10]/a');
//			subOpcao.put("Saúde - Menu Saúde AZB", '//*[@id="topicNav"]/li[11]/a');
//			subOpcao.put("Saúde - Reajuste de Premio", '//*[@id="topicNav"]/li[12]/a');
//			subOpcao.put("Saúde - Renovação Mensal", '//*[@id="topicNav"]/li[13]/a');
//			subOpcao.put("Transportes", '//*[@id="topicNav"]/li[14]/a');
//			subOpcao.put("Faturamento de frotas. Parc. Única", '//*[@id="topicNav"]/li[15]/a');
//			subOpcao.put("Alter. domiciliação Cobrança", '//*[@id="topicNav"]/li[16]/a');
//			subOpcao.put("Cotações especiais", '//*[@id="topicNav"]/li[17]/a');
//			subOpcao.put("Desbloqueáveis Produção", '//*[@id="topicNav"]/li[18]/a');
//			subOpcao.put("Criação de Planos", '//*[@id="topicNav"]/li[19]/a');
//			subOpcao.put("Análise de Inspeções", '//*[@id="topicNav"]/li[20]/a');
//			subOpcao.put("Produção", '//*[@id="topicNav"]/li[21]/a');
//			subOpcao.put("Cancelamento", '//*[@id="topicNav"]/li[22]/a');
//
//			//Produção e Sinistros
//			//Gestão Carteiras
//			subOpcao.put("Tabela Depart.-Área", '//*[@id="topicNav"]/li[1]/a');
//			subOpcao.put("Gestão renovações", '//*[@id="topicNav"]/li[2]/a');
//			subOpcao.put("Manutenção Tarifas", '//*[@id="topicNav"]/li[3]/a');
//			subOpcao.put("Renovação Frota", '//*[@id="topicNav"]/li[4]/a');
//			subOpcao.put("Mant. revisión cartera", '//*[@id="topicNav"]/li[5]/a');
//
//			//Produção e Sinistros
//			//Sinistros
//			subOpcao.put("Conta Corrente", '//*[@id="topicNav"]/li[1]/a');
//			subOpcao.put("Fornecimento de Peças", '//*[@id="topicNav"]/li[2]/a');
//			subOpcao.put("Gestão IVS", '//*[@id="topicNav"]/li[3]/a');
//			subOpcao.put("Gestão de Verificações", '//*[@id="topicNav"]/li[4]/a');
//			subOpcao.put("Gestão Precobros", '//*[@id="topicNav"]/li[5]/a');
//			subOpcao.put("Liquidaciones Profesionales", '//*[@id="topicNav"]/li[6]/a');
//
//			//Produção e Sinistros
//			//Produção Suporte
//			subOpcao.put("Alteração Apólices", '//*[@id="topicNav"]/li[1]/a');
//			subOpcao.put("Gestor de Cargas Masivas", '//*[@id="topicNav"]/li[2]/a');
//			subOpcao.put("Manutenção seguradoras", '//*[@id="topicNav"]/li[3]/a');
//			subOpcao.put("Oficina de Produtos", '//*[@id="topicNav"]/li[4]/a');
//			subOpcao.put("Radar Live", '//*[@id="topicNav"]/li[5]/a');
//			subOpcao.put("Duplicados Recibos", '//*[@id="topicNav"]/li[6]/a');
//			subOpcao.put("Mantenimiento IPC", '//*[@id="topicNav"]/li[7]/a');
//			subOpcao.put("Alteração Frotas", '//*[@id="topicNav"]/li[8]/a');
//			subOpcao.put("Mant. excepciones agente Allianz", '//*[@id="topicNav"]/li[9]/a');
//			subOpcao.put("Condiçoes Frotas", '//*[@id="topicNav"]/li[10]/a');
//
//			//Gestão e Marketing
//			//Mediadores/Clientes
//			subOpcao.put("Acompanhamento de Projectos", '//*[@id="topicNav"]/li[1]/a');
//			subOpcao.put("Black List", '//*[@id="topicNav"]/li[2]/a');
//			subOpcao.put("Gestão Solicitações Extrações", '//*[@id="topicNav"]/li[3]/a');
//			subOpcao.put("Agenda comercial", '//*[@id="topicNav"]/li[4]/a');
//			subOpcao.put("Gestão Corretores", '//*[@id="topicNav"]/li[5]/a');
//			subOpcao.put("Gestão Clientes", '//*[@id="topicNav"]/li[6]/a');
//
//			//Gestão e Marketing
//			//Administração
//			subOpcao.put("BDPO", '//*[@id="topicNav"]/li/a');
//
//			//Gestão e Marketing
//			//Administração Suporte
//			subOpcao.put("Adicionais", '//*[@id="topicNav"]/li[1]/a');
//			subOpcao.put("Embargos SEA", '//*[@id="topicNav"]/li[2]/a');
//
//			//Gestão e Marketing
//			//Reaseguro
//			subOpcao.put("Resseguro", '//*[@id="topicNav"]/li/a');
//
//			//Gestão e Marketing
//			//AllianzNet
//			subOpcao.put("e-Contact Allianz", '//*[@id="topicNav"]/li/a');
//
//			//Consultas e Estatisticas
//			//Consultas
//			subOpcao.put("Central de Bonus", '//*[@id="topicNav"]/li[1]/a');
//			subOpcao.put("Consulta de Exposicao", '//*[@id="topicNav"]/li[2]/a');
//			subOpcao.put("Consulta de Vistoria", '//*[@id="topicNav"]/li[3]/a');
//			subOpcao.put("Consultas Gerais", '//*[@id="topicNav"]/li[4]/a');
//			subOpcao.put("Contestacao", '//*[@id="topicNav"]/li[5]/a');
//			subOpcao.put("Listagens de Recibos", '//*[@id="topicNav"]/li[6]/a');
//			subOpcao.put("Ficheiro Marca/Modelo", '//*[@id="topicNav"]/li[7]/a');
//
//			//Consultas e Estatisticas
//			//Arquivo Digital
//			subOpcao.put("Ficha de Gestão", '//*[@id="topicNav"]/li[1]/a');
//			subOpcao.put("Pre-Indexação SEA", '//*[@id="topicNav"]/li[2]/a');
//			subOpcao.put("GO!", '//*[@id="topicNav"]/li[3]/a');
//			subOpcao.put("Consulta documentación Pre-Indexada SEA", '//*[@id="topicNav"]/li[4]/a');
//
//			//Consultas e Estatisticas
//			//Estatistica
//			subOpcao.put("Dia-Allz", '//*[@id="topicNav"]/li[1]/a');
//			subOpcao.put("Manutenção Dia-Allz", '//*[@id="topicNav"]/li[2]/a');
//			subOpcao.put("Mantenimiento Variables DiaAllz", '//*[@id="topicNav"]/li[3]/a');
//
//			//Serviços Corporativos
//			//Serviços Gerais
//			subOpcao.put("Conectividade", '//*[@id="topicNav"]/li[1]/a');
//			subOpcao.put("Controle de Impressão", '//*[@id="topicNav"]/li[2]/a');
//			subOpcao.put("Link de Impressão", '//*[@id="topicNav"]/li[3]/a');
//			subOpcao.put("Serviços de Impressão", '//*[@id="topicNav"]/li[4]/a');
//			subOpcao.put("Pedido de Impressos (SEA)", '//*[@id="topicNav"]/li[5]/a');
//
//
//			flexibleTestObject = new TestObject();
//			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, subOpcao.get(subMenus.toString())));
//		} catch (Exception e ){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível selecionar a opção "+subMenus, "FAIL");
//			finalizar();
//		}
//	}
//
//	/**validarTelaCriteirosPesquisa
//	 * @return
//	 */
//	public static validaTelaCriteirosPesquisa(){
//		try{
//			WebUI.delay(15);
//			String xpath = '//*[@id="sectionVGlobal"]/table/tbody/tr[1]/td/table/tbody/tr/td[2]';
//
//			WebDriver driver = DriverFactory.getWebDriver();
//			Set<String> handles = driver.getWindowHandles();
//			String originalWindow = driver.getWindowHandle();
//			Iterator <String> iterator = handles.iterator();
//			while(iterator.hasNext()) {
//				String newWindow = iterator.next();
//				if(!originalWindow.equalsIgnoreCase(newWindow)){
//					driver.switchTo().window(newWindow);
//				}
//			}
//			WebElement element = driver.findElement(By.xpath(xpath));
//			WebUI.delay(5);
//			if(element){
//				highlightElement('/html/body');
//				salvarEvidencia(issue_info.get(0).get("CT"),"Tela Critérios de Pesquisa Apresentada Com SUCESSO!:", "PASSED");
//				unHighlightElement('/html/body');
//			}else{
//				salvarEvidencia(issue_info.get(0).get("CT"),"Falha ao validar Tela Critérios de Pesquisa:", "FAIL");
//			}
//		} catch (Exception e) {
//			salvarEvidencia(issue_info.get(0).get("CT"),"Falha ao validar Tela Critérios de Pesquisa:", "FAIL");
//			finalizar();
//		}
//	}
//
//	/**validarJanelaMenuProducao
//	 * @return
//	 */
//	public static validaJanelaMenuProducao(){
//		try{
//			WebUI.delay(15);
//			String xpath = '//*[@id="cellAppPromptHeaderArea"]';
//			WebDriver driver = DriverFactory.getWebDriver();
//			Set<String> handles = driver.getWindowHandles();
//			String originalWindow = driver.getWindowHandle();
//			Iterator <String> iterator = handles.iterator();
//			while(iterator.hasNext()) {
//				String newWindow = iterator.next();
//				if(!originalWindow.equalsIgnoreCase(newWindow)){
//					driver.switchTo().window(newWindow);
//					driver.manage().window().maximize();
//				}
//			}
//			WebElement element = driver.findElement(By.xpath(xpath));
//			String validaTela = element.getText();
//			if(validaTela.contains("MENU PRODUÇÃO".toString())){
//				highlightElement('//*[@id="divAppContent"]');
//				salvarEvidencia(issue_info.get(0).get("CT"),"Janela Menu Produção Validado Com SUCESSO!:", "PASSED");
//				unHighlightElement('//*[@id="divAppContent"]');
//			}else{
//				salvarEvidencia(issue_info.get(0).get("CT"),"Falha ao validar Janela MENU PRODUÇÃO", "FAIL");
//			}
//		} catch (Exception e) {
//			salvarEvidencia(issue_info.get(0).get("CT"),"Falha ao validar Janela MENU PRODUÇÃO", "FAIL");
//			finalizar();
//		}
//	}
//
//	/**clicarBotaoPropostasBloqueadas
//	 * @return
//	 */
//	public static clicaNoBotaoPropostasBloqueadas(){
//		try{
//			clicarEmbotao('//*[@id="pBloqueadas"]', 1);
//		}   catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível clicar no botão propostas Bloqueadas", "FAIL" );
//			finalizar();
//		}
//	}
//
//	/**validarJanelaListaDeBloqueios
//	 * @return
//	 */
//	public static validaJanelaListaDeBloqueios(){
//		try{
//			WebUI.delay(10);
//			String xpath = '//*[@id="cellAppPromptHeaderArea"]';
//			WebDriver driver = DriverFactory.getWebDriver();
//			WebElement element = driver.findElement(By.xpath(xpath));
//			String lstBlock = element.getText();
//			if(lstBlock.contains("LISTA BLOQUEIOS".toString())){
//
//				highlightElement('//*[@id="divAppContent"]');
//				adicionaImagem(issue_info.get(0).get("CT"),"Janela Menu Produção Validado Com SUCESSO!:", "PASSED", 5);
//				unHighlightElement('//*[@id="divAppContent"]');
//			}else{
//				salvarEvidencia(issue_info.get(0).get("CT"),"Falha ao validar Janela Lista de Bloqueios:", "FAIL");
//			}
//		} catch (Exception e) {
//			salvarEvidencia(issue_info.get(0).get("CT"),"Falha ao validar Janela Lista de Bloqueios:", "FAIL");
//			finalizar();
//		}
//	}
//
//	/**preencherCamposPropostaTelalistaBloqueio
//	 * @return
//	 */
//	public static preencheCamposPropostaTelalistaBloqueio(){
//		try{
//			WebUI.delay(5);
//			WebDriver driver = DriverFactory.getWebDriver();
//			WebElement element = driver.findElement(By.xpath('//*[@id="poliza_Policy"]'));
//			element.sendKeys(casodeteste.getProposta());
//		}catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível preencher campos Proposta", "FAIL" );
//			finalizar();
//		}
//	}
//
//
//	/**preencheCamposProposta
//	 * @return
//	 */
//	public static preencheCamposProposta(){
//		try{
//			WebUI.delay(5);
//			WebDriver driver = DriverFactory.getWebDriver();
//			WebElement element = driver.findElement(By.xpath("//*[@id='apolice']"));
//			element.sendKeys(casodeteste.getProposta());
//		}catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível preencher campos Proposta", "FAIL" );
//			finalizar();
//		}
//	}
//
//
//	/**clicarBotaoPesquisarTelaListaBloqueio
//	 * @return
//	 */
//	public static clicarPesquisarTelaCriteirosPesquisa(){
//		try{
//			clicarEmbotao('//*[@id="o_11"]', 2);
//		}catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi clicar Botão Pesquisar", "FAIL" );
//			finalizar();
//		}
//	}
//
//
//	/**clicarBotaoPesquisarTelaListaBloqueio
//	 * @return
//	 */
//	public static clicaBotaoPesquisarTelaListaBloqueio(){
//		try{
//			clicarEmbotao('//*[@id="btnBuscar"]', 2);
//		}catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi clicar Botão Pesquisar", "FAIL" );
//			finalizar();
//		}
//	}
//
//	/**clicarBotaoEmitir
//	 * @return
//	 */
//	public static clicaNoBotaoEmitir(){
//		try{
//			clicarEmbotao('//*[@id="tablaBloqueos_0_15"]/table/tbody/tr/td[2]', 2);
//		}catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT"),"Não foi possível clicar no Botão Emitir", "FAIL" );
//			finalizar();
//		}
//	}
//
//	/**clicarEmOkPopUp
//	 * @return
//	 */
//	public static clicaEmOkPopUp(){
//		try{
//			WebUI.delay(5);
//			switchToOtherSession(DriverFactory.getWebDriver());
//		}catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT"),"Não foi possível clicar no Botão Emitir", "FAIL" );
//			finalizar();
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param xpath
//	 * @param dalay
//	 * @return
//	 */
//	public static clicarEmbotao(String xpath, int dalay){
//		try{
//			WebUI.delay(2);
//			WebDriver driver = DriverFactory.getWebDriver();
//			WebElement element = driver.findElement(By.xpath(xpath));
//			element.click();
//		}catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT"),"Não foi possível clicar no Botão", "FAIL" );
//			finalizar();
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @param driverFactory
//	 * @return
//	 */
//	public static switchToOtherSession(DriverFactory driverFactory){
//		try{
//			WebDriver driver = driverFactory;
//			driver.switchTo().alert().accept();
//		}catch (Exception e){
//			salvarEvidencia(issue_info.get(0).get("CT"),"Não foi possível (switchToOtherSession) Alterar driver", "FAIL" );
//			finalizar();
//		}
//	}
//
//
//	/**clicaNoMenuTelaCriteiriosPesquisa
//	 * Recebe por parametro opção de menu a ser clicado
//	 * @param menuPesquisa
//	 * @return
//	 */
//	public static clicaNoMenuTelaCriteiriosPesquisa(String menuPesquisa){
//		//String menuMatriz = issue_info.get(0).get("Menu");
//		try{
//
//			WebUI.delay(10);
//			Map<String, String> menu = new HashMap<String, String>();
//			menu.put("Gerais","//*[@id='9']");  //*[@id="9"]
//			menu.put("Informações","//*[@id='10']");  //*[@id="10"]
//			menu.put("Tom. Seg.", "//*[@id='444']");	//*[@id="444"]
//			menu.put("Dados Risco","//*[@id='10122']");  //*[@id="10122"]
//			menu.put("Coberturas","//*[@id='56']");  //*[@id="56"]
//			menu.put("Cláusulas","//*[@id='259']");  //*[@id="259"]
//			menu.put("SUSEP","//*[@id='7081']");  //*[@id="7081"]
//			menu.put("Resumo","//*[@id='110000']");  //*[@id="110000"]
//
//			flexibleTestObject = new TestObject();
//			WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, menu.get(menuPesquisa.toString())));
//		} catch (Exception e ){
//			salvarEvidencia(issue_info.get(0).get("CT")," Não foi possível selecionar a opção "+menuPesquisa, "FAIL");
//			finalizar();
//		}
//	}
//
//
//	/**validarCampoTelaMatriz
//	 * Recebe por paramero
//	 * @param xpath
//	 * @param conteudo
//	 * @param nomeCampo
//	 * @param atributo	 * 
//	 * Para fazer validação de um valor "Atributo" 'Text' 'Value'
//	 * @return
//	 * 
//	 */
//	public static validarCampoTelaMatriz(String xpath, String conteudo, String nomeCampo, String atributo){
//		try{
//			WebDriver driver = DriverFactory.getWebDriver();
//			WebElement element = driver.findElement(By.xpath(xpath));
//			String conteudoRecuperado = element.getAttribute(atributo).trim();
//			if(conteudoRecuperado.equals(conteudo)){
//				highlightElement(xpath);
//				adicionaImagem(issue_info.get(0).get("CT"),"Campo Validado "+nomeCampo+" Com sucesso! :", "PASSED", 5);
//				unHighlightElement(xpath);
//			}else{
//				highlightElement(xpath);
//				salvarEvidencia(issue_info.get(0).get("CT"),"Falha ao validar Campo "+nomeCampo+" Conteudo Capturado" +conteudoRecuperado+"", "FAIL");
//				unHighlightElement(xpath);
//			}
//		} catch (Exception e) {
//			salvarEvidencia(issue_info.get(0).get("CT"),"Falha ao validar Campo "+nomeCampo+"", "FAIL");
//			finalizar();
//		}
//	}
//
//
//}

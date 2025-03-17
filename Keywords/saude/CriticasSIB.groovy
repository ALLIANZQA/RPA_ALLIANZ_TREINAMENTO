package saude

import org.openqa.selenium.WebElement
import rpacommands.rpaCommands
import auxiliares.switchLeitorExcel
import backoffice.backofficeFuncionalidades
import bean.MassaDados
import auxiliares.switchAtualizarExcel
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import auxiliares.ExtractDates


public class CriticasSIB extends rpaCommands{


	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Saude/CriticasSIBGestaoClienteData'
	private static String path
	private static String diretorio = '\\C:\\Users\\Public\\Evidencias'
	private static String excelCopiado
	private static String emissao
	private static String msgAlerta

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		path = data.sourceUrl
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static boolean validaDados(){
		boolean value
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(2, i).equals('Processar')) {
				value = true
				break;
			} else {
				value = false
				break;
			}
		}
		System.err.println('Data ['+value+']')
		return value
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static copiaEvidencia(){
		initDataFiles()
		diretorio = createDiretory('CriticasSIB Gestao Cliente - '+dataRecuerada);

		System.err.println(path)
		System.err.println(diretorio)
		excelCopiado = copiarExcel(path, diretorio+"\\RoboCriticasSIBGestaoCliente-'"+dataRecuerada+"'.xlsx");

		System.err.println('\n'+excelCopiado)

		enviarEmailCriticaSib('CriticasSIBGestaoCliente')
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailCriticaSib(String tipoAba){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Saude/CriticasSIB/ConfiguracoesCriticasSIB.txt";
		lerConfigurações(pathTxtEmail);

		path1 = excelCopiado
		nome1 = "RoboCriticasSIBGestaoCliente-'"+dataRecuerada+"'.xlsx";

		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));

		initDataFiles()
		if(!validaDados()){
			List<String> anexos = new ArrayList<>();
			anexos[0] = path1;
			anexos[1] = nome1;
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba )
		} else {
			System.err.println('Não existem dados para processar')
		}
	}

	/**
	 * @author T_DCDia 
	 * @return
	 */
	public static aplicacaoGestaoCliente(){
		WebUI.delay(5);
		//Gestão & Marketing
		clickXpath('/html/body/div/div/app-root/app-private/app-private-header/nx-epac-header/nx-epac-navigation/div/div[1]/nx-epac-navigation-desktop/div[1]/div/div/ul/li[2]/a/span', 5);
		// Gestão Clientes
		clickXpath('/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul/li[2]/a', 1);
	}

	/**
	 * @author T_DCDias
	 * @param anexos
	 * @return
	 */
	public static correcaoDeCampos(List<String> anexos){
		WebUI.delay(10);
		fluxoChaveCPF(anexos);
		if(alerta.equals(false)){
			alteraDados(anexos[1], anexos[2], anexos[3], anexos[4])
		}
	}

	/**
	 * @author T_DCDias
	 * @param cpf
	 * @return
	 */
	public static fluxoChaveCPF(List<String> anexos){

		alteraJanejaGenerica(1,2);
		WebUI.maximizeWindow()
		//CLICAR ALTERAR
		clickXpath('//*[@id="2"]', 5);

		//Seleciona CPF ou Nome conforme critica
		StrategyCriticasSIB.ajusteCriticaPesquisaCliente(anexos[4], anexos[0], anexos[5],anexos[1])

		//CLICAR PESQUISAR
		clickXpath('//*[@id="o_3"]', 1);

		String msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)

		System.err.println(' msg '+  msg)

		WebUI.delay(2);
		if(msg.contains('atributo nao encontrado'.toString())){
			alerta = false
			fluxoVerificaDuplicidade();
		}  else {
			alerta = true
		}
		System.err.println('alerta '+alerta)
	}

	/**
	 * @author T_DCDias
	 * @param cpf
	 * @return
	 */
	public static fluxoVerificaDuplicidade(){
		try{
			String valorRecuperado = recuperaValor('//*[@id="listaClientes"]/table/tbody/tr[1]/td/table/tbody/tr/td[2]', 'textContent', 10)
			if(valorRecuperado.contains('DADOS GERAIS'.toString())){
				//clica no primeiro da lista
				clickXpath('//*[@id="row_0_tabela"]', 0);
			}
		} catch (Exception e){}
	}

	/**
	 * @author T_DCDias
	 * @param cpf
	 * @return
	 */
	public static fluxoClassficarDuplicado(String cpf){
		//clicarUnificar
		clickXpath('//*[@id="4"]', 1);
		//clicarUnificarClientes
		clickXpath('//*[@id="41"]', 3);
		//pesquisa CPF
		preencheXpath('//*[@id="id_doc"]', cpf, 5);
		//CLICAR PESQUISAR
		clickXpath('//*[@id="o_5"]', 1);
		//fluxoClassificar
		fluxoClassificar();
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static fluxoClassificar(){
		String xpath = '//*[@id="tabela_tbody"]'
		String tag = "tr";
		List<WebElement> listOfElements
		listOfElements = listaDeElementos(xpath,tag)
		int qtdTotalTr = listOfElements.size()/2;
		System.err.println(qtdTotalTr)
		for(int i = 0; i < qtdTotalTr; i++){
			if(i<=0){
				clickXpath('//*[@id="tipo.unificacao'+i+'"]', 1);
				clickXpath('//*[@id="tipo.unificacao'+i+'"]/option[2]', 1);
			}else{
				clickXpath('//*[@id="tipo.unificacao'+i+'"]', 1);
				clickXpath('//*[@id="tipo.unificacao'+i+'"]/option[3]', 1);
			}
		}
		clickXpath('//*[@id="o_9"]', 1);
	}

	/**
	 * @author T_DCDias
	 * @param nomeMae
	 * @param pis
	 * @param email
	 * @return
	 */
	public static alteraDados(String novoValor, String pis, String email, String critica){
		System.err.println()
		try{
			//Email
			String emailRecuperado = recuperaValor('//*[@id="cliente.dadosContacto.email1"]', 'value', 5)
			println emailRecuperado
			if(emailRecuperado.equals('')){
				WebUI.delay(2);
				String xpath = '//*[@id="cliente.dadosContacto.indicadorDocElectronica"]'
				String tag = 'option'
				selecionaListaDeElementos(xpath, tag)
				for(int i = 0 ; i < listOfElements.size(); i++ ){
					if(listOfElements.get(i).getText().equals('Não')){
						listOfElements.get(i).click()
					}
				}
			}

			System.err.println('critica ['+critica+'] Novo valor ['+novoValor+"]")
			WebUI.delay(10);
			StrategyCriticasSIB.ajusteCriticaDadosGerais(critica, novoValor, pis)
			//IOF
			String IOF = recuperaValor('//*[@id="cliente.dadosEconomicosDemograficos.isencaoSelo"]', 'value', 1)
			if(IOF.equals('')){
				clickXpath('//*[@id="cliente.dadosEconomicosDemograficos.isencaoSelo"]', 0);
				clickXpath('//*[@id="cliente.dadosEconomicosDemograficos.isencaoSelo"]/option[3]', 0);
			}

			//salvar
			clickXpath('//*[@id="o_11"]', 5);
			WebUI.delay(10);
			capturaAlertaGestao('//*[@id="blDadosGeraisCliente"]/table/tbody/tr[1]/td', 'innerText')
			WebUI.delay(2);
			alerta = false
		} catch (Exception e){
			alerta = true
		}

	}

	/**
	 * @author T_DCDias
	 * @param ramo
	 * @return
	 */
	public static String gestaoCliente(String critica){
		Map<String, String> gestao = new HashMap<String, String>();
		gestao.put("[NOME MAE] - Nome da Mãe do beneficiário contém caracteres especiais.",'[NOME MAE] - Nome da Mãe do beneficiário contém caracteres especiais.');
		gestao.put("[Numero PIS/PASEP] - Numero PisPasep não pode ser diferente de 11 caracteres",'[Numero PIS/PASEP] - Numero PisPasep não pode ser diferente de 11 caracteres');
		gestao.put("[NOME MAE] - Correção no nome da mãe.",'[NOME MAE] - Correção no nome da mãe.');
		gestao.put("[DATA] - correção no campo data de nascimento.",'[DATA] - correção no campo data de nascimento.');
		gestao.put("[CPF] - correção no campo cpf.",'[CPF] - correção no campo cpf.');
		gestao.put("[E-MAIL] - correção no campo e-mail.",'[E-MAIL] - correção no campo e-mail.');
		return gestao.get(critica).toString()
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static String capturaAlerta(){
		WebUI.delay(5);
		String alertalRecuperado
		if(alerta.equals(false)){
			//			if(recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr', 'innerText', 5).contains('DADOS PESSOAIS'.toString())  ){
			alertalRecuperado = 'Operação concluída com sucesso.'
			//			} else {
			//				alertalRecuperado = 'Operação não realizada.'+msgAlerta
			//			}
		} else {
			//alertalRecuperado = 'Cliente inexistente.'
			alertalRecuperado = 'Operação não realizada.'+msgAlerta
		}
		return alertalRecuperado
	}

	/**
	 * @author T_DCDias
	 * @param ramo
	 * @return
	 */
	public static String alertas(String critica){
		Map<String, String> alertas = new HashMap<String, String>();
		alertas.put("Cliente inexistente.",'Cliente inexistente.');
		return alertas.get(critica).toString()
	}


	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static capturaAlertaGestao(String xpath , String propriedade){
		WebUI.delay(10);
		String alerta = recuperaValor(xpath, propriedade, 5)
		System.out.println(alerta)
		if(!alerta.contains("atributo nao encontrado".toString())){
			msgAlerta = alerta
		}
	}

}

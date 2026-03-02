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
import rpacommands.rpaCommands
import internal.GlobalVariable
import auxiliares.FileInput;
import auxiliares.StringFormatter;
import bean.MassaDados
import auxiliares.MenuMatriz;
import auxiliares.ExtractDates

public class ReprocessamentoEmissaoApoliceFuncionalidades extends rpaCommands{


	private static TestData data
	private static String path
	private static String fechaData
	private static List<MassaDados> listaExcel;
	private static String datasProcessadas
	private static StringBuilder strb = new StringBuilder()
	private static List<MassaDados> listaDatas
	
	/**
	 * @author ALZ_ServiceQA
	 * @param listaExcel
	 * @return
	 */
	public static List<MassaDados> selecionaDatas(){
		List<MassaDados> listaDatas = new ArrayList<MassaDados>()
		HashSet<String> datas = new HashSet<String>();
		
		System.out.println(data.getRowNumbers())
		
		for (int i = 1; i <= data.getRowNumbers(); i++) {
				if (data.getValue(59, i) == 'Processar') {
					datas.add(data.getValue(7, i))
				}
			}
		
		Object[] myArr = datas.toArray();

		for(int i = 0; i<myArr.length; i++){
			listaDatas.add(myArr[i])
		}
		return listaDatas
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static fluxoReprocessamentoEmissaoApolice(String tipoAba){
		getData()
		datasProcessadas = ""
		datasProcessadas = datasReenvioPropostas()
		System.err.println("\ndatasProcess ["+datasProcessadas+"]")
		if(carregaDados()){
			fluxoCompletoReprocessamentoEmissaoApolice(tipoAba)
			reprocessaFalhas(tipoAba)
			enviarEmailReprocessamentoEmissaoApolice(tipoAba)
		}
		WebUI.delay(5)
		finalizar();
	}
	
//	/**
//	 * @author ALZ_ServiceQA
//	 * @return
//	 */
//	public static datasReenvioPropostas(){
//		datasProcessadas = ''
//	//	listaDatas.clear()
//		listaDatas = selecionaDatas()
//
//		for(int J = 0; J < listaDatas.size(); J++){
//			System.out.println("["+J+"] ["+listaDatas.get(J).toString()+"]")
//			strb.append(ExtractDates.formatDateToBR(listaDatas.get(J).toString())+" ")
//		}
//		datasProcessadas = strb.toString()
//		System.out.println("Datas ["+listaDatas.size()+'] ['+datasProcessadas+"]")
//	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static fluxoCompletoReprocessamentoEmissaoApolice(String tipoAba){
//		datasProcessadas = ""
//		datasProcessadas = datasReenvioPropostas()
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands(tipoAba);
		WebUI.delay(10);
		MenuMatriz.producaoSinistro()
		MenuMatriz.subMenu("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div", "a","Produção")
		//botão operação pendentes
		WebUI.delay(15);
		alteraJanejaGenerica(1, 5)
		WebUI.maximizeWindow()
		clickXpath('//*[@id="oPendientes"]', 5)
		getData()
		reprocessamentoEmissaoApolice(tipoAba)
	}

	/**
	 * @author be54955
	 * @param tipoAba
	 * @return
	 */
	public static reprocessamentoEmissaoApolice(String tipoAba){
		try{
			//datasReenvioPropostas()
			for(int J = 0; J < listaDatas.size(); J++){
				for (int i = 1; i <= data.getRowNumbers(); i++) {
				  if (data.getValue(59, i) == 'Processar' && data.getValue(7, i) == listaDatas.get(J).toString() ) {
					  System.err.println('Line ['+i+'] Status ['+data.getValue(59, i)+'] Apolice ['+data.getValue(3, i)+'] Situação [ '+data.getValue(5, i)+'] FECHA [ '+data.getValue(7, i)+']')
					  fechaData = data.getValue(7, i)
					  List<String> apolices = new ArrayList<>();
					  apolices[0] = data.getValue(3, i); // apolice
					  apolices[1] = data.getValue(5, i); //ESTADO
					  reprocessar(apolices, i, tipoAba)
				  }
				}
			
			}
		} catch (Exception e) {
			System.err.println(e)
		}
	}


	public static reprocessar(List<String> apolices, int i, String tipoAba){
		//preencher campo Proposta com poliza
		preencheXpath('//*[@id="polSusep_Policy"]', apolices[0], 3);

		//Situação
		selecionaSituação(apolices[1])

		//botão pesquisar
		clickXpath('//*[@id="o_4"]', 5);

		WebUI.delay(10)
		String msgErro = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5);
		WebUI.delay(5)
		if(msgErro.equals('atributo nao encontrado')){

			//Emissão
			clickXpath('//*[@id="null" and @title="Emissão"]', 5);
			WebUI.delay(5)
			msgErro = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5);
			WebUI.delay(5)
			if(msgErro.equals('atributo nao encontrado')){
				WebUI.delay(10)
				//Gravar operação e salvar na planilha
				String susep =  recuperaValor('//*[@id="SUSEP"]', 'innerText', 5);
				if(!susep.equals('atributo nao encontrado')){
					i = i-1
					auxiliares.WriteExcelFactory.updateSheetLine('', i, path = data.sourceUrl, 'Processado', 58 , tipoAba);
					auxiliares.WriteExcelFactory.updateSheetLine('', i, path = data.sourceUrl,susep, 59 , tipoAba);
				}

				//BOTÃO CONTINUAR
				clickXpath('//*[@id="o_3"]', 5);
			} else {
				i = i-1
				auxiliares.WriteExcelFactory.updateSheetLine('', i, path = data.sourceUrl, 'Processado', 58 , tipoAba);
				auxiliares.WriteExcelFactory.updateSheetLine('', i, path = data.sourceUrl, msgErro, 60 , tipoAba);
				//FECHAR
				clickXpath('//*[@id="o_2"]', 5);
				alteraJanejaGenerica(0, 5)
				MenuMatriz.producaoSinistro()
				MenuMatriz.producao()
				alteraJanejaGenerica(1, 5)
				WebUI.maximizeWindow()
				clickXpath('//*[@id="oPendientes"]', 5)
				getData()
			}

		} else {
			i = i-1
			auxiliares.WriteExcelFactory.updateSheetLine('', i, path = data.sourceUrl, 'Processado', 58 , tipoAba);
			auxiliares.WriteExcelFactory.updateSheetLine('', i, path = data.sourceUrl, msgErro, 60 , tipoAba);
			//FECHAR
			clickXpath('//*[@id="o_2"]', 5);
			alteraJanejaGenerica(0, 5)
			MenuMatriz.producaoSinistro()
			MenuMatriz.producao()
			alteraJanejaGenerica(1, 5)
			WebUI.maximizeWindow()
			clickXpath('//*[@id="oPendientes"]', 5)
			getData()
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static selecionaSituação(String situacao){
		String xpath = '//*[@id="estadoOpPendiente"]'
		String tag ="option";
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			if(listOfElements[i].getText().contains(listaDeSituacoes(situacao.trim()))){
				listOfElements[i].click()
				break
			}
		}
	}

	/**
	 * @author T_DCDias
	 * @param situacao
	 * @return
	 */
	public static String listaDeSituacoes(String situacao){
		Map<String, String> situacoes = new HashMap<String, String>();
		situacoes.put("X",'Errada');
		situacoes.put("XX",'Errada Visa');
		situacoes.put("XV",'WebService Emisión');
		situacoes.put("XI",'Con Items Erróneos');
		return situacoes.get(situacao).toString()
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static TestData getData(){
		data = findTestData('Data Files/Auto/ReprocessamentoEmissaoApoliceData/ReprocessamentoEmissaoAp')
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean carregaDados(){
		boolean sizeTrue = false
		if(validaExecutados(getData(), 'Processar', 59)==false){
			if(getData().getRowNumbers()>0){
				sizeTrue = true;
			}
		}
		System.err.println(sizeTrue )
		return sizeTrue
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailReprocessamentoEmissaoApolice(String tipoAba){
//		datasReenvioPropostas()
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Auto/ReprocessamentoEmissaoApolice/ConfiguracoesReprocessamentoEmissaoApolice.txt";
		lerConfigurações(pathTxtEmail)
		String pathAnexos = "C:/Users/Public/RPA_ALLIANZ/Emails/Auto/ReprocessamentoEmissaoApolice/Anexos.txt"
		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
//		String dataAnterior = ExtractDates.retornaDataInferior(1)
//		String dataAnterior = ExtractDates.formatDateToBR(fechaData)
		sendEmail.CreateEmail.createEmailSendMultipleFilesReprocessaEmissaoApolice(sender, emaildestination, FileInput.readFile(pathAnexos), host, porta, tipoAba, datasProcessadas )
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static String datasReenvioPropostas(){
		data = findTestData('Data Files/Auto/ReprocessamentoEmissaoApoliceData/ReprocessamentoEmissaoAp')
		String datasProcessadas = ''
		try{
		   List<MassaDados> listDates = new ArrayList<MassaDados>()
		   listDates = selecionaDatas()
		
		   for(int J = 0; J < listDates.size(); J++){
			System.out.println("["+J+"] ["+listDates.get(J).toString()+"]")
			strb.append(ExtractDates.formatDateToBR(listDates.get(J).toString())+" ")
		   }
		   
		   listaDatas = listDates
		   datasProcessadas = strb.toString()
		   System.out.println("Datas ["+listaDatas.size()+'] ['+datasProcessadas+"]")
		} catch (Exception e){}
		return datasProcessadas
	}
	
	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static reprocessaFalhas(String tipoAba){
		System.err.println('reprocessaFalhas')
		if(carregaDados()){
			fluxoCompletoReprocessamentoEmissaoApolice(tipoAba)
			reprocessaFalhas(tipoAba)
		}
	}
}

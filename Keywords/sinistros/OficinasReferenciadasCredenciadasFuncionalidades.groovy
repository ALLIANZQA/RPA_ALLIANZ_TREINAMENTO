package sinistros

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

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
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import auxiliares.RegrasEmailDestination
import auxiliares.MenuMatriz
import internal.GlobalVariable
import rpacommands.rpaCommands
import auxiliares.ExtractDates

public class OficinasReferenciadasCredenciadasFuncionalidades extends rpaCommands {

	private static TestData dataOfininas
	private static TestData dataPacotes
	private static String pathOfininas;
	private static String pathPacotes;
	private static String alerta
	private static int cellCheck = 3
	private static String status
	private static String referencia
	private static String OfininasPath = 'Data Files/Sinistro/Oficinas/OficinasReferenciadasCredenciadasOficinas'
	private static String PacotesPath = 'Data Files/Sinistro/Oficinas/OficinasReferenciadasCredenciadasPacotes'

	private static StringBuilder strbdr = new StringBuilder();

	/**
	 * @author be54955
	 * @param aba
	 * @param a
	 * @return
	 */
	public static init(String aba, String a){
		try{
			acessoAmbiente(aba,a)
			WebUI.delay(10);
			alteraJanejaGenerica(1, 10)
			WebUI.delay(10);
		} catch (Exception e){}
	}



	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataOfininas(){
		dataOfininas = findTestData(OfininasPath)
		pathOfininas = dataOfininas.sourceUrl
		System.out.println(dataOfininas.getRowNumbers())
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static boolean validaDados(int cellStatus){
		boolean value
		for (int i = 1; i <= dataOfininas.getRowNumbers(); i++) {
			if (dataOfininas.getValue(cellStatus, i).equals('Processar')) {
				value = true
				break;
			} else {
				value = false
			}
		}
		System.err.println('Data ['+value+']')
		return value
	}




	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataPacotes(){
		dataPacotes = findTestData(PacotesPath)
		pathPacotes = dataPacotes.sourceUrl
		System.out.println(dataPacotes.getRowNumbers())
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static List<String> listaOfininas( List<String> registros , int v){
		try{
			for (int i = 1; i <= dataOfininas.getColumnNumbers(); i++) {
				int d = i -1
				registros[d] = dataOfininas.getValue(i, v).trim()
			}

			for (int j = 0; j < registros.size();j++ ){
				System.err.println("["+j+"] ["+registros[j]+"]")
			}

			return registros
		}  catch (Exception e){
			System.err.println('\n['+e+']')
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static List<String> listaPacotes( List<String> registros , int v){
		try{
			for (int i = 1; i <= dataPacotes.getColumnNumbers(); i++) {
				int d = i -1
				if(dataPacotes.getValue(i, v).trim().length()>0){
					registros[d] = dataPacotes.getValue(i, v).trim()
				}
			}

			for (int j = 0; j < registros.size();j++ ){
				System.err.println("["+j+"] ["+registros[j]+"]")
			}

			return registros
		}  catch (Exception e){
			System.err.println('\n['+e+']')
		}
	}





	public static fluxoOficinasReferenciadasCredenciadas(String aba, String a){
		initDataPacotes()
		initDataOfininas()
		fluxoCompletoOficinasReferenciadasCredenciadas(aba, a);
		reprocessaFalhas(aba,a);
		enviarEmailOficinasReferenciadasCredenciadas(aba);
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailOficinasReferenciadasCredenciadas(String tipoAba){
		initDataPacotes()
		initDataOfininas()
		if(!validaDados(cellCheck)){
			pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Sinistros\\Oficinas\\ConfiguracoesOficinasReferenciadasCredenciadas.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = pathOfininas
			anexos[1] = "OficinasReferenciadasCredenciadas.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba, String a){
		System.err.println('Reprocessa Falhas')
		try{
			initDataPacotes()
			initDataOfininas()
			if(validaDados(cellCheck)){
				fluxoCompletoOficinasReferenciadasCredenciadas(aba, a);
				reprocessaFalhas(aba)
			}
		} catch (Exception e){
			reprocessaFalhas(aba, a)
		}
	}

	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoOficinasReferenciadasCredenciadas(String aba, String a){
		System.out.println()
		try{
			if(validaDados(cellCheck)){
				init(aba,a)
				MenuMatriz.producaoSinistro()
				MenuMatriz.subMenu('/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div/div/div[2]/ul', "a","Prestadores")
				for(int i = 1; i <= dataOfininas.getRowNumbers(); i++) {
					int j =  i - 1
					alerta = '';
					status = "";
					if (dataOfininas.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						listaOfininas( registros , i)
						registros[3] = "Oficinas";
						if(oficinas(registros, aba, a)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, pathOfininas, status, 2, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, pathOfininas, alerta+" - "+ExtractDates.dataAtualizada() , 3, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, pathOfininas, status, 2, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, pathOfininas, alerta+" - "+ExtractDates.dataAtualizada(), 3, aba);
							} catch (Exception e){}
						}
						//System.out.println()
					}
				}
			}
		} catch (Exception e){}
	}

	/**
	 * @author BE54955
	 * @param registros
	 * @param aba
	 * @param a
	 * @return
	 */
	public static boolean  oficinas(List<String> registros, String aba, String a){
		boolean b
		String tela
		System.out.println()
		alteraJanejaGenerica(1, 5)
		tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
		System.out.println(tela)
		if(listaDeTelas(tela)){
			//Gestão de prestadores
			selecionaItemListaContains("Gestão prestadores", '//*[@id="tableOptMenu_"]' , 'td')
			tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 0)
			//System.out.println(tela)
			if(listaDeTelas(tela)){
				//CPF/CNPJ
				preencheXpath('//*[@id="nif1_doc"]', registros[0],0);
				//Tipo Prestador
				selecionaItemListaContains(registros[3], '//*[@id="tipusProveidor"]' , 'option')
				//Pesquisar
				selecionaItemListaContains("Pesquisar", '//*[@id="SeccioRecercaSectionBar"]/table/tbody/tr/td/table' , 'div')
				//Prestadores
				selecionaPrestador(registros[0])
				tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
				//System.out.println(tela)
				if(listaDeTelas(tela)){
					//SUB MENU DE BOTÕES
					selecionaItemListaContains("Serviços Ad.", '//*[@id="MantenimientoViewForm"]/table[21]/tbody/tr[2]/td/table' , 'div')
					System.out.println()
					//Seleciona Serviços
					if(selecionaPacote(registros[1])){
						alerta = "Serviços gravados ";
						b = true;
					} else {
						alerta = "Serviço não encontrado: "+strbdr.toString()
						b = false;
					}
					//Gestão de prestadores
					selecionaItemListaContains("Gestão prestadores", '//*[@id="tableOptMenu_"]' , 'td')
					System.out.println(b)
					status = "Processado";
					return b
				} else {
					alerta = "Tela [GESTÃO OFICINAS] não paresentada ";
					status = "Processar";
					b = false;
				}
			} else {
				alerta = "Tela [GESTÃO PRESTADORES] não paresentada ";
				status = "Processar";
				b = false;
			}
		} else {
			alerta = "Tela [PRESTADORES] não paresentada ";
			status = "Processar";
			b = false;
		}
	}

	/**
	 * @author BE54955
	 * @param documento
	 * @return
	 */
	public static selecionaPrestador(String documento){
		WebUI.delay(2)
		selecionaListaDeElementos('//*[@id="taula"]',"tr")
		for(int i = 0; i < listOfElements.size(); i++){
			//System.err.println("["+i+"] ["+listOfElements[i].getText()+"] ["+documento+"]")
			if(listOfElements[i].getText().contains(documento.toString())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				listOfElements[i].click()
				break
			}
		}
	}

	/**
	 * @author BE54955
	 * @param p
	 * @return
	 */
	public static selecionaPacote(String p){
		boolean b
		int pacs
		int servs
		for(int i = 1; i <=dataPacotes.getRowNumbers() ; i++) {
			List<String> pacotes = new ArrayList<>();
			listaPacotes(pacotes,i)
			if(pacotes[0].equals(p)){
				//System.out.println("\nPACOTE ["+pacotes[0]+"]  PACOTE PARA SELECIONAR ["+p+"]\n")
				pacs = pacotes.size();
				servs = preencheServicos(pacotes)
			}
		}
		b = verificaServicosSelecionados(pacs,servs);
		return b;
	}

	/**
	 * @author BE54955
	 * @param p
	 * @param s
	 * @return
	 */
	public static verificaServicosSelecionados(int p, int s){
		System.out.println("PACS ["+p+"] SERVS ["+s+"]")
		boolean b
		if(p==s){
			b = true;
		} else {
			b = false;
		}
		return b;
	}

	/**
	 * @author BE54955
	 * @param pacotes
	 * @return
	 */
	public static int preencheServicos(List<String> pacotes){
		System.out.println()
		//System.out.println(pacotes.size())
		int servs = 0;
		int j = pacotes.size();
		for(int i = 1; i<pacotes.size() ; i++){
			//System.out.println("Serviço: "+pacotes[i]);
			WebUI.delay(2);
			if(selecionaServico(pacotes[i], '//*[@id="servicio"]' , 'option').equals(false)){
				strbdr.append(pacotes[i]+" | ");
				j--;
			} else {
				//Adiciona Serviço
				clickXpath('//*[@id="seccionServiciosContent"]/table/tbody/tr/td[2]', 1);
			}
		}
		return j
	}

	/**
	 * @author T_DCDias
	 * @return
	 * Seleciona o valor do serviço 
	 */
	public static boolean selecionaServico(String tipo,String xpath , String tag ){
		boolean s = false;
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			//System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().equals(tipo.toString())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				listOfElements[i].click()
				s = true;
				break
			}
		}
		System.out.println("["+s+"]")
		return s
	}


}

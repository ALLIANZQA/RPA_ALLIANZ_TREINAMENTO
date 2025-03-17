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

public class OficinasNaoReferenciadasFuncionalidades extends rpaCommands {

	private static TestData dataOfininas
	private static TestData dataPacotes
	private static String pathOfininas;
	private static String pathPacotes;
	private static String alerta
	private static int cellCheck = 3
	private static String status
	private static String referencia
	private static String OfininasPath = 'Data Files/Sinistro/OficinasNaoReferenciadas/OficinasNaoReferenciadasData'

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


	public static fluxoOficinasNaoReferenciadas(String aba, String a){
		initDataOfininas()
		fluxoCompletofluxoOficinasNaoReferenciadas(aba, a);
		reprocessaFalhas(aba,a);
		enviarEmailOficinasNaoReferenciadas(aba);
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailOficinasNaoReferenciadas(String tipoAba){
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

			initDataOfininas()
			if(validaDados(cellCheck)){
				fluxoCompletofluxoOficinasNaoReferenciadas(aba, a);
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
	public static fluxoCompletofluxoOficinasNaoReferenciadas(String aba, String a){
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


				//Alerta de dados
				alerta = recuperaValor('//*[@id="taula_tbody"]', 'innerText', 0)
				if(!alertListBlocList(alerta)){

					alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 1)
					System.out.println(alerta)
					if(!alertListBlocList(alerta )){
						//Prestadores
						selecionaPrestador(registros[0])
						tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
						System.out.println(tela)
						if(listaDeTelas(tela)){
							//SUB MENU DE BOTÕES
							selecionaItemListaContains("F.Oficinas", '//*[@id="MantenimientoViewForm"]/table[21]/tbody/tr[2]/td/table' , 'div')
							tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
							if(listaDeTelas(tela)){
								//Alterar
								selecionaItemListaContains("Alterar", '//*[@id="FichaOficinasViewForm"]/table[1]' , 'div')
								System.out.println()
								WebUI.delay(5)

								System.out.println()
								if(registros[1].toString().equals( tipoVeiculoMultimarca(registros[1].toString()))){
									if(selecionaMultimarca(registros[1], '//*[@id="concessionario"]' , 'option')){
										alerta = retornoTipoVeiculo(registros[1]);
										selecionaItemListaContains("Salvar", '//*[@id="FichaOficinasViewForm"]/table[1]' , 'div')
										b = true
									}

								} else {


									if(informaTecnicaOficinasSelecionada(registros[1])){

										//Seleciona Serviços
										if(selecioInformacaoTecnicaOficinas(registros[1])){
											alerta = retornoTipoVeiculo(registros[1]);
											//Salvar
											selecionaItemListaContains("Salvar", '//*[@id="FichaOficinasViewForm"]/table[1]' , 'div')
											b = true;
										} else {
											alerta = retornoTipoVeiculoNaoGravado(registros[1]);
											b = false;
										}

									} else {
										alerta = retornoTipoVeiculoJaSelecionado(registros[1]);
									}

								}

								//Gestão de prestadores
								selecionaItemListaContains("Gestão prestadores", '//*[@id="tableOptMenu_"]' , 'td')
								System.out.println(b)
								status = "Processado";
								return b
							}
						} else {
							alerta = "Tela [INFORMAÇÃO TÉCNICA OFICINAS] não apresentada ";
							status = "Processar";
							b = false;
						}

					} else {
						b = false;
						status = "Processado";
						//Gestão de prestadores
						selecionaItemListaContains("Gestão prestadores", '//*[@id="tableOptMenu_"]' , 'td')
					}



				} else {
					alerta = "Prestador do tipo Oficinas não encontrado. ";
					status = "Processado";
					b = false;
					//Prestadores
					selecionaItemListaAtributo("Início",'//*[@id="cellMenuIconsAppNaviHeaderFilledArea"]' , 'img', 'title' )
				}
			} else {
				alerta = "Tela [GESTÃO PRESTADORES] não apresentada ";
				status = "Processar";
				b = false;
			}
		} else {
			alerta = "Tela [PRESTADORES] não apresentada ";
			status = "Processar";
			b = false;
		}
	}




	/**
	 * @author BE54955
	 * @param tipo
	 * @return
	 */
	public  static String retornoTipoVeiculoJaSelecionado(String tipo){
		Map<String, String> veiculo = new HashMap<String, String>();
		veiculo.put("Motocicleta",tipo+" já selecionada");
		veiculo.put("Turismo",tipo+" já selecionado");
		veiculo.put("Caminhão",tipo+" já selecionado");
		return veiculo.get(tipo);;
	}

	/**
	 * @author BE54955
	 * @param tipo
	 * @return
	 */
	public  static String retornoTipoVeiculo(String tipo){
		Map<String, String> veiculo = new HashMap<String, String>();
		veiculo.put("Motocicleta",tipo+" gravada");
		veiculo.put("Turismo",tipo+" gravado");
		veiculo.put("Caminhão",tipo+" gravado");
		veiculo.put("Multimarcas","Multimarca selecionado");
		return veiculo.get(tipo);;
	}

	/**
	 * @author BE54955
	 * @param tipo
	 * @return
	 */
	public  static String retornoTipoVeiculoNaoGravado(String tipo){
		Map<String, String> veiculo = new HashMap<String, String>();
		veiculo.put("Motocicleta",tipo+" não gravada");
		veiculo.put("Turismo",tipo+" não gravado");
		veiculo.put("Caminhão",tipo+" não gravado");
		veiculo.put("Multimarcas","Multimarca selecionado");
		return veiculo.get(tipo);;
	}

	/**
	 * @author BE54955
	 * @param p
	 * @return
	 */
	public static selecionaPrestador(String p){
		selecionaListaDeElementos('//*[@id="taula_tbody"]',"tr")
		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println("["+i+"] ["+listOfElements[i].getText()+"] ["+p+"]")
			if(listOfElements[i].getText().contains(p)){
				listOfElements[i].click()
				break
			}
		}
	}

	/**
	 * @author BE54955
	 * @param ifoTec
	 * @return
	 */
	public static boolean selecioInformacaoTecnicaOficinas(String infoTec){
		boolean info = false;
		alerta="";

		selecionaListaDeElementos('//*[@id="FichaOficinasViewForm"]',"input")
		for(int i = 0; i < listOfElements.size(); i++){
			//System.err.println("["+i+"] ["+listOfElements[i].getAttribute("checked")+"] ["+listOfElements[i].getAttribute(tipoVeiculo(infoTec))+"] ["+infoTec+"]")
			System.err.print("*");
			if(listOfElements[i].getAttribute("name").contains(tipoVeiculo(infoTec))&& listOfElements[i].getAttribute("value").contains("S")){
				System.err.println("\nFOUND ["+i+"] ["+listOfElements[i].getAttribute("checked")+"] ["+
						listOfElements[i].getAttribute("name")+"] ["+
						listOfElements[i].getAttribute("value")+"]")
				listOfElements[i].click()
				info = true
				break
			}
		}

		return info;
	}

	/**
	 * @author BE54955
	 * @param ifoTec
	 * @return
	 */
	public static boolean informaTecnicaOficinasSelecionada(String infoTec){
		boolean info = false;
		alerta="";
		selecionaListaDeElementos('//*[@id="FichaOficinasViewForm"]',"input")
		for(int i = 0; i < listOfElements.size(); i++){
			//System.err.println("["+i+"] ["+listOfElements[i].getAttribute("checked")+"] ["+listOfElements[i].getAttribute(tipoVeiculo(infoTec))+"] ["+infoTec+"]")
			System.err.print("*");
			if(listOfElements[i].getAttribute("checked").equals(null)&&
			listOfElements[i].getAttribute("name").contains(tipoVeiculo(infoTec))&&
			listOfElements[i].getAttribute("value").contains("S")
			){
				System.err.println("\nFOUND ["+i+"] ["+listOfElements[i].getAttribute("checked")+"] ["+
						listOfElements[i].getAttribute("name")+"] ["+
						listOfElements[i].getAttribute("value")+"]")
				info = true
				break
			}
		}
		return info;
	}

	/**
	 * @author BE54955
	 * @param tipo
	 * @return
	 */
	public  static String tipoVeiculo(String tipo){
		Map<String, String> veiculo = new HashMap<String, String>();
		veiculo.put("Motocicleta",'motocicletaSN');
		veiculo.put("Turismo",'turismeSN');
		veiculo.put("Caminhão",'camioSN');
		return veiculo.get(tipo);;
	}


	/**
	 * @author BE54955
	 * @param tipo
	 * @return
	 */
	public  static String tipoVeiculoMultimarca(String tipo){
		Map<String, String> veiculo = new HashMap<String, String>();
		veiculo.put("Concessionária",'Concessionária');
		veiculo.put("Multimarcas",'Multimarcas');
		return veiculo.get(tipo);;
	}


	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean  selecionaMultimarca(String tipo,String xpath , String tag ){
		WebUI.delay(2)
		boolean item = false ;
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains(tipo.toString())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				listOfElements[i].click()
				item = true;
				break
			}
		}
		return item;
	}






}

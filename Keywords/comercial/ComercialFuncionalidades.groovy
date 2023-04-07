package comercial

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

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

import internal.GlobalVariable
import rpacommands.rpaCommands
import auxiliares.StringFormatter
import auxiliares.FileInput


public class ComercialFuncionalidades extends rpaCommands{

	private static TestData data
	private static String path
	private static String url = "https://www2.susep.gov.br/safe/Corretores/"
	private static String pathFile
	private static String dataRecuerada
	private static int pagina
	private static String novoDiretorio
	private static String para
	private static nomeArquivo
	private static int totalTabelas
	private static String pathLogPage
	private static int   paginaSalva
	private static String pathXL = "C:/Users/Public/RPA_ALLIANZ/Data Files/Comercial/inclusaosusep/InclusaoSUSEP.xlsx"
	private static int x = 0



	private static String nome
	private static String numeroCorretor
	private static String CPFCNPJ
	private static String recad
	private static String situacao
	private static String produtos
	private static String testDataPath = "Data Files/Comercial/inclusaosusep/inclusaosussepdata"


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
	public static validaDados(){

		//		x = data.getRowNumbers()
		//		x = x +1

		x = 3565
		System.err.println("RowNumbers "+x)
		System.err.println()

		//		boolean value
		//		for (int i = 1; i <= data.getRowNumbers(); i++) {
		//			if (data.getValue(cellStatus, i).equals('Processar')) {
		//				value = true
		//				break;
		//			} else {
		//				value = false
		//				//		break;
		//			}
		//		}
		//		System.err.println('Data ['+value+']')
		//		return value
	}


	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static consultaSUSEP(String aba, String file){
		
		
		//AGRESTA E ASS0CIAD0S C0RRETORA 
		//Alyne Bueno Simões
//    	String texto = 'SU SEG ADM E CORRETORA DE SEGUROS LTDA	202091762	0_.___.___/____-__	Sim	Ativo	Microsseguros, Planos de Capitalização, Planos de Previdência Complementar, Seguros de Danos, Seguros de Pessoas'
//		String texto = "Benone Lima Corretora de Seguros de Vida |Ltda 202072097 01.147.890/0001-21 Sim Ativo Microsseguros, Planos de Capitalização, Planos de Previdência Complementar, Seguros de Danos, Seguros de Pessoas" 
//      String texto = "ESTEVAO DE OLIVEIRA PINHEIRO CPF11273O5O69O 2O2O22637 22.4O5.OO4/OOO1-92 Sim Ativo Microsseguros, Planos de Capitalização, Planos de Previdência Complementar, Seguros de Danos, Seguros de Pessoas"
//	    String texto = "IVANI FREIRIA O1O.79O.728-37 2O1O1O458 ***.79O.728-** Sim Ativo Microsseguros, Planos de Capitalização, Planos de Previdência Complementar, Seguros de Danos, Seguros de Pessoas" 
//		String texto = "Rua Francisco rodrigues,3O ap104 2O1O1O458 ***.79O.728-** Sim Ativo Microsseguros, Planos de Capitalização, Planos de Previdência Complementar, Seguros de Danos, Seguros de Pessoas"
		
//		StringFormatter.tokenStringSUSEP(texto)
	
	    configuraParametros(file)
		if(fluxoCompletoConsulta(aba, file)){
			deletaArquivoGenerico(pathLogPage);
		}
		enviarEmailConsultaSUSEP(aba)
	}
	
	
	
	
	
	/**
	 * @author be54955
	 * @return
	 */
	public static configuraParametros(String file){
		pathLogPage = "C:\\Users\\Public\\logpage.txt"
		dataRecuerada = getDateHour();
		nomeArquivo = "logsusep_"+dataRecuerada+".txt"
		pathFile = "C:\\Users\\Public\\"+nomeArquivo
		totalTabelas = 0
		pagina = 1

		switch (file) {
			case "xlsx":
				validaDados()
				break;
			case "txt":
				cretFile(pathLogPage)
				break;
		}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static boolean fluxoCompletoConsulta(String aba, String file){
		boolean fluxoCompleto
		if(manipulaArquivo()){
			try{
				WebUI.openBrowser(url)
				//Encontrar um corretor
				WebUI.delay(5);
				clickXpath('//*[@id="navigation"]/ul/li[1]/a/span', 6);
				//Encontrar um corretor
				WebUI.delay(5);
				clickXpath('//*[@id="content"]/div/div/div/section/form/div[4]/button', 6);
				//TABELADE CORRETORES
				WebUI.delay(10);
				selecionaPaginaAposFalha()

				totalTabelas = recupetaNumTabelas()

				System.err.println("Total de tabelas ["+ totalTabelas+"]")

				BufferedWriter writer
				for(int p = 1; p < totalTabelas; p++){
					System.out.println("\nPagina ["+pagina+"]\n")
					String xpath2 = '//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div/table/tbody'
					String tag2 = 'tr'
					int l = 1

					selecionaListaDeElementos(xpath2, tag2)
					for(int i = 0 ; i < listOfElements.size(); i++ ){
						if(i < 25){
							
//						System.out.println(listOfElements.get(i).getText())	
						 
//							nome =   recuperaValor('//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div/table/tbody/tr['+l+']/td[1]', 'innerText', 1);
//							numeroCorretor =   recuperaValor('//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div/table/tbody/tr['+l+']/td[2]', 'innerText', 1);
//							CPFCNPJ =   recuperaValor('//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div/table/tbody/tr['+l+']/td[3]', 'innerText', 1);
//							recad =   recuperaValor('//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div/table/tbody/tr['+l+']/td[4]', 'innerText', 1);
//							situacao =   recuperaValor('//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div/table/tbody/tr['+l+']/td[5]', 'innerText', 1);
//							produtos =   recuperaValor('//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div/table/tbody/tr['+l+']/td[6]', 'innerText', 1);

							//System.err.println("["+l+"] ["+ nome+"] ["+numeroCorretor+"] ["+CPFCNPJ+"] ["+recad+"] ["+situacao+"] ["+produtos)

							switch (file) {

								case "xlsx":
								   nome =   recuperaValor('//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div/table/tbody/tr['+l+']/td[1]', 'innerText', 1);
								   numeroCorretor =   recuperaValor('//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div/table/tbody/tr['+l+']/td[2]', 'innerText', 1);
								   CPFCNPJ =   recuperaValor('//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div/table/tbody/tr['+l+']/td[3]', 'innerText', 1);
								   recad =   recuperaValor('//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div/table/tbody/tr['+l+']/td[4]', 'innerText', 1);
								   situacao =   recuperaValor('//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div/table/tbody/tr['+l+']/td[5]', 'innerText', 1);
								   produtos =   recuperaValor('//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div/table/tbody/tr['+l+']/td[6]', 'innerText', 1);
								
									if(!nome.contains("atributo nao encontrado")){
										auxiliares.WriteExcelFactory.updateSheetLine("", x,pathXL, nome, 0, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", x,pathXL,  numeroCorretor, 1, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", x,pathXL, CPFCNPJ, 2, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", x,pathXL, recad, 3, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", x,pathXL, situacao, 4, aba);
										auxiliares.WriteExcelFactory.updateSheetLine("", x,pathXL, produtos, 5, aba);
									}
									x++
									break;

								case "txt":
								//	if(!nome.contains("atributo nao encontrado")){
										System.out.print("*")
										writer = new BufferedWriter(new FileWriter(para+"\\"+nomeArquivo, true) );
										writer.newLine();   //Add new line
										writer.write(StringFormatter.tokenStringSUSEP(listOfElements.get(i).getText()));
								//		writer.write(nome+" | "+numeroCorretor+" | "+CPFCNPJ+" | "+recad+" | "+situacao+" | "+produtos);
										writer.close()
								//	}
								//	break;
							}

							l++
						//	WebUI.delay(2);
						}
					}
					paginaSalva = pagina
					logPages(paginaSalva)
					pagina++
					WebUI.delay(2);
					clickXpath('//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div/div/div/nav[2]/ul/li[4]/a/i', 1);
					WebUI.delay(5);
				}
				fluxoCompleto = true
			} catch (Exception e){
				fluxoCompleto = false
				System.err.println("Last page saved ["+paginaSalva+"]")
				logPages(paginaSalva)
			}

		}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static cabecalho(){
		BufferedWriter writer = new BufferedWriter(new FileWriter(para+"\\"+nomeArquivo, true) );
		writer.newLine();   //Add new line
		writer.write("Nome   | 	Número de corretor *	|   CPF/CNPJ	  |     Recad.	 |   Situação	|   Produtos");
		writer.close()
	}


	/**
	 * @author be54955
	 * @return
	 */
	public static int recupetaNumTabelas(){
		StringBuffer strb = new StringBuffer()
		String tabelas
		String xpath = '//*[@id="content"]/corretores-pequisa/section/div/div/div/div/div'
		String tag = 'li'
		selecionaListaDeElementos(xpath, tag)
		for(int i = 0 ; i < listOfElements.size(); i++ ){
			if(i == 1){
				strb.append(listOfElements.get(i).getText())
			}
		}

		String[] tokens = strb.toString().split(" ")

		for(int j = 0; j < tokens.size(); j++){
			if(j == 1){
				tabelas = tokens[j].toString()
			}
		}

		String str = tabelas.replaceAll("[.]","")

		return Integer.parseInt(str)
	}

	/**
	 * @author be54955
	 * @param pagina
	 * @return
	 */
	public static logPages(int pagina){
		try{
			System.err.println("\nSave Page ["+pagina+"]")
			addLine(String.valueOf(pagina))
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param line
	 * @return
	 */
	public static addLine(String line){
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(pathLogPage, true) );
			writer.newLine()   //Add new line
			writer.write( String.valueOf(line));
			writer.close()
		} catch (Exception e){}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param apolice
	 * @return
	 */
	public static boolean manipulaArquivo(){
		boolean bmanipulaArquivo
		if(cretFile(pathFile)){
			try{
				novoDiretorio = "LOGSUSEP"
				System.err.println("novoDiretorio "+novoDiretorio)
				createDiretory(novoDiretorio)
				WebUI.delay(5);
				WebUI.delay(15);
				if(!validaExistenciaDeArquivo(pathFile)){
					WebUI.delay(2);
					para = "C:\\Users\\Public\\"+novoDiretorio
					moveArquivoSUSEP(pathFile, para)
					WebUI.delay(5);
					cabecalho()
					bmanipulaArquivo = true
				} else {
					bmanipulaArquivo = false
				}
			} catch (Exception e){
				System.err.println("FAIL\n"+e)
				bmanipulaArquivo = false
			}
			return bmanipulaArquivo
		}
	}

	/**
	 * @author be54955	
	 * @return
	 */
	public static compactaArquivos(){
		zipDirectory('C:/Users/Public/'+novoDiretorio, 'C:/Users/Public/'+novoDiretorio+'.zip')
	}

	/**
	 * @author T_DCDias
	 * @param caminhoFonte
	 * @param caminhoSaida
	 * @return
	 */
	public static moveArquivoSUSEP(String caminhoFonte, String caminhoSaida){
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
	 * @author be54955
	 * @param path
	 * @return
	 */
	public static boolean cretFile(String path){
		boolean create
		try{
			File file = new File(path)

			if (file.createNewFile())
			{
				System.out.println("File is created!");
				create = true
			} else {
				System.out.println("File already exists.");
				create = true
			}
		} catch (Exception e){
			create = false
		}
		System.out.println("File is created! "+create);
		return create
	}

	/**
	 * @author T_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailConsultaSUSEP(String tipoAba){
		try{
			File arquivo = new File(pathLogPage);
			if(!arquivo.exists()) {
				pathTxtEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Comercial\\consultaSUSEP\\ConfiguracoesconsultaSUSEP.txt";
				lerConfigurações(pathTxtEmail)
				String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
				//compactaArquivos()
				List<String> anexos = new ArrayList<>();
				anexos[0] = "C:\\Users\\Public\\LOGSUSEP\\"+nomeArquivo
				anexos[1] = nomeArquivo
				sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, pwdsender, anexos, host, porta, tipoAba)
			}
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param numPag
	 * @return
	 */
	public static selecionaPaginaAposFalha(){
		try{
			String numPag
			File arquivo = new File(pathLogPage);
			if (!arquivo.exists()) {
				System.out.println("Arquivo não encontrado");
			} else {
				List<String> linhas = FileInput.readFile(pathLogPage)
				for(int i = 0; i < linhas.size();i++){
					System.err.println(i+" "+linhas[i].toString())
					numPag = linhas[i].toString()
				}

				pagina = Integer.parseInt(numPag)
				pagina++
				String xpaths = "//ul[@class='br-pagination pr-2']//li//select"
				String tags = 'option'
				selecionaListaDeElementos(xpaths, tags)
				for(int j = 0 ; j < listOfElements.size(); j++ ){
					if(listOfElements.get(j).getText().equals(String.valueOf(pagina))){
						listOfElements.get(j).click()
						break
					}
				}
			}
			WebUI.delay(5);
		} catch (Exception e){}

	}

	public static formatterText(String text){
		String[] token =  text.split(" | ")
		for(int i = 0; i < token.size(); i++){
			StringBuffer strb = new StringBuffer()
			System.err.println("["+i+"] ["+token[i]+"]")
		}
	}

}

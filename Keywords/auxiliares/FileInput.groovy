package auxiliares;

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint;
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase;
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData;
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject;

import com.kms.katalon.core.annotation.Keyword;
import com.kms.katalon.core.checkpoint.Checkpoint;
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW;
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile;
import com.kms.katalon.core.model.FailureHandling;
import com.kms.katalon.core.testcase.TestCase;
import com.kms.katalon.core.testdata.TestData;
import com.kms.katalon.core.testobject.TestObject;
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS;
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI;

import internal.GlobalVariable;

public class FileInput {

	static FileInputStream file;

	/**
	 * @author T_DCDias
	 * @param caminho_arquivo_xlsx
	 * @return
	 * @throws FileNotFoundException
	 * Efetua leitura de arquivos XLSX
	 */
	public static FileInputStream fileInput(String caminho_arquivo_xlsx) throws FileNotFoundException {
		file = new FileInputStream(new File(caminho_arquivo_xlsx));
		return file;
	}

	/**
	 *@author T_DCDias 
	 * @throws IOException
	 */
	public static void closeFileInputStream() throws IOException {
		file.close();
	}

	/**
	 * @author T_DCDias
	 * @param fileRequestXml
	 * @return
	 * @throws FileNotFoundException
	 * Efetua leitura de aquivo XML 
	 */
	public static String retornaXmlFileToString(String fileRequestXml) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(new File(fileRequestXml)));
		String line;
		StringBuilder sb = new StringBuilder();
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}


	/**
	 * @author ALZ_ServiceQA
	 * @return
	 * @throws IOException
	 */
	public static List<String>  readFile(String path) throws IOException {
		List<String> linhas = new ArrayList<>();
		BufferedReader bR = new BufferedReader(new FileReader(path));
		String linha;
		while( (linha = bR.readLine()) != null){
			linhas.add(linha);
		}
		bR.close();
		return linhas;
	}
}

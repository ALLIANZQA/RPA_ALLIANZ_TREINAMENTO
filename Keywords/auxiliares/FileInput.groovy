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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption
import java.nio.file.Path;

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

	/**
	 * @author ALZ_SUP54955
	 * @param pathFileValue
	 * @param namePdf
	 * @return
	 */
	public static boolean renomearArquivo(String pathFileValue, String namePdf){
		boolean b=false;
		String novoNome = "C:\\Users\\Public\\"+namePdf+".PDF"
		try{


			File f = new File(pathFileValue);
			if (f.renameTo(new File(novoNome))) {
				System.out.println("Arquivo renomeado");
				b =true;
			}
			else{
				System.out.println("Arquivo renomeado");
				b = false;
			}
		} catch (Exception e){}
		return b;
	}

	/**
	 * @author ALZ_SUP54955
	 * @param pathFileValue
	 * @return
	 */
	public static boolean arquivoExiste(pathFileValue){
		boolean b;
		try{
			File f = new File(pathFileValue);
			if (f.exists()) {
				System.out.println("Arquivo existe");
				b =true;
			}
			else{
				System.out.println("Arquivo não existe");
				b = false;
			}
		} catch (Exception e){}
		return b;
	}
	
	
	
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
	 * @author ALZ_SUP54955
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
	 * @author ALZ_SUP54955
	 * @param pathFileValue
	 * @return
	 */
	public static deletaArquivoPdf(String pathFileValue){
		try{
		
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
	 * @author ALZ_SUP54955
	 * @param start
	 * @param pathFile
	 */
	public static void criaLog(String start, String pathFile){
		List<String> linhas = Arrays.asList(start);
		try{
			Path path = Paths.get(pathFile);
			Files.write(path, linhas);
			System.out.println("Arquivo criado em: "+ path.toAbsolutePath()  )
		} catch (IOException e){
		  System.err.print("Erro ao criar arquivo: "+e.getMessage());
		  e.printStackTrace();
		}
	}
	
	/**
	 * @author ALZ_SUP54955
	 * @param end
	 * @param pathFile
	 */
	public static void atualizaLog(String end, String pathFile){
		List<String> linhas = Arrays.asList(end);
		try{
			Path path = Paths.get(pathFile);
			if(!Files.exists(path)){
				Files.createFile(path);
			}
			Files.write(path, linhas, StandardOpenOption.APPEND);
			System.out.println("Arquivo atualizado em: "+ path.toAbsolutePath()  )
		} catch (IOException e){
		  System.err.print("Erro ao criar arquivo: "+e.getMessage());
		  e.printStackTrace();
		}
	}
	
	/**
	 * @author ALZ_SUP54955
	 * @param pathFile
	 * @return
	 */
	public static List<String> lerArquivo(String pathFile){
		List<String> linhas
		Path path = Paths.get(pathFile);
		if(!Files.exists(path)){
			System.err.print("Erro ao procurar arquivo:\n"+path.toAbsolutePath());
			return;
		}
		
		try{
			linhas = Files.readAllBytes(pathFile);
			for(String linha : linhas){
				System.out.println("["+linha+"]");
			}
		
		} catch (IOException e){
		  System.err.print("Erro ao ler arquivo: "+e.getMessage());
		  e.printStackTrace();
		}
		return linhas;
	}
	
	
	
	
	
}

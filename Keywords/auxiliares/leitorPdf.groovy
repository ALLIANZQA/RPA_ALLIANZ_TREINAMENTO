package auxiliares

import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import java.io.File
import java.io.FileOutputStream;
import java.util.List

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

///////////////////////////////////////////////////////////////////////////



public class leitorPdf {

	static String pathFileValue;
	static StringBuffer strbffr ;



	/** recuperaNumeroApolice
	 * Recebe por parametro caminho do arquivo PDF
	 * 
	 * Corta String e retorna Numero da apolice
	 * 
	 * @return
	 * @author T_dvlopes
	 */
	public static String recuperaNumeroApolice(String pathFileValue){
		Thread.sleep(5000);
		String pdfComplet =  pdfReader(pathFileValue);
		String apolice;
		apolice = pdfComplet.substring(315, 327);
		return apolice;
	}


	/** Recebe caminho do arquivo por parametro, faz a leitura do pdf e retorna um String
	 * @param pathFileValue
	 * @return
	 * @author T_dvlopes
	 */
	public static StringBuffer pdfReader(String pathFileValue){
		strbffr = new StringBuffer();
		String text;
		int noPages = 1;
		try{
			for(int i=0; i<noPages; i++){
				PdfReader pdfReaderFiles= new PdfReader(pathFileValue);
				int j = i+1
				text = PdfTextExtractor.getTextFromPage(pdfReaderFiles, j)
				strbffr.append(text).toString();
				println strbffr;
				pdfReaderFiles.close();
				pdfReaderFiles.finalize();
			}
			return strbffr
		} catch (Exception e){
			System.err.println("pdfReader - Falha ao ler arquivo ["+e+"]")
		}
	}
	
	
	public static StringBuffer pdfReaderNPages(String pathFileValue ){
		strbffr = new StringBuffer();
		String text;
		int noPages = qtdPaginas(pathFileValue)
		try{
			for(int i=0; i<noPages; i++){
				PdfReader pdfReaderFiles= new PdfReader(pathFileValue);
				int j = i+1
				text = PdfTextExtractor.getTextFromPage(pdfReaderFiles, j)
				strbffr.append(text).toString();
				//println strbffr;
				pdfReaderFiles.close();
				pdfReaderFiles.finalize();
			}
			return strbffr
		} catch (Exception e){
			System.err.println("pdfReader - Falha ao ler arquivo ["+e+"]")
		}
	}
	
	
	public static List<String> pdfReaderNPagesList(String pathFileValue ){
		List<String> linhas = new ArrayList<String>();
		String text;
		int noPages = qtdPaginas(pathFileValue)
		try{
			for(int i=0; i<noPages; i++){
				PdfReader pdfReaderFiles= new PdfReader(pathFileValue);
				int j = i+1
				text = PdfTextExtractor.getTextFromPage(pdfReaderFiles, j)
				System.out.println(text.length())
				String[] linha = text.split("\n");
				linhas.addAll(linha);
				pdfReaderFiles.close();
				pdfReaderFiles.finalize();
			}
			for(int p = 0; p<linhas.size();p++){
			 	//System.out.println("["+p+"] ["+linhas.get(p).toString()+"]")
			}
			return linhas;
		} catch (Exception e){
			System.err.println("pdfReader - Falha ao ler arquivo ["+e+"]")
		}
	
	}



	/**renamefile
	 *
	 * localiza e renomeia arquivo recebendo destino e novo nome por parametro
	 * @param pathFileValue
	 * @param namePdf
	 * @return
	 */
	public static renamefileGenerico(String pathFileValue, String namePdf){
		File f = new File(pathFileValue);
		//String nameReplace = namePdf.replace("/", "-").replace(".", "-");
		if (f.renameTo(new File(namePdf)))
		{
			System.out.println("Arquivo renomeado");
		}
		else{
			System.out.println("Arquivo renomeado");
		}

	}



	/**renamefile
	 * 
	 * localiza e renomeia arquivo recebendo destino e novo nome por parametro
	 * @param pathFileValue
	 * @param namePdf
	 * @return
	 */
	public static renamefile(String pathFileValue, String namePdf){
		File f = new File(pathFileValue+"\\fichero.PDF");
		String nameReplace = namePdf.replace("/", "-").replace(".", "-");
		if (f.renameTo(new File(pathFileValue+"\\"+nameReplace+".PDF")))
		{
			System.out.println("Arquivo renomeado");
		}
		else{
			System.out.println("Arquivo renomeado");
		}

	}


	public static renamefileExcel(String pathFileValue, String namePdf){
		File f = new File(pathFileValue+"\\DataX.xlms");
		String nameReplace = namePdf.replace("/", "-").replace(".", "-");
		if (f.renameTo(new File(pathFileValue+"\\"+nameReplace+".XLMS")))
		{
			System.out.println("Arquivo renomeado");
		}
		else{
			System.out.println("Arquivo renomeado");
		}

	}


	public static int qtdPaginas(String pathFileValue){
		int pages;
		try{
			PdfReader pdfReaderFilesT = new PdfReader(pathFileValue);
			pages = pdfReaderFilesT.getNumberOfPages();
			pdfReaderFilesT.finalize();
			pdfReaderFilesT.close();
			System.err.println('numbers of pages ['+pages+'] ');
		} catch (Exception e){
			System.err.println("pdfReader - Falha ao ler arquivo ["+e+"]")
		}
		return pages;
	}


	public static  List<File> identificaArquivo(String path, String ext){
		 List<File> arquivosPdf = encontrarArquivosPdf(path, ext);
		 if (arquivosPdf.isEmpty()) {
			 System.out.println("Nenhum arquivo PDF encontrado no diretório.");
		 } else {
	   	    System.out.println("Arquivos PDF encontrados:");
		     for (File arquivo : arquivosPdf) {
				 System.out.println(arquivo.getName());
		     }
		 }
		 System.out.println("SIZE "+arquivosPdf.size())
		 return arquivosPdf;
	}
	
	public static List<File> encontrarArquivosPdf(String diretorio, String ext) {
		List<File> pdfFiles = new ArrayList<>();
		File folder = new File(diretorio);
		File[] arquivos = folder.listFiles();

		if (arquivos != null) {
			for (File arquivo : arquivos) {
				if (arquivo.isFile() && arquivo.getName().toLowerCase().endsWith(ext)) {
					pdfFiles.add(arquivo);
				}
			}
		}
		return pdfFiles;
	}
	
	
	public static String pegaNomeDoArquivo(String nome){
		String arquivoNome;
		String path = "C:\\Users\\Public\\";
		String ext = ".pdf"
		List<File> arquivosPdf =  identificaArquivo(path, ext)
		System.out.println("Quantidade de arquivos PDF encontrados por nome: "+arquivosPdf.size());
		for(int i = 0; arquivosPdf.size(); i++){
			if(arquivosPdf[i].toString().contains(nome)){
			  arquivoNome = arquivosPdf[i].toString()
			  break
			}
		}
		return arquivoNome;
	}
	
	public static boolean existeArquivo(String nome){
		boolean b=false;
		String arquivoNome;
		String path = "C:\\Users\\Public\\";
		String ext = ".pdf"
		List<File> arquivosPdf =  identificaArquivo(path, ext)
		System.out.println("Quantidade de arquivos PDF encontrados por nome: "+arquivosPdf.size());
		for(int i = 0; arquivosPdf.size(); i++){
			if(arquivosPdf[i].toString().contains(nome)){
			  arquivoNome = arquivosPdf[i].toString()
			  b=true;
			  break
			} else if(arquivosPdf[i].toString().contains("null")){
			break;
			}
		}
		return arquivoNome;
	}

}
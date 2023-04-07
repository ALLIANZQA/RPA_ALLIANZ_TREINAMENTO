package auxiliares

import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

///////////////////////////////////////////////////////////////////////////
import java.io.FileOutputStream;
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



}
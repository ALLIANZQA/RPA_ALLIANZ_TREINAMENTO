package auxiliares;

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint;
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase;
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData;
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject;

import bean.CasoDeTeste;

import bean.MassaDados
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
import java.text.SimpleDateFormat
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;




public class WriteExcelFactory extends FileInput{


	/**
	 * @author T_DCDias
	 * @param caminho_arquivo_xlsx
	 * @return
	 * Retorna qualquer tipo de lista
	 */
	public static List<MassaDados> readExcelFileToObjectMassaDados(String caminho_arquivo_xlsx, String aba, int lineCheck){

		List<MassaDados> listCasoDeTeste = new ArrayList<MassaDados>();
		String filePath = caminho_arquivo_xlsx;

		try{
			FileInputStream file = new FileInputStream(new File(filePath));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(aba);
			Iterator<?> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = (Row) rowIterator.next();
				// Descartando a primeira linha com o header
				if (row.getRowNum() == 0) {
					continue;
				}
				if (row.getCell(lineCheck).getStringCellValue().contentEquals(RegrasLeitura.regrasLeituraExcel(row.getCell(lineCheck).getStringCellValue()))){
					continue;
				}
				if (row.getCell(lineCheck).getStringCellValue().contentEquals(RegrasLeitura.regrasLeituraExcelStop(row.getCell(lineCheck).getStringCellValue()))){
					//if(XLSXSelection.verificaStop(row, lineCheck, )){
					break;
				}


				Iterator<?> cellIterator = row.cellIterator();
				MassaDados massa = new MassaDados()

				while (cellIterator.hasNext()) {
					Cell cell = (Cell) cellIterator.next();
					cell.setCellType(cell.CELL_TYPE_STRING);
					massa = XLSXSelection.switchCellRules(cell,massa,aba);
				}
				listCasoDeTeste.add(massa);
			}
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return listCasoDeTeste;
	}

	/**
	 * @author T_DCDias
	 * @param caminho_arquivo_xlsx
	 * @param ct
	 * @throws IOException
	 * Salvar evidência no arquivo xlsx
	 */
	public static void updateSheetLine(String apolice, int rowNum, String caminho_arquivo_xlsx, String msg, int numCell, String aba) throws IOException{
		println("\nUpdate Cell Apolice - ["+apolice+"]  - Status - ["+msg+"] - Linha - ["+rowNum+"] Cell ["+numCell+"] ABA ["+aba+"]");
		Iterator<?> rowItr = null;
		XSSFSGetrow xssfsgetrow = null;
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(fileInput(caminho_arquivo_xlsx));
			XSSFSheet sheet = workbook.getSheet(aba);
			rowItr = sheet.rowIterator();
			xssfsgetrow = new XSSFSGetrow();
			xssfsgetrow.setrowLine(rowItr,msg,numCell,rowNum);
			FileOutputStream outFile = new FileOutputStream(new File(caminho_arquivo_xlsx));
			workbook.write(outFile);
			workbook.finalize();
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	
	
	

	/**
	 * @author T_DCDias
	 * @param caminho_arquivo_xlsx
	 * @return
	 * Retorna qualquer tipo de lista
	 */
	public static List<MassaDados> readExcelAllFileToObjectMassaDados(String caminho_arquivo_xlsx, String aba, int lineCheck){

		List<MassaDados> listCasoDeTeste = new ArrayList<MassaDados>();
		String filePath = caminho_arquivo_xlsx;

		try{
			FileInputStream file = new FileInputStream(new File(filePath));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(aba);
			Iterator<?> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = (Row) rowIterator.next();
				// Descartando a primeira linha com o header
				if (row.getRowNum() == 0) {
					continue;
				}
				//	if (row.getCell(lineCheck).getStringCellValue().contentEquals(RegrasLeitura.regrasLeituraExcel(row.getCell(lineCheck).getStringCellValue()))){
				//		continue;
				//	}
				//	if (row.getCell(lineCheck).getStringCellValue().contentEquals(RegrasLeitura.regrasLeituraExcelStop(row.getCell(lineCheck).getStringCellValue()))){
				//if(XLSXSelection.verificaStop(row, lineCheck, )){
				//		break;
				//	}


				Iterator<?> cellIterator = row.cellIterator();
				MassaDados massa = new MassaDados()

				while (cellIterator.hasNext()) {
					Cell cell = (Cell) cellIterator.next();
					cell.setCellType(cell.CELL_TYPE_STRING);
					massa = XLSXSelection.switchCellRules(cell,massa,aba);
				}
				listCasoDeTeste.add(massa);
			}
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return listCasoDeTeste;
	}


}

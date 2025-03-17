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

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class XSSFSGetrow {

	/**
	 * @author T_DCDias
	 * @param rowIterator
	 * @param ct
	 * @return linha do CT 
	 */
	public static int getrow( Iterator<?> rowIterator , String ct){

		int rownum = 0;
		Row row=null;
		while ( rowIterator.hasNext()) {
			row = (Row) rowIterator.next();
			if (row.getRowNum() == 0) {
				continue;
			}
			Iterator<?> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = (Cell) cellIterator.next();
				if( cell.getStringCellValue().contentEquals(ct)) {
					System.err.println("-- Found CT ["+cell.getStringCellValue()+"] at row ["+row.getRowNum()+"] --");
					rownum = row.getRowNum();
				}
			}
		}
		return rownum;
	}

	/**
	 * @author T_DCDias
	 * @param rowIterator
	 * @param ct
	 * @param msg
	 * @param celnum
	 * Altera valor calular do CT selecionado 
	 */
	public static void setrow( Iterator<?> rowIterator , String ct, String msg, int celnum){

		try{
			int rownum = 0;
			Row row=null;
			while ( rowIterator.hasNext()) {
				row = (Row) rowIterator.next();
				if (row.getRowNum() == 0) {
					continue;
				}
				Iterator<?> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = (Cell) cellIterator.next();
					if( cell.getStringCellValue().contentEquals(ct)) {
						cell = row.getCell(celnum);
						cell.setCellValue(msg);
					}
				}
			}
		} catch (Exception e){
			System.err.println("-- Class XSSFSGetrow - Não foi possível salvar a evidência! ["+e+"] --");
		}
	}

	/**
	 * @author T_DCDias
	 * @param rowIterator
	 * @param ct
	 * @param msg
	 * @param celnum
	 * Altera valor do CT selecionado
	 */
	public static void setrowLine( Iterator<?> rowIterator , String msg, int celnum, int rowNum){
		println("["+msg+"] ["+ celnum+"] ["+rowNum+"]")
		try{
			int rownum = 0;
			Row row=null;
			while ( rowIterator.hasNext()) {
				row = (Row) rowIterator.next();
				if (row.getRowNum() == 0) {
					continue;
				}
				Iterator<?> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = (Cell) cellIterator.next();
					if( row.getRowNum() == rowNum+1) {
						cell = row.getCell(celnum);
						try{
						  cell.setCellValue(msg.trim());
						} catch (Exception e){
						  row.createCell(celnum).setCellValue(msg.trim());
						}
					}
				}
			}
		} catch (Exception e){
			System.err.println("-- Class XSSFSGetrow - Não foi possível salvar a evidência! ["+e+"] --");
		}
	}
}

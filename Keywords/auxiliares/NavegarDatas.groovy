package auxiliares;

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint;
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase;
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData;
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject;
import com.kms.katalon.core.webui.driver.DriverFactory;
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
import com.kms.katalon.core.testobject.ConditionType;

import internal.GlobalVariable;

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint;
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase;
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData;
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject;

import io.cucumber.datatable.DataTable;
import java.time.LocalDateTime;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

public class NavegarDatas {


	/**
	 * @author T_DCDias
	 * Form 1. Cotação Auto
	 * Informa a data atual
	 * @return
	 */
	public static dataAtual(String ultimoacesso, String chave){
		WebUI.delay(5);
		TestObject flexibleTestObject = new TestObject();
		def xpath = "//div[contains(@class,'alz-wm-bs-input-group date alz-wm-inputdate-iconleft ') and contains(@id,'dataInicioVigenciaSeguro_div')]//span[contains(@class,'input-group-addon')]//i[contains(@class,'allianz-icon allianz-icon-calendar')] ";
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, xpath));
		selecionaData(ultimoacesso,  chave);
	}


	/**
	 * @author T_DCDias
	 * Informa a data atual
	 * @return
	 */
	public static selecionaData(String ultimoacesso,String chave){
		Calendar calendar = new GregorianCalendar();
		int dia = calendar.get(calendar.DAY_OF_MONTH);
		int mes = calendar.get(calendar.MONTH);
		int ano = calendar.get(calendar.YEAR);
		System.err.println("-- Dia ["+dia+"/"+mes+"/"+ano+"] ["+ultimoacesso+"] Cave ["+chave+"]--");
		if (ExtractDates.extractHours(ultimoacesso) == Integer.parseInt(chave) ){
			navegar(dia+1);
		}
		else if(ExtractDates.extractHours(ultimoacesso) > Integer.parseInt(chave) ){
			navegar(dia+1);
		}
		else {
			navegar(dia);
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static dataSuperior(String ultimoacesso){
		WebUI.delay(5);
		TestObject flexibleTestObject = new TestObject();
		def xpath = "//div[contains(@class,'alz-wm-bs-input-group date alz-wm-inputdate-iconleft ') and contains(@id,'dataInicioVigenciaSeguro_div')]//span[contains(@class,'input-group-addon')]//i[contains(@class,'allianz-icon allianz-icon-calendar')] ";
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, xpath));
		Calendar calendar = new GregorianCalendar();
		int dia = calendar.get(calendar.DAY_OF_MONTH);
		int mes = calendar.get(calendar.MONTH);
		int ano = calendar.get(calendar.YEAR);
		println("Dia ["+dia+"/"+mes+"/"+ano+"] Ultimo acesso ["+ultimoacesso+"]");
		def novodia = dia+1;
		navegar(novodia);
	}


	/**
	 * @author T_DCDias
	 * Método informa data atual para pesquisar data pretérita 
	 * @return
	 */
	public static dataInferior(String ultimoacesso){
		WebUI.delay(5);
		TestObject flexibleTestObject = new TestObject();
		def xpath = "//div[contains(@class,'alz-wm-bs-input-group date alz-wm-inputdate-iconleft ') and contains(@id,'dataInicioVigenciaSeguro_div')]//span[contains(@class,'input-group-addon')]//i[contains(@class,'allianz-icon allianz-icon-calendar')] ";
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, xpath));
		Calendar calendar = new GregorianCalendar();
		int dia = calendar.get(calendar.DAY_OF_MONTH);
		navegarDataInferior(dia);
	}

	/**
	 * @author T_DCDias
	 * @param dia
	 * @return
	 */
	public static navegar(int dia){
		String sDate = Integer.toString(dia);
		WebDriver driverbt = DriverFactory.getWebDriver();
		WebElement elementbt = driverbt.findElement(By.xpath("//div[contains(@id,'dataInicioVigenciaSeguro_div')]//span[contains(@class,'input-group-addon')]//i[contains(@class,'allianz-icon allianz-icon-calendar')]"));
		elementbt.click();
		WebUI.delay(2);
		WebDriver driverdt = DriverFactory.getWebDriver();
		WebElement elementdt = driverdt.findElement(By.xpath("//tbody//tr//td[text()='"+sDate+"' and contains(@class,'today active')]"));
		elementdt.click();
	}

	/**
	 * @author T_DCDias
	 * @param dia
	 * Método navega para data pretéria 
	 * @return
	 */
	public static navegarDataInferior(int dia){
		int nDia = dia - 1;
		String sDate = Integer.toString(nDia);
		TestObject flexibleTestObject = new TestObject();
		WebUI.click(flexibleTestObject.addProperty("xpath", ConditionType.EQUALS, "//tbody//tr//td[contains(text(),'"+sDate+"') and contains(@class,'disabled')]"));
	}

//	/**
//	 * @author T_DCDias
//	 * @param dia
//	 * @return
//	 */
//	public static boolean validaDataBloqueada(int dia){
//		int nDia = dia - 1;
//		String sDate = Integer.toString(nDia);
//		String xpath = "//tbody//tr//td[contains(text(),'"+sDate+"') and contains(@class,'disabled')]";
//		return commands.AutoComandos.validaElemento(xpath);
//	}
}

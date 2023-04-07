package auxiliares

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
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.JavascriptExecutor


public class TakesScreenshots {

	/**
	 * @author T_DCDias
	 * @param ct
	 * @param path
	 * @return
	 */
	public static takeScreen(String path, String ScreenshotName){
		try{
			String destinationPath = null
			WebDriver driver = DriverFactory.getWebDriver()
			File destFolder = new File(path);
			destinationPath = destFolder.getCanonicalPath() + "//" + ScreenshotName + ".png";
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(sourceFile, new File(destinationPath));
		} catch (Exception e){}
	}

	/**
	 * @author T_DCDias
	 * @param valor
	 * @return
	 */
	public static porcentagemDeTela(int valor){
		try{
			WebDriver driver = DriverFactory.getWebDriver()
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("document.body.style.zoom = '"+valor+"%'");
		}  catch (Exception e){
			e
		}
	}
}

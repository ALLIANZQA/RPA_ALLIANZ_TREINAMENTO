import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable




import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



import java.io.File;





renamePathFileValue = "C:\\Users\\Public\\fichero.PDF"
novoNomePDF ="137982316/00002";
int p = 1
renomearArquivo(renamePathFileValue, novoNomePDF+"-"+String.valueOf(p) ,".pdf")

public static renomearArquivo(String pathFileValue, String name, String extencao){
	System.err.println('pathFileValue: '+pathFileValue)
	File f = new File(pathFileValue);
	String nameReplace = name.replace("/", "-").replace(".", "-");
	if (f.renameTo(new File("C:\\Users\\Public\\"+nameReplace+extencao)))
	{
		System.out.println("Arquivo renomeado");
	}
	else{
		System.out.println("Arquivo não renomeado");
	}
}




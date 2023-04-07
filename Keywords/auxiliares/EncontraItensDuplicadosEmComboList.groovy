//package auxiliares
//
//import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
//import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
//import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
//import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
//
//import java.awt.List
//
//import org.openqa.selenium.WebElement
//
//import com.kms.katalon.core.annotation.Keyword
//import com.kms.katalon.core.checkpoint.Checkpoint
//import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
//import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
//import com.kms.katalon.core.model.FailureHandling
//import com.kms.katalon.core.testcase.TestCase
//import com.kms.katalon.core.testdata.TestData
//import com.kms.katalon.core.testobject.TestObject
//import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
//import org.openqa.selenium.WebElement
//import internal.GlobalVariable
//
//public class EncontraItensDuplicadosEmComboList {
//
//
////	public static Integer verificaDuplicidades(List<WebElement> listOfElements){
////
////		//List<WebElement> listOfElements = listOfElements
////		final Map<String, Integer> valuefrequency = new HashMap<>();
////		def resultado = 0
////		def supplierNames = []
////		def qtd = []
////		for(int i = 0; i < listOfElements.size(); i++){
////			if(!listOfElements.get(i).getText().equals("")){
////				supplierNames.add(listOfElements.get(i).getText())
////			}
////		}
////
////		for(int i = 0; i < supplierNames.size(); i++){
////			def frequency = Collections.frequency(supplierNames, supplierNames[i])
////			valuefrequency.put( supplierNames[i] , frequency)
////			qtd.add(frequency)
////		}
////		println("Itens "+valuefrequency)
////		String valores = qtd;
////		println("Frequency" + valores)
////
////		String[] lstr = valores.split("")
////		for(int i=0; i < lstr.size(); i++ )
////		{
////			if(!lstr[i].equals("[") && lstr[i].equals(",") && lstr[i].equals("]"))
////			{
////				if(!lstr[i].equals("1")){
////					resultado = 1
////				}
////			}
////
////		}
////		return resultado
////	}
//}

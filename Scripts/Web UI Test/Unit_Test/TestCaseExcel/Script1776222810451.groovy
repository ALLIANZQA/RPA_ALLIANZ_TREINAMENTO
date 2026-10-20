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





int j = 0
String path = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\ProatividadePerceriasData\\Parcerias.xlsx"
String status = "ProcessadoTeste";
String aba="Parcerias";

auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 4, aba);
auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 5, aba);
auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 6, aba);
auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 7, aba);
auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 8, aba);
auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 15, aba);
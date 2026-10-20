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



import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.text.SimpleDateFormat;
import java.time.LocalDate
import java.time.format.DateTimeFormatter;




System.out.println("Dif Dias "+difDiasDatas("20260226","20260221"))



public long difDiasDatas(String dt1, String dt2){
	
	int diaUm;
	int mesUm;
	int anoUm;
	
	int diaDois;
	int mesDois;
	int anoDois;
	
	DateTimeFormatter formatador = DateTimeFormatter.ofPattern(dt1);
	LocalDate data = LocalDate.parse(dt1, formatador);
	diaUm = data.getDayOfMonth();
	mesUm = data.getMonthValue();
	anoUm = data.getYear();
	
	System.out.println(diaUm+" - "+mesUm+" - "+anoUm);
	
	DateTimeFormatter formatador = DateTimeFormatter.ofPattern(dt2);
	LocalDate data = LocalDate.parse(dt2, formatador);
	diaDois = data.getDayOfMonth();
	mesDois = data.getMonthValue();
	anoDois = data.getYear();
	
	System.out.println(diaDois+" - "+mesDois+" - "+anoDois);
	
    LocalDate dataInicial = LocalDate.of(anoUm,mesUm,diaUm);
    LocalDate dataFinal = LocalDate.of(anoDois,mesDois,diaDois);
    long difDias = ChronoUnit.DAYS.between(dataInicial, dataFinal);
    System.out.println(difDias);
    return difDias;
}





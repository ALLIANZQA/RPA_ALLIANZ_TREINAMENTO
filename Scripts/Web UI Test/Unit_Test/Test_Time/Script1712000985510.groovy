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



import java.text.DecimalFormat;
import java.time.Duration
import java.time.Instant

import auxiliares.timer;


 Instant start;
 Instant end;
 Duration timeLapse;


start = Instant.now();
WebUI.delay(0);
end =  Instant.now();
timeLapse = Duration.between(start, end);
System.out.println("Time Lapnse:\n"+
	                timeLapse.toHours()+"h "+
					timeLapse.toMinutes()+"m "+
					timeLapse.toSeconds()+"s ");
				


long h = 3;
long ms = 191;
long sgs = 11516.423000000

System.out.println("Elapsed time: "+h+"h "+ timer.converteMinutos(h, ms)+"m "+converteSegundos(ms, sgs)+"s");

public static long converteMinutos(long h, long ms){
    long v;	
	if(h>0){
		long m = h*60;
		long r = ms-m;
		v = r;
	} else {
	v = ms;
	}
	return v;
}
	

public static long converteSegundos(long min, long segs){
	long v;
	if(min>0){
		long m = min*60;
		long r = segs-m;
		v = r;
	} else {
	v = segs;
	}
	return v;
}


	
	
	
	
	

				
//System.out.println("SECONDS: "+timeLapse.ofSeconds(30, 123456789))


//System.out.println("Java Version: "+System.getProperty("java.version"))


//long tempoInicial = System.currentTimeMillis();


//Date startTime = startTime();
//WebUI.delay(5);


//long totalTime = getTotalTime(startTime)


//System.out.println("O método foi executado em " + totalTime/1000/60/60+" ");


//public Date startTime(){
//	Date now = new Date();
//	return now.getTime();
//}


//public long getTotalTime(Date start)
//{
//	Date now = new Date();
//	long millis = now.getTime() - start.getTime();
//	return millis;
//}


//System.out.println("O método foi executado em " + System.currentTimeMillis() - tempoInicial +" ilesgundos");

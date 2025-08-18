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



import auxiliares.StringFormatter
import auxiliares.CurrencyMonetary
import auxiliares.ExtractDates;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

String doc = "41.063.201/0001-15"


List<String> registros = new ArrayList<>();
registros.add("Relatório de Recusas Endosso 11.08.2025 a 17.08.2025");
registros.add("Relatório de Recusas Endosso 11.08.2025 a 17.08.2025");

System.out.println("formata texto : "+formataTituloIndexFG(registros))



public static String formataTituloIndexFG(List<String> registros){
	String index;
	Map<Integer,String> hasdates = new HashMap<Integer, String>();
	StringBuilder strb = new StringBuilder()
	StringBuilder strdts = new StringBuilder()
	
	for(int i=0 ; i < registros.size();i++){
		strdts.append(registros[i])
	}
	
	hasdates.putAll(ExtractDates.getSubDates(strdts.toString()))
	
	HashSet<String> strDT = new HashSet<String>();
	HashSet<String> strIDX = new HashSet<String>();
	
	for(int j = 0; j <hasdates.size(); j++){
		strIDX.add(StringFormatter.removeNumerosDeStrings(registros[j]))
		strDT.add(" - "+hasdates.get(j))
	}
	index = strIDX[0]
	
	for(int k=0; k<strDT.size();k++){
		strb.append(strDT[k])
	}
	System.out.println("formataTituloIndexFG ["+index+strb.toString()+"]")
	return index+strb.toString()
}



public static String ajusteRefCobCascoVD(String v){
	
	StringBuilder strb = new StringBuilder();
	String s;
	String[] l = v.split("")
	System.out.println("INVERT LIST\n")
	for(int i = l.size(); i--;  i<0   ){
		System.out.println("["+i+"] ["+l[i]+"]")
		
		if(i==5){
			strb.append(",")
		}
		strb.append(l[i])
	}
	String inverOrder = strb.toString()
	strb = new StringBuilder();
	l =inverOrder.split("")
	System.out.println("\nREORG LIST\n")
	for(int i = l.size(); i--;  i<0   ){
		System.out.println("["+i+"] ["+l[i]+"]")
		strb.append(l[i])
		
	}
	
	System.out.println("\n")
	System.out.println(strb.toString())
	System.out.println("\n")
	
	
	return s;
}





public static String ajusteDDD(String s){
	String d
	if(s.equals("--")){
		d = "11"
	} else {
	  d=s
	}
	return d
}	
	
	



public static String sequenceString(String txt, String path){
String[] p = txt.split(" ")
StringBuilder stb = new StringBuilder()
for(int i; i < p.size();i++){
	System.out.println("["+i+"] ["+p[i]+"]");
	stb.append(p[i])
	stb.append(" ")
	if(p[i].toString().equals(path)){
		break;
	}
}
System.out.println("["+stb.toString().trim()+"]")
return stb.toString().trim()
}









//25% da Normal
//50% da Normal
//150% da Normal
//200% da Normal
//Normal


public static int indexGroupLine(List<String> registros, String... path){
		int line;
		String[] p = path;
		
		for(int i=0; i<registros.size();i++){
			System.out.println("LINE ["+i+"] ["+registros[i]+"] ")
			if(registros[i].toString().contains(path)){
				System.out.println("FOUND ["+i+"] ["+registros[i]+"] ["+path+"]")
				line = i;
				break;
			}
		}
		return line;
	}




String a = CurrencyMonetary.currencyMonetaryValue("150")
System.out.println(a)
String s = a.subSequence(0, a.indexOf(","));
String r = s.replaceAll('[R\\$]',"")	
System.out.println(r)

public static String removeZeroDoInicioDeNumeroString(String sn){
	String n
	int i = Integer.parseInt(sn);
	n = String.valueOf(i);
	return n;
}

public static String stopString(String txt, String stop){
String [] stringList = txt.split(" ");
StringBuilder sb = new StringBuilder();
for(int i = 0; i< stringList.size(); i++){
	System.out.println("["+stringList[i]+"]")
	if(!stringList[i].toString().contains(stop)){
		sb.append(stringList[i]+" ");
	}
	if(stringList[i].toString().contains(stop)){
		break;
	} else {
	}
}
System.out.println("["+sb.toString().trim()+"]")
return sb.toString();
}



public static String pointStop(String txt, String stop){
	int inicio;
	StringBuilder sb = new StringBuilder();
	String [] stringList = txt.split(" ");
	for(int i = 0; i< stringList.size(); i++){
		if(stringList[i].contains(stop)){
			inicio = i;
		}
	}

	for(int i = 0; i< stringList.size(); i++){
		if(i > inicio){
			int next = i-1;
			sb.append(stringList[next]);
			break;
		}
	}
	return sb.toString().trim();
}




public static String carroReserva(String linha){
	String r;
	String[] reserva = new String[4];
	reserva[0] = "Não Contratado";
	reserva [1]="07 dias";
	reserva [2]="15 dias";
	reserva [3]="30 dias";
	for(int i = 0; i<reserva.length;i++){
		if(linha.contains(reserva[i])){
			r = reserva[i];
			break;
		}
	}
	
	String t = formataCarroReserva(r)
	if(!t.equals(null)){
		r = t;
	}
		
	return r;
}

public static String formataCarroReserva(String v){
	Map<String, String> combo = new HashMap<String, String>()
		   combo.put('Não Contratado', 'Não contratado');
	return combo.get(v.toString());
}

/**
 * @author be54955
 * @param b
 * @return
 */
public static String removeStrings(String b){
	String result
	List<String> registros = new ArrayList<>();
	StringBuilder strb = new StringBuilder()
	String[]t = b.split(" ")
	int j = 0
	for(int i = 0; i < t.size(); i++){
		System.out.println("["+i+"] ["+t[i]+"]")
		if(regexChar("[0-1-2-3-4-5-6-7-8-9]", t[i] )){
			registros[j] = t[i].trim()
			j++
		}
	}

	for(int i = 0; i < registros.size(); i++){
		strb.append(registros[i]+" ")
	}

	result = strb.toString().trim()

	System.out.println("result ["+result+"]")

	return result
}



public static boolean regexChar(String REGEX, String INPUT){
   boolean find = false
   // create a pattern
   Pattern  pattern = Pattern.compile(REGEX);
   // get a matcher object
   Matcher matcher = pattern.matcher(INPUT);
   while(matcher.find()) {
	   //Prints the start index of the match.
	   System.out.println("Match String start(): ["+matcher.start()+"]");
	   find = true
   }
   return find
}
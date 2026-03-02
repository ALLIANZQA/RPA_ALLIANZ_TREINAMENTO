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
import auxiliares.ExtractDates;
import auxiliares.StringFormatter;
import rpacommands.rpaCommands
import auxiliares.leitorPdf;
import sendEmail.envioDeEmail;



String s = "01 Data Averiguação *	 Data deve ser igual ou posterior ao dia de hoje 02 Telefone 1 *	 Campo necessário"
campoObrigatorio(s)
public static boolean campoObrigatorio(String alerta){
	boolean b = false
//	alerta=recuperaValor('//*[@id="cellDataErrorSection"]', 'innerText', 0)
	System.out.println("Alerta ["+alerta+"]")
	if(alerta.contains("Campo necessário") ){
		b = true;
	}
	
	if(alerta.contains("Campo obrigatório") ){
		b = true;
	}
		System.out.println("Alerta ["+b+"]")
 return b;
}

// || alerta.contains("Campo obrigatório")
envioDeEmail.send()


String arq1 = leitorPdf.pegaNomeDoArquivo("ticket")
System.out.println("Arquivo tamanho ["+arq1.length()+"]")

String arq2 = leitorPdf.pegaNomeDoArquivo("PDF da cotação")
System.out.println("Arquivo tamanho ["+arq2.length()+"]")


//rpaCommands.validaExistenciaDeArquivo(leitorPdf.pegaNomeDoArquivo("PDF da cotação (1)"))

System.out.println("["+validaArquivos("ticket" , "PDF da cotação")+"]")

public static boolean validaArquivos(String arquivoA, String arquivoB){
	String arq1 = leitorPdf.pegaNomeDoArquivo(arquivoA)
	String arq2 = leitorPdf.pegaNomeDoArquivo(arquivoB)
	boolean b=false;
	if(arq1.length()>0&& arq2.length()>0){b=true}
	return b
}








String txt = "Tipo de Seguro: Renovação de outra seguradora sem sinistro Produto: Automoveis 1211";
String start = "Tipo de Seguro:";
String end = "Produto:"

String txt2 = "Nome: FABIO GOES LEVANTOVISK MANTINS SOUZA DIAS CPF/CNPJ: 284.842.348-00";
String start2 = "Nome: ";
String end2 = "CPF/CNPJ:"

String txt3 = "Vigência: das 24H de 25/03/2025 às 24H de 25/03/2026 Nº da Apólice: ";
String start3 = "Vigência: das 24H de ";
String end3 = "às"

String txt4 = "Categoria de Risco: Automóvel - Particular";
String start4 = "Categoria de Risco:";
String end4 = ""

String txt5 = "Placa: FPE3816 Classe Bônus: 10";
String start5 = "Placa:";
String end5 = "Classe"

String txt6 = "Não Contratado - 100% FIPE 1.192,59 100% FIPE 1.192,59";
String start6 = "Não Contratado";
String end6 = "FIPE"


String txt7 = "Franquia Reduzida 7.721,01";
String stop7 = "Franquia";
int index7 = 0

String txt8 = "Não Contratado - 100% FIPE 1.192,59 100% FIPE 1.192,59";
String stop8 = "-";
int index8 = 0

String txt9 = "Deseja ampliar a cobertura do seguro para condutores do veículo segurado com idade entre 18 a 25 anos: Não. Estou";
String stop9 = "anos:";
int index9 = 0

String txt10 = "Assistência 24 hs Plano  3 249,04 Plano  3 249,04 Plano  3 249,04";
String stop10 = "hs";
int index10 = 0

String txt11 = "Plano 3 427,56 Plano 3 427,55 Plano 3 427,55";
String stop11 = "Plano";
int index11 = 0

String txt12 = "Carro Reserva Não Contratado - 15 Dias 33,72 15 Dias 33,72";
String stop12 = "Reserva";
int index12 = 0

String txt13 = "ASSISTÊNCIA A VIDROS, LANTERNAS, FARÓIS E RETROVISORES - PLANO 3";
String stop13 = "-";
int index13 = 0
//System.out.println("betweenStrings ["+StringFormatter.betweenStrings(txt6,start6,end6)+"]")
//Map<Integer,String> hasdates = new HashMap<Integer, String>();
//hasdates = ExtractDates.getSubDates(txt3.replaceAll("24H",""))
//System.out.println("DATAS ["+hasdates.size()+"] ["+hasdates.get(0)+"] ["+hasdates.get(1)+"]")
//for (Map.Entry<String,Integer> pair : hasdates.entrySet()) {
//	System.out.println(pair.getKey());
//	System.out.println(pair.getValue());
//}
//System.out.println("startWhitch : "+startWhitch(txt10, "Plano"))

System.out.println("Result : "+startWhitch(txt13, stop13) )



public static String startLength(String txt, String stop, int index, int len){
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
			for(int l = 0 ; l < len; l++){
			int next = l+i;
			 sb.append(stringList[l]+" ");
			 sb.append(stringList[next]+" ");
		   }
		   break;
		}
	}
	return sb.toString().trim();
}




public static String nextStopLength(String txt, String stop, int index, int len){
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
			for(int l = 0 ; l < len; l++){
			int next = l+i;
		     sb.append(stringList[next]+" ");
		   }
		   break;
		}
	}
	return sb.toString().trim();
}




public static String nextStop(String txt, String stop, int index){
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
			int next = i+index;
		   sb.append(stringList[next]);
		   break;
		}
	}
	return sb.toString().trim();
}




public static String startWhitch(String txt, String start){
	int inicio;
	StringBuilder sb = new StringBuilder();
	String [] stringList = txt.split(" ");
	for(int i = 0; i< stringList.size(); i++){
		if(stringList[i].contains(start)){
			inicio = i;
		}
	}
	
	for(int i = 0; i< stringList.size(); i++){
		if(i > inicio){
		   sb.append(stringList[i]+" ");
		}
	}
	return sb.toString().trim();
}





public static String betweenStrings(String txt, String start, String end){
	String s;
	String t;
	int stop;
  if(txt.contains(start)){
	s = txt.replace(start, "");
  }
  
  
  
  StringBuilder sb = new StringBuilder();
  String[] stringList = s.split(" ")
  
  if(end.length()>0){
  
  for(int j = 0; j < stringList.size(); j++){
	  if(stringList[j].contains(end)){
		  System.out.println(j+" "+stringList[j])
		  stop = j;
	  }
  }
  
  
  for(int i = 0; i < stop; i++){
	  System.out.println(i+" "+stringList[i])
		  sb.append(stringList[i]+" ");
  }
  
} else {
 sb.append(s)
}
  
   return sb.toString().trim();	
}





public static String betweenStringsIndex(String txt, String inicio, String fim){
	String s;
	String t;
	int start;
	int stop;
	
	StringBuilder sb = new StringBuilder();
	String[] stringList = txt.split(" ")
	
	for(int k = 0; k < stringList.size(); k++){
			System.out.println(k+" "+stringList[k])
		
	}
	
	
	for(int i = 0; i < stringList.size(); i++){
		if(stringList[i].contains(inicio)){
			System.out.println(i+" "+stringList[i])
			start = i;
		}
	}
	
	for(int j = 0; j < stringList.size(); j++){
		if(stringList[j].contains(fim)){
			System.out.println(j+" "+stringList[j])
			stop = j;
		}
	}
	
	
	
}







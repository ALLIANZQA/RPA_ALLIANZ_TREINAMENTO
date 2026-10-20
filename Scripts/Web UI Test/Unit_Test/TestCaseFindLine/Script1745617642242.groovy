

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


import auto.ReentradaPropostaAutoFuncionalidades;
import auxiliares.leitorPdf;
String filepath = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Auto\\ReentradaPropostaAuto\\Arquivos\\"
//String arquivo = "389216282 Cotação Fabio Goes"

//String filepath = "\\Ntxnas1.intrallianz.es\\brasil\\DIRETORIAS\\DIR EXE OPERACOES\\DIR TI\\SUPERINT DESENV PRODUTOS\\RPA\\Denilson\\"
String arquivo = "PDF da cotação-Modelo1 1 Sergio"


String texto1 = "[13] [Vigência: das 24H de 25/03/2025 às 24H de 25/03/2026 Nº da Apólice: ]"

ArrayList<ArrayList<String>> cotacoes = new ArrayList<ArrayList<String>>();
cotacoes.add(leitorPdf.pdfReaderNPagesList(filepath+arquivo+".pdf"));



pegaCotacao(cotacoes)

public static List<String> pegaCotacao(ArrayList<ArrayList<String>> cotacoes){
	List<String> linha = new ArrayList<String>()
	for(int i = 0; i<cotacoes.size();i++ ){
		int l = i+1;
		System.out.println("\nList: "+l+" **************************************************************************\n")
		for(int j = 0; j<cotacoes.get(i).size();j++){
			linha.add(cotacoes[i][j].toString());
		}
	}
	
	for(int i = 0;i< linha.size();i++){
		if(linha.get(i).contains("Nome:")){
		System.out.println("["+i+"] ["+linha.get(i)+"]")
		}
	}
	return linha;
}


List<String> linha = new ArrayList<String>()

linha = pegaCotacao(cotacoes)





buscaComAntecessor(linha, "SUAS INFORMAÇÕES", "Nome:")

buscaComAntecessor(linha, "INFORMAÇÕES DO SEU SEGURO", "Vigência:")

public static String buscaComAntecessor(List<String> linha, String antecessor, String path ){
	System.out.println("BUSCA COM ANTECESSOR \n")
	String texto;
	for(int i = 0;i< linha.size();i++){
		   if(linha.get(i).contains(antecessor)){
			  int next = i+1; 
			  if(linha.get(next).contains(path)){
		         System.out.println("["+next+"] ["+linha.get(next)+"]")
				 texto = linha.get(next);
			  break;
			  }
		  }
	}
	return texto;
}



//[0] [COTAÇÃO]
//[11] [SUAS INFORMAÇÕES]
//[24] [INFORMAÇÕES DO CONDUTOR PRINCIPAL]
//[31] [DETALHES DAS OFERTAS]





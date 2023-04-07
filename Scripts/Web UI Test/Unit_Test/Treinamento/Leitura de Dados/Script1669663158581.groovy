import rpacommands.rpaCommands
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import auxiliares.ExtractDates

import java.util.List
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import auxiliares.StringFormatter
import auxiliares.BruteForceSubstringSearch
import auxiliares.CurrencyMonetary

import java.text.SimpleDateFormat;
import java.time.LocalDate

//WebUI.openBrowser('')

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;




import auxiliares.switchLeitorExcel
import bean.MassaDados










import static com.kms.katalon.core.testdata.TestDataFactory.findTestData






String testDataPath = 'Data Files/Treinamento/TreinamentVictor2'

int cellCheck = 6
data = findTestData(testDataPath)
path = data.sourceUrl

System.out.println("\nTotal de itens na lista usando dataTeste ******["+data.getRowNumbers()+"]********\n");

for (int i = 1; i <= data.getRowNumbers(); i++) {
//	System.err.println("\nOLHA AQUI VICTOR ***** ["+data.getValue(cellCheck, i) +"]****")
}



List<MassaDados> listaExcel 
String path = 'C:\\Users\\Public\\TREINAMENTO\\TreinamentoRoboCotacaoEspecial.xlsx';
String tipoAba = "CotacoesEspeciais"
listaExcel = switchLeitorExcel.switchExcel(path, tipoAba);
////listaExcel.size();
//System.out.println(listaExcel.size());
//
//
System.out.println("\nTotal de itens na lista usando biblioteca apachePoi ******["+ listaExcel.size()+"]********\n");










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



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


int parcelasPagas = 9;
double valorAllianz = 226.49;
int parcala = 12; 
String vencimento = "11/04/2026";

System.out.println("Data Calculada ["+calculaData(parcelasPagas,valorAllianz,parcala,vencimento)+"]");


/**
 * @author ALZ_SUP54955
 * @param parcelasPagas
 * @param valorAllianz
 * @param parcala
 * @param vencimento
 * @return
 */
public static String calculaData(int parcelasPagas, double valorAllianz, int parcala, String vencimento  ){
	
	String dtVencimento;
	
	try {
		System.out.println("Data de entrada: ["+vencimento+"] NU PARC ["+parcelasPagas+"] VALOR ["+valorAllianz+"] PARCELA ["+parcala+"]");
		if(parcala>2){
			
			int res = (parcelasPagas*valorAllianz-valorAllianz)/valorAllianz;
			
			// 1. Define o formato correspondente ao texto da data
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			
			// Opcional: Garante que o Java rejeite datas inválidas como 32/10/2026
			formatador.setLenient(false);
			
			
			// 2. Converte a String para um objeto Date
			Date dataConvertida = formatador.parse(vencimento);
		
			// 3. Cria o Calendar e define o tempo com a Date obtida
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(dataConvertida);
		
			// Teste: Exibe o ano para confirmar que a conversão funcionou
			
			
			// 1. Extrair o dia do mês
			int dia = calendario.get(Calendar.DAY_OF_MONTH);
		
			// 2. Extrair o mês (LEMBRETE: Soma-se 1 porque Janeiro começa em 0)
			int mes = calendario.get(Calendar.MONTH) + 1;
		
			// 3. Extrair o ano
			int ano = calendario.get(Calendar.YEAR);
			
			//System.out.println("Data: ["+ dia+"/"+mes+"/"+ano+"]");
			
			LocalDate dataInicial;
			
			System.out.println("Resultado do calculo interno: ["+res+"]");
			// 2. Soma o número de meses desejado
			//int mesesParaSomar = 5;
		
			
			calendario.add(Calendar.MONTH, res);
			// 1. Extrair o dia do mês
			dia = calendario.get(Calendar.DAY_OF_MONTH);
		
			// 2. Extrair o mês (LEMBRETE: Soma-se 1 porque Janeiro começa em 0)
			mes = calendario.get(Calendar.MONTH) + 1;
		
			// 3. Extrair o ano
			ano = calendario.get(Calendar.YEAR);
			
			System.out.println("Data calculada: ["+dia+"/"+mes+"/"+ano+"]");
			
			// 2. Reduz 2 dias passando o valor negativo
			calendario.add(Calendar.DAY_OF_MONTH, -2);
			
			// 1. Extrair o dia do mês
			dia = calendario.get(Calendar.DAY_OF_MONTH);
		
			// 2. Extrair o mês (LEMBRETE: Soma-se 1 porque Janeiro começa em 0)
			mes = calendario.get(Calendar.MONTH) + 1;
		
			// 3. Extrair o ano
			ano = calendario.get(Calendar.YEAR);
			
			String dataFinal = dia+"/"+mes+"/"+ano;
			
			System.out.println("Data Final: ["+dataFinal+"]");
			System.out.println("Conversão realizada com sucesso!");
			
			dtVencimento = dataFinal;
			
			} else {
			
			dtVencimento = vencimento;
			}
		
	} catch (ParseException e) {
		// Executado caso o formato do texto esteja incorreto ou inválido
		System.err.println("Erro: A string não está no formato correto (dd/MM/yyyy). " + e.getMessage());
	}
	return dtVencimento;
}



//SE F5 MENOR QUE 2 RETORA H5 
package auxiliares;

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint;
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase;
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData;
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject;

import com.kms.katalon.core.annotation.Keyword;
import com.kms.katalon.core.checkpoint.Checkpoint;
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW;
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile;
import com.kms.katalon.core.model.FailureHandling;
import com.kms.katalon.core.testcase.TestCase;
import com.kms.katalon.core.testdata.TestData;
import com.kms.katalon.core.testobject.TestObject;
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS;
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI;

import internal.GlobalVariable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author T_DCDias 
 * @Data 19/10/2018
 * Classe com métodos de calculo de quantidade de dias entre duas datas no formato '01/11/2018' 
 */

public class CountDays {

	public static Integer verificadatas(String data1,String data2 )  {

		try{
			//          Método 'valida() ' verifica se a data tem '/' # Não usar, data formatada sem '/'
			//			if(!valida(data1)||!valida(data2))
			//			{
			//				System.out.println("\n[ Data invalida! ]");
			//				System.exit(1);
			//			}

			int diferenca;
			//atribuindo o dia, mês e ano as variáveis:
			int dia1=Integer.parseInt(data1.substring(0,2));
			int mes1=Integer.parseInt(data1.substring(3,5));
			int ano1=Integer.parseInt(data1.substring(6,10));

			int dia2=Integer.parseInt(data2.substring(0,2));
			int mes2=Integer.parseInt(data2.substring(3,5));
			int ano2=Integer.parseInt(data2.substring(6,10));

			//utilizando o método bissexto e se o dia e o mês e o ano são válidos:
			if(!bissexto(dia1,mes1,ano1)||!bissexto(dia2,mes2,ano2))
			{
				println("Data invalida!");
				System.exit(1);
			}

			//verificando se uma data é maior que a outra:
			if(ano2<ano1)
			{
				println("A data inicial digitada deve ser menor que a final!");
				System.exit(1);
			}
			else
			if((ano2==ano1)&&(mes2<mes1))
			{
				println("A data inicial digitada deve ser menor que a final!");
				System.exit(1);
			}

			//          Verifica se a data 2 < data 1, problema 13/10/2019  - 11/10/2019 compara 11 < 13 erro: A data inicial digitada deve ser menor que a final! #Não usar
			//          else
			//          if(dia2<dia1)
			//          {
			//            System.out.println("A data inicial digitada deve ser menor que a final!");
			//            System.exit(1);
			//          }

			//calculando a diferença de dias entre as datas:
			GregorianCalendar d1= new GregorianCalendar();
			d1.set(GregorianCalendar.DATE, dia1);
			d1.set(GregorianCalendar.MONTH, mes1);
			d1.set(GregorianCalendar.YEAR, ano1);
			d1.getTime();

			GregorianCalendar d2= new GregorianCalendar();
			d2.set(GregorianCalendar.DATE, dia2);
			d2.set(GregorianCalendar.MONTH, mes2);
			d2.set(GregorianCalendar.YEAR, ano2);
			d2.getTime();
			//			diferenca = diferencaEmDias(d1,d2);

			LocalDate inicio = LocalDate.of(ano1, mes1, dia1);
			LocalDate fim = LocalDate.of(ano2, mes2, dia2);

			System.err.println("DIA Inicio "+inicio+" DIA Fim "+fim);

			diferenca = (int)ChronoUnit.DAYS.between(inicio, fim);

			return diferenca;
		} catch (Exception e){ }
	}

	/**
	 * @author T_DCDias
	 * @param c1
	 * @param c2
	 * Calcula diferenca em dias
	 * @return
	 */
	public static int diferencaEmDias(Calendar c1, Calendar c2){
		long m1 = c1.getTimeInMillis();
		long m2 = c2.getTimeInMillis();
		return (int) ((m2 - m1) / (24*60*60*1000));
	}

	/**
	 *@author T_DCDias 
	 * @param str
	 * Verificando se a data possui somente os caracteres 0123456789/
	 * @return 
	 */
	private static boolean valida(String str){
		println("[ Método valida ]");
		//verificando se a data possui somente os caracteres 0123456789/
		for(int i=0;i<str.length();i++)
		{
			char ch=str.charAt(i);//verificando se nos índices data[5] e data[2] estão as barras "/"
			if((i==2)||(i==5))
			{
				if("/".indexOf(ch)==-1)
				{
					return(false);
				}
			}
			else
			if("0123456789".indexOf(ch)==-1)//verificando se nos outros índices estão os números "0123456789"
			{   println("[ FALSE ]")
				return(false);
			}
		}
		return(true);
	}

	/**
	 * @author T_DCDias
	 * @param dia
	 * @param mes
	 * @param ano
	 * método q verifica se o ano é bissexto
	 * verifica se o dia e o mês e o ano são válidos
	 * @return
	 */
	public static boolean bissexto(int dia, int mes, int ano){
		if((dia<1)||(dia>31)||(mes<1)||(mes>12)||(ano<1000))
		{
			return(false);
		}
		if(ano%400==0 || (ano%100!=0 && ano%4==0))
		{
			if((mes==2)&&(dia>29))
			{
				return(false);
			}
		}
		else
		if((mes==2)&&(dia>28))
		{
			return(false);
		}
		return(true);
	}


}

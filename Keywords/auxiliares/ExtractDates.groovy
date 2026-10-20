

package auxiliares

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
import java.text.SimpleDateFormat;
import java.time.LocalDate
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author T_DCDias
 * Classe responsável or receber um texto e extrair datas existentes. Exemplo : 'Vigência da apólice: 19/10/2018 à 19/10/2019'
 */

public class ExtractDates {

	/**
	 * @author T_DCDias
	 * @param text
	 * Método separa apenas nomeros e transforma em datas 03/07/25
	 * @return
	 */
	public static Map getSubDates(String text){

		Map<Integer,String> hasdates = new HashMap<Integer, String>();
		StringBuffer sbf = new StringBuffer();

		String[] vars = text.split("");
		int positions = 0;

		for(int i = 0; i< vars.length;i++){
			if(vars[i].equals("0") || vars[i].equals("1") ||
			vars[i].equals("2")|| vars[i].equals("3")  ||
			vars[i].equals("4")|| vars[i].equals("5")  ||
			vars[i].equals("6")|| vars[i].equals("7")  ||
			vars[i].equals("8")|| vars[i].equals("9")) {
				sbf.append(vars[i].toString());
				positions++;
			}
		}

		int joker = positions/8;
		int split =positions/joker;

		System.err.println("\n--[ Data values [ "+sbf.toString()+" ] "
				+"Positions [ "+positions+" ] "
				+"Total of Dates [ "+joker+" ] "
				+"Length of Dates [ "+split+" ] ] --\n");

		String srtval = sbf.toString();
		int start=0;
		int end=8;
		for(int i = 0; i < joker; i++){
			hasdates.put(i, formatDate(srtval.toString().substring(start, end)));
			start = start+8;
			end = end+8;
		}

		for(int i = 0; i < joker; i++){
			System.err.println("["+hasdates.get(i)+"]")
		}

		return hasdates;
	}

	/**
	 * @author T_DCDias
	 * @param strdate
	 * Formata para o padrão data 'dd/MM/yyyy'
	 * @return
	 */
	public static String formatDate(String strdate) {
		String dateFormated;
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		Date data = sdf.parse(strdate);
		sdf.applyPattern("dd/MM/yyyy");
		String dataFormatada = sdf.format(data);
		return dataFormatada;
	}

	/**
	 * @author T_DCDias
	 * @param strdate
	 * Formata para o padrão desejado 
	 * @return
	 * type - tipo de entrada, exe.: ddMMyyyy
	 * newtype - tipo de saida, exe.:dd/MM/yyyy 
	 * strdate - valor a ser formatado 
	 */
	public static String dateFormatteTypes(String type, String newtype, String strdate) {
		String dateFormated;
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		Date data = sdf.parse(strdate);
		sdf.applyPattern(newtype);
		String dataFormatada = sdf.format(data);
		return dataFormatada;
	}


	/**
	 * @author T_DCDias
	 * @param hours
	 * Extrai a hora de um texto
	 * @return
	 */
	public static int extractHours(String hours){
		int iHours;
		StringBuffer sbf = new StringBuffer();
		String[] vars = hours.split("");
		for(int i = 0; i< vars.length ; i++){
			if(i ==26 || i == 27  ){
				sbf.append(vars[i].toString());
			}
		}
		iHours = Integer.parseInt(sbf.toString());
		return iHours;
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static String dataAtual(){
		LocalDate hoje = LocalDate.now();
		return	dateFormatteTypes('yyy-MM-dd', 'dd/MM/yyyy',  hoje.toString())
	}

	/**
	 * @author T_DCDias
	 * @return
	 * Add days to current date using Calendar.add day
	 */
	public static String dataFutura(int day){
		String dateAfter
		Calendar now = Calendar.getInstance();
		//System.out.println("Current date : " + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR));
		now.add(Calendar.DATE, 1);
		String date = now.get(Calendar.DATE)
		String month = (now.get(Calendar.MONTH) + day)
		String year = now.get(Calendar.YEAR)
		dateAfter = date+"/"+month+ "/"+year
		return dateAfter
	}


	/**
	 * @author T_DCDias
	 * @param strdate
	 * Formata para o padrão data 'dd/MM/yyyy'
	 * @return
	 */
	public static String formatDateToBR(String strdate) {
		try{
			String dateFormated;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date data = sdf.parse(strdate);
			sdf.applyPattern("dd/MM/yyyy");
			String dataFormatada = sdf.format(data);
			return dataFormatada;
		} catch (Exception e){
			System.out.println("Sem data para formatar")
		}
	}

	/**
	 * Recebe 04/ago/2019
	 * retorna 04/08/2019
	 *
	 * @param dataRecive
	 * @return
	 *
	 *
	 */
	public static String converteData(String dataRecive){
		String dataReturn;
		String dataDia = dataRecive.substring(0, 2);
		String dataMes = dataRecive.substring(3, 6);
		String dataAno = dataRecive.substring(7, 11);
		Map<String, String> mes = new HashMap<String, String>();
		mes.put("jan",'01');
		mes.put("fev",'02');
		mes.put("mar",'03');
		mes.put("abr",'04');
		mes.put("mai",'05');
		mes.put("jun",'06');
		mes.put("jul",'07');
		mes.put("ago",'08');
		mes.put("set",'09');
		mes.put("out",'10');
		mes.put("nov",'11');
		mes.put("dez",'12');
		dataReturn = dataDia+'/'+mes.get(dataMes)+'/'+dataAno
		return dataReturn;
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param strDate1
	 * @param strDate2
	 * @return
	 */
	public static boolean compareDate(String strDate1, String strDate2) {
		boolean dateEquals = false;
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		Date data1 = sdf1.parse(strDate1);
		sdf1.applyPattern("ddMMyyyy");
		String data1Formatada = sdf1.format(data1);

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		Date data2 = sdf2.parse(strDate2);
		sdf2.applyPattern("ddMMyyyy");
		String data2Formatada =sdf2.format(data2);

		if(data1Formatada.equals(data2Formatada)){
			dateEquals = true
		}
		return dateEquals;
	}

	/**
	 * @author T_DCDias
	 * @param dias
	 * @return
	 * Retorna a data retrocedente da data atual ao numero de dias
	 */
	public static String retornaDataInferior(int dias){
		String data
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		if(dias == 0){
			data=sdf1.format(c.getTime())
		} else if(dias>0) {
			c.add(Calendar.DATE, -dias);
			data=sdf1.format(c.getTime())
		}

		System.out.println('['+data+']');
		return data
	}

	/**
	 * @author T_DCDias
	 * @param dias
	 * @return
	 * Retorna a data retrocedente da data atual ao numero de dias
	 */
	public static String retornaDataSuperior(int dias){
		String data
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		if(dias == 0){
			data=sdf1.format(c.getTime())
		} else if(dias>0) {
			c.add(Calendar.DATE, +dias);
			data=sdf1.format(c.getTime())
		}

		System.out.println('['+data+']');
		return data
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static String dataAtualizada(){
		String strdt
		try{
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
			Date date = new Date(System.currentTimeMillis());
			strdt = formatter.format(date).toString()
		} catch (Exception e){
		}
		return strdt
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static String horaAtualizada(){
		String strdt
		try{
			SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
			Date date = new Date(System.currentTimeMillis());
			strdt = formatter.format(date).toString()
		} catch (Exception e){
		}
		return strdt
	}

	/**
	 * @author be54955
	 * @return
	 * 
	 * "yyyy-MM-dd 'at' HH:mm:ss z"
	 * 
	 */
	public static String dataSetFormat(String f){
		String strdt
		try{
			SimpleDateFormat formatter= new SimpleDateFormat(f);
			Date date = new Date(System.currentTimeMillis());
			strdt = formatter.format(date).toString()
		} catch (Exception e){
		}
		return strdt
	}

	/**
	 * Recebe 04/ago/2019
	 * retorna 04/08/2019
	 *
	 * @param dataRecive
	 * @return
	 *
	 *
	 */
	public static String retornaMesLiteral(String mesDoAno){
		Map<String, String> mes = new HashMap<String, String>();
		mes.put("01",'Janeiro');
		mes.put("02",'Fevereiro');
		mes.put("03",'Março');
		mes.put("04",'Abril');
		mes.put("05",'Maio');
		mes.put("06",'Junho');
		mes.put("07",'Julho');
		mes.put("08",'Agosto');
		mes.put("09",'Setembro');
		mes.put("10",'Outubro');
		mes.put("11",'Novembro');
		mes.put("12",'Dezembro');

		return mes.get(mesDoAno)
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static String dataAtualizadaDia(){
		String strdt
		try{
			SimpleDateFormat formatter= new SimpleDateFormat("dd");
			Date date = new Date(System.currentTimeMillis());
			strdt = formatter.format(date).toString()
		} catch (Exception e){
		}
		return strdt
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static String dataAtualizadaMes(){
		String strdt
		try{
			SimpleDateFormat formatter= new SimpleDateFormat("MM");
			Date date = new Date(System.currentTimeMillis());
			strdt = formatter.format(date).toString()
		} catch (Exception e){
		}
		return strdt
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static String dataAtualizadaAno(){
		String strdt
		try{
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy");
			Date date = new Date(System.currentTimeMillis());
			strdt = formatter.format(date).toString()
		} catch (Exception e){
		}
		return strdt
	}
	
	/**
	 *
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static long difDiasDatas(String dt1, String dt2){
		
		int diaUm;
		int mesUm;
		int anoUm;
		
		int diaDois;
		int mesDois;
		int anoDois;
		
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate data = LocalDate.parse(dt1, formatador);
		diaUm = data.getDayOfMonth();
		mesUm = data.getMonthValue();
		anoUm = data.getYear();
		
		DateTimeFormatter formatadorDois = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate dataDois = LocalDate.parse(dt2, formatadorDois);
		diaDois = dataDois.getDayOfMonth();
		mesDois = dataDois.getMonthValue();
		anoDois = dataDois.getYear();
		
		LocalDate dataInicial = LocalDate.of(anoUm,mesUm,diaUm);
		LocalDate dataFinal = LocalDate.of(anoDois,mesDois,diaDois);
		long difDias = ChronoUnit.DAYS.between(dataInicial, dataFinal);
		System.out.println("\nNúmeros de dias entre ["+diaUm+"-"+mesUm+"-"+anoUm+"] e ["+diaDois+"-"+mesDois+"-"+anoDois+"] é ["+difDias+"]\n");
		return difDias;
	}
}

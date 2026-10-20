package sendEmail

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.text.DecimalFormat
import java.util.List

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import rpacommands.rpaCommands

public class emailDinamico extends rpaCommands {

	//	private static String authPathConfigEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Credenciais\\ConfigAuthEmail.txt"

	private static String authPathConfigEmailProp = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Credenciais\\ConfigAuthEmailProp.properties"

	/**
	 * @author Alz_sup54955
	 * @param tipoAba
	 * @param path
	 * @param file
	 * @param msgs
	 * @param hours
	 * @param startTime
	 * @param endTime
	 * @param hostName
	 * @param dataInicio
	 * @param dataFinal
	 * @return
	 */
	public static enviaEmailDinamico(String sender,String emaildestination, String tipoAba, String path, String file,List<String> msgs, String hours, String horaInicial, String horaFinal, String hostName, String dataInicio, String dataFinal, String assunto){
		try{
			//	lerConfiguraçõesAuth(authPathConfigEmail)
			lerConfiguraçõesAuthProp(authPathConfigEmailProp)
			System.out.println(user+" "+pwdsender+" "+host+" "+porta)
			
			List<String> anexos = new ArrayList<>();
			anexos[0] = path
			anexos[1] = file
			sendEmail.CreateEmail.createAllEmailSendMultipleFiles(sender,
					emaildestination,
					user,
					pwdsender,
					anexos,
					host,
					porta,
					tipoAba,
					Integer.toString(setMessagesTotalAlerts(msgs)),
					Integer.toString(setMessagesTotalPassed(msgs)),
					setMessagesAlerts(msgs),
					setMessagesPassed(msgs),
					horaInicial,
					horaFinal,
					hours,
					hostName,
					Integer.toString(msgs.size()),
					porcentagemDeDadosProcessados(msgs.size(), setMessagesTotalPassed(msgs)),
					porcentagemDeDadosProcessados(msgs.size(), setMessagesTotalAlerts(msgs)),
					dataInicio,
					dataFinal,
					assunto
					)
		} catch (Exception e){}
	}

	/**
	 * @author Alz_sup54955
	 * @param total
	 * @param totalMsg
	 * @return
	 */
	public static String porcentagemDeDadosProcessados(double total, double totalMsg){
		String result;
		try{
			if(totalMsg>0){
				double resto = totalMsg / total;
				double percent = resto * 100;

				System.out.println("\n"+total+"\n"+ totalMsg+"\n"+resto+"\n"+percent)

				String porcentagem = String.valueOf(percent) //.replace(".0", "")
				if(porcentagem.length() > 5){
					result = porcentagem.substring(0, 5)
				} else {
					result = porcentagem;
				}
			} else {
				result = "100";
			}
		} catch (ArithmeticException a){}

		System.out.println("result passed "+result)

		return result;
	}

	/**
	 * @author Alz_sup54955
	 * @param msgs
	 * @return
	 */
	public static String setMessagesAlerts(List<String> msgs){
		HashSet<String> corretor = new HashSet<String>();
		corretor.addAll(msgs);
		StringBuffer stbdr = new StringBuffer();
		for(int i = 0; i < corretor.size(); i++){
			if(corretor[i].toString().contains("Alerta:")){
				stbdr.append("<tr><td align='left' style='color: #122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'><b>")
				stbdr.append(corretor[i])
				stbdr.append("</b></td> </tr>")
			}
		}
		return stbdr.toString();
	}

	/**
	 * @author Alz_sup54955
	 * @param msgs
	 * @return
	 */
	public static int setMessagesTotalAlerts(List<String> msgs){
		int t;
		for(int i = 0; i <  msgs.size(); i++){
			if( msgs[i].toString().contains("Alerta:")){
				t = t +1;
			}
		}
		return t;
	}

	/**
	 * @author Alz_sup54955
	 * @param msgs
	 * @return
	 */
	public static String setMessagesPassed(List<String> msgs){
		HashSet<String> corretor = new HashSet<String>();
		corretor.addAll(msgs);
		StringBuilder stbdr = new StringBuilder();
		for(int i = 0; i < corretor.size(); i++){
			if(!corretor[i].toString().contains("Alerta:")){
				stbdr.append("<tr><td align='left' style='color: #122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'><b>")
				stbdr.append(corretor[i])
				stbdr.append("</b></td> </tr>")
			}
		}
		return stbdr.toString();
	}

	/**
	 * @author Alz_sup54955						   
	 * @param msgs
	 * @return
	 */
	public static int setMessagesTotalPassed(List<String> msgs){
		int t;
		for(int i = 0; i <  msgs.size(); i++){
			if(!msgs[i].toString().contains("Alerta:")){
				t = t +1;
			}
		}
		return t;
	}

	/**
	 * @author Alz_sup54955
	 * @param data
	 * @param colun
	 * @return
	 */
	public static List<String> listaDeAlertas(TestData data, int colun){
		System.out.println("\n\nRowNumbers :"+data.getRowNumbers()+"\n\n");
		List<String> msgsalert = new ArrayList<String>()
		for (int x= 1; x <= data.getRowNumbers(); x++) {
			msgsalert.add(data.getValue(colun, x))
		}
		System.out.println("msgsalert "+msgsalert.size());
		for(int j = 0; j < msgsalert.size(); j++){
			//	System.out.println("["+j+"] ["+ msgsalert.get(j)   +"]");
		}
		return  msgsalert;
	}

}

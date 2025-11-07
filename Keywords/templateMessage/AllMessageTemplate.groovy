package templateMessage

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

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

public class AllMessageTemplate {
	
	public static String messageTemplate(String totalError,
		String totalPassed,
		String listAlert,
		String listPassed,
		String horaInicio,
		String horaFinal,
		String horaTotal,
		String servidor,
		String totalRegistros,
		String porcentagem,
		String nomeRobo,
		String porcentagemDeAlertas,
		String dataInicio,
		String dataFinal
		){

System.out.println(totalError+" "+
	               listAlert+" "+ 
				   listPassed+" "+
				   horaInicio+" "+
				   horaFinal+" "+
				   horaTotal+" "+
				   servidor+" "+
				   totalRegistros+""+
				   porcentagem+" "+
				   nomeRobo+" "+
				   porcentagemDeAlertas+" "+
				   dataInicio+" "+
				   dataFinal)
System.out.println()

String sb = "<body style='margin:0; padding:0; background-color:#ffffff'>" +
"<center>" +
"<div class='gmailfix' style='white-space:nowrap; font:15px Arial,Sans-serif; line-height:0'>" +
"&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"+
"</div><table width='600' border='0' cellpadding='0' cellspacing='0' bgcolor='#ffffff'>" +
"</table><table width='600' align='center' border='0' cellpadding='0' cellspacing='0' class='width-1'>"+

"<tbody>"+

"<tr bgcolor='#FFFFFF'>"+

"	<td>"+
"		<table  border='0' cellpadding='0' cellspacing='0'>"+
"			<tbody><tr>"+
"				<td>"+
"					<img style='line-height: 0px; display: block;' src='cid:image_id1'>"+
"				</td>"+
"			</tr>"+
"		</tbody></table>"+
"	</td>"+

"</tr>"+

"<tr>"+
"</tr>"+

"<tr>"+
"<td>"+
"<table bgcolor='#FFFFFF' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 600px;'>"+
"						<tbody><tr>"+
"							<td align='left' style='color: #122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"								<b>"+
"								"+
"								<br><br>"+
"  							        <br>"+nomeRobo+
"								    <br>"+
"							</td>"+
"						  </tr>"+
"					    </tbody>"+
"                 <tr>"+
"	             <td style='height: 46px;'>"+
"		      &nbsp;"+
"	       </td>"+
"       </tr>"+
"</table>"+
"</td>"+
"</tr>"+

semProcessamento(totalPassed, totalError )+

/**
* Carrega lista de registros concluidos
*/
passed(totalPassed, listPassed, totalRegistros, porcentagem)+

//grafic("65")+

/**
* Carrega campo de mensagens de alerta
*/
alertas(totalError, listAlert, porcentagemDeAlertas)+

"<!-- card1 -->"+
"<tr>"+
"	<td bgcolor='#122b54'>"+
"		<table bgcolor='122b54' border='0' cellpadding='0' cellspacing='0' align='center'>"+
"			<tbody><tr>"+
"				<td style='height: 20px;'>"+
"					&nbsp;"+
"				</td>"+
"			</tr>"+
"			<tr>"+
"				<td>"+
"					<table border='0' cellpadding='0' cellspacing='0'>"+
"						<tbody><tr>"+
"							<td align='left' style='color: #ffffff; font-size: 18px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"								Tempo de execução: <br>"+
"							</td>"+
"						</tr>"+
"					</tbody></table>"+
"				</td>"+
"			</tr>"+
"			<tr>"+
"				<td style='height: 5px;'>"+
"					&nbsp;"+
"				</td>"+
"			</tr>"+
"		</tbody></table>"+
"	</td>"+
"</tr>"+

"<tr bgcolor='#dfeff2'>"+
"				<td>"+
"					<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 180px;'>"+
"						<tbody><tr>"+
"							<td align='left' style='color:#122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"									Data Inicial : "+
"							</td>"+
"							<td align='left' style='color:#FF4500; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"								<b>"+
"									"+dataInicio+
"								</b>"+
"							</td>"+

"						</tr>"+
"					</tbody></table>"+
"				</td>"+
"			</tr>"+

"<tr bgcolor='#dfeff2'>"+
"				<td>"+
"					<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 180px;'>"+
"						<tbody><tr>"+
"							<td align='left' style='color:#122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"									Data Final : "+
"							</td>"+
"							<td align='left' style='color:#FF4500; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"								<b>"+
"									"+dataFinal+
"								</b>"+
"							</td>"+

"						</tr>"+
"					</tbody></table>"+
"				</td>"+
"			</tr>"+

"<tr bgcolor='#dfeff2'>"+
"				<td>"+
"					<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 140px;'>"+
"						<tbody><tr>"+
"							<td align='left' style='color:#122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"									Hora Inicial: "+
"							</td>"+
"							<td align='left' style='color:#FF4500; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"								<b>"+
"									"+horaInicio+
"								</b>"+
"							</td>"+

"						</tr>"+
"					</tbody></table>"+
"				</td>"+
"			</tr>"+

"<tr bgcolor='#dfeff2'>"+
"				<td>"+
"					<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 143px;'>"+
"						<tbody><tr>"+
"							<td align='left' style='color:#122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"									Hora Final: "+
"							</td>"+
"							<td align='left' style='color:#FF4500; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"								<b>"+
"									"+horaFinal+
"								</b>"+
"							</td>"+

"						</tr>"+
"					</tbody></table>"+
"				</td>"+
"			</tr>"+


"<tr bgcolor='#dfeff2'>"+
"				<td>"+
"					<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 250px;'>"+
"						<tbody><tr>"+
"							<td align='left' style='color:#122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"									Tempo total: "+
"							</td>"+
"							<td align='left' style='color:#FF4500; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"								<b>"+
"									"+horaTotal+
"								</b>"+
"							</td>"+

"						</tr>"+
"					</tbody></table>"+
"				</td>"+
"			</tr>"+

"<tr bgcolor='#dfeff2'>"+
"				<td>"+
"					<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 250px;'>"+
"						<tbody><tr>"+
"							<td align='left' style='color:#122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"									Servidor executado: "+
"							</td>"+
"							<td align='left' style='color:#FF4500; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"								<b>"+
"									"+servidor+
"								</b>"+
"							</td>"+

"						</tr>"+
"					</tbody></table>"+
"				</td>"+
"			</tr>"+





"<tr  bgcolor='#FFFFFF'>"+
"  <td>"+
"	 <table bgcolor='#FFFFFF' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 600px;'>"+
"		<tbody><tr>"+
"         <td align='left' class='textfooter2' style='color: #414141; font-size: 9px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.7px; vertical-align: top;'>"+
"				</td>"+
"			</tr>"+
"		</tbody>"+
"        <tr>"+
"	        <td style='height: 40px;'>"+
"		      &nbsp;"+
"	        </td>"+
"       </tr>"+
"     </table>"+
"   </td>"+
"</tr>"+

rodape()


return sb;

}
		
		
		
public static String rodape(){
	String str = "<!-- box-btns -->"+
"<tr bgcolor='#122b54' align='left'>"+
"	<td>"+
"		<table align='left' border='0' cellpadding='0' cellspacing='0' style='width: 600px;>"+
"			 <!-- parte-1 -->"+
"			<tbody><tr>"+
"				<td>"+
"					<table border='0' cellpadding='0' cellspacing='0'>"+
"						<tbody><tr>"+
"							<td>"+
"  							  <img style='line-height: 0px; display: block;' src='cid:image_id2'>"+
"							</td>"+
"						</tr>"+
"					</tbody>"+
"                 </table>"+
"				</td>"+
"</tr>"
	return str;
}
		
public static String grafic(String p){
	String str = "<!-- grafico -->"+
 	       "     <table style='width:"+p+"%;'> "+
           "                  <div style='text-align: right;padding-top: 10px;padding-bottom: 10px;color: white;width:10%; background-color: #4CAF50;'>"+p+"% </div>"+
           "    </table>"
	return str;
}

public static String graficAlert(String p){
	String str = "<!-- grafico -->"+
			"     <table style='width:"+p+"%;'> "+
		   "                  <div style='text-align: right;padding-top: 10px;padding-bottom: 10px;color: white;width:10%; background-color: #FF0000;'>"+p+"% </div>"+
		   "    </table>"
	return str;
}
		
public static String semProcessamento(String totalPassed, String totalError ){
	int tl =  Integer.parseInt(totalError);
	int t2 =  Integer.parseInt(totalPassed);
	String str
	if(tl == 0 && t2 == 0){
	str = "<!-- card1 -->"+
	"<tr>"+
	"	<td bgcolor='#122b54'>"+
	"		<table bgcolor='122b54' border='0' cellpadding='0' cellspacing='0' align='center'>"+
	"			<tbody><tr>"+
	"				<td style='height: 20px;'>"+
	"					&nbsp;"+
	"				</td>"+
	"			</tr>"+
	"			<tr>"+
	"				<td>"+
	"					<table border='0' cellpadding='0' cellspacing='0'>"+
	"						<tbody><tr>"+
	"							<td align='left' style='color: #ffffff; font-size: 18px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
	"								Sem processamento: <br>"+
	"							</td>"+
	"						</tr>"+
	"					</tbody></table>"+
	"				</td>"+
	"			</tr>"+
	"			<tr>"+
	"				<td style='height: 5px;'>"+
	"					&nbsp;"+
	"				</td>"+
	"			</tr>"+
	"		</tbody></table>"+
	"	</td>"+
	"</tr>"+
	
	"<tr  bgcolor='#FFFFFF'>"+
	"  <td>"+
	"	 <table bgcolor='#FFFFFF' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 600px;'>"+
	"		<tbody><tr>"+
	"         <td align='left' class='textfooter2' style='color: #414141; font-size: 9px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.7px; vertical-align: top;'>"+
	"				</td>"+
	"			</tr>"+
	"		</tbody>"+
	"        <tr>"+
	"	        <td style='height: 30px;'>"+
	"		      &nbsp;"+
	"	        </td>"+
	"       </tr>"+
	"     </table>"+
	"   </td>"+
	"</tr>"
	} else {
      str = "<tr>"+
            "	<td style='height: 20px;'>"+
            "		&nbsp;"+
            "	</td>"+
            "</tr>"
}
return str;
	
}

public static String passed(String totalPassed, String listPassed, String totalRegistros, String porcentagem){
int tl =  Integer.parseInt(totalPassed);
String str
if(tl > 0){
str = "<!-- card1 -->"+
"<tr>"+
"	<td bgcolor='#122b54'>"+
"		<table bgcolor='122b54' border='0' cellpadding='0' cellspacing='0' align='center'>"+
"			<tbody><tr>"+
"				<td style='height: 20px;'>"+
"					&nbsp;"+
"				</td>"+
"			</tr>"+
"			<tr>"+
"				<td>"+
"					<table border='0' cellpadding='0' cellspacing='0'>"+
"						<tbody><tr>"+
"							<td align='left' style='color: #ffffff; font-size: 18px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"								Dados processados: <br>"+
"							</td>"+
"						</tr>"+
"					</tbody></table>"+
"				</td>"+
"			</tr>"+
"			<tr>"+
"				<td style='height: 5px;'>"+
"					&nbsp;"+
"				</td>"+
"			</tr>"+
"		</tbody></table>"+
"	</td>"+
"</tr>"+

"<tr bgcolor='#dfeff2'>"+
"				<td>"+
"					<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 70px;'>"+
"						<tbody><tr>"+
"							<td align='left' style='color:#122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"									Total: "+
"							</td>"+
"							<td align='left' style='color:#FF4500; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"								<b>"+
"									"+totalRegistros+
"								</b>"+
"							</td>"+

"						</tr>"+
"					</tbody></table>"+
"				</td>"+
"			</tr>"+

"<tr bgcolor='#dfeff2'>"+
"				<td>"+
"					<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 150px;'>"+
"						<tbody><tr>"+
"							<td align='left' style='color:#122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"									Total processado: "+
"							</td>"+
"							<td align='left' style='color:#FF4500; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"								<b>"+
"									"+totalPassed+
"								</b>"+
"							</td>"+

"						</tr>"+
"					</tbody></table>"+
"			</td>"+
"</tr>"+


"<!-- card2 -->"+
"<tr>"+
"	<td bgcolor='#dfeff2'>"+
"		<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='center'>"+
"			<tbody><tr>"+
"				<td style='height: 25px;'>"+
"					&nbsp;"+
"			</td>"+
"			</tr>"+

"			<tr bgcolor='#b5dae6'>"+
"				<!-- box1 -->"+
"				<td>"+
"					<table border='0' cellpadding'0' cellspacing='0'>"+
"					  <tbody>"+
"						<tr>"+
"							<td>"+
"								<table align='center' border='0' cellpadding='0' cellspacing='0'>"+
"									<tbody>"+
"												"+listPassed+""+
"								</tbody></table>"+
"							</td>"+
"						</tr>"+
"					 </tbody>"+
"                  </table>"+
"				</td>"+
"				<td style='width: 13px;'>"+
"				</td>"+


"<!-- card2 -->"+
"<tr>"+
"<td bgcolor='#f7160a'>"+
"		<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='center'>"+
"			<tbody><tr>"+
"				<td style='height: 25px;'>"+
"					&nbsp;"+
"			</td>"+
"</tr>"+

"<tr bgcolor='#b5dae6'>"+
"				<!-- box1 -->"+
"				<td>"+
                     grafic(porcentagem)+
"				</td>"+
"</td>"+


"<tr>"+
"	<td style='height: 20px;'>"+
"		&nbsp;"+
"	</td>"+
"</tr>"+

"<tr  bgcolor='#FFFFFF'>"+
"  <td>"+
"	 <table bgcolor='#FFFFFF' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 600px;'>"+
"		<tbody><tr>"+
"         <td align='left' class='textfooter2' style='color: #414141; font-size: 9px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.7px; vertical-align: top;'>"+
"				</td>"+
"			</tr>"+
"		</tbody>"+
"        <tr>"+
"	        <td style='height: 30px;'>"+
"		      &nbsp;"+
"	        </td>"+
"       </tr>"+
"     </table>"+
"   </td>"+
"</tr>"


} else {
str = "<tr>"+
"	<td style='height: 20px;'>"+
"		&nbsp;"+
"	</td>"+
"</tr>"
}
return str;
}
		
public static String alertas(String totalError, String listAlert, String porcentagemDeAlertas ){
int tl =  Integer.parseInt(totalError);
String str
if(tl > 0){
str = "<!-- card1 -->"+
"<tr>"+
"	<td bgcolor='#FFA500'>"+
"		<table bgcolor='#FFA500' border='0' cellpadding='0' cellspacing='0' align='center'>"+
"			<tbody><tr>"+
"				<td style='height: 20px;'>"+
"					&nbsp;"+
"				</td>"+
"			</tr>"+
"			<tr>"+
"				<td>"+
"					<table border='0' cellpadding='0' cellspacing='0'>"+
"						<tbody><tr>"+
"							<td align='left' style='color: #122b54; font-size: 18px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"							   <b>"+
"								  Dados não processados: <br>"+
"						       </b>"+
"							</td>"+
"						</tr>"+
"					</tbody></table>"+
"				</td>"+
"			</tr>"+
"			<tr>"+
"				<td style='height: 5px;'>"+
"					&nbsp;"+
"				</td>"+
"			</tr>"+
"		</tbody></table>"+
"	</td>"+
"</tr>"+

"<tr bgcolor='#dfeff2'>"+
"				<td>"+
"					<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 130px;'>"+
"						<tbody><tr>"+
"							<td align='left' style='color:#122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"									Total de alertas: "+
"							</td>"+
"							<td align='left' style='color:#FF4500; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
"								<b>"+
"									"+totalError+
"								</b>"+
"							</td>"+

"						</tr>"+
"					</tbody></table>"+
"			</td>"+
"</tr>"+

"<!-- card2 -->"+
"<tr>"+
"	<td bgcolor='#dfeff2'>"+
"		<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='center'>"+
"			<tbody><tr>"+
"				<td style='height: 25px;'>"+
"					&nbsp;"+
"			</td>"+
"			</tr>"+

"			<tr bgcolor='#b5dae6'>"+
"				<!-- box1 -->"+
"				<td>"+
"					<table border='0' cellpadding'0' cellspacing='0'>"+
"						<tbody>"+
"						<tr>"+
"							<td>"+
"								<table align='center' border='0' cellpadding='0' cellspacing='0'>"+
"									<tbody>"+
"												"+listAlert+""+
"								</tbody></table>"+
"							</td>"+
"						</tr>"+
"					</tbody></table>"+
"				</td>"+
"				<td style='width: 13px;'>"+
"				</td>"+

"<!-- card2 -->"+
"<tr>"+
"<td bgcolor='#f7160a'>"+
"		<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='center'>"+
"			<tbody><tr>"+
"				<td style='height: 25px;'>"+
"					&nbsp;"+
"			</td>"+
"</tr>"+

"<tr bgcolor='#b5dae6'>"+
"				<!-- box1 -->"+
"				<td>"+
					 graficAlert(porcentagemDeAlertas)+
"				</td>"+
"</td>"+



"<tr>"+
"	<td style='height: 20px;'>"+
"		&nbsp;"+
"	</td>"+
"</tr>"+

"<tr  bgcolor='#FFFFFF'>"+
"  <td>"+
"	 <table bgcolor='#FFFFFF' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 600px;'>"+
"		<tbody><tr>"+
"         <td align='left' class='textfooter2' style='color: #414141; font-size: 9px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.7px; vertical-align: top;'>"+
"				</td>"+
"			</tr>"+
"		</tbody>"+
"        <tr>"+
"	        <td style='height: 30px;'>"+
"		      &nbsp;"+
"	        </td>"+
"       </tr>"+
"     </table>"+
"   </td>"+
"</tr>"
} else {
str = "<tr>"+
"	<td style='height: 20px;'>"+
"		&nbsp;"+
"	</td>"+
"</tr>"
}
return str;
}
}

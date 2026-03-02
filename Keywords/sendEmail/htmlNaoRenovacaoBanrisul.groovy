package sendEmail

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

public class htmlNaoRenovacaoBanrisul {



	public static String htmlNaoRenovacaoBanrisulBody(String corretor,String email, String telefone, String susep, String proponente, String cpfcnpj, String apolice, String finalVigencia, String nome, List<String> imagens){

		String sb = "<body style='margin:0; padding:0; background-color:#ffffff'>" +
				"<center>" +
				"<table width='100%' bgcolor='#ffffff' border='0' cellspacing='0' cellpadding='0' align='center'>" +
				"<tr>" +
				"  <td align='center>'" +
				"   <!-- CONTAINER -->" +
				"	<table class='container' width='600' border='0' cellspacing='0' cellpadding='0' style='background-color:#ffffff;'>" +
				"	  <!-- HEADER -->" +
				"	  <tr>" +
				"		<td align='center'>" +
				"		  <img src='cid:image_id1' alt='Allianz' width='500' class='responsive-img'>" +
				"		</td>" +
				"	  </tr>" +
				"</td>" +
				"</table>" +
				"<center/>"+
				"<table width='552' border='0' cellspacing='0' cellpadding='0' >" +
				"<tr>"+
				"	<td>"+
				"		<table style='padding: 15px 20px; margin-left: 20px;'>"+
				"			<tbody><tr>"+
				"				<td>"+
				"					<table border='0' cellpadding='0' cellspacing='0'>"+
				"						<tbody><tr>"+
				"                       <tr><td style='font-size: 9px; color:#003781; '>CORRETOR: <span style='color:#f62459;'>"+corretor+"</span></td></tr>" +
				"                       <tr><td style='font-size: 9px; color:#003781; '>E-MAIL: <span style='color:#f62459;'>"+email+"</span></td></tr>" +
				"                       <tr><td style='font-size: 9px; color:#003781; '>TELEFONE: <span style='color:#f62459;'>"+telefone+"</span></td></tr>" +
				"                       <tr><td style='font-size: 9px; color:#003781; '>SUSEP: <span style='color:#f62459;'>"+susep+"</span></td></tr>" +
				"                       <tr><td height='20'>&nbsp;</td></tr>" +
				"                       <tr><td style='font-size: 9px; color:#003781; '>PROPONENTE: <span style='color:#f62459;'>"+proponente+"</span></td></tr>" +
				"                       <tr><td style='font-size: 9px; color:#003781; '>CPF/CNPJ: <span style='color:#f62459;'>"+cpfcnpj+"</span></td></tr>" +
				"                       <tr><td style='font-size: 9px; color:#003781; '>APÓLICE: <span style='color:#f62459;'>"+apolice+"</span></td></tr>" +
				"                       <tr><td style='font-size: 9px; color:#003781; '>FINAL VIGÊNCIA: <span style='color:#f62459;'>"+finalVigencia+"</span></td></tr>" +
				"						</tr>"+
				"					</tbody></table>"+
				"				</td>"+
				"			</tr>"+
				"		</tbody></table>"+
				"	</td>"+
				"</tr>"+
				"</table>"+

				"<table width='550' border='0' cellpadding='0' cellspacing='0' >" +
				"<tr>"+
				"	<td>"+
				"		<table style='padding: 10px 15spx; margin-left: 20px;'>"+
				"			<tbody><tr>"+
				"           <td style='padding: 20px; font-size:16px; color:#000000; line-height:1.5; '>"+
				"           <p><strong>Olá "+nome+",</strong></p>"+
				"           <p>Gostaríamos de informar que o seguro do seu veículo <br>"+
				"           com a Allianz <strong>não será renovado</strong> na próxima data de vencimento.</p>"+
				"           <p>Entendemos a importância de manter sua proteção e, por isso, <br>"+
				"           já informamos ao seu corretor para que ele possa te apoiar em  <br>"+
				"           uma transição segura, buscando proposta em novas seguradoras.</p>"+
				"           <p>Caso tenha alguma dúvida, entre em contato com o seu corretor. <br>"+
				"           Obrigado por escolher a Allianz e esperamos atender você  <br>"+
				"           em uma próxima oportunidade.</p>"+
				"           </td>"+
				"			</tr>"+
				"		    </tbody>"+
				"       </table>"+
				"	</td>"+
				"</tr>"+
				"</table>"+

				"<!-- FRASE FINAL -->"+
				"<tr align='center'>"+
				"  <td style='padding: 20px; color:#636363; font-size:16px; '>"+
				"	<strong>Allianz.</strong> Você pronto para viver o melhor."+
				"  </td>"+
				"</tr>"+



				"<table width='100%' bgcolor='#ffffff' border='0' cellspacing='0' cellpadding='0' align='center'>" +
				"<tr>" +
				"  <td align='center>'" +
				"   <!-- CONTAINER -->" +
				"	<table class='container' width='900' border='0' cellspacing='0' cellpadding='0' style='background-color:#ffffff;'>" +
				"	  <!-- HEADER -->" +
				"	  <tr>" +
				"		<td align='center'>" +
				"		  <img src='cid:image_id6' alt='Allianz' width='535' class='responsive-img'>" +
				"		</td>" +
				"	  </tr>" +
				"</td>" +
				"</table>" +

				"<table border='0' cellpadding='0' cellspacing='0' >"+
				"<tr>"+
				"	<td align='left' class='textfooter' style='font-size: 12px; color: #003781; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.9px; line-height: 1.2;'>"+
				"		Siga nossas redes sociais"+
				"	</td>"+
				"	<td width='10'>"+
				"		&nbsp;"+
				"	</td>"+
				"	<td width='26' align='left' style='line-height: 1px;'>"+
				"		<a href='https://www.facebook.com/allianzbrasil/' target='_blank'>"+
				"			<img src='cid:image_id2' alt='facebook'>"+
				"		</a>"+
				"	</td>"+
				"	<td width='5'>"+
				"		&nbsp;"+
				"	</td>"+
				"	<td width='26' align='left' style='line-height: 1px;'>"+
				"		<a href='https://www.instagram.com/allianzbrasil/' target='_blank'>"+
				"			<img src='cid:image_id3' alt='instagram'>"+
				"		</a>"+
				"	</td>"+
				"	<td width='5'>"+
				"		&nbsp;"+
				"	</td>"+
				"	<td width='28' align='left' style='line-height: 1px;margin-right:103px'>"+
				"		<a href='https://www.linkedin.com/company/allianz-seguros-brasil/mycompany/verification/' target='_blank'>"+
				"			<img src='cid:image_id4' alt='linkedin'>"+
				"		</a>"+
				"	</td>"+
				"	<td align='left' class='textfooter' style='font-size: 12px; color: #003781; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.9px; line-height: 1.2; '>"+
				"		Precisou? Chama no chat"+
				"	</td>"+
				"	<td width='10'>"+
				"		&nbsp;"+
				"	</td>"+
				"	<td width='26' align='left' style='line-height: 1px;'>"+
				"		<a href='https://chatcc.allianz.com.br/%0D%0D' target='_blank'>"+
				"			<img src='cid:image_id5' alt='facebook'>"+
				"		</a>"+
				"	</td>"+
				"</tr>"+
				"</table>"+

				"<tr><td height='20'>&nbsp;</td></tr>" +

				"<table width='520' border='0' cellpadding='0' cellspacing='0'>"+
				"<tr>"+
				"       <td align='justify' class='textfooter2' style='color: #414141; font-size: 8px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.5px; vertical-align: top;'>"+
				"            Allianz Seguros S.A., CNPJ: 61.573.796/0001<a href='#' style='color: #414141; text-decoration: none;'>-66, Rua Eugênio de Medeiros no 303,</a> ands. 1-parte, 2 ao 9, 15 e 16, Pinheiros, São Paulo - CEP: 05425-000. "+
				"            Processo SUSEP 15414.002216/2004-57, 15414.002901.2007/26, 15414.900597/2017-56, 15414.004661/2004-51, 15414.004732/2004-16, 15414.900340/2018-85, "+
				"            15414.100898/2004-62, 15414.001289.2007-74.. Consulte as Condições Gerais, assim como os "+
				"            planos de assistência 24h, serviços e abrangência do seguro no site <a href='https://www.allianz.com.br' style='color: #414141; text-decoration: none;'>www.allianz.com.br.</a> O registro do produto é automático e não representa aprovação "+
				"            ou recomendação por parte da SUSEP. A aceitação do seguro está sujeita a análise do risco. Algumas coberturas e serviços possuem restrições, carência "+
				"            e limites diferenciados. Para saber todos os detalhes e conhecer a lista completa consulte o Manual do Segurado/Condições Gerais. Linha Direta Allianz "+
				"            Corretor: 4090 1120 (Capitais e Regiões Metropolitanas), 0800 777 8243 (demais regiões). SAC (24 horas): 08000 115 215. Ouvidoria Allianz Seguros: 0800 "+
				"            771 3313. Atendimento à pessoa com deficiência auditiva ou de fala: 08000 121 239. A Allianz Seguros respeita a sua privacidade e é contra o spam na "+
				"            rede. Para mais informações, acesse a Política de Privacidade de Dados da Allianz, disponível em: <a href='https://www.allianz.com.br/politica-de-privacidade' style='color: #414141; text-decoration: none;'>www.allianz.com.br/politica-de-privacidade.</a> "+
				"       </td>"+
				"</tr>"+
				"<tr>"+
				"	<td style='height: 26px;'>"+
				"		&nbsp;"+
				"	</td>"+
				"</tr>"+


				"</table>"











		return sb;
	}
}

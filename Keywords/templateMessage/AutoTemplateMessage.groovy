package templateMessage

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

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

import internal.GlobalVariable
import sendEmail.MessageFactory

public class AutoTemplateMessage extends MessageFactory{

	/**
	 * Aviso de Não Renovação Apólice Allianz/Santander
	 * @return
	 */
	public static String avisoNaoRenovacaoAllianzSantanderNR(String segurado, String apolice, String rodape, String cabecalho, String dataVigencia, String dataAtual){
		String sb = "<head>" +
				"<center><table>" +
				addImageCabecalho(cabecalho,'80','35')+"<p>"+
				"<tr>" +
				"<td height='450'>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<td width='33'>&nbsp;</td>" +
				"		<td style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 14px; font-weight: 300; letter-spacing: -0.2px; line-height: 20px'>" +
				"			São Paulo, "+dataAtual+"<br><br>" +
				"			Após análise da proposta, por questões de regras internas de aceitação, infelizmente não <br>" +
				"			seguiremos com a contratação do seguro para o veículo (PLACA XXXXX) <br><br>" +
				"			Seu seguro atual vencerá em <strong>"+dataVigencia+"</strong>. <br><br>" +
				"			Para realizar uma cotação no Santander AutoCompara, escolha a forma de contato <br>" +
				"			mais conveniente para você: <br><br>" +
				"			Site AutoCompara <b><a href='https://www.santander.com.br/autocompara' target='_blank' style='text-decoration: none; color: #003781;'>www.autocompara.com.br</a></b>.<br>" +
				"			Central de Atendimento Santander AutoCompara: 4004 4495 (Regiões Metropolitanas) <br>" +
				"			0800 723 4495 (Demais Localidades). Disponível de segunda à sexta feira das 8h às 20h, <br>" +
				"			exceto feriados. <br> <br>" +
				"			É importante que você faça isso antes do final da vigência do seu seguro citado acima, <br>" +
				"			observado o tempo hábil para tratar de nova contratação. <br>" +
				"			Até breve," +
				"		</td>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				addImageRodape(rodape,'80','35')+
				"<tr><td height='30'>&nbsp;</td></tr>" +
				"<tr>" +
				"<td>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<td width='11'>&nbsp;</td>" +
				"		<td width='22' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='168' height='52' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.4px; line-height: 15px'>" +
				"			CENTRAL DE ATENDIMENTO <br>" +
				"			SANTANDER AUTOCOMPARA: <br><br>" +
				"		</td>" +
				"		<td width='13' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='170' height='52' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.4px; line-height: 15px'>" +
				"			SAC- SERVIÇO DE ATENDIMENTO <br>" +
				"			AO CONSUMIDOR SANTANDER <br>" +
				"			AUTOCOMPARA <br><br>" +
				"		</td>" +
				"		<td width='12' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='192' height='52' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.4px; line-height: 15px'>" +
				"			OUVIDORIA SANTANDER <br>" +
				"			AUTOCOMPARA <br><br>" +
				"		</td>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<td width='11'>&nbsp;</td>" +
				"		<td width='22' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='168' height='109' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.2px; line-height: 15px'>" +
				"			<b style='font-size: 11px; font-weight: 300; letter-spacing: -0.7px;'>" +
				"				Para cotações, contratações e demais <br>" +
				"				informações, ligue para: <br>" +
				"				<b style='color: #e4003a;'>4004 4495</b> (regiões metropolitanas). <br>" +
				"				<b style='color: #e4003a;'>0800 723 4495</b> (Demais localidades) <br>" +
				"				De segunda a sexta-feira, das 8h às 20h, exceto feriados." +
				"			</b>" +
				"		</td>" +
				"		<td width='13' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='170' height='109' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.2px; line-height: 15px'>" +
				"			<b style='font-size: 11px; font-weight: 300; letter-spacing: -0.7px;'>" +
				"				Reclamações. sugestões e informações <br>" +
				"				gerais:  <b style='color: #e4003a;'>0800 726 7777</b>. Atende também <br>" +
				"				deficientes auditivos e de fala. No <br>" +
				"				exterior, ligue a cobrar para: <br>" +
				"				<b style='color: #e4003a;'> 55 11 3012 3336. </b><br>" +
				"				Atendimento: 24h por dia,  todos os dias." +
				"			</b>" +
				"		</td>" +
				"		<td width='12' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='192' height='109' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.2px; line-height: 15px'>" +
				"			<b style='font-size: 11px; font-weight: 300; letter-spacing: -0.7px;'>" +
				"				Se não ficar satisfeito com a solução <br>" +
				"				apresentada <b style='color: #e4003a;'>0800 726 0322</b>. <br>" +
				"				Atende também deficientes auditivos e <br>" +
				"				de fala. De segunda a sexta-feira, das <br>" +
				"				9h às 18h, exceto feriados." +
				"			</b>" +
				"		</td>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				"<tr><td height='4'></td></tr>" +
				"<tr>" +
				"<td height='170'>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<tr>" +
				"			<td width='34'>&nbsp;</td>" +
				"			<td style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 9px; font-weight: 300; letter-spacing: -0.2px; line-height: 13px '>" +
				"				Seguro intermediado pela Santander Corretora de Seguros, Investimentos e Serviços S.A., CNPJ nº 04.270.778/0001-71, SUSEP<br>" +
				"				202019222 e garantido pela Allianz Brasil Seguradora S.A. O registro deste plano na SUSEP não implica, por parte da autarquia, em  <br>" +
				"				incentivo ou recomendação à sua comercialização. Consulte as condições gerais em sulamericaauto.com.br. Allianz Brasil Seguradora  <br>" +
				"               S.A., nova denominação da Sul América Seguros de Automóveis e Massificados S.A., CNPJ: 32.357.481/0001-83, Avenida República do <br>" +
				"				Chile nº 330, Bloco 1, 24º ao 28º andar, Centro, Rio de Janeiro – RJ. SAC 24 horas: 0800 725 5901. Ouvidoria: 0800 720 9740 e  <br>" +
				"				atendimento à pessoa com deficiência auditiva ou de fala 24 horas: 0800 970 0600. Desde julho de 2020, a Allianz adquiriu a seguradora <br>" +
				"				Sul América Seguros de Automóveis e Massificados S.A.  <br>" +
				"				A Allianz Seguros respeita a sua privacidade e é contra o spam na rede. Para mais informações, acesse a Política de Privacidade de  <br>" +
				"				Dados Allianz, disponível em:  <a href='https://www.allianz.com.br/politica-de-privacidade' style='color: #003781;'>www.allianz.com.br/politica-de-privacidade</a>." +
				"			</td>" +
				"		</tr>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				"</table>" +
				"</center></table>"
		return sb;
	}
	
	
	/**
	 * Aviso de Renovação Apólice Allianz/Santander
	 * IBLBDM-74087
	 * @return
	 */
	public static String avisoRenovacaoAllianzSantander15A(String segurado,  String apolice, String vigencia, String placa, String veiculo  ){
	
	String sb = "<html>" +
	"<head>" +
	"<center><table>" +
	"<meta charset='UTF-8'>" +
	"<link rel='stylesheet' href='style.css'>"+
	"<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
	"<title>Allianz</title>" +
    "</head>"+
	"<body>"+
	"<main class='container'>"+
	"<div class='center'>"+
	"<div class='container'>"+
	"<div class='center'>"+
	"<header class='header'>"+
    "		<img src='cid:image_id1' alt='Logo Allianz'>"+
	"</header>"+
	"</div>"+
	"<tr>"+
	"<td>"+
	"	<table border='0' cellpadding='0' cellspacing='0'>"+
	"		<tr>"+
	"			<td>"+
	"				<table border='0' cellpadding='0' cellspacing='0'>"+
	"					<tr>"+
	"						<td>"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"									<td style='color: #282828; font-size: 25px; text-align: center;'>"+
	"				                      <strong> Olá, "+segurado+"</strong>"+
	"									</td>"+
	"								</tr>"+
	"								<tr>"+
	"									<td >"+
	"			                            <img src='cid:image_id2' alt='Título'>"+
	"									</td>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	"						<td valign='bottom'>"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"								<tr>"+
	"									<td>"+
	"		                              <img src='cid:image_id3' alt='Imagem 1'>"+
	"									</td>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	"					</tr>"+
	"				</table>"+
	"			</td>"+
	"		</tr>"+
	"	</table>"+
	"</td>"+
    "</tr>"+
	
	"<tr style='height: 30px;'>"+
	"</tr>"+
	
	"<center><table>" +
	"<tr>"+
	"<td>"+
	"	<table border='0' cellpadding='0' cellspacing='0'>"+
	"		<tr>"+
	"			<td>"+
	"				<table border='0' cellpadding='0' cellspacing='0'>"+
	"					<tr>"+
	"						<p style=' font-size: 18px;' >"+
	"							Para garantir a sua tranquilidade e proteção, a <strong>Allianz Seguros</strong> em <br>"+
	"							parceria com o <strong>Autocompara</strong> preparou a renovação do seu seguro <br>"+
	"							auto, mantendo as coberturas já contratadas. <br>"+
	"						</p>"+
	"					</tr>"+
	"<tr style='height: 40px;'>"+
	"</tr>"+
	"				</table>"+
	"			</td>"+
	"		</tr>"+
	"	</table>"+
	"</td>"+
    "</tr>"+
	"</center></table>" +
	
	"<tr style='height: 80px;'>"+
	"</tr>"+
		
	"<center><table border='0' cellpadding='0' cellspacing='0' class='width-1'>" +
	"<div>"+
	"<img src='cid:image_id19' alt='top' width='495' heigh='390' style='display: block;' >"+
	"	<table  bgcolor='#e8e8e8' style='margin: 0; padding: 0; width: 495' >" +
	"		<tr style='height: 35px;' >" +
	"			<td>" +
	"				<img src='cid:image_id4' alt='Ícone Veículo'>"+
	"			</td>" +
	"		    <td style=' font-size: 15px;' >" +
	"				<strong>Veículo</strong> "+veiculo+""+
	"			</td>" +
	"		</tr>" +
	"		<tr style='height: 35px;' >" +
	"			<td>" +
	"				<img src='cid:image_id5' alt='Ícone Placa'>"+
	"			</td>" +
	"		    <td style=' font-size: 15px;' >" +
	"				<strong>Placa</strong> "+placa+""+
	"			</td>" +
	"		</tr>" +
	"		<tr  style='height: 35px;' >" +
	"			<td>" +
	"				<img src='cid:image_id6' alt='Ícone Apólice'>"+
	"			</td>" +
	"		    <td style=' font-size: 15px;' >" +
	"				<strong>Apólice</strong> "+apolice+""+
	"			</td>" +
	"		</tr>" +
	"		<tr  style='height: 35px;'>" +
	"			<td >" +
	"				<img src='cid:image_id7' alt='Ícone Vigência'>"+
	"			</td>" +
	"		    <td style=' font-size: 15px; ' >" +
	"				<strong>Vigência</strong> "+vigencia+""+
	"			</td>" +
	"		</tr>" +
	"	</table>" +
	"<img src='cid:image_id20' alt='down' width='495' heigh='390' >"+
	"</div>"+
	"</center></table>" +
	
	"<tr style='height: 80px;'>"+
	"</tr>"+
	
	"<center><table>" +
	"<tr>"+
	"<td>"+
	"	<table border='0' cellpadding='0' cellspacing='0'>"+
	"		<tr>"+
	"			<td>"+
	"				<table border='0' cellpadding='0' cellspacing='0'>"+
	"<tr style='height: 40px;'>"+
	"</tr>"+
	"					<tr>"+
	"						<p style=' font-size: 17px;' >"+
    "			            Caso alguma informação do seu seguro mudou nos últimos meses, e <br>"+
	"			            você ainda não informou, por favor, entre em contato com a central <br>"+
	"			            Autocompara. Teremos o maior prazer em esclarecer suas duvidas <br>"+
	"			            ou em atualizar as informações."+
	"						</p>"+
	"					</tr>"+
	"				</table>"+
	"			</td>"+
	"		</tr>"+
	"	</table>"+
	"</td>"+
	"</tr>"+
	"</center></table>" +
	
	"<div>"+
	"<img src='cid:image_id18' alt='Lelefones' width='600' heigh='500' >"+
	"</div>"+
	
	"<center><table  >" +
	"<div>"+
	"<tr>" +
	"<td bgcolor='#e8e8e8'  >" +
	"	<table border='0' cellpadding='0' cellspacing='0'>" +
	"		    <td style=' font-size: 30px; color: #ec0000'  align='left' >" +
	"				<strong>Fale com a Seguradora</strong><br><br>"+
	"			</td>" +
	"	</table>" +
	"	<table border='0' cellpadding='0' cellspacing='0'>"+
	"		<tr>"+
	"			<td>"+
	"				<table border='0' cellpadding='0' cellspacing='0'>"+
	"					<tr>"+
	
	"						<td  style='width: 200px;'>"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"								<tr>"+
	"									<td >"+
	"			                            <img src='cid:image_id9' alt='Título'>"+
	"									</td>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	
	"						<td valign='bottom' style='width: 219px;' >"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> Central de atendimento</strong>"+
	"									</td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Linha Direta Allianz Seguros"+
	"									</td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Capitais e regiões metropolitanas"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr>"+
	"                               <td style='color: #282828; font-size: 22px; text-align: left;'>"+
	"				                      <strong> 4090-1110</strong><br>"+
	"									  </td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Demais localidades"+
	"									</td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> 0800 777 7243</strong><br>"+
	"									</td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Atendimento WhatsApp"+
	"								</td>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> (11) 4090 1110</strong>"+
	"				                      ______________________<br>"+
	"									</td>"+
	"								</tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> Portal de atendimento</strong>"+
	"									</td>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Acesse o portal pelo link abaixo<br>"+
	"				                      ou pelo QR code.<br>"+
	"								</td>"+
	
	"								</tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Acesse"+
	"								</td>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      <a href='https://www.allianz.com.br' style='color: #ec0000; text-decoration: underline;'><strong>www.allianz.com.br</strong></a>'<br>"+
	"				                      _____________________________________<br>"+
	"								</td>"+
	"								</tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> Aplicativo da seguradora</strong>"+
	"									</td>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Baixe nosso App pelo link abaixo<br>"+
	"				                      ou acesse o QR code.<br>"+
	"								</td>"+
	"								</tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      <a href='https://apps.apple.com/br/app/allianz-cliente/id1554862261?mt=8' style='color: #ec0000; text-decoration: underline;'><strong>Baixar aplicativo</strong></a><br>"+
    "				                      _____________________________________<br>"+
	"								</td>"+
	"								</tr>"+
	"                               <tr style='height: 10px;'>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	"                       <td valign='bottom'>"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"								<tr>"+
	"									<td>"+
	"		                              <img src='cid:image_id10' alt='Imagem 1' align='middle' >"+
	"									</td>"+
	"								</tr>"+
	"                               <tr style='height: 130px;'>"+
	"								</tr>"+
	"								<tr>"+
	"									<td>"+
	"		                              <img src='cid:image_id11' alt='Imagem 1' align='middle' >"+
	"									</td>"+
	"								</tr>"+
	"                               <tr style='height: 35px;'>"+
	"								</tr>"+
	"								<tr>"+
	"									<td>"+
	"		                              <img src='cid:image_id12' alt='Imagem 1' align='middle' >"+
	"									</td>"+
	"								</tr>"+
	"                               <tr style='height: 20px;'>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	"                       <td valign='bottom' style='width: 10px;' >"+
	"						</td>"+
	"					</tr>"+
	"				</table>"+
	"			</td>"+
	"		</tr>"+
	"	</table>"+
	"</td>"+
	"</tr>"+
	"</table>"+
	"</td>" +
	"<tr>" +
	"</div>"+
	"</center></table>" +
	
	"<tr style='height: 30px;'>"+
	"</tr>"+
	
	"<div>"+
	"<img src='cid:image_id13' alt='Logo 2'>"+
    "</div>"+
	"<center><table  style='line-height: 1; letter-spacing: -0.3px; font-size: 13px; margin: 30px 0px; color: #282828; '>" +
	"<div class='space-component' >"+
	"<div style='width: 85%; '>"+
	"	<div style='line-height: 1; letter-spacing: -0.3px; font-size: 13px; margin: 30px 0px; color: #282828; justify-content: center; '>"+
	"		<p>"+
	"			O serviço Santander Autocompara é prestado pela Santander Corretora de Seguros,<br>"+
	"			Investimentos e Serviços S.A., registro SUSEP 10.2041572.9 e CNPJ 04.270.778/0001-71. O<br>"+
	"			registro dos planos na SUSEP não implica, por parte da Autarquia, incentivo ou recomendação<br>"+
	"			a sua comercialização."+
	"		</p>"+
	"	</div>"+
	"</div>"+
	"</div>"+
	"</center></table>" +
	"<center><table  style='line-height: 1; letter-spacing: -0.3px; font-size: 11px; margin: 7px 0px; color: #282828; '>" +
	"<div class='space-container' '>"+
	"<div style='width: 85%; '>"+
	"	<div style='line-height: 1; letter-spacing: -0.3px; font-size: 11px; margin: 7px 0px; color: #282828; text-align: left;'  >"+
	"		<p>"+
	"			Caro cliente, este é um e-mail automático, pedimos a gentileza de não respondê-lo, contate nossa central de<br>"+
	"			atendimento para mais informações. Você está recebendo este e-mail porque fez uma simulação no nosso site<br>"+
	"			e autorizou receber comunicações, de acordo com nossos Termos de Uso.<br>"+
	"			<br><br>"+
	"		</p>"+
	"	</div>"+
	"</div>"+
	"</div>"+
	"</center></table>" +
	"<center><table  >" +
	"<div>"+
	"<tr>" +
	"<td>" +
	"	<table border='0' cellpadding='0' cellspacing='0'>" +
	"		<tr>" +
	"			<td width='75' style='line-height: 1.2; letter-spacing: -0.1px; font-size: 19px;  margin: 7px 0px; color: #282828'>" +
	"				Siga-nos"+
	"			</td>" +
	"		   <td width='30'>" +
	"				<a href='https://www.facebook.com/allianzbrasil/' target='_blank'>" +
	"				<img src='cid:image_id14' alt='Facebook'></a>" +
	"			</td>" +
	"		   <td width='30'>" +
	"				<a href='https://www.linkedin.com/company/allianzbrasil/mycompany/verification/'>" +
	"					<img src='cid:image_id15' alt='LinkedIn'></a>" +
	"				</td>" +
	"		   <td>" +
	"				<a href='https://www.instagram.com/allianzbrasil/'>" +
	"					<img src='cid:image_id16' alt='Instagram'></a>" +
	"				</td>" +
	"		</tr>" +
	"	</table>" +
	"</td>" +
    "</tr>" +
    "<tr>" +
	"</div>"+
	"</center></table>" +
    "<div>"+
	"  <img src='cid:image_id17' alt='Linha'>"+
    "</div>"+
    "</div>"+
    "</div>"+
    "</main>"+
	"</body>"+
	"</center></table>" +
	"</html>"
		
	return sb;
	}
	
	
	
	/**
	 * Aviso de Renovação Apólice Allianz/Santander
	 * IBLBDM-74087
	 * @return
	 */
	public static String avisoRenovacaoAllianzSantander15A112025(String segurado,  String apolice, String vigencia, String placa, String veiculo  ){
	
	String sb = "<html>" +
	"<head>" +
	"<center><table>" +
	"<meta charset='UTF-8'>" +
	"<link rel='stylesheet' href='style.css'>"+
	"<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
	"<title>Allianz</title>" +
	"</head>"+
	"<body>"+
	"<main class='container'>"+
	"<div class='center'>"+
	"<div class='container'>"+
	"<div class='center'>"+
	"<header class='header'>"+
	"		<img src='cid:image_id1' alt='Logo Allianz'>"+
	"</header>"+
	"</div>"+
	"<tr>"+
	"<td>"+
	"	<table border='0' cellpadding='0' cellspacing='0'>"+
	"		<tr>"+
	"			<td>"+
	"				<table border='0' cellpadding='0' cellspacing='0'>"+
	"					<tr>"+
	"						<td>"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"									<td style='color: #282828; font-size: 24px; text-align: center;'>"+
	"				                      <strong> Olá, "+segurado+"</strong><br>"+
	"                                     Temos uma informação <br> importante<br>"+
	"                                     sobre o seu seguro."+
	"									</td>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	"						<td valign='bottom'>"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"								<tr>"+
	"									<td>"+
	"		                              <img src='cid:image_id3' alt='Imagem 1'>"+
	"									</td>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	"					</tr>"+
	"				</table>"+
	"			</td>"+
	"		</tr>"+
	"	</table>"+
	"</td>"+
	"</tr>"+
	
	"<tr style='height: 30px;'>"+
	"</tr>"+
	
	"<center><table>" +
	"<tr>"+
	"<td>"+
	"	<table border='0' cellpadding='0' cellspacing='0'>"+
	"		<tr>"+
	"			<td>"+
	"				<table border='0' cellpadding='0' cellspacing='0'>"+
	"					<tr>"+
	"						<p style=' font-size: 18px;' >"+
	"                           Informamos que o seguro do seu veículo com a Allianz Seguros <br>"+
	"							<strong>será renovado</strong> na próxima data de vencimento. <br><br>"+
    "                            Nas próximas semanas, enviaremos a apólice renovada para sua <br>"+
	"							revisão. Segue abaixo um resumo das principais alterações entre <br>"+
	"							sua apólice atual e a renovada, refletindo as atualizações em nosso <br>"+
	"							produto. <br><br>"+
	"							<strong> Coberturas:</strong><br>"+
	"							Sim, agora inclui a nova cobertura de Gastos com Defesa. <br><br>"+
	"							<strong>Assistência 24 Horas: </strong><br>"+
	"							Condições mantidas conforme a apólice anterior.<br><br>"+
	"							<strong>Franquias:</strong><br>"+
	"							Sim, tipo de franquia mantido alinhado ao valor de mercado do veículo. <br><br>"+
	"						</p>"+
	"					</tr>"+
	"<tr style='height: 40px;'>"+
	"</tr>"+
	"				</table>"+
	"			</td>"+
	"		</tr>"+
	"	</table>"+
	"</td>"+
	"</tr>"+
	"</center></table>" +
	
	"<tr style='height: 80px;'>"+
	"</tr>"+
		
	"<center><table border='0' cellpadding='0' cellspacing='0' class='width-1'>" +
	"<div>"+
	"<img src='cid:image_id19' alt='top' width='495' heigh='390' style='display: block;' >"+
	"	<table  bgcolor='#e8e8e8' style='margin: 0; padding: 0; width: 495' >" +
	"		<tr style='height: 35px;' >" +
	"			<td>" +
	"				<img src='cid:image_id4' alt='Ícone Veículo'>"+
	"			</td>" +
	"		    <td style=' font-size: 15px;' >" +
	"				<strong>Veículo</strong> "+veiculo+""+
	"			</td>" +
	"		</tr>" +
	"		<tr style='height: 35px;' >" +
	"			<td>" +
	"				<img src='cid:image_id5' alt='Ícone Placa'>"+
	"			</td>" +
	"		    <td style=' font-size: 15px;' >" +
	"				<strong>Placa</strong> "+placa+""+
	"			</td>" +
	"		</tr>" +
	"		<tr  style='height: 35px;' >" +
	"			<td>" +
	"				<img src='cid:image_id6' alt='Ícone Apólice'>"+
	"			</td>" +
	"		    <td style=' font-size: 15px;' >" +
	"				<strong>Apólice</strong> "+apolice+""+
	"			</td>" +
	"		</tr>" +
	"		<tr  style='height: 35px;'>" +
	"			<td >" +
	"				<img src='cid:image_id7' alt='Ícone Vigência'>"+
	"			</td>" +
	"		    <td style=' font-size: 15px; ' >" +
	"				<strong>Vigência</strong> "+vigencia+""+
	"			</td>" +
	"		</tr>" +
	"	</table>" +
	"<img src='cid:image_id20' alt='down' width='495' heigh='390' >"+
	"</div>"+
	"</center></table>" +
	
	"<tr style='height: 80px;'>"+
	"</tr>"+
	
	"<center><table>" +
	"<tr>"+
	"<td>"+
	"	<table border='0' cellpadding='0' cellspacing='0'>"+
	"		<tr>"+
	"			<td>"+
	"				<table border='0' cellpadding='0' cellspacing='0'>"+
	"<tr style='height: 40px;'>"+
	"</tr>"+
	"					<tr>"+
	"			            <p>Caso alguma informação do seu seguro mudou nos últimos meses, e <br>"+
	"			            você ainda não informou, por favor, entre em contato com a central <br>"+
	"			            Autocompara. Teremos o maior prazer em esclarecer suas duvidas <br>"+
	"			            ou em atualizar as informações.<br><br>"+
    "                       Caso não esteja de acordo com a contratação, por favor, entrar <br>"+
	"			            em contato com o <strong>Serviço de Atendimento ao Cliente Allianz,</strong><br>"+
	"			            <strong>através dos telefones</strong></p>"+
    "	                    <p><strong>08000 115 215</strong><br>"+
    "                       <strong>08000 121 239</strong> "+
    "                       <span class='texto-extra-small'>(exclusivo para portadores de limitação auditiva e de fala)</span></p>"+
	"					</tr>"+
	"				</table>"+
	"			</td>"+
	"		</tr>"+
	"	</table>"+
	"</td>"+
	"</tr>"+
	"</center></table>" +
	
	"<div>"+
	"<img src='cid:image_id18' alt='Lelefones' width='600' heigh='500' >"+
	"</div>"+
	
	"<center><table  >" +
	"<div>"+
	"<tr>" +
	"<td bgcolor='#e8e8e8'  >" +
	"	<table border='0' cellpadding='0' cellspacing='0'>" +
	"		    <td style=' font-size: 30px; color: #ec0000'  align='left' >" +
	"				<strong>Fale com a Seguradora</strong><br><br>"+
	"			</td>" +
	"	</table>" +
	"	<table border='0' cellpadding='0' cellspacing='0'>"+
	"		<tr>"+
	"			<td>"+
	"				<table border='0' cellpadding='0' cellspacing='0'>"+
	"					<tr>"+
	
	"						<td  style='width: 200px;'>"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"								<tr>"+
	"									<td >"+
	"			                            <img src='cid:image_id9' alt='Título'>"+
	"									</td>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	
	"						<td valign='bottom' style='width: 219px;' >"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> Central de atendimento</strong>"+
	"									</td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Linha Direta Allianz Seguros"+
	"									</td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Capitais e regiões metropolitanas"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr>"+
	"                               <td style='color: #282828; font-size: 22px; text-align: left;'>"+
	"				                      <strong> 4090-1110</strong><br>"+
	"									  </td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Demais localidades"+
	"									</td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> 0800 777 7243</strong><br>"+
	"									</td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Atendimento WhatsApp"+
	"								</td>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> (11) 4090 1110</strong>"+
	"				                      ______________________<br>"+
	"									</td>"+
	"								</tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> Portal de atendimento</strong>"+
	"									</td>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Acesse o portal pelo link abaixo<br>"+
	"				                      ou pelo QR code.<br>"+
	"								</td>"+
	
	"								</tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Acesse"+
	"								</td>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      <a href='https://www.allianz.com.br' style='color: #ec0000; text-decoration: underline;'><strong>www.allianz.com.br</strong></a>'<br>"+
	"				                      _____________________________________<br>"+
	"								</td>"+
	"								</tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> Aplicativo da seguradora</strong>"+
	"									</td>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Baixe nosso App pelo link abaixo<br>"+
	"				                      ou acesse o QR code.<br>"+
	"								</td>"+
	"								</tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      <a href='https://apps.apple.com/br/app/allianz-cliente/id1554862261?mt=8' style='color: #ec0000; text-decoration: underline;'><strong>Baixar aplicativo</strong></a><br>"+
	"				                      _____________________________________<br>"+
	"								</td>"+
	"								</tr>"+
	"                               <tr style='height: 10px;'>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	"                       <td valign='bottom'>"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"								<tr>"+
	"									<td>"+
	"		                              <img src='cid:image_id10' alt='Imagem 1' align='middle' >"+
	"									</td>"+
	"								</tr>"+
	"                               <tr style='height: 130px;'>"+
	"								</tr>"+
	"								<tr>"+
	"									<td>"+
	"		                              <img src='cid:image_id11' alt='Imagem 1' align='middle' >"+
	"									</td>"+
	"								</tr>"+
	"                               <tr style='height: 35px;'>"+
	"								</tr>"+
	"								<tr>"+
	"									<td>"+
	"		                              <img src='cid:image_id12' alt='Imagem 1' align='middle' >"+
	"									</td>"+
	"								</tr>"+
	"                               <tr style='height: 20px;'>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	"                       <td valign='bottom' style='width: 10px;' >"+
	"						</td>"+
	"					</tr>"+
	"				</table>"+
	"			</td>"+
	"		</tr>"+
	"	</table>"+
	"</td>"+
	"</tr>"+
	"</table>"+
	"</td>" +
	"<tr>" +
	"</div>"+
	"</center></table>" +
	
	"<tr style='height: 30px;'>"+
	"</tr>"+
	
	"<div>"+
	"<img src='cid:image_id13' alt='Logo 2'>"+
	"</div>"+
	
	
	"<center><table  style='line-height: 1; letter-spacing: -0.3px; font-size: 14px; margin: 30px 0px; color: #282828; '>" +
	"<div class='space-component' >"+
	"<div style='width: 80%; '>"+
	"	<div style='line-height: 1; letter-spacing: -0.1px; font-size: 13px; margin: 20px 0px; color: #282828; justify-content: center; '>"+
	"		<p>"+
	"			O serviço Santander Autocompara é prestado pela Santander Corretora de Seguros,<br>"+
	"			Investimentos e Serviços S.A., registro SUSEP 10.2041572.9 e CNPJ 04.270.778/0001-71. O<br>"+
	"			registro dos planos na SUSEP não implica, por parte da Autarquia, incentivo ou recomendação<br>"+
	"			a sua comercialização."+
	"		</p>"+
	"	</div>"+
	"</div>"+
	"</div>"+
	"</center></table>" +
	
	
	
	"<table width='520' border='0' cellpadding='0' cellspacing='0'>"+
	"<tr>"+
	"       <td style='line-height: 1; letter-spacing: -0.1px; font-size: 11px; margin: 20px 0px; color: #282828; justify-content: center;'>"+
	"			Caro cliente, este é um e-mail automático, pedimos a gentileza de não respondê-lo, contate nossa"+
    "           central de atendimento para mais informações. Você está recebendo este e-mail porque fez uma"+
    "           simulação no nosso site e autorizou receber comunicações, de acordo com nossos Termos de Uso."+
	"			Allianz Seguros S.A., CNPJ: 61.573.796/0001<a href='#' style='color: #414141; text-decoration: none;'>-66, Rua Eugênio de Medeiros no 303,</a> ands. 1-parte, 2 ao"+
    "           9, 15 e 16, Pinheiros, São Paulo - CEP: 05425-000. Processo SUSEP 15414.002216/2004-57,"+
    "           15414.002901.2007/26, 15414.900597/2017-56, 15414.004661/2004-51, 15414.004732/2004-16,"+
    "           15414.900340/2018-85, 15414.100898/2004-62, 15414.001289.2007-74.. Consulte as Condições Gerais,"+
    "           assim como os planos de assistência 24h, serviços e abrangência do seguro no site  carência"+
    "           <a href='https://www.allianz.com.br' style='color: #414141; text-decoration: none;'>www.allianz.com.br.</a> O registro do produto é automático e não representa aprovação ou recomendação"+
	"           por parte da SUSEP. A aceitação do seguro está sujeita a análise do risco. Algumas coberturas e"+  
    "           erviços possuem restrições, e limites diferenciados. Para saber todos os detalhes e"+
    "           conhecer a lista completa consulte o Manual do Segurado/Condições Gerais. Linha Direta Allianz"+
	"           Corretor: 4090 1120 (Capitais e Regiões Metropolitanas), 0800 777 8243 (demais regiões). SAC (24 horas):"+
    "           08000 115 215. Ouvidoria Allianz Seguros: 0800 771 3313. Atendimento à pessoa com"+
    "           deficiência auditiva ou de fala: 08000 121 239. A Allianz Seguros respeita a sua privacidade e é contra o"+
    "           spam na rede. Para mais informações, acesse a Política de Privacidade de Dados da Allianz, disponível"+
    "           em: <a href='https://www.allianz.com.br/politica-de-privacidade' style='color: #414141; text-decoration: none;'>www.allianz.com.br/politica-de-privacidade.</a>"+ 
	"       </td>"+
	"</tr>"+
	"<tr>"+
	"	<td style='height: 26px;'>"+
	"		&nbsp;"+
	"	</td>"+
	"</tr>"+
	"</table>" +
	
	
	

	
	
	
	"<center><table  >" +
	"<div>"+
	"<tr>" +
	"<td>" +
	"	<table border='0' cellpadding='0' cellspacing='0'>" +
	"		<tr>" +
	"			<td width='75' style='line-height: 1.2; letter-spacing: -0.1px; font-size: 19px;  margin: 7px 0px; color: #282828'>" +
	"				Siga-nos"+
	"			</td>" +
	"		   <td width='30'>" +
	"				<a href='https://www.facebook.com/allianzbrasil/' target='_blank'>" +
	"				<img src='cid:image_id14' alt='Facebook'></a>" +
	"			</td>" +
	"		   <td width='30'>" +
	"				<a href='https://www.linkedin.com/company/allianzbrasil/mycompany/verification/'>" +
	"					<img src='cid:image_id15' alt='LinkedIn'></a>" +
	"				</td>" +
	"		   <td>" +
	"				<a href='https://www.instagram.com/allianzbrasil/'>" +
	"					<img src='cid:image_id16' alt='Instagram'></a>" +
	"				</td>" +
	"		</tr>" +
	"	</table>" +
	"</td>" +
	"</tr>" +
	"<tr>" +
	"</div>"+
	"</center></table>" +
	
	
	"<div>"+
	"  <img src='cid:image_id17' alt='Linha'>"+
	"</div>"+
	"</div>"+
	"</div>"+
	"</main>"+
	"</body>"+
	"</center></table>" +
	"</html>"
		
	return sb;
	}
	
	
	/**
	 * Aviso de Renovação Apólice Allianz/Santander
	 * IBLBDM-74087
	 * @return
	 */
	public static String avisoNaoRenovacaoAllianzSantander112025(String segurado,  String apolice, String vigencia, String placa, String veiculo  ){
	
	String sb = "<html>" +
	"<head>" +
	"<center><table>" +
	"<meta charset='UTF-8'>" +
	"<link rel='stylesheet' href='style.css'>"+
	"<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
	"<title>Allianz</title>" +
	"</head>"+
	"<body>"+
	"<main class='container'>"+
	"<div class='center'>"+
	"<div class='container'>"+
	"<div class='center'>"+
	"<header class='header'>"+
	"		<img src='cid:image_id1' alt='Logo Allianz'>"+
	"</header>"+
	"</div>"+
	"<tr>"+
	"<td>"+
	"	<table border='0' cellpadding='0' cellspacing='0'>"+
	"		<tr>"+
	"			<td>"+
	"				<table border='0' cellpadding='0' cellspacing='0'>"+
	"					<tr>"+
	"						<td>"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"									<td style='color: #282828; font-size: 24px; text-align: center;'>"+
	"				                      <strong> Olá, "+segurado+"</strong><br>"+
	"                                     Temos uma informação <br> importante<br>"+
	"                                     sobre o seu seguro."+
	"									</td>"+
	"								</tr>"+
	"								<tr>"+
	"									<td >"+
//	"			                            <img src='cid:image_id2' alt='Título'>"+
	"									</td>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	"						<td valign='bottom'>"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"								<tr>"+
	"									<td>"+
	"		                              <img src='cid:image_id3' alt='Imagem 1'>"+
	"									</td>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	"					</tr>"+
	"				</table>"+
	"			</td>"+
	"		</tr>"+
	"	</table>"+
	"</td>"+
	"</tr>"+
	
	"<tr style='height: 30px;'>"+
	"</tr>"+
	
	"<center><table>" +
	"<tr>"+
	"<td>"+
	"	<table border='0' cellpadding='0' cellspacing='0'>"+
	"		<tr>"+
	"			<td>"+
	"				<table border='0' cellpadding='0' cellspacing='0'>"+
	"					<tr>"+
	"						<p style=' font-size: 18px;' >"+
	"                           Informamos que o seguro do seu veículo com a Allianz Seguros<br>"+
	"							<strong>não será renovado</strong> na próxima data de vencimento.<br><br>"+
    "                           Entendemos a importância de manter sua proteção e, por isso, já<br>"+
	"							acionamos o Autocompara para auxiliá-lo na busca por novas<br>"+
	"							opções em outras seguradoras, garantindo uma transição<br>"+
	"							tranquila."+
	"						</p>"+
	"					</tr>"+
	"<tr style='height: 40px;'>"+
	"</tr>"+
	"				</table>"+
	"			</td>"+
	"		</tr>"+
	"	</table>"+
	"</td>"+
	"</tr>"+
	"</center></table>" +
	
	"<tr style='height: 80px;'>"+
	"</tr>"+
		
	"<center><table border='0' cellpadding='0' cellspacing='0' class='width-1'>" +
	"<div>"+
	"<img src='cid:image_id19' alt='top' width='495' heigh='390' style='display: block;' >"+
	"	<table  bgcolor='#e8e8e8' style='margin: 0; padding: 0; width: 495' >" +
	"		<tr style='height: 35px;' >" +
	"			<td>" +
	"				<img src='cid:image_id4' alt='Ícone Veículo'>"+
	"			</td>" +
	"		    <td style=' font-size: 15px;' >" +
	"				<strong>Veículo</strong> "+veiculo+""+
	"			</td>" +
	"		</tr>" +
	"		<tr style='height: 35px;' >" +
	"			<td>" +
	"				<img src='cid:image_id5' alt='Ícone Placa'>"+
	"			</td>" +
	"		    <td style=' font-size: 15px;' >" +
	"				<strong>Placa</strong> "+placa+""+
	"			</td>" +
	"		</tr>" +
	"		<tr  style='height: 35px;' >" +
	"			<td>" +
	"				<img src='cid:image_id6' alt='Ícone Apólice'>"+
	"			</td>" +
	"		    <td style=' font-size: 15px;' >" +
	"				<strong>Apólice</strong> "+apolice+""+
	"			</td>" +
	"		</tr>" +
	"		<tr  style='height: 35px;'>" +
	"			<td >" +
	"				<img src='cid:image_id7' alt='Ícone Vigência'>"+
	"			</td>" +
	"		    <td style=' font-size: 15px; ' >" +
	"				<strong>Vigência</strong> "+vigencia+""+
	"			</td>" +
	"		</tr>" +
	"	</table>" +
	"<img src='cid:image_id20' alt='down' width='495' heigh='390' >"+
	"</div>"+
	"</center></table>" +
	
	"<tr style='height: 80px;'>"+
	"</tr>"+
	
	"<center><table>" +
	"<tr>"+
	"<td>"+
	"	<table border='0' cellpadding='0' cellspacing='0'>"+
	"		<tr>"+
	"			<td>"+
	"				<table border='0' cellpadding='0' cellspacing='0'>"+
	"<tr style='height: 40px;'>"+
	"</tr>"+
	"					<tr>"+
	"						<p style=' font-size: 17px;' >"+
	"			            Caso tenha alguma dúvida, entre em contato com o Autocompara.<br>"+
	"			            Obrigado por escolher a Allianz e esperamos atender você em<br>"+
	"			            uma próxima oportunidade.<br>"+
	"					</tr>"+
	"				</table>"+
	"			</td>"+
	"		</tr>"+
	"	</table>"+
	"</td>"+
	"</tr>"+
	"</center></table>" +
	
	"<div>"+
	"<img src='cid:image_id18' alt='Lelefones' width='600' heigh='500' >"+
	"</div>"+
	
	"<center><table  >" +
	"<div>"+
	"<tr>" +
	"<td bgcolor='#e8e8e8'  >" +
	"	<table border='0' cellpadding='0' cellspacing='0'>" +
	"		    <td style=' font-size: 30px; color: #ec0000'  align='left' >" +
	"				<strong>Fale com a Seguradora</strong><br><br>"+
	"			</td>" +
	"	</table>" +
	"	<table border='0' cellpadding='0' cellspacing='0'>"+
	"		<tr>"+
	"			<td>"+
	"				<table border='0' cellpadding='0' cellspacing='0'>"+
	"					<tr>"+
	
	"						<td  style='width: 200px;'>"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"								<tr>"+
	"									<td >"+
	"			                            <img src='cid:image_id9' alt='Título'>"+
	"									</td>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	
	"						<td valign='bottom' style='width: 219px;' >"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> Central de atendimento</strong>"+
	"									</td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Linha Direta Allianz Seguros"+
	"									</td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Capitais e regiões metropolitanas"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr>"+
	"                               <td style='color: #282828; font-size: 22px; text-align: left;'>"+
	"				                      <strong> 4090-1110</strong><br>"+
	"									  </td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Demais localidades"+
	"									</td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> 0800 777 7243</strong><br>"+
	"									</td>"+
	"								</td>"+
	"                               </tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Atendimento WhatsApp"+
	"								</td>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> (11) 4090 1110</strong>"+
	"				                      ______________________<br>"+
	"									</td>"+
	"								</tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> Portal de atendimento</strong>"+
	"									</td>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Acesse o portal pelo link abaixo<br>"+
	"				                      ou pelo QR code.<br>"+
	"								</td>"+
	
	"								</tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Acesse"+
	"								</td>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      <a href='https://www.allianz.com.br' style='color: #ec0000; text-decoration: underline;'><strong>www.allianz.com.br</strong></a>'<br>"+
	"				                      _____________________________________<br>"+
	"								</td>"+
	"								</tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 20px; text-align: left;'>"+
	"				                      <strong> Aplicativo da seguradora</strong>"+
	"									</td>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      Baixe nosso App pelo link abaixo<br>"+
	"				                      ou acesse o QR code.<br>"+
	"								</td>"+
	"								</tr>"+
	"                               <tr style='height: 12px;'>"+
	"								</tr>"+
	"                               <tr>"+
	"								<td style='color: #282828; font-size: 12px; text-align: left;'>"+
	"				                      <a href='https://apps.apple.com/br/app/allianz-cliente/id1554862261?mt=8' style='color: #ec0000; text-decoration: underline;'><strong>Baixar aplicativo</strong></a><br>"+
	"				                      _____________________________________<br>"+
	"								</td>"+
	"								</tr>"+
	"                               <tr style='height: 10px;'>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	"                       <td valign='bottom'>"+
	"							<table border='0' cellpadding='0' cellspacing='0'>"+
	"								<tr>"+
	"									<td>"+
	"		                              <img src='cid:image_id10' alt='Imagem 1' align='middle' >"+
	"									</td>"+
	"								</tr>"+
	"                               <tr style='height: 130px;'>"+
	"								</tr>"+
	"								<tr>"+
	"									<td>"+
	"		                              <img src='cid:image_id11' alt='Imagem 1' align='middle' >"+
	"									</td>"+
	"								</tr>"+
	"                               <tr style='height: 35px;'>"+
	"								</tr>"+
	"								<tr>"+
	"									<td>"+
	"		                              <img src='cid:image_id12' alt='Imagem 1' align='middle' >"+
	"									</td>"+
	"								</tr>"+
	"                               <tr style='height: 20px;'>"+
	"								</tr>"+
	"							</table>"+
	"						</td>"+
	"                       <td valign='bottom' style='width: 10px;' >"+
	"						</td>"+
	"					</tr>"+
	"				</table>"+
	"			</td>"+
	"		</tr>"+
	"	</table>"+
	"</td>"+
	"</tr>"+
	"</table>"+
	"</td>" +
	"<tr>" +
	"</div>"+
	"</center></table>" +
	
	"<tr style='height: 30px;'>"+
	"</tr>"+
	
	"<div>"+
	"<img src='cid:image_id13' alt='Logo 2'>"+
	"</div>"+
	
	
	"<center><table  style='line-height: 1; letter-spacing: -0.3px; font-size: 14px; margin: 30px 0px; color: #282828; '>" +
	"<div class='space-component' >"+
	"<div style='width: 80%; '>"+
	"	<div style='line-height: 1; letter-spacing: -0.1px; font-size: 13px; margin: 20px 0px; color: #282828; justify-content: center; '>"+
	"		<p>"+
	"			O serviço Santander Autocompara é prestado pela Santander Corretora de Seguros,<br>"+
	"			Investimentos e Serviços S.A., registro SUSEP 10.2041572.9 e CNPJ 04.270.778/0001-71.<br>"+
	"			O registro dos planos na SUSEP não implica, por parte da Autarquia, incentivo ou recomendação<br>"+
	"			a sua comercialização."+
	"		</p>"+
	"	</div>"+
	"</div>"+
	"</div>"+
	"</center></table>" +
	
	"<table width='520' border='0' cellpadding='0' cellspacing='0'>"+
	"<tr>"+
	"       <td style='line-height: 1; letter-spacing: -0.1px; font-size: 11px; margin: 20px 0px; color: #282828; justify-content: center;'>"+
    "           Caro cliente, este é um e-mail automático, pedimos a gentileza de não respondê-lo, contate nossa central de"+ 
    "           atendimento para mais informações. Você está recebendo este e-mail porque fez uma simulação no nosso site "+ 
    "           e autorizou receber comunicações, de acordo com nossos Termos de Uso.Allianz Seguros S.A., CNPJ:"+ 
    "           61.573.796/0001-66, Rua Eugênio de Medeiros no 303,ands. 1-parte, 2 ao 9, 15 e 16, Pinheiros, São Paulo -"+ 
    "           CEP: 05425-000. Processo SUSEP 15414.002216/2004-57, 15414.002901.2007/26, 15414.900597/2017-56,"+ 
    "           15414.004661/2004-51, 15414.004732/2004-16, 15414.900340/2018-85, 15414.100898/2004-62,"+ 
    "           15414.001289.2007-74. Consulte as Condições Gerais, assim como os planos de assistência 24h, serviços e"+ 
    "           abrangência do seguro no site <a href='https://www.allianz.com.br' style='color: #414141; text-decoration: none;'>www.allianz.com.br.</a> O registro do produto é automático e não representa"+ 
	"			aprovação ou recomendação por parte da SUSEP. A aceitação do seguro está sujeita a análise do risco."+ 
    "           Algumas coberturas e serviços possuem restrições, carência e limites diferenciados. Para saber todos os"+ 
    "           detalhes e conhecer a lista completa consulte o Manual do Segurado/Condições Gerais. Linha Direta Allianz"+ 
    "           Corretor: 4090 1120 (Capitais e Regiões Metropolitanas),0800 777 8243 (demais regiões). SAC (24 horas):"+
    "           08000 115 215. Ouvidoria Allianz Seguros: 0800 771 3313. Atendimento à pessoa com deficiência auditiva ou"+ 
    "           de fala: 08000 121 239. A Allianz Seguros respeita a sua privacidade e é contra o spam na rede. Para mais"+ 
    "           informações, acesse a Política de Privacidade de Dados da Allianz, disponível em: <a href='https://www.allianz.com.br/politica-de-privacidade' style='color: #414141; text-decoration: none;'>www.allianz.com.br/politica-"+
    "           de-privacidade.</a>"+
	"       </td>"+
	"</tr>"+
	"<tr>"+
	"	<td style='height: 26px;'>"+
	"		&nbsp;"+
	"	</td>"+
	"</tr>"+
	"</table>" +
	"		</p>"+
	"	</div>"+
	"</div>"+
	"</div>"+
	"</center></table>" +
	
	
	

	
	
	
	"<center><table  >" +
	"<div>"+
	"<tr>" +
	"<td>" +
	"	<table border='0' cellpadding='0' cellspacing='0'>" +
	"		<tr>" +
	"			<td width='75' style='line-height: 1.2; letter-spacing: -0.1px; font-size: 19px;  margin: 7px 0px; color: #282828'>" +
	"				Siga-nos"+
	"			</td>" +
	"		   <td width='30'>" +
	"				<a href='https://www.facebook.com/allianzbrasil/' target='_blank'>" +
	"				<img src='cid:image_id14' alt='Facebook'></a>" +
	"			</td>" +
	"		   <td width='30'>" +
	"				<a href='https://www.linkedin.com/company/allianzbrasil/mycompany/verification/'>" +
	"					<img src='cid:image_id15' alt='LinkedIn'></a>" +
	"				</td>" +
	"		   <td>" +
	"				<a href='https://www.instagram.com/allianzbrasil/'>" +
	"					<img src='cid:image_id16' alt='Instagram'></a>" +
	"				</td>" +
	"		</tr>" +
	"	</table>" +
	"</td>" +
	"</tr>" +
	"<tr>" +
	"</div>"+
	"</center></table>" +
	
	
	"<div>"+
	"  <img src='cid:image_id17' alt='Linha'>"+
	"</div>"+
	"</div>"+
	"</div>"+
	"</main>"+
	"</body>"+
	"</center></table>" +
	"</html>"
		
	return sb;
	}
	

	/**
	 * Aviso de Renovação Apólice Allianz/Santander
	 * @return
	 */
	public static String avisoRenovacaoAllianzSantander15(String segurado, String apolice, String rodape, String cabecalho, String dataAtual, String tapCap){
        System.out.println("["+tapCap+"]")
		String sb = "<head>" +
				"<center><table>" +
				addImageCabecalho(cabecalho,'80','35')+"<p>"+

				"<tr>" +
				"<td height='224'>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<td width='33'>&nbsp;</td>" +
				"		<td style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 14px; font-weight: 300; letter-spacing: -0.2px; line-height: 25px'>" +
				"			São Paulo, "+dataAtual+" <br>" +
				"			Prezado(a),<strong>"+segurado+"</strong> <br>" +
				"			Verificamos que a vigência do seu seguro de automóvel, apólice <br>" +
				"			<strong>"+apolice+"</strong>, vai encerrar em breve. <br>" +
				"			E para você continuar tranquilo, a <b>Allianz Seguros facilitou a contratação da sua nova <br>" +
				"			apólice</b>, com uma combinação de coberturas ideal para te proteger." +
				"		</td>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
//				"<td>" +
//				"	<table border='0' cellpadding='0' cellspacing='0'>" +
//				"		<td width='11'>&nbsp;</td>" +
//				"		<td width='22' bgcolor='#e3ebaf'>&nbsp;</td>" +
//				"		<td bgcolor='#e3ebaf' height='62' width='556' style='font-family: Arial, Helvetica, SansSerif; color: #003781; font-size: 13px; font-weight: bold; letter-spacing: -0.2px; line-height: 21px'>" +
//				"			Você já sabe, mas vale lembrar, a SulAmérica Auto agora é Allianz!" +
//				"		</td>" +
//				"	</table>" +
//				"</td>" +
				"</tr>" +
				"<tr><td height='30'>&nbsp;</td></tr>" +
				"<tr>" +
				"<td height='277'>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<td width='33'>&nbsp;</td>" +
				"		<td style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 14px; font-weight: 300; letter-spacing: -0.2px; line-height: 25px'>" +
				"			Junto com esta carta você está recebendo a proposta do seguro, que contém as <br>" +
				"			condições sugeridas para sua nova contratação. Não deixe de conferir, algumas <br>" +
				"			podem ter mudado. Confira também as condições gerais do produto no site Allianz. <br>" +
				"			Se você concorda com a proposta, basta aguardar, que em breve também receberá o <br>" +
				"			kit digital com: <br><br>" +
				"			• Sua apólice de seguro; <br><br>" +
				"			• O Cartão do Segurado,  com as principais informações do seu seguro e os canais de <br>" +
				"			atendimento para você falar com a Allianz Seguros em casos de emergência, de <br>" +
				"			solicitação de serviços ou de dúvidas." +
				"		</td>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				"<tr><td height='30'>&nbsp;</td></tr>" +
				"<tr>" +
				"<td>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<td width='33'>&nbsp;</td>" +
				"		<td width='21' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td bgcolor='#f8f4f2' width='506' height='166' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 14px; font-weight: 300; letter-spacing: -0.2px; line-height: 25px'>" +
				"			O Santander AutoCompara informa que a remuneração do Corretor <br>" +
				"			é de "+tapCap+"%, calculada sobre o Prêmio Líquido <br>" +
				"			Total informado na Proposta." +
				"		</td>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				"<tr><td height='30'>&nbsp;</td></tr>" +
				"<tr>" +
				"<td height='70'>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<td width='33'>&nbsp;</td>" +
				"		<td style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 14px; font-weight: 300; letter-spacing: -0.2px; line-height: 25px'>" +
				"			A Allianz Seguros está pronta para atender você em todas as questões relativas ao <br>" +
				"			seu seguro, através dos telefones 08000 115 215 ou 08000 121 239 (exclusivo para <br>" +
				"			portadores de limitação auditiva e de fala).​ <br><br>" +
				"			<b>E o Santander AutoCompara está à disposição caso você queira fazer uma nova <br>" +
				"			cotação, ou alterar suas coberturas e condições.​</b>" +
				"		</td>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				addImageRodape(rodape,'80','35')+
				"<tr>" +
				"<td>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<td width='11'>&nbsp;</td>" +
				"		<td width='22' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='168' height='52' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.4px; line-height: 15px'>" +
				"			CENTRAL DE ATENDIMENTO <br>" +
				"			SANTANDER AUTOCOMPARA: <br><br>" +
				"		</td>" +
				"		<td width='13' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='170' height='52' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.4px; line-height: 15px'>" +
				"			SAC- SERVIÇO DE ATENDIMENTO <br>" +
				"			AO CONSUMIDOR SANTANDER <br>" +
				"			AUTOCOMPARA <br><br>" +
				"		</td>" +
				"		<td width='12' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='192' height='52' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.4px; line-height: 15px'>" +
				"			OUVIDORIA SANTANDER <br>" +
				"			AUTOCOMPARA <br><br>" +
				"		</td>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"	<table border='0' cellpadding'0' cellspacing='0'>" +
				"		<td width='11'>&nbsp;</td>" +
				"		<td width='22' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='168' height='109' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.2px; line-height: 15px'>" +
				"			<b style='font-size: 11px; font-weight: 300; letter-spacing: -0.7px;'>" +
				"				Para cotações, contratações e demais <br>" +
				"				informações, ligue para: <br>" +
				"				<b style='color: #e4003a;'>4004 4495</b> (regiões metropolitanas). <br>" +
				"				<b style='color: #e4003a;'>0800 723 4495</b> (Demais localidades) <br>" +
				"				De segunda a sexta-feira, das 8h às 20h, exceto feriados." +
				"			</b>" +
				"		</td>" +
				"		<td width='13' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='170' height='109' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.2px; line-height: 15px'>" +
				"			<b style='font-size: 11px; font-weight: 300; letter-spacing: -0.7px;'>" +
				"				Reclamações. sugestões e informações <br>" +
				"				gerais:  <b style='color: #e4003a;'>0800 726 7777</b>. Atende também <br>" +
				"				deficientes auditivos e de fala. No <br>" +
				"				exterior, ligue a cobrar para: <br>" +
				"				<b style='color: #e4003a;'> 55 11 3012 3336. </b><br>" +
				"				Atendimento: 24h por dia,  todos os dias." +
				"		</b>" +
				"		</td>" +
				"		<td width='12' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='192' height='109' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.2px; line-height: 15px'>" +
				"			<b style='font-size: 11px; font-weight: 300; letter-spacing: -0.7px;'>" +
				"				Se não ficar satisfeito com a solução <br>" +
				"				apresentada <b style='color: #e4003a;'>0800 726 0322</b>. <br>" +
				"				Atende também deficientes auditivos e <br>" +
				"				de fala. De segunda a sexta-feira, das <br>" +
				"				9h às 18h, exceto feriados." +
				"			</b>" +
				"		</td>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				"<tr><td height='4'></td></tr>" +
				"<tr>"+
				"<td height='170'>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<tr>" +
				"			<td width='34'>&nbsp;</td>" +
				"			<td style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 9px; font-weight: 300; letter-spacing: -0.2px; line-height: 13px '>" +
				"				Seguro intermediado pela Santander Corretora de Seguros, Investimentos e Serviços S.A., CNPJ nº 04.270.778/0001-71, SUSEP<br>" +
				"				202019222 e garantido pela Allianz Brasil Seguradora S.A. O registro deste plano na SUSEP não implica, por parte da autarquia, em  <br>" +
				"				incentivo ou recomendação à sua comercialização. Consulte as condições gerais em sulamericaauto.com.br. Allianz Brasil Seguradora  <br>" +
				"               S.A., nova denominação da Sul América Seguros de Automóveis e Massificados S.A., CNPJ: 32.357.481/0001-83, Avenida República do <br>" +
				"				Chile nº 330, Bloco 1, 24º ao 28º andar, Centro, Rio de Janeiro – RJ. SAC 24 horas: 0800 725 5901. Ouvidoria: 0800 720 9740 e  <br>" +
				"				atendimento à pessoa com deficiência auditiva ou de fala 24 horas: 0800 970 0600. Desde julho de 2020, a Allianz adquiriu a seguradora <br>" +
				"				Sul América Seguros de Automóveis e Massificados S.A.  <br>" +
				"				A Allianz Seguros respeita a sua privacidade e é contra o spam na rede. Para mais informações, acesse a Política de Privacidade de  <br>" +
				"				Dados Allianz, disponível em:  <a href='https://www.allianz.com.br/politica-de-privacidade' style='color: #003781;'>www.allianz.com.br/politica-de-privacidade</a>." +
				"			</td>" +
				"		</tr>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				"</table>" +
				"</center></table>"

		return sb;
	}

	/**
	 * Renovação Apólice Allianz/Santander
	 * @return
	 */
	public static String renovacaoAllianzSantander30(String segurado, String apolice, String rodape, String cabecalho, String dataAtual){
		String sb = "<head>" +
				"<center><table>" +
				addImageCabecalho(cabecalho,'80','35')+"<p>"+
				"<tr>" +
				"<td height='224'>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<td width='33'>&nbsp;</td>" +
				"		<td style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 14px; font-weight: 300; letter-spacing: -0.2px; line-height: 25px'>" +
				"			São Paulo,"+dataAtual+"<br>" +
				"			Prezado(a) <strong>"+segurado+"</strong>, <br>" +
				"			Verificamos que a vigência do seu seguro de automóvel, apólice <strong>"+apolice+"</strong>, <br>" +
				"			vai se encerrar em breve. <br>" +
				"			Para garantir a tranquilidade que você merece, a <b>Allianz Seguros irá facilitar a <br>" +
				"			contratação da nova apólice do seu seguro auto</b>, propondo uma combinação ideal <br>" +
				"			de coberturas para te proteger." +
				"		</td>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
//				"<td>" +
//				"	<table border='0' cellpadding='0' cellspacing='0'>" +
//				"		<td width='11'>&nbsp;</td>" +
//				"		<td width='22' bgcolor='#e3ebaf'>&nbsp;</td>" +
//				"		<td bgcolor='#e3ebaf' height='107' width='556' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 13px; font-weight: 300; letter-spacing: -0.2px; line-height: 21px'>" +
//				"			<b style='color: #003781;'>Você sabia que a SulAmérica Auto e Massificados agora é Allianz?</b> <br>" +
//				"		    E a Allianz é líder global em seguros, <b><i>com mais de 2,7 milhões de veículos segurados no <br>" +
//				"			Brasil </i></b>, e está super preparada para proteger o seu veículo." +
//				"		</td>" +
//				"	</table>" +
//				"</td>" +
//				"</tr>" +
				"<tr>" +
				"<td height='536'>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<td width='33'>&nbsp;</td>" +
				"		<td style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 14px; font-weight: 300; letter-spacing: -0.2px; line-height: 25px'>" +
				"			Os valores e condições referentes à sua nova apólice estarão disponíveis 15 dias antes​ <br>" +
				"			do vencimento da sua apólice atual. Caso não esteja de acordo com a contratação,<br>" +
				"			favor entrar em contato com o Serviço de Atendimento ao Cliente Allianz, através dos telefones<br>" +
				"			08000 115 215 ou 08000 121 239 (exclusivo para portadores de limitação auditiva e de fala).​ <br><br>" +
				"			Durante essa nova contratação podem ocorrer alterações em algumas coberturas <br>" +
				"			disponibilizadas, uma vez que tivemos coberturas descontinuadas do produto anterior, <br>" +
				"			no percentual FIPE, e na opção de pagamento, que agora terá o prêmio sempre anual, <br>" +
				"			com possibilidade de parcelamento em até 10 vezes. <br><br>" +
				"			A classe de bônus poderá ser alterada de acordo com a sua experiência no decorrer da <br>" +
				"			vigência da apólice atual. Estas alterações são devidas aos critérios de aceitação <br>" +
				"			da seguradora. <br><br>" +
				"			A emissão da sua nova contratação está sujeita a análise e aceitação por parte da <br>" +
				"			Seguradora, que será comunicada a você 15 dias antes do vencimento da sua apólice <br>" +
				"			atual. <br><br>" +
				"			<b style='color: #003781;'>A Allianz Seguros está pronta para atender você em todas as questões relativas ao<br>" +
				"				seu seguro. </b> <br>" +
				"			E o Santander AutoCompara está à disposição caso você queira fazer uma nova​ <br>" +
				"			cotação, ou alterar suas coberturas e condições." +
				"		</td>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				addImageRodape(rodape,'80','35') +
				"<tr>" +
				"<td>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<td width='11'>&nbsp;</td>" +
				"		<td width='22' bgcolor='#f8f4f2''>&nbsp;</td>" +
				"		<td width='168' height='52' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.4px; line-height: 15px'>" +
				"		CENTRAL DE ATENDIMENTO <br>" +
				"		SANTANDER AUTOCOMPARA: <br><br>" +
				"		</td>" +
				"		<td width='13' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='170' height='52' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.4px; line-height: 15px'>" +
				"			SAC- SERVIÇO DE ATENDIMENTO <br>" +
				"			AO CONSUMIDOR SANTANDER <br>" +
				"			AUTOCOMPARA <br><br>" +
				"		</td>" +
				"		<td width='12' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='192' height='52' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.4px; line-height: 15px'>" +
				"			OUVIDORIA SANTANDER <br>" +
				"			AUTOCOMPARA <br><br>" +
				"		</td>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<td width='11'>&nbsp;</td>" +
				"		<td width='22' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='168' height='109' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.2px; line-height: 15px'>" +
				"			<b style='font-size: 11px; font-weight: 300; letter-spacing: -0.7px;'>" +
				"				Para cotações, contratações e demais <br>" +
				"				informações, ligue para: <br>" +
				"				<b style='color: #e4003a;'>4004 4495</b> (regiões metropolitanas). <br>" +
				"				<b style='color: #e4003a;'>0800 723 4495</b> (Demais localidades) <br>" +
				"				De segunda a sexta-feira, das 8h às 20h, exceto feriados." +
				"			</b>" +
				"		</td>" +
				"		<td width='13' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='170' height='109' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.2px; line-height: 15px'>" +
				"			<b style='font-size: 11px; font-weight: 300; letter-spacing: -0.7px;'>" +
				"				Reclamações. sugestões e informações <br>" +
				"				gerais:  <b style='color: #e4003a;'>0800 726 7777</b>. Atende também <br>" +
				"				deficientes auditivos e de fala. No <br>" +
				"				exterior, ligue a cobrar para: <br>" +
				"				<b style='color: #e4003a;'> 55 11 3012 3336. </b><br>" +
				"				Atendimento: 24h por dia,  todos os dias." +
				"			</b>" +
				"		</td>" +
				"		<td width='12' bgcolor='#f8f4f2'>&nbsp;</td>" +
				"		<td width='192' height='109' valign='top' bgcolor='#f8f4f2' style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 11px; font-weight: 300; letter-spacing: -0.2px; line-height: 15px'>" +
				"			<b style='font-size: 11px; font-weight: 300; letter-spacing: -0.7px;'>" +
				"				Se não ficar satisfeito com a solução <br>" +
				"				apresentada <b style='color: #e4003a;'>0800 726 0322</b>. <br>" +
				"				Atende também deficientes auditivos e <br>" +
				"				de fala. De segunda a sexta-feira, das <br>" +
				"				9h às 18h, exceto feriados." +
				"			</b>" +
				"		</td>" +
				"	</table>" +
				"</td>" +
				"</tr>" +

				"<tr><td height='4'></td></tr><p><p>" +

				"<tr>" +
				"<td height='170'>" +
				"	<table border='0' cellpadding='0' cellspacing='0'>" +
				"		<tr>" +
				"			<td width='34'>&nbsp;</td>" +
				"			<td style='font-family: Arial, Helvetica, SansSerif; color: #414141; font-size: 9px; font-weight: 300; letter-spacing: -0.2px; line-height: 13px '>" +
				"				Seguro intermediado pela Santander Corretora de Seguros, Investimentos e Serviços S.A., CNPJ nº 04.270.778/0001-71, SUSEP<br>" +
				"				202019222 e garantido pela Allianz Brasil Seguradora S.A. O registro deste plano na SUSEP não implica, por parte da autarquia, em  <br>" +
				"				incentivo ou recomendação à sua comercialização. Consulte as condições gerais em sulamericaauto.com.br. Allianz Brasil Seguradora  <br>" +
				"               S.A., nova denominação da Sul América Seguros de Automóveis e Massificados S.A., CNPJ: 32.357.481/0001-83, Avenida República do <br>" +
				"				Chile nº 330, Bloco 1, 24º ao 28º andar, Centro, Rio de Janeiro – RJ. SAC 24 horas: 0800 725 5901. Ouvidoria: 0800 720 9740 e  <br>" +
				"				atendimento à pessoa com deficiência auditiva ou de fala 24 horas: 0800 970 0600. Desde julho de 2020, a Allianz adquiriu a seguradora <br>" +
				"				Sul América Seguros de Automóveis e Massificados S.A.  <br>" +
				"				A Allianz Seguros respeita a sua privacidade e é contra o spam na rede. Para mais informações, acesse a Política de Privacidade de  <br>" +
				"				Dados Allianz, disponível em:  <a href='https://www.allianz.com.br/politica-de-privacidade' style='color: #003781;'>www.allianz.com.br/politica-de-privacidade</a>." +
				"			</td>" +
				"		</tr>" +
				"	</table>" +
				"</td>" +
				"</tr>" +
				"</center></table>"

		return sb;
	}

	/**
	 * @author T_DCDias
	 * @return
	 * 
	 */
	public static recusaDeFrota(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 *
	 */
	public static apolicesHomologacao(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 *
	 */
	public static renovacaoAutoFrota(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}


	/**
	 * @author T_DCDias
	 * @return
	 * Envio de PDF - Corretores Corporate
	 */
	public static renovaSantander(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 * @author T_DCDias
	 * @param rodape
	 * @param cabecalho
	 * @return
	 */
	public static reprocessamentoEmissaoApolice(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 * @author T_DCDias
	 * @return
	 * Envio de PDF - Corretores Corporate
	 */
	public static corretor(){
		String sb = "<head>" +
				"Prezado,<p>" +
				"Segue documentação para suas providencias. <p>" +
				"Att..<p>"
		return sb;
	}

	/**
	 * @author T_DCDias
	 * @param rodape
	 * @param cabecalho
	 * @return
	 */
	public static envioPDF( String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}
}

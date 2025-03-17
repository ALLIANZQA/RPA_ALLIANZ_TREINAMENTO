package templateMessage

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

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

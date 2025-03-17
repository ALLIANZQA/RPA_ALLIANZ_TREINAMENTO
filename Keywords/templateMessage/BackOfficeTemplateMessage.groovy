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
import auxiliares.StringFormatter

public class BackOfficeTemplateMessage extends MessageFactory{

	/**
	 * Aviso de Não Renovação Apólice Allianz/Santander
	 * @return
	 */
	public static String seguroFuncionarioResidencia(String filename){
		String sb = "<head>" +
				addImageCabecalho(filename,'80','35')+"<p>"+
				"Segue anexa proposta de renovação do seu seguro <strong>ALLIANZ RESIDÊNCIA</strong> já com as condições imbativeis e exclusivas para você, colaborador.<p>" +
				"Esperamos que você continue usufruindo desta vantagem e aguardamos o seu retorno a este e-mail até 48 horas para efetivarmos a sua renovação. Caso este<p>" +
				"prazo não seja cumprido, poderá acarretar em mudança do valor apresentado.<p>"+
				"Caso queira fazer outras cotações do <strong>ALLIANZ AUTO</strong> e <strong>ALLIANZ RESIDÊNCIA</strong>, aproveite este benefícios que também é extensivo à sua familia: pais, filhos e cônjuges.<p>" +
				"<strong>Acesse o menu lateral direito da IntraAllianz > seguro para colaborador</strong> e faça já a sua cotação ou endosso no canal exclusivo com a corretora Corsetec.<p>"
		return sb;
	}

	public static String seguroFuncionarioAuto(String filename){
		String sb = "<head>" +
				addImageCabecalho(filename,'80','35')+"<p>"+
				"Segue anexa proposta de renovação do seu seguro <strong>ALLIANZ AUTO</strong> já com as condições imbativeis e exclusivas para você, colaborador.<p>" +
				"Esperamos que você continue usufruindo desta vantagem e aguardamos o seu retorno a este e-mail até 48 horas para efetivarmos a sua renovação. Caso este<p>" +
				"prazo não seja cumprido, poderá acarretar em mudança do valor apresentado.<p>"+
				"Caso queira fazer outras cotações do <strong>ALLIANZ AUTO</strong> e <strong>ALLIANZ RESIDÊNCIA</strong>, aproveite este benefícios que também é extensivo à sua familia: pais, filhos e cônjuges.<p>" +
				"<strong>Acesse o menu lateral direito da IntraAllianz > seguro para colaborador</strong> e faça já a sua cotação ou endosso no canal exclusivo com a corretora Corsetec.<p>"
		return sb;
	}
	
	
	public static String  enviarEmailVoucher(String ...args){
		String[] valores = args
		String sb = "<head>" +
		            "<strong>Prezado, olá</strong>.<p>"+
		            "<strong style='text-decoration: underline;'>PENDÊNCIA - VISTORIA PRÉVIA</strong>.<p>"+
		            "<strong>Informamos que a análise para aceitação desta proposta está condicionada a realização da vistoria prévia.</strong>.</br>"+
		            "<strong>A não realização da vistoria prévia gerará a recusa da proposta em até 03 (três) dias uteis, a contar da data de transmissão da proposta.</strong>.</br>"+
		            "**CASO NECESSÁRIA ALGUMA COMUNICAÇÃO ADICIONAL, NÃO ALTERAR O CAMPO ASSUNTO DESTE EMAIL**.<p>"+
				addImageCabecalho(valores[0],'80','35')+"</br>"+
				addImageRodape(valores[1],'80','35')+"<p>"
		return sb;
	}

	
	/**
	 *
	 * @return
	 */
	public static String textoPadao(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}
	
	/**
	 *
	 * @return
	 */
	public static String textoPadaoErro(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'60','15')+"<br>"+
				templateBodyError()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 * Renovação Auto Residencia
	 * @return
	 */
	public static String renovAutoResidencia(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}



	/**
	 * Bloqueios Pendência GO
	 * @return
	 */
	public static String bloqueiosPendenciaGO(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 * Desbloqueio Proposta Agro Fiel Depositario
	 * @return
	 */
	public static String desbloqueioPropostaAgroFielDepositario(String cabecalho, String rodape){
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
	 */
	public static agendamentoInspecoes(String cabecalho, String rodape){
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
	public static envioPDFCorretoresCorporate(){
		String sb = "<head>" +
				"Prezado,<p>" +
				"Segue Apolice e Boleto. <p>" +
				"Att..<p>"
		return sb;
	}


	/**
	 * Desbloqueio Proposta
	 * @return
	 */
	public static String desbloqueioProposta(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 * Segunda via
	 * @return
	 */
	public static String segundaVia(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 * Segunda via
	 * @return
	 */
	public static String inspecaoRisco(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 *
	 * @return
	 */
	public static String inclusaoVCSantander(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 *
	 * @return
	 */
	public static String boletoAvulso(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 *
	 * @return
	 */
	public static String geraBoleto(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 *
	 * @return
	 */
	public static String pendenciaEmailSantander(String cabecalho, String rodape){
		return body(cabecalho, rodape);
	}

	/**
	 *@author ALZ_ServiceQA
	 * @return
	 */
	public static String pendenciaSnatander(String texto){
		return pendenciaSnatanderBody(texto);
	}

	/**
	 *
	 * @return
	 */
	public static String cadastroCorretorFilialFisicaProcessoFinal(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 * Renovação Apólice Allianz/Santander
	 * @return
	 */
	public static String cadastroCorretorFilialFisica(String corretor, String user, String pwr,String NOMEACCOUNT, String EMAILACCOUNT,String ddd, String telefone, String ddd_account, String telefone_account,List<String> imagens){

		System.out.println(ddd+" "+telefone)
		System.out.println()

		String sb = "<body style='margin:0; padding:0; background-color:#ffffff'>" +
		"<center>" +
		"<div class='gmailfix' style='white-space:nowrap; font:15px Arial,Sans-serif; line-height:0'>" +
		"&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"+
		"</div><table width='600' border='0' cellpadding='0' cellspacing='0' bgcolor='#ffffff'>" +
		"</table><table width='600' align='center' border='0' cellpadding='0' cellspacing='0' class='width-1'>"+
		
		"<tbody><tr>"+
		"	<td>"+
		"		<table border='0' cellpadding='0' cellspacing='0'>"+
		"			<tbody><tr>"+
		"				<td>"+
		"					<img style='line-height: 0px; display: block;' src='cid:image_id1'>"+
		"				</td>"+
		"			</tr>"+
		"			<tr>"+
		"				<td>"+
		"					<img style='line-height: 0px; display: block;' src='cid:image_id2'>"+
		"				</td>"+
		"			</tr>"+
		"			<tr>"+
		"				<td>"+
		"					<img style='line-height: 0px; display: block;' src='cid:image_id3'>"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td style='height: 50px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+

		"<!-- título -->"+
		"<tr>"+
		"	<td>"+
		"		<table align='center' border='0' cellpadding='0' cellspacing='0'>"+
		"			<tbody><tr>"+
		"				<td align='center' style='color: #003781; line-height: 1; letter-spacing: -2px; font-size: 38px; font-family: Arial, Helvetica, sans-serif; vertical-align: top;'>"+
		"					<b>"+
		"						Seja bem-vindo à Allianz!"+
		"					</b>"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td style='height: 62px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+

		"<!--TODO: texto -->"+
		"<tr>"+
		"	<td>"+
		"		<table align='center' border='0' cellpadding='0' cellspacing='0'>"+
		"			<tbody><tr>"+
		"				<td>"+
		"					<table border='0' cellpadding='0' cellspacing='0'>"+
		"						<tbody><tr>"+
		"							<td align='left' style='color: #414141; font-size: 20px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"								<b>"+
		"								Olá, corretor(a)!"+
		"								</b>"+
		"								<br><br>"+
		"  							    Ficamos felizes em desejar as boas-vindas a você,<br>"+
		"								nosso <b>novo parceiro de negócios.</b>"+
		"								<br><br>"+
		"								A Allianz Seguros é uma das maiores seguradoras do<br>"+
		"								mundo, presente em mais de 70 países. <b>E, a partir de<br>"+
		"								agora, você também faz parte e escreve essa história<br>"+
		"								com a gente.</b>"+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td style='height: 46px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+

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
		"							<td align='left' style='color: #ffffff; font-size: 20px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"								Abaixo estão os seus dados de acesso ao Allianznet,<br>"+
		"								a plataforma que vai apoiar você nas questões<br>"+
		"								do dia a dia."+
		"								<br><br>"+
		"								Realize cotações, gerencie sua carteira, acompanhe<br>"+
		"								suas solicitações e conte com muitos outros recursos<br>"+
		"								disponíveis."+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+
		"			<tr>"+
		"				<td style='height: 20px;'>"+
		"					&nbsp;"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
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

		"			<tr>"+
		"				<!-- box1 -->"+
		"				<td>"+
		"					<table border='0' cellpadding'0' cellspacing='0'>"+
		"						<tbody><tr>"+
		"							<td>"+
		"								<img style='line-height: 0px; display: block;' src='cid:image_id15'>"+
		"							</td>"+
		"						</tr>"+
		"						<tr>"+
		"							<td bgcolor='#b5dae6'>"+
		"								<table bgcolor='#b5dae6' align='center' border='0' cellpadding='0' cellspacing='0'>"+
		"									<tbody><tr>"+
		"										<td align='center' style='color: #122b54; font-size: 18px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"											<b>"+
		"												<a href='file:///C:/Users/local_BE54955/INetCache/Content.Outlook/OS80PQTD/index_social_corretor.html#' style='color: #122b54; text-decoration: none;'>"+
		"													Login "+user+""+
		"												</a>"+
		"											</b>"+
		"										</td>"+
		"									</tr>"+
		"								</tbody></table>"+
		"							</td>"+
		"						</tr>"+
		"						<tr>"+
		"							<td>"+
		"								<img style='line-height: 0px; display: block;' src='cid:image_id14''>"+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+

		"				<td style='width: 13px;'>"+
		"				</td>"+

		"				<!-- box2 -->"+
		"				<td>"+
		"					<table border='0' cellpadding='0' cellspacing='0'>"+
		"						<tbody><tr>"+
		"							<td>"+
		"								<img style='line-height: 0px; display: block;' src='cid:image_id15'>"+
		"							</td>"+
		"						</tr>"+
		"						<tr>"+
		"							<td bgcolor='#b5dae6'>"+
		"								<table bgcolor='#b5dae6' align='center' border='0' cellpadding='0' cellspacing='0'>"+
		"									<tbody><tr>"+
		"										<td align='center' style='color: #122b54; font-size: 18px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"											<b>"+
		"												<a href='file:///C:/Users/local_BE54955/INetCache/Content.Outlook/OS80PQTD/index_social_corretor.html#' style='color: #122b54; text-decoration: none;'>"+
		"													Senha "+pwr+""+
		"												</a>"+
		"											</b>"+
		"										</td>"+
		"									</tr>"+
		"								</tbody></table>"+
		"							</td>"+
		"						</tr>"+
		"						<tr>"+
		"							<td>"+
		"								<img style='line-height: 0px; display: block;' src='cid:image_id14'>"+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+

		"			<tr>"+
		"				<td style='height: 25px;'>"+
		"					&nbsp;"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+

		"<!-- btn1 -->"+
		"<tr>"+
		"	<td bgcolor='#dfeff2'>"+
		"		<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='center'>"+
		"			<tbody><tr>"+
		"				<td>"+
		"					<a href='https://allianznet.com.br/'>"+
		"						<img style='line-height: 0px; display: block;' src='cid:image_id4'>"+
		"					</a>"+
		"				</td>"+
		"			</tr>"+
		"			<tr>"+
		"				<td style='height: 25px;'>"+
		"					&nbsp;"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td style='height: 45px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+

		"<!-- texto2 -->"+
		"<tr>"+
		"	<td>"+
		"		<table align='center' border='0' cellpadding='0' cellspacing='0'>"+
		"			<tbody><tr>"+
		"				<td>"+
		"					<table border='0' cellpadding='0' cellspacing='0'>"+
		"						<tbody><tr>"+
		"							<td align='left' style='color: #414141; font-size: 20px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"								<b>Aqui na Allianz você conta com um time especializado</b><br>"+
		"								em atendimento comercial para apoiar o crescimento dos<br>"+
		"								seus negócios."+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td style='height: 47px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+

		"<!-- box-icon -->"+
		"<tr>"+
		"	<td bgcolor='#dfeff2'>"+
		"		<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0'>"+
		"			<tbody><tr>"+
		"				<td style='height: 20px;'>"+
		"					&nbsp;"+
		"				</td>"+
		"			</tr>"+

		"			<tr>"+
		"				<td>"+
		"					<table border='0' cellpadding='0' cellspacing='0' align='center' style='width: 600px;'>"+
		"						<tbody><tr>"+
		"							<td align='center' style='color: #122b54; font-size: 20px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"								<b>"+
		"									Veja abaixo os dados do seu atendimento comercial:"+
		"								</b>"+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+

		"			<tr>"+
		"				<td style='height: 23px;'>"+
		"					&nbsp;"+
		"				</td>"+
		"			</tr>"+

		"			<tr>"+
		"				<td>"+
		"					<table border='0' cellpadding='0' cellspacing='0'>"+
		"						<tbody><tr>"+
		"							<td valign='top'>"+
		"								<img style='line-height: 0px; display: block;' src='cid:image_id13'>"+
		"							</td>"+
		"							<td align='left' style='color: #414141; font-size: 21px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"								<b>"+
		"									<a href='file:///C:/Users/local_BE54955/INetCache/Content.Outlook/OS80PQTD/index_social_corretor.html#' style='color: #414141; text-decoration: none;'>"+
		"										Nome "+NOMEACCOUNT+""+
		"									</a><br>"+
		"									<a href='file:///C:/Users/local_BE54955/INetCache/Content.Outlook/OS80PQTD/index_social_corretor.html#' style='color: #414141; text-decoration: none;'>"+
		"										e-mail "+EMAILACCOUNT+""+
		"									</a><br>"+
		"									<a href='file:///C:/Users/local_BE54955/INetCache/Content.Outlook/OS80PQTD/index_social_corretor.html#' style='color: #414141; text-decoration: none;'>"+
		"										telefone ("+ddd_account+") "+telefone_account+""+
		"									</a>"+
		"								</b>"+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+
				   
		"			<tr>"+
		"				<td style='height: 20px;'>"+
		"					&nbsp;"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td style='height: 47px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td>"+
		"		<table align='center' border='0' cellpadding='0' cellspacing='0'>"+
		"			<tbody><tr>"+
		"				<td>"+
		"					<table border='0' cellpadding='0' cellspacing='0'>"+
		"						<tbody><tr>"+
		"							<td align='left' style='color: #414141; font-size: 20px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"								<b>Para se tornar um especialista em Allianz,</b> conte com a<br>"+
		"								plataforma de treinamentos +Capaz. Utilize o seu login<br>"+
		"								"+user+" e faça o seu primeiro <b><a href='https://maiscapaz.allianz.com.br/' style='color: #13a0d3; text-decoration: underline;'>acesso aqui.</a></b>"+
		"								<br><br>"+
		"								<b>Em compromisso com a ética e proteção</b> de nossos<br>"+
		"								parceiros e segurados, contamos com a sua participação<br>"+
		"								nos <b>treinamentos obrigatórios.</b> Clique abaixo para<br>"+
		"								acessar."+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td style='height: 26px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+

		"<!-- box-btns -->"+
		"<tr>"+
		"	<td>"+
		"		<table align='center' border='0' cellpadding='0' cellspacing='0'>"+
		"			 <!-- parte-1 -->"+
		"			<tbody><tr>"+
		"				<td>"+
		"					<table border='0' cellpadding='0' cellspacing='0'>"+
		"						<tbody><tr>"+
		"							<td>"+
		"								<a href='https://maiscapaz.allianz.com.br/play/player/86819307'>"+
		"									<img style='line-height: 0px; display: block;' src='cid:image_id5'>"+
		"								</a>"+
		"							</td>"+

		"							<td style='width: 9px;'>"+
									   
		"							</td>"+
	
		"							<td>"+
		"								<a href='https://maiscapaz.allianz.com.br/play/player/86819307'>"+
		"									<img style='line-height: 0px; display: block;' src='cid:image_id6'>"+
		"								</a>"+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+

		"			<tr>"+
		"				<td style='height: 10px;'>"+
		"				</td>"+
		"			</tr>"+

		"			<!-- parte-2 -->"+
		"			<tr>"+
		"				<td>"+
		"					<table border='0' cellpadding='0' cellspacing='0'>"+
		"						<tbody><tr>"+
		"							<td>"+
		"								<a href='https://maiscapaz.allianz.com.br/play/player/86819307'>"+
		"									<img style='line-height: 0px; display: block;' src='cid:image_id7''>"+
		"								</a>"+
		"							</td>"+

		"							<td style='width: 9px;'>"+
									   
		"							</td>"+
	
		"							<td>"+
		"								<a href='https://maiscapaz.allianz.com.br/play/player/86819307'>"+
		"									<img style='line-height: 0px; display: block;' src='cid:image_id8''>"+
		"								</a>"+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+
				  
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td style='height: 44px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+

		"<!-- box-icon2 -->"+
		"<tr>"+
		"	<td>"+
		"		<table border='0' cellpadding='0' cellspacing='0' align='center'>"+
		"			<tbody><tr>"+
		"				<td align='center' style='color: #414141; font-size: 19px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"					Siga <a href='file:///C:/Users/local_BE54955/INetCache/Content.Outlook/OS80PQTD/index_social_corretor.html#' style='color: #414141; text-decoration: none;'>@allianzcorretor</a> nas redes sociais:"+
		"				</td>"+
		"			</tr>"+

		"			<tr>"+
		"				<td style='height: 17px;'>"+
		"					&nbsp;"+
		"				</td>"+
		"			</tr>"+
	
		"			<tr>"+
		"				<td>"+
		"					<table border='0' cellpadding='0' cellspacing='0' align='center'>"+
		"						<tbody><tr>"+
		"							<td>"+
		"								<a href='https://www.facebook.com/AllianzCorretor/?locale=pt_BR'>"+
		"									<img style='line-height: 0px; display: block;' src='cid:image_id9'>"+
		"								</a>"+
		"							</td>"+

		"							<td style='width: 11px;'>"+
									   
		"							</td>"+
	
		"							<td>"+
		"								<a href='https://www.instagram.com/allianzcorretor/'>"+
		"									<img style='line-height: 0px; display: block;' src='cid:image_id10'>"+
		"								</a>"+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+

		
		"<tr>"+
		"	<td style='height: 45px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td>"+
		"		<table border='0' cellpadding='0' cellspacing='0' align='center'>"+
		"			<tbody><tr>"+
		"				<td align='center' style='color: #414141; font-size: 20px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"					<b>Vamos juntos construir uma parceria de sucesso.</b>"+
		"					<br><br>"+
		"					<b>Allianz.</b> Você pronto para viver o melhor."+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td style='height: 26px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td style='height: 20px; border-bottom: #003781 solid 1px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td style='height: 28px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td>"+
		"		<table border='0' cellpading='0' cellspacing='0' align='center'>"+
		"			<tbody><tr>"+
		"				<td>"+
		"					<table border='0' cellpadding='0' cellspacing='0' align='center'>"+
		
		"						<tbody><tr>"+
		"							<td align='left' class='textfooter' style='font-size: 15px; color: #003781; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.9px; line-height: 1.2;'>"+
		"								Precisou? Chama no chat"+
		"							</td>"+
		"							<td width='12'>"+
		"								&nbsp;"+
		"							</td>"+
		"							<td width='31' align='left' style='line-height: 1px;'>"+
		"								<a href='https://chatcc.allianz.com.br/%0D%0D' target='_blank'>"+
		"									<img src='cid:image_id11'>"+
		"								</a>"+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+

		"			<tr>"+
		"				<td style='height: 18px;'>"+
		"				</td>"+
		"			</tr>"+

		"			<tr>"+
		"				<td align='left' class='textfooter' style='font-size: 15px; color: #003781; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.9px; line-height: 1.2;'>"+
		"					Conte também com Help Desk Allianz"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td style='height: 15px;'>"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td>"+
		"		<table border='0' cellpadding='0' cellspacing='0'>"+
		"			<tbody><tr>"+
		"				<td>"+
		"					<img style='line-height: 0px; display: block;' src='cid:image_id12''>"+
		"				</td>"+
		"				<td align='left' style='color: #414141; font-size: 13px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"					<b style='color: #003781;'>4090 1155</b> (Capitais e Regiões Metropolitanas) <b style='color: #003781;'>0800 7710555</b> (Demais Regiões)<br>"+
		"					<b style='color: #003781;'>Horário de Atendimento:</b> 2ª à 6ª das 08h00 às 19h00"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td style='height: 23px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td>"+
		"		<table width='555' align='center' border='0' cellpadding='0' cellspacing='0'>"+
		"			<tbody><tr>"+
		"				<td align='left' class='textfooter2' style='color: #414141; font-size: 9px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.7px; vertical-align: top;'>"+
		"					Allianz Seguros S.A., CNPJ: 61.573.796/0001 <a href='file:///C:/Users/local_BE54955/INetCache/Content.Outlook/OS80PQTD/index_social_corretor.html#' style='color: #414141; text-decoration: none;'>-66, Rua Eugênio de Medeiros nº 303</a>, ands. 1-parte, 2 ao 9, 15 e 16, Pinheiros, São Paulo - CEP: 05425-000. Linha<br>"+
		"					Direta Allianz Corretor: 4090 1120 (Capitais e Regiões Metropolitanas), 0800 777 8243 (demais regiões). SAC (24 horas): 08000 115 215. Ouvidoria Allianz<br>"+
		"					Seguros: 0800 771 3313. Atendimento à pessoa com deficiência auditiva ou de fala: 08000 121 239. A Allianz Seguros respeita a sua privacidade e é contra o<br>"+
		"					spam na rede. Para mais informações, acesse a Política de Privacidade de Dados da Allianz, disponível em: <a href='https://www.allianz.com.br/politica-de-privacidade' style='color: #414141; text-decoration: none;'>www.allianz.com.br/politica-de-privacidade.</a>"+
		"				</td>"+
		"			</tr>"+
		"			<tr>"+
		"				<td style='height: 26px;'>"+
		"					&nbsp;"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"
				



		return sb;
	}

	/**
	 *@author ALZ_ServiceQA
	 * @return
	 */
	public static String body(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 *
	 * @return
	 */
	public static String pendenciaSnatanderBody(String texto){
		String sb = StringFormatter.emailBodyFormatter(texto)
		return sb;
	}

	/**
	 *
	 * @return
	 */
	public static String envioRetornoGoEmail(String cabecalho, String rodape){
		return body(cabecalho, rodape);
	}

	/**
	 *@author ALZ_ServiceQA
	 * @return
	 */
	public static String envioRetornoGoUnit(String texto){
		return envioRetornoGoBody(texto);
	}

	/**
	 *@author ALZ_ServiceQA
	 * @return
	 */
	public static String envioPendenciaAutomáticaCorretorUnit(String proposta){
		return envioPendenciaAutomáticaCorretor(proposta);
	}

	/**
	 *
	 * @return
	 */
	public static String geraBlocoBCadastroCorretorEmail(String cabecalho, String rodape){
		return body(cabecalho, rodape);
	}

	/**
	 *
	 * @return
	 */
	public static String distribuicaoPropostasGOlEmail(String cabecalho, String rodape){
		return body(cabecalho, rodape);
	}

	/**
	 *
	 * @return
	 */
	public static String recusaPropostaEmail(String cabecalho, String rodape){
		return body(cabecalho, rodape);
	}

	/**
	 *
	 * @return
	 */
	public static String reativacaoApolice(String cabecalho, String rodape){
		return body(cabecalho, rodape);
	}

	/**
	 *
	 * @return
	 */
	public static String envioPendenciaAutomáticaCorretor(String proposta){

		String sb = "<head>" +"<p>"+StringFormatter.emailBodyFormatterPendenciaAutomaticaCorretor(proposta)

		//		String sb = "<head>" +"<p>"+
		//		"Prezado (a) Corretor."+
		//		"	<p>" +
		//		"Existe (m) uma ou mais pendência (s) gerada (a) para a proposta NRº "+proposta+"<p>" +
		//		"<p>" +
		//		"*O RETORNO DEVE SER REALIZADO ATRAVES DA OPÇÃO DE INDEXAR ARQUIVO NO CODIGO 21*<p>" +
		//		"<p>" +
		//		"Laudo de vistoria cautelar não é aceito, somente conforme abaixo:<p>" +
		//		"<p>" +
		//		"Enviar laudo de inspeção veicular, emitido por empresa ACREDITADA pelo Inmetro. Nesta inspeção devem constar pelo menos duas fotos do veículo, o decalque do chassi e um check-list com todos os itens verificados.<br>" +
		//		"O laudo também deve atestar que o veículo está em plenas condições de uso e segurança, conforme as regras do DENATRAN.<p>" +
		//		"<p>" +
		//		"<p>" +
		//		"RETORNO DA(s) PENDÊNCIA(s)>>><br>" +
		//		"Para nos retornar a pendência há duas opções:<br>" +
		//		"•	Via portal, através de nota especifica, informada no texto da pendência que consta em seu painel de avisos; OU<br>" +
		//		"•	Através deste e-mail, e para isto o Campo Assunto Não deve ser alterado.<p>" +
		//		"<p>" +
		//		"Importante:<br>" +
		//		"•	Após o retorno da pendência o time de emissão tem um prazo de até 03 dias úteis para análise e emissão do seu documento.<br>" +
		//		"•	O prazo para retorno desta pendência é de 2 dias uteis.<br>" +
		//		"•	O  não  recebimento  do  retorno  dentro do prazo, pode acarretar a recusa da proposta.<br>" +
		//		"<p>" +
		//		"A disposição.<p>"

		return sb;
	}

	/**
	 *
	 * @return
	 */
	public static String envioRetornoGoBody(String texto){
		String sb = StringFormatter.emailBodyFormatter(texto)
		return sb;
	}
}

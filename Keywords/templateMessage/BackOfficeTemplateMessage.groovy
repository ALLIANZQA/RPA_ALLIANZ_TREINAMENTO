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
	public static String cadastroCorretorFilialFisica(String corretor, String user, String pwr,String NOMEACCOUNT, String EMAILACCOUNT,String ddd, String telefone, List<String> imagens){

		System.out.println(ddd+" "+telefone)
		System.out.println()

		String sb = "<body style='margin:0; padding:0; background-color:#ffffff'>" +
				"<center>" +
				"<div class='gmailfix' style='white-space:nowrap; font:15px Arial,Sans-serif; line-height:0'>" +
				"&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </div><table width='600' border='0' cellpadding='0' cellspacing='0' bgcolor='#ffffff'>" +
				"</table>" +
				"<table width='600' align='center' border='0' cellpadding='0' cellspacing='0' class='width-1'>" +
				"<tbody>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td><img src='cid:image_id1' alt=''>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td><img src='cid:image_id2X' alt=''>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:50px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='43'>&nbsp; </td>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='550' align='left' style='color:#013782; font-size:35px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.8px; line-height:1.2'>" +
				"Estamos felizes de ter você aqui. <br>" +
				"<b style='color:#5ecd89'>Pode contar com a gente!</b> </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:40px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td bgcolor='e0eff2'>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td bgcolor='e0eff2'>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td style='height:30px>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td width='157'>&nbsp; </td>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='550' align='center' style='color:#003680; font-size:26px; font-weight:bold; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				"Bem-vindo à Allianz! </td>" +
				"</tr>" +
				"<tr>" +
				"<td style='line-height:10px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td width='550' align='center' style='color:#003680; font-size:19px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				"Pra começar, você está recebendo <br>" +
				"<b>seu login e sua senha do Allianznet.</b> </td>" +
				"</tr>" +
				"<tr>" +
				"<td style='line-height:10px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td width='550' align='center' style='color:#003680; font-size:19px; font-weight:bold; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				"Login: <span style='color:#13a1d3'>"+user+"</span> Senha: <span style='color:#13a1d3'>" +
				""+pwr+"</span> </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +

				"<table>" +
				"<tr>" +
				"<td>" +
				"<img src='cid:image_id5'' alt=''>" +
				"</td>" +
				"</tr>" +
				"</table>" +

				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:30px'>&nbsp; </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td bgcolor='e0eff2' style='line-height:1px'>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				//	"<td width='600' style='height:40px; border-top:#003680 1px solid'>&nbsp; </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='600' align='center' style='color:#003680; font-size:17px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				"Lá você confere a cotação em 1 minuto, faz emissões, <br>" +
				"renovações, examina relatórios e acompanha sua carteira. <br>" +
				"<b>Tudo isso em um só lugar e com poucos cliques!</b> </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:20px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='600' align='center'style='color:#003680; font-size:17px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				"<a href='https://www.allianznet.com.br' target='_blank'><img src='cid:image_id8' alt=''>" +
				"</a></td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:30px'>&nbsp; </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:30px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='43'>&nbsp; </td>" +
				"<td width='550' align='left' style='color:#003680; font-size:19px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				"<b>E fique tranquilo(a)</b>, você conta desde já com um parceiro <br>" +
				"de negócios para apoiar você em tudo. </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:20px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='43'>&nbsp; </td>" +
				"<td width='30' align='left' style='color:#003680; font-size:19px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				"<img src='cid:image_id12' alt=''>" +
				"</td>" +
				"<td width='10'>&nbsp; </td>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='230' align='left' style='color:#003680; font-size:18px; font-weight:bold; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				"Anote os contatos: </td>" +
				"</tr>" +
				"<tr>" +
				"<td width='230' align='left' style='color:#003680; font-size:18px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				""+NOMEACCOUNT+" </td>" +
				"</tr>" +

				"<tr>" +
				"<td width='230' align='left' style='color:#003680; font-size:18px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				""+EMAILACCOUNT+" </td>" +
				"</tr>" +

				"<tr>" +
				"<td width='230' align='left' style='color:#003680; font-size:18px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				"("+ddd+") "+telefone+"" +
				"</tr>" +



				"</tbody>" +
				"</table>" +
				"</td>" +
				"<td valign='bottom'>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +

				//						"<tbody><tr>" +
				//						"<td width='230' align='left' style='color: #003680; font-size: 18px; font-weight: 300; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.5px; line-height: 1.2;'>" +
				//						"("+ddd+") "+telefone+"" +
				//						"</td>" +
				//						"</tr>" +

				//						"<tbody>" +
				//						"<tr>" +
				//						"<td width='230' align='left' style='color:#003680; font-size:18px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				//						""+EMAILACCOUNT+" </td>" +
				//						"</tr>" +
				//						"</tbody>" +


				"</table>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:20px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td><img src='cid:image_id15X' alt=''>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:35px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='43'>&nbsp; </td>" +
				"<td width='550' align='left' style='color:#003680; font-size:19px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				"<b>Importante:</b> para que o seu cadastro permaneça ativo, a Allianz <br>" +
				"se resguarda ao direito de solicitar o atingimento de uma <br>" +
				"produção mínima. <br>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='line-height:10px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td width='43'>&nbsp; </td>" +
				"<td width='550' align='left' style='color:#003680; font-size:19px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				"Em caso de dúvidas, entre em contato com o seu atendimento <br>" +
				"Comercial. </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:35px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td bgcolor='f8f8f8'>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td style='height:10px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='43'>&nbsp; </td>" +
				"<td width='550' align='left' style='color:#003680; font-size:15px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.5px; line-height:1.2'>" +
				"Confira também os documentos abaixo. Eles são importantes para a nossa parceria. </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:10px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='150' valign='top'>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='43'>&nbsp; </td>" +
				"<td valign='top'><img src='cid:image_id20' alt=''>" +
				"</td>" +
				"<td width='100' valign='top' align='left' style='text-decoration:underline; color:#003680; font-size:12px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.8px; line-height:1.2'>" +
				"<a href=' https://maquina-hom.allianz.com.br/media/df507c7e-4591-4f5b-9422-48a13811ac5c?user_id=6989ae46-5b9b-4667-9cc2-1596631edae9' target='_blank' style='color: #04397e; font-family: Arial, Helvetica, sans-serif; font-size: 12px;  line-height: 1.1;'>" +
				"Treinamento <br>" +
				"Brokers PLD </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"<td width='150'>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='24'>&nbsp; </td>" +
				"<td valign='top'><img src='cid:image_id20' alt=''>" +
				"</td>" +
				"<td valign='top' width='600' align='left' style='text-decoration:underline; color:#003680; font-size:12px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.8px; line-height:1.2'>" +
				"Treinamento <br>" +
				"<a href=' https://maquina-hom.allianz.com.br/media/10c0c2e2-db0c-4cab-ab93-032b5b0e6bcc?user_id=6989ae46-5b9b-4667-9cc2-1596631edae9' target='_blank' style='color: #04397e; font-family: Arial, Helvetica, sans-serif; font-size: 12px;  line-height: 1.1;'>" +
				"Prevenção à Fraude<br>" +
				"em seguros </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"<td width='110'>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='24'>&nbsp; </td>" +
				"<td valign='top'><img src='cid:image_id20' alt=''>" +
				"</td>" +
				"<td width='100' align='left' style='text-decoration:underline; color:#003680; font-size:12px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.8px; line-height:1.2'>" +
				"<a href=' https://maquina-hom.allianz.com.br/media/8cc1562b-6d69-4cf2-a25e-13e7a8c9ea54?user_id=6989ae46-5b9b-4667-9cc2-1596631edae9' target='_blank' style='color: #04397e; font-family: Arial, Helvetica, sans-serif; font-size: 12px;  line-height: 1.1;'>" +
				"Treinamento <br>" +
				"Prevenção à<br>" +
				"Corrupção </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"<td width='150' valign='top'>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='24'>&nbsp; </td>" +
				"<td valign='top'><img src='cid:image_id20' alt=''>" +
				"</td>" +
				"<td width='120' align='left' style='text-decoration:underline; color:#003680; font-size:12px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-1px; line-height:1.2'>" +
				"<a href=' https://maquina-hom.allianz.com.br/media/3ca4e2ce-d4eb-41e9-958b-c13d77ff8819?user_id=6989ae46-5b9b-4667-9cc2-1596631edae9' target='_blank' style='color: #04397e; font-family: Arial, Helvetica, sans-serif; font-size: 12px; line-height: 1.1;'>" +
				"Proteção<br>" +
				"do Cliente</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:10px'>&nbsp; </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:40px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='43'>&nbsp; </td>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +

				"<td>" +
				"<img src='cid:image_id21' alt=''>" +
				"</td>" +

				"<tbody>" +
				"<tr>" +
				"<td width='550' align='left' style='color:#013782; font-size:35px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.8px; line-height:1.2'>" +
				"E o que vem por aí? </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='line-height:10px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='34'>&nbsp; </td>" +
				"<td valign='top'><img src='cid:image_id24' alt=''>" +
				"</td>" +
				"<td width='14'>&nbsp; </td>" +
				"<td>" +
				"<table border='0' cellpadding='0'cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='500' align='left' style='color:#013782; font-size:16px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.8px; line-height:1.2'>" +
				"Em dois dias você já poderá acessar nossa plataforma de treinamentos. </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='line-height:10px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='34'>&nbsp; </td>" +
				"<td valign='top'><img src='cid:image_id27' alt=''>" +
				"</td>" +
				"<td width='14'>&nbsp; </td>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='500' align='left' style='color:#013782; font-size:16px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.8px; line-height:1.2'>" +
				"E,&nbsp;nos próximos e-mails, você vai saber mais sobre nossos produtos,<br>" +
				"processos e serviços digitais. </td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:50px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0' align='center'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='88'>&nbsp; </td>" +
				"<td><a href='https://www.instagram.com/allianzbrasil/' target='_blank'><img src='cid:image_id31' alt='' style='color: #003781; text-decoration: none;'> @allianzbrasil </a>" +
				"</td>" +
				"<td><a href='https://www.facebook.com/allianzbrasil/' target='_blank'><img src='cid:image_id33' alt='' style='color: #003781; text-decoration: none;'> /allianzbrasil </a>" +
				"</td>" +
				"<td><a href='https://www.linkedin.com/company/allianz-seguros-brasil/mycompany/verification/' target='_blank'><img src='cid:image_id35' alt='' style='color: #003781; text-decoration: none;'> /allianzbrasil </a>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"<tr>" +
				"<td style='height:30px'>&nbsp; </td>" +
				"</tr>" +
				"<tr>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='11'>&nbsp; </td>" +
				"<td>" +
				"<table border='0' cellpadding='0' cellspacing='0'>" +
				"<tbody>" +
				"<tr>" +
				"<td width='585' class='textfooter' align='left' style='color:#013782; font-size:13px; font-weight:300; font-family:Arial,Helvetica,sans-serif; letter-spacing:-0.8px; line-height:1.2'>" +
				"Allianz Seguros S.A., CNPJ 61.573.796/0001-<a style='color:#013781; text-decoration:none'>66, Rua Eugênio de Medeiros, nº 303, ands. 1-parte, 2 ao 9,</a>" +
				"<br>" +
				"15 e 16, Pinheiros, São Paulo - CEP 05425-000. Linha Direta Corretor: 4090 1120 (Capitais e Regiões" +
				"<br>" +
				"Metropolitanas), 0800 777 8243 (Demais Regiões). Ouvidoria Allianz Seguros: 0800 771 3313." +
				"<br>" +
				"Atendimento à pessoa com deficiência auditiva ou de fala: 08000 121 239.<br>" +
				"A Allianz Seguros respeita a sua privacidade e é contra o spam na rede. Para mais informações, acesse" +
				"<br>" +
				"a Política de Privacidade de Dados da Allianz, disponível em:<a style='color:#013781; text-decoration:none'>ww.allianz.com.br/politica-de-privacidade</a>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</td>" +
				"</tr>" +
				"</tbody>" +
				"</table>" +
				"</center>" +
				"</body>"



		//		String sb = "<body bgcolor='#FFFFFF' leftmargin='0' topmargin='0' marginwidth='0' marginheight='0'>" +
		//				"<table width='600' align='center' border='0' cellpadding='0 cellspacing='0' class='width-1'>" +
		//				"   <tr>" +
		//				"       <td>" +
		//				"           <img src='cid:image_id1' alt=''>" +
		//				"       </td>" +
		//				"   </tr>" +
		//				"   <tr>" +
		//				"       <td>" +
		//				"           <img src='cid:image_id2X' alt=''>" +
		//				"        </td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td height='44px'>&nbsp;</td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td>" +
		//				"            <table border='0' cellspacing='0' cellpadding='0' align='center'>" +
		//				"                <tr>" +
		//				"                    <td width='44px'>&nbsp;</td>" +
		//				"                    <td style='font-family: Arial, Helvetica, sans-serif; font-size: 35px; color: #013782; line-height: 1;'>" +
		//				"                        Estamos felizes de ter você aqui." +
		//				"                        <b style='font-weight: 300; color: #5ecd89;'>Pode contar com a gente!</b>" +
		//				"                    </td>" +
		//				"                </tr>" +
		//				"            </table>" +
		//				"        </td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td height='44px'>&nbsp;</td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"    <td align='center' bgcolor='#e0eff2'>" +
		//				"            <table border='0' cellspacing='0' cellpadding='0'>" +
		//				"                <tr>" +
		//				"                    <td height='30px'>&nbsp;</td>" +
		//				"                </tr>" +
		//				"                <tr>" +
		//				"                    <td >" +
		//				"                        <table border='0' cellspacing='0' cellpadding='0'>" +
		//				"                            <tr>" +
		//				"                                <td>" +
		//				"                                    <table border='0' cellspacing='0' cellpadding='0' >" +
		//				"                                        <tr>" +
		//				"                                            <td style='font-family: Arial, Helvetica, sans-serif; font-size: 26px; color: #003680; line-height: 1;'>" +
		//				"                                                <b>Bem-vindo à Allianz!</b>" +
		//				"                                            </td>" +
		//				"                                        </tr>" +
		//				"                                        <tr>" +
		//				"                                            <td height='19px'>&nbsp;</td>" +
		//				"                                        </tr>" +
		//				"                                        <tr>" +
		//				"                                            <td style='font-family: Arial, Helvetica, sans-serif; font-size: 20px; color: #003680; line-height: 1.2;'>" +
		//				"                                                Pra começar, você está recebendo<br> " +
		//				"                                                <b>seu login e sua senha do Allianznet.</b>" +
		//				"                                            </td>" +
		//				"                                        </tr>" +
		//				"                                        <tr>" +
		//				"                                            <td>" +
		//				"                                                <table border='0' cellspacing='0' cellpadding='0'>" +
		//				"                                                    <tr>" +
		//				"                                                        <td style='font-family: Arial, Helvetica, sans-serif; font-size: 20px; color: #003680; line-height: 2; font-weight: 600;'>" +
		//				"                                                            Login: <b style='color: #13a1d3;'>"+user+"</b>   " +
		//				"                                                        </td>" +
		//				"                                                        <td width='28px'>&nbsp;</td>" +
		//				"                                                        <td style='font-family: Arial, Helvetica, sans-serif; font-size: 20px; color: #003680; line-height: 2; font-weight: 600;'>" +
		//				"                                                            Senha: <b style=' color: #13a1d3;'>"+pwr+"</b> " +
		//				"                                                        </td>" +
		//				"                                                    </tr>" +
		//				"                                                </table>" +
		//				"                                            </td>" +
		//				"                                        </tr>" +
		//				"                                        <tr>" +
		//				"                                            <td height='12px'>&nbsp;</td>" +
		//				"                                        </tr>" +
		//				"                                    </table>" +
		//				"                                </td>" +
		//				"                            </tr>" +
		//				"                        </table>" +
		//				"                    </td>" +
		//				"                </tr>" +
		//				"            </table>" +
		//				"            <table>" +
		//				"             <tr>" +
		//				"                <td>" +
		//				"                   <img src='cid:image_id5'' alt=''>" +
		//				"                </td>" +
		//				"              </tr>" +
		//				"            </table>" +
		//				"        </td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"    <td align='center' bgcolor='#e0eff2'>" +
		//				"            <table border='0' cellspacing='0' cellpadding='0'>" +
		//				"                <tr>" +
		//				"                    <td height='27px'>&nbsp;</td>" +
		//				"                </tr>" +
		//				"                <tr>" +
		//				"                    <td>" +
		//				"                        <table border='0' cellspacing='0' cellpadding='0'>" +
		//				"                            <tr>" +
		//				"                                <td align='center' style='font-family: Arial, Helvetica, sans-serif; font-size: 17px; color: #003781; line-height: 1.2;'>" +
		//				"                                    Lá você confere a cotação em 1 minuto, faz emissões,<br> " +
		//				"                                    renovações, examina relatórios e acompanha sua carteira. <br>" +
		//				"                                    <b>Tudo isso em um só lugar e com poucos cliques!</b>" +
		//				"                                </td>" +
		//				"                            </tr>" +
		//				"                        </table>" +
		//				"                    </td>" +
		//				"                </tr>" +
		//				"                <tr>" +
		//				"                    <td height='29px'>&nbsp;</td>" +
		//				"                </tr>" +
		//				"                <tr>" +
		//				"                    <td align='center'>" +
		//				"                        <a href='https://www.allianznet.com.br' target='_blank'>" +
		//				"                        <img src='cid:image_id8' alt=''>" +
		//				"                    </a>" +
		//				"                    </td>" +
		//				"                </tr>" +
		//				"                <tr>" +
		//				"                    <td height='23px'>&nbsp;</td>" +
		//				"                </tr>" +
		//				"            </table>" +
		//				"        </td>" +
		//				"    </tr>   " +
		//				"    <tr>" +
		//				"        <td height='29px'>&nbsp;</td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td>" +
		//				"            <table border='0' cellspacing='0' cellpadding='0' align='center'>" +
		//				"                <tr>" +
		//				"                    <td width='42px'>&nbsp;</td>" +
		//				"                    <td  style='font-family: Arial, Helvetica, sans-serif; font-size: 19px; color: #003781; line-height: 1.2;'>" +
		//				"                        <b>E fique tranquilo(a)</b>, você conta desde já com um parceiro<br>" +
		//				"                        de negócios para apoiar você em tudo. " +
		//				"                    </td>" +
		//				"                </tr>" +
		//				"            </table>" +
		//				"        </td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td height='34px'>&nbsp;</td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td>" +
		//				"            <table border='0' cellspacing='0' cellpadding='0' align='center'>" +
		//				"                <tr>" +
		//				"                    <td>" +
		//				"                        <table border='0' cellspacing='0' cellpadding='0' align='center'>" +
		//				"                            <tr>" +
		//				"                                <td width='34px'>&nbsp;</td>" +
		//				"                                <td>" +
		//				"                                    <img src='cid:image_id12' alt=''>" +
		//				"                                </td>" +
		//				"                                <td width='12px'>&nbsp;</td>" +
		//				"                                <td  style='font-family: Arial, Helvetica, sans-serif; font-size: 18px; color: #003781; line-height: 1.2;'>" +
		//				"                                    <b>ANOTE OS CONTATOS:</b><br>" +
		//				"                                   "+NOMEACCOUNT+""+
		//				"                                </td>" +
		//				"                                <td width='26px'>&nbsp;</td>" +
		//				"                                <td valign='bottom' style='font-family: Arial, Helvetica, sans-serif; font-size: 18px; color: #003781; line-height: 1.2;'>" +
		//				"                                    "+EMAILACCOUNT+"" +
		//				"                                </td>" +
		//				"                            </tr>" +
		//				"                         </table>" +
		//				"                    </td>" +
		//				"                </tr>" +
		//				"            </table>" +
		//				"        </td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td height='20px'>&nbsp;</td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td>" +
		//				"           <img src='cid:image_id15X' alt=''>" +
		//				"        </td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td height='12px'>&nbsp;</td>" +
		//				"    </tr>" +
		//				"   <tr>" +
		//				"       <td bgcolor='#f8f8f8'>" +
		//				"            <table border='0' cellspacing='0' cellpadding='0' align='center'>" +
		//				"                <tr>" +
		//				"                    <td >" +
		//				"                        <table border='0' cellspacing='0' cellpadding='0'>" +
		//				"                            <tr>" +
		//				"                                <td height='23'>&nbsp;</td>" +
		//				"                            </tr>" +
		//				"                            <tr>" +
		//				"                                <td width='33px'>&nbsp;</td>" +
		//				"                                <td  style='font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #0d3872; line-height: 1;'>" +
		//				"                                    Confira também os documentos abaixo. Eles são importantes para a nossa parceria." +
		//				"                                </td>" +
		//				"                            </tr>" +
		//				"                        </table>" +
		//				"                    </td>" +
		//				"                </tr>" +
		//				"                <tr>" +
		//				"                    <td height='21px'>&nbsp;</td>" +
		//				"                </tr>" +
		//				"                <tr>" +
		//				"                    <td >" +
		//				"                        <table border='0' cellspacing='0' cellpadding='0'>" +
		//				"                            <tr>" +
		//				"                                <td>" +
		//				"                                    <table border='0' cellspacing='0' cellpadding='0'>" +
		//				"                                        <tr>" +
		//				"                                            <td>" +
		//				"                                                <table border='0' cellspacing='0' cellpadding='0'>" +
		//				"                                                    <tr>" +
		//				"                                                        <td width='34px'>&nbsp;</td>" +
		//				"                                                        <td valign='top'>" +
		//				"                                                            <img src='cid:image_id20' alt=''>" +
		//				"                                                        </td>" +
		//				"                                                        <td width='5px'>&nbsp;</td>" +
		//				"                                                        <td valign='top'>" +
		//				"                                                            <a href=' https://www.allianz.com.br/content/dam/onemarketing/iberolatam/allianz-br/documents/pagina-documentos/22/CartilhaPLDFT2022Corretores.pdf' target='_blank' style='color: #04397e; font-family: Arial, Helvetica, sans-serif; font-size: 12px;  line-height: 1.1;'>" +
		//				"                                                                Treinamento<br>" +
		//				"                                                                Brokers PLD</a>" +
		//				"                                                        </td>" +
		//				"                                                        <td width='31px'>&nbsp;</td>" +
		//				"                                                        <td valign='top'>" +
		//				"                                                            <img src='cid:image_id20' alt=''>" +
		//				"                                                        </td>" +
		//				"                                                        <td width='5px'>&nbsp;</td>" +
		//				"                                                        <td valign='top'>" +
		//				"                                                            <a href=' https://www.allianz.com.br/content/dam/onemarketing/iberolatam/allianz-br/documents/pagina-documentos/22/CartilhaAntifraude2022Corretores.pdf' target='_blank' style='color: #04397e; font-family: Arial, Helvetica, sans-serif; font-size: 12px;  line-height: 1.1;'>" +
		//				"                                                                Treinamento<br>" +
		//				"                                                                Prevenção à Fraude<br>" +
		//				"                                                                 em seguros</a>" +
		//				"                                                        </td>" +
		//				"                                                        <td width='30px'>&nbsp;</td>" +
		//				"                                                        <td valign='top'>" +
		//				"                                                            <img src='cid:image_id20' alt=''>" +
		//				"                                                        </td>" +
		//				"                                                        <td width='5px'>&nbsp;</td>" +
		//				"                                                        <td valign='top'>" +
		//				"                                                            <a href=' https://www.allianz.com.br/content/dam/onemarketing/iberolatam/allianz-br/documents/pagina-documentos/22/CartilhaAnticorrupcao2022Corretores.pdf' target='_blank' style='color: #04397e; font-family: Arial, Helvetica, sans-serif; font-size: 12px;  line-height: 1.1;'>" +
		//				"                                                                Treinamento<br> " +
		//				"                                                                Prevenção à<br> " +
		//				"                                                                Corrupção" +
		//				"                                                            </a>" +
		//				"                                                        </td>" +
		//				"                                                        <td width='33px'>&nbsp;</td>" +
		//				"                                                        <td valign='top'>" +
		//				"                                                            <img src='cid:image_id20' alt=''>" +
		//				"                                                        </td>" +
		//				"                                                        <td width='5px'>&nbsp;</td>" +
		//				"                                                        <td valign='top'>" +
		//				"                                                            <a href=' https://www.allianz.com.br/content/dam/onemarketing/iberolatam/allianz-br/documents/pagina-documentos/22/CartilhaCustomerProtectionCorretores.pdf' target='_blank' style='color: #04397e; font-family: Arial, Helvetica, sans-serif; font-size: 12px; line-height: 1.1;'>" +
		//				"                                                                Proteção <br>" +
		//				"                                                                do Cliente</a>" +
		//				"                                                        </td>" +
		//				"                                                    </tr>" +
		//				"                                                </table>" +
		//				"                                            </td>" +
		//				"                                        </tr>" +
		//				"                                    </table>" +
		//				"                                </td>" +
		//				"                            </tr>" +
		//				"                            <tr>" +
		//				"                                <td height='21px'>&nbsp;</td>" +
		//				"                            </tr>" +
		//				"                        </table>" +
		//				"                    </td>" +
		//				"                </tr>" +
		//				"            </table>" +
		//				"        </td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td height='44px'>&nbsp;</td>" +
		//				"    </tr>" +
		//				"       <tr>" +
		//				"           <td>" +
		//				"               <table border='0' cellspacing='0' cellpadding='0' > " +
		//				"                    <td>" +
		//				"                        <img src='cid:image_id21' alt=''>" +
		//				"                    </td>" +
		//				"                   <tr>" +
		//				"                    <td width='42px'>&nbsp;</td>" +
		//				"                       <td style='font-size: 35px; font-family: Arial, Helvetica, sans-serif; color: #003781;'>" +
		//				"                                <p>E o que vem por aí?" +
		//				"                       </td>" +
		//				"                   </tr>" +
		//				"                   <tr>" +
		//				"                    <td style='line-height: 1;'>&nbsp;</td>" +
		//				"                </tr>" +
		//				"               </table>" +
		//				"           </td>" +
		//				"       </tr>" +
		//				"       <tr>" +
		//				"        <td>" +
		//				"            <table border='0' cellspacing='0' cellpadding='0' align='center'>" +
		//				"                <tr>" +
		//				"                    <td width='34px'>&nbsp;</td>" +
		//				"                    <td>" +
		//				"                        <img src='cid:image_id24' alt=''>" +
		//				"                    </td>" +
		//				"                    <td width='15px'>&nbsp;</td>" +
		//				"                    <td style='font-size: 16px; font-family: Arial, Helvetica, sans-serif; color: #003781; line-height: 1.2;'>" +
		//				"                        Em breve, você já poderá acessar nossa plataforma on-line de<br>" +
		//				"                         treinamentos: Máquina de Capacitação Allianz." +
		//				"                    </td>" +
		//				"                </tr>" +
		//				"                <tr>" +
		//				"                    <td style='line-height: 1;'>&nbsp;</td>" +
		//				"                </tr>" +
		//				"                <tr>" +
		//				"                    <td width='34px'>&nbsp;</td>" +
		//				"                    <td>" +
		//				"                        <img src='cid:image_id27' alt=''>" +
		//				"                    </td>" +
		//				"                    <td width='15px'>&nbsp;</td>" +
		//				"                    <td style='font-size: 16px; font-family: Arial, Helvetica, sans-serif; color: #003781; line-height: 1.2;'>" +
		//				"                        E, nos próximos e-mails, você vai saber mais sobre nossos produtos,<br>" +
		//				"                         processos e serviços digitais." +
		//				"                    </td>" +
		//				"                </tr>" +
		//				"            </table>" +
		//				"        </td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td height='40px'>&nbsp;</td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td>" +
		//				"            <table border='0' cellspacing='0' cellpadding='0' align='center'>" +
		//				"                <tr>" +
		//				"                    <td width='115px'>&nbsp;</td>" +
		//				"                    <td>" +
		//				"                        <img src='cid:image_id31' alt=''>" +
		//				"                    </td>" +
		//				"                    <td width='6px'>&nbsp;</td>" +
		//				"                    <td style='font-size: 12px; font-family: Arial, Helvetica, sans-serif;'>" +
		//				"                       <a href='https://www.instagram.com/allianzcorretor/' target='_blank' style='color: #003781; text-decoration: none;'>@allianzbrasil</a>" +
		//				"                    </td>" +
		//				"                    <td width='21px'>&nbsp;</td>" +
		//				"                    <td>" +
		//				"                         <img src='cid:image_id33' alt=''>" +
		//				"                    </td>" +
		//				"                    <td width='6px'>&nbsp;</td>" +
		//				"                    <td style='font-size: 12px; font-family: Arial, Helvetica, sans-serif;'>" +
		//				"                       <a href='https://pt-br.facebook.com/AllianzCorretor/' target='_blank' style='color: #003781; text-decoration: none;'>/allianzbrasil</a>" +
		//				"                    </td>" +
		//				"                    <td width='25px'>&nbsp;</td>" +
		//				"                    <td>" +
		//				"                        <img src='cid:image_id35' alt=''>" +
		//				"                    </td>" +
		//				"                    <td width='6px'>&nbsp;</td>" +
		//				"                    <td style='font-size: 12px; font-family: Arial, Helvetica, sans-serif;'>" +
		//				"                       <a href='https://br.linkedin.com/company/allianz-seguros-brasil' target='_blank' style='color: #003781; text-decoration: none;'>/allianzbrasil</a>" +
		//				"                    </td>" +
		//				"                </tr>" +
		//				"            </table>" +
		//				"        </td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td height='37px'>&nbsp;</td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td>" +
		//				"            <table border='0' cellspacing='0' cellpadding='0' align='center'>" +
		//				"                <tr>" +
		//				"                    <td width='12px'>&nbsp;</td>" +
		//				"                    <td style='font-size: 12.4px; font-family: Arial, Helvetica, sans-serif; color: #48638b;'>" +
		//				"                        Allianz Seguros S.A., CNPJ 61.573.796/0001-66, Rua Eugênio de Medeiros, nº 303, ands. 1-parte, 2 ao 9,<br>" +
		//				"                        15 e 16, Pinheiros, São Paulo - CEP 05425-000. Linha Direta Corretor: 4090 1120 (Capitais e Regiões<br>" +
		//				"                        Metropolitanas), 0800 777 8243 (Demais Regiões). Ouvidoria Allianz Seguros: 0800 771 3313.<br>" +
		//				"                        Atendimento à pessoa com deficiência auditiva ou de fala: 08000 121 239.<br>" +
		//				"                        A &nbsp;Allianz &nbsp;Seguros respeita a sua privacidade e é contra o spam na rede. &nbsp;Para mais informações, acesse<br>" +
		//				"                        a Política de Privacidade&nbsp; de &nbsp;Dados&nbsp; da&nbsp; Allianz, disponível em: <a href='#' target='_blank' style='color: #48638b; text-decoration: none;'>ww.allianz.com.br/politica-de-privacidade</a>" +
		//				"                    </td>" +
		//				"                </tr>" +
		//				"            </table>" +
		//				"        </td>" +
		//				"    </tr>" +
		//				"    <tr>" +
		//				"        <td height='15px'>&nbsp;</td>" +
		//				"    </tr>" +
		//				"</table>   " +
		//				"</body>"



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

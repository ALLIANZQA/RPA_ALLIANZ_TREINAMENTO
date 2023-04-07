package sendEmail

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
import bean.MassaEmail
import internal.GlobalVariable



import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class envioDeEmail extends CreateXMLBackoffice{


	private static String subject = "Yessss";
	private static String content = "foo bar foo bar....";
	private static String recipient = "denilson.inmetricsext@allpronet.com.br";
	private static String from = "denilson.inmetricsext@allpronet.com.br";

	public static send(){


		Properties  properties = new Properties();

		properties.put("mail.smtp.auth", "false");
		//Put below to false, if no https is needed
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtpe.intrallianz.es");
		properties.put("mail.smtp.port", "25");

		//		// Get system properties
		//		Properties properties = System.getProperties();
		//		// Setup mail server
		//		properties.setProperty("mail.smtp.host", "smtpe.intrallianz.es");
		//		properties.put("mail.smtp.port", "25");
		//		properties.put("mail.smtp.auth", "false");

		//		properties.setProperty("mail.user", "PLACE YOUR SMTP USERNAME");
		//		properties.setProperty("mail.password", "PLACE YOUR SMTP PASSWORD");
		//properties.put("mail.debug", "true");

		System.err.println('properties')

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try{
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(recipient));

			message.setSubject(subject);
			message.setText(content);

			Transport trnsport;
			trnsport = session.getTransport("smtp");
			trnsport.connect(null, properties.getProperty("mail.password"));
			message.saveChanges();
			trnsport.sendMessage(message, message.getAllRecipients());
			trnsport.close();

			//		  Transport.send(message);

			System.out.println("Sent message successfully....");
		}catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}









	//MassaEmail massa = new MassaEmail();

	//createXmlRadarLiveRequestAuto(massa, 'C:\\Users\\T_DVLopes\\Downloads\\UA_RevisedRanger.pdf', 'EmailPDF');
	//createXmlRadarLiveRequestAuto(listCasoDeTestePlanilha, 'C:\\Users\\T_DVLopes\\Downloads\\UA_RevisedRanger.pdf', 'EmailPDF');

	/*
	 * //AgendaEmail oAgendaEmail = new AgendaEmail();
	 oAgendaEmail.ID_SISTEMA = "_GESTAO_RISCOS_";
	 oAgendaEmail.TP_FORMAT = AgendaEmail.FormatoEmail.HTML;
	 //oAgendaEmail.DS_ASSUNTO = string.Format("Allianz Saúde - Relatório Gerencial: {0}-{1} Período: {2} a {3}", apolice.ID_APOLICE.ToString(), apolice.DS_RAZAO_SOCIAL, dt_inicial.ToString("MMM/yyyy"), dt_final.ToString("MMM/yyyy"));
	 oAgendaEmail.DS_ASSUNTO = String.Format("ABC", apolice, apolice, DataEnvio, dt_final);
	 oAgendaEmail.DS_MENSAGEM = FormataMsg(apolice, DataEnvio, dt_final);
	 oAgendaEmail.DS_ENDER_PARA = 'danillo.inmetricsext@allpronet.com.br';
	 oAgendaEmail.DS_ENDER_CC = 'denilson.inmetricsext@allpronet.com.br';
	 oAgendaEmail.DS_ENDER_CO = 'priscila.gtisolutionext@allpronet.com.br';
	 AgendaEmail_SVC.Criar(oAgendaEmail);
	 response = AgendaEmail_SVC.Send(oAgendaEmail.ID_MAIL, anexos);
	 */

}

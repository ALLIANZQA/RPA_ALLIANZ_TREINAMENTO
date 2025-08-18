package sendEmail;

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
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import internal.GlobalVariable;
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart
import javax.mail.util.ByteArrayDataSource
import javax.mail.*;

import java.util.List
import java.util.Properties;
import javax.activation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import templateMessage.AutoTemplateMessage
import templateMessage.BackOfficeTemplateMessage
import sendEmail.MessageRule

public class MessageFactory extends ConfigParameters{

	protected static Message message;
	protected static Multipart multipart;
	protected static MimeBodyPart imagePart;

	/**
	 * @author ALZ_ServiceQA
	 * @param sender
	 * @param pwrsender
	 * @param emaildestination
	 * @param subject
	 * @param messagebory
	 */
	public MessageFactory(String sender, String pwrsender, String emaildestination){
		this.sender = sender;
		this.pwrsender = pwrsender;
		this.emaildestination = emaildestination;
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param config
	 */
	public void createMessageFiles(Properties config, List<String> anexos ,String ramo ){
		try{
			addContext(sender, pwrsender, emaildestination, config );
			addMessagesMultFiles(ramo,anexos);
			transport();
			System.err.println("Create Message Files Done !");
		} catch (MessagingException e){
			throw new RuntimeException();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param config
	 */
	public void createAllMessageFiles(Properties config, List<String> anexos ,String ramo, String totalAlert, String totalPassed, String messagesAlerts, String messagesPassed, String startHour, String endHours, String totalHours, String hostName, String totalRegistros, String porcentagem, String porcentagemDeAlertas, String dataInicio, String dataFinal, String assunto){
		try{
			addContext(sender, pwrsender, emaildestination, config );
			addAllMessagesMultFiles(anexos, ramo,totalAlert, totalPassed, messagesAlerts, messagesPassed, startHour, endHours, totalHours, hostName, totalRegistros, porcentagem, porcentagemDeAlertas, dataInicio, dataFinal , assunto );
			transport();
			System.err.println("Create Message Files Done !");
		} catch (MessagingException e){
			throw new RuntimeException();
		}
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param config
	 */
	public void createMessageFilesCorretor(Properties config, List<String> anexos ,String ramo ,String tomador, String apolice){
		try{
			addContext(sender, pwrsender, emaildestination, config );
			addMessagesMultFilesCorretor(anexos,ramo ,tomador,apolice);
			transport();
			System.err.println("Create Message Files Done !");
		} catch (MessagingException e){
			throw new RuntimeException();
		}
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param config
	 */
	public void createMessageFilesSeguroFuncionario(Properties config, List<String> anexos ,String ramo, String assunto){
		try{
			addContext(sender, pwrsender, emaildestination, config );
			addMessagesMultFilesSegurFuncionario(ramo,anexos, assunto);
			transport();
			System.err.println("Create Message Files Done !");
		} catch (MessagingException e){
			throw new RuntimeException();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param config
	 */
	public void createMessageFilesReprocessaEmissaoApolice(Properties config, List<String> anexos ,String ramo, String dataAtual){
		try{
			addContext(sender, pwrsender, emaildestination, config );
			addMessagesMultFilesReprocessaEmissaoApolice(ramo,anexos, dataAtual);
			transport();
			System.err.println("Create Message Files Done !");
		} catch (MessagingException e){
			throw new RuntimeException();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param config
	 */
	public void createMessageFilesMigracaoDeDados(Properties config, List<String> anexos ,String ramo, String dataAtual){
		try{
			addContext(sender, pwrsender, emaildestination, config );
			addMessagesMultFilesReprocessaMigracaoDeDados(ramo,anexos, dataAtual);
			transport();
			System.err.println("Create Message Files Done !");
		} catch (MessagingException e){
			throw new RuntimeException();
		}
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static MimeBodyPart addFile(String path, String fileName){
		try{
			DataSource  source = new FileDataSource(path);
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			return messageBodyPart
		} catch (MessagingException e){
			throw new RuntimeException();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param config
	 */
	public void createMessage(Properties config, String ...args ){
		try{
			String[] valores = args
			addContext(sender, pwrsender, emaildestination, config );
			addMessage(valores);
			transport();
			System.err.println("Create Message Done !");
		} catch (MessagingException e){
			throw new RuntimeException();
		}
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param config
	 */
	public void createMessageMutFiles(Properties config, List<String> anexos,   String ...args){
		try{
			String[] valores = args
			addContext(sender, pwrsender, emaildestination, config );
			addMessagesMultFilesBody(anexos,valores);
			transport();
			System.err.println("Create Message Mut Files Done !");
		} catch (MessagingException e){
			throw new RuntimeException();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static transport(){
		try{
			Transport.send(message);
		} catch (MessagingException e){
			throw new RuntimeException();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sender
	 * @param pwrsender
	 * @param emaildestination
	 * @param subject
	 * @param messagebory
	 * @param config
	 * @return
	 */
	public static addContext(String sender, String pwrsender, String emaildestination, Properties config  ){
		try{
			Sessions sessions = new Sessions();
			message = new MimeMessage(sessions.create(sender, pwrsender, config));
			message.setFrom(new InternetAddress(sender));
			Address[] toUser = InternetAddress.parse(emaildestination);
			message.setRecipients(Message.RecipientType.TO, toUser);
		} catch (MessagingException e){
			throw new RuntimeException();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static addMessage(String[] valores){
		multipart = new MimeMultipart();
		imagePart = new MimeBodyPart();
		imagePart.setContent(MessageRule.messageRules(valores) , "text/html;charset=utf-8");
		multipart.addBodyPart(imagePart);
		message.setContent(multipart);
		message.saveChanges();
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static addMessagesMultFiles(String ramo, List<String> anexos){
		multipart = new MimeMultipart();
		addFiles(anexos)
		imagePart = new MimeBodyPart();
		imagePart.setContent(MessageRule.messageRules(ramo) , "text/html;charset=utf-8");
		multipart.addBodyPart(imagePart);
		message.setContent(multipart);
		message.saveChanges();
	}


	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static addAllMessagesMultFiles( List<String> anexos, String ...args ){
		String[] valores = args;
		multipart = new MimeMultipart();
		addFiles(anexos)
		imagePart = new MimeBodyPart();
		imagePart.setContent(MessageRule.messageAllRules(valores) , "text/html;charset=utf-8");
		multipart.addBodyPart(imagePart);
		message.setContent(multipart);
		message.saveChanges();
	}


	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static addMessagesMultFilesBody(List<String> anexos, String[] valores){
		multipart = new MimeMultipart();
		addFiles(anexos)
		imagePart = new MimeBodyPart();
		imagePart.setContent(MessageRule.messageRules(valores) , "text/html;charset=utf-8");
		multipart.addBodyPart(imagePart);
		message.setContent(multipart);
		message.saveChanges();
	}


	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static addMessagesMultFilesCorretor( List<String> anexos, String ...args ){
		String[] valores = args
		multipart = new MimeMultipart();
		addFiles(anexos)
		imagePart = new MimeBodyPart();
		imagePart.setContent(MessageRule.messageRules(valores) , "text/html;charset=utf-8");
		multipart.addBodyPart(imagePart);
		message.setContent(multipart);
		message.saveChanges();
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static addMessagesMultFilesSegurFuncionario(String ramo, List<String> anexos, String assunto){
		multipart = new MimeMultipart();
		addFiles(anexos)
		imagePart = new MimeBodyPart();
		imagePart.setContent(MessageRule.messageRules(ramo,assunto) , "text/html;charset=utf-8");
		multipart.addBodyPart(imagePart);
		message.setContent(multipart);
		message.saveChanges();
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static addMessagesMultFilesReprocessaEmissaoApolice(String ramo, List<String> anexos, String dataAtual){
		multipart = new MimeMultipart();
		addFiles(anexos)
		imagePart = new MimeBodyPart();
		imagePart.setContent(MessageRule.messageRules(ramo,dataAtual) , "text/html;charset=utf-8");
		multipart.addBodyPart(imagePart);
		message.setContent(multipart);
		message.saveChanges();
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static addMessagesMultFilesReprocessaMigracaoDeDados(String ramo, List<String> anexos, String msg){
		multipart = new MimeMultipart();
		addFiles(anexos)
		imagePart = new MimeBodyPart();
		imagePart.setContent(MessageRule.messageRules(ramo,msg) , "text/html;charset=utf-8");
		multipart.addBodyPart(imagePart);
		message.setContent(multipart);
		message.saveChanges();
	}

	/**
	 * @author T_DCDias
	 * @param anexos
	 * @return
	 */
	public static addFiles(List<String> anexos){
		for(int i = 0; i < anexos.size();i++){
			System.err.println("Path ["+anexos.get(i).toString()+"]\nNome ["+anexos.get(i+1).toString()+"]")
			multipart.addBodyPart(addFile( anexos.get(i).toString(), anexos.get(i+1).toString() ));
			i++;
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String addImageCabecalho(String imageFilePath, String width, String height ){
		return "<img src='cid:image_id1'<br>"
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String addImageRodape(String imageFilePath, String width, String height ){
		return "<img src='cid:image_id2'<br>"
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String addImageBody(String imageFilePath, String width, String height, String id ){
		return "<img src="+id+"<br>"
	}

	/**
	 * @author T_DCDias
	 * @param filename
	 * @return
	 */
	public static MimeBodyPart adicionaImagem(String filename, String ContentID, String imageid){
		MimeBodyPart imagePart = new MimeBodyPart();
		DataSource source = new FileDataSource(filename);
		imagePart.setDataHandler(new DataHandler(source));
		imagePart.setFileName(filename);
		imagePart.setHeader(ContentID, imageid);
		return imagePart;
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param config
	 */
	public void createMessageFilesCartasAvisoSantander(Properties config, List<String> anexos ,String ramo, String assunto){
		try{
			addContext(sender, pwrsender, emaildestination, config );
			addMessagesMultFilesCartasAvisoSantander(ramo,anexos, assunto);
			transport();
			System.err.println("Create Message Files Done !");
		} catch (MessagingException e){
			throw new RuntimeException();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static addMessagesMultFilesCartasAvisoSantander(String ramo, List<String> anexos, String assunto){
		multipart = new MimeMultipart();
		addFiles(anexos)
		imagePart = new MimeBodyPart();
		imagePart.setContent(MessageRule.messageRules(ramo,assunto) , "text/html;charset=utf-8");
		multipart.addBodyPart(imagePart);
		message.setContent(multipart);
		message.saveChanges();
	}


	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static addMessagesMultFilesError(String ramo, List<String> anexos, String assunto){
		multipart = new MimeMultipart();
		addFiles(anexos)
		imagePart = new MimeBodyPart();
		imagePart.setContent(MessageRule.messageRules(ramo,assunto) , "text/html;charset=utf-8");
		multipart.addBodyPart(imagePart);
		message.setContent(multipart);
		message.saveChanges();
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param config
	 */
	public void createMessageFilesError(Properties config, List<String> anexos ,String ramo, String assunto){
		try{
			addContext(sender, pwrsender, emaildestination, config );
			addMessagesMultFilesError(ramo,anexos, assunto);
			transport();
			System.err.println("Create Message Files Done !");
		} catch (MessagingException e){
			throw new RuntimeException();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static String templateBody(){
		String sb =
				"	<table border='0' cellpadding='0' cellspacing='0'><p>" +
				"		<tr><p>" +
				"			<td class='width-1' width='73'>&nbsp;</td><p>" +
				"			<td class='text1' style='font-family: Tahoma, Arial, Helvetica, SansSerif; color: #003680; font-size: 20px; font-weight: 300;line-height: 1.2; letter-spacing: -0.3px'><p><br><br><br>" +
				"				Prezados,<br>"+
				"				todos os dados foram processados.<br>"+
				"				Att.."+
				"			</td>" +
				"		</tr>" +
				"	</table>"
		return sb;
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static String templateBodyError(){
		String sb =
				"	<table border='0' cellpadding='0' cellspacing='0'><p>" +
				"		<tr><p>" +
				"			<td class='width-1' width='73'>&nbsp;</td><p>" +
				"			<td class='text1' style='font-family: Tahoma, Arial, Helvetica, SansSerif; color: #003680; font-size: 20px; font-weight: 300;line-height: 1.2; letter-spacing: -0.3px'><p><br><br><br>" +
				"				Prezados,<br>"+
				"				processo não concluido, precisa de análise.<br>"+
				"				Att.."+
				"			</td>" +
				"		</tr>" +
				"	</table>"
		return sb;
	}
}


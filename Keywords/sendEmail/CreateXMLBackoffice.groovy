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

import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult




import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format
import org.jdom2.output.XMLOutputter;
import bean.MassaEmail

import internal.GlobalVariable
import java.text.SimpleDateFormat

public class CreateXMLBackoffice extends Xml{


	/**
	 * @author T_DCDias
	 * @param casoDeTeste
	 * @param pathEvidencia
	 * @return
	 */
	public static createXmlRadarLiveRequestAuto(MassaEmail massaEmail, String pathEvidencia, String tipoDeSeguro){
		try{
			Document doc = new Document();
			Namespace ns1 = Namespace.getNamespace("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
			Namespace ws = Namespace.getNamespace("ws", "http://drswbo05.allianz.com.br/V1_00_00/ws");
			Namespace sch = Namespace.getNamespace("sch", "http://drswbo05.allianz.com.br/V1_00_00/schemas");
			Element root = new Element("Envelope", ns1);
			root.addNamespaceDeclaration(sch);
			root.addNamespaceDeclaration(ws);
			Element body = new Element("Body", ns1);
			Element cotacaoRequest = new Element("cotacaoRequest",ws);
			body.addContent(cotacaoRequest);

			cotacaoRequest = XMLSelectionFlow.selectionFlowAuto(tipoDeSeguro, cotacaoRequest, massaEmail, sch);
			root.addContent(body);
			doc.setRootElement(root);

			XMLOutputter xout = new XMLOutputter();
			geraBoryRquestXML(xout, massaEmail, pathEvidencia, doc, tipoDeSeguro);
			return 	xout.outputString(doc);
		} catch (Exception e){
			println e;
			return null
		}
	}

	/**
	 * @author T_DCDias
	 * @param xoutMassaEmail
	 * @param casoDeTeste
	 * @param pathEvidencia
	 * @param doc
	 * @return
	 */
	public static geraBoryRquestXML(XMLOutputter xout, MassaEmail massaEmail, String pathEvidencia, Document doc, String tipoDeSeguro){
		try {
			Format f = Format.getPrettyFormat();
			f.setEncoding("UTF-8");
			f.setOmitDeclaration(true);
			xout.setFormat(f);
			OutputStream out = new FileOutputStream(new File(pathEvidencia+retornaNomeArquivoDataHoraXml(massaEmail.getCt())));
			xout.output(doc, out);
			System.out.println("[GERADO BODY REQUEST XML] - CENÁRIO DE TESTE ["+massaEmail.getCt()+" - "+tipoDeSeguro+"]");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author T_DCDias
	 * @param ct
	 * @return
	 */
	public static String retornaNomeArquivoDataHoraXml(String ct){
		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("ddMMyyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HHmmss").format(dataHoraAtual);
		String nomeArquivoFormatado = ct+"_REQUEST_"+"_"+data+"_"+hora+".xml"
		return nomeArquivoFormatado;
	}

	/**
	 * @author T_DCDias
	 * @param doc
	 * @return
	 */
	public static String toString(Document doc) {
		try {
			StringWriter sw = new StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			transformer.transform(new DOMSource(doc), new StreamResult(sw));
			return sw.toString();
		} catch (Exception ex) {
			throw new RuntimeException("Error converting to String", ex);
		}
	}

	/**
	 * @author T_DCDias
	 * @param doc
	 * @return
	 * @throws TransformerException
	 */
	public static String getStringFromDocument(Document doc) throws TransformerException {
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.transform(domSource, result);
		return writer.toString();
	}
}

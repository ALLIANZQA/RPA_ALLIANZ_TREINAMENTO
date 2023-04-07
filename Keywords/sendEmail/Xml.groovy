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
import internal.GlobalVariable
import bean.MassaEmail
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format
import org.jdom2.output.XMLOutputter;
public class Xml extends XMLCommandRules{

	/**
	 * @author T_DCDias
	 * @param casoDeTeste
	 * @param sch
	 * @param sUsuario
	 * @param sParceiro
	 * @param sMediador
	 * @return
	 */
	public static Element autenticacaoXml(MassaEmail casoDeTeste, Namespace sch, String sUsuario, String sParceiro, String sMediador){
		Element autenticacao = new Element("autenticacao", sch);
		Element usuario = new Element("usuario", sch);
		Element parceiro = new Element("parceiro", sch);
		Element mediador = new Element("mediador", sch);

		usuario.setText(sUsuario);
		parceiro.setText(sParceiro);
		mediador.setText(sMediador);

		autenticacao.addContent(usuario);
		autenticacao.addContent(parceiro);
		autenticacao.addContent(mediador);

		return autenticacao;
	}
	
	
	

	public static Element sendEmail(Element emailRequest, MassaEmail emailMassa, Namespace sch){
		autenticacao(emailMassa);

		emailRequest.addContent(autenticacaoXml(emailMassa, sch, emailMassa.getUsuario(), emailMassa.getParceiro(), emailMassa.getMediador()));
		return emailRequest;
	}
}





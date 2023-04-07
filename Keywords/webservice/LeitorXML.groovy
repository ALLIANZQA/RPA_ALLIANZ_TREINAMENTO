package webservice

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import bean.CasoDeTeste
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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


import internal.GlobalVariable
import javax.xml.transform.Source
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;


public class LeitorXML {

	private static String message;

	public static String transformerXML(String xml){
		try{

			Source xmlInput = new StreamSource(new StringReader(xml));
			StreamResult xmlOutput = new StreamResult(new StringWriter());
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//Formata a saida
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(xmlInput, xmlOutput);
			message = xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
		} catch (TransformerException e) {
			message = null;
		}

		System.err.println("Message "+ message)

		return message;
	}



	public static List<CasoDeTeste> carrega(InputStream input) {
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("pacote", CasoDeTeste.class);
		xstream.addImplicitCollection(CasoDeTeste.class, "pacote")
		return (List<CasoDeTeste>) xstream.fromXML(input);
	}


	public static carregaXmlTest(){

		//String xmlfile = 	'<list><casodeteste><ct>33335</ct><usuario>19</usuario></casodeteste><casodeteste><ct>33336</ct><usuario>20</usuario></casodeteste></list>'
		String xmlfile ='<list><pacote><codigoPacote>0034</codigoPacote><descricaoPacote>Compacto 1</descricaoPacote><formaPagamento>1</formaPagamento><quantidadeParcelas>1</quantidadeParcelas><valorPrimeiraParcela>2123.44</valorPrimeiraParcela><valorTotalParcelado>2123.44</valorTotalParcelado><valorTotalParcelaUnica>2123.44</valorTotalParcelaUnica><valorIof>0</valorIof><valorAdicionalFracionamento>0</valorAdicionalFracionamento><coberturas><cobertura><codigoCobertura>1</codigoCobertura><descricaoCobertura>Casco</descricaoCobertura><valorPremio>1564.91</valorPremio><valorFranquia>1852.53</valorFranquia><valorLMI>52865.00</valorLMI></cobertura><cobertura><codigoCobertura>2</codigoCobertura><descricaoCobertura>RCF Danos Materiais</descricaoCobertura><valorPremio>274.82</valorPremio><valorFranquia>0.00</valorFranquia><valorLMI>50000.00</valorLMI></cobertura><cobertura><codigoCobertura>3</codigoCobertura><descricaoCobertura>RCF Danos Corporais</descricaoCobertura><valorPremio>45.32</valorPremio><valorFranquia>0.00</valorFranquia><valorLMI>50000.00</valorLMI></cobertura><cobertura><codigoCobertura>4</codigoCobertura><descricaoCobertura>RCF Danos Morais</descricaoCobertura><valorPremio>10.97</valorPremio><valorFranquia>0.00</valorFranquia><valorLMI>10000.00</valorLMI></cobertura><cobertura><codigoCobertura>7</codigoCobertura><descricaoCobertura>APP Morte</descricaoCobertura><valorPremio>40.74</valorPremio><valorFranquia>0.00</valorFranquia><valorLMI>20000.00</valorLMI></cobertura><cobertura><codigoCobertura>8</codigoCobertura><descricaoCobertura>APP Invalidez</descricaoCobertura><valorPremio>40.74</valorPremio><valorFranquia>0.00</valorFranquia><valorLMI>20000.00</valorLMI></cobertura></coberturas></pacote><pacote><codigoPacote>0035</codigoPacote><descricaoPacote>Compacto 2</descricaoPacote><formaPagamento>1</formaPagamento><quantidadeParcelas>1</quantidadeParcelas><valorPrimeiraParcela>2185.96</valorPrimeiraParcela><valorTotalParcelado>2185.96</valorTotalParcelado><valorTotalParcelaUnica>2185.96</valorTotalParcelaUnica><valorIof>0</valorIof><valorAdicionalFracionamento>0</valorAdicionalFracionamento><coberturas><cobertura><codigoCobertura>1</codigoCobertura><descricaoCobertura>Casco</descricaoCobertura><valorPremio>1564.91</valorPremio><valorFranquia>1852.53</valorFranquia><valorLMI>52865.00</valorLMI></cobertura><cobertura><codigoCobertura>2</codigoCobertura><descricaoCobertura>RCF Danos Materiais</descricaoCobertura><valorPremio>319.14</valorPremio><valorFranquia>0.00</valorFranquia><valorLMI>100000.00</valorLMI></cobertura><cobertura><codigoCobertura>3</codigoCobertura><descricaoCobertura>RCF Danos Corporais</descricaoCobertura><valorPremio>48.25</valorPremio><valorFranquia>0.00</valorFranquia><valorLMI>100000.00</valorLMI></cobertura><cobertura><codigoCobertura>4</codigoCobertura><descricaoCobertura>RCF Danos Morais</descricaoCobertura><valorPremio>21.94</valorPremio><valorFranquia>0.00</valorFranquia><valorLMI>20000.00</valorLMI></cobertura><cobertura><codigoCobertura>7</codigoCobertura><descricaoCobertura>APP Morte</descricaoCobertura><valorPremio>40.74</valorPremio><valorFranquia>0.00</valorFranquia><valorLMI>20000.00</valorLMI></cobertura><cobertura><codigoCobertura>8</codigoCobertura><descricaoCobertura>APP Invalidez</descricaoCobertura><valorPremio>40.74</valorPremio><valorFranquia>0.00</valorFranquia><valorLMI>20000.00</valorLMI></cobertura>></coberturas></pacote></list>'

		InputStream xml = new ByteArrayInputStream(xmlfile.getBytes())
		List<CasoDeTeste> xmlList = carrega(xml)

		for(int i = 0; i < xmlList.size(); i++){
			//System.err.println("CT :"+xmlList[i].getCt() +" USUARIO: "+xmlList[i].getUsuario())
			System.err.println("Codigo Pacote ["+xmlList[i].getCodigoPacote() +"] Descricao Pacote ["+xmlList[i].getDescricaoPacote()+"] Valor PrimeiraParcela ["+xmlList[i].getValorPrimeiraParcela()+"] Descricao Cobertura ["+xmlList[i].getDescricaoCobertura()+"]")
		}
	}
}

package webservice;

import auxiliares.FileInput
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

@SuppressWarnings("deprecation")
public class RequestSoap {

	private static HttpClient httpClient;

	/**
	 * Luiz
	 * @param fileRequestXml
	 * @param urlResquest
	 * @return
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws XMLStreamException
	 * @throws JAXBException
	 * Efetua 'request' 
	 */
	public static String request(String fileRequestXml, String urlResquest)
			throws UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException,
	CertificateException, FileNotFoundException, IOException, XMLStreamException, JAXBException {

		//Configura Valores de configuração de SSL
		ConfigSsl setSsl = new ConfigSsl();

		//Create a StringEntity
		String bodyRequest = FileInput.retornaXmlFileToString(fileRequestXml);
		StringEntity stringEntity = new StringEntity(bodyRequest, "UTF-8");
		stringEntity.setChunked(true);

		//Request Parameter
		HttpPost httpPost = new HttpPost(urlResquest);
		httpPost.setEntity(stringEntity);
		httpPost.setHeader("Content-Type", "text/xml");
		httpPost.setHeader("SOAPAction", "");


		//HttpClient efetua o httpPost
		httpClient = new DefaultHttpClient();
		httpClient = HttpClients.custom().setSSLSocketFactory(setSsl.connectionSSL()).build();
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String strResponde = EntityUtils.toString(entity);
		//Imprimi XMl
		//System.out.println(strResponde);
		return strResponde;
	}



	/**
	 * Luiz
	 * @param fileRequestXml
	 * @param urlResquest
	 * @return
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws XMLStreamException
	 * @throws JAXBException
	 * Efetua 'request'
	 */
	public static String requestStringXml(String fileRequestXml, String urlResquest)
			throws UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException,
	CertificateException, FileNotFoundException, IOException, XMLStreamException, JAXBException {

		//Configura Valores de configuração de SSL
		ConfigSsl setSsl = new ConfigSsl();

		//Create a StringEntity
		String bodyRequest = fileRequestXml;
		StringEntity stringEntity = new StringEntity(bodyRequest, "UTF-8");
		stringEntity.setChunked(true);

		//Request Parameter
		HttpPost httpPost = new HttpPost(urlResquest);
		httpPost.setEntity(stringEntity);
		httpPost.setHeader("Content-Type", "text/xml");
		httpPost.setHeader("SOAPAction", "");


		//HttpClient efetua o httpPost
		httpClient = new DefaultHttpClient();
		httpClient = HttpClients.custom().setSSLSocketFactory(setSsl.connectionSSL()).build();
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		String strResponde = EntityUtils.toString(entity);
		//Imprimi XMl
		//System.out.println(strResponde);
		return strResponde;
	}
}

//package webservice;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.security.KeyManagementException;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.UnrecoverableKeyException;
//import java.security.cert.CertificateException;
//
//import javax.net.ssl.KeyManager;
//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.TrustManagerFactory;
//
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//
//public class ConfigSsl {
//
//	@SuppressWarnings("unused")
//	public SSLConnectionSocketFactory connectionSSL() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, UnrecoverableKeyException, KeyManagementException {
//
//		KeyStore clientStore = KeyStore.getInstance("PKCS12");
//		clientStore.load(new FileInputStream(new File("C:\\Users\\Public\\QA_ALLIANZ\\Certificado\\BP002621_INT.pfx")),
//				"allianz2018".toCharArray());
//
//		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//		kmf.init(clientStore, "allianz2018".toCharArray());
//		KeyManager[] kms = kmf.getKeyManagers();
//
//		KeyStore trustStore = KeyStore.getInstance("JKS");
//		trustStore.load(new FileInputStream("C:\\Users\\Public\\QA_ALLIANZ\\Certificado\\cacerts"), "changeit".toCharArray());
//
//		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//		tmf.init(trustStore);
//		TrustManager[] tms = tmf.getTrustManagers();
//
//		final SSLContext sslContext = SSLContext.getInstance("TLS");
//		sslContext.init(kms, tms, new SecureRandom());
//		SSLContext.setDefault(sslContext);
//
//		//SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"}, null, new NoopHostnameVerifier());
//
//		String[] tls = ["TLSv1", "TLSv1.1", "TLSv1.2"];
//
//		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, tls, null, new NoopHostnameVerifier());
//		return sslsf;
//
//	}
//}

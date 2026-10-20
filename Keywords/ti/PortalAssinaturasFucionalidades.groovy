package ti

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.time.Duration
import java.time.Instant

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
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import rpacommands.rpaCommands
import auxiliares.ExtractDates
import auxiliares.StringFormatter
import auxiliares.FileInput
import sendEmail.emailDinamico;
import auxiliares.timer;
import auxiliares.GetHost;
import auxiliares.CurrencyMonetary;
import auxiliares.GeneratorPDF;

public class PortalAssinaturasFucionalidades extends rpaCommands{
	
	private static TestData data
	private static String testDataPath = 'Data Files/TI/PortalAssinaturasData'
	private static String path
	private static String alerta
	private static int cellCheck = 38;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "PortalAssiantoras.xlsx"
	private static int cellCheckAlert = 40;
	private static boolean enviado = true;
	private static String xpath;
	private static String tela;
	private static String operacao;
	
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		path = data.sourceUrl
	}
	
	public static fluxoPortalAssinaturas(String aba, String a){
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		initDataFiles()
		fluxoCompletoPortalAssinaturas(aba,a)
		reprocessaFalhas(aba,a)
		FileInput.atualizaLog(Instant.now().toString(), pathFile)
		List<String> times = rpaCommands.convertToInstant(pathFile);
		String dataInicio = times[0];
		String horaInicial =  times[1];
		timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
		   enviaEmailPortalAssinaturas(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
		} catch (Exception e){
		}
	}
	
	
	public static void fluxoCompletoPortalAssinaturas(String aba, String a){
		
		
		
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba, String a){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoPortalAssinaturas(aba,a)
			reprocessaFalhas(aba,a)
		}
	}
	
	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static boolean validaDados(int cellStatus){
		boolean value
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(cellStatus, i).equals('Processar')) {
				value = true
				break;
			} else {
				value = false
			}
		}
		System.err.println('Data ['+value+']')
		return value
	}
	
	/**
	 * @author Alz_sup54955
	 * @param tipoAba
	 * @param timeLapnse
	 * @param dataInicio
	 * @param dataFinal
	 * @param horaInicial
	 * @param horaFinal
	 * @return
	 */
	public static enviaEmailPortalAssinaturas(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
		System.out.println()
	 try{
		 if(!validaDados(cellCheck)){
			String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\ti\\PortalAssinaturas\\ConfiguracoesPortalAssinaturas.txt";
			lerConfigurações(pathTxtEmailConfig)
			System.out.println(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			// FORMATA SAIDA DO TEMPO DE EXECUÇÃO
			long segds = timeLapnse.toSeconds();
			String hours = timeLapnse.toHours()+"h "+
						timer.converteMinutos(timeLapnse.toHours(), timeLapnse.toMinutes())+"m "+
						timer.converteSegundos(timeLapnse.toMinutes(),segds)+"s";
			// ENVIO DE E-MAIL
			//DADOS DO DOCUMENTO
			initDataFiles();
			// ENVIA E-MAIL DINAMICO
			emailDinamico.enviaEmailDinamico(sender, emaildestination, tipoAba, path, docName, emailDinamico.listaDeAlertas(data, cellCheckAlert)  , hours, horaInicial, horaFinal,  GetHost.getHostname() , dataInicio, dataFinal, "" );
			FileInput.deletaArquivoPdf(pathFile);
		 }
	  } catch (Exception e){}
	}
	
	public static String dataAtual(){
		String dt = "São Paulo "+ExtractDates.dataAtualizadaDia()+" de "+
								 ExtractDates.retornaMesLiteral(ExtractDates.dataAtualizadaMes())+" de "+
								 ExtractDates.dataAtualizadaAno()
		return dt;
	}
	
	public static criarPDF(){
		String corretor = "MASTER TELES CORRETORA DE SEGUROS EIRELI"
		String ct = "Termo de Aceite de Co-corretagem Seguralta_"+corretor
		String path = "C://Users//Public"
		String msg2 = "DECLARAÇÃO DE CO-CORRETAGEM"
		String msg3="A corretora MASTER TELES CORRETORA DE SEGUROS EIRELI, inscrita no CNPJ/MF nº "+
					"290279300001-10, com sede na AV DR ALFREDO AMANCIO FILHO, 1, na Cidade de "+
					"PARAUAPEBAS, Estado de SAO PAULO, CEP: 68515-000, declara, para os devidos fins, seu desejo "+
					"e ciência na emissão de apólices de seguro emitidas pela Allianz Seguros S/A, sociedade "+
					"seguradora inscrita no CNPJ/MF n° 61.573.796/0001-66, com sede na Rua Eugênio de Medeiros, "+
					"303, 1º andar-parte, 2º ao 7º andar, 15º e 16º andares, Pinheiros, na Cidade de São Paulo, Estado "+
					"de São Paulo, CEP 05425-000, em que esta atuar como corretora com a co-corretagem com a "+
					"corretora SGA CORRETORA DE SEGUROS LTDA, inscrita no CNPJ/MF n° 02.671.673/0001-07, com "+
					"sede na AV WALDEMAR HADDAD, 801, na Cidade de SAO JOSE DO RIO PRETO, Estado de SÃO PAULO, "+
					"CEP: 15093-300, atuando esta como líder nas apólices ora emitidas , concordando, ainda, com os respectivos "+
					"percentuais de comissão de corretagem."
		GeneratorPDF.createDocumentDeclaracaoCoCorretagem(ct, path)
		GeneratorPDF.adicionaTextoCenterDeclaracaoCoCorretagem(msg2, 2)
		GeneratorPDF.adicionaTextoJUSTIFIEDDeclaracaoCoCorretagem(msg3, 5)
		GeneratorPDF.adicionaTextoCenterDeclaracaoCoCorretagem(dataAtual(), 5)
		GeneratorPDF.rodapeDeclaracaoCoCorretagem("MDL CORRETORA DE SEGUROS", "LTDACORRETORA EMISSORA")
		GeneratorPDF.closeDocument()
	}
}

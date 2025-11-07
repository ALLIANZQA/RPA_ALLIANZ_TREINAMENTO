import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.time.Duration
import java.time.Instant
import java.util.List

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import auxiliares.StringFormatter
import rpacommands.rpaCommands
import auxiliares.ExtractDates
import auxiliares.GetHost;
    
import auxiliares.timer;
import sendEmail.emailDinamico;

import com.kms.katalon.core.testdata.TestData


Instant start;
Instant end;
Duration timeLapnse;
	
// FORNECE A HORA INICIAL DO PROCESSAMENTO
start = Instant.now();
String startTime =  ExtractDates.horaAtualizada();
WebUI.delay(10);

// FORNECE A HORA FINAL DO PROCESSAMENTO
end =  Instant.now();
String endTime =  ExtractDates.horaAtualizada();


timeLapnse = Duration.between(start, end);



// FORMATA SAIDA DO TEMPO DE EXECUÇÃO 
long segds = timeLapnse.toSeconds();
String hours = timeLapnse.toHours()+"h "+ 
                timer.converteMinutos(timeLapnse.toHours(), timeLapnse.toMinutes())+"m "+
				timer.converteSegundos(timeLapnse.toMinutes(),  segds  )+"s";

long h = 3;
long ms = 191;
long sgs = 11516.423000000;
String hoursX = h+"h "+ timer.converteMinutos(h, ms)+"m "+timer.converteSegundos(ms,sgs)+"s";
	
// OBTEM O NOME DO SERVIDOR 
String hostName = GetHost.getHostname();

//DATA DE INICIO E FIM DE PROCESSO
String dataInicio = ExtractDates.dataAtual(); 
String dataFinal = ExtractDates.dataAtual();
	
// ANEXO DO E-MAIL
String tipoAba = 'DuplicadoRecibos'
String pathFile = 'C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Sinistro\\DescontosEspecíficosSinistro.xlsx'
String file = "DescontosEspecíficosSinistro.xlsx"

// ENVIO DE E-MAIL 
String sender = "denilson.inmetricsext@allpronet.com.br";
String emaildestination = "denilson.inmetricsext@allpronet.com.br, andre.antunes@allianz.com.br"

//DADOS DO DOCUMENTO 
String testDataPath = 'Data Files/Sinistro/DescontosEspecíficosSinistroData'
TestData data = findTestData(testDataPath);

// ENVIA E-MAIL DINAMICO
emailDinamico.enviaEmailDinamico(sender, emaildestination, tipoAba, pathFile, file,  emailDinamico.listaDeAlertas(data, 13), hours, startTime, endTime, hostName, dataInicio, dataFinal, "")

System.out.println("timeLapnse >>>> "+segds)


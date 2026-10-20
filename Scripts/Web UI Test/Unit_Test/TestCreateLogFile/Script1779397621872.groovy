import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.time.Duration
import java.time.Instant

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


import auxiliares.FileInput
import auxiliares.ExtractDates
import rpacommands.rpaCommands

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption
import java.nio.file.Path;


System.out.println();

String pathFile = "C:\\Users\\Public\\RPA_ALLIANZ\\LogTimeLapse\\logtimelapse.txt"

//System.out.println(FileInput.validaExistenciaDeArquivo(pathFile));


Instant start;
Instant end;
Duration timeLapnse;


System.out.println();

if(FileInput.validaExistenciaDeArquivo(pathFile)){
   FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
   FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
   FileInput.atualizaLog( Instant.now().toString(), pathFile)
}

// Neste ponto tem as funcionalidades do robô 

System.out.println();


FileInput.atualizaLog(Instant.now().toString(), pathFile)
List<String> times = rpaCommands.convertToInstant(pathFile);
String dataInicio = times[0];
String horaInicial =  times[1];
timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
String dataFinal = ExtractDates.dataAtual();
String horaFinal =  ExtractDates.horaAtualizada();
FileInput.deletaArquivoPdf(pathFile)

System.out.println("["+times[0]+"] ["+times[1]+"] ["+ dataFinal+"] ["+ horaFinal+"] ["+times[2]+"] ["+times[3]+"] ["+timeLapnse+"]");

System.out.println();






package backoffice

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
import bean.MassaDados;
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel
import internal.GlobalVariable

public class fluxoEnvioPendenciaGo extends backofficeFuncionalidades{




	public static int loopEnvioPendencia(List<MassaDados> listaExcel, ExcelCopiado, tamanhoList, countEnviado){
		iniciaAplicacao();

		for(int i = 0; i < tamanhoList; i++){
			try{
				pendencias(listaExcel.get(i).getApolice(),listaExcel.get(i).getRamo(), listaExcel.get(i).getCodigoCorretor(), listaExcel.get(i).getBloqueio());
				switchAtualizarExcel.switchUpdateExcel('pendenciaGO', ExcelCopiado ,listaExcel.get(i).getApolice(), Integer.parseInt(listaExcel.get(i).getLinha()));
				countEnviado++
				i++
			} catch (Exception e) {
				System.err.println('\n Falha ao processar Apolice ['+ listaExcel.get(i).getApolice()+']\n');
				iniciaAplicacao();
				i++
			}
		}

		return countEnviado;
	}


	public static void iniciaAplicacao(){
		iniciaAplicacaoMatriz('MatrizProdução');
		realizaLoginMatrizProdução('userSenha');
		fluxoFichaGestao();
	}

	public static void pendencias(String apolice, String ramo, String codigoCorretor, String bloqueio){
		fluxoNotaAgenda(apolice);
		fluxoEnviarNotaAuto(ramo, codigoCorretor, bloqueio);
	}
}

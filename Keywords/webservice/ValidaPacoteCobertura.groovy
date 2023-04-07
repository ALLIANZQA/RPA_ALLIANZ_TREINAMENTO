package webservice

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import bean.Pacote
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

public class ValidaPacoteCobertura {
	
	private static boolean resultadoDoTeste = false
	private static boolean execucao = false
	private static WebServiceError mensagem = new WebServiceError();
	
	public static WebServiceError validarPacotesCoberturasWebServiceRadarLive(Pacote motorAntigo, Pacote motorNovo){
		
				//Inicializa a variavel resultado como false
				resultadoDoTeste = false;
		
				//Validação de Pacotes
				isError(motorAntigo.getDescricaoPacote().equals(motorNovo.getDescricaoPacote()))
				isError(motorAntigo.getFormaPagamento().equals(motorNovo.getFormaPagamento()))
				isError(motorAntigo.getQuantidadeParcelas().equals(motorNovo.getQuantidadeParcelas()))
				isError(motorAntigo.getValorPrimeiraParcela().equals(motorNovo.getValorPrimeiraParcela()))
				isError(motorAntigo.getValorTotalParcelado().equals(motorNovo.getValorTotalParcelado()))
				isError(motorAntigo.getValorTotalParcelaUnica().equals(motorNovo.getValorTotalParcelaUnica()))
				isError(motorAntigo.getValorIof().equals(motorNovo.getValorIof()))
				isError(motorAntigo.getValorAdicionalFracionamento().equals(motorNovo.getValorAdicionalFracionamento()))
		
				if(resultadoDoTeste){
					execucao = resultadoDoTeste
					mensagem.setMensagemPacote("Ocorreu um erro ou divergência nos dados do pacote.")
					mensagem.setStatusPacote("FAIL")
					//			throw new AutomacaoException(e)
				}else{
					mensagem.setMensagemPacote("Realizado a validação dos pacotes com sucesso")
					mensagem.setStatusPacote("PASSED")
				}
			
				resultadoDoTeste = false
		
				isError(motorAntigo.getCodigoCobertura().equals(motorNovo.getCodigoCobertura()))
				isError(motorAntigo.getDescricaoCobertura().equals(motorNovo.getDescricaoCobertura()))
				isError(motorAntigo.getValorPremio().equals(motorNovo.getValorPremio()))
				isError(motorAntigo.getValorFranquia().equals(motorNovo.getValorFranquia()))
				isError(motorAntigo.getValorLMI().equals(motorNovo.getValorLMI()))
		
				isError(motorAntigo.getCodigoCobertura2().equals(motorNovo.getCodigoCobertura2()))
				isError(motorAntigo.getDescricaoCobertura2().equals(motorNovo.getDescricaoCobertura2()))
				isError(motorAntigo.getValorPremio2().equals(motorNovo.getValorPremio2()))
				isError(motorAntigo.getValorFranquia2().equals(motorNovo.getValorFranquia2()))
				isError(motorAntigo.getValorLMI2().equals(motorNovo.getValorLMI2()))
		
				isError(motorAntigo.getCodigoCobertura3().equals(motorNovo.getCodigoCobertura3()))
				isError(motorAntigo.getDescricaoCobertura3().equals(motorNovo.getDescricaoCobertura3()))
				isError(motorAntigo.getValorPremio3().equals(motorNovo.getValorPremio3()))
				isError(motorAntigo.getValorFranquia3().equals(motorNovo.getValorFranquia3()))
				isError(motorAntigo.getValorLMI3().equals(motorNovo.getValorLMI3()))
		
				isError(motorAntigo.getCodigoCobertura4().equals(motorNovo.getCodigoCobertura4()))
				isError(motorAntigo.getDescricaoCobertura4().equals(motorNovo.getDescricaoCobertura4()))
				isError(motorAntigo.getValorPremio4().equals(motorNovo.getValorPremio4()))
				isError(motorAntigo.getValorFranquia4().equals(motorNovo.getValorFranquia4()))
				isError(motorAntigo.getValorLMI4().equals(motorNovo.getValorLMI4()))
		
				isError(motorAntigo.getCodigoCobertura5().equals(motorNovo.getCodigoCobertura5()))
				isError(motorAntigo.getDescricaoCobertura5().equals(motorNovo.getDescricaoCobertura5()))
				isError(motorAntigo.getValorPremio5().equals(motorNovo.getValorPremio5()))
				isError(motorAntigo.getValorFranquia5().equals(motorNovo.getValorFranquia5()))
				isError(motorAntigo.getValorLMI5().equals(motorNovo.getValorLMI5()))
		
				if(resultadoDoTeste){
					execucao = resultadoDoTeste
					mensagem.setStatusCobertura("FAIL")
					mensagem.setMensagemCobertura("Ocorreu um erro ou divergência nos dados do Cobertura.")
				}else{
					mensagem.setStatusCobertura("PASSED")
					mensagem.setMensagemCobertura("Realizado a validação das coberturas com sucesso.")
				}
		return mensagem
	}
		
	public static void isError(boolean error){
	  if(error == false){
			resultadoDoTeste = true
		}
	}
	
}

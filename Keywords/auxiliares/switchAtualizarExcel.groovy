package auxiliares

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
import bean.MassaDados
import internal.GlobalVariable

public class switchAtualizarExcel extends updateExcel {


	public static switchUpdateExcel(String nomeRPA, String caminho_Excel, String valor, int posicao){
		try{

			List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
			switch (nomeRPA) {

				case 'pendenciaGO':
					atualizaExcelPendenciaGo(caminho_Excel ,valor, posicao);
					println "pendenciaGO"
					break;

				case 'Agendamento':
					atualizaExcelAgendamentoInspecoes(caminho_Excel ,valor, posicao);
					println "Agendamento"
					break;

				case 'CotacoesEspeciais':
					atualizaRenovacoesCotacoesEspeciais(caminho_Excel ,valor, posicao);
					println "CotacoesEspeciais"
					break;
				case 'FIEL':
					atualizaDesbloqueioPropostaAgroFiel(caminho_Excel ,valor, posicao);
					println "DesbloqueioPropostaAgroFiel"
					break;

				case 'SeguroRenovResidencia':
					atualizaExcelSeguroResidencia(caminho_Excel ,valor, posicao);
					println "SeguroRenovResidencia"
					break;

				case 'SeguroRenovAuto':
					atualizaExcelSeguroRenovAuto(caminho_Excel ,valor, posicao);
					println "SeguroRenovAuto"
					break;

				case 'apoliceTransportador':
					atualizaExcelEmissaoApoliceTransporte(caminho_Excel ,valor, posicao);
					println "apoliceTransportador"
					break;

				case 'DesbloqueioProposta':
					atualizaDesbloqueioProposta(caminho_Excel ,valor, posicao);
					println "DesbloqueioProposta"
					break;

				case 'InspecaoRisco':
					atualizaExcelInspecaoRisco(caminho_Excel ,valor, posicao);
					println "InspecaoRisco"
					break;
			}
		} catch (Exception e){
		}
	}
}

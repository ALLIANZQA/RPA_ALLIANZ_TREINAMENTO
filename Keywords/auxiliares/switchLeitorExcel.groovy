package auxiliares

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.List

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

public class switchLeitorExcel extends tipoLeituraExcel {

	public static switchExcel(String caminhoExcel, String aba){
		try{

			List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
			switch (aba) {

				case 'Bloqueios':
					listMassaDados = leituraExcelPendenciaGoAuto(caminhoExcel, aba);
					println "Bloqueios"
					break;
				case 'Auto':
					listMassaDados = leituraExcelPendenciaGoAuto(caminhoExcel, aba);
					println "Auto"
					break;
				case 'EnvioPDF':
					listMassaDados = leituraExcelEnvioPDF(caminhoExcel, aba);
					println "EnvioPDF"
					break;
				case 'CotacoesEspeciais':
					listMassaDados = leituraExcelCotacoesEspeciais(caminhoExcel, aba);
					println "CotacoesEspeciais"
					break;
				case 'Agendamento':
					listMassaDados = leituraExcelAgendamentoInspecao(caminhoExcel, aba);
					println "Agendamento"
					break;
				case 'SeguroRenovResidencia':
					listMassaDados = leituraExcelSeguroFuncionarioRenovResidencia(caminhoExcel, aba);
					println "SeguroRenovResidencia"
					break;
				case 'SeguroRenovAuto':
					listMassaDados = leituraExcelSeguroFuncionarioRenovAuto(caminhoExcel, aba);
					println "SeguroRenovAuto"
					break;
				case 'apoliceTransportador':
					listMassaDados = leituraExcelEmissaoApoliceTransportador(caminhoExcel, aba);
					println "apoliceTransportador"
					break;
				case 'DesbloqueioProposta':
					listMassaDados = leituraExcelDesbloqueioProposta(caminhoExcel, aba);
					println "DesbloqueioProposta"
					break;
				case 'InspecaoRisco':
					listMassaDados = leituraExcelInspecaoRisco(caminhoExcel, aba);
					println "InspecaoRisco"
					break;
				case 'SegundaVia':
					listMassaDados = leituraExcelSegundaVia(caminhoExcel, aba);
					println "SegundaVia"
					break;
			}
			return listMassaDados;
		} catch (Exception e){
			e
			return null;
		}
	}

	/**
	 * @author BE54955
	 * @param caminhoExcel
	 * @param aba
	 * @return
	 */
	public static List<MassaDados> switchAnyExcel(String caminhoExcel, String aba){
		List<MassaDados> listMassaDados
		try{
			listMassaDados = new ArrayList<MassaDados>();
			listMassaDados = leituraExcel(caminhoExcel, aba);
			return listMassaDados;
		} catch (Exception e){
			e
			return null;
		}
	}
}

package auxiliares

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.util.List

import org.apache.poi.ss.usermodel.Cell

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

import bean.CasoDeTeste
import bean.MassaDados
import internal.GlobalVariable

public class XLSXSelection extends XLSXcellRules{


	/**
	 * @author T_DCDias
	 * @param cell
	 * @param massa
	 * @param aba
	 * @return
	 */
	public static MassaDados switchCellRules(Cell cell, MassaDados massa, String aba){
		switch (aba) {
			case 'EnvioPDF':
				return XLSXcellRulesBackOfficeDecorator.cellRulesBackOfficeMacroEmissõesCorporate(cell, massa);
				break;
			case 'Auto':
				return XLSXcellRulesBackOfficeDecorator.cellRulesEnvioAvisoPendênciaGOAuto(cell, massa);
				break;
			case 'userPssword':
				return XLSXcellRulesBackOfficeDecorator.cellRulesUserPssword(cell, massa);
				break;
			case 'Agendamento':
				return XLSXcellRulesBackOfficeDecorator.CellRulesAgendamentoInspecao(cell, massa);
				break;
			case 'Bloqueios':
				return XLSXcellRulesBackOfficeDecorator.cellRulesEnvioAvisoPendênciaGOAuto(cell, massa);
				break;
			case 'CotacoesEspeciais':
				return XLSXcellRulesBackOfficeDecorator.CellRulesCotacoesEspeciais(cell, massa);
				break;
			case 'SeguroRenovResidencia':
				return XLSXcellRulesBackOfficeDecorator.CellRulesRenovResidencia(cell, massa);
				break;
			case 'SeguroRenovAuto':
				return XLSXcellRulesBackOfficeDecorator.CellRulesRenovAuto(cell, massa);
				break;
			case 'apoliceTransportador':
				return XLSXcellRulesBackOfficeDecorator.CellRulesEmissaoApoliceTransporte(cell, massa);
				break;
			case 'DesbloqueioProposta':
				return XLSXcellRulesBackOfficeDecorator.CellRulesDesbloqueioProposta(cell, massa);
				break;
			case 'InspecaoRisco':
				return XLSXcellRulesBackOfficeDecorator.CellRulesInspecaoRisco(cell, massa);
				break;
			case 'SegundaVia':
				return XLSXcellRulesBackOfficeDecorator.CellRulesSegundaVia(cell, massa);
				break;
		}
	}
}

package auxiliares

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

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

import bean.MassaDados
import internal.GlobalVariable

public class XLSXcellRulesBackOfficeDecorator {


	/**
	 * @author T_DCDias
	 * @param cell
	 * @param massa
	 * @param aba
	 * @return
	 */
	public static MassaDados cellRulesBackOfficeMacroEmissõesCorporate(Cell cell, MassaDados massa){
		try{
			switch (cell.getColumnIndex()) {
				case 0:
					massa.setApolice(cell.getStringCellValue());
					break;
				case 1:
					massa.setAplica(cell.getStringCellValue());
					break;
				case 5:
					massa.setNomeCorretor(cell.getStringCellValue());
					break;
				case 6:
					massa.setNota(cell.getStringCellValue());
					break
				case 7:
					massa.setTxnota(cell.getStringCellValue());
					break
				case 9:
					massa.setRamol(cell.getStringCellValue());
					break
				case 10:
					massa.setEnvio(cell.getStringCellValue());
					break
				case 11:
					massa.setEmailde(cell.getStringCellValue());
					break;
				case 12:
					massa.setEmailpara(cell.getStringCellValue());
					break;
				case 13:
					massa.setStatus(cell.getStringCellValue());
					break;
				case 14:
					massa.setDocumento(cell.getStringCellValue());
					break;
				case 15:
					massa.setEmail(cell.getStringCellValue());
					break;
				case 17:
					massa.setLinha(cell.getStringCellValue());
					break;
			}
			return massa;
		} catch (Exception e){
			e
			return null;
		}
	}

	/**
	 * @author T_DCDias
	 * @param cell
	 * @param massa
	 * @return
	 */
	public static MassaDados cellRulesEnvioAvisoPendênciaGOAuto(Cell cell, MassaDados massa){
		try{
			switch (cell.getColumnIndex()) {
				case 0:
					massa.setApolice(cell.getStringCellValue());
					break;
				case 1:
					massa.setRamo(cell.getStringCellValue());
					break;
				case 4:
					massa.setCodigoCorretor(cell.getStringCellValue());
					break;
				case 5:
					massa.setBloqueio(cell.getStringCellValue());
					break;
				case 6:
					massa.setNota(cell.getStringCellValue());
					break;
				case 7:
					massa.setStatus(cell.getStringCellValue());
					break;
				case 8:
					massa.setLinha(cell.getStringCellValue());
					break;
			}
			return massa;
		} catch (Exception e){
			e
			return null;
		}
	}

	public static MassaDados cellRulesUserPssword(Cell cell, MassaDados massa){
		try{
			switch (cell.getColumnIndex()) {
				case 0:
					massa.setUser(cell.getStringCellValue());
					break;
				case 1:
					massa.setSenha(cell.getStringCellValue());
					break;
			}
			return massa;
		} catch (Exception e){
			e
			return null;
		}
	}

	public static MassaDados CellRulesAgendamentoInspecao(Cell cell, MassaDados massa){
		try{
			switch (cell.getColumnIndex()) {
				case 0:
					massa.setApolice(cell.getStringCellValue());
					break;
				case 1:
					massa.setAplica(cell.getStringCellValue());
					break;
				case 2:
					massa.setRamo(cell.getStringCellValue());
					break;
				case 11:
					massa.setStatus(cell.getStringCellValue());
					break;
				case 12:
					massa.setLinha(cell.getStringCellValue());
					break;
			}
			return massa;
		} catch (Exception e){
			e
			return null;
		}
	}

	public static MassaDados CellRulesCotacoesEspeciais(Cell cell, MassaDados massa){
		try{
			switch (cell.getColumnIndex()) {

				case 4:
					massa.setRamoModalidade(cell.getStringCellValue());
					break;
				case 8:
					massa.setApolice(cell.getStringCellValue());
					break;
				case 10:
					massa.setApoliceSucep(cell.getStringCellValue());
					break;
				case 13:
					massa.setDataFimVigenciaApolice(cell.getStringCellValue());
					break;
				case 16:
					massa.setCnpjCpf(cell.getStringCellValue());
					break;
				case 18:
					massa.setNomeRazaoSocial(cell.getStringCellValue());
					break;
				case 19:
					massa.setCodigoCorretor(cell.getStringCellValue());
					break;
				case 26:
					massa.setDataVencRisco(cell.getStringCellValue());
					break;
				case 33:
					massa.setAgrupamentoRamo(cell.getStringCellValue());
					break;
				case 34:
					massa.setStatus(cell.getStringCellValue());
					break;
				case 35:
					massa.setLinha(cell.getStringCellValue());
					break;
				case 37:
					massa.setPedidoGerado(cell.getStringCellValue());
					break;
				case 38:
					massa.setGeraPedido(cell.getStringCellValue());
					break;
				case 40:
					massa.setNotaGerada(cell.getStringCellValue());
					break;
			}
			return massa;
		} catch (Exception e){
			e
			return null;
		}
	}

	/**
	 * @param cell
	 * @param massa
	 * @return
	 */
	public static MassaDados CellRulesRenovResidencia(Cell cell, MassaDados massa){
		try{
			switch (cell.getColumnIndex()) {
				case 1:
					massa.setApoliceSucep(cell.getStringCellValue());
					break;
				case 3:
					massa.setRamo(cell.getStringCellValue());
					break;
				case 5:
					massa.setFinalVigencia(cell.getStringCellValue());
					break;
				case 6:
					massa.setDiaPagamento(cell.getStringCellValue());
					break;
				case 9:
					massa.setEmailColab(cell.getStringCellValue());
					break;
				case 10:
					massa.setNomeColab(cell.getStringCellValue());
					break;
				case 11:
					massa.setCpf(cell.getStringCellValue());
					break;
				case 13:
					massa.setRenovarProposta(cell.getStringCellValue());
					break;
				case 14:
					massa.setStatus(cell.getStringCellValue());
					break;
				case 15:
					massa.setLinha(cell.getStringCellValue());
					break;
			}
			return massa;
		} catch (Exception e){
			e
			return null;
		}
	}

	/**
	 * @param cell
	 * @param massa
	 * @return
	 */
	public static MassaDados CellRulesRenovAuto(Cell cell, MassaDados massa){
		try{
			switch (cell.getColumnIndex()) {
				case 1:
					massa.setApoliceSucep(cell.getStringCellValue());
					break;
				case 3:
					massa.setRamo(cell.getStringCellValue());
					break;
				case 5:
					massa.setFinalVigencia(cell.getStringCellValue());
					break;
				case 9:
					massa.setEmailColab(cell.getStringCellValue());
					break;
				case 10:
					massa.setNomeColab(cell.getStringCellValue());
					break;
				case 11:
					massa.setCpf(cell.getStringCellValue());
					break;
				case 13:
					massa.setRenovarProposta(cell.getStringCellValue());
					break;
				case 14:
					massa.setStatus(cell.getStringCellValue());
					break;
				case 15:
					massa.setLinha(cell.getStringCellValue());
					break;
			}
			return massa;
		} catch (Exception e){
			e
			return null;
		}
	}

	/**
	 * @param cell
	 * @param massa
	 * @return
	 */
	public static MassaDados CellRulesEmissaoApoliceTransporte(Cell cell, MassaDados massa){
		try{
			switch (cell.getColumnIndex()) {
				case 0:
					massa.setProposta(cell.getStringCellValue());
					break;
				case 1:
					massa.setSubGrupo(cell.getStringCellValue());
					break;
				case 2:
					massa.setDataInicioVigencia(cell.getStringCellValue());
					break;
				case 3:
					massa.setDataFinalVigencia(cell.getStringCellValue());
					break;
				case 4:
					massa.setCnpjCpf(cell.getStringCellValue());
					break;
				case 5:
					massa.setNomeTomador(cell.getStringCellValue());
					break;
				case 6:
					massa.setValorPremioLiquido(cell.getStringCellValue());
					break;
				case 7:
					massa.setQtdParcelas(cell.getStringCellValue());
					break;
				case 8:
					massa.setTextoComplementar(cell.getStringCellValue());
					break;
				case 9:
					massa.setStatus(cell.getStringCellValue());
					break;
				case 10:
					massa.setDesbloqueioproposta(cell.getStringCellValue());
					break;
				case 11:
					massa.setApolice(cell.getStringCellValue());
					break;
				case 14:
					massa.setLinha(cell.getStringCellValue());
					break;
			}
			return massa;
		} catch (Exception e){
			e
			return null;
		}
	}

	/**
	 * @param cell
	 * @param massa
	 * @return
	 */
	public static MassaDados CellRulesDesbloqueioProposta(Cell cell, MassaDados massa){
		try{
			switch (cell.getColumnIndex()) {
				case 0:
					massa.setApolice(cell.getStringCellValue());
					break;
				case 1:
					massa.setRamo(cell.getStringCellValue());
					break;
				case 2:
					massa.setStatus(cell.getStringCellValue());
					break;
				case 3:
					massa.setLinha(cell.getStringCellValue());
					break;
			}
			return massa;
		} catch (Exception e){
			e
			return null;
		}
	}



	public static MassaDados CellRulesInspecaoRisco(Cell cell, MassaDados massa){
		try{
			switch (cell.getColumnIndex()) {
				case 0:
					massa.setDataDesde(cell.getStringCellValue());
					break;
				case 1:
					massa.setCnpjCpf(cell.getStringCellValue());
					break;
				case 4:
					massa.setCodVerif(cell.getStringCellValue());
					break;
				case 5:
					massa.setApolice(cell.getStringCellValue());
					break;
				case 10:
					massa.setValorTributavel(cell.getStringCellValue());
					break;
				case 11:
					massa.setValorIsento(cell.getStringCellValue());
					break;
				case 12:
					massa.setStatus(cell.getStringCellValue());
					break;
				case 13:
					massa.setLinha(cell.getStringCellValue());
					break;
			}
			return massa;
		} catch (Exception e){
			e
			return null;
		}
	}


	/**
	 * @param cell
	 * @param massa
	 * @return
	 */
	public static MassaDados CellRulesSegundaVia(Cell cell, MassaDados massa){
		try{
			switch (cell.getColumnIndex()) {
				case 0:
					massa.setProposta(cell.getStringCellValue());
					break;
				case 1:
					massa.setItem(cell.getStringCellValue());
					break;
				case 2:
					massa.setCodstur(cell.getStringCellValue())
					break;
				case 3:
					massa.setNumeroCarteirinha(cell.getStringCellValue());
					break;

				case 4:
					massa.setSegurado(cell.getStringCellValue());
					break;

				case 5:
					massa.setStatus(cell.getStringCellValue());
					break;
				case 6:
					massa.setLinha(cell.getStringCellValue());
					break;
			}
			return massa;
		} catch (Exception e){
			e
			return null;
		}
	}
}

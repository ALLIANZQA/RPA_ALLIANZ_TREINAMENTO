package auxiliares

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import backoffice.backofficeFuncionalidades
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

public class updateExcel extends backofficeFuncionalidades {

	/**
	 *  
	 * @param caminho_arquivo_xlsx
	 * @param numeroApolice
	 * @param linha
	 * @return
	 */
	public static atualizaExcelPendenciaGo(String caminho_arquivo_xlsx, String numeroApolice, int linha){
		String aba = "Bloqueios";
		int lineCheck = 7;
		auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha,caminho_arquivo_xlsx, valueCheck, lineCheck, aba);
	}

	/**
	 *
	 * @param caminho_arquivo_xlsx
	 * @param numeroApolice
	 * @param linha
	 * @return
	 */
	public static atualizaExcelAgendamentoInspecoes(String caminho_arquivo_xlsx, String numeroApolice, int linha){
		String aba = "Agendamento";
		int lineCheck = 11;
		auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha,caminho_arquivo_xlsx, valueCheck, lineCheck, aba);
	}

	/**
	 * 
	 * @param caminho_arquivo_xlsx
	 * @param numeroApolice
	 * @param linha
	 * @return
	 */
	//	public static atualizaExcelEnvioPDF(String caminho_arquivo_xlsx, String numeroApolice, int linha){
	//		String aba = "EnvioPDF";
	//		int lineCheck = 17;
	//		System.err.println(linha);
	//		auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha,caminho_arquivo_xlsx, valueCheck, lineCheck, aba);
	//	}

	/**
	 * 
	 * @param caminho_arquivo_xlsx
	 * @param numeroApolice
	 * @param linha
	 * @return
	 */
	public static atualizaRenovacoesCotacoesEspeciais(String caminho_arquivo_xlsx, String numeroApolice, int linha){
		String aba = "CotacoesEspeciais";
		int lineCheck = 34;
		System.err.println("LINHA ["+linha+"] valueCheck ["+valueCheck+"]")
		if(valueCheck.equals('enviado')){
			auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha,caminho_arquivo_xlsx, 'Nenviado', lineCheck, aba);
		}else{
			String statusAtt = 'Não Enviado [ERRO]'
			auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha,caminho_arquivo_xlsx, statusAtt, lineCheck, aba);
		}
	}

	/**
	 *
	 * @param rownum
	 * @param path
	 * @param sPerfil
	 * @param sPerfilAlt
	 * @param sCodCapturado
	 * @param sStatus
	 * @param sObs
	 * @atualizaExcelAltera_perfil_COR
	 */
	public static atualizaExcelAlteraPerfilCor(Integer rownum, String path,String sPerfil,String sPerfilAlt,String sCodCapturado,String sStatus,String sObs){
		rownum = rownum -1
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,sPerfil,5,'Planilha1');
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,sPerfilAlt,6,'Planilha1');
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,sCodCapturado,7,'Planilha1');
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,sStatus,8,'Planilha1');
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,sObs,9,'Planilha1');
	}

	/**
	 *
	 * @param rownum
	 * @param path
	 * @param sProposta
	 * @param sApoliceSusep
	 * @param sStatus
	 * @param sCotacao
	 * @param descontoICGlobal
	 * @param premioliqcalculado
	 * @param premioTarifaCalculado
	 * @param premioLiquidoAjustado
	 * @param msg
	 * @return atualiza Excel Residencia
	 */
	public static atualizaExcelCotacaoResidencia(Integer rownum,
			String path,
			String sProposta,
			String sApoliceSusep,
			String sStatus,
			String sCotacao,
			String descontoICGlobal,
			String premioliqcalculado,
			String premioTarifaCalculado,
			String premioLiquidoAjustado,
			String msg) {
		rownum = rownum -1
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,premioliqcalculado,19,'Planilha1');
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,descontoICGlobal,20,'Planilha1');
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,"S",36,'Planilha1');
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,sProposta,37,'Planilha1');
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,sApoliceSusep,38,'Planilha1');
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,sStatus,39,'Planilha1');
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,sCotacao,40,'Planilha1');
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,premioTarifaCalculado,41,'Planilha1');
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,premioLiquidoAjustado,42,'Planilha1');
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,msg,43,'Planilha1');
	}

	/**
	 * @author T_DCDias		
	 * @param caminho_Excel
	 * @param apolice
	 * @param posicao
	 * @return
	 */
	public static atualizaDesbloqueioPropostaAgroFiel(String aba, String sResult,String sSusep, String sProc, int rownum, String path ){
		rownum = rownum -1
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,sSusep,12,aba);
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,sResult,13,aba);
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,sProc,14,aba);
	}

	/**
	 * @author T_DCDias
	 * @param aba
	 * @param sResult
	 * @param executado
	 * @param executar
	 * @param rownum
	 * @param path
	 * @return
	 */
	public static atualizaRecusaDeFrotaFuncionalidades(String aba, String sResult,String executar, String executado , int rownum, String path){
		rownum = rownum -1
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,executar,3,aba);
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,executado,4,aba);
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,sResult,5,aba);
	}



	/**
	 *
	 * @param caminho_arquivo_xlsx
	 * @param numeroApolice
	 * @param linha
	 * @return
	 */
	public static atualizaExcelSeguroResidenciaAuto(String caminho_arquivo_xlsx, String numeroApolice, int linha, int lineCheck, String valorRecuperado, String aba){
		auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha,caminho_arquivo_xlsx, valorRecuperado, lineCheck, aba);
	}


	/**
	 *
	 * @param caminho_arquivo_xlsx
	 * @param numeroApolice
	 * @param linha
	 * @return
	 */
	public static atualizaExcelSeguroResidencia(String caminho_arquivo_xlsx, String numeroApolice, int linha){
		String aba = "SeguroRenovResidencia";
		int lineCheck = 14;
		auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha,caminho_arquivo_xlsx, valueCheckFuncionarioAutoResidencia, lineCheck, aba);
	}

	/**
	 *
	 * @param caminho_arquivo_xlsx
	 * @param numeroApolice
	 * @param linha
	 * @return
	 */
	public static atualizaExcelSeguroRenovAuto(String caminho_arquivo_xlsx, String numeroApolice, int linha){
		String aba = "SeguroRenovAuto";
		int lineCheck = 14;
		auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha,caminho_arquivo_xlsx, valueCheckFuncionarioAutoResidencia, lineCheck, aba);
	}



	/**
	 *
	 * @param caminho_arquivo_xlsx
	 * @param numeroApolice
	 * @param linha
	 * @return
	 */
	public static atualizaExcelEmissaoApoliceTransporte(String caminho_arquivo_xlsx, String numeroApolice, int linha){
		String aba = "apoliceTransportador";
		int lineCheck = 9;
		auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha,caminho_arquivo_xlsx, valueCheck, lineCheck, aba);
	}

	/**
	 *
	 * @param caminho_arquivo_xlsx
	 * @param numeroApolice
	 * @param linha
	 * @return
	 */
	public static atualizaCadastroCorretorExcelCorretagemFacil(String path, String bamotor,String ativacaousuario, int rownum,String aba, String msgerror){
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,bamotor,8,aba);
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,ativacaousuario,9,aba);
		auxiliares.WriteExcelFactory.updateSheetLine('',rownum, path,msgerror,10,aba);
	}

	/**
	 *
	 * @param caminho_arquivo_xlsx
	 * @param numeroApolice
	 * @param linha
	 * @return
	 */
	public static atualizaDesbloqueioProposta(String caminho_arquivo_xlsx, String numeroApolice, int linha){
		String aba = "DesbloqueioProposta";
		int lineCheck = 2;
		auxiliares.WriteExcelFactory.updateSheetLine(numeroApolice, linha,caminho_arquivo_xlsx, valueCheck, lineCheck, aba);
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param caminho_arquivo_xlsx
	 * @param numeroApolice
	 * @param linha
	 * @return
	 */
	public static atualizaExcelInspecaoRisco(String caminho_arquivo_xlsx, String numeroApolice, int linha){
		String aba = "InspecaoRisco";
		auxiliares.WriteExcelFactory.updateSheetLine("", linha,caminho_arquivo_xlsx, "enviado", 12, aba);
		auxiliares.WriteExcelFactory.updateSheetLine("", linha,caminho_arquivo_xlsx, numeroApolice, 14, aba)
		auxiliares.WriteExcelFactory.updateSheetLine("", linha,caminho_arquivo_xlsx, ExtractDates.dataAtualizada(), 15, aba)
	}
}

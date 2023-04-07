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

public class tipoLeituraExcel {


	/**
	 * @param caminhoExcel
	 * @param aba
	 * @return
	 */
	public static List<MassaDados> leituraExcelPendenciaGoAuto(String caminhoExcel, String aba){
		int lineCheck = 7;
		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminhoExcel, aba,lineCheck);
		println 'Tamanho total ['+listMassaDados.size()+']'
		return listMassaDados;
	}


	/**leitura Excel EnvioPDF
	 * @return
	 */
	public static List<MassaDados> leituraExcelEnvioPDF(String caminho_arquivo_xlsx, String aba){
		int lineCheck = 13;
		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminho_arquivo_xlsx, aba,lineCheck);
		println 'Tamanho total ['+listMassaDados.size()+']'
		return listMassaDados;
	}


	/**
	 * @param caminhoExcel
	 * @param aba
	 * @return
	 */
	public static List<MassaDados> leituraExcelCotacoesEspeciais(String caminho_arquivo_xlsx, String aba){
		int lineCheck = 34;
		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminho_arquivo_xlsx, aba,lineCheck);
		println 'Tamanho total ['+listMassaDados.size()+']'
		return listMassaDados;
	}

	/**
	 * @param caminhoExcel
	 * @param aba
	 * @return
	 */
	public static List<MassaDados> leituraExcelAgendamentoInspecao(String caminho_arquivo_xlsx, String aba){
		int lineCheck = 11;
		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminho_arquivo_xlsx, aba,lineCheck);
		println 'Tamanho total ['+listMassaDados.size()+']'
		return listMassaDados;
	}

	/**
	 * @param caminhoExcel
	 * @param aba
	 * @return
	 */
	public static List<MassaDados> leituraExcelSeguroFuncionarioRenovResidencia(String caminho_arquivo_xlsx, String aba){
		int lineCheck = 14;
		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminho_arquivo_xlsx, aba,lineCheck);
		println 'Tamanho total ['+listMassaDados.size()+']'
		return listMassaDados;
	}

	/**
	 * @param caminhoExcel
	 * @param aba
	 * @return
	 */
	public static List<MassaDados> leituraExcelSeguroFuncionarioRenovAuto(String caminho_arquivo_xlsx, String aba){
		int lineCheck = 14;
		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminho_arquivo_xlsx, aba,lineCheck);
		println 'Tamanho total ['+listMassaDados.size()+']'
		return listMassaDados;
	}

	/**
	 * @param caminhoExcel
	 * @param aba
	 * @return
	 */
	public static List<MassaDados> leituraExcelEmissaoApoliceTransportador(String caminho_arquivo_xlsx, String aba){
		int lineCheck = 9;
		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminho_arquivo_xlsx, aba,lineCheck);
		println 'Tamanho total ['+listMassaDados.size()+']'
		return listMassaDados;
	}

	/**
	 * @param caminhoExcel
	 * @param aba
	 * @return
	 */
	public static List<MassaDados> leituraExcelDesbloqueioProposta(String caminho_arquivo_xlsx, String aba){
		int lineCheck = 2;
		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminho_arquivo_xlsx, aba,lineCheck);
		println 'Tamanho total ['+listMassaDados.size()+']'
		return listMassaDados;
	}

	/**
	 * @param caminhoExcel
	 * @param aba
	 * @return
	 */
	public static List<MassaDados> leituraExcelInspecaoRisco(String caminho_arquivo_xlsx, String aba){
		int lineCheck = 12;
		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminho_arquivo_xlsx, aba,lineCheck);
		println 'Tamanho total ['+listMassaDados.size()+']'
		return listMassaDados;
	}


	/**
	 * @param caminhoExcel
	 * @param aba
	 * @return
	 */
	public static List<MassaDados> leituraExcelSegundaVia(String caminho_arquivo_xlsx, String aba){
		int lineCheck = 5;
		List<MassaDados> listMassaDados = new ArrayList<MassaDados>();
		listMassaDados = auxiliares.WriteExcelFactory.readExcelFileToObjectMassaDados(caminho_arquivo_xlsx, aba,lineCheck);
		println 'Tamanho total ['+listMassaDados.size()+']'
		return listMassaDados;
	}
}

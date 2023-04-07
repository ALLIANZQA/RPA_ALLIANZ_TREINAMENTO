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
import bean.MassaDados
import internal.GlobalVariable
import sendEmail.envioDeEmail
import auxiliares.switchAtualizarExcel
import auxiliares.switchLeitorExcel


public class PendenciaGo extends backofficeFuncionalidades{


	public static envioPendenciaGo(String tipoAba){


		int countEnviado = 0;
		String caminhoDiretorioExcel = "C://Users//Public//RPA_ALLIANZ//MassaAuto//ProducaoGo.xlsx";
		String dataRecuerada = getDate();
		String diretorio = createDiretory('Processamento !GO!');
		String diretorioToDelete = diretorio;
		String ExcelCopiado = copiarExcel(caminhoDiretorioExcel, diretorio+"\\Produção.xlsx");

		List<MassaDados> listaExcel;
		listaExcel = switchLeitorExcel.switchExcel(ExcelCopiado, tipoAba);

		int tamanhoList	= listaExcel.size();

		countEnviado = fluxoEnvioPendenciaGo.loopEnvioPendencia(listaExcel, ExcelCopiado ,tamanhoList, countEnviado);


		if(countEnviado == tamanhoList){
			diretorio = createDiretory('Processado GO - '+dataRecuerada);
			copiarExcel(ExcelCopiado, diretorio+"\\Produção Processado-"+dataRecuerada+".xlsx");
			deletarArquivosExcel(diretorioToDelete);
		}
		else{

			println "Copiando Excel atualizado para diretorio inicial"
			copiarExcel(ExcelCopiado, "C://Users//Public//RPA_ALLIANZ//MassaAuto//ProducaoGo-NaoFinalizado.xlsx");

			//countEnviado = fluxoEnvioPendenciaGo.loopEnvioPendencia(listaExcel, ExcelCopiado ,tamanhoList, countEnviado);

		}
	}
}

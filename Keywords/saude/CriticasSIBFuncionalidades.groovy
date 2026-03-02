package saude

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

import internal.GlobalVariable
import rpacommands.rpaCommands

import auxiliares.switchAtualizarExcel
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import auxiliares.FileInput;
import auxiliares.StringFormatter;


public class CriticasSIBFuncionalidades extends rpaCommands{

	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Saude/SIBCriticasAjusteData'
	private static String path
	private static String diretorio = '\\C:\\Users\\Public\\Evidencias'
	private static String excelCopiado


	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		path = data.sourceUrl
	}

	/**
	 * @author t_dvlopes
	 * @param tipoAba
	 * @return
	 */
	public static fluxoCriticasSIB(String tipoAba){
		fluxoCompletoCriticasSIB(tipoAba)
		finalizar();
	}

	/**
	 * @author t_dvlopes
	 * @param tipoAba
	 * @return
	 * Fluxo Completo Envio Pendencia Go
	 */
	public static fluxoCompletoCriticasSIB(String tipoAba){
		initDataFiles()
		if(validaDados()){
			fluxoCriticasSIBExecucao(tipoAba)
			//processaFalhas(tipoAba)
			copiaEvidencia()
			enviarEmailCriticaSib(tipoAba)
		} else {
			System.err.println('Não existem dados para processar')
		}
		finalizar();
	}

	/**
	 * @author T_Dvlopes
	 * @param processoFinalizado
	 * @param tipoAba
	 * @return
	 * Processa as falhas de envio de pendências ao final do processo, caso ocorra mais falha o processo é re-feito até concluir
	 */
	public static processaFalhas(String tipoAba){
		System.err.println('processa Falhas ['+tipoAba+']')
		initDataFiles()
		if(validaDados()){
			fluxoCriticasSIBExecucao(tipoAba)
			processaFalhas(tipoAba)
			finalizar();
		} else {
			System.err.println('Não existem dados para processar')
			finalizar();
		}
	}

	/**
	 *  @author t_dvlopes
	 * @param listaExcel
	 */
	public static void fluxoCriticasSIBExecucao(String tipoAba){
		iniciaAplicacaoMatrizCommands('IntranetProdução');
		realizaLoginIntraNetProducaoCommands(tipoAba);
		initDataFiles()
		WebUI.delay(5);
		try{
			for (int i = 1; i <= data.getRowNumbers(); i++) {
				if (data.getValue(2, i).equals('Processar')) {
					System.err.println('Line ['+Integer.parseInt(data.getValue(1, i))+'] ['+data.getValue(11, i)+']')
					List<String> correcao = new ArrayList<>();
					System.err.println("ENDOSSO ["+data.getValue(24, i)+"] GESTÃO ["+data.getValue(25, i)+"]")

					if (data.getValue(24, i).equals('Processar')) {
						if(saude.Endosso.endosso(data.getValue(11, i)).contains( data.getValue(11, i) )){
							System.err.println("Endosso")
							saude.Endosso.aplicacaoEndosso()
							correcao[0] = data.getValue(7, i); // apolice
							correcao[1] = data.getValue(8, i); //acordo - ID_APLICA
							correcao[2] = data.getValue(6, i);//CPF
							saude.Endosso.fluxoPesquisaProposta(correcao) //return supep
							correcao[3] = supep
							correcao[4] = data.getValue(11, i) //DS_CRITICA
							correcao[5] = data.getValue(14, i) //Novo Valor
							correcao[6] = data.getValue(12, i) //NOME
							correcao[7] = data.getValue(9, i) //CODSITUR

							correcao[8] = data.getValue(19, i) //NM_COMPLETO
							correcao[9] = data.getValue(20, i) //DT_NASCIMENTO
							correcao[10] = data.getValue(21, i) //IDADE
							correcao[11] = data.getValue(22, i) //CNS


							auxiliares.WriteExcelFactory.updateSheetLine('', i-1, path, correcao[3], 4 , tipoAba);
							saude.Endosso.fluxoConsultaEndosso(correcao)
							WebUI.delay(2);
							auxiliares.WriteExcelFactory.updateSheetLine('', i-1, path, saude.Endosso.capturaAlertaEndosso(), 22 , tipoAba);
							auxiliares.WriteExcelFactory.updateSheetLine('', i-1, path, "Processado", 23 , tipoAba);
							clickXpath('//*[@id="lnkTopLink_Close"]', 1);
							fechaAlerta()
							alteraJanejaGenerica(0, 2)
						}
					}

					if (data.getValue(25, i).equals('Processar')) {
						if (saude.CriticasSIB.gestaoCliente(data.getValue(11, i)).contains( data.getValue(11, i) )){
							System.err.println('Gestao Cliente')
							saude.CriticasSIB.aplicacaoGestaoCliente()
							correcao[0] = data.getValue(6, i); //CPF
							correcao[1] = data.getValue(14, i); //Novo Valor
							correcao[2] = data.getValue(13, i); // PIS
							correcao[3] = data.getValue(6, i); // E-MAIL
							correcao[4] = data.getValue(11, i) //DS_CRITICA

							correcao[5] = data.getValue(19, i) //NM_COMPLETO
							correcao[6] = data.getValue(20, i) //DT_NASCIMENTO
							correcao[7] = data.getValue(21, i) //IDADE

							saude.CriticasSIB.correcaoDeCampos(correcao)
							WebUI.delay(2);
							auxiliares.WriteExcelFactory.updateSheetLine('', i-1, path, saude.CriticasSIB.capturaAlerta(), 25 , tipoAba);
							auxiliares.WriteExcelFactory.updateSheetLine('', i-1, path, "Processado", 24 , tipoAba);
							clickXpath('//*[@id="lnkTopLink_Close"]', 1);
							fechaAlerta()
							alteraJanejaGenerica(0, 2)
						}
					}
					auxiliares.WriteExcelFactory.updateSheetLine('', i-1, path, 'Processado', 1 , tipoAba);
				}
			}
		} catch (Exception e) {
			System.err.println(e)
		}
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static boolean validaDados(){
		boolean value
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(2, i).equals('Processar')) {
				value = true
				break;
			} else {
				value = false
			}
		}
		System.err.println('Data ['+value+']')
		return value
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static copiaEvidencia(){
		initDataFiles()
		diretorio = createDiretory('CriticasSIB-'+dataRecuerada);
		System.err.println(path)
		System.err.println(diretorio)
		excelCopiado = copiarExcel(path, diretorio+"\\RoboCriticasSIB-'"+dataRecuerada+"'.xlsx");
		System.err.println('\n'+excelCopiado)
		//		enviarEmailCriticaSib('CriticasSIB')
	}

	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailCriticaSib(String tipoAba){
		pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Saude/CriticasSIB/ConfiguracoesCriticasSIB.txt";
		lerConfigurações(pathTxtEmail);
		path1 = excelCopiado
		nome1 = "RoboCriticasSIB-'"+dataRecuerada+"'.xlsx";

		String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
		initDataFiles()
		if(!validaDados()){
			List<String> anexos = new ArrayList<>();
			anexos[0] = path1;
			anexos[1] = nome1;
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination,anexos, tipoAba )
		} else {
			System.err.println('Não existem dados para processar')
		}
	}
}

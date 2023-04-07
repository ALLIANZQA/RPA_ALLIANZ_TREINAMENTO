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


import internal.GlobalVariable

public class ValidaMassa {


	/**
	 * @author DVLOPES
	 * Verifica se existe massa na base e incia o RPA EM questão
	 * @return
	 */
	public static verificaMassa(){
		int i = 0;
		boolean existenciaMassa = false;
		boolean massa = false;
		while(massa == false){


			switch (i){
				case 0:
					existenciaMassa = validaExistenciaDeArquivo('RoboPendenciaGo');
					if(existenciaMassa){
						println ('Iniciando RoboPendenciaGo')
						backoffice.EnvioPendenciaGo.fluxoEnvioPendenciaGo('Bloqueios');
						println ('\n RoboPendenciaGo [FINALIZADO]')
					}
					break;
				case 1:
					existenciaMassa = validaExistenciaDeArquivo('RoboRenovAuto');
					if(existenciaMassa){
						println ('Iniciando RenovAuto')
						backoffice.SeguroFuncionarioRenovAuto.fluxoSeguroFuncionario('SeguroRenovAuto');
						println ('\n RoboRenovAuto [FINALIZADO]')
					}
					break;
				case 2:
					existenciaMassa = validaExistenciaDeArquivo('RoboRenovResidencia');
					if(existenciaMassa){
						println ('Iniciando RenovResidencia')
						backoffice.SeguroFuncionarioRenovResidencia.fluxoSeguroFuncionario('SeguroRenovResidencia');
						println ('\n RoboRenovResidencia [FINALIZADO]')
					}
					break;
				case 3:
					existenciaMassa = validaExistenciaDeArquivo('RoboAgendamento');
					if(existenciaMassa){
						println ('Iniciando RoboAgendamento')
						backoffice.AgendamentoInspecao.fluxoAgendamentoInspecoes('Agendamento');
						println ('\n RoboAgendamento [FINALIZADO]')
					}
					break;
				case 4:
					existenciaMassa = validaExistenciaDeArquivo('RoboCotacaoEspecial');
					if(existenciaMassa){
						println ('Iniciando Cotaçoes Especiais')
						agcs.CotacoesEspeciais.cotacoesEspeciais("CotacoesEspeciais");
						println ('\n RoboCotacaoEspecial [FINALIZADO]')
					}
					break;
			}

			i = i+1;
			if(i == 5){
				i = 0;
				aguarde(600)
			}
		}
	}


	public static aguarde(int time){
		println 'aguarde ['+time+'] Segundos'
		WebUI.delay(time);
	}


	/**
	 * @author T_DCDias
	 * @param path
	 * @return
	 */
	public static boolean validaExistenciaDeArquivo(String path){
		boolean resp
		println 'Procurando Massa ['+path+']';
		String pathCompleto = 'C:\\Users\\Public\\RPA_ALLIANZ\\MassaAuto\\'+path+'.xlsx';

		File arquivo = new File(pathCompleto);
		if(arquivo.exists()){
			System.err.println("Massa ["+path+"] encontrada")
			resp = true
		}else{
			System.err.println("Massa ["+path+"] Não encontrada")
			resp = false
		}
		return resp
	}
}

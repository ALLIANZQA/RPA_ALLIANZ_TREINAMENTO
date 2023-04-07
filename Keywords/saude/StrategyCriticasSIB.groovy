package saude

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

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
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import rpacommands.rpaCommands
import auxiliares.StringFormatter;
import auxiliares.ExtractDates

public class StrategyCriticasSIB extends rpaCommands{


	/**
	 * @author ALZ_ServiceQA
	 * @param criticas
	 * @return
	 */
	public static ajusteCriticaDadosGerais(String ... criticas){
		String[] valores = criticas
		System.err.println("Novo Valor ["+ valores[1]+"] Critica ["+valores[0]+"]")
		//		//Seleciona ajuste de critica
		switch (valores[0]){
			case "[NOME MAE] - Nome da Mãe do beneficiário contém caracteres especiais." :
			//Editar Nome da Mãe
				limpaCampo('//*[@id="cliente.dadosCompl.nomeMae"]',1);
				preencheXpath('//*[@id="cliente.dadosCompl.nomeMae"]',valores[1],1);
				break;

			case "[NOME MAE] - Correção no nome da mãe.":
			//Editar Nome da Mãe
				limpaCampo('//*[@id="cliente.dadosCompl.nomeMae"]',1);
				preencheXpath('//*[@id="cliente.dadosCompl.nomeMae"]',valores[1],1);
				break;

			case "[Numero PIS/PASEP] - Numero PisPasep não pode ser diferente de 11 caracteres":
			//Editar Pis
				limpaCampo('//*[@id="cliente.dadosCompl.pis"]',1);
				preencheXpath('//*[@id="cliente.dadosCompl.pis"]',valores[2],1);
				break;

			case "[DATA] - correção no campo data de nascimento.":
			//Editar Data Nascimento
				limpaCampo('//*[@id="cliente.dadosPessoais.dataNascimento"]',1);
				preencheXpath('//*[@id="cliente.dadosPessoais.dataNascimento"]', ExtractDates.dateFormatteTypes("dd/MM/yyyy", "ddMMyyyy", valores[1]) ,1);
				commandTAB('//*[@id="cliente.dadosPessoais.dataNascimento"]')
				WebUI.delay(3);
				break;

			case "[CPF] - correção no campo cpf." :
			//Editar CPF
				limpaCampo('//*[@id="cliente.dadosPessoais.id_doc"]',1);
				preencheXpath('//*[@id="cliente.dadosPessoais.id_doc"]',valores[1],1);
				break;
				
			case "[E-MAIL] - correção no campo e-mail." :
				//Editar E-mail
				limpaCampo('//*[@id="cliente.dadosContacto.email1"]',1);
				preencheXpath('//*[@id="cliente.dadosContacto.email1"]',valores[1],1);
				//Estado e-mail
				selecionaItemLista('Activo Verificado', '//*[@id="cliente.dadosContacto.estadoEmail1"]', 'option')
				//Autorização para receber SMS
				clickXpath('//*[@id="cliente.dadosContacto.smsSi"]', 2);
				//Alertas Meteo
				try{
					selecionaItemLista('Sim', '//*[@id="cliente.dadosContacto.ameteo"]', 'option')
				}catch (Exception e){}
			
				
				break;
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param endocos
	 * @return
	 */
	public static ajusteCriticasListaRiscosEndosso(List<String> endocos ){
		switch (endocos[4]){
			case "[ENDEREÇO] - Complemento do endereço não pode ser superior a 15 caracteres":
				String xpath = '//*[@id="ListadoAseguradosBean$lineas_tbody"]'
				String tag = "tr"
				selecionaListaDeElementos(xpath,tag);
				int size = listOfElements.size()/2
				System.err.println('list size ['+size+'] '+endocos[4])
				for(int i = 0; i < size; i++){
					if(size == 1){
						System.err.println('line ['+i+']')

						//LAPIS ALTERAR
						clickXpath('//*[@id="'+i+'"]', 4);
						WebUI.delay(10);

						//Doc. Alternativa
						clickXpath('//*[@id="ParamDirAseguradoBean$nifAsegurado_tipoDocAlt"]', 2);
						String xpath1 = '//*[@id="ParamDirAseguradoBean$nifAsegurado_tipoDocAlt"]'
						String tag1 = "option"
						selecionaListaDeElementos(xpath1,tag1);
						WebUI.delay(1);
						for(int j = 0; j < listOfElements.size(); j++){
							if(j == 0){
								listOfElements[j].click()
								break
							}
						}

						/**
					 * TELA LISTA DE RISCOS
					 */
						StrategyCriticasSIB.ajusteCriticasListaRiscosEndossoListaRiscos( endocos )
						Endosso.endossoRamo(endocos , '0'+Integer.toString(i) )
						//Complemento
						limpaCampo('//*[@id="DomicilioClienteBeanSegurado$restoMorada"]',2)
						preencheXpath('//*[@id="DomicilioClienteBeanSegurado$restoMorada"]', endocos[5], 2);
						//AVANÇAR
						clickXpath('//*[@id="btnAceptar"]', 10);
					} else if(size > 1){
						System.err.println('line ['+i+']')

						//LAPIS ALTERAR
						clickXpath('//*[@id="'+i+'"]', 4);
						WebUI.delay(10);

						//Doc. Alternativa
						clickXpath('//*[@id="ParamDirAseguradoBean$nifAsegurado_tipoDocAlt"]', 2);
						String xpath1 = '//*[@id="ParamDirAseguradoBean$nifAsegurado_tipoDocAlt"]'
						String tag1 = "option"
						selecionaListaDeElementos(xpath1,tag1);
						WebUI.delay(1);
						for(int j = 0; j < listOfElements.size(); j++){
							if(j == 0){
								listOfElements[j].click()
								break
							}
						}

						/**
					 * TELA LISTA DE RISCOS
					 */
						StrategyCriticasSIB.ajusteCriticasListaRiscosEndossoListaRiscos( endocos )
						Endosso.endossoRamo(endocos , '0'+Integer.toString(i) )
						//Complemento
						limpaCampo('//*[@id="DomicilioClienteBeanSegurado$restoMorada"]',2)
						preencheXpath('//*[@id="DomicilioClienteBeanSegurado$restoMorada"]', endocos[5], 2);
						//AVANÇAR
						clickXpath('//*[@id="btnAceptar"]', 10);
					}
				}
				break;

			case "[DATA] - correção no campo data de nascimento.":
			//Editar Data Nascimento
				String xpath = '//*[@id="ListadoAseguradosBean$lineas_tbody"]'
				String tag = "tr"
				selecionaListaDeElementos(xpath,tag);
				int size = listOfElements.size()/2
				System.err.println('list size ['+size+'] '+endocos[4])
				for(int i = 0; i < size; i++){
					String indexCol = Integer.toString(i)
					String valueCol = recuperaValor('//*[@id="ListadoAseguradosBean$lineas_'+indexCol+'_0"]', 'innerText', 5)
					System.err.println('['+i+'] ['+valueCol+"] ["+endocos[7]+"]")
					if(endocos[7].equals(valueCol)){
						String index = '0'+Integer.toString(i)
						clickXpath('//*[@id="'+index+'"]', 4);
						//Data de Nascimento
						ajusteCriticasListaRiscosEndossoListaRiscos(endocos)
						break;
					}
				}
				WebUI.delay(5);
				Endosso.endossoRamo(endocos , '00')
				break;

			case "[CPF] - correção no campo cpf.":
				System.out.println("CPF ["+endocos[5]+"]")
				System.out.println("ID_CODSITUR ["+endocos[7]+"]")
				String xpath1 = '//*[@id="ListadoAseguradosBean$lineas_tbody"]'
				String tag1 = "tr"
				selecionaListaDeElementos(xpath1,tag1);
				WebUI.delay(2);
				int bt
				for(int j = 0; j < listOfElements.size(); j++){
					System.out.println("["+j+'] ['+ listOfElements[j].getText()+"] BT ["+bt+"]")
					if(!listOfElements[j].getText().equals("")){
						if(CODSITUR(listOfElements[j].getText()).contains(endocos[7].toString())){
							System.out.println("["+j+'] ['+ listOfElements[j].getText()+"] BT ["+bt+"]")
							clickXpath('//*[@id="ListadoAseguradosBean$lineas_'+bt+'_5"]/img', 4);
							break
						}
						bt++
					}
				}

				unCheckMenor()
			    //TIPO DE DOCUMENTO
				limpaCampo('//*[@id="ParamDirAseguradoBean$nifAsegurado_doc"]',2)
				preencheXpath('//*[@id="ParamDirAseguradoBean$nifAsegurado_doc"]', endocos[5], 2);
			    //ACEITAR
				clickXpath('//*[@id="ParamDirAseguradoBean$btGuardarSub"]', 5);
			    //CNS
				WebUI.delay(2);
				limpaCampo('//*[@id="DadosSeguradoBean$cartaoNacSaude"]',2)
				preencheXpath('//*[@id="DadosSeguradoBean$cartaoNacSaude"]', endocos[11], 2);
			    //AVANÇAR
				clickXpath('//*[@id="btnAceptar"]', 5);
				break

		case "[E-MAIL] - correção no campo e-mail.":
		    System.out.println("E-MAIL ["+endocos[5]+"]")
		    System.out.println("CPF ["+endocos[2]+"]")
		    String xpath1 = '//*[@id="ListadoAseguradosBean$lineas_tbody"]'
		    String tag1 = "tr"
		    selecionaListaDeElementos(xpath1,tag1);
		    WebUI.delay(2);
		    int bt
		    for(int j = 0; j < listOfElements.size(); j++){
			   System.out.println("["+j+'] ['+ listOfElements[j].getText()+"] BT ["+bt+"]")
			   if(!listOfElements[j].getText().equals("")){
				   if(listOfElements[j].getText().contains(endocos[2].toString())){
					  System.out.println("["+j+'] ['+ listOfElements[j].getText()+"] BT ["+bt+"]")
					  clickXpath('//*[@id="ListadoAseguradosBean$lineas_'+bt+'_5"]/img', 4);
					  break
				   }
				   bt++
			   }
		    }
			//ACEITAR
			clickXpath('//*[@id="ParamDirAseguradoBean$btGuardarSub"]', 5);
			//AVANÇAR
			clickXpath('//*[@id="btnAceptar"]', 5);
			//AVANÇAR - QUESTIONÁRIO
			clickXpath('//*[@id="btnAceptar"]', 5);
			//ACEITAR - DADOS RISCO SAÚDE
			clickXpath('//*[@id="o_13"]', 5);
			// OK ALERTA 
			WebUI.delay(2);
			try{
			WebUI.acceptAlert()
			} catch (Exception e){}
		    // D. CLIENTE E-MAIL - [E-MAIL] - correção no campo e-mail.
		    limpaCampo('//*[@id="DomicilioClienteBeanSegurado$email1"]',2)
		    preencheXpath('//*[@id="DomicilioClienteBeanSegurado$email1"]', endocos[5], 2);
		    break
       }
		
	}


	/**
	 * @author be54955
	 * @return
	 */
	public static unCheckMenor(){
		String checked = recuperaValor('//*[@id="ParamDirAseguradoBean$esMenor"]', 'checked', 3)
		if(checked.equals("true")){
			clickXpath('//*[@id="ParamDirAseguradoBean$esMenor"]', 2);
		}
	}

	/**
	 * @author be54955	
	 * @param t
	 * @return
	 */
	public static String CODSITUR(String t){
		String [] v = t.split(" ")
		//		for(int i = 0; i< v.size(); v++){
		System.err.println("["+v[0].toString()+"]")
		//		}
		return v[0].toString()
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param endocos
	 * @return
	 */
	public static ajusteCriticasListaRiscosEndossoListaRiscos(List<String> endocos ){
		switch (endocos[4]){
			case "[DATA] - correção no campo data de nascimento.":
			//Data de Nascimento
				limpaCampo('//*[@id="ParamDirAseguradoBean$fecNacimiento"]',2)
				preencheXpath('//*[@id="ParamDirAseguradoBean$fecNacimiento"]', ExtractDates.dateFormatteTypes("dd/MM/yyyy", "ddMMyyyy", endocos[5]), 2);
				break;
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param endocos
	 * @return
	 */
	public static ajusteCriticasListaRiscosEndossoDadosRiscoSaude(List<String> endocos ){
		switch (endocos[4]){
			case "[ENDEREÇO] - Complemento do endereço não pode ser superior a 15 caracteres":
			//Complemento
				limpaCampo('//*[@id="DomicilioClienteBeanSegurado$restoMorada"]',2)
				preencheXpath('//*[@id="DomicilioClienteBeanSegurado$restoMorada"]', endocos[5], 2);
				break;
		}
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param criticas
	 * @return
	 */
	public static ajusteCriticaMotivo(String ... criticas){
		String[] valores = criticas
		System.err.println("Critica ["+valores[0]+"]")
		//		//Seleciona ajuste de critica
		switch (valores[0]){
			case "[NOME MAE] - Nome da Mãe do beneficiário contém caracteres especiais." :
				preencheXpath('//*[@id="motivo"]','Correção de Cadastro de Cliente.', 2);
				break;

			case "[NOME MAE] - Correção no nome da mãe.":
			//Editar Nome da Mãe
				preencheXpath('//*[@id="motivo"]','Correção de Cadastro de Cliente.', 2);
				break;

			case "[Numero PIS/PASEP] - Numero PisPasep não pode ser diferente de 11 caracteres":
				preencheXpath('//*[@id="motivo"]','Correção de Cadastro de Cliente.', 2);
				break;

			case "[DATA] - correção no campo data de nascimento.":
				preencheXpath('//*[@id="motivo"]','Correção de Cadastro de Cliente.', 2);
				break;

			case "[CPF] - correção no campo cpf." :
				preencheXpath('//*[@id="motivo"]','Correção de Cadastro (CPF para dependentes menores).', 2);
				break;
				
			case "[E-MAIL] - correção no campo e-mail." :
				preencheXpath('//*[@id="motivo"]','Correção de Cadastro (Correção de e-mail do Cadastro de Cliente.).', 2);
				break;
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param criticas
	 * @return
	 */
	public static ajusteCriticaEndossoDataAtual(String ... criticas){
		String[] valores = criticas
		System.err.println("Critica ["+valores[0]+"]")
		//		//Seleciona ajuste de critica
		switch (valores[0]){
			case "[NOME MAE] - Nome da Mãe do beneficiário contém caracteres especiais." :
			//DATA DO DIA
				limpaCampo('//*[@id="DatosSuplementoBean$fechaEfectoSupl"]',2)
				preencheXpath('//*[@id="DatosSuplementoBean$fechaEfectoSupl"]',ExtractDates.dataAtual(), 2);
				break;

			case "[NOME MAE] - Correção no nome da mãe.":
			//DATA DO DIA
				limpaCampo('//*[@id="DatosSuplementoBean$fechaEfectoSupl"]',2)
				preencheXpath('//*[@id="DatosSuplementoBean$fechaEfectoSupl"]',ExtractDates.dataAtual(), 2);
				break;

			case "[Numero PIS/PASEP] - Numero PisPasep não pode ser diferente de 11 caracteres":
			//DATA DO DIA
				limpaCampo('//*[@id="DatosSuplementoBean$fechaEfectoSupl"]',2)
				preencheXpath('//*[@id="DatosSuplementoBean$fechaEfectoSupl"]',ExtractDates.dataAtual(), 2);
				break;

			case "[DATA] - correção no campo data de nascimento.":
			//DATA DO DIA
				limpaCampo('//*[@id="DatosSuplementoBean$fechaEfectoSupl"]',2)
				preencheXpath('//*[@id="DatosSuplementoBean$fechaEfectoSupl"]',ExtractDates.dataAtual(), 2);
				break;
		}
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param criticas
	 * @return
	 */
	public static ajusteCriticaPesquisaCliente(String ... criticas){
		String[] valores = criticas
		System.err.println("Critica ["+valores[0]+"]")
		//		//Seleciona ajuste de critica
		switch (valores[0]){
			case "[NOME MAE] - Nome da Mãe do beneficiário contém caracteres especiais." :
			//PREENCHER CPF
				preencheXpath('//*[@id="id_doc"]', valores[1], 5);
				break;

			case "[NOME MAE] - Correção no nome da mãe.":
			//PREENCHER CPF
				preencheXpath('//*[@id="id_doc"]',  valores[1], 5);
				break;

			case "[Numero PIS/PASEP] - Numero PisPasep não pode ser diferente de 11 caracteres":
			//PREENCHER CPF
				preencheXpath('//*[@id="id_doc"]',  valores[1], 5);
				break;

			case "[DATA] - correção no campo data de nascimento.":
			//PREENCHER CPF
				preencheXpath('//*[@id="id_doc"]',  valores[1], 5);
				break;

			case "[CPF] - correção no campo cpf." :
			//PREENCHER NOME
				preencheXpath('//*[@id="nome"]',  valores[2], 5);
				break;
		
			case "[E-MAIL] - correção no campo e-mail." :
				//PREENCHER E-MAIL
				//preencheXpath('//*[@id="email"]',  valores[3], 5);
				//CPF
				preencheXpath('//*[@id="id_doc"]',  valores[1], 5);
				break;
		}
	}
}

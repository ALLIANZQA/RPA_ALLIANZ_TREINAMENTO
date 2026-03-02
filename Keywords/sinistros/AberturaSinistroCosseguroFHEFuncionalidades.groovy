package sinistros

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
import auxiliares.MenuMatriz
import auxiliares.ExtractDates

public class AberturaSinistroCosseguroFHEFuncionalidades extends rpaCommands{

	public static String dataRecuerada = getDateHour();
	private static TestData data
	private static String testDataPath = 'Data Files/Sinistro/RoboCosseguroFHEData'
	private static String path
	private static String confirmacaoReserva
	private static String alerta

	/**
	 * @author ALZ_ServiceQA
	 * @param aba
	 * @return
	 */
	public static fluxoAberturaSinistroCosseguroFHE(String tipoAba){
		fluxoCompletoAberturaSinistroCosseguroFHE(tipoAba)
		reprocessaAberturaSinistroCosseguroFHE(tipoAba)
		enviarEmailAberturaSinistroCosseguroFHE(tipoAba)
	}

	/**
	 * @
	 * @param tipoAba
	 * @return
	 */
	public static fluxoCompletoAberturaSinistroCosseguroFHE(String tipoAba){
		initDataFiles()
		if(carregaDados()){
			for (int i = 1; i <= data.getRowNumbers(); i++) {

				if(data.getValue(22, i).equals("Processar")){
					System.out.println("LINHA ["+data.getValue(25, i)+"] ["+data.getValue(22, i)+"]")
					try{
						iniciaAplicacaoMatrizCommands('IntranetProdução');
						realizaLoginIntraNetProducaoCommands(tipoAba);

						WebUI.delay(15);
						menuGIS()
						WebUI.delay(15);
						confirmacaoReserva = ''
						alerta = ""
						/**
						 * TELA GESTÃO INTEGRADA
						 */
						alteraJanejaGenerica(1,2);
						WebUI.maximizeWindow()
						List<String> sinistro = new ArrayList<>();
						lista(sinistro,i)
						if(data.getValue(23, i).equals("Processar")){
							aberturaSinistroCosseguroFHE(sinistro)
						}
						if(confirmacaoReserva.equals("Processar") || data.getValue(23, i).equals("Processado")){
							confirmacaoSinistroCosseguroFHE(sinistro)
						}

						WebUI.closeBrowser();
					}catch (Exception e){  }
				}
			}
			WebUI.closeBrowser();
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static lista( List<String> sinistro , int v){
		for (int i = 1; i <= data.getColumnNumbers(); i++) {
			int d = i -1
			if(i==3){
				sinistro[d] = ExtractDates.dateFormatteTypes("dd/MM/yyyy", "ddMMyyyy", data.getValue(i, v) )
			} else if(i==5){
				sinistro[d] = data.getValue(i, v).toUpperCase().trim()
			} else if(i==12){
				sinistro[d] = data.getValue(i, v).replaceAll("[R\$.,]","").trim()
			} else if(i==16){
				sinistro[d] = data.getValue(i, v)
			} else {
				sinistro[d] = data.getValue(i, v)
			}
		}

		for (int j = 0; j < sinistro.size();j++ ){
			System.err.println("["+j+"] [ "+sinistro[j]+"]")
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @return
	 */
	public static aberturaSinistroCosseguroFHE(List<String> sinistro){

		WebUI.delay(15);
		//CLICAR NOTIFICAÇÃO SINISTRO
		clickXpath('//*[@id="aperturaGenerica"]/div[1]/span', 5);
		WebUI.delay(17);

		/**
		 * //TELA NOTIFICAÇÃO DE SINISTROS
		 */
		alteraJanejaGenerica(2,2);
		WebUI.maximizeWindow()
		// CLICAR FORA DE SISTEMA
		clickXpath('//*[@id="fuerasis"]/div[1]/span', 5);

		/**
		 * TELA NOTIFICAÇÃO DE SINISTROS
		 */
		//PREENCHER RAMO
		preencheXpath('//*[@id="ramo"]', sinistro[0], 2);
		//PREENCHER ODALIDADE
		preencheXpath('//*[@id="modalidad"]',sinistro[1], 2);
		//ACEITAR
		clickXpath('//*[@id="o_3"]', 5);

		/**
		 * TELA NOTIFICAÇÃO DE SINISTROS
		 */
		//DATA SINISTRO
		preencheXpath('//*[@id="fechstro"]', sinistro[2] , 2);
		//Circunstancea LIST
		selecionaItemLista('//*[@id="circunstancia"]', 'option', sinistro[3] )
		WebUI.delay(2);
		//CAUSA LIST
		selecionaItemLista('//*[@id="familiaCausa"]', 'option', sinistro[4] )
		WebUI.delay(2);
		//APLOICE
		limpaCampo('//*[@id="poliza"]',2)
		preencheXpath('//*[@id="poliza"]',sinistro[5], 2);
		//ITEM
		limpaCampo('//*[@id="aplica"]',2)
		preencheXpath('//*[@id="aplica"]',sinistro[6], 2);
		//CORRETOR
		limpaCampo('//*[@id="mediador"]',2)
		preencheXpath('//*[@id="mediador"]',sinistro[7], 2);
		//Coseguradora list
		//*[@id="ciacoas"]
		selecionaItemLista('//*[@id="ciacoas"]', 'option', sinistro[8].replace("Ê","E") )
		//Codigo Seguradora
		preencheXpath('//*[@id="refabri"]',sinistro[9], 2);
		//ACEITAR
		clickXpath('//*[@id="o_3"]', 5);
		WebUI.delay(15);

		//Alerta
		alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
		System.out.println(alerta)

		if(alerta.contains('atributo nao encontrado')){

			/**
			 TELA NOTIFICAÇÃO DE SINISTROS - RESERVA
			 6770 MQC001 MORTE QUALQUER CAUSA
			 6766 IPA052 INVALIDEZ PERMANENTE TOTAL OU PARCIAL POR ACIDENTE
			 6763 IFPD53 INVALIDEZ FUNCIONAL PERMANENTE E TOTAL POR DOENÇA
			 6793 MQCF56 MORTE QUALQUER CAUSA FILHO
			 6792 MQCC55 MORTE QUALQUER CAUSA CONJUGE
			 CASO : Outros tipos de Acidentes
			 cobertura 6766 IPA052 INVALIDEZ PERMANENTE TOTAL OU PARCIAL POR ACIDENTE
			 CASO : INVALIDEZ POR DOENÇA
			 6763 IFPD53 INVALIDEZ FUNCIONAL PERMANENTE E TOTAL POR DOENÇA
			 CASO : Morte Acidental (Morte porque não tem a opção de morte acidental)
			 50% do valor em: 6762 IEA051 INDENIZAÇÃO ESPECIAL POR ACIDENTE
			 Os outros 50% em: 6770 MQC001 MORTE QUALQUER CAUSA
			 CASO : MORTE
			 (Em “ Coberturas ”, reserva conforme estiver na Planilha “ Coberturas ”, segue exemplo abaixo:)
			 6770 MQC001 MORTE QUALQUER CAUSA
			 6793 MQCF56 MORTE QUALQUER CAUSA FILHO
			 6792 MQCC55 MORTE QUALQUER CAUSA CONJUGE
			 sinistro[10]  [ 6770 MQC001-MORTE QUALQUER CAUSA ]
			 sinistro[11]  [   28831,86 ]
			 *
			 */
			//long valor = Integer.parseInt( sinistro[11] )
			long valor = Long.parseLong(sinistro[11])

			System.err.println("Prêmio "+valor)

			selecionaCobertura(sinistro[10], valor)
			//ACEITAR
			clickXpath('//*[@id="o_3"]', 5);
			WebUI.delay(10);


			alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'textContent', 5)
			System.out.println(alerta)

			if(alerta.contains('atributo nao encontrado')){

				//MENSAGEM E APOLICE
				String xpath = '//*[@id="CellContent"]'
				String tag = 'label'
				selecionaListaDeElementos(xpath, tag)
				WebUI.delay(2);
				for(int i = 0 ; i < listOfElements.size(); i++ ){
					System.err.println("["+i+"] ["+listOfElements.get(i).getText()+"]")
					sinistro[20] = listOfElements.get(1).getText()
					sinistro[25] = listOfElements.get(0).getText()
				}

				if( sinistro[25].contains("Processo de sinistro notificado o número")){
					confirmacaoReserva = "Processar"
					try{
						auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, sinistro[20], 20 , 'AberturaSinistroCoFHE');
						auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, "Processado", 22 , 'AberturaSinistroCoFHE');
						auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path,  sinistro[25]+" "+sinistro[20], 23 , 'AberturaSinistroCoFHE');
					}catch (Exception e){System.err.println("[ Cannot invoke method setCellValue() on null object ]")}
				}else {
					confirmacaoReserva = ""
				}
			} else {
				auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, "Processado", 21 , 'AberturaSinistroCoFHE');
				auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, "Processado", 22 , 'AberturaSinistroCoFHE');
				auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path,  alerta, 23 , 'AberturaSinistroCoFHE');
				alerta = ""

			}
			//ACEITAR 2
			//		clickXpath('//*[@id="o_4"]', 2);

			//FECHAR

		} else {
			// formataAlerta(sinistro)
			auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, "Processado", 21 , 'AberturaSinistroCoFHE');
			auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, "Processado", 22 , 'AberturaSinistroCoFHE');
			auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path,  formataAlerta(sinistro), 23 , 'AberturaSinistroCoFHE');
			alerta = ""
		}

		clickXpath('//*[@id="lnkTopLink_Close"]', 2);
		WebUI.delay(5);
		fechaAlerta();
		alteraJanejaGenerica(1,2);

	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static String formataAlerta(List<String> sinistro){
		String msg
		String causa= sinistro[4].toString()
		System.err.println(causa+" - "+alerta+' - '+ causa.length())

		if(causa.length()<4){
			msg = "Campo "+alerta+" - Campo causa ["+sinistro[4]+"] inválido"
		}
		System.out.println(msg)
		return msg
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param t
	 * @param valor
	 */
	public static void selecionaCobertura(String t, long valor){
		List<String> coberturas = new ArrayList<String>()
		StringBuilder strb = new StringBuilder()
		String v = t+","
		String [] token = v.split(" ")

		for(int i = 0; i < token.size(); i++){
			if(i==0){
				strb.append(" "+token[i])
			}

			if(token[i].contains(",")){
				coberturas.add(strb)
				strb = new StringBuilder()
			}
		}

		valor = valor/coberturas.size();
		for(int j = 0;j < coberturas.size(); j++){
			System.err.println("["+coberturas.get(j).replaceAll("[,]","").trim()+"]")
			selecionaReserva(coberturas.get(j).replaceAll("[,]","").trim(), valueOfpayment(valor) )
			WebUI.delay(3);
		}
	}

	/**
	 *
	 * @param valor
	 * @return
	 */
	public static valueOfpayment(long valor){
		BigDecimal payment = new BigDecimal(valor).movePointLeft(2);
		String valueOfBigDec = String.valueOf(payment);
		valueOfBigDec = valueOfBigDec.replace(".",",").trim()
		return valueOfBigDec
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @return
	 */
	public static confirmacaoSinistroCosseguroFHE(List<String> sinistro){


		System.err.println("SINISTRO ["+sinistro[20]+"]")

		try{

			//PESQUISA SINISTRO
			preencheXpath('//*[@id="refsiniestro"]',sinistro[20], 2);
			//PESQUISAR
			clickXpath('//*[@id="cellTarget_busqueda"]', 5);

			//DESCRÇÃO COM Referência Segurado
			limpaCampo('//*[@id="descripcio"]',2)
			preencheXpath('//*[@id="descripcio"]',"Lider "+sinistro[12], 2);

			//Referencia Segurado
			preencheXpath('//*[@id="refEuropcar"]',sinistro[13], 2);

			//ACEITAR
			clickXpath('//*[@id="acceptButton"]', 5);

			//PESSOAS ENVOLVIDAS
			clickXpath('//*[@id="intervinientes"]', 2);
			WebUI.delay(5);

			//PESSOAS SINISTRADAS - EMITIR
			clickXpath('//*[@id="o_21"]', 2);
			WebUI.delay(2);

			/**
			 * GESTÃO INTEGRADA DE SINISTROS
			 */
			//NOME
			preencheXpath('//*[@id="nombre"]',sinistro[14], 2);
			//IDADE
			preencheXpath('//*[@id="edad"]',sinistro[16], 2);
			//SEXO LISTA
			selecionaItemLista('//*[@id="sexo"]', 'option', sexo(sinistro[17]) )
			//CONDIÇÃO LISTA
			selecionaItemLista('//*[@id="condicion"]', 'option', sinistro[18] )
			//TIPO LESÃO LISTA
			selecionaItemLista('//*[@id="tiplesion"]', 'option', lesao(sinistro[19]))

			//ACEITAR
			clickXpath('//*[@id="o_18"]', 2);
			//MSG OPERCAO REALIZADA - innerText: " Operação realizada."
			String msg = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5).trim();

			System.err.println("["+msg+"]")
			WebUI.delay(10);

			if(msg.contains("Operação realizada.")){
				//EMITIR
				clickXpath('//*[@id="o_21"]', 2);
				try{
					auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, "Processado", 21 , 'AberturaSinistroCoFHE');
					auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, "Processado", 22 , 'AberturaSinistroCoFHE');
					auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, "Processo de sinistro notificado. "+msg, 25 , 'AberturaSinistroCoFHE');
				}catch (Exception e){System.err.println("[ Cannot invoke method setCellValue() on null object ]")}
			} else {
				try{
					auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, "Processado", 21 , 'AberturaSinistroCoFHE');
					auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, "Processado", 22 , 'AberturaSinistroCoFHE');
					auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, "Processo de sinistro não confirmado.", 25 , 'AberturaSinistroCoFHE');
				}catch (Exception e){System.err.println("[ Cannot invoke method setCellValue() on null object ]")}
			}

			//FECHAR
			clickXpath('//*[@id="lnkTopLink_Close"]', 2);
			WebUI.delay(5);
			fechaAlerta();
			alteraJanejaGenerica(0,2);
			//		WebUI.delay(10);

		} catch (Exception e){
			//FECHAR
			clickXpath('//*[@id="lnkTopLink_Close"]', 2);
			WebUI.delay(5);
			fechaAlerta();
			alteraJanejaGenerica(0,2);
			//WebUI.delay(10);
			auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, "Processado", 21 , 'AberturaSinistroCoFHE');
			auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, "Processado", 22 , 'AberturaSinistroCoFHE');
			auxiliares.WriteExcelFactory.updateSheetLine('', Integer.parseInt(sinistro[24]), path, "Processo de sinistro não confirmado.", 25 , 'AberturaSinistroCoFHE');

		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		path = data.sourceUrl
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param x
	 * @param t
	 * @param v
	 * @return
	 */
	public static selecionaReserva(String r, String v ){
		WebUI.delay(5);
		String x = '//*[@id="tablaCoberturas_tbody"]'
		String t = 'tr'
		selecionaListaDeElementos(x,t);
		int size = listOfElements.size()
		for(int i = 0; i < size; i++){
			if(listOfElements.get(i).getText().contains(r)){
				System.err.println('['+i+'] ['+listOfElements.get(i).getText()+"]")
				preencheXpath('//*[@id="reservaInput'+i+'"]',v, 2);
				commandTAB('//*[@id="reservaInput'+i+'"]')
				break
			}
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param x
	 * @param t
	 * @param v
	 * @return
	 */
	public static selecionaItemLista(String x, String t, String v ){
		selecionaListaDeElementos(x,t);
		int size = listOfElements.size()
		for(int i = 0; i < size; i++){
			if(listOfElements.get(i).getText().equals(v)){
				System.err.println('['+i+'] ['+listOfElements.get(i).getText()+"] SEARCH ["+v+"]")
				listOfElements.get(i).click()
				break
			}
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static menuGIS(){
		MenuMatriz.producaoSinistro()
		WebUI.delay(5);
		MenuMatriz.subMenu('/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div/div/div[2]/ul', 'a',"GIS")
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param s
	 * @return
	 */
	public static String sexo(String s){
		Map<String, String> mes = new HashMap<String, String>();
		mes.put("F",'Masculino');
		mes.put("M",'Masculino');
		return mes.get(s);
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static reprocessaAberturaSinistroCosseguroFHE(String tipoAba){
		if(carregaDados()){
			System.err.println("[ Reprocessa Abertura Sinistro CosseguroFHE")
			fluxoCompletoAberturaSinistroCosseguroFHE(tipoAba)
			reprocessaAberturaSinistroCosseguroFHE(tipoAba)
		}
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean carregaDados(){
		boolean carregar = false
		initDataFiles()
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if(data.getValue(22, i).equals("Processar")){
				System.err.println(i+' - Status - '+ data.getValue(22, i))
				carregar = true
				break
			}
		}
		System.err.println('carregaDados ['+ carregar+']')
		return carregar
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param tipoAba
	 * @return
	 */
	public static enviarEmailAberturaSinistroCosseguroFHE(String tipoAba){
		if(!carregaDados()){
			pathTxtEmail = "C:/Users/Public/RPA_ALLIANZ/Emails/Sinistros/CosseguroFHE/ConfiguracoesCosseguroFHE.txt";
			lerConfigurações(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			List<String> anexos = new ArrayList<>();
			anexos[0] = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Sinistro\\RoboCosseguroFHE.xlsx"
			anexos[1] = "RoboCosseguroFHE.xlsx"
			sendEmail.CreateEmail.createEmailSendMultipleFiles(sender, emaildestination, anexos, tipoAba)
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param s
	 * @return
	 */
	public static String lesao(String s){
		Map<String, String> lesoes = new HashMap<String, String>();
		lesoes.put("FERIDO",'Ferido');
		lesoes.put("GRAN INVALIDEZ",'Gran Invalidez');
		lesoes.put("INCAPACIDADE PERMANENTE PARCIAL",'Incapacidade Permanente Parcial');
		lesoes.put("INCAPACIDADE PERMANENTE TOTAL",'Incapacidade Permanente Total');
		lesoes.put("MORTE",'Morte');
		return lesoes.get(s.toUpperCase());
	}

}

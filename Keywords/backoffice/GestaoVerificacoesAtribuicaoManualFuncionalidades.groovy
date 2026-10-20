package backoffice

import java.time.Duration
import java.time.Instant
import java.util.List
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import auxiliares.MenuMatriz
import rpacommands.rpaCommands
import auxiliares.ExtractDates
import auxiliares.StringFormatter
import auxiliares.FileInput
import sendEmail.emailDinamico;
import auxiliares.timer;
import auxiliares.GetHost;
import auxiliares.CurrencyMonetary;
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData


public class GestaoVerificacoesAtribuicaoManualFuncionalidades extends rpaCommands{
	
	private static TestData data
	private static String testDataPath = 'Data Files/Backoffce/GestaoVerificacoesAtribuicaoManual/GestaoVerificacoesAtribuicaoManualData'
	private static String path
	private static String alerta
	private static int cellCheck = 5;
	private static Instant start;
	private static Instant end;
	private static Duration timeLapnse;
	private static String docName = "GestaoVerificacoesAtribuicaoManual.xlsx"
	private static int cellCheckAlert = 6;
	private static boolean enviado = true;
	private static String tela;
	private static boolean erroTela = false;
	private static String status
		
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static initDataFiles(){
		data = findTestData(testDataPath)
		path = data.sourceUrl
	}
	
	/**
	 * @author T_DCDias
	 * @param tipoAba
	 * @return
	 */
	public static boolean validaDados(int cellStatus){
		boolean value
		for (int i = 1; i <= data.getRowNumbers(); i++) {
			if (data.getValue(cellStatus, i).equals('Processar')) {
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
	 * @author ALZ_ServiceQA
	 * @param sinistro
	 * @param v
	 * @return
	 */
	public static List<String> lista( List<String> registros , int v){
		for (int i = 1; i <= data.getColumnNumbers(); i++) {
			int d = i -1
			registros[d] = data.getValue(i, v).trim()
		}

		for (int j = 0; j < registros.size();j++ ){
			System.err.println("["+j+"] ["+registros[j]+"]")
		}
		return registros
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoGestaoVerificacoesAtribuicaoManual(String aba, String a){
		System.out.println()
		if(FileInput.validaExistenciaDeArquivo(pathFile)){
			FileInput.criaLog( ExtractDates.dataAtual() , pathFile);
			FileInput.atualizaLog( ExtractDates.horaAtualizada() , pathFile);
			FileInput.atualizaLog( Instant.now().toString(), pathFile)
		}
		initDataFiles()
		fluxoCompletoGestaoVerificacoesAtribuicaoManual(aba,a)
		reprocessaFalhas(aba,a)
		FileInput.atualizaLog(Instant.now().toString(), pathFile)
		List<String> times = rpaCommands.convertToInstant(pathFile);
		String dataInicio = times[0];
		String horaInicial =  times[1];
		timeLapnse = Duration.between( Instant.parse(times[2]), Instant.parse(times[3]));
		String dataFinal = ExtractDates.dataAtual();
		String horaFinal =  ExtractDates.horaAtualizada();
		try{
		   enviaGestaoVerificacoesAtribuicaoManual(aba, timeLapnse, dataInicio, dataFinal, horaInicial, horaFinal );
		} catch (Exception e){
		}
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @param a
	 * @return
	 */
	public static init(String aba, String a){
		try{
			acessoAmbiente(aba,a)
			WebUI.delay(10);
			WebUI.delay(10);
		} catch (Exception e){}
	}
	
	/**
	 * @author Alz_sup54955
	 */
	public static void menu(){
		MenuMatriz.producaoSinistro()
		MenuMatriz.subMenuNome("a","Gestão de Verificações")
		alteraJanejaGenerica(1, 5)
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static fluxoCompletoGestaoVerificacoesAtribuicaoManual(aba,a){
		System.out.println()
		try{
			if(validaDados(cellCheck)){
				init(aba,a);
				menu()
				for(int i = 1; i <= data.getRowNumbers(); i++) {
					int j =  i - 1
					enviado = true;
					if (data.getValue(cellCheck, i).equals('Processar')) {
						List<String> registros = new ArrayList<>();
						lista( registros , i)
						if(atribuicao(registros,aba,a)) {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 4, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, alerta , 5, aba);
							} catch (Exception e){}
						} else {
							try{
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, status, 4, aba);
								auxiliares.WriteExcelFactory.updateSheetLine("", j, path, "Alerta: "+alerta, 5, aba);
							} catch (Exception e){}
							
						}
					}
				}
			} else {
				System.err.println('Não existem dados para processar')
			}
		} catch (Exception e){}
		WebUI.closeBrowser();
	}
	
	/**
	 * @author be54955
	 * @return
	 */
	public static boolean atribuicao(List<String> registros, String aba, String a){
		System.out.println()
		String msgAlert;
		String tela = true;
		try{
			informacoesAdicionaisDoRisco(registros)
			WebUI.delay(10);
			alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
			System.out.println("Alerta ["+alerta+"]")
			if(alertListBlocList(alerta)){
			   fecharJanelaAtual(1)
			   alteraJanejaGenerica(0, 0)
			   menu()
			   enviado = false;
			} else {
			   alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
			   System.out.println("Alerta ["+alerta+"]")
			   if(!alerta.contains("Existe uma averiguação ativa para esta apólice")){
			      novaAveriguacao(registros)
			   }
			   WebUI.delay(10);
			   tratamentoDeAlertas()
			   if(enviado){
			      selecao(registros)
			      averiguacoes()
			      resumoAveriguacao()
			      atribuir(registros)
			      alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
			      System.out.println("Alerta ["+alerta+"]")
			      if(alertListBlocList(alerta)){
				     fecharJanelaAtual(1)
				     alteraJanejaGenerica(0, 0)
				     menu()
				     enviado = false;
			      } else {
			        enviado = true;
			      }
			   }
		    }
			status = "Processado"
		} catch (Exception e) {
		  enviado = false
		  status = "Processado"
		  alerta="Falha ao preencher 'GESTÃO DE RECLAMAÇÕES'"
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 5)
		  init(aba, a)
		  menu()
		}
		System.out.println("\nenviado ["+ enviado+"] \nmsgAlerta ["+alerta+"]");
		return enviado;
	}
	
	/**
	 * @author Alz_sup54955
	 */
	public static void  tratamentoDeAlertas(){
		if(campoObrigatorio()){
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0, 0)
			menu()
			enviado=false;
		 }
	
		 if(enviado){
			if(alertListBlocList(alerta)){
			   enviado = false;
			   //alerta=msgAlerta
			   fecharJanelaAtual(1)
			   alteraJanejaGenerica(0, 0)
			   menu()
			   System.out.println("Alerta ["+alerta+"]")
		   }
		 }
		 System.out.println("Alerta ["+enviado+"]")
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 */
	public static void informacoesAdicionaisDoRisco(List<String> registros){
		//Nova Reclamação
		selecionaItemLista("Nova Averiguação",'//*[@id="divNavigationArea"]' , "td" )
		WebUI.delay(5);
		tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
		System.out.println(tela)
		if(listaDeTelas(tela)){
			 alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
			 System.out.println("Alerta ["+alerta+"]")
			 if(alertListBlocList(alerta)){
				  fecharJanelaAtual(1)
				  alteraJanejaGenerica(0, 0)
				  menu()
				  status = "Processado"
				  enviado = false;
			 } else {
			   //SIMU
			   preencheXpath('//*[@id="cotiPolizaCert_campo1"]', registros[0], 0)
			   preencheXpath('//*[@id="cotiPolizaCert_campo2"]', registros[1], 0)
			   //Lupa
			   clicarEmElementoDaTela('//*[@id="btBuscarVerificacion"]', 0);
			 }
		} else {
		  enviado = false
		  alerta="Tela 'Informações adicionais do risco' não apresentada"
		  status = "Processado"
		  fecharJanelaAtual(1)
		  alteraJanejaGenerica(0, 5)
		  menu()
		}
	}
		
	/**
	 * @author Alz_sup54955
	 * @param registros
	 */
	public static void novaAveriguacao(List<String> registros){
		if(enviado){
			//Nova Reclamação
			tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(tela)
			if(listaDeTelas(tela)){
				 alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
				 System.out.println("Alerta ["+alerta+"]")
				 if(alertListBlocList(alerta)){
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  menu()
					  status = "Processado"
					  enviado = false;
				 } else {
				   //ATRIBUIÇÃO 
				   selecionaItemListaAtributo(atribuicao("Manual"),'//*[@id="sectionIndicadoresContent"]/table/tbody/tr[1]' , "input", "value" )
				   //Data Averiguação 
				   String xPath = '//*[@id="fechaVerificacion"]'
				   preencheXpath(xPath, registros[2], 0)
				   commandTAB(xPath)
				   //Aceitar 
				   selecionaItemLista("Aceitar",'//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td' , "div" )
				 }
			} else {
			  enviado = false
			  alerta="Tela 'Nova Averiguação' não apresentada"
			  status = "Processado"
			  fecharJanelaAtual(1)
			  alteraJanejaGenerica(0, 5)
			  menu()
			}
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param registros
	 */
	public static void selecao(List<String> registros){
		if(enviado){
			tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(tela)
			if(listaDeTelas(tela)){
				 alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
				 System.out.println("Alerta ["+alerta+"]")
				 if(alertListBlocList(alerta)){
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  menu()
					  status = "Processado"
					  enviado = false;
				 } else {
				   //Seleção
				   selecionaItemLista("Seleção",'//*[@id="divNavigationArea"]' , "td" )
				   //Sim. ou Apólice/Aplica
				   preencheXpath('//*[@id="cotiPolizaCert_campo1"]', registros[0], 0)
				   preencheXpath('//*[@id="cotiPolizaCert_campo2"]', registros[1], 0)
				   //Pesquisar
				   selecionaItemLista("Pesquisar",'//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td' , "div" )
				 }
			} else {
			  enviado = false
			  alerta="Tela 'Seleção' não apresentada"
			  status = "Processado"
			  fecharJanelaAtual(1)
			  alteraJanejaGenerica(0, 5)
			  menu()
			}
		}
	}
	
	/**
	 * @author Alz_sup54955
	 */
	public static void averiguacoes(){
		if(enviado){
			//Nova Reclamação
			selecionaItemLista("Nova Averiguação",'//*[@id="divNavigationArea"]' , "td" )
			WebUI.delay(5);
			tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(tela)
			if(listaDeTelas(tela)){
				 alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
				 System.out.println("Alerta ["+alerta+"]")
				 if(alertListBlocList(alerta)){
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  menu()
					  status = "Processado"
					  enviado = false;
				 } else {
				 //LISTA DE AVERIGUAÇÕES
				   if(!selecionaItemParaAtribuicao("N.AS",'//*[@id="lista_tbody"]' , "tr" ) ){
					   alerta="Não tem item para atribuição manual"
					   enviado = false;
					   fecharJanelaAtual(1)
					   alteraJanejaGenerica(0, 5)
					   menu()
				   }
			     }
			} else {
			  enviado = false
			  alerta="Tela 'Averiguações' não apresentada"
			  status = "Processado"
			  fecharJanelaAtual(1)
			  alteraJanejaGenerica(0, 5)
			  menu()
			}
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @return
	 */
    public static resumoAveriguacao(){
		if(enviado){
			tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(tela)
			if(listaDeTelas(tela)){
				 alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
				 System.out.println("Alerta ["+alerta+"]")
				 if(alertListBlocList(alerta)){
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  menu()
					  status = "Processado"
					  enviado = false;
				 } else {
				 //LISTA DE AVERIGUAÇÕES
				  selecionaItemLista("Atrib. Manual",'//*[@id="resumenViewForm"]/table[1]' , "div" )
				 }
			} else {
			  enviado = false
			  alerta="Tela 'Averiguações' não apresentada"
			  status = "Processado"
			  fecharJanelaAtual(1)
			  alteraJanejaGenerica(0, 5)
			  menu()
			}
		}
	}
		
	/**
	 * @author Alz_sup54955
	 * @param registros
	 */
	public static void atribuir(List<String> registros){
		if(enviado){
			tela = recuperaValor('//*[@id="cellAppPromptHeaderArea"]', 'innerText', 1)
			System.out.println(tela)
			if(listaDeTelas(tela)){
				 alerta = recuperaValor('//*[@id="dataErrorSection"]/table/tbody/tr/td[2]', 'innerText', 5)
				 System.out.println("Alerta ["+alerta+"]")
				 if(alertListBlocList(alerta)){
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 0)
					  menu()
					  status = "Processado"
					  enviado = false;
				 } else {
				 //Averiguador Externo
				 clicarEmElementoDaTela('//*[@id="externo"]', 0);
                 //NIF *
				 preencheXpath('//*[@id="nif"]', registros[3], 0)
				 //Aceitar
				 selecionaItemLista("Aceitar",'//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td' , "div" )
				 }
			} else {
			  enviado = false
			  alerta="Tela 'Averiguações' não apresentada"
			  status = "Processado"
			  fecharJanelaAtual(1)
			  alteraJanejaGenerica(0, 5)
			  menu()
			}
		}
	}
	
	/**
	 * @author be54955
	 * @param aba
	 * @return
	 */
	public static reprocessaFalhas(String aba, String a){
		System.err.println('Reprocessa Falhas')
		initDataFiles()
		if(validaDados(cellCheck)){
			fluxoCompletoGestaoVerificacoesAtribuicaoManual(aba,a)
			reprocessaFalhas(aba,a)
		}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param tipoAba
	 * @param timeLapnse
	 * @param dataInicio
	 * @param dataFinal
	 * @param horaInicial
	 * @param horaFinal
	 * @return
	 */
	public static enviaGestaoVerificacoesAtribuicaoManual(String tipoAba, Duration timeLapnse, String dataInicio, String dataFinal, String horaInicial, String horaFinal){
	System.out.println()
	 try{
		 if(!validaDados(cellCheck)){
			String pathTxtEmailConfig = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\backoffice\\GestaoVerificacoesAtribuicaoManual\\ConfiguracoesGestaoVerificacoesAtribuicaoManual.txt";
			lerConfigurações(pathTxtEmailConfig)
			System.out.println(pathTxtEmail)
			String emaildestination = StringFormatter.formatterEmailDestination(FileInput.readFile(pathTxtEmail));
			// FORMATA SAIDA DO TEMPO DE EXECUÇÃO
			long segds = timeLapnse.toSeconds();
			String hours = timeLapnse.toHours()+"h "+
						timer.converteMinutos(timeLapnse.toHours(), timeLapnse.toMinutes())+"m "+
						timer.converteSegundos(timeLapnse.toMinutes(),segds)+"s";
			// ENVIO DE E-MAIL
			//DADOS DO DOCUMENTO
			initDataFiles();
			// ENVIA E-MAIL DINAMICO
			emailDinamico.enviaEmailDinamico(sender, emaildestination, tipoAba, path, docName, emailDinamico.listaDeAlertas(data, cellCheckAlert)  , hours, horaInicial, horaFinal,  GetHost.getHostname() , dataInicio, dataFinal, "" );
			FileInput.deletaArquivoPdf(pathFile);
		 }
	  } catch (Exception e){}
	}
	
	/**
	 * @author Alz_sup54955
	 * @param a
	 * @return
	 */
	public static String atribuicao(String a){
		Map<String, String> atributo = new HashMap<String, String>()
		atributo.put('Manual', 'M');
		atributo.put('Automatico', 'A');
		return atributo.get(a.toString());
	}
	
	/**
	 * @author Alz_sup54955
	 * @return
	 */
	public static boolean campoObrigatorio(){
		boolean b = false
		alerta=recuperaValor('//*[@id="cellDataErrorSection"]', 'innerText', 0)
		System.out.println("Alerta ["+alerta+"]")
	    if(alerta.contains("Campo necessário") ){
		  b = true;
	    }
	    if(alerta.contains("Campo obrigatório") ){
		  b = true;
	    }
		System.out.println("Alerta ["+b+"]")
	 return b;	 
	}
	
	/**
	 * @author T_DCDias
	 * @return
	 */
	public static boolean selecionaItemParaAtribuicao(String tipo,String xpath , String tag ){
		WebUI.delay(2)
		boolean item = false ;
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			System.err.println("["+i+"] ["+listOfElements[i].getText()+"]")
			if(listOfElements[i].getText().contains(tipo.toString())){
				System.err.println("FOUND ["+i+"] ["+listOfElements[i].getText()+"]")
				listOfElements[i].click()
				item=true
				break
			}
		}
		return item;
	}
	
}

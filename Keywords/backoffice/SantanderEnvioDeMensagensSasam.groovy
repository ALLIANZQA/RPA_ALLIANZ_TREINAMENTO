package backoffice

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

import internal.GlobalVariable
import rpacommands.rpaCommands
import auxiliares.ExtractDates
import auxiliares.FileInput
import auxiliares.StringFormatter
import auxiliares.MenuMatriz


public class SantanderEnvioDeMensagensSasam extends rpaCommands{



	private static String fileToDell = "C:\\Users\\Public\\documento.pdf"

	/**
	 * @author be54955
	 * @param inativacao
	 * @return
	 */
	public static boolean pesquisaProposta(List<String> inativacao, String carta, String tipo){
		System.err.println(carta +" "+tipo)
		boolean p
		if(carta.equals("15")){
			if(tipo.equals("RenovaSantanderSASAM2")){
			try{
				//System.err.println("[PROPOSTA ["+inativacao[51].substring(0, 9)+"]")
				//PROPOSTA
				preencheXpath('//*[@id="apolice"]', inativacao[51].substring(0, 9) , 1)
				//PESQUISAR
				selecionaItemLista('Pesquisar','//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td' , 'td' )
				//BT FICHA GESTÃO
				clickXpath('//*[@id="-61"]/div[1]/span', 5);
				//TELA MENU FICHA GESTÃO
				alteraJanejaGenerica(2, 5)
				//NOVA FICHA E GESTÃO - TABELA DE RESULTADOS
				deletaArquivoGenerico(fileToDell);
				System.out.println()
				try{
				    novaFichaGestaoPorAtributo(formataProposta(inativacao[51], ' / 0 / 0'),'')   
				    WebUI.delay(10);
				} catch (Exception e){}
				selecionaItemNaTabelaFichaGestao("Publico", 'Proposta')
				WebUI.delay(30);
				renomearArquivo(fileToDell, inativacao[51].substring(0, 9), ".pdf")
				fecharJanelaAtual(2)
				alteraJanejaGenerica(1, 5)
				fecharJanelaAtual(1)
				alteraJanejaGenerica(0, 5)
				p = true
			} catch (Exception e){
				p = false
			}
			}
			
			if(tipo.equals("RenovaSantander")){
				try{
					//System.err.println("[PROPOSTA ["+inativacao[51].substring(0, 9)+"]")
				    //PROPOSTA
				    preencheXpath('//*[@id="apolice"]', inativacao[22].substring(0, 9) , 1)
				    //PESQUISAR
				    selecionaItemLista('Pesquisar','//*[@id="divAppContent"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td' , 'td' )
				    //BT FICHA GESTÃO
				    clickXpath('//*[@id="-61"]/div[1]/span', 5);
				    //TELA MENU FICHA GESTÃO
				    alteraJanejaGenerica(2, 5)
				    //NOVA FICHA E GESTÃO - TABELA DE RESULTADOS
				    deletaArquivoGenerico(fileToDell);
				    System.out.println()
				    try{
				       novaFichaGestaoPorAtributo(formataProposta(inativacao[22], ' / 0 / 0'),'')   
				       WebUI.delay(10);
				    } catch (Exception e){}
				      selecionaItemNaTabelaFichaGestao("Publico", 'Proposta')
				      WebUI.delay(30);
				      renomearArquivo(fileToDell, inativacao[22].substring(0, 9), ".pdf")
					  fecharJanelaAtual(2)
					  alteraJanejaGenerica(1, 5)
					  fecharJanelaAtual(1)
					  alteraJanejaGenerica(0, 5)
				p = true
				} catch (Exception e){
					p = false
				}
				}
			
			
			
			
			
			
		} else if(carta.equals("30") || carta.equals("NR")){
			p = true
		}
		return p
	}

	/**
	 * @author T_DCDias
	 * @return
	 * Metodo de seleção de itens na tela de ficha e gestão
	 */
	public static selecionaItemNaTabelaFichaGestao(String data, String txnota){
		selecionaListaDeElementos('//*[@id="tablaLista"]/app-list-file/div/div/div/table/tbody','tr')
		int size = listOfElements.size()
		try{
			for(int i=0; i<size; i++){
				if(identificaDadosNaLinha(data, txnota, retornaLinhaTabelaFichaGestao(i))){
					painelDownload(i)
					break
				}
			}
		} catch (Exception e){
			System.err.println("Exception - selecionaItemNaTabela")
		}
	}

	/**
	 * @author be54955
	 * @param inativacao
	 * @return
	 */
	public static finalizaPesquisaProposta(List<String> inativacao){
		try{
			fecharJanelaAtual(2)
			fecharJanelaAtual(1)
			alteraJanejaGenerica(0, 5)
			deletaArquivoGenerico("C:\\Users\\Public\\"+inativacao[51].substring(0, 9)+".pdf");
		} catch (Exception e){
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param pathFileValue
	 * @param name
	 * @return
	 */
	public static renomearArquivo(String pathFileValue, String name, String extencao){
		System.err.println('pathFileValue: '+pathFileValue)
		File f = new File(pathFileValue);
		if (f.renameTo(new File("C:\\Users\\Public\\"+name+extencao))) {
			System.out.println("Arquivo renomeado");
		}
		else{
			System.out.println("Arquivo não renomeado");
		}
	}
}

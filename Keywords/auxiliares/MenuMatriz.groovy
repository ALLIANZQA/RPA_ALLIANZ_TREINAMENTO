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
import rpacommands.rpaCommands

public class MenuMatriz extends rpaCommands{

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static producaoSinistro(){
		subMenuEquals('/*/body/div/div/app-root/app-private/app-private-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div/ul', "span", "Produção & Sinistros")
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static gestaoMarketing(){
		subMenuEquals('/*/body/div/div/app-root/app-private/app-private-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div/ul', "span", "Gestão & Marketing")
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static sinistroSaude(){
		clickXpath("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[2]/div/div[1]/ul/li[1]/a", 2)
	}

	/**
	 * @author T_DCDias	
	 * @return
	 */
	public static producao(){
		clickXpath("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul/li[6]/a", 2)
	}

	public static producaoINTPRD(){
		MenuMatriz.subMenuEquals("/html/body/div[1]/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul", "a", "Produção")
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static consultasEstatisticas(){
		subMenuEquals('/*/body/div/div/app-root/app-private/app-private-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div/ul', "span", "Consultas & Estatísticas")
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static fichaGestao(){
		subMenu("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[2]/div/div[1]/ul/li[2]", "a","Ficha de Gestão")
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static fichaGestaoBoletoAvulso(){
		subMenu("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[3]/div/div[2]/ul/li[2]","a","Ficha de Gestão")
	}

	/**
	 * @author ALZ_ServiceQA
	 * @return
	 */
	public static consultasGerais(){
		subMenu("/*/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div[1]/div/div[2]/ul","a","Consultas Gerais")
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static sinistroGis(){
		clickXpath("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div/div/div[2]/ul/li[1]/a", 2)
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static manutencaoAtribuicaoProfessionais(){
		clickXpath("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div/div/div[2]/ul/li[2]/a", 2)
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static liquidacionesProfesionales(){
		clickXpath("/html/body/div/div/app-root/app-private/app-displayed-menu/div/app-teaser/div/div/div/div[2]/ul/li[3]/a", 2)
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static manutancaoGeral(){
		subMenuEquals('/*/body/div/div/app-root/app-private/app-private-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div/ul', "span", "Manutenção Geral")
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static notaAgendaFichaGestao(){
		subMenu('/*/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div/ul', 'a','Nota/Agenda')
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static indexacaoFichaGestao(){
		try{
			subMenu('/*/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div/ul', 'a','Indexación')
		} catch (Exception e){}

		try{
			subMenu('/*/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div/ul', 'a','Indexação')
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static servicosCorporativos(){
		subMenuEquals('/*/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/div/ul', 'a','Serviços Corporativos')
	}

	/**
	 * @author be54955
	 * @return
	 */
	public static inicioFichaGestao(){
		clickXpath("/*/body/div[1]/div/app-root/file-management/app-header/div/nx-ibl-header/nx-ibl-navigation/div/div[1]/nx-ibl-navigation-desktop/div[1]/div/a/img", 2)
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param xpath
	 * @param tag
	 * @param menu
	 * @return
	 */
	public static subMenu(String xpath, String tag,String menu){
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			System.out.println("["+i+"] ["+listOfElements[i].getText()+"]" )
			if(listOfElements[i].getText().contains(menu)){
				listOfElements[i].click()
				break
			}
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param xpath
	 * @param tag
	 * @param menu
	 * @return
	 */
	public static subMenuEquals(String xpath, String tag,String menu){
		WebUI.delay(2)
		selecionaListaDeElementos(xpath,tag)
		for(int i = 0; i < listOfElements.size(); i++){
			System.out.println("["+i+"] ["+listOfElements[i].getText()+"] ["+menu+"]" )
			if(listOfElements[i].getText().equals(menu)){
				listOfElements[i].click()
				break
			}
		}
	}

	/**NAVEGA NOS NOVOS MENUS!!!
	 * Consultas & Estatísticas
	 * Arquivo Digital
	 * Ficha de Gestão
	 *
	 */
	public static void newFluxoGestao(String xpath){
		try{
			MenuMatriz.consultasEstatisticas()
		} catch (Exception e){
		}

		try{
			MenuMatriz.subMenu(xpath, "a","Nova Ficha de Gestão")
		} catch (Exception e){
		}

		try{
			MenuMatriz.subMenu(xpath, "a","Nueva Ficha de Gestión")
		} catch (Exception e){
		}
	}
}

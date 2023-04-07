
package templateMessage

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

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

public class ReposicaoValoresMessage {

	/**
	 * @author be54955
	 */
	public static String bodyMessage(List<String> registros){
		
		String msg ="Ao(À) "+
		registros[6]+"– Apólice nº "+registros[0]+"\n"+
		"c/c: "+registros[5]+"\n\n\n"+
		"Ref.: Recomposição dos reajustes suspensos no período de setembro a dezembro de 2020.\n\n"+
		"A ALLIANZ SAÚDE S/A (“Allianz Saúde”), com sede na Rua Eugênio de Medeiros, nº 303, 7º andar-parte, "+
		"Pinheiros, Cidade de São Paulo, Estado de São Paulo, inscrita no CNPJ/ME sob o nº 04.439.627/0001-02, "+
		"vem, por meio da presente, através de seu representante legal abaixo-assinado, informar o quanto segue:\n\n"+
		"Em atendimento ao disposto no Comunicado nº 87, de 26 de novembro de 2020, expedido pela Agência Nacional "+
		"de Saúde Suplementar - ANS, a Allianz Saúde esclarece que realizará, a partir de janeiro de 2021, a "+
		"cobrança dos valores referentes aos reajustes suspensos no período de setembro a dezembro de 2020.\n\n"+
		"Neste sentido, seguindo as orientações da ANS, a Allianz Saúde informa que os valores relativos à suspensão "+
		"dos reajustes serão diluídos em 12 (doze) parcelas iguais e sucessivas, de janeiro de 2021 a dezembro de 2021.\n\n"+
		"Por essa razão, o Estipulante recebe, neste momento, o boleto referente à parcela "+registros[15]+", "+
		"da recomposição dos reajustes suspensos no período de setembro a dezembro de 2020 no valor de R\$ "+registros[14]+".\n\n"+
		"O boleto referente a cobertura da competência de "+registros[16]+", no valor total de R\$ "+registros[17]+", "+
		"já fora disponibilizado separadamente, mantendo sua data de vencimento original (dia 15)."+
		"Em caso de dúvidas, entre em contato com seu Account Comercial ou com a área de Relacionamento Empresarial Saúde.\n\n"+
		"Atenciosamente,\n\n\n"+
		"Matthias Kühn\n"+
		"Diretor Produto Saúde\n"+
		"Allianz Saúde S/A.\n\n\n\n"+
		"Rua Eugenío de Medeiros, 303 CEP 05425-000 São Paulo SP\n"+
		"Caixa Postal 1506 CEP 05425-000 – Tel. (0XX11) 3171 6000 Fax (0XX11) 3171 6171\n"+
		"http://www.allianz.com.br"
		
		return msg
	}
	
}

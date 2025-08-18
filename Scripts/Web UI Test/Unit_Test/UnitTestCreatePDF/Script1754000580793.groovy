import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import auxiliares.GeneratorPDF
import auxiliares.ExtractDates


String status = "TESTE"
String msg1 = "IBLBDM-72057 - [Seguralta] Termo de aceite - envio e recebimento"


String corretor = "MASTER TELES CORRETORA DE SEGUROS EIRELI"
String ct = "Termo de Aceite de Co-corretagem Seguralta_"+corretor 
String path = "C://Users//Public"

String msg2 = "DECLARAÇÃO DE CO-CORRETAGEM"
String msg3="A corretora MASTER TELES CORRETORA DE SEGUROS EIRELI, inscrita no CNPJ/MF nº "+
            "290279300001-10, com sede na AV DR ALFREDO AMANCIO FILHO, 1, na Cidade de "+
            "PARAUAPEBAS, Estado de SAO PAULO, CEP: 68515-000, declara, para os devidos fins, seu desejo "+
            "e ciência na emissão de apólices de seguro emitidas pela Allianz Seguros S/A, sociedade "+
            "seguradora inscrita no CNPJ/MF n° 61.573.796/0001-66, com sede na Rua Eugênio de Medeiros, "+
            "303, 1º andar-parte, 2º ao 7º andar, 15º e 16º andares, Pinheiros, na Cidade de São Paulo, Estado "+
            "de São Paulo, CEP 05425-000, em que esta atuar como corretora com a co-corretagem com a "+
            "corretora SGA CORRETORA DE SEGUROS LTDA, inscrita no CNPJ/MF n° 02.671.673/0001-07, com "+
            "sede na AV WALDEMAR HADDAD, 801, na Cidade de SAO JOSE DO RIO PRETO, Estado de SÃO PAULO, "+
            "CEP: 15093-300, atuando esta como líder nas apólices ora emitidas , concordando, ainda, com os respectivos "+
            "percentuais de comissão de corretagem."

GeneratorPDF.createDocumentDeclaracaoCoCorretagem(ct, path)
GeneratorPDF.adicionaTextoCenterDeclaracaoCoCorretagem(msg2, 2)
GeneratorPDF.adicionaTextoJUSTIFIEDDeclaracaoCoCorretagem(msg3, 5)
GeneratorPDF.adicionaTextoCenterDeclaracaoCoCorretagem(dataAtual(), 5)
GeneratorPDF.rodapeDeclaracaoCoCorretagem("MDL CORRETORA DE SEGUROS", "LTDACORRETORA EMISSORA")
GeneratorPDF.closeDocument()

public static String dataAtual(){
	String dt = "São Paulo "+ExtractDates.dataAtualizadaDia()+" de "+ 
	                         ExtractDates.retornaMesLiteral(ExtractDates.dataAtualizadaMes())+" de "+
							 ExtractDates.dataAtualizadaAno()
	return dt;
}

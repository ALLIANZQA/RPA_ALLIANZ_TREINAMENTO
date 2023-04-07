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

import RegexCharacter

public class StringFormatter {


	/**
	 * @author ALZ_ServiceQA
	 * @param linhas
	 * @return
	 */
	public static String formatterEmailDestination(List<String> linhas){
		String emails;
		int tamanho = linhas.size()
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tamanho;i++){
			if(i == 0 ){
				sb.append(" ")
			} else {
				sb.append(", ")
			}
			sb.append(linhas.get(i).toString())
		}
		return emails = sb.toString();
	}


	/**
	 * @author be54955
	 * @param str
	 * @return
	 */
	public static String tokenStringSUSEP(String str){

		try{

			Map<String, String> numbers = new HashMap<String, String>();
			String docNomeTemp
			String nomeCompleto
			String numCorretor
			String cpfcnpj
			String recad
			String situacao
			String produtos
			StringBuilder strb
			List<String> documentos = new ArrayList<String>()
			str = str.replaceAll('0','O')
			str = str.replaceAll('CPF','')

			str = keyWord(str)

			String[] token = str.split(" ")

			try{
				for(int i = 0; i < token.size(); i++){
					System.out.println('['+i+'] ['+token[i].toString()+']')
				}
			} catch (Exception e){}




			docNomeTemp = getDocOnNameStringSUSEP(str)
			numbers = formatterNameStringSUSEP(str, numbers, docNomeTemp)

			try{
				strb = new StringBuilder()
				for(int i = 0; i < token.size(); i++){
					if(i == 0 ){
						strb.append(token[i].toString()+" ")
						nomeCompleto = strb.toString()
					} else if(!checkStringSUSEP(token[i].toString())){
						strb.append(token[i].toString()+" ")
						nomeCompleto = strb.toString()
					} else if(token[i].toString().equals(numbers.get(token[i].toString()) )){
						strb.append(token[i].toString()+" ")
						nomeCompleto = strb.toString()
						docNomeTemp = token[i].toString()
					}	else if(token[i].toString().equals(docNomeTemp)){
						strb.append(token[i].toString()+" ")
						nomeCompleto = strb.toString()
						docNomeTemp = token[i].toString()
					} else {
						break
					}
					//				System.out.println(nomeCompleto )
				}
			} catch (Exception e){}

			try{
				strb = new StringBuilder()
				for(int i = 0; i < token.size(); i++){
					if(token[i].toString().equals('Ativo') || token[i].toString().equals('Suspenso') || token[i].toString().equals('Cancelado')){
						for(int j = i+1 ; j < token.size(); j++){
							strb.append(token[j].toString()+" ")
							produtos = strb.toString()
						}
					}
				}
			} catch (Exception e){}

			try{
				for(int i = 0; i < token.size(); i++){
					if(!nomeCompleto.contains(token[i].toString())){
						if(checkStringSUSEP(token[i].toString())){
							if(!token[i].toString().equals(docNomeTemp)){
								documentos.add(token[i].toString())
							}
						}
					}

					if(token[i].toString().equals('Sim')){
						documentos.add(token[i].toString())
					}

					if(token[i].toString().equals('Ativo') || token[i].toString().equals('Suspenso') || token[i].toString().equals('Cancelado')){
						documentos.add(token[i].toString())
					}
				}
				//		  System.err.println('documentos '+documentos)
			} catch (Exception e){}


			for(int d = 0; d< documentos.size();d++){
				numCorretor = documentos.get(0).toString()
				cpfcnpj = documentos.get(1).toString()
				recad = documentos.get(2).toString()
				situacao = documentos.get(3).toString()
			}

			//		System.out.println("\nNome ["+nomeCompleto+"] Número de corretor ["+numCorretor+"] CPF/CNPJ ["+cpfcnpj+"] Recad. ["+recad+"] Situação ["+situacao+"] Produtos ["+trocaNull(produtos)+"]\n")

			String s = nomeCompleto+" | "+numCorretor+" | "+cpfcnpj+" | "+recad+" | "+situacao+" | "+ trocaNull(produtos)
			System.err.println(s)
			return s

		} catch (Exception e){
			System.err.println("["+str+"]\n["+e+"]")
			return str
		}
	}

	/**
	 * @author be54955
	 * @param s
	 * @return
	 */
	public static boolean checkStringSUSEP(String s){
		boolean check = false
		String[] test = s.split("")
		for(int x = 0; x < test.size(); x++){
			if( test[x].toString().equals("0") ||
			test[x].toString().equals("1") ||
			test[x].toString().equals("2") ||
			test[x].toString().equals("3") ||
			test[x].toString().equals("4") ||
			test[x].toString().equals("5") ||
			test[x].toString().equals("6") ||
			test[x].toString().equals("7") ||
			test[x].toString().equals("8") ||
			test[x].toString().equals("9") ){
				check = true
			}
		}
		return check
	}

	/**
	 * @author be54955
	 * @param s
	 * @return
	 */
	public static String trocaNull(String s){
		String n
		if(s.equals(null)){
			n = ""
		} else {
			n = s
		}
		return n
	}

	/**
	 * @author be54955
	 * @param texto
	 * @return
	 */
	public static Map<String, String> formatterNameStringSUSEP(String texto, Map<String, String> numbers, String numDonName){
		int numeroNoNome = getDocument(texto)
		//		System.err.println('\n-['+numeroNoNome+']-\n')
		String[] token = texto.split(" ")
		try{

			for(int i = 0; i < token.size(); i++){
				String s = token[i].toString()
				if(checkStringSUSEP(s)){
					int t = s.length()
					//				   System.out.println('\n['+t+']\n')
					if(t==numeroNoNome){
						if(!token[i].toString().equals(numDonName)){
							//			         System.out.println('\n['+i+'] ['+token[i].toString()+'] ['+t+']\n')
							numbers.put(token[i].toString(),token[i].toString())
						}
					}
				}
			}
			return numbers
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param texto
	 * @return
	 */
	public static int getDocument(String texto){
		int value
		try{
			String[] token = texto.split(" ")
			for(int i = 0; i < token.size(); i++){
				//			System.out.println('\n['+i+'] ['+token.size()+']\n')
				String s = token[i].toString()
				if(checkStringSUSEP(s)){
					int t = s.length()
					if(t==numbersOff(t)){
						//			    System.out.println('\n['+i+'] ['+token[i].toString()+'] ['+t+'] ['+numbersOff(t)+']\n')
						value = t
					}
				}
			}
		} catch (Exception e){}
		return value
	}

	/**
	 * @author be54955
	 * @param n
	 * @return
	 */
	public static int numbersOff(int n){
		Map<Integer, Integer> numbers = new HashMap<Integer, Integer>();
		numbers.put(1,1)
		numbers.put(2,2)
		numbers.put(3,3)
		numbers.put(4,4)
		numbers.put(5,5)
		numbers.put(6,6)
		numbers.put(7,7)
		numbers.put(8,8)
		numbers.put(10,10)
		numbers.put(11,11)
		numbers.put(12,12)
		numbers.put(13,13)
		//		numbers.put(14,14)//RG
		numbers.put(15,15)
		numbers.put(16,16)
		numbers.put(17,17)
		numbers.put(19,19)
		numbers.put(20,20)
		numbers.put(21,21)
		numbers.put(22,22)
		numbers.put(23,23)
		return numbers.get(n)
	}

	/**
	 * @author be54955
	 * @param texto
	 * @param numbers
	 * @return
	 */
	public static String getDocOnNameStringSUSEP(String texto){
		try{
			int numeroNoNome = getDocument(texto)
			//		    System.err.println('\n-['+numeroNoNome+']-\n')
			String[] token = texto.split(" ")
			String numDonName

			for(int i = 0; i < token.size(); i++){
				String s = token[i].toString()
				if(checkStringSUSEP(s)){
					int t = s.length()
					//			   System.out.println('\n['+t+']\n')
					if(t==numeroNoNome){
						//	     System.out.println('\n-['+i+'] ['+token[i].toString()+'] ['+t+']-\n')
						numDonName = token[i].toString()
						break
					}
				}
			}
			return numDonName
		} catch (Exception e){}
	}

	/**
	 * @author be54955
	 * @param p
	 * @return
	 */
	public static String keyWord(String p){
		StringBuilder strb = new StringBuilder()
		String[] token = p.split(" ")
		for(int i = 0; i < token.size(); i++){
			if(i==0){
				if(token[i].toString().equals("Ativo")){
					strb.append("ATIVO ")
				} else if(p.trim().equals("Cancelado")){
					strb.append("CANCELADO ")
				} else if(p.trim().equals("Suspenso")){
					strb.append("SUSPENSO ")
				} else if(checkStringSUSEP(token[i].toString())){
					strb.append(token[i].toString()+" ")
				} else {
					strb.append(token[i].toString()+" ")
				}
			} else{
				strb.append(token[i].toString()+" ")
			}
		}
		return strb.toString()
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param b
	 * @return
	 */
	public static String emailBodyFormatter(String b){
		String body
		try{
			//System.out.println("TEXT TO FORMATTER\n"+b)
			List<String> registros = new ArrayList<>();
			StringBuilder strb = new StringBuilder()

			String[]t = b.split(" ")
			strb.append("<head><p>")
			int j = 0
			for(int i = 0; i < t.size(); i++){
				System.out.println("["+i+"] ["+t[i]+"]")
				if(RegexCharacter.regexChar("[a-zA-Z,Á,0-9]", t[i] )){
					registros[j] = t[i].trim()
					j++
				}
			}


			for(int i = 0; i < registros.size(); i++){
				if(i==0){
					//strb.append(registros[i]+" ")
					if(registros[i].equals("Prezado Corretor,")){
						strb.append(registros[i]+"<p>")
					} else if(registros[i].equals("Prezado")){
						strb.append(registros[i]+" ")
					}

				} else if(i>0) {
					if(!registros[i].contains("Corretor,")){
						if(registros[i].contains(".")){

							strb.append(" "+registros[i]+"<p>")

						} else {
							if(registros[i].equals("Atenciosamente,")){
								strb.append(registros[i]+"<p>")
							} else{
								strb.append(" "+registros[i])
							}
						}
					}
				}
			}

			strb.append("</p>")
			body = strb.toString()
			System.out.println(body)
			System.out.println("\n")
		} catch (Exception e){}
		return body
	}


	/**
	 * @author be54955
	 * @param b
	 * @return
	 */
	public static String removeNumerosDeStrings(String b){
		String result
		List<String> registros = new ArrayList<>();
		StringBuilder strb = new StringBuilder()
		String[]t = b.split(" ")
		int j = 0
		for(int i = 0; i < t.size(); i++){
			System.out.println("["+i+"] ["+t[i]+"]")
			if(RegexCharacter.regexChar("[a-zA-Z]", t[i] )){
				registros[j] = t[i].trim()
				j++
			}
		}

		for(int i = 0; i < registros.size(); i++){
			strb.append(registros[i]+" ")
		}

		result = strb.toString().trim()

		System.out.println("result ["+result+"]")

		return result
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param b
	 * @return
	 */
	public static String emailBodyFormatterPendenciaAutomaticaCorretor(String b){
		try{
			StringBuffer sbfr = new StringBuffer()
			String []v = b.split("")

			for(int i = 0; i < v.size(); i++){
				sbfr.append(v[i].toString())

				if(v[i].toString().trim().equals("Prezado (a) Corretor.")){
					sbfr.append("<p><p>")
				}

				if(v[i].toString().trim().equals(".")){
					sbfr.append("<br>")
				}

				if(v[i].toString().trim().equals("*")){
					sbfr.append("<p><p>")
				}

				if(v[i].toString().trim().equals(";")){
					sbfr.append("<br>")
				}

				if(v[i].toString().trim().equals(":")){
					sbfr.append("<br>")
				}

				if(v[i].toString().trim().equals(">")){
					sbfr.append("<br>")
				}
			}
			return sbfr.toString()
		} catch (Exception e){}
	}

	/**
	 *
	 * @param n
	 * @return
	 */
	public static String formataDocumentoCPFCNPJ(String n){
		String s
		String b = n.replaceFirst('^0+(?!$)','')
		if(b.length().equals(10) || b.length().equals(13)){
			s = "0"+b
		} else {
			s=b
		}
		return s
	}

	/**
	 * @author T_DCDias
	 * @param valor
	 * @return
	 * Remove espaço entre R$ e  valor
	 */
	public static String removeEspacos(String valor){
		String valorSemEspaco;
		StringBuffer sb = new StringBuffer();
		String[] lvr =valor.split("");
		for(int i = 0; i < lvr.length ; i++){
			if(i!=2){
				sb.append(lvr[i]);
			}
		}
		return	valorSemEspaco = sb.toString();
	}

	/**
	 * @author BE54955
	 * @param b
	 * @return
	 */
	public static String returnStringNumber(String b){
		String result
		StringBuilder strb
		List<String> registros = new ArrayList<>();
		strb = new StringBuilder()
		String[]t = b.replaceAll("[a-zA-Z-:.´]", "").split(" ")
		int j = 0
		for(int i = 0; i < t.size(); i++){
			if(t[i].size()>0){
				//	System.out.println("["+i+"] ["+t[i]+"]")
				strb.append(t[i])
			}
		}
		result = strb.toString()
		strb = new StringBuilder()
		String[]t2 = result
		for(int i = 0; i < t.size(); i++){
			if(t2[i].size()>0){
				//	System.out.println("["+i+"] ["+t2[i]+"]")
				if(checkStringSUSEP(t2[i])){
					strb.append(t2[i])
				}
			}
		}
		result = strb
		return result
	}

}

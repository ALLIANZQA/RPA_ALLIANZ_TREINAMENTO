
import rpacommands.rpaCommands
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import auxiliares.ExtractDates

import java.util.List
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import auxiliares.StringFormatter
import auxiliares.BruteForceSubstringSearch
import auxiliares.CurrencyMonetary

import java.text.SimpleDateFormat;
import java.time.LocalDate

//WebUI.openBrowser('')

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;




import auxiliares.switchLeitorExcel
import bean.MassaDados
import auxiliares.switchAtualizarExcel


//String ExcelCopiado = "C://Users//Public//RPA_ALLIANZ//Data Files//ContactCenter//ContactCenter.xlsx";


WebUI.openBrowser('')

//somaDeValores("01","02","03","04", "victor bora estudar caraii")
//
//
//
//public void somaDeValores(String... numeros){
//	for(int i = 0; i<numeros.size();i++){
//		System.out.println(i+" - "+numeros[i])
//	}
//}




//switchAtualizarExcel.switchUpdateExcel('ContactCenter', ExcelCopiado ,"TESTE !!!!!",11 );

//auxiliares.WriteExcelFactory.updateSheetLine("", 1, ExcelCopiado, "TESTE !!!!!", 10, 'ContactCenter');

//System.out.println(s.size())


//
//if(s.size()==5){
//String n = s.substring(0, 4)
//System.out.println(n)
//}


//                                     //  D. BÁSICOS : 0201-SAÚDE EMPRESARIAL [769492444]
//System.out.println(returnStringNumber("D. BÁSICOS : 0201-SAÚDE COLETIVO EMPRESARIAL GG [769490877]"))
//
//public static String returnStringNumber(String b){
//	String result
//	StringBuilder strb
//	List<String> registros = new ArrayList<>();
//	strb = new StringBuilder()
//	String[]t = b.replaceAll("[a-zA-Z-:.´]", "").split(" ")
//	int j = 0
//	for(int i = 0; i < t.size(); i++){
//		if(t[i].size()>0){
//			System.out.println("["+i+"] ["+t[i]+"]")
//			strb.append(t[i])
//		}
//	}
//	result = strb.toString()
//	strb = new StringBuilder()
//	String[]t2 = result
//	for(int i = 0; i < t.size(); i++){
//		if(t2[i].size()>0){
//			System.out.println("["+i+"] ["+t2[i]+"]")
//			if(checkStringSUSEP(t2[i])){
//			strb.append(t2[i])
//			}
//		}
//	}
//	result = strb
//	return result
//}
//
//public static boolean checkStringSUSEP(String s){
//	boolean check = false
//	String[] test = s.split("")
//	for(int x = 0; x < test.size(); x++){
//		if( test[x].toString().equals("0") ||
//		test[x].toString().equals("1") ||
//		test[x].toString().equals("2") ||
//		test[x].toString().equals("3") ||
//		test[x].toString().equals("4") ||
//		test[x].toString().equals("5") ||
//		test[x].toString().equals("6") ||
//		test[x].toString().equals("7") ||
//		test[x].toString().equals("8") ||
//		test[x].toString().equals("9") ){
//			check = true
//		}
//	}
//	return check
//}


//String[] coberturas = new String[3]
//coberturas[0] = '14750';
//coberturas[1] = '1475';
//coberturas[2] = '';
//
//for(int i = 0; i<coberturas.size();i++){
//	System.out.println("["+i+"] ["+coberturas[i]+"] ["+coberturas[i].size()+"] ["+mapCobertura(i)+"]")
//}
//
//public  static String mapCobertura(int c){
//	Map<Integer, String> mes = new HashMap<String, String>();
//	mes.put(0,"Danos Elétricos");
//	mes.put(1,"Vendaval");
//	mes.put(2,"Impacto de veiculo");
//	return mes.get(c);
//}


//Valor recupetado [D. EMISSÃO : 0202-SAÚDE PME [757231974]]
//TELA [D. EMISSÃO]


//System.out.println("D. EMISSÃO : 0202-SAÚDE PME [757231974]".contains("D. CLIENTE"))


//System.out.println(removeNumerosDeStrings("D. BÁSICOS : 0202-SAÚDE PME [757080861]"))
//
//public static String removeNumerosDeStrings(String b){
//	String result
//	StringBuilder strb
//	List<String> registros = new ArrayList<>();
//	strb = new StringBuilder()
//	String[]t = b.replaceAll("[a-zA-Z-:.´]", "").split(" ")
//	int j = 0
//	for(int i = 0; i < t.size(); i++){
//		if(t[i].size()>0){
//			System.out.println("["+i+"] ["+t[i]+"]")
//			strb.append(t[i])
//		}
//	}
//	result = strb.toString()
//	strb = new StringBuilder()
//	String[]t2 = result
//	for(int i = 0; i < t.size(); i++){
//		if(t2[i].size()>0){
//			System.out.println("["+i+"] ["+t2[i]+"]")
//			if(checkStringSUSEP(t2[i])){
//			strb.append(t2[i])
//			}
//		}
//	}
//	result = strb
//	return result
//}	
//
//
//	
//	public static boolean checkStringSUSEP(String s){
//		boolean check = false
//		String[] test = s.split("")
//		for(int x = 0; x < test.size(); x++){
//			if( test[x].toString().equals("0") ||
//			test[x].toString().equals("1") ||
//			test[x].toString().equals("2") ||
//			test[x].toString().equals("3") ||
//			test[x].toString().equals("4") ||
//			test[x].toString().equals("5") ||
//			test[x].toString().equals("6") ||
//			test[x].toString().equals("7") ||
//			test[x].toString().equals("8") ||
//			test[x].toString().equals("9") ){
//				check = true
//			}
//		}
//		return check
//	}


//System.out.println(rpaCommands.validaExistenciaDeArquivo("C:\\Users\\Public\\119885625.pdf"))


//List<MassaDados> listaExcel;
//String path = 'C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Auto\\ApolicesHomologacaoData\\ApolicesHomologacao.xlsx';
//String tipoAba = "ApolicesHomologacao"
//
//auxiliares.WriteExcelFactory.updateSheetLine("",0, path, "testeeee", 80, tipoAba);
//auxiliares.WriteExcelFactory.updateSheetLine("",0, path, "testeeee", 81, tipoAba);


//listaExcel = switchLeitorExcel.switchExcel(path, tipoAba);
////listaExcel.size();
//System.out.println(listaExcel.size());





//LocalDateTime datetime = LocalDateTime.now();
//System.out.println(datetime);

//
//String dateTime = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a")
//.format(LocalDateTime.now());
//
//System.out.println(dateTime);



//String hora = ExtractDates.horaAtualizada()
//int nHora = Integer.parseInt(hora.replace(":", ""))
//System.out.println("Hora atual ["+nHora+"]")


//Calendar cal = Calendar.getInstance();
//cal.set(2019, Calendar.JANUARY, 20); // 20 de janeiro de 2019
//cal.add(Calendar.DAY_OF_MONTH, 5); // somar 5 dias
//cal.add(Calendar.MONTH, 3); // somar 3 meses
//System.out.println(cal.getTime()); // Thu Apr 25 16:15:27 BRT 2019

//LocalDate data = new LocalDate(2019, 1, 20) // 20 de janeiro de 2019
//.plusDays(5) // somar 5 dias
//.plusMonths(3); // somar 3 meses
//System.out.println(data.getYear()); // 2019-04-25




//
//if(time(1706, 5)){
//	WebUI.openBrowser('')
//}
//
//
///**
// * @author BE54955
// * @param t
// * @param p
// * @return
// */
//public static boolean time(int t, int p){
//	boolean hs = false 
//	for(int i = 0 ; i<1000 ; i++){
//		if(compareTime(t)){
//			hs = true 
//			break
//		}
//		WebUI.delay(p)
//	}
//	return hs
//}
//
//
///**
// * @author BE54955
// * @param t
// * @return
// */
//public static boolean compareTime(int t){
//	boolean h = false 
//	String hora = ExtractDates.horaAtualizada()
//	int nHora = Integer.parseInt(hora.replace(":", ""))
//	System.out.println("Hora atual ["+nHora+"] Hora esperada ["+t+"]")
//	if(nHora==t){
//		h = true 
//	}
//	return h
//}






//String texto = "" 
//
//System.out.println(selecionaParagrafo(texto))
//
//public static selecionaParagrafo(String b){
//	boolean enviado = false
//	System.out.println("["+b.length()+"] ["+b+"]")
//	if(b.length()>0){
//	   List<String> registros = new ArrayList<>();
//	   String[]t = b.split("-")
//	   int j = 0
//	   for(int i = 0; i < t.size(); i++){
//		   System.out.println("["+i+"] ["+t[i]+"]")
//		   enviado = true 
//	//	   selecionaItemListaContains(t[i].toString(), '//*[@id="rightcolumn"]' , 'b')
//	   }
//	}
//	return  enviado
//}


//WebUI.openBrowser('')

//String aba = "RecusaPropostaAuto"
//String path = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Auto\\RecusaPropostaAuto\\RecusaPropostasAuto.xlsx"

//String aba = "DistribuicaoAutomaticaIR"
//String path = "C:/Users/Public/RPA_ALLIANZ/Data Files/GCF/DistribuicaoAutomaticaInspecaoRisco.xlsx"
//String registro = "TESTE"
//auxiliares.WriteExcelFactory.updateSheetLine("", 1692, path, registro, 25, aba);
//auxiliares.WriteExcelFactory.updateSheetLine("", 12, path, registro, 2,  aba);

//auxiliares.WriteExcelFactory.updateSheetLine('', i-1, path, evidencia, 12 , 'DescontosEspecíficosSinistro');


//String telaDadosEmissao = "20220910".substring(6, 8)

//int valor = Integer.parseInt(telaDadosEmissao);

//System.out.println(ExtractDates.dataAtualizada())
	
//WebUI.openBrowser('')
	

//String b = "9159-9151-9152-9153"
//List<String> registros = new ArrayList<>();
//String[]t = b.split("-")
//int j = 0
//for(int i = 0; i < t.size(); i++){
//	System.out.println("["+i+"] ["+t[i]+"]")
//	if(auxiliares.RegexCharacter.regexChar("[0-9]", t[i] )){
//		registros[j] = t[i].trim()
//		j++
//	}
//}






//
//	for(int i = 0; i < registros.size(); i++){
//		strb.append(registros[i]+" ")
//	}
//
//	result = strb.toString().trim()
//
//	return result
//}



//String aba = "Cota_resid"
//String path = "C:/Users/Public/RPA_ALLIANZ/Data Files/residencia/Cota_Resid.xlsx"
//String registro = "TESTE"
//
//
//auxiliares.WriteExcelFactory.updateSheetLine("", 0, path, registro, 25, aba);
//auxiliares.WriteExcelFactory.updateSheetLine("", 0, path, registro, 26, aba);
//auxiliares.WriteExcelFactory.updateSheetLine("", 0, path, registro, 27, aba);
//
//auxiliares.WriteExcelFactory.updateSheetLine("", 3, path, registro, 25, aba);
//auxiliares.WriteExcelFactory.updateSheetLine("", 3, path, registro, 26, aba);
//auxiliares.WriteExcelFactory.updateSheetLine("", 3, path, registro, 27, aba);




//System.out.println(dataAtual());




//public static String dataAtual(){
//	String strdt
//	try{
//		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
//		Date date = new Date(System.currentTimeMillis());
//		strdt = formatter.format(date).toString()
//	} catch (Exception e){
//	}
//	return strdt
//}

//  "[a-zA-Z]"

//String s = "O pedido com o número 675332 foi corretamente gravado.".replaceAll("[a-zA-Z ú .]", "")

//System.out.println(s)

//public static String ajusteMSGFG(String s){
//	
//	String str
//	if(s.equals("Referencia Inexistente")){
//		str = s
//	} else {
//	 str = "Alerta não apresentada"
//	}
//	return str
//}

//String strsplit = s.replaceAll("\\s+",'')
//System.out.println("["+strsplit+"]")
//
//String nValor = s.replaceAll("( ) registros","")
//System.out.println("["+nValor+"]")

//String nStr = s.replaceAll("[-,/,' ']",'')

///String nValor = s.replaceAll("[-()/.A-Z a-zÓ]","").subSequence(10,13)
//System.out.println("["+nValor+"]")

//int linhas = Integer.parseInt(nValor)
//System.out.println("["+linhas+"]")

//if(s.length()==8){
//	
//	System.out.println("CEP : "+s.subSequence(0,5))
//	System.out.println("SUFIXO : "+s.subSequence(5,8))
//	
//}

//WebUI.openBrowser('')


//String v = '1'
//
//System.out.println(ajusteValorCoberturas(v))
//
//
//	public static boolean valorZero(String v){
//		boolean b = false
//		if(v.equals('0')){
//			b = true
//		} else {
//			String nStr
//			nStr = v.replaceAll(',','')
//			Integer number = Integer.valueOf(nStr);
//			if(number==0){
//				b = true
//			}
//		}
//		return b
//	}
//
//
//	public static String ajusteValorCoberturas(String v){
//		String b
//
//		String nStr = v.replaceAll(',','.')
//		double d = Double.parseDouble(nStr);
//		String nStr2 = String.valueOf(d)
//		b = nStr2.replace('.',',')
//
//
//		return b
//	}


//
//def vigencia = '21.10.2021'
//def dia = '0'
//
//System.out.println(ajustaDiaPagamento(vigencia, dia))
//
//public static String ajustaDiaPagamento(String v, String d){
//	String nValue
//	try{
//	int n = Integer.parseInt(d)
//	if(n == 0){
//	 nValue = v.substring(0, 2)
//	}
//	} catch (Exception e){
//	
//	}
//	return nValue
//}

//
//System.out.println("Date : "+ formataDocumentoCPFCNPJ('00000000000191'))
//
//
//	public static String formataDocumentoCPFCNPJ(String n){
//		String s
//		String b = n.replaceFirst('^0+(?!$)','')
//		if(b.length().equals(10) || b.length().equals(13)){
//			s = "0"+b
//		} else {
//		   s=b
//		}
//		return s
//    } 



//	public static String ajusteValorCoberturas(String v){
//		String b 
//		int result = Integer.parseInt(v);
//		b = String.valueOf(result)
//		return b
//	}
//
//
//
//public static String formataData(String dt){
//	System.out.println("Date to format "+dt )
//	
//	String NdtFormat = dt.replace('.', '')
//	System.out.println("Date to formated "+NdtFormat )
//	return NdtFormat
//}

//public static String dataFutura(){
//	String dateAfter
//	Calendar now = Calendar.getInstance();
//	//System.out.println("Current date : " + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR));
//	// Add days to current date using Calendar.add method
//	now.add(Calendar.DATE, 1);
//	String date = now.get(Calendar.DATE)
//	String month = (now.get(Calendar.MONTH) + 1)
//	String year = now.get(Calendar.YEAR)
//	dateAfter = date+"/"+month+ "/"+year
//	
//	return dateAfter
//}




//
//String f = "Prezado (a) Corretor. Existe (m) uma ou mais pendência (s) gerada (a) para a proposta NRº 115143833 *O RETORNO VIA FICHA DE GESTÃO DEVE SER REALIZADO ATRAVES DA OPÇÃO DE INDEXAR ARQUIVO NO CODIGO 21* Conforme retorno da CBE existe divergência entre os dados da porposta e os dados da apólice que esta sendo renovada. - Para divergência de bônus ou tipo de renovação, informar se podemos distribuir a diferença de prêmio nas parcelas ou reduzir a comissão; - Para apólice vigência, enviar copia do endosso de cancelamento emitido na congenere; - Para renovação com substituição para veículo Zero KM, enviar cópia da nota fiscal com data de saída; Importante: Caso a forma de pagamento seja CARTAO DE CREDITO, para emissão com retificação será necessária alteração para CARNE ou DEBITO (infomra dados bancários). Se não desejar alteração da forma de pagamento procederemos com a recusa da proposta para que uma nova possa ser transmitida sem pendência. Os detalhes sobre a divergência devem ser verificados na opção de CONSULTA DE BONUS na tela do EPAC. Não havendo retorno conclusivo da pendência a recusa poderá ser gerada a qualquer momento, desde que obedeça ao limite do prazo SUSEP. RETORNO DA(s) PENDÊNCIA(s)>>> Para nos retornar a pendência há duas opções: • Via portal, através de nota especifica, informada no texto da pendência que consta em seu painel de avisos; OU • Através deste e-mail, e para isto o Campo Assunto Não deve ser alterado. Importante: • Após o retorno da pendência o time de emissão tem um prazo de até 03 dias úteis para análise e emissão do seu documento. • O prazo para retorno desta pendência é de 2 dias uteis. • O não recebimento do retorno dentro do prazo, pode acarretar a recusa da proposta. A disposição."
//
//
////String n = f.replaceAll(" ", "")
//
////System.out.println("["+n+"]")
//
//StringBuffer sbfr = new StringBuffer()
//String []v = f.split("")
//
//for(int i = 0; i < v.size(); i++){
////	System.out.println("["+i+"] [ "+v[i].toString()+"]")
//	
//	sbfr.append(v[i].toString())
//	
//	if(v[i].toString().trim().equals(".")){
//		sbfr.append(v[i].toString()+"<p>")
//	}
//	
//	if(v[i].toString().trim().equals("*")){
//		sbfr.append(v[i].toString()+"<p><p>")
//	}
//	
//	if(v[i].toString().trim().equals(";")){
//		sbfr.append(v[i].toString()+"<br>")
//	}
//	
//	if(v[i].toString().trim().equals(":")){
//		sbfr.append(v[i].toString()+"<p>")
//	}
//	
//	if(v[i].toString().trim().equals(">>>")){
//		sbfr.append(v[i].toString()+"<br>")
//	}
//	
//}




//System.out.println("PLS7A54".length())


//String vr = "99.000,00".replaceAll(",00", "")
////vr.replace(".", "")
//

//
//
//nValue.replaceAll('/.', '')

//int v = Integer.parseInt(nValue)

//System.out.println(nValue)

//String s = " 10 ESU3760 9BFVEADSXDBS41363 CARGO 816 504134-1 2013 08 SP RIBEIRAO PIRES 119197,00 Incluído"
//System.out.println("["+identificaItem(s)+"]")
//
//
//	public static boolean identificaItem(String s){
//	  boolean item = false
//	    String strItem = s.substring(0, 3)
//		String strNitem = strItem.trim()
//	    int numItem = Integer.parseInt(strNitem)
//		if(numItem > 9){
//			item = true
//		}
//		System.out.println("ITEM ["+numItem+"] ["+item+"]")
//	return item
//	}





//String nValue = s.substring(0, 7)

//StringBuffer strbf = new StringBuffer()
//
//String []v = s.split("")
//for(int i = 0; v.size(); i++){
//	System.out.println(i+" - "+v[i])
//	if(i> 2){
//	   strbf.append(v[i])
//	   if(v[i].toString().contains(" ")){
//	      break
//	   }
//	}
//}
//String nValueSrtbf = strbf.toString()
//System.out.println("Placa ["+nValueSrtbf+"] length ["+nValueSrtbf.trim().length()+"]")




//
//
//if(s.contains("semi-reboque")){
//	System.out.println(true)
//} else {
//   System.out.println(false)
//}


//Random ran = new Random()
//int n = ran.nextInt(4)
//String v = Integer.toString(n)
//
//System.out.println(v);


//String validaErro ="MODELOS ESPECIAIS / SEMI-REBOQUE NACIONAL / Cat. 52 - semi-reboque / 2007" 
//
//System.out.println( identificaSemiReboque(validaErro, 'semi-reboque')  )
//
//public static boolean identificaSemiReboque(String txt, String path){
//	boolean c = false
//	if(txt.contains(path)){
//		c= true
//	}
//	return c 
//}


//[100.000,00]
//[26] [100.000,00]
//[27] [50.000,00]

//

//double n = Double.parseDouble(op.replace('.', ''))

//int i = Integer.parseInt(op.replace('.', ''))

//System.out.println( n )



// [1.000.000,00]


//String s = "4 FNI1114 up! 005367-8 2015 N 34854 Compreensiva 100 Franquia Reduzida 34854 0 100000 100000 0 0 0 0 0 Plano 3 Vidros blindados Não contratado Incluído 1251 10 - Vistoria Digital Obrigatória "


//System.out.println( s.length() )



//System.out.println( auxiliares.BruteForceSubstringSearch.searchIndex( 'Incluído', s ) )

//System.out.println(s.substring(auxiliares.BruteForceSubstringSearch.searchIndex( 'Incluído',s) , s.length()))


//[100] [3]
//100.00000
//[50] [2]
//50.00000


			
			
//System.out.println(valor.substring(0, 6))

//System.out.println(valor.substring(0, 7))

//String p = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Auto\\RenovacaoAutoFrotaData\\RenovacaoAutoFrota.xlsx"
//String p = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Sinistro\\DescontosEspecíficosSinistro.xlsx"

//auxiliares.WriteExcelFactory.updateSheetLine("TESTEEEEEE 1", 0,p, 'TESTEEEEEEEEEEEEE 5', 37, 'RenovacaoAutoFrota');
//auxiliares.WriteExcelFactory.updateSheetLine("TESTEEEEEE 1", 0,p, 'TESTEEEEEEEEEEEEE 5', 38, 'RenovacaoAutoFrota');







//System.out.println("Campo vazio "+ campoVazio(""))
//
//public static boolean campoVazio(String d){
//boolean pj = false
//	if(d.length()==0){
//		pj = true
//	}
//	return pj
//}





//String v = "115832504 / 0 / 0"


///System.out.println(v.substring(0, 9)) alertListBloc [ Risco com Cobertura pela Apólice 5177202164310611572]Alert [ Risco com Cobertura pela Apólice 5177202164310611572]


// Risco com Cobertura pela Apólice 5177202164310611572

//System.out.println(alertListBlocList(" Risco com Cobertura pela Apólice"))

//public static boolean alertListBlocListVareaveis(String alert){
//	System.err.println("alertListBloc ["+alert+"]")
//	boolean r = true
//	List<String> listOfAlerts = new ArrayList<String>()
//	listOfAlerts.add(" Risco com Cobertura pela Apólice ")
//	System.out.println("Alert ["+alert+"]")
//	for(int i = 0; i < listOfAlerts.size(); i++){
//		System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
//		if(listOfAlerts[i].contains(alert.toString())){
//			System.err.println("FOUND ALERT ["+i+"] ["+listOfAlerts[i]+"]")
//			r = false
//			break
//		}
//	}
//	System.out.println("Alert ["+r+"]")
//	return  r
//}

// Vistoria Prévia Presencial Obrigatória


//alertListContinueList("Vistoria Prévia Presencial Obrigatória")
//
//public static String alertListContinueList(String alert){
//	String r
//	List<String> listOfAlerts = new ArrayList<String>()
//	listOfAlerts.add("Tipo de renovação divergente")
//	listOfAlerts.add("A placa informada não corresponde ao chassi do veículo.")
//	listOfAlerts.add("Campo necessário")
//	listOfAlerts.add("Nome alterado conforme a receita federal, favor avaliar")
//	listOfAlerts.add("Nome do condutor alterado conforme a receita federal, favor avaliar")
//	listOfAlerts.add("Vistoria Prévia Presencial Obrigatória")
//	listOfAlerts.add("Nome do condutor alterado conforme a receita federal, favor avaliar")
//	listOfAlerts.add("Campo 'Finalidade de Uso' é obrigatório.")
//	listOfAlerts.add(" Vigência retroativa não permitida")
//	
//	for(int i = 0; i < listOfAlerts.size(); i++){
//		System.err.println("["+i+"] ["+listOfAlerts[i]+"]")
//		if(listOfAlerts[i].contains(alert.toString())){
//			System.err.println("FOUND ALERT ["+i+"] ["+listOfAlerts[i]+"]")
//			r = listOfAlerts[i]
//			break
//		}
//	}
//	return  r
//}


//System.out.println("PJ "+pessoaJuridica("02329276000143"))
//
//
//public static boolean pessoaJuridica(String d){
//boolean pj = false
//	if(d.length()==14){
//		pj = true
//	}
//	return pj
//}


//
//validaDiaPagamento("0","20210401","")
//
//
//public static String validaDiaPagamento(String valorRcuperado, String FINAL_VIG, String xpath){
//	System.out.println("valorRcuperado ["+valorRcuperado+"] FINAL_VIG["+FINAL_VIG+"]")
//	String dia
//	int valor = Integer.parseInt(valorRcuperado);
//	
//	if(valor==0){
//	   dia = FINAL_VIG.substring(7, 8);
//	//   preencheXpath(xpath, dia, 1);
//	   System.out.println("Dia ["+dia+"]")
//	}
//	return dia
//}




//String premioTotal = "1483,40"
//String premioRecuperado = "R\$ 1.087,69"
//String limite = "10"


//calculoDeLimite(premioRecuperado, premioTotal, limite)
//
//
//public static boolean calculoDeLimite(String premioRecuperado, String premioTotal, String limite){
//	String  sAlerta
//	String limitePremio
//	boolean limit = false
//	premioRecuperado = premioRecuperado.replace("R\$","")
//	premioRecuperado = premioRecuperado.replace(".","")
//	premioRecuperado = premioRecuperado.replace(",",".").trim()
//	premioTotal = premioTotal.replace(".","")
//	premioTotal = premioTotal.replace(",",".")
//	BigDecimal bigLimite = new BigDecimal(limite.toString());
//	if(bigLimite>0){
//	   BigDecimal bigPremioRecuperado = new BigDecimal(premioRecuperado.toString());
//	   BigDecimal bigPremioTotal = new BigDecimal(premioTotal.toString());
//	   BigDecimal result = new BigDecimal(bigPremioTotal.subtract(bigPremioRecuperado))
//				  result = result.abs()
//	   BigDecimal resultCalculado = result.setScale(2, BigDecimal.ROUND_UP);
//				  String strngic = resultCalculado.toPlainString()
//				  strngic = strngic.replace(".", ",")
//	   if(result > bigLimite){
//		  limit = true
//		  sAlerta = 'Diferença do prêmio maior que o permitido. Premio Recuperado [R\$ '+bigPremioRecuperado+"] Premio total [ R\$ "+bigPremioTotal+"] Result [R\$ "+strngic+"] Limite [R\$ "+limite+",00]"
//	   } else {
//		  limitePremio = 'Diferença permitida. Premio Recuperado [R\$ '+bigPremioRecuperado+"] Premio total [ R\$ "+bigPremioTotal+"] Result [R\$ "+strngic+"] Limite [R\$ "+limite+",00]"
//	   }
//	   
//	}
//	System.err.println(sAlerta+"\n"+limitePremio)
//	return limit
//}

//WebUI.openBrowser('');





//PRESENCE 1.6 16v(Flex) 4p

//remveEspacos("PRESENCE 1.6 16v(Flex)  4p")
//
//public static String remveEspacos(String valor){
//	String [] nValor = valor.split()
//	StringBuilder strbr = new StringBuilder()
//	for(int i = 0; i < nValor.size(); i++){
//	//	strbr.append(nValor[i].trim())
//		System.err.println("["+i+"] ["+nValor[i].trim()+"]")
//	}
//	System.err.println("\n"+strbr.toString())
//	return strbr.toString()
//}


//System.err.println("Result "+alertListBloc(" Restrição técnica para o condutor"))
//
//
//public static String alertListBloc(String alert){
//	System.err.println("motodo alertListBloc ["+alert+"]")
//	Map<String, String> alertList = new HashMap<String, String>()
//	alertList.put("Erro on epac2020 on QA mode", "Erro on epac2020 on QA mode")
//	alertList.put("Apólice a renovar já utilizada", "Apólice a renovar já utilizada")
//	alertList.put("Classe de bônus divergente", "Classe de bônus divergente")
//	alertList.put(" Dados de conta corrente não pode ser de um CNPJ como Pagador", " Dados de conta corrente não pode ser de um CNPJ como Pagador")
//	alertList.put("Nome não retornado no Serviço", "Nome não retornado no Serviço")
//	alertList.put(" Política se aplica ao estado errado"," Política se aplica ao estado errado")
//	alertList.put("Error: Dados da conta bancária inválidos","Error: Dados da conta bancária inválidos")
//	alertList.put(" Restrição técnica para o condutor"," Restrição técnica para o condutor")
//	return  alertList.get(alert).toString();
//}



//String premioTotal = "1083,40"
//String premioRecuperado = "R\$ 1.287,69"
//
//
//calculoICGlobal(premioRecuperado, premioTotal)
//
//
//public static String calculoICGlobal(String premioRecuperado, String premioTotal){
//	
//	premioRecuperado = premioRecuperado.replace("R\$","")
//	premioRecuperado = premioRecuperado.replace(".","")
//	premioRecuperado = premioRecuperado.replace(",",".").trim()
//	premioTotal = premioTotal.replace(".","")
//	premioTotal = premioTotal.replace(",",".")
//	BigDecimal bigPremioRecuperado = new BigDecimal(premioRecuperado.toString());
//	BigDecimal bigPremioTotal = new BigDecimal(premioTotal.toString());
//	BigDecimal ic = new BigDecimal(bigPremioTotal*100/bigPremioRecuperado)
//	BigDecimal icArredondado = ic.setScale(2, BigDecimal.ROUND_UP);
//	String strngic = icArredondado.toPlainString()
//	strngic = strngic.replace(".", ",") 
//	System.err.println("Premio Recuperado ["+bigPremioRecuperado+"] Premio total ["+bigPremioTotal+"] IC Global ["+strngic+"]")
//	
//	return strngic 
//}








//System.err.println(alertListBloc(" Política se aplica ao estado errado"))
//
//
//public static String alertListBloc(String alert){
//	Map<String, String> alertList = new HashMap<String, String>()
//	alertList.put("Erro on epac2020 on QA mode", "Erro on epac2020 on QA mode")
//	alertList.put("Apólice a renovar já utilizada", "Apólice a renovar já utilizada")
//	alertList.put("Classe de bônus divergente", "Classe de bônus divergente")
//	alertList.put(" Dados de conta corrente não pode ser de um CNPJ como Pagador", " Dados de conta corrente não pode ser de um CNPJ como Pagador")
//	alertList.put("Nome não retornado no Serviço", "Nome não retornado no Serviço")
//	alertList.put(" Política se aplica ao estado errado"," Política se aplica ao estado errado")
//	return  alertList.get(alert).toString();
//}



//
//String aba = "RenovaSantanderSASAM"
//String path = 'C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Auto\\RenovacoesSantanderData\\RenovaSantanderSasam.xlsx'
//int i = 0
//auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'',2,aba);
//auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'x',3,aba);
//auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'teste sPacote',72,aba);
//auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'teste iPrLiqAtlAnt',73,aba);
//auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'teste String.valueOf(dPrliqAtuCalc)',74,aba);
//auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'teste sProposta',75,aba);
//auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'teste sApoliceRenov',76,aba);
//auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'teste sAlerta',77,aba);
//auxiliares.WriteExcelFactory.updateSheetLine('',i, path,'teste sLog',78,aba);


//String str="ACEITAÇÃO CONDICIONADA Á CONFIRMAÇÃO NA CBE."+
//
//           "Favor indexar na ficha de gestão cópia completa da apólice anterior 2018/2019 (Não será aceito a CI, uma vez em que a CBE não retorna os dados)."+
//           "PROPOSTA SANTANDER Nº                                                                                                                                               TELEFONES CONTATO SEGURADO (a)"
//
//
//
//StringFormatter.emailBodyFormatter(str)



//String pat = "Data de Emissão:"
//String txt = "Vigência das 24h de 24/08/2020 às 24h de 12/09/2020 Data de Emissão: 16/03/2020 Dados do Segurado".replace("24h","")
//
//System.err.println("INDEX ["+search(pat,txt) +"]")
//
//public static int search(String pat, String txt) {
//	int j, M = pat.length();
//	int i, N = txt.length();
// 
//	for (i = 0; i <= N - M; i++) {
//	   for (j = 0; j < M; j++)
//		  if (txt.charAt(i+j) != pat.charAt(j))
//			 break;
//	   if (j == M) return i;
//	}
//	return N;
// }
//
//
//ExtractDates.getSubDates(txt)



//String substr = txt.substring(0, 60)
//System.err.println("SUBSTRING ["+substr +"]")


//String INPUT="Prezado Corretor,"

//String INPUT="                         "

//String REGEX = "[a-zA-Z]";


//System.out.println("Match String start(): ["+auxiliares.RegexCharacter.regexChar(REGEX, INPUT)+"]")




//WebUI.openBrowser('');

//List<String> anexos = new ArrayList<>();
//anexos[0] = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Saude\\RoboCriticasSIB.xlsx";
//anexos[1] = "RoboCriticasSIB.xlsx";
//sendEmail.CreateEmail.createEmailSendMultipleFiles("denilson.inmetricsext@allpronet.com.br", "denilson.inmetricsext@allpronet.com.br", "be549556", anexos, "smtp.intrallianz.es", "25", "CriticasSIB" )


//auxiliares.WriteExcelFactory.updateSheetLine("TESTE 2", 100,"C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Comercial\\inclusaosusep\\InclusaoSUSEP.xlsx","TESTE" , 0, "recadastramentoSUSEP");


//String str = "ALVARO DENARDI | 201040729 | ***.709.978-** | Sim | Ativo | Microsseguros, Planos de Capitalização, Planos de Previdência Complementar, Seguros de Danos, Seguros de Pessoas"



//String t = "de 1.529 páginas"
//System.err.println(t.replaceAll("[.a-zá]",""))


//auxiliares.WriteExcelFactory.updateSheetLine("TESTEEEEEE 1", 1,"C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\CadastroCorretorFilialFisica\\CadastroCorretor.xlsx", 'TESTEEEEEEEEEEEEE 5', 13, 'CadastroCorretorFilialFisica');
//auxiliares.WriteExcelFactory.updateSheetLine("TESTEEEEEE 1", 1,"C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\InclusaoVCSantander\\InclusaoVCSantander.xlsx", 'TESTEEEEEEEEEEEEE 5', 5, 'InclusaoVCSantander');

//String p = "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\FielDepositarioData\\EmissaoFielDepositario.xlsx"

//auxiliares.WriteExcelFactory.updateSheetLine("", 1,p , "TESTE A", 12, "FIEL");
//auxiliares.WriteExcelFactory.updateSheetLine("", 1,p , "TESTE b", 12, "FIEL");

//auxiliares.WriteExcelFactory.updateSheetLine("", 1,"C:\\Users\\Public\\RPA_ALLIANZ\\MassaAuto\\RoboRenovAuto.xlsx", "TESTE 2", 7, 'InspecaoRisco');

//List<String> coberturas = new ArrayList<String>()
//StringBuilder strb = new StringBuilder() 
//
//String t = "6762 IEA051-INDENIZAÇÃO ESPECIAL POR ACI	DENTE, 6770 MQC001-MORTE QUALQUER CAUSA"+","
//
//String n = t.replace("[Ａ-Ｚａ-ｚ]","").trim()
//
//String [] token = n.split(" ")
//
//for(int i = 0; i < token.size(); i++){
//	strb.append(" "+token[i])
//	System.err.println(strb)
//	if(token[i].contains(",")){
//		coberturas.add(strb)
//		strb = new StringBuilder()
//	} 
//}
//
//
//for(int j = 0;j < coberturas.size(); j++){
//	System.err.println(j+" "+coberturas.get(j).replaceAll("[,]",""))
//}

//int valor = 1441593
//
//BigDecimal payment = new BigDecimal(valor).movePointLeft(2);
//
//String valueOfBigDec=String.valueOf(payment);
//
//valueOfBigDec = valueOfBigDec.replace(".",",").trim()
//System.err.println("payment "+valueOfBigDec)


//WebUI.openBrowser('');

//String t = "R\$ 28.831,86 "

//System.err.println(t.replaceAll("[R\$.]",""))


//auxiliares.WriteExcelFactory.updateSheetLine('', 1, "C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Sinistro\\RoboCosseguroFHE.xlsx", 'TESTEXXXXXXX', 20 , 'AberturaSinistroCoFHE');


//String texto = "Carta Inadimplência - Aviso"
//String textoBusca = "25/03/2020		9292		Allianz Aviso Inadimplência - 51772019532100	> DLGPDF  > 00/00/0000 	N"
//System.err.println(textoBusca.contains(texto) )


//rpaCommands.deleteFileDiretory("C:/Users/Public/TESTE_DELL.zip")

//backoffice.FluxoEnvioPDF.enviarDocumentoPorEmail('EnvioPDF', 1, "denilson.inmetricsext@allpronet.com.br", "denilson.inmetricsext@allpronet.com.br, andre.antunes@allianz.com.br", 'RESPONSABILIDADE CIVIL PROFISSIONAL AGCS')

//rpaCommands.zipDirectory('C:/Users/Public/RESPONSABILIDADE CIVIL PROFISSIONAL AGCS', 'C:/Users/Public/RESPONSABILIDADE CIVIL PROFISSIONAL AGCS.zip')


//auxiliares.WriteExcelFactory.updateSheetLine("", 0,"C:\\Users\\Public\\RPA_ALLIANZ\\MassaAuto\\RoboEnvioPdf.xlsx", "ERRO", 13, 'EnvioPDF');


//backoffice.FluxoEnvioPDF.enviarDocumentoPorEmail('Denilson')

//
//aude.CriticasSIB.copiaEvidencia()


//String dataAtual = auxiliares.ExtractDates.dataAtual()
//System.err.println('Data Atual: '+dataAtual)
//
//
//String dataToFormatte = auxiliares.ExtractDates.formatDateToBR('20191215')
//System.err.println('Data to Formatte: '+dataToFormatte)
//
//System.err.println('Dates Equals ['+auxiliares.ExtractDates.compareDate(dataAtual, dataToFormatte)+']')


//auto.RenovaSantanderFuncionalidades.validaDataParaPorcessar('18/12/2019')

//System.err.println(System.getProperties())

//rpaCommands.descompactaArquivo('C:\\Users\\Public\\extracao_operacoes_321644.zip', 'C:\\Users\\Public')

 //System.err.println( agcs.CotacoesEspeciais.ramoAGCS('0337') )


// 11/02/2020 - 20200211
//boolean value = auxiliares.ExtractDates.compareDate('11/02/2020', '20200211')
//System.err.println( value )


//rpaCommands.deletarRetrievedoc()



//backoffice.SeguroFuncionarioRenovAuto.enviarPdfSeguroRenovAuto('SeguroRenovAuto', 'osvaldo.advg@gmail.com', '57500398387');
















//try {
//    //teste();
//    descompacta('C:\\Users\\Public\\extracao_operacoes_280059.zip')
//    copiarExcel('C:\\Users\\Public\\001.txt', "C:\\Users\\Public\\");
//	deletaArquivoGenerico('C:\\Users\\Public\\001.txt');
//}
//catch (Exception e) {
//    e.printStackTrace()
//} 
////finalizar()
///**
// * @author T_DCDias
// * @param path
// * @return
// */
/**
 * @author ALZ_ServiceQA
 * @param caminhoFonte
 * @param caminhoSaida
 * @return
 */
/**
 * @author Dvlopes
 * @param arquivoEntrada
 * @param caminhoSaida
 * @return	 *
 * Descompacta arquivo recebendo dois parametros [ENTRADA / SAIDA]	 *
 * Entrada [C:\\Users\\ALZ_ServiceQA\\Desktop\\teste.zip]
 * Saida [C:\\Users\\ALZ_ServiceQA\\Desktop]
 *
 */

//public static void descompacta(String caminho) {
//    if (!(validaExistenciaDeArquivo(caminho))) {
//        descompactaArquivo('C:\\Users\\Public\\extracao_operacoes_280059.zip', 'C:\\Users\\Public\\001.txt')
//    } else {
//    }
//}
//
//public static boolean validaExistenciaDeArquivo(String path) {
//    boolean resp
//
//    File arquivo = new File(path)
//
//    if (arquivo.exists()) {
//        System.err.println('O ARQUIVO EXISTE')
//
//        resp = false
//    } else {
//        System.err.println('O ARQUIVO NÃO EXISTE')
//
//        resp = true
//    }
//    
//    return resp
//}
//
//
//public static String copiarExcel(String caminhoFonte, String caminhoSaida){
//	FileChannel canalFont = null;
//	FileChannel canalDest = null;
//	try {
//		canalFont = new FileInputStream(caminhoFonte).getChannel();
//		canalDest = new FileOutputStream(caminhoSaida).getChannel();
//		canalFont.transferTo(0, canalFont.size(), canalDest);
//	} finally {
//		if (canalFont != null && canalFont.isOpen()){
//			canalFont.close();
//		}
//		if (canalDest != null && canalDest.isOpen()){
//			canalDest.close();
//		}
//	}
//	println caminhoSaida
//	return caminhoSaida;
//}
//public static String copiarExcel(String caminhoFonte, String caminhoSaida) {
//    FileChannel canalFont = null
//
//    FileChannel canalDest = null
//
//    try {
//        canalFont = new FileInputStream(caminhoFonte).getChannel()
//
//        canalDest = new FileOutputStream(caminhoSaida).getChannel()
//
//        canalFont.transferTo(0, canalFont.size(), canalDest)
//    }
//    finally { 
//        if ((canalFont != null) && canalFont.isOpen()) {
//            canalFont.close()
//        }
//        
//        if ((canalDest != null) && canalDest.isOpen()) {
//            canalDest.close()
//        }
//    }
//    
//    println(caminhoSaida)
//
//    return caminhoSaida
//}

//public static void descompactaArquivo(String arquivoEntrada, String caminhoSaida) {
//    try {
//        String cmd = ((('PowerShell Expand-Archive -Path ' + arquivoEntrada) + ' -DestinationPath ') + caminhoSaida) + ''
//
//        System.err.println(((('PowerShell Expand-Archive -Path ' + arquivoEntrada) + ' -DestinationPath ') + caminhoSaida) + '')
//
//        Runtime.getRuntime().exec(cmd)
//    }
//    catch (def e) {
//        System.err.println(((('Não foi possivel descompactar arquivo [' + arquivoEntrada) + '] Para o caminho [') + caminhoSaida) + ']')
//    } 
//}
//
//
//public static deletaArquivoGenerico(String path){
//	try{		
//		System.err.println("pathValue ["+path+"]");
//		boolean result = validaExistenciaDeArquivo(path);
//		
//		if( result == false){
//			deleteFileDiretory(path);
//		}		
//	} catch (Exception e){
//	}
//}
//
///**
// * @author T_DCDias
// * @param pathValue
// * @return
// */
//public static boolean deleteFileDiretory(String pathValue ){
//	try{
//		boolean success = (new File(pathValue.toString())).delete();
//		if (!success) {
//			System.err.println("[ Arquivo ["+pathValue+"] não excluido ! ]")
//		} else{
//			System.err.println("[ Arquivo ["+pathValue+"] excluido ! ]")
//		}
//
//		while(validaExistenciaDeArquivo(pathValue)== false){
//			deleteFileDiretory(pathValue)
//		}
//	} catch (Exception e){
//		System.err.println("Não foi possível excluir o arquivo ["+pathValue+"]\n"+e )
//
//	}
//}

/*public static descompactaArquivo(String arquivoEntrada){	
	String cmd = 'PowerShell Expand-Archive -Path '+arquivoEntrada+' -DestinationPath "C:\\Users\\ALZ_ServiceQA\\Desktop';
	System.err.println('PowerShell Expand-Archive -Path '+arquivoEntrada+' -DestinationPath "C:\\Users\\ALZ_ServiceQA\\Desktop"')
	Runtime.getRuntime().exec(cmd);
}*/ //public static teste(String arquivoEntrada){
//	String [] args = arquivoEntrada
//InputStream entrada = new BufferedInputStream(new FileInputStream(args[0]));
//
//	ZipInputStream zin = new ZipInputStream(entrada);
//
//	ZipEntry e;
//
// 
//
//	while((e=zin.getNextEntry())!= null) {
//
//	  if (args.length > 1) {
//
//		if (e.getName().equals(args[1])) {
//
//		   unzip(zin, args[1]);
//
//		   break;
//
//		  }
//
//		}
//
//	   unzip(zin, e.getName());
//
//	  }
//
//	zin.close();
//
//	}
//
//   
//
//  public static void unzip(ZipInputStream zin, String s) throws IOException {
//
//	System.out.println("unzipping " + s);
//
//	FileOutputStream out = new FileOutputStream(s);
//
//	byte [] b = new byte[512];
//
//	int len = 0;
//
//	while ( (len=zin.read(b))!= -1 ) {
//
//	  out.write(b,0,len);
//
//	  }
//
//	out.close();
//
//	}
//public static teste(String arquivo) {
//    FileChannel canalDest = null
//
//    String caminho
//
//    //FileInputStream fis = new zip.getInputStream('C:\\Users\\ALZ_ServiceQA\\Desktop\\extracao_operacoes_282066.zip')
//	FileInputStream fis = new FileInputStream(arquivo)
//	
//
//    ZipInputStream zis = new ZipInputStream(fis)
//	ZipOutputStream saida = new ZipOutputStream(zis)
//
//    ZipEntry entrada = null
//	byte[] buffer = new byte[512]
//
//    while ((entrada = zis.getNextEntry()) != null) {
//        System.out.println(entrada.getName())
//    }
//    
//    canalDest = new FileOutputStream(saida)
//
//    int bytesLidos = 0
//
//    if (fis == null) {
//       // throw new ZipException('Erro ao ler a entrada do zip: ' + entrada.getName())
//    }
//    
//    while ((bytesLidos = fis.read(buffer)) > 0) {
//        canalDest.write(buffer, 0, bytesLidos)
//    }
//    
//    zis.close()
//
//    fis.close()
//}
//public static copiarExcel(String caminhoFonte, String caminhoSaida){
//	FileChannel canalFont = null;
//	FileChannel canalDest = null;
//	try {
//		canalFont = new FileInputStream(caminhoFonte).getChannel();
//		canalDest = new FileOutputStream(caminhoSaida).getChannel();
//		canalFont.transferTo(0, canalFont.size(), canalDest);
//	} finally {
//		if (canalFont != null && canalFont.isOpen()){
//			canalFont.close();
//		}
//		if (canalDest != null && canalDest.isOpen()){
//			canalDest.close();
//		}
//	}
//	println caminhoSaida
//	//return caminhoSaida;
//}
//cria diretório informado, caso não exista
//se for diretório inexistente, cria a estrutura
//e pula pra próxima entrada
//se a estrutura de diretórios não existe, cria
//lê o arquivo do zip e grava em disco
//public static extrairZip(File arquivoZip, File diretorio) {
//    ZipFile zip = null
//
//    File arquivo = null
//
//    InputStream is = null
//
//    OutputStream os = null
//
//    byte[] buffer = new byte[512]
//
//    try {
//        if (!(diretorio.exists())) {
//            diretorio.mkdirs()
//        }
//        
//        if (!(diretorio.exists()) || !(diretorio.isDirectory())) {
//            throw new IOException('Informe um diretório válido')
//        }
//        
//        zip = new ZipFile(arquivoZip)
//
//        Enumeration e = zip.entries()
//
//        while (e.hasMoreElements()) {
//            ZipEntry entrada = ((e.nextElement()) as ZipEntry)
//
//            arquivo = new File(diretorio, entrada.getName())
//
//            if (entrada.isDirectory() && !(arquivo.exists())) {
//                arquivo.mkdirs()
//
//                continue
//            }
//            
//            if (!(arquivo.getParentFile().exists())) {
//                arquivo.getParentFile().mkdirs()
//            }
//            
//            try {
//                is = zip.getInputStream(entrada)
//
//                os = new FileOutputStream(arquivo)
//
//                int bytesLidos = 0
//
//                if (is == null) {
//                    throw new ZipException('Erro ao ler a entrada do zip: ' + entrada.getName())
//                }
//                
//                while ((bytesLidos = is.read(buffer)) > 0) {
//                    os.write(buffer, 0, bytesLidos)
//                }
//            }
//            finally { 
//                if (is != null) {
//                    try {
//                        is.close()
//                    }
//                    catch (Exception ex) {
//                    } 
//                }
//                
//                if (os != null) {
//                    try {
//                        os.close()
//                    }
//                    catch (Exception ex) {
//                    } 
//                }
//            }
//        }
//    }
//    finally { 
//        if (zip != null) {
//            try {
//                zip.close()
//            }
//            catch (Exception e) {
//            } 
//        }
//    }
//}


//auxiliares.WriteExcelFactory.updateSheetLine('',0, 'C:\\Users\\Public\\RPA_ALLIANZ\\Data Files\\Backoffce\\FielDepositarioData\\EmissaoFielDepositario.xlsx',"TT",12,'FIEL');

//rpaCommands.unzip('C:\\Users\\Public\\extracao_operacoes_321644.zip', 'C:\\Users\\Public')
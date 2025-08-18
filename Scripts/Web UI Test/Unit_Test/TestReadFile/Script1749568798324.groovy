

import java.util.List

import auxiliares.leitorPdf;
import auxiliares.StringFormatter;

ArrayList<ArrayList<String>> cotacoes = new ArrayList<ArrayList<String>>();

cotacoes.add(leitorPdf.pdfReaderNPagesList("C:\\Users\\Public\\41f09642-dd57-463f-95c3-7fd5c4cde86f@PDF da cotação (1).pdf"));


//cotacoes.add(leitorPdf.pdfReaderNPages("C:\\Users\\Public\\e5a2e3a5-2491-4d76-9618-371173528def@PDF da cotação (1).pdf"));


for(int i = 0; i<cotacoes.size();i++ ){
	int l = i+1;
	System.out.println("\nList: "+l+" **************************************************************************\n")
	
	for(int j = 0; j<cotacoes.get(i).size();j++){
			   System.out.println("["+j+"] - ["+cotacoes[i][j].toString()+"]");
	}
}


StringFormatter.indexGroupLine(leitorPdf.pdfReaderNPagesList("C:\\Users\\Public\\41f09642-dd57-463f-95c3-7fd5c4cde86f@PDF da cotação (1).pdf"), "Parcelas Juros","Juros")




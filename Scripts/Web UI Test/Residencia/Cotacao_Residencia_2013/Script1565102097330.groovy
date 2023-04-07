import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

try {
	 residencia.CotacaoResidencia.executaCotacaoResidencia()
    }
catch (Exception e) {
	 System.err.println("Falha ao realizar Cotação Residencia\n"+e);
	 WebUI.closeBrowser();
    }

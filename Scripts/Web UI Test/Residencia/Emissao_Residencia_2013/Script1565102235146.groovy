import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

try {
	residencia.EmissaoResidencia.emissao()
 }
 catch (Exception e) {
	System.err.println("Falha ao realizar Emissao Residencia\n"+e);
	WebUI.closeBrowser();
 }

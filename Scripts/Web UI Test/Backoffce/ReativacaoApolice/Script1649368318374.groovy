import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

try {
	
	backoffice.ReativacaoApoliceFuncionalidades.fluxoReativacaoApolice('ReativacaoApolice','PRD')
	WebUI.closeBrowser();
 }
   catch (Exception e) {
   System.err.println("Falha ao executar fluxo Reativação da Apolice"+ e);
   WebUI.closeBrowser();
}
   
   


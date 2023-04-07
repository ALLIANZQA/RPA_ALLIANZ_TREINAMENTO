import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

try {
	/**
	 * 
	 * 
	 * Desenvolvimento: 12/01/2022
	 *  * 
	 * Para execuções em ambientes de integração usar: 
	 * INT
	 * 
	 * Para execuções em ambientes de produção usar: 
	 * PRD
	 * 
	 *
	 */
	migracaoDeDados.MigracaoDeDadosFuncionalidades.fluxoMigracaoDeDadosFuncionalidades('pozoResidencia','2026','Endosso','PRD')
	WebUI.closeBrowser();
 }
   catch (Exception e) {
   System.err.println("Falha ao executar fluxo Migracao De Dados - Residencia 2026"+ e);
   WebUI.closeBrowser();
}
   
   


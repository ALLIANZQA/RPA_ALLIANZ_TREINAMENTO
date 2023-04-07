import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

try {
	/**
	 * BDM-5142 
	 *
	 * Desenvolvimento: 27/04/2022
	 *  *
	 * Para execuções em ambientes de integração usar:
	 * INT
	 *
	 * Para execuções em ambientes de produção usar:
	 * PRD
	 *
	 *
	 */
	residencia.CotaResidFuncionalidades.fluxoCotaResidFuncionalidades('Cota_resid','Emissão','PRD')
	WebUI.closeBrowser();
 }
   catch (Exception e) {
   System.err.println("Falha ao executar fluxo Migracao De Dados - Residencia 2026"+ e);
   WebUI.closeBrowser();
}
   
   


import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

try {
	/**
	 * BDM-4401
	 * Desenvolvimento: 04/11/2021
	 * 
	 * Robô de cálculo para emissões do Imobiliária Residência e Imobiliária Empresa
	 * 
	 * Para execuções em ambientes de integração usar: 
	 * INT
	 * 
	 * Para execuções em ambientes de produção usar: 
	 * PRD
	 * 
	 */
	imobiliaria.ImobiliariaFuncionalidades.fluxoImobiliaria('imobiliariaEmpresa','Outras Operações', 'INT')
	WebUI.closeBrowser();
 }
   catch (Exception e) {
   System.err.println("Falha ao executar fluxo Imobiliaria Empresa"+ e);
   WebUI.closeBrowser();
}
   
   


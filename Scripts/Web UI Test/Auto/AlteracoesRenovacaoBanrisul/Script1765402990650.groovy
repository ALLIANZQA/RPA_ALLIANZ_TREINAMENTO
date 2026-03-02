import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import rpacommands.rpaCommands

try {
	/**
	 * Demanda IBLBDM-77786
	 * RPA -  Comunicação e de Renovação Automática Auto Renovação 
	 * Desenvolvimento: 09/10/2025
	 *
	 */
	auto.BanrisulFuncionalidades.fluxoBanrisul('EnvioBanrisul',"PRD");
	WebUI.closeBrowser();
 }
   catch (Exception e) {
   System.err.println("Falha ao executar fluxo de Gestao Reclamacao"+ e);
   auto.BanrisulFuncionalidades.enviarEmailError("Error", 'Banrisul' );
   WebUI.closeBrowser();
}




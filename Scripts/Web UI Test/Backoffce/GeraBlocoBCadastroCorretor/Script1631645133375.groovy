import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

try {
	/**
	 * BDM-4038 - GERA BLOCO B NO PROCESSO DE CADASTRO DE CORRETOR
	 * Desenvolvimento: 14/09/2021
	 *
	 */
	backoffice.GeraBlocoBCadastroCorretorFuncionalidades.fluxoGeraBlocoBCadastroCorretor('GeraBlocoBCadastroCorretor');
	WebUI.closeBrowser();
 }
   catch (Exception e) {
   backoffice.GeraBlocoBCadastroCorretorFuncionalidades.enviarEmailError("Error", 'GeraBlocoBCadastroCorretor' );
   System.err.println("Falha ao executar fluxo Gera Bloco B Cadastro Corretor"+ e);
   WebUI.closeBrowser();
}
   
   


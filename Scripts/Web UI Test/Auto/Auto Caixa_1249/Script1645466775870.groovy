import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

try {
	/**
	 * BBO-6615
	 * BDM-4362 Migração de Dados - Req. 3A - Migrar a base de apólices plurianuais
	 * Desenvolvimento: 04/11/2021
	 *
	 * Para execuções em ambientes de integração usar:
	 * INT
	 *
	 * Para execuções em ambientes de produção usar:
	 * PRD
	 ** Falta definir a origem dos dados para preenchimento da tela cosseguro aceito
	 */
	migracaoDeDados.MigracaoDeDadosFuncionalidades.fluxoMigracaoDeDadosFuncionalidades('pozoAutoCaixa','1249','Emissão', 'PRD')
	WebUI.closeBrowser();
 }
   catch (Exception e) {
   System.err.println("Falha ao executar fluxo Migracao De Dados"+ e);
   WebUI.closeBrowser();
}
   
   


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
	 * 
	 */
	migracaoDeDados.MigracaoDeDadosFuncionalidades.fluxoMigracaoDeDadosFuncionalidades('pozoAutoAnual','1240','Emissão', 'INT')
	WebUI.closeBrowser();
 }
   catch (Exception e) {
   System.err.println("Falha ao executar fluxo Migracao De Dados"+ e);
   WebUI.closeBrowser();
}
   
   


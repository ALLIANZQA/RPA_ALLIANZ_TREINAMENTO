import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


try {
	
	  /**
	   * [Cobrança] - Comunicação de Bloqueio 
	   * BDM-4681
	   * 09/08/2022
	   * INT - Integração
	   * PRD - Produção 
	   * @author be54955
	   * 
	   */
	  backoffice.ComunicacaoBloqueioFuncionalidades.fluxoComunicacaoBloqueio('ComunicacaoBloqueio','PRD');
	  WebUI.closeBrowser();
	  }
	   catch (Exception e) {
		 System.err.println("Falha ao realizar Comunicacao de Bloqueio" +e);
		 WebUI.closeBrowser();
	  }


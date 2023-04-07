

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI



try {
	//BDM-2103
	
	//manualmente 10 dias
	saude.SegundaViaCarterinhaFuncionalidades.fluxoSegundaViaCarterinha('SegundaVia');
	}
	 catch (Exception e) {
	   System.err.println("Falha ao realizar Segunda via Carteirinha" +e);
	   WebUI.closeBrowser();
	}
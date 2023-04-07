import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import saude.CriticasSIB

try {
	//BDM-2342
	
	saude.Endosso.fluxoEndosso('Endosso');
	}
	 catch (Exception e) {
	   System.err.println("Falha ao realizar Agendamento Inspeções" +e);
	   WebUI.closeBrowser();
	}

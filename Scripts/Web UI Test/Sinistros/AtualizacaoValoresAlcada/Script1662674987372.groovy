
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

try {
	/**
	 * @author be54955
	 * BDM-4941
	 * Atualização de Valores de Alçada
	 *
	 * Data : 07/09/2022
	 *
	 * INT
	 *
	 * Para execuções em ambientes de produção usar:
	 * PRD
	 */
    sinistros.AtualizacaoValoresAlcadaFuncionalidades.fluxoAtualizacaoValoresAlcada('AtualizacaoValoresAlcada', 'PRD')
    WebUI.closeBrowser();
  } catch (Exception e) {
	System.err.println("Falha ao Realizar Contact Center"+e);
	WebUI.closeBrowser();
 }
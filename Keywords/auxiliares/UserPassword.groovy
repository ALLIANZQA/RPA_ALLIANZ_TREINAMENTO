package auxiliares

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import backoffice.backofficeFuncionalidades
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

//Atualização 27/04/26
//public class UserPassword extends backofficeFuncionalidades{
public class UserPassword{

	private static user;
	private static senha;
	private static dominio;
	private static email;

	public static String pathpropertiesUser = "C:\\Users\\Public\\RPA_ALLIANZ\\ConfiUserPwr\\user.properties";
	public static String pathpropertiesPwd = "C:\\Users\\Public\\RPA_ALLIANZ\\ConfiUserPwr\\pwd.properties";

	/**Recebe por parametro nome do RPA e imputa valores de usuario e senha equivalente;
	 * @param nomeRpa
	 * @return
	 * Atualização : 30/10/2023 ás 10hs46
	 */
	public static getUsuarioSenha(String nomeRpa){



		switch (nomeRpa) {
			case 'Agendamento':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'envioPendenciaGOInspecao':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'envioPendenciaGo':
				user = returnUser(1)
				senha = returnPwrd(1)
			case 'Bloqueios':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'EnvioPDF':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'CotacoesEspeciais':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'SeguroRenovResidencia':
				user = returnUser(2)
				senha = returnPwrd(2)
				break;
			case 'SeguroRenovAuto':
				user = returnUser(2)
				senha = returnPwrd(2)
				break;
			case 'userSenhaBloqueioFiel':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'apoliceTransportador':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'InspecaoRisco':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'CadastroCorretor':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'DesbloqueioProposta':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'SegundaVia':
				user = returnUser(6)
				senha = returnPwrd(6)
				break;
			case 'CriticasSIBGestaoCliente':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'Endosso':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'RecusaDeFrota':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'RenovaSantanderPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'RenovaSantanderINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'RenovaSantanderDEV':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;

			case 'SinistroDash':
				user = returnUser(7)
				senha = returnPwrd(7)
				break;
			case 'SinistroErros':
				user = returnUser(7)
				senha = returnPwrd(7)
				break;
			case 'AtualizaCustoApolicesVigentes':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ByPass':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ReprocessamentoEmissaoApolice':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'CriticasSIB':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'AberturaSinistroCoFHE':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'DescontosEspecíficosSinistro':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'InativacaoProcedimento':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'InativacaoMATMED':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'InclusaoVCSantander':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'DistribuicaoAutomaticaIR':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'BoletoAvulso':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'PendenciaEmailSantander':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'infraestrutura':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'GeraBoleto':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ValidaQuadroPDF':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'RenovaSantanderSASAM':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'RenovaSantanderSASAM2':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ReposicaoValores':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'RenovaEmissaoIncorreta':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'DesvinculaCodigoCorretor':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'RenovacaoAutoFrota':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'GeraBlocoBCadastroCorretor':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'SubscricaoMassificados':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'pozoAutoINT':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'pozoAutoPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'pozoAutoAnualINT':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'pozoAutoAnualPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'pozoCondominioINT':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'pozoAutoCaixaINT':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'pozoAutoCaixaPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'pozoCondominioPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'pozoEmpresaINT':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'pozoEmpresaPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'pozoResidenciaINT':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'pozoResidenciaPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ReabreReavaliaSinistro':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'distribuicaoPropostasGO':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'RecusaPropostaINT':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'RecusaPropostaPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ReativacaoApolicePRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'AcessoAllianznetProducao':
				user = returnUser(9)
				senha = returnPwrd(9)
				break;
			case 'Cota_residPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'Cota_residINT':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'imobiliariaResidenciaPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'imobiliariaResidenciaINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'imobiliariaEmpresaPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'imobiliariaEmpresaINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ContactCenterINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ContactCenterPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ComunicacaoBloqueioPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ComunicacaoBloqueioINT':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'AtualizacaoValoresAlcadaPRD':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'AtualizacaoValoresAlcadaINT':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'RecusaPropostaAutoPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'RecusaPropostaAutoINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ApolicesHomologacaoPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ApolicesHomologacaoINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ReajusteApoliceSaudePRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ReajusteApoliceSaudeINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'BaseCancelamentoPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'BaseCancelamentoINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'DescredenciamentoDePrestadorPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'DescredenciamentoDePrestadorINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'DescredenciamentoSinistroPRD':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'DescredenciamentoSinistroINT':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'OficinasPRD':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'OficinasINT':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'OficinasNaoReferenciadasPRD':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'OficinasNaoReferenciadasINT':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'EnvioVoucherCorretoresParceriasPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'EnvioVoucherCorretoresParceriasINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'CadastroDeRestricao':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'IndexacaoFGPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'IndexacaoFGINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'DescredenciamentoDePrestadorEfePRD':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'DescredenciamentoDePrestadorEfeINT':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'DescarteIncidenciaPRD':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'DescarteIncidenciaINT':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'RegularizarIncidenciasPRD':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'RegularizarIncidenciasINT':
				user = returnUser(1)
				senha = returnPwrd(1)
				break;
			case 'EditorCorrespondenciaPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'EditorCorrespondenciaINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'DuplicadoRecibosPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'DuplicadoRecibosINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ReentradaPropostaAutoPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ReentradaPropostaAutoINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'GestorOperacoes':
				dominio = returnDom(1);
				email = returnEML(1);
				senha = returnPwrd(11);
				break;
			case 'CotacaoRenovacaoInternaPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'CotacaoRenovacaoInternaINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;

			case 'GestaoReclamacaoPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'GestaoReclamacaoINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;

			case 'GestaoReclamacaoAutomaticoPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'GestaoReclamacaoAutomaticoINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;

			case 'GestaoVerificacoesAtribuicaoManPRD':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'GestaoVerificacoesAtribuicaoManINT':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;

			case 'EndossoClausulasPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'EndossoClausulasINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;

			case 'RessarcimentoPRD':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'RessarcimentoINT':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'ParceriasPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ParceriasINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'GestaoVerificacoesCancelamentoPRD':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'GestaoVerificacoesCancelamentoINT':
				user = returnUser(4)
				senha = returnPwrd(4)
				break;
			case 'CancelamentoApolicesSicoobPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'CancelamentoApolicesSicoobINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'DigitacaoSeguroNovoFrotaPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'DigitacaoSeguroNovoFrotaINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;

			case 'ConfereEmpenhoRamosPRD':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
			case 'ConfereEmpenhoRamosINT':
				user = returnUser(3)
				senha = returnPwrd(3)
				break;
		}
	}

	/**
	 * @author T_DCDias
	 * @param id
	 * @return
	 */
	public static returnUser(int id){
		String [] list = new String[11];
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(pathpropertiesUser);
		props.load(fis);
		list[1]=props.getProperty("1")
		list[2]=props.getProperty("2")
		list[3]=props.getProperty("3")
		list[4]=props.getProperty("4")
		list[5]=props.getProperty("5")
		list[6]=props.getProperty("6")
		list[7]=props.getProperty("7")
		list[8]=props.getProperty("8")
		list[9]=props.getProperty("9")
		list[10]=props.getProperty("10")

		return list[id].toString();
	}

	/**
	 * @author T_DCDias
	 * @param id
	 * @return
	 */
	public static returnPwrd(int id){
		String [] list = new String[11];
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(pathpropertiesPwd);
		props.load(fis);
		list[1]=props.getProperty("1")
		list[2]=props.getProperty("2")
		list[3]=props.getProperty("3")
		list[4]=props.getProperty("4")
		list[5]=props.getProperty("5")
		list[6]=props.getProperty("6")
		list[7]=props.getProperty("7")
		list[8]=props.getProperty("8")
		list[9]=props.getProperty("9")
		list[10]=props.getProperty("10")

		return list[id].toString();
	}




	public static java.lang.Object getUser() {
		return user;
	}

	public static void setUser(java.lang.Object user) {
		UserPassword.user = user;
	}

	public static java.lang.Object getSenha() {
		return senha;
	}

	public static void setSenha(java.lang.Object senha) {
		UserPassword.senha = senha;
	}

	public static java.lang.Object getDominio() {
		return dominio;
	}

	public static void setDominio(java.lang.Object dominio) {
		UserPassword.dominio = dominio;
	}

	public static java.lang.Object getEmail() {
		return email;
	}

	public static void setEmail(java.lang.Object email) {
		UserPassword.email = email;
	}



}




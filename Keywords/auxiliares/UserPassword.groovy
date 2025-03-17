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

//public class UserPassword extends backofficeFuncionalidades{
public class UserPassword{

	private static user;
	private static senha;

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




		}
	}

	/**
	 * @author T_DCDias
	 * @param id
	 * @return
	 */
	public static returnUser(int id){
		Map<Integer, String> user = new HashMap<Integer, String>()
		user.put(1, 'be53298')
		user.put(2, 'ba811500')
		user.put(3, 'BE53725')
		user.put(4, 'BE54097')
		user.put(5, 'be01624')
		user.put(6, 'be01637')
		user.put(7, 'RelatorioSinistro')
		user.put(8, 'BE01448')
		user.put(9, 'BA450306')
		user.put(10, 'Be00151')

		return user.get(id);
	}

	/**
	 * @author T_DCDias
	 * @param id
	 * @return
	 */
	public static returnPwrd(int id){
		Map<Integer, String> pwrd = new HashMap<Integer, String>()
		pwrd.put(1, 'Allz202502' )
		pwrd.put(2, 'Allz202306' )
		pwrd.put(3, 'Allz202504' )
		pwrd.put(4, 'Allz202413' )
		pwrd.put(5, 'Am@nda36' )
		pwrd.put(6, 'Mari2005' )
		pwrd.put(7, 'sinistro' )
		pwrd.put(8, 'Caren045' )
		pwrd.put(9, 'teste123' )
		pwrd.put(10,'Brsp202305')

		return pwrd.get(id);
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

}




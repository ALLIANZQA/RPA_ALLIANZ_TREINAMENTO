package sendEmail

import auxiliares.ExtractDates
import templateMessage.AgcsTemplateMessage
import templateMessage.AutoTemplateMessage
import templateMessage.BackOfficeTemplateMessage
import templateMessage.InfraAlteraPerfilCorretor
import templateMessage.InfraCadastroPerfilCorretor
import templateMessage.TransporteTemplateMessage
import templateMessage.SaudeTemplateMessage
import templateMessage.SinistroTemplateMessage
import templateMessage.GcfTemplateMessage
import templateMessage.FinanceiroTemplateMessage
import templateMessage.ComercialTemplateMessage
import templateMessage.MigracaoDeDadosTemplateMessage

public class MessageRule extends AutoTemplateMessage{

	/**
	 * @author T_DCDias
	 * @param ramo
	 * @return
	 */
	public static String messageRules(String[] valores){
		switch (valores[0]){

			case "Carta_AvisoNaoRenovacao_NR":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/cabecalho30dsantander.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/rodape30dsantander.png";
				message.setSubject("Aviso de Não Renovação Apólice Allianz/Santander - "+valores[2]);
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return AutoTemplateMessage.avisoNaoRenovacaoAllianzSantanderNR(valores[1],valores[2],rodape,cabecalho,valores[3],valores[4])
				break;

			case "Carta_AvisoRenovacao_15D":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/cabecalho30dsantander.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/rodape30dsantander.png";
				message.setSubject("Aviso de Renovação Apólice Allianz/Santander - "+valores[2]);
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return AutoTemplateMessage.avisoRenovacaoAllianzSantander15(valores[1],valores[2], rodape, cabecalho, valores[4], valores[5])
				break;

			case "Carta_RenovacaoApolice_30D":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/cabecalho30dsantander.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/rodape30dsantander.png";
				message.setSubject("Renovação Apólice Allianz/Santander - "+valores[2]);
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return AutoTemplateMessage.renovacaoAllianzSantander30(valores[1],valores[2], rodape, cabecalho, valores[4])
				break;

			case "RecusaDeFrota":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Lote Recusa de Frota "+ExtractDates.dataAtual() );
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return AutoTemplateMessage.recusaDeFrota(cabecalho, rodape)
				break;

			case "RenovacaoAutoFrota":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Renovação Auto Frota ");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return AutoTemplateMessage.renovacaoAutoFrota(cabecalho, rodape)
				break;

			case "SeguroRenovResidencia":
				String filename = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/residencia/bkofficerssidencia.png";
				message.setSubject("Seguro Colaborador - Residencia");
				multipart.addBodyPart(adicionaImagem(filename, "Content-ID", "image_id1"))
				return BackOfficeTemplateMessage.seguroFuncionarioResidencia(filename)
				break;

			case "SeguroRenovAuto":
				String filename = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/auto/bkoffauto.png";
				message.setSubject("Seguro Colaborador - Auto");
				multipart.addBodyPart(adicionaImagem(filename, "Content-ID", "image_id1"))
				return BackOfficeTemplateMessage.seguroFuncionarioAuto(filename)
				break;

			case "finalRenovAutoResidencia":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Seguro Renovacao Residencia - Planilha");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.renovAutoResidencia(cabecalho, rodape);
				break;

			case "finalRenovAuto":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Seguro Renovacao Auto - Planilha");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.renovAutoResidencia(cabecalho, rodape);
				break;

			case "envioPendenciaGOInspecao":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Envio Pendencia GO Inspecão");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.bloqueiosPendenciaGO(cabecalho, rodape)
				break;

			case "envioPendenciaGo":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Envio Pendencia GO Inspecão");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.bloqueiosPendenciaGO(cabecalho, rodape)
				break;

			case "FIEL":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Desbloqueio Proposta Agro Fiel Depositario");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.desbloqueioPropostaAgroFielDepositario(cabecalho, rodape)
				break;

			case "Agendamento":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Agendamento de Inspecoes");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.agendamentoInspecoes(cabecalho, rodape)
				break;

			case "EnvioPDF":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Envio de PDF Corretores Corporate");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return AutoTemplateMessage.envioPDF(cabecalho, rodape)
				break;

			case "Planilha1":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Altera Perfil");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return InfraAlteraPerfilCorretor.alteraPerfilCorretor(cabecalho, rodape)
				break;

			case "CotacoesEspeciais":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Renovacoes Cotacoes Especiais");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return AgcsTemplateMessage.renovacoesCotacoesEspeciais(cabecalho, rodape)
				break;

			case "apoliceTransportador":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Emissao de apolice transportador");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return TransporteTemplateMessage.emissaoApoliceTransporte(cabecalho, rodape)
				break;

			case "CadastroCorretor":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Cadastro de Corretor");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return InfraCadastroPerfilCorretor.cadastroPerfilCorretor(cabecalho, rodape)
				break;

			case "DesbloqueioProposta":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Desbloqueio Proposta");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.desbloqueioProposta(cabecalho, rodape)
				break;

			case "SegundaVia":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Segunda Via");
				return BackOfficeTemplateMessage.segundaVia(cabecalho, rodape)
				break;

			case "InspecaoRisco":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Inspecao de Risco");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.inspecaoRisco(cabecalho, rodape)
				break;

			case "RenovaSantander":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Renovação Santander");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return AutoTemplateMessage.renovaSantander(cabecalho, rodape)
				break;

			case "RecusaPropostaAuto":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Recusa Proposta Auto");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return AutoTemplateMessage.renovaSantander(cabecalho, rodape)
				break;

			case "AtualizaCustoApolicesVigentes":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Atualiza Custo de Apolices Vigentes");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return SaudeTemplateMessage.reajusteSaude(cabecalho, rodape)
				break;

			case "CriticasSIBGestaoCliente":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - CriticasSIB Gestao de Cliente");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return SaudeTemplateMessage.criticasSIBGestaoCliente(cabecalho, rodape)
				break;

			case "emailCorretor":
				message.setSubject(""+valores[2]+" - "+valores[1] );
				return AutoTemplateMessage.corretor()
				break;

			case "CriticasSIB":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - CriticasSIB");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return SaudeTemplateMessage.criticasSIB(cabecalho, rodape)
				break;

			case "AberturaSinistroCoFHE":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Abertura de Sinistro Cosseguro FHE");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return SinistroTemplateMessage.aberturaSinistroCoFHE(cabecalho, rodape)
				break;

			case "DescontosEspecíficosSinistro":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Descontos Específicos Sinistro");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return SinistroTemplateMessage.descontosEspecíficosSinistro(cabecalho, rodape)
				break;

			case "InativacaoProcedimento":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Inativação de Procedimento");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return SaudeTemplateMessage.inativacaoProcedimento(cabecalho, rodape)
				break;

			case "InativacaoMATMED":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Inativação MAT MED");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return SaudeTemplateMessage.inativacaoProcedimento(cabecalho, rodape)
				break;

			case "InclusaoVCSantander":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Inclusao VC Santander");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.inclusaoVCSantander(cabecalho, rodape)
				break;

			case "CadastroCorretorFilialFisica":
				List<String> imagens = new ArrayList<>();
				imagens[0] = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/cadastroCorretorFilialFisica2022/a_01.jpg"
				imagens[1] = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/cadastroCorretorFilialFisica2022/a_02X.jpg"
				imagens[3] = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/cadastroCorretorFilialFisica2022/a_05.jpg"
				imagens[4] = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/cadastroCorretorFilialFisica2022/a_08.jpg"
				imagens[5] = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/cadastroCorretorFilialFisica2022/a_12.jpg"
				imagens[6] = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/cadastroCorretorFilialFisica2022/a_24.jpg"
				imagens[7] = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/cadastroCorretorFilialFisica2022/a_27.jpg"
				imagens[8] = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/cadastroCorretorFilialFisica2022/a_31.jpg"
				imagens[9] = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/cadastroCorretorFilialFisica2022/a_33.jpg"
				imagens[10] = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/cadastroCorretorFilialFisica2022/a_35.jpg"
				imagens[11] = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/cadastroCorretorFilialFisica2022/a_15X.jpg"
				imagens[12] = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/cadastroCorretorFilialFisica2022/a_20.jpg"
				imagens[13] = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/cadastroCorretorFilialFisica2022/a_21.jpg"
				message.setSubject("C"+valores[2]+" "+valores[1]+" CÓDIGO "+valores[7]);
				multipart.addBodyPart(adicionaImagem(imagens[0],"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(imagens[1],"Content-ID", "image_id2X"))
				multipart.addBodyPart(adicionaImagem(imagens[3],"Content-ID", "image_id5"))
				multipart.addBodyPart(adicionaImagem(imagens[4],"Content-ID", "image_id8"))
				multipart.addBodyPart(adicionaImagem(imagens[5],"Content-ID", "image_id12"))
				multipart.addBodyPart(adicionaImagem(imagens[6],"Content-ID", "image_id24"))
				multipart.addBodyPart(adicionaImagem(imagens[7],"Content-ID", "image_id27"))
				multipart.addBodyPart(adicionaImagem(imagens[8],"Content-ID", "image_id31"))
				multipart.addBodyPart(adicionaImagem(imagens[9],"Content-ID", "image_id33"))
				multipart.addBodyPart(adicionaImagem(imagens[10],"Content-ID", "image_id35"))
				multipart.addBodyPart(adicionaImagem(imagens[11],"Content-ID", "image_id15X"))
				multipart.addBodyPart(adicionaImagem(imagens[12],"Content-ID", "image_id20"))
				multipart.addBodyPart(adicionaImagem(imagens[13],"Content-ID", "image_id21"))
				return BackOfficeTemplateMessage.cadastroCorretorFilialFisica(valores[1], valores[3], valores[4], valores[5], valores[6], valores[8], valores[9],  imagens)
				break;

			case "CadastroCorretorFilialFisicaFinalProcesso":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Cadastro Corretor Filial Fisica");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.cadastroCorretorFilialFisicaProcessoFinal(cabecalho, rodape)
				break;

			case "recadastramentoSUSEP":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Recadastramento SUSEP Consulta");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.inclusaoVCSantander(cabecalho, rodape)
				break;

			case "DistribuicaoAutomaticaIR":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Distribuição automática Inspeção de risco");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return GcfTemplateMessage.distribuicaoAutomaticaIR(cabecalho, rodape)
				break;

			case "BoletoAvulso":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Boleto Avulso");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.boletoAvulso(cabecalho, rodape)
				break;

			case "PendenciaSantander":
				System.err.println("["+valores[2]+"]\n["+valores[1]+"]")
				message.setSubject(valores[2]);
				return BackOfficeTemplateMessage.pendenciaSnatander(valores[1])
				break;

			case "PendenciaEmailSantander":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Envio de Pendencia Automatica Email Santande");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.pendenciaEmailSantander(cabecalho, rodape)
				break;

			case "GeraBoleto":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Gera boleto ferramenta duplicado recibos");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.geraBoleto(cabecalho, rodape)
				break;

			case "ValidaQuadroPDF":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Valida Quadro PDF");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return FinanceiroTemplateMessage.validaQuadroPDF(cabecalho, rodape)
				break;

			case "EmailRetornoGo":
				System.err.println("["+valores[2]+"]\n["+valores[1]+"]")
				message.setSubject(valores[2]);
				return BackOfficeTemplateMessage.envioRetornoGoUnit(valores[1])
				break;

			case "RetornoGo":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Envio de e-mail retorno GO Gestão Operacional");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.envioRetornoGoEmail(cabecalho, rodape)
				break;

			case "PendenciaAutomáticaCorretor":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Envio de pendência automática ao Corretor");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.envioRetornoGoEmail(cabecalho, rodape)
				break;

			case "EmailPendenciaAutomáticaCorretor":
				System.err.println("["+valores[2]+"]\n["+valores[1]+"]")
				message.setSubject(valores[2]);
				return BackOfficeTemplateMessage.envioPendenciaAutomáticaCorretorUnit(valores[1])
				break;

			case "RenovaSantanderSASAM":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Renovação Santander Sasam");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return AutoTemplateMessage.renovaSantander(cabecalho, rodape)
				break;

			case "ReposicaoValores":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Reposicao de Valores");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return SaudeTemplateMessage.reposicaoValores(cabecalho, rodape)
				break;

			case "ReajusteApoliceSaude":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Reajuste Apólice Saude");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return SaudeTemplateMessage.reposicaoValores(cabecalho, rodape)
				break;

			case "DesvinculaCodigoCorretor":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Desvincula Codigo Corretor");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return ComercialTemplateMessage.DesvinculaCodigoCorretor(cabecalho, rodape)
				break;

			case "GeraBlocoBCadastroCorretor":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Gera BlocoB Cadastro Corretor");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.geraBlocoBCadastroCorretorEmail(cabecalho, rodape)
				break;

			case "distribuicaoPropostasGO":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Distribuição de propostas pelo GO");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.distribuicaoPropostasGOlEmail(cabecalho, rodape)
				break;

			case "RecusaProposta":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Recusa Proposta");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.recusaPropostaEmail(cabecalho, rodape)
				break;

			case "ReativacaoApolice":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Reativação Apolice");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.reativacaoApolice(cabecalho, rodape)
				break;

			case "Cota_resid":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Cotação Residencia");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.renovAutoResidencia(cabecalho, rodape);
				break;

			case "imobiliariaResidencia":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Emissão Imobiliaria Residencia");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.renovAutoResidencia(cabecalho, rodape);
				break;

			case "imobiliariaEmpresa":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Emissão Imobiliaria Empresa");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.renovAutoResidencia(cabecalho, rodape);
				break;

			case "ComunicacaoBloqueio":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Comunicação de Bloqueio");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.renovAutoResidencia(cabecalho, rodape);
				break;

			case "ContactCenter":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Contact Center");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return BackOfficeTemplateMessage.renovAutoResidencia(cabecalho, rodape);
				break;

			case "AtualizacaoValoresAlcada":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Atualizaçâo de Valores de Alçada");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return SinistroTemplateMessage.aberturaSinistroCoFHE(cabecalho, rodape);
				break;

			case "ApolicesHomologacao":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Carga de Apolices em Homologação");
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return AutoTemplateMessage.apolicesHomologacao(cabecalho, rodape);
				break;
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param ramo
	 * @param assunto
	 * @return
	 */
	public static String messageRules(String ramo, String assunto){
		switch (ramo){
			case "SeguroRenovResidencia":
				String filename = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/residencia/bkofficerssidencia.png";
				message.setSubject(assunto);
				multipart.addBodyPart(adicionaImagem(filename, "Content-ID", "image_id1"))
				return BackOfficeTemplateMessage.seguroFuncionarioResidencia(filename)
				break;

			case "SeguroRenovAuto":
				String filename = "C:/Users/Public/RPA_ALLIANZ/Images/backoffice/auto/bkoffauto.png";
				message.setSubject(assunto);
				multipart.addBodyPart(adicionaImagem(filename, "Content-ID", "image_id1"))
				return BackOfficeTemplateMessage.seguroFuncionarioAuto(filename)
				break;

			case "ReprocessamentoEmissaoApolice":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Reprocessamento de Emissão de Apolice " +assunto);
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return AutoTemplateMessage.reprocessamentoEmissaoApolice(cabecalho, rodape)
				break;

			case "RenovaSantanderCartasAviso":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Renovação Santander "+assunto);
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return AutoTemplateMessage.renovaSantander(cabecalho, rodape)
				break;

			case "pozoAuto":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Auto 1241 " +assunto);
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return MigracaoDeDadosTemplateMessage.migracaoDeDadosEmail(cabecalho, rodape)
				break;

			case "pozoAutoAnual":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Auto 1240 " +assunto);
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return MigracaoDeDadosTemplateMessage.migracaoDeDadosEmail(cabecalho, rodape)
				break;

			case "pozoCondominio":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Condominio 2027 " +assunto);
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return MigracaoDeDadosTemplateMessage.migracaoDeDadosEmail(cabecalho, rodape)
				break;

			case "pozoEmpresa":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Empresa 2028 " +assunto);
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return MigracaoDeDadosTemplateMessage.migracaoDeDadosEmail(cabecalho, rodape)
				break;

			case "pozoResidencia":
				String cabecalho = "C:/Users/Public/RPA_ALLIANZ/Images/logo_allianz_rs.png";
				String rodape = "C:/Users/Public/RPA_ALLIANZ/Images/RODAPEACEITACAO.png";
				message.setSubject("RPA - Residencia 2026 " +assunto);
				multipart.addBodyPart(adicionaImagem(cabecalho,"Content-ID", "image_id1"))
				multipart.addBodyPart(adicionaImagem(rodape,"Content-ID", "image_id2"))
				return MigracaoDeDadosTemplateMessage.migracaoDeDadosEmail(cabecalho, rodape)
				break;
		}
	}
}

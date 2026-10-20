package templateMessage

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

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
import sendEmail.MessageFactory
import auxiliares.StringFormatter
import sendEmail.htmlCadastroCorretor
import sendEmail.htmlAlteracoesRenovacaoBanrisul
import sendEmail.*;

public class BackOfficeTemplateMessage extends MessageFactory{

	
	
	
	
	public static String parcerias(String corpoEmail){
		String sb = "<head>" +
		         ""+corpoEmail
		return sb;
	}
	
	
	/**
	 * Aviso de Não Renovação Apólice Allianz/Santander
	 * @return
	 */
	public static String seguroFuncionarioResidencia(String filename){
		String sb = "<head>" +
				addImageCabecalho(filename,'80','35')+"<p>"+
				"Segue anexa proposta de renovação do seu seguro <strong>ALLIANZ RESIDÊNCIA</strong> já com as condições imbativeis e exclusivas para você, colaborador.<p>" +
				"Esperamos que você continue usufruindo desta vantagem e aguardamos o seu retorno a este e-mail até 48 horas para efetivarmos a sua renovação. Caso este<p>" +
				"prazo não seja cumprido, poderá acarretar em mudança do valor apresentado.<p>"+
				"Caso queira fazer outras cotações do <strong>ALLIANZ AUTO</strong> e <strong>ALLIANZ RESIDÊNCIA</strong>, aproveite este benefícios que também é extensivo à sua familia: pais, filhos e cônjuges.<p>" +
				"<strong>Acesse o menu lateral direito da IntraAllianz > seguro para colaborador</strong> e faça já a sua cotação ou endosso no canal exclusivo com a corretora Corsetec.<p>"
		return sb;
	}

	public static String seguroFuncionarioAuto(String filename){
		String sb = "<head>" +
				addImageCabecalho(filename,'80','35')+"<p>"+
				"Segue anexa proposta de renovação do seu seguro <strong>ALLIANZ AUTO</strong> já com as condições imbativeis e exclusivas para você, colaborador.<p>" +
				"Esperamos que você continue usufruindo desta vantagem e aguardamos o seu retorno a este e-mail até 48 horas para efetivarmos a sua renovação. Caso este<p>" +
				"prazo não seja cumprido, poderá acarretar em mudança do valor apresentado.<p>"+
				"Caso queira fazer outras cotações do <strong>ALLIANZ AUTO</strong> e <strong>ALLIANZ RESIDÊNCIA</strong>, aproveite este benefícios que também é extensivo à sua familia: pais, filhos e cônjuges.<p>" +
				"<strong>Acesse o menu lateral direito da IntraAllianz > seguro para colaborador</strong> e faça já a sua cotação ou endosso no canal exclusivo com a corretora Corsetec.<p>"
		return sb;
	}
	
	
	public static String  enviarEmailVoucher(String ...args){
		String[] valores = args
		String sb = "<head>" +
		            "<strong>Prezado, olá</strong>.<p>"+
		            "<strong style='text-decoration: underline;'>PENDÊNCIA - VISTORIA PRÉVIA</strong>.<p>"+
		            "<strong>Informamos que a análise para aceitação desta proposta está condicionada a realização da vistoria prévia.</strong>.</br>"+
		            "<strong>A não realização da vistoria prévia gerará a recusa da proposta em até 03 (três) dias uteis, a contar da data de transmissão da proposta.</strong>.</br>"+
		            "**CASO NECESSÁRIA ALGUMA COMUNICAÇÃO ADICIONAL, NÃO ALTERAR O CAMPO ASSUNTO DESTE EMAIL**.<p>"+
				addImageCabecalho(valores[0],'80','35')+"</br>"+
				addImageRodape(valores[1],'80','35')+"<p>"
		return sb;
	}

	
	/**
	 *
	 * @return
	 */
	public static String textoPadao(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}
	
	/**
	 *
	 * @return
	 */
	public static String textoPadaoErro(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'60','15')+"<br>"+
				templateBodyError()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 * Renovação Auto Residencia
	 * @return
	 */
	public static String renovAutoResidencia(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}



	/**
	 * Bloqueios Pendência GO
	 * @return
	 */
	public static String bloqueiosPendenciaGO(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 * Desbloqueio Proposta Agro Fiel Depositario
	 * @return
	 */
	public static String desbloqueioPropostaAgroFielDepositario(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 * @author T_DCDias
	 * @return
	 */
	public static agendamentoInspecoes(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 * @author T_DCDias
	 * @return
	 * Envio de PDF - Corretores Corporate
	 */
	public static envioPDFCorretoresCorporate(){
		String sb = "<head>" +
				"Prezado,<p>" +
				"Segue Apolice e Boleto. <p>" +
				"Att..<p>"
		return sb;
	}


	/**
	 * Desbloqueio Proposta
	 * @return
	 */
	public static String desbloqueioProposta(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 * Segunda via
	 * @return
	 */
	public static String segundaVia(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 * Segunda via
	 * @return
	 */
	public static String inspecaoRisco(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 *
	 * @return
	 */
	public static String inclusaoVCSantander(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 *
	 * @return
	 */
	public static String boletoAvulso(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 *
	 * @return
	 */
	public static String geraBoleto(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 *
	 * @return
	 */
	public static String pendenciaEmailSantander(String cabecalho, String rodape){
		return body(cabecalho, rodape);
	}

	/**
	 *@author ALZ_ServiceQA
	 * @return
	 */
	public static String pendenciaSnatander(String texto){
		return pendenciaSnatanderBody(texto);
	}

	/**
	 *
	 * @return
	 */
	public static String cadastroCorretorFilialFisicaProcessoFinal(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 * Renovação Apólice Allianz/Santander
	 * @return
	 */
	
	
	/**
	 *@author ALZ_ServiceQA
	 * @return
	 */
	public static String body(String cabecalho, String rodape){
		String sb = '<head>' +
				"<p><br>"+
				addImageCabecalho(cabecalho,'80','35')+"<br>"+
				templateBody()+".<p>"+
				addImageRodape(rodape,'80','35')+"</p>"
		return sb;
	}

	/**
	 *
	 * @return
	 */
	public static String pendenciaSnatanderBody(String texto){
		String sb = StringFormatter.emailBodyFormatter(texto)
		return sb;
	}

	/**
	 *
	 * @return
	 */
	public static String envioRetornoGoEmail(String cabecalho, String rodape){
		return body(cabecalho, rodape);
	}

	/**
	 *@author ALZ_ServiceQA
	 * @return
	 */
	public static String envioRetornoGoUnit(String texto){
		return envioRetornoGoBody(texto);
	}

	/**
	 *@author ALZ_ServiceQA
	 * @return
	 */
	public static String envioPendenciaAutomáticaCorretorUnit(String proposta){
		return envioPendenciaAutomáticaCorretor(proposta);
	}

	/**
	 *
	 * @return
	 */
	public static String geraBlocoBCadastroCorretorEmail(String cabecalho, String rodape){
		return body(cabecalho, rodape);
	}

	/**
	 *
	 * @return
	 */
	public static String distribuicaoPropostasGOlEmail(String cabecalho, String rodape){
		return body(cabecalho, rodape);
	}

	/**
	 *
	 * @return
	 */
	public static String recusaPropostaEmail(String cabecalho, String rodape){
		return body(cabecalho, rodape);
	}

	/**
	 *
	 * @return
	 */
	public static String reativacaoApolice(String cabecalho, String rodape){
		return body(cabecalho, rodape);
	}

	/**
	 *
	 * @return
	 */
	public static String envioPendenciaAutomáticaCorretor(String proposta){

		String sb = "<head>" +"<p>"+StringFormatter.emailBodyFormatterPendenciaAutomaticaCorretor(proposta)

		//		String sb = "<head>" +"<p>"+
		//		"Prezado (a) Corretor."+
		//		"	<p>" +
		//		"Existe (m) uma ou mais pendência (s) gerada (a) para a proposta NRº "+proposta+"<p>" +
		//		"<p>" +
		//		"*O RETORNO DEVE SER REALIZADO ATRAVES DA OPÇÃO DE INDEXAR ARQUIVO NO CODIGO 21*<p>" +
		//		"<p>" +
		//		"Laudo de vistoria cautelar não é aceito, somente conforme abaixo:<p>" +
		//		"<p>" +
		//		"Enviar laudo de inspeção veicular, emitido por empresa ACREDITADA pelo Inmetro. Nesta inspeção devem constar pelo menos duas fotos do veículo, o decalque do chassi e um check-list com todos os itens verificados.<br>" +
		//		"O laudo também deve atestar que o veículo está em plenas condições de uso e segurança, conforme as regras do DENATRAN.<p>" +
		//		"<p>" +
		//		"<p>" +
		//		"RETORNO DA(s) PENDÊNCIA(s)>>><br>" +
		//		"Para nos retornar a pendência há duas opções:<br>" +
		//		"•	Via portal, através de nota especifica, informada no texto da pendência que consta em seu painel de avisos; OU<br>" +
		//		"•	Através deste e-mail, e para isto o Campo Assunto Não deve ser alterado.<p>" +
		//		"<p>" +
		//		"Importante:<br>" +
		//		"•	Após o retorno da pendência o time de emissão tem um prazo de até 03 dias úteis para análise e emissão do seu documento.<br>" +
		//		"•	O prazo para retorno desta pendência é de 2 dias uteis.<br>" +
		//		"•	O  não  recebimento  do  retorno  dentro do prazo, pode acarretar a recusa da proposta.<br>" +
		//		"<p>" +
		//		"A disposição.<p>"

		return sb;
	}

	/**
	 *
	 * @return
	 */
	public static String envioRetornoGoBody(String texto){
		String sb = StringFormatter.emailBodyFormatter(texto)
		return sb;
	}
	
	public static String cadastroCorretorFilialFisica(String corretor, String user, String pwr,String NOMEACCOUNT, String EMAILACCOUNT,String ddd, String telefone, String ddd_account, String telefone_account,List<String> imagens){
				System.out.println(ddd+" "+telefone)
				System.out.println()
				//String sb = htmlCadastroCorretor.htmlBoasVindas(corretor, user, pwr, NOMEACCOUNT, EMAILACCOUNT, ddd, telefone, ddd_account, telefone_account,imagens)
				String sb = htmlCadastroCorretor.htmlBoasVindasIBLBDM75460(corretor, user, pwr, NOMEACCOUNT, EMAILACCOUNT, ddd, telefone, ddd_account, telefone_account,imagens)
				return sb;
	}
	
	
	public static String alteracoesRenovacaoBanrisul(String corretor, String email, String telefone, String susep, String proponente, String cpfcnpj, String apolice, String finalVigencia, String nome, List<String> imagens){
		System.out.println(corretor)
		System.out.println()
		String sb = htmlAlteracoesRenovacaoBanrisul.htmlAlteracoesRenovacaoBanrisulBody(corretor, email, telefone, susep, proponente, cpfcnpj, apolice, finalVigencia, nome, imagens)
		return sb;
	}
	
	public static String naoRenovacaoBanrisul(String corretor, String email, String telefone, String susep, String proponente, String cpfcnpj, String apolice, String finalVigencia, String nome, List<String> imagens){
		System.out.println(corretor)
		System.out.println()
		String sb = htmlNaoRenovacaoBanrisul.htmlNaoRenovacaoBanrisulBody(corretor, email, telefone, susep, proponente, cpfcnpj, apolice, finalVigencia, nome, imagens)
		return sb;
	}
	
	
	public static String allFilialFisica(String totalError, 
		                                 String totalPassed, 
										 String listAlert, 
										 String listPassed, 
										 String horaInicio, 
										 String horaFinal,
										 String horaTotal, 
										 String servidor
										 ){
		
		System.out.println(totalError+" "+listAlert+" "+ listPassed+" "+ horaInicio+" "+ horaFinal+" "+horaTotal+" "+servidor)
		System.out.println()

		String sb = "<body style='margin:0; padding:0; background-color:#ffffff'>" +
		"<center>" +
		"<div class='gmailfix' style='white-space:nowrap; font:15px Arial,Sans-serif; line-height:0'>" +
		"&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"+
		"</div><table width='600' border='0' cellpadding='0' cellspacing='0' bgcolor='#ffffff'>" +
		"</table><table width='600' align='center' border='0' cellpadding='0' cellspacing='0' class='width-1'>"+
		
		"<tbody>"+

		"<tr bgcolor='#D3D3D3'>"+
		
		"	<td>"+
		"		<table  border='0' cellpadding='0' cellspacing='0'>"+
		"			<tbody><tr>"+
		"				<td>"+
		"					<img style='line-height: 0px; display: block;' src='cid:image_id1'>"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		
		"</tr>"+

		
		"			<tr>"+
		"				<td>"+
		"					<table bgcolor='#D3D3D3' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 600px;'>"+
		"						<tbody><tr>"+
		"							<td align='left' style='color: #122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"								<b>"+
		"								Prezados!"+
		"								<br><br>"+
		"  							    Todos os dados foram processados.<br>"+
	    "								    Att <br>"+
		"							</td>"+
		"						  </tr>"+
		"					    </tbody>"+
		"<tr>"+
		"	<td style='height: 46px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+
        "</table>"+
		"</td>"+
		"</tr>"+
		
	    /**
	     * Carrega lista de registros concluidos
	     */
		passed(totalPassed, listPassed)+
		
		/**
		 * Carrega campo de mensagens de alerta 
		 */
		 alertas(totalError, listAlert)+
		
		"<!-- card1 -->"+
		"<tr>"+
		"	<td bgcolor='#122b54'>"+
		"		<table bgcolor='122b54' border='0' cellpadding='0' cellspacing='0' align='center'>"+
		"			<tbody><tr>"+
		"				<td style='height: 20px;'>"+
		"					&nbsp;"+
		"				</td>"+
		"			</tr>"+
		"			<tr>"+
		"				<td>"+
		"					<table border='0' cellpadding='0' cellspacing='0'>"+
		"						<tbody><tr>"+
		"							<td align='left' style='color: #ffffff; font-size: 18px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"								Tempo de execução: <br>"+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+
		"			<tr>"+
		"				<td style='height: 5px;'>"+
		"					&nbsp;"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+
		
		"<tr>"+
		"	<td style='height: 5px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+
		
		"			<tr bgcolor='#b5dae6'>"+
		"				<td>"+
		"					<table bgcolor='#b5dae6' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 120px;'>"+
		"						<tbody><tr>"+
		
		"							<td align='left' style='color: #122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		//"								<b>"+
		"									Inicio: "+
		//"								</b>"+
		"							</td>"+
		"							<td align='left' style='color: #FF4500; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"								<b>"+
		"									"+horaInicio+
		"								</b>"+
		"							</td>"+
	
		
			
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+
		
		"			<tr bgcolor='#b5dae6'>"+
		"				<td>"+
		"					<table bgcolor='#b5dae6' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 143px;'>"+
		"						<tbody><tr>"+
		"							<td align='left' style='color:#122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
//		"								<b>"+
		"									Fim: "+
//		"								</b>"+
		"							</td>"+
		"							<td align='left' style='color:#FF4500; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"								<b>"+
		"									"+horaFinal+
		"								</b>"+
		"							</td>"+
		
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+
		
		"			<tr bgcolor='#b5dae6'>"+
		"				<td>"+
		"					<table bgcolor='#b5dae6' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 600px;'>"+
		"						<tbody><tr>"+
		"							<td align='left' style='color: #122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
//		"								<b>"+
		"									Tempo de processamento: "+
//		"								</b>"+
		"							</td>"+
		"							<td align='left' style='color:#FF4500; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"								<b>"+
		"									"+horaTotal+
		"								</b>"+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+
		
		"			<tr bgcolor='#b5dae6'>"+
		"				<td>"+
		"					<table bgcolor='#b5dae6' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 250px;'>"+
		"						<tbody><tr>"+
		"							<td align='left' style='color: #122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
//		"								<b>"+
		"									Servidor executado: "+
//		"								</b>"+
		"							</td>"+
		"							<td align='left' style='color:#FF4500; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		"								<b>"+
		"									"+servidor+
		"								</b>"+
		"							</td>"+
		"						</tr>"+
		"					</tbody></table>"+
		"				</td>"+
		"			</tr>"+
		"		</tbody></table>"+
		"	</td>"+
		"</tr>"+

		"<tr>"+
		"	<td style='height: 20px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+
		
		
		"<!-- box-btns -->"+
		"<tr bgcolor='#122b54' align='left'>"+
		"	<td>"+
		"		<table align='center' border='0' cellpadding='0' cellspacing='0'>"+
		"			 <!-- parte-1 -->"+
		"			<tbody><tr>"+
		"				<td>"+
		"					<table border='0' cellpadding='0' cellspacing='0'>"+
		"						<tbody><tr>"+
		"							<td>"+
	    "  							  <img style='line-height: 0px; display: block;' src='cid:image_id2'>"+
		"							</td>"+
		"						</tr>"+
		"					</tbody>"+
        "                 </table>"+
		"				</td>"+
		"</tr>"+
		
		
		
		"<tr>"+
		"  <td>"+
		"	 <table bgcolor='#D3D3D3' border='0' cellpadding='0' cellspacing='0' align='left' style='width: 600px;'>"+
		"		<tbody><tr>"+
	    "         <td align='left' class='textfooter2' style='color: #414141; font-size: 9px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.7px; vertical-align: top;'>"+
		"				</td>"+
		"			</tr>"+
		"		</tbody>"+
		"<tr>"+
		"	<td style='height: 46px;'>"+
		"		&nbsp;"+
		"	</td>"+
		"</tr>"+
        "</table>"+
		"</td>"+
		"</tr>"

		return sb;
		
		}
										 
		public static String passed(String totalPassed, String listPassed){
			int tl =  Integer.parseInt(totalPassed);
			String str
			if(tl > 0){
				str = "<!-- card1 -->"+
				"<tr>"+
				"	<td bgcolor='#122b54'>"+
				"		<table bgcolor='122b54' border='0' cellpadding='0' cellspacing='0' align='center'>"+
				"			<tbody><tr>"+
				"				<td style='height: 20px;'>"+
				"					&nbsp;"+
				"				</td>"+
				"			</tr>"+
				"			<tr>"+
				"				<td>"+
				"					<table border='0' cellpadding='0' cellspacing='0'>"+
				"						<tbody><tr>"+
				"							<td align='left' style='color: #ffffff; font-size: 18px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
				"								Dados processados: "+totalPassed+"<br>"+
				"							</td>"+
				"						</tr>"+
				"					</tbody></table>"+
				"				</td>"+
				"			</tr>"+
				"			<tr>"+
				"				<td style='height: 5px;'>"+
				"					&nbsp;"+
				"				</td>"+
				"			</tr>"+
				"		</tbody></table>"+
				"	</td>"+
				"</tr>"+
			
				"<!-- card2 -->"+
				"<tr>"+
				"	<td bgcolor='#dfeff2'>"+
				"		<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='center'>"+
				"			<tbody><tr>"+
				"				<td style='height: 25px;'>"+
				"					&nbsp;"+
				"			</td>"+
				"			</tr>"+
			
				"			<tr bgcolor='#b5dae6'>"+
				"				<!-- box1 -->"+
				"				<td>"+
				"					<table border='0' cellpadding'0' cellspacing='0'>"+
				"						<tbody>"+
				"						<tr>"+
				"							<td>"+
				"								<table align='center' border='0' cellpadding='0' cellspacing='0'>"+
				"									<tbody>"+
				
			//        "                                       <tr>"+
			//		"										<td align='center' style='color: #122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
			//		"											<b>"+
				"												"+listPassed+""+
			//		"											</b>"+
			//		"										</td>"+
			//		"									    </tr>"+
				
				
				"								</tbody></table>"+
				"							</td>"+
				"						</tr>"+
				"					</tbody></table>"+
				"				</td>"+
				"				<td style='width: 13px;'>"+
				"				</td>"+
				"<tr>"+
				"	<td style='height: 20px;'>"+
				"		&nbsp;"+
				"	</td>"+
				"</tr>"
				
				
			} else {
				str = "<tr>"+
				"	<td style='height: 20px;'>"+
				"		&nbsp;"+
				"	</td>"+
				"</tr>"
			}
			return str;
		}								 
										 
		public static String alertas(String totalError, String listAlert ){
			int tl =  Integer.parseInt(totalError);
			String str 
			if(tl > 0){
				str = "<!-- card1 -->"+
			"<tr>"+
			"	<td bgcolor='#122b54'>"+
			"		<table bgcolor='122b54' border='0' cellpadding='0' cellspacing='0' align='center'>"+
			"			<tbody><tr>"+
			"				<td style='height: 20px;'>"+
			"					&nbsp;"+
			"				</td>"+
			"			</tr>"+
			"			<tr>"+
			"				<td>"+
			"					<table border='0' cellpadding='0' cellspacing='0'>"+
			"						<tbody><tr>"+
			"							<td align='left' style='color: #ffffff; font-size: 18px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
			"								Alertas de dados não processados: "+totalError+"<br>"+
			"							</td>"+
			"						</tr>"+
			"					</tbody></table>"+
			"				</td>"+
			"			</tr>"+
			"			<tr>"+
			"				<td style='height: 5px;'>"+
			"					&nbsp;"+
			"				</td>"+
			"			</tr>"+
			"		</tbody></table>"+
			"	</td>"+
			"</tr>"+
		
			"<!-- card2 -->"+
			"<tr>"+
			"	<td bgcolor='#dfeff2'>"+
			"		<table bgcolor='#dfeff2' border='0' cellpadding='0' cellspacing='0' align='center'>"+
			"			<tbody><tr>"+
			"				<td style='height: 25px;'>"+
			"					&nbsp;"+
			"			</td>"+
			"			</tr>"+
		
			"			<tr bgcolor='#b5dae6'>"+
			"				<!-- box1 -->"+
			"				<td>"+
			"					<table border='0' cellpadding'0' cellspacing='0'>"+
			"						<tbody>"+
			"						<tr>"+
			"							<td>"+
			"								<table align='center' border='0' cellpadding='0' cellspacing='0'>"+
			"									<tbody>"+
			
		//        "                                       <tr>"+
		//		"										<td align='center' style='color: #122b54; font-size: 15px; font-family: Arial, Helvetica, sans-serif; letter-spacing: -0.4px; vertical-align: top;'>"+
		//		"											<b>"+
			"												"+listAlert+""+
		//		"											</b>"+
		//		"										</td>"+
		//		"									    </tr>"+
			
			
			"								</tbody></table>"+
			"							</td>"+
			"						</tr>"+
			"					</tbody></table>"+
			"				</td>"+
			"				<td style='width: 13px;'>"+
			"				</td>"+
			"<tr>"+
			"	<td style='height: 20px;'>"+
			"		&nbsp;"+
			"	</td>"+
			"</tr>"
			} else {
			  str = "<tr>"+
			        "	<td style='height: 20px;'>"+
			        "		&nbsp;"+
			        "	</td>"+
			        "</tr>"
			}
			return str;
		} 
}

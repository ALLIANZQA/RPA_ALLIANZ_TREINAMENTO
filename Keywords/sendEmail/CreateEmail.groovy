package sendEmail;

import rpacommands.rpaCommands

public class CreateEmail  extends rpaCommands{


	private static String authPathConfigEmailProp = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Credenciais\\ConfigAuthEmailProp.properties"
	private static String authPathConfigEmail = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Credenciais\\ConfigAuthEmail.txt"
	private static String authPathConfigEmailCadCorretor = "C:\\Users\\Public\\RPA_ALLIANZ\\Emails\\Credenciais\\ConfigAuthEmailCadastroCorretor.txt"

	/**
	 * @author T_DCDias
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param path1
	 * @param nome1
	 * @param path2
	 * @param nome2
	 * @param texto
	 * @param subject
	 * @param host
	 * @param porta
	 * @return
	 */
	public static createAllEmailSendMultipleFiles(String sender,
			String emaildestination,
			String user,
			String pwdsender,
			List<String> anexos ,
			String host,
			String porta,
			String ramo,
			String totalAlert,
			String totalPassed,
			String messagesAlerts,
			String messagesPassed,
			String startHour,
			String endHours,
			String totalHours,
			String hostName,
			String totalRegistros,
			String porcentagem,
			String porcentagemDeAlertas,
			String dataInicio,
			String dataFinal,
			String assunto
	){
		try{
			//	lerConfiguraçõesAuth(authPathConfigEmail)
			//	lerConfiguraçõesAuthProp(authPathConfigEmailProp)
			System.out.println(user+" "+pwdsender+" "+host+" "+porta)
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createAllMessageFiles(configEmail.configureOutLookAuth(host, porta), anexos, ramo, totalAlert,totalPassed, messagesAlerts,messagesPassed, startHour,endHours,totalHours,hostName,totalRegistros, porcentagem, porcentagemDeAlertas, dataInicio, dataFinal, assunto);
		} catch(Exception e){
			System.err.println("Create Email Send Multiple Files FAIL\n"+e);
		}
	}


	/**
	 * @author T_DCDias
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param path1
	 * @param nome1
	 * @param path2
	 * @param nome2
	 * @param texto
	 * @param subject
	 * @param host
	 * @param porta
	 * @return
	 */
	public static createEmailSendMultipleFiles(String sender, String emaildestination, List<String> anexos ,String ramo ){
		//lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessageFiles(configEmail.configureOutLookAuth(host, porta), anexos, ramo);
		} catch(Exception e){
			System.err.println("Create Email Send Multiple Files FAIL\n"+e);
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param anexos
	 * @param host
	 * @param porta
	 * @param ramo
	 * @param tomador
	 * @param apolice
	 * @return
	 */
	public static boolean createEmailSendMultipleFilesCorretor(String sender, String emaildestination, List<String> anexos , String ramo, String tomador, String apolice ){
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		boolean envio
		System.err.println(ramo+" "+tomador+" "+apolice);
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessageFilesCorretor(configEmail.configureOutLookAuth(host, porta), anexos, ramo, tomador,apolice);
			envio = true
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
			envio false
		}
		return envio
	}


	/**
	 * @author ALZ_ServiceQA
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param anexos
	 * @param host
	 * @param porta
	 * @param ramo
	 * @param tomador
	 * @param apolice
	 * @return
	 */
	public static boolean createEmailSendMultipleFilesParcerias(String sender, String emaildestination, List<String> anexos , String ramo, String tomador, String apolice, String corpoEmail, String assunto, String ccemaildestination ){
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		boolean envio
		System.err.println(ramo+" "+tomador+" "+apolice);
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination, ccemaildestination );
			messageFactory.createMessageFilesParcerias(configEmail.configureOutLookAuth(host, porta), anexos, ramo, tomador,apolice,corpoEmail,assunto);
			envio = true
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
			envio false
		}
		return envio
	}





	/**
	 * Novas configuirações
	 */

	/**
	 * @author T_DCDias
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param texto
	 * @param subject
	 * @param host
	 * @param porta
	 * @return
	 * Envio de e-mail sem anexo
	 */
	public static createEmailSend(String sender, String emaildestination, String ramo ){
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessage(configEmail.configureOutLook(host, porta), ramo);
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
		}
	}

	/**
	 * Novas configuirações
	 */

	/**
	 * @author T_DCDias
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param texto
	 * @param subject
	 * @param host
	 * @param porta
	 * @return
	 * Envio de e-mail sem anexo
	 */
	public static createEmailSend(String sender, String emaildestination, List<String> anexos , String ramo, String assunto ){
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessage(configEmail.configureOutLookAuth(host, porta), ramo, texto, assunto);
		} catch(Exception e){
			System.err.println("Create Email Send Multiple Files FAIL\n"+e);
		}
	}

	/**
	 * @author T_DCDias
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param texto
	 * @param subject
	 * @param host
	 * @param porta
	 * @return
	 * Envio de e-mail sem anexo
	 */
	public static createEmailSendEmissaoSantander(String sender, String emaildestination, List<String> anexos , String ramo, String assunto ){
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		System.out.println()
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessageMutFiles(configEmail.configureOutLookAuth(host, porta),anexos ,ramo, assunto);
		} catch(Exception e){
			System.err.println("Create Email Send Multiple Files FAIL\n"+e);
		}
	}

	/**
	 * @author ALZ_ServiceQA
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param anexos
	 * @param host
	 * @param porta
	 * @param ramo
	 * @param assunto
	 * @return
	 */
	public static boolean createEmailSendMultipleFilesSeguroFuncionario(String sender, String emaildestination,List<String> anexos , String ramo, String assunto){
		boolean enviado
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessageFilesSeguroFuncionario(configEmail.configureOutLookAuth(host, porta), anexos, ramo, assunto);
			enviado = true
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
			enviado = false
		}
		return enviado
	}

	/**
	 * @author T_DCDias
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param anexos
	 * @param host
	 * @param porta
	 * @param ramo
	 * @param dataAnterior
	 * @return
	 */
	public static createEmailSendMultipleFilesReprocessaEmissaoApolice(String sender, String emaildestination, List<String> anexos , String host, String  porta, String ramo, String dataAnterior){
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessageFilesReprocessaEmissaoApolice(configEmail.configureOutLookAuth(host, porta), anexos, ramo, dataAnterior);
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
		}
	}

	/**
	 * @author T_DCDias
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param anexos
	 * @param host
	 * @param porta
	 * @param ramo
	 * @param dataAnterior
	 * @return
	 */
	public static createEmailSendMultipleFilesMigracaoDeDados(String sender, String emaildestination, List<String> anexos , String ramo, String msg){
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessageFilesMigracaoDeDados(configEmail.configureOutLook(host, porta), anexos, ramo, msg);
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
		}
	}


	/**
	 * @author T_DCDias
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param texto
	 * @param subject
	 * @param host
	 * @param porta
	 * @return
	 * Envio de e-mail sem anexo
	 */
	public static boolean createEmailSendSantander(String sender, String emaildestination, String host, String  porta, String ramo, String segurado, String apolice,String FINAL_VIG, String dataAtual, String tapCap ){
		boolean send
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessage(configEmail.configureOutLookAuth(host, porta), ramo, segurado, apolice, FINAL_VIG, dataAtual, tapCap);
			send = true
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
			send = false
		}
		return send
	}

	/**
	 * @author T_DCDias
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param texto
	 * @param subject
	 * @param host
	 * @param porta
	 * @return
	 * Envio de e-mail sem anexo
	 */
	public static boolean createEmailSendSantanderAnexos(String sender, String emaildestination, String ramo, String segurado, String apolice,String FINAL_VIG, String dataAtual, String tapCap,String placa, String COD_MARCA_MODELO, String DS_MODELO, String versao, String propostaAnterior, List<String> anexos ){
		boolean send
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		System.err.println('Sender ['+sender+'] Ramo ['+ramo+"] host ["+host+"] port ["+porta+"]")
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessageMutFiles(configEmail.configureOutLookAuth(host, porta), anexos,  ramo, segurado, apolice, FINAL_VIG, placa, COD_MARCA_MODELO, DS_MODELO, versao, propostaAnterior);
			send = true
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
			send = false
		}
		return send
	}


	/**
	 * @author T_DCDias
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param texto
	 * @param subject
	 * @param host
	 * @param porta
	 * @return
	 * Envio de e-mail sem anexo
	 */
	public static boolean createEmailSendSantanderSemAnexos(String sender, String emaildestination, String ramo, String segurado, String apolice,String FINAL_VIG, String dataAtual, String tapCap,String placa, String COD_MARCA_MODELO, String DS_MODELO, String versao){
		boolean send
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		System.err.println('Sender ['+sender+'] Ramo ['+ramo+"] host ["+host+"] port ["+porta+"]")
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessage(configEmail.configureOutLookAuth(host, porta), ramo, segurado, apolice, FINAL_VIG, placa, COD_MARCA_MODELO, DS_MODELO, versao);
			send = true
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
			send = false
		}
		return send
	}

	/**
	 * @author ALZ_SUP54955
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param host
	 * @param porta
	 * @param ramo
	 * @param segurado
	 * @param veiculo
	 * @param placa
	 * @param apolice
	 * @param vigencia
	 * @param anexos
	 * @return
	 */
	public static boolean createEmailSendSantanderAnexos15DA( String sender,String emaildestination, String pwdsender, String host, String porta, String ramo, String segurado, String veiculo, String placa , String apolice, String vigencia, List<String> anexos ){
		boolean send
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination);
			messageFactory.createMessageMutFiles(configEmail.configureOutLook(host, porta), anexos,  ramo, segurado, veiculo, placa, apolice, vigencia);
			send = true
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
			send = false
		}
		return send
	}

	/**
	 * @author ALZ_SUP54955
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param host
	 * @param porta
	 * @param ramo
	 * @param anexos
	 * @param assunto
	 * @return
	 */
	public static boolean createEmailSendVoucherAnexo(String sender, String emaildestination,String pwdsender, String host, String  porta, String ramo, List<String> anexos, String assunto ){
		boolean send
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination);
			messageFactory.createMessageMutFiles(configEmail.configureOutLook(host, porta), anexos,  ramo, assunto);
			send = true
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
			send = false
		}
		return send
	}

	/**
	 * @author T_DCDias
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param texto
	 * @param subject
	 * @param host
	 * @param porta
	 * @return
	 * Envio de e-mail sem anexo
	 */
	public static boolean createEmailCadastroCorretorFilialFisica(String sender, String emaildestination, String ramo, String corretor,String intermediario, String cpfcnpj, String newUser, String pwr,String NOMEACCOUNT, String EMAILACCOUNT,String ddd, String telefone, ddd_account, telefone_account ){
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		boolean send
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination);
			messageFactory.createMessage(configEmail.configureOutLookAuth(host, porta), ramo, corretor, cpfcnpj, newUser, pwr, NOMEACCOUNT, EMAILACCOUNT, intermediario, ddd, telefone, ddd_account, telefone_account);
			send = true
		} catch(Exception e){
			System.err.println("Create Email Cadastro Corretor Filial Fisica FAIL\n"+e);
			send = false
		}
		return send
	}

	/**
	 * @author be54955
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param host
	 * @param porta
	 * @param ramo
	 * @param texto
	 * @return
	 */
	public static boolean createEmailSendSantanderPendencia(String sender, String emaildestination, String ramo, String texto, String assunto){
		boolean send
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination);
			messageFactory.createMessage(configEmail.configureOutLookAuth(host, porta), ramo, texto, assunto);
			send = true
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
			send = false
		}
		System.err.println("email send ["+send+"]");
		return send
	}

	/**
	 * @author be54955
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param host
	 * @param porta
	 * @param ramo
	 * @param texto
	 * @return
	 */
	public static boolean createEmailSendEnvioEnvioRetornoGo(String sender, String emaildestination, String ramo, String texto, String assunto){
		boolean send
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		System.out.println(user+" "+pwdsender+" "+host+" "+porta)
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination);
			messageFactory.createMessage(configEmail.configureOutLookAuth(host, porta), ramo, texto, assunto);
			send = true
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
			send = false
		}
		System.err.println("email send ["+send+"]");
		return send
	}



	/**
	 * @author T_DCDias
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param path1
	 * @param nome1
	 * @param path2
	 * @param nome2
	 * @param texto
	 * @param subject
	 * @param host
	 * @param porta
	 * @return
	 */
	public static createEmailSendMultipleFilesCartasAvisoSantander(String sender, String emaildestination, List<String> anexos , String ramo, String assunto ){
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessageFilesCartasAvisoSantander(configEmail.configureOutLookAuth(host, porta), anexos, ramo, assunto);
		} catch(Exception e){
			System.err.println("Create Email Send Multiple Files FAIL\n"+e);
		}
	}


	public static createEmailSendError(String sender, String emaildestination, List<String> anexos , String host, String  porta, String ramo, String assunto ){
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessageFilesError(configEmail.configureOutLookAuth(host, porta), anexos, ramo, assunto);
		} catch(Exception e){
			System.err.println("Create Email Send Multiple Files FAIL\n"+e);
		}
	}

	public static createEmailSend(String sender, String emaildestination, List<String> anexos , String host, String  porta, String ramo, String assunto ){
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessageFilesError(configEmail.configureOutLookAuth(host, porta), anexos, ramo, assunto);
		} catch(Exception e){
			System.err.println("Create Email Send Multiple Files FAIL\n"+e);
		}
	}

	/**
	 * @author T_DCDias
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param path1
	 * @param nome1
	 * @param path2
	 * @param nome2
	 * @param texto
	 * @param subject
	 * @param host
	 * @param porta
	 * @return
	 */
	public static createEmailSendMultipleFilesAuth(String sender, String emaildestination,String user, String pwdsender, List<String> anexos , String host, String  porta, String ramo ){
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessageFilesAuth(configEmail.configureOutLookAuth(host, porta), anexos, ramo);
		} catch(Exception e){
			System.err.println("Create Email Send Multiple Files FAIL\n"+e);
		}
	}

	/**
	 * @author T_DCDias
	 * @param sender
	 * @param emaildestination
	 * @param pwdsender
	 * @param texto
	 * @param subject
	 * @param host
	 * @param porta
	 * @return
	 * Envio de e-mail sem anexo
	 */
	public static boolean createEmailBanrisul(String ramo, String sender, String emaildestination,String corretor, String email, String telefone, String susep, String proponente, String cpfcnpj, String apolice, String finalVigencia, String nome ){
		boolean send
		//	lerConfiguraçõesAuth(authPathConfigEmail)
		lerConfiguraçõesAuthProp(authPathConfigEmailProp)
		System.out.println('Sender ['+sender+'] Ramo ['+ramo+"] host ["+host+"] port ["+porta+"]")
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,user,pwdsender,emaildestination );
			messageFactory.createMessage(configEmail.configureOutLookAuth(host, porta),ramo, corretor, email, telefone, susep, proponente, cpfcnpj, apolice, finalVigencia, nome);
			send = true
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
			send = false
		}
		return send
	}
}
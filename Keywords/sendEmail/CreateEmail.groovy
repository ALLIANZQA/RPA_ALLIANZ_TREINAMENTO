package sendEmail;


public class CreateEmail {
	
	
	
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
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination );
			messageFactory.createAllMessageFiles(configEmail.configureOutLook(host, porta), anexos, ramo, totalAlert,totalPassed, messagesAlerts,messagesPassed, startHour,endHours,totalHours,hostName,totalRegistros, porcentagem, porcentagemDeAlertas, dataInicio, dataFinal, assunto);
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
	public static createEmailSendMultipleFiles(String sender, String emaildestination,String pwdsender, List<String> anexos , String host, String  porta, String ramo ){
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination );
			messageFactory.createMessageFiles(configEmail.configureOutLook(host, porta), anexos, ramo);
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
	public static boolean createEmailSendMultipleFilesCorretor(String sender, String emaildestination,String pwdsender, List<String> anexos , String host, String  porta, String ramo, String tomador, String apolice ){
		boolean envio
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination );
			messageFactory.createMessageFilesCorretor(configEmail.configureOutLook(host, porta), anexos, ramo, tomador,apolice);
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
	public static createEmailSend(String sender, String emaildestination,String pwdsender, String host, String  porta, String ramo ){
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination);
			messageFactory.createMessage(configEmail.configureOutLook(host, porta), ramo);
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
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
	public static boolean createEmailSendMultipleFilesSeguroFuncionario(String sender, String emaildestination,String pwdsender, List<String> anexos , String host, String  porta, String ramo, String assunto){
		boolean enviado
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination );
			messageFactory.createMessageFilesSeguroFuncionario(configEmail.configureOutLook(host, porta), anexos, ramo, assunto);
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
	public static createEmailSendMultipleFilesReprocessaEmissaoApolice(String sender, String emaildestination,String pwdsender, List<String> anexos , String host, String  porta, String ramo, String dataAnterior){
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination );
			messageFactory.createMessageFilesReprocessaEmissaoApolice(configEmail.configureOutLook(host, porta), anexos, ramo, dataAnterior);
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
	public static createEmailSendMultipleFilesMigracaoDeDados(String sender, String emaildestination,String pwdsender, List<String> anexos , String host, String  porta, String ramo, String msg){
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination );
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
	public static boolean createEmailSendSantander(String sender, String emaildestination,String pwdsender, String host, String  porta, String ramo, String segurado, String apolice,String FINAL_VIG, String dataAtual, String tapCap ){
		boolean send
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination);
			messageFactory.createMessage(configEmail.configureOutLook(host, porta), ramo, segurado, apolice, FINAL_VIG, dataAtual, tapCap);
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
	public static boolean createEmailSendSantanderAnexos(String sender, String emaildestination,String pwdsender, String host, String  porta, String ramo, String segurado, String apolice,String FINAL_VIG, String dataAtual, String tapCap,List<String> anexos ){
		boolean send
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination);
			messageFactory.createMessageMutFiles(configEmail.configureOutLook(host, porta), anexos,  ramo, segurado, apolice, FINAL_VIG, dataAtual, tapCap);
			send = true
		} catch(Exception e){
			System.err.println("FAIL\n"+e);
			send = false
		}
		return send
	}
	
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
	public static boolean createEmailCadastroCorretorFilialFisica(String sender, String emaildestination, String pwdsender, String host, String  porta, String ramo, String corretor,String intermediario, String cpfcnpj, String user, String pwr,String NOMEACCOUNT, String EMAILACCOUNT,String ddd, String telefone, ddd_account, telefone_account ){

		boolean send
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination);
			messageFactory.createMessage(configEmail.configureOutLook(host, porta), ramo, corretor, cpfcnpj, user, pwr, NOMEACCOUNT, EMAILACCOUNT, intermediario, ddd, telefone, ddd_account, telefone_account);
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
	public static boolean createEmailSendSantanderPendencia(String sender, String emaildestination,String pwdsender, String host, String  porta, String ramo, String texto, String assunto){
		boolean send
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination);
			messageFactory.createMessage(configEmail.configureOutLook(host, porta), ramo, texto, assunto);
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
	public static boolean createEmailSendEnvioEnvioRetornoGo(String sender, String emaildestination,String pwdsender, String host, String  porta, String ramo, String texto, String assunto){
		boolean send
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination);
			messageFactory.createMessage(configEmail.configureOutLook(host, porta), ramo, texto, assunto);
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
	public static createEmailSendMultipleFilesCartasAvisoSantander(String sender, String emaildestination,String pwdsender, List<String> anexos , String host, String  porta, String ramo, String assunto ){
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination );
			messageFactory.createMessageFilesCartasAvisoSantander(configEmail.configureOutLook(host, porta), anexos, ramo, assunto);
		} catch(Exception e){
			System.err.println("Create Email Send Multiple Files FAIL\n"+e);
		}
	}


	public static createEmailSendError(String sender, String emaildestination,String pwdsender, List<String> anexos , String host, String  porta, String ramo, String assunto ){
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination );
			messageFactory.createMessageFilesError(configEmail.configureOutLook(host, porta), anexos, ramo, assunto);
		} catch(Exception e){
			System.err.println("Create Email Send Multiple Files FAIL\n"+e);
		}
	}

	public static createEmailSend(String sender, String emaildestination,String pwdsender, List<String> anexos , String host, String  porta, String ramo, String assunto ){
		try{
			ConfigOutlook configEmail = new ConfigOutlook();
			MessageFactory messageFactory = new MessageFactory(sender,pwdsender,emaildestination );
			messageFactory.createMessageFilesError(configEmail.configureOutLook(host, porta), anexos, ramo, assunto);
		} catch(Exception e){
			System.err.println("Create Email Send Multiple Files FAIL\n"+e);
		}
	}
}
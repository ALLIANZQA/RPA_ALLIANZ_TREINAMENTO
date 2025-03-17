/*package webservice
 import javax.xml.bind.JAXBContext
 import javax.xml.bind.Unmarshaller
 import javax.xml.soap.MessageFactory
 import javax.xml.soap.SOAPMessage
 import javax.xml.stream.XMLInputFactory;
 import org.w3c.dom.*;
 import com.google.common.base.Charsets;
 import com.google.common.io.ByteSource;
 import bean.CasoDeTeste
 import bean.Cobertura
 import bean.Pacote
 import bean.Proposta
 import bean.Mensagem
 public class CotacaoWebService extends AutoComandos{
 //	private static final String configFileFull = 'C:/Users/Public/QA_ALLIANZ/xml/pacotesfull.xml'
 //	private static final String requestDominios = 'C:/Users/Public/QA_ALLIANZ/xml/dominios.xml'
 *//**
 * Criar outro metodo em  MessagesFactory para receber o xml response e retornar um objeto do tipo 'SOAPMessage'
 *//*
 //	private static SOAPMessage message = MessagesFactory.messageFactory(configFileFull)
 *//**
 * @author T_DCDias
 * @return
 * Retorna lista de mensagens
 *//*
 public static List<Mensagem> pegarMensagens(SOAPMessage message){
 try{
 int numberNodes = getNumberOfNodes(message,"mensagem")
 //System.err.println("Number of Nodes by mensagem ["+numberNodes+"]\n")
 List<Mensagem> mensagems = new ArrayList<Mensagem>()
 for(int i = 0; i<numberNodes; i++){
 Mensagem mensagem = new Mensagem()
 mensagem.setCodigo(getNodeValue(message, i,"codigo"))
 mensagem.setTipoMensagem(getNodeValue(message, i,"tipoMensagem"))
 mensagem.setDescricao(getNodeValue(message, i,"descricao"))
 mensagems.add(mensagem)
 }
 return mensagems
 } catch (IOException e){
 throw new RuntimeException()
 }
 }
 *//**
 * @author T_DCDias
 * @return
 * Retorna lista de mensagens
 *//*
 public static List<Mensagem> pegarMensagensFault(SOAPMessage message){
 try{
 int numberNodes = getNumberOfNodes(message,"soapenv:Fault")
 //System.err.println("Number of Nodes by FAULT ["+numberNodes+"]\n")
 List<Mensagem> mensagems = new ArrayList<Mensagem>()
 for(int i = 0; i<numberNodes; i++){
 Mensagem mensagem = new Mensagem()
 mensagem.setCodigo(getNodeValue(message, i,"faultcode"))
 mensagem.setTipoMensagem(getNodeValue(message, i,"faultstring"))
 mensagem.setDescricao(getNodeValue(message, i,"detail"))
 mensagems.add(mensagem)
 System.err.println(getNodeValue(message, i,"faultstring"));
 }
 return mensagems
 } catch (IOException e){
 throw new RuntimeException()
 }
 }
 *//**
 * @author Inmetrics
 * @return
 * Retorna lista com todos os pacotes do XML
 *//*
 public static List<Pacote> pegarPacotes(SOAPMessage message){
 try{
 int numberNodes = getNumberOfNodes(message,"pacote")
 System.err.println("Number of Nodes by pacote ["+numberNodes+"]\n")
 List<Pacote> pacotes = new ArrayList<Pacote>()
 for(int i = 0; i<numberNodes; i++){
 Pacote pacote = new Pacote()
 pacote.setCodigoPacote(getNodeValue(message, i,"codigoPacote"))
 pacote.setDescricaoPacote(getNodeValue(message, i,"descricaoPacote"))
 pacote.setFormaPagamento(getNodeValue(message, i,"formaPagamento"))
 pacote.setQuantidadeParcelas(getNodeValue(message, i,"quantidadeParcelas"))
 pacote.setValorPrimeiraParcela(getNodeValue(message, i,"valorPrimeiraParcela"))
 pacote.setValorTotalParcelado(getNodeValue(message, i,"valorTotalParcelado"))
 pacote.setValorTotalParcelaUnica(getNodeValue(message, i,"valorTotalParcelaUnica"))
 pacote.setValorIof(getNodeValue(message, i,"valorIof"))
 pacote.setValorAdicionalFracionamento(getNodeValue(message, i,"valorAdicionalFracionamento"))
 pacotes.add(pacote)
 }
 return pacotes
 } catch (IOException e){
 throw new RuntimeException()
 }
 }
 *//**
 * @author T_DCDias
 * @return
 * Retorna lista de coberturas
 *//*
 public static List<Cobertura> pegarCoberturas(SOAPMessage message){
 try{
 int numberNodes = getNumberOfNodes(message,"cobertura")
 //System.err.println("Number of Nodes by cobertura ["+numberNodes+"]\n")
 List<Cobertura> coberturas = new ArrayList<Cobertura>()
 for(int i = 0; i<numberNodes; i++){
 Cobertura cobertura = new Cobertura()
 cobertura.setCodigoCobertura(getNodeValue(message, i,"codigoCobertura"))
 cobertura.setDescricaoCobertura(getNodeValue(message, i,"descricaoCobertura"))
 cobertura.setValorPremio(getNodeValue(message, i,"valorPremio"))
 cobertura.setValorFranquia(getNodeValue(message, i,"valorFranquia"))
 cobertura.setValorLMI(getNodeValue(message, i,"valorLMI"))
 coberturas.add(cobertura)
 }
 return coberturas
 } catch (IOException e){
 throw new RuntimeException()
 }
 }
 *//**
 * @author T_DCDias
 * @return
 * Retorna lista de Pacores e Coberturas 
 *//*
 public static List<Cobertura> pegarPacotesCoberturas(SOAPMessage message){
 try{
 int numberNodes = getNumberOfNodes(message,"pacote")
 //System.err.println("Number of Nodes by pacote ["+numberNodes+"]\n")
 List<Pacote> pacotes = new ArrayList<Pacote>()
 for(int i = 0; i<numberNodes; i++){
 Pacote pacote = new Pacote()
 pacote.setCodigoPacote(getNodeValue(message, i,"codigoPacote"))
 pacote.setDescricaoPacote(getNodeValue(message, i,"descricaoPacote"))
 pacote.setFormaPagamento(getNodeValue(message, i,"formaPagamento"))
 pacote.setQuantidadeParcelas(getNodeValue(message, i,"quantidadeParcelas"))
 pacote.setValorPrimeiraParcela(getNodeValue(message, i,"valorPrimeiraParcela"))
 pacote.setValorTotalParcelado(getNodeValue(message, i,"valorTotalParcelado"))
 pacote.setValorTotalParcelaUnica(getNodeValue(message, i,"valorTotalParcelaUnica"))
 pacote.setValorIof(getNodeValue(message, i,"valorIof"))
 pacote.setValorAdicionalFracionamento(getNodeValue(message, i,"valorAdicionalFracionamento"))
 pacote.setCodigoCobertura(getNodeValue(message, i,"codigoCobertura"))
 pacote.setDescricaoCobertura(getNodeValue(message, i,"descricaoCobertura"))
 pacote.setValorPremio(getNodeValue(message, i,"valorPremio"))
 pacote.setValorFranquia(getNodeValue(message, i,"valorFranquia"))
 pacote.setValorLMI(getNodeValue(message, i,"valorLMI"))
 pacote.setCodigoCobertura2(getNodeValue(message, i+1,"codigoCobertura"))
 pacote.setDescricaoCobertura2(getNodeValue(message, i+1,"descricaoCobertura"))
 pacote.setValorPremio2(getNodeValue(message, i+1,"valorPremio"))
 pacote.setValorFranquia2(getNodeValue(message, i+1,"valorFranquia"))
 pacote.setValorLMI2(getNodeValue(message, i+1,"valorLMI"))
 pacote.setCodigoCobertura3(getNodeValue(message, i+2,"codigoCobertura"))
 pacote.setDescricaoCobertura3(getNodeValue(message, i+2,"descricaoCobertura"))
 pacote.setValorPremio3(getNodeValue(message, i+2,"valorPremio"))
 pacote.setValorFranquia3(getNodeValue(message, i+2,"valorFranquia"))
 pacote.setValorLMI3(getNodeValue(message, i+2,"valorLMI"))
 pacote.setCodigoCobertura4(getNodeValue(message, i+3,"codigoCobertura"))
 pacote.setDescricaoCobertura4(getNodeValue(message, i+3,"descricaoCobertura"))
 pacote.setValorPremio4(getNodeValue(message, i+3,"valorPremio"))
 pacote.setValorFranquia4(getNodeValue(message, i+3,"valorFranquia"))
 pacote.setValorLMI4(getNodeValue(message, i+3,"valorLMI"))
 pacote.setCodigoCobertura5(getNodeValue(message, i+4,"codigoCobertura"))
 pacote.setDescricaoCobertura5(getNodeValue(message, i+4,"descricaoCobertura"))
 pacote.setValorPremio5(getNodeValue(message, i+4,"valorPremio"))
 pacote.setValorFranquia5(getNodeValue(message, i+4,"valorFranquia"))
 pacote.setValorLMI5(getNodeValue(message, i+4,"valorLMI"))
 pacote.setCodigoCobertura6(getNodeValue(message, i+5,"codigoCobertura"))
 pacote.setDescricaoCobertura6(getNodeValue(message, i+5,"descricaoCobertura"))
 pacote.setValorPremio6(getNodeValue(message, i+5,"valorPremio"))
 pacote.setValorFranquia6(getNodeValue(message, i+5,"valorFranquia"))
 pacote.setValorLMI6(getNodeValue(message, i+5,"valorLMI"))
 pacotes.add(pacote)
 }
 return pacotes
 } catch (IOException e){
 throw new RuntimeException()
 }
 }
 *//**
 * @author Inmetrics
 * @param message
 * @param iten
 * @param tagName
 * @return
 * Retorna o valor no Nó, infoormando a mensagem, numero do nó(item) e tagName
 *//*
 public static String getNodeValue(SOAPMessage message, int item, String tagName){
 String nodeValue
 NodeList nodes = message.getSOAPBody().getElementsByTagName(tagName)
 Node node = nodes.item(item)
 return nodeValue =  node != null ? node.getTextContent() : "";
 }
 *//**
 * @author Inmetrics
 * @param message
 * @param tagName
 * @return
 *//*
 public static int getNumberOfNodes(SOAPMessage message, String tagName){
 NodeList nodes = message.getSOAPBody().getElementsByTagName(tagName)
 return nodes.length
 }
 *//**
 * @author Inmetrics
 * @return
 * Teste
 *//*
 public static List<Pacote> carregaPacotes(SOAPMessage message){
 List<Pacote> pacotes = pegarPacotes(message)
 System.err.println("Lista de Pacotes\n")
 for(int i = 0; i < pacotes.size(); i++){
 System.err.println("Cod. Pacote ["+pacotes[i].getCodigoPacote()
 +"] Descricao do Pacote ["+pacotes[i].getDescricaoPacote()
 +"] Forma de Pagamento ["+pacotes[i].getFormaPagamento()
 +"] Quantidade de Parcelas ["+pacotes[i].getQuantidadeParcelas()
 +"] Valor Primeira Parcela ["+pacotes[i].getValorPrimeiraParcela()
 +"] Valor Total Parcelado ["+pacotes[i].getValorTotalParcelado()
 +"] Valor Total Parcela Unica ["+pacotes[i].getValorTotalParcelaUnica()
 +"] Valor Iof ["+pacotes[i].getValorIof()
 +"] Valor Adicional Fracionamento ["+pacotes[i].getValorAdicionalFracionamento()
 +"]")
 }
 return pacotes;
 }
 *//**
 * @author Inmetrics
 * @return
 * Teste
 *//*
 public List<Pacote>carregaPacotesReturnList(SOAPMessage message){
 List<Pacote> pacotes = pegarPacotes(message)
 System.err.println("Lista de Pacotes\n")
 for(int i = 0; i < pacotes.size(); i++){
 System.err.println("Cod. Pacote ["+pacotes[i].getCodigoPacote()
 +"] Descricao do Pacote ["+pacotes[i].getDescricaoPacote()
 +"] Forma de Pagamento ["+pacotes[i].getFormaPagamento()
 +"] Quantidade de Parcelas ["+pacotes[i].getQuantidadeParcelas()
 +"] Valor Primeira Parcela ["+pacotes[i].getValorPrimeiraParcela()
 +"] Valor Total Parcelado ["+pacotes[i].getValorTotalParcelado()
 +"] Valor Total Parcela Unica ["+pacotes[i].getValorTotalParcelaUnica()
 +"] Valor Iof ["+pacotes[i].getValorIof()
 +"] Valor Adicional Fracionamento ["+pacotes[i].getValorAdicionalFracionamento()
 +"]")
 }
 return pacotes;
 }
 *//**
 * @return
 * Teste
 *//*
 public static carregaMensagens(SOAPMessage message){
 List<Mensagem> mensagens = pegarMensagens(message)
 for(int i=0 ; i<mensagens.size();i++){
 System.err.println("Codigo ["+mensagens[i].getCodigo()
 +"] Tipo Mnesagem ["+mensagens[i].getTipoMensagem()
 +"] Descrição ["+mensagens[i].getDescricao()+"]")
 }
 }
 public static adicionaPdfMensagemErroResponse(SOAPMessage message){
 List<Mensagem> mensagens = pegarMensagens(message)
 for(int i=0 ; i<mensagens.size();i++){
 adicionaLinhaTextoNegrito("Codigo ["+mensagens[i].getCodigo()
 +"] Tipo Mensagem ["+mensagens[i].getTipoMensagem()
 +"] Descrição ["+mensagens[i].getDescricao()+"]");
 }
 }
 }
 //
 //	/**
 //	 * @author T_DCDias
 //	 * @return
 //	 * Retorna lista de mensagens
 //	 */
//	public static List<Mensagem> pegarMensagens(){
//		try{
//			int numberNodes = getNumberOfNodes(message,"mensagem")
//			System.err.println("Number of Nodes by mensagem ["+numberNodes+"]\n")
//			List<Mensagem> mensagems = new ArrayList<Mensagem>()
//			for(int i = 0; i<numberNodes; i++){
//				Mensagem mensagem = new Mensagem()
//				mensagem.setCodigo(getNodeValue(message, i,"codigo"))
//				mensagem.setTipoMensagem(getNodeValue(message, i,"tipoMensagem"))
//				mensagem.setDescricao(getNodeValue(message, i,"descricao"))
//				mensagems.add(mensagem)
//			}
//			return mensagems
//		} catch (IOException e){
//			throw new RuntimeException()
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 * Retorna lista de coberturas
//	 */
//	public static List<Cobertura> pegarCoberturas(){
//		try{
//			int numberNodes = getNumberOfNodes(message,"cobertura")
//			System.err.println("Number of Nodes by cobertura ["+numberNodes+"]\n")
//			List<Cobertura> coberturas = new ArrayList<Cobertura>()
//			for(int i = 0; i<numberNodes; i++){
//				Cobertura cobertura = new Cobertura()
//				cobertura.setCodigoCobertura(getNodeValue(message, i,"codigoCobertura"))
//				cobertura.setDescricaoCobertura(getNodeValue(message, i,"descricaoCobertura"))
//				cobertura.setValorPremio(getNodeValue(message, i,"valorPremio"))
//				cobertura.setValorFranquia(getNodeValue(message, i,"valorFranquia"))
//				cobertura.setValorLMI(getNodeValue(message, i,"valorLMI"))
//				coberturas.add(cobertura)
//			}
//			return coberturas
//		} catch (IOException e){
//			throw new RuntimeException()
//		}
//	}

//	/**
//	 * @author Inmetrics
//	 * @return
//	 * Retorna lista com todos os pacotes do XML
//	 */
//	public static List<Pacote> pegarPacotes(){
//		try{
//			int numberNodes = getNumberOfNodes(message,"pacote")
//			System.err.println("Number of Nodes by pacote ["+numberNodes+"]\n")
//			List<Pacote> pacotes = new ArrayList<Pacote>()
//			for(int i = 0; i<numberNodes; i++){
//				Pacote pacote = new Pacote()
//				pacote.setCodigoPacote(getNodeValue(message, i,"codigoPacote"))
//				pacote.setDescricaoPacote(getNodeValue(message, i,"descricaoPacote"))
//				pacote.setFormaPagamento(getNodeValue(message, i,"formaPagamento"))
//				pacote.setQuantidadeParcelas(getNodeValue(message, i,"quantidadeParcelas"))
//				pacote.setValorPrimeiraParcela(getNodeValue(message, i,"valorPrimeiraParcela"))
//				pacote.setValorTotalParcelado(getNodeValue(message, i,"valorTotalParcelado"))
//				pacote.setValorTotalParcelaUnica(getNodeValue(message, i,"valorTotalParcelaUnica"))
//				pacote.setValorIof(getNodeValue(message, i,"valorIof"))
//				pacote.setValorAdicionalFracionamento(getNodeValue(message, i,"valorAdicionalFracionamento"))
//				pacotes.add(pacote)
//			}
//			return pacotes
//		} catch (IOException e){
//			throw new RuntimeException()
//		}
//	}





//
//	/**
//	 * @author Inmetrics
//	 * @return
//	 * Teste
//	 */
//	public static carregaPacotes(){
//		List<Pacote> pacotes = pegarPacotes()
//		System.err.println("Lista de Pacotes\n")
//		for(int i = 0; i < pacotes.size(); i++){
//			System.err.println("Cod. Pacote ["+pacotes[i].getCodigoPacote()
//					+"] Descricao do Pacote ["+pacotes[i].getDescricaoPacote()
//					+"] Forma de Pagamento ["+pacotes[i].getFormaPagamento()
//					+"] Quantidade de Parcelas ["+pacotes[i].getQuantidadeParcelas()
//					+"] Valor Primeira Parcela ["+pacotes[i].getValorPrimeiraParcela()
//					+"] Valor Total Parcelado ["+pacotes[i].getValorTotalParcelado()
//					+"] Valor Total Parcela Unica ["+pacotes[i].getValorTotalParcelaUnica()
//					+"] Valor Iof ["+pacotes[i].getValorIof()
//					+"] Valor Adicional Fracionamento ["+pacotes[i].getValorAdicionalFracionamento()
//					+"]")
//		}
//	}




//	/**
//	 * @author Inmetrics
//	 * @return
//	 * Teste
//	 */
//	public static carregaCoberturas(){
//		List<Cobertura> coberturas = pegarCoberturas()
//		System.err.println("Lista de Coberturas\n")
//		for(int i=0; i<coberturas.size();i++){
//			System.err.println("Cod. Pacote ["+coberturas[i].getCodigoPacote()
//					+"] Descricao do Pacote ["+coberturas[i].getDescricaoPacote()
//					+"] Codigo Cobertura ["+coberturas[i].getCodigoCobertura()
//					+"] Descricao Cobertura ["+coberturas[i].getDescricaoCobertura()
//					+"] Valor Premio ["+coberturas[i].getValorPremio()
//					+"] Valor Franquia ["+coberturas[i].getValorFranquia()
//					+"] valor LMI ["+coberturas[i].getValorLMI()
//					+"]")
//		}
//	}
//
//	/**
//	 * @author T_DCDias
//	 * @return
//	 * Teste
//	 */
//	public static carregaMensagens(){
//		List<Mensagem> mensagens = pegarMensagens()
//		for(int i=0 ; i<mensagens.size();i++){
//			System.err.println("Codigo ["+mensagens[i].getCodigo()
//					+"] Tipo Mnesagem ["+mensagens[i].getTipoMensagem()
//					+"] Descrição ["+mensagens[i].getDescricao()+"]")
//		}
//	}


package auxiliares

import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format
import org.jdom2.output.XMLOutputter;

import bean.CasoDeTeste
import java.text.SimpleDateFormat

public class CreateXMLRadarLive {

	public static createXmlRadarLiveRequest(CasoDeTeste casoDeTeste, String pathEvidencia){

		//CasoDeTeste casoDeTeste = casoDeTeste;
		Document doc = new Document();
		//Namespace ns1 = Namespace.getNamespace("urn:iso:std:iso:20022:tech:xsd:pain.001.001.03");
		Namespace ns1 = Namespace.getNamespace("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
		Namespace ws = Namespace.getNamespace("ws", "http://drswbo05.allianz.com.br/V1_00_00/ws");
		Namespace sch = Namespace.getNamespace("sch", "http://drswbo05.allianz.com.br/V1_00_00/schemas");
		Element root = new Element("Envelope", ns1);
		root.addNamespaceDeclaration(ws);
		root.addNamespaceDeclaration(sch);


		Element body = new Element("Body", ns1);

		// CotacaoRequest
		Element cotacaoRequest = new Element("cotacaoRequest",ws);
		body.addContent(cotacaoRequest);

		// Autenticacao
		Element autenticacao = new Element("autenticacao", sch);
		Element usuario = new Element("usuario", sch);
		Element parceiro = new Element("parceiro", sch);
		Element mediador = new Element("mediador", sch);

		usuario.setText(casoDeTeste.getUsuario());
		parceiro.setText(casoDeTeste.getParceiro());
		mediador.setText(casoDeTeste.getMediador());

		autenticacao.addContent(usuario);
		autenticacao.addContent(parceiro);
		autenticacao.addContent(mediador);

		cotacaoRequest.addContent(autenticacao);

		//Dados Fiscais
		Element dadosFiscais = new Element("dadosFiscais", sch);
		cotacaoRequest.addContent(dadosFiscais);

		Element isentoIOF = new Element("isentoIOF", sch);
		isentoIOF.setText(casoDeTeste.getIsentoIOF());
		dadosFiscais.addContent(isentoIOF);

		//Dados Gerais
		Element dadosGerais = new Element("dadosGerais", sch);
		Element dataInicio = new Element("dataInicio", sch);
		Element percentualComissao = new Element("percentualComissao", sch);
		Element agravoDesconto = new Element("agravoDesconto", sch);
		Element percentualDesconto = new Element("percentualDesconto", sch);
		Element percentualAgravo = new Element("percentualAgravo", sch);

		dataInicio.setText(casoDeTeste.getDataInicio());
		percentualComissao.setText(casoDeTeste.getPercentualComissao());

		if(casoDeTeste.getPercentualAgravo() != "0"){
			percentualAgravo.setText(casoDeTeste.getPercentualAgravo());
			agravoDesconto.addContent(percentualAgravo);
		}else{
			percentualDesconto.setText(casoDeTeste.getPercentualDesconto());
			agravoDesconto.addContent(percentualDesconto);
		}

		dadosGerais.addContent(dataInicio);
		dadosGerais.addContent(percentualComissao);
		dadosGerais.addContent(agravoDesconto);

		cotacaoRequest.addContent(dadosGerais);

		//Nó DADOS DE RISCO
		Element dadosRisco = new Element("dadosRisco", sch);
		Element nomeTomador = new Element("nomeTomador", sch);
		Element documentoTomador = new Element("documentoTomador", sch);
		Element nomeCondutor = new Element("nomeCondutor", sch);
		Element documentoCondutor = new Element("documentoCondutor", sch);
		Element dataNascimento = new Element("dataNascimento", sch);

		nomeTomador.setText(casoDeTeste.getNomeTomador());
		documentoTomador.setText(casoDeTeste.getDocumentoTomador());
		nomeCondutor.setText(casoDeTeste.getNomeCondutor());

		documentoCondutor.setText(casoDeTeste.getDocumentoCondutor());

		dataNascimento.setText(auxiliares.ExtractDates.dateFormatteTypes("dd/MM/yyyy", "yyyy-MM-dd",casoDeTeste.getDataNascimento()));

		dadosRisco.addContent(nomeTomador);
		dadosRisco.addContent(documentoTomador);
		dadosRisco.addContent(nomeCondutor);
		dadosRisco.addContent(documentoCondutor);
		dadosRisco.addContent(dataNascimento);

		cotacaoRequest.addContent(dadosRisco);

		//Nó DADOS DO VEÍCULO
		Element veiculo = new Element("veiculo", sch);
		Element chassi = new Element("chassi", sch);
		Element anoModelo = new Element("anoModelo", sch);
		Element situacaoVeiculo = new Element("situacaoVeiculo", sch);
		Element usado = new Element("usado", sch);
		Element zeroKm = new Element("zeroKm", sch);
		Element dataNotaFiscal = new Element("dataNotaFiscal", sch);
		Element isVeiculoUsado = new Element("isVeiculoUsado", sch);
		Element placa = new Element("placa", sch);
		Element codigoMarcaModelo = new Element("codigoMarcaModelo", sch);
		Element codigoFipe = new Element("codigoFipe", sch);
		Element percentualFipe = new Element("percentualFipe", sch);
		Element cepPernoite = new Element("cepPernoite", sch);
		Element tipoFranquia = new Element("tipoFranquia", sch);
		Element dispositivoAtual = new Element("dispositivoAtual", sch);
		Element veiculoBlindado = new Element("veiculoBlindado", sch);
		Element despesasExtraordinarias = new Element("despesasExtraordinarias", sch);

		chassi.setText(casoDeTeste.getChassi());
		anoModelo.setText(casoDeTeste.getAnoModelo());
		isVeiculoUsado.setText(casoDeTeste.getIsVeiculoUsado());
		placa.setText(casoDeTeste.getPlaca());
		codigoMarcaModelo.setText(casoDeTeste.getCodigoMarcaModelo());
		codigoFipe.setText(casoDeTeste.getCodigoFipe());
		percentualFipe.setText(casoDeTeste.getPercentualFipe());
		cepPernoite.setText(casoDeTeste.getCepPernoite());
		tipoFranquia.setText(casoDeTeste.getTipoFranquia());
		dispositivoAtual.setText(casoDeTeste.getDispositivoAtual());
		veiculoBlindado.setText(casoDeTeste.getVeiculoBlindado());
		despesasExtraordinarias.setText(casoDeTeste.getDespesasExtraordinarias());

		if(casoDeTeste.getIsVeiculoUsado().contentEquals("false")){
			dataNotaFiscal.setText(casoDeTeste.getDataInicio());
			zeroKm.addContent(dataNotaFiscal);
			situacaoVeiculo.addContent(zeroKm);
		}else{
			usado.addContent(isVeiculoUsado);
			usado.addContent(placa);
			situacaoVeiculo.addContent(usado);
		}

		veiculo.addContent(chassi);
		veiculo.addContent(anoModelo);
		veiculo.addContent(situacaoVeiculo);
		veiculo.addContent(codigoMarcaModelo);
		veiculo.addContent(codigoFipe);
		veiculo.addContent(percentualFipe);
		veiculo.addContent(cepPernoite);
		veiculo.addContent(tipoFranquia);
		veiculo.addContent(dispositivoAtual);
		veiculo.addContent(veiculoBlindado);
		veiculo.addContent(despesasExtraordinarias);

		cotacaoRequest.addContent(veiculo);
		
		//renovacaoExterna
		Element renovacao = new Element("renovacao", sch);
		Element renovacaoExterna = new Element("renovacaoExterna", sch);
		Element renovacaoComSinistro = new Element("renovacaoComSinistro", sch);
		Element classeBonus = new Element("classeBonus", sch);
		Element veicIgualApolAnt = new Element("veicIgualApolAnt", sch);
		Element codSeguradora = new Element("codSeguradora", sch);
		Element filial = new Element("filial", sch);
		Element ramoRenovacao = new Element("ramoRenovacao", sch);
		Element numApolice = new Element("numApolice", sch);
		Element numItem = new Element("numItem", sch);
		Element numCI = new Element("numCI", sch);
		Element dataFimVigencia = new Element("dataFimVigencia", sch);
		
		renovacaoComSinistro.setText(casoDeTeste.getRenovacaoComSinistro());
		classeBonus.setText(casoDeTeste.getClasseBonus());
		veicIgualApolAnt.setText(casoDeTeste.getVeicIgualApolAnt());
		codSeguradora.setText(casoDeTeste.getCodSeguradora());
		filial.setText(casoDeTeste.getFilial());
		ramoRenovacao.setText(casoDeTeste.getRamoRenovacao());
		numApolice.setText(casoDeTeste.getNumApolice());
		numItem.setText(casoDeTeste.getNumItem());
		numCI.setText(casoDeTeste.getNumCI());
		dataFimVigencia.setText(casoDeTeste.getDataFimVigencia());
		
		renovacaoExterna.addContent(renovacaoComSinistro);
		renovacaoExterna.addContent(classeBonus);
		renovacaoExterna.addContent(veicIgualApolAnt);
		renovacaoExterna.addContent(codSeguradora);
		renovacaoExterna.addContent(filial);
		renovacaoExterna.addContent(ramoRenovacao);
		renovacaoExterna.addContent(numApolice);
		renovacaoExterna.addContent(numItem);
		renovacaoExterna.addContent(numCI);
		renovacaoExterna.addContent(dataFimVigencia);

		renovacao.addContent(renovacaoExterna);
		cotacaoRequest.addContent(renovacao);
		
		//Nó DADOS DO RAMO
		Element ramo = new Element("ramo", sch);
		Element auto = new Element("auto", sch);
		Element categoriaRisco = new Element("categoriaRisco", sch);
		Element questionario = new Element("questionario", sch);
		Element sexo = new Element("sexo", sch);
		Element estadoCivil = new Element("estadoCivil", sch);
		Element tempoHabilitacao = new Element("tempoHabilitacao", sch);
		Element existeMenor25anos = new Element("existeMenor25anos", sch);
		Element garagemFaculdade = new Element("garagemFaculdade", sch);
		Element garagemResidencia = new Element("garagemResidencia", sch);
		Element garagemTrabalho = new Element("garagemTrabalho", sch);
		Element condutorPrincipalResideEm = new Element("condutorPrincipalResideEm", sch);

		categoriaRisco.setText(casoDeTeste.getCategoriaRisco());
		sexo.setText(casoDeTeste.getSexo());
		estadoCivil.setText(casoDeTeste.getEstadoCivil());
		tempoHabilitacao.setText(casoDeTeste.getTempoHabilitacao());
		existeMenor25anos.setText(casoDeTeste.getExisteMenor25anos());
		garagemFaculdade.setText(casoDeTeste.getGaragemFaculdade());
		garagemResidencia.setText(casoDeTeste.getGaragemResidencia());
		garagemTrabalho.setText(casoDeTeste.getGaragemTrabalho());
		condutorPrincipalResideEm.setText(casoDeTeste.getCondutorPrincipalResideEm());

		ramo.addContent(auto);
		auto.addContent(categoriaRisco);
		auto.addContent(questionario);

		questionario.addContent(sexo);
		questionario.addContent(estadoCivil);
		questionario.addContent(tempoHabilitacao);
		questionario.addContent(existeMenor25anos);
		questionario.addContent(garagemFaculdade);
		questionario.addContent(garagemResidencia);
		questionario.addContent(garagemTrabalho);
		questionario.addContent(condutorPrincipalResideEm);

		cotacaoRequest.addContent(ramo);


		root.addContent(body);
		doc.setRootElement(root);

		XMLOutputter xout = new XMLOutputter();
		try {
			Format f = Format.getPrettyFormat();
			f.setEncoding("UTF-8");
			f.setOmitDeclaration(true);
			xout.setFormat(f);

			OutputStream out = new FileOutputStream(new File(pathEvidencia+retornaNomeArquivoDataHoraXml(casoDeTeste.getCt())));
			xout.output(doc, out);
			System.out.println("[GERADO BODY REQUEST XML] - CENÁRIO DE TESTE ["+casoDeTeste.getCt()+"]");
		} catch (IOException e) {

			e.printStackTrace();
		}

		return 	xout.outputString(doc);

	}

	public static String retornaNomeArquivoDataHoraXml(String ct){
		Date dataHoraAtual = new Date();
		String data = new SimpleDateFormat("ddMMyyyy").format(dataHoraAtual);
		String hora = new SimpleDateFormat("HHmmss").format(dataHoraAtual);
		String nomeArquivoFormatado = ct+"_REQUEST_"+"_"+data+"_"+hora+".xml"
		return nomeArquivoFormatado;
	}

	public static String toString(Document doc) {
		try {
			StringWriter sw = new StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			transformer.transform(new DOMSource(doc), new StreamResult(sw));
			return sw.toString();
		} catch (Exception ex) {
			throw new RuntimeException("Error converting to String", ex);
		}
	}


	public static String getStringFromDocument(Document doc) throws TransformerException {
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.transform(domSource, result);
		return writer.toString();
	}

}


package bean

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

public class CasoDeTeste {

	private  String usuario
	private  String senha
	private  String descricaoCasoDeTeste;
	private  String usuario2
	private  String senha2
	private  String ct
	private  String cpfcnpj
	private  String cep
	private  String estadoCivil
	private  String placa
	private  String residenciaCondutor
	private  String chassis
	private  String formaDePagamento;
	private  String parcela;
	private  String valorPacote;
	private  String pacote;
	private  String valorSeguro;
	private  String apolice
	private  String cotacao
	private  String classeDeBonus
	private  String cenario
	private  String executar
	private  String numeroci
	private  String blindagem
	private  String numCpfCondutor
	private  String nomeCpfCondutor
	private  String proposta

	//Atributos do RadarLiveWS
	//private String ct;

	private String parceiro;
	private String mediador;
	private String isentoIOF;
	private String dataNascimentoSegurado;
	private String dataInicio;
	private String percentualComissao;
	private String percentualDesconto;
	private String percentualAgravo;
	private String nomeTomador;
	private String documentoTomador;
	private String nomeCondutor;
	private String documentoCondutor;
	private String dataNascimento;
	private String chassi;
	private String anoModelo;
	private String isVeiculoUsado;

	private String codigoMarcaModelo;
	private String codigoFipe;
	private String percentualFipe;
	private String cepPernoite;
	private String tipoFranquia;
	private String dispositivoAtual;
	private String veiculoBlindado;
	private String despesasExtraordinarias;
	private String categoriaRisco;
	private String sexo;

	private String tempoHabilitacao;
	private String existeMenor25anos;
	private String garagemFaculdade;
	private String garagemResidencia;
	private String garagemTrabalho;
	private String condutorPrincipalResideEm;
	
	private String renovacaoComSinistro;      
	private String classeBonus;               
	private String veicIgualApolAnt;          
	private String codSeguradora;             
	private String filial;                    
	private String ramoRenovacao;             
	private String numApolice;                
	private String numItem;                   
	private String numCI;                    
	private String dataFimVigencia;      
	    
	
	// ===================





	// Novos Metodos




	public String getDescricaoCasoDeTeste() {
		return descricaoCasoDeTeste;
	}

	public void setDescricaoCasoDeTeste(String descricaoCasoDeTeste) {
		this.descricaoCasoDeTeste = descricaoCasoDeTeste;
	}

	public String getPercentualAgravo() {
		return percentualAgravo;
	}

	public void setPercentualAgravo(String percentualAgravo) {
		this.percentualAgravo = percentualAgravo;
	}

	public String getParceiro() {
		return parceiro;
	}

	public void setParceiro(String parceiro) {
		this.parceiro = parceiro;
	}

	public String getMediador() {
		return mediador;
	}

	public void setMediador(String mediador) {
		this.mediador = mediador;
	}

	public String getIsentoIOF() {
		return isentoIOF;
	}

	public void setIsentoIOF(String isentoIOF) {
		this.isentoIOF = isentoIOF;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getPercentualComissao() {
		return percentualComissao;
	}

	public void setPercentualComissao(String percentualComissao) {
		this.percentualComissao = percentualComissao;
	}

	public String getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(String percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}

	public String getNomeTomador() {
		return nomeTomador;
	}

	public void setNomeTomador(String nomeTomador) {
		this.nomeTomador = nomeTomador;
	}

	public String getDocumentoTomador() {
		return documentoTomador;
	}

	public void setDocumentoTomador(String documentoTomador) {
		this.documentoTomador = documentoTomador;
	}

	public String getNomeCondutor() {
		return nomeCondutor;
	}

	public void setNomeCondutor(String nomeCondutor) {
		this.nomeCondutor = nomeCondutor;
	}

	public String getDocumentoCondutor() {
		return documentoCondutor;
	}

	public void setDocumentoCondutor(String documentoCondutor) {
		this.documentoCondutor = documentoCondutor;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public String getAnoModelo() {
		return anoModelo;
	}

	public void setAnoModelo(String anoModelo) {
		this.anoModelo = anoModelo;
	}

	public String getIsVeiculoUsado() {
		return isVeiculoUsado;
	}

	public void setIsVeiculoUsado(String isVeiculoUsado) {
		this.isVeiculoUsado = isVeiculoUsado;
	}

	public String getCodigoMarcaModelo() {
		return codigoMarcaModelo;
	}

	public void setCodigoMarcaModelo(String codigoMarcaModelo) {
		this.codigoMarcaModelo = codigoMarcaModelo;
	}

	public String getCodigoFipe() {
		return codigoFipe;
	}

	public void setCodigoFipe(String codigoFipe) {
		this.codigoFipe = codigoFipe;
	}

	public String getPercentualFipe() {
		return percentualFipe;
	}

	public void setPercentualFipe(String percentualFipe) {
		this.percentualFipe = percentualFipe;
	}

	public String getCepPernoite() {
		return cepPernoite;
	}

	public void setCepPernoite(String cepPernoite) {
		this.cepPernoite = cepPernoite;
	}

	public String getTipoFranquia() {
		return tipoFranquia;
	}

	public void setTipoFranquia(String tipoFranquia) {
		this.tipoFranquia = tipoFranquia;
	}

	public String getDispositivoAtual() {
		return dispositivoAtual;
	}

	public void setDispositivoAtual(String dispositivoAtual) {
		this.dispositivoAtual = dispositivoAtual;
	}

	public String getVeiculoBlindado() {
		return veiculoBlindado;
	}

	public void setVeiculoBlindado(String veiculoBlindado) {
		this.veiculoBlindado = veiculoBlindado;
	}

	public String getDespesasExtraordinarias() {
		return despesasExtraordinarias;
	}

	public void setDespesasExtraordinarias(String despesasExtraordinarias) {
		this.despesasExtraordinarias = despesasExtraordinarias;
	}

	public String getCategoriaRisco() {
		return categoriaRisco;
	}

	public void setCategoriaRisco(String categoriaRisco) {
		this.categoriaRisco = categoriaRisco;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTempoHabilitacao() {
		return tempoHabilitacao;
	}

	public void setTempoHabilitacao(String tempoHabilitacao) {
		this.tempoHabilitacao = tempoHabilitacao;
	}

	public String getExisteMenor25anos() {
		return existeMenor25anos;
	}

	public void setExisteMenor25anos(String existeMenor25anos) {
		this.existeMenor25anos = existeMenor25anos;
	}

	public String getGaragemFaculdade() {
		return garagemFaculdade;
	}

	public void setGaragemFaculdade(String garagemFaculdade) {
		this.garagemFaculdade = garagemFaculdade;
	}

	public String getGaragemResidencia() {
		return garagemResidencia;
	}

	public void setGaragemResidencia(String garagemResidencia) {
		this.garagemResidencia = garagemResidencia;
	}

	public String getGaragemTrabalho() {
		return garagemTrabalho;
	}

	public void setGaragemTrabalho(String garagemTrabalho) {
		this.garagemTrabalho = garagemTrabalho;
	}

	public String getCondutorPrincipalResideEm() {
		return condutorPrincipalResideEm;
	}

	public void setCondutorPrincipalResideEm(String condutorPrincipalResideEm) {
		this.condutorPrincipalResideEm = condutorPrincipalResideEm;
	}


	// ===================


	public String getProposta() {
		return proposta;
	}

	public void setProposta(String proposta) {
		this.proposta = proposta;
	}


	private  String pacoteAmpliado1
	private  String pacoteAmpliado2
	private  String pacoteCompacto1
	private  String pacoteCompacto2
	private  String pacoteEspecial
	private  String pacoteExclusivo
	private  String status

	//PACOTE
	private  String codigoPacote
	private  String descricaoPacote
	private  String quantidadeParcelas
	private  String valorPrimeiraParcela
	private  String valorTotalParcelado
	private  String valorTotalParcelaUnica
	private  String valorIof
	private  String valorAdicionalFracionamento
	private  String formaPagamento
	private  String Envelope

	//COBERTURA
	private  String codigoCobertura
	private  String descricaoCobertura
	private  String valorPremio
	private  String valorFranquia
	private  String valorLMI
	private  String coberturas



	public String getApolice() {
		return apolice;
	}

	public void setApolice(String apolice) {
		this.apolice = apolice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsuario2(){
		return usuario2
	}

	public void setUsuario2(String usuario2){
		this.usuario2 = usuario2
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public String getNomeCpfCondutor() {
		return nomeCpfCondutor;
	}

	public void setNomeCpfCondutor(String nomeCpfCondutor) {
		this.nomeCpfCondutor = nomeCpfCondutor;
	}

	public String getNumCpfCondutor() {
		return numCpfCondutor;
	}

	public void setNumCpfCondutor(String numCpfCondutor) {
		this.numCpfCondutor = numCpfCondutor;
	}

	public String getBlindagem() {
		return blindagem;
	}

	public void setBlindagem(String blindagem) {
		this.blindagem = blindagem;
	}

	public String getNumeroci() {
		return numeroci;
	}

	public void setNumeroci(String numeroci) {
		this.numeroci = numeroci;
	}

	public String getCenario() {
		return cenario;
	}

	public void setCenario(String cenario) {
		this.cenario = cenario;
	}

	public String getExecutar() {
		return executar;
	}

	public void setExecutar(String executar) {
		this.executar = executar;
	}

	public String getClasseDeBonus() {
		return classeDeBonus;
	}

	public void setClasseDeBonus(String classeDeBonus) {
		this.classeDeBonus = classeDeBonus;
	}

	public String getCotacao() {
		return cotacao;
	}

	public void setCotacao(String cotacao) {
		this.cotacao = cotacao;
	}

	public String getValorSeguro() {
		return valorSeguro;
	}

	public void setValorSeguro(String valorSeguro) {
		this.valorSeguro = valorSeguro;
	}

	public String getPacote() {
		return pacote;
	}

	public void setPacote(String pacote) {
		this.pacote = pacote;
	}

	public String getValorPacote(){
		return valorPacote
	}

	public void setValorPacote(String valorPacote) {
		this.valorPacote = valorPacote;
	}

	public String getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(String formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getUsuario(){
		return usuario
	}

	public void setUsuario(String usuario){
		this.usuario = usuario
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpfcnpj() {
		return cpfcnpj;
	}

	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getResidenciaCondutor() {
		return residenciaCondutor;
	}

	public void setResidenciaCondutor(String residenciaCondutor) {
		this.residenciaCondutor = residenciaCondutor;
	}

	public String getPacoteAmpliado1() {
		return pacoteAmpliado1;
	}

	public void setPacoteAmpliado1(String pacoteAmpliado1) {
		this.pacoteAmpliado1 = pacoteAmpliado1;
	}

	public String getPacoteAmpliado2() {
		return pacoteAmpliado2;
	}

	public void setPacoteAmpliado2(String pacoteAmpliado2) {
		this.pacoteAmpliado2 = pacoteAmpliado2;
	}

	public String getPacoteCompacto1() {
		return pacoteCompacto1;
	}

	public void setPacoteCompacto1(String pacoteCompacto1) {
		this.pacoteCompacto1 = pacoteCompacto1;
	}

	public String getPacoteCompacto2() {
		return pacoteCompacto2;
	}

	public void setPacoteCompacto2(String pacoteCompacto2) {
		this.pacoteCompacto2 = pacoteCompacto2;
	}

	public String getPacoteEspecial() {
		return pacoteEspecial;
	}

	public void setPacoteEspecial(String pacoteEspecial) {
		this.pacoteEspecial = pacoteEspecial;
	}

	public String getPacoteExclusivo() {
		return pacoteExclusivo;
	}

	public void setPacoteExclusivo(String pacoteExclusivo) {
		this.pacoteExclusivo = pacoteExclusivo;
	}

	public String getChassis() {
		return chassis;
	}

	public void setChassis(String chassis) {
		this.chassis = chassis;
	}

	public String getQuantidadeParcelas() {
		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(String quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}

	public String getValorPrimeiraParcela() {
		return valorPrimeiraParcela;
	}

	public void setValorPrimeiraParcela(String valorPrimeiraParcela) {
		this.valorPrimeiraParcela = valorPrimeiraParcela;
	}

	public String getValorTotalParcelado() {
		return valorTotalParcelado;
	}

	public void setValorTotalParcelado(String valorTotalParcelado) {
		this.valorTotalParcelado = valorTotalParcelado;
	}

	public String getValorTotalParcelaUnica() {
		return valorTotalParcelaUnica;
	}

	public void setValorTotalParcelaUnica(String valorTotalParcelaUnica) {
		this.valorTotalParcelaUnica = valorTotalParcelaUnica;
	}

	public String getValorIof() {
		return valorIof;
	}

	public void setValorIof(String valorIof) {
		this.valorIof = valorIof;
	}

	public String getValorAdicionalFracionamento() {
		return valorAdicionalFracionamento;
	}

	public void setValorAdicionalFracionamento(String valorAdicionalFracionamento) {
		this.valorAdicionalFracionamento = valorAdicionalFracionamento;
	}

	public String getCodigoCobertura() {
		return codigoCobertura;
	}

	public void setCodigoCobertura(String codigoCobertura) {
		this.codigoCobertura = codigoCobertura;
	}

	public String getDescricaoCobertura() {
		return descricaoCobertura;
	}

	public void setDescricaoCobertura(String descricaoCobertura) {
		this.descricaoCobertura = descricaoCobertura;
	}

	public String getValorPremio() {
		return valorPremio;
	}

	public void setValorPremio(String valorPremio) {
		this.valorPremio = valorPremio;
	}

	public String getValorFranquia() {
		return valorFranquia;
	}

	public void setValorFranquia(String valorFranquia) {
		this.valorFranquia = valorFranquia;
	}

	public String getValorLMI() {
		return valorLMI;
	}

	public void setValorLMI(String valorLMI) {
		this.valorLMI = valorLMI;
	}

	public String getCodigoPacote() {
		return codigoPacote;
	}

	public void setCodigoPacote(String codigoPacote) {
		this.codigoPacote = codigoPacote;
	}

	public String getDescricaoPacote() {
		return descricaoPacote;
	}

	public void setDescricaoPacote(String descricaoPacote) {
		this.descricaoPacote = descricaoPacote;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public String getCoberturas() {
		return coberturas;
	}

	public void setCoberturas(String coberturas) {
		this.coberturas = coberturas;
	}

	public String getEnvelope() {
		return Envelope;
	}

	public void setEnvelope(String envelope) {
		Envelope = envelope;
	}

	public String getParcela() {
		return parcela;
	}

	public void setParcela(String parcela) {
		this.parcela = parcela;
	}

	public String getDataNascimentoSegurado() {
		return dataNascimentoSegurado;
	}

	public void setDataNascimentoSegurado(String dataNascimentoSegurado) {
		this.dataNascimentoSegurado = dataNascimentoSegurado;
	}

	public String getClasseBonus() {
		return classeBonus;
	}

	public void setClasseBonus(String classeBonus) {
		this.classeBonus = classeBonus;
	}

	public String getCodSeguradora() {
		return codSeguradora;
	}

	public void setCodSeguradora(String codSeguradora) {
		this.codSeguradora = codSeguradora;
	}

	public String getFilial() {
		return filial;
	}

	public void setFilial(String filial) {
		this.filial = filial;
	}

	public String getNumApolice() {
		return numApolice;
	}

	public void setNumApolice(String numApolice) {
		this.numApolice = numApolice;
	}

	public String getNumCI() {
		return numCI;
	}

	public void setNumCI(String numCI) {
		this.numCI = numCI;
	}

	public String getDataFimVigencia() {
		return dataFimVigencia;
	}

	public void setDataFimVigencia(String dataFimVigencia) {
		this.dataFimVigencia = dataFimVigencia;
	}

	public String getRenovacaoComSinistro() {
		return renovacaoComSinistro;
	}

	public void setRenovacaoComSinistro(String renovacaoComSinistro) {
		this.renovacaoComSinistro = renovacaoComSinistro;
	}

	public String getVeicIgualApolAnt() {
		return veicIgualApolAnt;
	}

	public void setVeicIgualApolAnt(String veicIgualApolAnt) {
		this.veicIgualApolAnt = veicIgualApolAnt;
	}

	public String getRamoRenovacao() {
		return ramoRenovacao;
	}

	public void setRamoRenovacao(String ramoRenovacao) {
		this.ramoRenovacao = ramoRenovacao;
	}

	public String getNumItem() {
		return numItem;
	}

	public void setNumItem(String numItem) {
		this.numItem = numItem;
	}





}

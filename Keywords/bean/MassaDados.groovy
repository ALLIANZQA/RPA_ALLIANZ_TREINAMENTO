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

import bean.Pendencia

public class MassaDados{

	private String apolice;
	private String nomeCorretor;
	private String status;
	private String bloqueio;
	private String codigoCorretor;
	private String ramo;
	private String user;
	private String senha;
	private String linha;
	private String totalSinistro;
	private String porcentagemTotal;
	private String totalPremio;
	private String recibosPendentes;
	private String qtdSinistro;
	private List<Pendencia> pendencias;
	private String agrupamentoRamo;
	private String cnpjCpf;
	private String ramoModalidade;
	private String tipoSeguro;
	private String apoliceSucep;
	private String qtdPendencia;
	private String nomeColab;
	private String emailColab;
	private String dataDesde;
	private String dataAte;
	private String codVerif;
	private String cpf;
	private String nomeTomador;
	private String valorTributavel;
	private String valorIsento;
	private String subGrupo;
	private String valorPremioLiquido;
	private String qtdParcelas;
	private String dataInicioVigencia;
	private String dataFinalVigencia;
	private String proposta;
	private String textoComplementar;
	private String nomeMae;
	private String pis;
	private String numeroCarteirinha;
	private String item;
	private String codstur;
	private String email;
	private String dataFimVigenciaApolice
	private String diaPagamento
	private String renovarProposta
	private String desbloqueioproposta
    private String finalVigencia


	/**
	 * ENVIO PDF
	 */
	private String aplica
	private String nota
	private String envio
	private String corretor
	private String emailde
	private String emailpara
	private String txnota
	private String ramol
	private String documento


	private String resultadoFrota

	/**
	 * AGCS 
	 */
	private String geraPedido
	private String notaGerada
	private String pedidoGerado



	public String getPedidoGerado() {
		return pedidoGerado;
	}

	public void setPedidoGerado(String pedidoGerado) {
		this.pedidoGerado = pedidoGerado;
	}

	public String getGeraPedido() {
		return geraPedido;
	}

	public void setGeraPedido(String geraPedido) {
		this.geraPedido = geraPedido;
	}

	public String getNotaGerada() {
		return notaGerada;
	}

	public void setNotaGerada(String notaGerada) {
		this.notaGerada = notaGerada;
	}

	public String getResultadoFrota() {
		return resultadoFrota;
	}

	public void setResultadoFrota(String resultadoFrota) {
		this.resultadoFrota = resultadoFrota;
	}

	public String getRenovarProposta() {
		return renovarProposta;
	}

	public void setRenovarProposta(String renovarProposta) {
		this.renovarProposta = renovarProposta;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getRamol() {
		return ramol;
	}

	public void setRamol(String ramol) {
		this.ramol = ramol;
	}

	public String getTxnota() {
		return txnota;
	}

	public void setTxnota(String txnota) {
		this.txnota = txnota;
	}

	public String getEmailde() {
		return emailde;
	}

	public void setEmailde(String emailde) {
		this.emailde = emailde;
	}

	public String getEmailpara() {
		return emailpara;
	}

	public void setEmailpara(String emailpara) {
		this.emailpara = emailpara;
	}

	public String getCorretor() {
		return corretor;
	}

	public void setCorretor(String corretor) {
		this.corretor = corretor;
	}

	public String getAplica() {
		return aplica;
	}

	public void setAplica(String aplica) {
		this.aplica = aplica;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getEnvio() {
		return envio;
	}

	public void setEnvio(String envio) {
		this.envio = envio;
	}

	public String getDataFimVigenciaApolice() {
		return dataFimVigenciaApolice;
	}

	public void setDataFimVigenciaApolice(String dataFimVigenciaApolice) {
		this.dataFimVigenciaApolice = dataFimVigenciaApolice;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodstur() {
		return codstur;
	}

	public void setCodstur(String codstur) {
		this.codstur = codstur;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getNumeroCarteirinha() {
		return numeroCarteirinha;
	}

	public void setNumeroCarteirinha(String numeroCarteirinha) {
		this.numeroCarteirinha = numeroCarteirinha;
	}

	public String getSegurado() {
		return segurado;
	}

	public void setSegurado(String segurado) {
		this.segurado = segurado;
	}


	private String segurado


	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getDataFinalVigencia() {
		return dataFinalVigencia;
	}

	public void setDataFinalVigencia(String dataFinalVigencia) {
		this.dataFinalVigencia = dataFinalVigencia;
	}

	public String getTextoComplementar() {
		return textoComplementar;
	}

	public void setTextoComplementar(String textoComplementar) {
		this.textoComplementar = textoComplementar;
	}

	public String getSubGrupo() {
		return subGrupo;
	}

	public void setSubGrupo(String subGrupo) {
		this.subGrupo = subGrupo;
	}

	public String getValorPremioLiquido() {
		return valorPremioLiquido;
	}

	public void setValorPremioLiquido(String valorPremioLiquido) {
		this.valorPremioLiquido = valorPremioLiquido;
	}

	public String getQtdParcelas() {
		return qtdParcelas;
	}

	public void setQtdParcelas(String qtdParcelas) {
		this.qtdParcelas = qtdParcelas;
	}

	public String getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(String dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public String getProposta() {
		return proposta;
	}

	public void setProposta(String proposta) {
		this.proposta = proposta;
	}

	public String getValorTributavel() {
		return valorTributavel;
	}

	public void setValorTributavel(String valorTributavel) {
		this.valorTributavel = valorTributavel;
	}

	public String getValorIsento() {
		return valorIsento;
	}

	public void setValorIsento(String valorIsento) {
		this.valorIsento = valorIsento;
	}

	public String getNomeTomador() {
		return nomeTomador;
	}

	public void setNomeTomador(String nomeTomador) {
		this.nomeTomador = nomeTomador;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNomeColab() {
		return nomeColab;
	}

	public String getCodVerif() {
		return codVerif;
	}

	public void setCodVerif(String codVerif) {
		this.codVerif = codVerif;
	}

	public String getDataDesde() {
		return dataDesde;
	}

	public void setDataDesde(String dataDesde) {
		this.dataDesde = dataDesde;
	}

	public String getDataAte() {
		return dataAte;
	}

	public void setDataAte(String dataAte) {
		this.dataAte = dataAte;
	}

	public void setNomeColab(String nomeColab) {
		this.nomeColab = nomeColab;
	}

	public String getEmailColab() {
		return emailColab;
	}

	public void setEmailColab(String emailColab) {
		this.emailColab = emailColab;
	}

	public String getQtdPendencia() {
		return qtdPendencia;
	}

	public void setQtdPendencia(String qtdPendencia) {
		this.qtdPendencia = qtdPendencia;
	}

	public String getApoliceSucep() {
		return apoliceSucep;
	}

	public void setApoliceSucep(String apoliceSucep) {
		this.apoliceSucep = apoliceSucep;
	}

	public String getTipoSeguro() {
		return tipoSeguro;
	}

	public void setTipoSeguro(String tipoSeguro) {
		this.tipoSeguro = tipoSeguro;
	}

	public String getRamoModalidade() {
		return ramoModalidade;
	}

	public void setRamoModalidade(String ramoModalidade) {
		this.ramoModalidade = ramoModalidade;
	}

	public String getAgrupamentoRamo() {
		return agrupamentoRamo;
	}

	public void setAgrupamentoRamo(String agrupamentoRamo) {
		this.agrupamentoRamo = agrupamentoRamo;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public String getNomeRazaoSocial() {
		return nomeRazaoSocial;
	}

	public void setNomeRazaoSocial(String nomeRazaoSocial) {
		this.nomeRazaoSocial = nomeRazaoSocial;
	}

	public String getDataVencRisco() {
		return dataVencRisco;
	}

	public void setDataVencRisco(String dataVencRisco) {
		this.dataVencRisco = dataVencRisco;
	}

	public String getDescricaoRisco() {
		return descricaoRisco;
	}

	public void setDescricaoRisco(String descricaoRisco) {
		this.descricaoRisco = descricaoRisco;
	}


	private String nomeRazaoSocial;
	private String dataVencRisco;
	private String descricaoRisco;





	public List<Pendencia> getPendencias() {
		return pendencias;
	}

	public String getQtdSinistro() {
		return qtdSinistro;
	}

	public void setQtdSinistro(String qtdSinistro) {
		this.qtdSinistro = qtdSinistro;
	}

	public void setPendencias(List<Pendencia> pendencias) {
		this.pendencias = pendencias;
	}

	public String getPorcentagemTotal() {
		return porcentagemTotal;
	}

	public String getRecibosPendentes() {
		return recibosPendentes;
	}

	public void setRecibosPendentes(String recibosPendentes) {
		this.recibosPendentes = recibosPendentes;
	}

	public void setPorcentagemTotal(String porcentagemTotal) {
		this.porcentagemTotal = porcentagemTotal;
	}
	public String getTotalPremio() {
		return totalPremio;
	}

	public void setTotalPremio(String totalPremio) {
		this.totalPremio = totalPremio;
	}

	public String getTotalSinistro() {
		return totalSinistro;
	}

	public void setTotalSinistro(String totalSinistro) {
		this.totalSinistro = totalSinistro;
	}





	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}


	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRamo() {
		return ramo;
	}

	public void setRamo(String ramo) {
		this.ramo = ramo;
	}


	public String getCodigoCorretor() {
		return codigoCorretor;
	}
	public void setCodigoCorretor(String codigoCorretor) {
		this.codigoCorretor = codigoCorretor;
	}

	public String getBloqueio() {
		return bloqueio;
	}

	public void setBloqueio(String bloqueio) {
		this.bloqueio = bloqueio;
	}

	public String getApolice() {
		return apolice;
	}

	public void setApolice(String apolice) {
		this.apolice = apolice;
	}

	public String getNomeCorretor() {
		return nomeCorretor;
	}
	public void setNomeCorretor(String nomeCorretor) {
		this.nomeCorretor = nomeCorretor;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getDiaPagamento() {
		return diaPagamento;
	}

	public void setDiaPagamento(String diaPagamento) {
		this.diaPagamento = diaPagamento;
	}

	public String getDesbloqueioproposta() {
		return desbloqueioproposta;
	}

	public void setDesbloqueioproposta(String desbloqueioproposta) {
		this.desbloqueioproposta = desbloqueioproposta;
	}
	
	public String getFinalVigencia() {
		return finalVigencia;
	}

	public void setFinalVigencia(String finalVigencia) {
		this.finalVigencia = finalVigencia;
	}

	@Override
	public String toString() {
		return "MassaDados [apolice=" + apolice + ", nomeCorretor=" + nomeCorretor + ", status=" + status + "]";
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MassaDados massadados = (MassaDados) o;
		if(nomeCorretor == null){
			if(massadados.nomeCorretor !=null){
				return  false
			}else if(!nomeCorretor.equals(massadados.nomeCorretor)){
				return false
			}
		}

		System.err.println(true)
		return true
	}

	//	@Override
	//	public int hashCode() {
	//		return Objects.hash(nomeCorretor);
	//	}

	//	@Override
	//	public int compareTo(MassaDados m) {
	//	int valor = this.getNomeCorretor().compareTo(m.getNomeCorretor());
	//	return  ( valor !=0 ? valor : 1);
	//	}



}

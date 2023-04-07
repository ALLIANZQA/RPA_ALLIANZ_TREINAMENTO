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

public class Pacote extends Cobertura {

	protected String codigoPacote
	protected String descricaoPacote
	protected String formaPagamento
	protected String quantidadeParcelas
	protected String valorPrimeiraParcela
	protected String valorTotalParcelado
	protected String valorTotalParcelaUnica
	protected String valorIof
	protected String valorAdicionalFracionamento
	protected Double totalPremioCoberuta
	protected List<Cobertura> listaCobertura
	protected String numeroOperacao
	protected String numeroCotacao



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
	public List<Cobertura> getListaCobertura() {
		return listaCobertura;
	}
	public void setListaCobertura(List<Cobertura> listaCobertura) {
		this.listaCobertura = listaCobertura;
	}
	public String getNumeroOperacao() {
		return numeroOperacao;
	}
	public void setNumeroOperacao(String numeroOperacao) {
		this.numeroOperacao = numeroOperacao;
	}
	public String getNumeroCotacao() {
		return numeroCotacao;
	}
	public void setNumeroCotacao(String numeroCotacao) {
		this.numeroCotacao = numeroCotacao;
	}


	public Double getTotalPremioCobertura(){
		List<Cobertura> soma = getListaCobertura()
		Double somaPremioCobertura = 0.0;
		for(int i = 0; i<soma.size(); i++){
			somaPremioCobertura = somaPremioCobertura + Double.parseDouble(soma.get(i).getValorPremio());
		}
		return somaPremioCobertura;
	}
}

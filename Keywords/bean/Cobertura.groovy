package bean;

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint;
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase;
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData;
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject;

import com.kms.katalon.core.annotation.Keyword;
import com.kms.katalon.core.checkpoint.Checkpoint;
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW;
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile;
import com.kms.katalon.core.model.FailureHandling;
import com.kms.katalon.core.testcase.TestCase;
import com.kms.katalon.core.testdata.TestData;
import com.kms.katalon.core.testobject.TestObject;
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS;
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI;

import internal.GlobalVariable;

public class Cobertura {

	protected String codigoCobertura;
	protected String descricaoCobertura;
	protected String valorPremio;
	protected String valorFranquia;
	protected String valorLMI;

	protected String codigoCobertura2;
	protected String descricaoCobertura2;
	protected String valorPremio2;
	protected String valorFranquia2;
	protected String valorLMI2;

	protected String codigoCobertura3;
	protected String descricaoCobertura3;
	protected String valorPremio3;
	protected String valorFranquia3;
	protected String valorLMI3;

	protected String codigoCobertura4;
	protected String descricaoCobertura4;
	protected String valorPremio4;
	protected String valorFranquia4;
	protected String valorLMI4;

	protected String codigoCobertura5;
	protected String descricaoCobertura5;
	protected String valorPremio5;
	protected String valorFranquia5;
	protected String valorLMI5;

	protected String codigoCobertura6;
	protected String descricaoCobertura6;
	protected String valorPremio6;
	protected String valorFranquia6;
	protected String valorLMI6;
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
	public String getCodigoCobertura2() {
		return codigoCobertura2;
	}
	public void setCodigoCobertura2(String codigoCobertura2) {
		this.codigoCobertura2 = codigoCobertura2;
	}
	public String getDescricaoCobertura2() {
		return descricaoCobertura2;
	}
	public void setDescricaoCobertura2(String descricaoCobertura2) {
		this.descricaoCobertura2 = descricaoCobertura2;
	}
	public String getValorPremio2() {
		return valorPremio2;
	}
	public void setValorPremio2(String valorPremio2) {
		this.valorPremio2 = valorPremio2;
	}
	public String getValorFranquia2() {
		return valorFranquia2;
	}
	public void setValorFranquia2(String valorFranquia2) {
		this.valorFranquia2 = valorFranquia2;
	}
	public String getValorLMI2() {
		return valorLMI2;
	}
	public void setValorLMI2(String valorLMI2) {
		this.valorLMI2 = valorLMI2;
	}
	public String getCodigoCobertura3() {
		return codigoCobertura3;
	}
	public void setCodigoCobertura3(String codigoCobertura3) {
		this.codigoCobertura3 = codigoCobertura3;
	}
	public String getDescricaoCobertura3() {
		return descricaoCobertura3;
	}
	public void setDescricaoCobertura3(String descricaoCobertura3) {
		this.descricaoCobertura3 = descricaoCobertura3;
	}
	public String getValorPremio3() {
		return valorPremio3;
	}
	public void setValorPremio3(String valorPremio3) {
		this.valorPremio3 = valorPremio3;
	}
	public String getValorFranquia3() {
		return valorFranquia3;
	}
	public void setValorFranquia3(String valorFranquia3) {
		this.valorFranquia3 = valorFranquia3;
	}
	public String getValorLMI3() {
		return valorLMI3;
	}
	public void setValorLMI3(String valorLMI3) {
		this.valorLMI3 = valorLMI3;
	}
	public String getCodigoCobertura4() {
		return codigoCobertura4;
	}
	public void setCodigoCobertura4(String codigoCobertura4) {
		this.codigoCobertura4 = codigoCobertura4;
	}
	public String getDescricaoCobertura4() {
		return descricaoCobertura4;
	}
	public void setDescricaoCobertura4(String descricaoCobertura4) {
		this.descricaoCobertura4 = descricaoCobertura4;
	}
	public String getValorPremio4() {
		return valorPremio4;
	}
	public void setValorPremio4(String valorPremio4) {
		this.valorPremio4 = valorPremio4;
	}
	public String getValorFranquia4() {
		return valorFranquia4;
	}
	public void setValorFranquia4(String valorFranquia4) {
		this.valorFranquia4 = valorFranquia4;
	}
	public String getValorLMI4() {
		return valorLMI4;
	}
	public void setValorLMI4(String valorLMI4) {
		this.valorLMI4 = valorLMI4;
	}
	public String getCodigoCobertura5() {
		return codigoCobertura5;
	}
	public void setCodigoCobertura5(String codigoCobertura5) {
		this.codigoCobertura5 = codigoCobertura5;
	}
	public String getDescricaoCobertura5() {
		return descricaoCobertura5;
	}
	public void setDescricaoCobertura5(String descricaoCobertura5) {
		this.descricaoCobertura5 = descricaoCobertura5;
	}
	public String getValorPremio5() {
		return valorPremio5;
	}
	public void setValorPremio5(String valorPremio5) {
		this.valorPremio5 = valorPremio5;
	}
	public String getValorFranquia5() {
		return valorFranquia5;
	}
	public void setValorFranquia5(String valorFranquia5) {
		this.valorFranquia5 = valorFranquia5;
	}
	public String getValorLMI5() {
		return valorLMI5;
	}
	public void setValorLMI5(String valorLMI5) {
		this.valorLMI5 = valorLMI5;
	}
	public String getCodigoCobertura6() {
		return codigoCobertura6;
	}
	public void setCodigoCobertura6(String codigoCobertura6) {
		this.codigoCobertura6 = codigoCobertura6;
	}
	public String getDescricaoCobertura6() {
		return descricaoCobertura6;
	}
	public void setDescricaoCobertura6(String descricaoCobertura6) {
		this.descricaoCobertura6 = descricaoCobertura6;
	}
	public String getValorPremio6() {
		return valorPremio6;
	}
	public void setValorPremio6(String valorPremio6) {
		this.valorPremio6 = valorPremio6;
	}
	public String getValorFranquia6() {
		return valorFranquia6;
	}
	public void setValorFranquia6(String valorFranquia6) {
		this.valorFranquia6 = valorFranquia6;
	}
	public String getValorLMI6() {
		return valorLMI6;
	}
	public void setValorLMI6(String valorLMI6) {
		this.valorLMI6 = valorLMI6;
	}
}

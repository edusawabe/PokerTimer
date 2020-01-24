package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import application.ConfigManager;

public class ResultadoRodada {
	int rebuys;
	int addOn;
	String colocacao;
	double premiacao;
	double pontuacaoEtapa;
	int qtdeJogadores;
	int qtderebuysEtapa;
	int qtdeAddOnEtapa;

	public double getPontuacaoEtapa() {
		return getPontuacaoJogadorEtapa();
	}

	public void setPontuacaoEtapa(double pontuacaoEtapa) {
	    BigDecimal bd = new BigDecimal(pontuacaoEtapa);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
		this.pontuacaoEtapa = bd.doubleValue();
	}

	public ResultadoRodada() {
		colocacao = null;
		premiacao = 0;
		rebuys = 0;
		pontuacaoEtapa = 0;
	}

	public ResultadoRodada(String resultado) {
		parseStringResultado(resultado);
	}

	private void parseStringResultado(String resultado) {
		String[] res;
		res            = resultado.split("@");
		rebuys         = Integer.parseInt(res[0]);
		addOn          = Integer.parseInt(res[1]);
		colocacao      = res[2];
		premiacao      = Double.parseDouble(res[3]);
		pontuacaoEtapa = Double.parseDouble(res[4]);
	}

	public void getResultadoFromFileLine(String resultado){
		parseStringResultado(resultado);
	}

	public String getResultLine(){
		String res = new String();
		res =  rebuys + "@" + addOn + "@" + colocacao + "@" + premiacao + "@" + pontuacaoEtapa;
		return res;
	}

	public double getPontuacaoJogadorEtapa(){
		ConfigManager config = new ConfigManager();
		return config.getPontuacaoJogadorEtapa(this);
	}

	public String getColocacao() {
		return colocacao;
	}
	public void setColocacao(String colocacao) {
		this.colocacao = colocacao;
	}
	public double getPremiacao() {
		return premiacao;
	}
	public void setPremiacao(double premiacao) {
		this.premiacao = premiacao;
	}
	public int getRebuys() {
		return rebuys;
	}
	public void setRebuys(int rebuys) {
		this.rebuys = rebuys;
	}

	public int getQtdeJogadores() {
		return qtdeJogadores;
	}

	public void setQtdeJogadores(int qtdeJogadores) {
		this.qtdeJogadores = qtdeJogadores;
	}

	public int getQtderebuysEtapa() {
		return qtderebuysEtapa;
	}

	public int getQtdeAddOnEtapa() {
		return qtdeAddOnEtapa;
	}

	public void setQtderebuysEtapa(int qtderebuysEtapa) {
		this.qtderebuysEtapa = qtderebuysEtapa;
	}

	public void setQtdeAddOnEtapa(int qtdeAddOnEtapa) {
		this.qtdeAddOnEtapa = qtdeAddOnEtapa;
	}

	public int getAddOn() {
		return addOn;
	}

	public void setAddOn(int addOn) {
		this.addOn = addOn;
	}
}

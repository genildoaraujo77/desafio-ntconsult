package com.ntconsult.desafio.model;

import java.time.OffsetDateTime;

import com.ntconsult.desafio.domain.model.Pauta;
import com.ntconsult.desafio.domain.model.StatusSessao;

public class SessaoVotacaoModel {
	private Long id;

	private Pauta pauta;
	
	private Integer qtdvotos;
	
	private StatusSessao status;
 	
	private OffsetDateTime dataAbertura;
	
	private OffsetDateTime dataFinalizacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pauta getPauta() {
		return pauta;
	}

	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}

	public Integer getQtdvotos() {
		return qtdvotos;
	}

	public void setQtdvotos(Integer qtdvotos) {
		this.qtdvotos = qtdvotos;
	}

	public StatusSessao getStatus() {
		return status;
	}

	public void setStatus(StatusSessao status) {
		this.status = status;
	}

	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public OffsetDateTime getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(OffsetDateTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}
	
}

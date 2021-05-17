package com.ntconsult.desafio.model;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ntconsult.desafio.domain.model.Pauta;
import com.ntconsult.desafio.domain.model.StatusSessao;

@JsonInclude(Include.NON_NULL)
public class SessaoVotacaoModel {
	private Long id;

	private Pauta pauta;
	
	private Resultado resultado;
	
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

	public Resultado getResultado() {
		return resultado;
	}

	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
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

package com.ntconsult.desafio.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.ntconsult.desafio.domain.ValidationGroups;
import com.ntconsult.desafio.domain.model.Pauta;
import com.ntconsult.desafio.domain.model.StatusSessao;

public class SessaoVotacaoModel {
	private Long id;

	private String nomePauta;
	
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

	public String getNomePauta() {
		return nomePauta;
	}

	public void setNomePauta(String nomePauta) {
		this.nomePauta = nomePauta;
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

package com.ntconsult.desafio.model;

import java.time.OffsetDateTime;

import com.ntconsult.desafio.domain.model.Pauta;
import com.ntconsult.desafio.domain.model.SessaoVotacao;

public class VotoModel {

	private Long id;

	private Boolean simounao;

	private Pauta pauta;

	private AssociadoResumoModel associado;

	private OffsetDateTime dataEnvio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getSimounao() {
		return simounao;
	}

	public void setSimounao(Boolean simounao) {
		this.simounao = simounao;
	}

	public Pauta getPauta() {
		return pauta;
	}

	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}

	public AssociadoResumoModel getAssociado() {
		return associado;
	}

	public void setAssociado(AssociadoResumoModel associado) {
		this.associado = associado;
	}

	public OffsetDateTime getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(OffsetDateTime dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

}

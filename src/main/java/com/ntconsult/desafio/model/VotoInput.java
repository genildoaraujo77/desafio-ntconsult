package com.ntconsult.desafio.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class VotoInput {

	@Valid
	@NotNull
	private PautaIdInput pauta;

	@Valid
	@NotNull
	private AssociadoIdInput associado;

	@NotNull
	private Boolean simounao;

	public PautaIdInput getPauta() {
		return pauta;
	}

	public void setPauta(PautaIdInput pauta) {
		this.pauta = pauta;
	}

	public AssociadoIdInput getAssociado() {
		return associado;
	}

	public void setAssociado(AssociadoIdInput associado) {
		this.associado = associado;
	}

	public Boolean getSimounao() {
		return simounao;
	}

	public void setSimounao(Boolean simounao) {
		this.simounao = simounao;
	}

}

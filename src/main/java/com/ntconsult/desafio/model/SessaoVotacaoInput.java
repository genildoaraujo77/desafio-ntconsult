package com.ntconsult.desafio.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SessaoVotacaoInput {

	@Valid
	@NotNull
	private PautaIdInput pauta;

	public long tempoSessaoVotacao = 1;

	public PautaIdInput getPauta() {
		return pauta;
	}

	public void setPauta(PautaIdInput pauta) {
		this.pauta = pauta;
	}

	public long getTempoSessaoVotacao() {
		return tempoSessaoVotacao;
	}

	public void setTempoSessaoVotacao(long tempoSessaoVotacao) {
		this.tempoSessaoVotacao = tempoSessaoVotacao;
	}

}

package com.ntconsult.desafio.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AssociadoIdInput {

	@Valid
	@NotNull
	private String cpf;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}

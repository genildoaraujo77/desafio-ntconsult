package com.ntconsult.desafio.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AssociadoIdInput {

	@Valid
	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}

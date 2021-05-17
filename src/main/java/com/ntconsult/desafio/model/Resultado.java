package com.ntconsult.desafio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ntconsult.desafio.domain.model.StatusSessao;

@Entity
@JsonInclude(Include.NON_NULL)
public class Resultado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int sim;
	private int nao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "resultado_final", length = 9)
	private StatusSessao total;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getSim() {
		return sim;
	}
	public void setSim(int sim) {
		this.sim = sim;
	}
	public int getNao() {
		return nao;
	}
	public void setNao(int nao) {
		this.nao = nao;
	}
	public StatusSessao getTotal() {
		return total;
	}
	public void setTotal(StatusSessao total) {
		this.total = total;
	}
	
}

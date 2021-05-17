package com.ntconsult.desafio.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.ntconsult.desafio.domain.ValidationGroups;

@Entity
public class Voto {
	
	@NotNull(groups = ValidationGroups.VotoId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "voto")
	private Boolean simounao;

	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.PautaId.class)
	@NotNull
	@ManyToOne
	private Pauta pauta;
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.AssociadoId.class)
	@NotNull
	@ManyToOne
	private Associado associado;
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.AssociadoId.class)
	@NotNull
	@ManyToOne
	private SessaoVotacao sessaoVotacao;
	
	@Column(name = "data_envio")
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

	public Associado getAssociado() {
		return associado;
	}

	public void setAssociado(Associado associado) {
		this.associado = associado;
	}
	
	public SessaoVotacao getSessaoVotacao() {
		return sessaoVotacao;
	}

	public void setSessaoVotacao(SessaoVotacao sessaoVotacao) {
		this.sessaoVotacao = sessaoVotacao;
	}

	public OffsetDateTime getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(OffsetDateTime dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Voto other = (Voto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

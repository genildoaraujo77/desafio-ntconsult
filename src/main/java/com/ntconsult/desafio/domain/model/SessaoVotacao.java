package com.ntconsult.desafio.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.ntconsult.desafio.domain.ValidationGroups;
import com.ntconsult.desafio.domain.exception.VotacaoException;
import com.ntconsult.desafio.model.ResultadoVotacao;

@Entity
public class SessaoVotacao {

	@NotNull(groups = ValidationGroups.AssociadoId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@Valid
//	@ConvertGroup(from = Default.class, to = ValidationGroups.PautaId.class)
//	@NotNull
	@ManyToOne
	@JoinColumn(unique = true)
	private Pauta pauta;
	
//	@JsonProperty(access = Access.READ_ONLY)
	@Enumerated(EnumType.STRING)
	private StatusSessao status;
 	
//	@JsonProperty(access = Access.READ_ONLY)
	@Column(name = "data_abertura")
	private OffsetDateTime dataAbertura;
	
//	@JsonProperty(access = Access.READ_ONLY)
	@Column(name = "data_finalizacao")
	private OffsetDateTime dataFinalizacao;
	
	@OneToMany(mappedBy = "sessaoVotacao")
	private List<Voto> votos = new ArrayList<>();
	
	@Transient
	private ResultadoVotacao resultado;

	@Transient
	public long tempoSessaoVotacao = 1;

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
	
	public List<Voto> getVotos() {
		return votos;
	}

	public void setVotos(List<Voto> votos) {
		this.votos = votos;
	}

	public long getTempoSessaoVotacao() {
		return tempoSessaoVotacao;
	}

	public void setTempoSessaoVotacao(long tempoSessaoVotacao) {
		this.tempoSessaoVotacao = tempoSessaoVotacao;
	}
	
	public ResultadoVotacao getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoVotacao resultado) {
		this.resultado = resultado;
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
		SessaoVotacao other = (SessaoVotacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean podeSerFinalizada() {
		return StatusSessao.ABERTA.equals(getStatus());
	}
	
	public boolean naoPodeSerFinalizada() {
		return !podeSerFinalizada();
	}
	
	public void finalizar() {
		if(naoPodeSerFinalizada()) {
			throw new VotacaoException("Sessao de votação não pode ser finalizada");
		}
		
		setStatus(StatusSessao.FINALIZADA);
	}
	
}

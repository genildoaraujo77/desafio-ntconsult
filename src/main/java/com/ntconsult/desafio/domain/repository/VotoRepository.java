package com.ntconsult.desafio.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ntconsult.desafio.domain.model.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
	
	@Query(value = "SELECT * FROM voto v\r\n" + 
			"LEFT JOIN sessao_votacao sv on sv.pauta_id = ?1\r\n" + 
			"WHERE v.pauta_id = ?1 AND v.associado_id = ?2", nativeQuery = true)
	Voto findByVoto(Long pautaId, Long associadoId);

	@Query(value = "SELECT * FROM voto v WHERE v.sessao_votacao_id = ?1", nativeQuery = true)
	List<Voto> findByIdSessao(Long sessaoVotacaoId);
}

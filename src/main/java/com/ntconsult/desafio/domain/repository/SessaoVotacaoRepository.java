package com.ntconsult.desafio.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ntconsult.desafio.domain.model.SessaoVotacao;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long>{

	@Query(value = "SELECT * FROM sessao_votacao where pauta_id = ?1", nativeQuery = true)
	SessaoVotacao findByPauta(Long id);

}
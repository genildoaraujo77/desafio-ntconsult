package com.ntconsult.desafio.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ntconsult.desafio.domain.model.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
	
//	List<Pauta> findByNome(String nome);
//	List<Pauta> findByNomeContaining(String nome);
	Pauta findByNome(String nome);
}

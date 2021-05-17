package com.ntconsult.desafio.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ntconsult.desafio.model.Resultado;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
	
}

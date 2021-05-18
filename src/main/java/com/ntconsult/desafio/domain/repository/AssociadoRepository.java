package com.ntconsult.desafio.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ntconsult.desafio.domain.model.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {
	
	Associado findByCpf(String cpf);
}

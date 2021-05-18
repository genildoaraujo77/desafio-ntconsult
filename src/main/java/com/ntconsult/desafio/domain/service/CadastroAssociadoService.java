package com.ntconsult.desafio.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntconsult.desafio.domain.exception.VotacaoException;
import com.ntconsult.desafio.domain.model.Associado;
import com.ntconsult.desafio.domain.repository.AssociadoRepository;

@Service
public class CadastroAssociadoService {
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	public Associado salvar(Associado associado) {
		Associado associadoExistente = buscarAssociado(associado.getCpf());
		
		if(associadoExistente != null && !associadoExistente.equals(associado)) {
			throw new VotacaoException("Já existe uma associado cadastrado com o mesmo cpf.");
		}
		return associadoRepository.save(associado);
	}
	
	public void excluir(Long associadoId) {
		associadoRepository.deleteById(associadoId);
	}
	
	private Associado buscarAssociado(String associadoCpf) {
		return associadoRepository.findByCpf(associadoCpf);
	}
}

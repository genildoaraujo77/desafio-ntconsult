package com.ntconsult.desafio.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntconsult.desafio.domain.exception.VotacaoException;
import com.ntconsult.desafio.domain.model.Voto;
import com.ntconsult.desafio.domain.repository.VotoRepository;

@Service
public class CadastroVotoService {
	
	@Autowired
	private VotoRepository votoRepository;
	
	public Voto salvar(Voto voto) {
		Long idPauta = voto.getPauta().getId();
		Long idAssociado = voto.getAssociado().getId();
		
		Voto votoExistente = votoRepository.findByVoto(idPauta, idAssociado);
		
		if(votoExistente != null && !votoExistente.equals(voto)) {
				throw new VotacaoException("Você já votou nesta pauta, favor aguardar nova sessão de votação");	
		}
		
		return votoRepository.save(voto);
		
	}
	
	public void excluir(Long votoId) {
		votoRepository.deleteById(votoId);
	}
}

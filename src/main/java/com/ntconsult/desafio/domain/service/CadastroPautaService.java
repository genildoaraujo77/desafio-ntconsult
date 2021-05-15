package com.ntconsult.desafio.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntconsult.desafio.domain.exception.NegocioException;
import com.ntconsult.desafio.domain.model.Pauta;
import com.ntconsult.desafio.domain.repository.PautaRepository;

@Service
public class CadastroPautaService {
	
	@Autowired
	private PautaRepository pautaRepository;
	
	public Pauta salvar(Pauta pauta) {
		Pauta pautaExistente = pautaRepository.findByNome(pauta.getNome());
		
		if(pautaExistente != null && !pautaExistente.equals(pauta)) {
			throw new NegocioException("Já existe uma pauta cadastrada com o mesmo nome.");
		}
		return pautaRepository.save(pauta);
	}
	
	public void excluir(Long pautaId) {
		pautaRepository.deleteById(pautaId);
	}
}

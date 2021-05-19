package com.ntconsult.desafio.domain.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ntconsult.desafio.domain.model.Pauta;
import com.ntconsult.desafio.domain.repository.PautaRepository;

public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;
	
	public Pauta salvarPauta(Pauta pauta) {
		pauta = pautaRepository.save(pauta);
		System.out.println("salvou");
		return pauta;
	}

}

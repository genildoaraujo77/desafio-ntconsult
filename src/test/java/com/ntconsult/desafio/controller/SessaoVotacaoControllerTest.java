package com.ntconsult.desafio.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ntconsult.desafio.domain.exception.VotacaoException;
import com.ntconsult.desafio.domain.model.Pauta;
import com.ntconsult.desafio.domain.model.SessaoVotacao;
import com.ntconsult.desafio.domain.repository.SessaoVotacaoRepository;
import com.ntconsult.desafio.domain.service.SessaoVotacaoService;

@SpringBootTest
class SessaoVotacaoControllerTest {

	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;
	
	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	@Test
	void testAbrirSessaoNovo() {
		SessaoVotacao sessaoVotacao = new SessaoVotacao();
		Pauta pauta = new Pauta();
		pauta.setId(9L);
		sessaoVotacao.setPauta(pauta);
		SessaoVotacao sessaoVotacaoNovo = sessaoVotacaoService.criar(sessaoVotacao);
		assertEquals(sessaoVotacao, sessaoVotacaoNovo);
	}
	
	@Test
	void testCriarSessaoVotacaoJaExistente() {
		SessaoVotacao sessaoVotacao = new SessaoVotacao();
		Pauta pauta = new Pauta();
		pauta.setId(1L);
		sessaoVotacao.setPauta(pauta);
		assertThrows(VotacaoException.class, () -> sessaoVotacaoService.criar(sessaoVotacao));
	}
	
	@Test
	public void testListarSessoes() {
		List<SessaoVotacao> sessaoVotacoes = sessaoVotacaoRepository.findAll();
		assertThat(sessaoVotacoes).isNotEmpty();
		assertThat(sessaoVotacoes).isNotNull();
	}
	
	@Test
	void testBuscarSessaoVotacao() {
		Optional<SessaoVotacao> associado = sessaoVotacaoRepository.findById(1L);

		assertEquals(1L, associado.get().getId());

	}
	
}

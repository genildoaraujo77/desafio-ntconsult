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
import com.ntconsult.desafio.domain.service.CadastroPautaService;
import com.ntconsult.desafio.domain.service.SessaoVotacaoService;

@SpringBootTest
class SessaoVotacaoControllerTest {

	private final SessaoVotacaoService sessaoVotacaoService;
	private final SessaoVotacaoRepository sessaoVotacaoRepository;
	private final CadastroPautaService cadastroPautaService;

	@Autowired
	public SessaoVotacaoControllerTest(CadastroPautaService cadastroPautaService, 
			SessaoVotacaoService sessaoVotacaoService,
			SessaoVotacaoRepository sessaoVotacaoRepository) {
		this.cadastroPautaService = cadastroPautaService;
		this.sessaoVotacaoService = sessaoVotacaoService;
		this.sessaoVotacaoRepository = sessaoVotacaoRepository;
	}

	@Test
	void testAbrirSessaoNovo() {
		Pauta pauta = new Pauta("Nome nova pauta para sessao", "nova pauta para sessao teste junit");
		Pauta pautaNova = cadastroPautaService.salvar(pauta);
		assertEquals(pauta, pautaNova);
		SessaoVotacao sessaoVotacao = new SessaoVotacao();
		sessaoVotacao.setPauta(pautaNova);
		SessaoVotacao sessaoVotacaoNovo = sessaoVotacaoService.criar(sessaoVotacao);
		assertEquals(sessaoVotacao, sessaoVotacaoNovo);
	}
	
	@Test
	void testCriarSessaoVotacaoJaExistente() {
		Pauta pauta = new Pauta("pauta para sessao ja existente", "pauta para sessao ja existente teste junit");
		Pauta pautaNova = cadastroPautaService.salvar(pauta);
		assertEquals(pauta, pautaNova);
		SessaoVotacao sessaoVotacao = new SessaoVotacao();
		sessaoVotacao.setPauta(pautaNova);
		SessaoVotacao sessaoVotacaoNova = sessaoVotacaoService.criar(sessaoVotacao);
		assertEquals(sessaoVotacao, sessaoVotacaoNova);
		sessaoVotacaoNova.setPauta(pautaNova);
		assertThrows(VotacaoException.class, () -> sessaoVotacaoService.criar(sessaoVotacaoNova));
	}
	
	@Test
	public void testListarSessoes() {
		List<SessaoVotacao> sessaoVotacoes = sessaoVotacaoRepository.findAll();
		assertThat(sessaoVotacoes).isNotEmpty();
		assertThat(sessaoVotacoes).isNotNull();
	}
	
	@Test
	void testBuscarSessaoVotacao() {
		Pauta pauta = new Pauta("pauta buscar sessao votacao", "pauta para buscar sessao por id");
		Pauta pautaNova = cadastroPautaService.salvar(pauta);
		assertEquals(pauta, pautaNova);
		SessaoVotacao sessaoVotacao = new SessaoVotacao();
		sessaoVotacao.setPauta(pautaNova);
		SessaoVotacao sessaoVotacaoNova = sessaoVotacaoService.criar(sessaoVotacao);
		assertEquals(sessaoVotacao, sessaoVotacaoNova);
		Optional<SessaoVotacao> sessao = sessaoVotacaoRepository.findById(sessaoVotacaoNova.getId());

		assertEquals(sessaoVotacaoNova.getId(), sessao.get().getId());

	}
	
}

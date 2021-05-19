package com.ntconsult.desafio.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ntconsult.desafio.domain.model.Pauta;
import com.ntconsult.desafio.domain.repository.PautaRepository;
import com.ntconsult.desafio.domain.service.CadastroPautaService;

@SpringBootTest
class PautasControllerTest {

	private final PautaRepository pautaRepository;
	private final CadastroPautaService cadastroPautaService;

	@Autowired
	public PautasControllerTest(PautaRepository pautaRepository,
			CadastroPautaService cadastroPautaService) {
		this.pautaRepository = pautaRepository;
		this.cadastroPautaService = cadastroPautaService;
	}

	@Test
	void testCriarPauta() {
		Pauta pauta = new Pauta();
		pauta.setNome("Nome pauta");
		pauta.setDescricao("descrição pauta");
		Pauta pautaNovo = cadastroPautaService.salvar(pauta);
		assertEquals(pauta, pautaNovo);
	}
	

	@Test
	void testListarPautas() {
		List<Pauta> listPautas = pautaRepository.findAll();
		assertThat(listPautas).isNotEmpty();
		assertThat(listPautas).isNotNull();
	}

	@Test
	void testBuscarPauta() {
		Optional<Pauta> pauta = pautaRepository.findById(13L);

		assertEquals(13L, pauta.get().getId());

	}

	@Test
	void testAtualizarPautaLong() {
		Pauta pauta = new Pauta();
		pauta.setId(13L);
		pauta.setNome("Nome pauta Atualizada");
		pauta.setDescricao("Descrição pauta");
		Pauta pautaNova = cadastroPautaService.salvar(pauta);
		assertEquals(pauta, pautaNova);
	}

	@Test
	void testExcluirPautaLong() {
		cadastroPautaService.excluir(13L);
	}

}

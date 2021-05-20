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
	void testCriarPautaNovo() {
		Pauta pauta = new Pauta("Nome nova pauta", "nova pauta teste junit");
		Pauta pautaNova = cadastroPautaService.salvar(pauta);
		assertEquals(pauta, pautaNova);
	}

	@Test
	void testListarPautas() {
		List<Pauta> listPautas = pautaRepository.findAll();
		assertThat(listPautas).isNotEmpty();
		assertThat(listPautas).isNotNull();
	}

	@Test
	public void testBuscarPautaPorId() {
		Pauta pauta1 = new Pauta("Nome pauta", "pauta teste junit");
		Pauta product2 = pautaRepository.save(pauta1);
		Optional<Pauta> pautaOptional = pautaRepository.findById(product2.getId());
		assertEquals(product2.getId(), pautaOptional.get().getId());
		assertEquals(product2.getNome(), pautaOptional.get().getNome());
		assertEquals(product2.getDescricao(), pautaOptional.get().getDescricao());
	}

	@Test
	void testAtualizarPautaLong() {
		Pauta pauta1 = new Pauta("Nome pauta", "pauta teste junit");
		Pauta pauta2 = pautaRepository.save(pauta1);
		pauta2.setNome("Nome pauta Atualizado");
		Pauta pautaNovo = cadastroPautaService.salvar(pauta2);
		assertEquals(pauta2, pautaNovo);
	}

	@Test
	public void testExcluirPautaLong() {
		Pauta pauta = new Pauta("Nome excluir pauta", "excluir pauta teste junit");

		cadastroPautaService.salvar(pauta);
		cadastroPautaService.excluir(pauta.getId());
		Optional<Pauta> pautaOptional = pautaRepository.findById(pauta.getId());
		assertEquals(Optional.empty(), pautaOptional);
	}

}

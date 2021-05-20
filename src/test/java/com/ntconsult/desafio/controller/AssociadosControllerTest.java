package com.ntconsult.desafio.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ntconsult.desafio.domain.model.Associado;
import com.ntconsult.desafio.domain.repository.AssociadoRepository;
import com.ntconsult.desafio.domain.service.CadastroAssociadoService;

@SpringBootTest
class AssociadosControllerTest {

	private final AssociadoRepository associadoRepository;
	private final CadastroAssociadoService cadastroAssociadoService;

	@Autowired
	public AssociadosControllerTest(AssociadoRepository associadoRepository,
			CadastroAssociadoService cadastroAssociadoService) {
		this.associadoRepository = associadoRepository;
		this.cadastroAssociadoService = cadastroAssociadoService;
	}

	@Test
	void testCriarAssociadoNovo() {
		Associado associado = new Associado("Nome associado", "11111111111");
		Associado associadoNovo = cadastroAssociadoService.salvar(associado);
		assertEquals(associado, associadoNovo);
	}

	@Test
	void testListarAssociados() {
		List<Associado> listAssociados = associadoRepository.findAll();
		assertThat(listAssociados).isNotEmpty();
		assertThat(listAssociados).isNotNull();
	}

	@Test
	public void testBuscarAssociadoPorId() {
		Associado associado1 = new Associado("Nome associado", "22222222222");
		Associado product2 = associadoRepository.save(associado1);
		Optional<Associado> associadoOptional = associadoRepository.findById(product2.getId());
		assertEquals(product2.getId(), associadoOptional.get().getId());
		assertEquals(product2.getNome(), associadoOptional.get().getNome());
	}

	@Test
	void testAtualizarAssociadoLong() {
		Associado associado1 = new Associado("Nome associado", "33333333333");
		Associado associado2 = associadoRepository.save(associado1);
		associado2.setNome("Nome associado Atualizado");
		Associado associadoNovo = cadastroAssociadoService.salvar(associado2);
		assertEquals(associado2, associadoNovo);
	}

	@Test
	public void testExcluirAssociadoLong() {
		Associado associado = new Associado("Nome associado", "44444444444");

		cadastroAssociadoService.salvar(associado);
		cadastroAssociadoService.excluir(associado.getId());
		Optional<Associado> associadoOptional = associadoRepository.findById(associado.getId());
		assertEquals(Optional.empty(), associadoOptional);
	}

}

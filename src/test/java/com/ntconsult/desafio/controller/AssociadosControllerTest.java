package com.ntconsult.desafio.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.ntconsult.desafio.domain.exception.VotacaoException;
import com.ntconsult.desafio.domain.model.Associado;
import com.ntconsult.desafio.domain.repository.AssociadoRepository;
import com.ntconsult.desafio.domain.service.CadastroAssociadoService;

@SpringBootTest
class AssociadosControllerTest {

	private final AssociadoRepository associadoRepository;
	private final CadastroAssociadoService cadastroAssociadoService;
	private static String cpf = "";

	@Autowired
	public AssociadosControllerTest(AssociadoRepository associadoRepository,
			CadastroAssociadoService cadastroAssociadoService) {
		this.associadoRepository = associadoRepository;
		this.cadastroAssociadoService = cadastroAssociadoService;
	}

	@Test
	void testCriarAssociadoNovo() {
		Associado associado = new Associado();
		associado.setNome("Nome associado");
		associado.setCpf(cpf);
		Associado associadoNovo = cadastroAssociadoService.salvar(associado);
		assertEquals(associado, associadoNovo);
	}

	@Test
	void testCriarAssociadoJaExistente() {
		Associado associado = new Associado();
		associado.setNome("Nome associado");
		associado.setCpf("40752682012");
		assertThrows(VotacaoException.class, () -> cadastroAssociadoService.salvar(associado));
	}

	@Test
	void testListarAssociados() {
		List<Associado> listAssociados = associadoRepository.findAll();
		assertThat(listAssociados).isNotEmpty();
		assertThat(listAssociados).isNotNull();
	}

	@Test
	void testBuscarAssociado() {
		Optional<Associado> associado = associadoRepository.findById(1L);

		assertEquals(1L, associado.get().getId());

	}

	@Test
	void testAtualizarAssociadoLong() {
		Associado associado = new Associado();
		associado.setId(12L);
		associado.setNome("Nome associado Atualizado");
		associado.setCpf("40752682012");
		Associado associadoNovo = cadastroAssociadoService.salvar(associado);
		assertEquals(associado, associadoNovo);
	}

	@Test
	void testExcluirAssociadoLong() {
		assertThrows(EmptyResultDataAccessException.class, () -> cadastroAssociadoService.excluir(5L));
	}

}

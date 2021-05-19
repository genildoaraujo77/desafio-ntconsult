package com.ntconsult.desafio.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ntconsult.desafio.domain.model.Associado;
import com.ntconsult.desafio.domain.repository.AssociadoRepository;
import com.ntconsult.desafio.domain.service.CadastroAssociadoService;

@RestController
@RequestMapping("/associados")
public class AssociadosController {
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Autowired
	private CadastroAssociadoService cadastroAssociadoService;
	
	@GetMapping
	public List<Associado> listar() {
		return associadoRepository.findAll();
	}
	
	@GetMapping("/{associadoId}")
	public ResponseEntity<Associado> buscar(@PathVariable Long associadoId) {
		Optional<Associado> associado = associadoRepository.findById(associadoId);
		
		if(associado.isPresent()) {
			return ResponseEntity.ok(associado.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Associado criarAssociado(@Valid @RequestBody Associado associado) {
		return cadastroAssociadoService.salvar(associado);
	}
	
	@PutMapping("/{associadoId}")
	public ResponseEntity<Associado> atualizarAssociado(@Valid @PathVariable Long associadoId, @RequestBody Associado associado){
		if(!associadoRepository.existsById(associadoId)) {
			return ResponseEntity.notFound().build();
		}
		
		associado.setId(associadoId);
		associado = cadastroAssociadoService.salvar(associado);
		
		return ResponseEntity.ok(associado);
	}
	
	@DeleteMapping("/{associadoId}")
	public ResponseEntity<Void> excluirAssociado(@PathVariable Long associadoId){
		
		if(!associadoRepository.existsById(associadoId)) {
			return ResponseEntity.notFound().build();
		}
		
		cadastroAssociadoService.excluir(associadoId);
		
		return ResponseEntity.noContent().build();
	}
}

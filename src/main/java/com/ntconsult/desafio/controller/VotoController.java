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

import com.ntconsult.desafio.domain.model.Voto;
import com.ntconsult.desafio.domain.repository.VotoRepository;
import com.ntconsult.desafio.domain.service.CadastroVotoService;

@RestController
@RequestMapping("/votos")
public class VotoController {
	
	@Autowired
	private VotoRepository votoRepository;
	
	@Autowired
	private CadastroVotoService cadastroVotoService;
	
	@GetMapping
	public List<Voto> listar() {
		return votoRepository.findAll();
	}
	
	@GetMapping("/{votoId}")
	public ResponseEntity<Voto> buscar(@PathVariable Long votoId) {
		Optional<Voto> voto = votoRepository.findById(votoId);
		
		if(voto.isPresent()) {
			return ResponseEntity.ok(voto.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Voto criarVoto(@Valid @RequestBody Voto voto) {
		return cadastroVotoService.salvar(voto);
	}
	
	@PutMapping("/{votoId}")
	public ResponseEntity<Voto> atualizarVoto(@Valid @PathVariable Long votoId, @RequestBody Voto voto){
		if(!votoRepository.existsById(votoId)) {
			return ResponseEntity.notFound().build();
		}
		
		voto.setId(votoId);
		voto = cadastroVotoService.salvar(voto);
		
		return ResponseEntity.ok(voto);
	}
	
	@DeleteMapping("/{votoId}")
	public ResponseEntity<Void> atualizarVoto(@PathVariable Long votoId){
		
		if(!votoRepository.existsById(votoId)) {
			return ResponseEntity.notFound().build();
		}
		
		cadastroVotoService.excluir(votoId);
		
		return ResponseEntity.noContent().build();
	}
}

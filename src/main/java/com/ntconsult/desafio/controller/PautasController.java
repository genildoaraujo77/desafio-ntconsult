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

import com.ntconsult.desafio.domain.model.Pauta;
import com.ntconsult.desafio.domain.repository.PautaRepository;
import com.ntconsult.desafio.domain.service.CadastroPautaService;

@RestController
@RequestMapping("/pautas")
public class PautasController {
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private CadastroPautaService cadastroPautaService;
	
	@GetMapping
	public List<Pauta> listar() {
		return pautaRepository.findAll();
	}
	
	@GetMapping("/{pautaId}")
	public ResponseEntity<Pauta> buscar(@PathVariable Long pautaId) {
		Optional<Pauta> pauta = pautaRepository.findById(pautaId);
		
		if(pauta.isPresent()) {
			return ResponseEntity.ok(pauta.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pauta criarPauta(@Valid @RequestBody Pauta pauta) {
		return cadastroPautaService.salvar(pauta);
	}
	
	@PutMapping("/{pautaId}")
	public ResponseEntity<Pauta> atualizarPauta(@Valid @PathVariable Long pautaId, @RequestBody Pauta pauta){
		if(!pautaRepository.existsById(pautaId)) {
			return ResponseEntity.notFound().build();
		}
		
		pauta.setId(pautaId);
		pauta = cadastroPautaService.salvar(pauta);
		
		return ResponseEntity.ok(pauta);
	}
	
	@DeleteMapping("/{pautaId}")
	public ResponseEntity<Void> deletaPauta(@PathVariable Long pautaId){
		
		if(!pautaRepository.existsById(pautaId)) {
			return ResponseEntity.notFound().build();
		}
		
		cadastroPautaService.excluir(pautaId);
		
		return ResponseEntity.noContent().build();
	}
}

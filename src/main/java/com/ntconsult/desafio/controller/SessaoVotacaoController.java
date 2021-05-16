package com.ntconsult.desafio.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ntconsult.desafio.domain.model.SessaoVotacao;
import com.ntconsult.desafio.domain.repository.SessaoVotacaoRepository;
import com.ntconsult.desafio.domain.service.SessaoVotacaoService;
import com.ntconsult.desafio.model.SessaoVotacaoModel;

@RestController
@RequestMapping("/sessoes-votacao")
public class SessaoVotacaoController {
	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;
	
	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SessaoVotacaoModel criar(@Valid @RequestBody SessaoVotacao sessaoVotacao) {
		return toModel(sessaoVotacaoService.criar(sessaoVotacao));
	}
	
	@GetMapping
	public List<SessaoVotacaoModel> listar() {
		return toCollectionModel(sessaoVotacaoRepository.findAll());
	}
	
	@GetMapping("/{sessaoId}")
	public ResponseEntity<SessaoVotacaoModel> buscar(@PathVariable Long sessaoId) {
		Optional<SessaoVotacao> sessaoVotacao = sessaoVotacaoRepository.findById(sessaoId);
		
		if(sessaoVotacao.isPresent()) {
			SessaoVotacaoModel sessaoVotacaoModel = toModel(sessaoVotacao.get());
			return ResponseEntity.ok(sessaoVotacaoModel);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	private SessaoVotacaoModel toModel(SessaoVotacao sessaoVotacao) {
		return modelMapper.map(sessaoVotacao, SessaoVotacaoModel.class);
	}
	
	private List<SessaoVotacaoModel> toCollectionModel(List<SessaoVotacao> sessoesVotacao){
		return sessoesVotacao.stream()
				.map(sessaoVotacao -> toModel(sessaoVotacao))
				.collect(Collectors.toList());
	}
	
//	@PutMapping("/{sessaoId}")
//	public ResponseEntity<SessaoVotacao> atualizarSessaoVotacao(@Valid @PathVariable Long sessaoId, @RequestBody SessaoVotacao sessaoVotacao){
//		if(!sessaoVotacaoRepository.existsById(sessaoId)) {
//			return ResponseEntity.notFound().build();
//		}
//		
//		sessaoVotacao.setId(sessaoId);
//		sessaoVotacao = sessaoVotacaoService.atualizar(sessaoVotacao);
//		
//		return ResponseEntity.ok(sessaoVotacao);
//	}
}

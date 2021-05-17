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
import com.ntconsult.desafio.domain.repository.VotoRepository;
import com.ntconsult.desafio.domain.service.SessaoVotacaoService;
import com.ntconsult.desafio.model.SessaoVotacaoInput;
import com.ntconsult.desafio.model.SessaoVotacaoModel;

@RestController
@RequestMapping("/sessoes-votacao")
public class SessaoVotacaoController {
	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;
	
	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	@Autowired
	private VotoRepository votoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SessaoVotacaoModel criar(@Valid @RequestBody SessaoVotacaoInput sessaoVotacaoInput) {
		SessaoVotacao sessaoVotacao = toEntity(sessaoVotacaoInput);
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
	
//	@PutMapping("/{sessaoVotacaoId}/finalizacao")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void finalizar(@PathVariable Long sessaoVotacaoId){
//		sessaoVotacaoService.finalizar(sessaoVotacaoId);
//	}
	
	private SessaoVotacaoModel toModel(SessaoVotacao sessaoVotacao) {
		sessaoVotacao.qtdVotos();
		return modelMapper.map(sessaoVotacao, SessaoVotacaoModel.class);
	}
	
	private List<SessaoVotacaoModel> toCollectionModel(List<SessaoVotacao> sessoesVotacao){
		return sessoesVotacao.stream()
				.map(sessaoVotacao -> toModel(sessaoVotacao))
				.collect(Collectors.toList());
	}
	
	private SessaoVotacao toEntity(SessaoVotacaoInput sessaoVotacaoInput) {
		return modelMapper.map(sessaoVotacaoInput, SessaoVotacao.class);
	}
	
}

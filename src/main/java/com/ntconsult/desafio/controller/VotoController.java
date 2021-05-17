package com.ntconsult.desafio.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import com.ntconsult.desafio.domain.exception.EntidadeNaoEncontradaException;
import com.ntconsult.desafio.domain.model.SessaoVotacao;
import com.ntconsult.desafio.domain.model.Voto;
import com.ntconsult.desafio.domain.repository.SessaoVotacaoRepository;
import com.ntconsult.desafio.domain.repository.VotoRepository;
import com.ntconsult.desafio.domain.service.CadastroVotoService;
import com.ntconsult.desafio.domain.service.SessaoVotacaoService;
import com.ntconsult.desafio.model.VotoInput;
import com.ntconsult.desafio.model.VotoModel;

@RestController
@RequestMapping("/sessoes-votacao/{sessaoVotacaoId}/votos")
public class VotoController {
	
	@Autowired
	private VotoRepository votoRepository;
	
	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;
	
	@Autowired
	private CadastroVotoService cadastroVotoService;
	
	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<VotoModel> listar(@PathVariable Long sessaoVotacaoId) {
		SessaoVotacao sessaoVotacao = sessaoVotacaoRepository.findById(sessaoVotacaoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Sessao de votação não encontrada"));

		return toCollectionModel(sessaoVotacao.getVotos());
	}
	
	@GetMapping("/{votoId}")
	public ResponseEntity<VotoModel> buscar(@PathVariable Long votoId) {
		Optional<Voto> voto = votoRepository.findById(votoId);
		
		if(voto.isPresent()) {
			return ResponseEntity.ok(toModel(voto.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public VotoModel criarVoto(@Valid @RequestBody Voto voto) {
//		return toModel(cadastroVotoService.salvar(voto));
//	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VotoModel criarVoto(@PathVariable Long sessaoVotacaoId, 
			@Valid @RequestBody VotoInput votoInput) {
		Voto voto = sessaoVotacaoService.votar(sessaoVotacaoId, votoInput.getPauta().getId(), votoInput.getAssociado().getId(), votoInput.getSimounao());
		return toModel(voto);
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
	public ResponseEntity<Void> deletarVoto(@PathVariable Long votoId){
		
		if(!votoRepository.existsById(votoId)) {
			return ResponseEntity.notFound().build();
		}
		
		cadastroVotoService.excluir(votoId);
		
		return ResponseEntity.noContent().build();
	}
	
	private VotoModel toModel(Voto voto) {
		return modelMapper.map(voto, VotoModel.class);
	}
	
	private List<VotoModel> toCollectionModel(List<Voto> votos){
		return votos.stream()
				.map(voto -> toModel(voto))
				.collect(Collectors.toList());
	}
}

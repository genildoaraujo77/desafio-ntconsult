package com.ntconsult.desafio.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntconsult.desafio.domain.exception.VotacaoException;
import com.ntconsult.desafio.domain.model.Pauta;
import com.ntconsult.desafio.domain.model.SessaoVotacao;
import com.ntconsult.desafio.domain.model.StatusSessao;
import com.ntconsult.desafio.domain.repository.AssociadoRepository;
import com.ntconsult.desafio.domain.repository.PautaRepository;
import com.ntconsult.desafio.domain.repository.SessaoVotacaoRepository;

@Service
public class SessaoVotacaoService {
	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	@Autowired
	private AssociadoRepository associadoRepository;

	@Autowired
	private PautaRepository pautaRepository;

	public SessaoVotacao criar(SessaoVotacao sessaoVotacao) {
		Pauta pauta = pautaRepository.findById(sessaoVotacao.getPauta().getId())
				.orElseThrow(() -> new VotacaoException("Pauta não encontrada"));
		
		SessaoVotacao sessao = sessaoVotacaoRepository.findByPauta(sessaoVotacao.getPauta().getId());
		
		if(sessao != null && !sessao.equals(sessaoVotacao)) {
			throw new VotacaoException("Já existe uma sessão para esta pauta.");
		}

		sessaoVotacao.setPauta(pauta);

//		List<Associado> associados = new ArrayList<Associado>();
//		
//		if(sessaoVotacao.getAssociados() != null && !sessaoVotacao.getAssociados().isEmpty()) {
//			for (Associado associado : sessaoVotacao.getAssociados()) {
//				associado = associadoRepository.findById(associado.getId()).orElse(null);
//				if(associado != null) {
//					associados.add(associado);
//				}
//			}
//		}
		
		
//		sessaoVotacao.setAssociados(associados);
		sessaoVotacao.setStatus(StatusSessao.ABERTA);
		sessaoVotacao.setDataAbertura(OffsetDateTime.now());

		return sessaoVotacaoRepository.save(sessaoVotacao);
	}
}

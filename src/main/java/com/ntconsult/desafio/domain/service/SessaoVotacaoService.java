package com.ntconsult.desafio.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntconsult.desafio.domain.exception.EntidadeNaoEncontradaException;
import com.ntconsult.desafio.domain.exception.VotacaoException;
import com.ntconsult.desafio.domain.model.Associado;
import com.ntconsult.desafio.domain.model.Pauta;
import com.ntconsult.desafio.domain.model.SessaoVotacao;
import com.ntconsult.desafio.domain.model.StatusSessao;
import com.ntconsult.desafio.domain.model.Voto;
import com.ntconsult.desafio.domain.repository.AssociadoRepository;
import com.ntconsult.desafio.domain.repository.PautaRepository;
import com.ntconsult.desafio.domain.repository.SessaoVotacaoRepository;
import com.ntconsult.desafio.model.utils.Utils;

@Service
public class SessaoVotacaoService {
	private static final Logger LOG = LoggerFactory.getLogger(SessaoVotacaoService.class);
	
	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Autowired
	private CadastroVotoService cadastroVotoService;

	public SessaoVotacao criar(SessaoVotacao sessaoVotacao) {
		Pauta pauta = pautaRepository.findById(sessaoVotacao.getPauta().getId())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Pauta não encontrada"));
		
		SessaoVotacao sessao = sessaoVotacaoRepository.findByPauta(sessaoVotacao.getPauta().getId());
		
		if(sessao != null && !sessao.equals(sessaoVotacao)) {
			throw new VotacaoException("Já existe uma sessão para esta pauta.");
		}

		sessaoVotacao.setPauta(pauta);

		sessaoVotacao.setStatus(StatusSessao.ABERTA);
		
		OffsetDateTime dataAbertura = OffsetDateTime.now();
		sessaoVotacao.setDataAbertura(dataAbertura);
		
		OffsetDateTime dataExpiracao = Utils.criaDataExpiracao(sessaoVotacao.getTempoSessaoVotacao(), dataAbertura);
		
		sessaoVotacao.setDataFinalizacao(dataExpiracao);
		
		long time = (sessaoVotacao.getTempoSessaoVotacao() * 1000) * 60;
		
		finalizarSessaoVotacao(time);

		return sessaoVotacaoRepository.save(sessaoVotacao);
	}
	
	public void finalizarSessaoVotacao(long time) {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				finalizar(OffsetDateTime.now());
			}
		};
		long timeLong = time;
		timer.schedule(task, timeLong);
	}
	
	public void finalizar(OffsetDateTime offsetDateTime) {
		int minuto = offsetDateTime.getMinute();
		int hora = offsetDateTime.getHour();
		int dia = offsetDateTime.getDayOfMonth();
		int mes = offsetDateTime.getMonthValue();
		int ano = offsetDateTime.getYear();
		List<SessaoVotacao> sessoesVotacao = sessaoVotacaoRepository.findByStatus("ABERTA");
		if(sessoesVotacao != null && !sessoesVotacao.isEmpty()) {
		   for (SessaoVotacao sessaoVotacao : sessoesVotacao) {
			   if(ano == sessaoVotacao.getDataFinalizacao().getYear() && mes == sessaoVotacao.getDataFinalizacao().getMonthValue() && 
					   dia == sessaoVotacao.getDataFinalizacao().getDayOfMonth() && hora == sessaoVotacao.getDataFinalizacao().getHour() && 
					   minuto == sessaoVotacao.getDataFinalizacao().getMinute()) {
				sessaoVotacao.finalizar();
			   sessaoVotacaoRepository.save(sessaoVotacao);
			  }
		}	
		}
		
	}
	
	public Voto votar(Long sessaoVotacaoId,Long pautaId, Long associadoId, Boolean simounao) {
		SessaoVotacao sessaoVotacao = buscar(sessaoVotacaoId);
		
		Pauta pauta = buscarPauta(pautaId);
		
		Associado associado = buscarAssociado(associadoId);
		
		if(sessaoVotacao.getStatus().equals(StatusSessao.FINALIZADA)) {
			throw new VotacaoException("Está seção foi encerrada as: " + sessaoVotacao.getDataFinalizacao());
		}
		
		if(!sessaoVotacao.getPauta().equals(pauta)) {
			throw new VotacaoException("Está pauta não está sendo votada nesta sessão");
		}
		
		Voto voto = new Voto();
		voto.setPauta(pauta);
		voto.setAssociado(associado);
		voto.setSimounao(simounao);
		voto.setSessaoVotacao(sessaoVotacao);
		voto.setDataEnvio(OffsetDateTime.now());
		
		return cadastroVotoService.salvar(voto);
	}

	private Associado buscarAssociado(Long associadoId) {
		return associadoRepository.findById(associadoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Associado não encontrado"));
	}

	private Pauta buscarPauta(Long pautaId) {
		return pautaRepository.findById(pautaId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Pauta não encontrada"));
	}

	private SessaoVotacao buscar(Long sessaoVotacaoId) {
		return sessaoVotacaoRepository.findById(sessaoVotacaoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Sessao não encontrada"));
	}
}

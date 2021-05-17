package com.ntconsult.desafio.domain.service;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
import com.ntconsult.desafio.domain.repository.ResultadoRepository;
import com.ntconsult.desafio.domain.repository.SessaoVotacaoRepository;
import com.ntconsult.desafio.domain.repository.VotoRepository;
import com.ntconsult.desafio.model.Resultado;
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

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private ResultadoRepository resultadoRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void warmup() throws IOException, InterruptedException, ServletException {
		System.out.println("Código executado logo após a aplicação ser startada");
		verificaSessoes();
	}

	public SessaoVotacao criar(SessaoVotacao sessaoVotacao) {
		Pauta pauta = buscarPauta(sessaoVotacao.getPauta().getId());

		SessaoVotacao sessao = sessaoVotacaoRepository.findByPauta(sessaoVotacao.getPauta().getId());

		if (sessao != null && !sessao.equals(sessaoVotacao)) {
			LOG.info("Já existe uma sessão para esta pauta.");
			throw new VotacaoException("Já existe uma sessão para esta pauta.");
		}

		sessaoVotacao.setPauta(pauta);

		sessaoVotacao.setStatus(StatusSessao.ABERTA);

		OffsetDateTime dataAbertura = OffsetDateTime.now();
		sessaoVotacao.setDataAbertura(dataAbertura);

		OffsetDateTime dataExpiracao = Utils.criaDataExpiracao(sessaoVotacao.getTempoSessaoVotacao(), dataAbertura);

		sessaoVotacao.setDataFinalizacao(dataExpiracao);

//		long time = (sessaoVotacao.getTempoSessaoVotacao() * 1000) * 60;
		long epochMilli = sessaoVotacao.getDataFinalizacao().toInstant().toEpochMilli();
		Date date = new Date(epochMilli);

//		finalizarSessaoVotacao(time);
		finalizarSessaoVotacao(date);

		return sessaoVotacaoRepository.save(sessaoVotacao);
	}

//	public void finalizarSessaoVotacao(long time) {
//		Timer timer = new Timer();
//		TimerTask task = new TimerTask() {
//
//			@Override
//			public void run() {
//				finalizar(OffsetDateTime.now());
//			}
//		};
//		long timeLong = time;
//		timer.schedule(task, timeLong);
//	}

	private void verificaSessoes() {
		List<SessaoVotacao> sessoesVotacao = sessaoVotacaoRepository.findByStatus("ABERTA");

		if (sessoesVotacao != null && !sessoesVotacao.isEmpty()) {
			for (SessaoVotacao sessaoVotacao : sessoesVotacao) {

				OffsetDateTime offsetDateTime = OffsetDateTime.now();
				if (offsetDateTime.isAfter(sessaoVotacao.getDataFinalizacao()) || offsetDateTime.isEqual(sessaoVotacao.getDataFinalizacao())) {
					sessaoVotacao.finalizar();
					Resultado resultado = contabilizarVotos(sessaoVotacao);
					resultado = resultadoRepository.save(resultado);
					sessaoVotacao.setResultado(resultado);
					sessaoVotacaoRepository.save(sessaoVotacao);
				} else {
					long epochMilli = sessaoVotacao.getDataFinalizacao().toInstant().toEpochMilli();
					Date date = new Date(epochMilli);

					finalizarSessaoVotacao(date);
				}

			}
		}
	}

	public void finalizarSessaoVotacao(Date time) {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				finalizar(OffsetDateTime.now());
			}
		};
		Date timeLong = time;
		timer.schedule(task, timeLong);
	}

	public void finalizar(OffsetDateTime offsetDateTime) {
		List<SessaoVotacao> sessoesVotacao = sessaoVotacaoRepository.findByStatus("ABERTA");
		if (sessoesVotacao != null && !sessoesVotacao.isEmpty()) {
			for (SessaoVotacao sessaoVotacao : sessoesVotacao) {
				if (offsetDateTime.isAfter(sessaoVotacao.getDataFinalizacao()) || offsetDateTime.isEqual(sessaoVotacao.getDataFinalizacao())) {
					sessaoVotacao.finalizar();
					Resultado resultado = contabilizarVotos(sessaoVotacao);
					resultado = resultadoRepository.save(resultado);
					sessaoVotacao.setResultado(resultado);
					sessaoVotacaoRepository.save(sessaoVotacao);
				}
			}
		}

	}

	public Resultado contabilizarVotos(SessaoVotacao sessaoVotacao) {
		List<Voto> votos = buscarVotos(sessaoVotacao.getId());
		Resultado resultado = new Resultado();
		if (votos != null && !votos.isEmpty()) {
			int nao = 0;
			int sim = 0;
			for (Voto voto : votos) {
				if (voto.getSimounao()) {
					sim++;
					resultado.setSim(sim);
				} else {
					nao++;
					resultado.setNao(nao);
				}
			}
			if(sim > nao) {
				resultado.setTotal(StatusSessao.APROVADA);
			}
			if(sim < nao) {
				resultado.setTotal(StatusSessao.REPROVADA);
			}
			if(sim == nao) {
				resultado.setTotal(StatusSessao.EMPATADA);
			}
		}
		return resultado;
	}

	public Voto votar(Long sessaoVotacaoId, Long pautaId, Long associadoId, Boolean simounao) {
		SessaoVotacao sessaoVotacao = buscar(sessaoVotacaoId);

		Pauta pauta = buscarPauta(pautaId);

		Associado associado = buscarAssociado(associadoId);

		if (sessaoVotacao.getStatus().equals(StatusSessao.FINALIZADA)) {
			LOG.info("Está seção foi encerrada as: " + sessaoVotacao.getDataFinalizacao());
			throw new VotacaoException("Está seção foi encerrada as: " + sessaoVotacao.getDataFinalizacao());
		}

		if (!sessaoVotacao.getPauta().equals(pauta)) {
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

	public List<Voto> buscarVotos(Long sessaoVotacaoId) {
		return votoRepository.findByIdSessao(sessaoVotacaoId);
	}

}

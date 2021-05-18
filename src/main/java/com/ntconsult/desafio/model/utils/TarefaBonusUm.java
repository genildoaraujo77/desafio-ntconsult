package com.ntconsult.desafio.model.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ntconsult.desafio.domain.exception.VotacaoException;
import com.ntconsult.desafio.model.StatusAssociado;

@Component
public class TarefaBonusUm {

	private static final Logger LOG = LoggerFactory.getLogger(TarefaBonusUm.class);

	public static String BASE_TASK1;

	@Value("${ntconsult.urltask1}")
	public void setWehtherUrlTask1(String url) {
		BASE_TASK1 = url;
	}
	
	public static StatusAssociado validarCpf(String cpf) {

		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<StatusAssociado> response = restTemplate.exchange(
					BASE_TASK1 + cpf, HttpMethod.GET, null,
					new ParameterizedTypeReference<StatusAssociado>() {
					});
				return response.getBody();
			
		} catch (HttpClientErrorException e) {
			if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				LOG.info("CPF: "+ cpf +" inválido");
				throw new VotacaoException("Cpf inválido");
			}
		}
		return null;

	}
}

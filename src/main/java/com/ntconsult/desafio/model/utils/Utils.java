package com.ntconsult.desafio.model.utils;

import java.time.OffsetDateTime;

public class Utils {
	
	 public static OffsetDateTime criaDataExpiracao(long tempoSessaoVotacao, OffsetDateTime emissao) {
		  
		 OffsetDateTime dataExpiracao = emissao.plusMinutes(tempoSessaoVotacao);
		 
		 return dataExpiracao;
			
	    }
	 
}

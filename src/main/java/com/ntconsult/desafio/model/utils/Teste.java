package com.ntconsult.desafio.model.utils;

import java.time.OffsetDateTime;

public class Teste {

	public static void main(String[] args) {
		Utils.criaDataExpiracao(5, OffsetDateTime.now());
	}

}

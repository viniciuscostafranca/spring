package io.vinicius.rest.controller;

import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.Getter;

@Data
public class ApiErrors {
	
	@Getter
	private List<String> errors;
	
	public ApiErrors (String mensagemErro) {
		this.errors = Arrays.asList(mensagemErro);
	}
	public ApiErrors (List<String> mensagensErro) {
		this.errors =mensagensErro;
	}

}

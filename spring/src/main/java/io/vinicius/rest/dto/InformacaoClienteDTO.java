package io.vinicius.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InformacaoClienteDTO {
	private int codigo;
	private String nome;
	private String cpf;
	private int idade;
	private String status;
}

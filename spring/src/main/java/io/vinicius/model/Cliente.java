package io.vinicius.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.vinicius.util.DateUtil;
import io.vinicius.validation.JsonDateDeserializer;
import io.vinicius.validation.JsonDateSerializer;
import io.vinicius.validation.ValidDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "CLIENTE")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;

	@Column(name = "nome", length = 100)
	@NotEmpty(message = "{campo.nome.obrigatorio}")
	private String nome;

	@Column(name = "cpf", length = 11,unique = true)
	@NotEmpty(message = "{campo.cpf.obrigatorio}")
	@CPF(message = "{campo.cpf.invalido}")
	private String cpf;

	@Column(name = "data_nascimento")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@ValidDate(message = "{campo.datanascimento.invalida}")
	@Past(message = "{campo.datanascimento.maior.data.atual}")
	@JsonDeserialize(using=JsonDateDeserializer.class)
	@JsonSerialize(using=JsonDateSerializer.class)
	private Date dataNascimento;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusCliente status;
	
	
	public String getStatusString() {
		if(status != null) {
			return status.name();
		}
		return "";
	}
	
	public int minhaIdade() {
		return DateUtil.getDiffYears(getDataNascimento(),new Date());
		
	}

}

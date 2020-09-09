package io.vinicius.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import io.vinicius.model.Cliente;

public interface Clientes  extends JpaRepository<Cliente, Integer>{


	List<Cliente> findAllByNomeOrCpf(String nome, String cpf, Pageable pageable);
	
	boolean existsByCpf(String cpf);

}
package io.vinicius.service;

import java.util.List;

import io.vinicius.model.Cliente;
import io.vinicius.rest.dto.InformacaoClienteDTO;

public interface ClienteService {

	List<InformacaoClienteDTO> converterClienteParaDTO(List<Cliente> clientes);

	InformacaoClienteDTO converterClienteParaDTO(Cliente cliente);

	void atualizaStatus(Integer id, String statusCliente);

	InformacaoClienteDTO getClientById(Integer id);

	InformacaoClienteDTO save(Cliente cliente);

	void delete(Integer id);

	void update(Integer id, Cliente cliente);

	List<InformacaoClienteDTO> find(Integer pageNo, Integer pageSize, Cliente filtro);
}

package io.vinicius.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import io.vinicius.config.InternacionalizacaoConfig;
import io.vinicius.exception.ClienteNaoEncontradoException;
import io.vinicius.model.Cliente;
import io.vinicius.model.StatusCliente;
import io.vinicius.repository.Clientes;
import io.vinicius.rest.dto.InformacaoClienteDTO;
import io.vinicius.service.ClienteService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {
	@Autowired
	private Clientes repository;
	@Autowired
	private InternacionalizacaoConfig internacionalizacaoConfig;

	@Override
	@Transactional
	public void atualizaStatus(Integer id, String statusCliente) {
		if (id != null && statusCliente != null) {
			repository.findById(id).map(pedido -> {
				pedido.setStatus(StatusCliente.valueOf(statusCliente));
				return repository.save(pedido);
			}).orElseThrow(
					() -> new ClienteNaoEncontradoException(internacionalizacaoConfig.get("cliente.nao.encontrado")));

		} else {
			throw new ClienteNaoEncontradoException(internacionalizacaoConfig.get("cliente.nao.encontrado"));
		}
	}

	@Override
	public InformacaoClienteDTO getClientById(Integer id) {
		if (id != null) {
			Cliente cliente = repository.findById(id).orElseThrow(
					() -> new ClienteNaoEncontradoException(internacionalizacaoConfig.get("cliente.nao.encontrado")));
			return converterClienteParaDTO(cliente);
		} else {
			throw new ClienteNaoEncontradoException(internacionalizacaoConfig.get("cliente.nao.encontrado"));
		}
	}

	@Override
	public InformacaoClienteDTO save(Cliente cliente) {
		if (cliente.getCpf() != null && !repository.existsByCpf(cliente.getCpf())) {
			cliente.setStatus(StatusCliente.ATIVO);
			return converterClienteParaDTO(repository.save(cliente));
		}
		throw new ClienteNaoEncontradoException(internacionalizacaoConfig.get("cpf.ja.cadastrado"));
	}

	@Override
	public void delete(Integer id) {
		if (id != null) {
			repository.findById(id).map(clienteExistente -> {
				repository.delete(clienteExistente);
				return clienteExistente;
			}).orElseThrow(
					() -> new ClienteNaoEncontradoException(internacionalizacaoConfig.get("cliente.nao.encontrado")));
		} else {
			throw new ClienteNaoEncontradoException(internacionalizacaoConfig.get("cliente.nao.encontrado"));
		}
	}

	@Override
	public void update(Integer id, Cliente cliente) {
		if (id != null) {
			repository.findById(id).map(clienteExistente -> {
				cliente.setId(clienteExistente.getId());
				repository.save(cliente);
				return clienteExistente;
			}).orElseThrow(
					() -> new ClienteNaoEncontradoException(internacionalizacaoConfig.get("cliente.nao.encontrado")));
		}else {
			throw new ClienteNaoEncontradoException(internacionalizacaoConfig.get("cliente.nao.encontrado"));
		}

	}

	@Override
	public List<InformacaoClienteDTO> find(Integer pageNo, Integer pageSize, Cliente filtro) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example example = Example.of(filtro, matcher);

		Page<Cliente> pagedResult = repository.findAll(example, PageRequest.of(pageNo, pageSize));

		if (pagedResult.hasContent()) {

			return converterClienteParaDTO(pagedResult.getContent());
		} else {
			return Collections.emptyList();
		}

	}

	@Override
	public List<InformacaoClienteDTO> converterClienteParaDTO(List<Cliente> itens) {
		if (CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		return itens
				.stream().map(item -> InformacaoClienteDTO.builder().codigo(item.getId()).nome(item.getNome())
						.cpf(item.getCpf()).idade(item.minhaIdade()).status(item.getStatusString()).build())
				.collect(Collectors.toList());
	}

	@Override
	public InformacaoClienteDTO converterClienteParaDTO(Cliente item) {
		return InformacaoClienteDTO.builder().codigo(item.getId()).nome(item.getNome()).cpf(item.getCpf())
				.idade(item.minhaIdade()).status(item.getStatusString()).build();

	}

}

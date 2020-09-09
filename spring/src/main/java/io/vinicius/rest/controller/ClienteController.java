package io.vinicius.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.vinicius.model.Cliente;
import io.vinicius.rest.dto.AtualizacaoStatusClienteDTO;
import io.vinicius.rest.dto.InformacaoClienteDTO;
import io.vinicius.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {


	private ClienteService clienteService;

	public ClienteController( ClienteService clienteService) {
	
		this.clienteService = clienteService;
	}

	@GetMapping("{id}")
	public InformacaoClienteDTO getClientById(@PathVariable Integer id) {
		return clienteService.getClientById(id);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public InformacaoClienteDTO save(@RequestBody @Valid Cliente cliente) {
		return clienteService.save(cliente);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		clienteService.delete(id);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, @RequestBody @Valid Cliente cliente) {
		clienteService.update(id, cliente);
	}

	@GetMapping
	public List<InformacaoClienteDTO> find(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, Cliente filtro) {
		return clienteService.find(pageNo, pageSize, filtro);

	}

	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusClienteDTO dto) {
		clienteService.atualizaStatus(id, dto.getNovoStatus());
	}

}

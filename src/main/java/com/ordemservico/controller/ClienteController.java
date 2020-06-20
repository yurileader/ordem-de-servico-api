package com.ordemservico.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordemservico.domain.model.Cliente;
import com.ordemservico.domain.service.CadastroClienteService;
import com.ordemservico.repository.ClienteRepository;

@RestController
@RequestMapping("clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService clienteService; 
	
	@GetMapping
	public List<Cliente> listar (){
		return clienteRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarId (@PathVariable Long id) {
		
		return this.clienteRepository.findById(id)
				.map(cliente -> ResponseEntity.ok(cliente))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Cliente> adicionar (@Valid @RequestBody Cliente cliente){
		
		Cliente clienteSalvo = clienteService.salvar(cliente);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}
	
	@DeleteMapping("/{id}")
	public void excluir (@PathVariable Long id) {
		clienteService.excluir(id);
	}
	
}

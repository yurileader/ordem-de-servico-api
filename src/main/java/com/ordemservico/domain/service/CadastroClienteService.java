package com.ordemservico.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ordemservico.domain.model.Cliente;
import com.ordemservico.repository.ClienteRepository;

@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public void excluir (Long id) {
		clienteRepository.deleteById(id);
	}
	
	public Cliente atualizar(Cliente cliente, Long id) {
		
		Cliente clienteSalvo = encontrarCliente(id);
		/**BeansUtils pode ser usado para ajudar a tratar os dados para atualziar
		 * Source: A fonte dos dados - no caso da classe pessoas
		 * target: Para onde irei mandar os dados - no caso para minha variavel pessoaSalva
		 * ignoreProperties: qual dado devo ignorar - no caso o codigo que é codigo*/
		//BeanUtils.copyProperties(source, target, ignoreProperties);
		BeanUtils.copyProperties(cliente, clienteSalvo, "id");
		
		return clienteRepository.save(clienteSalvo);
	}
	
	public Cliente encontrarCliente(Long id) {
		Cliente clienteSalvo = this.clienteRepository.findById(id)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		return clienteSalvo;
	}
}

package com.ordemservico.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordemservico.domain.model.Cliente;
import com.ordemservico.domain.model.OrdemServico;
import com.ordemservico.domain.model.StatusOrdemServico;
import com.ordemservico.repository.OrdemServicoRepository;

@Service
public class OrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private CadastroClienteService clienteService;
	
	public OrdemServico criar( OrdemServico ordemServico) {
		Cliente cliente = clienteService.encontrarCliente(ordemServico.getCliente().getId());
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
}

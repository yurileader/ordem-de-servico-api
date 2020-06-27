package com.ordemservico.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordemservico.domain.exception.EntidadeNaoEncontrada;
import com.ordemservico.domain.model.Cliente;
import com.ordemservico.domain.model.Comentario;
import com.ordemservico.domain.model.OrdemServico;
import com.ordemservico.domain.model.StatusOrdemServico;
import com.ordemservico.repository.ComentarioRepository;
import com.ordemservico.repository.OrdemServicoRepository;

@Service
public class OrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private CadastroClienteService clienteService;
	
	public OrdemServico criar( OrdemServico ordemServico) {
		Cliente cliente = clienteService.encontrarCliente(ordemServico.getCliente().getId());
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	public void finalizar (Long id) {
		OrdemServico ordemServico = buscarOrdemServico(id);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}
	
	public Comentario adicionarComentario (Long id, String descricao) {
		OrdemServico ordemServico = buscarOrdemServico(id);
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}

	private OrdemServico buscarOrdemServico(Long id) {
		return ordemServicoRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontrada());
	}
}

package com.ordemservico.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordemservico.domain.model.OrdemServico;
import com.ordemservico.domain.service.OrdemServicoService;
import com.ordemservico.event.HeaderLocationEvent;
import com.ordemservico.repository.OrdemServicoRepository;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<OrdemServico> buscarOrdem(){
		
		return ordemServicoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<OrdemServico> criarOrdem (@Valid @RequestBody OrdemServico ordemServico, HttpServletResponse response){
		
		OrdemServico ordemServicoSalvo = ordemServicoService.criar(ordemServico);
		
		publisher.publishEvent(new HeaderLocationEvent(this, response, ordemServicoSalvo.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(ordemServicoSalvo);
	}
	
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		ordemServicoRepository.deleteById(id);;
	}
	
	
}

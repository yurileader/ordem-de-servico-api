package com.ordemservico.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ordemservico.domain.model.OrdemServico;
import com.ordemservico.domain.service.OrdemServicoService;
import com.ordemservico.dto.OrdemServicoDto;
import com.ordemservico.dto.OrdemServicoInputDto;
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
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<OrdemServicoDto> buscarOrdem(){
		
		return toListDto(ordemServicoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrdemServicoDto> buscarOrdemId(@PathVariable Long id, HttpServletResponse response){
		OrdemServico ordemServico = ordemServicoRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
		OrdemServicoDto dto = toDto(ordemServico);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<OrdemServicoDto> criarOrdem (@Valid @RequestBody OrdemServicoInputDto ordemServicoInputDto, HttpServletResponse response){
		
		OrdemServico ordemServico = toEntity(ordemServicoInputDto);
		
		OrdemServico ordemServicoSalvo = ordemServicoService.criar(ordemServico);
		
		publisher.publishEvent(new HeaderLocationEvent(this, response, ordemServicoSalvo.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(toDto(ordemServicoSalvo));
	}
	
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		ordemServicoRepository.deleteById(id);;
	}
	
	
	@PutMapping("/{id}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long id) {
		ordemServicoService.finalizar(id);
	}
	
	private OrdemServicoDto toDto (OrdemServico ordemServico) {
		
		//ModelMaper atribui as propriedades de ordemServico para OrdemServicoDto //
		return modelMapper.map(ordemServico, OrdemServicoDto.class);
	}

	//Retorna um casting de List<OrdemServico> para OrdemServicoDto
	private List<OrdemServicoDto> toListDto (List<OrdemServico> ordensServico) {
		
		return ordensServico.stream()
				.map(ordemServico -> toDto (ordemServico))
				.collect(Collectors.toList());
	}
	
	private OrdemServico toEntity (OrdemServicoInputDto ordemServicoInputDto) {
		
		return modelMapper.map(ordemServicoInputDto, OrdemServico.class);
	}
}

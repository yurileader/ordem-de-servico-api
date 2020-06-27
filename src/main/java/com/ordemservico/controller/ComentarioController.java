package com.ordemservico.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ordemservico.domain.exception.EntidadeNaoEncontrada;
import com.ordemservico.domain.model.Comentario;
import com.ordemservico.domain.model.OrdemServico;
import com.ordemservico.domain.service.OrdemServicoService;
import com.ordemservico.dto.ComentarioDto;
import com.ordemservico.dto.ComentarioInputDto;
import com.ordemservico.repository.OrdemServicoRepository;

@RestController
@RequestMapping("/ordens-servico/{id}/comentarios")
public class ComentarioController {

	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<ComentarioDto> listar (@PathVariable Long  id){
		OrdemServico ordemServico = ordemServicoRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontrada());
		
		return toListDto(ordemServico.getComentarios());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioDto adicionar(@PathVariable Long id, @Valid @RequestBody ComentarioInputDto comentarioInputDto) {
		
		Comentario comentario = ordemServicoService.adicionarComentario(id, comentarioInputDto.getDescricao());
		
		
		return toDto(comentario);
	}
	
	
	
	
	private ComentarioDto toDto (Comentario comentario) {
		
		return modelMapper.map(comentario, ComentarioDto.class);
	}
	
	private List<ComentarioDto> toListDto(List<Comentario> comentarios) {
		
		return comentarios.stream
				().map(comentario -> toDto(comentario))
				.collect(Collectors.toList());
	}
}

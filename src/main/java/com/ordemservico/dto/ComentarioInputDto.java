package com.ordemservico.dto;

import javax.validation.constraints.NotBlank;

public class ComentarioInputDto {

	@NotBlank
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}

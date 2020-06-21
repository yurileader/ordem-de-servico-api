package com.ordemservico.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ordemservico.event.HeaderLocationEvent;

public class HeaderLocationListener implements ApplicationListener<HeaderLocationEvent> {

	@Override
	public void onApplicationEvent(HeaderLocationEvent event) {
		
		HttpServletResponse response = event.getResponse();
		Long id = event.getId();
		
		adicionarHeaderLocation(response, id);
	}
	
	private void adicionarHeaderLocation(HttpServletResponse response, Long id ) {
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(id).toUri();
		
		response.setHeader("Location", uri.toASCIIString());
	}

}

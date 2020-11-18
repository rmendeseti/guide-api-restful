package br.eti.rmendes.forumapi.controller.response;

import java.time.LocalDateTime;

import br.eti.rmendes.forumapi.modelo.Topico;

public class TopicoDTO {
	
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;

	public TopicoDTO(Topico topico) {
		
		this.id=topico.getId();
		this.titulo=topico.getTitulo();
		this.mensagem=topico.getMensagem();
		this.dataCriacao=topico.getDataCriacao();
		
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

}

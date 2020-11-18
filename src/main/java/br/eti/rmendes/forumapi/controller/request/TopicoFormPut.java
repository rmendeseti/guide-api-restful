package br.eti.rmendes.forumapi.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class TopicoFormPut {
	
	@NotNull @NotEmpty @Length(min = 10)
	private String titulo;
	@NotNull @NotEmpty @Length(min = 10)
	private String mensagem;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}

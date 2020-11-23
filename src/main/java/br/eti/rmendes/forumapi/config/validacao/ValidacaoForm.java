package br.eti.rmendes.forumapi.config.validacao;

public class ValidacaoForm {
	
	private String campo;
	private String mensagem;
	
	public ValidacaoForm(String campo, String mensagem) {
		this.campo = campo;
		this.mensagem = mensagem;
	}

	public String getCampo() {
		return campo;
	}

	public String getMensagem() {
		return mensagem;
	}

}

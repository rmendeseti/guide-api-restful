package br.eti.rmendes.forumapi.config.validacao;

public class ValidacaoForm {
	
	private String campo;
	private String erro;
	
	public ValidacaoForm(String campo, String erro) {
		this.campo = campo;
		this.erro = erro;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}

}

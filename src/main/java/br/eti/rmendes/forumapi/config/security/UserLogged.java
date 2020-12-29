package br.eti.rmendes.forumapi.config.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserLogged extends User {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String nome;
	private String email;
	private List<GrantedAuthority> permissoes;

	public UserLogged(long id, String nome, String email, List<GrantedAuthority> permissoes) {
		super(email, "", permissoes);
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.permissoes = permissoes;
	}

	public long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public List<GrantedAuthority> getPermissoes() {
		return permissoes;
	}

}

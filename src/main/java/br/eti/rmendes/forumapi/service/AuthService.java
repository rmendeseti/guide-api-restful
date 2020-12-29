package br.eti.rmendes.forumapi.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import br.eti.rmendes.forumapi.modelo.Usuario;

@Service
public class AuthService {
	
	private final AuthenticationManager authenticationManager;
	
	public AuthService(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public Usuario auth(String username, String password) throws AuthenticationException {
		
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);
		
		return (Usuario) this.authenticationManager.authenticate(auth).getPrincipal();
		
	}

}

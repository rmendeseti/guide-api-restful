package br.eti.rmendes.forumapi.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.eti.rmendes.forumapi.controller.request.LoginForm;
import br.eti.rmendes.forumapi.modelo.Usuario;
import br.eti.rmendes.forumapi.service.AuthService;
import br.eti.rmendes.forumapi.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	private final TokenService tokenService;
	
	public AuthController(AuthService authService, TokenService tokenService) {
		this.authService = authService;
		this.tokenService = tokenService;
	}
	
	@PostMapping
	public ResponseEntity<?> login(@RequestBody @Valid LoginForm form){
		
		try {
			
			Usuario usuarioLogado = authService.auth(form.getUsername(), form.getPassword());
			String token = tokenService.build(usuarioLogado);
			
			return ResponseEntity.ok(token);
			
		} catch (AuthenticationException e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Usuário ou senha inválida");
			
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu erro Servidor");
			
		}
		
	}

}

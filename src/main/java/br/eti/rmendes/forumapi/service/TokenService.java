package br.eti.rmendes.forumapi.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.eti.rmendes.forumapi.config.security.UserLogged;
import br.eti.rmendes.forumapi.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String EMAIL = "email";
    public static final String AUTORIZACOES = "autorizacoes";
	
	@Value("${app.jwt.secret}")
	private String secret;
	
	@Value("${app.jwt.expiration}")
	private String expiration;

	public String build(Usuario usuario) {
		
		Date dtExpiration = Date.from(LocalDateTime.now().plusHours(Long.parseLong(expiration)).atZone(ZoneId.systemDefault()).toInstant());
		
		return Jwts.builder()
			.setSubject(usuario.getEmail())
			.setIssuedAt(new Date())
			.setExpiration(dtExpiration)
			.claim(ID, usuario.getId())
			.claim(NOME, usuario.getNome())
			.claim(EMAIL, usuario.getEmail())
				.claim(AUTORIZACOES, usuario.getAuthorities()
						.stream()
						.map(autorizacao -> autorizacao.toString())
						.collect(Collectors.toList()))
			.signWith(SignatureAlgorithm.HS512, secret)
			.compact();
		
	}

	public void validate(String token) {
		
		Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		
	}

	@SuppressWarnings("unchecked")
	public Authentication getAuthentication(String token) {
		
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		
		List<GrantedAuthority> autorizacoes = new ArrayList<GrantedAuthority>();
		
		if(StringUtils.hasText(claims.get(AUTORIZACOES).toString())) {
			
			List<String> autorizacoesClaim = (List<String>) claims.get(AUTORIZACOES);
			
			autorizacoes = autorizacoesClaim.stream()
				.map(authority -> new SimpleGrantedAuthority(authority))
				.collect(Collectors.toList());
			
		}
		
		String id = claims.get(ID).toString();
		String nome = claims.get(NOME).toString();
		String email = claims.get(EMAIL).toString();
		
		UserLogged userLogged = new UserLogged(Long.parseLong(id), nome, email, autorizacoes);

		return new UsernamePasswordAuthenticationToken(userLogged, null, autorizacoes);
		
	}

}

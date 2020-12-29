package br.eti.rmendes.forumapi.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import br.eti.rmendes.forumapi.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTFilter extends OncePerRequestFilter {
	
	private final Logger log = LoggerFactory.getLogger(JWTFilter.class);

	private final TokenService tokenService;
	
	public JWTFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		String token = SecurityUtils.resolveToken(request);
		
		if(StringUtils.hasText(token)) {
			
			try {
				
				tokenService.validate(token);
				authenticate(token);
			
			} catch (SecurityException | MalformedJwtException e) {
	            log.info("Invalid JWT signature.");
	            log.trace("Invalid JWT signature trace: {}", e);
	            
	        } catch (ExpiredJwtException e) {
	        	log.info("Expired JWT token.");
	            log.trace("Expired JWT token trace: {}", e);
	        	
	        } catch (UnsupportedJwtException e) {
	            log.info("Unsupported JWT token.");
	            log.trace("Unsupported JWT token trace: {}", e);
	            
	        } catch (IllegalArgumentException e) {
	            log.info("JWT token compact of handler are invalid.");
	            log.trace("JWT token compact of handler are invalid trace: {}", e);
	            
	        }
			
		}
		
		chain.doFilter(request, response);
				
	}

	private void authenticate(String token) {
		
		Authentication authentication = this.tokenService.getAuthentication(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}

}

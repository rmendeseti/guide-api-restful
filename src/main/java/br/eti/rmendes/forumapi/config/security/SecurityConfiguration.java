package br.eti.rmendes.forumapi.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.eti.rmendes.forumapi.service.TokenService;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		
	private final DomainUserDetails domainUserDetails;
	private final TokenService tokenService;
	
	public SecurityConfiguration(DomainUserDetails domainUserDetails, TokenService tokenService) {
		this.domainUserDetails = domainUserDetails;
		this.tokenService = tokenService;
	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

//	utilizado para configurar autenticacao com repository ou outra forma de auth
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
			.userDetailsService(domainUserDetails)
			.passwordEncoder(new BCryptPasswordEncoder());
		
	}

//	utilizado para autorizaçoes
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/topicos").permitAll()
				.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
				.antMatchers(HttpMethod.POST, "/auth").permitAll()
				.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
			.anyRequest()
				.authenticated()
			.and()
	        .headers()
	        	.frameOptions()
	        	.disable()
	        .and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.addFilterBefore(new JWTFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
		
	}
	
//	trata acesso a arquivos staticos como js, css e html
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web
			.ignoring()
			.antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
		
	}
	
}

package br.eti.rmendes.forumapi.config.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.eti.rmendes.forumapi.modelo.Usuario;
import br.eti.rmendes.forumapi.repository.UsuarioRepository;

@Service
public class DomainUserDetails implements UserDetailsService {
	
	private final UsuarioRepository usuarioRepository;
	
	public DomainUserDetails(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
		
		if(usuario.isPresent()) return usuario.get();
		
		throw new UsernameNotFoundException("usuario nao encontrado");
		
	}

}

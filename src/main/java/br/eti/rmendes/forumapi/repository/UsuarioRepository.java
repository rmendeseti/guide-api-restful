package br.eti.rmendes.forumapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eti.rmendes.forumapi.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String username);

}

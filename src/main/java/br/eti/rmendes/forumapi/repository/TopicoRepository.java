package br.eti.rmendes.forumapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eti.rmendes.forumapi.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

//	@Query("select t from Topico t where t.curso.nome = :nomeCurso")
	List<Topico> findByCursoNome(String nomeCurso);
	
	/*
	 * Usado qndo existe ambiguidade com nome de atributo, para contornar oé usado para o jpa ir na entidade curso -> nome 
	 * List<Topico> findByCurso_Nome(String nomeCurso);
	 */

}

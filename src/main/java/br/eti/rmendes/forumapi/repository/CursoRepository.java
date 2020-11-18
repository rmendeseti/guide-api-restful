package br.eti.rmendes.forumapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eti.rmendes.forumapi.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nomeCurso);

}

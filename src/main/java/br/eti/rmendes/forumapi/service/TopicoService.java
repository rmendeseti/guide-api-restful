package br.eti.rmendes.forumapi.service;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.eti.rmendes.forumapi.controller.request.TopicoFormPost;
import br.eti.rmendes.forumapi.controller.request.TopicoFormPut;
import br.eti.rmendes.forumapi.controller.response.TopicoDTO;
import br.eti.rmendes.forumapi.modelo.Curso;
import br.eti.rmendes.forumapi.modelo.Topico;
import br.eti.rmendes.forumapi.repository.CursoRepository;
import br.eti.rmendes.forumapi.repository.TopicoRepository;

@Service
public class TopicoService {
	
	private final TopicoRepository topicoRepository;
	private final CursoRepository cursoRepository;
	
	public TopicoService(TopicoRepository topicoRepository, CursoRepository cursoRepository) {
		this.topicoRepository = topicoRepository;
		this.cursoRepository = cursoRepository;
	}

	public Page<TopicoDTO> listarByFilter(String nomeCurso, Pageable paginacao) {
		return topicoRepository.findByCursoNome(nomeCurso, paginacao).map(TopicoDTO::new);
	}

	public Page<TopicoDTO> listarTodos(Pageable paginacao) {
		return topicoRepository.findAll(paginacao).map(TopicoDTO::new);

	}

	public Topico novo(TopicoFormPost form) {
		
		Topico topico = new Topico();
		
		topico.setTitulo(form.getTitulo());
		topico.setMensagem(form.getMensagem());
		
		Curso curso = cursoRepository.findByNome(form.getNomeCurso());
		
		if(curso == null) 
			throw new EmptyResultDataAccessException(String.format("Curso %s não encontrado!", form.getNomeCurso()), 1);
		
		topico.setCurso(curso);
		
		return topicoRepository.save(topico);
		
	}

	public Topico atualizar(Long id, TopicoFormPut form) {
		
		Optional<Topico> topicoOp = listarById(id);
		
		Topico topico = topicoOp.get();
		topico.setTitulo(form.getTitulo());
		topico.setMensagem(form.getMensagem());
		
		return topicoRepository.save(topico);
			
		
	}

	public Optional<Topico> listarById(Long id) {
		
		Optional<Topico> topicoOp = topicoRepository.findById(id);
		
		if(!topicoOp.isPresent()) 
			throw new EmptyResultDataAccessException(String.format("Topico com [Id=%s], não encontrado!", id), 1);
		
		return topicoOp;
		
	}

	public void deletarById(Long id) {
		topicoRepository.deleteById(id);
	}

}

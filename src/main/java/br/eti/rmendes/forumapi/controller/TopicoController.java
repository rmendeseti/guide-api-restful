package br.eti.rmendes.forumapi.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.eti.rmendes.forumapi.controller.request.TopicoFormPost;
import br.eti.rmendes.forumapi.controller.request.TopicoFormPut;
import br.eti.rmendes.forumapi.controller.response.TopicoDTO;
import br.eti.rmendes.forumapi.controller.response.TopicoDetalheDTO;
import br.eti.rmendes.forumapi.modelo.Topico;
import br.eti.rmendes.forumapi.service.TopicoService;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
	
	private final TopicoService topicoService;

	public TopicoController(TopicoService topicoService) {
		this.topicoService = topicoService;
	}
	
	@GetMapping
	@Cacheable(value = "topico")
	public Page<TopicoDTO> listarTodos(String nomeCurso, 
			@PageableDefault(page = 0, size = 10, direction = Direction.ASC, sort = {"id"}) Pageable paginacao) {
		
		if(nomeCurso != null)
			return topicoService.listarByFilter(nomeCurso, paginacao);
		
		return topicoService.listarTodos(paginacao);
		
	}
	
	@PostMapping
	@Transactional
	@CacheEvict(value = "topico", allEntries = true)
	public ResponseEntity<TopicoDTO> novo(@RequestBody @Valid TopicoFormPost form, UriComponentsBuilder uriBuilder) {
		
		Topico topico = topicoService.novo(form);
		
		URI uriTopico = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uriTopico).body(new TopicoDTO(topico));
		
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "topico", allEntries = true)
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoFormPut form) {
		
		Topico topico = topicoService.atualizar(id, form);
		return ResponseEntity.ok(new TopicoDTO(topico));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> detalhes(@PathVariable Long id) {
		
		Optional<Topico> topicoOp = topicoService.listarById(id);
		return ResponseEntity.ok(new TopicoDetalheDTO(topicoOp.get()));
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "topico", allEntries = true)
	public ResponseEntity<?> deletar(@PathVariable Long id){
		
		topicoService.deletarById(id);
		return ResponseEntity.ok().build();
		
	}

}

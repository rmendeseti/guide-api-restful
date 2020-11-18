package br.eti.rmendes.forumapi.config.validacao;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidacaoFormHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ValidacaoForm> handler(MethodArgumentNotValidException exception) {
		
		return exception.getBindingResult().getFieldErrors().stream()
		.map(field -> {
			String erro = messageSource.getMessage(field, LocaleContextHolder.getLocale());
			return new ValidacaoForm(field.getField(), erro);
		})
		.collect(Collectors.toList());
		
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> handler(EmptyResultDataAccessException exception) { 
		
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(Collections.singletonMap("erro", exception.getMessage()));
	}
	
}

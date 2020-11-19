package br.eti.rmendes.forumapi.config.validacao;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ValidacaoFormHandler {
	
	private final MessageSource messageSource;
	
	public ValidacaoFormHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ValidacaoForm> handleRequestBodyInvalido(MethodArgumentNotValidException exception) {
		
		return exception.getBindingResult().getFieldErrors().stream()
		.map(field -> {
			String erro = messageSource.getMessage(field, LocaleContextHolder.getLocale());
			return new ValidacaoForm(field.getField(), erro);
		})
		.collect(Collectors.toList());
		
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handlePathIvalido(MethodArgumentTypeMismatchException exception) { 
		
		return responseErrorSingleMap(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> handleObjetoNaoEncontrado(EmptyResultDataAccessException exception) { 
		
		return responseErrorSingleMap(HttpStatus.NOT_FOUND, exception.getMessage());
	}
	
	private ResponseEntity<?> responseErrorSingleMap(HttpStatus status, String message) {
		return ResponseEntity
				.status(status)
				.body(Collections.singletonMap("mensagem", message));
	}
	
}

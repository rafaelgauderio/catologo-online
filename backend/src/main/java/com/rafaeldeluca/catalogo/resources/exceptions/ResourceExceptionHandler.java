package com.rafaeldeluca.catalogo.resources.exceptions;


import java.time.Instant;

import javax.servlet.http.HttpServletRequest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rafaeldeluca.catalogo.services.exceptions.DataBaseException;
//atentar para importar a exceção personalizada
import com.rafaeldeluca.catalogo.services.exceptions.ResourceNotFoundException;

//permite que essa classe intercepte alguma exceção que acontecer
@ControllerAdvice
public class ResourceExceptionHandler {
	//handler = classe manipuladora
	//para saber qual tipo de exceção vai ser interceptada
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException error, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Recurso não encontrado");
		err.setMessage(error.getMessage());
		err.setPath(request.getRequestURI());
		//método status permite customizar o status a ser retornado
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> database(DataBaseException error, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_GATEWAY;
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Exceção de bando de dados");
		err.setMessage(error.getMessage());
		err.setPath(request.getRequestURI());		
		return ResponseEntity.status(status).body(err);
	}
	
	

}

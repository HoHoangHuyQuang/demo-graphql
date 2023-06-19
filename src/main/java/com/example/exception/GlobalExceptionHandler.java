package com.example.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler({ HttpClientErrorException.class, HttpServerErrorException.class })
	public ResponseEntity<String> handleHttpClientException(Exception ex) {
		HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
		if (ex instanceof HttpClientErrorException) {
			status = ((HttpClientErrorException) ex).getStatusCode();
		} else if (ex instanceof HttpServerErrorException) {
			status = ((HttpServerErrorException) ex).getStatusCode();
		}
		return new ResponseEntity<>("An error occurred: " + ex.getMessage(), status);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.toList());
		return new ResponseEntity<>("Validation errors occurred: " + errors.toString(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<String> handleMissingServletRequestParameterException(
			MissingServletRequestParameterException ex) {
		return new ResponseEntity<>("Missing request parameter: " + ex.getParameterName(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
	public ResponseEntity<String> handleIllegalStateException(Exception ex) {
		return new ResponseEntity<>("Illegal argument or state: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(Exception ex) {
		return new ResponseEntity<>("Resource not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<String> handleAccessDeniedException(Exception ex, WebRequest request) {
		return new ResponseEntity<String>("Access denied "+ ex.getMessage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

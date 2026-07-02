package com.doctorapp.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> resourceNotFound(ResourceNotFoundException e, WebRequest wr) {
		return build(e.getMessage(), wr, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorDetails> badRequest(BadRequestException e, WebRequest wr) {
		return build(e.getMessage(), wr, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorDetails> unauthorized(UnauthorizedException e, WebRequest wr) {
		return build(e.getMessage(), wr, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(PaymentException.class)
	public ResponseEntity<ErrorDetails> payment(PaymentException e, WebRequest wr) {
		return build(e.getMessage(), wr, HttpStatus.PAYMENT_REQUIRED);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDetails> badCredentials(BadCredentialsException e, WebRequest wr) {
		return build("Invalid email or password", wr, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> accessDenied(AccessDeniedException e, WebRequest wr) {
		return build("You do not have permission to perform this action", wr, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ResponseEntity<ErrorDetails> optimisticLock(ObjectOptimisticLockingFailureException e, WebRequest wr) {
		return build("This slot was just booked by someone else, please pick another slot", wr, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> validation(MethodArgumentNotValidException e, WebRequest wr) {
		String message = e.getBindingResult().getFieldError() != null
				? e.getBindingResult().getFieldError().getDefaultMessage()
				: "Validation failed";
		return build(message, wr, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> generic(Exception e, WebRequest wr) {
		return build(e.getMessage(), wr, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ErrorDetails> build(String message, WebRequest wr, HttpStatus status) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), message, wr.getDescription(false));
		return new ResponseEntity<>(err, status);
	}
}

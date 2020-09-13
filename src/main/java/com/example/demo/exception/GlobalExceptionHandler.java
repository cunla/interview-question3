package com.example.demo.exception;

import java.util.Date;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(),
				request.getDescription(false));

		return message;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(),
				request.getDescription(false));

		return message;
	}

	@ExceptionHandler({ TransactionSystemException.class })
	protected ErrorMessage handlePersistenceException(final Exception ex, final WebRequest request) {
		//
		Throwable cause = ((TransactionSystemException) ex).getRootCause();
		if (cause instanceof ConstraintViolationException) {

			ConstraintViolationException consEx = (ConstraintViolationException) cause;
			//final List<String> errors = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			for (final ConstraintViolation<?> violation : consEx.getConstraintViolations()) {

				sb.append(violation.getPropertyPath() + ": " + violation.getMessage() + " \n ");
			}

			ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), sb.toString(),
					request.getDescription(false));

			return message;
		}

		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(),
				request.getDescription(false));

		return message;
	}
}
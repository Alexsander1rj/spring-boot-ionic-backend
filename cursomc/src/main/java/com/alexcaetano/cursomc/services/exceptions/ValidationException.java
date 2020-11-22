package com.alexcaetano.cursomc.services.exceptions;

public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ValidationException(String msg) {
		super(msg);
	}
	
	public ValidationException(String msg, Throwable causa) {
		super(msg, causa);
	}
}

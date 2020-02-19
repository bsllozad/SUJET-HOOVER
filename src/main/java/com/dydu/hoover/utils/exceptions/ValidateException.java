package com.dydu.hoover.utils.exceptions;

/**
 * 
 * @comment Class to create a ValidateException  
 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
 * @project SUJET HOOVER
 * @class ValidateException
 * @date Feb 16, 2020
 *
 */
public class ValidateException extends HooverException{

	private static final long serialVersionUID = 6930314879487827856L;
	
	public ValidateException() {

	}

	public ValidateException(String message) {
		super(message);
	}

	public ValidateException(Throwable cause) {
		super(cause);
	}

	public ValidateException(String message, Throwable cause) {
		super(message, cause);
	}

}

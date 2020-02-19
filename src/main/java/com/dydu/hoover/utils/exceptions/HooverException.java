package com.dydu.hoover.utils.exceptions;

/**
 * @comment Class to declare a HooverException
 * @author <a href="mailto:bsllozad@gmail.com">Bernardo Lopez</a>
 * @project SUJET HOOVER
 * @class HooverException
 * @date Feb 16, 2020
 *
 */
public class HooverException extends RuntimeException{
	
	private static final long serialVersionUID = 216160926257813995L;

	public HooverException() {
		
	}
	
	public HooverException(String message) {
		super(message);
	}
	
	public HooverException(Throwable cause) {
		super(cause);
	}
	
	public HooverException(String message, Throwable cause) {
		super(message, cause);
	}

}

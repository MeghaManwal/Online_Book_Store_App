package com.bridgelabz.onlinebookstore.exception;

public class BookStoreException extends RuntimeException {
	
	public ExceptionTypes exceptionTypes;

    public BookStoreException(ExceptionTypes exceptionTypes) {
        this.exceptionTypes = exceptionTypes;
    }
    
    public enum ExceptionTypes {
    	 USER_ALREADY_PRESENT,
    	 USER_NOT_FOUND,
    	 
    }

    public BookStoreException(String message, BookStoreException.ExceptionTypes exceptionTypes) {
        super(message);
        this.exceptionTypes = exceptionTypes;
    }

}

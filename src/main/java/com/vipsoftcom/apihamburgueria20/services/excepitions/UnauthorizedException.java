package com.vipsoftcom.apihamburgueria20.services.excepitions;

public class UnauthorizedException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public UnauthorizedException(String msg) {
		super(msg);
	}
}

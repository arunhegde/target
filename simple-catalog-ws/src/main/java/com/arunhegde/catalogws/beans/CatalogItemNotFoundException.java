package com.arunhegde.catalogws.beans;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CatalogItemNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CatalogItemNotFoundException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
	
	public CatalogItemNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}

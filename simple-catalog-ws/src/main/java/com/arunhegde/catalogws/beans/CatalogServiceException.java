package com.arunhegde.catalogws.beans;

public class CatalogServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CatalogServiceException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
	
	public CatalogServiceException(String errorMessage) {
		super(errorMessage);
	}
}

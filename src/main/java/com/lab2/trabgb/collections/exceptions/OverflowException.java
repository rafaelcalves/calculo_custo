package com.lab2.trabgb.collections.exceptions;

public class OverflowException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OverflowException() {
		super("Overflow!");
	}
}

package com.db.awmd.challenge.exception;

/**
 * 
 * @author Souradip
 *
 */

@SuppressWarnings("serial")
public class OverDraftException extends Exception {

	public OverDraftException(String message) {
		super(message);
	}
}

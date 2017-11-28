package com.db.awmd.challenge.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * @author Souradip
 *
 */

@Data
public class TransferResponse {

	@SuppressWarnings("unused")
	private String success;

	@JsonCreator
	public TransferResponse(@JsonProperty("success") boolean success) {
		if (success)
			this.success = "SUCCESS";
		else
			this.success = "FAILED";
	}

}

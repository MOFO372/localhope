package com.libertymutual.goforcode.localhope.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This DoGooder cannot DeFollow this Charity because the DoGooder never followed it to srtat with...")
public class UnableToDeFollowThisCharityException extends Exception {

	private static final long serialVersionUID = 1L;

}

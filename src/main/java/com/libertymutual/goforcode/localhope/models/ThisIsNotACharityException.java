package com.libertymutual.goforcode.localhope.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="The user you provided is not a Charity. What are you doing?") 
public class ThisIsNotACharityException extends Exception {

		private static final long serialVersionUID = 1L;	

}

package com.libertymutual.goforcode.localhope.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.NOT_FOUND, reason = "This Charity has EIN that has been used already. EINs should be unique.") 
public class UniqueEinForCharitiesException extends Exception {

		private static final long serialVersionUID = 1L;	

}

package com.libertymutual.goforcode.localhope.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.CONFLICT, reason = "This Charity is already followed by this DoGooder.") 
public class FollowUniqueCharitiesOnlyException extends Exception {

		private static final long serialVersionUID = 1L;	

}

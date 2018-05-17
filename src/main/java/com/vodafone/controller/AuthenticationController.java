package com.vodafone.controller;

import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vodafone.dto.AccountCredentials;
import com.vodafone.exception.DatabaseException;
import com.vodafone.exception.UserNotFoundException;

@RestController
public class AuthenticationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

}

/*
 * Copyright (C) 2018  Atos Spain SA. All rights reserved.
 * 
 * This file is part of the hapi-fhir-jpaserver-example-mysql-oauth.
 * 
 * KeyCloakInterceptor.java is free software: you can redistribute it and/or modify it under the 
 * terms of the Apache License, Version 2.0 (the License);
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * The software is provided "AS IS", without any warranty of any kind, express or implied,
 * including but not limited to the warranties of merchantability, fitness for a particular
 * purpose and noninfringement, in no event shall the authors or copyright holders be 
 * liable for any claim, damages or other liability, whether in action of contract, tort or
 * otherwise, arising from, out of or in connection with the software or the use or other
 * dealings in the software.
 * 
 * See README file for the full disclaimer information and LICENSE file for full license 
 * information in the project root.
 * 
 * @author	Carlos Cavero
 *			Atos Research and Innovation, Atos SPAIN SA
 *			e-mail carlos.cavero@atos.net 
 * 
 * Interceptor which checks the authenticity of the KeyCloak token in the header
 */

package com.example.todo.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.todo.exceptions.NotAuthorizedException;

@Component
public class KeyCloakInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(KeyCloakInterceptor.class);
	
	//Constants
	@Value("${keycloak.url}")
    private String keycloak_url;
	
	@Value("${keycloak.realm}")
    private String keycloak_realm;
	
	@Value("${keycloak.oauth}")
    private String oauth;

	private String BEARER = "BEARER ";

	@Override
	public boolean preHandle(HttpServletRequest theRequest,
			HttpServletResponse theResponse, Object handler) throws Exception {	
		logger.info("Interceptor: Pre-handle");
		
        String resourcePath = theRequest.getServletPath();
        logger.info("Accessing Resource " + resourcePath);
        
		// To easily enable/disable OAuth authentication
        if (Boolean.valueOf(oauth) == false)
        	return true;
        
        if ("/login".equalsIgnoreCase(resourcePath) == true ||
        	"/sigin".equalsIgnoreCase(resourcePath) == true ||	
        	"/error".equalsIgnoreCase(resourcePath) == true	|| 
        	resourcePath.contains("swagger"))
        	return true;
        
        if ("OPTIONS".equalsIgnoreCase(theRequest.getMethod())) {
            theResponse.setStatus(HttpServletResponse.SC_OK);
            return true;
        } 
        
		String authHeader = theRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null){
            logger.warn("OAuth2 Authentication failure. No OAuth Token supplied in Authorization Header on Request.");
    		throw new NotAuthorizedException("OAuth2 Authentication failure. "
    				+ "No OAuth Token supplied in Authorization Header on Request.");
        }
        
        String authToken = null;
        if (authHeader.toUpperCase().startsWith(BEARER)) 
            authToken = authHeader.substring(BEARER.length());
        else 
    		throw new NotAuthorizedException("Invalid OAuth Header. Missing Bearer prefix");
        
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + authToken);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> response = restTemplate.exchange(keycloak_url + "/realms/" + 
			keycloak_realm + "/protocol/openid-connect/userinfo", 
			HttpMethod.POST, entity, String.class);

   		if (response.getStatusCode().value() != 200) {
            logger.warn("OAuth2 Authentication failure. Invalid OAuth Token supplied in Authorization Header on Request.");
    		throw new NotAuthorizedException("OAuth2 Authentication failure. "
    				+ "Invalid OAuth Token supplied in Authorization Header on Request.");
        }

        return true;
    }
}

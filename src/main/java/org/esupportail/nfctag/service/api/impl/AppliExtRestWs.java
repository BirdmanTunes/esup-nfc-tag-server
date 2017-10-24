/**
 * Licensed to ESUP-Portail under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 *
 * ESUP-Portail licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.esupportail.nfctag.service.api.impl;

import java.util.List;

import org.esupportail.nfctag.domain.TagLog;
import org.esupportail.nfctag.exceptions.EsupNfcTagException;
import org.esupportail.nfctag.exceptions.EsupNfcTagException.EsupNfcTagErrorMessage;
import org.esupportail.nfctag.service.api.AppliExtApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class AppliExtRestWs implements AppliExtApi {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
    protected RestTemplate restTemplate;
    
    protected String description;
    
    protected String header;
    
    protected String backgroundColor;

	protected String isTagableUrl;

	protected String validateTagUrl;
    
    protected String getLocationsUrl;

    public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setIsTagableUrl(String isTagableUrl) {
		this.isTagableUrl = isTagableUrl;
	}

	public void setValidateTagUrl(String validateTagUrl) {
		this.validateTagUrl = validateTagUrl;
	}
	
	public void setGetLocationsUrl(String getLocationsUrl) {
		this.getLocationsUrl = getLocationsUrl;
	}

	public AppliExtRestWs() {
		restTemplate = new RestTemplate();
		((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(5000);
	}

	@Override
	public List<String> getLocations4Eppn(String eppn) {
		log.debug("getLocation for : " + eppn);
		try {
			return restTemplate.getForObject(getLocationsUrl + "?eppn=" + eppn, List.class);
		} catch(HttpServerErrorException e) {
			log.error("HttpServerErrorException on "+getLocationsUrl+" - "+ e.getMessage() +" - "+e.getStackTrace());
			return null;
		} catch(RestClientException e) {
			log.error("RestClientException on "+getLocationsUrl + " - "+ e.getMessage() +" - "+e.getStackTrace());
			return null;			
		}
	}	
	
	@Override
	public void isTagable(TagLog tagLog) throws EsupNfcTagException {
		log.trace("isTagable : " + tagLog);
		try {
			restTemplate.postForLocation(isTagableUrl, tagLog);
		} catch(HttpServerErrorException e) {
			if(e.getStatusCode()==HttpStatus.SERVICE_UNAVAILABLE){
				throw new EsupNfcTagException(EsupNfcTagErrorMessage.error_esupnfctagexception_serviceunavailable);
			} else {
				log.warn("HttpServerErrorException : " + e.getMessage() + " - " + e.getResponseBodyAsString());
				throw new EsupNfcTagException(EsupNfcTagErrorMessage.error_esupnfctagexception_httperror, e.getResponseBodyAsString());	
			}
			
		}
	}

	@Override
	public boolean validateTag(TagLog tagLog) {
		log.trace("validateTag : " + tagLog);
		try {
			restTemplate.postForLocation(validateTagUrl, tagLog);
			return true;
		} catch(HttpServerErrorException e) {
			
			if(e.getStatusCode()!=HttpStatus.SERVICE_UNAVAILABLE) {
				log.warn("HttpServerErrorException : " + e.getMessage() + " - " + e.getResponseBodyAsString());

			}
			return false;
		}
	}

	@Override
	public boolean cancelTag(TagLog tagLog) {
		return true;
	}
	
}

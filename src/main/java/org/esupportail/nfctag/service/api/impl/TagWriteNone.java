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

import java.util.Date;

import org.esupportail.nfctag.service.api.TagWriteApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class TagWriteNone implements TagWriteApi {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
    protected RestTemplate restTemplate;
    
    protected String idFromEppnInitUrl;
	
	public void setIdFromEppnInitUrl(String idFromEppnInitUrl) {
		this.idFromEppnInitUrl = idFromEppnInitUrl;
	}

	public TagWriteNone() {
		restTemplate = new RestTemplate();
	}

	@Override
	public String getIdFromEppnInit(String eppnInit) {
		return "00";
	}
	
}
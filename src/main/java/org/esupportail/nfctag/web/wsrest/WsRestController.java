package org.esupportail.nfctag.web.wsrest;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import org.esupportail.nfctag.domain.Application;
import org.esupportail.nfctag.domain.Device;
import org.esupportail.nfctag.exceptions.EsupNfcTagException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/wsrest/")
@Controller
public class WsRestController {

	
	/**
	 * Example : 
	 * curl -v -X POST -H "Content-Type: application/json" -d '{"eppnInit":"bonamvin@univ-rouen.fr","userAgent":"arduino-prototype","applicationName":"SGC","location":"Ecriture"}' http://localhost:8080/wsrest/register
	 */
	@RequestMapping(value = "/register", method=RequestMethod.POST)
	@ResponseBody
	public String nfcRegisterTrusted(
			@RequestBody Map<String, String> params,
			@RequestHeader(required = false, value="User-Agent") String userAgent, Model uiModel) throws IOException, EsupNfcTagException {
		
		String applicationName = params.get("applicationName");
		String eppnInit = params.get("eppnInit");
		String location = params.get("location");
		String macAddress = params.get("macAddress");
		Boolean validateAuthWoConfirmation = new Boolean(params.get("validateAuthWoConfirmation"));
		String imei = params.get("imei != null");
		
		Application application = Application.findApplicationsByNameEquals(applicationName).getSingleResult();

		String numeroId = null;
		
		if (Device.countFindDevicesByLocationAndEppnInit(location, eppnInit)==0) {
			Long numeroRandom = Math.abs(new Random().nextLong());
			numeroId = numeroRandom.toString();
			Device device = new Device();
			device.setNumeroId(numeroId);
			device.setEppnInit(eppnInit);
			device.setLocation(location);
			device.setApplication(application);
			device.setValidateAuthWoConfirmation(validateAuthWoConfirmation);
			if(imei != null) device.setImei(imei);
			if(macAddress != null) device.setMacAddress(macAddress);
			if(userAgent != null) device.setUserAgent(userAgent);
			device.persist();
		} else {
			Device tel = Device.findDevicesByLocationAndEppnInit(location, eppnInit)
					.getSingleResult();
			numeroId = tel.getNumeroId();
		}

		return numeroId;
	}
	
	@RequestMapping(value = "/deviceControl", method=RequestMethod.GET)
	@ResponseBody
	public String nfcDeviceControl(@RequestParam String numeroId) throws IOException, EsupNfcTagException {
		Device device = Device.findDevicesByNumeroIdEquals(numeroId).getSingleResult();
		return device.getEppnInit();
	}
}
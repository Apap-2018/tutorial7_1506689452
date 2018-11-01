package com.apap.tutorial7.controller;
import com.apap.tutorial7.rest.Setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/airport")
public class AirportController {
	
	@GetMapping()
	public String getAirport(@RequestParam("term") String term) throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
		String path = Setting.airportUrl + "&country=ID&term=" + term;
		return restTemplate.getForEntity(path, String.class).getBody();
	}
}

package com.apap.tutorial7.controller;

import com.apap.tutorial7.model.PilotModel;
import com.apap.tutorial7.service.PilotService;
import com.apap.tutorial7.rest.PilotDetail;
import com.apap.tutorial7.rest.Setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/pilot")
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@Autowired
	RestTemplate restTemplate;

//	@RequestMapping("/")
//	private String home() {
//		return "home";
//	}

//	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
//	private String add(Model model) {
//		model.addAttribute("pilot", new PilotModel());
//		return "addPilot";
//	}
//
//	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
//	private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
//		pilotService.addPilot(pilot);
//		return "add";
//	}
	
//	@RequestMapping(value = "/pilot/view", method = RequestMethod.GET)
//	private String viewPilot(@RequestParam("licenseNumber") String licenseNumber, Model model) {
//		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
//		
//		model.addAttribute("pilot", pilot);
//		return "view-pilot";
//	}
	
//	@RequestMapping(value = "/pilot/view")
//	private String viewPilot(@RequestParam("licenseNumber") String licenseNumber, Model model) {
//		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
//		if (archive == null) {
//			return "view-error";
//		}
//		else {
//			model.addAttribute("pilot", archive);
//			return "view-pilot";
//		}
//	}
	
//	@RequestMapping(value = "/pilot/update/{id}", method = RequestMethod.GET)
//	private String update(@PathVariable Long id, Model model) {
//		PilotModel archive = pilotService.getPilotDetailById(id);
//		model.addAttribute("pilot", archive);
//		return "update-pilot";
//	}
//
//	@RequestMapping(value = "/pilot/update/{id}", method = RequestMethod.POST)
//	private String updateSubmit(@PathVariable Long id, @ModelAttribute PilotModel pilot) {
//		pilot.setId(id);
//		pilotService.updatePilot(pilot);
//		return "updated";
//	}
//	
//	@RequestMapping(value= "/pilot/delete/{id}")
//	private String delete(@PathVariable Long id) {
//		pilotService.removePilot(id);
//		return "deleted";
//	}
	
	@PostMapping(value = "/add")
	public PilotModel addPilotSubmit(@RequestBody PilotModel pilot) {
		return pilotService.addPilot(pilot);
	}
	
	@GetMapping(value = "/view/{licenseNumber}")
	public PilotModel pilotView(@PathVariable("licenseNumber") String licenseNumber) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		return pilot;
	}
	
	@DeleteMapping(value = "/delete")
	public String deletePilot(@RequestParam("pilotId") long pilotId) {
		pilotService.removePilot(pilotId);
		return "success";
	}
	
	@PutMapping(value = "/update/{pilotId}")
		public String updatePilotSubmit(@PathVariable("pilotId") long pilotId,
								  @RequestParam("name") String name,
								  @RequestParam("flyHour") int flyHour) {
			PilotModel pilot = pilotService.getPilotDetailById(pilotId);
			if (pilot.equals(null)) {
				return "Couldn't find your pilot";
			}
			pilot.setName(name);
			pilot.setFlyHour(flyHour);
//			pilot.setId(pilotId);
			pilotService.updatePilot(pilot);
			return "update";
		}
	
	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}
	
	@GetMapping(value = "/status/{licenseNumber}")
	public String getStatus(@PathVariable("licenseNumber") String licenseNumber) throws Exception {
		String path = Setting.pilotUrl + "/pilot?licenseNumber=" + licenseNumber;
		return restTemplate.getForEntity(path, String.class).getBody();
	}
	
	@GetMapping(value = "/full/{licenseNumber}")
	public PilotDetail postStatus(@PathVariable("licenseNumber") String licenseNumber) throws Exception {
		String path = Setting.pilotUrl + "/pilot";
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		PilotDetail detail = restTemplate.postForObject(path, pilot, PilotDetail.class);
		return detail;
	}
}

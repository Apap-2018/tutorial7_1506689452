package com.apap.tutorial7.controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.apap.tutorial7.model.FlightModel;
import com.apap.tutorial7.model.PilotModel;
import com.apap.tutorial7.service.FlightService;
import com.apap.tutorial7.service.PilotService;

@RestController
@RequestMapping("/flight")
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
//	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
//	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
//		FlightModel flight = new FlightModel();
//		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
//		flight.setPilotFlight(pilot);
//		
//		model.addAttribute("flight", flight);
//		return "addFlight";
//	}
//	
//	@RequestMapping(value = "/flight/add", method = RequestMethod.POST)
//	private String addFlightSubmit(@ModelAttribute FlightModel flight) {
//		flightService.addFlight(flight);
//		return "add";
//	}
	
//	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
//	private String add(@PathVariable (value = "licenseNumber") String licenseNumber, Model model) {
//		//FlightModel flight = new FlightModel();
//		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber).get();
//		pilot.setPilotFlight(new ArrayList<FlightModel>());
//		pilot.getPilotFlight().add(new FlightModel());
//		
//		model.addAttribute("pilot", pilot);
//		return "addFlight";
//		
//	}
//	
//	@RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.POST, params= {"submit"})
//	private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
//		PilotModel thePilot = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber()).get();
//		for (FlightModel flight : pilot.getPilotFlight()) {
//			flight.setPilot(thePilot);
//			flightService.addFlight(flight);
//		}
//		return "add";
//		
//	}
//	
//	@RequestMapping(value="/flight/add/{licenseNumber}", params={"addRow"})
//	public String addRow(final PilotModel pilot, final BindingResult bindingResult, Model model) {
//	    pilot.getPilotFlight().add(new FlightModel());
//	    
//	    model.addAttribute("pilot", pilot);
//	    return "addFlight";
//	}
//
//	@RequestMapping(value="/flight/add/{licenseNumber}", params={"removeRow"})
//	public String removeRow(
//	        final PilotModel pilot, final BindingResult bindingResult, 
//	        final HttpServletRequest req, Model model) {
//	    final int rowId = Integer.valueOf(req.getParameter("removeRow"));
//	    pilot.getPilotFlight().remove(rowId);
//	    model.addAttribute("pilot",pilot);
//	    return "addFlight";
//	}
//	
//	@RequestMapping(value= "/flight/viewall")
//    private String viewAll(Model model) {
//    	List<FlightModel> archive = flightService.getAllFlight();
//    	model.addAttribute("flight", archive);
//    	return "viewall-flight";
//    }
//	
//	@RequestMapping(value = "/flight/update/{id}", method = RequestMethod.GET)
//    private String update(@PathVariable Long id, Model model) {
//        FlightModel archive = flightService.flightById(id);
//        model.addAttribute("flight", archive);
//        return "update-flight";
//    }
//
//    @RequestMapping(value = "/flight/update/{id}", method = RequestMethod.POST)
//    private String updateSubmit(@PathVariable Long id, @ModelAttribute FlightModel flight) {
//        flight.setId(id);
//        flightService.updateFlight(flight);
//        return "updated";
//    }
//    
//    @RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
//    private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
//    	for(FlightModel flight : pilot.getPilotFlight()) {
//    		flightService.removeFlight(flight.getId());
//    	}
//    	return "deleted";
//    }
//    
//    @RequestMapping(value= "/flight/delete/{id}")
//    private String delete(@PathVariable Long id) {
//    	flightService.removeFlight(id);
//    	return "deleted";
//    }
	
	@PostMapping(value = "/add")
	public FlightModel addFlightSubmit(@RequestBody FlightModel flight) {
		return flightService.addFlight(flight);
	}
	
	@PutMapping(value = "/update/{flightId}")
	public String updateFlightSubmit(@PathVariable("flightId") long flightId,
							  @RequestParam(value = "destination", required = false) String destination,
							  @RequestParam(value = "origin", required = false) String origin,
							  @RequestParam(value = "time", required = false) Date time) {
		FlightModel flight = flightService.flightById(flightId);
		if (flight.equals(null)) {
			return "Couldn't find your flight";
		}
		if (destination != null) {
			flight.setDestination(destination);
		}
		if (origin != null) {
			flight.setOrigin(origin);
		}
		if (time != null) {
			flight.setTime(time);
		}
		flightService.updateFlight(flight);
		return "flight update success";
	}
	
	@GetMapping(value = "/view/{flightNumber}")
	public FlightModel flightView(@PathVariable("flightNumber") String flightNumber) {
		FlightModel flight = flightService.getFlightByFlightNumber(flightNumber);
		return flight;
	}
	
	@GetMapping(value = "/all")
	public List<FlightModel> flightAllFlight() {
		List<FlightModel> allFlight = flightService.getAllFlight();
		return allFlight;
	}
	
	@DeleteMapping(value = "/delete/{flightId}")
	public String deleteFlight(@PathVariable("flightId") long flightId) {
		flightService.removeFlight(flightId);
		return "flight has been deleted";
	}
}

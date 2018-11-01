package com.apap.tutorial7.service;

import java.util.List;

import com.apap.tutorial7.model.FlightModel;
import com.apap.tutorial7.model.PilotModel;

public interface FlightService {
	FlightModel addFlight(FlightModel flight);
	void updateFlight(FlightModel flightModel);
	void removeFlight(Long id);
	FlightModel flightById(Long id);
	List<FlightModel> getAllFlight();
	FlightModel getFlightByFlightNumber(String flightNumber);
//	List<FlightModel> getFlightDetailByPilot(String licenseNumber);
}
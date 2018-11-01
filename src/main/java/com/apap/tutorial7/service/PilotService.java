package com.apap.tutorial7.service;

import com.apap.tutorial7.model.PilotModel;

public interface PilotService {
	PilotModel getPilotDetailByLicenseNumber(String licenseNumber);
	PilotModel getPilotDetailById(Long id);
	PilotModel addPilot(PilotModel pilot);
	void removePilot(long pilot);
	void updatePilot(PilotModel pilotModel);
}


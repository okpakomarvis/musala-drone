package com.musala.drone.dronedispatchcontroller.service;

import com.musala.drone.dronedispatchcontroller.entity.Medication;

import java.util.Optional;

public interface MedicationService {
    Medication registerMedication(Medication medication);
    Optional<Medication> getMedication(String medicationCode);
}

package com.musala.drone.dronedispatchcontroller.service;

import com.musala.drone.dronedispatchcontroller.entity.Drone;
import com.musala.drone.dronedispatchcontroller.entity.Medication;
import com.musala.drone.dronedispatchcontroller.util.enums.State;

import java.util.List;
import java.util.Optional;

public interface DroneService {
    Drone registerDrone(Drone drone);
    Optional<Drone> getDroneBySerialNumber(String serialNumber);
    String loadDroneWithMedication(String serialNumber, List<String> medicationCodes);
    List<Drone> getDroneByState(State state);
    List<Medication> getDroneMedications(String droneSerialNumber);
    List<Drone> getAllDrones();
    int getCapacityOfDroneBattery(String droneSerialNumber);
    void logCapacityOfDroneBattery();

}

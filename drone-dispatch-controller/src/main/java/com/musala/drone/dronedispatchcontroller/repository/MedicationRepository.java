package com.musala.drone.dronedispatchcontroller.repository;

import com.musala.drone.dronedispatchcontroller.entity.Drone;
import com.musala.drone.dronedispatchcontroller.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, String> {
    List<Medication> findByDrone(Drone dr);
}

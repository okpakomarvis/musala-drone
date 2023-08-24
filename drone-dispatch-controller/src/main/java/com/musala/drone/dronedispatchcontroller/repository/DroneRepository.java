package com.musala.drone.dronedispatchcontroller.repository;

import com.musala.drone.dronedispatchcontroller.entity.Drone;
import com.musala.drone.dronedispatchcontroller.util.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DroneRepository  extends JpaRepository<Drone, String>{
    List<Drone> findByState(State state);
}

package com.musala.drone.dronedispatchcontroller;

import com.musala.drone.dronedispatchcontroller.entity.Drone;
import com.musala.drone.dronedispatchcontroller.entity.Medication;

import com.musala.drone.dronedispatchcontroller.repository.DroneRepository;
import com.musala.drone.dronedispatchcontroller.repository.MedicationRepository;
import com.musala.drone.dronedispatchcontroller.service.DroneService;
import com.musala.drone.dronedispatchcontroller.service.MedicationService;
import com.musala.drone.dronedispatchcontroller.util.enums.Model;
import com.musala.drone.dronedispatchcontroller.util.enums.State;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
class DroneDispatchControllerApplicationTests {

	@Autowired
	private DroneService droneService;
	@Autowired
	private MedicationService medicationService;
	@MockBean
	private DroneRepository droneRepository;
	@MockBean
	private MedicationRepository medicationRepository;
	@Test
	void contextLoads() {
	}
	/**
	 * write text for drone service
	 * **/
	@Test
	 void getAllDronesTest(){
		when(droneRepository.findAll()).thenReturn(Stream.of(
				new Drone("DRONE21",20,70,State.LOADING, Model.Lightweight),
				new Drone("DRONE22",60,90,State.LOADING, Model.Lightweight)
		).collect(toList()));
		assertEquals(2, droneService.getAllDrones().size());
	}
	@Test
	void getDroneBySerialNumberTest(){
		String droneSerialNumber ="DRONE21";
		when(droneRepository.findById(droneSerialNumber)).thenReturn(Stream.of(
				new Drone("DRONE21",20,70,State.LOADING, Model.Lightweight)).findFirst());
		assertEquals(true, droneService.getDroneBySerialNumber(droneSerialNumber).isPresent());
	}
	@Test
	void getDroneByState(){
		when(droneRepository.findByState(State.IDLE)).thenReturn(Stream.of(
				new Drone("DRONE21",0,70,State.IDLE, Model.Lightweight),
				new Drone("DRONE22",0,70,State.IDLE, Model.Lightweight)).collect(toList())
		);
		assertFalse(droneService.getDroneByState(State.IDLE).isEmpty());
		assertEquals(2, droneService.getDroneByState(State.IDLE).size());
	}
	@Test
	void getCapacityOfDroneBattery(){
		String droneSerialNumber ="DRONE21";
		when(droneRepository.findById(droneSerialNumber)).thenReturn(Stream.of(
				new Drone("DRONE21",0,70,State.IDLE, Model.Lightweight)).findFirst());
		assertTrue(droneService.getCapacityOfDroneBattery(droneSerialNumber) !=-1);
	}
	@Test
	void registerDrone(){
		Drone newDrone  = new Drone("DRONE51",0,90,State.IDLE, Model.Lightweight);
		when(droneRepository.saveAndFlush(newDrone)).thenReturn(newDrone);
		assertEquals(newDrone, droneService.registerDrone(newDrone));
	}

	/**
	 * write test for medication
	 * **/

	@Test
	void registerMedication(){
		Medication newMedication  = new Medication("MEDICATION60","Paracetamol",98,"https://pixabay.com/images/search/url/",null);
		when(medicationRepository.saveAndFlush(newMedication)).thenReturn(newMedication);
		assertEquals(newMedication, medicationService.registerMedication(newMedication));
	}

	@Test
	void getMedication(){
		String  medicationCode ="MEDICATION60";
		when(medicationRepository.findById(medicationCode)).thenReturn(Stream.of(
				new Medication("MEDICATION60","Paracetamol",98,"https://pixabay.com/images/search/url/",null)).findFirst());
		assertTrue(medicationService.getMedication(medicationCode).isPresent());
	}



}

package com.musala.drone.dronedispatchcontroller.controller;

import com.musala.drone.dronedispatchcontroller.entity.Drone;
import com.musala.drone.dronedispatchcontroller.entity.Medication;
import com.musala.drone.dronedispatchcontroller.exception.ClientException;
import com.musala.drone.dronedispatchcontroller.service.DroneService;
import com.musala.drone.dronedispatchcontroller.util.LoadRequestDTO;
import com.musala.drone.dronedispatchcontroller.util.ResponseDTO;
import com.musala.drone.dronedispatchcontroller.util.enums.State;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.musala.drone.dronedispatchcontroller.util.constants.ConstantUtil.SUCCESS;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/drone",produces = "application/json")
public class DroneController {
    private final DroneService droneService;
    @GetMapping
    public ResponseEntity<ResponseDTO> getAllDrones(){
        List<Drone> ResponseDrones = droneService.getAllDrones();
        ResponseDTO response = new ResponseDTO();

        if(ResponseDrones.isEmpty()){
            ResponseDrones = Collections.emptyList();
        }
        response.setStatus(SUCCESS);
        response.setTimestamps(System.currentTimeMillis());
        response.setResponse(ResponseDrones);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @GetMapping("/droneByState/{state}")
    public ResponseEntity<ResponseDTO> getDroneByState(@PathVariable State state){
        ResponseDTO response = new ResponseDTO();
        response.setResponse(droneService.getDroneByState(state));
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/availableDrone")
    public ResponseEntity<ResponseDTO> getAvailableDrone(){
        List<Drone> droneResult = new ArrayList<>();
        droneResult.addAll(droneService.getDroneByState(State.IDLE));
        droneResult.addAll(droneService.getDroneByState(State.LOADING));
        droneResult =droneResult.stream().filter(dr->dr.getCapacity()>=25)
                .collect(Collectors.toList());
        ResponseDTO response = new ResponseDTO();
        response.setResponse(droneResult);
        return new ResponseEntity<>( response, HttpStatus.ACCEPTED);
    }
    @GetMapping("/droneCapacity/{serial}")
    public ResponseEntity<ResponseDTO> getDroneCapacityBySerial(@PathVariable String serial){
        ResponseDTO response = new ResponseDTO();
        response.setResponse(droneService.getCapacityOfDroneBattery(serial));
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @GetMapping("/medication/{serial}")
    public ResponseEntity<ResponseDTO> getDroneMedications(@PathVariable String serial){
        List<Medication> responseDrones = droneService.getDroneMedications(serial);
        if(responseDrones.isEmpty()){
            responseDrones = Collections.emptyList();
        }
        ResponseDTO response = new ResponseDTO();
        response.setResponse(responseDrones);
        return new ResponseEntity<>( response, HttpStatus.ACCEPTED);
    }
    @PostMapping(value="/register",consumes = "application/json")
    public ResponseEntity<ResponseDTO> registerDrone(@RequestBody @Valid Drone drone){
        Optional<Drone> result  = droneService.getDroneBySerialNumber(drone.getSerialNumber());
        //check if drone with serial number already exit
        if(result.isEmpty()) {
            ResponseDTO response = new ResponseDTO();
            response.setResponse(droneService.registerDrone(drone));
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }else {
            throw new ClientException("Drone with serial number serialNumber  Already exist");
        }

    }
    @PutMapping(value = "/loadDrone", consumes = "application/json")
    public  ResponseEntity<ResponseDTO> loadDroneWithMedication(@RequestBody LoadRequestDTO loadRequestDTO){
        System.out.println("Serial number error: "+loadRequestDTO.getSerial()+" "+loadRequestDTO.getMedicationCodes());
        ResponseDTO response = new ResponseDTO();
        response.setResponse(droneService.loadDroneWithMedication(loadRequestDTO.getSerial(), loadRequestDTO.getMedicationCodes()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

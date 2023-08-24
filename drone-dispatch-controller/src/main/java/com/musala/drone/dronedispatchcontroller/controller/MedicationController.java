package com.musala.drone.dronedispatchcontroller.controller;


import com.musala.drone.dronedispatchcontroller.entity.Medication;
import com.musala.drone.dronedispatchcontroller.exception.ClientException;
import com.musala.drone.dronedispatchcontroller.service.MedicationService;
import com.musala.drone.dronedispatchcontroller.util.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/medication",produces = "application/json")
public class MedicationController {
    public final MedicationService medicationService;
    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<ResponseDTO> registerMedication(@RequestBody @Valid Medication medicationDto){
        Optional<Medication> result  = medicationService.getMedication(medicationDto.getCode());
        //check if medication with code already exit
        if(result.isEmpty()) {
            ResponseDTO response = new ResponseDTO();
            response.setResponse(medicationService.registerMedication(medicationDto));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else {
            throw new ClientException("Medication with Code  Already exist");
        }


    }
}

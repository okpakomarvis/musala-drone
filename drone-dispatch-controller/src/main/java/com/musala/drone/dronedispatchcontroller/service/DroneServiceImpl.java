package com.musala.drone.dronedispatchcontroller.service;

import com.musala.drone.dronedispatchcontroller.entity.Drone;
import com.musala.drone.dronedispatchcontroller.entity.Medication;
import com.musala.drone.dronedispatchcontroller.exception.ClientException;
import com.musala.drone.dronedispatchcontroller.repository.DroneRepository;
import com.musala.drone.dronedispatchcontroller.repository.MedicationRepository;
import com.musala.drone.dronedispatchcontroller.util.enums.State;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.musala.drone.dronedispatchcontroller.util.constants.ConstantUtil.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DroneServiceImpl  implements DroneService{


    private  final DroneRepository droneRepository;

    private  final MedicationRepository medicationRepository;
    @Override
    public Drone registerDrone(Drone drone) {
        if(droneRepository.count() == DRONE_FLEET_LIMIT){
            throw new ClientException("drone exceeded the predicted number");
        }
        drone.setWeight(0);
        drone.setState(State.IDLE);
        return droneRepository.saveAndFlush(drone);
    }

    @Override
    public Optional<Drone> getDroneBySerialNumber(String serialNumber) {
        return droneRepository.findById(serialNumber);
    }

    @Override
    public String loadDroneWithMedication(String serialNumber, List<String> medicationCodes) {

        Optional<Drone> drone = getDroneBySerialNumber(serialNumber);
        Drone dr;
        if(drone.isPresent()) {
            dr = drone.get();
            medicationCodes.stream().forEach(md->{
                Medication med = medicationRepository.findById(md).orElseThrow(()->new ClientException("Medication not found"));
                float newWeight = dr.getWeight() + med.getWeight();
                if(newWeight < WEIGHT_LIMIT){
                    dr.setWeight(newWeight);
                    dr.setState(State.LOADING);
                }else if(newWeight == WEIGHT_LIMIT){
                    dr.setWeight(newWeight);
                    dr.setState(State.LOADED);
                }else if(newWeight > WEIGHT_LIMIT){
                    //throw exception if drone weight exceeded
                    throw new ClientException("Drone Weight limit exceeded. try load a lighter weight ");
                }
                med.setDrone(dr);
                medicationRepository.saveAndFlush(med);
            });
            droneRepository.saveAndFlush(dr);
            return MEDICATION_LOADED;


        }else {
            throw new ClientException("Drone with serial number serialNumber "+serialNumber+" not found");
        }


    }

    @Override
    public List<Drone> getDroneByState(State state) {
        return droneRepository.findByState(state);
    }

    @Override
    public List<Medication> getDroneMedications(String droneSerialNumber) {
        Drone  dr = droneRepository.findById(droneSerialNumber).orElseThrow(()->new ClientException("Drone with serial number "+droneSerialNumber+"not found"));
        return  medicationRepository.findByDrone(dr);
    }
    @Override
    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    @Override
    public int getCapacityOfDroneBattery(String droneSerialNumber) {
       Optional<Integer> result = droneRepository.findById(droneSerialNumber).map(Drone::getCapacity);
        Integer batteryCapacity =null;
        if(result.isPresent()) {
            batteryCapacity = result.get();
        }else {
            //couldn't find drone battery
            throw  new ClientException("result not found -" +droneSerialNumber);
        }

       return batteryCapacity.intValue();
    }


    @Override
    @Scheduled(fixedRate = 8000)
    @Async
    public void logCapacityOfDroneBattery() {
        log.info("Drone Battery Capacity Check "
                + DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm").format(LocalDateTime.now()));
        List<Drone> drones = getAllDrones();
        drones.stream().forEach(dr -> log.info("Drone Serial Number - {}, Battery Capacity - {}", dr.getSerialNumber(), dr.getCapacity()));

    }
}

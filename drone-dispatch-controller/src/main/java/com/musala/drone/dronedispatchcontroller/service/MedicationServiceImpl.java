package com.musala.drone.dronedispatchcontroller.service;

import com.musala.drone.dronedispatchcontroller.entity.Medication;
import com.musala.drone.dronedispatchcontroller.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService{
    private final MedicationRepository medicationRepository;
    @Override
    public Medication registerMedication(Medication medication) {
        return medicationRepository.saveAndFlush(medication);
    }

    @Override
    public Optional<Medication> getMedication(String medicationCode) {
        return medicationRepository.findById(medicationCode);
    }
}

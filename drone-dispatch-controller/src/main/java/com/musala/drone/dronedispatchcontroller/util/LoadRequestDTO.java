package com.musala.drone.dronedispatchcontroller.util;

import com.musala.drone.dronedispatchcontroller.entity.Medication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class loadRequestDTO {
    private String serial;
    private List<Medication> medicationCodes;
}

package com.musala.drone.dronedispatchcontroller.util;

import com.musala.drone.dronedispatchcontroller.entity.Medication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data

public class LoadRequestDTO {
    private String serial;
    private List<String> medicationCodes;

}

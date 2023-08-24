package com.musala.drone.dronedispatchcontroller.entity;

import com.musala.drone.dronedispatchcontroller.util.enums.Model;
import com.musala.drone.dronedispatchcontroller.util.enums.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="drone")
@AllArgsConstructor
@NoArgsConstructor

public class Drone {
    @Id
    @Size(min = 1, max = 100,
    message = "must be at least 1 character long and max 200 characters")
    private String serialNumber;
    @Column(nullable = false)
    @Min(value = 0, message = "must be at least 0 character long")
    @Max(value = 500, message = " max characters must be 100")
    private float weight;
    @Column(nullable = false)
    @Min(value = 0, message = "must be at least 0 character long")
    @Max(value = 100, message = "max characters must be 100")
    private int capacity;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "must not be null")
    private State state;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "must not be null")
    private Model model;

}

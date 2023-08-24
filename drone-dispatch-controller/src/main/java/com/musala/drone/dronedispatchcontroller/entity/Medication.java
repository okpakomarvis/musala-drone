package com.musala.drone.dronedispatchcontroller.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="medication")
@AllArgsConstructor
@NoArgsConstructor
public class Medication {

    @Id
    @Column(nullable = false)
    @NotBlank(message = "must not be null")
    @Pattern(
            regexp = "[A-Z0-9_]+",
            message = "only upper case letters, underscore and numbers allowed"
    )
    private String code;
    @Column
    @NotBlank(message = "must not be null")
    @Pattern(
            regexp = "^[A-Za-z0-9_-]*$",
            message = "only letters, numbers, underscore and hyphen allowed"
    )
    private String name;

    @Column(nullable = false)
    @NotNull(message = "must not be null")
    private float weight;
    @Column
    private String picture;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_drone")
    private Drone drone;
}

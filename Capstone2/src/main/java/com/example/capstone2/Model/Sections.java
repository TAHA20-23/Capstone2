package com.example.capstone2.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class Sections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Section name can't be null")
    private String name;

    @NotNull(message = "Section time name can't be null")
    private LocalTime startTime;

    @NotNull(message = "Section time name can't be null")
    private LocalTime endTime;


    @NotNull(message = "ClassRoom Id can't be null")
    private Integer classRoomId;

}

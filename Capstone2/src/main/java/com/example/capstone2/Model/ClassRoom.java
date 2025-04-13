package com.example.capstone2.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class ClassRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Class Room name can't be null")
    private String name;

    @NotNull(message = "Max student Room name can't be null")
    @Positive(message = "Max student must be a positive integer")
    private Integer maxStudent;

    @NotNull(message = "Department Id Room name can't be null")
    private Integer departmentId;


}

package com.example.capstone2.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor


public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Name of department Can't be null")
    private String name;

    @NotNull(message = "Name of department Can't be null")
    @Pattern(regexp = "^[a-zA-Z-. ]+$", message = "Department head must contain only characters")
    private String departmentHead;
//
}

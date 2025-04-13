package com.example.capstone2.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Course name can't be null")
    @Length(min = 4, message = "Name must be mor than 3 characters long")
    private String name;

    @NotNull(message = "Credits can't be null")
    @Positive(message = "Credits must be a positive integer")

    private Integer credits;

    @NotNull(message = "Department Id can't Null ")
    private Integer departmentId;
    @NotNull(message = "Department Id can't Null ")
    private Integer instructorId;

//    @NotNull(message = "Department Id can't Null ")
//    private Integer studentId;

    @NotNull(message = "Department Id can't Null ")
    private Integer sectionId;

}

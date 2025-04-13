package com.example.capstone2.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "studentId can't be null")
    private Integer studentId;

    @NotNull(message = "studentId can't be null")
    private Integer courseId;

    @NotNull(message = "Department Id Room name can't be null")
    private Integer departmentId;
}

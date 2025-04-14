package com.example.capstone2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class Instructors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Full name can't be null")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Full name must contain only characters")
    @Length(min = 12, message = "Full name must be mor than 11 characters long")
    private String fullName;

    @NotNull(message = "Email can't be null")
    @Email(message = "Must be valid email")
    @Column(unique = true)
    private String email;

    @NotNull(message = "EnrollmentDate can't be null")
    private LocalDate enrollmentDate;

    @NotNull(message = "Field can't be null")
    private String field;

    @NotNull(message = "DepartmentId can't be null")
    private Integer departmentId;


}

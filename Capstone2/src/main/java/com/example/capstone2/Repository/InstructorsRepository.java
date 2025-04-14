package com.example.capstone2.Repository;

import com.example.capstone2.Model.Instructors;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorsRepository extends JpaRepository<Instructors, Integer> {

    Instructors findInstructorsById(Integer id);

    List<Instructors> findInstructorsByDepartmentId(Integer departmentId);

}

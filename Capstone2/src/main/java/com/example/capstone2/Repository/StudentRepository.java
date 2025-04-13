package com.example.capstone2.Repository;

import com.example.capstone2.Model.Student;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {

    Student findStudentById(Integer id);

    List<Student> findStudentByDepartmentId(Integer departmentId);
}

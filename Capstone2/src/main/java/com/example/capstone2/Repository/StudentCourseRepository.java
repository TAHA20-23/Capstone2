package com.example.capstone2.Repository;

import com.example.capstone2.Model.StudentCourse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {


    Integer countStudentCourseByCourseId(Integer courseId);

    List<StudentCourse> findStudentCourseByStudentId(Integer studentId);

    List<StudentCourse> findStudentCourseByCourseId(Integer courseId);

}

package com.example.capstone2.Repository;

import com.example.capstone2.Model.Grade;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradesRepository extends JpaRepository<Grade,Integer> {

    Grade findGradesById(Integer id);

    List<Grade> findGradesByStudentId(Integer studentId);

    List<Grade> findGradesByCourseId(Integer courseId);
}

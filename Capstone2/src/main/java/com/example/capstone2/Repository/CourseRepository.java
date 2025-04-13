package com.example.capstone2.Repository;

import com.example.capstone2.Model.Course;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    Course findCourseById(Integer id);

    List<Course> findBySectionId(Integer sectionId);


}

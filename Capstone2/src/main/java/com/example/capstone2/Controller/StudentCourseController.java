package com.example.capstone2.Controller;

import com.example.capstone2.Model.ApiResponse;
import com.example.capstone2.Model.Course;
import com.example.capstone2.Model.Student;
import com.example.capstone2.Model.StudentCourse;
import com.example.capstone2.Service.StudentCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student-course")
public class StudentCourseController {


    private final StudentCourseService studentCourseService;

    //---------------------------------------------------------------------------------------
    @PostMapping("/add")
    public ResponseEntity addStudentCourse(@RequestBody @Valid StudentCourse studentCourse, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isAdded = studentCourseService.addStudentCourse(studentCourse);

        if(isAdded){
            return ResponseEntity.status(200).body(new ApiResponse("StudentCourse added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("One or more IDs do not exist, Or check the capacity is it  full!!"));
    }

    //==-=--=-=-=-=-=
    @GetMapping("get-count/{coursedId}")
    public ResponseEntity studentStuddingThisCourse(@PathVariable Integer coursedId){
        Integer count = studentCourseService.getStudentStuddingThisCourse(coursedId);
        return ResponseEntity.status(200).body(" The number of students who added this subject to this ID " +coursedId+" is their number." + count);
    }

    //B.8-------------------------------------------------------
    @GetMapping("/courses/{studentId}")
    public ResponseEntity<?> getCoursesForStudent(@PathVariable Integer studentId) {
        List<Course> courses = studentCourseService.getCourseByStudentId(studentId);
        return ResponseEntity.status(200).body(courses);
    }



}

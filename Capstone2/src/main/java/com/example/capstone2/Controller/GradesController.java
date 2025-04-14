package com.example.capstone2.Controller;


import com.example.capstone2.Model.ApiResponse;
import com.example.capstone2.Model.Course;
import com.example.capstone2.Model.Grade;
import com.example.capstone2.Service.GradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/grade")
public class GradesController {

    private final GradeService gradeService;

    @GetMapping("/get")
    public ResponseEntity getAllGrade(){
        return ResponseEntity.status(200).body(gradeService.getAllGrades());
    }

    @PostMapping("/add")
    public ResponseEntity addGrade(@RequestBody @Valid Grade grade, Errors errors){
       if(errors.hasErrors()){
           return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
       }

        Boolean isAdded= gradeService.addGrade(grade);

        if(isAdded){
            return ResponseEntity.status(200).body(new ApiResponse("Grade added"));

            }
        return ResponseEntity.status(400).body(new ApiResponse("Course Id not exist"));
        }

    @PutMapping("update/{id}")
    public ResponseEntity updateGrade(@PathVariable Integer id, @RequestBody @Valid Grade grade, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        Boolean isUpdate = gradeService.updateGrade(grade, id);
            if (isUpdate) {
                return ResponseEntity.status(200).body(new ApiResponse("Grade updated"));
            }
            return ResponseEntity.status(400).body(new ApiResponse("Course ID does not exist"));

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteGrade(@PathVariable Integer id){
        Boolean isDelete= gradeService.deleteGrade(id);

        if(isDelete){
            return ResponseEntity.status(200).body(new ApiResponse("Grade deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Course ID does not exist"));
    }

    //B.6-----------------------------------------------------------
    @GetMapping("/student/{id}/gpa")
    public ResponseEntity<?> getStudentGPA(@PathVariable Integer id) {
        Double gpa = gradeService.calculateStudentGPA(id);
        return ResponseEntity.status(200).body("Student GPA withe ID("+id+") is "+ gpa);
    }

    //B7---------------------------------------
    @GetMapping("/details/{studentId}")
    public ResponseEntity<?> getStudentGradesDetails(@PathVariable Integer studentId) {
        List<Map<String, Object>> grades = gradeService.getStudentGradesWithCourseNames(studentId);
        return ResponseEntity.status(200).body(grades);
    }

    @GetMapping("/passed/{studentId}")
    public ResponseEntity<?> getPassedCourses(@PathVariable Integer studentId) {
        List<Course> courses = gradeService.getPassedCoursesByStudentId(studentId);
        return ResponseEntity.status(200).body(courses);
    }

}


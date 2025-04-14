package com.example.capstone2.Controller;


import com.example.capstone2.Model.ApiResponse;
import com.example.capstone2.Model.Student;
import com.example.capstone2.Model.StudentCourse;
import com.example.capstone2.Service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/get")
    public ResponseEntity getAllStudents() {
        return ResponseEntity.status(200).body(studentService.getAllStudents());
    }

    @PostMapping("/add")
    public ResponseEntity addStudent(@RequestBody @Valid Student student, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isAdded = studentService.addStudent(student);
        if (isAdded) {
            return ResponseEntity.status(200).body(new ApiResponse("Student added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Department ID does not exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateStudent(@PathVariable Integer id, @RequestBody @Valid Student student, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isUpdated = studentService.updateStudent(student, id);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Student updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Student ID does not exist"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteStudent(@PathVariable Integer id) {
        Boolean isDeleted = studentService.deleteStudent(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Student deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Student ID does not exist"));
    }

    //------------------------------------------------------------------------------
    @GetMapping("/top")
    public ResponseEntity<?> getTopStudents() {
        List<Map<String, Object>> topStudents = studentService.getTopStudents();
        return ResponseEntity.status(200).body(topStudents);
    }

    //12-------------------------------------------------------------
    @GetMapping("/by-department/{departmentId}")
    public ResponseEntity<?> getStudentsByDepartment(@PathVariable Integer departmentId) {
        List<Student> students = studentService.getSpecificDepartmentStudents(departmentId);
        return ResponseEntity.status(200).body(students);
    }




}

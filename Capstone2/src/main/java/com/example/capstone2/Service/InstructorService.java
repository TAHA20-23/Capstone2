package com.example.capstone2.Controller;


import com.example.capstone2.Model.ApiResponse;
import com.example.capstone2.Model.Instructors;
import com.example.capstone2.Service.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instructor")
public class InstructorsController {

    private final InstructorService instructorService;

    @PostMapping("/add")
    public ResponseEntity addInstructor(@RequestBody @Valid Instructors instructor, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isAdded = instructorService.addInstructor(instructor);
        if (isAdded) {
            return ResponseEntity.status(200).body(new ApiResponse("Instructor added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Department ID not found"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateInstructor(@PathVariable Integer id, @RequestBody @Valid Instructors instructor, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isUpdated = instructorService.updateInstructor(instructor, id);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Instructor updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Instructor ID does not exist"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteInstructor(@PathVariable Integer id) {
        Boolean isDeleted = instructorService.deleteInstructor(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Instructor deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Instructor ID does not exist"));
    }

    //14--------------------------------------------------
    @GetMapping("/with-course-count/{departmentId}")
    public ResponseEntity getInstructorsWithCourses(@PathVariable Integer departmentId) {
        List<Map<String, Object>> data = instructorService.getInstructorsWithCourseCountByDepartment(departmentId);
        return ResponseEntity.ok(data);
    }
}

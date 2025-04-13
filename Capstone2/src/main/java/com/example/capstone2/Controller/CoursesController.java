package com.example.capstone2.Controller;

import com.example.capstone2.Model.ApiResponse;
import com.example.capstone2.Model.Course;
import com.example.capstone2.Service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course")
public class CoursesController {

    private final CourseService courseService;

    @GetMapping("/get")
    public ResponseEntity getAllCourses(){
        return ResponseEntity.status(200).body(courseService.getAllCourses());
    }

    @PostMapping("/add")
    public ResponseEntity addCourse(@RequestBody @Valid Course course, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isAdded = courseService.addCourse(course);

        if(isAdded){
            return ResponseEntity.status(200).body(new ApiResponse("Course added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("One or more IDs do not exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCourse(@PathVariable Integer id, @RequestBody @Valid Course course, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isUpdated = courseService.updateCourse(course, id);

        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Course updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Course ID does not exist"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCourse(@PathVariable Integer id){
        Boolean isDeleted = courseService.deleteCourse(id);

        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Course deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Course ID does not exist"));
    }


}

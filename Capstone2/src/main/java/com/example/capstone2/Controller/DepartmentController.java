package com.example.capstone2.Controller;

import com.example.capstone2.Model.ApiResponse;
import com.example.capstone2.Model.Department;
import com.example.capstone2.Service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/add")
    public ResponseEntity addDepartment(@RequestBody @Valid Department department, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isAdded = departmentService.addDepartment(department);
        if(isAdded){
            return ResponseEntity.status(200).body(new ApiResponse("Department added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Unable to add department"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateDepartment(@PathVariable Integer id, @RequestBody @Valid Department department, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isUpdated = departmentService.updateDepartment(department, id);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Department updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Department ID does not exist"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteDepartment(@PathVariable Integer id){
        Boolean isDeleted = departmentService.deleteDepartment(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Department deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Department ID does not exist"));
    }
}

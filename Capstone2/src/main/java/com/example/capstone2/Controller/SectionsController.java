package com.example.capstone2.Controller;

import com.example.capstone2.Model.ApiResponse;
import com.example.capstone2.Model.Sections;
import com.example.capstone2.Model.Student;
import com.example.capstone2.Service.SectionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/section")
public class SectionsController {

    private final SectionsService sectionsService;

    @GetMapping("/get")
    public ResponseEntity getAllSections() {
        return ResponseEntity.status(200).body(sectionsService.getAllSections());
    }

    @PostMapping("/add")
    public ResponseEntity addSection(@RequestBody @Valid Sections section, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isAdded = sectionsService.addSection(section);
        if (isAdded) {
            return ResponseEntity.status(200).body(new ApiResponse("Section added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("ClassRoom ID does not exist or Another section is already scheduled at this time in this classroom"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateSection(@PathVariable Integer id, @RequestBody @Valid Sections section, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isUpdated = sectionsService.updateSection(section, id);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Section updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Section ID does not exist"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteSection(@PathVariable Integer id) {
        Boolean isDeleted = sectionsService.deleteSection(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Section deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Section ID does not exist"));
    }

    //-----------------------------------------
    @GetMapping("/get-availableSections")
    public ResponseEntity availableSections() {
        List<Sections> availableSections = sectionsService.getAvailableSections();

        if (availableSections != null) {
            return ResponseEntity.status(200).body(availableSections);
        }
        return ResponseEntity.status(400).body(new ApiResponse("They are no available Sections !!"));
    }

    //B.13-----------------------------------------------------------
    @GetMapping("/students/{sectionId}")
    public ResponseEntity<?> getStudentsInSection(@PathVariable Integer sectionId) {
        List<Student> students = sectionsService.getStudentsInSection(sectionId);
        return ResponseEntity.ok(students);
    }



    //B.11-----------------------------------------------------------
    @GetMapping("/analyze/{sectionId}")
    public ResponseEntity<?> analyzeSectionPerformance(@PathVariable Integer sectionId) {
        Map<String, Object> result = sectionsService.analyzeStudentPerformance(sectionId);
        return ResponseEntity.ok(result);
    }


}

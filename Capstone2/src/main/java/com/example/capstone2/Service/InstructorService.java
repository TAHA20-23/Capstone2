package com.example.capstone2.Service;

import com.example.capstone2.Model.Department;
import com.example.capstone2.Model.Instructors;
import com.example.capstone2.Repository.DepartmentRepository;
import com.example.capstone2.Repository.InstructorsRepository;
import com.example.capstone2.Repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorsRepository instructorsRepository;
    private final DepartmentRepository departmentRepository;

    public Boolean addInstructor(Instructors instructor){

        Department department= departmentRepository.findDepartmentById(instructor.getDepartmentId());

        if(department!=null){

            instructor.setEnrollmentDate(LocalDate.now());
            instructorsRepository.save(instructor);
            return true;
        }
        return false;
    }

    public Boolean updateInstructor(Instructors instructor, Integer id){
        Instructors olInstructors = instructorsRepository.findById(id).orElse(null);

        if (olInstructors == null) {
            return false;
        }

        olInstructors.setFullName(instructor.getFullName());
        olInstructors.setEmail(instructor.getEmail());
        olInstructors.setEnrollmentDate(instructor.getEnrollmentDate());
        olInstructors.setDepartmentId(instructor.getDepartmentId());

        instructorsRepository.save(olInstructors);
        return true;
    }

    public Boolean deleteInstructor(Integer id){
        Instructors isDelete = instructorsRepository.findInstructorsById(id);

        if (isDelete != null) {
            instructorsRepository.delete(isDelete);
            return true;
        }
        return false;
    }
}

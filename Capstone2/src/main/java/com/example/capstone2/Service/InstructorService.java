package com.example.capstone2.Service;

import com.example.capstone2.Model.Course;
import com.example.capstone2.Model.Department;
import com.example.capstone2.Model.Instructors;
import com.example.capstone2.Repository.CourseRepository;
import com.example.capstone2.Repository.DepartmentRepository;
import com.example.capstone2.Repository.InstructorsRepository;
import com.example.capstone2.Repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorsRepository instructorsRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;

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
        olInstructors.setField(instructor.getField());

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



    //B.14----------------------------------------------------------------------------------------------------
    //A function that displays all professors in a specific department, along with the number of subjects (courses) they teach.
    // This helps in making decisions about whether to increase the number of subjects for the professor or not.
    public List<Map<String, Object>> getInstructorsWithCourseCountByDepartment(Integer departmentId) {
        List<Instructors> instructors = instructorsRepository.findInstructorsByDepartmentId(departmentId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Instructors instructor : instructors) {
            List<Course> courses = courseRepository.findCourseByInstructorId(instructor.getId());

            Map<String, Object> entry = new HashMap<>();
            entry.put("instructorId", instructor.getId());
            entry.put("name", instructor.getFullName());
            entry.put("courseCount", courses.size());

            result.add(entry);
        }

        return result;
    }
    //16----------------------------------------------------
    // Get instructors by their fileds

    public List<Instructors> getInstructorsByField(String field){
        return instructorsRepository.findInstructorsByField(field);
    }
}

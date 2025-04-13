package com.example.capstone2.Service;

import com.example.capstone2.Model.*;
import com.example.capstone2.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;
    private final SectionRepository sectionRepository;
    private final InstructorsRepository instructorsRepository;
    private final StudentCourseRepository studentCourseRepository;


    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Boolean addCourse(Course course){
        Department department = departmentRepository.findDepartmentById(course.getDepartmentId());
//        Student student = studentRepository.findStudentById(course.getStudentId());
        Sections sections= sectionRepository.findSectionsById(course.getSectionId());
        Instructors instructors= instructorsRepository.findInstructorsById(course.getInstructorId());

//student!=null

        if(department != null  & sections !=null & instructors !=null) {
            courseRepository.save(course);
            return true;
        }
        return false;
    }

    public Boolean updateCourse(Course course, Integer id){
        Course oldCourse = courseRepository.findById(id).orElse(null);

        if (oldCourse == null) {
            return false;
        }

        oldCourse.setName(course.getName());
        oldCourse.setCredits(course.getCredits());
        oldCourse.setDepartmentId(course.getDepartmentId());
        oldCourse.setInstructorId(course.getInstructorId());
//        oldCourse.setStudentId(course.getStudentId());
        oldCourse.setSectionId(course.getSectionId());

        courseRepository.save(oldCourse);
        return true;
    }

    public Boolean deleteCourse(Integer id){
        Course courseToDelete = courseRepository.findById(id).orElse(null);

        if (courseToDelete != null) {
            courseRepository.delete(courseToDelete);
            return true;
        }
        return false;
    }






    }




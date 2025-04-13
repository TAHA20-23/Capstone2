package com.example.capstone2.Service;

import com.example.capstone2.Model.*;
import com.example.capstone2.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentCourseService {


    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final SectionRepository sectionRepository;
    private final ClassRoomRepository classRoomRepository;
    private final DepartmentRepository departmentRepository;

    //B.1 -----------------------------------------
    //Ensure that no student is added to the class if the number of students reaches the capacity of each class.
    public Boolean addStudentCourse(StudentCourse studentCourse){
        Student student = studentRepository.findStudentById(studentCourse.getStudentId());
        Course course = courseRepository.findCourseById(studentCourse.getCourseId());


        if(student != null && course != null ){
            Sections section = sectionRepository.findSectionsById(course.getSectionId());
            if(section == null) return false;

            ClassRoom classRoom = classRoomRepository.findClassRoomById(section.getClassRoomId());
            if(classRoom == null) return false;

            int currentCount = studentCourseRepository.countStudentCourseByCourseId(course.getId());
            if(currentCount >= classRoom.getMaxStudent()){
                return false;
            }

            studentCourseRepository.save(studentCourse);
            return true;
        }
        return false;
    }

    //S.3---------------------------------------------
    //Get all students Studying This Course
    public Integer getStudentStuddingThisCourse(Integer courseId){
        return studentCourseRepository.countStudentCourseByCourseId(courseId);
    }

    // B.2 ------------------------------------------------------------

    //Get full ClassRooms to help the department,  add a class or not?
    public List<ClassRoom> getFullClassRooms() {
        List<ClassRoom> allClassRooms = classRoomRepository.findAll();
        List<ClassRoom> fullClassRooms = new ArrayList<>();

        for (ClassRoom classRoom : allClassRooms) {

            // Get all sections linked to this classroom
            List<Sections> sections = sectionRepository.findByClassRoomId(classRoom.getId());

            int totalStudents = 0;

            // Loop through each section and get all courses in that section
            for (Sections section : sections) {
                List<Course> courses = courseRepository.findBySectionId(section.getId());

                // For each course, count the number of students enrolled
                for (Course course : courses) {
                    int count = studentCourseRepository.countStudentCourseByCourseId(course.getId());
                    totalStudents += count;
                }
            }

            // If total students in the classroom >= max capacity, mark it as full
            if (totalStudents >= classRoom.getMaxStudent()) {
                fullClassRooms.add(classRoom);
            }
        }

        return fullClassRooms;
    }


    //B4  --------------------------------------------------
    //get All Available Sections
    public List<Sections> getAvailableSections() {
        List<Sections> allSections = sectionRepository.findAll();
        List<Sections> availableSections = new ArrayList<>();

        for (Sections section : allSections) {

            // Get classroom of the section
            ClassRoom classRoom = classRoomRepository.findClassRoomById(section.getClassRoomId());
            if (classRoom == null) continue;

            // Get courses linked to this section
            List<Course> courses = courseRepository.findBySectionId(section.getId());

            int totalStudents = 0;

            // Count all students in these courses
            for (Course course : courses) {
                int count = studentCourseRepository.countStudentCourseByCourseId(course.getId());
                totalStudents += count;
            }

            // If classroom still has capacity, add this section
            if (totalStudents < classRoom.getMaxStudent()) {
                availableSections.add(section);
            }
        }

        return availableSections;
    }


    //B. 8-----------------------------------
    //get All Student Course he is studied it
    public List<Course> getCourseByStudentId(Integer studentId){

        List<StudentCourse> studentCourses= studentCourseRepository.findStudentCourseByStudentId(studentId);

        List<Course> courses1 = new ArrayList<>();

        for (StudentCourse sc : studentCourses){
            Course course = courseRepository.findCourseById(sc.getCourseId());

            if (course!=null){
                courses1.add(course);
            }
        }
        return courses1;
    }
}

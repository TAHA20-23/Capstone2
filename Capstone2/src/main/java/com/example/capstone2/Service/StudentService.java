package com.example.capstone2.Service;

import com.example.capstone2.Model.Course;
import com.example.capstone2.Model.Department;
import com.example.capstone2.Model.Grade;
import com.example.capstone2.Model.Student;
import com.example.capstone2.Repository.CourseRepository;
import com.example.capstone2.Repository.DepartmentRepository;
import com.example.capstone2.Repository.GradesRepository;
import com.example.capstone2.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentService {



    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final GradesRepository gradesRepository;
    private final CourseRepository courseRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Boolean addStudent(Student student) {
        Department department = departmentRepository.findDepartmentById(student.getDepartmentId());
        if (department != null) {

            student.setEnrollmentDate(LocalDate.now());
            studentRepository.save(student);
            return true;
        }
        return false;
    }

    public Boolean updateStudent(Student student, Integer id) {
        Student oldStudent = studentRepository.findStudentById(id);
        if (oldStudent == null) {
            return false;
        }
        oldStudent.setFullName(student.getFullName());
        oldStudent.setEmail(student.getEmail());
        oldStudent.setGander(student.getGander());
        oldStudent.setStatus(student.getStatus());
        oldStudent.setEnrollmentDate(student.getEnrollmentDate());
        oldStudent.setDepartmentId(student.getDepartmentId());

        studentRepository.save(oldStudent);
        return true;
    }

    public Boolean deleteStudent(Integer id) {
        Student isDeleted = studentRepository.findStudentById(id);
        if (isDeleted != null) {
            studentRepository.delete(isDeleted);
            return true;
        }
        return false;
    }


    //B.10 -------------------------------------------------------------------------
    // get TopStudent (Outstanding students)
    public List<Map<String, Object>> getTopStudents() {
        List<Student> allStudents = studentRepository.findAll();
        List<Map<String, Object>> topStudents = new ArrayList<>();

        for (Student student : allStudents) {
            List<Grade> grades = gradesRepository.findGradesByStudentId(student.getId());

            double totalPoints = 0;
            int totalCredits = 0;

            for (Grade grade : grades) {
                Course course = courseRepository.findCourseById(grade.getCourseId());
                if (course != null) {
                    totalPoints += grade.getGrade() * course.getCredits();
                    totalCredits += course.getCredits();
                }
            }

            if (totalCredits == 0) continue;

            double gpa = totalPoints / totalCredits;

            if (gpa >= 4.25) {
                Map<String, Object> studentInfo = new HashMap<>();
                studentInfo.put("studentId", student.getId());
                studentInfo.put("fullName", student.getFullName());
                studentInfo.put("gpa", gpa);
                topStudents.add(studentInfo);
            }
        }

        return topStudents;
    }

    //B.12 ------------------------------------------------------------------------
    // get All Students from specific Department
    public List<Student> getSpecificDepartmentStudents(Integer departmentId){
        List<Student> students =studentRepository.findStudentByDepartmentId(departmentId);

        if(students!=null){
            return students;
        }
        return null;
    }

}



//        user.setRegistrationDate(LocalDate.now());
package com.example.capstone2.Service;

import com.example.capstone2.Model.Course;
import com.example.capstone2.Model.Grade;
import com.example.capstone2.Model.Student;
import com.example.capstone2.Repository.CourseRepository;
import com.example.capstone2.Repository.GradesRepository;
import com.example.capstone2.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradesRepository gradesRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;


    public List<Grade> getAllGrades(){
        return gradesRepository.findAll();
    }

    public Boolean addGrade(Grade grade){

        Course course= courseRepository.findCourseById(grade.getCourseId());
        Student student= studentRepository.findStudentById(grade.getStudentId());

        if(course!=null & student!=null){
            gradesRepository.save(grade);
            return true;
        }
        return false;
    }

    public Boolean updateGrade(Grade grade, Integer id){
        Grade oldGrade= gradesRepository.findGradesById(id);

        if (oldGrade==null){
            return false;
        }
        oldGrade.setGrade(grade.getGrade());
        oldGrade.setCourseId(grade.getCourseId());
        gradesRepository.save(oldGrade);
        return true;
    }

    public Boolean deleteGrade(Integer id){
        Grade isDelete= gradesRepository.findGradesById(id);
        if(isDelete!=null){
            gradesRepository.delete(isDelete);
            return true;
        }
        return false;
    }

    //B.6 ----------------------------------------------

    //get GPA for one Student
    public Double calculateStudentGPA(Integer studentId) {
        List<Grade> grades = gradesRepository.findGradesByStudentId(studentId);

        if (grades.isEmpty())
            return 0.0;

        double totalPoints = 0;
        int totalCredits = 0;

        for (Grade grade : grades) {
            Course course = courseRepository.findCourseById(grade.getCourseId());
            int credits = course.getCredits();

            totalPoints += grade.getGrade() * credits;
            totalCredits += credits;
        }

        if (totalCredits == 0)
            return 0.0;

        return totalPoints / totalCredits;
    }

    //B.7-----------------------------------------------
    // List all Student grade withe courses names
    public List<Map<String, Object>> getStudentGradesWithCourseNames(Integer studentId) {
        List<Grade> grades = gradesRepository.findGradesByStudentId(studentId);

        //We used (<Map<String, Object>> ) to display a list containing the course name along with the grade in a customized manner.
        List<Map<String, Object>> result = new ArrayList<>();

        for (Grade grade : grades) {
            Course course = courseRepository.findCourseById(grade.getCourseId());
            if (course != null) {
                //HashMap: Allows you to store items as key-value pairs.
                Map<String, Object> entry = new HashMap<>();

                //Put: It adds the course name to the map under the key "courseName".
                entry.put("courseName", course.getName());
                //Ex."courseName": "Web Development",
                entry.put("grade", grade.getGrade());
                // Ex. "grade": 4.0

                result.add(entry);
            }
        }

        return result;
    }

    //B.9------------------------------------------------------------------
    // Get Passed Courses By StudentId
    public List<Course> getPassedCoursesByStudentId(Integer studentId) {
        List<Grade> grades = gradesRepository.findGradesByStudentId(studentId);
        List<Course> passedCourses = new ArrayList<>();

        for (Grade grade : grades) {
            if (grade.getGrade() >= 2.5) { // Assuming 3.0 is the passing grade
                Course course = courseRepository.findCourseById(grade.getCourseId());
                if (course != null) {
                    passedCourses.add(course);
                }
            }
        }

        return passedCourses;
    }



}

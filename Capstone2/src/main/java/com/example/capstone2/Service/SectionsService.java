package com.example.capstone2.Service;

import com.example.capstone2.Model.*;
import com.example.capstone2.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SectionsService {

    private final SectionRepository sectionRepository;
    private final ClassRoomRepository classRoomRepository;
    private final StudentCourseService studentCourseService;
    private final CourseRepository courseRepository;
    private final GradesRepository gradesRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final StudentRepository studentRepository;


    public List<Sections> getAllSections() {
        return sectionRepository.findAll();
    }

    //B.5 ------------------------------------------------------------------------------------------
    // Do not add a section if it conflicts with another section at the same time in the same class.
    public Boolean addSection(Sections section) {

        ClassRoom classRoom = classRoomRepository.findClassRoomById(section.getClassRoomId());
        List<Sections> existingSections = sectionRepository.findByClassRoomId(section.getClassRoomId());

        if (classRoom == null) {
            return false;
        }

        for (Sections existing : existingSections) {
            // Check if the new section time overlaps with any existing section
            boolean isOverlapping = !(section.getEndTime().isBefore(existing.getStartTime()) ||
                    section.getStartTime().isAfter(existing.getEndTime()));

            if (isOverlapping) {
                return false;
            }
        }

        // If no conflicts found, save the new section
        sectionRepository.save(section);
        return true;


    }

    public Boolean updateSection(Sections section, Integer id) {
        Sections oldSection = sectionRepository.findSectionsById(id);
        if (oldSection == null) {
            return false;
        }

        oldSection.setName(section.getName());
        oldSection.setStartTime(section.getStartTime());
        oldSection.setEndTime(section.getEndTime());
        oldSection.setClassRoomId(section.getClassRoomId());

        sectionRepository.save(oldSection);
        return true;
    }

    public Boolean deleteSection(Integer id) {
        Sections section = sectionRepository.findSectionsById(id);
        if (section != null) {
            sectionRepository.delete(section);
            return true;
        }
        return false;
    }



    //B Or S.4 --------------------------------------------------\
    public List<Sections> getAvailableSections() {
        return studentCourseService.getAvailableSections();
    }

    //B.13---------------------------------------------------------

    public List<Student> getStudentsInSection(Integer sectionId) {
        List<Course> courses = courseRepository.findBySectionId(sectionId);
        List<Integer> studentIds = new ArrayList<>();

        for (Course course : courses) {
            List<StudentCourse> studentCourses = studentCourseRepository.findStudentCourseByCourseId(course.getId());
            for (StudentCourse sc : studentCourses) {
                studentIds.add(sc.getStudentId());
            }
        }

        List<Student> students = new ArrayList<>();
        for (Integer studentId : studentIds) {
            Student student = studentRepository.findStudentById(studentId);
            if (student != null) {
                students.add(student);
            }
        }

        return students;
    }

    //15-------------------------------------------------------------------------------
    // // get all sections on a specific Classroom to check if there is space to add a new section
    public List<Map<String, Object>> getSectionsGroupedByClassRoom() {
        List<ClassRoom> classRooms = classRoomRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (ClassRoom room : classRooms) {
            List<Sections> sections = sectionRepository.findByClassRoomId(room.getId());

            //Sections information
            List<Map<String, Object>> sectionDetails = new ArrayList<>();
            for (Sections section : sections) {
                Map<String, Object> sec = new HashMap<>();
                sec.put("sectionId", section.getId());
                sec.put("sectionName", section.getName());
                sec.put("StartTime", section.getStartTime());
                sec.put("EndTime", section.getEndTime());
                sectionDetails.add(sec);
            }

            //to display the information of the class we want to get the Sections in it
            Map<String, Object> entry = new HashMap<>();
            entry.put("classRoomId", room.getId());
            entry.put("classRoomName", room.getName());
            entry.put("sections", sectionDetails);

            result.add(entry);
        }

        return result;
    }

    //====================================================================
    //B.11 ---------------------------------------------------------------
    //====================================================================
    //B.11 Displaying information about the level of students in a specific section,
    // so that it helps the doctor to know the level of each student in the section and to know the level of the students as a whole
    public Map<String, Object> analyzeStudentPerformance(Integer sectionId) {
        List<Course> courses = courseRepository.findBySectionId(sectionId);
        Set<Integer> studentIds = new HashSet<>();

        // Get all students registered in courses under this section
        for (Course course : courses) {
            List<StudentCourse> studentCourses = studentCourseRepository.findStudentCourseByCourseId(course.getId());
            for (StudentCourse sc : studentCourses) {
                studentIds.add(sc.getStudentId());
            }
        }

        List<Map<String, Object>> studentList = new ArrayList<>();
        double totalGpa = 0;
        int studentCount = 0;

        // For each student, calculate GPA from all grades (not just section-related)
        for (Integer studentId : studentIds) {
            List<Grade> grades = gradesRepository.findGradesByStudentId(studentId);

            double totalPoints = 0;
            int totalCredits = 0;

            for (Grade grade : grades) {
                Course course = courseRepository.findCourseById(grade.getCourseId());

                // Now we include all courses, not limited to section
                if (course != null) {
                    totalPoints += grade.getGrade() * course.getCredits();
                    totalCredits += course.getCredits();
                }
            }

            if (totalCredits > 0) {
                double gpa = totalPoints / totalCredits;
                totalGpa += gpa;
                studentCount++;

                Student student = studentRepository.findStudentById(studentId);

                Map<String, Object> studentInfo = new HashMap<>();
                studentInfo.put("fullName", student.getFullName());
                studentInfo.put("gpa", gpa);

                studentList.add(studentInfo);
            }
        }

        double averageGpa = studentCount > 0 ? totalGpa / studentCount : 0;

        String recommendation;
        if (averageGpa >= 3.5) {
            recommendation = "Students are high-performing. Consider deeper explanations.";
        } else if (averageGpa >= 2.5) {
            recommendation = "Students are average. Balance your explanation level.";
        } else {
            recommendation = "Students need support. Simplify explanations and add reviews.";
        }

        Map<String, Object> result = new HashMap<>();
        result.put("sectionId", sectionId);
        result.put("students", studentList);
        result.put("averageGpa", averageGpa);
        result.put("recommendation", recommendation);

        return result;
    }
}

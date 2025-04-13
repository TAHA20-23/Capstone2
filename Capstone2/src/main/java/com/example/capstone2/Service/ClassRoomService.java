package com.example.capstone2.Service;


import com.example.capstone2.Model.ClassRoom;
import com.example.capstone2.Model.Department;
import com.example.capstone2.Repository.ClassRoomRepository;
import com.example.capstone2.Repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassRoomService {

    private final ClassRoomRepository classRoomRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentCourseService studentCourseService;

        public List<ClassRoom> getAllClassRooms() {
            return classRoomRepository.findAll();
        }

        public Boolean addClassRoom(ClassRoom classRoom) {
            Department department = departmentRepository.findDepartmentById(classRoom.getDepartmentId());

            if(department!=null) {
                classRoomRepository.save(classRoom);
                return true;
            }
            return false;
        }

        public Boolean updateClassRoom(ClassRoom classRoom, Integer id) {
            ClassRoom oldClassRoom = classRoomRepository.findClassRoomById(id);
            if (oldClassRoom == null) {
                return false;
            }
            oldClassRoom.setName(classRoom.getName());
            oldClassRoom.setMaxStudent(classRoom.getMaxStudent());
            oldClassRoom.setDepartmentId(classRoom.getDepartmentId());
            classRoomRepository.save(oldClassRoom);
            return true;
        }

        public Boolean deleteClassRoom(Integer id) {
            ClassRoom isDeleted = classRoomRepository.findClassRoomById(id);
            if (isDeleted != null) {
                classRoomRepository.delete(isDeleted);
                return true;
            }
            return false;
        }

        //2 ---------------------------------------------------------
        public List<ClassRoom> getFullClassRooms(){
            return studentCourseService.getFullClassRooms();
        }



    }



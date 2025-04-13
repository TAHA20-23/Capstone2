package com.example.capstone2.Repository;

import com.example.capstone2.Model.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, Integer> {

    ClassRoom findClassRoomById(Integer id);

    List<ClassRoom> f

}

package com.example.capstone2.Repository;

import com.example.capstone2.Model.Sections;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Sections, Integer> {

    Sections findSectionsById(Integer id);

    List<Sections> findByClassRoomId(Integer classRoomId);

}

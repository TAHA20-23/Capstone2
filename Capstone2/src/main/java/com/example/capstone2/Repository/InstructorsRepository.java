package com.example.capstone2.Repository;

import com.example.capstone2.Model.Instructors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorsRepository extends JpaRepository<Instructors, Integer> {

    Instructors findInstructorsById(Integer id);

}

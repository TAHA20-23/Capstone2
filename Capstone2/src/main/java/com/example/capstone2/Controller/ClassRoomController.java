package com.example.capstone2.Controller;

import com.example.capstone2.Model.ApiResponse;
import com.example.capstone2.Model.ClassRoom;
import com.example.capstone2.Service.ClassRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/classRoom")
public class ClassRoomController {

    private final ClassRoomService classRoomService;

    @GetMapping("/get")
    public ResponseEntity getAllClassRooms(){
        return ResponseEntity.status(200).body(classRoomService.getAllClassRooms());
    }

    @PostMapping("/add")
    public ResponseEntity addClassRoom(@RequestBody @Valid ClassRoom classRoom, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isAdded = classRoomService.addClassRoom(classRoom);

        if(isAdded){
            return ResponseEntity.status(200).body(new ApiResponse("Class room added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Department ID does not exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateClassRoom(@PathVariable Integer id, @RequestBody @Valid ClassRoom classRoom, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean isUpdated = classRoomService.updateClassRoom(classRoom, id);

        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Class room updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Class room ID does not exist"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteClassRoom(@PathVariable Integer id){
        Boolean isDeleted = classRoomService.deleteClassRoom(id);

        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Class room deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Class room ID does not exist"));
    }

    //----------------------------------------------------------------------------------------
    @GetMapping("/full")
    public ResponseEntity<?> getFullClassRooms() {
        List<ClassRoom> fullRooms = classRoomService.getFullClassRooms();
        return ResponseEntity.ok(fullRooms);
    }
}

package com.example.capstone2.Service;

import com.example.capstone2.Model.Department;
import com.example.capstone2.Repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Boolean addDepartment(Department department){

        departmentRepository.save(department);
        return true;
    }

    public Boolean updateDepartment(Department department, Integer id){
        Department oldDepartment = departmentRepository.findDepartmentById(id);

        if (oldDepartment == null) {
            return false;
        }

        oldDepartment.setName(department.getName());
        oldDepartment.setDepartmentHead(department.getDepartmentHead());
        departmentRepository.save(oldDepartment);
        return true;
    }

    public Boolean deleteDepartment(Integer id){
        Department departmentToDelete = departmentRepository.findDepartmentById(id);

        if (departmentToDelete != null) {
            departmentRepository.delete(departmentToDelete);
            return true;
        }
        return false;
    }
}

package com.example.prog4.repository;

import com.example.prog4.repository.database1.entity.Employee;
import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.repository.database2.entity.CnapsEmployee;

import java.util.List;

@org.springframework.stereotype.Repository
public interface Repository {
    Employee findById(String idEmployee);
    void save(Employee toSave);

}

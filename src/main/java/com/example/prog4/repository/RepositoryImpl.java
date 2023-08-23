package com.example.prog4.repository;

import com.example.prog4.model.exception.NotFoundException;
import com.example.prog4.repository.database1.EmployeeRepository;
import com.example.prog4.repository.database1.entity.Employee;
import com.example.prog4.repository.database2.CnapsRepository;
import com.example.prog4.repository.database2.entity.CnapsEmployee;
import lombok.AllArgsConstructor;

@org.springframework.stereotype.Repository
@AllArgsConstructor
public class RepositoryImpl implements Repository {
    private EmployeeRepository employeeRepository;
    private CnapsRepository employeeCnapsRepository;

    @Override
    public Employee findById(String idEmployee) {
        return employeeRepository.findById(idEmployee).orElseThrow(() -> new NotFoundException("Employee not found"));
    }

    @Override
    public void save(Employee toSave) {
        employeeRepository.save(toSave);

        // Obtenir le numéro CNAPS actuel depuis la base de données CNAPS
        CnapsEmployee existingCnapsRecord = employeeCnapsRepository.findByPersonalEmail(toSave.getPersonalEmail())
                .orElse(null);

        String cnapsToUse = (existingCnapsRecord != null && existingCnapsRecord.getCnaps() != null)
                ? existingCnapsRecord.getCnaps()
                : toSave.getCnaps();

        if (cnapsToUse == null) {
            cnapsToUse = toSave.getCnaps();
        }

        employeeCnapsRepository.save(CnapsEmployee.builder()
                .address(toSave.getAddress())
                .address(toSave.getAddress())
                .cin(toSave.getCin())
                .cnaps(cnapsToUse)
                .firstName(toSave.getFirstName())
                .lastName(toSave.getLastName())
                .birthdate(toSave.getBirthDate())
                .childrenNumber(toSave.getChildrenNumber())
                .personalEmail(toSave.getPersonalEmail())
                .professionalEmail(toSave.getProfessionalEmail())
                .entranceDate(toSave.getEntranceDate())
                .departureDate(toSave.getDepartureDate())
                .build());
    }



}

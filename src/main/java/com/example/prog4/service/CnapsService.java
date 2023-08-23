package com.example.prog4.service;

import com.example.prog4.model.exception.NotFoundException;
import com.example.prog4.repository.database2.CnapsRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CnapsService {
    private final CnapsRepository CnapsEmployee ;

    public String getCnapsNumber(String idEmployee) {
        com.example.prog4.repository.database2.entity.CnapsEmployee employeeCnaps =CnapsEmployee.findByPersonalEmail(idEmployee)
                .orElseThrow(() -> new NotFoundException("Employee CNAPS not found"));
        return employeeCnaps.getCnaps();
    }
}

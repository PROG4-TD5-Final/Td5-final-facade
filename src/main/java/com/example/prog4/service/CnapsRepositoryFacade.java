package com.example.prog4.service;

import com.example.prog4.model.exception.NotFoundException;
import com.example.prog4.repository.database2.CnapsRepository;
import com.example.prog4.repository.database2.entity.CnapsEmployee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CnapsRepositoryFacade {

    private final CnapsRepository cnapsRepository;

    public String getCnapsNumberByPersonalEmail(String personalEmail) {
        CnapsEmployee cnapsEmployee = cnapsRepository.findByPersonalEmail(personalEmail)
                .orElseThrow(() -> new NotFoundException("Employee CNAPS not found"));
        return cnapsEmployee.getCnaps();
    }
}

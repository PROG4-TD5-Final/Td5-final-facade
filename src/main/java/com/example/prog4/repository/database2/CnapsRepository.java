package com.example.prog4.repository.database2;

import com.example.prog4.repository.database2.entity.CnapsEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CnapsRepository extends JpaRepository <CnapsEmployee, String> {
    Optional<CnapsEmployee> findByPersonalEmail(String personalEmail);
    CnapsEmployee findCnapsEmployeesByEndToEndId(String id);
}

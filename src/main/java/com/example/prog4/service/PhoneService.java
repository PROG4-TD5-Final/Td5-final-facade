package com.example.prog4.service;

import com.example.prog4.repository.database1.PhoneRepository;
import com.example.prog4.repository.database1.entity.Phone;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PhoneService {
    private PhoneRepository repository;

    public List<Phone> getAll(){
        return repository.findAll();
    }

    public Phone saveOne(Phone position){
        return repository.save(position);
    }
}

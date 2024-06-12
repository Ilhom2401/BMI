package com.example.buildings.service;

import com.example.buildings.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenericService {

    private final EmployeeRepository employeeRepository;

    public boolean checkUserAction(Long userId){
        return employeeRepository.existsById(userId);
    }
}


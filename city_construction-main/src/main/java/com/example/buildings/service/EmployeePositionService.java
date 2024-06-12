package com.example.buildings.service;

import com.example.buildings.entity.EmployeePosition;
import com.example.buildings.repository.EmployeePositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeePositionService {

    private final EmployeePositionRepository employeePositionRepository;

    public List<EmployeePosition> getList(){
        return employeePositionRepository.findAll();
    }
}

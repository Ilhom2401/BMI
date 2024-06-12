package com.example.buildings.service;

import com.example.buildings.entity.Unit;
import com.example.buildings.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    public List<Unit> getList(){
        return unitRepository.findAll();
    }
}

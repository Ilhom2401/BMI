package com.example.buildings.service;

import com.example.buildings.entity.RoleEntity;
import com.example.buildings.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public List<RoleEntity> getList(){
        return repository.findAll();
    }
}

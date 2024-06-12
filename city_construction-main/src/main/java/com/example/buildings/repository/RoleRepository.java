package com.example.buildings.repository;

import com.example.buildings.entity.Product;
import com.example.buildings.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query(value = "select * from task where project_id=?1", nativeQuery = true)
    List<Product> getListByProjectId(Long id);
}

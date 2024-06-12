package com.example.buildings.repository;

import com.example.buildings.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from product where project_id=?1", nativeQuery = true)
    List<Product> getListByProjectId(Long id);

    @Query(value = "select * from product", nativeQuery = true)
    List<Product> getListProject();

    @Query(value = "select * from product where name=?1 and unit_id=?2 and project_id=?3", nativeQuery = true)
    Product getByName(String name, Long unitId, Long projectId);

}

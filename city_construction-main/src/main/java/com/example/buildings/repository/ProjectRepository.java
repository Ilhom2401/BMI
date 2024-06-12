package com.example.buildings.repository;

import com.example.buildings.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value = "select * from project where address=?2 and name=?1", nativeQuery = true)
    Project getByNameAndAddress(String name, String address);
}

package com.example.buildings.repository;

import com.example.buildings.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "select e.id from employee e " +
            "    inner join role r on r.id = e.role_id " +
            "    inner join role_actions ra on r.id = ra.role_entity_id " +
            "    inner join action a on a.id = ra.actions_id and a.name = ?1 " +
            "WHERE e.id = ?2", nativeQuery = true)
    Long access( String action, Long userId);

    Optional<Employee> findByUsernameAndPassword(String username, String password);

    @Query(value = "select * from employee where employee_position_id IS NOT NULL and salary_id is not null order by role_id asc", nativeQuery = true)
    List<Employee> getList();

    @Query(value = "select * from employee where employee_position_id IS NOT NULL and salary_id is not null and role_id=4", nativeQuery = true)
    List<Employee> getAccountantList();

    @Query(value = "select * from employee where project_id=?1", nativeQuery = true)
    List<Employee> getListByProjectId(Long id);
}

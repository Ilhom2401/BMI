package com.example.buildings.entity;

import com.example.buildings.entity.base.BaseEntity;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Employee extends BaseEntity {

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String birthDate;
    private String address;
    private String phoneNumber;

    @OneToOne
    private EmployeePosition employeePosition;

    @OneToOne
    private RoleEntity role;

    @OneToOne
    private Salary salary;

    @OneToOne
    private Project project;

}

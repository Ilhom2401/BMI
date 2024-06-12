package com.example.buildings.dto;

import com.example.buildings.entity.EmployeePosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {
    private Long userId;
    private Long empId;
    private Long projectId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String birthDate;
    private String address;
    private Double salary;
    private String contractStartDate;
    private String contractEndDate;
    private String phoneNumber;
    private Long employeePositionId;
    private Long roleId;
    private Long stateId;
    private String newPassword1;
    private String newPassword2;
}

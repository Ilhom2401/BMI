package com.example.buildings.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountantDto {
    private Long projectId;
    private Long userId;
    private Long employeeId;
    private double salary;
    private String contractStartDate;
    private String contractEndDate;
}

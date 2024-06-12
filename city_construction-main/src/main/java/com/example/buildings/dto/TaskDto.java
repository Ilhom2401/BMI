package com.example.buildings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskDto {
    private Long id;
    private Long userId;
    private Long employeeId;
    private Long projectId;
    private String taskName;
    private String endDate;
    private String completedPercent;
    private String completedDate;
    private String createdDate;
    private Long percent;
    private String projectName;
    private boolean isCompleted = false;
}

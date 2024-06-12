package com.example.buildings.entity;

import com.example.buildings.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Task extends BaseEntity {
    private String name;
    private String endDate;
    @ManyToOne
    private Project project;
    private Long percentCompleted;
    private String createdDate;
    private String completedDate;
}

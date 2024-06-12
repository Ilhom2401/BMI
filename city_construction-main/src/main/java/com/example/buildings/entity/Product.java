package com.example.buildings.entity;

import com.example.buildings.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product extends BaseEntity {

    private String name;
    private String createdDate; //kirib kelgan sana

    @OneToOne
    private Employee createdBy;

    private double quantity;
    private boolean isOver;

    @OneToOne
    private Project project;

    @OneToOne
    private Unit unit;
}

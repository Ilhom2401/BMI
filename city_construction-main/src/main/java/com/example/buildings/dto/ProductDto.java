package com.example.buildings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    private String name;
    private double quantity;
    private Long unitId;
    private Long userId;
    private Long productId;
    private Long projectId;
}

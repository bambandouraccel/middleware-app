package com.ndourcodeur.categorybackendservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private double price;
    private String description;
    private boolean isInStock;
    private Date createdAt;
    private Date updatedAt;
    private String categoryName;
}

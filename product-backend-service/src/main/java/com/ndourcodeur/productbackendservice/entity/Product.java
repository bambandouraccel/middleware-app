package com.ndourcodeur.productbackendservice.entity;

import com.ndourcodeur.productbackendservice.entity.audit.AuditModel;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(
        name = "PRODUCTS",
        uniqueConstraints = @UniqueConstraint(name = "name_product_unique", columnNames = "name")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Product extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PRICE")
    private double price;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IS_IN_STOCK")
    private boolean isInStock;
    private String categoryName;
}

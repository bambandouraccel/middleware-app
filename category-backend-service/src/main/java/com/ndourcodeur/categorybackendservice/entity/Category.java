package com.ndourcodeur.categorybackendservice.entity;

import com.ndourcodeur.categorybackendservice.entity.audit.AuditModel;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(
        name = "CATEGORIES",
        uniqueConstraints = @UniqueConstraint(name = "name_category_unique", columnNames = "name")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Category extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
}

package com.omaryaya.jetbrains.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omaryaya.jetbrains.entity.audit.UserDateAudit;


@Entity
@Table(name="product_categories",
uniqueConstraints=
            @UniqueConstraint(columnNames={"name"})
)
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCategory extends UserDateAudit {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @JsonIgnore
    @OneToMany
    Set<Product> products;
    
}

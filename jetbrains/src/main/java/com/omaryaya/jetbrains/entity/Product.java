package com.omaryaya.jetbrains.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.omaryaya.jetbrains.entity.audit.UserDateAudit;


@Entity
@Table(name="products")
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends UserDateAudit {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    
    @Size(max = 20)
    private String sku;

    private Double price;
    
}

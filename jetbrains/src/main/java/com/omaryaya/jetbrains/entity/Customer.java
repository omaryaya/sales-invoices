package com.omaryaya.jetbrains.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.omaryaya.jetbrains.entity.audit.UserDateAudit;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "customers")
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends UserDateAudit {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;


    private String address;
    
}

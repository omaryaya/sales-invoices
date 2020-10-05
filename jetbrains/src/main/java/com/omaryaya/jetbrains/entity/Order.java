package com.omaryaya.jetbrains.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.omaryaya.jetbrains.entity.audit.UserDateAudit;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;


@Entity
@Table(name="orders")
@Data
@EqualsAndHashCode(callSuper = true)
public class Order extends UserDateAudit {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String referenceNumber;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    @JsonIgnoreProperties("order")
    private List<Product> products = new ArrayList<>();
    
    @NotNull
    private Instant date;

    private String currency;

    @ManyToOne
    private Customer customer;
    
    
}

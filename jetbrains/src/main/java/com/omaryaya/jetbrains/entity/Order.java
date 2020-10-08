package com.omaryaya.jetbrains.entity;

import java.time.Instant;
import java.util.Currency;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.omaryaya.jetbrains.entity.audit.UserDateAudit;

import org.hibernate.annotations.NaturalId;

import lombok.Data;
import lombok.EqualsAndHashCode;


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
    
    @NotNull
    private Instant date;

    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;
    
}

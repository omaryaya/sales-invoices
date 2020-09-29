package com.omaryaya.jetbrains.entity.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"},
        allowGetters = true
)
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class UserDateAudit extends DateAudit {
    
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @LastModifiedBy
    private Long updatedBy;

}

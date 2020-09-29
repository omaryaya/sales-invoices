package com.omaryaya.jetbrains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import com.omaryaya.jetbrains.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAll();
    
    Optional<Customer> findById(Long customerId);
    
    Page<Customer> findByCreatedBy(Long userId, Pageable pageable);

    long countByCreatedBy(Long userId);

    List<Customer> findByIdIn(List<Long> customerIds);

    List<Customer> findByIdIn(List<Long> customerIds, Sort sort);
    
}

package com.omaryaya.jetbrains.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import com.omaryaya.jetbrains.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAll();

    @Query(value=" select o.status, count(o.id) from Order o group by status")
    List<Object[]> findAllGroupByStatus();
    
    Optional<Order> findById(Long orderId);

    Page<Order> findByCustomerId(Long customerId, Pageable pageable);

    List<Order> findByCustomerId(Long customerId);

    Page<Order> findByCreatedBy(Long userId, Pageable pageable);

    long countByCreatedBy(Long userId);

    List<Order> findByIdIn(List<Long> orderIds);

    List<Order> findByIdIn(List<Long> orderIds, Sort sort);
}

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
import com.omaryaya.jetbrains.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();
    
    Page<Product> findByOrder(Order order, Pageable pageable);
    
    Optional<Product> findById(Long productId);
    
    Page<Product> findByCreatedBy(Long userId, Pageable pageable);

    List<Product> findByOrderId(Long orderId);

    Page<Product> findByOrderId(Long orderId, Pageable pageable);

    @Query(nativeQuery = true, value = "select category, count(*) from products where order_id = ?1 group by category")
    List<?> findOrderProductsItems(Long orderId);

    long countByCreatedBy(Long userId);

    List<Product> findByIdIn(List<Long> productIds);

    List<Product> findByIdIn(List<Long> productIds, Sort sort);
    
}

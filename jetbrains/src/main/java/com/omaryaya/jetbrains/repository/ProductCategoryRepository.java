package com.omaryaya.jetbrains.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import com.omaryaya.jetbrains.entity.ProductCategory;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findAll();
    
    Optional<ProductCategory> findById(Long orderId);
    
    Page<ProductCategory> findByCreatedBy(Long userId, Pageable pageable);

    long countByCreatedBy(Long userId);

    List<ProductCategory> findByIdIn(List<Long> productCategoryIds);

    List<ProductCategory> findByIdIn(List<Long> productCategoryIds, Sort sort);
    
}

package com.omaryaya.jetbrains.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import com.omaryaya.jetbrains.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAll();
        
    Optional<Item> findById(Long id);

    List<Item> findByOrderId(Long orderId);
    
    List<Item> findByIdIn(List<Long> productIds);

    List<Item> findByIdIn(List<Long> productIds, Sort sort);
    
}

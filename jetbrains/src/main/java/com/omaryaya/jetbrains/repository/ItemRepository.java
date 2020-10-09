package com.omaryaya.jetbrains.repository;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query( nativeQuery = true,
    value = "select order_id as orderId, sum(p.price * i.quantity) as profit from items i,products p where i.product_id = p.id  group by order_id")
    List<Object[]>  getOrdersProfitGroupByOrderId();
}

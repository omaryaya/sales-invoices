package com.omaryaya.jetbrains.service;

import java.util.List;

// import com.omaryaya.jetbrains.exception.BadRequestException;
// import com.omaryaya.jetbrains.exception.ResourceNotFoundException;
import com.omaryaya.jetbrains.entity.Item;
import com.omaryaya.jetbrains.entity.Order;
import com.omaryaya.jetbrains.entity.Product;
import com.omaryaya.jetbrains.payload.order.ItemRequest;
import com.omaryaya.jetbrains.repository.ItemRepository;
import com.omaryaya.jetbrains.security.UserPrincipal;
import com.omaryaya.jetbrains.util.Mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 'get' methods return DAOs (i.e. responses), while 'find' methods return exact
 * db entities
 */
@Service
public class ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private ItemRepository itemRepository;

    // CREATE

    public Item createItem(UserPrincipal currentUser, int quantity, Order order, Product product) {

        Item item = new Item();
        
        item.setQuantity(quantity);
        item.setOrder(order);
        item.setProduct(product);

        item = itemRepository.save(item);
        return item;
    }

    // READ
    public List<Item> getAllItemsOfOrder(UserPrincipal currentUser, Long orderId) {

        List<Item> items = itemRepository.findByOrderId(orderId);
        return items;
    }

    // UPDATE

    // DELETE

    public void deleteItemById(UserPrincipal currentUser, Long id) {
        itemRepository.deleteById(id);
    }

    // UTIL

}

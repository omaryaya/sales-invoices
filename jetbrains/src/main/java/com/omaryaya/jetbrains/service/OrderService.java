package com.omaryaya.jetbrains.service;

import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.Set;

import com.omaryaya.jetbrains.entity.Item;
import com.omaryaya.jetbrains.entity.Order;
import com.omaryaya.jetbrains.entity.Product;
import com.omaryaya.jetbrains.payload.order.ItemRequest;
import com.omaryaya.jetbrains.payload.order.OrderRequest;
import com.omaryaya.jetbrains.repository.OrderRepository;
import com.omaryaya.jetbrains.security.UserPrincipal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ItemService itemService;

    // Create 

    public Order createOrder(UserPrincipal currentUser, OrderRequest orderRequest) {

        logger.debug("CREATE ORDER");
        Order order = new Order();
        
        if(orderRequest.getReferenceNumber() != null)
            order.setReferenceNumber(orderRequest.getReferenceNumber().trim());
        
        
        if(orderRequest.getCurrency() != null)
            order.setCurrency(Currency.getInstance(orderRequest.getCurrency().trim().toUpperCase()));
        
        order.setDate(Instant.now());

        /*  TODO: Handle order customer
        if(orderRequest.getCustomerId() != null)
            order.setCustomer(null); */
        
        logger.debug("order before save", order);
        order = orderRepository.save(order);
        logger.debug("order after save", order);

        for(ItemRequest itemRequest : orderRequest.getItems()) {
            Product product = productService.findProductById(currentUser, itemRequest.getProductId());
            itemRequest.setOrderId(order.getId());
            itemService.createItem(currentUser, itemRequest.getQuantity(), order, product);
        }
        
        return order;

    }

    // Read
    
    public List<Order> getAllOrders(UserPrincipal currentUser) {
        return orderRepository.findAll();
    }

    public Order findOrderById(UserPrincipal currentUser, Long id) {

        Order order = orderRepository.findById(id).orElseThrow();
        return order;
    }

    // Update

    // Delete

    public void deleteOrderById(UserPrincipal currentUser, Long id) {
        orderRepository.deleteById(id);
        logger.info("user "+currentUser.getName()+" deleted order with id "+id);
    }



    // Currencies
    public Set<Currency> getCurrencies() {
        return Currency.getAvailableCurrencies();
    }

    // Items
    // Currencies
    public List<Item> getItems(UserPrincipal currentUser, Long orderId) {
        return itemService.getAllItemsOfOrder(currentUser, orderId);
    }

}

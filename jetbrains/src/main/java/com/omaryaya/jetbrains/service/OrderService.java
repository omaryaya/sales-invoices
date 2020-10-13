package com.omaryaya.jetbrains.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;

import com.omaryaya.jetbrains.entity.Item;
import com.omaryaya.jetbrains.entity.Order;
import com.omaryaya.jetbrains.model.OrderStatus;
import com.omaryaya.jetbrains.model.OrderStatusCount;
import com.omaryaya.jetbrains.entity.Product;
import com.omaryaya.jetbrains.payload.order.ItemRequest;
import com.omaryaya.jetbrains.payload.order.OrderRequest;
import com.omaryaya.jetbrains.repository.OrderRepository;
import com.omaryaya.jetbrains.security.UserPrincipal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private CustomerService customService;

    // Create 

    public Order createOrder(UserPrincipal currentUser, OrderRequest orderRequest) {

        logger.debug("CREATE ORDER");
        Order order = new Order();
        
        if(orderRequest.getReferenceNumber() != null)
            order.setReferenceNumber(orderRequest.getReferenceNumber().trim());
        
        if(orderRequest.getCurrency() != null)
            order.setCurrency(Currency.getInstance(orderRequest.getCurrency().trim().toUpperCase()));
        else
            order.setCurrency(Currency.getInstance("EUR"));
        
        // validate that none of the product Ids is null
        List<Product> products = new ArrayList<>();    
        for(ItemRequest itemRequest : orderRequest.getItems()) {
            products.add(productService.findProductById(currentUser, itemRequest.getProductId()));
        }

        order.setDate(Instant.now());
        order.setStatus(OrderStatus.NEW);

        /*  TODO: Handle order customer
        if(orderRequest.getCustomerId() != null)
            order.setCustomer(null); */
        
        order = orderRepository.save(order);

        Double cost = 0.0;
        for(int i=0 ; i<products.size() ; i++) {
            ItemRequest itemRequest = orderRequest.getItems().get(i);
            Product product = products.get(i);
            itemRequest.setOrderId(order.getId());
            itemService.createItem(currentUser, itemRequest.getQuantity(), order, products.get(i));
            cost += (Double) (product.getPrice() * itemRequest.getQuantity());
        }

        order.setCost(cost);
        
        return order;

    }

    // Read
    
    public List<Order> getAllOrders(UserPrincipal currentUser) {
        List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        for(Order order : orders) {
            if(order.getCost() == null) {
                order.setCost(itemService.getProfitForOrder(order.getId()));
                orderRepository.save(order);
            }
        }
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Order findOrderById(UserPrincipal currentUser, Long id) {

        Order order = orderRepository.findById(id).orElseThrow();
        return order;
    }

    public List<OrderStatusCount> groupOrdersByStatus() {
        List<?> result = orderRepository.findAllGroupByStatus();
        ServiceHelper<Object,OrderStatusCount> serviceHelper = new ServiceHelper<>();
        List<OrderStatusCount> ordersByStatus = new ArrayList<>();
        for(Object queryResult : result) {
            ordersByStatus.add(serviceHelper.map(queryResult, OrderStatusCount.class));
        }
        return ordersByStatus;
    }

    // Update


    // Update order status
    
	public void setOrderStatus(Long id, OrderStatus status) {
        orderRepository.findById(id).map(order -> {
            order.setStatus(status);
            return orderRepository.save(order);
            
        }).orElseThrow();
    }
    
    public void setOrderCustomer(Long orderId, Long customerId) {
        orderRepository.findById(orderId).map(order -> {
            order.setCustomer(customService.getCustomerById(customerId));
            return orderRepository.save(order);
            
        }).orElseThrow();
	}

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
    public List<Item> getItems(UserPrincipal currentUser, Long orderId) {
        return itemService.getAllItemsOfOrder(currentUser, orderId);
    }

	public List<Order> getOrdersByCustomer(Long id) {
		return orderRepository.findByCustomerId(id);
	}
}

package com.omaryaya.jetbrains.service;

import com.omaryaya.jetbrains.entity.*;
import com.omaryaya.jetbrains.payload.product.*;
import com.omaryaya.jetbrains.payload.order.*;
import com.omaryaya.jetbrains.payload.*;
import com.omaryaya.jetbrains.repository.OrderRepository;
import com.omaryaya.jetbrains.security.UserPrincipal;
import com.omaryaya.jetbrains.util.Helper;
import com.omaryaya.jetbrains.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public PagedResponse<Order> getAllOrders(UserPrincipal currentUser, int page, int size) {

        Helper.validatePageAndSize(page, size);

        // get orders
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Order> orders = orderRepository.findAll(pageable);

        /*
         * List<OrderResponse> orderResponses = orders.map(order -> { return new
         * Mapper<Order, OrderResponse>().mapEntityToDto(order, OrderResponse.class);
         * 
         * }).getContent();
         */

        /*
         * for(ProductResponse res : productResponses)
         * System.out.println("PRODUCT RESPONSE: "+res);
         */

        /*
         * return new PagedResponse<>(productResponses, products.getNumber(),
         * products.getSize(), products.getTotalElements(), products.getTotalPages(),
         * products.isLast());
         */
        return new PagedResponse<>(orders.getContent(), orders.getNumber(), orders.getSize(), orders.getTotalElements(),
                orders.getTotalPages(), orders.isLast());
    }

    public Order findOrderById(UserPrincipal currentUser, Long id) {

        Order order = orderRepository.findById(id).orElseThrow();

        /*
         * ProductResponse productResponse = new Mapper<Product,
         * ProductResponse>().mapEntityToDto(product, ProductResponse.class);
         */
        return order;
    }

    public Order createOrder(UserPrincipal currentUser, OrderRequest orderRequest) {

        logger.debug("CREATE ORDER");

        List<Product> orderProducts = new ArrayList<>();
        for (ProductRequest productRequest : orderRequest.getProducts()) {
            orderProducts.add(productService.findProductById(currentUser, productRequest.getId()));
        }

        Order order = new Order();
        if (orderRequest.getCurrency() != null)
            order.setCurrency(orderRequest.getCurrency());
        if (orderRequest.getReferenceNumber() != null)
            order.setReferenceNumber(orderRequest.getReferenceNumber());
        order.setProducts(orderProducts);
        order.setDate(Instant.now());

        logger.debug("order before save", order);
        order = orderRepository.save(order);
        logger.debug("order after save", order);
        for (Product product : orderProducts) {
            productService.setProductOrder(product, order);
        }
        Mapper<Order, OrderResponse> responseMapper = new Mapper<>();
        OrderResponse orderResponse = responseMapper.mapEntityToDto(order, OrderResponse.class);
        logger.debug("order response", orderResponse);
        return order;

    }

    public void deleteOrderById(UserPrincipal currentUser, Long id) {
        orderRepository.deleteById(id);
        logger.info("user "+currentUser.getName()+" deleted product with id "+id);
    }

    public PagedResponse<ProductResponse> getProductsByOrderId(UserPrincipal currentUser, Long orderId, int page,
            int size) {
        return productService.getProductsByOrderId(currentUser, orderId, page, size);
    }


    public List<OrderNewRequest> getOrdersNew(UserPrincipal currentUser) {
        List<Order> orders = orderRepository.findAll();
        List<OrderNewRequest> orderNewRequests = new ArrayList<>();
        for(Order order : orders) {
            OrderNewRequest onr = new OrderNewRequest();
            productService.getOrderProductsItems(currentUser, order.getId());

        }

        return orderNewRequests;
    }

}

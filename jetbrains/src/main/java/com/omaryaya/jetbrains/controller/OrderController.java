package com.omaryaya.jetbrains.controller;

import java.net.URI;
import java.util.Currency;
import java.util.List;

import javax.validation.Valid;

import com.omaryaya.jetbrains.entity.Order;
import com.omaryaya.jetbrains.payload.ApiResponse;
import com.omaryaya.jetbrains.payload.PagedResponse;
import com.omaryaya.jetbrains.payload.order.OrderRequest;
import com.omaryaya.jetbrains.security.CurrentUser;
import com.omaryaya.jetbrains.security.UserPrincipal;
import com.omaryaya.jetbrains.service.OrderService;
import com.omaryaya.jetbrains.util.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService ordersService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    
    
    // Create
    
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@CurrentUser final UserPrincipal currentUser,
            @Valid @RequestBody final OrderRequest orderRequest) {
        try {

            final Order order = ordersService.createOrder(currentUser, orderRequest);

            final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{orderId}")
                    .buildAndExpand(order.getId()).toUri();

            logger.info("Order {} added.", order.getReferenceNumber());

            return ResponseEntity.created(location).body(new ApiResponse<Order>(true, order));
        } catch (final Exception ex) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to add order. " + ex.getMessage()));
        }
    }

    // Read

    @GetMapping("/all")
    public ResponseEntity<?> getOrders(@CurrentUser final UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) final int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) final int size) {

        try {
            List<Order> orders = ordersService.getAllOrders(currentUser);
            return ResponseEntity.ok().body(orders);
        } catch (Exception ex) {
            logger.error("Unable to get orders", ex);
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage()));
        }

    }

    @GetMapping("/currencies")
    public ResponseEntity<?> getCurrencies(@CurrentUser final UserPrincipal currentUser) {

        try {
            return ResponseEntity.ok().body(ordersService.getCurrencies());
        } catch (Exception ex) {
            logger.error("Unable to get currencies", ex);
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage()));
        }

    }

    // Update

    // Delete

    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteOrderById(@CurrentUser final UserPrincipal currentUser,
            @PathVariable final Long id) {

        try {
            ordersService.deleteOrderById(currentUser, id);
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Order Deleted."));

        } catch (final Exception ex) {
            logger.error("Unable to delete order", ex);
            return ResponseEntity.badRequest().build();
        }
    }


}

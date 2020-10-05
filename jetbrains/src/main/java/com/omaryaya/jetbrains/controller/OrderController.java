package com.omaryaya.jetbrains.controller;

import java.net.URI;

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

    
    @GetMapping("/all")
    public PagedResponse<Order> getOrders(@CurrentUser final UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) final int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) final int size) {
        return ordersService.getAllOrders(currentUser, page, size);

    }
    @GetMapping("/new")
    public void getOrdersNew(@CurrentUser final UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) final int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) final int size) {
        ordersService.getOrdersNew(currentUser);

    }

    @GetMapping("/order/{id}/products")
    public ResponseEntity<?> getProductsByOrderId(@CurrentUser final UserPrincipal currentUser,
    @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) final int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) final int size,
             @PathVariable final Long id) {

        try {
            final PagedResponse<?> products = ordersService.getProductsByOrderId(currentUser, id, page, size);
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Product Found.", products));

        } catch (final Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteOrderById(@CurrentUser final UserPrincipal currentUser, @PathVariable final Long id) {

        try {
            ordersService.deleteOrderById(currentUser, id);
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Order Deleted."));

        } catch (final Exception ex) {
            logger.error("Unable to delete order", ex);
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@CurrentUser final UserPrincipal currentUser, @Valid @RequestBody final OrderRequest orderRequest) {
        try {

            final Order order = ordersService.createOrder(currentUser, orderRequest);

            final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{orderId}")
                    .buildAndExpand(order.getId()).toUri();

            logger.info("Order {} added.", order.getReferenceNumber());

            return ResponseEntity.created(location).body(new ApiResponse<Order>(true, order));
        } catch (final Exception ex) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to add order. " + ex.getLocalizedMessage()));
        }
    }


}

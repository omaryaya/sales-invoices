package com.omaryaya.jetbrains.controller;

import java.net.URI;
import java.util.List;

import com.omaryaya.jetbrains.entity.Customer;
import com.omaryaya.jetbrains.payload.ApiResponse;
import com.omaryaya.jetbrains.payload.customer.CustomerRequest;
import com.omaryaya.jetbrains.payload.customer.CustomerResponse;
import com.omaryaya.jetbrains.security.CurrentUser;
import com.omaryaya.jetbrains.security.UserPrincipal;
import com.omaryaya.jetbrains.service.CustomerService;
import com.omaryaya.jetbrains.util.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    // Create

    @PostMapping("/create")
    @CacheEvict(cacheNames = {"customers-all"}, beforeInvocation = true, allEntries = true)
    public ResponseEntity<?> createOrder(@CurrentUser final UserPrincipal currentUser,
            @RequestBody final CustomerRequest customerRequest) {
        try {

            final Customer customer = customerService.createCustomer(customerRequest);
            final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{orderId}")
                    .buildAndExpand(customer.getId()).toUri();

            logger.info("Customer {} added.", customer.getName());

            return ResponseEntity.created(location).body(new ApiResponse<Customer>(true,"Customer "+customer.getName()+" added.", customer));
        } catch (final Exception ex) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to add customer. " + ex.getMessage()));
        }
    }

    // Read

    @GetMapping("/all")
    @Cacheable("customers-all")
    public ResponseEntity<?> getCustomers(@CurrentUser final UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) final int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) final int size) {

        try {
            List<Customer> customers = customerService.getAllCustomers();
            return ResponseEntity.ok().body(customers);
        } catch (Exception ex) {
            logger.error("Unable to get customers", ex);
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage()));
        }

    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomer(@CurrentUser final UserPrincipal currentUser, @PathVariable Long id) {

        try {
            CustomerResponse customer = customerService.getCustomer(id);
            return ResponseEntity.ok().body(customer);
        } catch (Exception ex) {
            logger.error("Unable to fetch customer", ex);
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage()));
        }

    }


    // Update

    // Delete

    @DeleteMapping("/customer/{id}")
    @CacheEvict(cacheNames = {"customers-all"}, beforeInvocation = true, allEntries = true)
    public ResponseEntity<?> deleteOrderById(@CurrentUser final UserPrincipal currentUser,
            @PathVariable final Long id) {

        try {
            customerService.deleteCustomerById(currentUser, id);
            return ResponseEntity.ok().body(new ApiResponse<>(true, "Customer Deleted."));

        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.badRequest().build();
        }
    }

}

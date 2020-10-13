package com.omaryaya.jetbrains.service;

import java.util.List;

import com.omaryaya.jetbrains.entity.Customer;
import com.omaryaya.jetbrains.entity.Order;
import com.omaryaya.jetbrains.payload.customer.CustomerRequest;
import com.omaryaya.jetbrains.payload.customer.CustomerResponse;
import com.omaryaya.jetbrains.repository.CustomerRepository;
import com.omaryaya.jetbrains.security.UserPrincipal;
import com.omaryaya.jetbrains.util.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderService orderService;
    
    // Create 

    public Customer createCustomer(CustomerRequest customerRequest) {
        ServiceHelper<CustomerRequest, Customer> serviceHelper = new ServiceHelper<>();
        Customer customer = serviceHelper.map(customerRequest, Customer.class);
        return customerRepository.save(customer);

    }

    // Read
    
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll(Constants.DEFAULT_SORT);
        
        return customers;
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow();
    }

    /**
     * 
     * @param id
     * @return customer object with corresponding orders
     */
    public CustomerResponse getCustomer(Long id) {
        
        ServiceHelper<Customer, CustomerResponse> serviceHelper = new ServiceHelper<>();

        Customer customer = customerRepository.getOne(id);
        List<Order> orders = orderService.getOrdersByCustomer(id);

        CustomerResponse customerResponse = serviceHelper.map(customer, CustomerResponse.class);
        customerResponse.setOrders(orders);

        return customerResponse;
    }

    // Update

    // Delete

    public void deleteCustomerById(UserPrincipal currentUser, Long id) {
        customerRepository.deleteById(id);
        logger.info("user "+currentUser.getName()+" deleted order with id "+id);
    }

}

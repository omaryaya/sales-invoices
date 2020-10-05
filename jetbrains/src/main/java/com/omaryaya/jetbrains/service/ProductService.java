package com.omaryaya.jetbrains.service;

// import com.omaryaya.jetbrains.exception.BadRequestException;
// import com.omaryaya.jetbrains.exception.ResourceNotFoundException;
import com.omaryaya.jetbrains.entity.*;
import com.omaryaya.jetbrains.payload.product.*;
import com.omaryaya.jetbrains.payload.*;
import com.omaryaya.jetbrains.repository.ProductRepository;
import com.omaryaya.jetbrains.security.UserPrincipal;
import com.omaryaya.jetbrains.util.Constants;
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

import java.util.ArrayList;
import java.util.List;
/**
 * 'get' methods return DAOs (i.e. responses), while 'find' methods return exact
 * db entities
 */
@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    // CREATE

    public ProductResponse createProduct(ProductNewRequest productRequest) {

        
        Mapper<ProductNewRequest, Product> requestMapper = new Mapper<>();
        Product product = requestMapper.mapEntityToDto(productRequest, Product.class);

        
        product = productRepository.save(product);
        
        Mapper<Product, ProductResponse> responseMapper = new Mapper<>();
        ProductResponse productResponse = responseMapper.mapEntityToDto(product, ProductResponse.class);

        logger.debug("product response", productResponse);
        return productResponse;

    }

    // READ
    public PagedResponse<ProductResponse> getAllProducts(UserPrincipal currentUser, int page, int size) {

        Helper.validatePageAndSize(page, size);

        // get products
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Product> products = productRepository.findAll(pageable);

        List<ProductResponse> productResponses = products.map(product -> {
            return new Mapper<Product, ProductResponse>().mapEntityToDto(product, ProductResponse.class);

        }).getContent();
        for (ProductResponse res : productResponses)
            System.out.println("PRODUCT RESPONSE: " + res);

        return new PagedResponse<>(productResponses, products.getNumber(), products.getSize(),
                products.getTotalElements(), products.getTotalPages(), products.isLast());
    }

    public List<ProductResponse> getAllProductsList(UserPrincipal currentUser) {

        
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();
        for(Product product : products) {
            productResponses.add(
                new Mapper<Product, ProductResponse>().mapEntityToDto(product, ProductResponse.class)
            );
        }
        
        return productResponses;
    }

    public ProductResponse getProductById(UserPrincipal currentUser, Long id) {

        // get products

        Product product = productRepository.findById(id).orElseThrow();

        ProductResponse productResponse = new Mapper<Product, ProductResponse>().mapEntityToDto(product,
                ProductResponse.class);

        return productResponse;
    }

    /**
     * 
     * @param currentUser
     * @param id
     * @return database entity of the Product with specified ID
     */
    public Product findProductById(UserPrincipal currentUser, Long id) {

        return productRepository.findById(id).orElseThrow();
    }

    // UPDATE

    // DELETE

    public void deleteProductById(UserPrincipal currentUser, Long id) {
        productRepository.deleteById(id);
    }

    // UTIL

    public PagedResponse<ProductResponse> formatProducts(Page<Product> products) {
        List<ProductResponse> productResponses = products.map(product -> {
            return new Mapper<Product, ProductResponse>().mapEntityToDto(product, ProductResponse.class);

        }).getContent();
        for (ProductResponse res : productResponses)
            System.out.println("PRODUCT RESPONSE: " + res);

        return new PagedResponse<>(productResponses, products.getNumber(), products.getSize(),
                products.getTotalElements(), products.getTotalPages(), products.isLast());
    }

}

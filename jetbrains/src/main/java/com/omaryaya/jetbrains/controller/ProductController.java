package com.omaryaya.jetbrains.controller;

import com.omaryaya.jetbrains.entity.ProductCategory;
import com.omaryaya.jetbrains.payload.ApiResponse;
import com.omaryaya.jetbrains.payload.PagedResponse;
import com.omaryaya.jetbrains.payload.ProductNewRequest;
import com.omaryaya.jetbrains.payload.ProductResponse;
import com.omaryaya.jetbrains.security.CurrentUser;
import com.omaryaya.jetbrains.security.UserPrincipal;
import com.omaryaya.jetbrains.service.ProductService;
import com.omaryaya.jetbrains.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/all")
    public PagedResponse<ProductResponse> getProducts(@CurrentUser final UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) final int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) final int size) {
        return productService.getAllProducts(currentUser, page, size);

    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@CurrentUser final UserPrincipal currentUser, @PathVariable final Long id) {

        try {
            final ProductResponse product = productService.getProductById(currentUser, id);
            return ResponseEntity.ok().body(new ApiResponse<ProductResponse>(true, "Product Found.", product));

        } catch (final Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody final ProductNewRequest newProductRequest) {
        try {

            final ProductResponse product = productService.createProduct(newProductRequest);

            final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{productId}")
                    .buildAndExpand(product.getId()).toUri();

            logger.info("Product {} added.", product.getName());

            // return ResponseEntity.created(location).body(new ApiResponse(true, "Product
            // Added Successfully"));
            return ResponseEntity.created(location).body(new ApiResponse<ProductResponse>(true, product));
        } catch (final Exception ex) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to add product. " + ex.getLocalizedMessage()));
        }
    }

    @PostMapping("/category/create")
    public ResponseEntity<?> createCategory(@Valid @RequestBody final ProductCategory category) {
        try {
            productService.createProductCategory(category);
            logger.info("Product Category {} added.", category.getName());
            return ResponseEntity.accepted()
                    .body(new ApiResponse<>(true, "Product Category Added Successfully", category));
        } catch (final Exception ex) {
            logger.debug("Product Category already exists.");
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Product Category already exists."));
        }
    }

}

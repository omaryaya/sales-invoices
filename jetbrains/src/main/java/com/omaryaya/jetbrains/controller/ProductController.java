package com.omaryaya.jetbrains.controller;

import com.omaryaya.jetbrains.payload.ApiResponse;
import com.omaryaya.jetbrains.payload.PagedResponse;
import com.omaryaya.jetbrains.payload.product.ProductNewRequest;
import com.omaryaya.jetbrains.payload.product.ProductResponse;
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
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
// import javax.websocket.server.PathParam;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    // Create
    
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@CurrentUser final UserPrincipal currentUser,
            @RequestBody final ProductNewRequest productRequest) {
        try {

            final ProductResponse product = productService.createProduct(productRequest);

            final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{productId}")
                    .buildAndExpand(product.getId()).toUri();

            logger.info("Product {} added.", product.getName());

            return ResponseEntity.created(location).body(new ApiResponse<ProductResponse>(true,"Product "+product.getName()+" added", product));
        } catch (final Exception ex) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to add product. "));
        }
    }

    // Read

    // used for drop-downs and select components
    @GetMapping("/all/list")
    public ResponseEntity<?> getProductsList(@CurrentUser final UserPrincipal currentUser) {

        try {
            List<ProductResponse> products = productService.getAllProductsList(currentUser);
            return ResponseEntity.ok().body(products);
        } catch (Exception ex) {
            logger.error("Unable to get products", ex);
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage()));
        }

    }

    @GetMapping("/all")
    public ResponseEntity<?> getProducts(@CurrentUser final UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) final int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) final int size) {

        try {
            PagedResponse<ProductResponse> products = productService.getAllProducts(currentUser, page, size);
            return ResponseEntity.ok().body(products);
        } catch (Exception ex) {
            logger.error("Unable to get products", ex);
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage()));
        }

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

    // Update

    // Delete

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProductById(@CurrentUser final UserPrincipal currentUser, @PathVariable final Long id) {

        try {
            productService.deleteProductById(currentUser, id);
            return ResponseEntity.ok().body(new ApiResponse<ProductResponse>(true, "Product Deleted."));

        } catch (final Exception ex) {
            logger.error("Unable to delete product", ex);
            return ResponseEntity.badRequest().build();
        }
    }

}

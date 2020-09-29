package com.omaryaya.jetbrains.service;

// import com.omaryaya.jetbrains.exception.BadRequestException;
// import com.omaryaya.jetbrains.exception.ResourceNotFoundException;
import com.omaryaya.jetbrains.entity.*;
import com.omaryaya.jetbrains.payload.*;
import com.omaryaya.jetbrains.repository.ProductCategoryRepository;
import com.omaryaya.jetbrains.repository.ProductRepository;
import com.omaryaya.jetbrains.security.UserPrincipal;
// import com.omaryaya.jetbrains.util.Constants;
import com.omaryaya.jetbrains.util.Helper;
import com.omaryaya.jetbrains.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
/* import java.time.Duration;
import java.time.Instant;
import java.util.Collections; */
import java.util.List;
/* import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors; */

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    
    @Autowired
    private ProductRepository productRepository;

	public PagedResponse<ProductResponse> getAllProducts(UserPrincipal currentUser, int page, int size) {
        
        Helper.validatePageAndSize(page, size);

        // get products
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Product> products = productRepository.findAll(pageable);

        List<ProductResponse> productResponses = products.map(product -> {
            return new Mapper<Product, ProductResponse>().mapEntityToDto(product, ProductResponse.class);

        }).getContent();
        for(ProductResponse res : productResponses)
            System.out.println("PRODUCT RESPONSE: "+res);
        
            
        return new PagedResponse<>(productResponses, products.getNumber(), products.getSize(),
        products.getTotalElements(), products.getTotalPages(), products.isLast());
    }

    public ProductResponse getProductById(UserPrincipal currentUser, Long id) {
        
        // get products
        
        Product product = productRepository.findById(id).orElseThrow();

        ProductResponse productResponse = 
            new Mapper<Product, ProductResponse>().mapEntityToDto(product, ProductResponse.class);

        return productResponse;
    }
    
    public ProductCategory createProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    public ProductResponse createProduct(ProductNewRequest productRequest) {
        
        logger.debug("CREATE PRODUCT");
        Mapper<ProductNewRequest, Product> requestMapper = new Mapper<>();
        Product product = requestMapper.mapEntityToDto(productRequest, Product.class);
        logger.debug("product before save", product);
        product = productRepository.save(product);
        logger.debug("product after save", product);
        Mapper<Product, ProductResponse> responseMapper = new Mapper<>();
        ProductResponse productResponse = responseMapper.mapEntityToDto(product, ProductResponse.class);
        logger.debug("product response", productResponse);
        return productResponse;
        
    }
    
}

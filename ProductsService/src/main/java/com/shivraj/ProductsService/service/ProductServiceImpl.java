package com.shivraj.ProductsService.service;

import com.shivraj.ProductsService.entity.Product;
import com.shivraj.ProductsService.exception.ProductServiceCustomException;
import com.shivraj.ProductsService.model.ProductRequest;
import com.shivraj.ProductsService.model.ProductResponse;
import com.shivraj.ProductsService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product " + productRequest);

        Product product = Product.builder().productName(productRequest.getName()).quantity(productRequest.getQuantity()).price(productRequest.getPrice()).build();

        productRepository.save(product);
        log.info("product created successfully !!! ");

        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get product by productId is:- " + productId);

       Product product =  productRepository.findById(productId).orElseThrow(() -> new ProductServiceCustomException("Product not found","PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = new ProductResponse();

        copyProperties(product, productResponse);

        return productResponse;
    }
}

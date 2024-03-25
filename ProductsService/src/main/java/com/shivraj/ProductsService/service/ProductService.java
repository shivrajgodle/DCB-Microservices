package com.shivraj.ProductsService.service;

import com.shivraj.ProductsService.model.ProductRequest;
import com.shivraj.ProductsService.model.ProductResponse;

public interface ProductService {

     long addProduct(ProductRequest productRequest);

     ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}

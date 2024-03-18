package com.shivraj.ProductsService.controller;

import com.shivraj.ProductsService.model.ProductRequest;
import com.shivraj.ProductsService.model.ProductResponse;
import com.shivraj.ProductsService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
     long productId = productService.addProduct(productRequest);
     return new ResponseEntity<Long>(productId, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable long productId){
        ProductResponse productResponse = productService.getProductById(productId);
        return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
    }

}

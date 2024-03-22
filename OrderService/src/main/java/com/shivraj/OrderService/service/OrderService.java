package com.shivraj.OrderService.service;

import com.shivraj.OrderService.model.OrderRequest;

public interface OrderService {

    long placeOrder(OrderRequest orderRequest);
}

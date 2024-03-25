package com.shivraj.OrderService.service;

import com.shivraj.OrderService.model.OrderRequest;
import com.shivraj.OrderService.model.OrderResponse;

public interface OrderService {

    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrder(long orderId);
}

package com.shivraj.OrderService.service;

import com.shivraj.OrderService.entity.Order;
import com.shivraj.OrderService.model.OrderRequest;
import com.shivraj.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public long placeOrder(OrderRequest orderRequest) {

        //Order Entity -> save the data with status order create

        //product service -> Block products (Reduce the Quantity)

        //payments service -> Payment success completes , Else cancelled

        log.info("Placing Order Request {}", orderRequest);

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .orderDate(Instant.now())
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity()).build();

        order = orderRepository.save(order);

        log.info("Order Placed Successfully with Order id {}", order.getId());

        return order.getId();
    }
}

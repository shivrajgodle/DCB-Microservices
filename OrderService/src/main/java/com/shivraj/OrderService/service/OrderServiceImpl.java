package com.shivraj.OrderService.service;

import com.shivraj.OrderService.entity.Order;
import com.shivraj.OrderService.exception.CustomException;
import com.shivraj.OrderService.external.client.PaymentService;
import com.shivraj.OrderService.external.client.ProductService;
import com.shivraj.OrderService.external.client.request.PaymentRequest;
import com.shivraj.OrderService.model.OrderRequest;
import com.shivraj.OrderService.model.OrderResponse;
import com.shivraj.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaymentService paymentService;

    @Override
    public long placeOrder(OrderRequest orderRequest) {

        //Order Entity -> save the data with status order create

        //product service -> Block products (Reduce the Quantity)

        //payments service -> Payment success completes , Else cancelled

        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());

        log.info("Creating order with status CREATED");

        log.info("Placing Order Request {}", orderRequest);

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .orderDate(Instant.now())
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity()).build();

        order = orderRepository.save(order);

        log.info("Order Placed Successfully with Order id {}", order.getId());

        log.info("Calling payment service to complete the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(order.getAmount())
                .build();

        String orderStatus = null;
        try{
            paymentService.doPayment(paymentRequest);
            log.info("Payment Done Successfully , Changing the order status as Placed");
            orderStatus = "PLACED";
        }catch (Exception e){
            log.info("Error occurred in the Payment, Changing the order status as Failed");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        return order.getId();
    }

    @Override
    public OrderResponse getOrder(long orderId) {
        log.info("Get Order Details for Order Id {}", orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new CustomException("Could not find Order for Order Id:" + orderId,404,"NOT_FOUND"));

        log.info("Invoking product service to fetch product details for Order Id {}", order.getProductId());
        OrderResponse.ProductDetails productDetails = restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+order.getProductId(), OrderResponse.ProductDetails.class);

        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .productDetails(productDetails)
                .build();
        return orderResponse;
    }
}

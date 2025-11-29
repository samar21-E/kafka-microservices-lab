package com.lab.order.service;

import com.lab.order.dto.OrderDTO;
import com.lab.order.dto.OrderEventDTO;
import com.lab.order.model.Order;
import com.lab.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderEventDTO> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String orderEventsTopic;

    public Order createOrder(OrderDTO dto) {
        Order order = new Order();
        order.setProductName(dto.getProductName());
        order.setSkuCode(dto.getSkuCode()); // 🔥 FIX HERE
        order.setQuantity(dto.getQuantity());
        order.setPrice(dto.getPrice());
        order.setCustomerEmail(dto.getCustomerEmail());

        Order savedOrder = orderRepository.save(order);

        OrderEventDTO event = new OrderEventDTO(
                savedOrder.getId(),
                savedOrder.getSkuCode(),   // 🔥 FIX HERE
                savedOrder.getQuantity(),
                savedOrder.getPrice(),
                savedOrder.getCustomerEmail(),
                "ORDER_CREATED"
        );

        kafkaTemplate.send(orderEventsTopic, event);
        return savedOrder;
    }
}

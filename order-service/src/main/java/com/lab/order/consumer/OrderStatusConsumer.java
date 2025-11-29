package com.lab.order.consumer;

import com.lab.order.dto.OrderStatusEvent;
import com.lab.order.model.Order;
import com.lab.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderStatusConsumer {

    private final OrderRepository repository;

    @KafkaListener(topics = "order-status-events", groupId = "order-group")
    public void consumeStatus(OrderStatusEvent event) {
        Order order = repository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(event.getStatus());
        repository.save(order);

        System.out.println("Order " + order.getId() + " updated to: " + event.getStatus());
    }
}

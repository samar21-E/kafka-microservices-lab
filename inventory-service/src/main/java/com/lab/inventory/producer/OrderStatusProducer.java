package com.lab.inventory.producer;

import com.lab.inventory.dto.OrderStatusEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusProducer {

    private final KafkaTemplate<String, OrderStatusEvent> kafkaTemplate;

    public void sendOrderStatus(OrderStatusEvent event) {
        kafkaTemplate.send("order-status-events", event);
        System.out.println("STATUS SENT " + event);
    }
}

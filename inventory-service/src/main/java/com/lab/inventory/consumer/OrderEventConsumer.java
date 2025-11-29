package com.lab.inventory.consumer;

import com.lab.inventory.dto.OrderPlacedEvent;
import com.lab.inventory.dto.OrderStatusEvent;
import com.lab.inventory.model.InventoryItem;
import com.lab.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, OrderStatusEvent> kafkaTemplate;

    @KafkaListener(topics = "order-events", groupId = "inventory-group")
    public void consume(OrderPlacedEvent event) {

        InventoryItem item = inventoryRepository.findBySkuCode(event.getSkuCode())
                .orElseThrow(() -> new RuntimeException("Item not found: " + event.getSkuCode()));

        if (item.getQuantity() >= event.getQuantity()) {
            item.setQuantity(item.getQuantity() - event.getQuantity());
            inventoryRepository.save(item);

            kafkaTemplate.send("order-status-events",
                    new OrderStatusEvent(event.getOrderId(), "ORDER_COMPLETED"));
        } else {
            kafkaTemplate.send("order-status-events",
                    new OrderStatusEvent(event.getOrderId(), "ORDER_REJECTED"));
        }
    }
}

package com.lab.inventory.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("inventoryItem")
public class InventoryItem {

    @Id
    private String id;
    private String skuCode;
    private Integer quantity;
}

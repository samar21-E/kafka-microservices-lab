package com.lab.inventory.repository;

import com.lab.inventory.model.InventoryItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InventoryRepository extends MongoRepository<InventoryItem, String> {
    Optional<InventoryItem> findBySkuCode(String skuCode);
}

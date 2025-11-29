package com.lab.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderEventDTO {
    private Long orderId;
    private String skuCode;     
    private Integer quantity;
    private Double price;
    private String customerEmail;
    private String status;
}

package com.lab.order.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private String productName;
    private String skuCode;       
    private Integer quantity;
    private Double price;
    private String customerEmail;
}

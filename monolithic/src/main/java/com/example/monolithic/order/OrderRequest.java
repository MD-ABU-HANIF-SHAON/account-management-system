package com.example.monolithic.order;
public record OrderRequest(Long userId, Long itemId, int quantity) {}

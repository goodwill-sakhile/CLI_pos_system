package com.pos.model;

import java.time.LocalDateTime;
import java.util.List;

public class Sale {
    private final String saleId;
    private final List<CartItem> items;
    private final double totalAmount;
    private final LocalDateTime timestamp;

    public Sale(String saleId, List<CartItem> items, double totalAmount, LocalDateTime timestamp) {
        this.saleId = saleId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.timestamp = timestamp;
    }

    public String getSaleId() {
        return saleId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("Sale #%s - Total: R%.2f - Time: %s", 
            saleId, totalAmount, timestamp);
    }
}

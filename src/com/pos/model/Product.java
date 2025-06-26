package com.pos.model;

public class Product {
    private final String id;
    private final String name;
    private final double price;
    private int stock;

    public Product(String id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock(int quantity) {
        if (quantity <= stock) {
            stock -= quantity;
        } else {
            throw new IllegalArgumentException("Insufficient stock for product: " + name);
        }
    }

    public void increaseStock(int quantity) {
        this.stock += quantity;
    }

    @Override
    public String toString() {
        return String.format("Product[id=%s, name=%s, price=%.2f, stock=%d]",
                id, name, price, stock);
    }
}

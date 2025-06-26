package com.pos.service;

import com.pos.db.InMemoryDatabase;
import com.pos.model.Product;

import java.util.Collection;

public class InventoryService {
    private final InMemoryDatabase database;

    public InventoryService(InMemoryDatabase database) {
        this.database = database;
    }

    public void addProduct(String id, String name, double price, int stock) {
        Product existing = database.getProduct(id);
        if (existing != null) {
            existing.increaseStock(stock);
        } else {
            Product product = new Product(id, name, price, stock);
            database.addProduct(product);
        }
    }

    public Product getProductById(String id) {
        return database.getProduct(id);
    }

    public Collection<Product> getAllProducts() {
        return database.getAllProducts();
    }

    public void displayInventory() {
        System.out.println("==== Inventory ====");
        for (Product p : database.getAllProducts()) {
            System.out.printf("%s | R%.2f | Stock: %d\n",
                    p.getName(), p.getPrice(), p.getStock());
        }
        System.out.println("===================");
    }
}

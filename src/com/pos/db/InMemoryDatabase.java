package com.pos.db;

import com.pos.model.Product;
import com.pos.model.Sale;

import java.util.*;

public class InMemoryDatabase {
    private final Map<String, Product> products = new HashMap<>();
    private final List<Sale> sales = new ArrayList<>();

    public InMemoryDatabase() {
        seedProducts();
    }

    private void seedProducts() {
        addProduct(new Product("P001", "Coca-Cola 500ml", 14.99, 50));
        addProduct(new Product("P002", "White Bread", 12.50, 30));
        addProduct(new Product("P003", "2L Milk", 19.99, 20));
        addProduct(new Product("P004", "Soap Bar", 6.50, 100));
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Product getProduct(String id) {
        return products.get(id);
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public void addSale(Sale sale) {
        sales.add(sale);
    }

    public List<Sale> getSalesHistory() {
        return new ArrayList<>(sales);
    }
}

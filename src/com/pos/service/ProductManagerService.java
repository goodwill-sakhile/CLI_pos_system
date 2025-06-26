package com.pos.service;

import com.pos.db.InMemoryDatabase;
import com.pos.model.Product;
import com.pos.util.InputUtil;

import java.util.ArrayList;
import java.util.List;

public class ProductManagerService {

    private final InMemoryDatabase database;

    public ProductManagerService(InMemoryDatabase database) {
        this.database = database;
    }

    public void searchProductByName(String keyword) {
        System.out.println("🔍 Searching for: " + keyword);
        List<Product> results = new ArrayList<>();

        for (Product product : database.getAllProducts()) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(product);
            }
        }

        if (results.isEmpty()) {
            System.out.println("❌ No products found.");
        } else {
            System.out.println("✅ Found products:");
            results.forEach(p -> System.out.println(p.toString()));
        }
    }

    public void editProduct() {
        String id = InputUtil.readLine("Enter product ID to edit: ");
        Product product = database.getProduct(id);
        if (product == null) {
            System.out.println("❌ Product not found.");
            return;
        }

        System.out.println("Editing product: " + product.getName());
        String newName = InputUtil.readLine("New name (enter to skip): ");
        double newPrice = InputUtil.readDouble("New price (-1 to skip): ");
        int newStock = InputUtil.readInt("New stock (-1 to skip): ");

        if (!newName.isBlank()) {
            product = new Product(product.getId(), newName, 
                      newPrice >= 0 ? newPrice : product.getPrice(), 
                      newStock >= 0 ? newStock : product.getStock());
            database.addProduct(product);
            System.out.println("✅ Product updated.");
        } else {
            System.out.println("⚠️ No changes made.");
        }
    }

    public void deleteProduct() {
        String id = InputUtil.readLine("Enter product ID to delete: ");
        Product product = database.getProduct(id);
        if (product == null) {
            System.out.println("❌ Product not found.");
            return;
        }

        database.getAllProducts().remove(product);
        System.out.println("✅ Product deleted: " + product.getName());
    }
}

package com.pos.service;

import com.pos.db.InMemoryDatabase;
import com.pos.model.*;

import java.time.LocalDateTime;
import java.util.*;

public class SalesService {
    private final InMemoryDatabase database;
    private final Map<String, CartItem> cart;

    public SalesService(InMemoryDatabase database) {
        this.database = database;
        this.cart = new LinkedHashMap<>();
    }

    public void addToCart(String productId, int quantity) {
        Product product = database.getProduct(productId);
        if (product == null) {
            System.out.println("❌ Product not found.");
            return;
        }
        if (product.getStock() < quantity) {
            System.out.println("❌ Not enough stock.");
            return;
        }

        product.reduceStock(quantity);

        if (cart.containsKey(productId)) {
            cart.get(productId).addQuantity(quantity);
        } else {
            cart.put(productId, new CartItem(product, quantity));
        }

        System.out.println("✅ Added to cart: " + product.getName());
    }

    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("🛒 Cart is empty.");
            return;
        }

        System.out.println("==== Cart ====");
        double total = 0;
        for (CartItem item : cart.values()) {
            System.out.println(item);
            total += item.getTotalPrice();
        }
        System.out.printf("Total: R%.2f\n", total);
        System.out.println("==============");
    }

    public Sale checkout() {
        if (cart.isEmpty()) {
            System.out.println("❌ Cart is empty. Nothing to checkout.");
            return null;
        }

        List<CartItem> items = new ArrayList<>(cart.values());
        double total = items.stream().mapToDouble(CartItem::getTotalPrice).sum();
        String saleId = "SALE" + (database.getSalesHistory().size() + 1);
        LocalDateTime now = LocalDateTime.now();

        Sale sale = new Sale(saleId, items, total, now);
        database.addSale(sale);

        cart.clear(); // Empty the cart after sale
        System.out.println("✅ Checkout complete.");

        return sale;
    }

    public void printSalesHistory() {
        List<Sale> sales = database.getSalesHistory();
        if (sales.isEmpty()) {
            System.out.println("📄 No sales history yet.");
            return;
        }

        System.out.println("==== Sales History ====");
        for (Sale s : sales) {
            System.out.println(s);
        }
        System.out.println("========================");
    }
}

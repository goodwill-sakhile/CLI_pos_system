package com.pos.cli;

import com.pos.db.InMemoryDatabase;
import com.pos.service.InventoryService;
import com.pos.service.ReceiptService;
import com.pos.service.SalesService;
import com.pos.util.InputUtil;
import com.pos.model.Sale;

public class POSApplication {

    private final InventoryService inventoryService;
    private final SalesService salesService;
    private final ReceiptService receiptService;

    public POSApplication() {
        InMemoryDatabase database = new InMemoryDatabase();
        this.inventoryService = new InventoryService(database);
        this.salesService = new SalesService(database);
        this.receiptService = new ReceiptService();
    }

    public void start() {
        boolean running = true;

        while (running) {
            showMainMenu();
            String choice = InputUtil.readLine("Enter option: ");

            switch (choice) {
                case "1" -> inventoryService.displayInventory();
                case "2" -> handleAddToCart();
                case "3" -> salesService.viewCart();
                case "4" -> handleCheckout();
                case "5" -> salesService.printSalesHistory();
                case "6" -> handleAddNewProduct();
                case "0" -> {
                    System.out.println("👋 Exiting POS System. Goodbye!");
                    running = false;
                }
                default -> System.out.println("❌ Invalid option. Please try again.");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n===== POINT OF SALE (CLI) =====");
        System.out.println("1. View Inventory");
        System.out.println("2. Add Item to Cart");
        System.out.println("3. View Cart");
        System.out.println("4. Checkout");
        System.out.println("5. View Sales History");
        System.out.println("6. Add New Product");
        System.out.println("0. Exit");
        System.out.println("================================");
    }

    private void handleAddToCart() {
        String id = InputUtil.readLine("Enter product ID: ");
        int qty = InputUtil.readInt("Enter quantity: ");
        salesService.addToCart(id, qty);
    }

    private void handleCheckout() {
        Sale sale = salesService.checkout();
        receiptService.print(sale);
    }

    private void handleAddNewProduct() {
        String id = InputUtil.readLine("Product ID: ");
        String name = InputUtil.readLine("Product Name: ");
        double price = InputUtil.readDouble("Product Price: ");
        int stock = InputUtil.readInt("Initial Stock: ");
        inventoryService.addProduct(id, name, price, stock);
    }

    public static void main(String[] args) {
        new POSApplication().start();
    }
}

package com.pos.cli;
import com.pos.model.PaymentMethod;
import com.pos.auth.User;
import com.pos.auth.UserManager;
import com.pos.db.InMemoryDatabase;
import com.pos.export.CSVExporter;
import com.pos.model.Sale;
import com.pos.report.SalesReportGenerator;
import com.pos.service.InventoryService;
import com.pos.service.ReceiptService;
import com.pos.service.SalesService;
import com.pos.settings.StoreSettings;
import com.pos.util.InputUtil;

public class POSApplication {

    private final InventoryService inventoryService;
    private final SalesService salesService;
    private final ReceiptService receiptService;
    private final UserManager userManager;
    private final StoreSettings storeSettings;
    private final CSVExporter csvExporter;
    private final InMemoryDatabase database;

    private User currentUser;

    public POSApplication() {
        this.database = new InMemoryDatabase();
        this.inventoryService = new InventoryService(database);
        this.salesService = new SalesService(database);
        this.receiptService = new ReceiptService();
        this.userManager = new UserManager();
        this.storeSettings = new StoreSettings();
        this.csvExporter = new CSVExporter();
    }

    public void start() {
        System.out.println("🔐 Welcome to Java POS System");
        loginScreen();

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
                case "7" -> showStoreSettings();
                case "8" -> generateSalesReport();
                case "9" -> exportSalesToCSV();
                case "0" -> {
                    System.out.println("👋 Logged out. Goodbye " + currentUser.getUsername());
                    running = false;
                }
                default -> System.out.println("❌ Invalid option. Please try again.");
            }
        }
    }

    private void loginScreen() {
        while (true) {
            String username = InputUtil.readLine("Username: ");
            String password = InputUtil.readLine("Password: ");
            currentUser = userManager.login(username, password);
            if (currentUser != null) {
                System.out.println("✅ Login successful. Welcome, " + currentUser.getUsername());
                break;
            } else {
                System.out.println("❌ Invalid credentials. Try again.");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n===== JAVA POS MENU =====");
        System.out.println("1. View Inventory");
        System.out.println("2. Add Item to Cart");
        System.out.println("3. View Cart");
        System.out.println("4. Checkout");
        System.out.println("5. View Sales History");
        System.out.println("6. Add New Product");
        System.out.println("7. Store Settings");
        System.out.println("8. Generate Sales Report");
        System.out.println("9. Export Sales to CSV");
        System.out.println("0. Logout & Exit");
        System.out.println("=========================");
    }

    private void handleAddToCart() {
        String id = InputUtil.readLine("Enter product ID: ");
        int qty = InputUtil.readInt("Enter quantity: ");
        salesService.addToCart(id, qty);
    }

    private void handleCheckout() {
    if (salesService == null) return;

    if (salesService.cartIsEmpty()) {
        System.out.println("🛒 Cart is empty. Add items first.");
        return;
    }

    System.out.println("Select payment method:");
    System.out.println("1. Cash");
    System.out.println("2. Card");
    System.out.println("3. Mobile");
    String option = InputUtil.readLine("Option: ");

    PaymentMethod method;
    switch (option) {
        case "1" -> method = PaymentMethod.CASH;
        case "2" -> method = PaymentMethod.CARD;
        case "3" -> method = PaymentMethod.MOBILE;
        default -> {
            System.out.println("❌ Invalid payment method.");
            return;
        }
    }

    Sale sale = salesService.checkoutWithPayment(method);
    if (sale != null) {
        receiptService.print(sale);
    } else {
        // Restore stock if payment failed
        salesService.clearCart();
    }
}

    }

    private void handleAddNewProduct() {
        String id = InputUtil.readLine("Product ID: ");
        String name = InputUtil.readLine("Product Name: ");
        double price = InputUtil.readDouble("Product Price: ");
        int stock = InputUtil.readInt("Initial Stock: ");
        inventoryService.addProduct(id, name, price, stock);
    }

    private void showStoreSettings() {
        storeSettings.display();
        System.out.println("Do you want to update the store settings? (yes/no)");
        String choice = InputUtil.readLine(">> ");
        if (choice.equalsIgnoreCase("yes")) {
            String name = InputUtil.readLine("New Store Name: ");
            String location = InputUtil.readLine("Location: ");
            String contact = InputUtil.readLine("Contact Info: ");
            storeSettings.updateSettings(name, location, contact);
            System.out.println("✅ Store settings updated.");
        }
    }

    private void generateSalesReport() {
        SalesReportGenerator report = new SalesReportGenerator(database.getSalesHistory());
        report.generateSummary();
    }

    private void exportSalesToCSV() {
        String filename = InputUtil.readLine("Enter filename to export (e.g. sales.csv): ");
        csvExporter.exportSales(database.getSalesHistory(), filename);
    }

    public static void main(String[] args) {
        new POSApplication().start();
    }
}

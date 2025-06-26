package com.pos.model;

import java.time.format.DateTimeFormatter;

public class Receipt {

    private final Sale sale;

    public Receipt(Sale sale) {
        this.sale = sale;
    }

    public void printReceipt() {
        System.out.println("====== RECEIPT ======");
        System.out.println("Sale ID: " + sale.getSaleId());
        System.out.println("Date: " + sale.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        System.out.println("---------------------");

        for (CartItem item : sale.getItems()) {
            System.out.println(item);
        }

        System.out.println("---------------------");
        System.out.printf("Total: R%.2f\n", sale.getTotalAmount());
        System.out.println("=====================");
    }
}

package com.pos.report;

import com.pos.model.Sale;

import java.util.List;

public class SalesReportGenerator {
    private final List<Sale> sales;

    public SalesReportGenerator(List<Sale> sales) {
        this.sales = sales;
    }

    public void generateSummary() {
        if (sales.isEmpty()) {
            System.out.println("📉 No sales to report.");
            return;
        }

        double total = 0;
        int count = 0;

        System.out.println("📊 Daily Sales Summary:");
        System.out.println("------------------------");
        for (Sale sale : sales) {
            System.out.printf("🧾 %s | R%.2f | %s\n",
                    sale.getSaleId(),
                    sale.getTotalAmount(),
                    sale.getTimestamp());
            total += sale.getTotalAmount();
            count++;
        }

        System.out.println("------------------------");
        System.out.printf("Total Sales Today: %d\n", count);
        System.out.printf("Total Revenue: R%.2f\n", total);
    }
}

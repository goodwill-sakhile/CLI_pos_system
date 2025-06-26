package com.pos.export;

import com.pos.model.Sale;
import com.pos.model.CartItem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {

    public void exportSales(List<Sale> sales, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Sale ID,Date,Product,Quantity,Price\n");
            for (Sale sale : sales) {
                for (CartItem item : sale.getItems()) {
                    writer.write(String.format("%s,%s,%s,%d,%.2f\n",
                            sale.getSaleId(),
                            sale.getTimestamp(),
                            item.getProduct().getName(),
                            item.getQuantity(),
                            item.getTotalPrice()));
                }
            }
            System.out.println(" Export complete: " + filename);
        } catch (IOException e) {
            System.out.println(" Failed to write to file: " + e.getMessage());
        }
    }
}

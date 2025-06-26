package com.pos.service;

import com.pos.model.Receipt;
import com.pos.model.Sale;

public class ReceiptService {

    public void print(Sale sale) {
        if (sale == null) {
            System.out.println("❌ No sale to print.");
            return;
        }

        Receipt receipt = new Receipt(sale);
        receipt.printReceipt();
    }
}

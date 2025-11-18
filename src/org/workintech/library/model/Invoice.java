// Invoice java
package org.workintech.library.model;

import java.time.LocalDateTime;

public class Invoice {
    private final int invoiceId;
    private final int readerId;
    private final double amount;
    private final LocalDateTime createdAt;

    public Invoice(int invoiceId, int readerId, double amount) {
        this.invoiceId = invoiceId;
        this.readerId = readerId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public int getInvoiceId() { return invoiceId; }
    public int getReaderId() { return readerId; }
    public double getAmount() { return amount; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Invoice{" + invoiceId + ", reader=" + readerId + ", amount=" + amount + "}";
    }
}

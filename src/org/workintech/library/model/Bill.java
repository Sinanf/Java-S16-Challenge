package org.workintech.library.model;

import java.time.LocalDate;

/**
 * Kitap ödünç alma ve iade işlemlerinde faturayı temsil eder.
 */
public class Bill {

    private final Reader reader;
    private final Book book;
    private final double amount;
    private final LocalDate date;

    public Bill(Reader reader, Book book, double amount) {
        this.reader = reader;
        this.book = book;
        this.amount = amount;
        this.date = LocalDate.now();
    }

    public Reader getReader() {
        return reader;
    }

    public Book getBook() {
        return book;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void printBill() {
        System.out.println("----- Fatura -----");
        System.out.println("Kullanıcı: " + reader.getName());
        System.out.println("Kitap: " + book.getTitle());
        System.out.println("Tarih: " + date);
        System.out.println("Tutar: " + amount + " TL");
        System.out.println("------------------");
    }
}

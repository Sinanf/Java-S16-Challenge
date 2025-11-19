package org.workintech.library.model;

import java.time.LocalDate;

/**
 * Bir kitabın kime ve hangi tarihte ödünç verildiğini tutan kayıt sınıfıdır.
 */
public class BorrowRecord {

    private final Reader reader;
    private final Book book;
    private final LocalDate borrowDate;
    private LocalDate returnDate; // iade edilince dolacak

    public BorrowRecord(Reader reader, Book book) {
        this.reader = reader;
        this.book = book;
        this.borrowDate = LocalDate.now();
    }

    public Reader getReader() {
        return reader;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
                "reader=" + reader.getName() +
                ", book=" + book.getTitle() +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}

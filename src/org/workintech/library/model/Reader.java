package org.workintech.library.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Kütüphaneden kitap ödünç alan kullanıcı.
 * 5 kitap limiti bu sınıf üzerinden takip edilecek.
 */
public class Reader extends Person {

    private static final int MAX_BOOK_LIMIT = 5;

    // Şu anda kullanıcıda bulunan kitaplar
    private Set<Book> borrowedBooks;

    public Reader(int id, String name) {
        super(id, name);
        this.borrowedBooks = new HashSet<>();
    }

    public Set<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public boolean canBorrowMore() {
        return borrowedBooks.size() < MAX_BOOK_LIMIT;
    }

    public void addBorrowedBook(Book book) {
        if (book != null) {
            borrowedBooks.add(book);
        }
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "Reader{id=" + getId() +
                ", name='" + getName() + '\'' +
                ", borrowedCount=" + borrowedBooks.size() +
                '}';
    }
}

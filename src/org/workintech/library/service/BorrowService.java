package org.workintech.library.service;

import org.workintech.library.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Kitap ödünç alma ve iade etme işlemlerinin tüm iş mantığını içerir.
 */
public class BorrowService {

    private final Map<Integer, BorrowRecord> activeBorrows; // bookId -> record

    public BorrowService() {
        this.activeBorrows = new HashMap<>();
    }


    private static final double BORROW_PRICE = 10.0;

    public Bill borrowBook(Reader reader, Book book) {

        if (book.getStatus() == BookStatus.BORROWED) {
            System.out.println("Bu kitap zaten başka bir kullanıcıda.");
            return null;
        }

        if (!reader.canBorrowMore()) {
            System.out.println("Kullanıcı kitap limitine ulaştı (5 kitap).");
            return null;
        }

        // Borrow Record oluştur
        BorrowRecord record = new BorrowRecord(reader, book);
        activeBorrows.put(book.getId(), record);

        // Kullanıcıya ekle
        reader.addBorrowedBook(book);

        // Kitabın durumunu güncelle
        book.setStatus(BookStatus.BORROWED);

        // Fatura oluştur
        Bill bill = new Bill(reader, book, BORROW_PRICE);
        bill.printBill();

        return bill;
    }

    public Bill returnBook(Reader reader, Book book) {

        if (!reader.getBorrowedBooks().contains(book)) {
            System.out.println("Bu kitap kullanıcıda görünmüyor.");
            return null;
        }

        BorrowRecord record = activeBorrows.get(book.getId());
        if (record == null) {
            System.out.println("Bu kitap için aktif ödünç kaydı bulunamadı.");
            return null;
        }
        record.setReturnDate(LocalDate.now());

        // Reader’dan çıkar
        reader.removeBorrowedBook(book);

        // Kitap durumunu güncelle
        book.setStatus(BookStatus.AVAILABLE);

        // Aktif kayıttan kaldır
        activeBorrows.remove(book.getId());

        // -10 TL iade faturası
        Bill refund = new Bill(reader, book, -BORROW_PRICE);
        refund.printBill();

        return refund;
    }

    public BorrowRecord getActiveBorrow(int bookId) {
        return activeBorrows.get(bookId);
    }
}

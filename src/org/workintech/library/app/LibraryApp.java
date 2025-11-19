package org.workintech.library.app;

import org.workintech.library.model.*;
import org.workintech.library.service.BookService;
import org.workintech.library.service.BorrowService;

import java.util.HashMap;
import java.util.Map;

public class LibraryApp {

    public static void main(String[] args) {

        Library library = new Library("Workintech Library");
        BookService bookService = new BookService(library);
        BorrowService borrowService = new BorrowService();

        // Örnek kullanıcılar
        Map<Integer, Reader> readers = new HashMap<>();
        readers.put(1, new Reader(1, "Sinan"));
        readers.put(2, new Reader(2, "Ayşe"));
        readers.put(3, new Reader(3, "Mehmet"));

        // Örnek kütüphaneciler
        Map<Integer, Librarian> librarians = new HashMap<>();
        librarians.put(1, new Librarian(1, "Ali - Senior Librarian"));
        librarians.put(2, new Librarian(2, "Zeynep - Assistant Librarian"));

        ConsoleMenu consoleMenu =
                new ConsoleMenu(library, bookService, borrowService, readers, librarians);

        consoleMenu.start();
    }
}

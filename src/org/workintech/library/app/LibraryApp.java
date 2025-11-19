package org.workintech.library.app;

import org.workintech.library.model.*;
import org.workintech.library.service.BookService;
import org.workintech.library.service.BorrowService;
import org.workintech.library.app.ConsoleMenu;
import java.util.HashMap;
import java.util.Map;


import java.time.LocalDate;

public class LibraryApp {

    public static void main(String[] args) {

        Library library = new Library("Workintech Library");
        BookService bookService = new BookService(library);
        BorrowService borrowService = new BorrowService();

        // Örnek kullanıcılar (istersen menüye user ekleme kısmı da ekleyebiliriz)
        Map<Integer, Reader> readers = new HashMap<>();
        readers.put(1, new Reader(1, "Sinan"));
        readers.put(2, new Reader(2, "Ayşe"));
        readers.put(3, new Reader(3, "Mehmet"));

        ConsoleMenu consoleMenu = new ConsoleMenu(library, bookService, borrowService, readers);
        consoleMenu.start();


//        Library library = new Library("Workintech Library");
//        BookService bookService = new BookService(library);
//
//        Author tolkien = new Author(1, "J. R. R. Tolkien");
//        Author rowling = new Author(2, "J. K. Rowling");
//
//        bookService.createAndAddBook(100, "The Hobbit", tolkien,
//                BookCategory.GENERAL, 1, LocalDate.of(2020, 1, 10));
//
//        bookService.createAndAddBook(101, "The Lord of the Rings", tolkien,
//                BookCategory.GENERAL, 3, LocalDate.of(2019, 5, 5));
//
//        bookService.createAndAddBook(102, "Harry Potter and the Sorcerer's Stone", rowling,
//                BookCategory.STUDY_BOOK, 1, LocalDate.of(2021, 3, 15));
//
//        System.out.println("=== All books in library ===");
//        library.getAllBooks().forEach(Book::display);
//
//        System.out.println("\n=== Search by author: Tolkien ===");
//        bookService.searchByAuthor("tolkien").forEach(Book::display);
    }
}

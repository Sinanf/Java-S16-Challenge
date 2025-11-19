package org.workintech.library.app;

import org.workintech.library.model.*;
import org.workintech.library.service.BookService;

import java.time.LocalDate;

public class LibraryApp {

    public static void main(String[] args) {

        Library library = new Library("Workintech Library");
        BookService bookService = new BookService(library);

        Author tolkien = new Author(1, "J. R. R. Tolkien");
        Author rowling = new Author(2, "J. K. Rowling");

        bookService.createAndAddBook(100, "The Hobbit", tolkien,
                BookCategory.GENERAL, 1, LocalDate.of(2020, 1, 10));

        bookService.createAndAddBook(101, "The Lord of the Rings", tolkien,
                BookCategory.GENERAL, 3, LocalDate.of(2019, 5, 5));

        bookService.createAndAddBook(102, "Harry Potter and the Sorcerer's Stone", rowling,
                BookCategory.STUDY_BOOK, 1, LocalDate.of(2021, 3, 15));

        System.out.println("=== All books in library ===");
        library.getAllBooks().forEach(Book::display);

        System.out.println("\n=== Search by author: Tolkien ===");
        bookService.searchByAuthor("tolkien").forEach(Book::display);
    }
}

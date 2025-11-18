package org.workintech.library.app;

import org.workintech.library.model.*;

import java.time.LocalDate;

/**
 * İlk test için basit main sınıfı.
 * Amaç: model'lerin doğru bağlanıp bağlanmadığını görmek.
 */
public class LibraryApp {

    public static void main(String[] args) {

        Author author = new Author(1, "J. R. R. Tolkien");

        Book book = new Book(
                100,
                "The Hobbit",
                author,
                BookCategory.GENERAL,
                1,
                LocalDate.now()
        );

        Reader reader = new Reader(10, "Sinan");

        book.display();
        System.out.println(author);
        System.out.println(reader);

        System.out.println("Library system skeleton is running...");
    }
}

package org.workintech.library.service;

import org.workintech.library.model.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Kitapla ilgili tüm iş kurallarını yöneten sınıf.
 * Library koleksiyonu BookService tarafından yönetilir.
 */
public class BookService {

    private final Library library;

    public BookService(Library library) {
        this.library = library;
    }

    public Book createAndAddBook(int id,
                                 String title,
                                 Author author,
                                 BookCategory category,
                                 int edition,
                                 LocalDate dateOfPurchase) {

        Book book;

        switch (category) {
            case STUDY_BOOK -> {

                book = new StudyBook(
                        id,
                        title,
                        author,
                        category,
                        edition,
                        dateOfPurchase,
                        "General Study"
                );
            }
            case JOURNAL -> {
                book = new Journal(
                        id,
                        title,
                        author,
                        category,
                        edition,
                        dateOfPurchase,
                        1 // issueNumber: şimdilik 1
                );
            }
            case MAGAZINE -> {
                book = new Magazine(
                        id,
                        title,
                        author,
                        category,
                        edition,
                        dateOfPurchase,
                        "General"
                );
            }

                default -> {
                    book = new Book(id, title, author, category, edition, dateOfPurchase);
                }
        }

        library.addBook(book);
        return book;
    }


    public boolean updateTitle(int bookId, String newTitle) {
        Book book = library.findById(bookId);
        if (book == null) {
            return false;
        }
        book.setTitle(newTitle);
        return true;
    }

    public boolean updateCategory(int bookId, BookCategory newCategory) {
        Book book = library.findById(bookId);
        if (book == null) {
            return false;
        }
        book.setCategory(newCategory);
        return true;
    }

    public boolean deleteBook(int bookId) {
        return library.removeBook(bookId);
    }

    public Book getById(int id) {
        return library.findById(id);
    }

    public List<Book> searchByTitle(String titlePart) {
        return library.findByTitleContains(titlePart);
    }

    public List<Book> searchByAuthor(String authorName) {
        return library.findByAuthorName(authorName);
    }

    public List<Book> searchByCategory(BookCategory category) {
        return library.findByCategory(category);
    }
}

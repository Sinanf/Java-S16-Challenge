package org.workintech.library.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Kütüphanedeki kitap koleksiyonunu yöneten sınıf.
 * BookService iş mantığını yaparken Library sadece veriyi tutar.
 */
public class Library {

    private final String name;
    private final Map<Integer, Book> booksById;

    public Library(String name) {
        this.name = name;
        this.booksById = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Collection<Book> getAllBooks() {
        return booksById.values();
    }

    public void addBook(Book book) {
        booksById.put(book.getId(), book);
    }

    public boolean removeBook(int id) {
        return booksById.remove(id) != null;
    }

    public Book findById(int id) {
        return booksById.get(id);
    }

    public List<Book> findByTitleContains(String titlePart) {
        String lower = titlePart.toLowerCase();
        return booksById.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public List<Book> findByAuthorName(String authorName) {
        String lower = authorName.toLowerCase();
        return booksById.values().stream()
                .filter(b -> b.getAuthor() != null &&
                        b.getAuthor().getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public List<Book> findByCategory(BookCategory category) {
        return booksById.values().stream()
                .filter(b -> b.getCategory() == category)
                .collect(Collectors.toList());
    }
}

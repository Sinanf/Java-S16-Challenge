package org.workintech.library.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Yazar bilgisi. Bir yazarın birden fazla kitabı olabilir.
 */
public class Author extends Person {

    // Composition: Author has many Books
    private Set<Book> books;

    public Author(int id, String name) {
        super(id, name);
        this.books = new HashSet<>();
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        if (book != null) {
            books.add(book);
        }
    }

    @Override
    public String toString() {
        return "Author{id=" + getId() +
                ", name='" + getName() + '\'' +
                ", bookCount=" + books.size() +
                '}';
    }
}

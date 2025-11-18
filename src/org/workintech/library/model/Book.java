package org.workintech.library.model;

import java.time.LocalDate;

/**
 * Temel kitap sınıfı.
 * Journal, Magazine, StudyBook gibi alt sınıflar buradan türeyecek.
 */
public class Book {

    private final int id;
    private String title;
    private Author author;
    private BookCategory category;
    private BookStatus status;
    private int edition;
    private LocalDate dateOfPurchase;

    public Book(int id,
                String title,
                Author author,
                BookCategory category,
                int edition,
                LocalDate dateOfPurchase) {

        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.edition = edition;
        this.dateOfPurchase = dateOfPurchase;
        this.status = BookStatus.AVAILABLE;


        if (author != null) {
            author.addBook(this);
        }
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    /**
     * Konsolda tek satırda özet göstermek için.
     */
    public void display() {
        System.out.println(
                "[" + id + "] " + title +
                        " | Author: " + (author != null ? author.getName() : "Unknown") +
                        " | Category: " + category +
                        " | Status: " + status
        );
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + (author != null ? author.getName() : "Unknown") +
                ", category=" + category +
                ", status=" + status +
                '}';
    }
}

package org.workintech.library.model;

import java.time.LocalDate;

/**
 * Popüler dergi / magazin.
 */
public class Magazine extends Book {

    private String genre;

    public Magazine(int id,
                    String title,
                    Author author,
                    BookCategory category,
                    int edition,
                    LocalDate dateOfPurchase,
                    String genre) {

        super(id, title, author, category, edition, dateOfPurchase);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public void display() {
        System.out.println(
                "[MAGAZINE] " + getId() + " - " + getTitle() +
                        " | Genre: " + genre +
                        " | Author: " + (getAuthor() != null ? getAuthor().getName() : "Unknown") +
                        " | Status: " + getStatus()
        );
    }
}

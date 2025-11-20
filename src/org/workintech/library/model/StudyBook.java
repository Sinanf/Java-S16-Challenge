package org.workintech.library.model;

import java.time.LocalDate;

/**
 * Çalışma kitabı / ders kitabı.
 * Book sınıfından türeyerek polymorphism sağlar.
 */
public class StudyBook extends Book {

    private String subject;

    public StudyBook(int id,
                     String title,
                     Author author,
                     BookCategory category,
                     int edition,
                     LocalDate dateOfPurchase,
                     String subject) {

        super(id, title, author, category, edition, dateOfPurchase);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public void display() {
        System.out.println(
                "[STUDY BOOK] " + getId() + " - " + getTitle() +
                        " | Subject: " + subject +
                        " | Author: " + (getAuthor() != null ? getAuthor().getName() : "Unknown") +
                        " | Status: " + getStatus()
        );
    }
}

package org.workintech.library.model;

import java.time.LocalDate;

/**
 * Dergi / akademik journal temsil eder.
 */
public class Journal extends Book {

    private int issueNumber;

    public Journal(int id,
                   String title,
                   Author author,
                   BookCategory category,
                   int edition,
                   LocalDate dateOfPurchase,
                   int issueNumber) {

        super(id, title, author, category, edition, dateOfPurchase);
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    @Override
    public void display() {
        System.out.println(
                "[JOURNAL] " + getId() + " - " + getTitle() +
                        " | Issue: " + issueNumber +
                        " | Author: " + (getAuthor() != null ? getAuthor().getName() : "Unknown") +
                        " | Status: " + getStatus()
        );
    }
}

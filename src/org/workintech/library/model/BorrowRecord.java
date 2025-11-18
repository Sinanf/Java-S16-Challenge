// BorrowRecord java
package org.workintech.library.model;

import java.time.LocalDateTime;

public class BorrowRecord {
    private final int bookId;
    private final int readerId;
    private final LocalDateTime borrowAt;

    public BorrowRecord(int bookId, int readerId) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.borrowAt = LocalDateTime.now();
    }

    public int getBookId() { return bookId; }
    public int getReaderId() { return readerId; }
    public LocalDateTime getBorrowAt() { return borrowAt; }
}

// MemberRecord java
package org.workintech.library.model;

public class MemberRecord {
    private final int memberId;
    private final String name;
    private final String address;
    private final String phone;
    private int booksIssued = 0;
    private final int maxBookLimit = 5;

    public MemberRecord(int memberId, String name, String address, String phone) {
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getMemberId() { return memberId; }
    public int getBooksIssued() { return booksIssued; }
    public void incBooksIssued() { booksIssued++; }
    public void decBooksIssued() { if (booksIssued>0) booksIssued--; }
    public int getMaxBookLimit() { return maxBookLimit; }
}

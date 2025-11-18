package org.workintech.library.model;

/**
 * Sistemdeki tüm kişi tiplerinin (Reader, Author, Librarian) ortak atası.
 * Direkt Person nesnesi oluşturulmaz, sadece extend edilir.
 */

public abstract class Person {

    private final int id;
    private String name;

    protected Person(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{id=" + id + ", name='" + name + "'}";
    }
}

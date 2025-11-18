package org.workintech.library.model;

/**
 * Kütüphaneyi yöneten görevli.
 * Şimdilik ekstra alan yok, ileride rol vb. eklenebilir.
 */
public class Librarian extends Person {

    public Librarian(int id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Librarian{id=" + getId() +
                ", name='" + getName() + '\'' +
                '}';
    }
}

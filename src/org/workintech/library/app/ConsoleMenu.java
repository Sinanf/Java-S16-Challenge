package org.workintech.library.app;

import org.workintech.library.model.*;
import org.workintech.library.service.BookService;
import org.workintech.library.service.BorrowService;
import org.workintech.library.util.InputUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Konsol üzerinden kullanıcıyla etkileşimi yöneten sınıf.
 * BookService, BorrowService ve Library üzerinden kitap operasyonları yapılır.
 */
public class ConsoleMenu {

    private final Library library;
    private final BookService bookService;
    private final BorrowService borrowService;
    private final Map<Integer, Reader> readers;
    private final Map<Integer, Librarian> librarians;

    private final Scanner scanner;
    private Librarian activeLibrarian;

    public ConsoleMenu(Library library,
                       BookService bookService,
                       BorrowService borrowService,
                       Map<Integer, Reader> readers,
                       Map<Integer, Librarian> librarians) {

        this.library = library;
        this.bookService = bookService;
        this.borrowService = borrowService;
        this.readers = readers;
        this.librarians = librarians;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        
        if (!librarians.isEmpty()) {
            selectLibrarianFlow();
        }

        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addBookFlow();
                case "2" -> listAllBooks();
                case "3" -> searchById();
                case "4" -> searchByTitle();
                case "5" -> searchByAuthor();
                case "6" -> deleteBook();
                case "7" -> borrowBookFlow();
                case "8" -> returnBookFlow();
                case "9" -> listReaderBooksFlow();
                case "10" -> updateBookFlow();
                case "11" -> listByCategoryFlow();
                case "12" -> selectLibrarianFlow();
                case "0" -> {
                    running = false;
                    System.out.println("Sistemden çıkılıyor. Görüşmek üzere!");
                }
                default -> System.out.println("Geçersiz seçim, lütfen tekrar deneyin.");
            }

            System.out.println(); 
        }
        
    }

    private void printMenu() {
        System.out.println("===== WORKINTECH LIBRARY MENU =====");

        if (activeLibrarian != null) {
            System.out.println("Aktif Kütüphaneci: " + activeLibrarian.getName());
        }

        System.out.println("01 - Yeni kitap ekle");
        System.out.println("02 - Tüm kitapları listele");
        System.out.println("03 - Kitabı ID ile ara");
        System.out.println("04 - Kitabı isme göre ara");
        System.out.println("05 - Yazara göre kitap ara");
        System.out.println("06 - Kitap sil");
        System.out.println("07 - Kitap ödünç al");
        System.out.println("08 - Kitap iade et");
        System.out.println("09 - Kullanıcının elindeki kitapları listele");
        System.out.println("10 - Kitap bilgisi güncelle");
        System.out.println("11 - Kategoriye göre kitap listele");
        System.out.println("12 - Aktif kütüphaneci seç");
        System.out.println("0  - Çıkış");
        System.out.print("Seçiminiz: ");
    }

    /**
     * Aktif kütüphaneciyi seçme akışı
     */
    private void selectLibrarianFlow() {
        System.out.println("=== Kütüphaneci Seç ===");
        librarians.forEach((id, lib) ->
                System.out.println("ID: " + id + " | İsim: " + lib.getName()));

        int librarianId = InputUtil.readInt(scanner, "Kütüphaneci ID girin: ");
        Librarian selected = librarians.get(librarianId);

        if (selected == null) {
            System.out.println("Bu ID'ye sahip kütüphaneci bulunamadı.");
            return;
        }

        this.activeLibrarian = selected;
        System.out.println("Aktif kütüphaneci: " + activeLibrarian.getName());
    }

    /**
     * 1 - Yeni kitap ekleme akışı
     */
    private void addBookFlow() {
        System.out.println("=== Yeni Kitap Ekle ===");

        int id = InputUtil.readInt(scanner, "Kitap ID: ");
        String title = InputUtil.readNonEmpty(scanner, "Kitap başlığı: ");
        int authorId = InputUtil.readInt(scanner, "Yazar ID: ");

        System.out.print("Yazar adı: ");
        String authorName = scanner.nextLine().trim();

        Author author = new Author(authorId, authorName);
        BookCategory category = askCategoryFromUser();

        int edition = InputUtil.readInt(scanner, "Baskı (edition): ");
        LocalDate purchaseDate = LocalDate.now();

        Book created = bookService.createAndAddBook(
                id,
                title,
                author,
                category,
                edition,
                purchaseDate
        );

        System.out.println("Kitap eklendi:");
        created.display();

        if (activeLibrarian != null) {
            System.out.println("İşlemi yapan kütüphaneci: " + activeLibrarian.getName());
        }
    }

    /**
     * Kullanıcıdan kategori seçmesini isteyen yardımcı metod
     */
    private BookCategory askCategoryFromUser() {
        while (true) {
            System.out.println("Kategori seçin:");
            System.out.println("1 - GENERAL");
            System.out.println("2 - STUDY_BOOK");
            System.out.println("3 - JOURNAL");
            System.out.println("4 - MAGAZINE");
            System.out.print("Seçiminiz: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    return BookCategory.GENERAL;
                case "2":
                    return BookCategory.STUDY_BOOK;
                case "3":
                    return BookCategory.JOURNAL;
                case "4":
                    return BookCategory.MAGAZINE;
                default:
                    System.out.println("Geçersiz kategori, lütfen 1-4 arasında bir değer girin.");
            }
        }
    }

    /**
     * 2 - Tüm kitapları listeleme
     */
    private void listAllBooks() {
        System.out.println("=== Tüm Kitaplar ===");
        if (library.getAllBooks().isEmpty()) {
            System.out.println("Kütüphanede hiç kitap yok.");
            return;
        }
        library.getAllBooks().forEach(Book::display);
    }

    /**
     * 3 - ID ile arama
     */
    private void searchById() {
        int id = InputUtil.readInt(scanner, "Aranacak kitap ID: ");

        Book book = bookService.getById(id);
        if (book == null) {
            System.out.println("Bu ID'ye sahip kitap bulunamadı.");
        } else {
            System.out.println("Kitap bulundu:");
            book.display();
        }
    }

    /**
     * 4 - Başlıkla arama
     */
    private void searchByTitle() {
        System.out.print("Aranacak başlık (veya parçası): ");
        String query = scanner.nextLine().trim();

        List<Book> results = bookService.searchByTitle(query);
        if (results.isEmpty()) {
            System.out.println("Bu başlıkta bir kitap bulunamadı.");
        } else {
            System.out.println("Bulunan kitaplar:");
            results.forEach(Book::display);
        }
    }

    /**
     * 5 - Yazara göre arama
     */
    private void searchByAuthor() {
        System.out.print("Yazar adı (veya parçası): ");
        String query = scanner.nextLine().trim();

        List<Book> results = bookService.searchByAuthor(query);
        if (results.isEmpty()) {
            System.out.println("Bu yazara ait kitap bulunamadı.");
        } else {
            System.out.println("Bulunan kitaplar:");
            results.forEach(Book::display);
        }
    }

    /**
     * 6 - Kitap silme
     */
    private void deleteBook() {
        int id = InputUtil.readInt(scanner, "Silinecek kitap ID: ");

        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            System.out.println("Kitap başarıyla silindi.");
            if (activeLibrarian != null) {
                System.out.println("İşlemi yapan kütüphaneci: " + activeLibrarian.getName());
            }
        } else {
            System.out.println("Bu ID'ye sahip kitap bulunamadı, silinemedi.");
        }
    }

    /**
     * 7 - Kitap ödünç alma akışı
     */
    private void borrowBookFlow() {
        System.out.println("=== Kitap Ödünç Alma ===");

        Reader reader = askReaderFromUser();
        if (reader == null) {
            return;
        }

        int bookId = InputUtil.readInt(scanner, "Ödünç alınacak kitap ID: ");

        Book book = bookService.getById(bookId);
        if (book == null) {
            System.out.println("Bu ID'ye sahip kitap bulunamadı.");
            return;
        }

        borrowService.borrowBook(reader, book);
    }

    /**
     * 8 - Kitap iade akışı
     */
    private void returnBookFlow() {
        System.out.println("=== Kitap İade ===");

        Reader reader = askReaderFromUser();
        if (reader == null) {
            return;
        }

        int bookId = InputUtil.readInt(scanner, "İade edilecek kitap ID: ");

        Book book = bookService.getById(bookId);
        if (book == null) {
            System.out.println("Bu ID'ye sahip kitap bulunamadı.");
            return;
        }

        borrowService.returnBook(reader, book);
    }

    /**
     * 9 - Kullanıcının elindeki kitapları listeleme
     */
    private void listReaderBooksFlow() {
        Reader reader = askReaderFromUser();
        if (reader == null) {
            return;
        }

        System.out.println("=== " + reader.getName() + " kullanıcısının elindeki kitaplar ===");
        if (reader.getBorrowedBooks().isEmpty()) {
            System.out.println("Bu kullanıcının elinde kitap yok.");
            return;
        }

        reader.getBorrowedBooks().forEach(Book::display);
    }

    /**
     * 10 - Kitap bilgisi güncelle
     */
    private void updateBookFlow() {
        System.out.println("=== Kitap Güncelle ===");

        int bookId = InputUtil.readInt(scanner, "Güncellenecek kitap ID: ");

        Book book = bookService.getById(bookId);
        if (book == null) {
            System.out.println("Bu ID'ye sahip kitap bulunamadı.");
            return;
        }

        System.out.println("Güncellenecek alanı seçin:");
        System.out.println("1 - Başlık (Title)");
        System.out.println("2 - Kategori");
        System.out.println("0 - İptal");
        System.out.print("Seçiminiz: ");

        String input = scanner.nextLine().trim();

        switch (input) {
            case "1" -> {
                System.out.print("Yeni başlık: ");
                String newTitle = scanner.nextLine().trim();
                bookService.updateTitle(bookId, newTitle);
                System.out.println("Başlık güncellendi.");
            }
            case "2" -> {
                BookCategory newCategory = askCategoryFromUser();
                bookService.updateCategory(bookId, newCategory);
                System.out.println("Kategori güncellendi.");
            }
            case "0" -> System.out.println("İşlem iptal edildi.");
            default -> System.out.println("Geçersiz seçim.");
        }
    }

    /**
     * 11 - Kategoriye göre listeleme
     */
    private void listByCategoryFlow() {
        System.out.println("=== Kategoriye Göre Listeleme ===");

        BookCategory category = askCategoryFromUser();

        List<Book> results = bookService.searchByCategory(category);

        if (results.isEmpty()) {
            System.out.println("Bu kategoride hiç kitap yok.");
        } else {
            System.out.println("Kategori: " + category);
            results.forEach(Book::display);
        }
    }

    /**
     * Kullanıcıdan readerId isteyip, map'ten Reader getiren yardımcı metod.
     */
    private Reader askReaderFromUser() {
        System.out.println("Mevcut kullanıcılar:");
        readers.forEach((id, r) ->
                System.out.println("ID: " + id + " | İsim: " + r.getName()));

        int readerId = InputUtil.readInt(scanner, "Kullanıcı ID girin: ");

        Reader reader = readers.get(readerId);
        if (reader == null) {
            System.out.println("Bu ID'ye sahip kullanıcı bulunamadı.");
            return null;
        }
        return reader;
    }
}

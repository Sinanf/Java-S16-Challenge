package org.workintech.library.app;

import org.workintech.library.model.*;
import org.workintech.library.service.BookService;
import org.workintech.library.service.BorrowService;

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
    private final Scanner scanner;

    public ConsoleMenu(Library library,
                       BookService bookService,
                       BorrowService borrowService,
                       Map<Integer, Reader> readers) {

        this.library = library;
        this.bookService = bookService;
        this.borrowService = borrowService;
        this.readers = readers;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
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
                case "0" -> {
                    running = false;
                    System.out.println("Sistemden çıkılıyor. Görüşmek üzere!");
                }
                default -> System.out.println("Geçersiz seçim, lütfen tekrar deneyin.");
            }

            System.out.println(); // her işlemin sonuna boş satır
        }
    }

    private void printMenu() {
        System.out.println("===== WORKINTECH LIBRARY MENU =====");
        System.out.println("1 - Yeni kitap ekle");
        System.out.println("2 - Tüm kitapları listele");
        System.out.println("3 - Kitabı ID ile ara");
        System.out.println("4 - Kitabı isme göre ara");
        System.out.println("5 - Yazara göre kitap ara");
        System.out.println("6 - Kitap sil");
        System.out.println("7 - Kitap ödünç al");
        System.out.println("8 - Kitap iade et");
        System.out.println("9 - Kullanıcının elindeki kitapları listele");
        System.out.println("0 - Çıkış");
        System.out.print("Seçiminiz: ");
    }

    /**
     * 1 - Yeni kitap ekleme akışı
     */
    private void addBookFlow() {
        try {
            System.out.println("=== Yeni Kitap Ekle ===");

            System.out.print("Kitap ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Kitap başlığı: ");
            String title = scanner.nextLine().trim();

            System.out.print("Yazar ID: ");
            int authorId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Yazar adı: ");
            String authorName = scanner.nextLine().trim();

            Author author = new Author(authorId, authorName);

            BookCategory category = askCategoryFromUser();

            System.out.print("Baskı (edition) numarası: ");
            int edition = Integer.parseInt(scanner.nextLine().trim());

            // Şimdilik satın alma tarihi = bugün
            LocalDate purchaseDate = LocalDate.now();

            Book created = bookService.createAndAddBook(
                    id,
                    title,
                    author,
                    category,
                    edition,
                    purchaseDate
            );

            System.out.println("Kitap eklendi: ");
            created.display();
        } catch (NumberFormatException e) {
            System.out.println("Sayısal bir değeri hatalı girdiniz. Lütfen tekrar deneyin.");
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
        try {
            System.out.print("Aranacak kitap ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Book book = bookService.getById(id);
            if (book == null) {
                System.out.println("Bu ID'ye sahip kitap bulunamadı.");
            } else {
                System.out.println("Kitap bulundu:");
                book.display();
            }
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz ID girdiniz.");
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
        try {
            System.out.print("Silinecek kitap ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            boolean deleted = bookService.deleteBook(id);
            if (deleted) {
                System.out.println("Kitap başarıyla silindi.");
            } else {
                System.out.println("Bu ID'ye sahip kitap bulunamadı, silinemedi.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz ID girdiniz.");
        }
    }

    /**
     * 7 - Kitap ödünç alma akışı
     */
    private void borrowBookFlow() {
        try {
            System.out.println("=== Kitap Ödünç Alma ===");

            Reader reader = askReaderFromUser();
            if (reader == null) {
                return;
            }

            System.out.print("Ödünç alınacak kitap ID: ");
            int bookId = Integer.parseInt(scanner.nextLine().trim());

            Book book = bookService.getById(bookId);
            if (book == null) {
                System.out.println("Bu ID'ye sahip kitap bulunamadı.");
                return;
            }

            borrowService.borrowBook(reader, book);
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz ID girdiniz.");
        }
    }

    /**
     * 8 - Kitap iade akışı
     */
    private void returnBookFlow() {
        try {
            System.out.println("=== Kitap İade ===");

            Reader reader = askReaderFromUser();
            if (reader == null) {
                return;
            }

            System.out.print("İade edilecek kitap ID: ");
            int bookId = Integer.parseInt(scanner.nextLine().trim());

            Book book = bookService.getById(bookId);
            if (book == null) {
                System.out.println("Bu ID'ye sahip kitap bulunamadı.");
                return;
            }

            borrowService.returnBook(reader, book);
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz ID girdiniz.");
        }
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
     * Kullanıcıdan readerId isteyip, map'ten Reader getiren yardımcı metod.
     */
    private Reader askReaderFromUser() {
        System.out.println("Mevcut kullanıcılar:");
        readers.forEach((id, r) ->
                System.out.println("ID: " + id + " | İsim: " + r.getName()));

        System.out.print("Kullanıcı ID girin: ");
        String input = scanner.nextLine().trim();

        try {
            int readerId = Integer.parseInt(input);
            Reader reader = readers.get(readerId);
            if (reader == null) {
                System.out.println("Bu ID'ye sahip kullanıcı bulunamadı.");
                return null;
            }
            return reader;
        } catch (NumberFormatException e) {
            System.out.println("Geçersiz kullanıcı ID'si girdiniz.");
            return null;
        }
    }
}

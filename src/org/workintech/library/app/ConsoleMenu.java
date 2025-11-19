package org.workintech.library.app;

import org.workintech.library.model.*;
import org.workintech.library.service.BookService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Konsol üzerinden kullanıcıyla etkileşimi yöneten sınıf.
 * BookService ve Library üzerinden kitap işlemleri yapılır.
 */
public class ConsoleMenu {

    private final Library library;
    private final BookService bookService;
    private final Scanner scanner;

    public ConsoleMenu(Library library, BookService bookService) {
        this.library = library;
        this.bookService = bookService;
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
        System.out.println("0 - Çıkış");
        System.out.print("Seçiminiz: ");
    }

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

            // Şimdilik satın alma tarihini bugünün tarihi yapıyoruz.
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

    private void listAllBooks() {
        System.out.println("=== Tüm Kitaplar ===");
        if (library.getAllBooks().isEmpty()) {
            System.out.println("Kütüphanede hiç kitap yok.");
            return;
        }
        library.getAllBooks().forEach(Book::display);
    }

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
}

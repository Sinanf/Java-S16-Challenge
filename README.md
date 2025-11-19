# 📚 Library Management System – Java OOP Console Application

Bu proje, Java ile geliştirilmiş **Object Oriented Design** odaklı bir kütüphane otomasyon sistemidir.  
Amaç; nesne tabanlı prensipleri (Encapsulation, Inheritance, Abstraction, Polymorphism, Composition) gerçekçi bir senaryo üzerinde uygulamak ve konsol tabanlı bir kütüphane sistemi tasarlamaktır.

Uygulama tamamen **konsol** üzerinden çalışır ve kullanıcı etkileşimi `Scanner` + `InputUtil` ile yönetilir.

---

## 🔧 Özellikler

### 📘 Kitap İşlemleri
- Yeni kitap ekleme
- Tüm kitapları listeleme
- Kitabı:
    - ID ile arama
    - Başlık (title) ile arama
    - Yazar adına göre arama
- Kitap bilgilerini güncelleme:
    - Başlık (title)
    - Kategori
- Kitap silme
- Kategoriye göre kitap listeleme

### 👤 Kullanıcı (Reader) İşlemleri
- Sistemde tanımlı kullanıcılar (Reader) bulunur
- Her kullanıcı:
    - Kitap ödünç alabilir
    - Kitap iade edebilir
    - En fazla **5 kitap** alabilir
    - Elindeki kitapları listeleyebilir

5 kitap limiti `Reader` sınıfı üzerinden takip edilir (`canBorrowMore()` + `borrowedBooks` set).

### 🧾 Ödünç Alma & Fatura Sistemi
- Kullanıcı kitap ödünç aldığında:
    - Kitabın durumu **AVAILABLE → BORROWED** olur
    - `BorrowRecord` oluşturulur (`reader`, `book`, `borrowDate`)
    - Kullanıcıya **Bill** kesilir (10 TL) ve konsola yazdırılır
- Kitap iade edildiğinde:
    - Kitabın durumu **BORROWED → AVAILABLE** olur
    - `BorrowRecord.returnDate` güncellenir
    - Kayıt aktif listeden çıkarılır
    - Kullanıcıya **iade faturası** (–10 TL) kesilir

Bu sayede:
- Hangi kitabın kimde olduğu
- Ne zaman alındığı / iade edildiği
- Ne kadar ücret kesildiği / iade edildiği  
  sistem tarafından takip edilir.

### 🧑‍💼 Librarian Desteği

Sistemde ayrıca **Librarian (kütüphaneci)** rolü bulunur:

- Uygulama açılırken kullanıcıdan **aktif kütüphaneci** seçmesi istenir
- Menüde her zaman “Aktif Kütüphaneci” gösterilir
- Kitap ekleme ve kitap silme işlemlerinde:
    - `addBookFlow` / `deleteBook` sonunda hangi kütüphanecinin bu işlemi yaptığı loglanır

Bu sayede UML diyagramında belirtilen **Librarian** sınıfı sadece teoride kalmaz, gerçek iş akışında da kullanılır.

---

## 🧬 Kullanılan OOP Kavramları

### Encapsulation
- Tüm model sınıflarında alanlar `private`
- Erişimler `getter/setter` metodları ile yönetiliyor:
    - `Book`, `Author`, `Reader`, `Librarian`, `BorrowRecord`, `Library`, `Bill` vb.

### Inheritance
- Ortak kişi özellikleri `Person` (abstract) sınıfında toplanıyor:
    - `Author`, `Reader`, `Librarian` → `Person`’dan türetiliyor

### Abstract Class
- `Person` sınıfı `abstract`:
    - Doğrudan `Person` nesnesi oluşturulmuyor
    - Sadece alt sınıflar üzerinden kullanılıyor (`Reader`, `Author`, `Librarian`)

### Polymorphism
- Temel kitap sınıfı: `Book`
- Özel tipler:
    - `StudyBook extends Book`
    - `Journal extends Book`
    - `Magazine extends Book`
- Her biri `display()` metodunu override ediyor:
    - `StudyBook.display()` → subject gösteriyor
    - `Journal.display()` → issue number gösteriyor
    - `Magazine.display()` → genre gösteriyor
- Koleksiyonlar **`Collection<Book>`** üzerinden yönetiliyor, çağrılan `display()` runtime’da gerçek tipe göre çalışıyor → **runtime polymorphism**.

### Composition
- `Author` → `Set<Book> books`
- `Reader` → `Set<Book> borrowedBooks`
- `Library` → `Map<Integer, Book> booksById`
- `BorrowService` → `Map<Integer, BorrowRecord> activeBorrows`

Objeler birbirini “sahiplenerek” daha büyük yapılar oluşturuyor.

### Collections & Map Kullanımı
- `Map<Integer, Book>` → kitaplar ID ile saklanır (`Library`)
- `Map<Integer, Reader>` → kullanıcılar ID ile (`LibraryApp`)
- `Map<Integer, Librarian>` → kütüphaneciler ID ile
- `Map<Integer, BorrowRecord>` → ödünç alınmış kitapların aktif kayıtları (`BorrowService`)
- `Set<Book>` → bir yazarın kitapları, bir kullanıcının elindeki kitaplar
- `List<Book>` → arama sonuçları (title, author, category)

---

## 🏗️ Mimari ve Paket Yapısı

```text
org.workintech.library
 ├── app
 │   ├── LibraryApp      → uygulama giriş noktası (main)
 │   └── ConsoleMenu     → konsol menü / kullanıcı etkileşimi
 │
 ├── model
 │   ├── Person (abstract)
 │   ├── Author, Reader, Librarian
 │   ├── Book, StudyBook, Journal, Magazine
 │   ├── Library, BorrowRecord, Bill
 │   ├── enums: BookCategory, BookStatus
 │
 ├── service
 │   ├── BookService     → kitap ekleme/silme/güncelleme/arama iş mantığı
 │   └── BorrowService   → ödünç alma, iade, limit ve fatura
 │
 └── util
     └── InputUtil       → Scanner tabanlı input okuma helper'ı

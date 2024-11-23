import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true; // По умолчанию книга доступна
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void markAsLoaned() {
        isAvailable = false;
    }

    public void markAsAvailable() {
        isAvailable = true;
    }
}

class Reader {
    private String name;
    private List<Book> borrowedBooks;
    private final int maxBooks;

    public Reader(String name, int maxBooks) {
        this.name = name;
        this.maxBooks = maxBooks;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean borrowBook(Book book) {
        if (book.isAvailable() && borrowedBooks.size() < maxBooks) {
            borrowedBooks.add(book);
            book.markAsLoaned();
            System.out.println(name + " арендовал(а) книгу: " + book.getTitle());
            return true;
        } else {
            System.out.println(name + " не может арендовать книгу: " + book.getTitle());
            return false;
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            book.markAsAvailable();
            System.out.println(name + " вернул(а) книгу: " + book.getTitle());
        } else {
            System.out.println(name + " не арендовал(а) книгу: " + book.getTitle());
        }
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
}

class Librarian {
    private String name;

    public Librarian(String name) {
        this.name = name;
    }

    public void addBook(Library library, Book book) {
        library.addBook(book);
        System.out.println("Библиотекарь " + name + " добавил книгу: " + book.getTitle());
    }

    public void removeBook(Library library, Book book) {
        library.removeBook(book);
        System.out.println("Библиотекарь " + name + " удалил книгу: " + book.getTitle());
    }
}

class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<Book> searchBooks(String query) {
        return books.stream()
                .filter(book -> book.getTitle().contains(query) || book.getAuthor().contains(query))
                .collect(Collectors.toList());
    }

    public void displayBooks() {
        System.out.println("Все книги в библиотеке:");
        for (Book book : books) {
            String status = book.isAvailable() ? "Доступна" : "Арендована";
            System.out.println("- " + book.getTitle() + " (Автор: " + book.getAuthor() + ", Статус: " + status + ")");
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        // Создание объектов
        Library library = new Library();
        Librarian librarian = new Librarian("Анна");
        Reader reader = new Reader("Иван", 2);

        // Добавление книг
        Book book1 = new Book("Программирование на Java", "Автор A", "123-456");
        Book book2 = new Book("Алгоритмы и структуры данных", "Автор B", "789-101");
        Book book3 = new Book("Основы C++", "Автор C", "112-233");

        librarian.addBook(library, book1);
        librarian.addBook(library, book2);
        librarian.addBook(library, book3);

        // Отображение всех книг
        library.displayBooks();

        // Читатель берет книгу
        reader.borrowBook(book1);
        reader.borrowBook(book2);

        // Попытка арендовать книгу, когда уже превышен лимит
        reader.borrowBook(book3);

        // Поиск книг по запросу
        System.out.println("Поиск книг по запросу 'Программирование':");
        List<Book> searchResults = library.searchBooks("Программирование");
        for (Book b : searchResults) {
            System.out.println("- " + b.getTitle() + " (Автор: " + b.getAuthor() + ")");
        }

        // Возврат книги
        reader.returnBook(book1);

        // Отображение всех книг после изменений
        library.displayBooks();
    }
}

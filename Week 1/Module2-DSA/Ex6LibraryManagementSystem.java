import java.util.Scanner;
import java.util.Arrays;

class Book {
    private final int bookId;
    private String title;
    private String author;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}

class Library {
    private Book[] books;
    private int n;
    private int size;

    public Library(int size) {
        this.books = new Book[size];
        this.size = size;
        this.n = 0;
    }

    public void getIthBookDetails(int index) {

        if (!(index < n)) {
            System.out.println("Index out of range");
            return;
        }

        System.out.println();
        System.out.println("Book Id: " + books[index].getBookId());
        System.out.println("Title:   " + books[index].getTitle());
        System.out.println("Author:  " + books[index].getAuthor());
        System.out.println();
    }

    public void add(Book book, int index) {
        if (size == n) {
            System.out.println("Array is Full");
            return;
        }

        if (index < 0 || index > n) {
            System.out.println("Invalid index");
            return;
        }

        for (int i = n - 1; i >= index; i++) {
            books[i + 1] = books[i];
        }
        books[index] = book;
        n++;
    }

    public int linearSearchBook(String bookTitle) {
        for (int i = 0; i < n; i++) {
            if (books[i].getTitle().equalsIgnoreCase(bookTitle)) {
                return i;
            }
        }

        return -1;
    }

    private String[] getBookTitles() {
        String[] bookTitles = new String[n];

        for (int i = 0; i < n; i++) {
            bookTitles[i] = books[i].getTitle();
        }

        Arrays.sort(bookTitles);

        return bookTitles;
    }

    public int binarySearchBook(String bookTitle) {
        int start = 0;
        int end = n - 1;

        String[] bookTitles = getBookTitles();

        while (start <= end) {
            int mid = (start + end) / 2;
            int cmp = bookTitle.compareToIgnoreCase(bookTitles[mid]);

            if (cmp == 0) {
                return mid;
            } 
            else if (cmp > 0) {
                start = mid + 1;
            } 
            else {
                end = mid - 1;
            }
        }

        return -1;
    }

    public void displayAllBooks() {
        if (n == 0) {
            System.out.println("No books in the library.");
        } else {
            for (int i = 0; i < n; i++) {
                System.out.println("Book Id: " + books[i].getBookId());
                System.out.println("Title: " + books[i].getTitle());
                System.out.println("Author: " + books[i].getAuthor());
                System.out.println();
            }
        }
    }

}

public class Ex6LibraryManagementSystem {
    private static Scanner sc = new Scanner(System.in);
    private static Library library = new Library(100);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n=============================================\n");
            System.out.println("Library Management System Menu:");
        System.out.println("\n=============================================\n");
            System.out.println("1. Add Book");
            System.out.println("2. Get Book Details by Index");
            System.out.println("3. Linear Search Book by Title");
            System.out.println("4. Binary Search Book by Title");
            System.out.println("5. Display All Books");
            System.out.println("6. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    getBookDetailsByIndex();
                    break;
                case 3:
                    linearSearchBookByTitle();
                    break;
                case 4:
                    binarySearchBookByTitle();
                    break;
                case 5:
                    displayAllBooks();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        sc.close();
        System.out.println("Exiting the application.... Goodbye!");
        
    }

    private static void addBook() {
        System.out.print("Enter book ID: ");
        int bookId = sc.nextInt();
        sc.nextLine(); 

        System.out.print("Enter book title: ");
        String title = sc.nextLine();

        System.out.print("Enter book author: ");
        String author = sc.nextLine();

        System.out.print("Enter index to add the book: ");
        int index = sc.nextInt();

        Book newBook = new Book(bookId, title, author);
        library.add(newBook, index);
    }

    private static void getBookDetailsByIndex() {
        System.out.print("Enter index to get book details: ");
        int index = sc.nextInt();
        library.getIthBookDetails(index);
    }

    private static void linearSearchBookByTitle() {
        System.out.print("Enter book title to search: ");
        String title = sc.nextLine();
        int index = library.linearSearchBook(title);
        if (index == -1) {
            System.out.println("Book not found.");
        } else {
            System.out.println("Book found at index: " + index);
        }
    }

    private static void binarySearchBookByTitle() {
        System.out.print("Enter book title to search: ");
        String title = sc.nextLine();
        int index = library.binarySearchBook(title);
        if (index == -1) {
            System.out.println("Book not found.");
        } else {
            System.out.println("Book found at index: " + index);
        }
    }

    private static void displayAllBooks() {
        library.displayAllBooks();
    }
}


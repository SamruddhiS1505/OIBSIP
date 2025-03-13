import java.io.*;
import java.util.*;

class Book implements Serializable {
    private String id, title, author;
    private boolean isIssued;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return isIssued; }

    public void issueBook() { isIssued = true; }
    public void returnBook() { isIssued = false; }

    @Override
    public String toString() {
        return "ID: " + id + ", Title: " + title + ", Author: " + author + ", Issued: " + isIssued;
    }
}

class Library {
    private ArrayList<Book> books = new ArrayList<>();
    private final String FILE_NAME = "library_data.ser";

    public Library() { loadData(); }

    public void addBook(String id, String title, String author) {
        books.add(new Book(id, title, author));
        System.out.println("Book added successfully!");
        saveData();
    }

    public void removeBook(String id) {
        books.removeIf(book -> book.getId().equals(id));
        System.out.println("Book removed successfully!");
        saveData();
    }

    public void issueBook(String id) {
        for (Book book : books) {
            if (book.getId().equals(id) && !book.isIssued()) {
                book.issueBook();
                System.out.println("Book issued successfully!");
                saveData();
                return;
            }
        }
        System.out.println("Book not available!");
    }

    public void returnBook(String id) {
        for (Book book : books) {
            if (book.getId().equals(id) && book.isIssued()) {
                book.returnBook();
                System.out.println("Book returned successfully!");
                saveData();
                return;
            }
        }
        System.out.println("Book not found!");
    }

    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(books);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            books = (ArrayList<Book>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        }
    }
}

public class DigitalLibrary {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nDigital Library Management System:");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. List Books");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    library.addBook(id, title, author);
                    break;
                case 2:
                    System.out.print("Enter Book ID to remove: ");
                    id = scanner.nextLine();
                    library.removeBook(id);
                    break;
                case 3:
                    System.out.print("Enter Book ID to issue: ");
                    id = scanner.nextLine();
                    library.issueBook(id);
                    break;
                case 4:
                    System.out.print("Enter Book ID to return: ");
                    id = scanner.nextLine();
                    library.returnBook(id);
                    break;
                case 5:
                    library.listBooks();
                    break;
                case 6:
                    System.out.println("Exiting... Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
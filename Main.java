import java.util.*;

// Book Class
class Book {
    int id;
    String title;
    boolean issued;

    Book(int id, String title) {
        this.id = id;
        this.title = title;
        this.issued = false;
    }

    public String toString() {
        return id + " - " + title + " | Issued: " + issued;
    }
}

// BST Node
class BSTNode {
    Book book;
    BSTNode left, right;

    BSTNode(Book book) {
        this.book = book;
    }
}

// BST Class
class BookBST {
    BSTNode root;

    BSTNode insert(BSTNode root, Book book) {
        if (root == null)
            return new BSTNode(book);

        if (book.id < root.book.id)
            root.left = insert(root.left, book);
        else
            root.right = insert(root.right, book);

        return root;
    }

    void insert(Book book) {
        root = insert(root, book);
    }

    Book search(BSTNode root, int id) {
        if (root == null)
            return null;

        if (root.book.id == id)
            return root.book;

        if (id < root.book.id)
            return search(root.left, id);

        return search(root.right, id);
    }
}

// Main Class
public class Main {

    // Data Structures
    static LinkedList<Book> books = new LinkedList<>();
    static ArrayList<String> students = new ArrayList<>();
    static Queue<String> waitingQueue = new LinkedList<>();
    static Stack<String> returnHistory = new Stack<>();
    static BookBST bst = new BookBST();

    static Scanner sc = new Scanner(System.in);

    // Add Book
    static void addBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();

        Book book = new Book(id, title);

        books.add(book);
        bst.insert(book);

        System.out.println("Book Added Successfully!");
    }

    // Display Books
    static void displayBooks() {
        System.out.println("\n===== BOOK LIST =====");

        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        for (Book b : books) {
            System.out.println(b);
        }
    }

    // Issue Book
    static void issueBook() {
        System.out.print("Enter Book ID to Issue: ");
        int id = sc.nextInt();

        Book book = bst.search(bst.root, id);

        if (book != null) {
            if (!book.issued) {
                book.issued = true;
                System.out.println("Book Issued Successfully!");
            } else {
                System.out.println("Book already issued.");

                sc.nextLine();
                System.out.print("Enter Student Name for Waiting Queue: ");
                String name = sc.nextLine();

                waitingQueue.add(name);

                System.out.println("Added to waiting queue.");
            }
        } else {
            System.out.println("Book not found.");
        }
    }

    // Return Book
    static void returnBook() {
        System.out.print("Enter Book ID to Return: ");
        int id = sc.nextInt();

        Book book = bst.search(bst.root, id);

        if (book != null && book.issued) {
            book.issued = false;

            returnHistory.push(book.title);

            System.out.println("Book Returned Successfully!");

            // Queue Handling
            if (!waitingQueue.isEmpty()) {
                String student = waitingQueue.poll();

                System.out.println(student +
                        " from waiting queue can now issue this book.");
            }

        } else {
            System.out.println("Invalid Book ID or Book not issued.");
        }
    }

    // Recursive Search
    static void recursiveSearch(int index, String title) {

        if (index >= books.size()) {
            System.out.println("Book not found.");
            return;
        }

        if (books.get(index).title.equalsIgnoreCase(title)) {
            System.out.println("Book Found: " + books.get(index));
            return;
        }

        recursiveSearch(index + 1, title);
    }

    // Search Book
    static void searchBook() {
        sc.nextLine();

        System.out.print("Enter Book Title to Search: ");
        String title = sc.nextLine();

        recursiveSearch(0, title);
    }

    // View Return History
    static void viewReturnHistory() {
        System.out.println("\n===== RETURN HISTORY =====");

        if (returnHistory.isEmpty()) {
            System.out.println("No returned books.");
        } else {
            System.out.println(returnHistory);
        }
    }

    // Main Method
    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. Display Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book");
            System.out.println("6. View Return History");
            System.out.println("7. Exit");

            System.out.print("Enter Choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addBook();
                    break;

                case 2:
                    displayBooks();
                    break;

                case 3:
                    issueBook();
                    break;

                case 4:
                    returnBook();
                    break;

                case 5:
                    searchBook();
                    break;

                case 6:
                    viewReturnHistory();
                    break;

                case 7:
                    System.out.println("Exiting Program...");
                    break;

                default:
                    System.out.println("Invalid Choice.");
            }

        } while (choice != 7);
    }
}
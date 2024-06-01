package Library;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class LibraryManagementSystem extends Application {
    private ObservableList<Book> books = FXCollections.observableArrayList();
    private ObservableList<Borrower> borrowers = FXCollections.observableArrayList();
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    private ListView<Book> bookListView;
    private ListView<Borrower> borrowerListView;
    private ListView<Transaction> transactionListView;

    private int bookId = 1;
    private int borrowerId = 1;
    private int transactionId = 1;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Book management
        VBox bookBox = new VBox(10);
        bookBox.setPadding(new Insets(10));
        Label bookLabel = new Label("Book Management");
        TextField titleField = new TextField();
        TextField authorField = new TextField();
        Button addBookButton = new Button("Add Book");
        addBookButton.setOnAction(event -> addBook(titleField, authorField));
        bookListView = new ListView<>();
        bookListView.setItems(books);
        bookBox.getChildren().addAll(bookLabel, new Label("Title:"), titleField, new Label("Author:"), authorField, addBookButton, bookListView);

        // Borrower management
        VBox borrowerBox = new VBox(10);
        borrowerBox.setPadding(new Insets(10));
        Label borrowerLabel = new Label("Borrower Management");
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        Button addBorrowerButton = new Button("Add Borrower");
        addBorrowerButton.setOnAction(event -> addBorrower(nameField, emailField));
        borrowerListView = new ListView<>();
        borrowerListView.setItems(borrowers);
        borrowerBox.getChildren().addAll(borrowerLabel, new Label("Name:"), nameField, new Label("Email:"), emailField, addBorrowerButton, borrowerListView);

        // Transaction management
        VBox transactionBox = new VBox(10);
        transactionBox.setPadding(new Insets(10));
        Label transactionLabel = new Label("Transaction Management");
        Button issueButton = new Button("Issue Book");
        issueButton.setOnAction(event -> issueBook());
        Button returnButton = new Button("Return Book");
        returnButton.setOnAction(event -> returnBook());
        transactionListView = new ListView<>();
        transactionListView.setItems(transactions);
        transactionBox.getChildren().addAll(transactionLabel, issueButton, returnButton, transactionListView);

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(bookBox, borrowerBox, transactionBox);
        root.setCenter(hbox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Library Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addBook(TextField titleField, TextField authorField) {
        String title = titleField.getText();
        String author = authorField.getText();
        if (!title.isEmpty() &&!author.isEmpty()) {
            Book book = new Book(bookId++, title, author);
            books.add(book);
            titleField.clear();
            authorField.clear();
        }
    }

    private void addBorrower(TextField nameField, TextField emailField) {
        String name = nameField.getText();
        String email = emailField.getText();
        if (!name.isEmpty() && !email.isEmpty()) {
            Borrower borrower = new Borrower(borrowerId++, name, email);
            borrowers.add(borrower);
            nameField.clear();
            emailField.clear();
        }
    }

    private void issueBook() {
        Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
        Borrower selectedBorrower = borrowerListView.getSelectionModel().getSelectedItem();
        if (selectedBook != null && selectedBorrower != null) {
            selectedBook.setIssued(true);
            Transaction transaction = new Transaction(transactionId++, selectedBook, selectedBorrower, LocalDate.now());
            transactions.add(transaction);
        }
    }

    private void returnBook() {
        Transaction selectedTransaction = transactionListView.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            Book selectedBook = selectedTransaction.getBook();
            selectedBook.setIssued(false);
            transactions.remove(selectedTransaction);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package Library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private int id;
    private Book book;
    private Borrower borrower;
    private LocalDate issueDate;

    public Transaction(int id, Book book, Borrower borrower, LocalDate issueDate) {
        this.id = id;
        this.book = book;
        this.borrower = borrower;
        this.issueDate = issueDate;
    }

    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    @Override
    public String toString() {
        return String.format("%s issued to %s on %s", book.getTitle(), borrower.getName(), issueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
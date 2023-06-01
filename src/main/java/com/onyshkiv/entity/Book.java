package com.onyshkiv.entity;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class Book extends Entity{
    private String isbn;
    private String name;
    private Date dateOfPublication;
    private Publication publication;
    private int quantity;
    private String details;
    private Set<Author> authors;
    public Book() {}

    public Book(String isbn, String name, Date dateOfPublication, Publication publication, int quantity, String details, Set<Author> authors) {
        this.isbn = isbn;
        this.name = name;
        this.dateOfPublication = dateOfPublication;
        this.publication = publication;
        this.quantity = quantity;
        this.details = details;
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public Date getDateOfPublication() {
        return dateOfPublication;
    }

    public Publication getPublication() {
        return publication;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDetails() {
        return details;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn=" + isbn +
                ", name='" + name + '\'' +
                ", dateOfPublication=" + dateOfPublication +
                ", publication=" + publication +
                ", quantity=" + quantity +
                ", details='" + details + '\'' +
                ", authors=" + authors +
                '}';
    }
}

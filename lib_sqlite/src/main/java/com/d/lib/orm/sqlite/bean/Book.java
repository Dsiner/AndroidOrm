package com.d.lib.orm.sqlite.bean;

/**
 * Entity mapped to table "BOOK".
 */
public class Book {
    public int type;

    public Long isbn;

    public String name;

    public String author;

    public Long date;

    public Double price;

    public Book() {
    }

    public Book(Long isbn, String name, String author, Long date,
                Double price) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.date = date;
        this.price = price;
    }

    public Long getIsbn() {
        return this.isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getDate() {
        return this.date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

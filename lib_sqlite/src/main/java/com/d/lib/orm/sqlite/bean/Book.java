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
}

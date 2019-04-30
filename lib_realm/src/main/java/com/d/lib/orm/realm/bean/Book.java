package com.d.lib.orm.realm.bean;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Entity mapped to table "BOOK".
 */
public class Book extends RealmObject {
    @Ignore
    public int type;

    @PrimaryKey
    public Long isbn;

    @Required
    public String name;

    public String author;

    public Long date;

    public Double price;

    public Book deepCopy(Book book) {
        this.type = book.type;
        this.isbn = new Long(book.isbn);
        this.name = new String(book.name);
        this.author = new String(book.author);
        this.date = new Long(book.date);
        this.price = new Double(book.price);
        return this;
    }

    public Book() {
    }

    public Book(Long isbn) {
        this.isbn = isbn;
    }

    public Book(Long isbn, String name, String author, Long date, Double price) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.date = date;
        this.price = price;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    /**
     * Not-null value.
     */
    public String getName() {
        return name;
    }

    /**
     * Not-null value; ensure this value is available before it is saved to the database.
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}

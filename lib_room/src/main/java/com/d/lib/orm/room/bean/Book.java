package com.d.lib.orm.room.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.d.lib.common.component.mvp.model.BaseModel;

/**
 * Entity mapped to table "BOOK".
 */
@Entity(tableName = "Book")
public class Book extends BaseModel {
    @Ignore
    public int type;

    @PrimaryKey
    @ColumnInfo(name = "isbn")
    public Long isbn;

    public String name;

    public String author;

    public Long date;

    public Double price;

    public Book() {
    }

    @Ignore
    public Book(Long isbn) {
        this.isbn = isbn;
    }

    @Ignore
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

    public String getName() {
        return name;
    }

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

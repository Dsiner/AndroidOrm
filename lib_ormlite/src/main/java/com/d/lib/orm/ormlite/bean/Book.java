package com.d.lib.orm.ormlite.bean;

import com.d.lib.common.component.mvp.model.BaseModel;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Entity mapped to table "BOOK".
 */
@DatabaseTable(tableName = "Book")
public class Book extends BaseModel {

    public int type;

    @DatabaseField(id = true, canBeNull = false, columnName = "isbn")
    public Long isbn;

    @DatabaseField(canBeNull = false, columnName = "name")
    private String name;

    @DatabaseField
    private String author;

    @DatabaseField
    private Long date;

    @DatabaseField
    private Double price;

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

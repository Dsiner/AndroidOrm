package com.d.lib.orm.greendao.bean;

import com.d.lib.common.component.mvp.model.BaseModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Entity mapped to table "BOOK".
 */
@Entity
public class Book extends BaseModel {
    @Transient
    public int type;

    @Property(nameInDb = "ISDN")
    @Id(autoincrement = true)
    public Long isbn;

    @NotNull
    public String name;

    public String author;

    public Long date;

    public Double price;

    @Generated(hash = 1281994737)
    public Book(Long isbn, @NotNull String name, String author, Long date,
            Double price) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.date = date;
        this.price = price;
    }

    @Generated(hash = 1839243756)
    public Book() {
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

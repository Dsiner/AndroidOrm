package com.d.lib.orm.sqlite.bean;

import com.d.lib.orm.sqlite.annotations.Entity;
import com.d.lib.orm.sqlite.annotations.Property;

/**
 * Entity mapped to table "BOOK".
 */
@Entity(nameInDb = "BookXX")
public class Book {
    public int type;

    @Property(index = 0, id = true, autoincrement = true)
    public Long isbn;

    @Property(index = 1)
    public String name;

    @Property(index = 2)
    public String author;

    @Property(index = 3)
    public Long date;

    @Property(index = 4,nameInDb = "PrXWW")
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

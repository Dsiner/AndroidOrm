package com.d.lib.orm.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.d.lib.orm.room.bean.Book;

import java.util.List;

@Dao
public interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrReplace(Book... users);

    @Delete
    void delete(Book... users);

    @Update
    void update(Book... users);

    @Query("SELECT * FROM Book ORDER BY isbn ASC")
    List<Book> queryAll();

    @Query("SELECT * FROM Book WHERE isbn IN (:isbns)")
    List<Book> queryAllByIsbn(int[] isbns);

    @Query("SELECT * FROM Book WHERE name LIKE :name LIMIT 1")
    Book queryByName(String name);
}

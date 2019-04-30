package com.d.lib.orm.realm.operation;

import com.d.lib.orm.realm.bean.Book;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.log.RealmLog;

public class OpBook extends AbstractOp<Book, Long> {

    public OpBook(Realm realm) {
        super(realm);
    }

    @Override
    public void insert(final Book o) {
        executeTransaction(new Runnable() {
            @Override
            public void run() {
//                Book book = realm.createObject(Book.class); // Create a new object
//                book.deepCopy(o);
//
//                Book book = new Book();
//                book.deepCopy(o);
//                realm.copyToRealm(book);

                realm.insert(o);
            }
        });
    }

    @Override
    public void insertOrReplace(final Book o) {
        executeTransaction(new Runnable() {
            @Override
            public void run() {
                RealmResults<Book> books = query(o);
                if (books == null) {
                    realm.insertOrUpdate(o);
                }
            }
        });
    }

    @Override
    public void insert(final List<Book> list, boolean transaction) {
        if (list == null) {
            return;
        }
        if (!transaction) {
            for (int i = 0; i < list.size(); i++) {
                insert(list.get(i));
            }
            return;
        }
        executeTransaction(new Runnable() {
            @Override
            public void run() {
                realm.insert(list);
            }
        });
    }

    @Override
    public void insertOrReplace(final List<Book> list, boolean transaction) {
        if (list == null) {
            return;
        }
        if (!transaction) {
            for (int i = 0; i < list.size(); i++) {
                insertOrReplace(list.get(i));
            }
            return;
        }
        executeTransaction(new Runnable() {
            @Override
            public void run() {
                realm.insertOrUpdate(list);
            }
        });
    }

    @Override
    public void deleteAll() {
        executeTransaction(new Runnable() {
            @Override
            public void run() {
                realm.delete(Book.class);
            }
        });
    }

    @Override
    public void deleteById(final Long id) {
        executeTransaction(new Runnable() {
            @Override
            public void run() {
                RealmResults<Book> books = realm.where(Book.class).equalTo("isbn", id).findAll();
                if (books != null && books.size() > 0) {
                    books.deleteFromRealm(0);
                }
            }
        });
    }

    @Override
    public void delete(final Book o) {
        executeTransaction(new Runnable() {
            @Override
            public void run() {
                RealmResults<Book> books = query(o);
                if (books != null && books.size() > 0) {
                    books.deleteFromRealm(0);
                }
            }
        });
    }

    @Override
    public void update(final Book o) {
        executeTransaction(new Runnable() {
            @Override
            public void run() {
//                Book book = realm.where(Book.class).equalTo("isbn", o.isbn).findFirst();
//                if (book != null) {
//                    book.deepCopy(o);
                realm.insertOrUpdate(o);
//                }
            }
        });
    }

    @Override
    public List<Book> queryAll() {
        RealmResults<Book> books = realm.where(Book.class).findAll();
        return books;
    }

    private RealmResults<Book> query(Book o) {
        return realm.where(Book.class).equalTo("isbn", o.isbn).findAll();
    }

    public long queryMaxIsbn() {
        Number number = realm.where(Book.class).max("isbn");
        return number != null ? number.longValue() : 0;
    }

    public void executeTransaction(Runnable runnable) {
        boolean commitNow = false;
        if (!realm.isInTransaction()) {
            realm.beginTransaction();
            commitNow = true;
        }
        try {
            runnable.run();
            if (commitNow) {
                realm.commitTransaction();
            }
        } catch (Throwable e) {
            if (realm.isInTransaction()) {
                realm.cancelTransaction();
            } else {
                RealmLog.warn("Could not cancel transaction, not currently in a transaction.");
            }
            throw e;
        }
    }
}

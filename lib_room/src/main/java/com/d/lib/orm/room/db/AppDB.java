package com.d.lib.orm.room.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.d.lib.orm.room.bean.Book;
import com.d.lib.orm.room.dao.BookDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * AppDB
 * Created by D on 2017/7/25.
 */
@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    private static AppDB instance;
    private SupportSQLiteDatabase db;
    private Gson gson;

    private static AppDB init(Context context) {
        AppDB appDB = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "room.db")
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .allowMainThreadQueries()
                .build();
        appDB.db = appDB.getOpenHelper().getWritableDatabase();
        appDB.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return appDB;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
                    + "`name` TEXT, PRIMARY KEY(`id`))");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Book " + " ADD COLUMN pub_year INTEGER");
        }
    };

    public static AppDB getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDB.class) {
                if (instance == null) {
                    instance = init(context);
                }
            }
        }
        return instance;
    }

    public abstract BookDao optBook();
}

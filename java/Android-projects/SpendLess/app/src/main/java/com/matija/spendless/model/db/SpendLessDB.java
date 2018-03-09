package com.matija.spendless.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.matija.spendless.com.matija.spendless.model.dao.CategoryDao;
import com.matija.spendless.com.matija.spendless.model.dao.TransactionDao;
import com.matija.spendless.model.Category;
import com.matija.spendless.model.converters.DateConverter;

/**
 * Created by matija on 9.3.18..
 */

@Database(entities = {Transaction.class, Category.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class SpendLessDB extends RoomDatabase {

    private static final String DB_NAME = "spendLessDB.db";
    private static SpendLessDB instance;

    public static synchronized SpendLessDB getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static SpendLessDB create(final Context context) {
        return Room.databaseBuilder(
                context,
                SpendLessDB.class,
                DB_NAME
            ).build();
    }

    public abstract TransactionDao getTransactionDAO();
    public abstract CategoryDao getCategoryDAO();
}

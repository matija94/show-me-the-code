package com.matija.spendless.model.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.matija.spendless.R;
import com.matija.spendless.model.Category;
import com.matija.spendless.model.Transaction;
import com.matija.spendless.model.converters.DateConverter;
import com.matija.spendless.model.dao.CategoryDao;
import com.matija.spendless.model.dao.TransactionDao;


import java.util.concurrent.Executors;

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
            ).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        getInstance(context).getCategoryDAO().insert(defaultCategories());
                    }
                });
            }
        })

                .build();
    }

    public abstract TransactionDao getTransactionDAO();
    public abstract CategoryDao getCategoryDAO();


    private static Category[] defaultCategories() {
        Category[] categories = new Category[6];

        //TODO add categories ,defined in strings.xml
        categories[0] = new Category("Food", R.drawable.food);
        categories[1] = new Category("Sports", R.drawable.sports);
        categories[2] = new Category("Bills", R.drawable.bills);
        categories[3] = new Category("Party", R.drawable.party);
        categories[4] = new Category("Shopping", R.drawable.shopping);
        categories[5] = new Category("Coffee shop", R.drawable.coffee_shop);
        return categories;
    }
}

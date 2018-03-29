package com.matija.spendless.model.dao;

import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListProvider;
import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import com.matija.spendless.model.Transaction;

import java.util.List;

/**
 * Created by matija on 9.3.18..
 */

@Dao
public interface TransactionDao {

    @Insert
    public void insert(Transaction... transactions);

    @Update
    public void update(Transaction... transactions);

    @Delete
    public void delete(Transaction... transactions);

    @Query("SELECT * FROM `transaction` WHERE category_id=:categoryId")
    public List<Transaction> findAllTransactionsForCategory(final int categoryId);

    @Query("SELECT * FROM `transaction`")
    public DataSource.Factory<Integer, Transaction> getAllTransactions();

    @RawQuery(observedEntities = Transaction.class)
    DataSource.Factory<Integer, Transaction> getTransactionsCustomQuery(SupportSQLiteQuery query);
}

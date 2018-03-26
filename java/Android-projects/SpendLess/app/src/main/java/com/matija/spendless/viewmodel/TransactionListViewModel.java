package com.matija.spendless.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.matija.spendless.model.Transaction;
import com.matija.spendless.model.db.SpendLessDB;

import java.util.List;

/**
 * Created by matija on 25.3.18..
 */

public class TransactionListViewModel extends AndroidViewModel {


    private MediatorLiveData<PagedList<Transaction>> transactionsLiveData;

    public TransactionListViewModel(@NonNull Application application) {
        super(application);
        transactionsLiveData = new MediatorLiveData<>();
        transactionsLiveData.setValue(null);

        LiveData<PagedList<Transaction>> allTransactions = SpendLessDB.getInstance(application).getTransactionDAO().getAllTransactions()
                .create(0, new PagedList.Config.Builder()
                                        .setPageSize(20)
                                        .setPrefetchDistance(5)
                                        .setEnablePlaceholders(true).build());

        transactionsLiveData.addSource(allTransactions, transactionsLiveData::setValue);
    }

    public LiveData<PagedList<Transaction>> getTransactions() {
        return transactionsLiveData;
    }
}

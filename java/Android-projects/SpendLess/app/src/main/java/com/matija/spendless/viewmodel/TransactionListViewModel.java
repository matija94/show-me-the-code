package com.matija.spendless.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
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

    private LiveData<PagedList<Transaction>> lastSource = null;

    public TransactionListViewModel(@NonNull Application application) {
        super(application);
        transactionsLiveData = new MediatorLiveData<>();
        transactionsLiveData.setValue(null);

        if (lastSource == null) {
            LiveData<PagedList<Transaction>> allTransactions = new LivePagedListBuilder<>(
                    SpendLessDB.getInstance(application).getTransactionDAO().getAllTransactions(),
                    new PagedList.Config.Builder()
                            .setPageSize(20)
                            .setPrefetchDistance(5)
                            .setEnablePlaceholders(true).build()).build();
            lastSource = allTransactions;
            transactionsLiveData.addSource(allTransactions, transactionsLiveData::setValue);
        }
    }

    public LiveData<PagedList<Transaction>> getTransactions() {
        return transactionsLiveData;
    }

    public void replaceDataSourceWith(LiveData<PagedList<Transaction>> source) {
        transactionsLiveData.removeSource(lastSource);
        lastSource = source;
        transactionsLiveData.addSource(source, transactionsLiveData::setValue);
    }

}

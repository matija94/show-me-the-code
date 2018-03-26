package com.matija.spendless.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.matija.spendless.model.Transaction;
import com.matija.spendless.model.db.SpendLessDB;

import java.util.List;

/**
 * Created by matija on 25.3.18..
 */

public class TransactionListViewModel extends AndroidViewModel {


    private MediatorLiveData<List<Transaction>> transactionsLiveData;

    public TransactionListViewModel(@NonNull Application application) {
        super(application);
        transactionsLiveData = new MediatorLiveData<>();
        transactionsLiveData.setValue(null);

        // TODO: load chunck by chunck
        LiveData<List<Transaction>> allTransactions = SpendLessDB.getInstance(application).getTransactionDAO().getAllTransactions();

        transactionsLiveData.addSource(allTransactions, transactionsLiveData::setValue);
    }

    public LiveData<List<Transaction>> getTransactions() {
        return transactionsLiveData;
    }
}

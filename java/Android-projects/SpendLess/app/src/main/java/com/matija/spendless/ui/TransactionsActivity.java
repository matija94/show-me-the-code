package com.matija.spendless.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.matija.spendless.R;
import com.matija.spendless.model.Transaction;
import com.matija.spendless.ui.adapters.CategoryAdapter;
import com.matija.spendless.ui.adapters.TransactionAdapter;
import com.matija.spendless.ui.views.*;
import com.matija.spendless.viewmodel.TransactionListViewModel;

import java.util.List;

/**
 * Created by matija on 23.3.18..
 */

public class TransactionsActivity extends AppCompatActivity {

    private TextView loadingText;
    private EditTextDatePicker fromDate;
    private EditTextDatePicker toDate;
    private EditText fromValue;
    private EditText toValue;
    private EditTextCategoryPicker categoryPicker;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    private TransactionAdapter transactionAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transactions_dashboard);

        loadingText = (TextView) findViewById(R.id.transactions_dashboard_loading_transactions_text_view);
        fromDate = (EditTextDatePicker) findViewById(R.id.transactions_dashboard_transaction_date_from_edit_text_date_picker);
        toDate = (EditTextDatePicker) findViewById(R.id.transactions_dashboard_transaction_date_to_edit_text_date_picker);
        fromValue = (EditText) findViewById(R.id.transactions_dashboard_transaction_value_from_edit_text);
        toValue = (EditText) findViewById(R.id.transactions_dashboard_transaction_value_to_edit_text);
        categoryPicker = findViewById(R.id.transactions_dashboard_category_dialog_edit_text);

        layoutManager = new LinearLayoutManager(this);
        transactionAdapter = new TransactionAdapter(this);
        recyclerView = findViewById(R.id.transactions_dashboard_transactions_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(transactionAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadingText.setVisibility(View.GONE);

        TransactionListViewModel transactionListViewModel = ViewModelProviders.of(this).get(TransactionListViewModel.class);
        transactionListViewModel.getTransactions().observe(this, transactions -> {
            if (transactions == null) {
                loadingText.setVisibility(View.VISIBLE);
            }
            else {
                loadingText.setVisibility(View.GONE);
                transactionAdapter.setTransactionList(transactions);
            }
        });
    }
}

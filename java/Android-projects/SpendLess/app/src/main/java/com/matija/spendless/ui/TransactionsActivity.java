package com.matija.spendless.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.matija.spendless.R;
import com.matija.spendless.model.Category;
import com.matija.spendless.model.Transaction;
import com.matija.spendless.model.db.SpendLessDB;
import com.matija.spendless.ui.adapters.TransactionAdapter;
import com.matija.spendless.ui.views.EditTextCategoryPicker;
import com.matija.spendless.ui.views.EditTextDatePicker;
import com.matija.spendless.utils.ApplicationExecutors;
import com.matija.spendless.utils.DateParser;
import com.matija.spendless.viewmodel.TransactionListViewModel;

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
    private Button querySubmitButton;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    private TransactionAdapter transactionAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        loadingText = (TextView) findViewById(R.id.transactions_dashboard_loading_transactions_text_view);
        fromDate = (EditTextDatePicker) findViewById(R.id.transactions_dashboard_transaction_date_from_edit_text_date_picker);
        toDate = (EditTextDatePicker) findViewById(R.id.transactions_dashboard_transaction_date_to_edit_text_date_picker);
        fromValue = (EditText) findViewById(R.id.transactions_dashboard_transaction_value_from_edit_text);
        toValue = (EditText) findViewById(R.id.transactions_dashboard_transaction_value_to_edit_text);
        categoryPicker = findViewById(R.id.transactions_dashboard_category_dialog_edit_text);
        querySubmitButton = (Button) findViewById(R.id.transactions_dashboard_query_submit_button);
        querySubmitButton.setOnClickListener(v-> this.sendQuery());

        layoutManager = new LinearLayoutManager(this);
        transactionAdapter = new TransactionAdapter();
        recyclerView = findViewById(R.id.transactions_dashboard_transactions_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(transactionAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadingText.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        updateTransactions(null);

    }

    private void updateTransactions(LiveData<PagedList<Transaction>> queriedTransactions) {
        TransactionListViewModel transactionListViewModel = ViewModelProviders.of(this).get(TransactionListViewModel.class);
        if (queriedTransactions!=null) {
            transactionListViewModel.replaceDataSourceWith(queriedTransactions);
        }
        else {
            transactionListViewModel.getTransactions().observe(this, transactions -> {
                    loadingText.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    transactionAdapter.setList(transactions);
            });
        }
    }

    private void sendQuery() {
        loadingText.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        new ApplicationExecutors().getIoThread().execute(() -> {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT * FROM `transaction` where ");

            String fromDate = DateParser.backendParser().format(this.fromDate.getDate());
            String toDate = DateParser.backendParser().format(this.toDate.getDate());

            queryBuilder.append(String.format("date_time BETWEEN '%s' AND '%s' ", fromDate, toDate));
            queryBuilder.append("AND ");

            int fromValue = Integer.parseInt(this.fromValue.getText().toString());
            int toValue = Integer.parseInt(this.toValue.getText().toString());
            queryBuilder.append(String.format("value BETWEEN %d AND %d ", fromValue, toValue));

            if (categoryPicker.getText() != null) {
                String categoryName = categoryPicker.getText().toString();
                Category category = SpendLessDB.getInstance(this).getCategoryDAO().findCategoryByName(categoryName);
                queryBuilder.append("AND ");
                queryBuilder.append(String.format("category_id = %d", category.getId()));
            }

            DataSource.Factory<Integer, Transaction> transactionsCustomQueryRes =
                    SpendLessDB.getInstance(this).getTransactionDAO().getTransactionsCustomQuery(new SimpleSQLiteQuery(queryBuilder.toString(),
                                                                                                                        new Object[0]));

            LiveData<PagedList<Transaction>> transactions = new LivePagedListBuilder(transactionsCustomQueryRes,
                    new PagedList.Config.Builder()
                            .setPageSize(20)
                            .setPrefetchDistance(5)
                            .setEnablePlaceholders(true).build()).build();

            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(() -> {
                updateTransactions(transactions);
            });

        });

    }


}

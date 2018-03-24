package com.matija.spendless;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.matija.spendless.R;
import com.matija.spendless.dialogs.CategoryPickerDialog;
import com.matija.spendless.model.db.SpendLessDB;
import com.matija.spendless.views.EditTextCategoryPicker;
import com.matija.spendless.views.EditTextDatePicker;

import java.util.stream.Collectors;

/**
 * Created by matija on 23.3.18..
 */

public class TransactionsActivity extends AppCompatActivity {

    private EditTextDatePicker fromDate;
    private EditTextDatePicker toDate;
    private EditText fromValue;
    private EditText toValue;
    private EditTextCategoryPicker categoryPicker;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transactions_dashboard);


        fromDate = (EditTextDatePicker) findViewById(R.id.transactions_dashboard_transaction_date_from_edit_text_date_picker);
        toDate = (EditTextDatePicker) findViewById(R.id.transactions_dashboard_transaction_date_to_edit_text_date_picker);
        fromValue = (EditText) findViewById(R.id.transactions_dashboard_transaction_value_from_edit_text);
        toValue = (EditText) findViewById(R.id.transactions_dashboard_transaction_value_to_edit_text);
        categoryPicker = findViewById(R.id.transactions_dashboard_category_dialog_edit_text);
    }
}

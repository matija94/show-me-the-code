package com.matija.spendless.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.EditText;

import com.matija.spendless.R;
import com.matija.spendless.model.Category;
import com.matija.spendless.model.Transaction;
import com.matija.spendless.model.db.SpendLessDB;
import com.matija.spendless.ui.views.EditTextCategoryPicker;
import com.matija.spendless.ui.views.EditTextDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * Created by matija on 15.3.18..
 */

public class NewTransactionDialogFragment extends DialogFragment {

    private EditText value;
    private EditText description;
    private EditTextDatePicker date;
    private EditTextCategoryPicker category;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_transaction_dialog, null);

        initViewComponents(view);

        builder
            .setView(view)
            .setPositiveButton(R.string.submit, (dialogInterface, i) -> {
                try {
                    createTransaction();
                    Snackbar.make(view.findViewById(R.id.addTransactionCoordinatorLayout),
                            R.string.transaction_insert, Snackbar.LENGTH_LONG)
                            .show();
                }
                catch (ParseException e) {
                }
            });

        return builder.create();
    }

    private void initViewComponents(View v) {
        this.value = v.findViewById(R.id.value);
        this.description = v.findViewById(R.id.description);
        this.date = v.findViewById(R.id.dateEdit);
        this.category = v.findViewById(R.id.categoryButton);
    }

    private void createTransaction() throws ParseException {
        Executors.newSingleThreadExecutor().execute(() -> {
            float value = Float.parseFloat(this.value.getText().toString());

            String description = this.description.getText().toString();

            Date date = this.date.getDate();

            String categoryStr = category.getText().toString();
            Category category = SpendLessDB.getInstance(getContext()).getCategoryDAO().findCategoryByName(categoryStr);

            Transaction transaction = new Transaction(null, value, date, Integer.parseInt(Long.toString(category.getId())), description);
            SpendLessDB.getInstance(getContext()).getTransactionDAO().insert(transaction);
        });

    }

}

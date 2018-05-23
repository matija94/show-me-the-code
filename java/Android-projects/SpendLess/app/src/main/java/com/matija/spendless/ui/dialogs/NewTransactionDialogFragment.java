package com.matija.spendless.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.matija.spendless.R;
import com.matija.spendless.model.Category;
import com.matija.spendless.model.Transaction;
import com.matija.spendless.model.db.SpendLessDB;
import com.matija.spendless.preferences.SpendLessPreferences;
import com.matija.spendless.ui.views.EditTextCategoryPicker;
import com.matija.spendless.ui.views.EditTextDatePicker;

import java.util.Date;
import java.util.concurrent.Executors;


/**
 * Created by matija on 15.3.18..
 */

public class NewTransactionDialogFragment extends DialogFragment {

    private EditText value;
    private EditText description;
    private EditTextDatePicker date;
    private EditTextCategoryPicker category;

    private boolean valid[] = {false};

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
            .setOnDismissListener(this)
            .setOnCancelListener(this)
            .setPositiveButton(R.string.submit, (dialogInterface, i) -> {
                Float value=null;
                String description=null, categoryStr=null;
                Date date=null;

                try {
                    value = Float.parseFloat(this.value.getText().toString());
                    description = this.description.getText().toString();
                    date = this.date.getDate();
                    categoryStr = category.getText().toString();
                    valid[0] = true;
                }
                catch (Exception e) {
                    Toast.makeText(getActivity(), R.string.transaction_insert_fail, Toast.LENGTH_SHORT).show();
                }
                if (valid[0]) {
                    createTransaction(value, description, date ,categoryStr);

                    updateRemainingSpendings(value);
                    SpendLessPreferences.setLastTransactionTimestamp(getActivity(), new Date().getTime());
                    Toast.makeText(getActivity(), R.string.transaction_insert, Toast.LENGTH_SHORT).show();
                }
            });

        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        valid[0] = true;
        dialog.dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (!valid[0]) {
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().remove(this).commit();
            show(fm, "transactionDialogFragment");
        }
    }

    private void initViewComponents(View v) {
        this.value = v.findViewById(R.id.value);
        this.description = v.findViewById(R.id.description);
        this.date = v.findViewById(R.id.dateEdit);
        this.category = v.findViewById(R.id.categoryButton);
    }

    private void createTransaction(float value, String description, Date date, String categoryStr) {
        Executors.newSingleThreadExecutor().execute(() -> {

            Category category = SpendLessDB.getInstance(getContext()).getCategoryDAO().findCategoryByName(categoryStr);

            Transaction transaction = new Transaction(null, value, date, Integer.parseInt(Long.toString(category.getId())), description);
            SpendLessDB.getInstance(getContext()).getTransactionDAO().insert(transaction);
        });
    }

    private void updateRemainingSpendings(float value) {
        int remainingSpendings = SpendLessPreferences.getRemainingDailySpendings(getActivity()) - (int) value;
        SpendLessPreferences.setRemainingDailySpending(getActivity(), remainingSpendings);
    }

}

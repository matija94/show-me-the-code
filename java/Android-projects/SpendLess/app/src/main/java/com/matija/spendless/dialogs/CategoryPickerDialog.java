package com.matija.spendless.dialogs;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.matija.spendless.R;
import com.matija.spendless.model.db.SpendLessDB;

import java.util.stream.Collectors;

/**
 * Created by matija on 18.3.18..
 */

public class CategoryPickerDialog extends DialogFragment {

    private CharSequence categories[];

    public interface CategoryDialogPickerCallback {

        public void pickedCategory(CharSequence category);
    }

    private CategoryDialogPickerCallback categoryDialogPickerCallback;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pick_category)
                .setItems(categories, (dialog, i) -> {
                   categoryDialogPickerCallback.pickedCategory(categories[i]);
                });
        return builder.create();
    }

    public void setCategories(CharSequence categories[]) {
        this.categories = categories;
    }

    public void setCategoryDialogPickerCallback(CategoryDialogPickerCallback categoryDialogPickerCallback) {
        this.categoryDialogPickerCallback = categoryDialogPickerCallback;
    }

}

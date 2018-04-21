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
import com.matija.spendless.model.db.SpendLessDB;
import com.matija.spendless.ui.CategoriesActivity;
import com.matija.spendless.utils.ApplicationExecutors;


/**
 * Created by matija on 21.4.18..
 */

public class NewCategoryDialogFragment extends DialogFragment {

    private EditText categoryText;
    private Category categoryExisting = null;
    private OnCategoryUpdateListener categoryUpdateImpl;
    private OnCategoryCreateListener categoryCreateImpl;


    public interface OnCategoryUpdateListener {

        public void onCategoryUpdated(Category category);
    }

    public interface OnCategoryCreateListener {

        public void onCategoryCreated(Category category);
    }


    public NewCategoryDialogFragment() {
        super();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_category_dialog, null);


        categoryText = view.findViewById(R.id.add_category_dialog_category_edit_text);
        categoryCreateImpl = (CategoriesActivity) getActivity();
        categoryUpdateImpl = (CategoriesActivity) getActivity();

        if (categoryExisting != null) {
            categoryText.setText(categoryExisting.getName());
        }
        builder
            .setView(view)
            .setPositiveButton(R.string.submit, (dialogInterface, i) -> {
                if (categoryExisting == null) {
                    categoryCreateImpl.onCategoryCreated(createCategory());
                }
                else {
                    updateCategory();
                    categoryUpdateImpl.onCategoryUpdated(categoryExisting);
                }
                Snackbar
                    .make(view.findViewById(R.id.add_category_dialog_constraint_layout),R.string.category_created, Snackbar.LENGTH_SHORT)
                    .show();
            });


        return builder.create();
    }

    private void updateCategory() {
        String categoryName = categoryText.getText().toString();
        categoryExisting.setName(categoryName);
        ApplicationExecutors.getInstance().getIoThread().execute(() -> {
            SpendLessDB.getInstance(getActivity()).getCategoryDAO().update(categoryExisting);
        });
    }

    public void setCategoryExisting(Category categoryExisting) {
        this.categoryExisting = categoryExisting;
    }

    private Category createCategory() {
        String categoryName = categoryText.getText().toString();
        Category category = new Category(categoryName, null);
        ApplicationExecutors.getInstance().getIoThread().execute(() -> {
            SpendLessDB.getInstance(getActivity()).getCategoryDAO().insert(category);
        });
        return category;
    }


}

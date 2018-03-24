package com.matija.spendless.views;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;

import com.matija.spendless.dialogs.CategoryPickerDialog;
import com.matija.spendless.model.Category;
import com.matija.spendless.model.db.SpendLessDB;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by matija on 23.3.18..
 */

public class EditTextCategoryPicker extends AppCompatEditText implements View.OnClickListener, CategoryPickerDialog.CategoryDialogPickerCallback {

    private CategoryPickerDialog categoryPickerDialog;

    public  EditTextCategoryPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        categoryPickerDialog = new CategoryPickerDialog();
        categoryPickerDialog.setCategoryDialogPickerCallback(this);
        setInputType(InputType.TYPE_NULL);
        setOnClickListener(this);
        new CategoryFetcher().execute(context);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = ((AppCompatActivity) getContext()).getSupportFragmentManager();
        categoryPickerDialog.show(fm, "CategoryPickerDialog");
    }

    @Override
    public void pickedCategory(CharSequence category) {
        setText(category);
    }

    private class CategoryFetcher extends AsyncTask<Context, Void, List<Category>> {

        @Override
        protected List<Category> doInBackground(Context... contexts) {
            List<Category> allCategories = SpendLessDB.getInstance(contexts[0]).getCategoryDAO().findAllCategories();
            return allCategories;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(List<Category> categories) {
            categoryPickerDialog.setCategories(
                    categories
                            .stream()
                            .map(cat -> cat.getName())
                            .collect(Collectors.toList())
                            .toArray(new CharSequence[categories.size()])
            );
        }
    }
}
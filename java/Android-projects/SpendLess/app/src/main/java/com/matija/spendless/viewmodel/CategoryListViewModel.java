package com.matija.spendless.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.matija.spendless.model.Category;
import com.matija.spendless.model.db.SpendLessDB;

import java.util.List;

/**
 * Created by matija on 10.3.18..
 */

public class CategoryListViewModel extends AndroidViewModel {

    private SpendLessDB db;

    private final LiveData<List<Category>> categoryList;

    public CategoryListViewModel(Application application) {
        super(application);

        db = SpendLessDB.getInstance(application);

        categoryList = db.getCategoryDAO().getAllCategories();
    }

    public LiveData<List<Category>> getCategoryList() {
        return categoryList;
    }
}

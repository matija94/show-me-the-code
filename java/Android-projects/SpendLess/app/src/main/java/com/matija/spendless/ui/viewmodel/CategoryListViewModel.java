package com.matija.spendless.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;

import com.matija.spendless.model.Category;
import com.matija.spendless.model.db.SpendLessDB;
import com.matija.spendless.utils.ApplicationExecutors;

import java.util.List;

/**
 * Created by matija on 10.3.18..
 */

public class CategoryListViewModel extends AndroidViewModel {

    private SpendLessDB db;

    private final MutableLiveData<List<Category>> categoryList;

    public CategoryListViewModel(Application application) {
        super(application);

        db = SpendLessDB.getInstance(application);

        categoryList = new MutableLiveData<>();
        ApplicationExecutors.getInstance().getIoThread().execute(() -> {
            categoryList.postValue(db.getCategoryDAO().findAllCategories());
        });
    }

    public MutableLiveData<List<Category>> getCategoryList() {
        return categoryList;
    }


}

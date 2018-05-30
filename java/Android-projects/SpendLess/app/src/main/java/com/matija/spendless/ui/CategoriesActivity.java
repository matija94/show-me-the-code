package com.matija.spendless.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.matija.spendless.R;
import com.matija.spendless.model.db.SpendLessDB;
import com.matija.spendless.ui.adapters.CategoryAdapter;
import com.matija.spendless.model.Category;

import com.matija.spendless.ui.dialogs.NewCategoryDialogFragment;
import com.matija.spendless.utils.ApplicationExecutors;
import com.matija.spendless.ui.viewmodel.CategoryListViewModel;

import java.util.ArrayList;

import java.util.List;

/**
 * Created by matija on 28.2.18..
 */
public class CategoriesActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickListener, NewCategoryDialogFragment.OnCategoryCreateListener, NewCategoryDialogFragment.OnCategoryUpdateListener {

    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static Category newCategoryLabel;
    static {
        newCategoryLabel = new Category("Add new category", R.drawable.ic_add_circle_black_24dp);
        newCategoryLabel.setId(-1l);
    }


    public static final String NEW_CATEGORY = "Add new category";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        mLayoutManager = new GridLayoutManager(this,3);

        mRecyclerView = findViewById(R.id.categoryRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CategoryAdapter(new ArrayList<Category>(), this);

        mRecyclerView.setAdapter(mAdapter);

        updateCategories(null);

    }

    @Override
    public void onClick(Category category) {
        if (category.getName().equals(NEW_CATEGORY)) {
            Log.d("CategoriesActivity", "Create new category");
            NewCategoryDialogFragment categoryDialogFragment = new NewCategoryDialogFragment();
            categoryDialogFragment.show(getSupportFragmentManager(), "NewCategoryDialogFragment");
        }
    }

    @Override
    public void onCategoryMenuItemClick(Category category, MenuItem item) {
        Log.d("CategoriesActivity", String.format("%s item has been clicked for %s category", item.toString(), category.getName()));
        switch (item.getItemId()) {
            case R.id.action_update_menu_categories:
                NewCategoryDialogFragment categoryDialogFragment = new NewCategoryDialogFragment();
                Category clone = new Category(category.getName(), category.getDrawableId());
                clone.setId(category.getId());
                categoryDialogFragment.setCategoryExisting(clone);
                categoryDialogFragment.show(getSupportFragmentManager(), "NewCategoryDialogFragment");
                break;
            case R.id.action_remove_menu_categories:
                ApplicationExecutors.getInstance().getIoThread().execute(() -> {
                    SpendLessDB.getInstance(this).getCategoryDAO().delete(category);
                });
                List<Category> newCategories = new ArrayList<>(mAdapter.getCategories());
                newCategories.remove(category);
                updateCategories(newCategories);
                break;
        }
    }


    @Override
    public void onCategoryUpdated(Category category) {
        List<Category> newCategories = new ArrayList<>(mAdapter.getCategories());
        for (Category currentCat : newCategories) {
            if (currentCat.getId().equals(category.getId())) {
                currentCat.setName(category.getName());
            }
        }
        updateCategories(newCategories);
    }

    @Override
    public void onCategoryCreated(Category category) {
        List<Category> newCategories = new ArrayList<>(mAdapter.getCategories());
        newCategories.set(newCategories.size()-1, category);
        updateCategories(newCategories);
    }


    private void updateCategories(List<Category> categories) {
        CategoryListViewModel categoryListViewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);
        if (categories != null) {
            categoryListViewModel.getCategoryList().setValue(categories);
        }
        else {
            categoryListViewModel.getCategoryList().observe(this, new Observer<List<Category>>() {
                @Override
                public void onChanged(@Nullable List<Category> categories) {
                    // append 'add new category' to the end of the list
                    if(!categories.contains(newCategoryLabel)) {
                        categories.add(newCategoryLabel);
                    }
                    mAdapter.addCategoryItems(categories);
                }
            });
        }

    }
}

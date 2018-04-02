package com.matija.spendless.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.matija.spendless.R;
import com.matija.spendless.ui.adapters.CategoryAdapter;
import com.matija.spendless.model.Category;

import com.matija.spendless.viewmodel.CategoryListViewModel;

import java.util.ArrayList;

import java.util.List;

/**
 * Created by matija on 28.2.18..
 */
public class CategoriesActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickListener {

    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        CategoryListViewModel categoryListViewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);
        categoryListViewModel.getCategoryList().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                // append 'add new category' to the end of the list
                categories.add(new Category("Add new category", R.drawable.ic_add_circle_black_24dp));
                mAdapter.addCategoryItems(categories);
            }
        });

        registerForContextMenu(mRecyclerView);
    }

    @Override
    public void onClick(Category category) {
        if (category.getName().equals("Add new category")) {
            //TODO initialize dialog to make new category
        }
    }

    @Override
    public void onCategoryMenuItemClick(Category category, MenuItem item) {

    }

}

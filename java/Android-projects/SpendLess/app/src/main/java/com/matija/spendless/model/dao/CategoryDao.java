package com.matija.spendless.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.matija.spendless.model.Category;

import java.util.List;

/**
 * Created by matija on 9.3.18..
 */

@Dao
public interface CategoryDao {

    @Insert
    public void insert(Category... categories);

    @Update
    public void update(Category... categories);

    @Delete
    public void delete(Category... categories);

    @Query("SELECT * FROM category")
    public LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM category")
    public List<Category> findAllCategories();

    @Query("SELECT * FROM category WHERE name=:name")
    public Category findCategoryByName(String name);
}

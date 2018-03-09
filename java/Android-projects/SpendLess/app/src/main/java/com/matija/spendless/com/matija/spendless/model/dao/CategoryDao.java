package com.matija.spendless.com.matija.spendless.model.dao;

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
    public List<Category> getAllCategories();
}

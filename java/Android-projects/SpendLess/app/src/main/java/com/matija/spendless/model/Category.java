package com.matija.spendless.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by matija on 9.3.18..
 */

@Entity
public class Category {

    @PrimaryKey(autoGenerate = true)
    private Long id = null;

    private String name;

    private Integer drawableId;

    public Category(String name, Integer drawableId) {
        this.name = name;
        this.drawableId = drawableId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(Integer drawableId) {
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

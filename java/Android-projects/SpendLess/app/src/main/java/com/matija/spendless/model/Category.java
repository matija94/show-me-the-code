package com.matija.spendless.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by matija on 9.3.18..
 */

@Entity
public class Category {

    @PrimaryKey
    private final Long id;

    private String name;

    public Category(final long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

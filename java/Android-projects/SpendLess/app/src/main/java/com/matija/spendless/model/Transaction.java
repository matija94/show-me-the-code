package com.matija.spendless.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by matija on 9.3.18..
 */

@Entity(foreignKeys = @ForeignKey(entity = Category.class,
                                  parentColumns = "id",
                                  childColumns ="category_id" ))
public class Transaction {

    @PrimaryKey
    private long id;

    private final Float value;

    private final Date dateTime;

    @ColumnInfo(name = "category_id")
    private final int categoryId;

    public Transaction(final long id, float value, Date dateTime, final int categoryId) {
        this.id = id;
        this.value = value;
        this.dateTime = dateTime;
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public Float getValue() {
        return value;
    }

    public Date getDateTime() {
        return dateTime;
    }

}

package com.matija.spendless.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by matija on 9.3.18..
 */

@Entity(foreignKeys = @ForeignKey(entity = Category.class,
                                  parentColumns = "id",
                                  childColumns ="category_id" ),
                                indices = {@Index(value = "category_id", name = "idx")})
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private Float value;

    private Date dateTime;

    private String description;

    @ColumnInfo(name = "category_id")
    private Integer categoryId;

    public Transaction(Long id, Float value, Date dateTime, Integer categoryId, String description) {
        this.id = id;
        this.value = value;
        this.dateTime = dateTime;
        this.categoryId = categoryId;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.matija.softtehn.model.embeddables;

import com.matija.softtehn.model.converters.DateConverter;

import javax.persistence.*;
import java.util.Date;

@Embeddable
public class DateTime {

    @Convert(converter = DateConverter.class)
    private Date createdAt;

    @Convert(converter = DateConverter.class)
    private Date updatedAt;

    public DateTime() {}

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static DateTime createDateTime() {
        DateTime dt = new DateTime();
        Date current = new Date();
        dt.setCreatedAt(current);
        dt.setUpdatedAt(current);
        return dt;
    }
}

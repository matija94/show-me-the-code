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
}

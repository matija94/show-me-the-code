package com.matija.softtehn.model.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Date;

@Converter
public class DateConverter implements AttributeConverter<Date, Long> {

    @Override
    public Long convertToDatabaseColumn(Date date) {
        if (date != null) {
            return date.getTime();
        }
        else return null;
    }

    @Override
    public Date convertToEntityAttribute(Long aLong) {
        if (aLong != null) {
            return new Date(aLong);
        }
        return null;
    }
}

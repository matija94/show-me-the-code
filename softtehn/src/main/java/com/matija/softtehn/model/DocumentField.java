package com.matija.softtehn.model;

import com.matija.softtehn.model.converters.DateConverter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DocumentField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long documentFieldId;

    @Column
    private int position;

    @Column
    private int intValue;

    @Column
    private String stringValue;

    @Column
    @Convert(converter = DateConverter.class)
    private Date dateValue;

    public long getDocumentFieldId() {
        return documentFieldId;
    }

    public void setDocumentFieldId(long documentFieldId) {
        this.documentFieldId = documentFieldId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}

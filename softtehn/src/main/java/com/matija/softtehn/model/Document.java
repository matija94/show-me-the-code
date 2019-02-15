package com.matija.softtehn.model;

import com.matija.softtehn.model.embeddables.DateTime;

import javax.persistence.*;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long documentId;

    @Embedded
    private DateTime dateTime;

    @Column
    private String data;

    public Document() {
    }

    public Document(DateTime dateTime, String data) {
        this.dateTime = dateTime;
        this.data = data;
    }

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
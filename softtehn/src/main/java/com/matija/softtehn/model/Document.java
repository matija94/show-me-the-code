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

    //TODO model document fields objects

    public Document() {
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
}
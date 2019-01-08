package com.matija.softtehn.model;

import com.matija.softtehn.model.embeddables.DateTime;

import javax.persistence.*;
import java.util.List;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long documentId;

    @Column
    private String code;

    @Column
    private String name;

    @Column
    private String description;

    @Embedded
    private DateTime dateTime;

    @OneToMany
    @JoinColumn(name = "document_id")
    private List<DocumentField> documentFields;

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<DocumentField> getDocumentFields() {
        return documentFields;
    }

    public void setDocumentFields(List<DocumentField> documentFields) {
        this.documentFields = documentFields;
    }
}

package com.matija.softtehn.model;

import com.matija.softtehn.model.enums.TemplateFieldType;

import javax.persistence.*;
import java.util.List;

@Entity
public class TemplateField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long templateFieldId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    @Enumerated
    private TemplateFieldType templateFieldType;

    @Column
    private boolean mandatory;

    @OneToMany
    @JoinColumn(name = "templateField_id")
    private List<DocumentField> documentFields;

    public long getTemplateFieldId() {
        return templateFieldId;
    }

    public void setTemplateFieldId(long templateFieldId) {
        this.templateFieldId = templateFieldId;
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

    public TemplateFieldType getTemplateFieldType() {
        return templateFieldType;
    }

    public void setTemplateFieldType(TemplateFieldType templateFieldType) {
        this.templateFieldType = templateFieldType;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public List<DocumentField> getDocumentFields() {
        return documentFields;
    }

    public void setDocumentFields(List<DocumentField> documentFields) {
        this.documentFields = documentFields;
    }
}

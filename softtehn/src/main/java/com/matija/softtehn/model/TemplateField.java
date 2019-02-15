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
    private boolean required;

    @Column
    private boolean isList;

    @OneToMany
    @JoinColumn(name = "template_field_id")
    private List<TemplateFieldItem> templateFieldItems;

    @OneToMany
    @JoinColumn(name = "template_field_id")
    private List<TemplateField> templateFields;

    public TemplateField() {}

    public TemplateField(String name, String description, TemplateFieldType templateFieldType, boolean required, boolean isList, List<TemplateFieldItem> templateFieldItems) {
        this.name = name;
        this.description = description;
        this.templateFieldType = templateFieldType;
        this.required = required;
        this.isList = isList;
        this.templateFieldItems = templateFieldItems;
    }

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

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isList() {
        return isList;
    }

    public void setList(boolean list) {
        isList = list;
    }

    public List<TemplateFieldItem> getTemplateFieldItems() {
        return templateFieldItems;
    }

    public void setTemplateFieldItems(List<TemplateFieldItem> templateFieldItems) {
        this.templateFieldItems = templateFieldItems;
    }

    public List<TemplateField> getTemplateFields() {
        return templateFields;
    }

    public void setTemplateFields(List<TemplateField> templateFields) {
        this.templateFields = templateFields;
    }
}

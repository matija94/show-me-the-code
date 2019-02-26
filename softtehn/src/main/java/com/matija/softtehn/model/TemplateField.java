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
    private TemplateFieldType type;

    @Column
    private boolean required;

    @Column
    private boolean isList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "complex_template_field_id")
    private List<TemplateField> fields;

    public TemplateField() {}

    public TemplateField(String name, String description, TemplateFieldType type,
                         boolean required, boolean isList, List<TemplateField> fields) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.required = required;
        this.isList = isList;
        this.fields = fields;
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

    public TemplateFieldType getType() {
        return type;
    }

    public void setType(TemplateFieldType type) {
        this.type = type;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public List<TemplateField> getFields() {
        return fields;
    }

    public void setFields(List<TemplateField> fields) {
        this.fields = fields;
    }

    public boolean isList() {
        return isList;
    }

    public void setList(boolean list) {
        isList = list;
    }
}

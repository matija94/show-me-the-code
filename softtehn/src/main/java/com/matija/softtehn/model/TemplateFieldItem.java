package com.matija.softtehn.model;

import javax.persistence.*;

@Entity
public class TemplateFieldItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long templateFieldItemId;

    @Column
    private String description;

    public TemplateFieldItem() {}

    public TemplateFieldItem(String description) {
        this.description = description;
    }

    public long getTemplateFieldItemId() {
        return templateFieldItemId;
    }

    public void setTemplateFieldItemId(long templateFieldItemId) {
        this.templateFieldItemId = templateFieldItemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

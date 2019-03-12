package com.matija.softtehn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matija.softtehn.model.embeddables.DateTime;

import javax.persistence.*;
import java.util.List;

@Entity
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long templateId;

    @Column
    private String name;

    @Column
    private String description;

    @Embedded
    private DateTime dateTime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "template_id")
    private List<TemplateField> templateFields;

    @OneToMany(mappedBy = "template")
    private List<Document> documents;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private Group group;

    public Template() {}

    public Template(String name, String description,
                    DateTime dateTime, List<TemplateField> templateFields,
                    List<Document> documents, Group group) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.templateFields = templateFields;
        this.documents = documents;
        this.group = group;
    }

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(long templateId) {
        this.templateId = templateId;
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

    public List<TemplateField> getTemplateFields() {
        return templateFields;
    }

    public void setTemplateFields(List<TemplateField> templateFields) {
        this.templateFields = templateFields;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}

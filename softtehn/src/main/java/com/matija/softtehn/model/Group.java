package com.matija.softtehn.model;

import com.matija.softtehn.model.embeddables.DateTime;

import javax.persistence.*;
import java.util.List;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long groupId;

    @Column
    private String name;

    @Embedded
    private DateTime dateTime;

    @OneToMany
    @JoinColumn(name = "group_id")
    private List<User> users;

    @OneToMany
    @JoinColumn(name = "group_id")
    private List<Template> templates;

    public Group() {}

    public Group(String name, DateTime dateTime, List<User> users, List<Template> templates) {
        this.name = name;
        this.dateTime = dateTime;
        this.users = users;
        this.templates = templates;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

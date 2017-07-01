package com.springmvc.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "provider_pro")
public class Provider extends AbstractDatable {
    private int id;
    private String name;
    private Set<Account> accounts;

    //<editor-fold> Default getters and setters

    @Id
    @Column(name = "pro_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pro_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "provider", cascade = CascadeType.ALL)
    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    @JsonIgnore
    @Column(name = "pro_date_created")
    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    @JsonIgnore
    @Column(name = "pro_date_modified")
    public Date getDateModified() {
        return dateModified;
    }

    //</editor-fold>
}

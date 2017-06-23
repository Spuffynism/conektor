package com.springmvc.model.provider;

import com.springmvc.model.account.Account;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "provider_pro")
public class Provider {
    private int id;
    private String name;
    private String iconPath;
    private String textColor;
    private String panelColor;
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

    @Basic
    @Column(name = "pro_icon_path")
    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    @Basic
    @Column(name = "pro_text_color")
    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    @Basic
    @Column(name = "pro_panel_color")
    public String getPanelColor() {
        return panelColor;
    }

    public void setPanelColor(String panelColor) {
        this.panelColor = panelColor;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "provider", cascade = CascadeType.ALL)
    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    //</editor-fold>
}

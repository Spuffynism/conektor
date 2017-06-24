package com.springmvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_usr")
public class User implements UserDetails {
    private int id;
    private String username;
    private String email;
    private String password;
    private int permission;
    private int attemptedPasswordChanges;
    private Set<Account> accounts;

    //<editor-fold> Basic getters and setters

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "usr_username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "usr_email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    @Basic
    @Column(name = "usr_password")
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    @Basic
    @Column(name = "usr_permission")
    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    @JsonIgnore
    @Basic
    @Column(name = "usr_attempted_password_changes")
    public int getAttemptedPasswordChanges() {
        return attemptedPasswordChanges;
    }

    @JsonProperty
    public void setAttemptedPasswordChanges(int nbTentativesChangementMotDePasse) {
        this.attemptedPasswordChanges = attemptedPasswordChanges;
    }

    //</editor-fold>

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public boolean isAdmin() {
        return Permission.ADMIN.isContainedIn(permission);
    }

    public boolean isUser() {
        return Permission.USER.isContainedIn(permission);
    }

    public void addPermission(Permission newPermission) {
        setPermission(newPermission.addTo(permission));
    }

    public void removePermission(Permission oldPermission) {
        setPermission(oldPermission.removeFrom(permission));
    }

    public boolean hasNoPermissions() {
        return permission == Permission.NONE.value();
    }

    //<editor-fold> UserDetails Authorization getters and setters

    @JsonIgnore
    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonProperty
    public void setAuthorities(List<? extends GrantedAuthority> grantedAuthorities) {
    }

    @JsonIgnore
    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty
    public void setAccountNonExpired(boolean accountNonExpired) {
    }

    @JsonIgnore
    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty
    public void setAccountNonLocked(boolean accountNonLocked) {
    }

    @JsonIgnore
    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    }

    @JsonIgnore
    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

    @JsonProperty
    public void setEnabled(boolean enabled) {

    }

    //</editor-fold>
}
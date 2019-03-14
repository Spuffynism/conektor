package xyz.ndlr.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import xyz.ndlr.domain.AbstractDatable;
import xyz.ndlr.domain.Email;
import xyz.ndlr.domain.account.Account;
import xyz.ndlr.domain.dispatching.SupportedProvider;
import xyz.ndlr.security.auth.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_usr")
public class User extends AbstractDatable {
    private int id;
    private Username username;
    private Email email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private int role;
    @JsonIgnore
    private int attemptedPasswordChanges;
    @JsonIgnoreProperties("user")
    private Set<Account> accounts;

    public User() {
    }

    @Transient
    @JsonIgnore
    public Account getAccount(SupportedProvider provider) {
        return accounts.stream()
                .filter(x -> x.getProvider().getName().equals(provider.value()))
                .collect(Collectors.toList())
                .get(0);
    }

    @Transient
    @JsonIgnore
    public boolean isAdmin() {
        return Role.ADMIN.isContainedIn(role);
    }

    @Transient
    @JsonIgnore
    public boolean isUser() {
        return Role.USER.isContainedIn(role);
    }

    public void addRole(Role newRole) {
        setRole(newRole.addTo(role));
    }

    public void removeRole(Role oldRole) {
        setRole(oldRole.removeFrom(role));
    }

    public boolean hasNoRole() {
        return role == Role.NONE.value();
    }

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
    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    @Basic
    @Column(name = "usr_email")
    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    @Basic
    @Column(name = "usr_password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "usr_permission")
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setRole(Role role) {
        this.role = role.value();
    }

    @Basic
    @Column(name = "usr_attempted_password_changes")
    public int getAttemptedPasswordChanges() {
        return attemptedPasswordChanges;
    }

    public void setAttemptedPasswordChanges(int attemptedPasswordChanges) {
        this.attemptedPasswordChanges = attemptedPasswordChanges;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "acc_user_id")
    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    @JsonIgnore
    @Column(name = "usr_date_created")
    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    @JsonIgnore
    @Column(name = "usr_date_modified")
    public Date getDateModified() {
        return dateModified;
    }
}
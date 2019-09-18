package xyz.ndlr.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import xyz.ndlr.domain.AbstractDatable;
import xyz.ndlr.domain.account.Account;
import xyz.ndlr.domain.password.HashedPassword;
import xyz.ndlr.infrastructure.dispatching.SupportedProvider;
import xyz.ndlr.infrastructure.security.auth.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_usr")
public class User extends AbstractDatable {
    private UserId id;
    private Username username;
    private Email email;
    // TODO(nich): Move this to value object
    @JsonIgnore
    private HashedPassword password;
    // TODO(nich): Move this to value object
    @JsonIgnore
    private int role;
    @JsonIgnore
    // TODO(nich): Move this to a UserChangingPassword class
    private int attemptedPasswordChanges;
    @JsonIgnoreProperties("user")
    private Set<Account> accounts;

    public User() {
    }

    public User(Username username, Email email, HashedPassword password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
    public UserId getId() {
        return id;
    }

    public void setId(UserId id) {
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
    public HashedPassword getPassword() {
        return password;
    }

    public void setPassword(HashedPassword password) {
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
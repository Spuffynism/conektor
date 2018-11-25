package xyz.ndlr.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.ndlr.domain.dispatching.SupportedProvider;
import xyz.ndlr.security.auth.Role;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_usr")
public class User extends AbstractDatable implements UserDetails {
    private int id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private int role;
    @JsonIgnore
    private int attemptedPasswordChanges;
    @JsonIgnoreProperties("user")
    private Set<Account> accounts;
    @JsonIgnore
    private List<GrantedAuthority> grantedAuthorities;

    public User() {
        // needed because we use the UserDetails class but not its grantedAuthoritites property.
        grantedAuthorities = Collections.emptyList();
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

    //<editor-fold> Default getters and setters

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
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

    @Override
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

    //</editor-fold>

    //<editor-fold> UserDetails Authorization getters and setters

    @JsonIgnore
    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @JsonProperty
    public void setAuthorities(List<? extends GrantedAuthority> grantedAuthorities) { }

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
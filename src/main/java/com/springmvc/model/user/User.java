package com.springmvc.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springmvc.model.achat.Achat;
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
    private String prenom;
    private String nom;
    private String username;
    private String email;
    private String password;
    private int nbTentativesChangementMotDePasse;
    private Set<Achat> achats;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "usr_prenom")
    public String getPrenom() {
        return prenom;
    }

    public User setPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    @Basic
    @Column(name = "usr_nom")
    public String getNom() {
        return nom;
    }

    public User setNom(String nom) {
        this.nom = nom;
        return this;
    }

    @Basic
    @Column(name = "usr_username")
    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    @Basic
    @Column(name = "usr_email")
    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @JsonIgnore
    @Basic
    @Column(name = "usr_mot_de_passe")
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @JsonIgnore
    @Basic
    @Column(name = "usr_tentatives_changement_mot_de_passe")
    public int getNbTentativesChangementMotDePasse() {
        return nbTentativesChangementMotDePasse;
    }

    @JsonProperty
    public User setNbTentativesChangementMotDePasse(int nbTentativesChangementMotDePasse) {
        this.nbTentativesChangementMotDePasse = nbTentativesChangementMotDePasse;
        return this;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    public Set<Achat> getAchats() {
        return achats;
    }

    public User setAchats(Set<Achat> achats) {
        this.achats = achats;
        return this;
    }

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
}
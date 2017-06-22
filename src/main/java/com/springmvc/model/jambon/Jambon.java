package com.springmvc.model.jambon;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springmvc.model.achat.Achat;
import com.springmvc.model.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "jambon_jam")
public class Jambon {
    private int id;
    private String nom;
    private double prix;
    private Double poids;
    private Set<Achat> achats;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jam_id")
    public int getId() {
        return id;
    }

    public Jambon setId(int id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "jam_nom")
    public String getNom() {
        return nom;
    }

    public Jambon setNom(String nom) {
        this.nom = nom;
        return this;
    }

    @Basic
    @Column(name = "jam_prix")
    public double getPrix() {
        return prix;
    }

    public Jambon setPrix(double prix) {
        this.prix = prix;
        return this;
    }

    @Basic
    @Column(name = "jam_poids")
    public Double getPoids() {
        return poids;
    }

    public Jambon setPoids(Double poids) {
        this.poids = poids;
        return this;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jambon")
    public Set<Achat> getAchats() {
        return achats;
    }

    public Jambon setAchats(Set<Achat> achats) {
        this.achats = achats;
        return this;
    }
}
